/*
 * SavePACommand.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class SavePACommand extends BaseCommand {

	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("SavePACommand");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID =
		"mailtracking.defaults.masters.postaladministration";	
	private static final String DUPLICATE_CODE =
		"mailtracking.defaults.pamaster.msg.err.dupeCode";
	private static final String SAVE_SUCCESS =
		"mailtracking.defaults.pamaster.msg.info.savesuccess";
	private static final String PA_CODE_EMPTY =
		"mailtracking.defaults.pamaster.msg.err.paempty";
	private static final String PA_NAME_EMPTY =
		"mailtracking.defaults.pamaster.msg.err.panameempty";
	private static final String COUNTRY_EMPTY =
		"mailtracking.defaults.pamaster.msg.err.countryempty";
	private static final String CITY_EMPTY =
		"mailtracking.defaults.pamaster.msg.err.cityempty";
	
	private static final String RESDITVER_EMPTY =
		"mailtracking.defaults.pamaster.msg.err.resditverempty";
	private static final String MAIL_PARCODE="mailtracking.defaults.parcod";

	//private static final String INVALID_CUSTOMER =	"mailtracking.defaults.pamaster.msg.err.invalidcustomer";

/*	private static final String ACC_NUMBER_EMPTY=
		"mailtracking.defaults.pamaster.msg.err.accnoempty";*/
	private static final String PARVAL_EMPTY = "mailtracking.defaults.pamaster.msg.err.parvalempty";
	private static final String INVINFO="INVINFO";
	private static final String FROMDATE_EMPTY="mailtracking.defaults.pamaster.msg.err.fromdateempty";
	private static final String TODATE_EMPTY="mailtracking.defaults.pamaster.msg.err.todateempty";
	private static final String CWTST="CWTST";
	private static final String PAWBASSCONENAB="PAWBASSCONENAB";
	private static final String ALL="ALL";
	private static final String HYPHEN="-";
	//private static final String MESSAGING_PARTIAL = "P";
	//Added by A-7794 as part of ICRD-233590
	private static final String PA_PARTY_IDR_EMPTY =
			"mailtracking.defaults.pamaster.msg.err.papartyidrempty";
	//added as part of IASCB-853
		private static final String EMAIL_EMPTY="mailtracking.defaults.emailempty";
		private static final String NOT_EMAIL="mailtracking.defaults.notavalidemail";
		private static final String SINGLE_EMAIL="mailtracking.defaults.singleemail";
		private static final String PRF_NOTFOR_PAS="mailtracking.defaults.profomanotforpas";
		//Added as part of IASCB-853 starts 
		private static final String COMMA=",";
		private static final String BLANK="";
		//Added as part of IASCB-853 ends
	private static final String DUPLICATE_PARCODE ="mailtracking.defaults.pamaster.msg.err.dupeParCode";
	private static final String INVALID_FRCIMPVAL ="mailtracking.defaults.pamaster.msg.err.invalidfrcimpval";
	private static final String FORCE_IMPORT="FRCIMP";	
	private static final String PASBLGID="PASBLGID";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the save command----------> \n\n");

		PostalAdministrationForm paMasterForm =
			(PostalAdministrationForm)invocationContext.screenModel;
		PostalAdministrationSession paSession =
			getScreenSession(MODULE_NAME,SCREEN_ID);   
		HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsMap=null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		//ArrayList<MailEventVO> mailEventVOs = new ArrayList<MailEventVO>();
		ArrayList<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs = new ArrayList<PostalAdministrationDetailsVO>();
		Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOForDisplay=new ArrayList<PostalAdministrationDetailsVO>();
		Collection <PostalAdministrationDetailsVO> postDetailsVOs = new ArrayList<PostalAdministrationDetailsVO>();
		String[] opFlag = paMasterForm.getOperationFlag();
		//String[] rowIds = paMasterForm.getRowId();
		//if(MESSAGING_PARTIAL.equals(paMasterForm.getMessagingEnabled())) {
