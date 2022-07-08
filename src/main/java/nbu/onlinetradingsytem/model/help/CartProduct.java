package nbu.onlinetradingsytem.model.help;

import nbu.onlinetradingsytem.model.Supplier;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


public class CartProduct {

    private Integer id;

    private String name;

    private double price;

    private String imageURL;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDiscount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDiscount;

    @Column()
    private double discount = 0;

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
       return price;
    }

    public double getFinalPrice()
    {
        if (startDiscount != null && endDiscount!=null){
            if (new Date().after(startDiscount) && new Date().before(endDiscount))
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

    public CartProduct() {

    }
}
