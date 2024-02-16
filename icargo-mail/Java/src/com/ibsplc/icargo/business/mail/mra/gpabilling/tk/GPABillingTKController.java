/*
 * GPABillingController.java Created on Mar 18, 2014
 *
 * Copyright 2014 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.tk;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.cra.defaults.vo.tk.AOInvoiceReportDetailsVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.MRABillingMaster;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN51Summary;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingMRAProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MessageBrokerConfigProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressDetailVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressVO;
import com.ibsplc.icargo.business.msgbroker.config.mode.vo.MessageModeParameterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.eai.base.vo.CommunicationVO;
import com.ibsplc.icargo.framework.message.vo.EmailVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

@Module("mail")
@SubModule("mra")
public class GPABillingTKController {

	
	private Log log = LogFactory.getLogger("MRA:GPABILLING");
	//Added by A-4809 for ICRD-39860 
	private static final String BILLINGRPTLEVEL="mailtracking.mra.gpabilling.levelforgpabillingreports";
	private static final String DSNLEVELIMPORT="mailtracking.defaults.DsnLevelImportToMRA";
	private static final String OVERRIDEROUNDING="mailtracking.mra.overrideroundingvalue";
	private static final String BLANK="";
	private static final String OK="OK";
	private static String isSuccessFlag="N";
	private static final String COMMA=",";
	private static final String GENINVMRA="GENINVMRA";
	private static final String LOCKSCREENID= "MRA009";
	private static final String LOCKREMARK="MANUAL LOCK";
	private static final String LOCkDESC="GENERATE INVOICE LOCK";
	private static final String GENINV="GENINV";
    private static final String PRODUCT_CODE="mail";
    private static final String SUB_PRODUCTCODE="mra";
    private static final String REPORT_ID="RPRMTK088";
    private static final String REPORT_TITLE="Invoice Report";
    private static final String BUNDLE="geninvresources";
    private static final String MSGBROKER_EMAIL_INTERFACE_PARAMETER = "msgbroker.message.emailinterfacesystem";
    private static final String MSGBROKER_EMAIL_INTERFACE = "EMAIL";
		
	/**
	 * 
	 * 	Method		:	MRAGpaBillingTKController.generateCN66Report
	 *	Added by 	:	A-5219 on 18-Mar-2014
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws MailTrackingMRABusinessException 
	 *	Return type	: 	Map<String,Object>
	 */
	public Map<String, Object> generateCN66Report(ReportSpec reportSpec)
	throws SystemException, RemoteException,
	MailTrackingMRABusinessException {
		log.entering("GPABillingTKController", "generateCN66Report");
		CN51CN66FilterVO cn51CN66FilterVO = (CN51CN66FilterVO) reportSpec
				.getFilterValues().iterator().next();
		Collection<CN66DetailsVO> cn66DetailsVos = CN51Summary
				.generateCN66Report(cn51CN66FilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = null;
		//Added by A-4809 for ICRD-39860 Starts
		Map<String,String> systemParameterMap =null;
		String dsnLevelImport=null;
		String billingReportLevel=null;
		String curKey =null;
		String prevKey =null;
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add(DSNLEVELIMPORT);
		systemParCodes.add(BILLINGRPTLEVEL);
		systemParCodes.add(OVERRIDEROUNDING);
		//Added by A-4809 for ICRD-39860 Ends
		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
			//Added by A-4809
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}
		//Added by A-4809 for ICRD-39860 Starts
		if(systemParameterMap !=null && systemParameterMap.size()>0){
			dsnLevelImport =systemParameterMap.get(DSNLEVELIMPORT);
			billingReportLevel = systemParameterMap.get(BILLINGRPTLEVEL);
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
		}
		ArrayList<CN66DetailsVO> cn66DetailsVOs = null;
		//Added by A-4809 for ICRD-39860 Ends
		if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
			CN66DetailsVO cN66DetailsVOforRpt = null;
			cn66DetailsVOs = new ArrayList<CN66DetailsVO>();
			for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				double amount=0.0;
				double serviceTax=0.0;
				double vatAmount=0.0;
				double netAmount=0.0;
				double totalWeight=0.0;
				int count =0;
		/*		if (cN66DetailsVO.getBillingStatus() != null) {
					for (OneTimeVO oneTimeVO : mailCategory) {
						if (cN66DetailsVO.getMailCategoryCode().equals(
								oneTimeVO.getFieldValue())) {
							cN66DetailsVO.setMailCategoryCode(oneTimeVO
									.getFieldDescription());
						}
					}
				}*/
				//Added by A-4809 for ICRD-39860 Starts
				curKey = new StringBuilder().append(cN66DetailsVO.getCompanyCode()).
						append(cN66DetailsVO.getInvoiceNumber()).append(cN66DetailsVO.getDsn()).toString();
				if(CN66DetailsVO.FLAG_YES.equals(dsnLevelImport)){
					cn66DetailsVOs.add(cN66DetailsVO);
				}else if(CN66DetailsVO.FLAG_NO.equals(dsnLevelImport)){
					if("M".equals(billingReportLevel)){
						cn66DetailsVOs.add(cN66DetailsVO);
					}else if("D".equalsIgnoreCase(billingReportLevel)){ 
						if(!curKey.equals(prevKey)){
							prevKey = curKey;
							cN66DetailsVO.setRsn("");
							cN66DetailsVO.setRegInd("");
							cN66DetailsVO.setHsn("");
							cn66DetailsVOs.add(cN66DetailsVO);
						}else{
							prevKey = curKey;
							count = cn66DetailsVOs.size();
							cN66DetailsVOforRpt = cn66DetailsVOs.get(count-1);
							totalWeight = cN66DetailsVOforRpt.getTotalWeight()+cN66DetailsVO.getTotalWeight();
							amount = cN66DetailsVOforRpt.getAmount()+cN66DetailsVO.getAmount();
							serviceTax = cN66DetailsVOforRpt.getServiceTax()+cN66DetailsVO.getServiceTax();
							vatAmount = cN66DetailsVOforRpt.getVatAmount().getAmount()+cN66DetailsVO.getVatAmount().getAmount();
							netAmount = cN66DetailsVOforRpt.getNetAmount().getAmount()+cN66DetailsVO.getNetAmount().getAmount();
							cN66DetailsVOforRpt.setTotalWeight(totalWeight);
							cN66DetailsVOforRpt.setAmount(amount);
							cN66DetailsVOforRpt.setServiceTax(serviceTax);
							cN66DetailsVOforRpt.getVatAmount().setAmount(vatAmount);
							cN66DetailsVOforRpt.getNetAmount().setAmount(netAmount);
						}
					}
				}
				cN66DetailsVO.setOverrideRounding(overrideRounding);
				//Added by A-4809 for ICRD-39860 Ends
			}
		}
		if (cn66DetailsVos == null || cn66DetailsVos.size() <= 0) {
			MailTrackingMRABusinessException mailTrackingMRABusinessException = new MailTrackingMRABusinessException();
			ErrorVO reporterror = new ErrorVO(
					MailTrackingMRABusinessException.NO_DATA_FOUND);
			mailTrackingMRABusinessException.addError(reporterror);
			throw mailTrackingMRABusinessException;
		}
		log.log(Log.INFO, " <-- the cn66DetailsVos is --> \n\n ",
				cn66DetailsVos);
		ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "invoiceNumber",
				"gpaCode", "airlineCode" });
		reportSpec.addParameterMetaData(parameterMetaData);
		reportSpec.addParameter(cn51CN66FilterVO);
		if(billingReportLevel!=null &&("M").equals(billingReportLevel))
			{
			reportSpec.setReportId("RPRMTK090");
			}
		else
			{
			reportSpec.setReportId("RPRMTK091");
			}
		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "MALCTGCOD", "DSN", "RSN", "CCAREFNUM", "ORGCOD", "DSTCOD", 
				"SECTOR", "FLTNUM", "TOTWGT", "APLRAT", "BLDAMT", "SRVTAX", "VATAMT","DCLVAL", "NETAMT", "BLGCURCOD","BLDPRD",
				"TOTWGTCP","TOTWGTLC","TOTWGTSV","TOTWGTEMS","BLDAMT1","HNI","RI","FLTDAT","OVRRND"
		});
		if(MailConstantsVO.FLAG_NO.equals(overrideRounding)){
		reportMetaData.setFieldNames(new String[] { "mailCategoryCode", "dsn", "rsn", "ccaRefNo",
				"origin", "destination", "sector",
				"flightNumber", "totalWeight", "applicableRate", "actualAmount", "serviceTax", "valCharges","declaredValue", "netAmount", "currencyCode","billingPeriod",
				"weightCP","weightLC","weightSV","weightEMS","amount","hsn","regInd","flightDate","overrideRounding"
		});//Modified for ICRD-105225
		}else{
			reportMetaData.setFieldNames(new String[] { "mailCategoryCode", "dsn", "rsn", "ccaRefNo",
					"origin", "destination", "sector",
					"flightNumber", "totalWeight", "applicableRate", "amount", "serviceTax", "valCharges","declaredValue", "scalarNetAmount", "currencyCode","billingPeriod",
					"weightCP","weightLC","weightSV","weightEMS","amount","hsn","regInd","flightDate","overrideRounding"
			});
		}
		reportSpec.setReportMetaData(reportMetaData);
		/*Collection<CN66DetailsVO> cn66DetailsVOS = (Collection<CN66DetailsVO>)reportSpec.getData();//Modified for ICRD-105225 
		for(CN66DetailsVO cN66DetailsVO : cn66DetailsVOS){
			if (cN66DetailsVO.getBillingStatus() != null) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cN66DetailsVO.getMailCategoryCode().equals(
							oneTimeVO.getFieldValue())) {
						cN66DetailsVO.setMailCategoryCode(oneTimeVO
								.getFieldDescription());
					}
				}
			}
		}*/
		reportSpec.setData(cn66DetailsVos);
		return ReportAgent.generateReport(reportSpec);
	}
	
	/**
	 *  Method for updating the updateInvoiceReference number for THY invoice printing
	 * 	Method		: GPABillingTKController.updateInvoiceReference
	 *	Added by 	: A-5273 on Mar 20, 2014
	 * 	@param aoInvoiceReportDetailsVO
	 * 	@throws SystemException
	 *  void
	 */
	public void updateInvoiceReference(AOInvoiceReportDetailsVO aoInvoiceReportDetailsVO)
			 throws SystemException{
		 try {
			new CN51Summary().updateInvoiceReference(aoInvoiceReportDetailsVO);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getMessage(), finderException);
		}
	 }
	
	/**
	 * 	Method		:	GPABillingController.generateInvoiceTK
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160 generateInvoice specific for TK
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 * @throws SystemException
	 */
	public void generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{
		log.entering("GPABillingControlle", "generateInvoiceTK");
		//LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
/*		TransactionProvider tm = PersistenceController.getTransactionProvider();
		Transaction tx = tm.getNewTransaction(true);
		boolean success = false;*/
		try{
			Collection<PostalAdministrationVO> postalAdministrationVOs = findAllPACodes(generateInvoiceFilterVO);

			sendEmailsForPA(postalAdministrationVOs,generateInvoiceFilterVO);
			//sendEmailforPAs(postalAdministrationVOs,generateInvoiceFilterVO);
		}catch(SystemException se){
			log.log(Log.SEVERE, "Exception",se);
			//success = false;
		}//finally{
/*			if(success){
				tx.commit();
			}else{
				tx.rollback();
			}*/
			/*Collection<LockVO> lockvos=new ArrayList<LockVO>();
			TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
			generateInvoiceLockVO.setAction(GENINV);
			generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
			generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
			generateInvoiceLockVO.setDescription(LOCkDESC);
			generateInvoiceLockVO.setRemarks(LOCKREMARK);
			generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
			generateInvoiceLockVO.setScreenId(LOCKSCREENID);
			lockvos.add(generateInvoiceLockVO);
			releaseLocks(lockvos);
			isSuccessFlag="N";
			log.exiting("GPABillingControlle", "generateInvoiceTK");
		}*/
	}
	private void sendEmailsForPA(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException{

		for(PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs){
			try{
				Collection<PostalAdministrationVO> palist = new ArrayList<PostalAdministrationVO>(1);
				palist.add(postalAdministrationVO);
				new MailTrackingMRAProxy().sendEmailforTK(palist,generateInvoiceFilterVO);
			}catch (ProxyException e){
				throw new SystemException(e.getMessage());
			}
		}
	}
	
	/**
	 * 	Method		:	GPABillingController.findAllPACodes
	 *	Added by 	:	A-4809 on 06-Jan-2014
	 * 	Used for 	:	ICRD-42160 finding All PAs
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<PostalAdministrationVO>
	 */
	public Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException{
		log.entering("GPABillingControlle", "findAllPACodes");
		Collection<PostalAdministrationVO> postalAdministrationVOs = null;
		try {
			postalAdministrationVOs = new MailTrackingDefaultsProxy().findAllPACodes(generateInvoiceFilterVO);
		} catch (ProxyException e) {
			log.log(Log.SEVERE, "ExceptionCaught",e);
			throw new SystemException(e.getErrors());
		}
		log.exiting("GPABillingControlle", "findAllPACodes");
		return postalAdministrationVOs;
	}
	
	

	public void sendEmailforTK(Collection<PostalAdministrationVO> postalAdministrationVOs,
			GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {
		String outParameter[] = null;
		String invNumber = null;
		StringBuilder remarks = new StringBuilder();
		StringBuilder finalRemarks = new StringBuilder();
		String txnSta = BLANK;
		String failureFlag = null;
		String successFlag = null;
		String Syspar ="System Parameter Not found";
		LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<String> invoiceNumbers = new ArrayList<String>();
		//Added by A-8527 for ICRD-324512 starts
		Map<String,String> systemParameterMap =null;
		String overrideRounding =null;
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add(OVERRIDEROUNDING);
		try {
			systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		if(systemParameterMap !=null && systemParameterMap.size()>0){
			overrideRounding = systemParameterMap.get(OVERRIDEROUNDING);
		}
		//Added by A-8527 for ICRD-324512 Ends
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		if(postalAdministrationVOs !=null && postalAdministrationVOs.size() >0){
		for(PostalAdministrationVO postalAdministrationVO : postalAdministrationVOs){

			boolean success = false;
			GenerateInvoiceFilterVO invoiceFilterVO = new GenerateInvoiceFilterVO();
			invoiceFilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
			invoiceFilterVO.setBillingFrequency(postalAdministrationVO.getBillingFrequency());
			invoiceFilterVO.setBillingPeriodFrom(generateInvoiceFilterVO.getBillingPeriodFrom());
			invoiceFilterVO.setBillingPeriodTo(generateInvoiceFilterVO.getBillingPeriodTo());
			invoiceFilterVO.setCountryCode(postalAdministrationVO.getCountryCode());
			invoiceFilterVO.setGpaCode(postalAdministrationVO.getPaCode());
			invoiceFilterVO.setGpaName(postalAdministrationVO.getPaName());
			invoiceFilterVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());//Added for ICRD-211662
			invoiceFilterVO.setAddNew(generateInvoiceFilterVO.isAddNew());
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
			invoiceFilterVO.setCurrentDate(currentDate);
			outParameter = MRABillingMaster.generateInvoiceTK(invoiceFilterVO);
			if(outParameter != null){

				log.log(Log.FINE, "outParameter[0]",outParameter[0]);
				log.log(Log.FINE, "outParameter[1]",outParameter[1]);
				log.log(Log.FINE, "outParameter[2]",outParameter[2]);

			String[] parameter =  outParameter[0].split("#");

			 log.log(Log.FINE, "Parameters :>>>>>>>>>>>>>>>>",parameter);
			if(parameter!=null && parameter.length>0){
				if(OK.equals(parameter[0])){
					//if(PostalAdministrationVO.FLAG_YES.equals(postalAdministrationVO.getAutoEmailReqd())){
						CN51CN66FilterVO cN51CN66FilterVO =null;
						Collection<CN66DetailsVO> cn66DetailsVos =null;
						Collection<CN51DetailsVO> cn51DetailsVos =null;
						if(parameter.length > 1 && parameter[1]!=null && parameter[1].trim().length()>0){
							int len = parameter.length-1;
							for(int i=1;i<=len;i++){
							cN51CN66FilterVO = new CN51CN66FilterVO();
							cN51CN66FilterVO.setCompanyCode(postalAdministrationVO.getCompanyCode());
							cN51CN66FilterVO.setInvoiceNumber(parameter[i]);
							cN51CN66FilterVO.setGpaCode(postalAdministrationVO.getPaCode());
					   		 cn66DetailsVos = CN51Summary.generateCN66Report(cN51CN66FilterVO);
							 cn51DetailsVos = CN51Summary.generateCN51Report(cN51CN66FilterVO);
							 //added by A-8527 for ICRD-324283 starts
							 for(CN66DetailsVO cn66DetailsVo:cn66DetailsVos){
									cn66DetailsVo.setSettlementCurrencyCode(postalAdministrationVO.getSettlementCurrencyCode());
									cn66DetailsVo.setOverrideRounding(overrideRounding);
								}
							//added by A-8527 for ICRD-324283 Ends
							 invoiceNumbers.add(parameter[i]);
							 invNumber = parameter[i];
							 log.log(Log.FINE, "Invoice number",invNumber);
							//cN51CN66VO = findCN51CN66Details(cN51CN66FilterVO);
							/*Commented for TK
								String sysparValue = null;
								// the system parameter for audit value Y for activate audit and N for
								// deactivate audit
								String sysparCode = "mailtracking.mra.dsnauditrequired";

								ArrayList<String> systemParameters = new ArrayList<String>();
								systemParameters.add(sysparCode);

								HashMap<String, String> systemParameterMap = null;
								try {
									systemParameterMap = (HashMap<String, String>) new SharedDefaultsProxy()
									.findSystemParameterByCodes(systemParameters);
								} catch (ProxyException e) {
									throw new SystemException(e.getMessage());
								}
								log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
								if (systemParameterMap != null) {
									sysparValue = systemParameterMap.get(sysparCode);
									// if value is Y then call the audit method in helper
									if ("Y".equals(sysparValue)) {
										MRAAuditBuilder mRAAuditBuilder = new MRAAuditBuilder();
										mRAAuditBuilder.auditInvoiceGeneration(invNumber);
									}
								}*/
						if(PostalAdministrationVO.FLAG_YES.equals(postalAdministrationVO.getAutoEmailReqd())){
						if(cn66DetailsVos!=null && cn66DetailsVos.size()>0){
							//cN51CN66VO.setEmailToaddress(postalAdministrationVO.getEmail());
							InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
							invoiceDetailsReportVO.setPaName(postalAdministrationVO.getPaName());
							invoiceDetailsReportVO.setAddress(postalAdministrationVO.getAddress());
							invoiceDetailsReportVO.setCity(postalAdministrationVO.getCity());
							invoiceDetailsReportVO.setState(postalAdministrationVO.getState());
							invoiceDetailsReportVO.setCountry(postalAdministrationVO.getCountry());
							invoiceDetailsReportVO.setPhone1(postalAdministrationVO.getPhone1());
							invoiceDetailsReportVO.setPhone2(postalAdministrationVO.getPhone2());
							invoiceDetailsReportVO.setFax(postalAdministrationVO.getFax());
							invoiceDetailsReportVO.setFromBillingPeriod(generateInvoiceFilterVO.getBillingPeriodFrom());
							invoiceDetailsReportVO.setToBillingPeriod(generateInvoiceFilterVO.getBillingPeriodTo());
							log.log(Log.FINE, "Details send to sendEmail cn66DetailsVos:-",cn66DetailsVos);
							log.log(Log.FINE, "Details send to sendEmail cn51DetailsVos:-",cn51DetailsVos);
							log.log(Log.FINE, "Details send to sendEmail invoiceDetailsReportVO:-",invoiceDetailsReportVO);
							log.log(Log.FINE, "Details send to sendEmail cN51CN66FilterVO:-",cN51CN66FilterVO);
							log.log(Log.FINE, "Details send to sendEmail emailID:-",postalAdministrationVO.getEmail());
							try{
							sendEmail(cn66DetailsVos,cn51DetailsVos,invoiceDetailsReportVO,cN51CN66FilterVO,postalAdministrationVO.getEmail());
							}catch(SystemException exception){
								log.log(Log.FINE, "Issue in sending email invoice for the ",postalAdministrationVO.getPaCode());
							}
						}
						}
						}
					}

					success = true;
					successFlag ="Y";
					isSuccessFlag="Y";
				}else{
				success = false;
				failureFlag = "Y";
				// Added by A-3434 for CR ICRD-114599 on 29SEP2015 begins..For Invoice log screen

				 log.log(Log.FINE, "outParameter[1] before setting rmk111",outParameter[1] );

				// if ( outParameter[1].trim().length() > 0) {

					remarks.append(outParameter[1]);


						log.log(Log.FINE, "remarks inside",remarks.toString() );
				//}

				 if(Syspar.equals(outParameter[0])){

					 break;

				   }

				 if(postalAdministrationVOs.size() >1){
					    remarks.append(COMMA);
				   }

				}
			}



	}		//Release Locks for Each PA
			Collection<LockVO> lockvos=new ArrayList<LockVO>();
			TransactionLockVO generateInvoiceLockVO = new TransactionLockVO(GENINVMRA);
			generateInvoiceLockVO.setAction(GENINV);
			generateInvoiceLockVO.setClientType(ClientType.APPLICATION);
			generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
			generateInvoiceLockVO.setDescription(LOCkDESC);
			generateInvoiceLockVO.setRemarks(LOCKREMARK);
			generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
			generateInvoiceLockVO.setScreenId(LOCKSCREENID);
			lockvos.add(generateInvoiceLockVO);
			releaseLocks(lockvos);
			isSuccessFlag="N";
}
}

			if (generateInvoiceFilterVO.getCountryCode() != null
					&& generateInvoiceFilterVO.getCountryCode().trim().length() > 0) {
				finalRemarks.append("Country:")
						.append(generateInvoiceFilterVO.getCountryCode()).append(COMMA);
			}if (generateInvoiceFilterVO.getGpaCode()!= null
					&& generateInvoiceFilterVO.getGpaCode().trim().length() > 0) {
				finalRemarks.append("GpaCod:")
						.append(generateInvoiceFilterVO.getGpaCode()).append(COMMA);
			}


			finalRemarks.append("From Date:").append(generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayFormat("dd/MM/yyyy"))
					.append(COMMA).append("To Date:")
					.append(generateInvoiceFilterVO.getBillingPeriodTo().toDisplayFormat("dd/MM/yyyy"));



		   if("Y".equals(failureFlag) && "Y".equals(isSuccessFlag)){
			   txnSta = "P";
			   finalRemarks.append("\n").append("Failure Reason:");
		   }else if("Y".equals(failureFlag)){
			   txnSta = "F";
			   finalRemarks.append("\n").append("Failure Reason:");
		   }else if("Y".equals(successFlag)){
			   txnSta = "C";
			   remarks.append(COMMA);
			   if("P".equals(generateInvoiceFilterVO.getInvoiceType())){
				   if(outParameter[0] != null && outParameter[0].split("#").length >=2)
					   {
					   remarks.append("Proforma Invoice: ").append(outParameter[0].split("#")[1]).append(" generated Successfully");
					   }

			   }else{
			   remarks.append(outParameter[1]);
			   }


		   }

		   if(remarks != null){

				log.log(Log.FINE, "remarks last",remarks.toString() );
				//finalRemarks.append(COMMA);
				finalRemarks.append(remarks.toString());

           }
		   invoiceTransactionLogVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
		   //invoiceTransactionLogVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());
		   invoiceTransactionLogVO.setInvoiceType("GB");
		   invoiceTransactionLogVO.setTransactionCode(generateInvoiceFilterVO.getTransactionCode());
		   invoiceTransactionLogVO.setSerialNumber(generateInvoiceFilterVO.getInvoiceLogSerialNumber());

		   invoiceTransactionLogVO.setInvoiceGenerationStatus(txnSta);


		   if(finalRemarks != null && finalRemarks.length() >0){
		   invoiceTransactionLogVO.setRemarks(finalRemarks.toString());
		   }else{
			   invoiceTransactionLogVO.setRemarks(BLANK);
		   }

			try {
			     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage());
			}

			// Added by A-3434 for CR ICRD-114599 on 29SEP2015 ends




	}
	
	/**
	 * 	Method		:	GPABillingController.sendEmail
	 *	Added by 	:	A-4809 on 08-Jan-2014
	 * 	Used for 	:	ICRD-42160 sendEmail to address configured
	 *	Parameters	:	@param cN51CN66VO
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	public void sendEmail(Collection<CN66DetailsVO> cn66DetailsVos,Collection<CN51DetailsVO> cn51DetailsVos,InvoiceDetailsReportVO invoiceDetailsReportVO,CN51CN66FilterVO cn51CN66FilterVO,String email)
	throws SystemException{
		log.entering("GPABillingControlle", "sendEmail");
		ReportSpec reportSpec = new ReportSpec();
		//Collection<CN51CN66VO> cN51CN66VOs = new ArrayList<CN51CN66VO>();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs = new ArrayList<InvoiceDetailsReportVO>();
		LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		//cN51CN66VOs.add(cN51CN66VO);
		InvoiceDetailsReportVO invoiceDetailsReportVO1= new InvoiceDetailsReportVO();
		invoiceDetailsReportVO1 = CN51Summary.generateInvoiceReportTK(cn51CN66FilterVO);
		InvoiceDetailsReportVO invoiceReportVO = CN51Summary
		.generateInvoiceReport(cn51CN66FilterVO);
		if(invoiceReportVO!=null){
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setBilledDateString(invoiceReportVO.getBilledDateString());
			invoiceDetailsReportVO1.setTotalAmountinBillingCurrency(invoiceReportVO.getTotalAmountinBillingCurrency());
			invoiceDetailsReportVO1.setPaName(invoiceReportVO.getPaName());
			invoiceDetailsReportVO1.setPhone1(invoiceReportVO.getPhone1());
			invoiceDetailsReportVO1.setPhone2(invoiceReportVO.getPhone2());
			invoiceDetailsReportVO1.setAddress(invoiceReportVO.getAddress());
			invoiceDetailsReportVO1.setCity(invoiceReportVO.getCity());
			invoiceDetailsReportVO1.setState(invoiceReportVO.getState());
			invoiceDetailsReportVO1.setCountry(invoiceReportVO.getCountry());
			invoiceDetailsReportVO1.setFax(invoiceReportVO.getFax());
		}
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO1);
   		reportSpec.addExtraInfo(cn51CN66FilterVO);
   		reportSpec.addSubReportParameter(cn51CN66FilterVO);
		reportSpec.setData(invoiceDetailsReportVOs);
		/*Collection<CN51DetailsVO> updatedCN51DetailsVO = new ArrayList<CN51DetailsVO>();
		Collection<CN66DetailsVO> updatedCN66DetailsVO = new ArrayList<CN66DetailsVO>();*/
/*		for(CN51DetailsVO CN51DetailsVO: cN51CN66VO.getCn51DetailsVOs()){
			updatedCN51DetailsVO.add(CN51DetailsVO);
		}
		for(CN66DetailsVO CN66DetailsVO: cN51CN66VO.getCn66DetailsVOs()){
			updatedCN66DetailsVO.add(CN66DetailsVO);
		}*/
		//reportSpec.addSubReportData(cn51DetailsVos);
		//reportSpec.addSubReportData(cn66DetailsVos);
		reportSpec.setProductCode(PRODUCT_CODE);
		reportSpec.setSubProductCode(SUB_PRODUCTCODE);
		ReportMetaData metaData = new ReportMetaData();
		/*metaData.setColumnNames(new String[] { "INVNUM", "SECTOR",
   				"BLGPRDFRM", "BLGPRDTOO", "TOTAMTBLGCUR", "BLGCURCOD",
   				"POANAM", "POAADR", "CITY", "STATE", "COUNTRY", "PHONE1",
   				"PHONE2", "FAX", "VATNUM", "BLDPRD", "MALCTGCOD" });
		metaData.setFieldNames(new String[] { "invoiceNumber", "sector",
   				"fromDateString", "toDateString",
   				"totalAmountinBillingCurrency", "billingCurrencyCode",
   				"paName", "address", "city", "state", "country", "phone1",
   				"phone2", "fax", "vatNumber", "billedDateString",
   		"mailCategoryCode" });*/
		metaData.setColumnNames(new String[] {"BLGSITCOD","BLGSITNAM","ARLADR","CORADR","SGNONE","DSGONE","SGNTWO","DSGTWO",
				"CURCOD","BNKNAM","BNKBRC","ACCNUM","CTYNAM","CNTNAM","SWTCOD","IBNNUM",
				"CURCOD1","BNKNAM1","BNKBRC1","ACCNUM1","CTYNAM1","CNTNAM1","SWTCOD1","IBNNUM1",
				"TOTAMTBLGCUR", "BLGCURCOD", "POANAM", "POAADR", "CITY", "STATE", "COUNTRY", "FAX", "VATNUM", "BLDPRD", "MALCTGCOD","CCOMID","INVDAT" });
		metaData.setFieldNames(new String[] {"billingSiteCode","billingSite","airlineAddress","correspondenceAddress",
				"signatorOne","designatorOne","signatorTwo","designatorTwo",
				"currency","bankName","branch","accNo","bankCity","bankCountry","swiftCode","ibanNo",
				"currencyOne","bankNameOne","branchOne","accNoOne","bankCityOne","bankCountryOne","swiftCodeOne","ibanNoOne",
				"totalAmountinBillingCurrency","billingCurrencyCode","paName","address","city","state",
				"country","fax","vatNumber","billedDateString","mailCategoryCode","clearComId","toDateString"});
		reportSpec.setReportMetaData(metaData);
   		Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeHashMap=null;
		try {
			oneTimeHashMap = new SharedDefaultsProxy().findOneTimeValues(
					cn51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		if (oneTimeHashMap != null) {
			mailCategory = oneTimeHashMap.get("mailtracking.defaults.mailcategory");
		}
		Collection<CN66DetailsVO> updatedCN66DetailsVO = new ArrayList<CN66DetailsVO>();
		/*if (cn66DetailsVos != null && cn66DetailsVos.size() > 0) {
		for (CN66DetailsVO cN66DetailsVO : cn66DetailsVos) {
				cN66DetailsVO.setBillFrm(logonAttributes.getOwnAirlineCode());
			if (cN66DetailsVO.getBillingStatus() != null) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cN66DetailsVO.getMailCategoryCode().equals(
							oneTimeVO.getFieldValue())) {
						cN66DetailsVO.setMailCategoryCode(oneTimeVO
								.getFieldDescription());
							updatedCN66DetailsVO.add(cN66DetailsVO);
						}
					}
				}
			}
		}*/
		Collection<CN51DetailsVO> cn51DetailsVoS = new ArrayList<CN51DetailsVO>();
		if (cn51DetailsVos != null && cn51DetailsVos.size() > 0) {
			for (CN51DetailsVO cn51VO : cn51DetailsVos) {
				for (OneTimeVO oneTimeVO : mailCategory) {
					if (cn51VO.getMailCategoryCode().equals(oneTimeVO.getFieldValue())) {
						cn51VO.setMailCategoryCode(oneTimeVO.getFieldDescription());
					}
				}
				cn51VO.setMonthFlag("C");
				cn51VO.setSector(cn51VO.getOrigin().concat("-").concat(cn51VO.getDestination()));
				cn51VO.setAirlineCode(logonAttributes.getOwnAirlineCode());
				cn51DetailsVoS.add(cn51VO);
			}
		}
		ReportMetaData parameterMetaData = new ReportMetaData();
   		parameterMetaData.setFieldNames(new String[] { "airlineCode" });
 		ReportMetaData reportMetaDataForCN51 = new ReportMetaData();
   		reportMetaDataForCN51.setColumnNames(new String[] {
   				"SECTOR","MALCTGCOD","TOTWGT","APLRAT","VATAMT","NETAMT","MALSUBCLS",
   				"MONTHFLAG","TOTAMTLC","TOTAMTCP","INVNUM","POACOD","BILLFRM"
   		});
   		reportMetaDataForCN51.setFieldNames(new String[] {
   				"sector","mailCategoryCode","totalWeight","applicableRate","valCharges",
   				"totalAmount","mailSubclass","monthFlag","totalAmountLC","totalAmountCP","invoiceNumber","gpaCode",
   				"airlineCode"
   		});
   		reportSpec.addParameterMetaData(parameterMetaData);
   		reportSpec.addParameter(cn51CN66FilterVO);
   		reportSpec.addSubReportMetaData(reportMetaDataForCN51);
   		reportSpec.addSubReportData(cn51DetailsVoS);
   		ReportMetaData reportMetaDataForCN61 = new ReportMetaData();
   		reportMetaDataForCN61.setColumnNames(new String[] { "MALCTGCOD", "DSN", "RSN", "CCAREFNUM", "ORGCOD", "DSTCOD",
   				"SECTOR", "FLTNUM", "TOTWGT", "APLRAT", "BLDAMT", "SRVTAX", "VATAMT", "NETAMT", "BLGCURCOD","BLDPRD"
   				 //"RMK", "MALSUBCLS", "MONTHFLG", "BLDPRD" ,"TOTPCS"
   				,"TOTWGTCP","TOTWGTLC","TOTWGTEMS","TOTWGTSV","FLTDAT"
   				});
   		reportMetaDataForCN61.setFieldNames(new String[] { "mailCategoryCode", "dsn", "rsn", "ccaRefNo",
   				"origin", "destination", "sector",
   				"flightNumber", "totalWeight", "applicableRate", "amount", "serviceTax", "valCharges", "netAmount", "currencyCode","billingPeriod",
   				"weightCP","weightLC","weightEMS","weightSV","flightDate"
   				//"billingStatus","mailSubclass", "monthFlag", "billingPeriod","totalPieces"
   				});
   		reportSpec.addSubReportMetaData(reportMetaDataForCN61);
   		reportSpec.addSubReportData(cn66DetailsVos);
		reportSpec.setPreview(false);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setReportTitle(REPORT_TITLE);
		reportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
		reportSpec.setShouldExport(true);
		String attachmentName="Invoice report";
		String printerName="";
		String messageType="MTKINVRPT";
		String date =null;
		if(invoiceDetailsReportVO.getFromBillingPeriod()!=null){
		 date = TimeConvertor.toStringFormat(invoiceDetailsReportVO.getFromBillingPeriod().toCalendar(), "MM-yyyy");
		}
		StringBuilder sb = new StringBuilder("Dear Sir/Madam,");
		sb.append("\n").append("\n").append("Your airmail transportation Invoice and details (CN51, CN66) with Turkish Airlines Inc. on ").append(date).append(" ").append("period attached.").
		append("\n").append("According to our company procedures, you are kindly requested to pay in one month upon receipt of this e-mail.").append("\n").
		append("Your invoice will be sent by postal mail also.").append("\n").append("\n").append("Kind Regards").append("\n").append("\n").append("Turkish Airlines/Turkish Cargo").
		append("\n").append("Post Office 34830").append("\n").append("Yesilkoy/Istanbul/Turkey").append("\n").append("mailbilling@thy.com");
		EmailVO emailVO = populateEmailVO(logonAttributes.getCompanyCode(),attachmentName,ReportConstants.FORMAT_PDF,printerName,messageType);
		emailVO.setToEmailIDs(email);
		emailVO.setEmailBody(sb.toString());
		log.log(Log.FINE, "Email VOs formed",emailVO);
		reportSpec.setEmailVO(emailVO);
		ReportAgent.generateReport(reportSpec);
		log.exiting("GPABillingControlle", "sendEmail");
	}
	
	/**
	 * 	Method		:	GPABillingController.releaseLocks
	 *	Added by 	:	A-4809 on 10-Jan-2014
	 * 	Used for 	:	ICRD-42160
	 *	Parameters	:	@param lockVOs
	 *	Parameters	:	@throws ObjectNotLockedException
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	void
	 */
	   private void  releaseLocks(Collection<LockVO> lockVOs)
	   throws ObjectNotLockedException,SystemException{
		   try {
			   new FrameworkLockProxy().releaseLocks(lockVOs);
		   }  catch (ProxyException ex) {
			   throw new SystemException(ex.getMessage(),ex);
		   }
		   catch (SystemException ex) {
			   log.log(Log.SEVERE, "System Exception");
			   boolean isFound = false;
			   if (ex.getErrors() != null && ex.getErrors().size() > 0) {
				   for (ErrorVO errvo : ex.getErrors()) {
					   if(ObjectNotLockedException.OBJECT_NOT_LOCKED
							   .equals(errvo.getErrorCode())){
						   isFound = true;
						   break;
					   }
				   }
			   }
			   if (isFound) {
				   throw new ObjectNotLockedException(ex);
			   }
			   throw new SystemException(ex.getErrors());
		   }
	   }
	   /**
		 * 	Method		:	GPABillingController.populateEmailVO
		 *	Added by 	:	A-4809 on 08-Jan-2014
		 * 	Used for 	:	ICRD-42160 to populateEmailVO
		 *	Parameters	:	@return
		 *	Return type	: 	EmailVO
		 *  Parameters  :   @throws SystemException
		 */
		private EmailVO populateEmailVO(String companyCode,String attachmentName, String attachmentType, String printerName, String messageType)
		throws SystemException {
			EmailVO emailVO = new EmailVO();
		try {
			emailVO.setAttachementName(attachmentName);
			emailVO.setAttachementType(attachmentType);
			emailVO.setEmailBody("");
			emailVO.setSubject(printerName);
			 ArrayList<MessageAddressDetailVO> addressDetails=null;
				ArrayList<String> systemParameters = new ArrayList<String>();
				MessageAddressDetailVO addressDetailVO= new MessageAddressDetailVO();
				MessageAddressVO addressVO=new MessageAddressVO();
				ArrayList<MessageAddressVO> addresses=null;
				systemParameters.add(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
			 String emailInterface = null;
				HashMap<String, String> systemParameterMap = null;
				try{
					systemParameterMap = (HashMap<String, String>)new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
				}catch(ProxyException proxyException){
					throw new SystemException(proxyException.getMessage());
				}
				if (systemParameterMap != null){
					emailInterface = systemParameterMap.get(MSGBROKER_EMAIL_INTERFACE_PARAMETER);
				}
				if(emailInterface==null){
					emailInterface=MSGBROKER_EMAIL_INTERFACE;
				}
				MessageAddressFilterVO filterVO=new MessageAddressFilterVO();
				filterVO.setCompanyCode(companyCode);
				//filterVO.setInterfaceSystem(emailInterface);
				//filterVO.setAirportCode(companyCode);
				filterVO.setMessageType(messageType);
				//Added as part of BUG ICRD-108425 by A-5526 starts
				filterVO.setProfileStatus(MessageAddressVO.STATUS_ACTIVE);
				//Added as part of BUG ICRD-108425 by A-5526 ends
				addresses=(ArrayList<MessageAddressVO>)	new MessageBrokerConfigProxy().findMessageAddressDetails(filterVO);
				log.log(Log.FINE, "Address obtained",addresses);
				if(addresses!=null){
					for(int j=0;j<addresses.size();j++){
						addressVO=addresses.get(j);
						addressDetails=(ArrayList<MessageAddressDetailVO>)addressVO.getMessageAddressDetails();
						for(int i=0;i<addressDetails.size();i++){
							addressDetailVO=addressDetails.get(i);
							MessageBrokerConfigProxy messageBrokerConfigProxy = new MessageBrokerConfigProxy();
							Collection<MessageModeParameterVO> addDtls = new ArrayList<MessageModeParameterVO>();
							addDtls = messageBrokerConfigProxy.getSplitedAddress(companyCode, CommunicationVO.MODE_EMAIL, addressDetailVO.getModeAddress());
								for(MessageModeParameterVO messageModeParameterVO : addDtls){
								if(MessageModeParameterVO.EMAIL_FROM_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
									emailVO.setFromAddress(messageModeParameterVO.getParameterValue());
								}
								if(MessageModeParameterVO.EMAIL_CC_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
									emailVO.setCcEmailIDs(messageModeParameterVO.getParameterValue());
								}
								if(MessageModeParameterVO.EMAIL_BCC_ADDRESS.equals(messageModeParameterVO.getParameterCode())){
									emailVO.setBccEmailIDs(messageModeParameterVO.getParameterValue());
								}
								if(MessageModeParameterVO.EMAILADDRESS.equals(messageModeParameterVO.getParameterCode())){
									emailVO.setToEmailIDs(messageModeParameterVO.getParameterValue());
								}
								if(emailVO.getSubject() == null && MessageModeParameterVO.EMAIL_SUBJECT.equals(messageModeParameterVO.getParameterCode())){
									emailVO.setSubject(messageModeParameterVO.getParameterValue());
								}
							}
						}
					}
					log.log(Log.FINE, "Email VO constructed",emailVO);
				}
			} catch(ProxyException pe){
				log.log(Log.SEVERE, "ProxyException",pe);
			}
			return emailVO;
		}
	}
