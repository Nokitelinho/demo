package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;


public class BrokerMappingModelConverter {
	
	public static BrokerMappingModel convertToBrokerMappingModel(CustomerVO customerVo)
	{
		BrokerMappingModel brokerMappingModel=new BrokerMappingModel();
		if(customerVo!=null)
		{
		
			brokerMappingModel.setCustomerDetails(convertToCustomerDetails(customerVo));
			
			
			if(customerVo.getCustomerAgentVOs()!=null&&!customerVo.getCustomerAgentVOs().isEmpty())
			{
				brokerMappingModel.setBrokerDetails(convertToPOADetails(customerVo.getCustomerAgentVOs()));
				brokerMappingModel.setAwbDetails(convertToSinglePOADetails(customerVo.getCustomerAgentVOs()));

			}
			else{
				Collection<PoaMappingDetails> brokerDetails  =new ArrayList<>();
				brokerMappingModel.setBrokerDetails(brokerDetails);
				brokerMappingModel.setAwbDetails(brokerDetails);
			}
			if(customerVo.getCustomerConsigneeVOs()!=null&&!customerVo.getCustomerConsigneeVOs().isEmpty())
			{
			brokerMappingModel.setConsigneeDetails(convertToPOADetails(customerVo.getCustomerConsigneeVOs()));
			}
			else{
				Collection<PoaMappingDetails> consigneeDetails  =new ArrayList<>();
				brokerMappingModel.setConsigneeDetails(consigneeDetails);
			}
			
		}
		else{
			Collection<PoaMappingDetails> poaDetails  =new ArrayList<>();
			brokerMappingModel.setBrokerDetails(poaDetails);
			brokerMappingModel.setConsigneeDetails(poaDetails);
			brokerMappingModel.setAwbDetails(poaDetails);
			brokerMappingModel.setWarningFlag(true);
		}
		return brokerMappingModel;
		
	}
	private static Collection<PoaMappingDetails> convertToSinglePOADetails(
			Collection<CustomerAgentVO> customerAgentVOs) {
		Collection<PoaMappingDetails> awbDetails  =new ArrayList<>();
		int i=0;
		for(CustomerAgentVO customerAgentVO:customerAgentVOs )
		{
			if("Single POA".equalsIgnoreCase(customerAgentVO.getPoaType()))
			{
				PoaMappingDetails poaMappingDetails =new PoaMappingDetails();
				poaMappingDetails.setOperatonalFlag("N");
				poaMappingDetails.setPoaType(customerAgentVO.getPoaType());
				poaMappingDetails.setSequenceNumber(customerAgentVO.getSequenceNumber());
				poaMappingDetails.setCustomerCode(customerAgentVO.getCustomerCode());
				poaMappingDetails.setAwbNumber(customerAgentVO.getAwbNum());
				poaMappingDetails.setIndex(i);
				if(customerAgentVO.getPoaCreationTime()!=null){
					poaMappingDetails.setCreationDate(customerAgentVO.getPoaCreationTime().toString());
				}
				String[] add={};
				poaMappingDetails.setSccCodeInclude(add);
				poaMappingDetails.setSccCodeExclude(add);
				poaMappingDetails.setOrginExclude(add);
				poaMappingDetails.setOrginInclude(add);
				poaMappingDetails.setDestination(add);
				
				awbDetails.add(poaMappingDetails);
				i++;
			}
			
		}
		return awbDetails;
	}
	private static Collection<PoaMappingDetails> convertToPOADetails(Collection<CustomerAgentVO> customerAgentVOs) {
		Collection<PoaMappingDetails> brokerDetails  =new ArrayList<>();
		int i=0;
		for(CustomerAgentVO customerAgentVO:customerAgentVOs )
		{
			if(!"Single POA".equalsIgnoreCase(customerAgentVO.getPoaType()))
			{
				PoaMappingDetails poaMappingDetails =new PoaMappingDetails();
				poaMappingDetails.setDeletionRemarks(null);
				poaMappingDetails.setOperatonalFlag("N");
				poaMappingDetails.setPoaType(customerAgentVO.getPoaType());
				poaMappingDetails.setAgentCode(customerAgentVO.getAgentCode());
				poaMappingDetails.setAgentName(customerAgentVO.getAgentName());
				setScc(customerAgentVO, poaMappingDetails);
				String[] add={};
				if (customerAgentVO.getExcludedScc() != null) {
					String sccE = customerAgentVO.getExcludedScc();
					poaMappingDetails.setSccCodeExclude(sccE.split(","));
				} else {
					poaMappingDetails.setSccCodeExclude(add);
				}
				if (customerAgentVO.getExcludedOrigins() != null) {
					String orginE = customerAgentVO.getExcludedOrigins();
					poaMappingDetails.setOrginExclude(orginE.split(","));
				} else {
					poaMappingDetails.setOrginExclude(add);
				}
				if (customerAgentVO.getIncludedOrigins() != null) {
					String orginI = customerAgentVO.getIncludedOrigins();
					poaMappingDetails.setOrginInclude(orginI.split(","));
				} else {
					poaMappingDetails.setOrginInclude(add);
				}
				if (customerAgentVO.getDestination() != null) {
					poaMappingDetails.setDestination(customerAgentVO.getDestination().split(","));
				} else {
					poaMappingDetails.setDestination(add);
				}
				
				poaMappingDetails.setStation(customerAgentVO.getStationCode());
				poaMappingDetails.setIndex(i);
				poaMappingDetails.setCustomerCode(customerAgentVO.getCustomerCode());
				poaMappingDetails.setSequenceNumber(customerAgentVO.getSequenceNumber());
				if(customerAgentVO.getPoaCreationTime()!=null){
					poaMappingDetails.setCreationDate(customerAgentVO.getPoaCreationTime().toString());
				}
				if(customerAgentVO.getValidityStartDate()!=null){
					poaMappingDetails.setVldStartDate(customerAgentVO.getValidityStartDate().toString());
				}
				if(customerAgentVO.getValidityEndDate()!=null){
					poaMappingDetails.setVldEndDate(customerAgentVO.getValidityEndDate().toString());
				}
				brokerDetails.add(poaMappingDetails);
				i++;
			}
		}
		return brokerDetails;
		
	}
	private static void setScc(CustomerAgentVO customerAgentVO, PoaMappingDetails poaMappingDetails) {
		if(customerAgentVO.getScc()!=null)
		{
			String sccI=customerAgentVO.getScc();
			poaMappingDetails.setSccCodeInclude(sccI.split(","));
			
		}
		else
		{
			String[] add={};
			poaMappingDetails.setSccCodeInclude(add);
		}
	}

