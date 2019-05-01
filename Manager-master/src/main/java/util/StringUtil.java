package util;

public class StringUtil {
    public static boolean isNotEmpty(String string) {
        if(!"".equals(string)&&string!=null){
            return true;
        }
        else {return false;}
    }
    public static boolean isEmpty(String str) {
        if("".equals(str)||str==null){
            return true;
        }
        else {return false;}
    }
}
