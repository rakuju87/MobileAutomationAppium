package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

  public static String getNextMondayDate(String pattern) {
    Calendar now = Calendar.getInstance();
    int weekday = now.get(Calendar.DAY_OF_WEEK);
    if (weekday != Calendar.MONDAY)
    {
      int days = (Calendar.SATURDAY - weekday + 2) % 7;
      now.add(Calendar.DAY_OF_YEAR, days);
    }
    return new SimpleDateFormat(pattern).format(now.getTime());
  }

  public static String removeExtraZeroDate(String date, String delimeter) {
    date = date.split(delimeter)[0] + delimeter + new Integer(date.split(delimeter)[1]).toString() + delimeter + date.split(delimeter)[2];
    return date;
  }

}
