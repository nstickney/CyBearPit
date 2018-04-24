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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;

public class HTTPUtility {

    public static String get(String query, int port, int timeout) {
        try {

            // Time out properly
            RequestConfig httpConfig = RequestConfig.custom()
                    .setConnectTimeout(timeout * 1000)
                    .setConnectionRequestTimeout(timeout * 1000)
                    .setSocketTimeout(timeout * 1000).build();

            // Allow self-signed HTTPS certificates
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(new SSLContextBuilder().loadTrustMaterial(new TrustSelfSignedStrategy()).build());

            // Create the client
            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(httpConfig)
                    .setSSLSocketFactory(sslsf).build();

            // Create the request
            if (!query.startsWith("https://") && !query.startsWith("http://")) {
                query = "http://" + query; // Assume HTTP as default
            }
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
}
