package com.github.biba.lib.httpclient;

import java.net.MalformedURLException;
import java.net.URL;

public final class HttpRequest implements IHttpRequest {

    private final String mUrl;
    private final HttpMethod mMethod;
    private final Headers mHeaders;

    private HttpRequest(final Builder pBuilder) {
        mUrl = pBuilder.mUrl;
        mMethod = pBuilder.mMethod;
        mHeaders = pBuilder.mHeaders;
    }

    @Override
    public URL getUrl() throws MalformedURLException {
        return new URL(mUrl);
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public Headers getHeaders() {
        return mHeaders;
    }

    public static class Builder {

        private String mUrl;
        private HttpMethod mMethod;
        private Headers mHeaders;

        public Builder setUrl(final String pUrl) {
            mUrl = pUrl;
            return this;
        }

        public Builder setMethod(final HttpMethod pMethod) {
            mMethod = pMethod;
            return this;
        }

        public Builder setHeaders(final Headers pHeaders) {
            mHeaders = pHeaders;
            return this;
        }

        public IHttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
