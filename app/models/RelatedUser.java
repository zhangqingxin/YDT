package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
@Table(name="meta_subuserinfo")
public class RelatedUser extends Model {
	
	
	 	@Required
	    @MaxSize(15)
	    @MinSize(4)
	    @Match(value="^\\w*$", message="Not a valid username")
	    public String username;
	 
	 
	 
	    @Required
	    public String account;
	    
	    @Required
	    public String addr;
	    
	    public String feetime;
	    
	    public String grouptype;
	    
	    @ManyToOne
	    @Required
		public  User user ;

}
