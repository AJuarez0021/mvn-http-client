package com.work.client.dto;

/**
 * The Class HttpClientRequestDto.
 */
public class HttpClientRequestDto {

    /**
     * The url.
     */
    private String url;

    /**
     * The json.
     */
    private String json;

    /**
     * The timeout.
     */
    private int timeout;

    /**
     * The content type.
     */
    private String contentType;

    /**
     * The Constant JSON_CONTENT_TYPE.
     */
    public static final String JSON_CONTENT_TYPE = "application/json";

    /**
     * The Constant URL_ENCODED_CONTENT_TYPE.
     */
    public static final String URL_ENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded";

    /**
     * The Constant TIMEOUT.
     */
    private static final int TIMEOUT = -1;

    /**
     * The basic authorization.
     */
    private String basicAuthorization;

    /**
     * Instantiates a new http client request dto.
     */
    public HttpClientRequestDto() {
        this("", "", TIMEOUT, JSON_CONTENT_TYPE);
    }

    /**
     * Instantiates a new http client request dto.
     *
     * @param url the url
     */
    public HttpClientRequestDto(String url) {
        this(url, "", TIMEOUT, JSON_CONTENT_TYPE);
    }

    /**
     * Instantiates a new http client request dto.
     *
     * @param url the url
     * @param contentType the content type
     */
    public HttpClientRequestDto(String url, String contentType) {
        this(url, "", TIMEOUT, contentType);
    }

    /**
     * Instantiates a new http client request dto.
     *
     * @param url the url
     * @param json the json
     * @param contentType the content type
     */
    public HttpClientRequestDto(String url, String json, String contentType) {
        this(url, json, TIMEOUT, contentType);
    }

    /**
     * Instantiates a new http client request dto.
     *
     * @param url the url
     * @param json the json
     * @param timeout the timeout
     * @param contentType the content type
     */
    public HttpClientRequestDto(String url, String json, int timeout, String contentType) {
        super();
        this.url = url;
        this.json = json;
        this.timeout = timeout;
        this.contentType = contentType;
        this.basicAuthorization = "";
    }

    /**
     * Gets the content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the new content type
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     *
     * @param url the new url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the json.
     *
     * @return the json
     */
    public String getJson() {
        return json;
    }

    /**
     * Sets the json.
     *
     * @param json the new json
     */
    public void setJson(String json) {
        this.json = json;
    }

    /**
     * Gets the timeout.
     *
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout.
     *
     * @param timeout the new timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Gets the basic authorization.
     *
     * @return the basic authorization
     */
    public String getBasicAuthorization() {
        return basicAuthorization;
    }

    /**
     * Sets the basic authorization.
     *
     * @param basicAuthorization the new basic authorization
     */
    public void setBasicAuthorization(String basicAuthorization) {
        this.basicAuthorization = basicAuthorization;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HttpClientRequestDto [url=" + url + ", json=" + json + ", timeout=" + timeout + ", contentType="
                + contentType + "]";
    }
}
