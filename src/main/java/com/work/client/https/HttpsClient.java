package com.work.client.https;

import com.work.client.dto.HttpClientRequestDto;
import com.work.client.dto.HttpClientResponseDto;
import com.work.client.util.Configuration;
import com.work.client.util.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;

/**
 * The Class HttpsClient.
 */
public abstract class HttpsClient {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = Logger.getLogger("HttpsClient");

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
        HttpsURLConnection httpClient = getHttps(POST, request.getContentType(), request);
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
        HttpsURLConnection httpClient = getHttps(GET, request.getContentType(), request);
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
    protected abstract HttpClientResponseDto send(HttpsURLConnection httpClient, HttpClientRequestDto request)
            throws IOException;

    /**
     * The Class HttpsHostnameVerifier.
     */
    private static class HttpsHostnameVerifier implements HostnameVerifier {

        /**
         * Verify.
         *
         * @param string the string
         * @param ssls the ssls
         * @return true, if successful
         */
        @Override
        public boolean verify(String string, SSLSession ssls) {
            return true;
        }
    }

    /**
     * Gets the https.
     *
     * @param requestMethod the request method
     * @param contentType the content type
     * @param request the request
     * @return the https
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private HttpsURLConnection getHttps(String requestMethod, String contentType, HttpClientRequestDto request)
            throws IOException {
        String url = request.getUrl();
        int timeout = request.getTimeout();

        HttpsURLConnection httpsClient = (HttpsURLConnection) new URL(url).openConnection();
        httpsClient.setDoOutput(true);
        httpsClient.setDoInput(true);
        httpsClient.setHostnameVerifier(new HttpsHostnameVerifier());
        SSLContext ssl = getSSL(false);
        if (ssl != null) {
            httpsClient.setSSLSocketFactory(ssl.getSocketFactory());
        }
        httpsClient.setUseCaches(false);
        if (timeout >= 0) {
            httpsClient.setReadTimeout(timeout);
            httpsClient.setConnectTimeout(timeout);
        }
        httpsClient.setRequestMethod(requestMethod);
        httpsClient.setRequestProperty("Content-Type", contentType);
        httpsClient.setRequestProperty("Content-Length",
                GET.equalsIgnoreCase(requestMethod) ? "0" : String.valueOf(request.getJson().length()));
        return httpsClient;
    }

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
     * Gets the ssl.
     *
     * @param verifyingSsl the verifying ssl
     * @return the ssl
     */
    private SSLContext getSSL(boolean verifyingSsl) {

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            if (!verifyingSsl) {
                sc.init(null, trustCertificate(), new java.security.SecureRandom());
            } else {
                sc.init(null, loadJks(), new java.security.SecureRandom());
            }
            return sc;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "SSL: {}", ex);
            return null;
        }
    }

    /**
     * Trust certificate.
     *
     * @return the trust manager[]
     */
    private TrustManager[] trustCertificate() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509ExtendedTrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) {

            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};
        return trustAllCerts;
    }

    /**
     * Load jks.
     *
     * @param synCtx the syn ctx
     * @return the trust manager[]
     * @throws Exception the exception
     */
    private TrustManager[] loadJks() throws Exception {
        String alias = Configuration.getInstance().getString("jks_alias");
        String path = Configuration.getInstance().getString("jks_path");
        String pwd = Configuration.getInstance().getString("jks_pwd");
        String fileName = Configuration.getInstance().getString("jks_fileName");
        path = Utility.getPath(path) + fileName;
        KeyStore store = KeyStore.getInstance("JKS");
        LOG.log(Level.FINEST, "Alias Name: {0}", alias);
        LOG.log(Level.INFO, "Path: {0}", path);
        URI uri = HttpsClient.class.getClassLoader().getResource(path).toURI();
        store.load(new FileInputStream(new File(uri)), pwd.toCharArray());
        X509Certificate ca = (X509Certificate) store.getCertificate(alias);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry(Integer.toString(1), ca);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        return tmf.getTrustManagers();
    }
}
