package com.github.biba.flashlang.mocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class FileStreamProvider {

    public InputStream get(final String pPath) throws IOException {

        final File initialFile = new File(pPath);
        assertNotNull(initialFile);

        return new FileInputStream(initialFile);
    }
}
