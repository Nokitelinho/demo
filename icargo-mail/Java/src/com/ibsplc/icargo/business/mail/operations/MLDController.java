/*
 * MLDController.java Created on DEC 17, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

 
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;

import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDFlownDetailMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageDetailVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDStatusMessageVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5526
 * 
 */
@Module("mail")
@SubModule("operations")
public class MLDController {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String CLASS = "MLDController";
	private static final String DUPLICATE_MLD_CONFIGURATION = "mailtracking.defaults.duplictae.mld.configurations";
	private static final String MLD_REC_HND ="REC_HND";
	/**
	 * Method : MLDController.saveMLDConfiguarions Added by : A-5526 on Dec 16,
	 * 2015 Used for : Save MLD Configurations Parameters : @param
	 * mLDConfigurationVOs Parameters : @throws SystemException Return type :
	 * void
	 * 
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 */
	public void saveMLDConfigurations(
			Collection<MLDConfigurationVO> mLDConfigurationVOs)
			throws SystemException, MailTrackingBusinessException {
		log.entering(CLASS, "saveMLDConfiguarions");
		log.log(Log.FINE, "mLDConfigurationVOs for save", mLDConfigurationVOs);

		for (MLDConfigurationVO mLDConfigurationVO : mLDConfigurationVOs) {
			MLDConfiguration mLDConfiguration = null;
			MLDConfigurationPK mLDConfigurationPK = new MLDConfigurationPK();
			mLDConfigurationPK.setCompanyCode(mLDConfigurationVO
					.getCompanyCode());
			mLDConfigurationPK.setAirportCode(mLDConfigurationVO
					.getAirportCode());
			mLDConfigurationPK.setCarrierIdentifier(mLDConfigurationVO
					.getCarrierIdentifier());  
			try {
				mLDConfiguration = MLDConfiguration.find(mLDConfigurationPK);
			} catch (FinderException e) {
				new MLDConfiguration(mLDConfigurationVO);  
			}  

//			if (mLDConfiguration == null) {
//				new MLDConfiguration(mLDConfigurationVO);
//			} else 
				
			//Added mLDConfiguration null check as part of Bug ICRD-143797 by A-5526
				if(mLDConfiguration!=null){
				if (MailConstantsVO.OPERATION_FLAG_DELETE
					.equals(mLDConfigurationVO.getOperationFlag())) {
				mLDConfiguration.remove();
			} else {

				if (MailConstantsVO.OPERATION_FLAG_INSERT
						.equals(mLDConfigurationVO.getOperationFlag())
						&& mLDConfiguration != null) {
					throw new MailTrackingBusinessException(
							DUPLICATE_MLD_CONFIGURATION,
							new Object[] { mLDConfigurationVO.getCarrierCode(),
									mLDConfigurationVO.getAirportCode() });
				} else {
					log.log(Log.FINE, "mLDConfiguration is already present",
							mLDConfiguration);
					mLDConfiguration.setAllocationRequired(mLDConfigurationVO
							.getAllocatedRequired());
					//Removed carrier code setting as part of Bug ICRD-143797 by A-5526				
					mLDConfiguration.setDeliveredRequired(mLDConfigurationVO
							.getDeliveredRequired());
					mLDConfiguration.setHndRequired(mLDConfigurationVO
							.gethNDRequired());
					mLDConfiguration.setReceivedRequired(mLDConfigurationVO
							.getReceivedRequired());
					mLDConfiguration.setUpliftedRequired(mLDConfigurationVO
							.getUpliftedRequired());
					
					//Added for CRQ ICRD-135130 by A-8061 starts
					
					mLDConfiguration.setMldversion(mLDConfigurationVO.getMldversion());
					mLDConfiguration.setStagedRequired(mLDConfigurationVO.getStagedRequired());
					mLDConfiguration.setNestedRequired(mLDConfigurationVO.getNestedRequired());
					mLDConfiguration.setReceivedFromFightRequired(mLDConfigurationVO.getReceivedFromFightRequired());
					mLDConfiguration.setTransferredFromOALRequired(mLDConfigurationVO.getTransferredFromOALRequired());
					mLDConfiguration.setReceivedFromOALRequired(mLDConfigurationVO.getReceivedFromOALRequired());
					mLDConfiguration.setReturnedRequired(mLDConfigurationVO.getReturnedRequired());
					
					
					//Added for CRQ ICRD-135130 by A-8061 end
				}

			}
				}

		}
		log.exiting(CLASS, "saveMLDConfiguarions");

	}
/**
 * @author A-8061
 * @param carrierIdentifier
 * @param companyCode
 * @param airportCode
 * @return
 * @throws SystemException
 */
	public String getMLDVersion(int carrierIdentifier,String companyCode,String airportCode) throws SystemException{
		String mldVersion="";
		MLDConfiguration mLDConfiguration = null;
		MLDConfigurationPK mLDConfigurationPK = new MLDConfigurationPK();
		mLDConfigurationPK.setCompanyCode(companyCode);
		mLDConfigurationPK.setAirportCode(airportCode);
		mLDConfigurationPK.setCarrierIdentifier(carrierIdentifier);  
		try {
			mLDConfiguration = MLDConfiguration.find(mLDConfigurationPK);
			if(mLDConfiguration!=null){
				mldVersion=mLDConfiguration.getMldversion();
			}
			
		} catch (FinderException e) {
			
			log.log(Log.SEVERE, "Finder Exception Caught");
		}  
		return mldVersion;
	}
	/**
	 * Method : MLDController.findMLDCongfigurations Added by : A-5526 on Dec
	 * 17, 2015 Used for : Find MLD Configurations Parameters : @param
	 * mLDConfigurationFilterVO Parameters : @throws SystemException Return type
	 * : Collection<MLDConfigurationVO>
	 * 
	 * @throws SystemException
	 */
	public Collection<MLDConfigurationVO> findMLDCongfigurations(
			MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws SystemException {
		log.entering(CLASS, "findMLDCongfigurations");
		//Added as part of Bug ICRD-143797 by A-5526 starts
		if(mLDConfigurationFilterVO.getCarrierCode()!=null && !mLDConfigurationFilterVO.getCarrierCode().isEmpty()){
		mLDConfigurationFilterVO.setCarrierIdentifier(findCarrierIdentifier(mLDConfigurationFilterVO.getCompanyCode(), mLDConfigurationFilterVO.getCarrierCode()));
		}      
		//Added as part of Bug ICRD-143797 by A-5526 ends  
		Collection<MLDConfigurationVO> mLDConfigurationVOs = MLDConfiguration
				.findMLDCongfigurations(mLDConfigurationFilterVO);
		//Added as part of Bug ICRD-143797 by A-5526 starts
		for(MLDConfigurationVO mLDConfigurationVO:mLDConfigurationVOs){
			mLDConfigurationVO.setCarrierCode(findAirline(mLDConfigurationVO.getCompanyCode(), mLDConfigurationVO.getCarrierIdentifier()));         
		}
		//Added as part of Bug ICRD-143797 by A-5526 ends
		log.exiting(CLASS, "findMLDCongfigurations");
		return mLDConfigurationVOs;

	}

	public void flagMLDForMailOperations(Collection<MailbagVO> mailbagVOs,
			String mode) throws SystemException {
		log.entering("MLDController", "flagMLDForMailOperations");
		if ((mailbagVOs == null) || (mailbagVOs.size() <= 0)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromMailbagVOs(
				mailbagVOs, null, mode);

		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			if ((MailConstantsVO.MLD_ALL.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isAllocationRequired())
					|| (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isRecRequired())
					|| (MailConstantsVO.MLD_UPL.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isUpliftedRequired())
					|| (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde()) && mldMasterVO.ishNdRequired())
					|| (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isdLVRequired())
					|| (MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde()) && mldMasterVO.issTGRequired())
					|| (MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isnSTRequired())
					|| (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrCFRequired())
					|| (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()) && mldMasterVO.istFDRequired())
					|| (MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrCTRequired())
					|| (MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde()) && mldMasterVO.isrETRequired())
					) {			
			new MLDMessageMaster(mldMasterVO);
			}
		}

		log.exiting(CLASS, "flagMLDForMailOperations");
	}

	/**
	 * Method : MLDController.createMLDVOsFromMailbagVOs Added by : A-5526 on
	 * Dec 17, 2015 Used for : to createMLDVOsFromMailbagVOs Parameters : @param
	 * toContainerVO,mode Parameters : @throws SystemException Return type :
	 * Collection<MLDMasterVO>
	 * 
	 * @throws SystemException
	 */

	 Collection<MLDMasterVO> createMLDVOsFromMailbagVOs(Collection<MailbagVO> mailbagVOs,
			ContainerVO toContainerVO, String mode) throws SystemException {
		log.entering(CLASS, "createMLDVOsFromMailbaagVOs");
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();

		if ((mailbagVOs == null) || (mailbagVOs.isEmpty())) {
			return mldMasterVOs;
		}
		
		LogonAttributes logon = null;

		logon =  ContextUtils.getSecurityContext().getLogonAttributesVO();

		for (MailbagVO mailbagVO : mailbagVOs) {
			String airport = "";
			String carrier = "";
			airport = findAirportForMLDMode(mailbagVO,mode);
			carrier = findCarrierCodeForMLDMode(mailbagVO,mode,toContainerVO,logon);
			int carrierIdentifier =findCarrierIdentifier(
					logon.getCompanyCode(), carrier);
	
			
			MLDConfigurationFilterVO filterVO = new MLDConfigurationFilterVO();
			filterVO.setCompanyCode(logon.getCompanyCode());
			filterVO.setAirportCode(mailbagVO.getScannedPort()!=null?mailbagVO.getScannedPort():logon.getAirportCode());
			filterVO.setCarrierIdentifier(carrierIdentifier);
			Collection<MLDConfigurationVO> configVOs = findMLDCongfigurations(filterVO);
			MLDConfigurationVO	configVO = null;
			String mldVersion = "";
			if(!configVOs.isEmpty()) {
			configVO = configVOs.iterator().next();
			 mldVersion = configVO.getMldversion();
			}
			MLDMasterVO masterVO = constructMLDMasterVO(configVO);
			if (mldVersion.equals(MailConstantsVO.MLD_VERSION2)) {
				 masterVO = createMLDVOsForVersion2(mailbagVO, toContainerVO, mldVersion, mode, airport, logon,masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			}else if(mldVersion.contentEquals(MailConstantsVO.MLD_VERSION1)){
				 masterVO = createMLDVOsForVersion1(mailbagVO, toContainerVO, mldVersion, mode, airport,logon,masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			}else {
				break;
			}
		}
		log.exiting(CLASS, "createMLDVOsFromMailbaagVOs");
		return mldMasterVOs;
	}
	 
	 

	/**
	 * Method : MLDController.findCarrierIdentifier Added by : A-5526 on Dec 17,
	 * 2015 Used for : to findCarrierIdentifier Parameters : @param
	 * companyCode,carrierCode Parameters : @throws SystemException
	 * 
	 * @throws SystemException
	 */

	public int findCarrierIdentifier(String companyCode, String carrierCode)
			throws SystemException {
		log.entering(CLASS, "findCarrierIdentifier");
		AirlineValidationVO airlineValidationVO = null;
		int carrierId = 0;
		try {
			airlineValidationVO = Proxy.getInstance().get(SharedAirlineProxy.class).validateAlphaCode(
					companyCode, carrierCode);
		} catch (SharedProxyException sharedProxyException) {
			throw new SystemException(sharedProxyException.getMessage());
		}
		if (airlineValidationVO != null) {
			carrierId = airlineValidationVO.getAirlineIdentifier();
		}
		log.exiting(CLASS, "findCarrierIdentifier");
		return carrierId;
	}

	public void flagMLDForMailbagReturn(
			Collection<MailbagVO>mailbags) throws SystemException {
		log.entering(CLASS, "flagMLDForMailbagReturn");

		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();

			if ((mailbags != null) && (mailbags.size() > 0)) {
				for (MailbagVO mailbagVO : mailbags) {
					//if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
						mailbagVOs.add(mailbagVO);
					//}
				}
			}

		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_RET);
		log.exiting(CLASS, "flagMLDForMailbagReturn");
	}
	
	/**
	 * Method : MLDController.flagMLDForMailAcceptance Added by : A-5526 on Dec
	 * 17, 2015 Used for : to flagMLDForMailAcceptance Parameters : @param
	 * mailAcceptanceVO,dsnMap Parameters : @throws SystemException
	 * 
	 * @throws SystemException
	 */

	public void flagMLDForMailAcceptance(MailAcceptanceVO mailAcceptanceVO,
			Collection<MailbagVO>mailbags) throws SystemException {
		log.entering(CLASS, "flagMLDForMailAcceptance");

		if ((mailAcceptanceVO == null) || (mailbags == null)) {
			return;
		}
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		Collection<MailbagVO> transferFromCarriermailbagVOs = new ArrayList<>();
		Collection<MailbagVO> transferFromPartnerCarriermailbagVOs = new ArrayList<>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

			constructMailbagVos(mailAcceptanceVO, mailbags, mailbagVOs, transferFromCarriermailbagVOs,
					transferFromPartnerCarriermailbagVOs, logonAttributes);
	
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_REC);
		//Added as part of bug ICRD-225164 by A-5526
		//Added by A-4809, MLD-ALL need not be trigerred for carrier acceptance 
		if(mailAcceptanceVO.getFlightNumber()!=null && !mailAcceptanceVO.getFlightNumber().equals("-1")){
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_FRESH_ALL);  
		}
		//Added for CRQ ICRD-135130 by A-8061 starts
		
			flagMLDForMailOperations(transferFromCarriermailbagVOs, MLD_REC_HND);
	
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_NST);
		Collection<ContainerDetailsVO> containerdetailsVOs =mailAcceptanceVO.getContainerDetails();
		if(containerdetailsVOs!=null && !containerdetailsVOs.isEmpty()){
		for(ContainerDetailsVO containerdetailsVO:containerdetailsVOs){
			if("B".equals(containerdetailsVO.getContainerType()))
			{
				flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_STG);
			}
		}
		}
		flagMLDForMailOperations(mailbagVOs, MailConstantsVO.MLD_RCT);
		flagMLDForMailOperations(transferFromPartnerCarriermailbagVOs, MailConstantsVO.MLD_TFD);

		//Added for CRQ ICRD-135130 by A-8061 end
		
		log.exiting(CLASS, "flagMLDForMailAcceptance");
	}
	private void constructMailbagVos(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbags,
			Collection<MailbagVO> mailbagVOs, Collection<MailbagVO> transferFromCarriermailbagVOs,
			Collection<MailbagVO> transferFromPartnerCarriermailbagVOs, LogonAttributes logonAttributes)
			throws SystemException {
		if ((mailbags != null) && !mailbags.isEmpty() ) {
			for (MailbagVO mailbagVO : mailbags) {
				transferFromCarriermailbagVOs(mailAcceptanceVO, mailbagVOs, transferFromCarriermailbagVOs, mailbagVO);
			transferFromPartnerCarriermailbagVOs(transferFromPartnerCarriermailbagVOs, logonAttributes, mailbagVO);	
				
			}
		}
	}
	private void transferFromPartnerCarriermailbagVOs(Collection<MailbagVO> transferFromPartnerCarriermailbagVOs,
			LogonAttributes logonAttributes, MailbagVO mailbagVO) throws SystemException {
		if (mailbagVO.getTransferFromCarrier() != null && !mailbagVO.getTransferFromCarrier().isEmpty()) {
			Collection<PartnerCarrierVO> partnerCarierVos = PartnerCarrier.findAllPartnerCarriers(
					mailbagVO.getCompanyCode(), logonAttributes.getOwnAirlineCode(),
					mailbagVO.getScannedPort());
			String carrierCode = mailbagVO.getTransferFromCarrier();
		if (partnerCarierVos != null) {
				for (PartnerCarrierVO partner : partnerCarierVos) {
					String partnerCarrier = partner.getPartnerCarrierCode();
					if (partnerCarrier.equals(carrierCode) && ( partner.getMldTfdReq()!=null &&"A".equals(partner.getMldTfdReq()))) {
						transferFromPartnerCarriermailbagVOs.add(mailbagVO);
					}
				}
			}
		}
	}
	private void transferFromCarriermailbagVOs(MailAcceptanceVO mailAcceptanceVO, Collection<MailbagVO> mailbagVOs,
			Collection<MailbagVO> transferFromCarriermailbagVOs, MailbagVO mailbagVO) {
		if (MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
			if(mailbagVO.getFlightSequenceNumber()==0 && mailAcceptanceVO.getFlightSequenceNumber()!=0) {
				mailbagVO.setFlightSequenceNumber(mailAcceptanceVO.getFlightSequenceNumber());
			}
			mailbagVOs.add(mailbagVO);
			if(mailbagVO.getTransferFromCarrier()!=null && !mailbagVO.getTransferFromCarrier().isEmpty()){
				transferFromCarriermailbagVOs.add(mailbagVO);
			}
		}
	}

	/**
	 * Method : MLDController.flagMLDForContainerTransfer Added by : A-5526 on
	 * Dec 17, 2015 Used for : to flagMLDForContainerTransfer Parameters : @param
	 * mailbagVOs,containerVOs,operationalFlightVO Parameters : @throws
	 * SystemException
	 * 
	 * @throws SystemException
	 */
	public void flagMLDForContainerTransfer(Collection<MailbagVO> mailbagVOs,
			Collection<ContainerVO> containerVOs,
			OperationalFlightVO operationalFlightVO) throws SystemException {
		log.entering(CLASS, "flagMLDForContainerTransfer");
		if (mailbagVOs == null) {
			return;
		}

		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		ContainerVO containerVO = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		String airport = "";
		String carrier = "";

		for (MailbagVO mailbagVO : mailbagVOs) {
			if (containerVOs != null && containerVOs.size() > 0) {
				containerVO = containerVOs.iterator().next();
			}

			if (containerVO != null) {
			 airport = mailbagVO.getScannedPort();
			 AirlineValidationVO airlineValidationVO = null;
		        if(containerVO.getCarrierCode()==null){
		    	    try{
		     	        	airlineValidationVO= new SharedAirlineProxy()
				     .findAirline(mailbagVO.getCompanyCode(), mailbagVO.getCarrierId());
		     	  }catch (SharedProxyException sharedProxyException) {
		     		 log.log(Log.INFO, sharedProxyException);
				   } catch (SystemException ex) {
					   log.log(Log.INFO, ex);
				   }
		        }
			carrier = airlineValidationVO!=null ? airlineValidationVO.getAlphaCode():"";
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if(!mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION1)){
				continue;
			}
			mailbagVO.setPol(containerVO.getAssignedPort());
			mldMasterVO = new MLDMasterVO();

			mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
			mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_HND);
			mldMasterVO.setScanTime(mailbagVO.getScannedDate());

			mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			String destinationAirport = "";
			if (mailbagVO.getMailbagId() != null
					&& mailbagVO.getMailbagId().trim().length() == 29) {
				/*mldMasterVO.setWeight(mailbagVO.getMailbagId()
						.substring(25, 29));*/
				mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId()
						.substring(25, 29))));//added by A-7371
				destinationAirport = new MailUploadController()
						.findAirportCityForMLD(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId().substring(8, 11));
			}

			mldMasterVO.setDestAirport(destinationAirport);
			if(mailbagVO.getContainerNumber()!=null){
			mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
			}else if(containerVO!=null){
				mldMasterVO.setUldNumber(containerVO.getContainerNumber());    
			}
			mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());

			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setMessagingMode("O");
			mldMasterVO.setMessageSequence(1);
			mldMasterVO.setMessageVersion("1");
			mldMasterVO.setUldWeightCode("KG");
			mldMasterVO.setWeightCode("HG");
			mldDetailVO = new MLDDetailVO();
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			}
			mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldDetailVO.setMailIdr(mailbagVO.getMailbagId());

			mldDetailVO.setMessageSequence(1);

			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
			mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
					.getFlightSequenceNumber());
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setPouOub(mailbagVO.getPou());
			//Commented as part of bug ICRD-144653 by A-5526
