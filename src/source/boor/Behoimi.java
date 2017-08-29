package source.boor;

import engine.BooruEngineException;
import engine.HttpsConnection;
import engine.Method;
import module.interfacе.CommentModuleInterface;
import module.interfacе.LoginModuleInterface;
import module.interfacе.RemotePostModuleInterface;
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
 * Implements <tt>LoginModuleInterface</tt>, <tt>RemotePostModuleInterface</tt>, <tt>CommentModuleInterface</tt>.
 */
/*
    Loging is OK.
    Commenting is OK.
    Post Voting disable.
 */
public class Behoimi extends AbstractBoorAdvanced implements LoginModuleInterface, RemotePostModuleInterface, CommentModuleInterface {

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

    @Override
    public void logIn(String login, String password) throws BooruEngineException {
        String postData = "url=&user%5Bname%5D="+login+"&user%5Bpassword%5D="+password+"&commit=Login";

        HttpsConnection connection = new HttpsConnection()
                .setRequestMethod(Method.POST)
                .setUserAgent(HttpsConnection.DEFAULT_USER_AGENT)
                .setBody(postData)
                .openConnection(getAuthenticateRequest());

        for (int i = 0; i < connection.getHeader("Set-Cookie").size(); i++){
            String[] data = connection.getHeader("Set-Cookie").get(i).split("; ")[0].split("=");
            if (data.length == 2) this.loginData.put(data[0], data[1]);        }
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
    public String getVotePostRequest() {
        return null;
    }

    @Override
    public boolean commentPost(int id, String body, boolean postAsAnon, boolean bumpPost) throws BooruEngineException {
        String cbody = "comment%5Bpost_id%5D=" + id +
                "&comment%5Bbody%5D=" + body.replaceAll(" ", "+") +
                "&commit=" + (bumpPost ? "Post" : "Post+without+bumping");

        new HttpsConnection()
                .setRequestMethod(Method.POST)
                .setUserAgent(HttpsConnection.DEFAULT_USER_AGENT)
                .setCookies(getLoginData().toString().replaceAll(", ", "; "))
                .setBody(cbody)
                .openConnection(getCreateCommentRequest(id));

        return true;
    }

    @Override
    public String getCreateCommentRequest(int id) {
        return getCustomRequest("/comment/create");
    }

}
