/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;


import java.util.Collection;

import com.ibsplc.icargo.business.shared.agent.vo.AgentFilterVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.customer.CustomerDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class FetchCustomerDetailsAjaxCommand extends BaseCommand {
	
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow ULD");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	/**
	 * target String if success
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
 		log.entering("FetchCustomerDetailsAjaxCommand", "execute");
		MaintainULDTransactionForm maintainULDTransactionForm =
			(MaintainULDTransactionForm) invocationContext.screenModel;
		
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		String shortCode = maintainULDTransactionForm.getToShortCode();
		String partyCode = maintainULDTransactionForm.getToPartyCode();
		String txnType = maintainULDTransactionForm.getTransactionType();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = "";
		boolean isFromParty = false;
		boolean isFromPartyAgent = false;
		companyCode = logonAttributes.getCompanyCode();
		if(shortCode == null ||  shortCode.trim().length()== 0){
			shortCode = maintainULDTransactionForm.getFromShortCode();
			isFromParty = true;
		}
//		added for bugfix 101303 on 13Oct10
		if("R".equals(txnType)){
			partyCode = maintainULDTransactionForm.getFromPartyCode();
			isFromPartyAgent = true;
		}/* Modified by A-3415 for ICRD-114538
		else if("B".equals(txnType)){
			partyCode = maintainULDTransactionForm.getFromPartyCode();
			isFromPartyAgent = true;
		}*/
//		added for bugfix 101303 on 13Oct10 ends
		
		CustomerDelegate customerDelegate = new CustomerDelegate();		
		CustomerListFilterVO customerListFilterVO = new CustomerListFilterVO();
		Page<CustomerVO>  page= null;
		
		log.log(Log.INFO, " ShortCode = issss=%%===>", shortCode);
		log.log(Log.INFO, " PartyCode = issss=&&===>", partyCode);
		if(partyCode != null && partyCode.trim().length() > 0 && "agentlov".equals(maintainULDTransactionForm.getComboFlag()) ){
			AgentDelegate agentDelegate = new AgentDelegate();
			AgentFilterVO agentFilterVO = new AgentFilterVO();
			agentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			AgentVO agentVO = null;
			partyCode = partyCode.toUpperCase();
			try {
				agentVO = agentDelegate.findAgentDetails(logonAttributes.getCompanyCode(), partyCode);
			} catch (BusinessDelegateException e) {
				Collection<ErrorVO> errors = handleDelegateException(e);
	        	if(errors != null && errors.size() > 0){
	        		invocationContext.addAllError(errors);
	        		invocationContext.target =ACTION_SUCCESS;
	        		return;
	        	}
			}
			if(agentVO != null){
				if(isFromPartyAgent){
					maintainULDTransactionForm.setFromPartyName(agentVO.getAgentName());
				}else{
					maintainULDTransactionForm.setToPartyCode(agentVO.getAgentCode());
					maintainULDTransactionForm.setToPartyName(agentVO.getAgentName());
					maintainULDTransactionForm.setToShortCode(agentVO.getShortCode());
				}
			}
		}
		
		else if(shortCode != null && shortCode.trim().length() > 0 ){
			
			customerListFilterVO.setCompanyCode(companyCode);
			customerListFilterVO.setPageNumber(1);
			customerListFilterVO.setShortCode(shortCode.toUpperCase().trim());	 
			customerListFilterVO.setTotalRecords(-1);
			try {
				page = customerDelegate.listCustomerDetails(customerListFilterVO);
			
			}catch(BusinessDelegateException businessDelegateException) {	
				 handleDelegateException(businessDelegateException);
			
			}
			//log.log(Log.INFO," ShortCode ==>"+page);
			if(page != null && page.size()> 0){
				
				if(isFromParty){
			  maintainULDTransactionForm.setFromPartyCode(page.get(0).getCustomerCode());
			  maintainULDTransactionForm.setFromPartyName(page.get(0).getCustomerName());
				}else{
					maintainULDTransactionForm.setToPartyCode(page.get(0).getCustomerCode());
					maintainULDTransactionForm.setToPartyName(page.get(0).getCustomerName());
				}
			}
			log.exiting("FetchCustomerDetailsAjaxCommand", "execute");
		}//added for bugfix 101303 on 13Oct10
		else if(partyCode != null && partyCode.length() > 0){
						
			customerListFilterVO.setCompanyCode(companyCode);
			customerListFilterVO.setPageNumber(1);
			customerListFilterVO.setCustomerCode(partyCode.toUpperCase().trim());
			customerListFilterVO.setTotalRecords(-1);
			try {
				page = customerDelegate.listCustomerDetails(customerListFilterVO);
			
			}catch(BusinessDelegateException businessDelegateException) {	
				 handleDelegateException(businessDelegateException);
			
			}			
			if(page != null && page.size()> 0){
				
				if(isFromPartyAgent){
			  maintainULDTransactionForm.setFromShortCode(page.get(0).getShortCode());
			  maintainULDTransactionForm.setFromPartyName(page.get(0).getCustomerName());
				}else{
					maintainULDTransactionForm.setToShortCode(page.get(0).getShortCode());
					maintainULDTransactionForm.setToPartyName(page.get(0).getCustomerName());
				}
			}
			log.exiting("FetchCustomerDetailsAjaxCommand", "execute");
			
		}
//		added for bugfix 101303 on 13Oct10 ends
		invocationContext.target = ACTION_SUCCESS;
	}

}
