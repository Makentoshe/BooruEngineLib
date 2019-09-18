import com.makentoshe.boorusdk.base.BooruManager
import com.makentoshe.boorusdk.base.model.TagCategory
import com.makentoshe.boorusdk.base.model.Type
import com.makentoshe.boorusdk.base.request.*
import com.makentoshe.boorusdk.base.request.comment.*
import com.makentoshe.boorusdk.base.request.pool.GetPoolRequest
import com.makentoshe.boorusdk.base.request.pool.GetPoolsRequest
import com.makentoshe.boorusdk.base.request.post.GetPostRequest
import com.makentoshe.boorusdk.base.request.post.GetPostsRequest
import cookie.CookieStorage
import cookie.SessionCookie
import function.*
import function.comment.CreateComment
import function.comment.DeleteComment
import function.comment.GetComment
import function.comment.GetComments
import function.comment.GetPostComments
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit

open class DanbooruManager(
    protected val danbooruApi: DanbooruApi, protected val cookieStorage: CookieStorage
) : BooruManager {

    override fun getPosts(request: GetPostsRequest): String {
        return GetPosts(danbooruApi).apply(request)
    }

    override fun getPost(request: GetPostRequest): String {
        return GetPost(danbooruApi).apply(request)
    }

    override fun newComment(request: NewCommentRequest): String {
        return CreateComment(danbooruApi).apply(request)
    }

    override fun getAutocomplete(request: AutocompleteRequest): String {
        val response = danbooruApi.getAutocomplete(
            type = request.type.name.toLowerCase(),
            term = request.term
        ).execute()
        return String(extractBody(response))
    }

    override fun getComment(request: GetCommentRequest): String {
        return GetComment(danbooruApi).apply(request)
    }

    override fun getComments(request: GetCommentsRequest): String {
        return GetComments(danbooruApi).apply(request)
    }

    override fun getPostComments(request: GetPostCommentsRequest): String {
        return GetPostComments(danbooruApi).apply(request)
    }

    override fun getPostHttp(request: GetPostRequest): String {
        val response = danbooruApi.getPostHttp(request.postId).execute()
        return String(extractBody(response))
    }

    override fun getTags(request: TagsRequest): String {
        val tagId = request.id
        val response = if (tagId != null) {
            danbooruApi.getTag(
                type = request.type.name.toLowerCase(),
                id = tagId
            ).execute()
        } else {
            val hideEmpty = when (request.hideEmpty) {
                true -> "yes"
                false -> "no"
                else -> null
            }
            val hasWiki = when (request.hasWiki) {
                true -> "yes"
                false -> "no"
                else -> null
            }
            val hasArtist = when (request.hasArtist) {
                true -> "yes"
                false -> "no"
                else -> null
            }
            val category = when (request.category) {
                TagCategory.GENERAL -> 0
                TagCategory.ARTIST -> 1
                TagCategory.COPYTIGHT -> 3
                TagCategory.CHARACTER -> 4
                else -> null
            }
            danbooruApi.getTags(
                type = request.type.name.toLowerCase(),
                pattern = request.pattern,
                name = request.name,
                hideEmpty = hideEmpty,
                hasWiki = hasWiki,
                hasArtist = hasArtist,
                order = request.orderby?.name?.toLowerCase(),
                category = category
            ).execute()
        }
        return String(extractBody(response))
    }

    override fun login(request: LoginRequest): Boolean {
        return Login(danbooruApi).apply(request)
    }

    override fun votePost(request: VotePostRequest, parser: (ByteArray) -> Int): Int {
        TODO("Not implemented. For testing this functional need Gold Account permissions")
    }

    override fun deleteComment(request: DeleteCommentRequest): String {
        return DeleteComment(danbooruApi, cookieStorage).apply(request)
    }

    override fun getPool(request: GetPoolRequest): String {
        return GetPool(danbooruApi).apply(request)
    }

    override fun getPools(request: GetPoolsRequest): String {
        return GetPools(danbooruApi).apply(request)
    }

    private fun extractBody(response: Response<ByteArray>): ByteArray {
        return if (response.isSuccessful) {
            response.body() ?: byteArrayOf()
        } else {
            throw Exception(response.errorBody()?.string() ?: response.message())
        }
    }

    companion object {
        fun build(): DanbooruManager {
            val cookieStorage = SessionCookie()
            val client = OkHttpClient.Builder().cookieJar(cookieStorage).build()
            val retrofit = Retrofit.Builder().client(client).baseUrl("https://danbooru.donmai.us")
                .addConverterFactory(ByteArrayConverterFactory()).build()
            return DanbooruManager(retrofit.create(DanbooruApi::class.java), cookieStorage)
        }
    }
}

fun main() {
    val manager = DanbooruManager.build()

    val result = manager.getPool(
        GetPoolRequest(
            type = Type.JSON,
            poolId = 12746
        )
    )

    println(result)
}
