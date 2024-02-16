/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1883
 *
 */
public class CustomersAlreadyAttatchedException extends BusinessException{

	/**
     * Constant for Already Customers Attatched exception
     */
	public static final String CUSTOMERS_ALREADY_ATTACHED = 
		"customermanagement.defaults.customersAlreadyAttached";
	/**
	 *
	 */
    public CustomersAlreadyAttatchedException() {
		super(CUSTOMERS_ALREADY_ATTACHED);
	}
    /**
     * @param customersAlreadyAttached
     */
    public CustomersAlreadyAttatchedException(String customersAlreadyAttached){
    	super(customersAlreadyAttached);
    }
/**
 * @param errorData
 */
    public CustomersAlreadyAttatchedException(Object[] errorData) {
		super(CUSTOMERS_ALREADY_ATTACHED,errorData);
	}

}
