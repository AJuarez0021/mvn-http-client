package com.work.client.service;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import com.work.client.http.HttpClient;
import com.work.client.http.HttpClientImpl;
import com.work.client.https.HttpsClient;
import com.work.client.https.HttpsClientImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Class ClientHttpImpl.
 */
public final class ClientHttpImpl implements ClientHttp {

    /**
     * The instance.
     */
    private static ClientHttpImpl instance = null;

    /**
     * Instantiates a new client http impl.
     */
    private ClientHttpImpl() {

    }

    /**
     * Gets the single instance of ClientHttpImpl.
     *
     * @return single instance of ClientHttpImpl
     */
    public static ClientHttp getInstance() {
        if (instance == null) {
            synchronized (ClientHttpImpl.class) {
                ClientHttp inst = instance;
                if (inst == null) {
                    instance = new ClientHttpImpl();
                }
            }
        }
        return instance;
    }

    /**
     * Checks if is http.
     *
     * @param request the request
     * @return true, if is http
     * @throws MalformedURLException the malformed URL exception
     */
    private boolean isHttp(HttpClientRequestDto request) throws MalformedURLException {
        URL url = new URL(request.getUrl());
        return "http".equalsIgnoreCase(url.getProtocol());
    }

    /**
     * Send post.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public HttpClientResponseDto sendPost(HttpClientRequestDto request) throws IOException {
        if (isHttp(request)) {
            HttpClient http = new HttpClientImpl();
            return http.sendPost(request);
        }

        HttpsClient https = new HttpsClientImpl();
        return https.sendPost(request);
    }

    /**
     * Send get.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public HttpClientResponseDto sendGet(HttpClientRequestDto request) throws IOException {
        if (isHttp(request)) {
            HttpClient http = new HttpClientImpl();
            return http.sendGet(request);
        }

        HttpsClient https = new HttpsClientImpl();
        return https.sendGet(request);
    }

    /**
     * Checks if is ok.
     *
     * @param statusCode the status code
     * @return true, if is ok
     */
    @Override
    public boolean isOk(int statusCode) {
        return statusCode >= 200 && statusCode <= 299;
    }
}
