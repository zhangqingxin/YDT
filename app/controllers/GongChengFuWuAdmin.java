package controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.YeWuBanLi;
import models.Image;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import play.Play;
import play.libs.Files;
import play.mvc.Controller;
import play.mvc.With;
import utils.Utils;

@Check("admin")
@With(Secure.class)
public class GongChengFuWuAdmin extends Controller {

    public static void adminOrder() {
        render();
    }

    public static void getOrders(Integer page, Integer rows, String startdate, String enddate) throws ParseException {
    	if (page == 0) {
    		page = page + 1;
    	}
    	int start = (page-1) * rows;
    	List<YeWuBanLi> orderList;
    	long count = 0;
    	if (startdate !=null && enddate != null) {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    		Date sDate = sf.parse(startdate);
    		Date eDate = sf.parse(enddate);
    		orderList = YeWuBanLi.find("type = ?, date >= ? and date <= ? and isdelete = '0' order by date desc",2,sDate, eDate).from(start).fetch(rows);
    		count = orderList.size();
    	} else {
    		orderList = YeWuBanLi.find("isdelete = '0' and type=2 order by date desc").from(start).fetch(rows);
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
        obj.addProperty("registerid", order.orderNum);
        if (order.date != null) {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
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
        obj.addProperty("name", order.customName);
        obj.addProperty("other", order.des);
        obj.addProperty("type", order.type);
        
        return obj;
    }
    
    public static void chageOrderStatus(List<Long> id, String status, String des) {
    	for (Long orderid: id) {
    		YeWuBanLi order = YeWuBanLi.find("byId", orderid).first();
    		order.orderstate = status;
    		order.des = des;
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

    public static void uploadImage(Long data, File fileupload) {
    	YeWuBanLi df = YeWuBanLi.find("byId", data).first();
    	if (df != null) {
    		String url = "/public/userimage/" + Utils.getImageFileId();
    		Files.copy(fileupload, Play.getFile(url));
    		String filename = fileupload.getName();
    		Image image = new Image();
    		image.filename = filename;
    		image.url = url;
    		image.size = fileupload.length();
    		image.save();
    		df.addImage(image);
    		JsonObject obj = new JsonObject();
    		obj.addProperty("result", "success");
    		renderHtml("success");
    	} else {
    		System.out.println("ERROR: Can't find id at " + data);
    	}
    }
    
    public static void deleteImage(Long orderid, String id) {
    	YeWuBanLi df = YeWuBanLi.find("byId", orderid).first();
    	Image todel = null;
    	if (df != null && df.imagelist != null) {
    		for (Image image: df.imagelist) {
    			if (image.id == Long.valueOf(id)) {
    				todel = image;
    				df.imagelist.remove(image);
    				break;
    			}
    		}
    	}
    	if (todel != null) {
    		df.save();
    		Files.delete(Play.getFile(todel.url));
    		renderText("success");
    	} else {
    		renderText("failed");
    	}
    }
    
    public static void getImageList(Long id) {
    	YeWuBanLi df = YeWuBanLi.find("byId", id).first();
    	if (df != null) {
    		JsonObject list = new JsonObject();
    		list.addProperty("total", df.imagelist.size());
    		JsonArray array = new JsonArray();
    		for (Image image: df.imagelist) {
    			JsonObject obj = new JsonObject();
    			obj.addProperty("id", image.id);
    			obj.addProperty("filename", image.filename);
    			obj.addProperty("size", image.size);
    			obj.addProperty("url", image.url);
    			array.add(obj);
    		}
    		list.add("rows", array);
    		renderText(list);
    	} else {
    		System.out.println("ERROR: Can't find id at " + id);
    	}
    }
}
