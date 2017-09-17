package module;

import com.sun.istack.internal.NotNull;
import engine.BooruEngineException;
import source.еnum.Rating;

import java.io.File;

/**
 * Interface for creating posts and upload images on *boor.
 */
public interface UploadModule {

    /**
     * Method create post(upload) on *booru.
     *
     * @param post      - file which will be upload. It must be image of gif-animation.
     *                  Also it can be video file with .webm extension.
     * @param tags      - tags are describe file content.
     *                  They separates by spaces, so, spaces in title must be replace by underscores.
     * @param title     - post title.
     * @param source    - source from file was get. It must be URL like "https://sas.com/test.jpg" or something else.
     * @param rating    - post rating. As usual it can be {@code Rating.SAFE}, {@code Rating.QUESTIONABLE} or
     *                  {@code Rating.EXPLICIT}
     * @param parent_id - also known as Post Relationships, are a means of linking together groups of related posts.
     *                  One post (normally the "best" version) is chosen to be the parent,
     *                  while the other posts are made its children.
     * @return response from POST-request.
     * @throws BooruEngineException when something go wrong. Use <code>GetCause</code> to see more details.
     */
    String createPost(
            @NotNull final File post,
            @NotNull final String tags,
            final String title,
            final String source,
            @NotNull final Rating rating,
            final String parent_id

    ) throws BooruEngineException;


    /**
     * Get address for creating <code>Method.POST</code> request for creating post.
     *
     * @return the constructed request address to server.
     */
    String getCreatePostRequest();
}
