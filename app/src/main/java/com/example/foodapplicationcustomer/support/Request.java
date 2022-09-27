package com.example.foodapplicationcustomer.support;

import java.util.List;

public class Request {

        private String cellNumber;
        private String Name;
        private String address;
        private String total;
        private List<OrderModel> orders;
        private String status;



        public Request(){

        }

    public Request(String cellNumber, String name, String address, String total, List<OrderModel> orders) {
        this.cellNumber = cellNumber;
        Name = name;
        this.address = address;
        this.total = total;
        this.orders = orders;
        this.status = "Placed"; ///"Placed", "On Delivery", "Delivered"

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
