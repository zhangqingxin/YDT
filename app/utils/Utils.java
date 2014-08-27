package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class Utils {

    private static SimpleDateFormat sPiddatesf = new SimpleDateFormat("yyMMddHHmmss");

    public static String getImageFileId() {
    	Date now = new Date();
    	String pdate = sPiddatesf.format(now);
    	int r1=(int)(Math.random()*(10));//产生3个0-9的随机数
    	int r2=(int)(Math.random()*(10));
    	int r3=(int)(Math.random()*(10));
    	String id =new StringBuffer(pdate).append(r1).append(r2).append(r3).toString();
    	return id;
    }

}
