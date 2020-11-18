package org.uplifteds.entitygenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomType {
    public static LocalDate generateRandomDate() {
        final int DAYS_IN_YEAR = 365;
        final int HUNDRED = 100;
        int periodOfYears = HUNDRED * DAYS_IN_YEAR;
        return LocalDate.ofEpochDay(ThreadLocalRandom
                .current().nextInt(-periodOfYears, periodOfYears));
    }

    public static Instant generateRandomTimestamp() {
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextInt());
    }

    public static String generateRandomString(int targetStringLength) {
        int leftLimit = 97; // code of letter 'a'
        int rightLimit = 122; // code of letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String randString = buffer.toString();
        if (targetStringLength > StudentGenerator.maxNameLength){
            randString = randString.toUpperCase(); // only name would be lowercase
        }

        if (targetStringLength == StudentGenerator.skillNameLength){
            randString = "101-" + randString; // skill code prefix
        }

        if (targetStringLength == SubjectGenerator.tutorNameLength){
            randString = "Prof. " + randString; // skill code prefix
        }

        return randString;
    }

}
