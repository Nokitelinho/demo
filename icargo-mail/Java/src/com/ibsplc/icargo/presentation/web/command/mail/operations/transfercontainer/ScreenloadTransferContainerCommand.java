/*
 * ScreenloadTransferContainerCommand.java Created on Oct 05, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.transfercontainer;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1861
 *
 */
public class ScreenloadTransferContainerCommand extends BaseCommand { 
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
      
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.transfercontainer";

   private static final String TARGET_SUCCESS = "success";
       
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadCommand","execute");
    	  
    	TransferContainerForm transferContainerForm = 
    		(TransferContainerForm)invocationContext.screenModel;
    	TransferContainerSession reassignContainerSession = 
    		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	
    	Collection<ContainerVO> containerVOs = reassignContainerSession.getSelectedContainerVOs();
    	ContainerVO containerVO = reassignContainerSession.getContainerVO();
    	
    	log.log(Log.FINE, "Selected ContainerVOs ------------> ", containerVOs);
		for (ContainerVO containervo : containerVOs) {
    		transferContainerForm.setDestination(containervo.getFinalDestination());
    		break;
    	}
    	
    	LocalDate dat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		String date = dat.toDisplayDateOnlyFormat();
		String time = dat.toDisplayFormat("HH:mm");		
		transferContainerForm.setScanDate(date);
		transferContainerForm.setMailScanTime(time);
		transferContainerForm.setSimilarCarrier("NONE");
//		Added by Deepu for CR QF1545 starts
		if (containerVO.getOnwardRoutings() != null && containerVO.getOnwardRoutings().size() > 0 
				&& !("N").equals(transferContainerForm.getIsScreenLoad())) {
			OnwardRoutingVO onwardRoutingVO = containerVO.getOnwardRoutings().iterator().next();
			if (onwardRoutingVO != null) {
				transferContainerForm.setFlightCarrierCode(onwardRoutingVO.getOnwardCarrierCode());
				transferContainerForm.setFlightNumber(onwardRoutingVO.getOnwardFlightNumber());
				LocalDate localDate = onwardRoutingVO.getOnwardFlightDate();
				String onwardFlilocalDate = localDate.toDisplayDateOnlyFormat();
				transferContainerForm.setFlightDate(onwardFlilocalDate);
				transferContainerForm.setFlightPou(onwardRoutingVO.getPou());
			}
			containerVO.getOnwardRoutings().remove(onwardRoutingVO);
		}
		
//		Added by Deepu for CR QF1545 Ends
    	
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("ScreenloadCommand","execute");
    	
    }
    
}
