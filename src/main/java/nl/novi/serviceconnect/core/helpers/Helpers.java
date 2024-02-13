package nl.novi.serviceconnect.core.helpers;

import java.util.Date;

public class Helpers {
    public static boolean isNotNullOrEmptyDate(Date date) { return date != null && !date.equals(new Date(0)); }
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
