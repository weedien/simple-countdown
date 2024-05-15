package cn.weedien.countdown.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationUtil {
    public static String intervalFromNow(Date date) {
        long durationMillis = date.getTime() - System.currentTimeMillis();
        if (durationMillis < 0) {
            return "0s";
        }
        long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
        durationMillis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis);
        durationMillis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis);

        if (hours >= 24) {
            return String.format("%dd%dh%dm%ds", hours / 24, hours % 24, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%dh%dm%ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm%ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    private static final Pattern DURATION_PATTERN = Pattern.compile("(\\d+h)?(\\d+m)?(\\d+s)?");

    public static long parseToMillis(String input) {
        Matcher matcher = DURATION_PATTERN.matcher(input);
        if (matcher.matches()) {
            int hours = 0, minutes = 0, seconds = 0;
            if (matcher.group(1) != null) {
                hours = Integer.parseInt(matcher.group(1).substring(0, matcher.group(1).length() - 1));
            }
            if (matcher.group(2) != null) {
                minutes = Integer.parseInt(matcher.group(2).substring(0, matcher.group(2).length() - 1));
            }
            if (matcher.group(3) != null) {
                seconds = Integer.parseInt(matcher.group(3).substring(0, matcher.group(3).length() - 1));
            }
            return hours * 3600000L + minutes * 60000L + seconds * 1000L;
        } else {
            throw new IllegalArgumentException("Invalid duration format: " + input);
        }
    }

    public static String intervalBeforeNow(Date date) {
        long durationMillis = System.currentTimeMillis() - date.getTime();
        if (durationMillis < 0) {
            return "0s";
        }
        long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
        durationMillis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis);
        durationMillis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis);

        if (hours >= 24) {
            return String.format("%dd%dh%dm%ds", hours / 24, hours % 24, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%dh%dm%ds", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%dm%ds", minutes, seconds);
        } else {
            return String.format("%ds", seconds);
        }
    }

    public static void main(String[] args) {
        // 获取一个距离当前时间1天前的Date对象
        Date pastDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        String duration = intervalBeforeNow(pastDate);
        System.out.println(duration); // 输出: 24h0m0s

        // 获取一个距离当前时间1小时后的Date对象
        Date futureDate = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
        duration = intervalFromNow(futureDate);
        System.out.println(duration); // 输出: 1h0m0s
    }
}