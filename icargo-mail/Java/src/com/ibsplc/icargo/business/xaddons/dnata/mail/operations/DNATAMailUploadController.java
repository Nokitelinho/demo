/*
 * DNATAMailController.java Created on Jun 25, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.dnata.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitValidationVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.WarehouseDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


/**
 * @author A-9619
 * com.ibsplc.icargo.business.xaddons.dnata.mail.operations.DNATAMailController.java
 */		 

@Module("mail")
@SubModule("operations")
public class DNATAMailUploadController extends MailUploadController{

	public static final String STORAGEUNIT_SYSTEM_PARAM_KEY = "mail.operations.storageunitintegrationneeded";	//added as part of IASCB-57385
	
	
	public void specificMailbagValidationForAndroid(MailUploadVO mailUploadVO) throws SystemException, MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException{
    	/** Implementation should be done in Client specific mapper placed in xaddons **/
    	if (mailUploadVO.getStorageUnit() != null && !mailUploadVO.getStorageUnit().isEmpty()) {
			Map<String, String> systemParams = Proxy.getInstance().get(SharedDefaultsProxy.class)
					.findSystemParameterByCodes(Arrays.asList(STORAGEUNIT_SYSTEM_PARAM_KEY));
			if(systemParams != null && MailConstantsVO.FLAG_YES.equals(systemParams.get(STORAGEUNIT_SYSTEM_PARAM_KEY))) {
				StorageUnitValidationVO storageUnitValidationVO = Proxy.getInstance().get(WarehouseDefaultsProxy.class).validateStorageUnit(
						mailUploadVO.getCompanyCode(), mailUploadVO.getScannedPort(), mailUploadVO.getStorageUnit());
				if (storageUnitValidationVO == null) {
					constructAndRaiseException(
							MailConstantsVO.MAIL_STORAGE_UNIT_NOT_FOUND_ERR_CODE,
							MailHHTBusniessException.MAIL_STORAGE_UNIT_NOT_FOUND_ERR_CODE,
						   null);
				}
			}
		}				  
	}
	
	//added by A-9529 for IASCB-44567
	/**
	 * This method  is used to make Mail Bag delivery based on StorageUnit from in bound react screen
	 * @author a-9529
	 * @param ScannedMailDetailVO, LogonAttributes
	 * @return
	 * @throws SystemException
	 * @throws ForceAcceptanceException 
	 */
	public void doMailbagDeliveryOnstoargeUnitScan(ScannedMailDetailsVO scannedMailDetailsVO, LogonAttributes logonAttributes) throws
	SystemException, MailHHTBusniessException, MailMLDBusniessException, ForceAcceptanceException{
		
		Collection<MailbagVO> mailbagVOs = DNATAMailController.findMailbagsInStorageUnit(scannedMailDetailsVO.getStorageUnit());
		if(!Objects.nonNull(mailbagVOs)){
			return;
		}
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ARRIVED);  
		scannedMailDetailsVO.setContainerAsSuchArrOrDlvFlag(MailConstantsVO.FLAG_YES);
		scannedMailDetailsVO.setContainerDeliveryFlag(MailConstantsVO.FLAG_YES);
		scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
        Map<String, FlightValidationVO> flightDetailMap = new HashMap<>();



