package com.demo.livegift;

/**
 * Created by tongleer.com on 2017/2/8.
 * 礼物实体类
 */

public class GiftEntity {
    private String id;
    private String name;
    private String type;
    private String price;
    private int pic;

    public GiftEntity() {
    }

    public GiftEntity(String id, String name, String type, String price, int pic) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GiftEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price='" + price + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
