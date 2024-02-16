/*
 * ValidateDuplicateFlightCommand.java Created on Jul 20,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmflightexceptionlist;



import java.util.ArrayList;









import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;




import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;





import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMFlightExceptionListSession;




import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * This command class is used to list the UCM error logs
 * @author A-1862
 */

public class ValidateDuplicateFlightCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListUcmFlightException Error Logs");
	private static final String MODULE = "uld.defaults";
	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID =
		"uld.defaults.ucmflightexceptionlist";	
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	if(invocationContext.getErrors() != null
    			&& invocationContext.getErrors().size() > 0) {    		
    		return;
    	}	
		UCMFlightExceptionListSession ucmFlightExceptionListSession = 
			(UCMFlightExceptionListSession)getScreenSession(MODULE,SCREENID);
		
		ArrayList<UCMExceptionFlightVO> ucmExceptionFlightVOs =
			ucmFlightExceptionListSession.getUcmExceptionFlightVOs();
		if(ucmExceptionFlightVOs != null &&
				ucmExceptionFlightVOs.size() > 0) {
			int noOfElements = ucmExceptionFlightVOs.size();
			int lastVosIndex = -1;
			for(int i = noOfElements - 1; i >= 0; i-- ) {
				if(!AbstractVO.OPERATION_FLAG_DELETE.equals(
						ucmExceptionFlightVOs.get(i).getOpeartionalFlag())) {
					lastVosIndex = i;
					break;
				}
			}
			if(lastVosIndex < 0) {				
				return;
			}
			log.log(Log.FINE, "ucmExceptionFlightVOs duplicate ---->>",
					ucmExceptionFlightVOs);
			UCMExceptionFlightVO lastUCMExceptionFlightVO =
				ucmExceptionFlightVOs.get(lastVosIndex);
			log.log(Log.FINE, "lastUCMExceptionFlightVO duplicate ---->>",
					lastUCMExceptionFlightVO);
			log.log(Log.FINE, "lastVosIndex duplicate ---->>", lastVosIndex);
			for(int i = 0; i < lastVosIndex; i++ ) {
				if(!AbstractVO.OPERATION_FLAG_DELETE.equals(
						ucmExceptionFlightVOs.get(i).getOpeartionalFlag())) {
					log.log(Log.FINE,
							"ucmExceptionFlightVOs.get(i) duplicate ---->>",
							ucmExceptionFlightVOs.get(i));
					if(lastUCMExceptionFlightVO.getCarrierId() == 
						ucmExceptionFlightVOs.get(i).getCarrierId() &&
						ucmExceptionFlightVOs.get(i).getFlightNumber().equals( 
								lastUCMExceptionFlightVO.getFlightNumber()) &&
						lastUCMExceptionFlightVO.getFlightSequenceNumber() == 
							ucmExceptionFlightVOs.get(i).getFlightSequenceNumber() &&						
						lastUCMExceptionFlightVO.getLegSerialNumber() == 
							ucmExceptionFlightVOs.get(i).getLegSerialNumber()) {
						log.log(Log.FINE," duplicate ---->>");
						lastUCMExceptionFlightVO.setFlightSequenceNumber(0);
						lastUCMExceptionFlightVO.setLegSerialNumber(0);
						ErrorVO error = new ErrorVO(
								"uld.defaults.ucmflightexceptionlist.duplicateflightentry");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						break;
					}
				}
			}
		}
        
    }
   
}
