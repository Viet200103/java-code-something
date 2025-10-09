package business.utilities;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT = "dd-mm-yyyy";

    public static void checkFormatDate(String date) throws DateTimeException {
        boolean isValidated = date.matches("\\d{1,2}-\\d{1,2}-\\d{4}");
        if (!isValidated) {
            throw new DateTimeException("Inappropriate date format!.");
        }
    }

    public static void validateExpirationDate(String manufacturingDate, String expirationDate) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);


        Date expDate = dateFormat.parse(expirationDate);
        Date manDate = dateFormat.parse(manufacturingDate);

        if (expDate.before(manDate)) {
            throw new DateTimeException("Expiration date must be after manufacturing date");
        }
    }

    public static String formatDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }
}
