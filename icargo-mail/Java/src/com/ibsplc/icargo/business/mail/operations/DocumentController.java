/*
 * DocumentController.java Created on Jul 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeature;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.operations.proxy.MailtrackingMRAProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AV7ReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.CN38ReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.CN41ReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ControlDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInReportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.config.vo.PrintConfigVO;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstance;
import com.ibsplc.icargo.framework.report.util.ReportUtils;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;

/**
 * @author a-1883
 *
 */
public class DocumentController {

	private static final String CONSIGNMENT_REPORT_ID = "RPTOPR046";
	
	private static final String AV7_REPORT_ID = "RPTMTK052";

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String HYPHEN = "-";

	private static final String IMPORT_TRIGGERPOINT="mailtracking.mra.triggerforimport";
	private static final String CONSIGNMENTCAPTURE ="C";
	private static final String CSGDOCNUM_GEN_KEY = "CSGDOCNUM_GEN_KEY";
	private static final String IMPORTTRIGGER_DELIVERY ="D";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";//added by A-8353 for ICRD-274933
	private static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
	private static final String MODULENAME = "mail.operations";
	private static final String DOCUMENT_CONTROLLER ="DocumentController";
	/**
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @return Integer
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws InvalidMailTagFormatException
	 * @throws DuplicateDSNException
	 * @throws DuplicateMailBagsException 
	 */
	public Integer saveConsignmentDocument(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException,
			InvalidMailTagFormatException,DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "saveConsignmentDocument");
		Integer consignmentSeqNumber = null;
		String triggerPoint = "";
		log.log(Log.FINE, " OPERATION FLAG :", consignmentDocumentVO.getOperationFlag());
		LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
		consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
		try {
			triggerPoint = mailController.findSystemParameterValue(IMPORT_TRIGGERPOINT);
		} catch (SystemException e) {
			log.log(Log.FINE, e.getMessage());
		}
		catch(Exception e){
			log.log(Log.FINE, e.getMessage());
		}
		/*
		 * Validating the Mailbag
		 */
		validateMailBags((Collection<MailInConsignmentVO>)consignmentDocumentVO.getMailInConsignmentVOs(),consignmentDocumentVO.isScanned());
		/*
		 * validating the Mailbag, to check, whether it is already assigned to anyother Consignment
		 */
		//checkMailbagsAlreadyAssigned(consignmentDocumentVO);//commented by A-8353 for	 ICRD-230449
		/*
		 * Check if DSN is present in Mailbag/Despatch Level 
		 */
		checkDSNForAcceptedMailAndDespatch(consignmentDocumentVO);	
		//Added as part of CRQ ICRD-103713 by A-5526 starts
		mailController.saveConsignmentDetails(consignmentDocumentVO);
		//Added as part of CRQ ICRD-103713 by A-5526 ends
		if (ConsignmentDocumentVO.OPERATION_FLAG_INSERT
				.equals(consignmentDocumentVO.getOperationFlag())) {
			/*
			 * Inserting new Consignment Document Details
			 */
			//new MailController().updateMailBag(consignmentDocumentVO);//saving mail bags since consignmentdetails table require mail bag Id
			if(consignmentDocumentVO.getMailInConsignmentVOs()!=null &&
					!consignmentDocumentVO.getMailInConsignmentVOs().isEmpty()){
				for(MailInConsignmentVO mailvo: consignmentDocumentVO.getMailInConsignmentVOs()){
					if(mailvo.getMailSource() == null){
						mailvo.setMailSource(MailConstantsVO.CARDIT_PROCESS);
					}
					if(MailInConsignmentVO.OPERATION_FLAG_INSERT
							.equals(mailvo.getOperationFlag())){

						double actualVolume = 0.0;
						double stationVolume=0.0;
						String commodityCode = "";
						String stationVolumeUnit="";
						CommodityValidationVO commodityValidationVO = null;
						try {
							commodityCode = new MailController().findSystemParameterValue("mailtracking.defaults.booking.commodity");
							commodityValidationVO = new MailController().validateCommodity(
									mailvo.getCompanyCode(),
									commodityCode,consignmentDocumentVO.getPaCode());
						} catch (SystemException e) {
							log.log(Log.FINE, e.getMessage()); 
						}
						catch(Exception e){
							log.log(Log.FINE, e.getMessage());
						}
						if (commodityValidationVO != null
								&& commodityValidationVO.getDensityFactor() != 0) {
							//mailbag weight is in KG,hence no need of converting to Hg
							/*actualVolume = (mailvo.getStatedWeight())
									/ (commodityValidationVO.getDensityFactor());*/
							 //added by A-8353 for ICRD-274933 starts
							    Map stationParameters = null; 
								String stationCode = logonVO.getStationCode();	
								Collection<String> parameterCodes = new ArrayList<String>();    
								parameterCodes.add(STNPAR_DEFUNIT_VOL);
						      stationParameters =new SharedAreaProxy().findStationParametersByCode(logonVO.getCompanyCode(), stationCode, parameterCodes); 
							  stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
                               if(mailvo.getStatedWeight()!=null){
                            	  double weightInKg=unitConvertion(UnitConstants.MAIL_WGT,mailvo.getStatedWeight().getSystemUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,mailvo.getStatedWeight().getSystemValue());
                            	  actualVolume=(weightInKg/(commodityValidationVO.getDensityFactor()));   
							//actualVolume = (mailvo.getStatedWeight().getRoundedSystemValue())/// (commodityValidationVO.getDensityFactor());//added by A-7371
                             stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,actualVolume);
							log.log(Log.INFO, "inside volume calculation for mailbags***:>>>", actualVolume);
							if (stationVolume < 0.01) {
								stationVolume = 0.01;
							}
							}
						}
						//mailvo.setVolume(actualVolume);
						if(stationVolumeUnit!=null){
						mailvo.setVolume(new Measure(UnitConstants.VOLUME,0.0,stationVolume,stationVolumeUnit));//added by A-7371
						}
						 //added by A-8353 for ICRD-274933 ends
					}
				}
			}
			ConsignmentDocument consignmentDocument = new ConsignmentDocument(
					consignmentDocumentVO);
			consignmentSeqNumber = Integer.valueOf(consignmentDocument
					.getConsignmentDocumentPK().getConsignmentSequenceNumber());
			consignmentDocumentVO
					.setConsignmentSequenceNumber(consignmentSeqNumber
							.intValue());
			/* updating mail Bag with consignment document information */
			
		} else if (ConsignmentDocumentVO.OPERATION_FLAG_UPDATE
				.equals(consignmentDocumentVO.getOperationFlag())) {
			ConsignmentDocument consignmentDocument = ConsignmentDocument
					.find(consignmentDocumentVO);			
			consignmentDocument.update(consignmentDocumentVO);
			modifyChildren(consignmentDocumentVO, consignmentDocument);
			consignmentSeqNumber = Integer.valueOf(consignmentDocument
					.getConsignmentDocumentPK().getConsignmentSequenceNumber());
		}
		else if(ConsignmentDocumentVO.OPERATION_FLAG_DELETE.equals(consignmentDocumentVO.getOperationFlag())){
			ConsignmentDocument consignmentDocument = ConsignmentDocument
			.find(consignmentDocumentVO);
			log.log(Log.SEVERE, new StringBuilder("This code executed from delete consignment command_2, csgdocnum ").append(consignmentDocumentVO.getConsignmentNumber())
					.append(" CSGSEQNUM ").append(consignmentDocumentVO.getConsignmentSequenceNumber()).toString());
			if(consignmentDocument != null){
				checkDespatchesAlreadyAccepted((Collection<MailInConsignmentVO>)consignmentDocumentVO.getMailInConsignmentVOs());
				//For removing routing in consignment
				if(consignmentDocument.getRoutingsInConsignments() != null && consignmentDocument.getRoutingsInConsignments().size() >0){
					RoutingInConsignment routingInConsignment = null;
					Iterator<RoutingInConsignment> routingInConsignmentIterator = consignmentDocument.getRoutingsInConsignments().iterator();
					while(routingInConsignmentIterator.hasNext()){
						routingInConsignment = routingInConsignmentIterator.next();
						routingInConsignment.remove();
						routingInConsignmentIterator.remove();
					}
				}

				//Foe removing MailInConsignment
				if(consignmentDocument.getMailsInConsignments() != null && consignmentDocument.getMailsInConsignments().size() >0){
					MailInConsignment mailInConsignment = null;
					Iterator<MailInConsignment> mailInConsignmentIterator = consignmentDocument.getMailsInConsignments().iterator();
					while(mailInConsignmentIterator.hasNext()){
						mailInConsignment = mailInConsignmentIterator.next();
						mailInConsignment.remove();
						mailInConsignmentIterator.remove();

					}

				}
				
				//For removing consignment
				consignmentDocument.remove();

			}

		}
		if(isMraImportRequired(consignmentDocumentVO,triggerPoint) ){
			importConsignmentDataToMra(consignmentDocumentVO);
		}
		log.exiting("DocumentController", "saveConsignmentDocument");
		return consignmentSeqNumber;
	}
	
	public boolean isMraImportRequired(ConsignmentDocumentVO consignmentDocumentVO, String triggerPoint) throws SystemException {
		boolean isMraImportRequired=false;
		if((triggerPoint.contains(CONSIGNMENTCAPTURE) || triggerPoint.contains(IMPORTTRIGGER_DELIVERY) )&& 
			(consignmentDocumentVO.getMailInConsignmentVOs()!=null &&	!consignmentDocumentVO.getMailInConsignmentVOs().isEmpty())){
				Mailbag mailbag=null;
				boolean isMailInMRA = false;
				MailbagPK mailbagPk = new MailbagPK();
				for(MailInConsignmentVO mailVO: consignmentDocumentVO.getMailInConsignmentVOs()){
					mailbagPk.setCompanyCode(mailVO.getCompanyCode());
					mailbagPk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
							try {
								mailbag = Mailbag.find(mailbagPk);
								} catch (FinderException finderException) {
									log.log(Log.FINE, "FinderException",finderException);
								}
							if(mailbag != null){
							   try{
	                        		isMailInMRA = new MailtrackingMRAProxy().isMailbagInMRA(mailbagPk.getCompanyCode(), mailbagPk.getMailSequenceNumber());
	                        	}catch(Exception e){
	                        		log.log(Log.FINE, "Exception",e);
	                        	}
	                        	 if(isMailInMRA || (mailbag.getScanWavedFlag()!=null && MailConstantsVO.FLAG_YES.equals(mailbag.getScanWavedFlag()))){
	                            	  isMraImportRequired=true;
	                            	  break;
								 }  				
				}			
			}			
			}			
		return isMraImportRequired;
		}
	/**
	 * 	Method		:	DocumentController.importConsignmentDataToMra
	 *	Added by 	:	A-4809 on Nov 19, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentDocumentVO 
	 *	Return type	: 	void
	 */
	public void importConsignmentDataToMra(ConsignmentDocumentVO consignmentDocumentVO) {
		try {
			new MailtrackingMRAProxy().importConsignmentDataToMra(consignmentDocumentVO);
		} catch (ProxyException e) {
			 log.log(Log.SEVERE, "ProxyException on import of consignment Data to MRA Caught");
		} catch (SystemException e) {
			log.log(Log.SEVERE, "SystemException on import of consignment Data to MRA Caught");
		}
	}
	/**
	 * @author A-3227
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws InvalidMailTagFormatException
	 * @throws DuplicateDSNException
	 * @throws DuplicateMailBagsException 
	 */
	public void saveConsignmentForManifestedDSN(ConsignmentDocumentVO consignmentDocumentVO) 
	throws SystemException, MailbagAlreadyAcceptedException, 
	InvalidMailTagFormatException, DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "saveConsignmentForManifestedDSN");
		ArrayList<MailInConsignmentVO> mailInCondignmentVOs = null;
		Page<MailInConsignmentVO> mailbagVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		if (mailbagVOs != null && mailbagVOs.size() > 0) {
			mailInCondignmentVOs = new ArrayList<MailInConsignmentVO>();
			for (MailInConsignmentVO mailInConsignment : mailbagVOs) {
				MailInConsignmentVO assignedVO = null;

				/*
				 * Finding Whether the mail is assigned to any other consignemt 
				 */
				if (mailInConsignment.getMailId() != null) {
					assignedVO = new DocumentController().findConsignmentDetailsForMailbag(
							mailInConsignment.getCompanyCode(),
							mailInConsignment.getMailId(), null);
				}
				/*
				 * Mailbags which are not assigned to any other Consignments
				 * wont be taken to the system again.
				 */
				if(assignedVO == null) {
					mailInCondignmentVOs.add(mailInConsignment);
				}
			}
			if(mailInCondignmentVOs != null && mailInCondignmentVOs.size() > 0) {
				Page<MailInConsignmentVO>mailInConsignmentVOs = 
					new Page<MailInConsignmentVO>((ArrayList<MailInConsignmentVO>)
							mailInCondignmentVOs,0,0,0,0,0,false);
				consignmentDocumentVO.setMailInConsignmentVOs(mailInConsignmentVOs);
			}
		}
		/*
		 * Saving Consignment Document
		 */
		saveConsignmentDocument(consignmentDocumentVO);
		log.exiting("DocumentController", "saveConsignmentForManifestedDSN");
	}
	
	/**
	 * @author A-3227
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws DuplicateMailBagsException 
	 */
	public void saveConsignmentDocumentFromCardit(
			ConsignmentDocumentVO consignmentDocumentVO)
	throws SystemException, DuplicateMailBagsException{
		log.entering("DocumentController", "saveConsignmentDocumentFromCardit");
		Integer consignmentSeqNumber = 1;
		if(consignmentDocumentVO != null) {
			LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
			consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
			consignmentDocumentVO.setConsignmentSequenceNumber(consignmentSeqNumber.intValue());
			ConsignmentDocument consignmentDocument = null;
			HashMap<String,Long> flightSeqNumMap=new HashMap<>();
			try {
				consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);	
				if(consignmentDocument != null) {
					consignmentDocument.update(consignmentDocumentVO);
					maintainCarditReceptacles(consignmentDocumentVO, consignmentDocument);
				}
			} catch (SystemException ex) {
				consignmentDocument = new ConsignmentDocument(consignmentDocumentVO);
			}
			Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
			if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {	
				for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
					log.log(Log.SEVERE, "-----UPDATING MAIL BAG DETAIL----");
					mailInConsignmentVO.setConsignmentSequenceNumber(
							consignmentDocument.getConsignmentDocumentPK().getConsignmentSequenceNumber());
					/* updating mail Bag detail */
					new MailController().updateMailBagConsignmentDetails(mailInConsignmentVO,consignmentDocumentVO.getRoutingInConsignmentVOs(),flightSeqNumMap);
				}
			}
		}
		log.exiting("DocumentController", "saveConsignmentDocumentFromCardit");
	}
	
	/**
	 * @author A-3227
	 * @param consignmentDocumentVO
	 * @param consignmentDocument
	 * @throws SystemException
	 */
	public void maintainCarditReceptacles(ConsignmentDocumentVO consignmentDocumentVO,
			ConsignmentDocument consignmentDocument) throws SystemException {
		log.entering("DocumentController", "maintainCarditReceptacles");
		Page<MailInConsignmentVO> mailInConsignmentVOs = 
			consignmentDocumentVO.getMailInConsignmentVOs();
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {							
			MailInConsignment mailInConsignment = null;		
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				log.log(Log.SEVERE, "-----INSERTING NEW MAIL----");
				mailInConsignmentVO.setConsignmentSequenceNumber(
						consignmentDocument.getConsignmentDocumentPK().getConsignmentSequenceNumber());
				mailInConsignment = new MailInConsignment(mailInConsignmentVO);
				consignmentDocument.getMailsInConsignments().add(mailInConsignment);
				consignmentDocument.setStatedBags(consignmentDocument.getStatedBags()
						+ mailInConsignment.getStatedBags());
				consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight());
			}
		}
		log.exiting("DocumentController", "maintainCarditReceptacles");
	}
	
	/**
	 * To Check if DSN is present in Accepted Mailbag/Despatch Level 
	 * @param consignmentDocumentVO
	 * @throws DuplicateDSNException
	 * @throws SystemException 
	 */
	private void checkDSNForAcceptedMailAndDespatch(ConsignmentDocumentVO consignmentDocumentVO) 
	throws DuplicateDSNException, SystemException {

//		--------------Added for DSN PK Check --->> no mailbags and despatches must have same DSNPK
		Page<MailInConsignmentVO> newMailsTosaveVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
		Collection<MailInConsignmentVO> newMailsToInsert = new ArrayList<MailInConsignmentVO>();
		if(newMailsTosaveVOs != null && newMailsTosaveVOs.size() > 0) {
			for (MailInConsignmentVO mailInConsignmentVO : newMailsTosaveVOs) {
				log.log(Log.FINE, " Operation FLAG : ", mailInConsignmentVO.getOperationFlag());
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
					newMailsToInsert.add(mailInConsignmentVO);
				}	
			}
		}
		DSNVO dsnVO = null;
		String inpk="";
		int err=0;
		if(newMailsToInsert != null && newMailsToInsert.size() > 0) {
			for(MailInConsignmentVO newMailTosave : newMailsToInsert){
				dsnVO = new DSNVO();			 
				dsnVO.setCompanyCode(newMailTosave.getCompanyCode());
				dsnVO.setOriginExchangeOffice(newMailTosave.getOriginExchangeOffice());
				dsnVO.setDestinationExchangeOffice(newMailTosave.getDestinationExchangeOffice());
				dsnVO.setMailCategoryCode(newMailTosave.getMailCategoryCode());
				dsnVO.setMailSubclass(newMailTosave.getMailSubclass());
				dsnVO.setYear(newMailTosave.getYear());
				dsnVO.setDsn(newMailTosave.getDsn());
				if(newMailTosave.getReceptacleSerialNumber()==null) {
					dsnVO.setPltEnableFlag("N");
				} else{
					
					if(("").equals(newMailTosave.getReceptacleSerialNumber().trim()))
					{
						dsnVO.setPltEnableFlag("N");
					}else{
						dsnVO.setPltEnableFlag("Y");
					}   
				}

				String pltFlag = findMailType(dsnVO);
				log.log(Log.FINE, "!!!!!!!!!!!!!!!!pltFlag", pltFlag);
				if(pltFlag !=null && pltFlag.trim().length()>0){
					if(! "M".equals(pltFlag)){
						if("".equals(inpk)){
							inpk = dsnVO.getOriginExchangeOffice()+HYPHEN
							+dsnVO.getDestinationExchangeOffice()+HYPHEN
							+dsnVO.getMailCategoryCode()+HYPHEN
							+dsnVO.getMailSubclass()+HYPHEN
							+dsnVO.getDsn()+HYPHEN
							+dsnVO.getYear();
						}
						else{
							String ipk = dsnVO.getOriginExchangeOffice()+HYPHEN
							+dsnVO.getDestinationExchangeOffice()+HYPHEN
							+dsnVO.getMailCategoryCode()+HYPHEN
							+dsnVO.getMailSubclass()+HYPHEN
							+dsnVO.getDsn()+HYPHEN
							+dsnVO.getYear();
							inpk = new StringBuilder().append(inpk).append(" , ").append(ipk).toString();
						}
						err=1;
					}
				}

				if(err==1){
					log.log(Log.FINE, "!!!!!!!!!!!!!!!!inpk", inpk);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_MAILBAG_DESPATCH,new Object[]{inpk});
				}
			}
		}
	}	
	
	/**
	 * 
	 * @param consignmentDocument
	 * @throws DuplicateDSNException 
	 */
	private void checkDSNExistanceInConsignment(Collection<MailInConsignmentVO> mailInConsignmentVOs,
			ConsignmentDocument consignmentDocument) throws DuplicateDSNException {
		Set<MailInConsignment> existingMailInConsignments = consignmentDocument.getMailsInConsignments();
		for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
			log.log(Log.FINE, " Operation FLAG : ", mailInConsignmentVO.getOperationFlag());
			if (MailInConsignmentVO.OPERATION_FLAG_INSERT.equals(mailInConsignmentVO.getOperationFlag())) {
				/*
				 * Checking existance of DSN accross the consignment.
				 */
				int error = 0;
				String newMailKey = new StringBuilder()
												.append(mailInConsignmentVO.getOriginExchangeOffice())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInConsignmentVO.getDestinationExchangeOffice())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInConsignmentVO.getMailCategoryCode())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInConsignmentVO.getMailSubclass())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInConsignmentVO.getDsn())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInConsignmentVO.getYear())
												.toString();
				for(MailInConsignment mailInCsg : existingMailInConsignments) {			
					String existingMailKey = new StringBuilder()
												.append(mailInCsg.getMailInConsignmentPK().getCompanyCode())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInCsg.getMailInConsignmentPK().getConsignmentNumber())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInCsg.getMailInConsignmentPK().getConsignmentSequenceNumber())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInCsg.getMailInConsignmentPK().getMailSequenceNumber())
												.append(MailConstantsVO.SEPARATOR)
												.append(mailInCsg.getMailInConsignmentPK().getPaCode())
					
												.toString();				
					if(newMailKey.equalsIgnoreCase(existingMailKey)) {
						if(mailInConsignmentVO.getReceptacleSerialNumber() == null || 
								(mailInConsignmentVO.getReceptacleSerialNumber() != null &&
										mailInConsignmentVO.getReceptacleSerialNumber().trim().length() == 0)) {
						/*	if(mailInCsg.getReceptacleSerialNumber() == null || 
									(mailInCsg.getReceptacleSerialNumber() != null &&
											mailInCsg.getReceptacleSerialNumber().trim().length() == 0)) {
								/*
								 * New Mail : Despatch
								 * Existing Mail : Despatch 
								 */
								//error = 1;
							/*}else {
								/*
								 * New Mail : Despatch
								 * Existing Mail : Mailbag
								 */
								error = 2;
							//}
						/*}else {
							if(mailInCsg.getReceptacleSerialNumber() == null || 
									(mailInCsg.getReceptacleSerialNumber() != null &&
											mailInCsg.getReceptacleSerialNumber().trim().length() == 0)) {
								/*
								 * New Mail : Mailbag
								 * Existing Mail : Despatch 
								 */
								error = 2;
						//	}
						}
						break;
					}
				}
				if(error == 1) {
					log.log(Log.SEVERE, "-----DuplicateDSNException----Similar Despatch already Exist in consignment---");
					log.log(Log.SEVERE, "-----mailInConsignmentVO---",
							mailInConsignmentVO);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_DESPATCH);
				}else if(error == 2) {
					log.log(Log.FINE, "----DSN EXIST FOR ANOTHER MAILBAG----",
							newMailKey);
					throw new DuplicateDSNException(DuplicateDSNException.DSN_IN_MAILBAG_DESPATCH,new Object[]{newMailKey});
				}
			}
		}
	}
	
	/**
	 * TODO Purpose May 26, 2008 , A-3251
	 *
	 * @param dsnVO
	 */
	public String findMailType(DSNVO dsnVO)
	throws SystemException{
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setCompanyCode(dsnVO.getCompanyCode());
		String mailId=MailtrackingDefaultsVOConverter.createMailBag(dsnVO);
		mailbagVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailId, dsnVO.getCompanyCode()));
		mailbagVO.setMailbagId(mailId);
				
	return new Mailbag().findMailType(mailbagVO);
	}
	

	
	/**
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 */
	private void checkMailbagsAlreadyAssigned(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException {
		log.entering("DocumentController", "checkMailbagsAlreadyAssigned");
		Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO
				.getMailInConsignmentVOs();
		Collection<ErrorVO> errorVOs = null;
		Collection<MailInConsignmentVO> mailConsignmentVOsToRemove = new ArrayList<MailInConsignmentVO>();
		String airportCode = consignmentDocumentVO.getAirportCode();
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(mailInConsignmentVO.getOperationFlag())) {
					if (mailInConsignmentVO.getMailId() != null) {
						MailInConsignmentVO assignedVO = findConsignmentDetailsForMailbag(
								mailInConsignmentVO.getCompanyCode(),
								mailInConsignmentVO.getMailId(), airportCode);
						if (assignedVO != null && (!assignedVO.getConsignmentNumber().equalsIgnoreCase(consignmentDocumentVO.getConsignmentNumber())||
								!assignedVO.getPaCode().equalsIgnoreCase(consignmentDocumentVO.getPaCode()))) {
							Object[] errorData = new Object[2];
							errorData[0] = assignedVO.getConsignmentNumber();
							errorData[1] = mailInConsignmentVO.getMailId();
							ErrorVO errorVO = new ErrorVO(
									MailbagAlreadyAcceptedException.MAILBAG_ALREADY_ASSIGNED,
									errorData);
							if (errorVOs == null) {
								errorVOs = new ArrayList<ErrorVO>();
							}
							errorVOs.add(errorVO);
						}
						//Modified for ICRD-110913 to remove the mailinconsignmentvo if it is already assigned to the sameconsignment to avoid duplicating
						else if (assignedVO != null && (assignedVO.getConsignmentNumber().equalsIgnoreCase(consignmentDocumentVO.getConsignmentNumber())||
								assignedVO.getPaCode().equalsIgnoreCase(consignmentDocumentVO.getPaCode()))) {
							mailConsignmentVOsToRemove.add(mailInConsignmentVO);
							
						}
					}
				}
			}
			if(mailConsignmentVOsToRemove != null && mailConsignmentVOsToRemove.size()>0){
				mailInConsignmentVOs.removeAll(mailConsignmentVOsToRemove);
			}
		}
		
	
		if (errorVOs != null && errorVOs.size() > 0) {
			log.log(Log.FINE, " Mailbags Already Attached to Consignment ");
			MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException =
                new MailbagAlreadyAcceptedException();
			mailbagAlreadyAcceptedException.addErrors(errorVOs);
			throw mailbagAlreadyAcceptedException;
		}
		log.log(Log.FINE, " Mailbags not Attached to Any Consignment ");
		log.exiting("DocumentController", "checkMailbagsAlreadyAssigned");
	}

	/**
	 * @author a-1883
	 * @param mailInConsignmentVOs
	 * @throws SystemException
	 * @throws InvalidMailTagFormatException
	 */
	private void validateMailBags(
			Collection<MailInConsignmentVO> mailInConsignmentVOs,boolean isScanned)
			throws SystemException, InvalidMailTagFormatException {
		log.entering("DocumentController", "validateMailBags");
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
			Collection<DSNVO> dSNVOs = new ArrayList<DSNVO>();
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(mailInConsignmentVO.getOperationFlag())) {
					if (mailInConsignmentVO.getReceptacleSerialNumber() != null) {
						MailbagVO mailbagVO = new MailbagVO();
						mailbagVO
								.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
						mailbagVO.setCompanyCode(mailInConsignmentVO
								.getCompanyCode());
						mailbagVO.setDoe(mailInConsignmentVO
								.getDestinationExchangeOffice());
						mailbagVO.setOoe(mailInConsignmentVO
								.getOriginExchangeOffice());
						mailbagVO.setMailSubclass(mailInConsignmentVO
								.getMailSubclass());
                        mailbagVO.setMailCategoryCode(mailInConsignmentVO
								.getMailCategoryCode());
						mailbagVO.setMailbagId(mailInConsignmentVO.getMailId());
						mailbagVO.setDespatchSerialNumber(mailInConsignmentVO
								.getDsn());
						mailbagVO.setMailClass(mailInConsignmentVO
								.getMailClass());
						mailbagVO.setYear(mailInConsignmentVO.getYear());
						mailbagVO.setUldNumber(mailInConsignmentVO
								.getUldNumber());
						mailbagVO.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
						mailbagVO.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
						
						//added for ICRD-255189
						mailbagVO = new MailController().constructOriginDestinationDetails(mailbagVO);
					
						
						mailbagVOs.add(mailbagVO);
					} else {
						DSNVO dSNVO = new DSNVO();
						dSNVO.setOperationFlag(DSNVO.OPERATION_FLAG_INSERT);
						dSNVO.setCompanyCode(mailInConsignmentVO
								.getCompanyCode());
						dSNVO.setDestinationExchangeOffice(mailInConsignmentVO
								.getDestinationExchangeOffice());
						dSNVO.setOriginExchangeOffice(mailInConsignmentVO
								.getOriginExchangeOffice());
						dSNVO.setDsn(mailInConsignmentVO.getDsn());
						dSNVO.setMailClass(mailInConsignmentVO.getMailClass());
                        dSNVO.setMailSubclass(
                            mailInConsignmentVO.getMailSubclass());
                        dSNVO.setMailCategoryCode(
                            mailInConsignmentVO.getMailCategoryCode());
						dSNVO.setYear(mailInConsignmentVO.getYear());
						dSNVOs.add(dSNVO);
					}
				}
			}
			if (mailbagVOs.size() > 0) {
				log.log(Log.FINE, " Validating Mailbags ");
				if(isScanned){
					
				    new MailController().validateScannedMailDetails(mailbagVOs);
				}else{
					new MailController().validateMailBags(mailbagVOs);
				}
				
			}
			if (dSNVOs.size() > 0) {
				log.log(Log.FINE, " Validating DSNs ");
			//	new MailController().validateDSNs(dSNVOs);
			}
		}
		log.entering("DocumentController", "validateMailBags");
	}

	/**
	 * @author a-1883 This method performs Insert / Update / Delete operations
	 * @param consignmentDocumentVO
	 * @param consignmentDocument
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws DuplicateDSNException 
	 * @throws DuplicateMailBagsException 
	 */
	private void modifyChildren(ConsignmentDocumentVO consignmentDocumentVO,
			ConsignmentDocument consignmentDocument) throws SystemException,
			MailbagAlreadyAcceptedException, DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "modifyChildren");
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = consignmentDocumentVO
				.getRoutingInConsignmentVOs();
		Page<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO
				.getMailInConsignmentVOs();
		if (routingInConsignmentVOs != null
				&& routingInConsignmentVOs.size() > 0) {
			modifyRoutingInConsignment(routingInConsignmentVOs,
					consignmentDocument);
		}
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			/*
			 * To Check if DSN is already present in Despatch/Mailbag level of this consignment
			 */
			checkDSNExistanceInConsignment((Collection<MailInConsignmentVO>)mailInConsignmentVOs,consignmentDocument);
			
			modifyMailInConsignment((Collection<MailInConsignmentVO>)mailInConsignmentVOs, consignmentDocument);
		}

		log.exiting("DocumentController", "modifyChildren");
	}

	/**
	 * @author A-1883
	 * @param routingInConsignmentVOs
	 * @param consignmentDocument
	 * @throws SystemException
	 */
	private void modifyRoutingInConsignment(
			Collection<RoutingInConsignmentVO> routingInConsignmentVOs,
			ConsignmentDocument consignmentDocument) throws SystemException {
		log.entering("DocumentController", "modifyRoutingInConsignment");
		RoutingInConsignment routingInConsignment = null;
		for (RoutingInConsignmentVO routingInConsignmentVO : routingInConsignmentVOs) {
			log.log(Log.FINE, " Operation FLAG : ", routingInConsignmentVO.getOperationFlag());
			if (RoutingInConsignmentVO.OPERATION_FLAG_INSERT
					.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignmentVO
						.setConsignmentSequenceNumber(consignmentDocument
								.getConsignmentDocumentPK().getConsignmentSequenceNumber());
				routingInConsignment = new RoutingInConsignment(
						routingInConsignmentVO);
				consignmentDocument.getRoutingsInConsignments().add(
						routingInConsignment);
			} else if (RoutingInConsignmentVO.OPERATION_FLAG_DELETE
					.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignment = RoutingInConsignment
						.find(routingInConsignmentVO);
				routingInConsignment.remove();
				consignmentDocument.getRoutingsInConsignments().remove(
						routingInConsignment);
			} else if (RoutingInConsignmentVO.OPERATION_FLAG_UPDATE
					.equals(routingInConsignmentVO.getOperationFlag())) {
				routingInConsignment = RoutingInConsignment
						.find(routingInConsignmentVO);
				routingInConsignment.update(routingInConsignmentVO);
				
			} else {
				/* No change to the Child */
			}
		}
		log.exiting("DocumentController", "modifyRoutingInConsignment");
	}

	/**
	 * @author A-1883
	 * @param mailInConsignmentVOs
	 * @param consignmentDocument
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws DuplicateDSNException 
	 * @throws DuplicateMailBagsException 
	 */
	private void modifyMailInConsignment(
			Collection<MailInConsignmentVO> mailInConsignmentVOs,
			ConsignmentDocument consignmentDocument) throws SystemException,
			MailbagAlreadyAcceptedException, DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "modifyMailInConsignment");
			boolean isFirstTime = true;
			int  carrierId=0;
			MailInConsignment mailInConsignment = null;
		    Collection<RoutingInConsignmentVO> routingInConsignmentVOs = new ArrayList<>();
		    RoutingInConsignmentVO routingInConsignmentVO = null;
		    HashMap<String,Long> flightSeqNumMap=new HashMap<>();
			Collection<RoutingInConsignment>routingInConsignments=consignmentDocument.getRoutingsInConsignments();
			if(routingInConsignments!=null && routingInConsignments.size()>0){
				for(RoutingInConsignment routingInConsignment:routingInConsignments){
					carrierId=routingInConsignment.getOnwardCarrierId();
				       	routingInConsignmentVO = new RoutingInConsignmentVO();
				        routingInConsignmentVO.setOnwardFlightNumber(routingInConsignment.getOnwardFlightNumber());
				        routingInConsignmentVO.setOnwardCarrierCode(routingInConsignment.getOnwardCarrierCode());
				        if (routingInConsignment.getOnwardFlightDate() != null){
				          routingInConsignmentVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, routingInConsignment.getOnwardFlightDate(), false)); 
				        }
				        routingInConsignmentVO.setOnwardCarrierSeqNum(routingInConsignment.getOnwardCarrierSeqNum());
				        routingInConsignmentVO.setPou(routingInConsignment.getPou());
				        routingInConsignmentVO.setPol(routingInConsignment.getPol());  //Added as part of IASCB-32332
				        routingInConsignmentVOs.add(routingInConsignmentVO);
				}
			}
			for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
				log.log(Log.FINE, " Operation FLAG : ", mailInConsignmentVO.getOperationFlag());
				if (MailInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(mailInConsignmentVO.getOperationFlag())) {
					modifyExistingMailInConsignment(mailInConsignmentVO);
					mailInConsignmentVO.setConsignmentSequenceNumber(consignmentDocument
							.getConsignmentDocumentPK().getConsignmentSequenceNumber());
					log.log(Log.SEVERE, "-----INSERTING NEW MAIL----");
					mailInConsignmentVO.setConsignmentNumber(consignmentDocument.getConsignmentDocumentPK().getConsignmentNumber());
					mailInConsignmentVO.setConsignmentSequenceNumber(consignmentDocument.getConsignmentDocumentPK().getConsignmentSequenceNumber());
					mailInConsignmentVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, consignmentDocument.getConsignmentDate(), true));
					mailInConsignmentVO.setAirportCode(consignmentDocument.getAirportCode());
					mailInConsignmentVO.setCarrierId(carrierId);
					new MailController().updateMailBagConsignmentDetails(mailInConsignmentVO, routingInConsignmentVOs,flightSeqNumMap);
					mailInConsignment = new MailInConsignment(mailInConsignmentVO);
					//Added as part of bug IASCB-65365 by A-5526 starts
					if(mailInConsignmentVO.getMailSource()==null){
					mailInConsignmentVO.setMailSource(MailConstantsVO.CARDIT_PROCESS);   
					}
					//Added as part of bug IASCB-65365 by A-5526 ends
					consignmentDocument.getMailsInConsignments().add(mailInConsignment);			
					/*
					 * Increasing stated bags count as well as weight of
					 * consignment document
					 */
					consignmentDocument.setStatedBags(consignmentDocument.getStatedBags()
							+ mailInConsignment.getStatedBags());
					consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight());

						

				} else if (MailInConsignmentVO.OPERATION_FLAG_DELETE
						.equals(mailInConsignmentVO.getOperationFlag())) {
					if (isFirstTime) {
						checkDespatchesAlreadyAccepted(mailInConsignmentVOs);
						/*
						 * Checks whether Mailbags already Accepted if yes then
						 * clears consignment details
						 */
						checkMailbagAccepted(mailInConsignmentVOs);
						isFirstTime = false;
						log.log(Log.FINE, " Mailbags has not accepted Yet ");
					}
					try {
						mailInConsignmentVO.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(mailInConsignmentVO.getMailId(), mailInConsignmentVO.getCompanyCode()));
						mailInConsignment = MailInConsignment
						.find(mailInConsignmentVO);
					} catch (FinderException finderException) {
						log.log(Log.SEVERE, "  Finder Exception ");
						throw new SystemException(finderException.getErrorCode(),
								finderException);
					}
					/*
					 * Decreasing stated bags count as well as weight from
					 * consignment document on deletion of mailbag
					 */
					consignmentDocument.setStatedBags(consignmentDocument
							.getStatedBags()
							- mailInConsignment.getStatedBags());
					consignmentDocument.setStatedWeight(consignmentDocument
							.getStatedWeight());
					mailInConsignment.remove();
					consignmentDocument.getMailsInConsignments().remove(
							mailInConsignment);
				} else if (MailInConsignmentVO.OPERATION_FLAG_UPDATE
						.equals(mailInConsignmentVO.getOperationFlag())) {
			
					modifyExistingMailInConsignment(mailInConsignmentVO);
					try {
						mailInConsignment = MailInConsignment
						.find(mailInConsignmentVO);
					} catch (FinderException finderException) {
						log.log(Log.SEVERE, "  Finder Exception ");
						new MailInConsignment(mailInConsignmentVO);
					}
					if(mailInConsignment!=null){
					/*
					 * Updating stated bags count as well as weight of
					 * consignment document
					 */
					consignmentDocument
							.setStatedBags(consignmentDocument.getStatedBags()
									+ (mailInConsignmentVO.getStatedBags() - mailInConsignment
											.getStatedBags()));
					/*consignmentDocument
							.setStatedWeight(consignmentDocument
									.getStatedWeight()
									+ (mailInConsignmentVO.getStatedWeight()));*/
					consignmentDocument
							.setStatedWeight(consignmentDocument
									.getStatedWeight()
							+ (mailInConsignmentVO.getStatedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */));//added by A-7371
					//Added as part of bug IASCB-65365 by A-5526 starts
					if(mailInConsignmentVO.getMailSource()==null){
						mailInConsignmentVO.setMailSource(MailConstantsVO.CARDIT_PROCESS);   
						} 
					//Added as part of bug IASCB-65365 by A-5526 ends
					mailInConsignment.update(mailInConsignmentVO);
					}
					//Added as part of IASCB-32332
					MailbagVO mailbagToUpdate = new MailbagVO();
					mailbagToUpdate.setCompanyCode(mailInConsignmentVO.getCompanyCode());
					mailbagToUpdate.setMailbagId(mailInConsignmentVO.getMailId());
					mailbagToUpdate.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
					mailbagToUpdate.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
					if(mailInConsignmentVO.getMailServiceLevel()!=null && !mailInConsignmentVO.getMailServiceLevel().isEmpty()){
					mailbagToUpdate.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
					}else{
						mailbagToUpdate.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
						mailbagToUpdate.setMailClass(mailInConsignmentVO.getMailClass());
						mailbagToUpdate.setMailSubclass(mailInConsignmentVO.getMailSubclass());
						mailbagToUpdate.setPaCode(mailInConsignmentVO.getPaCode());
						mailbagToUpdate.setMailServiceLevel(new MailController().findMailServiceLevel(mailbagToUpdate));
						
					}
					mailbagToUpdate.setOrigin(mailInConsignmentVO.getMailOrigin());
					mailbagToUpdate.setDestination(mailInConsignmentVO.getMailDestination());
					mailbagToUpdate.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
					mailbagToUpdate.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
					mailbagToUpdate.setPaBuiltFlag(mailInConsignmentVO.getPaBuiltFlag());
					mailbagToUpdate.setWeight(mailInConsignmentVO.getStatedWeight());
					mailbagToUpdate.setMailSource(mailInConsignmentVO.getMailSource());
					mailbagToUpdate.setSecurityStatusCode(consignmentDocument.getSecurityStatusCode());
				    // new Mailbag().updateRDT(mailbagToUpdate);
					//added for IASCB-50003
				    new Mailbag().updateMailbagForConsignment(mailbagToUpdate);
				} else {
					/* No change to Child */
				}
			}
		log.exiting("DocumentController", "modifyMailInConsignment");
	}

	/**
	 * @author a-1883
	 * @param mailInConsignmentVOs
	 * @throws SystemException
	 */
	private void checkMailbagAccepted(
			Collection<MailInConsignmentVO> mailInConsignmentVOs)
			throws SystemException {
		log.entering("DocumentController", "checkMailbagAccepted");
		Collection<Mailbag> mailbags = new MailController()
				.checkMailbagAccepted(mailInConsignmentVOs);
		log.log(Log.FINE, "Mailbags :", mailbags);
		if (mailbags != null && mailbags.size() > 0) {
			log
					.log(Log.FINE,
							" Going to set consignment details of all already accepted mail bags as Null ");
			for (Mailbag mailbag : mailbags) {
				
				mailbag.setConsignmentNumber(null);
				mailbag.setConsignmentSequenceNumber(0);
				//mailbag.setPaCode(null); commented as part of IASCB-65094
			}
		}
		log.exiting("DocumentController", "checkMailbagAccepted");
	}

	/**
	 * @author a-1883 Find Consignment . If it does not exist , create from
	 *         here. Else update MailInConsignment and toal stated bags and
	 *         weight
	 * @param consignmentDocumentVO
	 * @return int
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws DuplicateMailBagsException 
	 */
	public int saveConsignmentForAcceptance(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException, DuplicateMailBagsException {
		log.entering("DocumentController", "saveConsignmentForAcceptance");
		int consignmentSeqNumber = -1;
		ConsignmentDocumentVO documentVO = new ConsignmentDocument()
				.checkConsignmentDocumentExists(consignmentDocumentVO);
		log.log(Log.FINE, " ConsignmentDocumentVO : ", documentVO);
		if (documentVO != null) {
			log.log(Log.FINE, " Consignment Document is Existing ");
			ConsignmentDocument consignmentDocument = ConsignmentDocument
					.find(documentVO);
			consignmentSeqNumber = documentVO.getConsignmentSequenceNumber();
			Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO
					.getMailInConsignmentVOs();
			if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
				for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
						log.log(Log.FINE,
								" Consignment seq no. Fom acceptance side  : ",
								mailInConsignmentVO.getConsignmentSequenceNumber());
						mailInConsignmentVO
								.setConsignmentSequenceNumber(consignmentDocument
										.getConsignmentDocumentPK().getConsignmentSequenceNumber());
						log
								.log(
										Log.FINE,
										" Consignment seq no. from Consignment entity : ",
										mailInConsignmentVO.getConsignmentSequenceNumber());
						mailInConsignmentVO.setMailSequenceNumber(ConsignmentDocument
										.findMailSequenceNumber(mailInConsignmentVO));
						log.log(Log.FINE, "  MailSequenceNumber got is ",
								mailInConsignmentVO.getMailSequenceNumber());
						findCreateMailInConsignment(mailInConsignmentVO,
								consignmentDocument,consignmentSeqNumber,consignmentDocumentVO);

				}
			}
		} else {
			log.log(Log.FINE, " Consignment Document is not Existing ");
			consignmentDocumentVO
					.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			try {
				consignmentSeqNumber = saveConsignmentDocument(
						consignmentDocumentVO).intValue();
			} catch (InvalidMailTagFormatException invalidMailTagFormatException) {
				/* Already validated while acceptance so Ignore this Exception */
				log.log(Log.SEVERE, " InvalidMailTagFormatException ");
			}
			catch (DuplicateDSNException duplicateDSNException) {
				log.log(Log.SEVERE, " DuplicateDSNException ");
			}

		}
		log.exiting("DocumentController", "saveConsignmentForAcceptance");
		return consignmentSeqNumber;
	}
