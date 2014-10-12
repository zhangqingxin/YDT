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
    		if(type.equals("1")){ 
    			
    			
    			renderTemplate("Application/yonngdianmaster_list.html",user);
    			
    			}
    			
    		else if(type.equals("3"))
    			renderTemplate("Application/showproject.html",user);
    			//renderTemplate("Application/yewubanli_project.html",user);
    		else if(type.equals("2"))
    			renderTemplate("Application/yewubanli_fee.html",user);
    		else if(type.equals("4"))
    			renderTemplate("Application/yewubanli_project.html",user);
    		else if(type.equals("5"))
    			renderTemplate("Application/showquestion.html",user);
    		else if(type.equals("6"))
    			renderTemplate("Application/yewubanli_submitquestion.html",user);
    	}
    	
    	index();
    	
    }
    
    
    public static void yewubanli_list(){
    	User user = connected();
    	
    	List<YeWuBanLi> ls= YeWuBanLi.find("user=? and type <>1 order by date desc", user).fetch();
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
    	
    	if(yewu.type==1){
    		render(yewu,user,url);
    	}else if(yewu.type==2){
    		renderTemplate("Application/show2.html",yewu,user);
    	}else if(yewu.type==6){
    		renderTemplate("Application/show6.html",yewu,user);
    	}
    	
    	
    	
    }
    
    public static void yewubanli_submit(String userNum,String customName,String addr,String tel,Double fee,int yewutype,String projectType){
    	
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
    	
    	yewu.question= params.get("question");
    	yewu.projectType=projectType;
    	
    	//yewu.imagelist.add(im);
    	User user = yewu.user;
    	yewu.save();
    	StringBuffer sbHtml = new StringBuffer();
    	if(yewutype==1){
    		renderTemplate("Application/confirmPay.html",user,fee,trade_no);
    	}else{
    		sbHtml.append("<script>alert('提交成功！'); window.location.href='/'; </script>");
    		renderHtml(sbHtml);
    	}
    	
    	
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
	
	
	public static void addSubinfo(String account, String username,String addr,String feetime,String group){
		User user = connected();
		if(user==null){
			index();
			
		}else{
			RelatedUser sub = new RelatedUser();
			
			sub.account=account;
			sub.username=username;
			sub.addr=addr;
			sub.feetime=feetime;
			sub.grouptype=group;
			sub.user=user;
			sub.save();
			StringBuffer sbHtml = new StringBuffer();
			sbHtml.append("<script>alert('添加信息成功！'); window.location.href='/yongdianguanjia'; </script>");
    		renderHtml(sbHtml);
			
		}
		
		
	}
	
	
	public static void yongdianguanjia(){
		User user = connected();
		List<RelatedUser> subs = RelatedUser.find("byUser", user).fetch();
		List<YeWuBanLi> ls= YeWuBanLi.find("user=?  order by date desc", user).fetch();
		
		renderTemplate("Application/yonngdianmaster_list.html",user,subs,ls);
		
	}
	
	public static void delsubUserInfo(Long id){
		User user = connected();
		RelatedUser.delete("id=?", id);
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<script>alert('删除信息成功！'); window.location.href='/yongdianguanjia'; </script>");
		renderHtml(sbHtml);
	}
	
	public static void updatesubUserInfo(Long update_id,String update_account, String update_username,String update_addr,String update_feetime,String update_group ){
		User user = connected();
		if(user==null){
			index();
			
		}else{
			RelatedUser sub = RelatedUser.findById(update_id);
			
			sub.account=update_account;
			sub.username=update_username;
			sub.addr=update_addr;
			sub.feetime=update_feetime;
			sub.grouptype=update_group;
			sub.user=user;
			sub.save();
			StringBuffer sbHtml = new StringBuffer();
			sbHtml.append("<script>alert('修改信息成功！'); window.location.href='/yongdianguanjia'; </script>");
    		renderHtml(sbHtml);
			
		}
		
		
	}
	
}