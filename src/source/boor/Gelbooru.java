package source.boor;

import source.еnum.Api;

/**
 * Singleton.
 * Storage data about Gelbooru API and method for getting request
 */
public final class Gelbooru extends BoorAbstract {

    private static final Gelbooru instance = new Gelbooru();

    public static Gelbooru get() {
        return instance;
    }

    private final Api api = Api.BASICS;

    @Override
    public Api getApi() {
        return api;
    }

    @Override
    public String getCompleteRequest(int itemCount, String request, int pid) {
        return "https://gelbooru.com/index.php?page=dapi&s=post&q=index&" +
                "limit=" + itemCount + "&tags=" + request + "&pid=" + pid;
    }
}