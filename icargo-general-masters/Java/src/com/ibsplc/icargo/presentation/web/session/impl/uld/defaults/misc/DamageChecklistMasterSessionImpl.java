/*
 * DamageChecklistMasterSessionImpl.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageChecklistMasterSession;
/**
 * @author A-3459
 *
 */
/**
 * @author A-3459
 *
 */
/**
 * @author A-3459
 *
 */
public class DamageChecklistMasterSessionImpl extends AbstractScreenSession
				implements DamageChecklistMasterSession{
	

private static final String KEY_SECTION = "oneTimeValues";
private static final String KEY_MODULE_NAME = "uld.defaults";
private static final String KEY_SCREEN_ID = "uld.defaults.damagechecklistmaster";
private static final String KEY_ULDDMGCHKLST = "ULDDamageChecklistVO";

@Override
/**
 * This method returns the MODULE name for the IAW Message screen
 */
public String getModuleName() {
	return KEY_MODULE_NAME;
}

@Override
/**
 * This method returns the SCREEN ID for the IAW Message screen
 */
public String getScreenID() {
	 return KEY_SCREEN_ID;
}
/**
 * for getting the MessageRuleDefenitionVO from the session
 * @return MessageRuleDefenitionVO
 */
public Collection<OneTimeVO> getSection(){
    return (Collection<OneTimeVO>)getAttribute(KEY_SECTION);
}

/**
 * This method is used to set the ULDDamageChecklistVO in session
 * @param section
 * 
 */
public void setSection(Collection<OneTimeVO> section) {
    setAttribute(KEY_SECTION,(ArrayList<OneTimeVO>)section);
}

/**
 * 
 * @return
 */
public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO(){
   return (Collection<ULDDamageChecklistVO>) 
   		getAttribute(KEY_ULDDMGCHKLST);
}
/**
 * @param uldDamageChecklistVO
 *            The ULDDamageChecklistVO to set.
 */
public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> uldDamageChecklistVO){
   setAttribute(
		   KEY_ULDDMGCHKLST, (ArrayList<ULDDamageChecklistVO>)uldDamageChecklistVO);
}
/**
 * This method removes the ULDDamageChecklistVO in session
 */

public void removeULDDamageChecklistVO() {
	// To be reviewed Auto-generated method stub
	removeAttribute(KEY_ULDDMGCHKLST);
}



}
