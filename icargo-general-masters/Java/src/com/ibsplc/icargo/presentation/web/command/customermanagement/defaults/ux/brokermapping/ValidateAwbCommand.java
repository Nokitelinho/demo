package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.AwbFilter;
import com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping.BrokerMappingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ValidateAwbCommand extends AbstractCommand {
	private static final String VALIDATE_AWB_SUCCESS = "VALIDATE_AWB_SUCCESS";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		ResponseVO responseVO= new ResponseVO();
		BrokerMappingModel brokerMappingModel =(BrokerMappingModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		AwbFilter awbFilter =brokerMappingModel.getAwbFilter();
		Collection<ShipmentVO> shipmentVOs =null;
		Collection<CustomerAgentVO> customerAgentVOs =null;
		ErrorVO error=null;
		boolean errorExist=false;
		AirlineValidationVO airlineValidationVO=null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		 try{
			 airlineValidationVO = airlineDelegate.validateNumericCode(logonAttributes.getCompanyCode(),awbFilter.getShipmentPrefix());
		 }catch(BusinessDelegateException ex){
			handleDelegateException(ex);
			errorExist=true;
	    	}
		 if(!errorExist && airlineValidationVO!=null){
			error = validateAWB(logonAttributes, awbFilter, shipmentVOs, customerAgentVOs, error);
		}else{
			error=new ErrorVO("Invalid AWB");
		}
		if(error!=null){
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			List<ErrorVO> results = new ArrayList<>();
    		results.add(error);
    		responseVO.setResults(results);
		}
		else{
			responseVO.setStatus(VALIDATE_AWB_SUCCESS);
		}
		actionContext.setResponseVO(responseVO);
	}

	protected ErrorVO validateAWB(LogonAttributes logonAttributes, AwbFilter awbFilter,
			Collection<ShipmentVO> shipmentVOs, Collection<CustomerAgentVO> customerAgentVOs, ErrorVO error) {
		CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
		ShipmentFilterVO shipmentFilterVO=populateShipmentFilterVO(logonAttributes.getCompanyCode(),awbFilter);
		try{
			shipmentVOs = delegate.validateShipmentDetails(shipmentFilterVO);
		}catch(BusinessDelegateException ex){
			handleDelegateException(ex);
		}
		if(shipmentVOs!=null && !shipmentVOs.isEmpty())
		{
			try{
				customerAgentVOs=delegate.validateSinglePoa(shipmentFilterVO);
			}catch(BusinessDelegateException ex){
				handleDelegateException(ex);
			}
			if(customerAgentVOs!=null && !customerAgentVOs.isEmpty()){
				error=new ErrorVO("POA already exist for the AWB number");
			}else{
				Collection<ShipmentHistoryVO> shpHisVOs=null;
				ShipmentHistoryFilterVO shipmentHistoryFilterVO=populateShipmentHistoryFilterVO(shipmentVOs);
				try {
					shpHisVOs=delegate.findShipmentHandlingHistory(shipmentHistoryFilterVO);
				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
				if(shpHisVOs!=null && !shpHisVOs.isEmpty()){
					error=new ErrorVO("POA cannot be created as flight is already arrived");
				}
			}
		}else{
			error=new ErrorVO("AWB does not exists in the system");
		}
		return error;
	}
	
	private ShipmentHistoryFilterVO populateShipmentHistoryFilterVO(Collection<ShipmentVO> shipmentVOs) {
		ShipmentVO shipmentVO=shipmentVOs.iterator().next();
		ShipmentHistoryFilterVO shipmentHistoryFilterVO=new ShipmentHistoryFilterVO();
		shipmentHistoryFilterVO.setCompanyCode(shipmentVO.getCompanyCode());
		shipmentHistoryFilterVO.setMasterDocumentNumber(shipmentVO.getMasterDocumentNumber());
		shipmentHistoryFilterVO.setDocumentOwnerId(shipmentVO.getOwnerId());
		shipmentHistoryFilterVO.setDuplicateNumber(shipmentVO.getDuplicateNumber());
		shipmentHistoryFilterVO.setAirportCode(shipmentVO.getDestination());
		shipmentHistoryFilterVO.setTransactionCode("ARR");
		return shipmentHistoryFilterVO;
	}

	private ShipmentFilterVO populateShipmentFilterVO(String companyCode, AwbFilter awbFilter){
        ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
        shipmentFilterVO.setCompanyCode(companyCode);
        shipmentFilterVO.setShipmentPrefix(awbFilter.getShipmentPrefix());
        shipmentFilterVO.setDocumentNumber(awbFilter.getMasterDocumentNumber());                  
        shipmentFilterVO.setRetrieveMasterOnly(true);
        return shipmentFilterVO;
    }
	
	

}
