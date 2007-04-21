package org.appfuse.web;

import org.apache.struts2.util.StrutsTypeConverter;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Map;

public class DateConverter extends StrutsTypeConverter {
    public static final String format = "MM/dd/yyyy";

    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(value[0]);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return null;
    }

    public String convertToString(Map ctx, Object data) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(data);
    }
} 