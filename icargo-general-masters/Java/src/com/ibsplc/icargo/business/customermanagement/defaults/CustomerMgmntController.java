/*
 * CustomerMgmntController.java Created on Jan 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceDetailsVO;
import com.ibsplc.icargo.business.accounting.invoicing.vo.InvoiceFilterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.CharterOperationsVO;
import com.ibsplc.icargo.business.cap.defaults.charter.vo.ListCharterOperationsFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryFilterVO;
import com.ibsplc.icargo.business.capacity.allotment.vo.CustomerAllotmentEnquiryVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingFilterVO;
import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimFilterVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimListVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CCADetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerBillingInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceDetailsVO;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.PaymentDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.EmailAccountStatementFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.savebrokermapping.SaveBrokerMappingFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument.UploadBrokerMappingDocumentFeature;
import com.ibsplc.icargo.business.customermanagement.defaults.helper.CustomerMgmntHelper;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.AirWayBillLoyaltyProgram;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.AttachLoyaltyProgramme;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerAlreadyAttachedToLoyaltyException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerLoyaltyAttribute;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomerServices;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.CustomersAlreadyAttatchedException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.LoyaltyProgramme;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.LoyaltyProgrammeExpiredException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.LoyaltyProgrammeNotAttachedException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.ParameterExistsException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.ServiceInUseException;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AirWayBillLoyaltyProgramVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeAuditVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.CustomerServicesVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyAttributeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyParameterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ParameterDescriptionVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.RedemptionValidationVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.CustomerGroupLoyaltyProgramme;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.CustomerGroupLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.AccountingInvoicingProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.AdminUserProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CRAAgentBillingProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CapDefaultsProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CapacityBookingProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.CapacityMonitoringProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.ClaimsDefaultsProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.TariffFreightProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageHistoryVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageListFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentHistoryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCertificateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerCreditVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerErrorVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestDetailsVO;
import com.ibsplc.icargo.business.tariff.freight.spotrate.vo.SpotRateRequestFilterVO;
import com.ibsplc.icargo.business.tariff.freight.vo.RateLineVO;
import com.ibsplc.icargo.business.tariff.freight.vo.TariffFilterVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstance;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteDetailsVO;
import com.ibsplc.icargo.framework.web.json.vo.ScribbleNoteFilterVO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.error.ErrorUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
import com.ibsplc.xibase.util.zip.ByteObject;

/**
 * @author A-1496
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class CustomerMgmntController {

	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");

	/**
	 *
	 */
	public static final String CRTCUSLTYPGM = "CUSLTYPGMCRT";
	/**
	 *
	 */
	public static final String UPDCUSLTYPGM = "CUSLTYPGMUPD";
	/**
	 *
	 */
	public static final String DELCUSLTYPGM = "CUSLTYPGMDEL";

	/**
	 *
	 */
	public static final String CRTLTYPGM = "LTYPGMCRT";
	/**
	 *
	 */
	public static final String UPDLTYPGM = "LTYPGMUPD";
	/**
	 *
	 */
	public static final String DELLTYPGM = "LTYPGMDEL";

	 private static final String LP_REPORT_ID = "RPTLST045";

	 private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";
	 
	 public static final String DUPLICATE_USER_ERROR = "admin.user.duplicateuser";
	 
	 public static final String DUPLICATE_USER_ERRORDESC = "Duplicate User Exists. Cannot proceed with customer save";
	 
	private static final String REPORT_ID = "RPRCUS003";

	private static final String ACTION = "printAccountStatement";

	private static final String PRODUCT = "customermanagement";

	private static final String SUBPRODUCT = "defaults";

	private static final String BUNDLE = "customerConsoleResourceBundle";
	
	private static final String BUSINESS_EXCEPTION = "BusinessException";

	/***
	 * Finds all Loyalty programmes
	 * @param companyCode
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	/*public Collection<LoyaltyProgrammeVO> findAllLoyaltyProgrammes(String companyCode)
	throws SystemException {
		log.entering("CustomerMgmntController","findAllLoyaltyProgrammes");

		Collection<LoyaltyProgrammeVO> loyaltyProgramVOs =null;

		loyaltyProgramVOs = LoyaltyProgramme.findAllLoyaltyProgrammes(companyCode);
		return loyaltyProgramVOs;
	}*/
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 * @throws ParameterExistsException
	 * @throws CustomersAlreadyAttatchedException
	 */
	public void createLoyaltyProgramme(LoyaltyProgrammeVO loyaltyProgrammeVO)
	throws SystemException ,ParameterExistsException
	,CustomersAlreadyAttatchedException{
		log.entering("CustomerMgmntController","createLoyaltyProgramme");
		if(LoyaltyProgrammeVO.OPERATION_FLAG_INSERT.equals(loyaltyProgrammeVO
				.getOperationFlag())){
			if(loyaltyProgrammeVO.getLoyaltyParameterVOs() != null &&
					loyaltyProgrammeVO.getLoyaltyParameterVOs().size() > 0){
				validateParameter(loyaltyProgrammeVO);
			}
			LoyaltyProgramme loyaltyProgramme =new LoyaltyProgramme(loyaltyProgrammeVO);
			log.log(Log.FINE,"\n\n New Loyalty Programme Created \n\n");
			AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
					LoyaltyProgrammeVO.MODULE,LoyaltyProgrammeVO.SUBMODULE,
					LoyaltyProgrammeVO.ENTITY);
			auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
					auditVO,loyaltyProgramme,true);
			auditVO = findLoyaltyProgrammeAuditVO(
					loyaltyProgramme,auditVO,loyaltyProgrammeVO);
			auditVO.setActionCode(CRTLTYPGM);
			//auditVO.setAuditRemarks(AuditAction.INSERT.toString());
				 AuditUtils.performAudit(auditVO);
		}
		else if(LoyaltyProgrammeVO.OPERATION_FLAG_DELETE.equals(loyaltyProgrammeVO.
				getOperationFlag())){
			checkLoyaltyProgrammeAttached(loyaltyProgrammeVO);
			LoyaltyProgramme loyaltyProgramme = LoyaltyProgramme.find(loyaltyProgrammeVO);
			log.log(Log.FINE,"\n\n Audit for Loyalty Programme Deletion \n\n");




			AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
					LoyaltyProgrammeVO.MODULE,LoyaltyProgrammeVO.SUBMODULE,
					LoyaltyProgrammeVO.ENTITY);

			auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
							auditVO,loyaltyProgramme,false);
			loyaltyProgramme.remove();
			auditVO = findLoyaltyProgrammeAuditVO(
					loyaltyProgramme,auditVO,loyaltyProgrammeVO);
			auditVO.setActionCode(DELLTYPGM);
			//auditVO.setAuditRemarks(AuditAction.DELETE.toString());
				 AuditUtils.performAudit(auditVO);



		}else if(LoyaltyProgrammeVO.OPERATION_FLAG_UPDATE.equals(loyaltyProgrammeVO.
				getOperationFlag())){
			Collection<LoyaltyParameterVO> parameterVOs = loyaltyProgrammeVO.
			getLoyaltyParameterVOs();
			log.log(Log.FINE, "\n\n parameterVOs ", parameterVOs);
			if(parameterVOs != null && parameterVOs.size() > 0){
				validateParameter(loyaltyProgrammeVO);
				/*for(LoyaltyParameterVO loyaltyParameterVO:parameterVOs){
					if(LoyaltyParameterVO.OPERATION_FLAG_INSERT.equals(loyaltyParameterVO.getOperationFlag())
							|| LoyaltyParameterVO.OPERATION_FLAG_UPDATE.equals(loyaltyParameterVO.
									getOperationFlag())){
						log.log(Log.FINE,"\n\n Going to call validateParameter since  parameterVOs > 0 \n\n");
						validateParameter(loyaltyProgrammeVO);
						break;
					}
				}*/
			}
			if(loyaltyProgrammeVO.isDateChanged()){
				checkCustomerAttached(loyaltyProgrammeVO);
			}
			LoyaltyProgramme loyaltyProgramme = LoyaltyProgramme.find(loyaltyProgrammeVO);
			log.log(Log.FINE," Before Going to perform Audit ");
			AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
					LoyaltyProgrammeVO.MODULE,LoyaltyProgrammeVO.SUBMODULE,
					LoyaltyProgrammeVO.ENTITY);
			auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
					auditVO,loyaltyProgramme,false);
			loyaltyProgramme.update(loyaltyProgrammeVO);
			auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
					auditVO,loyaltyProgramme,false);
			auditVO = findLoyaltyProgrammeAuditVO(
					loyaltyProgramme,auditVO,loyaltyProgrammeVO);
			auditVO.setActionCode(UPDLTYPGM);
			AuditUtils.performAudit(auditVO);
		}
		log.exiting("CustomerMgmntController","createLoyaltyProgramme");
	}

	/**
	 *
	 * @param loyaltyProgramme
	 * @param auditVO
	 * @param loyaltyProgrammeVO
	 * @return AttachLoyaltyProgrammeAuditVO
	 * @throws SystemException
	 */
	private AttachLoyaltyProgrammeAuditVO findLoyaltyProgrammeAuditVO(
			LoyaltyProgramme loyaltyProgramme,AttachLoyaltyProgrammeAuditVO auditVO,
			LoyaltyProgrammeVO loyaltyProgrammeVO) throws SystemException {
		log.log(Log.FINE,"---------setting AttachLoyaltyProgrammeAuditVO VO-------");
		LogonAttributes logonAttributes =
			 ContextUtils.getSecurityContext().getLogonAttributesVO();

		StringBuffer additionalInfo = new StringBuffer();
		auditVO.setCompanyCode(loyaltyProgramme.getLoyaltyProgrammePK().getCompanyCode());
		auditVO.setLoyaltyProgrammeCode(
				loyaltyProgramme.getLoyaltyProgrammePK().getLoyaltyProgrammeCode());
		auditVO.setTxnTime(new LocalDate(logonAttributes.getStationCode(),Location.STN, true));
		auditVO.setUserId(loyaltyProgramme.getLastUpdatedUser());

		if(LoyaltyProgrammeVO.OPERATION_FLAG_DELETE.equals(loyaltyProgrammeVO.getOperationFlag())) {
			log.log(Log.INFO,"Going to populate additional info");
			additionalInfo.append("Attribute  : ");
			additionalInfo.append(loyaltyProgrammeVO.getAttibute());
			additionalInfo.append(" , ");
			additionalInfo.append("Value  : ");
			additionalInfo.append(loyaltyProgrammeVO.getUnits());
			additionalInfo.append(" , ");
			additionalInfo.append("Period  : ");
			additionalInfo.append(loyaltyProgrammeVO.getFromDate());
			additionalInfo.append("-");
			additionalInfo.append(loyaltyProgrammeVO.getToDate());
		}



	/*	if(auditVO.getAuditFields() != null &&
				auditVO.getAuditFields().size() > 0) {

			for(AuditFieldVO auditField : auditVO.getAuditFields()) {
				if(auditField != null){
					additionalInfo
					.append(" || ")
					.append(auditField.getFieldName())
					.append(" : ")
					.append(auditField.getNewValue());
				}else{
					log.log(Log.WARNING,"auditField is NULL!!!!!!!!!!!!!!!!!!!");
				}
			}
		}
		*/
		auditVO.setAdditionalInformation(additionalInfo.toString());
		log.exiting("findAuditVO","...Finished construction of vo");
		return auditVO;

	}

	/**
	 * Checks whether loyalty programme attached to any customer or group
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 * @throws CustomersAlreadyAttatchedException
	 */
	private void checkLoyaltyProgrammeAttached(LoyaltyProgrammeVO loyaltyProgrammeVO)
	throws	SystemException,CustomersAlreadyAttatchedException{
		log.entering("Customer Management Controller","checkLoyaltyProgrammeAttached");
		Collection<String> programmeCodes =
			LoyaltyProgramme.checkLoyaltyProgrammeAttached(loyaltyProgrammeVO);
		if(programmeCodes != null && programmeCodes.size() > 0){
			ErrorVO errorVO = new ErrorVO(
					CustomersAlreadyAttatchedException.CUSTOMERS_ALREADY_ATTACHED);
			CustomersAlreadyAttatchedException customersAlreadyAttatchedException =
					new CustomersAlreadyAttatchedException();
			customersAlreadyAttatchedException.addError(errorVO);
			throw customersAlreadyAttatchedException;
		}
		log.exiting("Customer Management Controller","checkLoyaltyProgrammeAttached");
	}
	/**
	 * @author A-1883
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 * @throws CustomersAlreadyAttatchedException
	 */
	private void checkCustomerAttached(LoyaltyProgrammeVO loyaltyProgrammeVO)
	throws	SystemException,CustomersAlreadyAttatchedException{
		Collection<String> codes = LoyaltyProgramme.checkCustomerAttached(loyaltyProgrammeVO);
		if(codes != null && codes.size() > 0){
			throw new CustomersAlreadyAttatchedException();
		}
	}
	/**
	 * @author A-1883
	 * Validates parameter value
	 * @param loyaltyProgrammeVO
	 * @throws SystemException
	 * @throws ParameterExistsException
	 */
	private void validateParameter(
			LoyaltyProgrammeVO loyaltyProgrammeVO)throws
			SystemException ,ParameterExistsException {
		log.entering("CustomerMgmntController","validateParameter");
		Collection<LoyaltyProgrammeVO> programmeCodes =  LoyaltyProgramme.
		validateParameter(loyaltyProgrammeVO);
		if(programmeCodes != null && programmeCodes.size() > 0 ){
			log.log(Log.SEVERE,"\n\n Parameter Already Exists!!!!!");
			ErrorVO errorVo = null;
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			for(LoyaltyProgrammeVO programmeVO:programmeCodes){
				log.log(Log.INFO, "%%%%%%%%%programmeVO", programmeVO);
				/*errorVo = new ErrorVO(ParameterExistsException.
						PARAMETER_ALREADY_EXISTS);
				Object[] errorData = new Object[3];
				errorData[0] = programmeVO.getLoyaltyProgrammeCode();
				errorData[1] = programmeVO.getFromDate().toDisplayDateOnlyFormat();
				errorData[2] = programmeVO.getToDate().toDisplayDateOnlyFormat() ;
				errorVo.setErrorData(errorData);
				errorVOs.add(errorVo);
				*/
				errorVo = ErrorUtils.getError(
						ParameterExistsException.
						PARAMETER_ALREADY_EXISTS ,
						programmeVO.getLoyaltyProgrammeCode() ,
						programmeVO.getFromDate().toDisplayDateOnlyFormat() ,
						programmeVO.getToDate().toDisplayDateOnlyFormat());
				errorVOs.add(errorVo);
			}
			log.log(Log.INFO, "%%%%%%%%%errorVOs", errorVOs);
			ParameterExistsException parameterExistsException =
				new ParameterExistsException();
			parameterExistsException.addErrors(errorVOs);
			throw parameterExistsException;
		}
		log.exiting("CustomerMgmntController","validateParameter");
	}
	/**
	 * List Loyalty programmes based on Filter
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<LoyaltyProgrammeVO> listLoyaltyProgramme(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO)
			throws SystemException {
		log.entering("CustomerMgmntController","listLoyaltyProgramme");
		Collection<LoyaltyProgrammeVO> loyaltyProgramVOs =null;
		return new LoyaltyProgramme().listLoyaltyProgramme(loyaltyProgrammeFilterVO);
	}
	/**
	 *
	 * @param companyCode
	 * @return HashMap<String,Collection<LoyaltyAttributeVO>>
	 * @throws SystemException
	 */
	public HashMap<String,Collection<LoyaltyAttributeVO>> listLoyaltyAttributeDetails
	(String companyCode)throws SystemException {
		log.entering("CustomerMgmntController","listLoyaltyAttributeDetails");
		Collection<LoyaltyAttributeVO> loyaltyAttributeVOs = new
		CustomerLoyaltyAttribute().listLoyaltyAttributeDetails(companyCode);
		HashMap<String,Collection<LoyaltyAttributeVO>> attributeDetails = null;
		String currentAttribute = "";
		String previousAttribute = "";
		String key ="";
		Collection<LoyaltyAttributeVO> attributeVOs = null;
		if(loyaltyAttributeVOs != null && loyaltyAttributeVOs.size() >0){
			attributeDetails = new HashMap<String,Collection<LoyaltyAttributeVO>>();
			for(LoyaltyAttributeVO loyaltyAttributeVO:loyaltyAttributeVOs){
				currentAttribute = loyaltyAttributeVO.getAttribute();
				if(!currentAttribute.equals(previousAttribute)){
					attributeVOs = new ArrayList<LoyaltyAttributeVO>();
					key = loyaltyAttributeVO.getAttribute();
					attributeDetails.put(key,attributeVOs);
					previousAttribute = currentAttribute;
				}
				attributeVOs.add(loyaltyAttributeVO);
			}
		}
		log.exiting("CustomerMgmntController","listLoyaltyAttributeDetails");
		return attributeDetails;
	}
	/**
	 *
	 * @param customerGroupVOs
	 * @return
	 * @throws SystemException
	 */
