package is.stma.judgebean.util;

import is.stma.judgebean.model.AbstractEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EntityUtility {

    public static String prefix(AbstractEntity entity) {
        return entity.getClass().getSimpleName() + " " + entity.getId().substring(0, 5)
                + ": " + entity.getName();
    }

    public static LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}