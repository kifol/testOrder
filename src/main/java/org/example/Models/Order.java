package org.example.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private int id;
    private double x;
    private double y;
    private double x1;
    private double y1;
    private double s;

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    private List<Map.Entry<Integer, Double>> orders;

    private List<Map.Entry<Integer, Double>> couriers;

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

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public List<Map.Entry<Integer, Double>> getOrders() {

        return orders;
    }

    public void setOrders(Map.Entry<Integer, Double> integerDoubleEntry) {
        if (this.orders == null){
            this.orders = new ArrayList<Map.Entry<Integer, Double>>();
        }
        this.orders.add(integerDoubleEntry);
    }

    public List<Map.Entry<Integer, Double>> getCouriers() {
        return couriers;
    }

    public void setCouriers(Map.Entry<Integer, Double> integerDoubleEntry) {
        if (this.couriers == null){
            this.couriers = new ArrayList<Map.Entry<Integer, Double>>();
        }
        this.couriers.add(integerDoubleEntry);
    }

    public void setCouriers1(List<Map.Entry<Integer, Double>> integerDoubleEntry) {
        this.couriers = integerDoubleEntry;
    }


}
