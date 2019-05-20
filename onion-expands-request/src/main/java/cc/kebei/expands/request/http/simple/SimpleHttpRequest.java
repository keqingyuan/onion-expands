package cc.kebei.expands.request.http.simple;

import org.apache.http.HttpResponse;
import cc.kebei.expands.request.http.Response;

import java.io.IOException;

/**
 * Created by Kebei on 16-6-28.
 */
public class SimpleHttpRequest extends AbstractHttpRequest {
    public SimpleHttpRequest(String url) {
        super(url);
    }

    @Override
    protected Response getResultValue(HttpResponse res) throws IOException {
        return new SimpleResponse(res);
    }
}
