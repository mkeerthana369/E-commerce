package com.incture.e_commerce.dto;

public class ProductDtoResponse {
	private int product_id;
    private String product_name;
    private String product_desciption;
    private double product_price;
    private int product_stock;
    private String product_category;
    private String product_image_url;
    private double product_rating;

    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getProduct_desciption() {
        return product_desciption;
    }
    public void setProduct_desciption(String product_desciption) {
        this.product_desciption = product_desciption;
    }
    public double getProduct_price() {
        return product_price;
    }
    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }
    public int getProduct_stock() {
        return product_stock;
    }
    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }
    public String getProduct_category() {
        return product_category;
    }
    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }
    public String getProduct_image_url() {
        return product_image_url;
    }
    public void setProduct_image_url(String product_image_url) {
        this.product_image_url = product_image_url;
    }
    public double getProduct_rating() {
        return product_rating;
    }
    public void setProduct_rating(double product_rating) {
        this.product_rating = product_rating;
    }

}
