package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        render();
    }

    public static void reg(String username,String passwd) {
    	
    	
    	User user = new User(username, passwd,"");
		user.save();
		session.put("user", username);
		renderArgs.put("user", user);
		renderTemplate("Application/index.html",user);
		//index();
    }

    public static void login(String login_username, String login_passwd) {
    	User user = User.connect(login_username, login_passwd);
    	if (user != null) {
    		session.put("user", user.username);
    		System.out.println("User: " + user.username);
    		index();
    	} else {
    		renderText("failed");
    	}
    }

    public static void logout(){
    	 session.clear();
         index();
    	
    }
    
    
    public static void yewubanli(String type ){
    	User user = connected();
        if(user != null) {
        	renderArgs.put("user", user);
        }
    	if(type!=null){
    		if(type.equals("1"))
    			renderTemplate("Application/yewubanli.html",user);
    		else if(type.equals("3"))
    			renderTemplate("Application/yewubanli_project.html",user);
    		else if(type.equals("2"))
    			renderTemplate("Application/yewubanli_fee.html",user);
    	}
    	
    	index();
    	
    }
    
    public static void yewubanli_submit(String userNum,String customName,String addr,String tel,int yewutype){
    	
    	YeWuBanLi yewu = new YeWuBanLi();
    	yewu.user= connected();
    	yewu.orderNum= userNum;
    	yewu.address=addr;
    	yewu.customName=customName;
    	yewu.phone=tel;
    	yewu.type=yewutype;
    	yewu.save();
    	 index();
    	
    }
    
    
    
    public static void  checkUserLogin(String login_username,String login_passwd){
    	
    	User user = User.find("byUsernameAndPassword", login_username, login_passwd).first();
		if(user!=null){
			response.print(true);
		}else{
			response.print(false);
			
		}
		
    	
    }
    
    
    
    public static void checkUser(String name){

		User user =User.find("byUsername", name).first();
		
		if(user!=null){
			response.print(false);
		}else{
			response.print(true);
			
		}
		
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