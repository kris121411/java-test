package classes;

import java.util.*;
import java.text.*;
import org.simpleframework.xml.transform.*;

public class SetDateFormat implements Transform<Date>
{
    private DateFormat dateFormat;

    public SetDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public Date read(String value) throws Exception {
        return dateFormat.parse(value);
    }

    @Override
    public String write(Date value) throws Exception {
        return dateFormat.format(value);
    }
}
