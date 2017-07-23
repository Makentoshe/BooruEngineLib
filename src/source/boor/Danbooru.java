package source.boor;

import source.Post;
import source.еnum.Boor;
import source.еnum.Format;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Singleton.
 * Storage data about Danbooru API and method for getting request
 */
public class Danbooru extends AbstractBoorAdvanced {

    private static final Danbooru instance = new Danbooru();

    public static Danbooru get() {
        return instance;
    }



    public void setFormat(Format format){
        this.format = format;
    }



    @Override
    public String getCustomRequest(String request) {
        return "https://danbooru.donmai.us/" + request;
    }

    @Override
    public String getPackByTagsRequest(int limit, String tags, int page, Format format){
        return getCustomRequest("posts."+format.toString().toLowerCase()+"?tags="+tags+"&limit=" + limit + "&page=" + page);
    }

    public String getCommentByPostIdRequest(int post_id, Format format){
        return getCustomRequest("comments."+format.toString().toLowerCase()+"?group_by=comment&search[post_id]=" + post_id);
    }

    @Override
    public Post newPostInstance(HashMap<String, String> attributes){
        Post post = new Post(Boor.Danbooru);
        //create Entry
        Set<Map.Entry<String, String>> entrySet = attributes.entrySet();
        //for each attribute
        for (Map.Entry<String, String> pair : entrySet) {
            switch (pair.getKey()){
                case "id":{
                    post.setId(Integer.parseInt(pair.getValue()));
                    break;
                }
                case "md5":{
                    post.setMd5(pair.getValue());
                    break;
                }
                case "rating":{
                    post.setRating(pair.getValue());
                    break;
                }
                case "score":{
                    post.setScore(Integer.parseInt(pair.getValue()));
                    break;
                }
                case "preview_file_url":{
                    post.setPreview_url("https://danbooru.donmai.us" + pair.getValue());
                    break;
                }
                case "tag_string":{
                    post.setTags(pair.getValue());
                    break;
                }
                case "file_url":{
                    post.setSample_url("https://danbooru.donmai.us" + pair.getValue());
                    break;
                }
                case "large_file_url":{
                    post.setFile_url("https://danbooru.donmai.us" + pair.getValue());
                    break;
                }
                case "source":{
                    post.setSource(pair.getValue());
                    break;
                }
            }
        }
        return post;
    }

}
