package module;

import engine.BooruEngineException;

/**
 * Interface with the basic methods for commenting *boor posts.
 * <p>
 * Require login information - so use <tt>LoginModule</tt> for getting access to user data.
 */
public interface CommentCreatorModule {

    /**
     * Create comment for post. Note: <strong>Be careful: Not all *boors support "postAsAnon" or "bumpPost" params.</strong>
     *
     * @param id post id, for which comment will be created.
     * @param body comment body.
     * @param postAsAnon using for anonymously posting.
     * @param bumpPost using for bump up post.
     * @return {@code true} if success.
     * @throws BooruEngineException if something go wrong. Use <code>GetCause</code> to see more details.
     */
    boolean commentPost(final int id, final String body, final boolean postAsAnon, final boolean bumpPost) throws BooruEngineException;

    /**
     * Get address for creating <code>Method.POST</code> request for creating comment.
     *
     * @param id post id, for which comment will be created.
     * @return the constructed request address to server.
     */
    String getCommentRequest(final int id);
}
