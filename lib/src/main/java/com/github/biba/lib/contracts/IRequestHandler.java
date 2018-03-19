package com.github.biba.lib.contracts;

public interface IRequestHandler<Request extends IRequest, Response> {

    Response handle(Request pRequest) throws Exception;

}
