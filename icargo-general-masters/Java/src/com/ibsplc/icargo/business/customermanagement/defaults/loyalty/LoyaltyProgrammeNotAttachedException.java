/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-1883
 *
 */
public class LoyaltyProgrammeNotAttachedException extends BusinessException{

	/**
     * Constant for Loyalty Programme Not Attached 
     */
	public static final String LOYALTY_PROGRAMME_NOT_ATTACHED = 
		"customermanagement.defaults.loyaltyProgrammeNotAttached";
	/**
	 *
	 */
    public LoyaltyProgrammeNotAttachedException() {
		super(LOYALTY_PROGRAMME_NOT_ATTACHED);
	}
    /**
     * @param loyaltyProgrammeNotAttached
     */
    public LoyaltyProgrammeNotAttachedException(String loyaltyProgrammeNotAttached){
    	super(loyaltyProgrammeNotAttached);
    }
/**
 * @param errorData
 */
    public LoyaltyProgrammeNotAttachedException(Object[] errorData) {
		super(LOYALTY_PROGRAMME_NOT_ATTACHED,errorData);
	}

}
