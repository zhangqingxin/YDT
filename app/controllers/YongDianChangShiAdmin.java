package controllers;

import models.User;
import play.mvc.Controller;

public class YongDianChangShiAdmin extends Controller {

	public static void index() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}

	public static void jumin_all() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}

	public static void jumin_jibenqingkuang() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}

	public static void jumin_dianfeidianjia() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void jumin_anquanyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void jumin_jieyueyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void jumin_yongdianbiangeng() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void jumin_weixiuweihu() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_all() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_jibenqingkuang() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_jieyueyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_anquanyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_yongdianbiangeng() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_dianfeidianjia() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void geti_weixiuweihu() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_all() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_jibenqingkuang() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_jieyueyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_anquanyongdian() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_yongdianbiangeng() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_dianfeidianjia() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}
	
	public static void bangong_weixiuweihu() {
		User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
	}

	private static User connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        
        String userid = session.get("userid");
       
        if(userid != null) {
            return User.findById(Long.valueOf(userid));
        }
        
        String username = session.get("user");
        
        if(username != null) {
        	
            return User.find("byUsername", username).first();
        }
        
        return null;
    }
}
