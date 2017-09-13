package module;

import com.sun.istack.internal.Nullable;
import engine.BooruEngineException;

/**
 * Interface with the basic methods for storing and creating a user data, with which a *boor will work.
 * <p>
 * Give access for login and other actions, which requires user data.
 */
public interface LoginModule {
//TODO: use URLEncoder.encode(data, String.valueOf(Charset.defaultCharset()))
    /**
     * Login a user.
     *
     * @param login user login
     * @param password user pass
     * @throws BooruEngineException - when something go wrong.
     * Use <tt>getCause</tt> to see details.
     */
    void logIn(final String login, final String password) throws BooruEngineException;

    /**
     * Logoff a user. Remove all user data.
     */
    void logOff();

    /**
     * Get all user data.
     *
     * @return any object, which storage login data.
     */
    Object getLoginData();

    /**
     * Get request for authentication.
     *
     * @return constructed request to server.
     */
    String getAuthenticateRequest();

    /**
     * Transforms the user data into cookies.
     *
     * @return user data in Cookie format. Can return {@code null} if data not defined or else.
     */
    @Nullable
    String getCookieFromLoginData();
}