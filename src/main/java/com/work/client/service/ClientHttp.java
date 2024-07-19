package com.work.client.service;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import java.io.IOException;

/**
 *
 * @author linux
 */
public interface ClientHttp {

    /**
     * Checks if is ok.
     *
     * @param statusCode the status code
     * @return true, if is ok
     */
    boolean isOk(int statusCode);

    /**
     * Send post.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    HttpClientResponseDto sendPost(HttpClientRequestDto request) throws IOException;

    /**
     * Send get.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    HttpClientResponseDto sendGet(HttpClientRequestDto request) throws IOException;
}
