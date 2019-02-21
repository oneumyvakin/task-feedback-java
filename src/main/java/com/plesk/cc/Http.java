package com.plesk.cc;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.InputStream;

public class Http {
    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public InputStream Get(GenericUrl url) throws Exception {
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest request) {

                    }
                });
        InputStream inputStream;

        try {
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse response = request.execute();
            inputStream = response.getContent();
        } catch (Exception e) {
            throw new Exception("failed to get '" + url.toString() + "' with error: '" + e.getMessage() + "'");
        }

        return inputStream;
    }

}
