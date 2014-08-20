package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="meta_yewubanli")
public class YeWuBanLi extends Model {

	@Required
	@ManyToOne
	public User user;
	
	// 用电地址
	public String address;

	// 缴费金额
	public Double fee;

	// 联系电话
	public String phone;
	
	// 办理业务类别
	public int type;

}
