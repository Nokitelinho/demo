/*
 * GPABillingEntriesSessionImpl.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
//import java.util.ArrayList;
//import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;


/**
 * @author A-1556
 *
 */
/**
 * @author A-2408
 *
 */
/**
 * @author A-2408
 *
 */
public class GPABillingEntriesSessionImpl extends AbstractScreenSession implements GPABillingEntriesSession {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";

	private static final String KEY_GPABILLINGDETAILS="docbillingdetailsvos";
	
	//private static final String KEY_GPABILLINGDETAILS="gpabillingdetailsvo";


	private static final String KEY_ONETIME_VOS="onetimevalues";
	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";

	private static final String KEY_SELECT_ROWS="selectedrows";
	
	private static final String KEY_ONETIME_VOS_POPUP="onetimevaluesforpopup";
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
	 /**
     * indexMap
     */
    public static final String KEY_INDEXMAP = "indexMap";
    
    private static final String KEY_TOTALRECORDS = "totalrecords";
    
    private static final String KEY_GPABILLINGENTRIESFILTERVO = "gpabillingentriesfiltervo";
    
    private static final String KEY_SELECTED_VOID_MAILS = "selectedvoidmailbags";
	
    /**
     *
     */
    public GPABillingEntriesSessionImpl() {
        super();

    }
    /**
     * @return
     */
    @Override
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

   /* 

    * @param gpaBillingDetailsVOs

    
    public void setGpaBillingDetails(ArrayList<GPABillingDetailsVO> gpaBillingDetailsVOs){
    	setAttribute(KEY_GPABILLINGDETAILS,(ArrayList<GPABillingDetailsVO>)gpaBillingDetailsVOs);
    }
    *//**

    *

    * @return

    *//*
    public ArrayList<GPABillingDetailsVO> getGpaBillingDetails(){
    	return (ArrayList<GPABillingDetailsVO>)getAttribute(KEY_GPABILLINGDETAILS);
    }*/

    /**

    *

    * @param gpaBillingDetailsVOs

    */
    public void setGpaBillingDetails(Page<DocumentBillingDetailsVO> gpaBillingDetailsVOs){
    	setAttribute(KEY_GPABILLINGDETAILS,(Page<DocumentBillingDetailsVO>)gpaBillingDetailsVOs);
    }
    /**

    *

    * @return

    */
    public Page<DocumentBillingDetailsVO> getGpaBillingDetails(){
    	return (Page<DocumentBillingDetailsVO>)getAttribute(KEY_GPABILLINGDETAILS);
    }
    /**

    *

    *remove gpabillingdetails

    */
    public void removeGpaBillingDetails(){
    	removeAttribute(KEY_GPABILLINGDETAILS);
    }
    /**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
    /**

     *

     * @return

     */
	public HashMap<String, String> getSystemparametres()
	{
		return getAttribute(KEY_SYSPARAMETERS);
	}
	public void setSystemparametres(HashMap<String, String> sysparameters)
	{
		setAttribute(KEY_SYSPARAMETERS, sysparameters);
	}

     public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

     return getAttribute(KEY_ONETIME_VOS);

     }
     /**

     *

     * @param oneTimeVOs

     */

     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

     setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

     }
     /**

     *

     *remove onetimes

     */

     public void removeOneTimeVOs() {

     removeAttribute(KEY_ONETIME_VOS);

     }
     /**

     *

     * @return

     */
     public String[] getSelectedRows(){
     	return getAttribute(KEY_SELECT_ROWS);
     }
     /**

     *

     * @param selectArray

     */
     public void setSelectedRows(String[] selectArray){
     	setAttribute(KEY_SELECT_ROWS,selectArray);
     }
     /**

     *

     *remove onetimes

     */
     public void removeSelectedRows(){
     	removeAttribute(KEY_SELECT_ROWS);
     }
     /**

     *

     * @return oneTimeVOs

     */

     public HashMap<String, Collection<OneTimeVO>> getOneTimeForPopup(){

    	 return getAttribute(KEY_ONETIME_VOS_POPUP);

     }
     /**

     *

     * @param oneTimeVOs

     */

     public void setOneTimeForPopup(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

     setAttribute(KEY_ONETIME_VOS_POPUP, oneTimeVOs);

     }
     /**

     *

     *remove onetimes

     */

     public void removeOneTimeForPopup() {

     removeAttribute(KEY_ONETIME_VOS_POPUP);

     }
     /**
      * This method is used for PageAwareMultiMapper to get the Index Map
      * @return  HashMap<String,String>
      */
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
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession#getTotalRecords()
	 *	Added by 			: A-5175 on 15-Oct-2012
	 * 	Used for 	:ICRD-21098
	 *	Parameters	:	@return 
	 */
	
	public Integer getTotalRecords() {
		
		return getAttribute(KEY_TOTALRECORDS);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession#setTotalRecords(int)
	 *	Added by 			: A-5175 on 15-Oct-2012
	 * 	Used for 	:ICRD-21098
	 *	Parameters	:	@param totalRecords 
	 */
	
	public void setTotalRecords(int totalRecords) {
		
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
     
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession#getGPABillingEntriesFilterVO()
	 *	Added by 			: A-7866 on 19-Oct-2017
	 * 	Used for 	:ICRD-189046
	 *	Parameters	:	@return 
	 */
	public GPABillingEntriesFilterVO getGPABillingEntriesFilterVO() {
		
		return getAttribute(KEY_GPABILLINGENTRIESFILTERVO);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession#setGPABillingEntriesFilterVO()
	 *	Added by 			: A-7866 on 19-Oct-2017
	 * 	Used for 	:ICRD-189046
	 *	Parameters	:	@return
	 */
	public void setGPABillingEntriesFilterVO(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) {
		
		setAttribute(KEY_GPABILLINGENTRIESFILTERVO, gpaBillingEntriesFilterVO);
	}
	
	/**
	 * 
	 */
	public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> VOs){
		setAttribute(KEY_SELECTED_VOID_MAILS,(ArrayList<DocumentBillingDetailsVO>)VOs);
	}
	
	
	/**
	 * 
	 */
	public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags() {
		return (Collection<DocumentBillingDetailsVO>)getAttribute(KEY_SELECTED_VOID_MAILS);
	}
     
}
