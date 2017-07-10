package source.boor;

import source.еnum.Api;
import source.еnum.DataType;

/**
 * Singleton.
 * Storage data about Konachan API and method for getting request.
 */
public class Konachan extends AbstractBoor {

    private static final Konachan instance = new Konachan();

    public static Konachan get() {
        return instance;
    }

    private final Api api = Api.ADVANCED;

    private final DataType dataType = DataType.XML_BASIC;

    @Override
    public Api getApi() {
        return api;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public String getCompleteRequest(int itemCount, String request, int pid) {
        return "https://konachan.com/post.xml?" +
                "limit=" + itemCount + "&tags=" + request + "&page=" + pid;
    }
}
