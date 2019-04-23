package util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    public static String getWeekDays(Date date) {
        String[] weekdays={"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekdays[w];
    }

    public static int getSecond(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
      int hour=  calendar.get(Calendar.HOUR_OF_DAY);
        int minute=  calendar.get(Calendar.MINUTE);
        int second=  calendar.get(Calendar.SECOND);
        return hour*60*60+minute*60+second;
    }


}
