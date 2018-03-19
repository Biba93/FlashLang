package com.github.biba.lib.httpclient;

import com.github.biba.lib.contracts.IRequest;

import java.net.MalformedURLException;
import java.net.URL;

interface IHttpRequest extends IRequest {

    URL getUrl() throws MalformedURLException;

    HttpMethod getMethod();

    Headers getHeaders();

}
