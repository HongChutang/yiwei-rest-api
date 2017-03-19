package person.practice.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Evan Hung on 2016/12/26.
 */
@Entity
public class Goods {
    @Id
    private String goodsName;
    @NotBlank
    private String author;
    @NotNull
    private Double price;
    @NotNull
    private Integer stock;
    private Integer cartStock;
    @NotBlank
    private String description;
    @NotBlank
    private String image;
    @NotBlank
    private String type;
    private String typeLabel;

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCartStock() {
        return cartStock;
    }

    public void setCartStock(Integer cartStock) {
        this.cartStock = cartStock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
