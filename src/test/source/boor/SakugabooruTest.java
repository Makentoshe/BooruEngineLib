package test.source.boor;

import org.junit.Before;
import org.junit.Test;
import source.boor.Sakugabooru;
import source.еnum.Api;
import source.еnum.Format;

import static org.junit.Assert.*;


public class SakugabooruTest {

    @Before
    public void setUp(){
        Sakugabooru.get().setFormat(Format.JSON);
    }

    @Test
    public void getApi_Test() throws Exception {
        assertEquals(Api.ADVANCED, Sakugabooru.get().getApi());
    }

    @Test
    public void getDataType_Test() throws Exception {
        assertEquals(Format.JSON, Sakugabooru.get().getFormat());
    }

    @Test
    public void getCustomRequest_Test() throws Exception {
        String request = Sakugabooru.get().getCustomRequest("request");
        String expected = "https://sakugabooru.com/request";
        assertEquals(expected, request);
    }

    @Test
    public void getIdRequest_Test() throws Exception{
        String request = Sakugabooru.get().getIdRequest(401562);
        String expected = "https://sakugabooru.com/post.json?tags=id:401562";
        assertEquals(expected, request);
    }

    @Test
    public void getPackByTagsRequest_Test() throws Exception{
        String request = Sakugabooru.get().getPackByTagsRequest(10, "hatsune_miku", 0);
        String expected = "https://sakugabooru.com/post.json?tags=hatsune_miku&limit=10&page=0";
        assertEquals(expected, request);
    }

}