package business;

import java.util.Random;

public class ReservationCodeGenerator {
    private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateReservationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

}
