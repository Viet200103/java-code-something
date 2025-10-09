package viettl.data.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import viettl.cart.ShoppingCart;
import viettl.data.DatabaseHelper;
import viettl.data.orderdetail.OrderDetailDto;
import viettl.data.orderdetail.OrderDetailDao;
import viettl.data.product.ProductDao;
import viettl.data.product.ProductDto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author viett
 */
public class OrderDao implements Serializable {

    private final String ORDER_ID = "orderID";
    private final String ORDER_DATE = "orderDate";
    private final String ORDER_TOTAL = "total";
    private final String ORDER_USER_ADDRESS = "userAddress";

    public String makeOrder(String userAddress, ShoppingCart cart)
            throws SQLException, ClassNotFoundException {

        Map<String, Integer> items = cart.getItems();
        OrderDto order = new OrderDto();
        order.setUserAddress(userAddress);

        ProductDao productDao = new ProductDao();

        ArrayList<OrderDetailDto> orderDetailList = new ArrayList<>();
        float orderTotal = 0;
        for (Entry<String, Integer> item : items.entrySet()) {

            String producId = item.getKey();
            ProductDto product = productDao.getProductById(producId);

            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setProductId(producId);
            orderDetailDto.setQuantity(item.getValue());
            orderDetailDto.setUnitPrice(product.getUnitPrice());

            float total = product.getUnitPrice() * orderDetailDto.getQuantity();
            orderDetailDto.setTotal(total);

            orderTotal += total;

            orderDetailList.add(orderDetailDto);
        }

        order.setItemList(orderDetailList);
        order.setTotal(orderTotal);

        return insertOrder(order);
    }

    private String insertOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        String orderId = null;

        boolean result = false;

        try {

            connection = DatabaseHelper.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO [Order]("
                    + ORDER_ID + "," + ORDER_DATE + ","
                    + ORDER_USER_ADDRESS + "," + ORDER_TOTAL
                    + ") VALUES ("
                    + "?, ?, ?, ?"
                    + ")";

            PreparedStatement preStatement = connection.prepareStatement(
                    sql, new String[]{ORDER_ID}
            );

            preStatement.setString(1, null);
            preStatement.setTimestamp(2, null);
            preStatement.setString(3, orderDto.getUserAddress());
            preStatement.setFloat(4, orderDto.getTotal());

            preStatement.executeUpdate();

            try (ResultSet resultSet = preStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    orderId = resultSet.getString(ORDER_ID);
                }
            }

            if (orderId != null) {
                OrderDetailDao orderDetailDao = new OrderDetailDao();
                orderDto.setOrderId(orderId);
                result = orderDetailDao.insertOrderDetail(orderDto, connection);
            }

        } finally {
            if (connection != null) {
                if (result) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
                connection.close();
            }
        }

        return orderId;
    }

    public OrderDto getOrderById(String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        OrderDto orderDto = null;
        try {
            connection = DatabaseHelper.getConnection();

            String sql = "SELECT "
                    + ORDER_USER_ADDRESS + ", " + ORDER_DATE + ", " + ORDER_TOTAL + " "
                    + "FROM [Order] "
                    + "WHERE " + ORDER_ID + "=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, orderId);

            OrderDetailDao orderDetailDao = new OrderDetailDao();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String userAddress = resultSet.getString(ORDER_USER_ADDRESS);
                    Timestamp dateTime = resultSet.getTimestamp(ORDER_DATE);
                    List<OrderDetailDto> itemList = orderDetailDao.getOrderDetailList(orderId, connection);;
                    float total = resultSet.getFloat(ORDER_TOTAL);

                    orderDto = new OrderDto(orderId, userAddress, dateTime, total, itemList);
                }
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return orderDto;
    }
}
