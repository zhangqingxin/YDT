package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="meta_dianfei")
public class DianFei extends Model {
	
	@Required
	public String orderNum;
	
	@Required
	@ManyToOne
	public User user;
	
	//用电客户名称
	public String username;
	
	//用电地址
	public String address;
	
	//缴费金额
	public Double fee;
	
	//联系电话
	public String phone;
	
	public Date date;
	
	public String orderstate="0";//0:未处理 1：已发货 2：已签收 3：完成交易
	
	public int isdelete;
	
	public String des = "";

}