		for (MailbagVO mailbagtoArrive: mailbagVOs) {
            FlightValidationVO flightDetailsVO = null;
            String flightDetailKey = mailbagtoArrive.getFlightNumber()+"_"+mailbagtoArrive.getFlightSequenceNumber()+"_"+mailbagtoArrive.getCarrierId();
            if(flightDetailMap.containsKey(flightDetailKey)) {
                flightDetailsVO = flightDetailMap.get(flightDetailKey);
            }else{
                flightDetailsVO=validateFlight(mailbagtoArrive);
                flightDetailMap.put(flightDetailKey,flightDetailsVO);
            }
			mailbagtoArrive.setFlightDate(flightDetailsVO.getFlightDate());
			mailbagtoArrive.setCarrierCode(flightDetailsVO.getCarrierCode());
		}
        scannedMailDetailsVO.setMailDetails(mailbagVOs);
		saveDeliverFromUpload(scannedMailDetailsVO,logonAttributes); 

	}
		
	 //added by A-9529 for IASCB-53243
    /**
     * This method  is used to find flightDetails of mailbag 
     * @author a-9529
     * @param MailbagVO
     * @return FlightValidationVO
     * @throws SystemException, MailHHTBusniessException
     */
    public FlightValidationVO  validateFlight(MailbagVO mailbagVO) throws MailHHTBusniessException, SystemException {
        Collection<FlightValidationVO> flightValidationVOs = null;
        FlightFilterVO flightFilterVO = new FlightFilterVO();
        flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
        flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
        flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
        flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
        flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
        flightFilterVO.setAirportCode(mailbagVO.getScannedPort());
        flightFilterVO.setStation(mailbagVO.getScannedPort());
        if (mailbagVO.getFlightSequenceNumber() >= 0
                && mailbagVO.getFlightNumber() != null && !"-1".equals(mailbagVO.getFlightNumber())) {
            flightValidationVOs = new MailController().validateFlight(flightFilterVO);
            if (flightValidationVOs == null || (flightValidationVOs.isEmpty())) {
                throw new MailHHTBusniessException(MailHHTBusniessException.FLIGHT_NOT_EXIST);
            }
            return flightValidationVOs.iterator().next();
        }
        return null;
    }
	
	
	@Override
	public ScannedMailDetailsVO saveMailUploadDetailsForAndroid(MailUploadVO mailUploadVO,
			String scanningPort) throws MailMLDBusniessException, MailHHTBusniessException, SystemException,
			MailTrackingBusinessException, RemoteException, PersistenceException {
		
		try{LogonAttributes logonAttributes = getLogonAttributes();  
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
		MailbagVO mailbagVO = new MailbagVO(); 

		if(mailUploadVO.getProcessPoint()!=null){
		scannedMailDetailsVO.setProcessPoint(mailUploadVO.getProcessPoint());
		}

		mailUploadVO.setScannedPort(scanningPort);
			
		if(mailUploadVO.getProcessPoint()!=null){
			scannedMailDetailsVO.setProcessPoint(mailUploadVO.getProcessPoint());
		}
		
		specificMailbagValidationForAndroid(mailUploadVO);    
		
		if (MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND.equals(mailUploadVO.getMailSource()) &&
				(mailUploadVO.getContainerNumber().isEmpty() || mailUploadVO.getContainerNumber().equals("") || mailUploadVO.getContainerNumber() == null)
				&& (!mailUploadVO.getStorageUnit().isEmpty() && !mailUploadVO.getStorageUnit().equals("")) && mailUploadVO.isDeliverd() &&
				(scannedMailDetailsVO.getMailDetails() == null )){
			
			
				constructScannedMailDetailVO(scannedMailDetailsVO, mailUploadVO, mailbagVO,
						logonAttributes, mailUploadVO.getScannedPort());
				doMailbagDeliveryOnstoargeUnitScan(scannedMailDetailsVO, logonAttributes);
			
			
			
			return scannedMailDetailsVO;
			
		}else {
			return super.saveMailUploadDetailsForAndroid(mailUploadVO, scanningPort);
		}

	
		} catch (ForceAcceptanceException exception) {
			Collection<ErrorVO> errors = new ArrayList<>();
			if (exception.getErrors()!=null){
			errors=exception.getErrors();
			}
			ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO(); 
			if (errors != null && !errors.isEmpty()) {
				for(ErrorVO vo : errors) {
			scannedMailDetailsVO.setErrorCode(vo.getErrorCode());
			scannedMailDetailsVO.setErrorDescription(vo.getConsoleMessage());
			scannedMailDetailsVO.setForceAccepted(true);
			}
			}
			return scannedMailDetailsVO;
		}
		
	}
	
	@Override
	public void setScanInformationForAndroid(MailUploadVO uploadedMaibagVO, 
			ScannedMailDetailsVO scannedMailDetailsVO, String scannedPort) throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		super.setScanInformationForAndroid(uploadedMaibagVO, scannedMailDetailsVO, scannedPort);
		//added by A-9529 for IASCB-44567
        if(MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND.equals(uploadedMaibagVO.getMailSource())){
            scannedMailDetailsVO.setStorageUnit(uploadedMaibagVO.getStorageUnit());
        }
		
	}
	
	@Override
	public void setMailbagDetailsForAndroid(MailbagVO mailbagVOToSave,
			MailUploadVO uploadedMaibagVO,ScannedMailDetailsVO scannedMailDetailsVO) 
					throws FinderException, SystemException, MailHHTBusniessException, 
							MailMLDBusniessException, ForceAcceptanceException {
		
		mailbagVOToSave.setStorageUnit(uploadedMaibagVO.getStorageUnit()); //added by A-9529 for IASCB-44567
		mailbagVOToSave.setScreeningUser(uploadedMaibagVO.getScreeningUser()); //added by A-9529 for IASCB-44567
		super.setMailbagDetailsForAndroid(mailbagVOToSave, uploadedMaibagVO, scannedMailDetailsVO);
	}
	
	
	@Override
	public ScannedMailDetailsVO constructScannedMailDetailsVO(
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		
		ScannedMailDetailsVO newScannedMailDetailsVO = super.constructScannedMailDetailsVO(scannedMailDetailsVO);
		//added by A-9529 for IASCB-44567
		if(MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND.equals(scannedMailDetailsVO.getMailSource())){
			newScannedMailDetailsVO.setStorageUnit(scannedMailDetailsVO.getStorageUnit());
		}
		
		return newScannedMailDetailsVO;
	}
	
	/**
	 * @author A-9619 
	 * @param scannedMailDetailsVO
	 * @param containerVO
	 */
	@Override
	public void setScreeningUserForDnataSpecific(ScannedMailDetailsVO scannedMailDetailsVO,
			 ContainerVO containerVO) {
	
		if("SCAN:HHT046".equals(scannedMailDetailsVO.getMailSource())){//Added by A-9498 as part of IASCB-44577
			for (MailbagVO mailBagVo : scannedMailDetailsVO.getMailDetails()) {
				containerVO.setScreeningUser(mailBagVo.getScreeningUser());
				
			}
		}
    }
	
	/**
	 * @author A-9619 as part of IASCB-55196
	 * @param mailbagToArrive
	 * @param scannedMailDetailsVO
	 */
	@Override
	public void setStoragUnitForDnataSpecific(MailbagVO mailbagToArrive, ScannedMailDetailsVO scannedMailDetailsVO) {
		
		/* added by A-9529 for IASCB-44567
         * Added null check for storageunit while storageunit level delivery
         * storage unit value will be in mailbagVO and not in scannedMaildetailVo
         * so to avoid clearing value of storage unit in mailbagvo.
        */
        if(MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND.equals(scannedMailDetailsVO.getMailSource()) && mailbagToArrive.getStorageUnit() == null){
		    mailbagToArrive.setStorageUnit(scannedMailDetailsVO.getStorageUnit());
        }
		
	}
		
}
