/*
 * MailProrationSessionImpl.java Created on Mar 7, 2007 
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;


import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;


/**
 * @author Ruby Abraham
 * Session implementation for Mail Proration screen
 * 
 * Revision History     
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1		Mar 7, 2007     Ruby Abraham				Initial draft
 */
public class MailProrationSessionImpl extends AbstractScreenSession implements
										MailProrationSession{

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	
	/**
	 * Constant for the session variable memoFilterVO
	 */
	private static final String KEY_LISTFILTER = "prorationFilterVO";
	
	/**
	 * Constant for the session variable memoInInvoiceVOs
	 */
	private static final String KEY_PRORATIONVOS = "prorationVOs";
	
	
	/**
	 * Constant for the session variable category
	 */
	private static final String KEY_CATEGORY = "category";
	
	
	
	
	/**
	 * Constant for the session variable subClass
	 */
	private static final String KEY_SUBCLASS = "subClass";
	
	
	/**
	 * Constant for the session variable prorationType
	 */
	private static final String KEY_PRORATIONTYPE = "prorationType";
	
	
	/**
	 * Constant for the session variable prorationPayType
	 */
	private static final String KEY_PRORATIONPAYTYPE = "prorationPayType";
	
	/*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getScreenID()
     */
	/**
	 * @return screenID 
	 */
    public String getScreenID() {

        return SCREEN_ID;
    }

    /**
     * @return moduleName
     */
    /*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.framework.session.AbstractScreenSession#getModuleName()
     */
    public String getModuleName() {
        return MODULE_NAME;
    }
    
    
    
    /**
     * 
     * @return prorationFilterVO
     */
    
	public ProrationFilterVO getProrationFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}
	/**
	 * @param prorationFilterVO The  prorationFilterVO to set.
	 */
	public void setProrationFilterVO(ProrationFilterVO prorationFilterVO) {
		setAttribute(KEY_LISTFILTER,prorationFilterVO);
	}
	
	/**
	 * @author A-2122
	 */	
	
	public void removeProrationFilterVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_LISTFILTER);
	}
   
	/**
	 * @param prorationVOs 
	 */
	public void setProrationVOs(ArrayList<ProrationDetailsVO> prorationVOs){
		setAttribute(KEY_PRORATIONVOS,prorationVOs);
	}
	/**
	 * @return ArrayList<ProrationDetailsVO>
	 */
	public ArrayList<ProrationDetailsVO> getProrationVOs(){
		return getAttribute(KEY_PRORATIONVOS);
	}
	
	/**
	 * @author A-2122
	 *
	 */
	public void removeProrationVOs(){
		
		removeAttribute(KEY_PRORATIONVOS);
		
	}
	
	
	/**
	 * @return Returns the category
	 */
	public ArrayList<OneTimeVO> getCategory() {
		return getAttribute(KEY_CATEGORY);
	}
	
	/**
	 * @param category The  category to set.
	 */
	public void setCategory(ArrayList<OneTimeVO> category) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_CATEGORY,category);
		
	}
	
	
	/**
	 * @return Returns the subClass
	 */
	public ArrayList<OneTimeVO> getSubClass() {
		return getAttribute(KEY_SUBCLASS);
	}
	
	/**
	 * @param subClass The  subClass to set.
	 */
	public void setSubClass(ArrayList<OneTimeVO> subClass) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_SUBCLASS,subClass);
		
	}
	
	
	
	/**
	 * @return Returns the prorationType
	 */
	public ArrayList<OneTimeVO> getProrationType() {
		return getAttribute(KEY_PRORATIONTYPE);
	}
	
	/**
	 * @param prorationType The  prorationType to set.
	 */
	public void setProrationType(ArrayList<OneTimeVO> prorationType) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_PRORATIONTYPE,prorationType);
		
	}

	/**
	 * @return Returns the prorationPayType
	 */
	public ArrayList<OneTimeVO> getProrationPayType() {	
		return getAttribute(KEY_PRORATIONPAYTYPE);
	}
	
	/**
	 * @param prorationPayType The  prorationPayType to set.
	 */
	public void setProrationPayType(ArrayList<OneTimeVO> prorationPayType) {
		
		// TODO Auto-generated method stub
		setAttribute(KEY_PRORATIONPAYTYPE,prorationPayType);
		
	}

	
	
				
}