//			mldDetailVO.setFlightOperationDateOub(operationalFlightVO
//					.getFlightDate());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());

			mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(containerVO
					.getFlightSequenceNumber());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			//Commented as part of bug ICRD-144653 by A-5526
			//mldDetailVO.setFlightOperationDateInb(containerVO.getFlightDate());
			mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
			
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			if (containerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVOs.add(mldMasterVO);
		}
		}

		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}
		ContainerVO containerVo = containerVOs.iterator().next();

		flagMLDForMailbagTransfer(mailbagVOs, containerVo, operationalFlightVO,MailConstantsVO.MLD_HND);
		
		//Added for CRQ ICRD-135130 by A-8061 starts
		flagRCFmessageForMailbags(mailbagVOs, containerVo, operationalFlightVO);
		flagMLDmessageForMailbags(mailbagVOs, containerVo, operationalFlightVO,MailConstantsVO.MLD_TFD);
		
		//Added for CRQ ICRD-135130 by A-8061 end
		
		log.exiting(CLASS, "flagMLDForContainerTransfer");
	}

	/**
	 * @author A-8061
	 * @param mailbagVOs
	 * @param containerVO
	 * @param operationalFlightVO
	 * @throws SystemException
	 */
	public void flagRCFmessageForMailbags(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO, OperationalFlightVO operationalFlightVO)
			throws SystemException {
		log.entering(CLASS, "flagRCFmessageForMailbags");
		if (mailbagVOs == null) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		String airport ="";
		String carrier ="";
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();

		
		for (MailbagVO mailbagVO : mailbagVOs) {
		
			mldMasterVO = new MLDMasterVO();
			airport = mailbagVO.getScannedPort();
			 carrier = mailbagVO.getCarrierCode();
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if(mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2)){

			mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
			mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_RCF);
			mldMasterVO.setScanTime(mailbagVO.getScannedDate());
			if(containerVO.getTransactionLevel()!=null) {
				mldMasterVO.setTransactionLevel(containerVO.getTransactionLevel());
			}
			mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			String destinationAirport = "";
			if (mailbagVO.getMailbagId() != null
					&& mailbagVO.getMailbagId().trim().length() == 29) {
				/*mldMasterVO.setWeight(mailbagVO.getMailbagId()
						.substring(25, 29));*/
				mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId()
						.substring(25, 29))));//added by A-7371
				destinationAirport = new MailUploadController()
						.findAirportCityForMLD(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId().substring(8, 11));
			}

			mldMasterVO.setDestAirport(destinationAirport);
			if(mailbagVO.getContainerNumber()!=null){
			mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
			}else if(containerVO!=null){
				mldMasterVO.setUldNumber(containerVO.getContainerNumber());    
			}
			mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());

			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setMessagingMode("O");
			mldMasterVO.setMessageSequence(1);
			mldMasterVO.setMessageVersion("2");
			mldMasterVO.setUldWeightCode("KG");
			mldMasterVO.setWeightCode("HG");
			mldDetailVO = new MLDDetailVO();
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			}
			mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
			
			mldMasterVO.setBarcodeValue(mldMasterVO.getUldNumber());
			mldDetailVO.setMailIdr(mldMasterVO.getUldNumber());

			mldDetailVO.setMessageSequence(1);

			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
			mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
					.getFlightSequenceNumber());
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setPouOub(mailbagVO.getPou());
			//Commented as part of bug ICRD-144653 by A-5526
//			mldDetailVO.setFlightOperationDateOub(operationalFlightVO
//					.getFlightDate());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());

			mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(containerVO
					.getFlightSequenceNumber());
			mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			//Commented as part of bug ICRD-144653 by A-5526
			//mldDetailVO.setFlightOperationDateInb(containerVO.getFlightDate());
			
			
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			if (containerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVOs.add(mldMasterVO);
			break;
		}
		}


		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}

		log.exiting(CLASS, "flagRCFmessageForMailbags");
	}
	
	
	/**
	 * @author A-8061
	 * @param mailbagVOs
	 * @param containerVO
	 * @param operationalFlightVO
	 * @param mode
	 * @throws SystemException
	 */
	public void flagMLDmessageForMailbags(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO, OperationalFlightVO operationalFlightVO,String mode)
			throws SystemException {
		log.entering(CLASS, "flagMLDmessageForMailbags");
		if (mailbagVOs == null) {
			return;
		}
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;

		if(MailConstantsVO.MLD_TFD.equals(mode)){
		if(logonAttributes.getOwnAirlineCode().equals(operationalFlightVO.getCarrierCode())){
			return ;
		}
		}
		String airport = "";
		String carrier = "";
		
		for (MailbagVO mailbagVO : mailbagVOs) {
		
			mldMasterVO = new MLDMasterVO();
			airport = mailbagVO.getScannedPort();
			 carrier = mailbagVO.getCarrierCode();
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if(!mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2)){
				continue;
			}

			mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
			mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
			mldMasterVO.setEventCOde(mode);
			mldMasterVO.setScanTime(mailbagVO.getScannedDate());

			mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			String destinationAirport = "";
			if (mailbagVO.getMailbagId() != null
					&& mailbagVO.getMailbagId().trim().length() == 29) {
				/*mldMasterVO.setWeight(mailbagVO.getMailbagId()
						.substring(25, 29));*/
				mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId()
						.substring(25, 29))));//added by A-7371
				destinationAirport = new MailUploadController()
						.findAirportCityForMLD(mailbagVO.getCompanyCode(),
								mailbagVO.getMailbagId().substring(8, 11));
			}

			mldMasterVO.setDestAirport(destinationAirport);
			if(mailbagVO.getContainerNumber()!=null){
			mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
			}else if(containerVO!=null){
				mldMasterVO.setUldNumber(containerVO.getContainerNumber());    
			}
			mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());

			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setMessagingMode("O");
			mldMasterVO.setMessageSequence(1);
			mldMasterVO.setMessageVersion("2");
			mldMasterVO.setUldWeightCode("KG");
			mldMasterVO.setWeightCode("HG");
			mldDetailVO = new MLDDetailVO();
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			}
			mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldDetailVO.setMailIdr(mailbagVO.getMailbagId());

			mldDetailVO.setMessageSequence(1);

			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(operationalFlightVO.getCarrierId());
			mldDetailVO.setFlightSequenceNumberOub(operationalFlightVO
					.getFlightSequenceNumber());
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setPouOub(mailbagVO.getPou());
			//Commented as part of bug ICRD-144653 by A-5526
//			mldDetailVO.setFlightOperationDateOub(operationalFlightVO
//					.getFlightDate());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());

			mldDetailVO.setCarrierCodeInb(containerVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberInb(containerVO
					.getFlightSequenceNumber());
			//Added as part of bug ICRD-144653 by A-5526
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			//Commented as part of bug ICRD-144653 by A-5526
			//mldDetailVO.setFlightOperationDateInb(containerVO.getFlightDate());
			mldDetailVO.setCarrierIdInb(containerVO.getCarrierId());
			
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			if (containerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeInb("F");

			} else {
				mldDetailVO.setMailModeOub("H");

			}
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldDetailVO.setPolInb(mailbagVO.getPol());
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVOs.add(mldMasterVO);
		}


		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}

		log.exiting(CLASS, "flagMLDmessageForMailbags");
	}
	
	/**
	 * Method : MLDController.flagMLDForMailbagTransfer Added by : A-5526 on Dec
	 * 17, 2015 Used for : to flagMLDForMailbagTransfer Parameters : @param
	 * mailbagVOs,containerVOs,operationalFlightVO Parameters : @throws
	 * SystemException
	 * 
	 * @throws SystemException
	 */
	public void flagMLDForMailbagTransfer(Collection<MailbagVO> mailbagVOs,
			ContainerVO containerVO, OperationalFlightVO operationalFlightVO,String mode)
			throws SystemException {
		log.entering(CLASS, "flagMLDForMailbagTransfer");
		if (mailbagVOs == null) {
			return;
		}
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<MLDMasterVO>();
		MLDMasterVO mldMasterVO = null;
		MLDDetailVO mldDetailVO = null;
		String airport ="";
		String carrier ="";
		ArrayList<String> partnerCarriers = new ArrayList<>();


		for (MailbagVO mailbagVO : mailbagVOs) {
			airport = mailbagVO.getScannedPort();
			 carrier = mailbagVO.getCarrierCode();
			int carrierIdentifier =findCarrierIdentifier(
					logonAttributes.getCompanyCode(), carrier);
			String mldVersion= getMLDVersion(carrierIdentifier,logonAttributes.getCompanyCode(),airport);
			if((mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION1 )&& (mode.equalsIgnoreCase(MailConstantsVO.MLD_HND) || 
					mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL))) || (mldVersion.equalsIgnoreCase(MailConstantsVO.MLD_VERSION2)&&
							(mode.equalsIgnoreCase(MailConstantsVO.MLD_STG)||
									mode.equalsIgnoreCase(MailConstantsVO.MLD_NST) ||
									mode.equalsIgnoreCase(MailConstantsVO.MLD_TFD )	)	)) {
				
			//Added for CRQ ICRD-135130 by A-8061 starts
				if (MailConstantsVO.MLD_TFD.equals(mode)) {
					boolean isPartnerCarrier = false;
					isPartnerCarrier = partnerCarrierCheck(logonAttributes, carrier, partnerCarriers, mailbagVO,
							isPartnerCarrier);
					if (!isPartnerCarrier && logonAttributes.getOwnAirlineCode().equals(containerVO.getCarrierCode())) {

						break;
					}

				}
			//Added for CRQ ICRD-135130 by A-8061 end
			

			if (operationalFlightVO == null) {
				mldMasterVO = new MLDMasterVO();

				mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
				mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());

				//mldMasterVO.setEventCOde(MailConstantsVO.MLD_HND);   
				mldMasterVO.setEventCOde(mode);//Added for CRQ ICRD-135130 by A-8061 
				
				mldMasterVO.setScanTime(mailbagVO.getScannedDate());
				mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
							//Added for bug ICRD-152670 by A-5526
				findSenderOrReceiverAirportCodes(mailbagVO,null,mldMasterVO,MailConstantsVO.MLD_HND);
/*				if(mailbagVO.getPou()!=null && !mailbagVO.getPou().isEmpty()){
				mldMasterVO.setSenderAirport(mailbagVO.getPou());
				}*/

				mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
				String destinationAirport = "";
				if (mailbagVO.getMailbagId() != null
						&& mailbagVO.getMailbagId().trim().length() == 29) {
					/*mldMasterVO.setWeight(mailbagVO.getMailbagId().substring(
							25, 29));*/
					mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId().substring(
							25, 29))));//added by A-7371
					destinationAirport = new MailUploadController()
							.findAirportCityForMLD(mailbagVO.getCompanyCode(),
									mailbagVO.getMailbagId().substring(8, 11));
				}

				mldMasterVO.setDestAirport(destinationAirport);
				mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
				mldMasterVO.setLastUpdatedUser(containerVO.getLastUpdateUser());

				mldMasterVO.setProcessStatus("NEW");
				mldMasterVO.setMessagingMode("O");
				mldMasterVO.setMessageSequence(1);
				mldMasterVO.setMessageVersion(mldVersion);
				mldMasterVO.setUldWeightCode("KG");
				mldMasterVO.setWeightCode("HG");
				mldDetailVO = new MLDDetailVO();
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");

				}
				mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
				mldDetailVO.setMailIdr(mailbagVO.getMailbagId());

				mldDetailVO.setMessageSequence(1);

				mldDetailVO.setCarrierCodeOub(containerVO.getCarrierCode());

				mldDetailVO.setCarrierIdOub(containerVO.getCarrierId());
				//Commented as part of bug ICRD-144653 by A-5526
