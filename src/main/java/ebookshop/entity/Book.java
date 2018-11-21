package ebookshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // for JPA, means this class can be mapped to a database table
@Table(name="books") // name of table in database - omit if same as class name
public class Book {
    @Id // required for JPA entity
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String title;
    private String author;
    private float price;
    private int qty;

    public Book() {} // default constructor required by Hibernate

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

}