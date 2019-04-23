package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
    public Connection getCon()throws Exception{
        Class.forName(PropertiesUtil.getValue("jdbcName"));
       return  DriverManager.getConnection(PropertiesUtil.getValue("dburl"), PropertiesUtil.getValue("dbUserName"), PropertiesUtil.getValue("dbPassword"));

    }
    public void closeCon(Connection con)throws Exception{
        if (con!=null){
            con.close();
        }
    }


}
