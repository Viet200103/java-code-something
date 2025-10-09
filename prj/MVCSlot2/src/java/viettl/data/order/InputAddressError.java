/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettl.data.order;

import java.io.Serializable;

/**
 *
 * @author viett
 */
public class InputAddressError implements Serializable {
    
    private String addressLengthError;
    
    public InputAddressError() {
        
    }

    public String getAddressLengthError() {
        return addressLengthError;
    }

    public void setAddressLengthError(String addressLengthError) {
        this.addressLengthError = addressLengthError;
    }
}
