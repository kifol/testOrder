package org.example.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Courier{
    private int id;
    private double x;
    private double y;
    private int orderId;
    private double s;
    private List<HashMap<Integer, Double>> dopOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public List<HashMap<Integer, Double>> getDopOrder() {
        return dopOrder;
    }

    public void setDopOrder(List<HashMap<Integer, Double>> dopOrder) {
        this.dopOrder = dopOrder;
    }

    public void setDopOrder(int id, double s) {
        if (this.dopOrder == null){
            this.dopOrder = new ArrayList<>();
        }
        HashMap<Integer, Double> dop = new HashMap<>();
        dop.put(id, s);
        this.dopOrder.add(dop);

    }
}
