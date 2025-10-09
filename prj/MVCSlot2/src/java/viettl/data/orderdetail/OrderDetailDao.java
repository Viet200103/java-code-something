package viettl.data.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import viettl.data.order.OrderDto;
import viettl.data.product.ProductDao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author viett
 */
public class OrderDetailDao implements Serializable {

    private final String ODETAIL_ID = "orderDetailID";
    private final String ODETAIL_PRODUCT_ID = "productID";
    private final String ODETAIL_UNIT_PRICE = "unitPrice";
    private final String ODETAIL_QUANTITY = "quantity";
    private final String ODETAIL_ORDER_ID = "orderID";
    private final String ODRTAIL_TOTAL = "total";

    public boolean insertOrderDetail(
            OrderDto orderDto, Connection connection
    ) throws SQLException, ClassNotFoundException {
        boolean result = false;

        String sql = "INSERT INTO [OrderDetail]("
                + ODETAIL_PRODUCT_ID + ", "
                + ODETAIL_ORDER_ID + ", "
                + ODETAIL_UNIT_PRICE + ", "
                + ODETAIL_QUANTITY + ", "
                + ODRTAIL_TOTAL
                + ") VALUES ("
                + "?, ?, ?, ?, ?"
                + ")";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        List<OrderDetailDto> itemList = orderDto.getItemList();
        for (OrderDetailDto item : itemList) {
            preparedStatement.setString(1, item.getProductId());
            preparedStatement.setString(2, orderDto.getOrderId());
            preparedStatement.setFloat(3, item.getUnitPrice());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.setFloat(5, item.getTotal());

            preparedStatement.addBatch();
        }

        int[] resultExcute = preparedStatement.executeBatch();
        if (resultExcute.length == itemList.size()) {
            result = true;
        }

        return result;
    }

    public List<OrderDetailDto> getOrderDetailList(String orderId, Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDto> itemList = new ArrayList();

        String sql = "SELECT "
                + ODETAIL_PRODUCT_ID + ", "
                + ODETAIL_UNIT_PRICE + ", "
                + ODETAIL_QUANTITY + ", "
                + ODRTAIL_TOTAL + " "
                + "FROM [OrderDetail] "
                + "WHERE " + ODETAIL_ORDER_ID + "=? ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, orderId);
        
        ProductDao productDao = new ProductDao();
        
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String productId = resultSet.getString(ODETAIL_PRODUCT_ID);
                float unitPrice = resultSet.getFloat(ODETAIL_UNIT_PRICE);
                int quantity = resultSet.getInt(ODETAIL_QUANTITY);
                float total = resultSet.getFloat(ODRTAIL_TOTAL);
                
                String productName = productDao.getProductName(productId, connection);

                OrderDetailDto orderDetailDto = new OrderDetailDto(
                        productId, orderId, productName, unitPrice, quantity, total
                );
                itemList.add(orderDetailDto);
            }
        }

        return itemList;
    }

}