/*	public Collection<String> saveCustomerGroup(Collection<CustomerLoyaltyGroupVO>
	customerGroupVOs) throws SystemException {
		/*
		log.entering("CustomerMgmntController","saveCustomerGroup");
		Collection<String> groupCodes = new ArrayList<String>();

		if(customerGroupVOs != null && customerGroupVOs.size() >0) {
			for(CustomerLoyaltyGroupVO groupVO : customerGroupVOs) {
				if(CustomerLoyaltyGroupVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(groupVO.getOperationalFlag())) {
					CustomerLoyaltyGroup customerGroup = null;

					customerGroup = CustomerLoyaltyGroup.find(
							groupVO.getCompanyCode(),
							groupVO.getGroupCode());
					customerGroup.remove();
				}
			}
			for(CustomerLoyaltyGroupVO groupVO : customerGroupVOs) {
				if(CustomerLoyaltyGroupVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(groupVO.getOperationalFlag())) {
					 new CustomerLoyaltyGroup(groupVO);
					String groupCode = groupVO.getGroupCode();
					log.log(Log.FINE,"groupCode is"+groupCode);
					groupCodes.add(groupCode);

				}else if(CustomerLoyaltyGroupVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(groupVO.getOperationalFlag())) {
					CustomerLoyaltyGroup customerGroup = null;

					customerGroup = CustomerLoyaltyGroup.find(
							groupVO.getCompanyCode(),
							groupVO.getGroupCode());

					customerGroup.update(groupVO);

				}
			}
		}

		log.exiting("CustomerMgmntController","saveCustomerGroup");
		return groupCodes;

		return null;
	}*/
	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupVO>
	 * @throws SystemException
	 */
/*	public  Collection<CustomerLoyaltyGroupVO> listCustomerGroup(String companyCode ,
			String groupCode) throws SystemException {
		/*
		log.entering("CustomerMgmntController","listCustomerGroup");
		return CustomerLoyaltyGroup.listCustomerGroup(
				companyCode,groupCode);

		return null;
	}*/

	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