	private static CustomerDetails convertToCustomerDetails(CustomerVO customerVo) {
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setCustomerName(customerVo.getCustomerName());
		if(customerVo.getAdditionalDetails()!=null)
		{
			customerDetails.setAdditionalDetails(customerVo.getAdditionalDetails());
		}
		if(customerVo.getAdditionalNames()!=null){
			Collection<AdditionalName> addName=new ArrayList<>();
			
			String[] arry=customerVo.getAdditionalNames().split(";");
			int index=0;
			for(String adlarry:arry)
			{
				AdditionalName additionalName=new AdditionalName();
				additionalName.setAdlNam(adlarry);
				additionalName.setIndex(index);
				additionalName.setOperationalFlag("N");
				addName.add(additionalName);
				index++;
			}
			customerDetails.setAdditionalNames(addName);
		}else{
			Collection<AdditionalName> addName=new ArrayList<>();
			customerDetails.setAdditionalNames(addName);
		}
		customerDetails.setCity(customerVo.getCity());
		customerDetails.setCountry(customerVo.getCountry());
		customerDetails.setStation(customerVo.getStationCode());
		customerDetails.setStreet(customerVo.getAddress1());
		customerDetails.setZipCode(customerVo.getZipCode());
		if(customerVo.getCustomerType()!=null)
		{
			customerDetails.setCustomerType(customerVo.getCustomerType());
		}
		
		return customerDetails;
	}
	
	public static Collection<PoaHistoryDetails> convertToAuditDetails(Collection<AuditDetailsVO> auditDetailsVOs,
			String cusCode) {
		Collection<PoaHistoryDetails> poaHistoryDetails = new ArrayList<>();
		for (AuditDetailsVO auditDetailsVO : auditDetailsVOs) {

			PoaHistoryDetails poaHistoryDetail = new PoaHistoryDetails();
			poaHistoryDetail.setTransactionCode("POA deletion");
			poaHistoryDetail.setDeletionDate(auditDetailsVO.getLastUpdateTime().toString().substring(0,17));
			poaHistoryDetail.setStation(auditDetailsVO.getStationCode());
			poaHistoryDetail.setTriggerPoint(auditDetailsVO.getTriggerPoint());
			poaHistoryDetail.setUserId(auditDetailsVO.getLastUpdateUser());
			if (auditDetailsVO.getAdditionalInformation() != null) {
				String[] addInfo;
				addInfo = auditDetailsVO.getAdditionalInformation().split("\\|");
				if (addInfo.length > 1) {
					if (cusCode.equals(auditDetailsVO.getRefOne())) {
						poaHistoryDetail.setAdlInfo(addInfo[0]);
					} else {
						poaHistoryDetail.setAdlInfo(addInfo[1]);
					}
				} else {
					poaHistoryDetail.setAdlInfo(addInfo[0]);
				}
			}

			poaHistoryDetails.add(poaHistoryDetail);
		}
		return poaHistoryDetails;
	}

	
}
