package application.utilities;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd-mm-yyyy";
    private static final String TIME_FORMAT = "HH:mm ";

    public static void checkFormatDate(String date) throws DateTimeException {
        boolean isValidated = date.matches("\\d{1,2}-\\d{1,2}-\\d{4}");
        if (!isValidated) {
            throw new DateTimeException("Inappropriate date format!.");
        }
    }

    public static void checkFormatTime(String time) throws DateTimeException {
        boolean isValidated = time.matches("\\d{1,2}:\\d{1,2}");
        if (!isValidated) {
            throw  new DateTimeException("Inappropriate time format!.");
        }
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }
}
