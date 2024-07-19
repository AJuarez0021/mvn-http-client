package com.work.client.http;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The Class HttpClient.
 */
public abstract class HttpClient {

    /**
     * The Constant POST.
     */
    protected static final String POST = "POST";

    /**
     * The Constant GET.
     */
    protected static final String GET = "GET";

    /**
     * Send post.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public HttpClientResponseDto sendPost(HttpClientRequestDto request) throws IOException {
        HttpURLConnection httpClient = getHttp(POST, request.getContentType(), request);
        return send(httpClient, request);
    }

    /**
     * Send get.
     *
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public HttpClientResponseDto sendGet(HttpClientRequestDto request) throws IOException {
        HttpURLConnection httpClient = getHttp(GET, request.getContentType(), request);
        return send(httpClient, request);
    }

    /**
     * Send.
     *
     * @param httpClient the http client
     * @param request the request
     * @return the http client response dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected abstract HttpClientResponseDto send(HttpURLConnection httpClient, HttpClientRequestDto request)
            throws IOException;

    /**
     * Read stream.
     *
     * @param is the is
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected String readStream(final InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /**
     * Write stream.
     *
     * @param out the out
     * @param json the json
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void writeStream(final OutputStream out, String json) throws IOException {
        try (OutputStream wr = out) {
            wr.write(json.getBytes(StandardCharsets.UTF_8));
            wr.flush();
        }
    }

    /**
     * Checks if is ok.
     *
     * @param responseCode the response code
     * @return true, if is ok
     */
    protected boolean isOk(int responseCode) {
        return responseCode >= HttpURLConnection.HTTP_OK && responseCode <= 299;
    }

    /**
     * Gets the http.
     *
     * @param requestMethod the request method
     * @param request the request
     * @return the http
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private HttpURLConnection getHttp(String requestMethod, String contentType, HttpClientRequestDto request) throws IOException {
        String url = request.getUrl();
        int timeout = request.getTimeout();
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod(requestMethod);
        httpClient.setRequestProperty("Content-Type", contentType);
        httpClient.setRequestProperty("Content-Length",
                GET.equalsIgnoreCase(requestMethod) ? "0" : String.valueOf(request.getJson().length()));

        httpClient.setDoOutput(true);
        httpClient.setDoInput(true);
        httpClient.setUseCaches(false);

        if (timeout >= 0) {
            httpClient.setReadTimeout(timeout);
            httpClient.setConnectTimeout(timeout);
        }

        return httpClient;
    }
}
