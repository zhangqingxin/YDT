package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.YeWuBanLi;
import play.mvc.Controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class YeWuBanLiAdmin extends Controller {
    public static void adminOrder() {
        render();
    }

    public static void getOrders(Integer page, Integer rows, String startdate, String enddate) throws ParseException {
    	int start = (page-1) * rows;
    	List<YeWuBanLi> orderList;
    	long count = 0;
    	if (startdate !=null && enddate != null) {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    		Date sDate = sf.parse(startdate);
    		Date eDate = sf.parse(enddate);
    		orderList = YeWuBanLi.find("date >= ? and date <= ? and isdelete = '0' order by date desc",sDate, eDate).from(start).fetch(rows);
    		count = orderList.size();
    	} else {
    		orderList = YeWuBanLi.find("isdelete = '0' order by date desc").from(start).fetch(rows);
    		count = YeWuBanLi.count();
    	}
        
        renderText(generateJson(orderList, count));
    }
    
    public static JsonObject generateJson(List<YeWuBanLi> list, long count) {
        JsonObject json = new JsonObject();
        json.addProperty("total", count);
        JsonArray array = new JsonArray();
        for(YeWuBanLi order: list) {
            JsonObject obj = getOrderJsonObj(order);
            array.add(obj);
        }
        json.add("rows", array);
        return json;
    }
    
    private static JsonObject getOrderJsonObj(YeWuBanLi order) {
    	JsonObject obj = new JsonObject();
    	if (order == null) {
    		return obj;
    	}
        obj.addProperty("orderid", order.id);
        obj.addProperty("ordernum", order.orderNum);
        obj.addProperty("registerid", order.user.registerId);
        if (order.date != null) {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        	obj.addProperty("date", df.format(order.date));
        }
        obj.addProperty("orderprice", order.fee);
        obj.addProperty("state", order.orderstate);
        obj.addProperty("address", order.address);
        String tel = "";
        if (order.phone != null) {
        	tel = order.phone;
        }
        obj.addProperty("tel", tel);
        obj.addProperty("name", order.user.realname);
        obj.addProperty("other", order.des);
        
        return obj;
    }

    public static void chageOrderStatus(List<String> id, String status) {
    	for (String ordernum: id) {
    		YeWuBanLi order = YeWuBanLi.find("byOrderNum", ordernum).first();
    		order.orderstate = status;
    		order.save();
    	}
    }
    
    public static void deleteOrder(Long[] id) {
    	for (int i=0;i<id.length;i++) {
    		YeWuBanLi order = YeWuBanLi.findById(id[i]);
    		order.isdelete = 1;
    		order.save();
    	}
    }
}