/**
 * @author a-1883
 * @param mailInConsignmentVO
 * @param consignmentDocument
 * @param consignmentSeqNumber
 * @param consignmentDocumentVO
 * @throws SystemException
 */
	private void findCreateMailInConsignment(MailInConsignmentVO
			mailInConsignmentVO,ConsignmentDocument consignmentDocument,
			int consignmentSeqNumber,ConsignmentDocumentVO consignmentDocumentVO)
		throws SystemException {
		log.entering("DocumentController","findMailInConsignment");
		MailInConsignment mailInConsignment = null;
		try{
			mailInConsignment = MailInConsignment.find(mailInConsignmentVO);
			log.log(Log.FINE, "  Mail In Consignment is  Existing ",
					mailInConsignmentVO);
			} catch (FinderException finderException) {
				log.log(Log.FINE, "  Mail In Consignment is Not Existing ",
						mailInConsignmentVO);
				mailInConsignmentVO
						.setConsignmentSequenceNumber(consignmentSeqNumber);
				 mailInConsignment = new MailInConsignment(
						mailInConsignmentVO);
				consignmentDocument.getMailsInConsignments().add(
						mailInConsignment);
			}
	}
	/**
	 * @author A-2037
	 * @param companyCode
	 * @param mailId
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public MailInConsignmentVO findConsignmentDetailsForMailbag(
			String companyCode, String mailId, String airportCode)
			throws SystemException {
		log.entering("DocumentController", "findConsignmentDetailsForMailbag");
		return ConsignmentDocument.findConsignmentDetailsForMailbag(
				companyCode, mailId, airportCode);
	}

	/**
	 * @author a-1883 This method returns Consignment Details
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 */
	public ConsignmentDocumentVO findConsignmentDocumentDetails(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException {
		log.entering("DocumentController", "findConsignmentDocumentDetails");
		return ConsignmentDocument
				.findConsignmentDocumentDetails(consignmentFilterVO);
	}

	/**
	 * This method deletes Consignment document details and its childs
	 *
	 * @author a-1883
	 * @param consignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 */
	public void deleteConsignmentDocumentDetails(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException {
		log.entering("DocumentController", "deleteConsignmentDocumentDetails");
		log.log(Log.SEVERE, new StringBuilder("This code executed from delete consignment command_1, csgdocnum ").append(consignmentDocumentVO.getConsignmentNumber())
				.append(" CSGSEQNUM ").append(consignmentDocumentVO.getConsignmentSequenceNumber()).toString());
		ConsignmentDocument consignmentDocument = ConsignmentDocument
				.find(consignmentDocumentVO);
		checkDespatchesAlreadyAccepted(consignmentDocumentVO
				.getMailInConsignmentVOs());
		consignmentDocument.remove();
		/* clears the consigment details of accepted mailbags */
		checkMailbagAccepted(consignmentDocumentVO.getMailInConsignmentVOs());
		log.exiting("DocumentController", "deleteConsignmentDocumentDetails");

	}

	/**
	 * This method checks whether any mails already accepted (calls while
	 * deleting mails from consignments )
	 *
	 * @author a-1883
	 * @param mailInConsignmentVOs
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 */
	private void checkDespatchesAlreadyAccepted(
			Collection<MailInConsignmentVO> mailInConsignmentVOs)
			throws SystemException, MailbagAlreadyAcceptedException {
		log.entering("DocumentController", "checkMailsAlreadyAccepted");
		//Modified by A-4809 for resolving issue while deleting consignment from screen.
		if (mailInConsignmentVOs != null && mailInConsignmentVOs.size() > 0) {
			log.log(Log.FINE, "Count of VOs", mailInConsignmentVOs.size());
		
				Collection<ErrorVO> errorVOs = null;
				String companyCode = null;
				StringBuilder errorString = null;
				for (MailInConsignmentVO mailInConsignmentVO : mailInConsignmentVOs) {
					if(mailInConsignmentVO.getMailId()==null){
			String mail = null;
						StringBuilder sb = new StringBuilder();
						mail = sb.append(mailInConsignmentVO.getCompanyCode())
								.append(mailInConsignmentVO.getConsignmentNumber())
								.append(mailInConsignmentVO.getConsignmentSequenceNumber())
								.append(mailInConsignmentVO.getDestinationExchangeOffice())
								.append(mailInConsignmentVO.getOriginExchangeOffice())
								.append(mailInConsignmentVO.getPaCode())
								.append(mailInConsignmentVO.getDsn())
								.append(mailInConsignmentVO.getMailSubclass())
								.append(mailInConsignmentVO.getMailCategoryCode())
								.append(mailInConsignmentVO.getYear()).toString();
						mailInConsignmentVO.setMailId(mail);
					}
					
				if (mailInConsignmentVO.getOperationFlag() !=null && !(MailInConsignmentVO.OPERATION_FLAG_INSERT
						.equals(mailInConsignmentVO.getOperationFlag()))
						&& !(MailInConsignmentVO.OPERATION_FLAG_UPDATE
								.equals(mailInConsignmentVO.getOperationFlag()))
									){
					//companyCode = new MailController()
							//.checkMailAccepted(mailInConsignmentVO); //Commented by A-8164 for ICRD-328503
					log.log(Log.FINE, " CompanyCode :", companyCode);
					if (companyCode != null && companyCode.trim().length() > 0) {
						if (errorString == null) {
							errorString = new StringBuilder();
						} else {
							errorString.append(",");
						}
						errorString
								.append(createErrorString(mailInConsignmentVO));
					}
				}
				} 
				if (errorString != null && errorString.length() > 0) {
					log.log(Log.FINE, " Mails Already Accepted ");
					Object[] errorData = new Object[1];
					errorData[0] = errorString.toString();
					ErrorVO errorVO = new ErrorVO(
							MailbagAlreadyAcceptedException.MAIL_ALREADY_ACCEPTED,
							errorData);
					MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException =
                        new MailbagAlreadyAcceptedException();
					if (errorVOs == null) {
						errorVOs = new ArrayList<ErrorVO>();
					}
					errorVOs.add(errorVO);
					mailbagAlreadyAcceptedException.addErrors(errorVOs);
					throw mailbagAlreadyAcceptedException;
				}
				log.log(Log.FINE, " Mails Not Accepted Yet");
			}

		
		log.exiting("DocumentController", "checkMailsAlreadyAccepted");
	}

	/**
	 * This method creates Error String for Mails accepted
	 *
	 * @author a-1883
	 * @param mailInConsignmentVO
	 * @return String
	 * @throws SystemException
	 */
	private String createErrorString(MailInConsignmentVO mailInConsignmentVO)
			throws SystemException {
		log.entering("DocumentController", "createErrorString");
		return new StringBuilder().append(
				mailInConsignmentVO.getOriginExchangeOffice()).append(
				mailInConsignmentVO.getDestinationExchangeOffice()).append(
            mailInConsignmentVO.getMailSubclass()).append(
				mailInConsignmentVO.getMailCategoryCode()).append(
				mailInConsignmentVO.getYear()).append(
				mailInConsignmentVO.getDsn()).toString();
	}


	/**
	 *
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String,Object> generateConsignmentReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException {
		log.entering("DocumentController","generateConsignmentReport");

		ConsignmentFilterVO consignmentFilterVO =
					(ConsignmentFilterVO)reportSpec.getFilterValues()
   															.iterator().next();

   		ConsignmentDocumentVO consignmentDocumentVO =null;
   		try {
			consignmentDocumentVO = Proxy.getInstance().get(MailOperationsProxy.class).generateConsignmentReportDtls(consignmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}

   		log.log(Log.FINE, "\n\n\n--consignmentDocumentVO----->",
				consignmentDocumentVO);
		log.log(Log.FINE, "\n\n\n--consignmentFilterVO----->",
				consignmentFilterVO);
		ArrayList<MailInConsignmentVO> consignmentVOs=new ArrayList<MailInConsignmentVO>();
		StringBuilder routingInfo=new StringBuilder("");
		if(consignmentDocumentVO.getRoutingInConsignmentVOs()!=null &&
				consignmentDocumentVO.getRoutingInConsignmentVOs().size()>0){
	for(RoutingInConsignmentVO routingConsignmentVO:
						consignmentDocumentVO.getRoutingInConsignmentVOs()){
		log.log(Log.INFO, "routingInfo inside ", routingInfo.toString());
		routingInfo.append(routingConsignmentVO.getOnwardCarrierCode());
		routingInfo.append(" ");
		routingInfo.append(routingConsignmentVO.getOnwardFlightNumber());
		routingInfo.append(" | ");
		String date="";
		LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		 fromDate=routingConsignmentVO.getOnwardFlightDate();
		date=fromDate.toDisplayDateOnlyFormat();
		log.log(Log.INFO, "OnwardFlightDate ", date.toUpperCase());
		routingInfo.append(date.toUpperCase());
		routingInfo.append(" | ");
		routingInfo.append(routingConsignmentVO.getPol());
		routingInfo.append(" | ");
		routingInfo.append(routingConsignmentVO.getPou());
		routingInfo.append("; \n");
		}
     log.log(Log.INFO, "routingInfo ", routingInfo.toString());
	}
		if(consignmentDocumentVO.getMailInConsignmentcollVOs()!=null &&
						consignmentDocumentVO.getMailInConsignmentcollVOs().size()>0){
			for(MailInConsignmentVO mailConsignmentVO:
								consignmentDocumentVO.getMailInConsignmentcollVOs()){
				if(!("").equals(mailConsignmentVO.getReceptacleSerialNumber())){
					log.log(Log.INFO, "getReceptacleSerialNumber ",
							mailConsignmentVO.getReceptacleSerialNumber());
								String wt="";
								if(mailConsignmentVO.getStatedWeight() != null &&  String.valueOf(mailConsignmentVO.getStatedWeight().getSystemValue())!=null){
						    	 wt = String.valueOf(mailConsignmentVO.getStatedWeight().getSystemValue());
								}
								 	int len = wt.indexOf(".");
						    	String wgt = wt;
						    	if(len > 0 && len < 4){
						    		wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,wt.length())).toString();
						    	}else{
						    		wgt = wt.substring(0,len);
						    	}
						    	String stdwgt = wgt;
						    	if(wgt.length() == 3){
						    		stdwgt = new StringBuilder("0").append(wgt).toString();
						    	}
						    	if(wgt.length() == 2){
						    		stdwgt = new StringBuilder("00").append(wgt).toString();
						    	}
						    	if(wgt.length() == 1){
						    		stdwgt = new StringBuilder("000").append(wgt).toString();
						    	}
								
						    	//mailConsignmentVO.setStrWeight(stdwgt);
						    	mailConsignmentVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(stdwgt)));//added by A-7371
						    
								}
				consignmentVOs.add(mailConsignmentVO);
			}
			consignmentFilterVO.setRoutingInfo(routingInfo.toString());
			log.log(Log.INFO, "routingInfo ", routingInfo.toString());
			String date="";
			LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			 fromDate=consignmentDocumentVO.getConsignmentDate();
			date=fromDate.toDisplayDateOnlyFormat();
			
			consignmentFilterVO.setConDate(date);
			consignmentFilterVO.setConType(consignmentDocumentVO.getType());
			if(reportSpec.getReportId()==null)
			{
			reportSpec.setReportId(CONSIGNMENT_REPORT_ID);
			}
			ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[] { "consignmentNumber",
			"paCode","conDate","conType","routingInfo","subType"});
			reportSpec.addParameterMetaData(parameterMetaData);
			reportSpec.addParameter(consignmentFilterVO);
			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] { "ORGEXGOFC",
			"DSTEXGOFC", "MALCTGCOD","MALSUBCLS", "YER","DSN",
			"RSN","PCS", "HSN","REGIND", "WGT","ULDNUM","STRWGT","DCLVAL","CURCOD"});
			reportMetaData.setFieldNames(new String[] { "originExchangeOffice",
			"destinationExchangeOffice", "mailCategoryCode","mailSubclass","year","dsn",
			"receptacleSerialNumber","statedBags","highestNumberedReceptacle",
			"registeredOrInsuredIndicator","statedWeight","uldNumber","strWeight","declaredValue","currencyCode"});
			reportSpec.setReportMetaData(reportMetaData);
			reportSpec.setData(consignmentVOs);

		}

		return ReportAgent.generateReport(reportSpec);

	} 
	
	/**
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public void generateControlDocumentReport(OperationalFlightVO
			operationalFlightVO)throws SystemException {
		log.entering("MailController", "generateControlDocumentReport");
        Collection < ControlDocumentVO> controlDocumentVOs =
				new ArrayList<ControlDocumentVO>();
        Map < String, Map<String, CN38ReportVO>> controlDocumentCN38Map
            = new HashMap<String, Map<String, CN38ReportVO>>();
        Map < String, CN38ReportVO> cn38Map = null;
		CN38ReportVO cn38ReportVO = null;
        Map < String, Map<String, CN41ReportVO>> controlDocumentCN41Map
            = new HashMap<String, Map<String, CN41ReportVO>>();
        Map < String, CN41ReportVO> cn41Map = null;
        Map < String, Map<String, AV7ReportVO>> controlDocumentAV7Map
            = new HashMap<String, Map<String, AV7ReportVO>>();
        Map < String, AV7ReportVO> av7Map = null;
		String airportName = null;
		CN41ReportVO cn41ReportVO = null;
		AV7ReportVO av7ReportVO = null;
		MailInReportVO mailInReportVO = null;
		ControlDocumentVO controlDocumentVO = null;
		String controlDocumentNumber = null;
		String reportKey = null;
		String av7Key = null;
        Collection < String> controlDocuments = null;
        Collection < String> airportCodes = new ArrayList<String>();
        Collection < String> controlDocumentNumbers = new ArrayList<String>();
        Map < String, AirportValidationVO>  airportNameMap = new HashMap<String,

						AirportValidationVO>();
		AirportValidationVO airportValidationVO = null;
		boolean isAPO = false;
        Collection < MailDetailVO> mailDetailVOs = AssignedFlight.
		findMailbagDetailsForReport(operationalFlightVO, null);
        log.log(Log.FINE, " mailDetailVOs before Grouping :", mailDetailVOs);
		if(mailDetailVOs  != null && mailDetailVOs.size() > 0) {
            for(MailDetailVO mailDetailVO:mailDetailVOs) {
                if(!airportCodes.contains(mailDetailVO.getDestinationAirport())) {

					airportCodes.add(mailDetailVO.getDestinationAirport());
				}
			}
			/*collects AirportName for the Corresponding Airport */
			airportNameMap = new SharedAreaProxy().validateAirportCodes(
					operationalFlightVO.getCompanyCode(),
					airportCodes);
            for(MailDetailVO mailDetailVO:mailDetailVOs) {
                airportValidationVO = airportNameMap.get(mailDetailVO.
                    getDestinationAirport());
                if(airportValidationVO != null) {
					 airportName = airportValidationVO.getAirportName();
				 }
				controlDocumentNumber = mailDetailVO.getControlDocumentNumber();
				reportKey = new StringBuilder().
				append(mailDetailVO.getOriginAirport()).
				append(mailDetailVO.getDestinationAirport()).
				append(mailDetailVO.getPou()).toString();
                log.log(Log.FINE, " Group Key : ", reportKey);
				if(checkMilitaryMailClass(mailDetailVO.getMailClass())) {
                    log.log(Log.FINE, " AV7 Control Document ");
                    log.log(Log.FINE, " AV7 ControlDocumentNumber ",
							controlDocumentNumber);
					isAPO = checkAPO(mailDetailVO.getMailSubclass());
                    av7Key = new StringBuilder().append(mailDetailVO.
                        getOriginAirport()).
                        append(mailDetailVO.getDestinationAirport()).append(
                        isAPO).toString();
						if(controlDocumentAV7Map == null
                        || controlDocumentAV7Map.size() == 0) {
							controlDocumentAV7Map = new
                            HashMap<String, Map<String, AV7ReportVO>>();
                        av7Map = new HashMap<String, AV7ReportVO>();
						controlDocumentAV7Map.put(controlDocumentNumber,
								av7Map);
						av7ReportVO = createAV7ReportVO(mailDetailVO,
                            operationalFlightVO, airportName, isAPO);
                        av7Map.put(av7Key, av7ReportVO);
                    }else {
                        av7Map = controlDocumentAV7Map.get(controlDocumentNumber
                            );
                        if(av7Map == null) {
                            av7Map = new HashMap<String, AV7ReportVO>();
								controlDocumentAV7Map.put(controlDocumentNumber,
										av7Map);
							}
							av7ReportVO = av7Map.get(av7Key);
                        if(av7ReportVO == null) {
								av7ReportVO = createAV7ReportVO(mailDetailVO,
                                operationalFlightVO, airportName, isAPO);
                            av7Map.put(av7Key, av7ReportVO);
							}
                        else {
                            createMailInReportVOForAV7(mailDetailVO,
                                av7ReportVO);
                        }
                    }
                }
                else {
						/* CN38 Report Grouping Start */
                    if(MailConstantsVO.MAIL_CATEGORY_AIR.equals(mailDetailVO.
                        getMailCategoryCode())) {
                        log.log(Log.FINE, " CN38 Control Document ");
                        log.log(Log.FINE, " CN38 ControlDocumentNumber ",
								controlDocumentNumber);
							if(controlDocumentCN38Map == null
                            || controlDocumentCN38Map.size() == 0) {
							controlDocumentCN38Map = new
                                HashMap<String, Map<String, CN38ReportVO>>();
                            cn38Map = new HashMap<String, CN38ReportVO>();
							controlDocumentCN38Map.put(controlDocumentNumber,
									cn38Map);
							cn38ReportVO = createCN38ReportVO(mailDetailVO,
                                operationalFlightVO, airportName);
                            cn38Map.put(reportKey, cn38ReportVO);
                        }else {
                            cn38Map = controlDocumentCN38Map.get(
                                controlDocumentNumber);
                            if(cn38Map == null) {
                                cn38Map = new HashMap<String, CN38ReportVO>();
                                controlDocumentCN38Map.put(controlDocumentNumber
                                    ,
											cn38Map);
								}
								cn38ReportVO = cn38Map.get(reportKey);
                            if(cn38ReportVO == null) {
									cn38ReportVO = createCN38ReportVO(mailDetailVO,
                                    operationalFlightVO, airportName);
                                cn38Map.put(reportKey, cn38ReportVO);
								}
                            else {
                                mailInReportVO = createMailInReportVOForCN38(
                                    mailDetailVO,
                                    cn38ReportVO);
                                cn38ReportVO.getMailInReportVOs().add(
                                    mailInReportVO);
								}
							}
						}
						/* CN38 Report Grouping End */
						/* CN41 Report Grouping Start */
                    else if(MailConstantsVO.MAIL_CATEGORY_SAL.equals(
                        mailDetailVO.getMailCategoryCode())) {
                        log.log(Log.FINE, " CN41 Control Document ");
                        log.log(Log.FINE, " CN41 ControlDocumentNumber ",
								controlDocumentNumber);
							if(controlDocumentCN41Map == null ||
                            controlDocumentCN41Map.size() == 0) {
								controlDocumentCN41Map = new
                                HashMap<String, Map<String, CN41ReportVO>>();
                            cn41Map = new HashMap<String, CN41ReportVO>();
							controlDocumentCN41Map.put(controlDocumentNumber,
									cn41Map);
							cn41ReportVO = createCN41ReportVO(mailDetailVO,
                                operationalFlightVO, airportName);
                            cn41Map.put(reportKey, cn41ReportVO);
                        }else {
                            cn41Map = controlDocumentCN41Map.get(
                                controlDocumentNumber);
                            if(cn41Map == null) {
                                cn41Map = new HashMap<String, CN41ReportVO>();
                                controlDocumentCN41Map.put(controlDocumentNumber
                                    ,
											cn41Map);
								}
								cn41ReportVO = cn41Map.get(reportKey);
                            if(cn41ReportVO == null) {
									cn41ReportVO = createCN41ReportVO(mailDetailVO,
                                    operationalFlightVO, airportName);
                                cn41Map.put(reportKey, cn41ReportVO);
								}
                            else {
									 createMailInReportVOForCN41(mailDetailVO,
											cn41ReportVO);
								}
							}
						}
						/* CN41 Report Grouping End */
					}
				}

		}// MailDetailVOs null check

        if(controlDocumentCN38Map != null && controlDocumentCN38Map.keySet() !=
            null) {
			controlDocumentNumbers.addAll(controlDocumentCN38Map.keySet());
		}
        if(controlDocumentCN41Map != null && controlDocumentCN41Map.keySet() !=
            null ) {
			controlDocuments = controlDocumentCN41Map.keySet();
            for(String cn41ControlNum:controlDocuments) {
                if(!controlDocumentNumbers.contains(cn41ControlNum)) {
					controlDocumentNumbers.add(cn41ControlNum);
				}
			}
		}
        if(controlDocumentAV7Map != null && controlDocumentAV7Map.keySet() !=
            null ) {
			controlDocuments = controlDocumentAV7Map.keySet();
            for(String av7ControlNum:controlDocuments) {
                if(!controlDocumentNumbers.contains(av7ControlNum)) {
					controlDocumentNumbers.add(av7ControlNum);
				}
			}
		}
        if(controlDocumentNumbers.size() > 0 ) {
            for(String controlDocNum:controlDocumentNumbers) {
				controlDocumentVO = new ControlDocumentVO();
				cn38Map = controlDocumentCN38Map.get(controlDocNum);
                if(cn38Map != null && cn38Map.values() != null ) {
				controlDocumentVO.setCn38ReportVos(cn38Map.values());
				}
				cn41Map = controlDocumentCN41Map.get(controlDocNum);
                if(cn41Map != null && cn41Map.values() != null ) {
				controlDocumentVO.setCn41ReportVos(cn41Map.values());
				}
				av7Map = controlDocumentAV7Map.get(controlDocNum);
                if(av7Map != null && av7Map.values() != null ) {
					controlDocumentVO.setAv7ReportVos(av7Map.values());
					}
				controlDocumentVOs.add(controlDocumentVO);
			}
		}
        log.log(Log.FINE, " controlDocumentVOs ==>", controlDocumentVOs);
		if(controlDocumentVOs != null && controlDocumentVOs.size() > 0) {
            for(ControlDocumentVO controlDocumentVo:controlDocumentVOs) {
				printControlDocumentReport(controlDocumentVo);
			}
		}

	}
	
	/**
	 * @param mailDetailVO
	 * @param operationalFlightVO
	 * @param destinationAirportName
	 * @return
	 */
	private CN38ReportVO createCN38ReportVO(MailDetailVO mailDetailVO,
			OperationalFlightVO operationalFlightVO,
        String destinationAirportName) {
		CN38ReportVO cn38ReportVO = new CN38ReportVO();
        Collection < MailInReportVO> mailInReportVOs = new ArrayList<
            MailInReportVO>();
        MailInReportVO mailInReportVO = null;
        cn38ReportVO.setControlDocumentNumber(mailDetailVO.
            getControlDocumentNumber());
		cn38ReportVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
		cn38ReportVO.setFlightDate(operationalFlightVO.getFlightDate());
		cn38ReportVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		cn38ReportVO.setOriginAirport(mailDetailVO.getOriginAirport());
		cn38ReportVO.setDestinationAirportName(destinationAirportName);
		cn38ReportVO.setPou(mailDetailVO.getPou());		
		try {
		mailInReportVO = createMailInReportVOForCN38(mailDetailVO,
				cn38ReportVO);		
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE, "SystemException",e.getMessage());
		}		
		mailInReportVOs.add(mailInReportVO);
		cn38ReportVO.setMailInReportVOs(mailInReportVOs);
		return cn38ReportVO;
	}
	
	
	/**
	 * @author A-1883
	 * @param controlDocumentVO
	 * @throws SystemException
	 */
	private void printControlDocumentReport(ControlDocumentVO controlDocumentVO)
	throws SystemException {
		ReportSpec reportSpec = null;
        Collection < CN38ReportVO> cn38ReportVOs = controlDocumentVO.
            getCn38ReportVos();
        Collection < CN41ReportVO> cn41ReportVOs = controlDocumentVO.
            getCn41ReportVos();
        Collection < AV7ReportVO> av7ReportVOs = controlDocumentVO.
            getAv7ReportVos();
        if(cn38ReportVOs != null && cn38ReportVOs.size() > 0 ) {
            for(CN38ReportVO cn38ReportVO:cn38ReportVOs) {
				reportSpec = new ReportSpec();
				reportSpec.setReportId(MailConstantsVO.CN38_REPORT_ID);
				reportSpec.addParameter(cn38ReportVO);
                Collection < CN38ReportVO> reportCn38Data = new ArrayList<
                    CN38ReportVO>();
		    	reportCn38Data.add(cn38ReportVO);
		    	reportSpec.setData(reportCn38Data);
		    	ReportAgent.generateReport(reportSpec);
			}
		}
        if(cn41ReportVOs != null && cn41ReportVOs.size() > 0 ) {
            for(CN41ReportVO cn41ReportVO:cn41ReportVOs) {
				reportSpec = new ReportSpec();
				reportSpec.setReportId(MailConstantsVO.CN41_REPORT_ID);
				reportSpec.addParameter(cn41ReportVO);
                Collection < CN41ReportVO> reportCn41Data = new ArrayList<
                    CN41ReportVO>();
		    	reportCn41Data.add(cn41ReportVO);
		    	reportSpec.setData(reportCn41Data);
		    	ReportAgent.generateReport(reportSpec);
			}
		}
        if(av7ReportVOs != null && av7ReportVOs.size() > 0 ) {
            for(AV7ReportVO av7ReportVO:av7ReportVOs) {
				reportSpec = new ReportSpec();
				reportSpec.setReportId(MailConstantsVO.AV7_REPORT_ID);
				reportSpec.addParameter(av7ReportVO);
                Collection < AV7ReportVO> reportAv7Data = new ArrayList<
                    AV7ReportVO>();
		    	reportAv7Data.add(av7ReportVO);
		    	reportSpec.setData(reportAv7Data);
		    	ReportAgent.generateReport(reportSpec);
			}
		}
	}
	
	
	
	/**
	 * @param mailDetailVO
	 * @param cn38ReportVO
	 * @return
	 * @throws SystemException 
	 */
    private MailInReportVO createMailInReportVOForCN38(MailDetailVO mailDetailVO
        ,
        CN38ReportVO cn38ReportVO) throws SystemException {
		MailInReportVO mailInReportVO = new MailInReportVO();
		mailInReportVO.setDestinationOffice(mailDetailVO.getDestinationCity());
		mailInReportVO.setDsn(mailDetailVO.getDsn());
		mailInReportVO.setMailId(mailDetailVO.getMailId());
		mailInReportVO.setOriginOffice(mailDetailVO.getOriginCity());
        mailInReportVO.setRsn(mailDetailVO.getMailId().substring(20, 23));
		mailInReportVO.setUldNumber(mailDetailVO.getUldNumber());
		
        double weight = Double.parseDouble(mailDetailVO.getMailId().substring(25
            )) / 10;
        if(MailConstantsVO.LETTERS_CODE.equals(mailDetailVO.getMailSubclass().
            substring(0, 1))) {
			//mailInReportVO.setWeightOfLetters(weight);
        	mailInReportVO.setWeightOfLetters(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371
            cn38ReportVO.setTotalLetterBags(cn38ReportVO.getTotalLetterBags() +
                1);
           /* cn38ReportVO.setTotalLetterWeight(cn38ReportVO.getTotalLetterWeight(
                ) + weight);*/
            try {
				cn38ReportVO.setTotalLetterWeight(Measure.addMeasureValues(cn38ReportVO.getTotalLetterWeight(
				        ), new Measure(UnitConstants.MAIL_WGT,weight)));
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getErrorCode());
			}//added by A-7371
        }else if(MailConstantsVO.PARCELS_CODE.equals(mailDetailVO.
            getMailSubclass().substring(0, 1))) {
			//mailInReportVO.setWeightOfParcels(weight);
        	mailInReportVO.setWeightOfParcels(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371
            cn38ReportVO.setTotalParcelBags(cn38ReportVO.getTotalParcelBags() +
                1);
           /* cn38ReportVO.setTotalParcelWeight(cn38ReportVO.getTotalParcelWeight(
                ) + weight);*/
            try {
				cn38ReportVO.setTotalParcelWeight(Measure.addMeasureValues(cn38ReportVO.getTotalParcelWeight(
				        ), new Measure(UnitConstants.MAIL_WGT,weight)));
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getErrorCode());
			}//added by A-7371
        }else if(MailConstantsVO.EXPRESSMAIL_CODE.equals(mailDetailVO.
            getMailSubclass().substring(0, 1))) {
			//mailInReportVO.setWeightOfExpressMails(weight);
        	mailInReportVO.setWeightOfExpressMails(new Measure(UnitConstants.MAIL_WGT,weight));//added by A-7371
            cn38ReportVO.setTotalEMSBags(cn38ReportVO.getTotalEMSBags() + 1);
            /*cn38ReportVO.setTotalEMSWeight(cn38ReportVO.getTotalEMSWeight() +
                weight);*/
            try {
				cn38ReportVO.setTotalEMSWeight(Measure.addMeasureValues(cn38ReportVO.getTotalEMSWeight(),new Measure(UnitConstants.MAIL_WGT ,weight)
				        ));
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				throw new SystemException(e.getErrorCode());
			}
		}
		return mailInReportVO;
	}
	
    
    /**
	 * @param mailDetailVO
	 * @param operationalFlightVO
	 * @param airportName
	 * @param isAPO
	 * @return
	 */
	private AV7ReportVO createAV7ReportVO(MailDetailVO mailDetailVO,
        OperationalFlightVO operationalFlightVO, String airportName,
        boolean isAPO) {
		AV7ReportVO av7ReportVO = new AV7ReportVO();
        av7ReportVO.setControlDocumentNumber(mailDetailVO.
            getControlDocumentNumber());
		av7ReportVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
		av7ReportVO.setFlightDate(operationalFlightVO.getFlightDate());
		av7ReportVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		av7ReportVO.setOriginAirport(mailDetailVO.getOriginAirport());
		av7ReportVO.setDestinationAirport(mailDetailVO.getDestinationAirport());
		av7ReportVO.setDestinationAirportName(airportName);
		av7ReportVO.setAPO(isAPO);
		try {
		createMailInReportVOForAV7(mailDetailVO,
				av7ReportVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE,"SystemException",e.getMessage());
		}
		return av7ReportVO;
	}
	
	/**
	 * @param mailDetailVO
	 * @param av7ReportVO
	 * @throws SystemException 
	 */
	private void createMailInReportVOForAV7(MailDetailVO mailDetailVO,
        AV7ReportVO av7ReportVO) throws SystemException {
		boolean isODPairPresent = false;
		String subClassCode = null;
		boolean isLetter = false;
		MailInReportVO mailInReportVO = null;
		String mailInReportKey = new StringBuilder().append(mailDetailVO.
            getOriginCity()).append(mailDetailVO.getDestinationCity()).toString(
            );
        log.log(Log.FINE, "mailInReportKey : ", mailInReportKey);
		Collection < MailInReportVO> mailInReportVOs = av7ReportVO.
            getMailInReportVOs();
        if(mailInReportVOs != null && mailInReportVOs.size() > 0) {
            for(MailInReportVO alreadyAddedReportVO :mailInReportVOs) {
                if(new StringBuilder().append(alreadyAddedReportVO.
                    getOriginOffice()).
                    append(alreadyAddedReportVO.getDestinationOffice()).
                    toString().equals(mailInReportKey)) {
					isODPairPresent = true;
					mailInReportVO = alreadyAddedReportVO;
					break;
				}
			}
        }else {
			mailInReportVOs = new ArrayList<MailInReportVO>();
		}
        log.log(Log.FINE, "isODPairPresent : ", isODPairPresent);
		if(!isODPairPresent) {
			mailInReportVO = new MailInReportVO();
			mailInReportVOs.add(mailInReportVO);
            mailInReportVO.setDestinationOffice(mailDetailVO.getDestinationCity(
                ));
			mailInReportVO.setOriginOffice(mailDetailVO.getOriginCity());
			mailInReportVO.setUldNumber(mailDetailVO.getUldNumber());
			av7ReportVO.setMailInReportVOs(mailInReportVOs);
		}
        double weight = Double.parseDouble(mailDetailVO.getMailId().substring(25
            )) / 10;
		subClassCode = mailDetailVO.getMailSubclass();
        if(av7ReportVO.isAPO()) {
            if(MailConstantsVO.APO_LETTER_CODE.equals(subClassCode)) {
               /* mailInReportVO.setWeightOfLetters(mailInReportVO.
                    getWeightOfLetters() + weight);*/
            	try {
					mailInReportVO.setWeightOfLetters(Measure.addMeasureValues(mailInReportVO.
					        getWeightOfLetters(), new Measure(UnitConstants.MAIL_WGT,weight)));
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					throw new SystemException(e.getErrorCode());
				}//added by A-7371
                mailInReportVO.setNumberOfLetters(mailInReportVO.
                    getNumberOfLetters() + 1);
                av7ReportVO.setTotalLetterBags(av7ReportVO.getTotalLetterBags() +
                    1);
               /* av7ReportVO.setTotalLetterWeight(av7ReportVO.
                    getTotalLetterWeight() + weight);*/
                try {
					av7ReportVO.setTotalLetterWeight(Measure.addMeasureValues(av7ReportVO.
					        getTotalLetterWeight(), new Measure(UnitConstants.MAIL_WGT,weight)));
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					throw new SystemException(e.getErrorCode());
				}//added by A-7371
            }else {
                for(String parcelCode:MailConstantsVO.APO_PARCEL_CODES) {
                    if(parcelCode.equals(subClassCode)) {
                       /* mailInReportVO.setWeightOfParcels(mailInReportVO.
                            getWeightOfParcels() + weight);*/
                    	try {
							mailInReportVO.setWeightOfParcels(Measure.addMeasureValues(mailInReportVO.
							    getWeightOfParcels(),new Measure(UnitConstants.MAIL_WGT,weight)));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}//added by A-7371
                        mailInReportVO.setNumberOfParcels(mailInReportVO.
                            getNumberOfParcels() + 1);
                        av7ReportVO.setTotalParcelBags(av7ReportVO.
                            getTotalParcelBags() + 1);
                        /*av7ReportVO.setTotalParcelWeight(av7ReportVO.
                            getTotalParcelWeight() + weight);*/
                        try {
							av7ReportVO.setTotalParcelWeight(Measure.addMeasureValues(av7ReportVO.
							    getTotalParcelWeight(), new Measure(UnitConstants.MAIL_WGT, weight)));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}//added by A-7371
                        break;
                    }
                }
            }
        }else {
            for(String letterCode:MailConstantsVO.FPO_LETTER_CODES) {
                if(letterCode.equals(subClassCode)) {
                    /*mailInReportVO.setWeightOfLetters(mailInReportVO.
                        getWeightOfLetters() + weight);*/
                	try {
						mailInReportVO.setWeightOfLetters(Measure.addMeasureValues(mailInReportVO.
						    getWeightOfLetters(), new Measure(UnitConstants.MAIL_WGT,weight)));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						throw new SystemException(e.getErrorCode());
					}//added by A-7371
                    mailInReportVO.setNumberOfLetters(mailInReportVO.
                        getNumberOfLetters() + 1);
                    av7ReportVO.setTotalLetterBags(av7ReportVO.
                        getTotalLetterBags() + 1);
                    /*av7ReportVO.setTotalLetterWeight(av7ReportVO.
                        getTotalLetterWeight() + weight);*/
                    try {
						av7ReportVO.setTotalLetterWeight(Measure.addMeasureValues(av7ReportVO.
						    getTotalLetterWeight(), new Measure(UnitConstants.MAIL_WGT,weight)));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						throw new SystemException(e.getErrorCode());
					}//added by A-7371
				isLetter = true;
				break;
				}
			}
            if(!isLetter) {
                for(String parcelCode:MailConstantsVO.FPO_PARCEL_CODES) {
                    if(parcelCode.equals(subClassCode)) {
                        /*mailInReportVO.setWeightOfParcels(mailInReportVO.
                            getWeightOfParcels() + weight);*/
                    	try {
							mailInReportVO.setWeightOfParcels(Measure.addMeasureValues(mailInReportVO.
							    getWeightOfParcels(), new Measure(UnitConstants.MAIL_WGT,weight)));//added by A-7371
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}
                        mailInReportVO.setNumberOfParcels(mailInReportVO.
                            getNumberOfParcels() + 1);
                        av7ReportVO.setTotalParcelBags(av7ReportVO.
                            getTotalParcelBags() + 1);
                        /*av7ReportVO.setTotalParcelWeight(av7ReportVO.
                            getTotalParcelWeight() + weight);*/
                        try {
							av7ReportVO.setTotalParcelWeight(Measure.addMeasureValues(av7ReportVO.
							    getTotalParcelWeight(), new Measure(UnitConstants.MAIL_WGT,weight)));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							throw new SystemException(e.getErrorCode());
						}//added by A-7371
                        break;
                    }
                }
            }
        }
    }
	

	/**
	 * @param mailDetailVO
	 * @param operationalFlightVO
	 * @param airportName
	 * @return
	 */
	private CN41ReportVO createCN41ReportVO(MailDetailVO mailDetailVO,
        OperationalFlightVO operationalFlightVO, String airportName) {
		CN41ReportVO cn41ReportVO = new CN41ReportVO();
        cn41ReportVO.setControlDocumentNumber(mailDetailVO.
            getControlDocumentNumber());
		cn41ReportVO.setFlightCarrierCode(operationalFlightVO.getCarrierCode());
		cn41ReportVO.setFlightDate(operationalFlightVO.getFlightDate());
		cn41ReportVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		cn41ReportVO.setOriginAirport(mailDetailVO.getOriginAirport());
		cn41ReportVO.setDestinationAirportName(airportName);
		cn41ReportVO.setPou(mailDetailVO.getPou());
		cn41ReportVO.setDestinationAirport(mailDetailVO.getDestinationAirport());
		cn41ReportVO.setPouName(mailDetailVO.getPou());
		try {
		createMailInReportVOForCN41(mailDetailVO,
				cn41ReportVO);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE, "SystemException",e.getMessage());
		}
		return cn41ReportVO;
	}
	
	
	
	 /**
	  * @param mailDetailVO
	  * @param cn41ReportVO
	 * @throws SystemException 
	  */
	private void createMailInReportVOForCN41(MailDetailVO mailDetailVO,
       CN41ReportVO cn41ReportVO) throws SystemException {
		boolean isDsnAlreadyPresent = false;
		MailInReportVO mailInReportVO = null;
       String mailInReportKey = new StringBuilder().append(mailDetailVO.getDsn(
           ))
           .append(mailDetailVO.getOriginCity()).append(mailDetailVO.
           getDestinationCity()).toString();
       log.log(Log.FINE, "mailInReportKey : ", mailInReportKey);
	Collection < MailInReportVO> mailInReportVOs = cn41ReportVO.
           getMailInReportVOs();
       if(mailInReportVOs != null && mailInReportVOs.size() > 0) {
           for(MailInReportVO alreadyAddedReportVO :mailInReportVOs) {
				if(new StringBuilder().append(alreadyAddedReportVO.getDsn()).
						append(alreadyAddedReportVO.getOriginOffice()).
						append(alreadyAddedReportVO.getDestinationOffice()).
                   toString().equals(mailInReportKey)) {
					isDsnAlreadyPresent = true;
					mailInReportVO = alreadyAddedReportVO;
					break;
				}
			}
       }else {
			mailInReportVOs = new ArrayList<MailInReportVO>();
		}
       log.log(Log.FINE, "isDsnAlreadyPresent : ", isDsnAlreadyPresent);
	if(!isDsnAlreadyPresent) {
			mailInReportVO = new MailInReportVO();
			mailInReportVOs.add(mailInReportVO);
           mailInReportVO.setDestinationOffice(mailDetailVO.getDestinationCity(
               ));
           mailInReportVO.setDsn(mailDetailVO.getDsn());
           mailInReportVO.setOriginOffice(mailDetailVO.getOriginCity());
           mailInReportVO.setRsn(mailDetailVO.getMailId().substring(20, 23));
			mailInReportVO.setUldNumber(mailDetailVO.getUldNumber());
			cn41ReportVO.setMailInReportVOs(mailInReportVOs);
		}
       double weight = Double.parseDouble(mailDetailVO.getMailId().substring(25
           )) / 10;
       if(MailConstantsVO.LETTERS_CODE.equals(mailDetailVO.getMailSubclass().
           substring(0, 1))) {
          /* mailInReportVO.setWeightOfLetters(mailInReportVO.getWeightOfLetters(
               ) + weight);*/
    	   try {
			mailInReportVO.setWeightOfLetters(Measure.addMeasureValues(mailInReportVO.getWeightOfLetters(
			       ), new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by A-7371
           mailInReportVO.setNumberOfLetters(mailInReportVO.getNumberOfLetters(
               ) + 1);
           cn41ReportVO.setTotalLetterBags(cn41ReportVO.getTotalLetterBags() +
               1);
          /* cn41ReportVO.setTotalLetterWeight(cn41ReportVO.getTotalLetterWeight(
               ) + weight);*/
           try {
			cn41ReportVO.setTotalLetterWeight(Measure.addMeasureValues(cn41ReportVO.getTotalLetterWeight(
			       ),  new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by A-7371
       }else if(MailConstantsVO.PARCELS_CODE.equals(mailDetailVO.
           getMailSubclass().substring(0, 1))) {
           /*mailInReportVO.setWeightOfParcels(mailInReportVO.getWeightOfParcels(
               ) + weight);*/
    	   try {
			mailInReportVO.setWeightOfParcels(Measure.addMeasureValues(mailInReportVO.getWeightOfParcels(), 
					   new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by A-7371
           mailInReportVO.setNumberOfParcels(mailInReportVO.getNumberOfParcels(
               ) + 1);
           cn41ReportVO.setTotalParcelBags(cn41ReportVO.getTotalParcelBags() +
               1);
           /*cn41ReportVO.setTotalParcelWeight(cn41ReportVO.getTotalParcelWeight(
               ) + weight);*/
           try {
			cn41ReportVO.setTotalParcelWeight(Measure.addMeasureValues(cn41ReportVO.getTotalParcelWeight(
			       ), new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by A-7371
       }else if(MailConstantsVO.EXPRESSMAIL_CODE.equals(mailDetailVO.
           getMailSubclass().substring(0, 1))) {
          /* mailInReportVO.setWeightOfExpressMails(mailInReportVO.
               getWeightOfExpressMails() + weight);*/
    	   try {
			mailInReportVO.setWeightOfExpressMails(Measure.addMeasureValues(mailInReportVO.
			       getWeightOfExpressMails(),  new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by a-7371
           mailInReportVO.setNumberOfExpressMails(mailInReportVO.
               getNumberOfExpressMails() + 1);
           cn41ReportVO.setTotalEMSBags(cn41ReportVO.getTotalEMSBags() + 1);
           /*cn41ReportVO.setTotalEMSWeight(cn41ReportVO.getTotalEMSWeight() +
               weight);*/
           try {
			cn41ReportVO.setTotalEMSWeight(Measure.addMeasureValues(cn41ReportVO.getTotalEMSWeight(), 
					   new Measure(UnitConstants.MAIL_WGT,weight)));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			throw new SystemException(e.getErrorCode());
		}//added by A-7371
       }
   }

	/**
	 * @param mailClass
	 * @return
	 */
    private  boolean checkMilitaryMailClass(String mailClass) {
		boolean isMilitaryMail = false;
        for(String classCode:MailConstantsVO.MILITARY_CLASS) {
            if(classCode.equals(mailClass)) {
				isMilitaryMail = true;
				break;
			}
		}
        log.log(Log.FINE, " isMilitaryMail : ", isMilitaryMail);
		return isMilitaryMail;
	}
	
    
    /**
	 * @param mailSubClass
	 * @return
	 */
    private boolean checkAPO(String mailSubClass) {
		boolean isAPO = false;
        for(String apoCode:MailConstantsVO.APO_CODES) {
            if(apoCode.equals(mailSubClass.substring(0, 1))) {
				isAPO = true;
				break;
			}
		}
        log.log(Log.FINE, " isAPO :", isAPO);
		return isAPO;
	}
    
    
    /**
	 * GenerateControlDocument with Single preview
	 * Sep 14, 2007, a-1739
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> generateControlDocument(ReportSpec reportSpec) throws SystemException {
		log.entering("MailController", "generateControlDocument");
		OperationalFlightVO opFlightVO = OperationalFlightVO.class.cast(reportSpec.getFilterValues().get(0));		
		String reportKey = (String)reportSpec.getFilterValues().get(1);		
		log.log(Log.FINEST, "reprtkey ", reportKey);
		String[] rprtDtls = reportKey.split(MailConstantsVO.CONSIGN_REPORT_SEP);
		Collection < MailDetailVO> mailDetailVOs =
			AssignedFlight.findMailbagDetailsForReport(opFlightVO, reportKey);
		String reportType = rprtDtls[0];		
		if(MailConstantsVO.CONSIGNMENT_TYPE_AV7.equals(reportType)) {
			String av7PO = rprtDtls[4];
			AV7ReportVO av7ReportVO = null;
			for(MailDetailVO mailDetailVO : mailDetailVOs) {
				if(av7ReportVO == null) {
					av7ReportVO = createAV7ReportVO(mailDetailVO,
						opFlightVO,mailDetailVO.getDestnAirportName(), 
						MailConstantsVO.MIL_POST_APO.equals(av7PO));
				} else {
			            createMailInReportVOForAV7(mailDetailVO,
			                av7ReportVO);
			     }
			}
			reportSpec.addParameter(av7ReportVO);
			Collection<AV7ReportVO> av7s = new ArrayList<AV7ReportVO>();
			av7s.add(av7ReportVO);
			reportSpec.setData(av7s);
		} else if(MailConstantsVO.CONSIGNMENT_TYPE_CN38.equals(reportType)) {
			CN38ReportVO cn38ReportVO = null;
			for(MailDetailVO mailDetailVO : mailDetailVOs) {
				 if(cn38ReportVO == null) {
						cn38ReportVO = createCN38ReportVO(mailDetailVO,
							opFlightVO, mailDetailVO.getDestnAirportName());
				 } else {
						cn38ReportVO.getMailInReportVOs().add(
							createMailInReportVOForCN38(mailDetailVO, cn38ReportVO));
					 
				 }
			}
			reportSpec.addParameter(cn38ReportVO);
			Collection<CN38ReportVO> cn38s = new ArrayList<CN38ReportVO>();
			cn38s.add(cn38ReportVO);
			reportSpec.setData(cn38s);
		} else if(MailConstantsVO.CONSIGNMENT_TYPE_CN41.equals(reportType)) {
			CN41ReportVO cn41ReportVO = null;
			for(MailDetailVO mailDetailVO : mailDetailVOs) {
				 if(cn41ReportVO == null) {
						cn41ReportVO = createCN41ReportVO(mailDetailVO,
							opFlightVO, mailDetailVO.getDestnAirportName());
				 } else {
					 createMailInReportVOForCN41(mailDetailVO, cn41ReportVO);
				 }
			}
			reportSpec.addParameter(cn41ReportVO);
			Collection<CN41ReportVO> cn41s = new ArrayList<CN41ReportVO>();
			cn41s.add(cn41ReportVO);
			reportSpec.setData(cn41s);
		}
		log.entering("MailController", "generateControlDocument");
		return ReportAgent.generateReport(reportSpec);
	}
	
	
	/**
	 * Returns the report keys needed for consignment printing
	 * Sep 14, 2007, A-1739
	 * @param opFlightVO
	 * @return
	 * @throws SystemException
	 */
	public ControlDocumentVO findControlDocumentsForPrint(OperationalFlightVO opFlightVO)
	throws SystemException {
		log.entering("MailController", "findControlDocumentsForPrint");
		Collection < MailDetailVO> mailDetailVOs =
			AssignedFlight.findMailbagDetailsForReport(opFlightVO, null);

		Map<String, Collection<String>> documentReportsMap  =
			new HashMap<String, Collection<String>>();

		ControlDocumentVO docVO = new ControlDocumentVO();
		docVO.setDocumentReportsMap(documentReportsMap);
		Collection<String> reportKeys = null;
		String reportKey = null;
		String controlDocNum = null;
		String ctrlDocKey = null;
		for(MailDetailVO mailDetailVO : mailDetailVOs) {
			
			controlDocNum = mailDetailVO.getControlDocumentNumber();
						
			if(checkMilitaryMailClass(mailDetailVO.getMailClass())) {		

				ctrlDocKey = new StringBuilder().append(
					MailConstantsVO.CONSIGNMENT_TYPE_AV7).append(
					MailConstantsVO.CONSIGN_REPORT_SEP).append(controlDocNum).toString();
				
				reportKeys = documentReportsMap.get(ctrlDocKey);
				if(reportKeys == null) {
					reportKeys = new ArrayList<String>();
					documentReportsMap.put(ctrlDocKey, reportKeys);
				}
				String rptType = null;
				if(checkAPO(mailDetailVO.getMailSubclass())) {
					rptType = MailConstantsVO.MIL_POST_APO;
				} else {
					rptType = MailConstantsVO.MIL_POST_FPO;
				}
				reportKey = new StringBuilder().append(mailDetailVO.
                    getOriginAirport()).
                    append(MailConstantsVO.CONSIGN_REPORT_SEP).
                    append(mailDetailVO.getDestinationAirport()).
                    append(MailConstantsVO.CONSIGN_REPORT_SEP).
                    append(rptType).toString();
				
				if(!reportKeys.contains(reportKey)) {
					reportKeys.add(reportKey);
				}
			} else {
				reportKey = new StringBuilder().
				                    append(mailDetailVO.getOriginAirport()).
				                    append(MailConstantsVO.CONSIGN_REPORT_SEP).
				                    append(mailDetailVO.getDestinationAirport()).
				                    append(MailConstantsVO.CONSIGN_REPORT_SEP).
				                    append(mailDetailVO.getPou()).toString();
				if(MailConstantsVO.MAIL_CATEGORY_AIR.equals(mailDetailVO.getMailCategoryCode())) {

					ctrlDocKey = new StringBuilder().append(
						MailConstantsVO.CONSIGNMENT_TYPE_CN38).append(
						MailConstantsVO.CONSIGN_REPORT_SEP).append(controlDocNum).toString();
					
					reportKeys = documentReportsMap.get(ctrlDocKey);
					if(reportKeys == null) {
						reportKeys = new ArrayList<String>();
						documentReportsMap.put(ctrlDocKey, reportKeys);
					}
					if(!reportKeys.contains(reportKey)) {
						reportKeys.add(reportKey);
					}
				} else if(MailConstantsVO.MAIL_CATEGORY_SAL.equals(
										mailDetailVO.getMailCategoryCode())){
					ctrlDocKey = new StringBuilder().append(
						MailConstantsVO.CONSIGNMENT_TYPE_CN41).append(
						MailConstantsVO.CONSIGN_REPORT_SEP).append(controlDocNum).toString();
					
					reportKeys = documentReportsMap.get(ctrlDocKey);
					if(reportKeys == null) {
						reportKeys = new ArrayList<String>();
						documentReportsMap.put(ctrlDocKey, reportKeys);
					}
					if(!reportKeys.contains(reportKey)) {
						reportKeys.add(reportKey);
					}
					
				}
			}
		}
		log.exiting("MailController", "findControlDocumentsForPrint");
		return docVO;
	}

   /**
    * @author A-3227  - FEB 10, 2009
    * @param companyCode
    * @param despatchDetailsVOs
    * @return The Collection Of Despatches with wrong CSG DTLS
    * @throws SystemException
    */
   public Collection<DespatchDetailsVO> validateConsignmentDetails(String companyCode,Collection<DespatchDetailsVO> despatchDetailsVOs)
   throws SystemException{ 		   
	   Collection<DespatchDetailsVO> despatchesWithWrongCsgDtls = new ArrayList<DespatchDetailsVO>();
	   for(DespatchDetailsVO despatchDetailVO : despatchDetailsVOs) {
		   DespatchDetailsVO despDtlVO = ConsignmentDocument.findConsignmentDetailsForDespatch(companyCode,despatchDetailVO);
		   if(despDtlVO != null) {
			   if(despatchDetailVO.getConsignmentNumber().equals(despDtlVO.getConsignmentNumber()) &&
					   despatchDetailVO.getPaCode().equals(despDtlVO.getPaCode()) &&
					   despatchDetailVO.getDsn().equals(despDtlVO.getDsn()) &&
					   despatchDetailVO.getOriginOfficeOfExchange().equals(despDtlVO.getOriginOfficeOfExchange()) &&
					   despatchDetailVO.getDestinationOfficeOfExchange().equals(despDtlVO.getDestinationOfficeOfExchange()) &&
					   despatchDetailVO.getMailCategoryCode().equals(despDtlVO.getMailCategoryCode()) &&
					   despatchDetailVO.getMailSubclass().equals(despDtlVO.getMailSubclass()) &&
					   despatchDetailVO.getYear()== despDtlVO.getYear()) {
				   if(!despatchDetailVO.getConsignmentDate().toDisplayDateOnlyFormat()
						   .equals(despDtlVO.getConsignmentDate().toDisplayDateOnlyFormat()) ||
						   despatchDetailVO.getStatedBags() != despDtlVO.getStatedBags() ||
						   despatchDetailVO.getStatedWeight() != despDtlVO.getStatedWeight()) {
					   despatchesWithWrongCsgDtls.add(despatchDetailVO);
				   }
			   }
		   }
	   }
	   return despatchesWithWrongCsgDtls;
   }
   
   /**
	 * @author a-3429 This method returns Consignment Details for print
	 * @param consignmentFilterVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 */
	public ConsignmentDocumentVO generateConsignmentReportDtls(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException {
		log.entering("DocumentController", "generateConsignmentReport");
		return ConsignmentDocument
				.generateConsignmentReport(consignmentFilterVO);
	}
	/**
	 * @author a-2391
	 * @param consignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 * @throws SystemException
	 * @throws MailbagAlreadyAcceptedException
	 * @throws InvalidMailTagFormatException
	 * @throws DuplicateDSNException
	 * @throws DuplicateMailBagsException 
	 */
	public ConsignmentDocumentVO saveConsignmentDocumentForHHT(
			ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException,
			InvalidMailTagFormatException,DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "saveConsignmentDocument");
		Integer consignmentSeqNumber = null;
		log.log(Log.FINE, " OPERATION FLAG :", consignmentDocumentVO.getOperationFlag());
		LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
		consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
		/*
		 * Validating the Mailbag
		 */
		//validateMailBags((Collection<MailInConsignmentVO>)consignmentDocumentVO.getMailInConsignmentVOs(),consignmentDocumentVO.isScanned());
		/*
		 * validating the Mailbag, to check, whether it is already assigned to anyother Consignment
		 */
		checkMailbagsAlreadyAssigned(consignmentDocumentVO);
		/*
		 * Check if DSN is present in Mailbag/Despatch Level 
		 */
		checkDSNForAcceptedMailAndDespatch(consignmentDocumentVO);	
		
		if (ConsignmentDocumentVO.OPERATION_FLAG_INSERT
				.equals(consignmentDocumentVO.getOperationFlag())) {
			/*
			 * Inserting new Consignment Document Details
			 */
			ConsignmentDocument consignmentDocument = new ConsignmentDocument(
					consignmentDocumentVO);
			consignmentSeqNumber = Integer.valueOf(consignmentDocument
					.getConsignmentDocumentPK().getConsignmentSequenceNumber());
			consignmentDocumentVO
					.setConsignmentSequenceNumber(consignmentSeqNumber
							.intValue());
			/* updating mail Bag with consignment document information */
			new MailController().updateMailBag(consignmentDocumentVO);
		} else if (ConsignmentDocumentVO.OPERATION_FLAG_UPDATE
				.equals(consignmentDocumentVO.getOperationFlag())) {
			ConsignmentDocument consignmentDocument = ConsignmentDocument
					.find(consignmentDocumentVO);			
			consignmentDocument.update(consignmentDocumentVO);
			modifyChildren(consignmentDocumentVO, consignmentDocument);
			consignmentSeqNumber = Integer.valueOf(consignmentDocument
					.getConsignmentDocumentPK().getConsignmentSequenceNumber());
		}
		ConsignmentFilterVO filterVO=new ConsignmentFilterVO();
		filterVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
		filterVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
		filterVO.setPaCode(consignmentDocumentVO.getPaCode());
		log.exiting("DocumentController", "saveConsignmentDocument");
		return ConsignmentDocument
		.findConsignmentDocumentDetailsForHHT(filterVO);
	}
	/**   
	 * @param consignmentDocumentVOs
	 * @throws SystemException  
	 */
		public void saveConsignmentDocumentFromManifest(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
		throws SystemException{
			log.entering("DocumentController", "saveConsignmentDocumentFromManifest"); 
			int value=0;
			for(ConsignmentDocumentVO consignmentDocumentVO :consignmentDocumentVOs){
				try{
					value=saveConsignmentDocument(consignmentDocumentVO);
				}catch (InvalidMailTagFormatException invalidMailTagFormatException) {
					log.log(Log.SEVERE, " InvalidMailTagFormatException ");
				}catch (DuplicateDSNException duplicateDSNException) {
					log.log(Log.SEVERE, " DuplicateDSNException ");
				}catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException){
					log.log(Log.SEVERE, " mailbagAlreadyAcceptedException ");
				}catch (DuplicateMailBagsException ex) {
					log.log(Log.SEVERE, "DuplicateMailBagsException");
				}
			}
			log.exiting("DocumentController", "saveConsignmentDocumentFromManifest");
	}
		/**
		 * 	Method		:	MailController.generateConsignmentReports
		 *	Added by 	:	A-5526 on 24 Jun, 2016 for CRQ-ICRD-103713
		 * 	Used for 	:	generateConsignmentReports
		 *	Parameters	:	@param reportSpec
		 *	Parameters	:	@throws SystemException 
		 **	Parameters	:	@throws ReportGenerationException 
		 *	Return type	: 	Map<String,Object>
		 */
		public Map<String,Object> generateConsignmentReports(ReportSpec reportSpec)
			throws SystemException, ReportGenerationException {
		log.entering("DocumentController", "generateConsignmentReport");
		List<ReportSpec> reportSpecs = new ArrayList<>();
		Map<String, Object> reportDataMap = null;
		Collection<ConsignmentFilterVO> consignmentFilterVOs = null;
		if(reportSpec.getFilterValues().iterator().next() instanceof ConsignmentFilterVO) {
			consignmentFilterVOs = new ArrayList<>();
			consignmentFilterVOs.add((ConsignmentFilterVO) reportSpec.getFilterValues().iterator().next());
		} else {
		consignmentFilterVOs =(Collection<ConsignmentFilterVO>) reportSpec
				.getFilterValues().iterator().next(); 
		}
		ConsignmentDocumentVO consignmentDocumentVO = null;
		ReportSpec newReportSpec = null;
		boolean isBulkDownload = false;
		for(ConsignmentFilterVO consignmentFilterVO:consignmentFilterVOs) {
		isBulkDownload = consignmentFilterVO.isBulkDownload();
		newReportSpec = new ReportSpec();
		BeanHelper.copyProperties(newReportSpec, reportSpec);
		try {
			consignmentDocumentVO = Proxy.getInstance().get(MailOperationsProxy.class).generateConsignmentReportDtls(consignmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
		log.log(Log.FINE, "\n\n\n--consignmentDocumentVO----->",
				consignmentDocumentVO);
		log.log(Log.FINE, "\n\n\n--consignmentFilterVO----->",
				consignmentFilterVO);
		ArrayList<MailInConsignmentVO> consignmentVOs = new ArrayList<>();
		StringBuilder routingInfo = new StringBuilder("");
		String directTransitPort="";
		String destinationPort="";
		StringBuilder flightDetails = new StringBuilder("");
		
		if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null
				&& consignmentDocumentVO.getRoutingInConsignmentVOs().size() > 0) {
			int count=0;
			int noOfRoutes=0;
			if( consignmentDocumentVO
					.getRoutingInConsignmentVOs()!=null &&  !consignmentDocumentVO
					.getRoutingInConsignmentVOs().isEmpty()){
				noOfRoutes=consignmentDocumentVO
						.getRoutingInConsignmentVOs().size();
			}
			for (RoutingInConsignmentVO routingConsignmentVO : consignmentDocumentVO
					.getRoutingInConsignmentVOs()) {
				count++;
				log.log(Log.INFO, "routingInfo inside ", routingInfo.toString());
				routingInfo.append(routingConsignmentVO.getOnwardCarrierCode());
				routingInfo.append(" ");
				routingInfo
						.append(routingConsignmentVO.getOnwardFlightNumber());
				routingInfo.append(" | ");
				String date = "";
				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				fromDate = routingConsignmentVO.getOnwardFlightDate();
				date = fromDate.toDisplayDateOnlyFormat();
				log.log(Log.INFO, "OnwardFlightDate ", date.toUpperCase());
				routingInfo.append(date.toUpperCase());
				routingInfo.append(" | ");
				routingInfo.append(routingConsignmentVO.getPol());
				routingInfo.append(" | ");
				routingInfo.append(routingConsignmentVO.getPou());
				routingInfo.append("; \n");
				if("CN41".equals(consignmentDocumentVO.getType())  ){  
				directTransitPort=routingConsignmentVO.getPol();    
				destinationPort=routingConsignmentVO.getPou(); 
				flightDetails.append(routingConsignmentVO.getOnwardCarrierCode()).append("-").append(routingConsignmentVO.getOnwardFlightNumber());
				if(count<noOfRoutes){
					flightDetails.append("/");
				}
				}
			}
			log.log(Log.INFO, "routingInfo ", routingInfo.toString());
		}
		     
		if("CN41".equals(consignmentDocumentVO.getType())  ){  
			
			if(directTransitPort!=null && !directTransitPort.isEmpty())
				directTransitPort =  new MailController().findAirportDescription(consignmentDocumentVO.getCompanyCode(), directTransitPort);
			if(destinationPort!=null && !destinationPort.isEmpty())
	             destinationPort =  new MailController().findAirportDescription(consignmentDocumentVO.getCompanyCode(), destinationPort);
	             
	             if (directTransitPort != null && destinationPort != null) {
						consignmentDocumentVO.setFlightRoute(new StringBuilder().append(directTransitPort).append("-").append(destinationPort).toString());
					}
	             consignmentDocumentVO.setOperatorOrigin(new MailController().findUpuCodeName(consignmentDocumentVO.getCompanyCode(), consignmentDocumentVO.getPaCode()));
	             if(consignmentDocumentVO.getOperatorOrigin()==null || consignmentDocumentVO.getOperatorOrigin().isEmpty()){
	            	 consignmentDocumentVO.setOperatorOrigin(consignmentDocumentVO.getPaCode());
	             }
	             if(flightDetails!=null){
	            	 consignmentDocumentVO.setFlightDetails(flightDetails.toString());
	             }

		}
		if (consignmentDocumentVO.getMailInConsignmentcollVOs() != null
				&& consignmentDocumentVO.getMailInConsignmentcollVOs().size() > 0) {
			for (MailInConsignmentVO mailConsignmentVO : consignmentDocumentVO
					.getMailInConsignmentcollVOs()) {
				if (!("").equals(mailConsignmentVO.getReceptacleSerialNumber())) {
					log.log(Log.INFO, "getReceptacleSerialNumber ",
							mailConsignmentVO.getReceptacleSerialNumber());
					/*String wt = "";
					if (String.valueOf(mailConsignmentVO.getStatedWeight()) != null) {
						//Modified by A-7794 as part of ICRD-249767
						
						wt = String
								.valueOf(mailConsignmentVO.getStatedWeight().getRoundedSystemValue());
					}
					int len = wt.indexOf(".");
					String wgt = wt;
					if (len > 0 && len < 4) {
						wgt = new StringBuilder(wt.substring(0, len)).append(
								wt.substring(len + 1, wt.length())).toString();
					} else {
						wgt = wt.substring(0, len);
					}
					String stdwgt = wgt;
					if (wgt.length() == 3) {
						stdwgt = new StringBuilder("0").append(wgt).toString();
					}
					if (wgt.length() == 2) {
						stdwgt = new StringBuilder("00").append(wgt).toString();
					}
					if (wgt.length() == 1) {
						stdwgt = new StringBuilder("000").append(wgt)
								.toString();
					}
					//mailConsignmentVO.setStrWeight(stdwgt);*/	//commented by A-8353 for ICRD-260603
				if(mailConsignmentVO.getStatedWeight()!=null)
				mailConsignmentVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,mailConsignmentVO.getStatedWeight().getSystemValue(),0.0,"H"));//added by A-7371,modified by A-8353 for ICRD-260603
				}
				consignmentVOs.add(mailConsignmentVO);
			}
			consignmentFilterVO.setRoutingInfo(routingInfo.toString());
			log.log(Log.INFO, "routingInfo ", routingInfo.toString());
			String date = "";
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			fromDate = consignmentDocumentVO.getConsignmentDate();
			date = fromDate.toDisplayDateOnlyFormat();
			consignmentFilterVO.setConDate(date);
			consignmentFilterVO.setConType(consignmentDocumentVO.getType());
			if (newReportSpec.getReportId() == null)
				{
				newReportSpec.setReportId("RPRMTK094");
				}
		}
		if (consignmentDocumentVO.getAirlineCode() != null) {
			AirlineValidationVO airlineValidationVO = null;
			try {
				airlineValidationVO = new MailController()
						.findAirlineDescription(
								consignmentDocumentVO.getCompanyCode(),
								consignmentDocumentVO.getAirlineCode());
			} catch (SharedProxyException e) {
				e.getMessage();
			}
			if (airlineValidationVO != null) {
				consignmentDocumentVO.setAirlineCode(airlineValidationVO
						.getAirlineName());
			}
		}
		List<ConsignmentDocumentVO> reportConsignmentList = new ArrayList<>();
		reportConsignmentList.add(consignmentDocumentVO);
		newReportSpec.setData(reportConsignmentList);
		newReportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
		reportSpecs.add(newReportSpec); 
		}
		if(isBulkDownload) {
			  List<Map<String, Object>> reportMap = ReportUtils.exportMultipleReports(reportSpecs);
		      List<byte[]> reportBytes = getListReportBytes(reportMap); 
		      byte[] mergedReport = ReportUtils.mergePDFbyteArray(reportBytes);
		      reportDataMap = (Map)reportMap.get(0);
		      reportDataMap.put(ReportConstants.REPORT_DATA, mergedReport);
		      PrintConfigVO printConfigVO = new PrintConfigVO();
			  printConfigVO.setPrintInitiationMode("S");
			  reportDataMap.put(ReportConstants.PRINT_CONFIGURATION,printConfigVO ); 
		} else {
		reportDataMap = ReportAgent.generateReport(reportSpecs.iterator().next()) ;
		}
		return reportDataMap;
	}
		private List<byte[]> getListReportBytes(List<Map<String, Object>> reportDataList) {
			log.log(Log.FINE, "getListReportBytes in ShipmentController ");
			List<byte[]> listReportBytes = new LinkedList<>();
			for(Map<String, Object> reportDataMap : reportDataList){
				listReportBytes.add((byte[]) reportDataMap.get(ReportConstants.REPORT_DATA));
			}
			return listReportBytes;
		}
		/**
		 * 	Method		:	MailController.generateAV7Report
		 *	Added by 	:	A-6986  for CRQ-ICRD-212235
		 * 	Used for 	:	generateAV7Report
		 *	Parameters	:	@param reportSpec
		 *	Parameters	:	@throws SystemException 
		 **	Parameters	:	@throws ReportGenerationException 
		 *	Return type	: 	Map<String,Object>
		 */
		public Map<String,Object> generateAV7Report(ReportSpec reportSpec)
			throws SystemException, ReportGenerationException {
				log.entering("DocumentController","generateAV7Report");
				
				//int totalSacks = 0;
				//Measure totalWeight = null;
				Measure totalLetterWeight = null;
				Measure totalParcelWeight = null;
				ConsignmentFilterVO consignmentFilterVO =
						(ConsignmentFilterVO)reportSpec.getFilterValues()
	   														.iterator().next();
                
				ConsignmentDocumentVO consignmentDocumentVO = null;
				try {
					consignmentDocumentVO = Proxy.getInstance().get(MailOperationsProxy.class).generateConsignmentReportDtls(consignmentFilterVO);
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage(),e);
				}
				
			//	ArrayList<ConsignmentDocumentVO> consignmentVOs = new ArrayList<ConsignmentDocumentVO>();
				//StringBuilder routingInfo = new StringBuilder("");
				ArrayList<MailInConsignmentVO> consignmentVOs=new ArrayList<MailInConsignmentVO>();
				if(consignmentDocumentVO.getMailInConsignmentcollVOs()!=null &&
						consignmentDocumentVO.getMailInConsignmentcollVOs().size()>0){
					
					for(MailInConsignmentVO mailConsignmentVO:
										consignmentDocumentVO.getMailInConsignmentcollVOs()){
						if(mailConsignmentVO.getTotalLetterBags()>0 ||
								mailConsignmentVO.getTotalParcelBags()>0){
							consignmentDocumentVO.setTotalSacks(consignmentDocumentVO.getTotalSacks() +
									mailConsignmentVO.getTotalLetterBags() +
									mailConsignmentVO.getTotalParcelBags());
						}else{
							consignmentDocumentVO.setTotalSacks(0);
						}
						//Modified for ICRD-336007 starts
						/*if(mailConsignmentVO.getTotalLetterWeight().getRoundedSystemValue() >0
								|| mailConsignmentVO.getTotalParcelWeight().getRoundedSystemValue() > 0){
							
							try{
							totalWeight = Measure.addMeasureValues(
									mailConsignmentVO.getTotalLetterWeight(),
									mailConsignmentVO.getTotalParcelWeight());
							consignmentDocumentVO.setTotalWeight(Measure.addMeasureValues(
									consignmentDocumentVO.getTotalWeight(),totalWeight));
							}catch (UnitException e) {
								
								throw new SystemException(e.getErrorCode());
							}
						}else{
							consignmentDocumentVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
						}*/
						//Total Letter weight
						if(mailConsignmentVO.getTotalLetterWeight()!= null 
								&& mailConsignmentVO.getTotalLetterWeight().getRoundedSystemValue() > 0){
							try{
								consignmentDocumentVO.setTotalLetterWeight(Measure.addMeasureValues(
										consignmentDocumentVO.getTotalLetterWeight(),mailConsignmentVO.getTotalLetterWeight()));
							}catch (UnitException e) {
								throw new SystemException(e.getErrorCode());
							}
						}/*else{
							consignmentDocumentVO.setTotalLetterWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
						}*/
					 //total parcel weight
						if(mailConsignmentVO.getTotalParcelWeight()!= null 
								&& mailConsignmentVO.getTotalParcelWeight().getRoundedSystemValue() > 0){
							try{
								consignmentDocumentVO.setTotalParcelWeight(Measure.addMeasureValues(
										consignmentDocumentVO.getTotalParcelWeight(),mailConsignmentVO.getTotalParcelWeight()));
							}catch (UnitException e) {
								throw new SystemException(e.getErrorCode());
							}
						}/*else{
							consignmentDocumentVO.setTotalParcelWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
						}*/
						//Modified for ICRD-336007 ends
							//consignmentVOs.add(mailConsignmentVO);
					}
			
					String date = "";
					LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					 fromDate=consignmentDocumentVO.getConsignmentDate();
					date=fromDate.toDisplayDateOnlyFormat();
					
					consignmentFilterVO.setConDate(date);
					consignmentFilterVO.setConType(consignmentDocumentVO.getType());
					consignmentFilterVO.setConsignmentDestination(
							consignmentDocumentVO.getDestination());//destination airport code,city,country
					String flight = consignmentDocumentVO.getFlightDetails();
					consignmentFilterVO.setFltNumber(flight);
					
					//Added for ICRD-335944 starts
					StringBuilder routingInfo = new StringBuilder("");
					if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null
							&& consignmentDocumentVO.getRoutingInConsignmentVOs().size() > 0) {
						for (RoutingInConsignmentVO routingConsignmentVO : consignmentDocumentVO
								.getRoutingInConsignmentVOs()) {
							log.log(Log.INFO, "routingInfo inside ", routingInfo.toString());
							routingInfo.append(routingConsignmentVO.getPol());
							routingInfo.append(" / ");
							routingInfo.append(routingConsignmentVO.getOnwardCarrierCode());
							routingInfo.append(" ");
							routingInfo
									.append(routingConsignmentVO.getOnwardFlightNumber());
							routingInfo.append(" \n");
						}
						log.log(Log.INFO, "routingInfo ", routingInfo.toString());
					}
					consignmentDocumentVO.setFlightRoute(routingInfo.toString());
					//Added for ICRD-335944 ends
					if (reportSpec.getReportId() == null) {
					
						reportSpec.setReportId(AV7_REPORT_ID);
					}
					
					ReportMetaData parameterMetaData = new ReportMetaData();
					parameterMetaData.setFieldNames(new String[] { 
							"consignmentNumber","conDate","destination",
							"FlightNo","route","TotalSacks","TotalWtByLetterClasses","TotalWtByParcelClasses","TotalLetterWtDecimal","TotalParcelWtDecimal"});
					
					
					reportSpec.addParameterMetaData(parameterMetaData);
					reportSpec.addParameter(consignmentDocumentVO);
					
					ReportMetaData reportMetaData = new ReportMetaData();
					reportMetaData.setColumnNames(new String[] { "ORIGINOFFICE",
					"DESTNOFFICE", "LCAONO","CPNO", "LCAOWT","LCAOWTDEC","CPWT","CPWTDEC","REMARKS"});
					
					reportMetaData.setFieldNames(new String[] { "originOfficeOfExchange",
					"destinationExchangeOffice", "TotalLetterBags","TotalParcelBags","TotalLetterWeight","TotalLetterWeightDec",
					"TotalParcelWeight","TotalParcelWeightDec","remarks"});
					
					reportSpec.setReportMetaData(reportMetaData);
					//reportSpec.setData(consignmentVOs);
					List<ConsignmentDocumentVO> reportConsignmentList = new ArrayList<ConsignmentDocumentVO>();
					reportConsignmentList.add(consignmentDocumentVO);
					reportSpec.setData(reportConsignmentList);
				}
				return ReportAgent.generateReport(reportSpec);

		}
		/***
		 * @author A-7794
		 * @param fileUploadFilterVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException 
		 */
		public String processMailDataFromExcel(FileUploadFilterVO fileUploadFilterVO) throws SystemException, PersistenceException {
			log.entering("DocumentController", "processMailDataFromExcel");
			String processStatus = null;
			Collection<ConsignmentDocumentVO> documentVOs = null;
			processStatus = new MailController().processMailDataFromExcel(fileUploadFilterVO);
			if("OK".equals(processStatus)){
				documentVOs = new MailController().fetchMailDataForOfflineUpload(fileUploadFilterVO.getCompanyCode(), fileUploadFilterVO.getFileType());
				if(null != documentVOs){
					for(ConsignmentDocumentVO consgnVO :documentVOs ){
						if(processStatus.equals("NOTOK")){
							break;
						}
						for(MailInConsignmentVO mailVO : consgnVO.getMailInConsignment()){
							if((mailVO.getMailId() != null && mailVO.getMailId().equals(mailVO.getDsn())) && (mailVO.getConsignmentDate() != null && mailVO.getConsignmentDate().equals(mailVO.getReqDeliveryTime()))){
								fileUploadFilterVO.setStatus("Mailbag already exist in system");
								saveFileUploadError(fileUploadFilterVO);
								processStatus ="NOTOK";
								break;
							}
						}
					}
					for(ConsignmentDocumentVO consgnVO :documentVOs ){
						if(processStatus.equals("NOTOK")){
							break;
						}
						if(processStatus.equals("OK")){
							constructConsignmentDocumentVO(consgnVO);
							try {
								saveConsignmentDocument(consgnVO);
							} catch (MailbagAlreadyAcceptedException
									| InvalidMailTagFormatException
									| DuplicateDSNException | DuplicateMailBagsException e) {
								processStatus ="NOTOK";
								fileUploadFilterVO.setStatus(e.getMessage());
								saveFileUploadError(fileUploadFilterVO);
								break;
							} 
						}
					}

				}
			}
				new MailController().removeDataFromTempTable(fileUploadFilterVO);
			MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER_BEAN);
			mailController.insertHistoryDetailsForExcelUpload(documentVOs);
			return processStatus;
		}
		/***
		 * @author A-7794
		 * @param fileUploadFilterVO
		 * @throws SystemException
		 */
		private void saveFileUploadError(FileUploadFilterVO fileUploadFilterVO) throws SystemException {
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			 FileUploadErrorLogVO errorLogVO = new FileUploadErrorLogVO();
			 Collection<FileUploadErrorLogVO> errorLogVOs=new ArrayList<FileUploadErrorLogVO>();
	         errorLogVO.setCompanyCode(fileUploadFilterVO.getCompanyCode());
	         errorLogVO.setProcessIdentifier(fileUploadFilterVO.getProcessIdentifier());
	         errorLogVO.setFileName(fileUploadFilterVO.getFileName());
	         errorLogVO.setFileType(fileUploadFilterVO.getFileType());
	         errorLogVO.setLineNumber(0);
	         errorLogVO.setLineContent(fileUploadFilterVO.getFileName());
	         errorLogVO.setErrorCode(fileUploadFilterVO.getStatus());
	         errorLogVO.setUpdatedUser(logonAttributes.getUserId());
	         LocalDate date = new LocalDate(LocalDate.NO_STATION,
	 				Location.NONE, false);
	         errorLogVO.setUpdatedTime(date);
	         errorLogVOs.add(errorLogVO);
	         if (!errorLogVOs.isEmpty()) {
	             new SharedDefaultsProxy().saveFileUploadExceptions(errorLogVOs);
	           }
		}
		/***
		 * @author A-7794
		 * @param documentVOs
		 * @throws SystemException
		 */
		public void constructConsignmentDocumentVO(ConsignmentDocumentVO documentVO) throws SystemException{
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			AirportVO airportVO = new SharedAreaProxy().findAirportDetails(documentVO.getCompanyCode(), logonAttributes.getAirportCode());
			log.log(Log.FINE, "AIRPORT VO", airportVO);
			String id = new StringBuilder().append(airportVO.getCountryCode()).append(airportVO.getCityCode()).toString();
			Criterion criterion = KeyUtils.getCriterion(documentVO.getCompanyCode(),CSGDOCNUM_GEN_KEY, id);
			String key = KeyUtils.getKey(criterion);
			String str = "";
			double totalwgt =0.0;
			
			int count =0;
			for(int i=0;i<(7-key.length());i++){
				if (count==0){
					str="0";
					count=1;
				}else{
					str =  new StringBuilder().append(str).append("0").toString();
				}
			}
			String conDocNo = new StringBuilder().append(id).append("S").append(str).append(key).toString();
			documentVO.setConsignmentNumber(conDocNo);
			documentVO.setOperation("O");
			documentVO.setAirportCode(logonAttributes.getAirportCode());
			Collection<RoutingInConsignmentVO> routingVOs = documentVO.getRoutingInConsignmentVOs();
			for(MailInConsignmentVO mailVO :documentVO.getMailInConsignment()){
				double systemWt =0.0;
				if(routingVOs == null){
					routingVOs = new ArrayList<RoutingInConsignmentVO>();
					RoutingInConsignmentVO routingVO = new RoutingInConsignmentVO();
					routingVO.setPol(mailVO.getMailOrigin());
					routingVO.setPou(mailVO.getMailDestination());
					routingVO.setCompanyCode(mailVO.getCompanyCode());
					routingVO.setOnwardCarrierCode(documentVO.getCarrierCode());
					routingVO.setConsignmentNumber(conDocNo);
					routingVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
					routingVO.setConsignmentSequenceNumber(documentVO.getConsignmentSequenceNumber());
					routingVO.setOriginOfficeOfExchange(mailVO.getDsn().substring(0,6));
					routingVO.setDestinationOfficeOfExchange(mailVO.getDsn().substring(6,12));
					routingVO.setOnwardFlightNumber("0000");
					routingVO.setOnwardCarrierSeqNum(MailConstantsVO.DESTN_FLT);
					routingVO.setRoutingSerialNumber(MailConstantsVO.ZERO);
					routingVO.setSegmentSerialNumber(MailConstantsVO.ZERO);
					routingVO.setNoOfPieces(1);
					routingVO.setPaCode(documentVO.getPaCode());
					if(mailVO.getReqDeliveryTime() != null){
						routingVO.setOnwardFlightDate(mailVO.getReqDeliveryTime());
					}
					routingVOs.add(routingVO);
					documentVO.setRoutingInConsignmentVOs(routingVOs);
					documentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
				}else{
					for(RoutingInConsignmentVO vo :routingVOs){
						vo.setPol(mailVO.getMailOrigin());
						vo.setPou(mailVO.getMailDestination());
						vo.setCompanyCode(mailVO.getCompanyCode());
						vo.setConsignmentNumber(conDocNo);
						documentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
					}
				}
			mailVO.setMailSource("FILE");
			mailVO.setConsignmentNumber(conDocNo);
			mailVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_INSERT);
			mailVO.setMailId(mailVO.getDsn());//setting mailBag ID got from excel
			
			if(mailVO.getMailId() != null){
			mailVO.setDsn(mailVO.getMailId().substring(16,20));
			mailVO.setYear(Integer.parseInt(mailVO.getMailId().substring(15,16)));
			mailVO.setMailSubclass(mailVO.getMailId().substring(13,15));
			mailVO.setReceptacleSerialNumber(mailVO.getMailId().substring(20,23));
			mailVO.setHighestNumberedReceptacle(mailVO.getMailId().substring(23,24));
			mailVO.setRegisteredOrInsuredIndicator(mailVO.getMailId().substring(24,25));
			mailVO.setStatedBags(1);
			mailVO.setMailClass(mailVO.getMailId().substring(13,14));
			systemWt=(Double.parseDouble(mailVO.getMailId().substring(25,29)))/10;
			}
			if(mailVO.getReqDeliveryTime() != null){
				mailVO.setConsignmentDate(mailVO.getReqDeliveryTime());
			}
			
			Measure wgt=new Measure(UnitConstants.WEIGHT,0.0,systemWt,UnitConstants.WEIGHT_UNIT_KILOGRAM);//IASCB-30981
			mailVO.setStatedWeight(wgt);
			
			totalwgt = totalwgt+systemWt;
			}
			documentVO.setStatedWeight(new Measure(UnitConstants.WEIGHT,totalwgt));
			Page<MailInConsignmentVO> mailconsgnVos =new Page<MailInConsignmentVO>((ArrayList<MailInConsignmentVO>)documentVO.getMailInConsignment(), 0, 0, 0, 0, 0, false);
			documentVO.setMailInConsignmentVOs(mailconsgnVos);
		}
		/**
		 * @author A-8353
		 * @param unitType
		 * @param fromUnit
		 * @param toUnit
		 * @param fromValue
		 * @return
		 */
		public double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
			UnitConversionNewVO unitConversionVO= null;
			try {
				unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			double convertedValue = unitConversionVO.getToValue();
			return convertedValue;
		}

	public Integer updateConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO)
			throws SystemException, MailbagAlreadyAcceptedException,
			InvalidMailTagFormatException, DuplicateDSNException, DuplicateMailBagsException {
		log.entering("DocumentController", "updateConsignmentDocument");
		Integer consignmentSeqNumber = null;
		String triggerPoint = "";
		String consignmentDate=null;
		String latestConsignmentDate=null;
		log.log(Log.FINE, " OPERATION FLAG :", consignmentDocumentVO.getOperationFlag());
		LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
		consignmentDocumentVO.setLastUpdateUser(logonVO.getUserId());
		try {
			triggerPoint = new MailController().findSystemParameterValue(IMPORT_TRIGGERPOINT);
		} catch (SystemException e) {
			log.log(Log.FINE, e.getMessage());
		} catch (Exception e) {
			log.log(Log.FINE, e.getMessage());
		}
		/*
		 * Validating the Mailbag
		 */
		validateMailBags((Collection<MailInConsignmentVO>) consignmentDocumentVO.getMailInConsignmentVOs(), consignmentDocumentVO.isScanned());
		/*
		 * Check if DSN is present in Mailbag/Despatch Level
		 */
		checkDSNForAcceptedMailAndDespatch(consignmentDocumentVO);
		//Added as part of CRQ ICRD-103713 by A-5526 starts
		new MailController().saveConsignmentDetails(consignmentDocumentVO);
		//Added as part of CRQ ICRD-103713 by A-5526 ends
		if (consignmentDocumentVO.getMailInConsignmentVOs() != null && consignmentDocumentVO.getMailInConsignmentVOs().size() > 0) {

			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			consignmentFilterVO.setPaCode(consignmentDocumentVO.getPaCode());
			consignmentFilterVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
			ConsignmentDocumentVO consignmentDocumentVOTofind = new DocumentController()
					.findConsignmentDocumentDetails(consignmentFilterVO);

			if (consignmentDocumentVOTofind != null) {
				consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_NO);
				consignmentFilterVO.setPageNumber(0);
				consignmentDocumentVOTofind = new DocumentController()
						.findConsignmentDocumentDetails(consignmentFilterVO);

				ConsignmentDocument consignmentDocument = ConsignmentDocument
						.find(consignmentDocumentVOTofind);


				if(consignmentDocument!=null){
					//IASCB-45762 beg
					if(consignmentDocument.getConsignmentDate()!=null && consignmentDocumentVO.getConsignmentDate()!=null){
						
						consignmentDate=new LocalDate(LocalDate.NO_STATION, Location.NONE, consignmentDocument.getConsignmentDate(), true).toDisplayFormat();
						latestConsignmentDate=consignmentDocumentVO.getConsignmentDate().toDisplayFormat();
						
						//if(!consignmentDate.equals(latestConsignmentDate)){
							 PostalAdministrationVO postalAdministrationVO =new MailController().findPACode(consignmentDocument.getConsignmentDocumentPK().getCompanyCode(), consignmentDocument.getConsignmentDocumentPK().getPaCode());
							consignmentDocument.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toCalendar());
							
							if(consignmentDocument.getMailsInConsignments()!=null &&! consignmentDocument.getMailsInConsignments().isEmpty()){
								
								consignmentDocument.getMailsInConsignments().forEach(mailInCsg->{
									
									MailbagVO  mailbagVO= new MailbagVO();
									mailbagVO.setCompanyCode(mailInCsg.getMailInConsignmentPK().getCompanyCode());
									mailbagVO.setMailSequenceNumber(mailInCsg.getMailInConsignmentPK().getMailSequenceNumber());
									mailbagVO.setConsignmentDate(consignmentDocumentVO.getConsignmentDate());
									try {
										new Mailbag().updateDespatchDate(mailbagVO,postalAdministrationVO.getDupMailbagPeriod());
									} catch (SystemException e) {
										e.printStackTrace();
									}
								});
							}
							
						//}
					}//IASCB-45762 end

				for(MailInConsignmentVO mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()){
					mailInConsignmentVO.setConsignmentSequenceNumber(consignmentDocument.getConsignmentDocumentPK().getConsignmentSequenceNumber());
					mailInConsignmentVO.setPaCode(consignmentDocument.getConsignmentDocumentPK().getPaCode());
					
				}
				modifyMailInConsignment(consignmentDocumentVO.getMailInConsignmentVOs(),consignmentDocument);
				}
			}
		}
		if(isMraImportRequired(consignmentDocumentVO,triggerPoint) ){  
			importConsignmentDataToMra(consignmentDocumentVO);
		}
		log.exiting("DocumentController", "updateConsignmentDocument");
		return consignmentSeqNumber;
	}
	/**
	 * 
	 * 	Method		:	DocumentController.modifyExistingMailbagInConsignment
	 *	Added by 	:	A-6245 on 12-Oct-2020
	 * 	Used for 	:	Modify Consignment tables if same mailbag present in another consignment
	 *	Parameters	:	@param consignmentDocumentVO 
	 *	Return type	: 	void
	 */
	public void modifyExistingMailInConsignment(MailInConsignmentVO mailInConsignmentVO) {
		log.entering("DocumentController", "modifyExistingMailInConsignment");
		if (mailInConsignmentVO.getMailSource() != null
				&& !MailConstantsVO.CARDIT_PROCESS.equalsIgnoreCase(mailInConsignmentVO.getMailSource())) {
			MailInConsignmentVO existingMailInConsignmentVO = null;
			double statedWeight = 0.0;
			try {
				existingMailInConsignmentVO = MailInConsignment.findConsignmentDetailsForMailbag(
						mailInConsignmentVO.getCompanyCode(), mailInConsignmentVO.getMailId(), null);
			} catch (SystemException e) {
				log.log(Log.FINE, "Consignment Not present, no need to modify");
			}
			if (mailInConsignmentVO.getStatedWeight() != null) {
				statedWeight = mailInConsignmentVO.getStatedWeight().getSystemValue();
			}
			if (existingMailInConsignmentVO != null) {
				ConsignmentDocument consignmentDocument = null;
				MailInConsignment mailInConsignment = null;
				ConsignmentDocumentVO consignmentDocumentVO = null;
				try {
					mailInConsignment = MailInConsignment.find(existingMailInConsignmentVO);
					consignmentDocumentVO = new ConsignmentDocumentVO();
					consignmentDocumentVO.setCompanyCode(mailInConsignment.getMailInConsignmentPK().getCompanyCode());
					consignmentDocumentVO
							.setConsignmentNumber(mailInConsignment.getMailInConsignmentPK().getConsignmentNumber());
					consignmentDocumentVO.setConsignmentSequenceNumber(
							mailInConsignment.getMailInConsignmentPK().getConsignmentSequenceNumber());
					consignmentDocumentVO.setPaCode(mailInConsignment.getMailInConsignmentPK().getPaCode());
					consignmentDocument = ConsignmentDocument.find(consignmentDocumentVO);
					consignmentDocument.setStatedBags(consignmentDocument.getStatedBags() - 1);
					consignmentDocument.setStatedWeight(consignmentDocument.getStatedWeight() - statedWeight);
					mailInConsignment.remove();
					consignmentDocument.getMailsInConsignments().remove(mailInConsignment);
				} catch (FinderException | SystemException e) {
					log.log(Log.FINE, "Consignment Not present, no need to modify");
				}
			}
		}
		log.exiting("DocumentController", "modifyExistingMailInConsignment");
	}  

	public HashMap<String, Object> generateConsignmentSecurityReport(ReportSpec reportSpec) throws PersistenceException, SystemException {
		Collection<ConsignmentDocumentVO>ConsignmentDocumentVOs = new ArrayList<>();
		ConsignmentScreeningVO consignmentScreeningVO = (ConsignmentScreeningVO) reportSpec.getFilterValues().iterator().next();	
		Collection<ConsignmentScreeningVO> carditVO = new ArrayList<>();
		Collection<ConsignmentScreeningVO> screenVO = new ArrayList<>();

		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setConsignmentNumber(consignmentScreeningVO.getConsignmentNumber());
		consignmentFilterVO.setPaCode(consignmentScreeningVO.getPaCode());

		ConsignmentDocumentVO consignmentDocumentVO=null;
		try {
				consignmentDocumentVO= Proxy.getInstance().get(MailOperationsProxy.class).generateConsignmentSecurityReportDtls(consignmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}


		if(consignmentDocumentVO!=null){
		for(ConsignmentScreeningVO consignment : consignmentDocumentVO.getConsignementScreeningVOs()){
			if((MailConstantsVO.SCREEN).equals(consignment.getSource())){
				screenVO.add(consignment);
			}
			if((MailConstantsVO.MESSAGETYPE_CARDIT).equals(consignment.getSource())){
				carditVO.add(consignment);
			}        
		} 
		if(!screenVO.isEmpty() && !carditVO.isEmpty()){
			consignmentDocumentVO.setConsignementScreeningVOs(screenVO);
		}
		consignmentDocumentVO.setOneTimes(findOneTimeValue(consignmentDocumentVO.getCompanyCode(),MailConstantsVO.MAIL_CATEGORY));
		
		
		ConsignmentDocumentVOs.add(consignmentDocumentVO);
		reportSpec.setData(ConsignmentDocumentVOs);
		return ReportAgent.generateReport(reportSpec);
		}else{
		return null;
		}
	} 
	/**
	 * @author A-4809
	 * findOneTimeValue
	 * @param companyCode
	 * @param onetimeCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeValue(String companyCode,String onetimeCode){
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<String> oneTimeList = new ArrayList<>();
		oneTimeList.add(onetimeCode);
		try {
			oneTimes = new SharedDefaultsProxy().findOneTimeValues(companyCode,
					oneTimeList);
		} catch (ProxyException | SystemException e) {
			log.log(Log.INFO, e.getMessage());
		}
		return oneTimes;
	}

	public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException {
		log.entering("DocumentController", "generateConsignmentSecurityReportDtls");
		return ConsignmentScreeningDetails
				.generateSecurityReport(consignmentFilterVO.getConsignmentNumber(),consignmentFilterVO.getPaCode());
	}
	
	/**
	 * 	Method		:	DocumentController.generateConsignmentSummaryReports
	 *	Added by 	:	A-9084 on 12-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return 
	 *	Return type	: 	Map
	 */
	
	public Map<String, Object> generateConsignmentSummaryReports(ReportSpec reportSpec) throws PersistenceException, SystemException, ReportGenerationException { 
			log.entering("DocumentController","generateConsignmentReport");
			
			List<ReportSpec> reportSpecs = new ArrayList<>();
			Map<String, Object> reportDataMap = null;
			Collection<ConsignmentFilterVO> consignmentFilterVOs = null;
			if(reportSpec.getFilterValues().iterator().next() instanceof ConsignmentFilterVO) {
				consignmentFilterVOs = new ArrayList<>();
				consignmentFilterVOs.add((ConsignmentFilterVO) reportSpec.getFilterValues().iterator().next());
			} else {
			consignmentFilterVOs =(Collection<ConsignmentFilterVO>) reportSpec
					.getFilterValues().iterator().next(); 
			}
			ConsignmentDocumentVO consignmentDocumentVO=null;
			ReportSpec newReportSpec = null;
			boolean isBulkDownload = false;
			
			for(ConsignmentFilterVO consignmentFilterVO:consignmentFilterVOs) {
			isBulkDownload = consignmentFilterVO.isBulkDownload();
			newReportSpec = new ReportSpec();
			BeanHelper.copyProperties(newReportSpec, reportSpec);
			
			Collection<ConsignmentDocumentVO>ConsignmentDocumentVOs = new ArrayList<>();
			try {
				consignmentDocumentVO = Proxy.getInstance().get(MailOperationsProxy.class).generateConsignmentSummaryReportDtls(consignmentFilterVO);
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage(),e);
			}

			Collection<String> flightNumbers = new ArrayList<>();
			Collection<String> flightDate = new ArrayList<>();
			Collection<String> pou = new ArrayList<>();
			Collection<String> pol = new ArrayList<>();
			StringBuilder flightDetails = new StringBuilder("");
			StringBuilder flightDates = new StringBuilder("");
			StringBuilder airportOfTransShipment = new StringBuilder("");
			String pointOfUnloading = null;
			
			int lcCount = 0;
			int cpCount = 0;
			int emsCount = 0;
			int totalPieces=0;
			Map<String,String> airportNames = new HashMap<String,String>();
			if (consignmentDocumentVO.getRoutingInConsignmentVOs() != null && consignmentDocumentVO.getRoutingInConsignmentVOs().size() > 0) {
				int count=1;
				int countDate=1;
				int airportCount=1;
				if(consignmentDocumentVO.getRoutingInConsignmentVOs()!=null &&  !consignmentDocumentVO.getRoutingInConsignmentVOs().isEmpty()){
					for (RoutingInConsignmentVO routingConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()) {
					if(!flightNumbers.contains(routingConsignmentVO.getOnwardFlightNumber()))	{
					if((count != 1)){
						flightDetails.append("/");
					}
					count++;
					flightDetails.append(routingConsignmentVO.getOnwardCarrierCode()).append("-").append(routingConsignmentVO.getOnwardFlightNumber());
					flightNumbers.add(routingConsignmentVO.getOnwardFlightNumber());
					}
					
					if(!flightDate.contains(routingConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat()))	{
						if((countDate != 1)){
							flightDates.append(",");
						}	
					countDate++;
					flightDates.append(routingConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
					flightDate.add(routingConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
					}
					
					pol.add(routingConsignmentVO.getPol());
					pou.add(routingConsignmentVO.getPou());
					pointOfUnloading = routingConsignmentVO.getPou();
					if(routingConsignmentVO.getPol() != null && !airportNames.containsKey(routingConsignmentVO.getPol())){
						airportNames.put(routingConsignmentVO.getPol(), routingConsignmentVO.getPolAirportName());
					}
					if(routingConsignmentVO.getPou() != null && !airportNames.containsKey(routingConsignmentVO.getPou())){
						airportNames.put(routingConsignmentVO.getPou(), routingConsignmentVO.getPouAirportName());
					}
				}	
					
					for(String loading : pol){
						for(String unloading : pou){
							if(loading.equals(unloading)){
								if((airportCount != 1)){
									airportOfTransShipment.append("-");
								}
								airportCount++;
								airportOfTransShipment.append(unloading);
							}
						}
					}
			    }
		    }		
			if(airportNames != null && airportNames.size() > 0 ){
				String[] airports = airportOfTransShipment.toString().split("-");
			if(airportOfTransShipment != null && airportOfTransShipment.toString().trim().length() > 0 ){
				if(airports != null && airports.length == 1 ){
					consignmentDocumentVO.setTranAirportName(airportNames.get(airports[0]));
				}
			}
			if(pointOfUnloading != null && airportNames.containsKey(pointOfUnloading)){
				consignmentDocumentVO.setPouName(airportNames.get(pointOfUnloading));
			}
			}
			if(consignmentDocumentVO.getMailInConsignmentcollVOs()!= null && !consignmentDocumentVO.getMailInConsignmentcollVOs().isEmpty()){
				for(MailInConsignmentVO mailInconsign : consignmentDocumentVO.getMailInConsignmentcollVOs()){
					
					if(MRAConstantsVO.MAILSUBCLASSGROUP_LC.equals(mailInconsign.getMailSubClassGroup())){
						lcCount = mailInconsign.getStatedBags();
					}
					if(MRAConstantsVO.MAILSUBCLASSGROUP_CP.equals(mailInconsign.getMailSubClassGroup())){
						cpCount = mailInconsign.getStatedBags();
					}
					if(MRAConstantsVO.MAILSUBCLASSGROUP_EMS.equals(mailInconsign.getMailSubClassGroup()) || MRAConstantsVO.MAILSUBCLASSGROUP_SV.equals(mailInconsign.getMailSubClassGroup())){
						emsCount =  mailInconsign.getStatedBags();
					}
				}
				totalPieces = lcCount+cpCount+emsCount;
			}
			consignmentDocumentVO.setFlightDetails(flightDetails.toString());
			consignmentDocumentVO.setFlightDates(flightDates.toString());
			consignmentDocumentVO.setSubClassLC(lcCount);
			consignmentDocumentVO.setSubClassCP(cpCount);
			consignmentDocumentVO.setSubClassEMS(emsCount);
			consignmentDocumentVO.setPou(pointOfUnloading);
			consignmentDocumentVO.setAirportOftransShipment(airportOfTransShipment.toString());
			consignmentDocumentVO.setStatedBags(totalPieces);
			ConsignmentDocumentVOs.add(consignmentDocumentVO);
			newReportSpec.setData(ConsignmentDocumentVOs);
			newReportSpec.addSubReportData(ConsignmentDocumentVOs);
			newReportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
			reportSpecs.add(newReportSpec); 
			}
			if(isBulkDownload) {
				  List<Map<String, Object>> reportMap = ReportUtils.exportMultipleReports(reportSpecs);
			      List<byte[]> reportBytes = getListReportBytes(reportMap); 
			      byte[] mergedReport = ReportUtils.mergePDFbyteArray(reportBytes);
			      reportDataMap = (Map)reportMap.get(0);
			      reportDataMap.put(ReportConstants.REPORT_DATA, mergedReport);
			      PrintConfigVO printConfigVO = new PrintConfigVO();
				  printConfigVO.setPrintInitiationMode("S");
				  reportDataMap.put(ReportConstants.PRINT_CONFIGURATION,printConfigVO ); 
			} else {
			reportDataMap = ReportAgent.generateReport(reportSpecs.iterator().next()) ;
			}
			
			
			return reportDataMap;
	}


	/**
	 * 	Method		:	DocumentController.generateConsignmentSummaryReportDtls
	 *	Added by 	:	A-9084 on 12-Nov-2020
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return 
	 *	Return type	: 	ConsignmentDocumentVO
	 * @throws SystemException 
	 * @throws PersistenceException 
	 */
	public ConsignmentDocumentVO generateConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException, PersistenceException {
		log.entering("DocumentController", "generateConsignmentReport");
		return ConsignmentDocument.generateConsignmentSummaryReport(consignmentFilterVO);
	}
	/**
	 * 
	 * 	Method		:	DocumentController.saveUploadedConsignmentData
	 *	Added by 	:	A-6245 on 22-Dec-2020
	 * 	Used for 	:	IASCB-81526
	 *	Parameters	:	@param consignmentDocumentVOs
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingBusinessException 
	 *	Return type	: 	void
	 */
	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
			throws SystemException, MailTrackingBusinessException {
		SaveConsignmentUploadFeature saveConsignmentUploadFeature =(SaveConsignmentUploadFeature) SpringAdapter.getInstance().getBean("mail.operations.saveconsignmentuploadfeature");
		for (ConsignmentDocumentVO consignmentDocumentVO : consignmentDocumentVOs) {
			try {
				saveConsignmentUploadFeature.execute(consignmentDocumentVO);
			} catch (BusinessException ex) {
				throw new MailTrackingBusinessException(ex);
			}
		}
	}
	/**
	 * 
	 * 	Method		:	DocumentController.generateCN46ConsignmentReport
	 *	Added by 	:	A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ReportGenerationException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	Map<String,Object>
	 */
	public Map<String,Object> generateCN46ConsignmentReport(ReportSpec reportSpec)
			throws SystemException, ReportGenerationException, PersistenceException {
		log.entering(DOCUMENT_CONTROLLER, "generateCN46ConsignmentReport");
		List<ReportSpec> reportSpecs = new ArrayList<>();
		Map<String, Object> reportDataMap = null;
		Collection<ConsignmentFilterVO> consignmentFilterVOs = null;
		consignmentFilterVOs =(Collection<ConsignmentFilterVO>) reportSpec
				.getFilterValues().iterator().next(); 
		ReportSpec newReportSpec = null;
		boolean isBulkDownload = false;
		ConsignmentFilterVO consignmentFilterVO = consignmentFilterVOs.iterator().next();
		isBulkDownload = consignmentFilterVO.isBulkDownload();
		Collection<ConsignmentDocumentVO>manifestConsignmentDocumentVOs = null;
		try {
			manifestConsignmentDocumentVOs = Proxy.getInstance().get(MailOperationsProxy.class).generateCN46ConsignmentReportDtls(consignmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
		
		
		
		for(ConsignmentDocumentVO consignmentDocumentVO:manifestConsignmentDocumentVOs) {
		newReportSpec = new ReportSpec();
		BeanHelper.copyProperties(newReportSpec, reportSpec);
		ArrayList<MailInConsignmentVO> consignmentVOs = new ArrayList<>();
		StringBuilder routingInfo = new StringBuilder("");

		if (consignmentDocumentVO.getMailInConsignmentcollVOs() != null
				&& !consignmentDocumentVO.getMailInConsignmentcollVOs().isEmpty()) {
			for (MailInConsignmentVO mailConsignmentVO : consignmentDocumentVO
					.getMailInConsignmentcollVOs()) {
				if(mailConsignmentVO.getStatedWeight()!=null) {
				mailConsignmentVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,mailConsignmentVO.getStatedWeight().getSystemValue(),0.0,"H"));//added by A-7371,modified by A-8353 for ICRD-260603
				}
				consignmentVOs.add(mailConsignmentVO);
			}
			String date = "";
			LocalDate fromDate = consignmentDocumentVO.getConsignmentDate();
			date = fromDate.toDisplayDateOnlyFormat();
			consignmentFilterVO.setConDate(date);
			consignmentFilterVO.setConType(consignmentDocumentVO.getType());
		}
		if (consignmentDocumentVO.getAirlineCode() != null) {
			AirlineValidationVO airlineValidationVO = null;
			try {
				airlineValidationVO = Proxy.getInstance().get(SharedAirlineProxy.class)
						.validateAlphaCode(consignmentDocumentVO.getCompanyCode(),
								consignmentDocumentVO.getAirlineCode());
			} catch (SharedProxyException e) {
				log.log(Log.FINE,e);
			}
			if (airlineValidationVO != null) {
				consignmentDocumentVO.setAirlineCode(airlineValidationVO
						.getAirlineName());
			}
		}
		List<ConsignmentDocumentVO> reportConsignmentList = new ArrayList<>();
		reportConsignmentList.add(consignmentDocumentVO);
		newReportSpec.setData(reportConsignmentList);
		newReportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
		reportSpecs.add(newReportSpec); 
		}
		if(isBulkDownload) {
			  List<Map<String, Object>> reportMap =ReportUtilInstance.getIstance().exportMultipleReports(reportSpecs);
		      List<byte[]> reportBytes = getListReportBytes(reportMap); 
		      byte[] mergedReport = ReportUtilInstance.getIstance().mergePDFbyteArray(reportBytes);
		      reportDataMap = reportMap.get(0);
		      reportDataMap.put(ReportConstants.REPORT_DATA, mergedReport);
		      PrintConfigVO printConfigVO = new PrintConfigVO();
			  printConfigVO.setPrintInitiationMode("S");
			  reportDataMap.put(ReportConstants.PRINT_CONFIGURATION,printConfigVO ); 
		} 
		return reportDataMap;
	}
	/**
	 * 
	 * 	Method		:	DocumentController.constructDAO
	 *	Added by 	:	A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MailTrackingDefaultsDAO
	 */
	private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			LOGGER.log(Log.FINE, persistenceException);
			throw new SystemException(persistenceException.getMessage());
		}
	}
	/**
	 * 
	 * 	Method		:	DocumentController.generateCN46ConsignmentReportDtls
	 *	Added by 	:	A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	ConsignmentDocumentVO
	 */
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReportDtls(
			ConsignmentFilterVO consignmentFilterVO) throws SystemException, PersistenceException {
		log.entering(DOCUMENT_CONTROLLER, "generateCN46ConsignmentReport");
	return constructDAO().generateCN46ConsignmentReport(consignmentFilterVO);
	}
	/**
	 * 
	 * 	Method		:	DocumentController.generateCN46ConsignmentSummaryReport
	 *	Added by 	:	A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param reportSpec
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ReportGenerationException 
	 *	Return type	: 	Map<String,Object>
	 */
	public Map<String, Object> generateCN46ConsignmentSummaryReport(ReportSpec reportSpec)
			throws PersistenceException, SystemException, ReportGenerationException {
		log.entering(DOCUMENT_CONTROLLER,"generateSummaryConsignmentReport");
		List<ReportSpec> reportSpecs = new ArrayList<>();
		Map<String, Object> reportDataMap = null;
		Collection<ConsignmentFilterVO> consignmentFilterVOs = null;
		consignmentFilterVOs = (Collection<ConsignmentFilterVO>) reportSpec.getFilterValues().iterator().next();
		ReportSpec newReportSpec = null;
		boolean isBulkDownload = false;
		ConsignmentFilterVO consignmentFilterVO = consignmentFilterVOs.iterator().next();
		isBulkDownload = consignmentFilterVO.isBulkDownload();
		Collection<ConsignmentDocumentVO> manifestConsignmentDocumentVOs = null;
		
		try {
			manifestConsignmentDocumentVOs = Proxy.getInstance().get(MailOperationsProxy.class).generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}

		for (ConsignmentDocumentVO consignmentDocumentVO : manifestConsignmentDocumentVOs) {
		Collection<ConsignmentDocumentVO>consignmentDocumentVOs = new ArrayList<>();
			newReportSpec = new ReportSpec();
			BeanHelper.copyProperties(newReportSpec, reportSpec);
			StringBuilder flightDetails = new StringBuilder("");
			StringBuilder flightDates = new StringBuilder("");
			String pointOfUnloading = null;
		int lcCount = 0;
		int cpCount = 0;
		int emsCount = 0;
		int totalPieces=0;
			if(consignmentDocumentVO.getRoutingInConsignmentVOs()!=null &&  !consignmentDocumentVO.getRoutingInConsignmentVOs().isEmpty()){
				RoutingInConsignmentVO routingConsignmentVO = consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next();
				flightDetails.append(routingConsignmentVO.getOnwardCarrierCode()).append("-").append(routingConsignmentVO.getOnwardFlightNumber());
				flightDates.append(routingConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
				pointOfUnloading = routingConsignmentVO.getPou();
			}
			consignmentDocumentVO.setFlightDetails(flightDetails.toString());
			consignmentDocumentVO.setFlightDates(flightDates.toString());
			consignmentDocumentVO.setPou(pointOfUnloading);
			if (consignmentDocumentVO.getMailInConsignmentcollVOs() != null
					&& !consignmentDocumentVO.getMailInConsignmentcollVOs().isEmpty()) {
			for(MailInConsignmentVO mailInconsign : consignmentDocumentVO.getMailInConsignmentcollVOs()){
					lcCount = lcCount + mailInconsign.getTotalLetterBags();
					cpCount = cpCount + mailInconsign.getTotalParcelBags();
					emsCount = emsCount + mailInconsign.getTotalEmsBags() + mailInconsign.getTotalSVbags();
			}
			totalPieces = lcCount+cpCount+emsCount;
		}
		consignmentDocumentVO.setSubClassLC(lcCount);
		consignmentDocumentVO.setSubClassCP(cpCount);
		consignmentDocumentVO.setSubClassEMS(emsCount);
		consignmentDocumentVO.setStatedBags(totalPieces);
		consignmentDocumentVOs.add(consignmentDocumentVO);
		newReportSpec.setData(consignmentDocumentVOs);
		newReportSpec.addSubReportData(consignmentDocumentVOs);
		newReportSpec.setExportFormat(ReportConstants.FORMAT_PDF);
		reportSpecs.add(newReportSpec); 
		}
		if(isBulkDownload) {
			  List<Map<String, Object>> reportMap = ReportUtilInstance.getIstance().exportMultipleReports(reportSpecs);
		      List<byte[]> reportBytes = getListReportBytes(reportMap); 
		      byte[] mergedReport = ReportUtilInstance.getIstance().mergePDFbyteArray(reportBytes);
		      reportDataMap = reportMap.get(0);
		      reportDataMap.put(ReportConstants.REPORT_DATA, mergedReport);
		      PrintConfigVO printConfigVO = new PrintConfigVO();
			  printConfigVO.setPrintInitiationMode("S");
			  reportDataMap.put(ReportConstants.PRINT_CONFIGURATION,printConfigVO ); 
		} 		
		return reportDataMap;
}
	/**
	 * 
	 * 	Method		:	DocumentController.generateCN46ConsignmentSummaryReportDtls
	 *	Added by 	:	A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws PersistenceException 
	 *	Return type	: 	ConsignmentDocumentVO
	 */
	public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException, PersistenceException {
		log.entering(DOCUMENT_CONTROLLER, "generateSummaryConsignmentReport");
		return constructDAO().generateCN46ConsignmentSummaryReport(
				consignmentFilterVO);
		}
	
}