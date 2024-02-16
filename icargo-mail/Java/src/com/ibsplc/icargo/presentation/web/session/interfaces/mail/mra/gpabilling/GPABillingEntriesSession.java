/*
 * GPABillingEntriesSession.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
//import java.util.ArrayList;
//import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;

/**
 * @author A-1556
 *
 */
	public interface GPABillingEntriesSession extends ScreenSession {
	
		
		
	/**
	 * @param gpaBillingDetailsVOs
	 *//*
	public void setGpaBillingDetails(ArrayList<GPABillingDetailsVO> gpaBillingDetailsVOs);
	*//**
	 * @return
	 *//*
	public ArrayList<GPABillingDetailsVO> getGpaBillingDetails();
*/

	/**
	 * @param gpaBillingDetailsVOs
	 */
	public void setGpaBillingDetails(Page<DocumentBillingDetailsVO> gpaBillingDetailsVOs);
	/**
	 * @return
	 */
	public Page<DocumentBillingDetailsVO> getGpaBillingDetails();

	/**
	 * to remove gpabilling details
	 */
	public void removeGpaBillingDetails();

	/**
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
	 * @return HashMap<String, Collection<OneTimeVO>>
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	
	/**
	 * @return
	 */
	public String[] getSelectedRows();
	/**
	 * @param selectArray
	 */
	public void setSelectedRows(String[] selectArray);
	/**
	 *
	 */
	public void removeSelectedRows();
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeForPopup();

	/**
	 * @param oneTimeVOsForPopup
	 */
	public void setOneTimeForPopup(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeForPopup();
	
	/**
     * This method is used for PageAwareMultiMapper to get the Index Map
     * @return  HashMap<String,String>
     */
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
    
	//added by A-5175 for QF CR icrd-21098 starts
	public Integer getTotalRecords();
	
	public void setTotalRecords(int totalRecords);
	//added by A-5175 for QF CR icrd-21098 ends

	//added by A-7866 for CR ICRD-189046 starts
	public GPABillingEntriesFilterVO getGPABillingEntriesFilterVO();
	
	public void setGPABillingEntriesFilterVO(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO);
	//added by A-7866 for CR ICRD-189046 ends
	
	/**
	 * 
	 * @param gpaBillingDetailsVOs
	 */
	public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> gpaBillingDetailsVOs);
	
	/**
	 * 
	 * @return
	 */
	public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags();

	}
