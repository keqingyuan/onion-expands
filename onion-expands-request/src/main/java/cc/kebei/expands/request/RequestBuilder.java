package cc.kebei.expands.request;

import cc.kebei.expands.request.email.EmailRequest;
import cc.kebei.expands.request.ftp.FtpRequest;
import cc.kebei.expands.request.http.HttpRequest;
import cc.kebei.expands.request.http.HttpRequestGroup;
import cc.kebei.expands.request.webservice.WebServiceRequestBuilder;
import cc.kebei.expands.request.websocket.WebSocketRequest;

import java.io.IOException;

public interface RequestBuilder {
    HttpRequestGroup http();

    HttpRequest http(String url);

    HttpRequest https(String url);

    FtpRequest ftp(String host, int port, String username, String password) throws IOException;

    FtpRequest ftp(String host, int port) throws IOException;

    FtpRequest ftp(String host) throws IOException;

    WebServiceRequestBuilder webService() throws Exception;

    EmailRequest email();

    WebSocketRequest webSocket(String url);

}
