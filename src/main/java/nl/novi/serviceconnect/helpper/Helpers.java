package nl.novi.serviceconnect.helpper;

import java.io.File;
import java.util.Date;

public class Helpers {
    public static boolean isNotNullOrEmptyDate(Date date) { return date != null && !date.equals(new Date(0)); }
    public static boolean isValidFile(File file) { return file != null && file.exists() && file.isFile(); }
}
