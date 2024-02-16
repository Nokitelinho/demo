/*
 * ListCustomerGroupCommand.java Created on May 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.struts.comp.config.ICargoComponent;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the customer group details of the specified 
 * group 
 * @author A-2122
 */
public class ListCustomerGroupCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("LIST CUSTOMER GROUP");
	private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_error";
    private static final String LISTSTATUS = "noListForm";
    private static final String SCREEN_ID = 
    				"customermanagement.defaults.listcustomergroup";
	private static final String MODULE_NAME = "customermanagement.defaults";
	
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering("ListCustomerGroupCommand","execute");
        ApplicationSessionImpl applicationSession = getApplicationSession();
        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
        ListCustomerGroupForm listCustomerGroupForm = 
			(ListCustomerGroupForm) invocationContext.screenModel;
        ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
        CustomerGroupFilterVO customerGroupFilterVO = 
        	listCustomerGroupSession.getCustomerGroupFilterVO();
       //Added by A-5220 for ICRD-20902 starts
       if(!ListCustomerGroupForm.PAGINATION_MODE_FROM_LIST.equals(listCustomerGroupForm.getNavigationMode())){
    	   CustomerGroupFilterVO customerGroupFilterVO1 = listCustomerGroupSession.getCustomerGroupFilterVO();
    	   if(customerGroupFilterVO1 != null){
    		   populateForm(listCustomerGroupForm, customerGroupFilterVO1);
    	   }
       }
      //Added by A-5220 for ICRD-20902 ends

            /* *** code added for pagination ***** */
            
           /* HashMap<String,String> indexMap = null;
            HashMap<String,String> finalMap = null;
            if(listCustomerGroupSession.getIndexMap()!= null){
            indexMap = listCustomerGroupSession.getIndexMap();
            }
            else {
            indexMap = new HashMap<String,String>();
            indexMap.put("1", "1");
            }
           */
           
            /* *** code added for pagination ends ***** */
                           
        	Page<CustomerGroupVO>  customerGroupVOList = null;
        	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        	if(customerGroupFilterVO == null){
        		customerGroupFilterVO = 
									new CustomerGroupFilterVO();
		}
        	
        	

 			if(ListCustomerGroupForm.PAGINATION_MODE_FROM_LIST.equals(listCustomerGroupForm.getNavigationMode())
 					|| listCustomerGroupForm.getNavigationMode() == null){
 				
 				customerGroupFilterVO.setTotalRecordsCount(-1);
				//Commented by A-5220 for ICRD-20902 starts
 				//customerGroupFilterVO.setPageNumber(1); 
 				//Commented by A-5220 for ICRD-20902 ends
 			
 				log.log(Log.INFO, "PAGINATION_MODE_FROM_NAVIGATION_LIST ");
 			}else if(ListCustomerGroupForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(listCustomerGroupForm.getNavigationMode()))
 			{
 				
 				customerGroupFilterVO.setTotalRecordsCount(listCustomerGroupSession.getTotalRecordsCount());
 				customerGroupFilterVO.setPageNumber(Integer.parseInt(listCustomerGroupForm.getDisplayPage()));
 				
 			}
 			
        	
        listCustomerGroupForm.setCreateFlag("List");
		if(!(LISTSTATUS.equals(listCustomerGroupSession.getListStatus()))) {
			updateFilterVO(listCustomerGroupForm,customerGroupFilterVO);	
		}
		else {
			listCustomerGroupSession.setListStatus("");
			//Commented by A-5220 for ICRD-20902 starts

			//listCustomerGroupForm.setDisplayPage("1");
			//listCustomerGroupForm.setLastPageNum("0");
			//Commented by A-5220 for ICRD-20902 ends
			
			customerGroupFilterVO.setTotalRecordsCount(-1);

			//Commented by A-5220 for ICRD-20902 starts
			//customerGroupFilterVO.setPageNumber(1);
			//Commented by A-5220 for ICRD-20902 ends
			
		}
		listCustomerGroupSession.setCustomerGroupFilterVO(customerGroupFilterVO);
		
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!!!!!inside errors!= null");
		}else{
			log.log(Log.FINE,"!!!inside errors== null");
			//Added by A-5220 for ICRD-20902 starts
			customerGroupFilterVO.setPageNumber(Integer.parseInt(listCustomerGroupForm.getDisplayPage()));
			//Added by A-5220 for ICRD-20902 starts
			customerGroupVOList = listCustomerGroup(customerGroupFilterVO);
	if(customerGroupVOList == null || 
	   (customerGroupVOList != null &&customerGroupVOList.size()==0)){
				log.log(Log.FINE,"!!!inside resultList== null");
				listCustomerGroupSession.setCustomerGroupVO(null);
				ErrorVO errorVO = new ErrorVO(
						"customermanagement.defaults.listcustomergroup.msg.err.nocustomergrouplistexists");
				errorVO.setErrorDisplayType(ERROR);
				errors.add(errorVO);
			}
		}
		if(errors != null && errors.size() > 0){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
		}else{
			log.log(Log.FINE,"!!!inside resultList!= null");
			listCustomerGroupSession.setCustomerGroupVO(customerGroupVOList);	
			
			listCustomerGroupSession.setTotalRecordsCount(customerGroupVOList.getTotalRecordCount());
			
			new ICargoComponent().setScreenStatusFlag(
	  				ComponentAttributeConstants.SCREEN_STATUS_VIEW);
    		invocationContext.target = LIST_SUCCESS;
            
            /* *********** code added for pagination ******** */
           /* finalMap = indexMap;
                if(listCustomerGroupSession.getCustomerGroupVO() != null){
                    finalMap = buildIndexMap(indexMap,
                            listCustomerGroupSession.getCustomerGroupVO());
                    listCustomerGroupSession.setIndexMap(finalMap);
                }*/		
            /* *********** code added for pagination ******** */
            
        log.exiting("ListCustomerGroupCommand","execute");
    }
    }
    
  private void updateFilterVO(ListCustomerGroupForm listCustomerGroupForm,
    		CustomerGroupFilterVO customerGroupFilterVO){
      ApplicationSessionImpl applicationSession = getApplicationSession();
      LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
      	String companyCode = logonAttributes.getCompanyCode();
    	log.entering("ListAccessoriesStockCommand","updateFilterVO");
    	customerGroupFilterVO.setCompanyCode(companyCode);
    	//String displayPage = listCustomerGroupForm.getDisplayPage();    	
    	//int pageNumber=Integer.parseInt(displayPage);
    	//customerGroupFilterVO.setPageNumber(pageNumber);
    	String groupCode = listCustomerGroupForm.getGroupCode();
    	if(groupCode != null && groupCode.trim().length() > 0){
    		customerGroupFilterVO.setGroupCode(groupCode.toUpperCase());
    	}
    	else{
    		customerGroupFilterVO.setGroupCode("");
    	}
    	
    	String groupName = listCustomerGroupForm.getGroupName();
    	if(groupName != null && groupName.trim().length() > 0){
    		customerGroupFilterVO.setGroupName(
    				groupName.toUpperCase());
    	}
    	else{
    		customerGroupFilterVO.setGroupName("");
    	}
        
        
          /* ******** code added for pagination ******* */
         /*String strAbsoluteIndex = indexMap.get(displayPage);
         listCustomerGroupForm.setAbsIdx(strAbsoluteIndex);
         if(strAbsoluteIndex != null){
         customerGroupFilterVO.setPageAbsoluteIndex(Integer.parseInt(strAbsoluteIndex));
         }*/
         
          /* ******** code added for pagination ******* */
         
         
    	log.exiting("ListCustomerGroupCommand","updateFilterVO");
    }
    
    private Page<CustomerGroupVO> listCustomerGroup(CustomerGroupFilterVO customerGroupFilterVO){
    	log.entering("ListAccessoriesStockCommand","listCustomerGroup");
    	Page<CustomerGroupVO> customerGroupVOs = null;
    	try{
    		log.log(Log.FINE,"before calling delegate");
    		CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
    		customerGroupVOs = 
    			customerDelegate.viewCustomerGroups(customerGroupFilterVO);
    		log.log(Log.FINE,"after calling delegate");
    	}catch(BusinessDelegateException businessDelegateException){
    		log.log(Log.FINE,"inside listCustomerGroup caught busDelgExc");
//printStackTrrace()();
        	handleDelegateException( businessDelegateException);
        	
    	}
    	log.exiting("ListAccessoriesStockCommand","listCustomerGroup");
    	return customerGroupVOs;
    }
    
    /**
     * method for updating the index map
     * @param existingMap
     * @param page
     * @return
     */
   /* private HashMap<String,String> buildIndexMap(HashMap<String,String> existingMap, Page<CustomerGroupVO> page) {
        HashMap<String,String> finalMap = existingMap;
        String indexPage = String.valueOf((page.getPageNumber()+1));

        boolean isPageExits = false;
        Set<Map.Entry<String, String>> set = existingMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            String pageNum = entry.getKey();
            if (pageNum.equals(indexPage)) {
                isPageExits = true;
            }
        }

        if (!isPageExits) {
            finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
        }
        return finalMap;
    }*/
	//Added by A-5220 for ICRD-20902 starts
	private void populateForm( ListCustomerGroupForm listCustomerGroupForm,CustomerGroupFilterVO customerGroupFilterVO ){
		listCustomerGroupForm.setGroupCode(customerGroupFilterVO.getGroupCode());
		listCustomerGroupForm.setGroupName(customerGroupFilterVO.getGroupName());
	}
	//Added by A-5220 for ICRD-20902 ends
}
