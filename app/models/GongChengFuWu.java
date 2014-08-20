package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="meta_gongchengfuwu")
public class GongChengFuWu extends Model {

	@Required
	@ManyToOne
	public User user;
	
	// 用电地址
	public String address;

	// 缴费金额
	public Double fee;

	// 联系电话
	public String phone;
	
	// 工程服务类别
	public int type;

}
