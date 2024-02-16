/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;

/**
 * @author a-1883
 *
 */
public class LoyaltyProgrammeExpiredException extends BusinessException {
	/**
     * Constant for Loyalty Programme Expired
     */
	public static final String LOYALTY_PROGRAMME_EXPIRED = 
		"customermanagement.defaults.loyaltyProgrammeExpired";
	/**
	 *
	 */
    public LoyaltyProgrammeExpiredException() {
		super(LOYALTY_PROGRAMME_EXPIRED);
	}
    /**
     * @param loyaltyProgrammeExpired
     */
    public LoyaltyProgrammeExpiredException(String loyaltyProgrammeExpired){
    	super(loyaltyProgrammeExpired);
    }
/**
 * @param errorData
 */
    public LoyaltyProgrammeExpiredException(Object[] errorData) {
		super(LOYALTY_PROGRAMME_EXPIRED,errorData);
	}

}
