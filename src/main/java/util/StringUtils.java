package util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by max on 25.09.2016.
 */
public class StringUtils {
    public static int stringTimeToSeconds(String timeInString) {
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        System.out.println(timeInString);
        Pattern pattern = Pattern.compile("([0-9]*)( )(days)");
        Matcher matcher = pattern.matcher(timeInString);
        if (matcher.find()) {
            days = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile("( *)([0-9]*)( )(hours)");
        matcher = pattern.matcher(timeInString);
        if (matcher.find()) {
            hours = Integer.parseInt(matcher.group(2));
        }

        pattern = Pattern.compile("([0-9]*)( )(minutes)(.*)");
        matcher = pattern.matcher(timeInString);
        if (matcher.find()) {
            minutes = Integer.parseInt(matcher.group(1));
        }

        pattern = Pattern.compile("([0-9]*)([.]*)([0-9]*)( )(seconds)(.*)");
        matcher = pattern.matcher(timeInString);
        if (matcher.find()) {
            seconds = (int) (Double.parseDouble(matcher.group(1)));
        }
        seconds = seconds + minutes * 60 + hours * 60 * 60 + days * 24 * 60 * 60;
        return seconds;
    }

    public static String numberToElement(String number){
        int s = Integer.parseInt(number);
        switch (s) {
            case 1: return "H";
            case 2: return "He";
            case 3: return "Li";
            case 4: return "Be";
            case 5: return "B";
            case 6: return "C";
            case 7: return "N";
            case 8: return "O";
            case 9: return "F";
            case 10: return "Ne";
            case 11: return "Na";
            case 12: return "Mg";
            case 13: return "Al";
            case 14: return "Si";
            case 15: return "P";
            case 16: return "S";
            case 17: return "Cl";
            case 18: return "Ar";
            case 19: return "K";
            case 20: return "Ca";
            case 21: return "Sc";
            case 22: return "Ti";
            case 23: return "V";
            case 24: return "Cr";
            case 25: return "Mn";
            case 26: return "Fe";
            case 27: return "Co";
            case 28: return "Ni";
            case 29: return "Cu";
            case 30: return "Zn";
            case 31: return "Ga";
            case 32: return "Ge";
            case 33: return "As";
            case 34: return "Se";
            case 35: return "Br";
            case 36: return "Kr";
            case 37: return "Rb";
            case 38: return "Sr";
            case 39: return "Y";
            case 40: return "Z";
            case 41: return "Nb";
            case 42: return "Mo";
            case 43: return "Tc";
            case 44: return "Ru";
            case 45: return "Rh";
            default:return number;
        }
    }


    public static String secondsToDate(int time) {
        time= time;
        int day = (int) TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time);
        hours = hours-day*24;
        long minute = TimeUnit.SECONDS.toMinutes(time) ;
        minute = minute - hours*60 - day*24*60;
        int second = (int) (time - minute*60 - hours*60*60 - day*24*60*60);
        return day+" days "+hours+" hours " + minute +" minutes " +second + " seconds";
    }
}