//		if(opFlag != null) {
//			MailEventVO mailEventVO = null;
//
//			String recieved = paMasterForm.getRecievedArray();
//			String[] recievedArray = recieved.split(",");
//
//			String handoverRcvdArray = paMasterForm.getHandoverRcvdArray();
//			String[] handoverRcvdArr = handoverRcvdArray.split(",");
//
//			String loadedArray = paMasterForm.getLoadedArray();
//			String[] loadedArr = loadedArray.split(",");
//
//			String onlineHandoverArray = paMasterForm.getOnlineHandoverArray();
//			String[] onlineHandoverArr = onlineHandoverArray.split(",");
//
//			String uplifted = paMasterForm.getUpliftedArray();
//			String[] upliftedArray = uplifted.split(",");
//
//			String assigned = paMasterForm.getAssignedArray();
//			String[] assignedArray = assigned.split(",");
//
//			String returned = paMasterForm.getReturnedArray();
//			String[] returnedArray = returned.split(",");
//
//			String handedOver = paMasterForm.getHandedOverArray();
//			String[] handedOverArray = handedOver.split(",");
//
//			String pending = paMasterForm.getPendingArray();
//			String[] pendingArray = pending.split(",");
//
//			//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 starts
//			String readyForDelivery = paMasterForm.getReadyForDeliveryArray();
//			String[] readyForDeliveryArray = readyForDelivery.split(",");
//			String transportationCompleted = paMasterForm.getTransportationCompletedArray();
//			String[] transportationCompletedArray = transportationCompleted.split(",");
//			String arrived = paMasterForm.getArrivedArray();
//			String[] arrivedArray = arrived.split(",");
//			//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 end
//			String delivered = paMasterForm.getDeliveredArray();
//			String[] deliveredArray = delivered.split(",");
//
//			for(int i=0;i<opFlag.length;i++) {
//
//				if(!"NOOP".equals(opFlag[i])){
//					mailEventVO = new MailEventVO();
//
//					mailEventVO.setPaCode(paMasterForm.getPaCode().toUpperCase());
//					if(paMasterForm.getMailCategory()[i]==null||paMasterForm.getMailCategory()[i].trim().length()<=0){	
//						mailEventVO.setMailCategory(ALL);
//					}else{
//						mailEventVO.setMailCategory(paMasterForm.getMailCategory()[i].toUpperCase());
//					}
//					if(paMasterForm.getMailClass()[i]==null||paMasterForm.getMailClass()[i].trim().length()<=0){		
//						mailEventVO.setMailClass(ALL);   
//					}else{
//					mailEventVO.setMailClass(paMasterForm.getMailClass()[i].toUpperCase());
//					}
//
//					if(("true").equals(recievedArray[i])) {
//						mailEventVO.setReceived(true);
//					}else {
//						mailEventVO.setReceived(false);
//					}
//
//					if(("true").equals(handoverRcvdArr[i])) {
//						mailEventVO.setHandedOverReceivedResditFlag(true);
//					}else {
//						mailEventVO.setHandedOverReceivedResditFlag(false);
//					}
//
//					if(("true").equals(loadedArr[i])) {
//						mailEventVO.setLoadedResditFlag(true);
//					}else {
//						mailEventVO.setLoadedResditFlag(false);
//					}
//
//					if(("true").equals(onlineHandoverArr[i])) {
//						mailEventVO.setOnlineHandedOverResditFlag(true);
//					}else {
//						mailEventVO.setOnlineHandedOverResditFlag(false);
//					}
//
//					if(("true").equals(upliftedArray[i])) {
//						mailEventVO.setUplifted(true);
//					}else {
//						mailEventVO.setUplifted(false);
//					}
//
//					if(("true").equals(assignedArray[i])) {
//						mailEventVO.setAssigned(true);
//					}else {
//						mailEventVO.setAssigned(false);
//					}
//
//					if(("true").equals(returnedArray[i])) {
//						mailEventVO.setReturned(true);
//					}else {
//						mailEventVO.setReturned(false);
//					}
//
//					if(("true").equals(handedOverArray[i])) {
//						mailEventVO.setHandedOver(true);
//					}else {
//						mailEventVO.setHandedOver(false);
//					}
//
//					if(("true").equals(pendingArray[i])) {
//						mailEventVO.setPending(true);
//					}else {
//						mailEventVO.setPending(false);
//					}
//					//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 starts
//					if(("true").equals(readyForDeliveryArray[i])) {
//						mailEventVO.setReadyForDelivery(true);
//					}else {
//						mailEventVO.setReadyForDelivery(false);
//					}
//
//					if(("true").equals(transportationCompletedArray[i])) {
//						mailEventVO.setTransportationCompleted(true);
//					}else {
//						mailEventVO.setTransportationCompleted(false);
//					}
//					if(("true").equals(arrivedArray[i])) {
//						mailEventVO.setArrived(true);
//					}else {
//						mailEventVO.setArrived(false);
//					}
//					//Added by A-5201 for ICRD-85233,ICRD-79018,ICRD-80366 end
//					if(("true").equals(deliveredArray[i])) {
//						mailEventVO.setDelivered(true);
//					}else {
//						mailEventVO.setDelivered(false);
//					}
//					mailEventVO.setCompanyCode(logonAttributes.getCompanyCode());
//					mailEventVO.setOperationFlag(paMasterForm.getOperationFlag()[i]);
//					//Added as part of bug ICRD-159637 by A-5526 starts
//					if(MailConstantsVO.FLAG_NO.equals(paMasterForm.getMessagingEnabled())){
//						
//						mailEventVO.setReceived(false);       
//						mailEventVO.setHandedOverReceivedResditFlag(false);
//						mailEventVO.setLoadedResditFlag(false);
//						mailEventVO.setOnlineHandedOverResditFlag(false);    
//						mailEventVO.setUplifted(false);
//						mailEventVO.setAssigned(false);
//						mailEventVO.setReturned(false);
//						mailEventVO.setHandedOver(false);
//						mailEventVO.setPending(false);
//						mailEventVO.setReadyForDelivery(false);
//						mailEventVO.setTransportationCompleted(false);
//						mailEventVO.setArrived(false);
//						mailEventVO.setDelivered(false);
//					}
//					//Added as part of bug ICRD-159637 by A-5526 ends
//					mailEventVOs.add(mailEventVO);
//				}
//			}
//		}

		log.log(Log.FINE, "\n\n paMasterForm.getMessagingEnabled()====> ",
				paMasterForm.getMessagingEnabled());
		PostalAdministrationVO paVO = new PostalAdministrationVO();
		int maxBillingSerNum=0;
		int maxSettSerNum=0;
		//added for icrd-7298
		int maxInvSerNum = 0;
		//added by A-7794 as part of ICRD-223754
		int upuCount= 0;

		if(paSession.getPaVO() != null){
			log.log(Log.FINE, "from session pavo", paSession.getPaVO());
			paVO = paSession.getPaVO();
			if(paSession.getPostalAdministrationDetailsVOs()!=null && paSession.getPostalAdministrationDetailsVOs().size()>0){
				log.log(Log.FINE, "from session detailvos", paSession.getPostalAdministrationDetailsVOs());
				HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdminDetailsVOs=paSession.getPostalAdministrationDetailsVOs();
				if(postalAdminDetailsVOs.containsKey("BLGINFO")){
					Collection<PostalAdministrationDetailsVO> pADetailVos=postalAdminDetailsVOs.get("BLGINFO");
					if(pADetailVos!=null && pADetailVos.size()>0){
						for(PostalAdministrationDetailsVO pAdministrationDetailsVO:pADetailVos){
							maxBillingSerNum=Integer.parseInt(pAdministrationDetailsVO.getSernum());
						}
					}
				}
				if(postalAdminDetailsVOs.containsKey("STLINFO")){
					Collection<PostalAdministrationDetailsVO> pADetailVOs=postalAdminDetailsVOs.get("STLINFO");
					if(pADetailVOs!=null && pADetailVOs.size()>0){
						for(PostalAdministrationDetailsVO pAdminDetailsVO:pADetailVOs){
							maxSettSerNum=Integer.parseInt(pAdminDetailsVO.getSernum());
						}
					}

				}
				if(postalAdminDetailsVOs.containsKey(INVINFO)){
					Collection<PostalAdministrationDetailsVO> pADetailVOs=postalAdminDetailsVOs.get(INVINFO);
					if(pADetailVOs!=null && pADetailVOs.size()>0){
						for(PostalAdministrationDetailsVO pAdminDetailsVO:pADetailVOs){
							if(Integer.parseInt(pAdminDetailsVO.getSernum()) > maxInvSerNum) {
							maxInvSerNum=Integer.parseInt(pAdminDetailsVO.getSernum());
						}
						}
						postDetailsVOs.addAll(pADetailVOs);
					}
				}
			}
		}

		log.log(Log.FINE, "\n\n maxSettSerNum====> ", maxSettSerNum);
		log.log(Log.FINE, "\n\n maxBillingSerNum====> ", maxBillingSerNum);
		String[] bilOpFlag=paMasterForm.getBilOpFlag();

		if(bilOpFlag!=null){

			PostalAdministrationDetailsVO postalAdministrationDetailsVO=null;
			String billingSource[]=paMasterForm.getBillingSource();
			String billingFrequency[]=paMasterForm.getBillingFrequency();
			/*String[] profInv=paMasterForm.getProfInv();*/
			String[]  validFrm=paMasterForm.getValidFrom();
			String[] validTo=paMasterForm.getValidTo();
			String[] billingSerNum=paMasterForm.getBilSerNum();
			LocalDate fromDate=null;
			LocalDate toDate=null;


			for(int i=0;i<bilOpFlag.length-1;i++) {

				if(!"NOOP".equals(bilOpFlag[i])){

					//create new obeject and set values
					postalAdministrationDetailsVO=new PostalAdministrationDetailsVO();
					postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					postalAdministrationDetailsVO.setPoaCode(paMasterForm.getPaCode().toUpperCase());
					postalAdministrationDetailsVO.setParCode("BLGINFO");
					if(!"D".equals(bilOpFlag[i])){
						postalAdministrationDetailsVO.setSernum(String.valueOf(++maxBillingSerNum));
					}
					else{
						if(billingSerNum[i]!=null){
							postalAdministrationDetailsVO.setSernum(billingSerNum[i]);
						}
					}


					postalAdministrationDetailsVO.setOperationFlag(bilOpFlag[i]);
					postalAdministrationDetailsVO.setBillingSource(billingSource[i]);
					postalAdministrationDetailsVO.setBillingFrequency(billingFrequency[i]);
					/*if(profInv[i]!=null){
					if(profInv[i].equals("Y")){
					postalAdministrationDetailsVO.setProfInv("Y");
					}
				}else{*/
					postalAdministrationDetailsVO.setProfInv(null);
					//	}

					if(validFrm[i]!=null && validFrm[i].length()>0){

						fromDate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						fromDate.setDate(validFrm[i]);

						postalAdministrationDetailsVO.setValidFrom(fromDate);
					}
					else{

						ErrorVO error = new ErrorVO(FROMDATE_EMPTY);
						errors.add(error);
					}
					if(validTo[i]!=null && validTo[i].length()>0){

						toDate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						toDate.setDate(validTo[i]);

						postalAdministrationDetailsVO.setValidTo(toDate);
						if(fromDate.isGreaterThan(toDate)){
							ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.fromdateisgreaterthantodate");
							errors.add(error);
						}
					}
					else{

						ErrorVO error = new ErrorVO(TODATE_EMPTY);
						errors.add(error);
					}

					if(i>=1){
						if(!"D".equals(bilOpFlag[i])){
							LocalDate prevToDate=new LocalDate(LocalDate.NO_STATION,
									Location.NONE, false).setDate((validTo[i-1]));

							if(prevToDate!=null){
								if(!"D".equals(bilOpFlag[i-1])&&!"NOOP".equals(bilOpFlag[i-1])){//Added for ICRD-155284
									//Modified as part of bug ICRD-154140 by A-5526
									if((fromDate!=null && fromDate.isLesserThan(prevToDate)) || (fromDate!=null && fromDate.equals(prevToDate))||(toDate!=null && toDate.isLesserThan(prevToDate))||(toDate!=null && toDate.equals(prevToDate))){
										ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.billingperiodcantoverlap");
										errors.add(error);
									}
								}
							}
						}

					}
					postalAdministrationDetailsVOs.add(postalAdministrationDetailsVO);
					postalAdministrationDetailsVOForDisplay.add(postalAdministrationDetailsVO);
					if(postalAdministrationDetailsMap==null)
					{
						postalAdministrationDetailsMap=new HashMap<>();	
					}
					postalAdministrationDetailsMap.put("BLGINFO",postalAdministrationDetailsVOForDisplay );
					paVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsMap);
					paSession.setPostalAdministrationDetailsVOs(paVO.getPostalAdministrationDetailsVOs());
					
					if(errors != null && errors.size()>0) {
						paSession.setPaVO(paVO);
						
						invocationContext.addAllError(errors);
						invocationContext.target = FAILURE;
						return;
					}
					
					
				}

			}



		}
		String[] stlOpFlag=paMasterForm.getStlOpFlag();
		
		if(stlOpFlag!=null){
			PostalAdministrationDetailsVO postalAdministrationDetailsVO=null;
			postalAdministrationDetailsVOForDisplay=new ArrayList<>();
			LocalDate curntDate=null;
			String settlementCurrencyCode[]=paMasterForm.getSettlementCurrencyCode();
			 curntDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
			String[]  validSetFrm=paMasterForm.getValidSetFrom();
			String[] validSetTo=paMasterForm.getValidSetTo();
			String[] settSerNum=paMasterForm.getSeSerNum();

			LocalDate fromsetdate=null;
			LocalDate tosetdate=null;
			for(int i=0;i<stlOpFlag.length-1;i++) {
				if(!"NOOP".equals(stlOpFlag[i]) && !"N".equals(stlOpFlag[i])){
					postalAdministrationDetailsVO=new PostalAdministrationDetailsVO();
					postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					postalAdministrationDetailsVO.setPoaCode(paMasterForm.getPaCode().toUpperCase());
					postalAdministrationDetailsVO.setParCode("STLINFO");
					if(!"D".equals(stlOpFlag[i])&&!"U".equals(stlOpFlag[i])){
						postalAdministrationDetailsVO.setSernum(String.valueOf(++maxSettSerNum));
					}
					else{
						if(settSerNum[i]!=null){
							postalAdministrationDetailsVO.setSernum(settSerNum[i]);
						}
					}


					postalAdministrationDetailsVO.setOperationFlag(stlOpFlag[i]);
					postalAdministrationDetailsVO.setSettlementCurrencyCode(settlementCurrencyCode[i]);
						if(!"D".equals(stlOpFlag[i])){
					if(validSetFrm[i]!=null && validSetFrm[i].length()>0){
						fromsetdate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						fromsetdate.setDate(validSetFrm[i]);
						postalAdministrationDetailsVO.setValidFrom(fromsetdate);
					}
					else{

						ErrorVO error = new ErrorVO(FROMDATE_EMPTY);
						errors.add(error);
					}
					if(validSetTo[i]!=null && validSetTo[i].length()>0){
						tosetdate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						tosetdate.setDate(validSetTo[i]);
						postalAdministrationDetailsVO.setValidTo(tosetdate);
						if(fromsetdate.isGreaterThan(tosetdate)){
							ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.fromdateisgreaterthantodate");
							errors.add(error);
						}
					}
					else{
						ErrorVO error = new ErrorVO(TODATE_EMPTY);
						errors.add(error);
					}
					
					if(!(tosetdate.isGreaterThan(curntDate)))
					{
						ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.todateislessthancurrentDate");
						errors.add(error);	
					}
						}		
					if(i>=1 && !"D".equals(stlOpFlag[i])){
							checkForSettlementCurrencyOverLap(errors, stlOpFlag, validSetFrm, validSetTo, fromsetdate,
									tosetdate, i);	
					}
					postalAdministrationDetailsVOs.add(postalAdministrationDetailsVO);
					
					postalAdministrationDetailsVOForDisplay.add(postalAdministrationDetailsVO);
					if(postalAdministrationDetailsMap==null) 
					{
						postalAdministrationDetailsMap=new HashMap<>();	
					}
					
					postalAdministrationDetailsMap.put("STLINFO", postalAdministrationDetailsVOForDisplay);
					
					
					paVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsMap);
					paSession.setPostalAdministrationDetailsVOs(paVO.getPostalAdministrationDetailsVOs());
					
					if(errors != null && errors.size()>0) {
						invocationContext.addAllError(errors);
						paSession.setPaVO(paVO);
						invocationContext.target = FAILURE;
						return;
					}	

				}
								
				
			}
		}
		//added for ICRD-7298
		String[] invOpFlg=paMasterForm.getInvOpFlag();
		if(paMasterForm.getInvOpFlag() != null){

			PostalAdministrationDetailsVO postalAdministrationDetailsVO=null;
			postalAdministrationDetailsVOForDisplay=new ArrayList<>();
			String invoiceCode[] = paMasterForm.getParCode();
			String parameterValue[] = paMasterForm.getParameterValue();
			String companyCode = logonAttributes.getCompanyCode().toUpperCase();
			int i=0;
			Collection<String> agentCodes= new ArrayList<String>();
			for(i=0;i<invoiceCode.length-1;i++){
				if(invoiceCode[i].equals("AGTCOD")){
					agentCodes.add(parameterValue[i]);
					}}
				Collection<ErrorVO> errs = validateAgentCodes(companyCode,
						agentCodes);
				if (errs != null && errs.size() > 0) {
					errors
					.add(new ErrorVO(
							"mailtracking.defaults.pamaster.msg.err.invalidagent"));
					log.log(Log.INFO, "Erros in agent code", errs.size());
				}
			//Added by A-7794 as part of ICRD-223754
			String partyIdentifier[] = paMasterForm.getPartyIdentifier();
			String remarks[] = paMasterForm.getDetailedRemarks();
			String[]  validInvFrm=paMasterForm.getValidInvFrom();
			String[] validInvTo=paMasterForm.getValidInvTo();
			String[] invSernum=paMasterForm.getInvSerNum();
			//commented by A-4810  for icrd-19224
			    /*LocalDate fromDate=null;
			     LocalDate toDate=null;*/
				/*LocalDate prevToDate = null;
				LocalDate prevFromDate = null;*/
			
				/*Modified by A-4803 for bug ICRD-13739 starts, as after modifying a 
				 * single error message was getting duplicated. To avoid this 
				 * used flag.*/
				boolean isEntryOn = false;
				boolean isCurrentDate = false;
				boolean isLesser = false;
				boolean isGreater = false;
				boolean isOverlapping = false;
				boolean isParValEmpty = false;
				boolean isToDateEmpty = false;
				boolean isFromDateEmpty = false;
				boolean isForceImportConfigured=false;
				/*Modified by A-4803 for bug ICRD-13739 starts*/
			Map<String, Collection<OneTimeVO>> oneTimMap = paSession.getOneTimeValues();
			Collection<ErrorVO> updateErrors = new ArrayList<ErrorVO>();
			updateErrors=validateUpdation(postDetailsVOs,paMasterForm);
			
			for(i=0;i<invOpFlg.length-1;i++) {
				//added by A-4810  for icrd-19224
				LocalDate fromDate=null;
				LocalDate toDate=null;

				if(!"NOOP".equals(invOpFlg[i])){

					//create new object and set values
					postalAdministrationDetailsVO=new PostalAdministrationDetailsVO();

					postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					postalAdministrationDetailsVO.setPoaCode(paMasterForm.getPaCode().toUpperCase());					
					postalAdministrationDetailsVO.setParameterType("I");						

					String code = findOneTimeCode(invoiceCode[i],oneTimMap.get(MAIL_PARCODE));
					if(code != null && code.trim().length()>0){
						postalAdministrationDetailsVO.setParCode(code);
					}
					else{
						postalAdministrationDetailsVO.setParCode(invoiceCode[i]);
					}
					if("HANMALTYP".equals(postalAdministrationDetailsVO.getParCode())) {
						parameterValue[i]=paMasterForm.getHandoverMailTypValue()[i];
					}

					if(parameterValue [i]!=null && parameterValue [i].length()>0){
						//If parameter code is consolidated wgt& Srv.Tax , parameter value should be Y or N
						if(CWTST.equals(postalAdministrationDetailsVO.getParCode())){
							if(("Y").equals(parameterValue [i])||("N").equals(parameterValue [i])){
								postalAdministrationDetailsVO.setParameterValue(parameterValue[i]);
							}
							else{
									if(!isEntryOn){
										isEntryOn = true;
								ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.enteryorn");
								errors.add(error);
							}
						}
							}
						else if(FORCE_IMPORT.equals(postalAdministrationDetailsVO.getParCode())){
							String forceImportDes= oneTimMap.get(MAIL_PARCODE).stream().filter(elm->FORCE_IMPORT.equals(elm.getFieldValue())).findAny().orElse(null).getFieldDescription();
							try{
							Integer.parseInt(parameterValue [i]);
							postalAdministrationDetailsVO.setParameterValue(parameterValue[i]);

							}catch(NumberFormatException numberFormatException){
								Object[] ErrDes={forceImportDes};
								ErrorVO error = new ErrorVO(INVALID_FRCIMPVAL,ErrDes);
								errors.add(error);
							}
							if(isForceImportConfigured){
								Object[] ErrDes={forceImportDes};
								ErrorVO error = new ErrorVO(DUPLICATE_PARCODE,ErrDes);
								errors.add(error);
							}
							if(!"D".equals(invOpFlg[i])){
								isForceImportConfigured=true;
							}
						}
						else if (PAWBASSCONENAB.equals(postalAdministrationDetailsVO.getParCode())
								&& (("Yes").equalsIgnoreCase(parameterValue[i])
										|| ("No").equalsIgnoreCase(parameterValue[i]))) {

							postalAdministrationDetailsVO.setParameterValue(parameterValue[i]);
						}
						else if (PAWBASSCONENAB.equals(postalAdministrationDetailsVO.getParCode())
								&& (!(("Yes").equalsIgnoreCase(parameterValue[i])
										|| ("No").equalsIgnoreCase(parameterValue[i])))) {
							isEntryOn = true;
							ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.pawbparcod");
							errors.add(error);
						}
						else{
							postalAdministrationDetailsVO.setParameterValue(parameterValue[i]);
						}
					}
					else{
							if(!isParValEmpty){
								isParValEmpty = true;
						ErrorVO error = new ErrorVO(PARVAL_EMPTY);
						errors.add(error);
					}			
						}			

					postalAdministrationDetailsVO.setDetailedRemarks(remarks[i]);
					//Added by A-7794 as part of ICRD-223754
					if(MailConstantsVO.UPU_CODE.equals(postalAdministrationDetailsVO.getParCode())){
					if("I".equals(invOpFlg[i])){
						//Added by A-7794 as part of ICRD-233590
						if(("").equals(partyIdentifier[i])){
							log.log(Log.FINE, "\n\n Party Identifier Empty =============> \n\n");
							ErrorVO error = new ErrorVO(PA_PARTY_IDR_EMPTY);
							errors.add(error);
						}else {
					postalAdministrationDetailsVO.setPartyIdentifier(partyIdentifier[i]);
						}
					}
					}else{
						postalAdministrationDetailsVO.setPartyIdentifier("");
					}

					postalAdministrationDetailsVO.setOperationFlag(invOpFlg[i]);
					postalAdministrationDetailsVO.setProfInv(null);
					if("I".equals(invOpFlg[i])){
						postalAdministrationDetailsVO.setSernum(String.valueOf(++maxInvSerNum));
					}
					else{
						if(invSernum[i]!=null){
							postalAdministrationDetailsVO.setSernum(invSernum[i]);
						}
					}
					if(validInvFrm[i]!=null && validInvFrm[i].length()>0){
						fromDate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						fromDate.setDate(validInvFrm[i]);
						postalAdministrationDetailsVO.setValidFrom(fromDate);
						LocalDate sysdate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
						/*if("D".equals(invOpFlg[i])){ //Commented by A-8164 for ICRD-264549
								if((!isCurrentDate) &&( postalAdministrationDetailsVO.getValidFrom().isLesserThan(sysdate)||postalAdministrationDetailsVO.getValidFrom().equals(sysdate))){
									isCurrentDate = true;
								ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.cannotdeletecurrentdate");
								errors.add(error);
								
							}
						} */
						if("I".equals(invOpFlg[i])){
								if((!isLesser) && fromDate.isLesserThan(sysdate)){
									isLesser = true;
								ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.fromdatelessthansysdate");
								errors.add(error);
							}
						}

					}
					else{
							if(!isFromDateEmpty && !FORCE_IMPORT.equals(postalAdministrationDetailsVO.getParCode())){
								isFromDateEmpty = true;
						ErrorVO error = new ErrorVO(FROMDATE_EMPTY);
						errors.add(error);
					}
						}
					if(validInvTo[i]!=null && validInvTo[i].length()>0){

						toDate=new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						toDate.setDate(validInvTo[i]);

						postalAdministrationDetailsVO.setValidTo(toDate);
						if(fromDate!=null){
								if((!isGreater) && fromDate.isGreaterThan(toDate)){
									isGreater = true;
								ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.fromdateisgreaterthantodate");
								errors.add(error);
							}
								else{
									isCurrentDate = checkForCurrentDate(paMasterForm, errors, invOpFlg,
											postalAdministrationDetailsVO, i, isCurrentDate);
									
							}
						}
						
						

					}
					else{
							if(!isToDateEmpty &&!FORCE_IMPORT.equals(postalAdministrationDetailsVO.getParCode())){
								isToDateEmpty = true;
						ErrorVO error = new ErrorVO(TODATE_EMPTY);
						errors.add(error);
					}
						}

					
						String currInvCod = null;
					
						
							if("I".equals(invOpFlg [i])||"D".equals(invOpFlg[i])||"U".equals(invOpFlg[i])){
								currInvCod = invoiceCode[i];
								
							}
					
	//Put empty check by A-4803
						if(!"D".equals(invOpFlg[i])){
							
							for(int j=0; j<invOpFlg.length-1;j++){   
								if(j!=i){
									//added by A-4810  for icrd-19224
									LocalDate prevToDate = null;
									LocalDate prevFromDate = null;

								if(findOneTimeCode(invoiceCode[j], oneTimMap.get(MAIL_PARCODE)).equals(findOneTimeCode(currInvCod,oneTimMap.get(MAIL_PARCODE)))){
										if((validInvTo[j]).length() != 0){  
											prevToDate=new LocalDate(LocalDate.NO_STATION,
													Location.NONE, false).setDate((validInvTo[j]));	
										}
										if(validInvFrm[j].length() != 0){   
											prevFromDate=new LocalDate(LocalDate.NO_STATION,
													Location.NONE, false).setDate((validInvFrm[j]));
										}
									if(prevToDate!=null && prevFromDate!=null && fromDate!=null && toDate!=null ){
											if(!"D".equals(invOpFlg[j])){
	/* Modified the if condition by A-4803 for BUG ICRD-13711, so as to satisfy 
	 * the overlapping logic*/
												if(!("N".equals(invOpFlg[i])) && (!isOverlapping) && !((fromDate.isLesserThan(prevFromDate) && 
													fromDate.isLesserThan(prevToDate) && 
													toDate.isLesserThan(prevFromDate) && 
													toDate.isLesserThan(prevToDate)) || 
												(fromDate.isGreaterThan(prevFromDate) && 
													fromDate.isGreaterThan(prevToDate) && 
													toDate.isGreaterThan(prevFromDate) && 
													toDate.isGreaterThan(prevToDate)))){
													isOverlapping = true;
												ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.overlappingdate");
												errors.add(error);
												break;
											}
										}
									}
								}
							}
						}

					}					
					
					postalAdministrationDetailsVOs.add(postalAdministrationDetailsVO);
					postalAdministrationDetailsVOForDisplay.add(postalAdministrationDetailsVO);
					}
				}
			
			if(postalAdministrationDetailsMap==null) 
			{
				postalAdministrationDetailsMap=new HashMap<>();	
			}	
			
			postalAdministrationDetailsMap.put(INVINFO, postalAdministrationDetailsVOForDisplay);
				paVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsMap);
				paSession.setPostalAdministrationDetailsVOs(paVO.getPostalAdministrationDetailsVOs());	
			if((!updateErrors.isEmpty())){
				invocationContext.addAllError(updateErrors);
				invocationContext.target = FAILURE;
				return;
			}
			
			if((!errors.isEmpty())){
				invocationContext.addAllError(errors);
				invocationContext.target = FAILURE;
				return;
			}
			}
		
		//Added by A-5200 for the ICRD-78230 starts
		if (paMasterForm.getGibCustomerFlag()!=null && ("on".equals(paMasterForm.getGibCustomerFlag()))) {
			paVO.setGibCustomerFlag("Y");
		} else {
			paVO.setGibCustomerFlag("N");
		}
		//Added by A-5200 for the ICRD-78230 ends
		//Added by A-6991 for the ICRD-211662 starts
		if (paMasterForm.getProformaInvoiceRequired()!=null && ("on".equals(paMasterForm.getProformaInvoiceRequired()))) {
			paVO.setProformaInvoiceRequired(MailConstantsVO.FLAG_YES);
		} else {
			paVO.setProformaInvoiceRequired(MailConstantsVO.FLAG_NO);
		}
		//Added by A-6991 for the ICRD-211662 ends
		paVO.setPaDetails(postalAdministrationDetailsVOs);

		paVO.setCompanyCode(logonAttributes.getCompanyCode());
		paVO.setPaCode(paMasterForm.getPaCode().toUpperCase());
		paVO.setPaName(paMasterForm.getPaName());
		paVO.setCountryCode(paMasterForm.getCountryCode().toUpperCase());
		paVO.setAddress(paMasterForm.getAddress());
		paVO.setPartialResdit(paMasterForm.isPartialResdit());
		paVO.setMsgEventLocationNeeded(paMasterForm.isMsgEventLocationNeeded());
		paVO.setMessagingEnabled(paMasterForm.getMessagingEnabled());
		//Added for MRA
		paVO.setBaseType(paMasterForm.getBaseType());
		//paVO.setBillingFrequency(paMasterForm.getBillingFrequency());
		//paVO.setBillingSource(paMasterForm.getBillingSource());
		//paVO.setSettlementCurrencyCode(paMasterForm.getSettlementCurrencyCode());
		paVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		//paVO.setMailEvents(mailEventVOs);
		paVO.setOperationFlag(paMasterForm.getOpFlag());

		paVO.setStatus(paMasterForm.getStatus());
		paVO.setDebInvCode(paMasterForm.getDebInvCode());
		//paVO.setCusCode(paMasterForm.getCusCode());
		paVO.setConPerson(paMasterForm.getConPerson());
		paVO.setCity(paMasterForm.getCity());
		paVO.setState(paMasterForm.getState());
		paVO.setCountry(paMasterForm.getCountry());
		paVO.setMobile(paMasterForm.getMobile());
		paVO.setPostCod(paMasterForm.getPostCod());
		paVO.setPhone1(paMasterForm.getPhone1());
		paVO.setPhone2(paMasterForm.getPhone2());
		paVO.setFax(paMasterForm.getFax());
		paVO.setEmail(paMasterForm.getEmail());
		paVO.setRemarks(paMasterForm.getRemarks());
		paVO.setResidtversion(paMasterForm.getResidtversion());
		//Added by a-7540
		if("".equals(paMasterForm.getLatValLevel())){
			paVO.setLatValLevel("N");
		}
		else{
		paVO.setLatValLevel(paMasterForm.getLatValLevel());
		}
		paVO.setSettlementLevel(paMasterForm.getSettlementLevel());
		paVO.setTolerancePercent(paMasterForm.getTolerancePercent());
		paVO.setToleranceValue(paMasterForm.getToleranceValue());
		paVO.setMaxValue(paMasterForm.getMaxValue());
		paVO.setAccNum(paMasterForm.getAccNum());
		paVO.setVatNumber(paMasterForm.getVatNumber());
		paVO.setAutoEmailReqd(paMasterForm.getAutoEmailReqd());
		paVO.setResditTriggerPeriod(paMasterForm.getResditTriggerPeriod());//added by A-7371 for ICRD-212135
		paVO.setDupMailbagPeriod(paMasterForm.getDupMailbagPeriod());//added by A-8353 for ICRD-230449
		//Added as part of IASCB-853 starts
		paVO.setSecondaryEmail1(paMasterForm.getSecondaryEmail1());
		paVO.setSecondaryEmail2(paMasterForm.getSecondaryEmail2());
		//Added as part of IASCB-853 ends
		if(paMasterForm.getDueInDays()!=null && paMasterForm.getDueInDays().trim().length()>0)
			{
			paVO.setDueInDays(Integer.parseInt(paMasterForm.getDueInDays()));
			}
		else
			{
			paVO.setDueInDays(0);
			}
		paSession.setPaVO(paVO);

		if(("").equals(paMasterForm.getPaCode())) {
			log.log(Log.FINE, "\n\n PA_CODE_EMPTY =============> \n\n");
			ErrorVO error = new ErrorVO(PA_CODE_EMPTY);
			errors.add(error);
		}
		/*if(("").equals(paMasterForm.getAccNum())) {
	    	log.log(Log.FINE, "\n\n ACCOUNT NO EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(ACC_NUMBER_EMPTY);
	    	errors.add(error);
    	}*/
		if(("").equals(paMasterForm.getPaName())) {
			log.log(Log.FINE, "\n\n PA_NAME_EMPTY =============> \n\n");
			ErrorVO error = new ErrorVO(PA_NAME_EMPTY);
			errors.add(error);
		}
		if(("").equals(paMasterForm.getCountryCode())) {
			log.log(Log.FINE, "\n\n COUNTRY_EMPTY =============> \n\n");
			ErrorVO error = new ErrorVO(COUNTRY_EMPTY);
			errors.add(error);
		}
		//Added by A-5124 for ICRD-22015 Starts
		if(("").equals(paMasterForm.getCity())) {
			log.log(Log.FINE, "\n\n CITY_EMPTY =============> \n\n");
			ErrorVO error = new ErrorVO(CITY_EMPTY);
			errors.add(error);
		}
		//Added by A-5124 for ICRD-22015 end
		if(!(MailConstantsVO.FLAG_NO.equalsIgnoreCase(paMasterForm.getMessagingEnabled())) && 
				("").equals(paMasterForm.getResidtversion())) {
			log.log(Log.FINE, "\n\n Resditversion ===========> \n\n");
			ErrorVO error = new ErrorVO(RESDITVER_EMPTY);
			errors.add(error);
		}		
		//Added as part of IASCB-853 starts
		if(!( paMasterForm.getAutoEmailReqd()==null))
		{
		if (paMasterForm.getAutoEmailReqd().equals("Y") && paMasterForm.getAutoEmailReqd().length()>0)
		{
			if(paMasterForm.getEmail().equals(BLANK)|| paMasterForm.getEmail()==null)
			{
				log.log(Log.FINE, "\n\n Email empty ===========> \n\n");
			ErrorVO error = new ErrorVO(EMAIL_EMPTY);
			errors.add(error);
			}
		}
		}
		/*if(!(paMasterForm.getEmail().contains(".")||paMasterForm.getEmail().contains("@")))
		{
			log.log(Log.FINE, "\n\nnot a valid email===========> \n\n");
			ErrorVO error = new ErrorVO(NOT_EMAIL);
			errors.add(error);
		}*/
		
		if((paMasterForm.getEmail()!=null && paMasterForm.getEmail().contains(COMMA))||(paMasterForm.getSecondaryEmail1()!=null && paMasterForm.getSecondaryEmail1().contains(COMMA))||(paMasterForm.getSecondaryEmail2()!=null && paMasterForm.getSecondaryEmail2().contains(COMMA)))
		{
			log.log(Log.FINE, "\n\n Email ID column stores one address only===========> \n\n");
			ErrorVO error = new ErrorVO(SINGLE_EMAIL);
			errors.add(error);
		}
		if(postalAdministrationDetailsVOs!=null ){
			for(PostalAdministrationDetailsVO dtlvo : postalAdministrationDetailsVOs){
				if(PASBLGID.equals(dtlvo.getParCode())){
					if(MailConstantsVO.FLAG_YES.equals(paVO.getProformaInvoiceRequired())){
						log.log(Log.FINE, "\n\n Profoma cannot be used for PASS PA \n\n");
						ErrorVO error = new ErrorVO(PRF_NOTFOR_PAS);
						errors.add(error);	
					}
				}
			}
		}
		//Added as part of IASCB-853 ends
		if(errors != null && errors.size()>0) {
			paSession.setPostalAdministrationDetailsVOs(paVO.getPostalAdministrationDetailsVOs());
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}

		/*
		 * Validating the Customer Code if Porvided
		 * Commented by Gopinath M (A-3217) for the Bug Id: MTK708 
		 * @ TRV on 31-Aug-2009
		 */

		/*if(paMasterForm.getCusCode() != null && 
    			paMasterForm.getCusCode().trim().length() > 0) {
    		CustomerDelegate customerDelegate = new CustomerDelegate();
    		Collection<String> customerCodes = new ArrayList<String>();
    		customerCodes.add(paMasterForm.getCusCode());
			log.log(Log.FINE,"validateCustomers");
			Collection<ErrorVO> error = null;
			Map<String,CustomerValidationVO> customerMap = null;
			try {
				customerMap = customerDelegate.validateCustomers(logonAttributes.getCompanyCode(), customerCodes);
			}catch (BusinessDelegateException businessDelegateException) {
				error = handleDelegateException(businessDelegateException);
			}
			if ((error != null && error.size() > 0) || (customerMap == null) ) {
				invocationContext.addError(new ErrorVO(INVALID_CUSTOMER,
						new Object[]{paMasterForm.getCusCode()}));
				invocationContext.target = FAILURE;
		    	log.log(Log.FINE, "\n\n INVALID_CUSTOMER =============> \n\n");
				return;
			}
    	}*/
		//errors = checkDuplicateEvents(mailEventVOs);

		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			paSession.setPaVO(paVO);
			invocationContext.target = FAILURE;
			return;
		}

		log.log(Log.FINE, "\n\n paVO----------> ", paVO);
		try {
			new MailTrackingDefaultsDelegate().savePACode(paVO);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if(errors != null && errors.size()>0) {
			//resetCategorySubClassDetails(paVO.getMailEvents());
			invocationContext.addAllError(errors);
			paSession.setPaVO(paVO);
			invocationContext.target = FAILURE;
			return;
		}

		ErrorVO error = new ErrorVO(SAVE_SUCCESS);
		errors.add(error);
		invocationContext.addAllError(errors);
		paMasterForm.setPaCode("");
		paMasterForm.setPaName("");
		paMasterForm.setCountryCode("");
		paMasterForm.setAddress("");
		paMasterForm.setPartialResdit(false);
		paMasterForm.setMsgEventLocationNeeded(false);
		//paMasterForm.setSettlementCurrencyCode("");
		paMasterForm.setBaseType("");
		//paMasterForm.setBillingFrequency("");
		//paMasterForm.setBillingSource("");
		paMasterForm.setStatus("");
		paMasterForm.setDebInvCode("");
		//paMasterForm.setCusCode("");
		paMasterForm.setConPerson("");
		paMasterForm.setCity("");
		paMasterForm.setState("");
		paMasterForm.setCountry("");
		paMasterForm.setMobile("");
		paMasterForm.setPostCod("");
		paMasterForm.setPhone1("");
		paMasterForm.setPhone2("");
		paMasterForm.setFax("");
		paMasterForm.setEmail("");
		paMasterForm.setRemarks("");
		paMasterForm.setDueInDays("");
		paMasterForm.setAccNum("");
		paMasterForm.setResidtversion("");
		//Added by A-7540
		paMasterForm.setLatValLevel("");

		paMasterForm.setVatNumber("");
  		//Added by A-7794 as part of ICRD-229736
		paMasterForm.setParCode(null);
		paMasterForm.setProformaInvoiceRequired("");
		paSession.setPaVO(null);
		paSession.setPostalAdministrationDetailsVOs(null);
		//Added as part of IASCB-853 starts
		paMasterForm.setSecondaryEmail1("");
		paMasterForm.setSecondaryEmail2("");
		//Added as part of IASCB-853 ends
		paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);

		paMasterForm.setScreenStatusFlag
		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;
	}
	private void checkForSettlementCurrencyOverLap(Collection<ErrorVO> errors, String[] stlOpFlag, String[] validSetFrm,
			String[] validSetTo, LocalDate fromsetdate, LocalDate tosetdate, int i) {
		LocalDate prevToSetDate=new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate((validSetTo[i-1]));
		LocalDate prevFromSetDate=new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate((validSetFrm[i-1]));
			if((prevToSetDate!=null)&&(prevFromSetDate)!=null && !"D".equals(stlOpFlag[i-1]) && !((fromsetdate.isLesserThan(prevFromSetDate) && 
						fromsetdate.isLesserThan(prevToSetDate) && 
						tosetdate.isLesserThan(prevFromSetDate) && 
						tosetdate.isLesserThan(prevToSetDate)) || 
					(fromsetdate.isGreaterThan(prevFromSetDate) && 
						fromsetdate.isGreaterThan(prevToSetDate) && 
						tosetdate.isGreaterThan(prevFromSetDate) && 
						tosetdate.isGreaterThan(prevToSetDate)))){
				//Modified as part of ICRD-101506
					ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.settlementcurrencyperiodcantoverlap");
					errors.add(error);
				}
			}
	private boolean checkForCurrentDate(PostalAdministrationForm paMasterForm, Collection<ErrorVO> errors,
			String[] invOpFlg, PostalAdministrationDetailsVO postalAdministrationDetailsVO, int i,
			boolean isCurrentDate) {
		if("D".equals(invOpFlg[i] )&& (!"INACTIVE".equals(paMasterForm.getStatusActiveOrInactive()))){ //Added by A-8164 for ICRD-264549
		LocalDate sysdate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if((!isCurrentDate) &&( postalAdministrationDetailsVO.getValidFrom().isLesserThan(sysdate)||postalAdministrationDetailsVO.getValidFrom().equals(sysdate))
				&&( postalAdministrationDetailsVO.getValidTo().isGreaterThan(sysdate)||postalAdministrationDetailsVO.getValidTo().equals(sysdate))){
			isCurrentDate = true;
		ErrorVO error = new ErrorVO("mailtracking.defaults.pamaster.msg.err.cannotdeletecurrentdate");
		errors.add(error);
}
}
		return isCurrentDate;
	}
	/**
	 * method for validating modifications
	 * @param postDetailsVOs
	 * @param paMasterForm
	 * @return
	 */
	private Collection<ErrorVO> validateUpdation(Collection<PostalAdministrationDetailsVO> postDetailsVOs, PostalAdministrationForm paMasterForm) {
		Collection<ErrorVO> errors= new ArrayList<ErrorVO>();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		LocalDate validfrom = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		LocalDate validto = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		String[] invOpFlg=paMasterForm.getInvOpFlag();
		for(int i=0;i<invOpFlg.length-1;i++) {
			if (errors != null && errors.size() > 0) {
				break;
			}
			if("U".equals(paMasterForm.getInvOpFlag()[i])){
				int sequencenumber = Integer
				.parseInt(paMasterForm.getInvSerNum()[i]);
				if(paMasterForm.getValidInvFrom()[i]!=null && paMasterForm.getValidInvFrom()[i].length()>0){
					validfrom.setDate(paMasterForm.getValidInvFrom()[i]);
				}
				if(paMasterForm.getValidInvTo()[i]!=null && paMasterForm.getValidInvTo()[i].length()>0){
					validto.setDate(paMasterForm.getValidInvTo()[i]);
				}
				if(postDetailsVOs!=null && postDetailsVOs.size()>0){
					for(PostalAdministrationDetailsVO postalAdministrationDetailsVO : postDetailsVOs){
					if(Integer.parseInt(postalAdministrationDetailsVO.getSernum())==sequencenumber){
						//Cannot modify Expired set up
						if(validfrom!=null && validto!=null &&  postalAdministrationDetailsVO.getValidFrom()!=null && postalAdministrationDetailsVO.getValidTo()!=null){	
							if((postalAdministrationDetailsVO.getValidFrom().isLesserThan(currentDate)) && (postalAdministrationDetailsVO.getValidTo().isLesserThan(currentDate))){
								if(!postalAdministrationDetailsVO.getValidTo().equals(
										validto)||!postalAdministrationDetailsVO.getValidFrom().equals(
												validfrom)||
												(!(postalAdministrationDetailsVO.getParameterValue()).equals(paMasterForm.getParameterValue()[i]) || 
														(postalAdministrationDetailsVO.getPartyIdentifier()==null || !(postalAdministrationDetailsVO.getPartyIdentifier()).equals(paMasterForm.getPartyIdentifier()[i])))){
									errors.add(new ErrorVO(
											"mailtracking.defaults.pamaster.msg.err.cannotmodifyexpired"));
									break;
								}
							}
						}
						//On entering Valid To Date to a date less than Current Date, system should throw error message on Save
						// “Cannot enter Back Date”
						//cannot modify from date and value for this row
						if(validfrom!=null &&validto!=null &&  postalAdministrationDetailsVO.getValidFrom()!=null && postalAdministrationDetailsVO.getValidTo()!=null ){
							//Added condition to check if validfromdate is less than current date 
							//Added by a-4810 for icrd-19223
							if(postalAdministrationDetailsVO.getValidFrom()!=null && postalAdministrationDetailsVO.getValidFrom()!=null ){
							if((postalAdministrationDetailsVO.getValidFrom().equals(currentDate)||postalAdministrationDetailsVO.getValidFrom().isLesserThan(currentDate))&& 
									(postalAdministrationDetailsVO.getValidTo().isGreaterThan(currentDate)||postalAdministrationDetailsVO.getValidTo().equals(currentDate))){	
								if((!postalAdministrationDetailsVO.getValidFrom().equals(
										validfrom)||
										!(postalAdministrationDetailsVO.getParameterValue()).equals(paMasterForm.getParameterValue()[i])) && (!"INACTIVE".equals(paMasterForm.getStatusActiveOrInactive()))){
									errors.add(new ErrorVO(
									"mailtracking.defaults.pamaster.msg.err.cannotmodifyvalfrmdateofcurrsetup"));
									break;
								}
								if(!postalAdministrationDetailsVO.getValidTo().equals(
										validto)){
									if(validto.isLesserThan(currentDate)){
										errors.add(new ErrorVO(
										"mailtracking.defaults.pamaster.msg.err.cannotenterbackdate"));
										break;
									}
								}
							}
							}
						}
						// Cannot enter Back Date”
						if(validfrom!=null &&validto!=null &&  postalAdministrationDetailsVO.getValidFrom()!=null && postalAdministrationDetailsVO.getValidTo()!=null){
							if((postalAdministrationDetailsVO.getValidFrom().isGreaterThan(currentDate))&& 
									(postalAdministrationDetailsVO.getValidTo().isGreaterThan(currentDate)||postalAdministrationDetailsVO.getValidTo().equals(currentDate))){
								if((!postalAdministrationDetailsVO.getValidFrom().equals(
										validfrom))){
									if(validfrom.isLesserThan(currentDate)){
										errors.add(new ErrorVO(
										"mailtracking.defaults.pamaster.msg.err.fromdatelessthansysdate"));
										break;
									}					
								}
								if((!postalAdministrationDetailsVO.getValidTo().equals(
										validto))){
									if(validto.isLesserThan(currentDate)){
										errors.add(new ErrorVO(
										"mailtracking.defaults.pamaster.msg.err.todatelessthansysdate"));
										break;
									}	
								}
							}
						}
					}
					}
				}
			}
		}
		return errors;

	}

	/**
	 * 
	 * @param fieldValue
	 * @param oneTimeVOs
	 * @return desc
	 */
	private String findOneTimeCode(String fieldDescription,
			Collection<OneTimeVO> oneTimeVOs) {
		String code = fieldDescription ;		
		if(oneTimeVOs != null){
			for(OneTimeVO onetimeVo : oneTimeVOs){
				if(onetimeVo.getFieldDescription().equals(fieldDescription)){
					code = onetimeVo.getFieldValue();
				}
			}
		}

		return code;
	}
	private Collection<ErrorVO> checkDuplicate
	(PostalAdministrationForm paForm){

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		log.log(Log.FINE,"\n\n **in check duplicate***\n");

		if(paForm.getOperationFlag() != null){
			String[] opFlag = paForm.getOperationFlag();
			int num = 0;
			String flag = "Y";
			String obj = "";

			for(int i=0;i<opFlag.length;i++){
				if((OPERATION_FLAG_INSERT).equals
						(paForm.getOperationFlag()[i])){
					num=0;
					String catogory = paForm.getMailCategory()[i].toUpperCase();
					String subClas = paForm.getMailClass()[i].toUpperCase();

					String code = new StringBuffer(catogory).append("-")
					.append(subClas).toString();

					//			log.log(Log.FINE,"\n\n **code***"+code);

					for(int j=0;j<opFlag.length;j++){
						//				log.log(Log.FINE,"\n **getOperationFlag***"+
						//									paForm.getOperationFlag()[j]);
						if(!(OPERATION_FLAG_DELETE).equals
								(paForm.getOperationFlag()[j]) &&
								!(OPERATION_FLAG_DELETE).equals
								(paForm.getOperationFlag()[i])) {
							String chkCatogory = paForm.getMailCategory()[j]
							                                              .toUpperCase();
							String chkSubClas = paForm.getMailClass()[j]
							                                          .toUpperCase();

							String chkCode = new StringBuffer(chkCatogory)
							.append("-")
							.append(chkSubClas)
							.toString();

							//		log.log(Log.FINE,"\n\n **chkCode***"+chkCode);

							if(code.equals(chkCode)){
								num++;
							}

						}

					}
					if(num>1){
						log.log(Log.FINE, "**duplicate present*** num =", num);
						obj = code;
						flag = "N";
					}
				}
			}

			String destnObj=obj;
			Object[] destinationObject={destnObj};

			if(("N").equals(flag)) {
				error = new ErrorVO(DUPLICATE_CODE,destinationObject);
				errors.add(error);
			}

		}

		return errors;
	}

	
	/**
	 * 
	 * @param mailEventVOs
	 * @return
	 */
	private Collection<ErrorVO> checkDuplicateEvents
	(ArrayList<MailEventVO> mailEventVOs){

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String code ="";
		int num = 0;
		log.log(Log.FINE,"\n\n **in check checkDuplicateEvents***\n");
		boolean duplicateFlag=false;
		for(MailEventVO mailEventVO:mailEventVOs){
			if(((OPERATION_FLAG_INSERT).equals
					(mailEventVO.getOperationFlag())) || ((OPERATION_FLAG_UPDATE).equals
							(mailEventVO.getOperationFlag()))){
				num = 0;
				code = new StringBuffer(mailEventVO.getMailCategory()).append("-")
				.append(mailEventVO.getMailClass()).toString();
				for(MailEventVO mailEventVOCheck:mailEventVOs){
					if(((OPERATION_FLAG_INSERT).equals
							(mailEventVOCheck.getOperationFlag())) || ((OPERATION_FLAG_UPDATE).equals
									(mailEventVOCheck.getOperationFlag()))){
						String chkCode = new StringBuffer(mailEventVOCheck.getMailCategory())
						.append("-")
						.append(mailEventVOCheck.getMailClass())
						.toString();
						if(code.equals(chkCode)){
							num++;
						}
						if(num>1){
							duplicateFlag=true;
							break;
						}
					}
				}
				if(duplicateFlag){
					break;
				}
			}
		}
		if(duplicateFlag){
			if(code.indexOf(ALL)!=-1){
				code=code.replaceAll(ALL, "");
				code=code.replaceAll(HYPHEN, "");
			}
			Object[] destinationObject={code};
			error = new ErrorVO(DUPLICATE_CODE,destinationObject);
			errors.add(error);
			
			//for resetting category and subclass with 'ALL'
			resetCategorySubClassDetails(mailEventVOs);
		}
		return errors;
	}
	
	
	/**
	 * 
	 * @param mailEventVOs
	 * @return
	 */
	private void resetCategorySubClassDetails(
			Collection<MailEventVO> mailEventVOs){
		if(mailEventVOs!=null){
			for(MailEventVO mailEventVO:mailEventVOs){
				if(ALL.equals(mailEventVO.getMailCategory())){
					mailEventVO.setMailCategory(null);
				}
				if(ALL.equals(mailEventVO.getMailClass())){
					mailEventVO.setMailClass(null);
				}
			}
		}
	}
	
	private Collection<ErrorVO> validateAgentCodes(String companyCode,
			Collection<String> agentCodes) {
		try {
			new AgentDelegate().validateAgents(companyCode, agentCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}
}