//				mldDetailVO.setFlightOperationDateOub(containerVO
//						.getFlightDate());
				//Added as part of bug ICRD-144653 by A-5526
				
				mldDetailVO.setFlightSequenceNumberOub(containerVO
						.getFlightSequenceNumber());

				mldDetailVO.setFlightNumberOub(containerVO.getFlightNumber());
				
				if(mldDetailVO.getFlightNumberOub()!=null)//Added for CRQ ICRD-135130 by A-8061 
				{
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				}
				
				mldDetailVO.setPouOub(containerVO.getPou());

				//Modified as part of bug ICRD-149386 by A-5526 
				if (containerVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeOub("F");
					mldDetailVO.setModeDescriptionOub("Flight Operation");
				} else {
					mldDetailVO.setMailModeOub("H");
					mldDetailVO.setModeDescriptionOub("Carrier Operation");
				}
				mldDetailVO.setPostalCodeOub(containerVO.getPaCode());

				mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
				mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
				mldDetailVO.setFlightSequenceNumberInb(mailbagVO
						.getFlightSequenceNumber());
				//Added as part of bug ICRD-144653 by A-5526
				if(mldDetailVO.getFlightNumberInb()!=null)//Added for CRQ ICRD-135130 by A-8061 
				{
				updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
				}
			
				//Commented as part of bug ICRD-144653 by A-5526
				//mldDetailVO
						//.setFlightOperationDateInb(mailbagVO.getFlightDate());
				mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
				
				mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
				if (mailbagVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeInb("F");

				} else {
					mldDetailVO.setMailModeOub("H");

				}
				mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
				mldDetailVO.setPolInb(mailbagVO.getPol());
				mldMasterVO.setMldDetailVO(mldDetailVO);
				mldMasterVOs.add(mldMasterVO);

			}

			if(MailConstantsVO.MLD_HND.equals(mode)){
			mldMasterVO = new MLDMasterVO();
			mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());

			mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);
			mldMasterVO.setScanTime(mailbagVO.getScannedDate());
			//mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			if(mailbagVO.getPou()==null){
			mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());
			}else{
			mldMasterVO.setSenderAirport(mailbagVO.getPou());
			}
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
			String destination = "";
			if (mailbagVO.getMailbagId() != null
					&& mailbagVO.getMailbagId().trim().length() == 29) {
				/*mldMasterVO.setWeight(mailbagVO.getMailbagId()
						.substring(25, 29));*/
				mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagVO.getMailbagId()
						.substring(25, 29))));//added by A-7371
				destination = new MailUploadController().findAirportCityForMLD(
						mailbagVO.getCompanyCode(), mailbagVO.getMailbagId()
								.substring(8, 11));
			}
			mldMasterVO.setDestAirport(destination);  
