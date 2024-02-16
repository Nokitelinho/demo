/*
 * ListInterlineBillingEntriesSession.java Created on Aug 7, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3434
 *
 */
public  interface ListInterlineBillingEntriesSession extends ScreenSession{
	/**
	 *   Method to get the onetime map in the
	 *         session
	 * @return HashMap the onetime map from session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

	/**
	 *  Method to set the Onetimes map to session
	 * @param oneTimeMap
	 *            The one time map to be set to session
	 */
	public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

	/**
	 *  Method to remove One Time Map from
	 *         session
	 */
	public void removeOneTimeMap();


	/**
	 * @param BillingStatus
	 */
	public void setBillingStatus(Collection<OneTimeVO> billingStatus);
	/**
	 * @param BillingStatus
	 */
	public Collection<OneTimeVO> getBillingStatus();
	/**
	 * @return BillingStatusKeys
	 */
	public Collection<String> getBillingStatusKeys();

	/**
	 * @param BillingStatusKeys
	 */
	public void setBillingStatusKeys(Collection<String> billingStatusKeys);
	/**
	 * @return BillingStatusVOs
	 */
	public HashMap<String, String> getBillingStatusVOs();

	/**
	 * @param BillingStatusVOs
	 */
	public void setBillingStatusVOs(HashMap<String, String> billingStatusVOs);
	/**
	 * @return documentBillingDetailsVO
	 */
	public DocumentBillingDetailsVO getDocumentBillingDetailsVO();

	/**
	 * @param documentBillingDetailsVO
	 */
	 public void setDocumentBillingDetailsVO(DocumentBillingDetailsVO documentBillingDetailsVO);
	 /**
	  *  remove documentBillingDetailsVO
	*/
	 public void removeDocumentBillingDetailsVO();
	 /**
		 * @return airlineBillingFilterVO
		 */
	public AirlineBillingFilterVO getAirlineBillingFilterVO();

		/**
		 * @param airlineBillingFilterVO
		 */
	public void setAirlineBillingFilterVO(AirlineBillingFilterVO airlineBillingFilterVO);
		 /**
		  *  remove airlineBillingFilterVO
		*/
	public void removeAirlineBillingFilterVO();
	/**
	 * @return SelectedRows
	 */
	public String getSelectedRow();
	/**
	 * @param selectArray
	 */
	public void setSelectedRow(String selectArray);
	/**
	 * remove SelectedRows
	 */
	public void removeSelectedRow();
	/**
	 *
	 * @return
	 */
	public Page<DocumentBillingDetailsVO> getDocumentBillingDetailVOs();

	/**
	 *
	 * @param Collection<DocumentBillingDetailVO>
	 */
	public void setDocumentBillingDetailVOs(Page<DocumentBillingDetailsVO> documentBillingDetailVOs);

	/**
	 * remove DocumentBillingDetailVOs
	 */
	public void removeDocumentBillingDetailVOs();
	 /**
	 *
	 * @return String fromScreen
	 */
	public String getFromScreen();

	/**
	 *
	 * @param fromScreen
	 */
	public void setFromScreen(String fromScreen);
	 /**
	 *
	 * @return String toScreen
	 */
	public String getToScreen();

	/**
	 *
	 * @param toScreen
	 */
	public void setToScreen(String toScreen);
	/**
	 *
	 * @return String closeFlag
	 */
	public String getCloseFlag();

	/**
	 *
	 * @param closeFlag
	 */
	public void setCloseFlag(String closeFlag);

	/**
	 *   Method to get the onetime map in the
	 *         session
	 * @return HashMap the onetime map from session
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeForPopup();

	/**
	 *  Method to set the Onetimes map to session
	 * @param oneTimeMap
	 *            The one time map to be set to session
	 */
	public void setOneTimeForPopup(HashMap<String, Collection<OneTimeVO>> oneTimeMapForPopup);


	 public HashMap<String,String>getIndexMap();

	    /**Sets the hashmap for Absolute index of page
	     * @param indexMap
	     */
	    public void setIndexMap(HashMap<String,String>indexMap);

	    /**
	     * Removes the hashmap for Absolute index of page
	     *
	     */
	    public void removeIndexMap();

	  //added by A-5223 for ICRD-21098 starts
		public Integer getTotalRecords();
		public void setTotalRecords(int totalRecords);
		//added by A-5223 for ICRD-21098 ends
		/**
		 * @author A-7540
		 * @return HashMap<String, String>
		 */
		public HashMap<String, String> getSystemparametres();
		/**
		 *
		 * @param sysparameters sysparameters
		 */
		public void setSystemparametres(HashMap<String, String> sysparameters);

		/**
		 *
		 * 	Method		:	ListInterlineBillingEntriesSession.setSelectedVoidMailbags
		 *	Added by 	:	A-5219 on 16-Oct-2019
		 * 	Used for 	:
		 *	Parameters	:	@param gpaBillingDetailsVOs
		 *	Return type	: 	void
		 */
		public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> gpaBillingDetailsVOs);
		/**
		 *
		 * 	Method		:	ListInterlineBillingEntriesSession.getSelectedVoidMailbags
		 *	Added by 	:	A-5219 on 16-Oct-2019
		 * 	Used for 	:
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<DocumentBillingDetailsVO>
		 */
		public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags();
}
