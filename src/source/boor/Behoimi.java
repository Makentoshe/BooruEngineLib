package source.boor;

import engine.BooruEngineException;
import engine.connector.HttpsConnection;
import engine.connector.Method;
import module.CommentCreatorModule;
import module.LoginModule;
import module.RemotePostModule;
import source.Post;
import source.еnum.Format;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton.
 * <p>
 * Describe Behoimi.
 * <p>
 * Implements <tt>LoginModule</tt>, <tt>RemotePostModule</tt>, <tt>CommentCreatorModule</tt>.
 */
/*
    Loging is OK.
    Commenting is OK.
    Post Voting disable.
 */
public class Behoimi extends AbstractBoorAdvanced implements LoginModule, RemotePostModule, CommentCreatorModule {

    private static final Behoimi instance = new Behoimi();

    private final Map<String, String> loginData = new HashMap<>();

    public static Behoimi get() {
        return instance;
    }

    private Behoimi() {
        super();
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public String getCustomRequest(String request) {
        return "http://behoimi.org" + request;
    }

    @Override
    public String getPostByIdRequest(int id, Format format) {
        return getCustomRequest("/post/index." + format.toString().toLowerCase() + "?tags=id:" + id);
    }

    @Override
    public String getCommentsByPostIdRequest(int post_id, Format format) {
        return getCustomRequest("/comment/index." + format.toString().toLowerCase() + "?post_id=" + post_id);
    }

    @Override
    public Post newPostInstance(final Map<String, String> attributes) {
        Post post = new Post(instance);
        //create Entry
        Set<Map.Entry<String, String>> entrySet = attributes.entrySet();
        //for each attribute
        for (Map.Entry<String, String> pair : entrySet) {
            switch (pair.getKey()) {
                case "id": {
                    post.setId(Integer.parseInt(pair.getValue()));
                    break;
                }
                case "md5": {
                    post.setMd5(pair.getValue());
                    break;
                }
                case "rating": {
                    post.setRating(pair.getValue());
                    break;
                }
                case "score": {
                    post.setScore(Integer.parseInt(pair.getValue()));
                    break;
                }
                case "preview_url": {
                    post.setPreview_url(pair.getValue());
                    break;
                }
                case "tags": {
                    post.setTags(pair.getValue());
                    break;
                }
                case "sample_url": {
                    post.setSample_url(pair.getValue());
                    break;
                }
                case "file_url": {
                    post.setFile_url(pair.getValue());
                    break;
                }
                case "source": {
                    post.setSource(pair.getValue());
                    break;
                }
                case "creator_id": {
                    post.setCreator_id(Integer.parseInt(pair.getValue()));
                    break;
                }
                case "has_comments": {
                    if ("true".equals(pair.getValue())) {
                        post.setHas_comments(true);
                    } else {
                        post.setHas_comments(false);
                    }
                    break;
                }
                case "created_at": {
                    post.setCreate_Time(pair.getValue());
                    break;
                }
            }
        }
        //after all check comments flag
        if (post.isHas_comments()) {
            //and if true - setup comments url.
            post.setComments_url(instance.getCommentsByPostIdRequest(post.getId()));
        }
        return post;
    }

    /**
     * Create connection to server and get user data - login cookies.
     * All necessary data will be stored while method is work,
     * so there is no reason try to store data from <code>HttpsConnection</code>.
     *
     * @param login    user login.
     * @param password user pass.
     * @return connection with all data about request.
     * @throws BooruEngineException when something go wrong. Use <code>getCause</code> to see more details.
     *                              Note that exception can be contain one of:
     *                              <p>{@code IllegalStateException} will be thrown when the user data is not defined.
     *                              <p>{@code BooruEngineConnectionException} will be thrown when something go wrong with connection.
     *                              <p>{@code AuthenticationException} will be thrown when the authentication failed
     *                              and response did not contain a login cookies.
     */
    @Override
    public HttpsConnection logIn(String login, String password) throws BooruEngineException {
        String postData = "url=&user%5Bname%5D="+login+"&user%5Bpassword%5D="+password+"&commit=Login";

        HttpsConnection connection = new HttpsConnection()
                .setRequestMethod(Method.POST)
                .setUserAgent(HttpsConnection.getDefaultUserAgent())
                .setBody(postData)
                .openConnection(getAuthenticateRequest());

        for (int i = 0; i < connection.getHeader("Set-Cookie").size(); i++){
            String[] data = connection.getHeader("Set-Cookie").get(i).split("; ")[0].split("=");
            if (data.length == 2) this.loginData.put(data[0], data[1]);
        }
            return connection;
    }

    @Override
    public void logOff() {
        this.loginData.clear();
    }

    @Override
    public Object getLoginData() {
        return this.loginData;
    }

    @Override
    public String getCookieFromLoginData() {
        return getLoginData().toString().replaceAll(", ", "; ").replaceAll("\\{","").replaceAll("\\}", "");
    }

    @Override
    public String getAuthenticateRequest() {
        return getCustomRequest("/user/authenticate");
    }

    @Override
    public boolean commentPost(int id, String body, boolean postAsAnon, boolean bumpPost) throws BooruEngineException {
        String cbody = "comment%5Bpost_id%5D=" + id +
                "&comment%5Bbody%5D=" + body.replaceAll(" ", "+") +
                "&commit=" + (bumpPost ? "Post" : "Post+without+bumping");

        new HttpsConnection()
                .setRequestMethod(Method.POST)
                .setUserAgent(HttpsConnection.getDefaultUserAgent())
                .setCookies(getLoginData().toString().replaceAll(", ", "; "))
                .setBody(cbody)
                .openConnection(getCommentRequest(id));

        return true;
    }

    @Override
    public String getCommentRequest(int id) {
        return getCustomRequest("/comment/create");
    }

}
