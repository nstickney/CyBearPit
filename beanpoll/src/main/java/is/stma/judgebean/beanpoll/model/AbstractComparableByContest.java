/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.model;

public abstract class AbstractComparableByContest extends AbstractEntity implements Comparable<AbstractComparableByContest> {

    @Override
    public boolean equals(Object o) {
        return null != o &&
                o.getClass().equals(getClass()) &&
                equalByUUID((AbstractComparableByContest) o);
    }

    @Override
    public int hashCode() {
        String hashCodeString = getId() + getDisplayName();
        return hashCodeString.hashCode();
    }

    abstract Contest getContest();

    public String getDisplayName() {
        if (null != getContest()) {
            return getContest().getName() + ": " + getName();
        }
        return getName();
    }

    public String getLook() {
        if (null == getContest()) {
            return "warning";
        } else if (getContest().isRunning()) {
            return "success";
        }
        return "default";
    }

    int compare(AbstractComparableByContest e1, AbstractComparableByContest e2) {

        // If both are assigned to contests, decide based on which contest is running
        if (null != e1.getContest() && null != e2.getContest()) {

            // If e1's contest is running (but not e2's), it is lower, and vice versa
            if (e1.getContest().isRunning() && !e2.getContest().isRunning()) {
                return -1;
            } else if (!e1.getContest().isRunning() && e2.getContest().isRunning()) {
                return 1;
            }
        }

        // If only one is not assigned to a contest, that one is lower
        if (null == e1.getContest() && null != e2.getContest()) {
            return -1;
        } else if (null != e1.getContest() && null == e2.getContest()) {
            return 1;
        }

        int byDisplayName = e1.getDisplayName().compareToIgnoreCase(e2.getDisplayName());

        // If no other criteria have decided the issue, decide based on UUID
        if (0 == byDisplayName) {
            return e1.getId().compareTo(e2.getId());
        }
        return byDisplayName;
    }

}
