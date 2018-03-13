package is.stma.judgebean.beanpoll.model;

public abstract class ComparableByContest extends AbstractEntity implements Comparable {

    abstract Contest getContest();

    public String getDisplayName() {
        if (null != getContest()) {
            return getContest().getName() + ": " + getName();
        }
        return getName();
    }

    public int compare(ComparableByContest e1, ComparableByContest e2) {

        // If both are assigned to Contests, decide based on display name
        if (null != e1.getContest() && null != e2.getContest()) {
            return e1.getDisplayName().compareTo(e2.getDisplayName());
        }

        // If neither is assigned to a Contest, decide based on Entity name
        if (null == e1.getContest() && null == e2.getContest()) {
            return e1.getName().compareTo(e2.getName());
        }

        // Return whichever is not assigned to a Contest
        if (null == e1.getContest()) {
            return -1;
        }
        return 1;
    }

}