//Added for bug ICRD-152670 by A-5526
			if(mldMasterVO.getSenderAirport()!=null && mldMasterVO.getSenderAirport().equals(mldMasterVO.getReceiverAirport()) && containerVO!=null){
				mldMasterVO.setReceiverAirport(containerVO.getPou());
			}
			if(mailbagVO.getFlightSequenceNumber()>0)
			{
			findSenderOrReceiverAirportCodes(mailbagVO,null,mldMasterVO,MailConstantsVO.MLD_ALL);
			}
			if(mldMasterVO.getReceiverAirport()==null || mldMasterVO.getReceiverAirport().isEmpty()){       
				mldMasterVO.setReceiverAirport(mldMasterVO.getDestAirport());
			}
			if(mailbagVO.getContainerNumber()!=null){
			mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
				}else if(containerVO!=null){
					mldMasterVO.setUldNumber(containerVO.getContainerNumber());          
				}
			
		
			mldMasterVO.setLastUpdatedUser(mailbagVO.getScannedUser());
			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setMessagingMode("O");
			mldMasterVO.setMessageSequence(1);
			mldMasterVO.setMessageVersion("1");
			mldMasterVO.setUldWeightCode("KG");
			mldMasterVO.setWeightCode("HG");

			mldDetailVO = new MLDDetailVO();

			mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
			mldDetailVO.setMailIdr(mailbagVO.getMailbagId());

			mldDetailVO.setMessageSequence(1);

			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberOub(mailbagVO
					.getFlightSequenceNumber());
			if (operationalFlightVO != null) {
				//Added as part of bug ICRD-144653 by A-5526
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				//Commented as part of bug ICRD-144653 by A-5526
				//mldDetailVO.setFlightOperationDateOub(operationalFlightVO
						//.getFlightDate());
			} else {
				mldDetailVO.setFlightNumberOub(containerVO.getFlightNumber());
				mldDetailVO.setFlightSequenceNumberOub(containerVO
						.getFlightSequenceNumber());
				//Added as part of bug ICRD-144653 by A-5526
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				//Commented as part of bug ICRD-144653 by A-5526
				//mldDetailVO.setFlightOperationDateOub(containerVO
						//.getFlightDate());
			}
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
			mldDetailVO.setPouOub(mailbagVO.getPou());
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
			if (containerVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");
				mldDetailVO.setModeDescriptionOub("Flight Operation");
			} else {
				mldDetailVO.setMailModeOub("H");
				mldDetailVO.setModeDescriptionOub("Carrier Operation");
			}

			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVOs.add(mldMasterVO);
		}
		}
		}
		
		for (MLDMasterVO mldMasterVOLoop : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVOLoop);
		}

		log.exiting(CLASS, "flagMLDForMailbagTransfer");
	}
	private boolean partnerCarrierCheck(LogonAttributes logonAttributes, String carrier,
			ArrayList<String> partnerCarriers, MailbagVO mailbagVO, boolean isPartnerCarrier) throws SystemException {
		Collection<PartnerCarrierVO> partnerCarierVos = PartnerCarrier.findAllPartnerCarriers(
				mailbagVO.getCompanyCode(), logonAttributes.getOwnAirlineCode(),
				mailbagVO.getScannedPort());
		if (!partnerCarierVos.isEmpty()) {
			for (PartnerCarrierVO partner : partnerCarierVos) {
				String partnerCarrier = partner.getPartnerCarrierCode();
				partnerCarriers.add(partnerCarrier);
				if (partnerCarriers.contains(carrier)
						&& (partner.getMldTfdReq()!=null && ("T".equals(partner.getMldTfdReq()) || "A".equals(partner.getMldTfdReq())))) {
					isPartnerCarrier = true;
				}
			}
		}
		return isPartnerCarrier;
	}

	
	/**
	 * Method : MLDController.flagMLDForMailReassignOperations Added by : A-5526
	 * on Dec 17, 2015 Used for : to flagMLDForMailReassignOperations Parameters
	 * : @param mailbagVOs,containerVOs,mode Parameters : @throws
	 * SystemException
	 * 
	 * @throws SystemException
	 */
	public void flagMLDForMailReassignOperations(
			Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
			String mode) throws SystemException {
		log.entering(CLASS, "flagMLDForMailReassignOperations");
		if ((mailbagVOs == null) || (mailbagVOs.size() <= 0)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromMailbagVOs(
				mailbagVOs, toContainerVO, mode);

		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			new MLDMessageMaster(mldMasterVO);
		}

		log.exiting(CLASS, "flagMLDForMailReassignOperations");

	}

	/**
	 * Method : MLDController.flagMLDForUpliftedMailbags Added by : A-5526 on
	 * Dec 17, 2015 Used for : to flagMLDForUpliftedMailbags Parameters : @param
	 * operationalFlightVOs Parameters : @throws SystemException
	 * 
	 * @throws SystemException
	 */
	public void flagMLDForUpliftedMailbags(
			Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException {
		log.entering(CLASS, "flagMLDForUpliftedMailbags");

		if (operationalFlightVOs != null) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {

				Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
				totalMailBags = MailAcceptance
						.findMailBagsForUpliftedResdit(operationalFlightVO);
				flagMLDForMailOperations(totalMailBags, MailConstantsVO.MLD_UPL);
				
				
			}

		}
		log.exiting(CLASS, "flagMLDForUpliftedMailbags");
	}
	 /**
	 * @author A-5526 This method is used to find the MailBags Accepted to a
	 *         ParticularFlight and Flag MLD-UPL for the Same.For bug ICRD-150084
	 * @param operationalFlightVOs
	 * @throws SystemException
	 */
	public void flagMLDForUpliftedMailbagsForATDCapture(
			Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException {
		log.entering(CLASS, "flagMLDForUpliftedMailbags");

		if (operationalFlightVOs != null) {
			for (OperationalFlightVO operationalFlightVO : operationalFlightVOs) {
				
				AssignedFlight assignedFlight = null;
				AssignedFlightPK assignedFlightPk = new AssignedFlightPK();
				assignedFlightPk.setAirportCode(operationalFlightVO.getPol());
				assignedFlightPk.setCompanyCode(operationalFlightVO.getCompanyCode());
				assignedFlightPk.setFlightNumber(operationalFlightVO.getFlightNumber());
				assignedFlightPk.setFlightSequenceNumber(operationalFlightVO
						.getFlightSequenceNumber());
				assignedFlightPk.setLegSerialNumber(operationalFlightVO
						.getLegSerialNumber());
				assignedFlightPk.setCarrierId(operationalFlightVO.getCarrierId());
				try {
					assignedFlight = AssignedFlight.find(assignedFlightPk);
				} catch (FinderException ex) {
					assignedFlight=null;
				}
				if (assignedFlight != null && MailConstantsVO.FLIGHT_STATUS_CLOSED.equals(assignedFlight.getExportClosingFlag())) {
					Collection<MailbagVO> totalMailBags = new ArrayList<MailbagVO>();
					totalMailBags = MailAcceptance
							.findMailBagsForUpliftedResdit(operationalFlightVO);

					flagMLDForMailOperations(totalMailBags, MailConstantsVO.MLD_UPL);
				

				}

			}

		}
		log.exiting(CLASS, "flagMLDForUpliftedMailbags");
	}

	/**
	 * Method : MLDController.flagMLDForContainerReassign Added by : A-5526 on
	 * Dec 17, 2015 Used for : to flagMLDForContainerReassign Parameters : @param
	 * Collection<ContainerVO>,OperationalFlightVO Parameters : @throws
	 * SystemException
	 * 
	 * @throws SystemException
	 */

	public void flagMLDForContainerReassign(
			Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO)
			throws SystemException {
		log.entering(CLASS, "flagMLDForContainerReassign");
		LogonAttributes logon = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();
		ContainerAssignmentVO containerAssignmentVO = null;
		//Modified as part of BUG ICRD-157048 by A-5526 starts
		for(ContainerVO containerVO:containerVOs){

		Collection<MailbagVO> mailBagsToReassign = new ArrayList<MailbagVO>();
		containerAssignmentVO = Container.findLatestContainerAssignment(logon.getCompanyCode(),containerVO.getContainerNumber());
			//OperationalFlightVO operationalFlightVO = createOperationalFlightVO(containerVo);
			Collection<ContainerDetailsVO> containerDetailsVOs=findContainerDetailsVO(containerVO,toFlightVO,containerAssignmentVO);
			
			Collection<ContainerDetailsVO> containerWithMailbagInfos = MailAcceptance
					.findMailbagsInContainer(containerDetailsVOs);
			for(ContainerDetailsVO containerDetailsVO:containerWithMailbagInfos){
				ContainerVO toContainerVO = createToContainerVO(containerDetailsVO, toFlightVO);
				
				Collection<MailbagVO> totalMailBags=containerDetailsVO.getMailDetails();
		if (totalMailBags != null && totalMailBags.size() > 0) {
			for (MailbagVO mailbagVO : totalMailBags) {
					if (containerDetailsVO.getContainerNumber() != null
							&& (containerDetailsVO.getContainerNumber().equals(mailbagVO
								.getContainerNumber()))) {
						mailbagVO.setCarrierCode(toContainerVO.getCarrierCode());       
					mailBagsToReassign.add(mailbagVO);
				}
			}
			flagMLDForMailReassignOperations(mailBagsToReassign, toContainerVO,
					MailConstantsVO.MLD_ALL);
			
			//Added for CRQ ICRD-135130 by A-8061 starts
			if("B".equals(containerAssignmentVO.getContainerType())) {
			flagMLDForMailReassignOperations(mailBagsToReassign, toContainerVO,
					MailConstantsVO.MLD_STG);
			}
			

		}
			}
		}

	
	
		
		
		
		//Modified as part of BUG ICRD-157048 by A-5526 ends
		log.exiting(CLASS, "flagMLDForContainerReassign");
	}
/**
 * @author A-5526
 * @param containerVO
 * @return Collection<ContainerDetailsVO>
 * Added as part of BUG ICRD-157048 by A-5526 
 */
	private Collection<ContainerDetailsVO> findContainerDetailsVO(ContainerVO containerVO,OperationalFlightVO toFlightVO,ContainerAssignmentVO containerAssignmentVO) {
		Collection<ContainerDetailsVO> containerDetailsVOs=new ArrayList<ContainerDetailsVO>();
		
			ContainerDetailsVO condetVO = new ContainerDetailsVO();
			condetVO.setCompanyCode(containerVO.getCompanyCode());
			condetVO.setContainerNumber(containerVO.getContainerNumber());
			condetVO.setContainerType(containerVO.getType());
			condetVO.setCarrierId(containerVO.getCarrierId());
			condetVO.setPol(containerVO.getAssignedPort());
			condetVO.setCarrierCode(containerVO.getCarrierCode());   
			/*Added by A-7540
			  this check is added because,if at all the data is getting persisted in the table
			before the flagMLDMessageTriggering,then we will take the flight info from
			toFlightVO,else we will take old flight details*/
			if(containerAssignmentVO.getFlightNumber().equals(containerVO.getFlightNumber()) &&
					containerAssignmentVO.getFlightSequenceNumber()==containerVO.getFlightSequenceNumber()){
			
			condetVO.setFlightNumber(containerVO.getFlightNumber());
			condetVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			condetVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			condetVO.setLegSerialNumber(containerVO.getLegSerialNumber());    
			}
			else{
				condetVO.setFlightNumber(toFlightVO.getFlightNumber());
				condetVO.setFlightSequenceNumber(toFlightVO.getFlightSequenceNumber());
				condetVO.setSegmentSerialNumber(toFlightVO.getSegSerNum());
				condetVO.setLegSerialNumber(toFlightVO.getLegSerialNumber()); 
		        }
			containerDetailsVOs.add(condetVO);
		
		
		return containerDetailsVOs;
		
	}

	/**
	 * Method : MLDController.createToContainerVO Added by : A-5526 on Dec 17,
	 * 2015 Used for : to createToContainerVO Parameters : @param
	 * ContainerVO,OperationalFlightVO Parameters : @throws SystemException
	 * Return : ContainerVO
	 * 
	 * @throws SystemException
	 */
	//Modified as part of BUG ICRD-157048 by A-5526 
	private ContainerVO createToContainerVO(ContainerDetailsVO containerDetailsVO,
			OperationalFlightVO toFlightVO) {
		log.entering(CLASS, "createToContainerVO");
		ContainerVO containerVO = new ContainerVO();
		containerVO.setCarrierCode(toFlightVO.getCarrierCode());
		containerVO.setCarrierId(toFlightVO.getCarrierId());
		containerVO.setFlightNumber(toFlightVO.getFlightNumber());
		containerVO.setFlightSequenceNumber(toFlightVO
				.getFlightSequenceNumber());
		containerVO.setFlightDate(toFlightVO.getFlightDate());
		containerVO.setLegSerialNumber(toFlightVO.getLegSerialNumber());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setPou(toFlightVO.getPou());
		log.exiting(CLASS, "createToContainerVO");
		return containerVO;

	}

	/**
	 * Method : MLDController.createOperationalFlightVO Added by : A-5526 on Dec
	 * 17, 2015 Used for : to createOperationalFlightVO Parameters : @param
	 * ContainerVO Parameters : @throws SystemException Return :
	 * OperationalFlightVO
	 * 
	 * @throws SystemException
	 */

	private OperationalFlightVO createOperationalFlightVO(
			ContainerVO containerVo) throws SystemException {
		log.entering(CLASS, "createOperationalFlightVO");
		LogonAttributes logon = (LogonAttributes) ContextUtils
				.getSecurityContext().getLogonAttributesVO();

		OperationalFlightVO operationalFlightVo = new OperationalFlightVO();
		operationalFlightVo.setCompanyCode(containerVo.getCompanyCode());
		operationalFlightVo.setCarrierId(containerVo.getCarrierId());
		operationalFlightVo.setFlightNumber(containerVo.getFlightNumber());
		operationalFlightVo.setFlightSequenceNumber(containerVo
				.getFlightSequenceNumber());
		if (containerVo.getPol() != null) {
			operationalFlightVo.setPol(containerVo.getPol());
		} else {
			operationalFlightVo.setPol(logon.getAirportCode());
		}
		operationalFlightVo
				.setLegSerialNumber(containerVo.getLegSerialNumber());
		operationalFlightVo.setFlightDate(containerVo.getFlightDate());
		operationalFlightVo.setCarrierCode(containerVo.getCarrierCode());
		log.exiting(CLASS, "createOperationalFlightVO");
		return operationalFlightVo;

	}

	/**
	 * Method : MLDController.triggerMLDMessages Added by : A-5526 on Dec 17,
	 * 2015 Used for : to triggerMLDMessages Parameters : @param String
	 * 
	 * @throws SystemException
	 */
	public void triggerMLDMessages(String companyCode,int recordCount) throws SystemException {
		log.entering(CLASS, "triggerMLDMessages");
		//Going to find all MLD details valid for send MLD messages
		Collection<MLDMasterVO> mldMasterVOs = new MLDMessageMaster()
				.findMLDDetails(companyCode,recordCount);
           //Going to send Mld messages
		sendMLDMessages(mldMasterVOs);
		log.exiting(CLASS, "triggerMLDMessages");
	}

	

	/**
	 * Method : MLDController.sendMLDMessages Added by : A-5526 on Dec 17, 2015
	 * Used for : to sendMLDMessages Parameters : @param Collection<MLDMasterVO>
	 * 
	 * @throws SystemException
	 */
	void sendMLDMessages(Collection<MLDMasterVO> mldMasterVOs) throws SystemException {
		log.entering(CLASS, "sendMLDMessages");
		if ((mldMasterVOs == null) || (mldMasterVOs.size() <= 0)) {
			return;
		}

		Map<String, Collection<MLDMasterVO>> eventModeCollections = new HashMap<>();
		// The below code is to group the MLD messages as part of eventCOde,flight and
		// ULD info's
		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();

			if ((MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())|| MailConstantsVO.MLD_ALL.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_UPL.equals(mldMasterVO.getEventCOde())|| MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())|| (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()) && mldDetailVO.getFlightNumberInb() == null))&& ((mldDetailVO.getFlightNumberOub() != null) || (mldDetailVO.getMailModeOub() != null

					

							
									&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeOub())
							&& (mldDetailVO.getCarrierCodeOub() != null && mldDetailVO.getCarrierIdOub() != 0)))) {

				String flightNumber = mldDetailVO.getFlightNumberOub() != null ? mldDetailVO.getFlightNumberOub()
						: "FFFF";

				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdOub()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberOub();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections,mldMasterVO,key);



								
									
									


			}
			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())
					&& (mldDetailVO.getFlightNumberInb() != null && mldDetailVO.getFlightSequenceNumberInb() != 0)) {
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ mldDetailVO.getFlightNumberInb() + mldDetailVO.getFlightSequenceNumberInb();
		if("U".equals(mldMasterVO.getTransactionLevel())){
					eventModeCollections = constructMLDMasterCollectionsForRCF(eventModeCollections,mldMasterVO,key);
					}
					else {
				eventModeCollections = constructMLDMasterCollections(eventModeCollections,mldMasterVO,key);
					}

			}
			if (((MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					&& ((mldDetailVO.getFlightNumberInb() != null && mldDetailVO.getFlightSequenceNumberInb() != 0)
							|| (MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())
									&& mldDetailVO.getCarrierCodeInb() != null)))) {
				String flightNumber = mldDetailVO.getFlightNumberInb() != null ? mldDetailVO.getFlightNumberInb()
						: "FFFF";
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberInb();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections,mldMasterVO,key);

			}
			if (MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())) {
				String flightNumber = mldDetailVO.getFlightNumberInb() != null ? mldDetailVO.getFlightNumberInb()
						: "FFFF";
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ flightNumber + mldDetailVO.getFlightSequenceNumberInb();
				if("U".equals(mldMasterVO.getTransactionLevel())){
				eventModeCollections = constructMLDMasterCollectionsForRCF(eventModeCollections,mldMasterVO,key);
				}
				else {
					eventModeCollections = constructMLDMasterCollections(eventModeCollections,mldMasterVO,key);
				}
			}
			if (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())
					&& (mldDetailVO.getFlightNumberOub() != null)) {
				String key = mldMasterVO.getEventCOde() + mldMasterVO.getSenderAirport()
						+ mldMasterVO.getReceiverAirport() + mldMasterVO.getUldNumber() + mldDetailVO.getCarrierIdInb()
						+ mldDetailVO.getFlightNumberOub() + mldDetailVO.getFlightSequenceNumberOub();
				eventModeCollections = constructMLDMasterCollections(eventModeCollections,mldMasterVO,key);
			}
		}

		// iterating hashmap
		for (Entry<String, Collection<MLDMasterVO>> modeVOs : eventModeCollections.entrySet()) {
			Collection<MLDMasterVO> mLDMasterVOs =  modeVOs.getValue();
			//After grouping going to create MLD Message VO
			MLDMessageVO mLDMessageVO = constructMLDMessageVO(mLDMasterVOs);
           // Triggering the call to message broker module to sent messages.
			mLDMessageVO.setMessageSubType(mLDMasterVOs.iterator().next().getMessageVersion());// Added for CRQ
																								// ICRD-135130 by A-8061
			Proxy.getInstance().get(MsgBrokerMessageProxy.class).encodeAndSaveMessage(mLDMessageVO);
		}
		

		log.exiting(CLASS, "sendMLDMessages");

	}
	


		

	/**
	 * Method : MLDController.updateMLDMessageSendStatus Added by : A-5526 on
	 * Dec 17, 2015 Used for : to updateMLDMessageSendStatus Parameters : @param
	 * MLDMasterVO
	 * 
	 * @throws SystemException
	 */
	private void updateMLDMessageSendStatus(MLDMasterVO mLDMasterVO)
			throws SystemException {
		log.entering(CLASS, "updateMLDMessageSendStatus");
		MLDMessageMaster mLDMessageMaster = null;
		MLDMessageMasterPK mLDMessageMasterPK = new MLDMessageMasterPK();
		mLDMessageMasterPK.setCompanyCode(mLDMasterVO.getCompanyCode());
		mLDMessageMasterPK.setSerialNumber(mLDMasterVO.getSerialNumber());
		try {
			mLDMessageMaster = MLDMessageMaster.find(mLDMessageMasterPK);
		} catch (FinderException e) {
			mLDMessageMaster = null;
		}

		if (mLDMessageMaster != null) {
			mLDMessageMaster.setProcessStatus("SND");
		}
		log.exiting(CLASS, "updateMLDMessageSendStatus");
	}

	/**
	 * Method : MLDController.constructMLDMessageVO Added by : A-5526 on Dec 17,
	 * 2015 Used for : to constructMLDMessageVO Parameters : @param
	 * Collection<MLDMasterVO> Return : MLDMessageVO
	 * 
	 * @throws SystemException
	 * 
	 */
	private MLDMessageVO constructMLDMessageVO(
			Collection<MLDMasterVO> mLDMasterVOs) throws SystemException {
		log.entering(CLASS, "constructMLDMessageVO");
		MLDMessageVO mLDMessageVO = new MLDMessageVO();
		MLDMasterVO mLDMasterVO = null;
		MLDMessageDetailVO mLDMessageDetailVOToTriger = null;
		if (mLDMasterVOs != null && mLDMasterVOs.size() > 0) {
			mLDMasterVO = mLDMasterVOs.iterator().next();

			mLDMessageVO.setCompanyCOde(mLDMasterVO.getCompanyCode());
			mLDMessageVO.setCompanyCode(mLDMasterVO.getCompanyCode());
			mLDMessageVO.setMessageVersion(mLDMasterVO.getMessageVersion());
			mLDMessageVO.setMessageSequence(mLDMasterVO.getMessageSequence());
			mLDMessageVO.setMessageType(MailConstantsVO.MLD);
			mLDMessageVO.setStationCode(mLDMasterVO.getSenderAirport());
			mLDMessageVO.setMessageStandard(MailConstantsVO.IMP);
			mLDMessageVO.setSenderID(findAirline(mLDMasterVO.getCompanyCode(),
					Integer.parseInt(mLDMasterVO.getAddrCarrier())));
			mLDMessageVO
					.setMldMessageDetailVOs(new ArrayList<MLDMessageDetailVO>());
			mLDMessageVO.setSerialNumber(mLDMasterVO.getSerialNumber());
			for (MLDMasterVO mLDMasterVOForTrigger : mLDMasterVOs) {
				MLDMessageDetailVO mldMessageDetailVO = new MLDMessageDetailVO();
				mLDMessageDetailVOToTriger = createMldMessageDetailVO(
						mLDMasterVOForTrigger, mldMessageDetailVO);
				if (mLDMessageDetailVOToTriger != null)
					{
						mLDMessageVO.getMldMessageDetailVOs().add(mLDMessageDetailVOToTriger);
						String count=generateCounter(mLDMasterVO.getCompanyCode());
						constructFileName(mLDMessageVO,count,mLDMasterVO);
					}
			}

		}
		log.exiting(CLASS, "constructMLDMessageVO");
		return mLDMessageVO;
	}

	/**
	 * Method : MLDController.createMldMessageDetailVO Added by : A-5526 on Dec
	 * 17, 2015 Used for : to createMldMessageDetailVO Parameters : @param
	 * MLDMasterVO,MLDMessageDetailVO Return : MLDMessageDetailVO
	 * 
	 * @throws SystemException
	 */

	private MLDMessageDetailVO createMldMessageDetailVO(
			MLDMasterVO mldMasterVO, MLDMessageDetailVO mldMessageDetailVO)
			throws SystemException {
		log.entering(CLASS, "createMldMessageDetailVO");

		MLDDetailVO mldDetailVO = null;

		MLDStatusMessageVO mldStatusMessageVO = new MLDStatusMessageVO();

		mldStatusMessageVO.setDestAirport(mldMasterVO.getDestAirport());
		mldStatusMessageVO.setEventCOde(mldMasterVO.getEventCOde());
		//Added as part of bug ICRD-151112 by A-5526 starts
		ContainerAssignmentVO containerAssignmentVO = null;
		if (mldMasterVO.getUldNumber() != null
				&& mldMasterVO.getUldNumber().trim().length() > 0) {
			
			containerAssignmentVO = new MailController().findLatestContainerAssignment(mldMasterVO.getUldNumber());
			
		}
		// Changed the setting of barcode type for ICRD-338876
		if(containerAssignmentVO!=null && MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())){      
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
			mldStatusMessageVO.setExpectedInd(MailConstantsVO.FLAG_YES);
		} else {
			mldStatusMessageVO.setExpectedInd(MailConstantsVO.FLAG_NO);
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL);
		}
		//Added as part of bug ICRD-151112 by A-5526 ends
		mldStatusMessageVO.setReasonCode("");
		mldStatusMessageVO.setReceiverAirport(mldMasterVO.getReceiverAirport());
		mldStatusMessageVO.setScanTime(mldMasterVO.getScanTime());
		mldStatusMessageVO.setSenderAirport(mldMasterVO.getSenderAirport());

		if ((mldMasterVO.getUldNumber() != null)
				&& (!mldMasterVO.getUldNumber().startsWith(MailConstantsVO.CONST_BULK))) {
			mldStatusMessageVO.setUldNumber(mldMasterVO.getUldNumber());
		}
		
		if((mldMasterVO.getUldNumber() != null) && MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())){
			mldStatusMessageVO.setUldNumber(mldMasterVO.getUldNumber());
		}

		//mldStatusMessageVO.setWeight(mldMasterVO.getWeight());
		mldStatusMessageVO.setWeight(String.valueOf(mldMasterVO.getWeight().getRoundedSystemValue()));//added by A-7371
		mldStatusMessageVO.setWeightCode(mldMasterVO.getWeightCode());
