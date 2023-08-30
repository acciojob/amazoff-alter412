package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String,Order> orders = new HashMap<>();
    HashMap<String,DeliveryPartner> partners = new HashMap<>();

    HashMap<String,String> orderPartnerPair = new HashMap<>();

    HashMap<String,List<String>> partnerOrders = new HashMap<>();

    public void addOrder(Order order) {
        if(orders!=null){
            if(!orders.containsKey(order.getId())){
                orders.put(order.getId(),order);
            }
        }


    }

    public void addPartner(String partnerId) {
        DeliveryPartner temp = new DeliveryPartner(partnerId);
        if(!partners.containsKey(partnerId)){
            partners.put(partnerId,temp);
        }
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(!orderPartnerPair.containsKey(orderId)){
            orderPartnerPair.put(orderId,partnerId);
            if(partnerOrders.containsKey(partnerId)){
                List<String> temp = partnerOrders.get(partnerId);
                if(!temp.contains(orderId)){
                    temp.add(orderId);
                    DeliveryPartner dp =partners.get(partnerId);
                    dp.setNumberOfOrders(dp.getNumberOfOrders()+1);
                }

            }else{
                List<String> temp = new ArrayList<>();
                temp.add(orderId);
                partnerOrders.put(partnerId,temp);
                DeliveryPartner dp =partners.get(partnerId);
                dp.setNumberOfOrders(dp.getNumberOfOrders()+1);
            }
        }
    }

    public Order getOrderById(String orderId) {
        Order o = null;
        if(orders.containsKey(orderId)){
            o = orders.get(orderId);
        }
        return o;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner dp = null;
        if(partners.containsKey(partnerId)){
            dp = partners.get(partnerId);
        }

        return dp;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        Integer count=0;
        if(partners.containsKey(partnerId)){
            count=partners.get(partnerId).getNumberOfOrders();
        }
        return count;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> temp = null;
        if(partnerOrders.containsKey(partnerId)){
            temp = partnerOrders.get(partnerId);
        }

        return temp;
    }

    public List<String> getAllOrders() {
        List<String> temp = new ArrayList<>();
        for(String x:orders.keySet()){
            temp.add(x);
        }
        return temp;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer count=0;
        for(String x:orders.keySet()){
            if(!orderPartnerPair.containsKey(x)){
                count++;
            }
        }
        return count;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String[] temp = time.split(":");
        int hr = Integer.parseInt(temp[0]);
        int min = Integer.parseInt(temp[1]);
        int total = (hr*60)+min;
        Integer count = 0;
        if(partnerOrders.containsKey(partnerId)){
            List<String> or = partnerOrders.get(partnerId);
            for(String x:or){
                Order o = orders.get(x);
                if(o.getDeliveryTime()>total){
                    count++;
                }
            }
        }

        return count;

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String ans="";
        int max=0;
        if(partnerOrders.containsKey(partnerId)){
            for(String x:partnerOrders.get(partnerId)){
                if(orders.get(x).getDeliveryTime()>max){
                    max=orders.get(x).getDeliveryTime();
                }
            }
            ans=ans+(max/60)+":"+(max%60);
        }
        return ans;
    }

    public void deletePartnerById(String partnerId) {
        if(partners.containsKey(partnerId)){
            partners.remove(partnerId);
        }
        if(partnerOrders.containsKey(partnerId)){
            List<String> o = partnerOrders.get(partnerId);
            partnerOrders.remove(partnerId);
            for(String x:o){
                orderPartnerPair.remove(x);
            }
        }
    }

    public void deleteOrderById(String orderId) {
        if(orders.containsKey(orderId)){
            orders.remove(orderId);
        }
        if(orderPartnerPair.containsKey(orderId)){
            String temp = orderPartnerPair.get((orderId));
            orderPartnerPair.remove(orderId);
            partnerOrders.get(temp).remove(orderId);
            DeliveryPartner dp=partners.get(temp);
            dp.setNumberOfOrders(dp.getNumberOfOrders()-1);
        }
    }
}
