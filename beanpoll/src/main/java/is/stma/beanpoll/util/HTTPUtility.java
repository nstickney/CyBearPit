/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class HTTPUtility {

    /**
     * Make an HTTP request for the query given, on the given port, within the given timeout
     *
     * @param query   http or https query URI
     * @param port    port number of the listening server
     * @param timeout number of seconds to wait before timing out
     * @return the response to the given request
     */
    public static String get(String query, int port, int timeout) {
        try {

            // Time out properly
            RequestConfig httpConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();

            // Create the client (it should accept any hostname on certificates)
            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(httpConfig)
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(new TrustSelfSignedStrategy()).build())
                    .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();

            // Create the request
            query = setDefaultHTTPProtocol(query);
            String scheme = new URI(query).getScheme();
            String path = new URI(query).getPath();
            String domain = new URI(query).getHost();
            domain = domain.startsWith("www.") ? domain.substring(4) : domain;
            HttpHost httpHost = new HttpHost(domain, port, scheme);
            HttpGet httpGet = new HttpGet(path);

            // Run the request
            try (CloseableHttpResponse response = httpClient.execute(httpHost, httpGet)) {
                HttpEntity entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity);
                } else {
                    return "ERROR: No Response";
                }
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * If the protocol of a query address is not given, assume it is http.
     *
     * @param address the address to check for a protocol
     * @return the address, with a correct protocol string
     */
    public static String setDefaultHTTPProtocol(String address) {
        if (!address.startsWith("https://") && !address.startsWith("http://")) {
            address = "http://" + address; // Assume HTTP as default
        }
        return address;
    }
}
