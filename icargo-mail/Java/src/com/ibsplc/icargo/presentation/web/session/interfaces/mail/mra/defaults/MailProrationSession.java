/*
 * MailProrationSession.java Created on Mar 7,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author Ruby Abraham
 * Session interface for Mail Proration Screen.
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		Mar 7, 2007    Ruby Abraham				Initial draft
 */
public interface MailProrationSession extends ScreenSession {
	
	
	 /**
     * 
     * @return prorationFilterVO
     */
    
	public ProrationFilterVO getProrationFilterVO();
	
	/**
	 * @param prorationFilterVO The  prorationFilterVO to set.
	 */
	public void setProrationFilterVO(ProrationFilterVO prorationFilterVO);
	
	/**
	 * @author A-2122
	 */	
	
	public void removeProrationFilterVO();
	
	
	
	/**
	 * @param prorationVOs 
	 */
	public void setProrationVOs(ArrayList<ProrationDetailsVO> prorationVOs);
	
	
	/**
	 * @return ArrayList<ProrationDetailsVO>
	 */
	public ArrayList<ProrationDetailsVO> getProrationVOs();
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeProrationVOs();
		
	
	
	
	/**
	 * @return Returns the category
	 */
	public ArrayList<OneTimeVO> getCategory();
	
	
	/**
	 * @param category The  category to set.
	 */
	public void setCategory(ArrayList<OneTimeVO> category);
	
	
	
	/**
	 * @return Returns the subClass
	 */
	public ArrayList<OneTimeVO> getSubClass();
	
	
	
	/**
	 * @param subClass The  subClass to set.
	 */
	public void setSubClass(ArrayList<OneTimeVO> subClass);
	
	
	/**
	 * @return Returns the prorationType
	 */
	public ArrayList<OneTimeVO> getProrationType();
	
	/**
	 * @param prorationType The  prorationType to set.
	 */
	public void setProrationType(ArrayList<OneTimeVO> prorationType);
	
	
	/**
	 * @return Returns the prorationPayType
	 */
	public ArrayList<OneTimeVO> getProrationPayType();
	
	/**
	 * @param prorationPayType The  prorationPayType to set.
	 */
	public void setProrationPayType(ArrayList<OneTimeVO> prorationPayType);
}

