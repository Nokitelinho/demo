/*
 * ReturnDsnSessionImpl.java Created on July 21, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;

/**
 * @author A-1861
 *
 */
public class ReturnDsnSessionImpl extends AbstractScreenSession
        implements ReturnDsnSession {

	private static final String SCREEN_ID = "mailtracking.defaults.returndsn";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_ONETIMEDAMAGECODE = "oneTimeDamageCode";
	private static final String POSTALADMINVOS = "PostalAdministrationVOs";
	private static final String DAMAGEDDSNVOS = "dsnvos";
	private static final String KEY_WEIGHTROUNDINGVO = "rounding_wt_vo";
	/**
     * This method returns the onetime vos
     * @return ONETIME_OneTimeDamageCode - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOneTimeDamageCodes() {
		return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEDAMAGECODE);
	}
	
	/**
     * This method is used to set onetime values to the session
     * @param oneTimeDamageCode - Collection<OneTimeVO>
     */
	public void setOneTimeDamageCodes(Collection<OneTimeVO> oneTimeDamageCode) {
		setAttribute(KEY_ONETIMEDAMAGECODE,(ArrayList<OneTimeVO>)oneTimeDamageCode);
	}
		
	/**
     * This method returns the PostalAdministrationVOS
     * @return POSTALADMINVOS - Collection<PostalAdministrationVO>
     */
	public Collection<PostalAdministrationVO> getPostalAdministrationVOs() {
		return (Collection<PostalAdministrationVO>)getAttribute(POSTALADMINVOS);
	}
	
	/**
     * This method is used to set PostalAdministrationVOs to the session
     * @param postalAdministrationVOs - Collection<PostalAdministrationVO>
     */
	public void setPostalAdministrationVOs(Collection<PostalAdministrationVO> postalAdministrationVOs) {
		setAttribute(POSTALADMINVOS,(ArrayList<PostalAdministrationVO>)postalAdministrationVOs);
	}
	
	/**
	 * This method is used to set Collection<DamagedDSNVO> to the session
	 * @param damagedDsnVOs - Collection<DamagedDSNVO>
	 */
	public void setDamagedDsnVOs(Collection<DamagedDSNVO> damagedDsnVOs) {
		setAttribute(DAMAGEDDSNVOS,(ArrayList<DamagedDSNVO>)damagedDsnVOs);
	}

	/**
	 * This method returns the Collection<DamagedDSNVO>
	 * @return DAMAGEDDSNVOS - Collection<DamagedDSNVO>
	 */
	public Collection<DamagedDSNVO> getDamagedDsnVOs() {
		return (Collection<DamagedDSNVO>)getAttribute(DAMAGEDDSNVOS);
	}
		
    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	 /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO) {
    	setAttribute(KEY_WEIGHTROUNDINGVO, unitRoundingVO);
    }

    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() {
    	return getAttribute(KEY_WEIGHTROUNDINGVO);
    }

    /**
     * @param key String
     */
    public void removeWeightRoundingVO(String key) {
    }    
	
	

}
