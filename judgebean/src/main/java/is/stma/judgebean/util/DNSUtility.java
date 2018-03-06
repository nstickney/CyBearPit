package is.stma.judgebean.util;

import org.xbill.DNS.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.Stream;

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
     * @return top (first) response from the server, or ERROR
     */
    public static String forwardLookup(String hostAddress, int hostPort, String query) {
        try {
            SimpleResolver resolver = new SimpleResolver(hostAddress);
            resolver.setPort(hostPort);
            Lookup lookup = new Lookup(query, A);
            Record[] records = lookup.run();
            if (null == records) {
                return "ERROR: No results returned";
            }
            List<String> addresses = Stream.of(records)
                    .filter(it -> it instanceof ARecord)
                    .map(it -> ((ARecord) it).getAddress().getHostAddress())
                    .collect(Collectors.toList());
            return addresses.get(0);
        } catch (UnknownHostException | TextParseException e) {
            return "ERROR: Resolution failed";
        }

    }
}
