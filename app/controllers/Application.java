package controllers;

import play.*;
import play.mvc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import models.*;



public class Application extends Controller {

	public static	java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyMMddhhmmss");
	
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
    
    
    public static void changepd(String oldpassword,String login_passwd,String login_passwd_re){
    	StringBuffer sbHtml = new StringBuffer();
    	User user = connected();
    	if(user.password.equals(oldpassword)&&login_passwd.equals(login_passwd_re)){
    		user.password=login_passwd_re;
    		user.save();
    		sbHtml.append("<script>alert('修改密码成功！'); window.location.href='/personal'; </script>");
    	}else{
    		sbHtml.append("<script>alert('修改密码失败，请重试！'); window.location.href='/personal'; </script>");
    		
    	}
    	

    	
    	
    	renderHtml(sbHtml);
    	
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
    			renderTemplate("Application/showproject.html",user);
    			//renderTemplate("Application/yewubanli_project.html",user);
    		else if(type.equals("2"))
    			renderTemplate("Application/yewubanli_fee.html",user);
    		else if(type.equals("4"))
    			renderTemplate("Application/yewubanli_project.html",user);
    		else if(type.equals("5"))
    			renderTemplate("Application/showquestion.html",user);
    	}
    	
    	index();
    	
    }
    
    
    public static void yewubanli_list(){
    	User user = connected();
    	
    	List<YeWuBanLi> ls= YeWuBanLi.find("user=? order by date desc", user).fetch();
        if(user != null) {
        	renderArgs.put("user", user);
        }
        
        render(ls);
    	
    	
    }
    
    public static void show(Long id){
    	User user = connected();
    	YeWuBanLi yewu = YeWuBanLi.findById(id);
    	Image im =null;
    	String url = "";
    	if( yewu.imagelist!=null&&yewu.imagelist.size()>0){
    		
    		 im = yewu.imagelist.get(0);
    		 url= im.url;
    	}
    	
    	render(yewu,user,url);
    	
    }
    
    public static void yewubanli_submit(String userNum,String customName,String addr,String tel,Double fee,int yewutype,File picture){
    	
    	YeWuBanLi yewu = new YeWuBanLi();
    
//    	play.libs.Files.copy(picture, Play.getFile("public/userFiles/"+picture.getName()));
//    	Image im = new Image();
//    	im.filename=picture.getName();
//    	im.size=picture.length();
//    	im.url="public/userFiles/"+picture.getName();
    	
//    	String [] tmp= picture.getName().split("\\.");
//    	if(tmp.length>0)
//    		im.type=tmp[tmp.length-1];
//    	else
//    		im.type="";
    	String s = format.format(new Date());
    	
    	String trade_no=s+"_"+yewutype;
    	yewu.user= connected();
    	yewu.orderNum= userNum;
    	yewu.address=addr;
    	yewu.customName=customName;
    	yewu.phone=tel;
    	yewu.type=yewutype;
    	yewu.fee=fee;
    	
    	
    	yewu.trade_no=trade_no;
    	
    	
    	//yewu.imagelist.add(im);
    	User user = yewu.user;
    	yewu.save();
    	renderTemplate("Application/confirmPay.html",user,fee,trade_no);
    	
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
	
	
	
	
	public static void listinfo(){
		
		render();
		
	}
	
	
	public static void showinfo(String page){
		User user = connected();
		renderTemplate("Application/"+page+".html",user);
		
	}
}