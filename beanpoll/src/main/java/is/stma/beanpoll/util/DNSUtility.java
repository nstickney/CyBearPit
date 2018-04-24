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

import org.xbill.DNS.*;

import java.net.UnknownHostException;

import static org.xbill.DNS.ReverseMap.fromAddress;
import static org.xbill.DNS.Type.*;

public class DNSUtility {

    private static final String DEFAULT_DNS_ADDRESS = "9.9.9.9";
    private static final int DEFAULT_DNS_PORT = 53;
    private static final boolean DEFAULT_DNS_TCP = false;
    private static final int DEFAULT_DNS_TYPE = A;
    private static final boolean DEFAULT_DNS_RECURSIVE = false;
    private static final int DEFAULT_DNS_TIMEOUT = 2;
    private static final int MAX_DNS_TIMEOUT = 5;
    private static final boolean DEFAULT_DNS_FIRST_ONLY = true;

    /**
     * Runs a forward lookup for records on the given query at the specified
     * host address and port. Note that only DNS records of types A, AAAA,
     * CNAME, DNAME, MX, NS, PTR, and TXT are properly filtered.
     *
     * @param hostAddress address or hostname of DNS server to query
     * @param hostPort    server port of the DNS server to query
     * @param query       query to send to the DNS server
     * @param tcp         whether to use TCP for the query or not
     * @param type        the type of record to ask for
     * @param recursive   whether to request recursion (TODO: Unimplemented)
     * @param timeout     length of the timeout in seconds
     * @param firstOnly   whether or not to return only the first result
     * @return top (first) response from the server, or ERROR
     */
    public static String lookup(String hostAddress, int hostPort, String query, int type,
                                boolean tcp, boolean recursive, int timeout, boolean firstOnly) {
        try {

            // Set up the resolution options
            SimpleResolver resolver;
            if (null == hostAddress) {
                resolver = new SimpleResolver(DEFAULT_DNS_ADDRESS);
            } else {
                resolver = new SimpleResolver(hostAddress);
            }

            if (0 == hostPort) {
                resolver.setPort(DEFAULT_DNS_PORT);
            } else if (0 < hostPort && hostPort < 65535) {
                resolver.setPort(hostPort);
            } else {
                return "ERROR: port " + hostPort + " does not exist";
            }

            resolver.setTCP(tcp);

            if (0 == timeout) {
                resolver.setTimeout(DEFAULT_DNS_TIMEOUT);
            } else if (0 < timeout && timeout < MAX_DNS_TIMEOUT) {
                resolver.setTimeout(timeout);
            } else {
                resolver.setTimeout(MAX_DNS_TIMEOUT);
            }

            // Do the lookup
            Lookup lookup;
            if (PTR == type) {
                lookup = new Lookup(fromAddress(query), PTR);
            } else {
                lookup = new Lookup(query, type);
            }
            lookup.setResolver(resolver);
            lookup.setCache(null);

            // Execute the query and return the result
            Record[] records = lookup.run();
            if (null == records) {
                return "ERROR: " + lookup.getErrorString();
            } else if (records.length < 1) {
                return "ERROR: no results returned";
            }

            // Filter supported types
            StringBuilder result = new StringBuilder();
            for (Record r : records) {
                switch (type) {
                    case A:
                        result.append(((ARecord) r).getAddress().toString());
                        break;
                    case AAAA:
                        result.append(((AAAARecord) r).getAddress().toString());
                        break;
                    case CNAME:
                        result.append(((CNAMERecord) r).getAlias().toString());
                        break;
                    case DNAME:
                        result.append(((DNAMERecord) r).getAlias().toString());
                        break;
                    case MX:
                        result.append(r.getAdditionalName().toString());
                        break;
                    case NS:
                        result.append(r.getAdditionalName().toString());
                        break;
                    case PTR:
                        result.append(((PTRRecord) r).getTarget().toString());
                        break;
                    case SOA:
                        result.append(((SOARecord) r).getHost().toString());
                        break;
                    case TXT:
                        result.append(((TXTRecord) r).getStrings().get(0));
                        break;
                    default:
                        result.append(r.toString());
                        break;
                }
                if (firstOnly && !"".equals(result.toString())) {
                    return result.toString();
                }
            }
            return result.toString();

        } catch (UnknownHostException | TextParseException e) {
            return "ERROR: resolution failed";
        }
    }

    public static String lookup(String query) {
        return lookup(DEFAULT_DNS_ADDRESS, DEFAULT_DNS_PORT, query, DEFAULT_DNS_TYPE, DEFAULT_DNS_TCP, DEFAULT_DNS_RECURSIVE, DEFAULT_DNS_TIMEOUT, DEFAULT_DNS_FIRST_ONLY);
    }

    public static String lookup(String hostAddress, String query) {
        return lookup(hostAddress, DEFAULT_DNS_PORT, query, DEFAULT_DNS_TYPE, DEFAULT_DNS_TCP, DEFAULT_DNS_RECURSIVE, DEFAULT_DNS_TIMEOUT, DEFAULT_DNS_FIRST_ONLY);
    }

    public static String lookup(String query, int type) {
        return lookup(DEFAULT_DNS_ADDRESS, DEFAULT_DNS_PORT, query, type, DEFAULT_DNS_TCP, DEFAULT_DNS_RECURSIVE, DEFAULT_DNS_TIMEOUT, DEFAULT_DNS_FIRST_ONLY);
    }

    public static String lookup(String hostAddress, String query, int type) {
        return lookup(hostAddress, DEFAULT_DNS_PORT, query, type, DEFAULT_DNS_TCP, DEFAULT_DNS_RECURSIVE, DEFAULT_DNS_TIMEOUT, DEFAULT_DNS_FIRST_ONLY);
    }
}
