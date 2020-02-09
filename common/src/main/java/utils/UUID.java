package utils;

import java.security.SecureRandom;
import java.util.GregorianCalendar;

public class UUID {
    public static final String generateGUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public static final String generateUUID() {
        return UUIDGenerator.generate();
    }

    public static final String nameGUIDFromBytes(byte[] bs) {
        return java.util.UUID.nameUUIDFromBytes(bs).toString();
    }

    private static final class UUIDGenerator {
        private static final char[] BASE64_DIGITS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};

        private static final long EPOCH_ADJUSTMENT = new GregorianCalendar(1970, 0, 1, 0, 0, 0).getTime().getTime() - new GregorianCalendar(1582, 9, 15, 0, 0, 0)
                .getTime()
                .getTime();

        private static final byte[] uuid = new byte[16];

        private static final char[] buffer = new char[23];

        private static long lastTime = System.currentTimeMillis() + EPOCH_ADJUSTMENT;

        private static short clockSequence;

        private static short timeAdjustment;

        private static int sleepTime = 1;

        static {
            SecureRandom random = new SecureRandom();

            clockSequence = (short) random.nextInt(16384);
            updateClockSequence();

            byte[] nodeAddress = new byte[6];

            random.nextBytes(nodeAddress);
            int tmp495_494 = 0;
            byte[] tmp495_493 = nodeAddress;
            tmp495_493[tmp495_494] = ((byte) (tmp495_493[tmp495_494] | 0xFFFFFF80));
            for (int i = 0; i < 6; i++) {
                uuid[(i + 10)] = nodeAddress[i];
            }
            buffer[0] = '_';
        }

        public static synchronized String generate() {

            for (int i = 0; i < 5; i++) {
                buffer[(4 * i + 1)] = BASE64_DIGITS[(uuid[(i * 3)] >> 2 & 0x3F)];
                buffer[(4 * i + 2)] = BASE64_DIGITS[(uuid[(i * 3)] << 4 & 0x30 | uuid[(i * 3 + 1)] >> 4 & 0xF)];
                buffer[(4 * i + 3)] = BASE64_DIGITS[(uuid[(i * 3 + 1)] << 2 & 0x3C | uuid[(i * 3 + 2)] >> 6 & 0x3)];
                buffer[(4 * i + 4)] = BASE64_DIGITS[(uuid[(i * 3 + 2)] & 0x3F)];
            }
            buffer[21] = BASE64_DIGITS[(uuid[15] >> 2 & 0x3F)];
            buffer[22] = BASE64_DIGITS[(uuid[15] << 4 & 0x30)];

            return new String(buffer);
        }

        public static synchronized void generate(byte[] uuid) {

            for (int i = 0; i < 16; i++) {
                uuid[i] = uuid[i];
            }
        }

        private static void updateClockSequence() {
            uuid[8] = ((byte) (clockSequence >> 8 & 0x3F | 0x80));

            uuid[9] = ((byte) (clockSequence & 0xFF));
        }

        private static void updateCurrentTime() {
            long currentTime = System.currentTimeMillis() + EPOCH_ADJUSTMENT;
            if (lastTime > currentTime) {
                clockSequence = (short) (clockSequence + 1);
                if (16384 == clockSequence) {
                    clockSequence = 0;
                }
                updateClockSequence();
            } else if (lastTime == currentTime) {
                timeAdjustment = (short) (timeAdjustment + 1);
                if (timeAdjustment > 9999) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException localInterruptedException) {
                    }
                    timeAdjustment = 0;
                    currentTime = System.currentTimeMillis() + EPOCH_ADJUSTMENT;
                    while (lastTime == currentTime) {
                        try {
                            sleepTime += 1;
                            Thread.sleep(1L);
                        } catch (InterruptedException localInterruptedException1) {
                        }
                        currentTime = System.currentTimeMillis() + EPOCH_ADJUSTMENT;
                    }
                }
            } else {
                timeAdjustment = 0;
            }
            lastTime = currentTime;

            currentTime *= 10000L;
            currentTime += timeAdjustment;
            currentTime |= 0x0;
            for (int i = 0; i < 4; i++) {
                uuid[i] = ((byte) (int) (currentTime >> 8 * (3 - i) & 0xFF));
            }
            for (int i = 0; i < 2; i++) {
                uuid[(i + 4)] = ((byte) (int) (currentTime >> 8 * (1 - i) + 32 & 0xFF));
            }
            for (int i = 0; i < 2; i++) {
                uuid[(i + 6)] = ((byte) (int) (currentTime >> 8 * (1 - i) + 48 & 0xFF));
            }
        }
    }
}
