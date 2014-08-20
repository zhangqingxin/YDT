package controllers;

import models.AdminUser;
import models.User;

public class Security extends Secure.Security {
	
    static boolean authentify(String username, String password) {
        return AdminUser.connect(username, password) != null;
    }
    
    static boolean check(String profile) {
        if("admin".equals(profile)) {
            return AdminUser.find("byUserName", connected()).<AdminUser>first().isAdmin;
        }
        return false;
    }
    
    static void onDisconnected() {
        Application.index();
    }
    
//    static void onAuthenticated() {
//        Admin.index();
//    }
	
}
