/*
 * DeleteULDMovementCommand.java Created on jan 29, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1936 Karhick.V
 *
 */
public class DeleteULDMovementCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	
	/**
	 * The execute method in BaseCommand
	 * @author A-1936
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("DeleteRowCommand","execute");
		
		ArrayList<ULDMovementVO> list=null;
		ArrayList<ULDMovementVO> listSession=null;
		
		RecordULDMovementForm  recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		RecordUldMovementSession session = (RecordUldMovementSession)
							getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");


		
		String[] carrierCode = recordULDMovementForm.getCarrierCode();
		String[] content = recordULDMovementForm.getContent();
		//String[] dummyMovement=recordULDMovementForm.getDummyMovement();		
		
	    String[]  flightNumber=recordULDMovementForm.getFlightNumber();
	    String[] pointOfLading = recordULDMovementForm.getPointOfLading();
	    String[] pointOfUnLading=recordULDMovementForm.getPointOfUnLading();
	    String[] checkForDelete =  recordULDMovementForm.getCheckForDelete();
		int iterator=0;

		Collection<ULDMovementVO> movementVOs = session.getULDMovementVOs();
		
		if(movementVOs!=null){
			log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE AFTER IF");
			for(ULDMovementVO uldMovementVO:movementVOs){
				uldMovementVO.setCarrierCode(carrierCode[iterator]);
				uldMovementVO.setContent(content[iterator]);
				/*for(String  str:dummyMovement){
					log.log(Log.FINE,"NULL CHECK DUMMYMOVEMENT"+str);
					log.log(Log.FINE,"NULL CHECK DUMMYMOVEMENT"+str);
				}
				if("UNCHECKED".equals(dummyMovement[iterator])){
					uldMovementVO.setDummyMovement(false);	
				}
				else{
					uldMovementVO.setDummyMovement(true);	
				}*/
				//uldMovementVO.setFlightDateString(flightDateString[iterator]);
				uldMovementVO.setFlightNumber(flightNumber[iterator].toUpperCase());
				uldMovementVO.setPointOfLading(pointOfLading[iterator]);
				uldMovementVO.setPointOfUnLading(pointOfUnLading[iterator]);
					iterator++;
			   log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE AFTER FOR");
			   log.log(Log.FINE, "vo >>>>>>>>>>>>>>", uldMovementVO);
			}
			session.setULDMovementVOs(movementVOs);
		}
		
		if(session.getULDMovementVOs()!=null){
		  list = new ArrayList<ULDMovementVO>(session.getULDMovementVOs());
		  listSession = new ArrayList<ULDMovementVO>(session.getULDMovementVOs());

		     log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
			if(list!=null && checkForDelete!=null){
				log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
				log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
					for(int i=0; i<checkForDelete.length; i++){
						 ULDMovementVO vo = list.get(Integer.parseInt(checkForDelete[i]));
						 listSession.remove(vo);
					}
				session.setULDMovementVOs(listSession);
			}
		}
	    log.exiting("DeleteRowCommand","execute");
		invocationContext.target = "save_success";
	}
}
