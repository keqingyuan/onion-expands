package cc.kebei.expands.request.http;

/**
 * @author Kebei
 */
public interface HttpRequestGroup {

    String getCookie();

    HttpRequestGroup clearCookie();

    HttpRequest request(String url);

}
