package test.source.boor;

import org.junit.Before;
import org.junit.Test;
import source.boor.Sakugabooru;
import source.boor.Yandere;
import source.еnum.Api;
import source.еnum.Format;

import static org.junit.Assert.*;


public class YandereTest {

    @Before
    public void setUp(){
        Yandere.get().setFormat(Format.JSON);
    }

    @Test
    public void getApi_Test() throws Exception {
        assertEquals(Api.ADVANCED, Yandere.get().getApi());
    }

    @Test
    public void getDataType_Test() throws Exception {
        assertEquals(Format.JSON, Yandere.get().getFormat());
    }

    @Test
    public void getCustomRequest_Test() throws Exception {
        String request = Yandere.get().getCustomRequest("request");
        String expected = "https://yande.re/request";
        assertEquals(expected, request);
    }

//    @Test
//    public void getCompleteRequest_Test() throws Exception {
//        int itemCount = 100;
//        String request = "hatsune_miku";
//        int pid = 0;
//        String link = Yandere.get().getCompleteRequest(itemCount, request, pid);
//        String expected = "https://yande.re/post.json?limit=100&tags=hatsune_miku&page=0";
//        assertEquals(expected, link);
//
//        Yandere.get().setFormat(Format.XML);
//        link = Yandere.get().getCompleteRequest(itemCount, request, pid);
//        expected = "https://yande.re/post.xml?limit=100&tags=hatsune_miku&page=0";
//        assertEquals(expected, link);
//    }

}