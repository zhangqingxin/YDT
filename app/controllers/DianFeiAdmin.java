package controllers;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.DianFei;
import models.Image;
import play.Play;
import play.libs.Files;
import play.mvc.Controller;
import play.mvc.With;
import utils.Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Check("admin")
@With(Secure.class)
public class DianFeiAdmin extends Controller {

    public static void adminOrder() {
        render();
    }

    public static void getOrders(Integer page, Integer rows, String startdate, String enddate) throws ParseException {
    	if (page == 0) {
    		page = page + 1;
    	}
    	int start = (page-1) * rows;
    	List<DianFei> orderList;
    	long count = 0;
    	if (startdate !=null && enddate != null) {
    		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    		Date sDate = sf.parse(startdate);
    		Date eDate = sf.parse(enddate);
    		orderList = DianFei.find("date >= ? and date <= ? and isdelete = '0' order by date desc",sDate, eDate).from(start).fetch(rows);
    		count = orderList.size();
    	} else {
    		orderList = DianFei.find("isdelete = '0' order by date desc").from(start).fetch(rows);
    		count = DianFei.count();
    	}
        
        renderText(generateJson(orderList, count));
    }
    
    public static JsonObject generateJson(List<DianFei> list, long count) {
        JsonObject json = new JsonObject();
        json.addProperty("total", count);
        JsonArray array = new JsonArray();
        for(DianFei order: list) {
            JsonObject obj = getOrderJsonObj(order);
            array.add(obj);
        }
        json.add("rows", array);
        return json;
    }
    
    private static JsonObject getOrderJsonObj(DianFei order) {
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
    		DianFei order = DianFei.find("byOrderNum", ordernum).first();
    		order.orderstate = status;
    		order.save();
    	}
    }
    
    public static void deleteOrder(Long[] id) {
    	for (int i=0;i<id.length;i++) {
    		DianFei order = DianFei.findById(id[i]);
    		order.isdelete = 1;
    		order.save();
    	}
    }

    public static void uploadImage(String data, File fileupload) {
    	DianFei df = DianFei.find("byOrderNum", data).first();
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
    		System.out.println("ERROR: Can't find OrderNum at " + data);
    	}
    }
    
    public static void deleteImage(String orderid, String id) {
    	DianFei df = DianFei.find("byOrderNum", orderid).first();
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
    
    public static void getImageList(String id) {
    	DianFei df = DianFei.find("byOrderNum", id).first();
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
    		System.out.println("ERROR: Can't find OrderNum at " + id);
    	}
    }
}