// Changed the setting of barcode type and value for ICRD-338876		
if((MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) || MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde()) || MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())) && MailConstantsVO.ULD_TYPE.equalsIgnoreCase(mldMasterVO.getTransactionLevel()) && mldMasterVO.getUldNumber()!=null &&mldMasterVO.getUldNumber().equals(mldMasterVO.getBarcodeValue())){     
			mldMessageDetailVO.setBarcodeValue(mldMasterVO.getUldNumber());
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
		} else{
		mldMessageDetailVO.setBarcodeType(MailConstantsVO.MLD_BARCODETYPE_FOR_MAIL_LEVEL);
		mldMessageDetailVO.setBarcodeValue(mldMasterVO.getBarcodeValue());
		}
		mldMessageDetailVO.setMldStatusMessageVO(mldStatusMessageVO);
		
		
		//Added for CRQ ICRD-135130 by A-8061 starts
		if(MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())&& mldMasterVO.getBarcodeValue() != null
				&& mldMasterVO.getBarcodeValue().trim().length()==9){
			mldMessageDetailVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
		}
			
		//Added for CRQ ICRD-135130 by A-8061 end
	

		mldDetailVO = mldMasterVO.getMldDetailVO();
		String temp = null;

		if (mldDetailVO != null) {
			//Construction INB details
			MLDFlownDetailMessageVO mldFlownDetailMessageVO = new MLDFlownDetailMessageVO();

			mldFlownDetailMessageVO
					.setCarrierCode(findAirline(mldMasterVO.getCompanyCode(),
							mldDetailVO.getCarrierIdInb()));
			mldFlownDetailMessageVO.setEventTime(mldDetailVO.getEventTimeInb());
			mldFlownDetailMessageVO.setFlightNumber(mldDetailVO
					.getFlightNumberInb());
			mldFlownDetailMessageVO.setFlightOperationDate(mldDetailVO
					.getFlightOperationDateInb());

			if ((mldDetailVO.getFlightNumberInb() != null)
					&& (!"".equals(mldDetailVO.getFlightNumberInb()))) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_FLIGHT);
				temp = findAirline(mldMasterVO.getCompanyCode(),
						mldDetailVO.getCarrierIdInb())
						+ mldDetailVO.getFlightNumberInb();
			} else if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())) {

				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null
						&& mldMasterVO.getBarcodeValue().trim().length() == 29)
					{
					temp = mldMasterVO.getBarcodeValue().substring(0, 6);
					}

			}
			//Added for CRQ ICRD-135130 by A-8061 starts
			else if (MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde())||MailConstantsVO.MLD_NST.equals(mldMasterVO.getEventCOde())
					||MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())) {

				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null
						&& mldMasterVO.getBarcodeValue().trim().length() == 29)
					{
					temp = mldMasterVO.getBarcodeValue().substring(0, 6);
					}

			}
			//Added for CRQ ICRD-135130 by A-8061 end
			
			
			if (MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())) {
				mldFlownDetailMessageVO.setMailMode(mldDetailVO
						.getMailModeInb());
				temp = findAirline(mldMasterVO.getCompanyCode(),
						mldDetailVO.getCarrierIdInb());
			}
			//Added for CRQ ICRD-135130 by A-8061 starts
			if ((MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					&& MailConstantsVO.MLD_MODE_CARRIER.equals(mldDetailVO.getMailModeInb())) {
				mldFlownDetailMessageVO.setMailMode(mldDetailVO
						.getMailModeInb());
				temp = findAirline(mldMasterVO.getCompanyCode(),
						mldDetailVO.getCarrierIdInb());
			}
			if (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())||MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = mldFlownDetailMessageVO.getCarrierCode();
			}

			if(mldMasterVO.getMessageVersion()!=null && "2".equals(mldMasterVO.getMessageVersion())){
				if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())){
				//to get pa
				String ooe="";
				if (mldMasterVO.getBarcodeValue() != null
						&& mldMasterVO.getBarcodeValue().trim().length() == 29)
					{
					ooe = mldMasterVO.getBarcodeValue().substring(0, 6);
					}
				 temp=findPostalOperatorCode(mldMasterVO.getCompanyCode(),ooe);
				}
				
			}
			//Added for CRQ ICRD-135130 by A-8061 end

			
			mldFlownDetailMessageVO.setModeDescription(temp);
			mldFlownDetailMessageVO.setOperationMode(MailConstantsVO.MLD_OPERATION_INBOUND_MODE);
			mldFlownDetailMessageVO.setPolOrPou(mldDetailVO.getPolInb());
			mldFlownDetailMessageVO.setPostalCode(mldDetailVO
					.getPostalCodeInb());
			if ((mldDetailVO.getFlightNumberInb() == null)
					|| ("".equals(mldDetailVO.getFlightNumberInb()))) {
				mldFlownDetailMessageVO.setPolOrPou(mldMasterVO
						.getSenderAirport());

			}
			if ((mldDetailVO.getFlightNumberInb() != null)
					&& (!"".equals(mldDetailVO.getFlightNumberInb()))) {
				mldMessageDetailVO
						.setInbMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			} else if (MailConstantsVO.MLD_REC.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_HND.equals(mldMasterVO.getEventCOde())|| MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_RCT.equals(mldMasterVO.getEventCOde())
					|| MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())) {
				mldMessageDetailVO
						.setInbMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}

//Constructing OUB details
			mldFlownDetailMessageVO = new MLDFlownDetailMessageVO();
			mldFlownDetailMessageVO
					.setCarrierCode(findAirline(mldMasterVO.getCompanyCode(),
							mldDetailVO.getCarrierIdOub()));
			mldFlownDetailMessageVO.setEventTime(mldDetailVO.getEventTimeOub());
			mldFlownDetailMessageVO.setFlightNumber(mldDetailVO
					.getFlightNumberOub());
			mldFlownDetailMessageVO.setFlightOperationDate(mldDetailVO
					.getFlightOperationDateOub());

			if ((mldDetailVO.getFlightNumberOub() != null)
					&& (!"".equals(mldDetailVO.getFlightNumberOub()))
					&& mldDetailVO.getFlightSequenceNumberOub() > 0) {

				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_FLIGHT);
				temp = findAirline(mldMasterVO.getCompanyCode(),
						mldDetailVO.getCarrierIdOub())
						+ mldDetailVO.getFlightNumberOub();
			} else {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = findAirline(mldMasterVO.getCompanyCode(),
						mldDetailVO.getCarrierIdOub());
			}

			mldFlownDetailMessageVO.setModeDescription(temp);
			mldFlownDetailMessageVO.setOperationMode(MailConstantsVO.MLD_OPERATION_OUTBOUND_MODE);

			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())) {

				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				if (mldMasterVO.getBarcodeValue() != null
						&& mldMasterVO.getBarcodeValue().trim().length() == 29)
					{
					temp = mldMasterVO.getBarcodeValue().substring(6, 12);
					}
				mldFlownDetailMessageVO.setModeDescription(temp);

			}
			
			//Added for CRQ ICRD-135130 by A-8061 starts
			if (MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde())) {
				mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_CARRIER);
				temp = mldFlownDetailMessageVO.getCarrierCode();
				mldFlownDetailMessageVO.setModeDescription(temp);
			}
			
			if(mldMasterVO.getMessageVersion()!=null && "2".equals(mldMasterVO.getMessageVersion())){
				if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())||MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())){
				//to get pa
				String ooe="";
				if (mldMasterVO.getBarcodeValue() != null
						&& mldMasterVO.getBarcodeValue().trim().length() == 29)
					
					{
					if (MailConstantsVO.MLD_RET.equals(mldMasterVO.getEventCOde())){
					ooe = mldMasterVO.getBarcodeValue().substring(0, 6);
						} else {
						ooe = mldMasterVO.getBarcodeValue().substring(6, 12);
						}
					}
				 temp=findPostalOperatorCode(mldMasterVO.getCompanyCode(),ooe);
				 mldFlownDetailMessageVO.setModeDescription(temp);
				 mldFlownDetailMessageVO.setMailMode(MailConstantsVO.MLD_MODE_POSTAL_AUTHORITY);
				}
				
			}
			//Added for CRQ ICRD-135130 by A-8061 end
			

			mldFlownDetailMessageVO.setPolOrPou(mldDetailVO.getPouOub());
			mldFlownDetailMessageVO.setPostalCode(mldDetailVO
					.getPostalCodeOub());
			if ((mldDetailVO.getFlightNumberOub() != null)
					&& (!"".equals(mldDetailVO.getFlightNumberOub()))) {
				mldMessageDetailVO
						.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			if (MailConstantsVO.MLD_DLV.equals(mldMasterVO.getEventCOde())) {
				mldMessageDetailVO
						.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			
			//Added for CRQ ICRD-135130 by A-8061 starts
			if (((MailConstantsVO.MLD_TFD.equals(mldMasterVO.getEventCOde()))
					|| (MailConstantsVO.MLD_STG.equals(mldMasterVO
							.getEventCOde()))
					|| (MailConstantsVO.MLD_RCT.equals(mldMasterVO
							.getEventCOde())) || (MailConstantsVO.MLD_RET
						.equals(mldMasterVO.getEventCOde())))
					&& (mldDetailVO.getFlightNumberOub() != null || mldDetailVO
							.getCarrierIdOub() > 0)) {
				mldMessageDetailVO
						.setOubMLDFlownDetailMessageVO(mldFlownDetailMessageVO);
			}
			//Added for CRQ ICRD-135130 by A-8061 end
			
			// Changed the setting of barcode value and type for ICRD-338876
			if(MailConstantsVO.MLD_RCF.equals(mldMasterVO.getEventCOde()) && containerAssignmentVO!=null && MailConstantsVO.ULD_TYPE.equals(containerAssignmentVO.getContainerType())){     
				mldMasterVO.setBarcodeType(MailConstantsVO.ULD_TYPE);
				mldMasterVO.setBarcodeValue(mldMasterVO.getUldNumber());
		}
			
		}

		log.exiting(CLASS, "createMldMessageDetailVO");
		return mldMessageDetailVO;
	}
