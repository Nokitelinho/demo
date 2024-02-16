/*
 * DeleteFlightDetailsCommand.java Created on Feb 27, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1739
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * -------------------------------------------------------------------------
 * 0.1     		  Feb 27, 2007			a-2257		Created
 */
public class DeleteFlightDetailsCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "DeleteFlightDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra";

	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/**
	 * To identify if the row selected for deletion is new or already there in database]
	 * so that it can be permenently deleted from collection if flag is insert
	 */
	private static final String DELETE = "DE";

	private static final String REMOVE = "RE";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";

	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CaptureGPAReportSession session =
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);
		CaptureGPAReportForm form =
			(CaptureGPAReportForm)invocationContext.screenModel;

		GPAReportingDetailsVO gpaReportingDetailsVO = session.getSelectedGPAReportingDetailsVO();

		Collection<GPAReportingFlightDetailsVO> gpaFlightDetailsVOs
					= gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs();

		log.log(Log.FINE, "checkedRow find");

		String[] checked = form.getSelectedRows().split(",");

		//log.log(Log.FINE, "checkedRow" + checked);
		/**
		 * setting operation flag as DE to Identify Vos
		 * deleted in the current delete operation
		 */
		for (int i = 0; i < checked.length; i++) {
			int indexOfChecked=0;

			if(gpaFlightDetailsVOs!=null && gpaFlightDetailsVOs.size()>0){

			for(GPAReportingFlightDetailsVO fltDetailsVO : gpaFlightDetailsVOs){
				if(!GPAReportingDetailsVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(fltDetailsVO.getOperationFlag())){

					if(checked[i]!=null && checked[i].trim().length()!=0){
						if(indexOfChecked==Integer.parseInt(checked[i])){

						log.log(Log.FINE, "fltDetailsVO==", fltDetailsVO);
							if(GPAReportingDetailsVO.OPERATION_FLAG_INSERT.equals(fltDetailsVO.getOperationFlag())){
								fltDetailsVO.setOperationFlag(REMOVE);
							}else{
								fltDetailsVO.setOperationFlag(DELETE);
							}
						}
					}
					indexOfChecked++;
				}
			}
		}
		}
		/**
		 * finally constructing the colletion with
		 * operation flag delete for all the vos deleted till now
		 */
		Collection<GPAReportingFlightDetailsVO> removedVOs = new ArrayList<GPAReportingFlightDetailsVO>();

		for(GPAReportingFlightDetailsVO fltDetailsVO : gpaFlightDetailsVOs){
			log.log(Log.FINE, "updating the final delete");
			if(DELETE.equalsIgnoreCase(fltDetailsVO.getOperationFlag())){
				log.log(Log.FINE, "updating the final delete when operation flag is not insert");
				fltDetailsVO.setOperationFlag(GPAReportingDetailsVO.OPERATION_FLAG_DELETE);
			}
			if(REMOVE.equalsIgnoreCase(fltDetailsVO.getOperationFlag())){
				log.log(Log.FINE, "updating the final delete when operation flag is insert");
				removedVOs.add(fltDetailsVO);

			}
		}

		if(removedVOs!=null && removedVOs.size()>0){
			log.log(Log.FINE, "going to remove");
			gpaFlightDetailsVOs.removeAll(removedVOs);
			log.log(Log.FINE, "removed");
		}

		log.log(Log.FINE, "gpaFlightDetailsVOs after deletion",
				gpaFlightDetailsVOs);
		gpaReportingDetailsVO.setGpaReportingFlightDetailsVOs(gpaFlightDetailsVOs);
		invocationContext.target =ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
