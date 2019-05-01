package util;

import java.util.List;

public class StatusUtil {
    public static void status(List statusList, String first) {
        String[] temp = {"空闲", "所有", "使用中"};
        for (int i = 0; i < temp.length; i++) {int flag=0;
            if (first.equals(temp[i])) {
                statusList.set(flag, temp[i]);
                for (int j = 0; j < temp.length; j++) {
                    if (j != i) {    flag = flag + 1;
                        statusList.set(flag, temp[j]);
                    }
                }
            }
        }
    }
}
