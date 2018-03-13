package is.stma.judgebean.beanpoll.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtility {

    public static LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}