/*
 * ListInterlineBillingEntriesSessionImpl.java Created on Aug 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3434
 *
 */
public class ListInterlineBillingEntriesSessionImpl extends AbstractScreenSession implements
ListInterlineBillingEntriesSession {

	/**
	 * KEY for MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * KEY for SCREEN_ID in session
	 */
	public static final String KEY_SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	/**
	 * @author a-3434 Key for getting one time values
	 *
	 */

	private static final String KEY_ONETIME_CODES_MAP = "onetimecodesmap";
	/**
	 * @author a-3434 Key for getting one time values
	 *
	 */

	private static final String KEY_ONETIME_CODES_POPUP = "onetimecodesforpopup";
	/**
	 * KEY for setting billingStatus in session
	 */
	public static final String KEY_BILLINGSTATUS = "billingStatus";

	/**
	 * KEY for setting billingStatus Keys in session
	 */
	public static final String KEY_BILLINGSTATUSKEYS = "billingStatusKeys";
	/**
	 * KEY for setting BillingStatusVOs in session
	 */
	public static final String KEY_BILLINGSTATUSVOS="billingStatusVo";
	/**
	 * KEY for setting DocBillingDetailsVOs in session
	 */
	public static final String KEY_DOCBLGDETAILSVO = "documentBillingDetailsVO";
	/**
	 * KEY for setting AirlineBillingFilterVO in session
	 */
	public static final String KEY_AIRLINEBLGFILTERVO = "airlineBillingDetailsVO";
	private static final String KEY_SELECT_ROW="selectedrow";
	private static final String KEY_DOCBLGDETAILSVOS="documentBillingDetailsVOs";
	private static final String FROM_SCREEN = "fromScreen";
	private static final String CLOSE_FLAG="closeFlag";
	private static final String TO_SCREEN = "toScreen";
	/**
	 *
	 */
	 public static final String KEY_INDEXMAP = "indexMap";

	//added by A-5223 for ICRD-20507 starts
		private static final String  TOTALRECORDS = "totalRecords";
		//added by A-5223 for ICRD-20507 ends

	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";

	private static final String KEY_SELECTED_VOID_MAILS = "selectedairlinevoidmailbags";
	public  ListInterlineBillingEntriesSessionImpl() {
		super();

	}
	/**
	 * This method returns the MODULE name for the ListGPABillingInvoice screen
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME;
	}

	/**
	 * This method returns the SCREEN ID for the ListGPABillingInvoice screen
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID;
	}


	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_MAP);
	}

	/**
	 * Method to set the Onetimes map to session
	 *
	 * @param oneTimeMap
	 * The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap) {
		setAttribute(KEY_ONETIME_CODES_MAP,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeMap);
	}

	/**
	 * Method to remove One Time Map from session
	 */
	public void removeOneTimeMap() {
		removeAttribute(KEY_ONETIME_CODES_MAP);

	}
	/**
	 * @return BillingStatus
	 */
	public Collection<OneTimeVO> getBillingStatus() {
		return (Collection<OneTimeVO>) getAttribute(KEY_BILLINGSTATUS);
	}

	/**
	 * @param BillingStatus
	 */
	public void setBillingStatus(Collection<OneTimeVO> billingStatus) {
		setAttribute(KEY_BILLINGSTATUS, (ArrayList<OneTimeVO>) billingStatus);
	}

	/**
	 * @return BillingStatusKeys
	 */
	public Collection<String> getBillingStatusKeys() {
		return (Collection<String>) getAttribute(KEY_BILLINGSTATUS);
	}

	/**
	 * @param BillingStatusKeys
	 */
	public void setBillingStatusKeys(Collection<String> billingStatusKeys) {
		setAttribute(KEY_BILLINGSTATUSKEYS, (ArrayList<String>)billingStatusKeys);
	}

	/**
	 * @return BillingStatusVOs
	 */
	public HashMap<String, String> getBillingStatusVOs() {
		return (HashMap<String, String>) getAttribute(KEY_BILLINGSTATUSVOS);
	}

	/**
	 * @param BillingStatusVOs
	 */
	public void setBillingStatusVOs(HashMap<String, String> billingStatusVOs) {
		setAttribute(KEY_BILLINGSTATUSVOS,
				(HashMap<String, String>) billingStatusVOs);
	}

	/**
	 * @return Returns the documentBillingDetailsVO.
	 */
    public DocumentBillingDetailsVO getDocumentBillingDetailsVO(){
    	return ((DocumentBillingDetailsVO) getAttribute(KEY_DOCBLGDETAILSVO));
    }
    /**
	 * @param documentBillingDetailsVO
	 */
    public void setDocumentBillingDetailsVO(DocumentBillingDetailsVO documentBillingDetailsVO){
    	setAttribute(KEY_DOCBLGDETAILSVO,
    			(DocumentBillingDetailsVO)documentBillingDetailsVO);
    }
    /**
	 * remove documentBillingDetailsVO
	 */
    public void removeDocumentBillingDetailsVO() {
		removeAttribute(KEY_DOCBLGDETAILSVO);

	}

    /**
	 * @return Returns the airlineBillingFilterVO
	 */
    public AirlineBillingFilterVO getAirlineBillingFilterVO(){
    	return ((AirlineBillingFilterVO) getAttribute(KEY_AIRLINEBLGFILTERVO));
    }
    /**
	 * @param airlineBillingFilterVO
	 */
    public void setAirlineBillingFilterVO(AirlineBillingFilterVO airlineBillingFilterVO){
    	setAttribute(KEY_AIRLINEBLGFILTERVO,
    			(AirlineBillingFilterVO)airlineBillingFilterVO);
    }
    /**
	 * remove airlineBillingFilterVO
	 */
    public void removeAirlineBillingFilterVO() {
		removeAttribute(KEY_AIRLINEBLGFILTERVO);

	}
    /**

    *

    * @return SelectedRows

    */
    public String getSelectedRow(){
    	return getAttribute(KEY_SELECT_ROW);
    }
    /**

    *

    * @param selectArray

    */
    public void setSelectedRow(String selectArray){
    	setAttribute(KEY_SELECT_ROW,selectArray);
    }
    /**

    *

    *remove SelectedRows

    */
    public void removeSelectedRow(){
    	removeAttribute(KEY_SELECT_ROW);
    }
    public Page<DocumentBillingDetailsVO> getDocumentBillingDetailVOs() {
		//		 TODO Auto-generated method stub
		return (Page<DocumentBillingDetailsVO>)this.getAttribute(KEY_DOCBLGDETAILSVOS);
	}

	public void setDocumentBillingDetailVOs(Page<DocumentBillingDetailsVO> documentBillingDetailVOs) {
		this.setAttribute(KEY_DOCBLGDETAILSVOS,(Page<DocumentBillingDetailsVO>)documentBillingDetailVOs);
	}
	 public void removeDocumentBillingDetailVOs(){
	    	removeAttribute(KEY_DOCBLGDETAILSVOS);
	    }
	 /**
		 * @return String
		 */
	public String getFromScreen(){
		return getAttribute(FROM_SCREEN);
	}

		/**
		 * @param fromscreen
		 */
	public void setFromScreen(String fromscreen){
		setAttribute(FROM_SCREEN, fromscreen);
	}
	 /**
	 * @return String
	 */
	public String getToScreen(){
	return getAttribute(TO_SCREEN);
	}

	/**
	 * @param toScreen
	 */
	public void setToScreen(String toscreen){
	setAttribute(TO_SCREEN, toscreen);
	}
	 /**
	 * @return String
	 */
	public String getCloseFlag(){
	return getAttribute(CLOSE_FLAG);
	}

	/**
	 * @param closeFlag
	 */
	public void setCloseFlag(String closeFlag){
	setAttribute(CLOSE_FLAG, closeFlag);
	}
	public HashMap<String, Collection<OneTimeVO>> getOneTimeForPopup() {
		return (HashMap<String, Collection<OneTimeVO>>) getAttribute(KEY_ONETIME_CODES_POPUP);
	}

	/**
	 * Method to set the Onetimes map to session
	 *
	 * @param oneTimeMap
	 * The one time map to be set to session
	 */
	public void setOneTimeForPopup(HashMap<String, Collection<OneTimeVO>> oneTimeMapForPopup) {
		setAttribute(KEY_ONETIME_CODES_POPUP,
				(HashMap<String, Collection<OneTimeVO>>) oneTimeMapForPopup);
	}

	public HashMap<String,String>getIndexMap(){
   	 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
   }

   /**Sets the hashmap for Absolute index of page
    * @param indexMap
    */
   public void setIndexMap(HashMap<String,String>indexMap){
   	 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
   }
   /**
    * Removes the hashmap for Absolute index of page
    *
    */
   public void removeIndexMap(){
   	removeAttribute(KEY_INDEXMAP);
   }
   /**
    *
    *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession#getTotalRecords()
    *	Added by 			: a-5223 on 16-Oct-2012
    * 	Used for 	: getting total records
    *	Parameters	:	@return
    */
    @Override
    public Integer getTotalRecords() {
    	return getAttribute(TOTALRECORDS);
    }
/**
 *
 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession#setTotalRecords(int)
 *	Added by 			: a-5223 on 16-Oct-2012
 * 	Used for 	: setting total records
 *	Parameters	:	@param totalRecords
 */
    @Override
    public void setTotalRecords(int totalRecords) {
    	setAttribute(TOTALRECORDS,totalRecords);

    }

    public HashMap<String, String> getSystemparametres(){
		return getAttribute(KEY_SYSPARAMETERS);
	}
	public void setSystemparametres(HashMap<String, String> sysparameters){
		setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession#setSelectedVoidMailbags(java.util.Collection)
	 *	Added by 			: A-5219 on 16-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param VOs
	 */
	public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> VOs){
		setAttribute(KEY_SELECTED_VOID_MAILS,(ArrayList<DocumentBillingDetailsVO>)VOs);
	}
	/**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession#getSelectedVoidMailbags()
	 *	Added by 			: A-5219 on 16-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags() {
		return (Collection<DocumentBillingDetailsVO>)getAttribute(KEY_SELECTED_VOID_MAILS);
	}


}