/*	public Page<CustomerLoyaltyGroupVO> customerGroupLOV(String companyCode ,
			String groupCode,int pageNumber)
			throws SystemException {
		/*
		log.entering("CustomerMgmntController","customerGroupLOV");
		return CustomerLoyaltyGroup.customerGroupLOV(
				companyCode,groupCode, pageNumber);

		return null;
	}*/


	/**
	 *
	 * @param tempCustomerVOs
	 *
	 * @return String
	 * @throws SystemException
	 */
	public String saveTempCustomer(Collection<TempCustomerVO> tempCustomerVOs)
	throws SystemException {
		log.entering("CustomerMgmntController","saveTempCustomer");
		SharedCustomerProxy  sharedCustomerProxy = new SharedCustomerProxy();

		try {
			return sharedCustomerProxy.saveTempCustomer(tempCustomerVOs);
		} catch (ProxyException e) {
			// To be reviewed Auto-generated catch block
			throw new SystemException(e.getMessage(),e);
		}
		/*
		String tempCustCode =null;
		if(TempCustomerVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(tempCustomerVO.getOperationalFlag())) {
			TempCustomer tempCustomer = new TempCustomer();
			tempCustomer = TempCustomer.find(
					tempCustomerVO.getCompanyCode(),
					tempCustomerVO.getTempCustCode());
			tempCustomer.remove();
		}else if(TempCustomerVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(tempCustomerVO.getOperationalFlag())) {
			new TempCustomer(tempCustomerVO);
			tempCustCode = tempCustomerVO.getTempCustCode();
		}else if(TempCustomerVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(tempCustomerVO.getOperationalFlag())) {
			TempCustomer tempCustomer = new TempCustomer();
			tempCustomer = TempCustomer.find(
					tempCustomerVO.getCompanyCode(),
					tempCustomerVO.getTempCustCode());
			tempCustomer.update(tempCustomerVO);
		}
		return tempCustCode;
		*/
	}

	/**
	 *
	 * @param companyCode
	 * @param tempCustCode
	 * @return
	 * @throws SystemException
	 */
	public TempCustomerVO listTempCustomer(String companyCode,
			String tempCustCode)  throws SystemException {
		log.entering("CustomerMgmntController","listTempCustomer");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		try {
			return sharedCustomerProxy.listTempCustomer(companyCode,tempCustCode);
		} catch (ProxyException e) {
			// To be reviewed Auto-generated catch block
			throw new SystemException(e.getMessage(),e);
		}

	//	return null;
	//	return TempCustomer.listTempCustomer(companyCode,tempCustCode);
	}

	/**
	 *
	 * @param listTempCustomerVO
	 * @return TempCustomerVO
	 * @throws SystemException
	 */
	public Page<TempCustomerVO> listTempCustomerDetails(ListTempCustomerVO
			listTempCustomerVO)   throws SystemException {
		log.entering("CustomerMgmntController","listTempCustomerDetails");

		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		try {
			return sharedCustomerProxy.listTempCustomerDetails(listTempCustomerVO);
		} catch (ProxyException e) {
			// To be reviewed Auto-generated catch block
			throw new SystemException(e.getMessage(),e);
		}
	//	return TempCustomer.listTempCustomerDetails(
	//			listTempCustomerVO);
	//	return null;
	}

	/**
	 *
	 * @param groupLoyaltyProgrammeVOs
	 * @throws SystemException
	 * @throws CustomerAlreadyAttachedToLoyaltyException
	 */
	public void saveGroupLoyaltyPgm(Collection<CustomerGroupLoyaltyProgrammeVO>
	groupLoyaltyProgrammeVOs) throws SystemException,CustomerAlreadyAttachedToLoyaltyException{
		log.entering("CustomerMgmntController","saveGroupLoyaltyPgm");
		String result = null;
		String startDate = null;
		String endDate = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(groupLoyaltyProgrammeVOs != null && groupLoyaltyProgrammeVOs.size() >0) {
			for(CustomerGroupLoyaltyProgrammeVO groupLoyaltyProgrammeVO: groupLoyaltyProgrammeVOs) {
				if(CustomerGroupLoyaltyProgrammeVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(groupLoyaltyProgrammeVO.getOperationalFlag())) {
					CustomerGroupLoyaltyProgramme groupLoyaltyProgramme = null;
					groupLoyaltyProgramme =CustomerGroupLoyaltyProgramme.find(
							groupLoyaltyProgrammeVO.getCompanyCode(),
							groupLoyaltyProgrammeVO.getGroupCode(),
							groupLoyaltyProgrammeVO.getLoyaltyProgramCode(),
							groupLoyaltyProgrammeVO.getSequenceNumber());
					groupLoyaltyProgramme.remove();
				}
			}
			for(CustomerGroupLoyaltyProgrammeVO groupLoyaltyProgrammeVO: groupLoyaltyProgrammeVOs) {
				if(CustomerGroupLoyaltyProgrammeVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(groupLoyaltyProgrammeVO.getOperationalFlag())) {
				result =	AttachLoyaltyProgramme.checkForCustomerLoyalty(groupLoyaltyProgrammeVO);
				if("1".equalsIgnoreCase(result)) {
					startDate = TimeConvertor.toStringFormat(groupLoyaltyProgrammeVO.getLoyaltyFromDate().toCalendar(),
				   			TimeConvertor.CALENDAR_DATE_FORMAT);
					endDate = TimeConvertor.toStringFormat(groupLoyaltyProgrammeVO.getLoyaltyToDate().toCalendar(),
				   			TimeConvertor.CALENDAR_DATE_FORMAT);
					ErrorVO error = ErrorUtils.getError(CustomerAlreadyAttachedToLoyaltyException.CUSTOMER_ATTACHED_TOLOYALTY,
							startDate,
							endDate ,
							groupLoyaltyProgrammeVO.getLoyaltyProgramCode());
                    errors.add(error);

				}
					new CustomerGroupLoyaltyProgramme(groupLoyaltyProgrammeVO);
				}else if(CustomerGroupLoyaltyProgrammeVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(groupLoyaltyProgrammeVO.getOperationalFlag())) {
					result =	AttachLoyaltyProgramme.checkForCustomerLoyalty(groupLoyaltyProgrammeVO);
					if("1".equalsIgnoreCase(result)) {
						startDate = TimeConvertor.toStringFormat(groupLoyaltyProgrammeVO.getLoyaltyFromDate().toCalendar(),
					   			TimeConvertor.CALENDAR_DATE_FORMAT);
						endDate = TimeConvertor.toStringFormat(groupLoyaltyProgrammeVO.getLoyaltyToDate().toCalendar(),
					   			TimeConvertor.CALENDAR_DATE_FORMAT);
						ErrorVO error = ErrorUtils.getError(CustomerAlreadyAttachedToLoyaltyException.CUSTOMER_ATTACHED_TOLOYALTY,
								startDate,
								endDate ,
								groupLoyaltyProgrammeVO.getLoyaltyProgramCode());
	                    errors.add(error);
					}

					CustomerGroupLoyaltyProgramme groupLoyaltyProgramme = null;
					groupLoyaltyProgramme =CustomerGroupLoyaltyProgramme.find(
							groupLoyaltyProgrammeVO.getCompanyCode(),
							groupLoyaltyProgrammeVO.getGroupCode(),
							groupLoyaltyProgrammeVO.getLoyaltyProgramCode(),
							groupLoyaltyProgrammeVO.getSequenceNumber());
					groupLoyaltyProgramme.update(groupLoyaltyProgrammeVO);
				}
			}

		}
		if(errors.size() > 0){
			CustomerAlreadyAttachedToLoyaltyException  exception =
				new CustomerAlreadyAttachedToLoyaltyException();
			exception.addErrors(errors);
    		throw exception;
    	}
	}


	/**
	 *
	 * @param companyCode
	 * @param groupCode
	 * @return Collection<CustomerGroupLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<CustomerGroupLoyaltyProgrammeVO> listGroupLoyaltyPgm(
			String companyCode,String groupCode)
			throws SystemException{
		log.entering("CustomerMgmntController","listGroupLoyaltyPgm");
		Collection<CustomerGroupLoyaltyProgrammeVO> groupLoyaltyProgrammeVOs= null;

		return CustomerGroupLoyaltyProgramme.listGroupLoyaltyPgm(companyCode, groupCode);
	}
	/**
	 * Lists All Loyalty Programmes based on Filter
	 * @author A-1883
	 * @param loyaltyProgrammeFilterVO
	 * @param pageNumber
	 * @return Page<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public  Page<LoyaltyProgrammeVO> listAllLoyaltyProgrammes(
			LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO,int pageNumber)
			throws SystemException {
		log.entering("CustomerMgmntController","listAllLoyaltyProgrammes");
		Page<LoyaltyProgrammeVO> loyaltyProgramVOs =null;
		return LoyaltyProgramme.listAllLoyaltyProgrammes(loyaltyProgrammeFilterVO, pageNumber);
	}
	/**
	 * @author A-1883
	 * @param airWayBillLoyaltyProgramVO
	 * @throws SystemException
	 */
	/*public void generateLoyaltyPointsForAWB(AirWayBillLoyaltyProgramVO
			airWayBillLoyaltyProgramVO)
	throws SystemException {
		log.entering("CustomerMgmntController","generateLoyaltyPointsForAWB");
		calculateLoyaltyPoints(airWayBillLoyaltyProgramVO);
		log.exiting("CustomerMgmntController","generateLoyaltyPointsForAWB");
	}*/
	/**
	 * @param airWayBillLoyaltyProgramVO
	 * @throws SystemException
	 */
	/*private void calculateLoyaltyPoints(AirWayBillLoyaltyProgramVO
			airWayBillLoyaltyProgramVO) throws SystemException {
		log.entering("CustomerMgmntController","calculateLoyaltyPoints");
		log.log(Log.FINE, "\n AirWayBillLoyaltyProgramVO from AWB :",
				airWayBillLoyaltyProgramVO);
		Collection<LoyaltyProgrammeVO> customerLoyaltyVOs =
			findAttachedLoyaltyProgrammes(airWayBillLoyaltyProgramVO);
		log.log(Log.FINE, "\ncustomerLoyaltyVOs :", customerLoyaltyVOs);
		boolean isValid = false ;
		if(customerLoyaltyVOs != null && customerLoyaltyVOs.size() > 0){
			for(LoyaltyProgrammeVO loyaltyVO:customerLoyaltyVOs){
				isValid = true ;
				LoyaltyProgramme loyaltyProgramme = LoyaltyProgramme.find(loyaltyVO);
				if(AirWayBillLoyaltyProgramVO.ACTIVE_STATUS.equals(
						loyaltyProgramme.getActiveStatus())){
					Collection<LoyaltyParameter> parameters =
						loyaltyProgramme.getLoyaltyParameters();
					Collection<ParameterVO> parameterVOs = airWayBillLoyaltyProgramVO.
					getParameterVOs();
					if(parameterVOs != null && parameterVOs.size() >0
							&& parameters != null && parameters.size() > 0 ){
						log.log(Log.FINE, "\nFrom AWB parameterVOs : ",
								parameterVOs);
						log.log(Log.FINE, "Flag Check::1", isValid);
						for(LoyaltyParameter parameter:parameters){
							if(isValid){
								isValid = false;
								String pgmParamCode = parameter.getLoyaltyParameterPK().getParameterCode();
								log.log(Log.FINE, "Programme Parameter Code :",
										pgmParamCode);
								for(ParameterVO parameterVO:parameterVOs){
									String parameterCode = parameterVO.getParameter();
									log.log(Log.FINE, "AWB Parameter Code :",
											parameterCode);
									if(pgmParamCode.equals(parameterCode)){
										String value = parameter.getParameterValue();
										log.log(Log.FINE,"Parameter Code Match Found ");
										log
												.log(
														Log.FINE,
														"Loyalty Programme parameterValue: ",
														value);
										//  Distance and Revenue special case
										if(ParameterVO.DISTANCE.equals(parameterCode) ||
												ParameterVO.REVENUE.equals(parameterCode) ){
											log.log(Log.FINE," Special Parameter ");
											String unit = findParameterUnit(airWayBillLoyaltyProgramVO.getCompanyCode(),
													parameterCode);
											ArrayList<String> values = (ArrayList<String>)parameterVO.getParameterValues();
											int parameterValue = Integer.valueOf(values.get(0)).intValue();
											if(!unit.equals(parameterVO.getUnit())){
												parameterValue = findParameterValue(airWayBillLoyaltyProgramVO.getCompanyCode(),
														parameterCode,parameterVO.getUnit(),
														unit,parameterValue);
											}
											if(parameterValue > Integer.valueOf(value).intValue()){
												log.log(Log.FINE," Special Parameter is Valid");
												isValid = true;
											}else {
												log.log(Log.FINE," Special Parameter is Not Valid");
												isValid = false;
											}
											break;
										}// Other Parameter Validation
										else{
											if(parameterVO.getParameterValues().contains(value)){
												log
														.log(
																Log.FINE,
																"AWB Parameter Value  : ",
																value);
												log.log(Log.FINE," Parameter Value Match Found ");
												isValid = true;
											}else{
												log.log(Log.FINE," Parameter Value No Match Found ");
												isValid = false;
											}
											break;
										}
									}
								}//For LOOP  VOs
							}else{
								log.log(Log.FINE," Loyalty Programme Match Not Found ");
								break;
							}
						}  // For LOOP Entity
					}  // Null check
					if(isValid){
						saveAwbLoyaltyProgram(loyaltyProgramme,airWayBillLoyaltyProgramVO);
					}
				}// Active check
			}// For Loop
		}
		log.exiting("CustomerMgmntController","calculateLoyaltyPoints");
	}*/
	/**
	 * finds parameter value in default unit
	 * @param companyCode
	 * @param unitType
	 * @param fromUnit
	 * @param toUnit
	 * @param fromValue
	 * @return int
	 * @throws SystemException
	 */
	/*private int findParameterValue(String companyCode,
			String unitType,String fromUnit,String toUnit,double fromValue)
		throws SystemException {
		int parameterValue = 0;
		log.entering("CustomerMgmntController","findParameterValue");
		try{
			if(ParameterVO.DISTANCE.equals(unitType) || LoyaltyAttributeVO.VOLUME.equals(unitType)
					|| LoyaltyAttributeVO.WEIGHT.equals(unitType)){
				SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
				parameterValue = (int)sharedDefaultsProxy.findConvertedUnitValue(
						companyCode,unitType,fromUnit,toUnit,fromValue).getToValue();
			}else if(ParameterVO.REVENUE.equals(unitType)||
					LoyaltyAttributeVO.YIELD.equals(unitType)){
				SharedCurrencyProxy sharedCurrencyProxy = new SharedCurrencyProxy();
				
				//Modified for exchange rate change
				CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();
	   			currencyConvertorVO.setCompanyCode(companyCode);
	   			currencyConvertorVO.setFromCurrencyCode(fromUnit);
	   			currencyConvertorVO.setToCurrencyCode(toUnit);
	   			currencyConvertorVO.setRatingBasisType(CurrencyConvertorVO.DAILYBANKERSRATE);	   			
				
				double conversionRate = sharedCurrencyProxy.findConversionRate(
						currencyConvertorVO).doubleValue();
				parameterValue = (int)(conversionRate*fromValue);
			}
		}catch(ProxyException proxyException){
			log.log(Log.SEVERE," ProxyException ");
			for(ErrorVO errorVO : proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
		log.exiting("CustomerMgmntController","findParameterValue");
		return parameterValue;
	}*/
	/**
	  * Finds unit of pameter (Distance and Revenue)
	  * @author A-1883
	  * @param companyCode
	  * @param parameterCode
	  * @return String
	  * @throws SystemException
	  */
	 /*private String findParameterUnit(String companyCode,String parameterCode)
	 throws SystemException {
		 log.entering("CustomerMgmntController","findParameterUnit");
		 return LoyaltyProgramme.findParameterUnit(companyCode,parameterCode);
	 }*/
