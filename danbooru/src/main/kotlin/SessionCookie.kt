import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SessionCookie : CookieStorage, CookieJar {

    private var cookies = HashSet<Cookie>()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach { cookie ->
            val cookieToRemove = this.cookies.filter { it.name == cookie.name }
            if (cookieToRemove.isNotEmpty()) {
                this.cookies.removeAll(cookieToRemove)
            }
            this.cookies.add(cookie)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies.toList()
    }

    override fun getCookie(url: HttpUrl): List<Cookie> {
        return loadForRequest(url)
    }

    override fun setCookie(url: HttpUrl, cookies: List<Cookie>) {
        saveFromResponse(url, cookies)
    }

    override fun getCookie(name: String): Cookie {
        return cookies.stream().filter { it.name == name }.findFirst().get()
    }

    override fun hasCookie(name: String): Boolean {
        return cookies.stream().filter { it.name == name }.count() > 0
    }
}
