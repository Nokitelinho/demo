package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.PoaMappingDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;

public class DeleteCustomerCommand extends AbstractCommand {
	private static final String LOCK_ACTION = "MODIFYCUS";
	private static final String LOCK_REMARKS = "CUSTOMER LOCKING";
	private static final String LOCK_DESCRIPTION = "Customer Modifications";
	private static final String SCREENID = "customermanagement.defaults.ux.brokermapping";
	private static final String DELETE_SUCCESS = "DELETE_SUCCESS";
	private static final String BLANK = "";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		ResponseVO responseVO= new ResponseVO();
		BrokerMappingModel brokerMappingModel =(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		BrokerMappingFilter brokerMappingFilter = brokerMappingModel.getBrokerMappingFilter();
		CustomerVO customerVO = new CustomerVO();
		CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
		if(brokerMappingFilter.getCustomerCode()!=null && brokerMappingFilter.getCustomerCode().trim().length()>0){
			
			customerVO.setCompanyCode(logonAttributes.getCompanyCode());
			customerVO.setCustomerType("TMP");
			customerVO.setCustomerCode(brokerMappingFilter.getCustomerCode());
			customerVO.setOperationFlag(CustomerVO.OPERATION_FLAG_DELETE);
			populateCustomerVO(customerVO,brokerMappingModel);
		}
		
		Collection<LockVO> locks = prepareLockForSave();
		String customerCode = BLANK;
		try {
			customerCode = delegate.registerCustomer(customerVO,locks);
		} catch (BusinessDelegateException ex) {
			handleDelegateException(ex);
		}
		List<String> results = new ArrayList<>();
		results.add(customerCode);
		responseVO.setResults(results);
		responseVO.setStatus(DELETE_SUCCESS);
		actionContext.setResponseVO(responseVO);
	}

	private void populateCustomerVO(CustomerVO customerVO, BrokerMappingModel brokerMappingModel) {
		Collection<PoaMappingDetails> consigneeDetails=brokerMappingModel.getConsigneeDetails();
		customerVO.setSource("MNGPOA");
		if(consigneeDetails!=null && !consigneeDetails.isEmpty())
		{
		customerVO.setCustomerConsigneeVOs(populatePOADetails(consigneeDetails));
		}
	}

	private Collection<CustomerAgentVO> populatePOADetails(Collection<PoaMappingDetails> poaDetails) {
		Collection<CustomerAgentVO> customerPOADetails=new ArrayList<>();
		for(PoaMappingDetails details:poaDetails)
		{
			CustomerAgentVO poaDetail=new CustomerAgentVO();
			poaDetail.setAgentCode(details.getAgentCode());
			poaDetail.setAgentName(details.getAgentName());
			poaDetail.setPoaType(details.getPoaType());
			poaDetail.setOperationFlag(details.getOperatonalFlag());
			setScc(details, poaDetail);
			if(details.getSccCodeExclude()!=null && details.getSccCodeExclude().length>0)
			{
				String [] sccE=details.getSccCodeExclude();
				poaDetail.setExcludedScc(String.join(",", sccE));
			}
			if(details.getOrginInclude()!=null && details.getOrginInclude().length>0)
			{
				String[] orginI=details.getOrginInclude();
				poaDetail.setIncludedOrigins(String.join(",", orginI));
			}
			if(details.getOrginExclude()!=null && details.getOrginExclude().length>0)
			{
				String[] orginE=details.getOrginExclude();
				poaDetail.setExcludedOrigins(String.join(",",orginE));
			}
			if(details.getDestination()!=null && details.getDestination().length>0)
			{
				String [] dest=details.getDestination();
				poaDetail.setDestination(String.join(",", dest));
			}
			poaDetail.setStationCode(details.getStation());
			poaDetail.setCustomerCode(details.getCustomerCode());
			poaDetail.setSequenceNumber(details.getSequenceNumber());
			customerPOADetails.add(poaDetail);
		}
		return customerPOADetails;
	}

	private void setScc(PoaMappingDetails details, CustomerAgentVO poaDetail) {
		if(details.getSccCodeInclude()!=null && details.getSccCodeInclude().length>0)
		{
			String [] sccI=details.getSccCodeInclude();
			poaDetail.setScc(String.join(",",sccI));
		}
	}
	private Collection<LockVO> prepareLockForSave(){
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<LockVO> locks = new ArrayList<>();
		LockVO txnLockVO = new TransactionLockVO(LOCK_ACTION);
		txnLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		txnLockVO.setAction(LOCK_ACTION);
		txnLockVO.setScreenId(SCREENID);
		txnLockVO.setStationCode(logonAttributes.getStationCode());
		txnLockVO.setDescription(LOCK_DESCRIPTION);
		txnLockVO.setRemarks(LOCK_REMARKS);
		locks.add(txnLockVO);
		return locks;
	}

	

}
