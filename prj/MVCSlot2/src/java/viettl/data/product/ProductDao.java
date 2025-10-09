/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import viettl.data.DatabaseHelper;

/**
 *
 * @author viett
 */
public class ProductDao implements Serializable {

    private final String TABLE_NAME = "Product";
    private final String PRODUCT_ID = "productID";
    private final String PRODUCT_NAME = "name";
    private final String PRODUCT_DESCIPTION = "description";
    private final String PRODUCT_UNIT_PRICE = "unitPrice";
    private final String PRODUCT_QUANNTITY = "quantity";
    private final String PRODUCT_STATE = "state";

    public List<ProductDto> getProductList() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        List<ProductDto> productList = new ArrayList<>();

        try {
            connection = DatabaseHelper.getConnection();
            if (connection != null) {
                String column = createProductColumn();

                String sql = "SELECT " + column
                        + "FROM " + TABLE_NAME;

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    productList.add(mapProduct(resultSet));
                }
            }
        } finally {

            if (connection != null) {
                connection.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }

        return productList;
    }

    private ProductDto mapProduct(ResultSet resultSet) throws SQLException {
        String productID = resultSet.getString(PRODUCT_ID);
        String name = resultSet.getString(PRODUCT_NAME);
        String description = resultSet.getString(PRODUCT_DESCIPTION);
        float unitPrice = resultSet.getFloat(PRODUCT_UNIT_PRICE);
        int quantity = resultSet.getInt(PRODUCT_QUANNTITY);
        boolean state = resultSet.getBoolean(PRODUCT_STATE);

        return new ProductDto(
                productID, name, description, unitPrice, quantity, state
        );
    }

    private String createProductColumn() {
        return String.format(
                "%s, %s, %s, %s, %s, %s ",
                PRODUCT_ID,
                PRODUCT_NAME,
                PRODUCT_DESCIPTION,
                PRODUCT_UNIT_PRICE,
                PRODUCT_QUANNTITY,
                PRODUCT_STATE
        );
    }

    public ProductDto getProductById(String productId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        ResultSet resultSet = null;
        ProductDto product = null;

        try {
            connection = DatabaseHelper.getConnection();

            if (connection != null) {
                String sql = "SELECT " + createProductColumn()
                        + " FROM " + TABLE_NAME
                        + " WHERE " + PRODUCT_ID + " = ? ";

                PreparedStatement preStatement = connection.prepareStatement(sql);
                preStatement.setString(1, productId);

                resultSet = preStatement.executeQuery();
                if (resultSet.next()) {
                    product = mapProduct(resultSet);
                }
            }

        } finally {

            if (connection != null) {
                connection.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }

        return product;
    }

    public String getProductName(String productId, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = null;
        String productName = null;

        try {
            String sql = "SELECT " + PRODUCT_NAME
                    + " FROM " + TABLE_NAME
                    + " WHERE " + PRODUCT_ID + " = ? ";

            PreparedStatement preStatement = connection.prepareStatement(sql);
            preStatement.setString(1, productId);

            resultSet = preStatement.executeQuery();
            if (resultSet.next()) {
                productName = resultSet.getString(PRODUCT_NAME);
            }

        } finally {

            if (resultSet != null) {
                resultSet.close();
            }
        }

        return productName;
    }
}
