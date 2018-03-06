package is.stma.judgebean.util;

import org.xbill.DNS.*;

import java.net.UnknownHostException;

import static org.xbill.DNS.ReverseMap.fromAddress;
import static org.xbill.DNS.Type.*;

public class DNSUtility {

    /**
     * Runs a forward lookup for A records on the given query at the specified
     * host address and port.
     *
     * Loosely based on code from https://www.programcreek.com/java-api-examples/?code=amirkibbar/plum/plum-master/src/main/java/ajk/consul4spring/DnsResolver.java.
     *
     * @param hostAddress address or hostname of DNS server to query
     * @param hostPort server port of the DNS server to query
     * @param query query to send to the DNS server
     * @param tcp whether to use TCP for the query or not
     * @param type the type of record to ask for
     * @param recursive whether to request recursion or not (TODO: Unimplemented)
     * @return top (first) response from the server, or ERROR
     */
    public static String forwardLookup(String hostAddress, int hostPort, String query,
                                       boolean tcp, int type, boolean recursive) {
        try {
            // Set up the resolution options
            SimpleResolver resolver = new SimpleResolver(hostAddress);
            resolver.setPort(hostPort);
            resolver.setTCP(tcp);
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
            return records[0].toString();

        } catch (UnknownHostException | TextParseException e) {
            return "ERROR: resolution failed";
        }

    }
}
