/* DeleteAttachGroupCodeCommand.java Created on Feb 06,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the damage reports. 
 * @author A-1862
 */
public class DeleteAttachGroupCodeCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("DeleteAttachGroupCodeCommand");
	private static final String SCREENID = "customermanagement.defaults.listcustomergroup";
	private static final String MODULE = "customermanagement.defaults";
	private static final String DELETE_SUCCESS = "deleteRow_success";
	private static final String OPERATION_FLAG_INS_DEL
	       = "operation_flg_insert_delete";
	private static final String BLANK = "";
   /**
    * @param invocationContext
    * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)throws CommandInvocationException {
    	log.entering("DeleteAttachGroupCodeCommand","execute");
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListCustomerGroupForm listCustomerGroupForm = (ListCustomerGroupForm) invocationContext.screenModel;
		ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULE, SCREENID);
		Collection<CustomerGroupLoyaltyProgrammeVO> customerGroupLoyaltyProgrammeVOs =
        	new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
	        ArrayList<CustomerGroupLoyaltyProgrammeVO> CustomerGroupLoyaltyProgrammeVOsFromSession = 
	        	listCustomerGroupSession.getCustomerGroupLoyaltyProgrammeVOs()!= null ?
					new ArrayList<CustomerGroupLoyaltyProgrammeVO>(listCustomerGroupSession.getCustomerGroupLoyaltyProgrammeVOs()): 
					new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
			log.log(Log.FINE, "\n\n\n\n Collection from session ---> ",
					CustomerGroupLoyaltyProgrammeVOsFromSession);
			String[] rowIds = listCustomerGroupForm.getSelectedRows();
			log.log(Log.FINE, "\n\n rowIds----------->", rowIds);
			if(CustomerGroupLoyaltyProgrammeVOsFromSession != null && CustomerGroupLoyaltyProgrammeVOsFromSession.size()>0){
				String[] loyaltyPgm = listCustomerGroupForm.getLoyaltyPgm();
		 		String[] pgmFromDate = listCustomerGroupForm.getPgmFromDate();
		 		String[] pgmToDate = listCustomerGroupForm.getPgmToDate();
		 		String[] attachFromDate = listCustomerGroupForm.getAttachFromDate();
		 		String[] attachToDate = listCustomerGroupForm.getAttachToDate();
		 		
		 		int index = 0;	
	    		for(CustomerGroupLoyaltyProgrammeVO vo : CustomerGroupLoyaltyProgrammeVOsFromSession){
		 			
	    			if((vo.getOperationalFlag()!=null &&
	    		    		!vo.getOperationalFlag().equals(AbstractVO.OPERATION_FLAG_DELETE))    		    					
	    		    			|| vo.getOperationalFlag()==null){  
	    				log.log(Log.FINE, "vo.getLoyaltyProgramCode()", vo.getLoyaltyProgramCode());
						log.log(Log.FINE, "vo.getFromDate()", vo.getFromDate());
						log.log(Log.FINE, "vo.getToDate()", vo.getToDate());
						if(
			    				(BLANK.equals(vo.getLoyaltyProgramCode()) && !BLANK.equals(loyaltyPgm[index])) ||
		    					
		    					(!BLANK.equals(vo.getLoyaltyProgramCode()) && !vo.getLoyaltyProgramCode().equals(loyaltyPgm[index])) || 
			    					
			    				(vo.getFromDate()== null && !BLANK.equals(attachFromDate[index])) ||
			    					
			    				(vo.getFromDate()!= null && !vo.getFromDate().toDisplayFormat(true).equals(attachFromDate[index])) || //Modified by A-5374
			    				
			    				(vo.getToDate()== null && !BLANK.equals(attachToDate[index])) ||
		    					
			    				(vo.getToDate()!= null && !vo.getToDate().toDisplayFormat(true).equals(attachToDate[index]))){ //Modified by A-5374
			    				if(vo.getOperationalFlag()==null){
			    					vo.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);
			    				}
			    			}
			    			if(loyaltyPgm[index] != null && loyaltyPgm[index].trim().length() != 0){
			    				vo.setLoyaltyProgramCode(loyaltyPgm[index].toUpperCase());
			    			}
			    			if(attachFromDate[index] != null && attachFromDate[index].trim().length() != 0){
			    				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setFromDate(fromDate.setDate(attachFromDate[index]));
			    			}
			    			if(attachToDate[index] != null && attachToDate[index].trim().length() != 0){
			    				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setToDate(toDate.setDate(attachToDate[index]));			    			
			    			}
			    			if(pgmFromDate[index] != null && pgmFromDate[index].trim().length() != 0){
			    				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setLoyaltyFromDate(fromDate.setDate(pgmFromDate[index]));
			    			}
			    			if(pgmToDate[index] != null && pgmToDate[index].trim().length() != 0){
			    				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN,false);
			    				vo.setLoyaltyToDate(toDate.setDate(pgmToDate[index]));			    			
			    			}
	    			}
	    			customerGroupLoyaltyProgrammeVOs.add(vo);
	    			index++;	    			
	    		}
	      	}
  	ArrayList<CustomerGroupLoyaltyProgrammeVO> customerGroupLoyaltyProgrammeVOtmp = 
  		new ArrayList<CustomerGroupLoyaltyProgrammeVO>();
	
	log.log(Log.FINE, "\n\n customerGroupLoyaltyProgrammeVOtmp--------->>>",
			customerGroupLoyaltyProgrammeVOtmp);
		if (rowIds != null) {
			int index=0;
			 for (CustomerGroupLoyaltyProgrammeVO vo :customerGroupLoyaltyProgrammeVOs) {
			for (int i = 0; i < rowIds.length; i++) {
				if (index == Integer.parseInt(rowIds[i])) {
					
					log.log(Log.FINE, "\n\n\n\n vo inside for loop ---> ", vo);
					log
							.log(
									Log.FINE,
									"\n\n\n\n vo.getOperationalFlag() inside for loop ---> ",
									vo.getOperationalFlag());
					if(vo.getOperationalFlag()!=null && 
	        				!vo.getOperationalFlag().equals
	        							(AbstractVO.OPERATION_FLAG_INSERT))
	        		   {
	        			   vo.setOperationalFlag
	        			   						(AbstractVO.OPERATION_FLAG_DELETE);  
	        		   }
	        		   if(vo.getOperationalFlag()==null )
	        		   {
	        			   vo.setOperationalFlag
	        			   						(AbstractVO.OPERATION_FLAG_DELETE);  
	        		   }
	        		   if(vo.getOperationalFlag()!=null &&
	        				   vo.getOperationalFlag().equals
	        								(AbstractVO.OPERATION_FLAG_INSERT))
	        		   {
	        			   vo.setOperationalFlag(OPERATION_FLAG_INS_DEL);
	        		   }
	        		   log
							.log(
									Log.FINE,
									"\n\n\n\n vo.getOperationalFlag() after change ---> ",
									vo.getOperationalFlag());
	        	   }
	        	   }index++;
	               }
			 }
		
		 for (CustomerGroupLoyaltyProgrammeVO customerGroupLoyaltyProgrammeVOTmp :customerGroupLoyaltyProgrammeVOs) {
			   log
					.log(
							Log.FINE,
							"\n\n\n\n customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag() before change ---> ",
							customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag());
			if(customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag()!=null && 
		   			   !customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag()
		   			   				.equals(OPERATION_FLAG_INS_DEL)){
		   		customerGroupLoyaltyProgrammeVOtmp.add(customerGroupLoyaltyProgrammeVOTmp);
		   	   }
		   	   if(customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag()==null ){
		   		customerGroupLoyaltyProgrammeVOtmp.add(customerGroupLoyaltyProgrammeVOTmp);
			   }
		   	  log
					.log(
							Log.FINE,
							"\n\n\n\n customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag() after change ---> ",
							customerGroupLoyaltyProgrammeVOTmp.getOperationalFlag());
      }
	   log.log(Log.FINE, "\n\n customerGroupLoyaltyProgrammeVOtmp---------->>>>",
			customerGroupLoyaltyProgrammeVOtmp);
	listCustomerGroupSession.setCustomerGroupLoyaltyProgrammeVOs(customerGroupLoyaltyProgrammeVOtmp);
       invocationContext.target =  DELETE_SUCCESS;
     }
}
