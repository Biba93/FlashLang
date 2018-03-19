package com.github.biba.lib.httpclient;

import com.github.biba.lib.contracts.IResponse;

public interface IHttpResponse extends IResponse<String> {

    IHttpRequest getRequest();

}
