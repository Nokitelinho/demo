/*
 * DeleteCommand.java Created on Feb 21, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;


import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2105
 *
 */
public class DeleteCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String DELETE_SUCCESS="delete_success";
	
	

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		//log.log(Log.INFO,"captureCN51Form.getSelect()....>>>"+captureCN51Form.getSelect());
		String[] rows = captureCN51Form.getSelect();
		//AirlineCN51DetailsVO airlineCN51DetailsVO = null;
		Page<AirlineCN51DetailsVO> airlineCN51DetailsVOs = 
			captureCN51Session.getCn51Details().getCn51DetailsPageVOs();
		ArrayList<AirlineCN51DetailsVO> airlineCN51DetailsVO = new ArrayList<AirlineCN51DetailsVO>();
		for(AirlineCN51DetailsVO airlineCN51DetailVO :airlineCN51DetailsVOs ){
			airlineCN51DetailsVO.add(airlineCN51DetailVO);
				}
		for(int i=rows.length-1;i>=0;i--) {
			int index = Integer.parseInt(rows[i]);
			//	airlineCN51DetailsVO = airlineCN51DetailsVOs.get(index);
			//if((AirlineCN51DetailsVO.OPERATION_FLAG_INSERT).equals(airlineCN51DetailsVO.getOperationFlag())) {
			airlineCN51DetailsVO.remove(index);
			/*}else{
				airlineCN51DetailsVO.setOperationFlag(AirlineCN51DetailsVO.OPERATION_FLAG_DELETE);
			}*/
		}
		int _display_Page = 1; 
        int _startIndex = 1;  
        int _endIndex = 25;
        boolean _hasNext_Page = false;   
        int _total_Rec_Count = 0; 
        int maxPageLimit=25;
	Page<AirlineCN51DetailsVO> airlineCN51DetailsVOss = new Page<AirlineCN51DetailsVO>(
			airlineCN51DetailsVO,
                _display_Page,  
                maxPageLimit,
                airlineCN51DetailsVO.size(),
                _startIndex,
                _endIndex,
                _hasNext_Page,
                _total_Rec_Count);
	captureCN51Session.getCn51Details().setCn51DetailsPageVOs(airlineCN51DetailsVOss); 
		invocationContext.target=DELETE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	
}
