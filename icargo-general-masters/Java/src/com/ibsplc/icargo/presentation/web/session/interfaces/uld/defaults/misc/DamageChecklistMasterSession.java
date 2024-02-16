/*
 * DamageChecklistMasterSession.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author A-3459
 *
 */
public interface DamageChecklistMasterSession extends ScreenSession{
	/**
     * 
     * @return Collection
     */
	public Collection<OneTimeVO> getSection();
	/**
	 * 
	 * @param section
	 */
    public void setSection(Collection<OneTimeVO> section);
    /**
     * 
     * @return
     */
    /**
     * @return
     */
   public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO();
   /**
    * 
    * @param uldRepairVOs
    */
   public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> uldDamageChecklistVO);
   
   
   /**
 * @return sxcreen id
 */
public String getScreenID();
   
   
   /**
 * @return modulename
 */
public String getModuleName();

/**
 * remove details
 */
public void removeULDDamageChecklistVO(); 
}
