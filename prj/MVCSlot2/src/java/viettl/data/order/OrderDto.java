/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data.order;

import java.sql.Timestamp;
import java.util.List;
import viettl.data.orderdetail.OrderDetailDto;

/**
 *
 * @author viett
 */
public class OrderDto {
    private String orderId;
    private Timestamp datetime;
    private String userAddress;
    private float total;
    
    private List<OrderDetailDto> itemList;

    public OrderDto() {
        
    }
    
    public OrderDto(
            String orderId, 
            String userAdress,
            Timestamp datetime, 
            float total, 
            List<OrderDetailDto> itemList
    ) {
        this.orderId = orderId;
        this.userAddress = userAdress;
        this.datetime = datetime;
        this.total = total;
        this.itemList = itemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<OrderDetailDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderDetailDto> itemList) {
        this.itemList = itemList;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
