package is.stma.judgebean.beanpoll.util;

import is.stma.judgebean.beanpoll.model.AbstractEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EntityUtility {

    public static String prefix(AbstractEntity entity) {
        if (null != entity) {
            String entityUUID = entity.getId();
            if (entityUUID.length() > 5) {
                entityUUID = entityUUID.substring(0, 5);
            }
            return entity.getClass().getSimpleName() + " " + entityUUID + ": " + entity.getName();
        }
        return "null entity";
    }

    public static LocalDate convert(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}