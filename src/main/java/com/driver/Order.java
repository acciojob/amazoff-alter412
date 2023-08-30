package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    private String s;
    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.s=deliveryTime;
        String[] temp = deliveryTime.split(":");
        this.id = id;
        this.deliveryTime = (Integer.parseInt(temp[0])*60)+Integer.parseInt(temp[1]);
    }

    public String getId() {
        return id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
