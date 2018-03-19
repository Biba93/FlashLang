package com.github.biba.lib.httpclient;

public class HttpResponse implements IHttpResponse {

    private IHttpRequest mRequest;
    private String mResponseBody;
    private Throwable mError;

    HttpResponse() {
    }

    void setRequest(final IHttpRequest pRequest) {
        mRequest = pRequest;
    }

    void setResponse(final String pResponseBody) {
        mResponseBody = pResponseBody;
    }

    void setError(final Throwable pError) {
        mError = pError;
    }

    @Override
    public IHttpRequest getRequest() {
        return mRequest;
    }

    @Override
    public String getResult() {
        return mResponseBody;
    }

    @Override
    public Throwable getError() {
        return mError;
    }
}
