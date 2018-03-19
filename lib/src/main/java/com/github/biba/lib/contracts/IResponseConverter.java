package com.github.biba.lib.contracts;

import java.io.IOException;

public interface IResponseConverter<Response, Source> {

    Response convert(Source pSource) throws IOException;
}
