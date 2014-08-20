package controllers;
 
import java.util.List;

import models.AdminUser;
import models.FuncTreeNode;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import controllers.Secure.Security;

 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
        	AdminUser user = AdminUser.find("byUserName", Security.connected()).first();
            renderArgs.put("user", user.username);
        }
    }
 
    
    public static void getFuncTreeNode() {
        FuncTreeNode root = FuncTreeNode.find("byParent", Integer.valueOf(0)).first();
        JsonArray rootArray = new JsonArray();
        JsonObject jsroot = new JsonObject();
        jsroot.addProperty("id", root.id);
        jsroot.addProperty("text", root.label);

        List<FuncTreeNode> secondTreeNodes = FuncTreeNode.find("byParent", Integer.valueOf(root.getId().intValue())).fetch();
        JsonArray jsarray = new JsonArray();
        for (FuncTreeNode node: secondTreeNodes) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", node.id);
            obj.addProperty("text", node.label);
            obj.addProperty("action", node.action);
            obj.addProperty("level", node.level);
            jsarray.add(obj);
        }

        jsroot.add("children", jsarray);
        rootArray.add(jsroot);
        response.setHeader("charset", "UTF-8");
        renderText(rootArray);
    }
    
    public static void admin() {
        render();
    }
}
