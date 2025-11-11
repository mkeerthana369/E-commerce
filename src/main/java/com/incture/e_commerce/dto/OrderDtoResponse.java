package com.incture.e_commerce.dto;


import java.util.Date;
import java.util.List;

public class OrderDtoResponse {
	private int order_id;
    private int user_id;
    private List<OrderDtoItem> items;
    private double total_amount;
    private Date order_date;
    private String order_payment_status;
    private String order_status;

    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public List<OrderDtoItem> getItems() {
        return items;
    }
    public void setItems(List<OrderDtoItem> items) {
        this.items = items;
    }
    public double getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    public String getOrder_payment_status() {
        return order_payment_status;
    }
    public void setOrder_payment_status(String order_payment_status) {
        this.order_payment_status = order_payment_status;
    }
    public String getOrder_status() {
        return order_status;
    }
    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

}
