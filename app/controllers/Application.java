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

    public static void regist() {
    	
    }

    public static void login(String username, String password) {
    	User user = User.connect(username, password);
    	if (user != null) {
    		session.put("user", user.username);
    		System.out.println("User: " + user.username);
    		index();
    	} else {
    		renderText("failed");
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
        
        System.out.println("Is user in session: " + username);
        
        if(username != null) {
        	
            return User.find("byUsername", username).first();
        }
        
        return null;
    }
}