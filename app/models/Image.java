package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="meta_image")
public class Image extends Model {

	public String filename;

	public String url;
	
	public long size;

	public String type;
}
