package test.source.boor;

import org.junit.Test;
import source.boor.Danbooru;
import source.boor.Konachan;
import source.еnum.Format;

import static org.junit.Assert.*;


public class AbstractBoorAdvancedTest {

    @Test
    public void getIdRequest_Test() throws Exception {
        String request = Danbooru.get().getIdRequest(2281488);
        String expected = "https://danbooru.donmai.us/posts/2281488.json";
        assertEquals(expected, request);
    }

    @Test
    public void getIdRequestWithFormat_Test() throws Exception {
        String request = Danbooru.get().getIdRequest(2281488, Format.XML);
        String expected = "https://danbooru.donmai.us/posts/2281488.xml";
        assertEquals(expected, request);
    }

    @Test
    public void getPackRequest_Test() throws Exception {
        String request = Konachan.get().getPackByTagsRequest(10, "hatsune_miku", 0);
        String expected = "https://konachan.com/posts.json?tags=hatsune_miku&limit=10&page=0";
        assertEquals(expected, request);
    }

    @Test
    public void getPackRequestWithFormat_Test() throws Exception {
        String request = Konachan.get().getPackByTagsRequest(10, "hatsune_miku", 0, Format.XML);
        String expected = "https://konachan.com/posts.xml?tags=hatsune_miku&limit=10&page=0";
        assertEquals(expected, request);
    }

}