/**
 *
 * @param companyCode
 * @param customerCode
 * @param points
 * @return
 * @throws SystemException
 */
	/*private String accumulateCustomerPoints(String companyCode,
			String customerCode,double points)throws SystemException {
		log. entering("CustomerMgmntController","accumulateCustomerPoints");
		String groupCode = null ;
		try{
			SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
			groupCode = sharedCustomerProxy.addCustomerPoints(
					companyCode,customerCode,points);
			log.log(Log.FINE, "Group Code : ", groupCode);
		}catch(ProxyException proxyException){
			log.log(Log.SEVERE," ProxyException ");
			for(ErrorVO errorVO : proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
		log.exiting("CustomerMgmntController","accumulateCustomerPoints");
		return groupCode;
	}*/
	/**
	 * This method adds points obtained to group points
	 * @param companyCode
	 * @param groupCode
	 * @param points
	 * @throws SystemException
	 */
	/*private void accumulateGroupPoints(String companyCode,
			String groupCode,double points)throws SystemException {
		log. entering("CustomerMgmntController","accumulateGroupPoints");
		try{
			SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
			sharedCustomerProxy.addGroupPoints(companyCode,groupCode,points);
		}catch(ProxyException proxyException){
			log.log(Log.SEVERE," ProxyException ");
			for(ErrorVO errorVO : proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
		log.exiting("CustomerMgmntController","accumulateGroupPoints");
	}*/
	/**
	 * Calculates point and Persists
	 * also updates customer and group pionts
	 * @param loyaltyProgramme
	 * @param airWayBillLoyaltyProgramVO
	 * @throws SystemException
	 */
	/*private void saveAwbLoyaltyProgram(LoyaltyProgramme loyaltyProgramme,
			AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO)
	throws SystemException {
		log. entering("CustomerMgmntController","saveAwbLoyaltyProgram");
		String companyCode = airWayBillLoyaltyProgramVO.getCompanyCode();
		for(LoyaltyAttributeVO loyaltyAttributeVO:airWayBillLoyaltyProgramVO.getAttributeVOs()){
			if(loyaltyProgramme.getAttibute().equals(loyaltyAttributeVO.getAttribute())){
				double points = 0.0;
				//Modified by A-5374 for ICAST Error
				if(loyaltyAttributeVO.getUnit().equals(loyaltyProgramme.getUnits())){
					points =(loyaltyAttributeVO.getValue()/loyaltyProgramme.getAmount())
					*loyaltyProgramme.getPoints();
					log.log(Log.FINE, " Points Accrued :", points);

				}else{
					String unitType = findUnitType(loyaltyAttributeVO.getAttribute());
					log.log(Log.FINE, " Unit Type :", unitType);
					int attributeValue = findParameterValue(companyCode,
							unitType,loyaltyAttributeVO.getUnit(),loyaltyProgramme.getUnits(),
							loyaltyAttributeVO.getValue());
					log.log(Log.FINE, " Converted Value :", attributeValue);
					points =(attributeValue/loyaltyProgramme.getAmount())
								*loyaltyProgramme.getPoints();
					log.log(Log.FINE, " Points Accrued :", points);
				}
				if(points > 0) {
					airWayBillLoyaltyProgramVO.setPointsAccrued(points);
					airWayBillLoyaltyProgramVO.setLoyaltyAttributeUnit(loyaltyProgramme.getUnits());
					airWayBillLoyaltyProgramVO.setLoyaltyAttribute(loyaltyProgramme.getAttibute());
					airWayBillLoyaltyProgramVO.setLoyaltyProgrammeCode(loyaltyProgramme.
							getLoyaltyProgrammePK().getLoyaltyProgrammeCode());
					log.log(Log.FINE,
							" AirWayBillLoyaltyProgramVO Before Save :",
							airWayBillLoyaltyProgramVO);
					AirWayBillLoyaltyProgram airWayBillLoyaltyProgram= null;
				//	AirWayBillLoyaltyProgramAuditVO awbLoyaltyAuditVO =
				//		new AirWayBillLoyaltyProgramAuditVO(AirWayBillLoyaltyProgramVO.MODULE,
				//				AirWayBillLoyaltyProgramVO.SUBMODULE,
				//				AirWayBillLoyaltyProgramVO.ENTITY);
					try{
						airWayBillLoyaltyProgram = AirWayBillLoyaltyProgram.
								find(airWayBillLoyaltyProgramVO);
			//			awbLoyaltyAuditVO =(AirWayBillLoyaltyProgramAuditVO)AuditUtils.populateAuditDetails(
			//					awbLoyaltyAuditVO,airWayBillLoyaltyProgram,false);
						airWayBillLoyaltyProgram.update(airWayBillLoyaltyProgramVO);
			//			awbLoyaltyAuditVO =(AirWayBillLoyaltyProgramAuditVO)AuditUtils.populateAuditDetails(
			//					awbLoyaltyAuditVO,airWayBillLoyaltyProgram,false);
			//		    awbLoyaltyAuditVO.setActionCode(AuditVO.UPDATE_ACTION);
					}catch(FinderException finderException){
						airWayBillLoyaltyProgram = new AirWayBillLoyaltyProgram(
								airWayBillLoyaltyProgramVO);
			//			awbLoyaltyAuditVO =(AirWayBillLoyaltyProgramAuditVO)AuditUtils.populateAuditDetails(
			//					awbLoyaltyAuditVO,airWayBillLoyaltyProgram,true);
			//			    awbLoyaltyAuditVO.setActionCode(AuditVO.CREATE_ACTION);
					}
			//		collectAwbAuditDetails(airWayBillLoyaltyProgram,awbLoyaltyAuditVO);
			//		AuditUtils.performAudit(awbLoyaltyAuditVO);
					String groupCode = accumulateCustomerPoints(airWayBillLoyaltyProgramVO.
							getCompanyCode(),airWayBillLoyaltyProgramVO.getCustomerCode(),
							airWayBillLoyaltyProgramVO.getPointsAccrued());
					if(groupCode != null && groupCode.trim().length()>0){
						accumulateGroupPoints(airWayBillLoyaltyProgramVO.getCompanyCode(),
								groupCode,airWayBillLoyaltyProgramVO.getPointsAccrued());
					}
				}
			}
		}
		log.exiting("CustomerMgmntController","saveAwbLoyaltyProgram");
	}*/
	/**
	 * @param airWayBillLoyaltyProgram
	 * @param awbLoyaltyAuditVO
	 * @throws SystemException
	 */

	/**
	 * find Unit Type
	 * @param attriute
	 * @return String
	 */
	/*private String findUnitType(String attriute){
		log.entering("CustomerMgmntController","findUnitType");
		String unitType = null;
		if(LoyaltyAttributeVO.WEIGHT_ATTRIBUTE.equals(attriute)){
			unitType = LoyaltyAttributeVO.WEIGHT;
		}else if(LoyaltyAttributeVO.VOLUME_ATTRIBUTE.equals(attriute)){
			unitType = LoyaltyAttributeVO.VOLUME;
		}else if(LoyaltyAttributeVO.DISTANCE_ATTRIBUTE.equals(attriute)){
			unitType = LoyaltyAttributeVO.DISTANCE;
		}else if(LoyaltyAttributeVO.YIELD_ATTRIBUTE.equals(attriute)){
			unitType = LoyaltyAttributeVO.YIELD;
		}else{
			// Revnue Attribute
			unitType = LoyaltyAttributeVO.YIELD;
		}
		log.exiting("CustomerMgmntController","findUnitType");
		return unitType;
	}*/
	/**
	 * @param airWayBillLoyaltyProgramVO
	 * @return Collection<LoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	/*private Collection<LoyaltyProgrammeVO> findAttachedLoyaltyProgrammes(
			AirWayBillLoyaltyProgramVO airWayBillLoyaltyProgramVO)
			throws SystemException {
		log.entering("CustomerMgmntController","findAttachedLoyaltyProgrammes");
		return LoyaltyProgramme.findAttachedLoyaltyProgrammes(airWayBillLoyaltyProgramVO);
	}*/
	/**
	 *
	 * @param customerVO
	 * @return
	 * @throws SystemException
	 * @throws CustomerBusinessException
	 */
	public  String registerCustomer(CustomerVO customerVO)
	throws SystemException,CustomerBusinessException{
		log.entering("CustomerMgmntController","registerCustomer");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		try{
			return sharedCustomerProxy.saveCustomer(customerVO);
		}catch(ProxyException proxyException){
			throw new CustomerBusinessException(proxyException);
		}catch(SystemException exception){//A-5273 Added for ICRD-59741 Start			
			if (exception.getErrors() != null
					&& !exception.getErrors().isEmpty()) {
				for (ErrorVO errorVO : exception.getErrors()) {
					if (("STLDTA").equals(errorVO.getErrorCode())) {
						if(errorVO.getErrorData() != null){
							for(Object data :errorVO.getErrorData()){
								if(data == null && customerVO != null){
									errorVO.setErrorData(new Object[]{customerVO.getCustomerCode()});								}
							}							
						}
						
					}
				}
			}
			throw exception;
			//A-5273 Added for ICRD-59741 End
		}
	}
	/**
	 *
	 * @param customerFilterVO
	 * @return CustomerVO
	 * @throws SystemException
	 */
	public CustomerVO listCustomer(CustomerFilterVO customerFilterVO)
	throws SystemException{
		log.entering("CustomerMgmntController","listCustomer");
		/*	CustomerVO customerVO = new CustomerVO();
		if("Y".equalsIgnoreCase(customerFilterVO.getTempCustomerFlag())) {
			TempCustomerVO tempCustomerVO = TempCustomer.listTempCustomer(
					customerFilterVO.getCompanyCode(),
					customerFilterVO.getCustomerCode());
			customerVO.setCompanyCode(customerFilterVO.getCompanyCode());
			customerVO.setCustomerCode(customerFilterVO.getCustomerCode());
			customerVO.setTelephone(tempCustomerVO.getPhoneNumber());
			customerVO.setCustomerName(tempCustomerVO.getTempCustName());
			customerVO.setStatus(tempCustomerVO.getActiveStatus());
			customerVO.setStationCode(tempCustomerVO.getStation());
			customerVO.setAddress1(tempCustomerVO.getAddress());
			customerVO.setEmail(tempCustomerVO.getEmailAddress());

			return customerVO;

		}*/

		SharedCustomerProxy sharedCustomerProxy = new
		SharedCustomerProxy();
		try{
			return sharedCustomerProxy.listCustomer(customerFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}

	}
	/**
	 *
	 * @param customerListFilterVO
	 * @return Page<CustomerVO>
	 * @throws SystemException
	 */
	public Page<CustomerVO> listCustomerDetails(CustomerListFilterVO
			customerListFilterVO) throws SystemException{
		log.entering("CustomerMgmntController","listCustomerDetails");
		SharedCustomerProxy sharedCustomerProxy = new
		SharedCustomerProxy();
		try{
			return sharedCustomerProxy.listCustomerDetails(customerListFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
	/**
	 *
	 * @param customerGroupVOs
	 * @throws SystemException
	 * @throws CustomerAttachedToGroupException
	 */
 /*	public void checkForCustomer(Collection<CustomerLoyaltyGroupVO>
	customerGroupVOs) throws SystemException,CustomerAttachedToGroupException{
		/*
		log.entering("CustomerMgmntController","listCustomerDetails");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<String> groupCodes = new ArrayList<String>();
		if(customerGroupVOs != null && customerGroupVOs.size() >0) {
			for(CustomerLoyaltyGroupVO groupVO :customerGroupVOs) {
				String groupCode = CustomerLoyaltyGroup.checkForCustomer(groupVO);
				if(groupCode != null && groupCode.trim().length() >0) {
					groupCodes.add(groupCode);
				}
			}
		}
		if(groupCodes != null && groupCodes.size() >0) {
			for(String groupCode :groupCodes) {
				ErrorVO error = ErrorUtils.getError(
						CustomerAttachedToGroupException.CUSTOMER_ATTACHED_TOGROUP,
						groupCode);
				errors.add(error);
			}
			CustomerAttachedToGroupException exception =
				new CustomerAttachedToGroupException();
			exception.addErrors(errors);
			throw exception;
		}

	}*/
	/**
	 *
	 * @param companyCode
	 * @param customerCode
	 * @param groupCode
	 * @return  Collection<AttachLoyaltyProgrammeVO>
	 * @throws SystemException
	 */
	public Collection<AttachLoyaltyProgrammeVO> listLoyaltyPgmToCustomers(
			String companyCode,String customerCode,String groupCode)
			throws SystemException{
		log.entering("CustomerMgmntController","listLoyaltyPgmToCustomers");
		Collection<AttachLoyaltyProgrammeVO> attachLoyaltyProgrammeVOs = null;


		return AttachLoyaltyProgramme.listLoyaltyPgmToCustomers(companyCode, customerCode, groupCode);
	}
	/**
	 * @author A-1883
	 * @param listPointsAccumulatedFilterVO
	 * @param pageNumber
	 * @return Page<ListCustomerPointsVO>
	 * @throws SystemException
	 */
	public  Page<ListCustomerPointsVO> listLoyaltyPointsForAwb(ListPointsAccumulatedFilterVO
			listPointsAccumulatedFilterVO,int pageNumber)
			throws SystemException {
		log.entering("CustomerMgmntController","listLoyaltyPointsForAwb");
		return AirWayBillLoyaltyProgram.listLoyaltyPointsForAwb(
				listPointsAccumulatedFilterVO,pageNumber);
	}
	/**
	 *
	 * @param attachLoyaltyProgrammeVOs
	 * @throws SystemException
	 */
	public void saveLoyaltyPgmToCustomers( Collection<AttachLoyaltyProgrammeVO>
	attachLoyaltyProgrammeVOs)
	throws SystemException{
		Collection<AttachLoyaltyProgrammeVO> programPointVOs =  new
		                               ArrayList<AttachLoyaltyProgrammeVO>();
		log.entering("CustomerMgmntController","saveLoyaltyPgmToCustomers");
		double points =0;
		if(attachLoyaltyProgrammeVOs != null &&
				attachLoyaltyProgrammeVOs.size() >0) {
			for(AttachLoyaltyProgrammeVO programmeVO : attachLoyaltyProgrammeVOs) {
				if(AttachLoyaltyProgrammeVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(programmeVO.getOperationFlag())) {

					AttachLoyaltyProgramme attachLoyaltyProgramme =null;
					attachLoyaltyProgramme =AttachLoyaltyProgramme.find(programmeVO);
					AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
							AttachLoyaltyProgrammeVO.MODULE,AttachLoyaltyProgrammeVO.SUBMODULE,AttachLoyaltyProgrammeVO.ENTITY);

					auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
									auditVO,attachLoyaltyProgramme,false);
					attachLoyaltyProgramme.remove();
					auditVO = findAttachLoyaltyProgrammeAuditVO(
							attachLoyaltyProgramme,auditVO,programmeVO);
					auditVO.setActionCode(DELCUSLTYPGM);
				//	auditVO.setAuditRemarks(AuditAction.DELETE.toString());
						 AuditUtils.performAudit(auditVO);
				}
			}
			for(AttachLoyaltyProgrammeVO programmeVO : attachLoyaltyProgrammeVOs) {
				if(AttachLoyaltyProgrammeVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(programmeVO.getOperationFlag())) {
					AttachLoyaltyProgramme attachLoyaltyProgramme =null;
					attachLoyaltyProgramme =AttachLoyaltyProgramme.find(programmeVO);

					 AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
								AttachLoyaltyProgrammeVO.MODULE,AttachLoyaltyProgrammeVO.SUBMODULE,AttachLoyaltyProgrammeVO.ENTITY);

					auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
										auditVO,attachLoyaltyProgramme,false);
					attachLoyaltyProgramme.update(programmeVO);
					auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
							auditVO,attachLoyaltyProgramme,false);
					auditVO = findAttachLoyaltyProgrammeAuditVO(
							attachLoyaltyProgramme,auditVO,programmeVO);

					auditVO.setActionCode(UPDCUSLTYPGM);
				//	auditVO.setAuditRemarks(AuditAction.UPDATE.toString());
					AuditUtils.performAudit(auditVO);

				}else if(AttachLoyaltyProgrammeVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(programmeVO.getOperationFlag())) {
					log.log(Log.INFO, "programmeVO&&&&&&&&&&&&&&&&",
							programmeVO);
					if(programmeVO.isEntryPoints()){
					    programPointVOs.add(programmeVO);
					}


					AttachLoyaltyProgramme attachLoyaltyProgramme = null;
					attachLoyaltyProgramme= new AttachLoyaltyProgramme(programmeVO);


                  AttachLoyaltyProgrammeAuditVO auditVO = new AttachLoyaltyProgrammeAuditVO(
							AttachLoyaltyProgrammeVO.MODULE,AttachLoyaltyProgrammeVO.SUBMODULE,AttachLoyaltyProgrammeVO.ENTITY);

					auditVO = (AttachLoyaltyProgrammeAuditVO)AuditUtils.populateAuditDetails(
									auditVO,attachLoyaltyProgramme,true);
					auditVO = findAttachLoyaltyProgrammeAuditVO(
							attachLoyaltyProgramme,auditVO,programmeVO);
					auditVO.setActionCode(CRTCUSLTYPGM);
					//auditVO.setAuditRemarks(AuditAction.INSERT.toString());
					AuditUtils.performAudit(auditVO);

				}
			}
		}

		if(programPointVOs != null && programPointVOs.size() >0) {
			log.log(Log.INFO,"INSIDE IF ->programPointVOs");
			String companyCode =null;
			String customerCode =null;
			for(AttachLoyaltyProgrammeVO pgmVO : programPointVOs) {
				companyCode = pgmVO.getCompanyCode();
				customerCode = pgmVO.getCustomerCode();
				break;
			}
			points = LoyaltyProgramme.findEntryPoints(programPointVOs);
			log.log(Log.FINE, "points", points);
			SharedCustomerProxy sharedCustomerProxy = new
			SharedCustomerProxy();


			try {
			 	sharedCustomerProxy.addCustomerPoints(companyCode,
						customerCode,points);
			}catch (ProxyException e) {
				throw new SystemException(e.getMessage(),e);
			}
		}
	}
	/**
	 *
	 * @param loyaltyProgramme
	 * @param auditVO
	 * @param programmeVO
	 * @return AttachLoyaltyProgrammeAuditVO
	 * @throws SystemException
	 */
	private AttachLoyaltyProgrammeAuditVO findAttachLoyaltyProgrammeAuditVO(
			AttachLoyaltyProgramme loyaltyProgramme,AttachLoyaltyProgrammeAuditVO auditVO,
			AttachLoyaltyProgrammeVO programmeVO) throws SystemException {
		log.log(Log.FINE,"---------setting AttachLoyaltyProgrammeAuditVO VO-------");
		LogonAttributes logonAttributes =
			 ContextUtils.getSecurityContext().getLogonAttributesVO();

		StringBuffer additionalInfo = new StringBuffer();
		auditVO.setCompanyCode(loyaltyProgramme.getAttachLoyaltyProgrammePK().getCompanyCode());
		auditVO.setLoyaltyProgrammeCode(
				loyaltyProgramme.getAttachLoyaltyProgrammePK().getLoyaltyProgrammeCode());
		auditVO.setTxnTime(new LocalDate(logonAttributes.getStationCode(),Location.STN, true));
		auditVO.setUserId(loyaltyProgramme.getLastUpdatedUser());


		log.log(Log.INFO,"Going to populate additional info");
		additionalInfo.append("Customer Code  : ");
		additionalInfo.append(loyaltyProgramme.getAttachLoyaltyProgrammePK().getCustomerCode());

		if(!AttachLoyaltyProgrammeVO.OPERATION_FLAG_DELETE.equals(programmeVO.getOperationFlag())) {
			additionalInfo.append(" , ");
			additionalInfo.append("Period  : ");
			additionalInfo.append(programmeVO.getFromDate());
			additionalInfo.append("-");
			additionalInfo.append(programmeVO.getToDate());
		}
		/*
		if(auditVO.getAuditFields() != null &&
				auditVO.getAuditFields().size() > 0) {

			for(AuditFieldVO auditField : auditVO.getAuditFields()) {
				if(auditField != null){
					additionalInfo
					.append(" || ")
					.append(auditField.getFieldName())
					.append(" : ")
					.append(auditField.getNewValue());
				}else{
					log.log(Log.WARNING,"auditField is NULL!!!!!!!!!!!!!!!!!!!");
				}
			}
		}
		*/
		auditVO.setAdditionalInformation(additionalInfo.toString());
		log.exiting("findAuditVO","...Finished construction of vo");
		return auditVO;

	}

	/**
	 * @author A-1883
	 * @param companyCode
	 * @return Collection<ParameterDescriptionVO>
	 * @throws SystemException
	 */
	public Collection<ParameterDescriptionVO> findParameterDetails(String companyCode)
	throws SystemException {
		log.entering("CustomerMgmntController","findParameterDetails");
		return LoyaltyProgramme.findParameterDetails(companyCode);
	}
	/**
	 *
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<String> listPointAccumulated(
			AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
			throws SystemException{
		log.entering("CustomerMgmntController","findParameterDetails");
		return AirWayBillLoyaltyProgram .listPointAccumulated(
				airWayBillLoyaltyProgramFilterVO);
	}
	/**
	 *
	 * @param airWayBillLoyaltyProgramFilterVO
	 * @return Collection<AirWayBillLoyaltyProgramVO>
	 * @throws SystemException
	 */
	public Collection<AirWayBillLoyaltyProgramVO> showPoints(AirWayBillLoyaltyProgramFilterVO airWayBillLoyaltyProgramFilterVO)
	throws SystemException{
		log.entering("CustomerMgmntController","showPoints");
		return AirWayBillLoyaltyProgram .showPoints(
				airWayBillLoyaltyProgramFilterVO);
	}
	/**
	 *
	 * @param companyCode
	 * @param customerCode
	 * @return Collection<CustomerContactVO>
	 * @throws SystemException
	 */
	public Collection<CustomerContactVO> customerContactsLov(
			 String companyCode,String customerCode)
			 throws SystemException{
		log.entering("CustomerMgmntController","customerContactsLov");
		SharedCustomerProxy sharedCustomerProxy = new
		SharedCustomerProxy();

		try {
		 return	sharedCustomerProxy.customerContactsLov(companyCode,
					customerCode);
		}catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}

	}
	/**
	 *
	 * @param pointsVOs
	 * @throws SystemException
	 */
	public void saveCustomerContactPoints(Collection<CustomerContactPointsVO>
    pointsVOs) throws SystemException{
		log.entering("CustomerMgmntController","saveCustomerContactPoints");
		SharedCustomerProxy sharedCustomerProxy = new
		SharedCustomerProxy();
		try {
			sharedCustomerProxy.saveCustomerContactPoints(pointsVOs);
			}catch (ProxyException e) {
				throw new SystemException(e.getMessage(),e);
			}

	}
	/**
	 *
	 * @param companyCode
	 * @param customerCode
	 * @return
	 * @throws SystemException
	 */
	 public Collection<CustomerContactPointsVO> listCustomerContactPoints(
	    		String companyCode,String customerCode)throws SystemException{
		 log.entering("CustomerMgmntController","listCustomerContactPoints");
			SharedCustomerProxy sharedCustomerProxy = new
			SharedCustomerProxy();
			try {
				return sharedCustomerProxy.listCustomerContactPoints(
						companyCode,customerCode);
				}catch (ProxyException e) {
					throw new SystemException(e.getMessage(),e);
				}
	 }
	 /**
	  *
	  * @param customerVOs
	  * @throws SystemException
	  */
	 public void changeStatusOfCustomers(Collection<CustomerVO> customerVOs)
	 throws SystemException{
		 log.entering("CustomerMgmntController","changeStatusOfCustomers");
			SharedCustomerProxy sharedCustomerProxy = new
			SharedCustomerProxy();
			try {
				 sharedCustomerProxy.changeStatusOfCustomers(
						customerVOs);
				}catch (ProxyException e) {
					throw new SystemException(e.getMessage(),e);
				}
	 }

		/**
		  * @author a-1883
		  * @param redemptionValidationVO
		  * @return Collection<ErrorVO>
		  * @throws SystemException
		  * @throws LoyaltyProgrammeNotAttachedException
		  */
		public Collection<ErrorVO> validateCustomerForPointsRedemption(
				 RedemptionValidationVO redemptionValidationVO)
				 throws SystemException , LoyaltyProgrammeNotAttachedException{
			log.entering("CustomerMgmntController","redemptionValidationVO");
			Collection<LoyaltyProgrammeVO> loyaltyProgrammeVOs =
				LoyaltyProgramme.validateCustomerForPointsRedemption(redemptionValidationVO);
			log.log(Log.FINE, "VOs", loyaltyProgrammeVOs);
			Collection<ErrorVO> errorVOs = null;
			if(loyaltyProgrammeVOs != null && loyaltyProgrammeVOs.size() >0){
				boolean isValid = false;
				ErrorVO errorVo = null;
				 errorVOs = new ArrayList<ErrorVO>();
				for(LoyaltyProgrammeVO programmeVO:loyaltyProgrammeVOs){
					int years = 0;
					int months = 0;
					if("M".equals(programmeVO.getExpiryDuration())){
						double period = programmeVO.getExpiryPeriod();
						years = (int)period/12;
						months = (int)period%12;
					}else{
						years = (int)programmeVO.getExpiryPeriod();
					}
					log.log(Log.FINE, "years", years);
					log.log(Log.FINE, "months", months);
					LocalDate expiryDate = programmeVO.getToDate();
					expiryDate.addMonths(months);
					expiryDate.addYears(years);
					if(redemptionValidationVO.getCurrentDate().before(expiryDate)){
						isValid = true;
						break;
					}
						errorVo = new ErrorVO(LoyaltyProgrammeExpiredException.
								LOYALTY_PROGRAMME_EXPIRED);
						Object[] errorData = new Object[2];
						errorData[0] = programmeVO.getLoyaltyProgrammeCode();
						errorData[1] = expiryDate.toDisplayDateOnlyFormat();
						errorVo.setErrorData(errorData);
						errorVOs.add(errorVo);
				}
				if(!isValid){
					log.log(Log.FINE," Throwing LoyaltyProgrammeExpiredException");
					clearCustomerPoints(redemptionValidationVO);
				}else{
					log.log(Log.FINE," Customer Can Redeeem Points ");
					errorVOs.clear();
				}
			}else{
				log.log(Log.FINE," There is no Loyalty Programme Attached to this customer ");
				throw new LoyaltyProgrammeNotAttachedException();
			}
			log.exiting("CustomerMgmntController","redemptionValidationVO");
			return errorVOs;
		}
		/**
		 * @param redemptionValidationVO
		 * @throws SystemException
		 */
		private void clearCustomerPoints(RedemptionValidationVO redemptionValidationVO)
		throws SystemException {
			log.entering("CustomerMgmntController","clearCustomerPoints");
			SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
			try{
				sharedCustomerProxy.clearCustomerPoints(
					redemptionValidationVO.getCompanyCode(),
					redemptionValidationVO.getCustomerCode());
			}catch (ProxyException proxyException) {
                throw new SystemException(proxyException.getMessage(),proxyException);
	         }
			log.exiting("CustomerMgmntController","clearCustomerPoints");
		}

     /***
      *
      * @param customerGroupFilterVO
      * @return
      * @throws SystemException
      */

     public Page<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> viewCustomerGroups(CustomerGroupFilterVO customerGroupFilterVO)
     throws SystemException {
         log.entering("CustomerMgmntController","viewCustomerGroups");
         SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
         try {
             log.log(Log.FINE," going for Shared:Customer:Proxy call -->> viewCustomerGroups ");
             return sharedCustomerProxy.viewCustomerGroups(customerGroupFilterVO);
         }catch (ProxyException e) {
                throw new SystemException(e.getMessage(),e);
         }
     }
     /**
      *
      * @param companyCode
      * @param groupCode
      * @return
      * @throws SystemException
      */
     public com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO listCustomerGroupDetails(String companyCode, String groupCode)
      throws SystemException{
         log.entering("CustomerMgmntController","listCustomerGroupDetails");
         SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
         try {
             log.log(Log.FINE," going for Shared:Customer:Proxy call -->> listCustomerGroupDetails ");
             return sharedCustomerProxy.listCustomerGroupDetails(companyCode,groupCode);
         }catch (ProxyException e) {
                throw new SystemException(e.getMessage(),e);
         }

     }

     /**
      *
      * @param customerGroupVOs
      * @return
      * @throws SystemException
      */
     public String saveCustomerGroupDetails(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
     throws SystemException {
         log.entering("CustomerMgmntController","saveCustomerGroupDetails");
         SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
         try {
             log.log(Log.FINE," going for Shared:Customer:Proxy call -->> saveCustomerGroupDetails ");
             return sharedCustomerProxy.saveCustomerGroupDetails(customerGroupVOs);
         }catch (ProxyException e) {
                throw new SystemException(e.getMessage(),e);
         }


     }
   /**
    *
    * @param customerGroupVOs
    * @throws SystemException
    * @throws CustomerBusinessException
    */
     public void deleteCustomerGroups(Collection<com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO> customerGroupVOs)
     throws SystemException,CustomerBusinessException {
         log.entering("CustomerMgmntController","deleteCustomerGroups");
         SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
         try {
             log.log(Log.FINE," going for Shared:Customer:Proxy call -->> saveCustomerGroupDetails ");
             sharedCustomerProxy.deleteCustomerGroups(customerGroupVOs);
         }catch (ProxyException e) {
                throw new CustomerBusinessException(e);
         }

     }
     /**
	  * @author a-1883
	  * @param companyCode
	  * @param currentDate
	  * @param pageNumber
	  * @return Page<LoyaltyProgrammeVO>
	  * @throws SystemException
	  */
	public  Page<LoyaltyProgrammeVO> runningLoyaltyProgrammeLOV(
			 String companyCode,LocalDate currentDate,int pageNumber)
			 throws SystemException{
		log.entering("CustomerMgmntController","runningLoyaltyProgrammeLOV");
		Page<LoyaltyProgrammeVO> loyaltyProgramVOs =null;
		return LoyaltyProgramme.runningLoyaltyProgrammeLOV(companyCode, currentDate, pageNumber);
	}
	/**
	 *
	 * @param customerVOs
	 * @throws SystemException
	 */
	/*public void deleteCustomers(Collection<CustomerVO> customerVOs)
    throws SystemException{
		log.entering("CustomerMgmntController","deleteCustomers");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();

		try {
			sharedCustomerProxy.deleteCustomers(customerVOs);
		}catch (ProxyException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}
	}*/
	/**
	 *
	 * @param servicesVO
	 * @throws SystemException
	 * @throws ServiceInUseException
	 */
	 public void saveCustomerServices(CustomerServicesVO servicesVO)
     throws SystemException,ServiceInUseException{
		 log.entering("CustomerMgmntController","saveCustomerServices");
		 if(CustomerServicesVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(servicesVO.getOperationFlag())) {
			 CustomerServices customerServices = CustomerServices.find(servicesVO);
			 String result = CustomerServices.checkForService(servicesVO);
			 if("1".equals(result)) {
				 throw new ServiceInUseException();
			 }
			 customerServices.remove();
		 }else if(CustomerServicesVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(servicesVO.getOperationFlag())) {
			 new CustomerServices(servicesVO);
		 }else if(CustomerServicesVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(servicesVO.getOperationFlag())) {
			 CustomerServices customerServices = CustomerServices.find(servicesVO);
			 customerServices.update(servicesVO);
		 }
	 }
	 /**
	  *
	  * @param companyCode
	  * @param serviceCode
	  * @return
	  * @throws SystemException
	  */
	 public CustomerServicesVO listCustomerServices(String companyCode,
      		String serviceCode)throws SystemException{
		 log.entering("CustomerMgmntController","listCustomerServices");
		 return CustomerServices.listCustomerServices(companyCode,
				 serviceCode);
	 }
	/**
	 *
	 * @param companyCode
	 * @param serviceCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	 public Page<CustomerServicesVO> customerServicesLOV(String companyCode,
      		String serviceCode,int pageNumber)throws SystemException{
		 log.entering("CustomerMgmntController","customerServicesLOV");
		 return CustomerServices.customerServicesLOV(companyCode,
				 serviceCode,pageNumber);
	 }

	/**
	 * @author a-1863
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> generateLoyaltyDetailsReport(ReportSpec reportSpec)
	throws SystemException{
		 log.entering("CustomerMgmntController","generateLoyaltyDetailsReport");

		 LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO =
			 					(LoyaltyProgrammeFilterVO)reportSpec.getFilterValues().iterator().next();
		 Collection<LoyaltyProgrammeVO> loyaltyDetails = new ArrayList<LoyaltyProgrammeVO>();
		 Page<LoyaltyProgrammeVO> page =
			 	listAllLoyaltyProgrammes(loyaltyProgrammeFilterVO,loyaltyProgrammeFilterVO.getPageNumber());
		 if(page!=null){
			 for(LoyaltyProgrammeVO programmeVO:page){
				 loyaltyDetails.add(programmeVO);
			 }
		 }
		 log.log(Log.FINE,
				"\n\n*-*-*-*-*-*-****************************----*-*-*-*",
				loyaltyDetails.size());
		reportSpec.setReportId(LP_REPORT_ID);
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "LTYPRGCOD",
					"LTYPRGDES", "FRMDAT", "TOODAT", "ENTPTS",
					"EXPPER"});
		reportMetaData.setFieldNames(new String[] { "loyaltyProgrammeCode",
					"loyaltyProgrammeDesc", "fromDate", "toDate",
					"entryPoints", "expiryPeriod"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(loyaltyDetails);


		return ReportAgent.generateReport(reportSpec);
	}

	/**
	 * @author a-2052
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> printListCustomerReport(ReportSpec reportSpec)
	throws SystemException{
		 log.entering("CustomerMgmntController","printListCustomerReport");

		Iterator<Object> filterValues = reportSpec.getFilterValues().iterator();
		CustomerListFilterVO customerListFilterVO =
				(CustomerListFilterVO)filterValues.next();
		log.log(Log.FINE, "CustomerListFilterVO======", customerListFilterVO);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<CustomerVO> customerVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();		
		try {
			customerVOs = new SharedCustomerProxy().listCustomerDetailsForReport(customerListFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
		/*if(customerVOs == null || customerVOs.size()==0){
			ErrorVO errorVO = new ErrorVO(
			"customermanagement.defaults.custlisting.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}*/

		log.log(Log.FINE, "\n\n--Obtained customerVOs from the server->",
				customerVOs);
		Map<String, Collection<OneTimeVO>>  oneTimes = new HashMap<String, Collection<OneTimeVO>>();

		Collection<String> oneTimeList = new ArrayList<String>();

		oneTimeList.add(CUSTOMER_STATUS);
		//oneTimes =
		//	new SharedDefaultsProxy().findOneTimeValues(customerListFilterVO.getCompanyCode(),oneTimeList) ;
		try {
			oneTimes = new SharedDefaultsProxy().findOneTimeValues(
					logonAttributes.getCompanyCode(), oneTimeList);

		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR,proxyException) ;
		}

		Collection<OneTimeVO> custSta = new ArrayList<OneTimeVO>();

		if(oneTimes != null){
			custSta =	oneTimes.get(CUSTOMER_STATUS);
		}

		for(CustomerVO customerVO :customerVOs ){
			for(OneTimeVO oneTimeVO:custSta){
				if(customerVO.getStatus() != null &&
					customerVO.getStatus().equals(oneTimeVO.getFieldValue())){
					customerVO.setStatus(oneTimeVO.getFieldDescription());
				}
			}
		}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CUSCOD",
				"CUSNAM", "STNCOD", "CUSSTA", "PHNONE",
				"FAXNUM"});
		reportMetaData.setFieldNames(new String[] { "customerCode",
				"customerName", "stationCode", "status",
				"telephone", "fax"});
		reportSpec.setReportMetaData(reportMetaData);
		reportSpec.setData(customerVOs);
		reportSpec.setPreview(true);	
		reportSpec.addExtraInfo(custSta);
		return ReportAgent.generateReport(reportSpec);
	}
	
	/**
	 * Added by A-2593
	 * Method will return all the shipments with the same prefix and awb number.
	 * It wil return empty Set if no awbs exist with the same prefix and awb
	 * number
	 *
	 * @param shipmentFilterVO
	 * @return Collection<ShipmentVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	/*public Collection<ShipmentVO> findShipments(
			ShipmentFilterVO shipmentFilterVO) throws RemoteException,
			SystemException// ,ShipmentBusinessException
	{
		log.entering("CUSTOMER MANAGEMENT", "findShipments");
		Collection<ShipmentVO> shipments = null;
		try{		
			shipments = new OperationsShipmentProxy().findShipments(shipmentFilterVO);
		}catch(ProxyException proxyException){
			log.log(Log.SEVERE," ProxyException ");
			for(ErrorVO errorVO : proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
		log.log(Log.FINE, "The Shipments is got----> ", shipments);
		return shipments;
	}*/

	/**
	 * Added by A-3045	 
	 * Method will upload the datas from the excel sheet
	 * @param tsaData
	 * @return Collection<ErrorVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<ErrorVO> uploadTSAData(String fileType,
			byte[] tsaData) throws RemoteException,SystemException{
		log.entering("CUSTOMER MANAGEMENT", "uploadTSAData");
		Collection<ErrorVO> errors = null;
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();

		try {
			errors = sharedCustomerProxy.uploadTSAData(fileType, tsaData);
		}catch (ProxyException e) {
//printStackTraccee()();
			throw new SystemException(e.getMessage());
		}
		log.log(Log.FINE, "The errors got----> ", errors);
		return errors;
	}
	/**
	 * @author a-2883
	 * @param bookingFilterVO
	 * @return Page<BookingVO>
	 * @throws SystemException
	 */
	public Page<BookingVO>  findBookings(
			BookingFilterVO bookingFilterVO) 
			throws SystemException {
			log.entering("CustomerMgmntController","findBookings");
			CapacityBookingProxy bookingProxy = new CapacityBookingProxy();
			try {
				 return bookingProxy.findBookings(bookingFilterVO);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
		}
	
	/**
	 * @author a-2883
	 * @param claimFilterVO
	 * @throws SystemException
	 * @return Page<ClaimListVO>  
	 */
	public Page<ClaimListVO>  findClaimList(ClaimFilterVO claimFilterVO) 
		throws SystemException {
		log.entering("CustomerMgmntController","findClaimList");
		ClaimsDefaultsProxy claimProxy = new ClaimsDefaultsProxy();
		try {
			 return claimProxy.findClaimList(claimFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
	
	
	/**
	 * @author a-2883
	 * @param stockDetailsFilterVO
	 * @return Page<StockDetailsVO>
	 * @throws SystemException
	 */
	public StockDetailsVO  findCustomerStockDetails(
			StockDetailsFilterVO stockDetailsFilterVO) 
			throws SystemException,CustomerBusinessException {
			log.entering("CustomerMgmntController","findCustomerStockDetails");
			StockcontrolDefaultsProxy stockProxy = new StockcontrolDefaultsProxy();
			StockDetailsVO stockDetailsVO = new StockDetailsVO();
			try {
				return stockDetailsVO= stockProxy.findCustomerStockDetails(stockDetailsFilterVO);
			} catch (ProxyException proxyException) {
				for(ErrorVO errorVO : proxyException.getErrors()){
					if("stockcontrol.defaults.stockholdernotfound".equals(errorVO.getErrorCode())){
						throw new CustomerBusinessException(errorVO.getErrorCode());
					}else{
						throw new SystemException(proxyException.getMessage(),proxyException);
					}
				}
			}
			return stockDetailsVO;
		}
	
	
	
	/**
	 * @author a-2883
	 * @param messageListFilterVO
	 * @return Page<MessageVO>
	 * @throws SystemException
	 */
	public Page<MessageHistoryVO>  findMessageForCustomers(
			MessageListFilterVO messageListFilterVO,Collection<String> address) 
			throws SystemException {
			log.entering("CustomerMgmntController","messageListFilterVO");
			MsgbrokerMessageProxy msgbrokerProxy = new MsgbrokerMessageProxy();
			try {
				 return msgbrokerProxy.findMessageForCustomers(messageListFilterVO,address);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
		}
	
	
	/**
	 * @author a-2883
	 * @param filterVO
	 * @return Page<CustomerAllotmentEnquiryVO>
	 * @throws SystemException
	 */
	
	public Page<CustomerAllotmentEnquiryVO> findCustomerEnquiryDetails(
			CustomerAllotmentEnquiryFilterVO filterVO ) 
			throws SystemException {
			log.entering("CustomerMgmntController","findCustomerEnquiryDetails");
			CapacityMonitoringProxy capacityProxy = new CapacityMonitoringProxy();
			try {
				 return capacityProxy.findCustomerEnquiryDetails(filterVO);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
		}
	
	
	/**
	 * @author a-2883
	 * @param spotRateRequestFilterVO
	 * @throws SystemException
	 * @return Page<SpotRateRequestDetailsVO>  
	 */
	public Page<SpotRateRequestDetailsVO>  findSpotRateRequestsByFilter(
			SpotRateRequestFilterVO spotRateRequestFilterVO) 
			throws SystemException {
		log.entering("CustomerMgmntController","findSpotRateRequestsByFilter");
		TariffFreightProxy tariffProxy = new TariffFreightProxy();
		try {
			 return tariffProxy.findSpotRateRequestsByFilter(spotRateRequestFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
	
	/**
	 * @author a-2883
	 * @param tariffFilterVO
	 * @return Page<RateLineVO>
	 * @throws SystemException
	 */
	public Page<RateLineVO>  findRateLineDetailsByFilter(
			TariffFilterVO tariffFilterVO) 
			throws SystemException {
		log.entering("CustomerMgmntController","findRateLineDetailsByFilterxx");
		TariffFreightProxy tariffProxy = new TariffFreightProxy();
		try {
			 return tariffProxy.findRateLineDetailsByFilter(tariffFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
	
	/**
	 * @author a-2883
	 * @param invoiceFilterVO
	 * @return Page<InvoiceDetailsVO>
	 * @throws SystemException
	 */
	public Page<InvoiceDetailsVO>  listInvoices(
			InvoiceFilterVO invoiceFilterVO) 
			throws SystemException {
			log.entering("CustomerMgmntController","listInvoices");
			AccountingInvoicingProxy accountingProxy = new AccountingInvoicingProxy();
			try {
				 return accountingProxy.listInvoices(invoiceFilterVO);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
		}
	
	/**
	 * @author a-2883
	 * @param invoiceFilterVO
	 * @return Page<InvoiceDetailsVO>
	 * @throws SystemException
	 */
	public Page<CharterOperationsVO>findCharterForCustomer(
			ListCharterOperationsFilterVO filterVO) 
			throws SystemException {
			log.entering("CustomerMgmntController","findCharterForCustomer");
			CapDefaultsProxy charterProxy = new CapDefaultsProxy();
			try {
				 return charterProxy.findCharterForCustomer(filterVO);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
		}
	
	
	
	
	
	
	/**
	 *
	 * @param customerFilterVO
	 * @return CustomerVO
	 * @throws SystemException
	 */
	public CustomerVO customerEnquiryDetails(CustomerFilterVO customerFilterVO)
	throws SystemException{
		log.entering("CustomerMgmntController","customerEnquiryDetails");
			SharedCustomerProxy sharedCustomerProxy = new
		SharedCustomerProxy();
		try{
			return sharedCustomerProxy.customerEnquiryDetails(customerFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}

	}

	/**
	 * 
	 * 	Method		:	CustomerMgmntController.saveCustomerDetails
	 *	Added by 	:	A-4789 on 15-Oct-2012
	 * 	Used for 	:	Saves the customer details
	 *	Parameters	:	@param customerDetails
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> saveCustomerDetails(
			Collection<CustomerVO> customerDetails) 
			throws SystemException {
		log.entering("CustomerMgmntController","saveCustomerDetails");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		Collection<CustomerVO> returnCustomers = null;
		// Added by A-5290 for ICRD-90172
		Collection<CustomerErrorVO> errors = new ArrayList<CustomerErrorVO>();
		try {
			returnCustomers = sharedCustomerProxy.saveCustomerDetails(customerDetails);
			log.log(Log.FINE, returnCustomers.toString());
		} catch(ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage());
			throw new SystemException(proxyException.getMessage(), proxyException);
		} catch (SystemException ex) {
			log.log(Log.SEVERE,"inside systemexce");

			if (ex.getErrors() != null && !ex.getErrors().isEmpty()) {
				for (ErrorVO errorVO : ex.getErrors()) {
					log.log(Log.INFO,"Error Code for save customer-->", errorVO.getErrorCode());
					log.log(Log.INFO,"Error Desc for save customer-->", errorVO.getErrorDescription());
					log.log(Log.INFO,"Error Msg for save customer-->", ex.getMessage());
				}
				for (ErrorVO errorVO : ex.getErrors()) {
					if (("STLDTA").equals(errorVO.getErrorCode())) {
						CustomerErrorVO customerErrorVO = new CustomerErrorVO();
						customerErrorVO.setErrorCode(errorVO.getErrorCode());
						customerErrorVO.setErrorDescription(CustomerErrorVO.ERR_DESC_STALE_DATA);
						errors.add(customerErrorVO);
						if(returnCustomers != null) {
							returnCustomers.iterator().next().setCustomerErrors(errors);
						} else {
							returnCustomers = new ArrayList<CustomerVO>();
							CustomerVO customerVO = new CustomerVO();
							customerVO.setCompanyCode(customerDetails.iterator().next().getCompanyCode());
							customerVO.setCustomerCode(customerDetails.iterator().next().getCustomerCode());
							customerVO.setStatus(customerDetails.iterator().next().getStatus());
							customerVO.setCustomerErrors(errors);
							returnCustomers.add(customerVO);
						}
					}else if(DUPLICATE_USER_ERROR.equalsIgnoreCase(errorVO.getErrorCode())){//Added by A-7364 as part of ICRD-255463 starts
						CustomerErrorVO customerErrorVO = new CustomerErrorVO();
						customerErrorVO.setErrorCode(errorVO.getErrorCode());
						customerErrorVO.setErrorDescription(DUPLICATE_USER_ERRORDESC);
						errors.add(customerErrorVO);
						if(returnCustomers != null) {
							returnCustomers.iterator().next().setCustomerErrors(errors);
						} else {
							returnCustomers = new ArrayList<CustomerVO>();
							CustomerVO customerVO = new CustomerVO();
							customerVO.setCompanyCode(customerDetails.iterator().next().getCompanyCode());
							customerVO.setCustomerCode(customerDetails.iterator().next().getCustomerCode());
							customerVO.setStatus(customerDetails.iterator().next().getStatus());
							customerVO.setCustomerErrors(errors);
							returnCustomers.add(customerVO);
						}
					//Added by A-7364 as part of ICRD-255463 ends	
					}else {
						throw new SystemException(ex.getMessage());
					}
				}
			}
		}
		log.exiting("CustomerMgmntController","saveCustomerDetails");
		return returnCustomers;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.saveCustomerCreditDetails
	 *	Added by 	:	A-10509 on 08-Nov-2022
	 * 	Used for 	:	Saves the customer credit details
	 *	Parameters	:	@param customerCreditVO
	 *	Parameters	:	@return customerCreditVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	 
	 **/
	public CustomerCreditVO saveCustomerCreditDetails(CustomerCreditVO customerCreditVO) 
			throws SystemException {
		try {
			Proxy.getInstance().get(SharedCustomerProxy.class).saveCustomerCreditDetails(customerCreditVO);
		}
		catch(SystemException | ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);			
		}		
		return customerCreditVO;	
	}
	
	/**
	* 
	* 	Method		:	CustomerMgmntController.findCustomerCreditDetails
	*	Added by 	:	A-10509 on 08-Nov-2022
	* 	Used for 	:	finds the customer credit details
	*	Parameters	:	@param customerCreditFilterVO
	*	Parameters	:	@return customerCreditVO
	*	Parameters	:	@throws SystemException  
    **/
	public CustomerCreditVO findCustomerCreditDetails(CustomerCreditFilterVO customerCreditFilterVO) throws SystemException {
		CustomerCreditVO customerCreditVO = new CustomerCreditVO();
		try {
			customerCreditVO = Proxy.getInstance().get(SharedCustomerProxy.class).findCustomerCreditDetails(customerCreditFilterVO);
		} catch (SystemException | ProxyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		return customerCreditVO;
	}

	/**
	 *
	 * @param customerVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<CustomerCertificateVO> findCustomerCertificates(CustomerVO customerVO) throws SystemException {
		Collection<CustomerCertificateVO> customerCertificateVOS = null;
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(customerVO.getCompanyCode());
		customerFilterVO.setCustomerCode(customerVO.getCustomerCode());
		try{
			customerCertificateVOS = Proxy.getInstance().get(SharedCustomerProxy.class).findCertificateTypes(customerFilterVO);
		}
	 catch (SystemException | ProxyException ex) {
		throw new SystemException(ex.getMessage(), ex);
	}
		return customerCertificateVOS;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.saveCustomerCertificateDetails
	 *	Added by 	:	A-4789 on 15-Oct-2012
	 * 	Used for 	:	Saves the customer certificate details
	 *	Parameters	:	@param customerCertificates
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerCertificateVO>
	 */
	public Collection<CustomerCertificateVO> saveCustomerCertificateDetails(
			Collection<CustomerCertificateVO> customerCertificates) 
			throws SystemException {
		log.entering("CustomerMgmntController","saveCustomerCertificateDetails");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		Collection<CustomerCertificateVO> returnCertificates = null;
		try {
			returnCertificates = sharedCustomerProxy.saveCustomerCertificateDetails(customerCertificates);
			log.log(Log.FINE, returnCertificates.toString());
		} catch(ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage());
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
		log.exiting("CustomerMgmntController","saveCustomerCertificateDetails");
		return returnCertificates;
	}

	/**
	 * 
	 * 	Method		:	CustomerMgmntController.updateCustomerStatus
	 *	Added by 	:	A-4789 on 17-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customers
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> updateCustomerStatus(
			Collection<CustomerVO> customers) 
			throws SystemException {
		log.entering("CustomerMgmntController","updateCustomerStatus");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		Collection<CustomerVO> returnCustomers = null;
		try {
			returnCustomers = sharedCustomerProxy.updateCustomerStatus(customers);
			log.log(Log.FINE, returnCustomers.toString());
		} catch(ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage());
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
		log.exiting("CustomerMgmntController","updateCustomerStatus");
		return returnCustomers;
	}
	/**
	 * 	Method		:	CustomerMgmntController.getCustomerDetails
	 *	Added by 	:	A-4789 on 19-Oct-2012
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilters
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CustomerVO>
	 * @throws SystemException 
	 */
	public Collection<CustomerVO> getCustomerDetails(
			Collection<CustomerFilterVO> customerFilters) throws SystemException {
		log.entering("CustomerMgmntController","getCustomerDetails");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		Collection<CustomerVO> returnCustomers = null;
		try {
			returnCustomers = sharedCustomerProxy.getCustomerDetails(customerFilters);
			log.log(Log.FINE, returnCustomers.toString());
		} catch(ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage());
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
		log.exiting("CustomerMgmntController","getCustomerDetails");
		return returnCustomers;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.loadCustomerDetailsHistory
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	To Load Customer Details History. Added for ICRD-67442.
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> loadCustomerDetailsHistory(CustomerFilterVO customerFilterVO) throws SystemException {
		log.entering("CustomerMgmntController","loadCustomerDetailsHistory");
		try{
			return new SharedCustomerProxy().loadCustomerDetailsHistory(customerFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	} 
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.getAllCustomers
	 *	Added by 	:	A-4816 on 16-Jun-2015
	 * 	Used for 	:
	 *	Parameters	:	@param customerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<CustomerVO>
	 */
	public Collection<CustomerVO> getAllCustomers(
			CustomerFilterVO customerFilterVO) throws SystemException {
		log.entering("CustomerMgmntController","=========>getAllCustomers");
		SharedCustomerProxy sharedCustomerProxy = new SharedCustomerProxy();
		Collection<CustomerVO> returnCustomers = null;
		try {
			returnCustomers = sharedCustomerProxy.getAllCustomers(customerFilterVO);
			log.log(Log.FINE, returnCustomers.toString());
		} catch(ProxyException proxyException) {
			log.log(Log.SEVERE, proxyException.getMessage());
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
		log.exiting("CustomerMgmntController","===========>getAllCustomers");
		return returnCustomers;
	}
	
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.findSystemParameterByCodes
	 *	Added by 	:	A-7364 on 03-May-2018
	 * 	Used for 	:
	 *	Parameters	:	@param parameterCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,String>
	 */
	public Map<String, String> findSystemParameterByCodes(
			Collection<String> parameterCodes) throws SystemException {
		Map<String, String> systemParameters = null;
		try {
			systemParameters = new SharedDefaultsProxy()
			.findSystemParameterByCodes(parameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		return systemParameters;
	}
	/**
	 * 
	 * 	Method		:	CustomerMgmntController.findUserDetails
	 *	Added by 	:	a-5956 on 19-Oct-2017
	 * 	Used for 	:	ICRD-212854
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param userID
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	UserVO
	 */
	public UserVO findUserDetails(String companyCode, String userID)throws SystemException{
		log.entering("CustomerMgmntController","findUserDetails");
		AdminUserProxy adminUserProxy = new AdminUserProxy();
		UserVO userVO = null;
		try {
			userVO = adminUserProxy.findUserDetails(companyCode, userID);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		log.entering("CustomerMgmntController","findUserDetails");
		return userVO;
	}
	
	/**
	 * 	Method		:	CustomerMgmntController.validateStockHolderFromCustomerActivation
	 *	Added by 	:	A-8154
	 * 	Used for 	: 	CR ICRD-253447
	 *	Parameters	:	@param customerVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	boolean
	 */
	public boolean validateStockHolderFromCustomerActivation(CustomerVO customerVO)
			 throws SystemException{
		log.entering("CustomerMgmntController","validateStockHolderFromCustomerActivation");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String companyCode= logonAttributes.getCompanyCode();
		Collection<String> stockHolderCodes= new ArrayList<String>();
		stockHolderCodes.add(customerVO.getCustomerCode());
		if(customerVO.isStockAutomationRequired()){
			StockcontrolDefaultsProxy controlDefaultsProxy =new StockcontrolDefaultsProxy(); 
			try {
				controlDefaultsProxy.validateStockHolders(companyCode,stockHolderCodes);
			} catch (ProxyException proxyException) {
				throw new SystemException(proxyException.getErrors());
			}
		 } 
		return true;
	}
	/**
	 * 	Method		:	CustomerMgmntController.getBillingInvoiceDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *	Parameters	:	@param filterVO 
	 *	Parameters	:	@throws SystemException 
	 *  Return type	: 	CustomerBillingInvoiceDetailsVO
	 */
	public CustomerBillingInvoiceDetailsVO getBillingInvoiceDetails(CustomerFilterVO filterVO) throws SystemException {
		log.entering("CustomerMgmntController", "getBillingInvoiceDetails");
		CustomerBillingInvoiceDetailsVO customerBillingInvoiceDetailsVO=null;
		CRAAgentBillingProxy craAgentBillingProxy = new CRAAgentBillingProxy();
		try {
			customerBillingInvoiceDetailsVO = craAgentBillingProxy.getBillingInvoiceDetails(filterVO);
		}
		catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		log.exiting("CustomerMgmntController", "getBillingInvoiceDetails");
		return customerBillingInvoiceDetailsVO;
	}
	/**
	 * 	Method		:	CustomerMgmntController.getCCADetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 
	 *  Return type	: 	List<CCADetailsVO>
	 * @throws SystemException 
	 */
	public List<CCADetailsVO> getCCADetails(String invoiceNumber,String companyCode)throws SystemException {
		log.entering("CustomerMgmntController", "getCCADetails");
		List<CCADetailsVO> ccaDetailsVO=null;
		CRAAgentBillingProxy craAgentBillingProxy = new CRAAgentBillingProxy();
		try {
			ccaDetailsVO = craAgentBillingProxy.getCCADetails(invoiceNumber,companyCode);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		log.exiting("CustomerMgmntController", "getCCADetails");
		return ccaDetailsVO;
	}
	/**
	 * 	Method		:	CustomerMgmntController.getPaymentDetails
	 *	Added by 	:	A-8227 
	 *	Used for	: 	CR ICRD-236527
	 *  Parameters	:	@param invoiceNumber 
	 *	Parameters  :   @param companyCode
	 *	Parameters	:	@throws SystemException 
	 *  Return type	: 	Collection<PaymentDetailsVO>
	 * @throws SystemException 
	 */
	public Collection<PaymentDetailsVO> getPaymentDetails(CustomerInvoiceDetailsVO customerInvoiceDetailsVO) throws SystemException {
		log.entering("CustomerMgmntController", "getPaymentDetails");
		Collection<PaymentDetailsVO> paymentDetailsVO=null;
		CRAAgentBillingProxy craAgentBillingProxy = new CRAAgentBillingProxy();
		try {
			paymentDetailsVO = craAgentBillingProxy.getPaymentDetails(customerInvoiceDetailsVO);
		}
		catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getErrors());
		}
		log.exiting("CustomerMgmntController", "getPaymentDetails");
		return paymentDetailsVO;
	}
	/**
	 * 	Method		:	CustomerMgmntController.printAccountStatement
	 *	Added by 	:	A-8169 on 14-Nov-2018 
	 *	Used for	: 	ICRD-236527
	 * Modified method as part of IASCB-45955, IASCB-118899
	 */
	public byte[] printAccountStatement(CustomerFilterVO customerFilterVO) throws SystemException {
		log.entering("CustomerMgmntController", "printAccountStatement");
		byte bytes[] = null;
		try {
			List<CustomerInvoiceAWBDetailsVO> invoiceAWBDetailsVOs = Proxy.getInstance().get(CRAAgentBillingProxy.class)
					.findAccountStatementForPrint(customerFilterVO);
			if ("E".equals(customerFilterVO.getExportMode())) {
				bytes = createWorkBookBytesForExcelReport(invoiceAWBDetailsVOs);
			} else if ("P".equals(customerFilterVO.getExportMode())) {
				bytes = createBytesForPDFReport(invoiceAWBDetailsVOs);
			}
		} catch (ProxyException proxyException) {
			throw new SystemException("ProxyException", proxyException.getMessage(), proxyException);
		}
		return bytes;
	}

	/**
	 * Method		:	CustomerMgmntController.createBytesForPDFReport()
	 * Used for 		:	to generate pdf report to print and email customer statement
	 */
	public byte[] createBytesForPDFReport(List<CustomerInvoiceAWBDetailsVO> customerInvoiceAWBDetailsVOs) {
		ReportSpec reportSpec = new ReportSpec();
		reportSpec.setProductCode(PRODUCT);
		reportSpec.setSubProductCode(SUBPRODUCT);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setPreview(false);
		reportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
		reportSpec.setData(customerInvoiceAWBDetailsVOs);
		reportSpec.addParameter(customerInvoiceAWBDetailsVOs);
		return new ByteObject(ReportUtilInstance.getIstance().exportReport(reportSpec)).getBytes();
	}
	
	/**
	 * Method		:	CustomerMgmntController.createWorkBookBytesForExcelReport()
	 * Used for 		:	to generate excel report to print and email customer statement
	 */
	public byte[] createWorkBookBytesForExcelReport(List<CustomerInvoiceAWBDetailsVO> customerInvoiceAWBDetailsVOs) {
		return CustomerMgmntHelper.createWorkBookBytesForExcelReport(customerInvoiceAWBDetailsVOs);
	}
	
	/**
	 * 	Method		:	CustomerMgmntController.getCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteFilterVO 
	 *	Parameters	:	@throws SystemException 
	 *  Return type	: 	List<ScribbleNoteDetailsVO>
	 */
	public List<ScribbleNoteDetailsVO> getCustomerScribbleDetails(ScribbleNoteFilterVO scribbleNoteFilterVO) throws SystemException {
		log.entering("CustomerMgmntController", "getCustomerScribbleDetails");
		CRAAgentBillingProxy craAgentBillingProxy = new CRAAgentBillingProxy();
		List<ScribbleNoteDetailsVO> scribbleNoteDetailsVOs = null;
				try{
					scribbleNoteDetailsVOs = craAgentBillingProxy.getCustomerScribbleDetails(scribbleNoteFilterVO);
				}catch(ProxyException proxyException){
					throw new SystemException(proxyException.getMessage(),proxyException);
				}
		log.exiting("CustomerMgmntController", "getCustomerScribbleDetails");
		return scribbleNoteDetailsVOs;
	}
	/**
	 * 	Method		:	CustomerMgmntController.saveCustomerScribbleDetails
	 *	Added by 	:	A-8169 on 07-Feb-2019 
	 *	Used for	: 	ICRD-308832
	 *  Parameters	:	@param ScribbleNoteDetailsVO 
	 *	Parameters	:	@throws SystemException 
	 *  Return type	: 	void
	 */
	public void saveCustomerScribbleDetails(ScribbleNoteDetailsVO scribbleNoteDetailsVO) throws SystemException {
		log.entering("CustomerMgmntController", "saveCustomerScribbleDetails");
		CRAAgentBillingProxy craAgentBillingProxy = new CRAAgentBillingProxy();
				try{
					craAgentBillingProxy.saveCustomerScribbleDetails(scribbleNoteDetailsVO);
				}catch(ProxyException proxyException){
					throw new SystemException(proxyException.getMessage(),proxyException);
				}
		log.exiting("CustomerMgmntController", "saveCustomerScribbleDetails");
	}

	/**
	 * Method		:	CustomerMgmntController.emailAccountStatement()
	 * Used for 		:	for IASCB-104246
	 */
	public void emailAccountStatement(EmailAccountStatementFeatureVO featureVO) throws SystemException {
		try {
			EmailAccountStatementFeature feature = (EmailAccountStatementFeature) SpringAdapter.getInstance()
					.getBean("customermanagement.defaults.emailAccountStatementFeature");
			feature.execute(featureVO);
		} catch (BusinessException e) {
			throw new SystemException(BUSINESS_EXCEPTION, e.getMessage(), e);
		}
	}
	/**
	 * Done for IASCB-130291
	 * @param generalMasterGroupFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<GeneralMasterGroupVO> listGroupDetailsForCustomer
						(GeneralMasterGroupFilterVO generalMasterGroupFilterVO)throws SystemException {
		try{
			return getSharedGeneralMasterGroupingProxy().listGroupDetailsForCustomer(generalMasterGroupFilterVO);
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
	public SharedGeneralMasterGroupingProxy getSharedGeneralMasterGroupingProxy(){
		 return Proxy.getInstance().get(SharedGeneralMasterGroupingProxy.class);
       
    }
	
	public Collection<ShipmentVO> validateShipmentDetails(ShipmentFilterVO shipmentFilterVO) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "validateShipmentDetails");
		try {
			return Proxy.getInstance().get(OperationsShipmentProxy.class).validateShipmentDetails(shipmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}	
	}
	
	public Collection<CustomerAgentVO> validateSinglePoa(ShipmentFilterVO shipmentFilterVO) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "validateSinglePoa");
		Collection<CustomerAgentVO> singlePOADetails= null;
		try {
			singlePOADetails=Proxy.getInstance().get(SharedCustomerProxy.class).validateSinglePoa(shipmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		} 
		return singlePOADetails;
	}
	
	public Collection<ShipmentHistoryVO> findShipmentHandlingHistory(ShipmentHistoryFilterVO shipmentHistoryFilterVO) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "findShipmentHandlingHistory");
		try {
			return Proxy.getInstance().get(OperationsShipmentProxy.class).findShipmentHandlingHistory(shipmentHistoryFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}	
	}
	
	/**
	 * Method		:	CustomerMgmntController.saveBrokerMapping()
	 * Used for 		:	to modify broker-consignee mapping
	 */
	public String saveBrokerMapping(CustomerVO customerVO) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "saveBrokerMapping");
		String customerCode = "";
		try {
			SaveBrokerMappingFeature feature = (SaveBrokerMappingFeature) SpringAdapter.getInstance()
					.getBean("customermanagement.defaults.saveBrokerMappingFeature");
			customerCode = feature.execute(customerVO);
		} catch (BusinessException e) {
			throw new SystemException(BUSINESS_EXCEPTION, e.getMessage(), e);
		}
		return customerCode;
	}

	/**
	 * Method		:	CustomerMgmntController.uploadBrokerMappingDocuments()
	 * Used for 		:	to upload broker-consignee documents
	 */
	public void uploadBrokerMappingDocuments(DocumentRepositoryMasterVO documentRepositoryMasterVO)
			throws SystemException {
		log.entering(this.getClass().getSimpleName(), "uploadBrokerMappingDocuments");
		try {
			UploadBrokerMappingDocumentFeature feature = (UploadBrokerMappingDocumentFeature) SpringAdapter
					.getInstance().getBean("customermanagement.defaults.uploadBrokerMappingDocumentFeature");
			feature.execute(documentRepositoryMasterVO);
		} catch (BusinessException e) {
			throw new SystemException(BUSINESS_EXCEPTION, e.getMessage(), e);
		}
	}

	/**
	 * Method		:	CustomerMgmntController.interfaceBrokerMappingDocuments()
	 * Used for 		:	to interface customer broker poa documents
	 */
	public void interfaceBrokerMappingDocuments(Collection<POADocumentJMSTemplateVO> poaDocumentJMSTemplateVOs)
			throws SystemException {
		Collection<BaseMessageVO> baseMessageVOs = new ArrayList<>();
		baseMessageVOs.addAll(poaDocumentJMSTemplateVOs);

		try {
			Proxy.getInstance().get(MsgbrokerMessageProxy.class).encodeAndSaveMessagesAsync(baseMessageVOs);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), "ProxyException", e);
		}
	}
}
