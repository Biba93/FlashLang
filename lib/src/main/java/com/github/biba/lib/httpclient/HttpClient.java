package com.github.biba.lib.httpclient;

import android.support.annotation.NonNull;

import com.github.biba.lib.Constants;
import com.github.biba.lib.contracts.IResponseConverter;
import com.github.biba.lib.utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

class HttpClient implements IHttpClient {

    @Override
    public IHttpResponse handle(final IHttpRequest pRequest) throws Exception {
        final HttpResponse response = new HttpResponse();
        response.setRequest(pRequest);

        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            connection = buildConnection(pRequest);
            final boolean isSuccessful = isSuccessful(connection);

            stream = getInputStream(connection);

            final String readStream = readStream(stream);
            if (!isSuccessful) {
                response.setError(new Exception(readStream));
            } else {
                response.setResponse(readStream);
            }
        } finally {
            IOUtils.close(stream);
            IOUtils.disconnect(connection);
        }

        return response;
    }

    @Override
    public <Response> Response getResponse(final IHttpRequest pRequest, final IResponseConverter<Response, InputStream> pResponseConverter) throws IOException {

        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            connection = buildConnection(pRequest);
            if (!isSuccessful(connection)) {
                return null;
            }
            stream = getInputStream(connection);
            return pResponseConverter.convert(stream);
        } finally {
            IOUtils.close(stream);
            IOUtils.disconnect(connection);
        }
    }

    private InputStream getInputStream(final HttpURLConnection pConnection) throws IOException {
        final InputStream stream;
        if (isSuccessful(pConnection)) {
            stream = pConnection.getInputStream();
        } else {
            stream = pConnection.getErrorStream();
        }
        return stream;
    }

    @NonNull
    private HttpURLConnection buildConnection(final IHttpRequest pRequest) throws IOException {
        final HttpURLConnection connection;
        final URL requestUrl = pRequest.getUrl();

        connection = (HttpURLConnection) requestUrl.openConnection();

        connection.setRequestMethod(pRequest.getMethod().name());

        final Headers headers = pRequest.getHeaders();
        if (headers != null) {
            final Map<String, String> map = headers.getMap();
            for (final String key : map.keySet()) {
                connection.addRequestProperty(key, map.get(key));
            }
        }
        return connection;
    }

    private boolean isSuccessful(final HttpURLConnection pConnection) throws IOException {
        return pConnection.getResponseCode() >= Constants.HttpClient.MIN_SUCCESS_CODE && pConnection.getResponseCode() < Constants.HttpClient.MAX_SUCCESS_CODE;
    }

    @NonNull
    private String readStream(final InputStream pStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(pStream));
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

}
