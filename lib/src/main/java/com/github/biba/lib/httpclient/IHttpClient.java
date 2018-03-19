package com.github.biba.lib.httpclient;

import com.github.biba.lib.contracts.IRequestHandler;
import com.github.biba.lib.contracts.IResponseConverter;

import java.io.IOException;
import java.io.InputStream;

public interface IHttpClient extends IRequestHandler<IHttpRequest, IHttpResponse> {

    <Response> Response getResponse(IHttpRequest pRequest, IResponseConverter<Response, InputStream> pResponseConverter) throws IOException;

    final class Impl {

        public static IHttpClient getClient() {
            return new HttpClient();
        }
    }
}
