package com.work.client.util;

import java.io.File;

/**
 * The Class Utility.
 */
public final class Utility {

    /**
     * Instantiates a new utility.
     */
    private Utility() {

    }

    /**
     * Gets the path.
     *
     * @param url the url
     * @return the path
     */
    public static String getPath(String url) {
        if (url == null) {
            return "";
        }

        if (url.endsWith(File.separator)) {
            return url;
        }

        return url + File.separator;
    }
}
