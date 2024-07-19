package com.work.client.dto;

/**
 * The Class HttpClientResponseDto.
 */
public class HttpClientResponseDto {

    /**
     * The status code.
     */
    private int statusCode;

    /**
     * The response.
     */
    private String response;

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     *
     * @param statusCode the new status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets the response.
     *
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * Sets the response.
     *
     * @param response the new response
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HttpClientResponseDto{" + "statusCode=" + statusCode + ", response=" + response + '}';
    }
}
