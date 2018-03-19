package com.github.biba.flashlang.mocks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringStreamProvider {

    public InputStream get(final String pSource) {
        return new ByteArrayInputStream(pSource.getBytes());
    }
}
