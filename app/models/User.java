package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="meta_customer")
public class User extends Model {

    @Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MaxSize(15)
    @MinSize(5)
    public String password;
    
    @Email
    @Required
    public String email;
    
    @Required
    public String tel;
    
    @Required
    public String address;
    
    //用电客户编号
    @Required
  	public String registerId;
    
    //用电客户名称
    @Required
  	public String realname;
    
    
    @OneToMany(cascade=CascadeType.PERSIST, mappedBy="user" )
    public List<RelatedUser> relatedUsers;
    
    public User(String username,String password,String tel) {
        this.password = password;
        this.username = username;
        this.tel=tel;
    }
    
    public static User connect(String username, String password) {
        return find("byUsernameAndPassword", username, password).first();
    }
}
