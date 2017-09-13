package module;

import com.sun.istack.internal.NotNull;
import engine.BooruEngineException;
import source.еnum.Rating;

import java.io.File;

/**
 * Interface for creating posts and upload images on *boor.
 */
public interface UploadModule {

    String createPost(
            @NotNull final File post,
            @NotNull final String tags,
            final String title,
            final String source,
            @NotNull final Rating rating,
            final String parent

    ) throws BooruEngineException;

     String getCreatePostRequest();
}