/**
 * @author A-8061
 * @param companyCode
 * @param ooe
 * @return
 */
	private String findPostalOperatorCode(String companyCode, String ooe)  {

				String paCode="";
				String partyIdentifier="";
			
					try {

						paCode = new MailController().findPAForOfficeOfExchange(companyCode,  ooe);
						
						if(paCode!=null && !"".equals(paCode)){
						PostalAdministrationVO postalAdministrationVO= new MailController().findPACode(companyCode, paCode);
						if(Objects.nonNull(postalAdministrationVO)
								&& Objects.nonNull(postalAdministrationVO.getPostalAdministrationDetailsVOs())
								&& !postalAdministrationVO.getPostalAdministrationDetailsVOs().isEmpty()){
						partyIdentifier = getPartyIdentifierForPA(postalAdministrationVO);
							}
						}
						
					} catch (SystemException e) {
						log.log(Log.INFO, " error msg \n\n ", e.getMessage());
					}

		return partyIdentifier;
	}

	private String getPartyIdentifierForPA(PostalAdministrationVO postalAdministrationVO) {
		Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs;
		String partyIdentifier = "";
		postalAdministrationDetailsVOs = postalAdministrationVO.getPostalAdministrationDetailsVOs().get("INVINFO");
		if (postalAdministrationDetailsVOs != null && !postalAdministrationDetailsVOs.isEmpty()) {
			for (PostalAdministrationDetailsVO postalAdministrationDetailsVO : postalAdministrationDetailsVOs) {
				if ("UPUCOD".equals(postalAdministrationDetailsVO.getParCode())
				  &&postalAdministrationDetailsVO.getPartyIdentifier()!=null ) {
					partyIdentifier = postalAdministrationDetailsVO.getPartyIdentifier();
				}
			}
		}
		return partyIdentifier;
	}
	/**
	 * Method : MLDController.findAirline Added by : A-5526 on Dec 17, 2015 Used
	 * for : to findAirline Parameters : @param String,int Return : String
	 * 
	 * @throws SystemException
	 */
	private String findAirline(String companyCode, int carrierId)
			throws SystemException {
		log.entering(CLASS, "findAirline");
		AirlineValidationVO airlineValidationVO = null;
		String carrierCode = "";
		if (carrierId > 0) {
			try {
				airlineValidationVO = Proxy.getInstance().get(SharedAirlineProxy.class).findAirline(
						companyCode, carrierId);
			} catch (SharedProxyException sharedProxyException) {
				throw new SystemException(sharedProxyException.getMessage());
			}
			if (airlineValidationVO != null) {
				carrierCode = airlineValidationVO.getAlphaCode();
			}
		}
		log.exiting(CLASS, "findAirline");
		return carrierCode;

	}
	/**
	 * Added as part of bug ICRD-144653 by A-5526
	 * This method is to set flight date with time as STD / ATA
	 * @author A-5526
	 * @param operationMode
	 * @param mldDetailVO
	 * @throws SystemException
	 */
	private void updateFlightDateWithScheduledTime(
			String operationMode, MLDDetailVO mldDetailVO,String airportCode) throws SystemException {
		Collection<FlightValidationVO> flightVOs=null;
		
		FlightFilterVO	flightFilterVO=new FlightFilterVO();      
		flightFilterVO.setCompanyCode(mldDetailVO.getCompanyCode());
		flightFilterVO.setDirection(operationMode);  
		flightFilterVO.setPageNumber(1);      
			if(MailConstantsVO.OPERATION_INBOUND.equals(operationMode)){
				flightFilterVO.setFlightNumber(mldDetailVO.getFlightNumberInb());
				flightFilterVO.setFlightSequenceNumber(mldDetailVO.getFlightSequenceNumberInb());
				flightFilterVO.setFlightCarrierId(mldDetailVO.getCarrierIdInb());
			}
			else{
				flightFilterVO.setFlightNumber(mldDetailVO.getFlightNumberOub());
				flightFilterVO.setFlightSequenceNumber(mldDetailVO.getFlightSequenceNumberOub());
				flightFilterVO.setFlightCarrierId(mldDetailVO.getCarrierIdOub());
				if(airportCode!=null){
				flightFilterVO.setAirportCode(airportCode);
				flightFilterVO.setStation(airportCode);
				}
			}
		
			FlightValidationVO flightDetailVO=null;
			flightVOs=new MailController().validateFlight(flightFilterVO);
		
		if(flightVOs!=null && flightVOs.size()>0){
			flightDetailVO=flightVOs.iterator().next();
			if(flightDetailVO!=null){
				if(MailConstantsVO.OPERATION_INBOUND.equals(operationMode))
				{
					//Modified by A-7794 as part of ICRD-268613
				mldDetailVO.setFlightOperationDateInb(flightDetailVO.getSta());
				}
				else
					{
					//Modified by A-7794 as part of ICRD-268613
					mldDetailVO.setFlightOperationDateOub(flightDetailVO.getStd());      
					if(mldDetailVO.getPouOub()==null) {
					mldDetailVO.setPouOub(flightDetailVO.getLegDestination());
					}
			}
		}
		}
		
		
	}
	/**
	 * Added as part of bug ICRD-152670 by A-5526
	 * This method is to find flight segment Origin station 
	 * @author A-5526
	 * @param toContainerVO 
	 * @param eventCode 
	 * @param operationMode
	 * @param mldDetailVO
	 * @throws SystemException
	 */
	
	private void findSenderOrReceiverAirportCodes(MailbagVO mailbagVO,
			ContainerVO toContainerVO, MLDMasterVO mldMasterVO, String eventCode) throws SystemException {
		
		
		
		Collection<FlightSegmentSummaryVO> flightSegments = null;
		Collection<FlightSegmentSummaryVO> flightSegmentsTemp = new ArrayList<FlightSegmentSummaryVO>();
		
		
		
			if(MailConstantsVO.MLD_ALL.equals(eventCode) && toContainerVO!=null)        
			{
				flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
						mailbagVO.getCompanyCode(),
						toContainerVO.getCarrierId(),
						toContainerVO.getFlightNumber(),
						toContainerVO.getFlightSequenceNumber());
			}else{
				flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
						mailbagVO.getCompanyCode(),
						mailbagVO.getCarrierId(),
						mailbagVO.getFlightNumber(),
						mailbagVO.getFlightSequenceNumber());
			}
			BeanHelper.copyProperties(flightSegmentsTemp, flightSegments);	
			
			Collection<String> routes = new ArrayList<String>();
			ArrayList<FlightSegmentSummaryVO> segmentsTemp = (ArrayList<FlightSegmentSummaryVO>) flightSegments;
			int segmentsTempLength=segmentsTemp.size();
			 if(segmentsTempLength>0){
			
			routes.add(segmentsTemp.get(0).getSegmentOrigin());
			routes.add(segmentsTemp.get(0).getSegmentDestination());
			}
			 if(segmentsTempLength>1){    
			
			routes.add(segmentsTemp.get(1).getSegmentDestination());
			}
			
			if(flightSegments!=null)
			{
			for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
							if (MailConstantsVO.MLD_HND.equals(eventCode) && segmentSummaryVO.getSegmentDestination().equals(mailbagVO.getScannedPort())) {
					for (String route : routes) {
						if (route.equalsIgnoreCase(mailbagVO.getScannedPort())) {
													mldMasterVO.setSenderAirport(segmentSummaryVO.getSegmentOrigin());
											}
									}
							}
							if (MailConstantsVO.MLD_ALL.equals(eventCode) && segmentSummaryVO.getSegmentOrigin().equals(mailbagVO.getScannedPort())) {
					for (String route : routes) {
						if (route.equalsIgnoreCase(mailbagVO.getScannedPort())) {
													mldMasterVO.setReceiverAirport(segmentSummaryVO.getSegmentDestination());
											}
									}
							}
					}
			}

		 
	}
	/**
	  * 
	  * 	Method		:	MLDController.createMLDVOsForVersion2
	  *	Added by 	:	A-10647 on 24-Feb-2022
	  * 	Used for 	:
	  *	Parameters	:	@param mailbagVO
	  *	Parameters	:	@param toContainerVO
	  *	Parameters	:	@param mldVersion
	  *	Parameters	:	@param mode
	  *	Parameters	:	@param airport
	  *	Parameters	:	@param carrier
	  *	Parameters	:	@param carrierID
	  *	Parameters	:	@param logon
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	MLDMasterVO
	  */
	 MLDMasterVO createMLDVOsForVersion2(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion,
			String mode, String airport,LogonAttributes logon,MLDMasterVO mldMasterVO) throws SystemException {
		MLDDetailVO mldDetailVO = null;
		String mldEventMode = mode;
		if(mode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL) || mode.equalsIgnoreCase(MLD_REC_HND) 
				|| mode.equalsIgnoreCase(MailConstantsVO.MLD_UPL) || mode.equalsIgnoreCase(MailConstantsVO.MLD_HND) ||
				mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)) {
			return null;
		}
		mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
		mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		mldMasterVO.setScanTime(mailbagVO.getScannedDate());
		mldMasterVO.setSenderAirport(airport);
		if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
		}
		String destination = "";
		if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().trim().length() == 29) {
			destination = new MailUploadController().findAirportCityForMLD(mailbagVO.getCompanyCode(),
					mailbagVO.getMailbagId().substring(8, 11));
			mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,
					Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29))));// added by A-7371
		}
		if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
			mldMasterVO.setReceiverAirport(destination);
		}
		mldMasterVO.setDestAirport(destination);
		
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setMessageVersion(mldVersion);
		mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldDetailVO.setMailIdr(mailbagVO.getMailbagId());
		mldDetailVO.setMessageSequence(1);
		
		if ((MailConstantsVO.MLD_REC.equals(mode)) || (MailConstantsVO.MLD_NST.equals(mode))
				|| (MailConstantsVO.MLD_STG.equals(mode)) || (MailConstantsVO.MLD_RCT.equals(mode))
				|| (MailConstantsVO.MLD_RET.equals(mode) || (MailConstantsVO.MLD_TFD.equals(mode)))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			mldMasterVO = findParametersForVersion2( mldMasterVO, mode, mailbagVO, logon, toContainerVO);
			if(mode.equalsIgnoreCase(MailConstantsVO.MLD_RCT) && mldMasterVO==null) {
				return null;
			}
			mldDetailVO = mldMasterVO.getMldDetailVO();
		}
		else {
			if (MailConstantsVO.MLD_DLV.equals(mode) || (MailConstantsVO.MLD_RCF.equals(mode))) {
			mldMasterVO.setSenderAirport(airport);
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			mldMasterVO.setDestAirport(destination);
			mldMasterVO.setMessagingMode("O");
			mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
			mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
			mldDetailVO.setFlightSequenceNumberInb(mailbagVO.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND, mldDetailVO);
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldDetailVO.setMailModeInb("F");
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			}
		} 
		if ((MailConstantsVO.MLD_RCF.equals(mode) || MailConstantsVO.MLD_DLV.equals(mode))){
			if(mailbagVO.getTransactionLevel()!=null &&
					MailConstantsVO.ULD_LEVEL_TRANSACTION.equals(mailbagVO.getTransactionLevel())) {
				mldMasterVO.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
				mldMasterVO.setBarcodeValue(mailbagVO.getContainerNumber());
			}
			else {
				mldMasterVO.setTransactionLevel(MailConstantsVO.MAILBAG_LEVEL_TRANSACTION);
			}
		}
		if ((MailConstantsVO.MLD_RCF.equals(mode) || MailConstantsVO.MLD_DLV.equals(mode)) && mldMasterVO.getTransactionLevel()!=null
				&& MailConstantsVO.MAILBAG_LEVEL_TRANSACTION.equals(mldMasterVO.getTransactionLevel())) {
			mldMasterVO.setUldNumber(null);
			}
			else if ((MailConstantsVO.MLD_STG.equals(mode) || MailConstantsVO.MLD_NST.equals(mode)) && toContainerVO!=null) {
         mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
			}
			else {
		mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}
	 /**
	  * 
	  * 	Method		:	MLDController.createMLDVOsForVersion1
	  *	Added by 	:	A-10647 on 24-Feb-2022
	  * 	Used for 	:
	  *	Parameters	:	@param mailbagVO
	  *	Parameters	:	@param toContainerVO
	  *	Parameters	:	@param mldVersion
	  *	Parameters	:	@param mode
	  *	Parameters	:	@param airport
	  *	Parameters	:	@param carrier
	  *	Parameters	:	@param carrierID
	  *	Parameters	:	@param logon
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	MLDMasterVO
	  */
	 MLDMasterVO createMLDVOsForVersion1(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion,
			String mode, String airport,LogonAttributes logon,MLDMasterVO mldMasterVO) throws SystemException {
		String mldEventMode = mode;
		if(mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL)|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)||
				mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_REC)|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_HND)||
				mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_UPL)||mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_DLV)||
				mldEventMode.equalsIgnoreCase(MLD_REC_HND)) {
			mldMasterVO = findParametersForVersion1( mailbagVO,  toContainerVO,  mldVersion,
			 mode,  airport,  logon,mldMasterVO);
			return mldMasterVO;
		} else {
			return null;

		}
			
		
	}
	 /**
	  * 
	  * 	Method		:	MLDController.findAirportForMLDMode
	  *	Added by 	:	A-10647 on 04-Mar-2022
	  * 	Used for 	:
	  *	Parameters	:	@param mailbagVO
	  *	Parameters	:	@param mode
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	String
	  */
	
	public String findAirportForMLDMode(MailbagVO mailbagVO,String mode) throws SystemException {
		String airport ="";
		if (MailConstantsVO.MLD_DLV.equals(mode) || (MailConstantsVO.MLD_HND.equals(mode))
				|| (MailConstantsVO.MLD_RCF.equals(mode))) {
			if (mailbagVO.getPol() != null) {
				airport = mailbagVO.getPol();
			} else {
				ContainerAssignmentVO containerAssignmentVO = null;
				containerAssignmentVO = new MailController()
						.findLatestContainerAssignment(mailbagVO.getContainerNumber());
				if (containerAssignmentVO != null
						&& (containerAssignmentVO.getFlightNumber() != null
								&& containerAssignmentVO.getFlightNumber().equals(mailbagVO.getFlightNumber()))
						&& containerAssignmentVO.getFlightSequenceNumber() == mailbagVO.getFlightSequenceNumber()) {
					airport = containerAssignmentVO.getAirportCode();
				} else {
					airport = findAirportForEmptyContainer(mailbagVO);
				}
			}
		} else {
			airport = mailbagVO.getScannedPort();
		}
		return airport;
	
}
	/**
	 * 
	 * 	Method		:	MLDController.findCarrierCodeForMLDMode
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param mode
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@param logon
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String findCarrierCodeForMLDMode(MailbagVO mailbagVO,String mode,ContainerVO toContainerVO,LogonAttributes logon) {
		String carrier ="";
	if ((MailConstantsVO.MLD_ALL.equals(mode) && toContainerVO!=null) || ((MailConstantsVO.MLD_STG.equals(mode) && toContainerVO != null)
			|| (MailConstantsVO.MLD_NST.equals(mode) && toContainerVO != null))) {
		carrier = toContainerVO.getCarrierCode();
	} else if (MailConstantsVO.MLD_UPL.equals(mode)) {
		carrier = mailbagVO.getCarrierCode();
	} else {
		carrier = mailbagVO.getCarrierCode();
	}
	return carrier;
	}
	/**
	 * 
	 * 	Method		:	MLDController.findAirportForEmptyContainer
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	
	public String findAirportForEmptyContainer(MailbagVO mailbagVO) throws SystemException {
		String airport ="";
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setStation(mailbagVO.getScannedPort());
		flightFilterVO.setFlightDate(mailbagVO.getFlightDate());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		Collection<FlightValidationVO> flightValidationVOs = new MailController()
				.validateFlight(flightFilterVO);
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			for (FlightValidationVO flightValidationVO : flightValidationVOs) {
				Collection<FlightSegmentSummaryVO> flightSegments = null;
				try {
					flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
							flightValidationVO.getCompanyCode(),
							flightValidationVO.getFlightCarrierId(),
							flightValidationVO.getFlightNumber(),
							flightValidationVO.getFlightSequenceNumber());
				} catch (SystemException e) {
					
					log.log(Log.FINE, e);
				}
				airport=findAirportFromFlightSegment(flightSegments,mailbagVO);

				
			}
		}
		return airport;
	}
	/**
	 * 
	 * 	Method		:	MLDController.findAirportFromFlightSegment
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param flightSegments
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String findAirportFromFlightSegment(Collection<FlightSegmentSummaryVO> flightSegments,MailbagVO mailbagVO) {
		String airport ="";
		if (flightSegments != null && !flightSegments.isEmpty()) {
			for (FlightSegmentSummaryVO segmentVo : flightSegments) {
				if (segmentVo.getSegmentDestination().equals(mailbagVO.getScannedPort())) {
					airport = segmentVo.getSegmentOrigin();
					if (mailbagVO.getPol()==null){
					mailbagVO.setPol(segmentVo.getSegmentOrigin());
					}
				}
			}
		}
		return airport;
	}
	/**
	 * 
	 * 	Method		:	MLDController.findParametersForVersion2
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mldMasterVO
	 *	Parameters	:	@param mode
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param logon
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDMasterVO
	 */
	
	public MLDMasterVO findParametersForVersion2(MLDMasterVO mldMasterVO,String mode,MailbagVO mailbagVO,LogonAttributes logon,ContainerVO toContainerVO) throws SystemException {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
			if(MailConstantsVO.MLD_RCT.equals(mode) || (MailConstantsVO.MLD_TFD.equals(mode))) {
			if ( (MailConstantsVO.MLD_RCT.equals(mode) || (MailConstantsVO.MLD_TFD.equals(mode))) && (mailbagVO.getTransferFromCarrier() != null)
					&& (mailbagVO.getTransferFromCarrier().trim().length() > 0)) {
				mldDetailVO.setCarrierCodeInb(mailbagVO.getTransferFromCarrier());
				mldDetailVO.setCarrierIdInb(
						findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getTransferFromCarrier()));
			} else {
				return null;
			}
			}
		
		if (((MailConstantsVO.MLD_STG.equals(mode) && toContainerVO != null)
				|| (MailConstantsVO.MLD_NST.equals(mode) && toContainerVO != null))) {
			mldDetailVO =findDetailVOForSTGAndNST( mldDetailVO, mailbagVO, logon, toContainerVO);
			mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
		} else {
			mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
			mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getCarrierCode()));
			mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberOub(mailbagVO.getFlightSequenceNumber());
			
			mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
			mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
			mldDetailVO.setPouOub(mailbagVO.getPou());
			updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,mldMasterVO.getSenderAirport());
			if (mailbagVO.getFlightSequenceNumber() > 0) {
				mldDetailVO.setMailModeOub("F");
			} else {
				mldDetailVO.setMailModeOub("H");
			}
		}
		mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());
		if (MailConstantsVO.MLD_RET.equals(mode)) {
			findDetailVOForRET( mldDetailVO, mailbagVO, logon);
			mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	
	}
	/**
	 * 
	 * 	Method		:	MLDController.findDetailVOForSTGAndNST
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mldDetailVO
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param logon
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDDetailVO
	 */
	public MLDDetailVO findDetailVOForSTGAndNST(MLDDetailVO mldDetailVO,MailbagVO mailbagVO,LogonAttributes logon,ContainerVO toContainerVO) throws SystemException {
		mldDetailVO.setCarrierCodeOub(toContainerVO.getCarrierCode());
		mldDetailVO.setFlightNumberOub(toContainerVO.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(toContainerVO.getFlightSequenceNumber());
		mldDetailVO.setCarrierIdOub(toContainerVO.getCarrierId());
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,mailbagVO.getScannedPort());
		mldDetailVO.setPouOub(toContainerVO.getPou());
		if (mailbagVO.getScannedDate() != null) {
			mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
		} else {
			mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
		}
		mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
		if (toContainerVO.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		return mldDetailVO;
	}
	/**
	 * 
	 * 	Method		:	MLDController.findDetailVOForRET
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mldDetailVO
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param logon
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDDetailVO
	 */
	public MLDDetailVO findDetailVOForRET(MLDDetailVO mldDetailVO,MailbagVO mailbagVO,LogonAttributes logon)  {
		mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
		if (mailbagVO.getDamagedMailbags() != null && !mailbagVO.getDamagedMailbags().isEmpty()) {
			Collection<DamagedMailbagVO> damagedMailBags = mailbagVO.getDamagedMailbags();
			DamagedMailbagVO damagedMailbagVO = damagedMailBags.iterator().next();
				mldDetailVO.setPostalCodeOub(damagedMailbagVO.getPaCode());
		}
		return mldDetailVO;
	}
	/**
	 * 
	 * 	Method		:	MLDController.findMasterVOForVersion1
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mldMasterVO
	 *	Parameters	:	@param mode
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param logon
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDMasterVO
	 */
	
	public MLDMasterVO findMasterVOForVersion1(MLDMasterVO mldMasterVO,String mode,ContainerVO toContainerVO,MailbagVO mailbagVO,LogonAttributes logon) throws SystemException {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		if (MailConstantsVO.MLD_ALL.equals(mode) && toContainerVO!=null) {

			mldMasterVO.setUldNumber(toContainerVO.getContainerNumber());
				
			

				mldDetailVO.setCarrierCodeOub(toContainerVO.getCarrierCode());
		
			mldDetailVO.setFlightNumberOub(toContainerVO.getFlightNumber());
			mldDetailVO.setFlightSequenceNumberOub(toContainerVO.getFlightSequenceNumber());
				updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
				mldDetailVO.setCarrierIdOub(toContainerVO.getCarrierId());
			
				mldDetailVO.setPouOub(toContainerVO.getPou());
				if (mailbagVO.getScannedDate() != null) {
					mldDetailVO.setEventTimeOub(mailbagVO.getScannedDate());
				} else {
					mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(),ARP,true));  
				}
				mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(),ARP,true));      

				if (toContainerVO.getFlightSequenceNumber() > 0) {
					mldDetailVO.setMailModeOub("F");

				} else {
					mldDetailVO.setMailModeOub("H");

				}
			} else {
				mldDetailVO = findMLDDetailVOForModeOtherThanALL( mldDetailVO, mode, logon, mailbagVO);
			}
			mldDetailVO.setPostalCodeOub(mailbagVO.getPaCode());

		if (MLD_REC_HND.equals(mldMasterVO.getEventCOde()) && mailbagVO.getTransferFromCarrier() != null
					&& mailbagVO.getTransferFromCarrier().trim().length() > 0) {
				mldDetailVO.setMailModeInb("H");
				mldMasterVO.setEventCOde(MailConstantsVO.MLD_HND);

			mldDetailVO.setCarrierCodeInb(mailbagVO.getTransferFromCarrier());
			mldDetailVO.setCarrierIdInb(
					findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getTransferFromCarrier()));

		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	

	
	}
	/**
	 * 
	 * 	Method		:	MLDController.findMLDDetailVOForModeOtherThanALL
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mldDetailVO
	 *	Parameters	:	@param mode
	 *	Parameters	:	@param logon
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDDetailVO
	 */
	public MLDDetailVO findMLDDetailVOForModeOtherThanALL(MLDDetailVO mldDetailVO,String mode,LogonAttributes logon,MailbagVO mailbagVO) throws SystemException {


		if (MailConstantsVO.MLD_UPL.equals(mode)) {

			mldDetailVO.setCarrierCodeOub(logon.getCompanyCode());
		mldDetailVO.setCarrierIdOub(
				findCarrierIdentifier(logon.getCompanyCode(), logon.getOwnAirlineCode()));
		}else{
		mldDetailVO.setCarrierCodeOub(mailbagVO.getCarrierCode());
		mldDetailVO.setCarrierIdOub(
				findCarrierIdentifier(logon.getCompanyCode(), mailbagVO.getCarrierCode()));
		} 
		mldDetailVO.setFlightNumberOub(mailbagVO.getFlightNumber());
	
	mldDetailVO.setFlightSequenceNumberOub(mailbagVO.getFlightSequenceNumber());
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
		mldDetailVO.setCarrierIdOub(mailbagVO.getCarrierId());
		mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(),ARP,true));          
		
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);   
		mldDetailVO.setPouOub(mailbagVO.getPou());
		if (mailbagVO.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");

		} else {
			mldDetailVO.setMailModeOub("H");

		}
		return mldDetailVO;
	
	}
	/**
	 * 
	 * 	Method		:	MLDController.findParametersForVersion1
	 *	Added by 	:	A-10647 on 04-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@param toContainerVO
	 *	Parameters	:	@param mldVersion
	 *	Parameters	:	@param mode
	 *	Parameters	:	@param airport
	 *	Parameters	:	@param logon
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	MLDMasterVO
	 */
	public MLDMasterVO findParametersForVersion1(MailbagVO mailbagVO, ContainerVO toContainerVO, String mldVersion,
			String mode, String airport, LogonAttributes logon,MLDMasterVO mldMasterVO ) throws SystemException {
		
		String mldEventMode = mode;
		mldMasterVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldMasterVO.setBarcodeValue(mailbagVO.getMailbagId());
		mldMasterVO.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		if(MailConstantsVO.MLD_FRESH_ALL.equals(mldEventMode)){
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);      
			mode=MailConstantsVO.MLD_REC;
		}
		if(MLD_REC_HND.equals(mldEventMode)){
			mldMasterVO.setEventCOde(MLD_REC_HND);
			mode=MailConstantsVO.MLD_REC;
		}
		mldMasterVO.setScanTime(mailbagVO.getScannedDate());
		mldMasterVO.setSenderAirport(mailbagVO.getScannedPort());  
		if(MailConstantsVO.MLD_ALL.equals(mode)){
			if(toContainerVO!=null){
				findSenderOrReceiverAirportCodes(mailbagVO,toContainerVO,mldMasterVO,mode);
		} else {
				findSenderOrReceiverAirportCodes(mailbagVO,null,mldMasterVO,mode);
			}
		}
		
	if (mldMasterVO.getReceiverAirport() == null || mldMasterVO.getReceiverAirport().isEmpty()) {
		mldMasterVO.setReceiverAirport(mailbagVO.getPou());
		}
		String destination = "";
	if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().trim().length() == 29) {
		destination = new MailUploadController().findAirportCityForMLD(mailbagVO.getCompanyCode(),
				mailbagVO.getMailbagId().substring(8, 11));

		mldMasterVO.setWeight(new Measure(UnitConstants.MAIL_WGT,
				Double.parseDouble(mailbagVO.getMailbagId().substring(25, 29))));// added by A-7371
		}
		     
		mldMasterVO.setDestAirport(destination);
		mldMasterVO.setUldNumber(mailbagVO.getContainerNumber());
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setWeightCode("KG");
		mldMasterVO.setMessageVersion(mldVersion);
		MLDDetailVO mldDetailVO = new MLDDetailVO();

		mldDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
		mldDetailVO.setMailIdr(mailbagVO.getMailbagId());

		mldDetailVO.setMessageSequence(1);

		if ((MailConstantsVO.MLD_ALL.equals(mode)) || (MailConstantsVO.MLD_UPL.equals(mode))
			|| (MailConstantsVO.MLD_REC.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			 mldMasterVO =findMasterVOForVersion1( mldMasterVO, mode, toContainerVO, mailbagVO, logon);
			 mldDetailVO = mldMasterVO.getMldDetailVO();

		}

	else {
			mldMasterVO.setSenderAirport(mailbagVO.getPol()); 
			mldMasterVO.setReceiverAirport(mailbagVO.getPou());
		mldMasterVO.setSenderAirport(airport);	
			mldMasterVO.setReceiverAirport(mailbagVO.getScannedPort());
			mldMasterVO.setDestAirport(destination);

			mldMasterVO.setMessagingMode("O");
			mldDetailVO.setCarrierCodeInb(mailbagVO.getCarrierCode());
			mldDetailVO.setFlightNumberInb(mailbagVO.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberInb(mailbagVO.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			mldDetailVO.setCarrierIdInb(mailbagVO.getCarrierId());
			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
			mldDetailVO.setPolInb(mailbagVO.getPol());
			mldDetailVO.setPostalCodeInb(mailbagVO.getPaCode());
			mldDetailVO.setMailModeInb("F");

			mldDetailVO.setEventTimeInb(mailbagVO.getScannedDate());
		}

		mldMasterVO.setMldDetailVO(mldDetailVO);
	return mldMasterVO;
		
	}
	private MLDMasterVO constructMLDMasterVO(MLDConfigurationVO configVO) {
		MLDMasterVO masterVO =null;
		if (configVO != null) {
			masterVO = new MLDMasterVO();
			if (MailConstantsVO.FLAG_YES.equals(configVO.getAllocatedRequired())) {
				masterVO.setAllocationRequired(true); 
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedRequired())) {
				masterVO.setRecRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getUpliftedRequired())) {
				masterVO.setUpliftedRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.gethNDRequired())) {
				masterVO.sethNdRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getDeliveredRequired())) {
				masterVO.setdLVRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getStagedRequired())) {
				masterVO.setsTGRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getNestedRequired())) {
				masterVO.setnSTRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedFromFightRequired())) {
				masterVO.setrCFRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getTransferredFromOALRequired())) {
				masterVO.settFDRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReceivedFromOALRequired())) {
				masterVO.setrCTRequired(true);
			}
			if (MailConstantsVO.FLAG_YES.equals(configVO.getReturnedRequired())) {
				masterVO.setrETRequired(true);
			}
		}
		return masterVO;
	}

	/**
	 * @author A-8353
	 * @param currentKey
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public  String generateCounter(String companyCode)
			throws SystemException {
			String key=null;
			StringBuilder keyCondition = new StringBuilder();
			keyCondition.append("MLD_KEY_TYP");
			Criterion criterion = KeyUtils.getCriterion(companyCode,
					"MLD_KEY_SEQ", keyCondition.toString());
			key=KeyUtilInstance.getInstance().getKey(criterion);
			if(key.length() > 5) {
				key = "99999";
				KeyUtilInstance.getInstance().resetKey(criterion, "1");
			}else {
				key=String.valueOf(Long.parseLong(key));
				KeyUtilInstance.getInstance().resetKey(criterion, key);
			}
		return checkLength(key, 5);
	}
	/**
	 * @author A-8353
	 * @param key
	 * @param maxLength
	 * @return
	 */
	private  String checkLength(String key,int maxLength){
		String modifiedKey = null;
		StringBuilder buildKey = new StringBuilder();
		modifiedKey = new StringBuilder().append(key).toString();
		int keyLength = modifiedKey.length();
		if(modifiedKey.length() < maxLength){
			int diff = maxLength - keyLength;
			String val = null;
			for(int i=0;i< diff;i++){
				val = buildKey.append("0").toString();
			}
			modifiedKey = 	new StringBuilder().append(val).append(key).toString();
		}
		return modifiedKey;
	}
	/**
	 * @author A-8353
	 * @param mLDMessageVO
	 * @param count
	 */
	public void constructFileName(MLDMessageVO mLDMessageVO, String count, MLDMasterVO mLDMasterVO) {
		String fileName = null;
		  LocalDate localdate = null;
		   String airportCode =null;
		   if(MailConstantsVO.MLD_DLV.equalsIgnoreCase(mLDMasterVO.getEventCOde())||
					MailConstantsVO.MLD_HND.equalsIgnoreCase(mLDMasterVO.getEventCOde())
					||MailConstantsVO.MLD_RCF.equalsIgnoreCase(mLDMasterVO.getEventCOde())){
			   airportCode=mLDMasterVO.getReceiverAirport();
			}else{
			  airportCode=mLDMasterVO.getSenderAirport();
			}
		  if (airportCode!=null){
		  localdate= new LocalDate(airportCode, Location.ARP, false);
		  }
		  else{
		  localdate= new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		  }
		 String appendingDate= localdate.toGMTDate().toDisplayFormat("yyyyMMdd");
		fileName= new StringBuilder()
				.append(mLDMessageVO.getSenderID())
				.append("_").append(airportCode)
				.append("_").append("iCargo").append("_").append(appendingDate)
				.append("_")
				.append(count).toString();
		mLDMessageVO.setMldFileName(fileName);
	}
	/**
	 * Method:MLDController.constructMLDMasterCollections
	 * @param eventModeCollections
	 * @param mldMasterVO
	 * @param key
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Collection<MLDMasterVO>> constructMLDMasterCollections(Map<String,Collection<MLDMasterVO>> eventModeCollections,MLDMasterVO mldMasterVO,String key) throws SystemException {
		if (eventModeCollections.get(key) == null) {
			Collection<MLDMasterVO> modeMasterVOs = new ArrayList<>();
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			/**
			 * This method is to update the PROSTA as SND for the MLD info which is
			 * considered for message sent. This will be change in future as if any failure
			 * occurred in message sending flow , we need to identify and update the process
			 * status accordingly not SND
			 */
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		} else {
			Collection<MLDMasterVO> modeMasterVOs = eventModeCollections.get(key);
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		}
		return eventModeCollections;
	}
	public void flagMLDForMailOperationsInULD(ContainerVO containerVo,
			String mode) throws SystemException{
		log.entering(CLASS, "flagMLDForMailOperationsInULD");
		if ((containerVo == null)) {
			return;
		}
		Collection<MLDMasterVO> mldMasterVOs = createMLDVOsFromUldVOs(
				containerVo, mode);
		for (MLDMasterVO mldMasterVO : mldMasterVOs) {
			if ((MailConstantsVO.MLD_STG.equals(mldMasterVO.getEventCOde()) && mldMasterVO.issTGRequired())
			) {			
			new MLDMessageMaster(mldMasterVO);  
			}
		}
		log.exiting(CLASS, "flagMLDForMailOperationsInULD");
	}
	public Collection<MLDMasterVO> createMLDVOsFromUldVOs(ContainerVO containerVo, String mode) throws SystemException {
		log.entering(CLASS, "createMLDVOsFromUldVOs");
		Collection<MLDMasterVO> mldMasterVOs = new ArrayList<>();
		LogonAttributes logon = null;
		logon =  ContextUtils.getSecurityContext().getLogonAttributesVO();
			String airport = "";
			airport = containerVo.getAssignedPort();
			int carrierIdentifier =containerVo.getCarrierId();
			MLDConfigurationFilterVO filterVO = new MLDConfigurationFilterVO();
			filterVO.setCompanyCode(logon.getCompanyCode());
 			filterVO.setAirportCode(containerVo.getAssignedPort()); 
			filterVO.setCarrierIdentifier(carrierIdentifier);
			Collection<MLDConfigurationVO> configVOs = findMLDCongfigurations(filterVO);
			MLDConfigurationVO	configVO = null;
			String mldVersion = "";
			if(!configVOs.isEmpty()) {
			configVO = configVOs.iterator().next();
			 mldVersion = configVO.getMldversion();
			}
			MLDMasterVO masterVO = constructMLDMasterVO(configVO);
			if (mldVersion.equals(MailConstantsVO.MLD_VERSION2)) {
				 masterVO = createMLDVOsForVersion2(containerVo, mldVersion, mode, airport, logon,masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			}else {
				 masterVO = createMLDVOsForVersion1(containerVo, mldVersion, mode, airport,logon,masterVO);
				if (masterVO != null) {
					mldMasterVOs.add(masterVO);
				}
			}
		log.exiting(CLASS, "createMLDVOsFromUldVOs");
		return mldMasterVOs;
	}
	public  MLDMasterVO createMLDVOsForVersion1(ContainerVO containerVo,  String mldVersion,
			String mode, String airport, LogonAttributes logon, MLDMasterVO mldMasterVO) throws SystemException {
		 String mldEventMode = mode;
			if(mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL)|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)||
					mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_REC)|| mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_HND)||
					mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_UPL)||mldEventMode.equalsIgnoreCase(MailConstantsVO.MLD_DLV)||
					mldEventMode.equalsIgnoreCase(MLD_REC_HND)) {
				mldMasterVO = findParametersForVersion1( containerVo,  mldVersion,
				 mode,  airport,  logon,mldMasterVO);
				return mldMasterVO;
			} else {
				return null;
			}
	}
	public  MLDMasterVO findParametersForVersion1(ContainerVO containerVo,
			String mldVersion, String mode, String airport, LogonAttributes logon, MLDMasterVO mldMasterVO) throws SystemException {
		String mldEventMode = mode;
		mldMasterVO.setCompanyCode(containerVo.getCompanyCode());
		mldMasterVO.setBarcodeValue(containerVo.getContainerNumber());
		mldMasterVO.setEventCOde(mldEventMode);
		if(MailConstantsVO.MLD_FRESH_ALL.equals(mldEventMode)){
			mldMasterVO.setEventCOde(MailConstantsVO.MLD_ALL);      
			mode=MailConstantsVO.MLD_REC;
		}
		mldMasterVO.setScanTime(containerVo.getScannedDate());
		mldMasterVO.setSenderAirport(containerVo.getPol());  
		if(MailConstantsVO.MLD_ALL.equals(mode)){
				findSenderOrReceiverAirportCodes(containerVo,mldMasterVO);
		}
		mldMasterVO.setReceiverAirport(containerVo.getPou());
		String destination = "";
		mldMasterVO.setDestAirport(destination);
		mldMasterVO.setUldNumber(containerVo.getContainerNumber());
		mldMasterVO.setLastUpdatedUser(logon.getUserId());
		mldMasterVO.setProcessStatus("NEW");
		mldMasterVO.setWeightCode("KG");
		mldMasterVO.setMessageVersion(mldVersion);
		MLDDetailVO mldDetailVO = new MLDDetailVO();
		mldDetailVO.setCompanyCode(containerVo.getCompanyCode());
		mldDetailVO.setMailIdr(null);
		mldDetailVO.setMessageSequence(1);
		if ((MailConstantsVO.MLD_ALL.equals(mode)) || (MailConstantsVO.MLD_UPL.equals(mode))
			|| (MailConstantsVO.MLD_REC.equals(mode))) {
			mldMasterVO.setMldDetailVO(mldDetailVO);
			 mldMasterVO =findMasterVOForVersion1( mldMasterVO, containerVo, logon);
			 mldDetailVO = mldMasterVO.getMldDetailVO();
		}
	else {
			mldMasterVO.setSenderAirport(containerVo.getPol()); 
			mldMasterVO.setReceiverAirport(containerVo.getPou());
		mldMasterVO.setSenderAirport(airport);	
			mldMasterVO.setReceiverAirport(containerVo.getPou());
			mldMasterVO.setDestAirport(destination);
			mldMasterVO.setMessagingMode("O");
			mldDetailVO.setCarrierCodeInb(containerVo.getCarrierCode());
			mldDetailVO.setFlightNumberInb(containerVo.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberInb(containerVo.getFlightSequenceNumber());
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			updateFlightDateWithTime(MailConstantsVO.OPERATION_INBOUND,mldDetailVO);
			mldDetailVO.setCarrierIdInb(containerVo.getCarrierId());
			mldDetailVO.setEventTimeInb(containerVo.getScannedDate());
			mldDetailVO.setPolInb(containerVo.getPol());
			mldDetailVO.setPostalCodeInb(containerVo.getPaCode());
			mldDetailVO.setMailModeInb("F");
			mldDetailVO.setEventTimeInb(containerVo.getScannedDate());
		}
		mldMasterVO.setMldDetailVO(mldDetailVO);
	return mldMasterVO;
	}
	public MLDMasterVO findMasterVOForVersion1(MLDMasterVO mldMasterVO,
			ContainerVO containerVo, LogonAttributes logon) throws SystemException {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
				mldDetailVO = findMLDDetailVOForModeOtherThanALL( mldDetailVO, logon, containerVo);
			mldDetailVO.setPostalCodeOub(containerVo.getPaCode());
		mldMasterVO.setMldDetailVO(mldDetailVO);
		return mldMasterVO;
	}
	public MLDDetailVO findMLDDetailVOForModeOtherThanALL(MLDDetailVO mldDetailVO, LogonAttributes logon,
			ContainerVO containerVo) throws SystemException {
		mldDetailVO.setCarrierCodeOub(containerVo.getCarrierCode());
		mldDetailVO.setCarrierIdOub(
				findCarrierIdentifier(logon.getCompanyCode(), containerVo.getCarrierCode()));
		mldDetailVO.setFlightNumberOub(containerVo.getFlightNumber());
	mldDetailVO.setFlightSequenceNumberOub(containerVo.getFlightSequenceNumber());
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);
		mldDetailVO.setCarrierIdOub(containerVo.getCarrierId());
		mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(),ARP,true));          
		updateFlightDateWithTime(MailConstantsVO.OPERATION_OUTBOUND,mldDetailVO);   
		mldDetailVO.setPouOub(containerVo.getPou());
		if (containerVo.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
		return mldDetailVO;
	}
	
	public void findSenderOrReceiverAirportCodes(ContainerVO containerVo,
			MLDMasterVO mldMasterVO) throws SystemException {
		Collection<FlightSegmentSummaryVO> flightSegments = null;
				flightSegments = Proxy.getInstance().get(FlightOperationsProxy.class).findFlightSegments(
						containerVo.getCompanyCode(),
						containerVo.getCarrierId(),
						containerVo.getFlightNumber(),
						containerVo.getFlightSequenceNumber());
			Collection<String> routes = new ArrayList<>();
			if(flightSegments!=null) {
			ArrayList<FlightSegmentSummaryVO> segmentsTemp = (ArrayList<FlightSegmentSummaryVO>) flightSegments;
			int segmentsTempLength=segmentsTemp.size();
			 if(segmentsTempLength>0){
			routes.add(segmentsTemp.get(0).getSegmentOrigin());
			routes.add(segmentsTemp.get(0).getSegmentDestination());
			}
			 if(segmentsTempLength>1){    
			routes.add(segmentsTemp.get(1).getSegmentDestination());
			}
			recevierAirport(containerVo, mldMasterVO, flightSegments, routes);
			}
	}
	public void recevierAirport(ContainerVO containerVo, MLDMasterVO mldMasterVO,
			Collection<FlightSegmentSummaryVO> flightSegments, Collection<String> routes) {
			for (FlightSegmentSummaryVO segmentSummaryVO : flightSegments) {
						if (segmentSummaryVO.getSegmentOrigin().equals(containerVo.getPol())) {
				routeMthd(containerVo, mldMasterVO, routes, segmentSummaryVO);
						}
				}
	}
	public void routeMthd(ContainerVO containerVo, MLDMasterVO mldMasterVO, Collection<String> routes,
			FlightSegmentSummaryVO segmentSummaryVO) {
					for (String route : routes) {
			if (route.equalsIgnoreCase(containerVo.getPol())) {
													mldMasterVO.setReceiverAirport(segmentSummaryVO.getSegmentDestination());
											}
									}
							}
