package com.work.client.https;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 * The Class HttpsClientImpl.
 */
public class HttpsClientImpl extends HttpsClient {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = Logger.getLogger("HttpsClientImpl");

    /**
     * Send.
     *
     * @param httpsClient
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public HttpClientResponseDto send(HttpsURLConnection httpsClient, HttpClientRequestDto request) throws IOException {
        if (request.getBasicAuthorization() != null && !request.getBasicAuthorization().isEmpty()) {
            httpsClient.setRequestProperty("Authorization", String.format("Basic %s", request.getBasicAuthorization()));
        }
        HttpClientResponseDto response = new HttpClientResponseDto();

        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "URL: {0}", request.getUrl());
            LOG.log(Level.FINE, "Request: {0}", request.getJson());
        }

        if (!GET.equalsIgnoreCase(httpsClient.getRequestMethod())) {
            writeStream(httpsClient.getOutputStream(), request.getJson());
        }

        int responseCode = httpsClient.getResponseCode();
        response.setStatusCode(responseCode);

        String stream = readStream(!isOk(responseCode) ? httpsClient.getErrorStream() : httpsClient.getInputStream());
        response.setResponse(stream);

        httpsClient.disconnect();
        return response;
    }

}
