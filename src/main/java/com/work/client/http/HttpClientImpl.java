package com.work.client.http;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class HttpClientImpl.
 */
public class HttpClientImpl extends HttpClient {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = Logger.getLogger("HttpClientImpl");

    /**
     * Send.
     *
     * @param httpClient the http client
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public HttpClientResponseDto send(HttpURLConnection httpClient, HttpClientRequestDto request) throws IOException {
        if (request.getBasicAuthorization() != null && !request.getBasicAuthorization().isEmpty()) {
            httpClient.setRequestProperty("Authorization", String.format("Basic %s", request.getBasicAuthorization()));
        }
        HttpClientResponseDto response = new HttpClientResponseDto();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "URL: {0}", request.getUrl());
            LOG.log(Level.FINE, "Request: {0}", request.getJson());
        }

        if (!GET.equalsIgnoreCase(httpClient.getRequestMethod())) {
            writeStream(httpClient.getOutputStream(), request.getJson());
        }

        int responseCode = httpClient.getResponseCode();
        response.setStatusCode(responseCode);

        String stream = readStream(!isOk(responseCode) ? httpClient.getErrorStream() : httpClient.getInputStream());
        response.setResponse(stream);

        httpClient.disconnect();
        return response;
    }
}
