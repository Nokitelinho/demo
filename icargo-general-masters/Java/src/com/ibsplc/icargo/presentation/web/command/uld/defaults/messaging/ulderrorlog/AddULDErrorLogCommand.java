/*
 * AddULDErrorLogCommand.java Created on Jun 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ulderrorlog;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
//Commented by Manaf for INT ULD510
//import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.ULDErrorLogForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class AddULDErrorLogCommand extends BaseCommand {
    
	/**
	 * Logger for ULD Error Log
	 */
	private Log log = LogFactory.getLogger("ULD Error Log");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	private static final String SCREENID_ULDERRORLOG =
		"uld.defaults.ulderrorlog";
	
	private static final String ADD_SUCCESS = "add_success";
    
	private static final String ENTER_UCMNO="uld.defaults.ulderrorlog.plsenterucmno";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ULDErrorLogSession uldErrorLogSession = 
			(ULDErrorLogSession)getScreenSession(MODULE,SCREENID_ULDERRORLOG);
    	ULDErrorLogForm uldErrorLogForm = 
			(ULDErrorLogForm) invocationContext.screenModel;
    	// Commented by Manaf for INT ULD510
    	//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
    	updateSessionWithForm(uldErrorLogForm,uldErrorLogSession);
    	Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs
    												=uldErrorLogSession.getULDFlightMessageReconcileDetailsVOs();
    	ULDFlightMessageReconcileDetailsVO ULDFlightMsgVO=null;
    	if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
    		ULDFlightMsgVO=new ULDFlightMessageReconcileDetailsVO();
    		if(uldFlightMessageReconcileDetailsVOs.get(0)!=null){
    		ULDFlightMsgVO.setAirportCode(uldFlightMessageReconcileDetailsVOs.get(0).getAirportCode());
    		ULDFlightMsgVO.setCarrierCode(uldFlightMessageReconcileDetailsVOs.get(0).getCarrierCode());
    		ULDFlightMsgVO.setCompanyCode(uldFlightMessageReconcileDetailsVOs.get(0).getCompanyCode());
    		ULDFlightMsgVO.setContent(uldFlightMessageReconcileDetailsVOs.get(0).getContent());
    		ULDFlightMsgVO.setDamageStatus(uldFlightMessageReconcileDetailsVOs.get(0).getDamageStatus());
    		//ULDFlightMsgVO.setErrorCode(uldFlightMessageReconcileDetailsVOs.get(0).getErrorCode());
    		ULDFlightMsgVO.setFlightCarrierIdentifier(uldFlightMessageReconcileDetailsVOs.get(0).getFlightCarrierIdentifier());
    		ULDFlightMsgVO.setFlightDate(uldFlightMessageReconcileDetailsVOs.get(0).getFlightDate());
    		ULDFlightMsgVO.setFlightNumber(uldFlightMessageReconcileDetailsVOs.get(0).getFlightNumber());
    		ULDFlightMsgVO.setFlightSequenceNumber(uldFlightMessageReconcileDetailsVOs.get(0).getFlightSequenceNumber());
    		ULDFlightMsgVO.setLegSerialNumber(uldFlightMessageReconcileDetailsVOs.get(0).getLegSerialNumber());
    		ULDFlightMsgVO.setMessageType(uldFlightMessageReconcileDetailsVOs.get(0).getMessageType());
    		ULDFlightMsgVO.setOperationFlag(OPERATION_FLAG_INSERT);
    		ULDFlightMsgVO.setPou(uldFlightMessageReconcileDetailsVOs.get(0).getPou());
    		ULDFlightMsgVO.setSequenceNumber(uldFlightMessageReconcileDetailsVOs.get(0).getSequenceNumber());
    		//ULDFlightMsgVO.setUcmErrorCode(uldFlightMessageReconcileDetailsVOs.get(0).getUcmErrorCode());
    		//ULDFlightMsgVO.setContent(content)
    		//Modified by A-6223 for ICRD-111467 starts
    		ULDFlightMsgVO.setUldNumber("");
    		//Modified by A-6223 for ICRD-111467 ends
    		}
    		uldFlightMessageReconcileDetailsVOs.add(ULDFlightMsgVO);
    		
    		uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
    	}else{
    		ArrayList<String> pous=new ArrayList<String>();
    		// Commented by Manaf for INT ULD510
    		//ArrayList<String> ucms=new ArrayList<String>();
    		ApplicationSessionImpl applicationSession = getApplicationSession();
    		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    		String  companyCode = logonAttributes.getCompanyCode();
    		String carrierCode=uldErrorLogForm.getCarrierCode();
    		String flightNumber=uldErrorLogForm.getFlightNo();
    		String flightDate=uldErrorLogForm.getFlightDate();
    		String airport=uldErrorLogForm.getUlderrorlogAirport();
    	
    		pous.add(airport.trim().toUpperCase());
    		String messageType=uldErrorLogForm.getMessageType();
    		String ucmNo=uldErrorLogForm.getUcmNo();
    		ULDFlightMessageReconcileDetailsVO  vo=new ULDFlightMessageReconcileDetailsVO ();
    		vo.setCompanyCode(companyCode);
    		vo.setCarrierCode(carrierCode);
    		vo.setFlightNumber(flightNumber);
    		LocalDate flightDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    		flightDat.setDate(flightDate);
    		vo.setFlightDate(flightDat);
    		vo.setAirportCode(airport);
    		vo.setMessageType(messageType);
    		/*if(ucmNo!=null && ucmNo.trim().length()>0){
    		vo.setSequenceNumber(ucmNo);
    		ucms.add(ucmNo.trim().toUpperCase());
    		}
    		else{
    			errorVO=new ErrorVO(ENTER_UCMNO);
    			invocationContext.addError(errorVO);
    			invocationContext.target = ADD_SUCCESS;	
    			return;
    		}*/
    		vo.setOperationFlag(OPERATION_FLAG_INSERT);
    		if(uldErrorLogSession.getUcmNumberValues()!=null && uldErrorLogSession.getUcmNumberValues().size()>0){
    			vo.setSequenceNumber(uldErrorLogSession.getUcmNumberValues().get(0));
    		}
    		ArrayList<ULDFlightMessageReconcileDetailsVO> reconVOs=new ArrayList<ULDFlightMessageReconcileDetailsVO>();
    		reconVOs.add(vo);
    		Page<ULDFlightMessageReconcileDetailsVO> reconPageVOs
    		=new Page<ULDFlightMessageReconcileDetailsVO>(reconVOs,1,0,reconVOs.size(),0,0,false);
    		log.log(Log.INFO, "seq values", uldErrorLogSession.getUcmNumberValues());
			uldErrorLogSession.setULDFlightMessageReconcileDetailsVOs(reconPageVOs);
    		uldErrorLogSession.removePouValues();
    		//uldErrorLogSession.removeUcmNumberValues();
    		uldErrorLogSession.setPouValues(pous);
    		//uldErrorLogSession.setUcmNumberValues(ucms);
    		//to do set pou and ucm values in session;
    		//uldErrorLogSession.setUcmNumberValues();
    	}
    	
    	invocationContext.target = ADD_SUCCESS;	
    }
    /**
     * @param form
     * @param session
     */
    private void updateSessionWithForm(ULDErrorLogForm form,ULDErrorLogSession session){
        	
        	String[] pous=form.getPou();
        	//String[] uldNo=form.getUldNumber();
        	String[] ucmnos=form.getSequenceNumber();
        	String[] content=form.getContent();
        	Page<ULDFlightMessageReconcileDetailsVO> uldFlightMessageReconcileDetailsVOs
    		=session.getULDFlightMessageReconcileDetailsVOs();
        	if(uldFlightMessageReconcileDetailsVOs!=null && uldFlightMessageReconcileDetailsVOs.size()>0){
        		int fltSize = uldFlightMessageReconcileDetailsVOs.size();
        		for(int i=0;i<fltSize;i++){
        			log.log(Log.INFO, "vo for save",
							uldFlightMessageReconcileDetailsVOs.get(i));
					if(!(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))&&
        					!(OPERATION_FLAG_DELETE.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag()))){
        				if(!(pous[i].equals(uldFlightMessageReconcileDetailsVOs.get(i).getPou()))){
        					uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
        					uldFlightMessageReconcileDetailsVOs.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
        				}
        				if(!(content[i].equals(uldFlightMessageReconcileDetailsVOs.get(i).getContent()))){
        					uldFlightMessageReconcileDetailsVOs.get(i).setContent(content[i]);
        					uldFlightMessageReconcileDetailsVOs.get(i).setOperationFlag(OPERATION_FLAG_UPDATE);
        				}
        			}
        			if(OPERATION_FLAG_INSERT.equals(uldFlightMessageReconcileDetailsVOs.get(i).getOperationFlag())){
        				//Modified by A-6223 for ICRD-111467 starts
        				uldFlightMessageReconcileDetailsVOs.get(i).setUldNumber("");
        				//Modified by A-6223 for ICRD-111467 ends
        				uldFlightMessageReconcileDetailsVOs.get(i).setPou(pous[i]);
        				uldFlightMessageReconcileDetailsVOs.get(i).setSequenceNumber(ucmnos[i]);
        				uldFlightMessageReconcileDetailsVOs.get(i).setContent(content[i]);
        			}
        		}
        	}
        	session.setULDFlightMessageReconcileDetailsVOs(uldFlightMessageReconcileDetailsVOs);
        }
}
