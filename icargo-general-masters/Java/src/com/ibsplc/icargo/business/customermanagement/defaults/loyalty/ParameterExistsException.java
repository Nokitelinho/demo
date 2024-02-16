/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author A-1883
 *
 */
public class ParameterExistsException extends BusinessException {
	/**
     * Constant for Already parameter Exists exception
     */
	public static final String PARAMETER_ALREADY_EXISTS = 
		"customermanagement.defaults.parameterAlreadyExists";
	/**
	 *
	 */
    public ParameterExistsException() {
		super(PARAMETER_ALREADY_EXISTS);
	}
    /**
     * @param parameterAlreadyExists
     */
    public ParameterExistsException(String parameterAlreadyExists){
    	super(parameterAlreadyExists);
    }
/**
 * @param errorData
 */
    public ParameterExistsException(Object[] errorData) {
		super(PARAMETER_ALREADY_EXISTS,errorData);
	}

}
