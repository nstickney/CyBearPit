package is.stma.judgebean.util;

import org.xbill.DNS.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.xbill.DNS.ReverseMap.fromAddress;
import static org.xbill.DNS.Type.*;

public class DNSUtility {

    /**
     * Runs a forward lookup for A records on the given query at the specified
     * host address and port. Note that only DNS records of tyles A, AAAA,
     * CNAME, DNAME, MX, NS, PTR, and TXT are properly filtered, and only the
     * first record of any given type is returned.
     *
     * @param hostAddress address or hostname of DNS server to query
     * @param hostPort server port of the DNS server to query
     * @param query query to send to the DNS server
     * @param tcp whether to use TCP for the query or not
     * @param type the type of record to ask for
     * @param recursive whether to request recursion (TODO: Unimplemented)
     * @param timeout length of the timeout in seconds
     * @return top (first) response from the server, or ERROR
     */
    public static String forwardLookup(String hostAddress, int hostPort, String query,
                                       boolean tcp, int type, boolean recursive,
                                       int timeout) {
        try {
            // Set up the resolution options
            SimpleResolver resolver = new SimpleResolver(hostAddress);
            resolver.setPort(hostPort);
            resolver.setTCP(tcp);
            resolver.setTimeout(timeout);
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
            switch (type) {
                case A: return ((ARecord) records[0]).getAddress().toString();
                case AAAA: return ((AAAARecord) records[0]).getAddress().toString();
                case CNAME: return ((CNAMERecord) records[0]).getAlias().toString();
                case DNAME: return ((DNAMERecord) records[0]).getAlias().toString();
                case MX: return ((MXRecord) records[0]).getAdditionalName().toString();
                case NS: return ((NSRecord) records[0]).getAdditionalName().toString();
                case PTR: return ((PTRRecord) records[0]).getTarget().toString();
                case SOA: return ((SOARecord) records[0]).getHost().toString();
                case TXT: return (String)((TXTRecord) records[0]).getStrings().get(0);
            }

            return records[0].toString();

        } catch (UnknownHostException | TextParseException e) {
            return "ERROR: resolution failed";
        }
    }
}
