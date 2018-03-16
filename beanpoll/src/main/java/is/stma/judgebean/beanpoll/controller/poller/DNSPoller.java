package is.stma.judgebean.beanpoll.controller.poller;

import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;

public class DNSPoller extends AbstractPoller {

    DNSPoller(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Poll poll() {
        return null;
    }
}
