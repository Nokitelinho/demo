/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-2048
 *
 */
public class ServiceInUseException extends BusinessException {
	/**
     * Constant for Already parameter Exists exception
     */
	public static final String SERVICE_IN_USE = 
		"customermanagement.defaults.serviceinuse";
	/**
	 *
	 */
    public ServiceInUseException() {
		super(SERVICE_IN_USE);
	}
   /**
    * 
    * @param serviceinuse
    */
    public ServiceInUseException(String serviceinuse){
    	super(serviceinuse);
    }
/**
 * @param errorData
 */
    public ServiceInUseException(Object[] errorData) {
		super(SERVICE_IN_USE,errorData);
	}

}
