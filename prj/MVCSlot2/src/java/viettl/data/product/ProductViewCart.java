/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data.product;

import java.io.Serializable;

/**
 *
 * @author viett
 */
public class ProductViewCart implements Serializable {
    private String productName;
    private String productId;
    private int quantity;
    
    public ProductViewCart() {
        
    }

    public ProductViewCart(ProductDto product, Integer value) {
        productName = product.getName();
        productId = product.getProductId();
        quantity = value;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
