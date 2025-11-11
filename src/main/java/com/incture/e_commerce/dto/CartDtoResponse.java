package com.incture.e_commerce.dto;

import java.util.List;

public class CartDtoResponse {
	private int cart_id;
    private int user_id;
    private List<CartDtoItem> items;
    private double total_price;

    public int getCart_id() {
        return cart_id;
    }
    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public List<CartDtoItem> getItems() {
        return items;
    }
    public void setItems(List<CartDtoItem> items) {
        this.items = items;
    }
    public double getTotal_price() {
        return total_price;
    }
    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

}
