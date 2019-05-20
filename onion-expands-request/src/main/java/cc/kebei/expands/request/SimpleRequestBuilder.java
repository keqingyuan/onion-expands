package cc.kebei.expands.request;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import cc.kebei.expands.request.email.EmailRequest;
import cc.kebei.expands.request.ftp.FtpRequest;
import cc.kebei.expands.request.ftp.simple.SimpleFtpRequest;
import cc.kebei.expands.request.http.HttpRequest;
import cc.kebei.expands.request.http.HttpRequestGroup;
import cc.kebei.expands.request.http.simple.SimpleHttpRequest;
import cc.kebei.expands.request.http.simple.SimpleHttpsRequest;
import cc.kebei.expands.request.http.simple.SimpleRequestGroup;
import cc.kebei.expands.request.webservice.SimpleWebServiceRequestBuilder;
import cc.kebei.expands.request.webservice.WebServiceRequestBuilder;
import cc.kebei.expands.request.websocket.WebSocketRequest;

import java.io.IOException;

public class SimpleRequestBuilder implements RequestBuilder {

    private PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

    @Override
    public HttpRequestGroup http() {
        return new SimpleRequestGroup();
    }

    @Override
    public HttpRequest http(String url) {
        if (!url.startsWith("http")) url = "http://" + url;
        SimpleHttpRequest request = new SimpleHttpRequest(url);
        request.setPool(pool);
        return request;
    }

    public HttpRequest https(String url) {
        if (!url.startsWith("http")) url = "https://" + url;
        try {
            return new SimpleHttpsRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FtpRequest ftp(String host, int port, String username, String password) throws IOException {
        return new SimpleFtpRequest(host, port, username, password);
    }

    @Override
    public FtpRequest ftp(String host, int port) throws IOException {
        return ftp(host, port, null, null);
    }

    @Override
    public FtpRequest ftp(String host) throws IOException {
        return ftp(host, 22);
    }

    @Override
    public WebServiceRequestBuilder webService() throws Exception {
        return new SimpleWebServiceRequestBuilder();
    }


    @Override
    public EmailRequest email() {
        // TODO: 16-9-29
        throw new UnsupportedOperationException();
    }

    @Override
    public WebSocketRequest webSocket(String url) {
        // TODO: 16-9-29
        throw new UnsupportedOperationException();
    }

}
