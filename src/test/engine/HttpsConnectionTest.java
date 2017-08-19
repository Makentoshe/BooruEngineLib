package test.engine;

import engine.BooruEngineException;
import engine.HttpsConnection;
import org.junit.Test;
import source.boor.Gelbooru;

import static org.junit.Assert.*;

public class HttpsConnectionTest {

    @Test
    public void successRequest_test() throws Exception {
        HttpsConnection httpsConnection = new engine.HttpsConnection()
                .setRequestMethod("GET")
                .setUserAgent("BooruEngineLib(mkliverout@gmail.com)/1.0")
                .openConnection(Gelbooru.get().getPackByTagsRequest(1, "touhou", 0));
        assertEquals(200, httpsConnection.getResponseCode());
    }

    @Test(expected = BooruEngineException.class)
    public void failedRequest_Test() throws Exception {
        new HttpsConnection()
                .setRequestMethod("GET")
                .setUserAgent("BooruEngineLib(mkliverout@gmail.com)/1.0")
                .openConnection("sas");
    }

}
