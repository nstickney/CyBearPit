// https://jj-blogger.blogspot.de/2015/06/utilizing-java-8-date-time-api-with-jsf.html
// https://www.mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
package is.stma.judgebean.beanpoll.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FacesConverter(value = "LocalDateTimeConverter")
public class LocalDateTimeConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss a"));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm:ss a"));
    }

}
