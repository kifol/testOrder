package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Models.Courier;
import org.example.Models.Order;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = new File("src/main/resources/response.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(file).get("orders");

        List<Order> orders = Arrays.asList(mapper.readValue(node.toString(), Order[].class));
        node = mapper.readTree(file).get("couriers");
        List<Courier> couriers = Arrays.asList(mapper.readValue(node.toString(), Courier[].class));

        HashMap<Integer, Courier> courierId = new HashMap<>();
        HashMap<Integer, Order> orderId = new HashMap<>();
        HashMap<Courier, List<Order>> result = new HashMap<>();
        HashMap<Order, String> orderHM = new HashMap<>();

        for (Order order: orders) {
            orderHM.put(order, "new");
            orderId.put(order.getId(), order);
            HashMap<Integer, Double> courierHashMap = new HashMap<>();
            order.setS(getDistance(order.getX(), order.getY(), order.getX1(), order.getY1()));
            for (Courier courier: couriers) {
                courierId.put(courier.getId(), courier);
                courierHashMap.put(courier.getId(), getDistance(order.getX(), order.getY(), courier.getX(), courier.getY()));
            }
            courierHashMap.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue()).forEach(order::setCouriers);
        }

        while (orderHM.size() != 0){
            for (Map.Entry<Order, String> order : orderHM.entrySet()){
                find(order.getKey(), courierId, orderId);
            }
            for (Courier courier: couriers){
                if(courier.getOrderId() != 0){
                    orderHM.remove(orderId.get(courier.getOrderId()));
                    List<Order> tmpOrder = result.get(courier);
                    if (tmpOrder == null){
                        tmpOrder = new ArrayList<>();
                    }
                    tmpOrder.add(orderId.get(courier.getOrderId()));
                    result.put(courier, tmpOrder);
                    courier.setDopOrder(courier.getOrderId(), courier.getS());
                    courier.setX(orderId.get(courier.getOrderId()).getX1());
                    courier.setY(orderId.get(courier.getOrderId()).getY1());
                    courier.setOrderId(0);
                    courier.setS(0);
                }
            }
            refresh(orderHM, couriers, courierId);
        }

        for (Map.Entry<Courier, List<Order>> res: result.entrySet()){
            List<Order> res1 = res.getValue();
            System.out.println("Курьер " + res.getKey().getId() + ". Заказы:");
            List<HashMap<Integer, Double>> zakaz = res.getKey().getDopOrder();
            for (HashMap<Integer, Double> zMap: zakaz){
                for (Map.Entry<Integer, Double> mp : zMap.entrySet()){
                    System.out.println("Заказ №" + mp.getKey() + ", расстояние: " + String.format("%.2f", mp.getValue()) + " м.");
                }
            }
            System.out.println();
        }
    }

    private static void refresh(HashMap<Order, String> orderHM, List<Courier> couriers, HashMap<Integer, Courier> courierId){
        for (Map.Entry<Order, String> orderMap : orderHM.entrySet()) {
            Order order = orderMap.getKey();

            HashMap<Integer, Double> courierHashMap = new HashMap<>();
            order.setS(getDistance(order.getX(), order.getY(), order.getX1(), order.getY1()));
            for (Courier courier: couriers) {
                courierHashMap.put(courier.getId(), getDistance(order.getX(), order.getY(), courier.getX(), courier.getY()));
            }
            courierHashMap.entrySet().stream().sorted(Map.Entry.<Integer, Double>comparingByValue()).forEach(order::setCouriers);
        }
    }

    private static void find(Order order,  HashMap<Integer, Courier>  courierId, HashMap<Integer, Order> orderId){
        List<Map.Entry<Integer, Double>> orderCouriers = order.getCouriers();
        int x = 0;
        List<Integer> index = new ArrayList<>();
        for (Map.Entry<Integer, Double> cour: orderCouriers){
            double s = order.getS() + cour.getValue();
            Courier courier1 = courierId.get(cour.getKey());
            if (courier1.getOrderId() == 0){
                courier1.setOrderId(order.getId());
                courier1.setS(s);
                break;
            } else if (courier1.getS() > s) {
                Order oldOrder = orderId.get(courier1.getOrderId());
                courier1.setOrderId(order.getId());
                courier1.setS(s);

                oldOrder.getCouriers().remove(0);

                find(oldOrder, courierId, orderId);
                break;
            } else {
                index.add(x);
            }
            x++;
        }
        List<Map.Entry<Integer, Double>> delCour = order.getCouriers();
        for (int i = index.size()-1;i>-1;i--){
            delCour.remove(i);
        }
        order.setCouriers1(delCour);
    }

    private static double getDistance(double llat1, double llong1, double llat2, double llong2) {

        double rad = 6372795;

        double lat1 = llat1 * Math.PI / 180;
        double lat2 = llat2 * Math.PI / 180;
        double long1 = llong1 * Math.PI / 180;
        double long2 = llong2 * Math.PI / 180;

        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double delta = long2 - long1;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);

        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y,x);
        return ad * rad;
    }
}