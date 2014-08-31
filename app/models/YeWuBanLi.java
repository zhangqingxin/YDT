package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="meta_yewubanli")
public class YeWuBanLi extends Model {

	public YeWuBanLi() {
		this.imagelist = new ArrayList<Image>();
	}

	@Required
	public String orderNum;

	@Required
	@ManyToOne
	public User user;
	
	// 用电地址
	public String address;
	
	// 用电地址
	public String customName;

	// 缴费金额
	public Double fee;

	// 联系电话
	public String phone;
	
	// 办理业务类别
	public int type;

	public Date date;
	
	public String orderstate="0";//0:未处理 1：已发货 2：已签收 3：完成交易
	
	public int isdelete;
	
	public String des = "";
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval=true)
	public List<Image> imagelist;
	
	public YeWuBanLi addImage(Image image) {
		this.imagelist.add(image);
		this.save();
		return this;
	}
}
