package pl.pracinho.countrycitygame.utils;


public class TimeUtils {

    public static String timeLeft(long milis) {
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = milis / daysInMilli;
        milis = milis % daysInMilli;

        long elapsedHours = milis / hoursInMilli;
        milis = milis % hoursInMilli;

        long elapsedMinutes = milis / minutesInMilli;
        milis = milis % minutesInMilli;

        long elapsedSeconds = milis / secondsInMilli;


        String result = ((elapsedDays > 0) ? elapsedDays + "d " : "") +
                        ((elapsedHours > 0) ? elapsedHours + "h " : "") +
                        ((elapsedMinutes > 0) ? elapsedMinutes + "m " : "") +
                        ((elapsedSeconds > 0) ? elapsedSeconds + "s " : "");

        return result.trim().isEmpty() ? "" : result;
    }
}
