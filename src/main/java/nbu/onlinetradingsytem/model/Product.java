package nbu.onlinetradingsytem.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String imageURL;

    @Column(nullable = false)
    private Category category;

    @Column()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDiscount;

    @Column()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDiscount;

    @Column()
    private double discount = 0;

    @ManyToOne(cascade=CascadeType.ALL)
    private Supplier supplier;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        if (startDiscount != null && endDiscount!=null){
            if (new Date().after(startDiscount) && new Date().before(endDiscount)) // Problem?
            {
                return price - price*discount/100;
            }
        }

        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getStartDiscount() {
        return startDiscount;
    }

    public void setStartDiscount(Date startDiscount) {
        this.startDiscount = startDiscount;
    }

    public Date getEndDiscount() {
        return endDiscount;
    }

    public void setEndDiscount(Date endDiscount) {
        this.endDiscount = endDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = (discount.isEmpty() ? 0 : Double.parseDouble(discount));
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Product() {

    }

    public enum Category {
        HARDWARE, CLOTHING, FURNITURE, OTHER
    }
}