public	MLDMasterVO createMLDVOsForVersion2(ContainerVO containerVo, String mldVersion,
			String mode, String airport, LogonAttributes logon, MLDMasterVO mldMasterVO) throws SystemException {
		 MLDDetailVO mldDetailVO = null;
			String mldEventMode = mode;
			if(mode.equalsIgnoreCase(MailConstantsVO.MLD_FRESH_ALL) || mode.equalsIgnoreCase(MLD_REC_HND) 
					|| mode.equalsIgnoreCase(MailConstantsVO.MLD_UPL) || mode.equalsIgnoreCase(MailConstantsVO.MLD_HND) ||
					mode.equalsIgnoreCase(MailConstantsVO.MLD_ALL)) {
				return null;
			}
			mldMasterVO.setCompanyCode(containerVo.getCompanyCode());
			mldMasterVO.setBarcodeValue(containerVo.getContainerNumber());
			mldMasterVO.setDestAirport(containerVo.getPou());
			mldMasterVO.setEventCOde(mldEventMode);
			mldMasterVO.setScanTime(containerVo.getScannedDate());
			mldMasterVO.setSenderAirport(airport);
			mldMasterVO.setReceiverAirport(containerVo.getPou());
			mldMasterVO.setTransactionLevel(MailConstantsVO.ULD_LEVEL_TRANSACTION);
			if ((MailConstantsVO.MLD_RCF.equals(mode))) {
				mldMasterVO.setUldNumber(null);}
			else {
			mldMasterVO.setUldNumber(containerVo.getContainerNumber());
			}
			mldMasterVO.setLastUpdatedUser(logon.getUserId());
			mldMasterVO.setProcessStatus("NEW");
			mldMasterVO.setWeight(containerVo.getActualWeight());
			if (containerVo.getActualWeight()!=null) {
			mldMasterVO.setWeightCode(containerVo.getActualWeight().getDisplayUnit());
			} 
			mldMasterVO.setMessageVersion(mldVersion);
			mldDetailVO = new MLDDetailVO();
			mldDetailVO.setCompanyCode(containerVo.getCompanyCode());
			mldDetailVO.setMailIdr(null);
			mldDetailVO.setMessageSequence(1);
			if ((MailConstantsVO.MLD_REC.equals(mode)) || (MailConstantsVO.MLD_NST.equals(mode))
					|| (MailConstantsVO.MLD_STG.equals(mode)) || (MailConstantsVO.MLD_RCT.equals(mode))
					|| (MailConstantsVO.MLD_RET.equals(mode))) {
				mldMasterVO.setMldDetailVO(mldDetailVO);
				mldMasterVO = findParametersForVersion2( mldMasterVO, containerVo, logon);
				mldDetailVO = mldMasterVO.getMldDetailVO();
			}
			mldMasterVO.setTransactionLevel("U");
			mldMasterVO.setMldDetailVO(mldDetailVO);
			return mldMasterVO;
	}
	public MLDMasterVO findParametersForVersion2(MLDMasterVO mldMasterVO, ContainerVO containerVo,
			LogonAttributes logon) throws SystemException {
		MLDDetailVO mldDetailVO = mldMasterVO.getMldDetailVO();
		mldDetailVO.setCarrierCodeOub(containerVo.getCarrierCode());
		mldDetailVO.setCarrierIdOub(findCarrierIdentifier(logon.getCompanyCode(), containerVo.getCarrierCode()));
		mldDetailVO.setFlightNumberOub(containerVo.getFlightNumber());
		mldDetailVO.setFlightSequenceNumberOub(containerVo.getFlightSequenceNumber());
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,mldMasterVO.getSenderAirport());
		mldDetailVO.setCarrierIdOub(containerVo.getCarrierId());
		mldDetailVO.setEventTimeOub(new LocalDate(logon.getAirportCode(), ARP, true));
		updateFlightDateWithScheduledTime(MailConstantsVO.OPERATION_OUTBOUND, mldDetailVO,mldMasterVO.getSenderAirport());
		mldDetailVO.setPouOub(containerVo.getPou());
		if (containerVo.getFlightSequenceNumber() > 0) {
			mldDetailVO.setMailModeOub("F");
		} else {
			mldDetailVO.setMailModeOub("H");
		}
	mldDetailVO.setPostalCodeOub(containerVo.getPaCode());
	mldMasterVO.setMldDetailVO(mldDetailVO);
	return mldMasterVO;
	}
	/**
	 * @author A-8353
	 * @param operationInbound
	 * @param mldDetailVO
	 * @throws SystemException 
	 */
	private void updateFlightDateWithTime(String operationMode, MLDDetailVO mldDetailVO) throws SystemException {
		String  airportCode=null;
		updateFlightDateWithScheduledTime( operationMode, mldDetailVO, airportCode);
		

		
	}
	   	public Map<String, Collection<MLDMasterVO>> constructMLDMasterCollectionsForRCF(Map<String,Collection<MLDMasterVO>> eventModeCollections,MLDMasterVO mldMasterVO,String key) throws SystemException {
		if (eventModeCollections.get(key) == null) {
			Collection<MLDMasterVO> modeMasterVOs = new ArrayList<>();
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			
			updateMLDMessageSendStatus(mldMasterVO);
			modeMasterVOs.add(mldMasterVO);
			eventModeCollections.put(key, modeMasterVOs);
		} else {
			mldMasterVO.setProcessStatus(MailConstantsVO.FLAG_YES);
			updateMLDMessageSendStatus(mldMasterVO);
		}
		return eventModeCollections; 
	}
	   	public void updateMLDMsgSentTime(MLDMessageVO mldMessageVO) throws SystemException {
	   		log.entering(CLASS, "updateMLDMsgSentTime");
			MLDMessageMaster mLDMessageMaster = null;
			MLDMessageMasterPK mLDMessageMasterPK = new MLDMessageMasterPK();
			mLDMessageMasterPK.setCompanyCode(mldMessageVO.getCompanyCode());
			mLDMessageMasterPK.setSerialNumber(mldMessageVO.getSerialNumber());
			try {
				mLDMessageMaster = MLDMessageMaster.find(mLDMessageMasterPK);
			} catch (FinderException e) {
				log.log(Log.INFO, e);
				mLDMessageMaster = null;
			}
			if (mLDMessageMaster != null) {
				LocalDate sendDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
				GMTDate gmt =  sendDate.toGMTDate();
				mLDMessageMaster.setMsgTimUTC(gmt.toCalendar());
			}
			log.exiting(CLASS, "updateMLDMsgSentTime");	
	}

	
}