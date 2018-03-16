package is.stma.judgebean.beanpoll.controller.poller;

import is.stma.judgebean.beanpoll.model.Resource;

public class PollerFactory {

    public static AbstractPoller getPoller(Resource resource) {
        switch (resource.getType()) {
            case Resource.DNS:
                return new DNSPoller(resource);
            default:
                return new HTTPPoller(resource);
        }
    }

}
