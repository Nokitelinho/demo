
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 /**
  * 
  *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist.TransferContainerCommand.java
  *	Version		:	Name	:	Date			:	Updation
  * ---------------------------------------------------
  *		0.1		:	A-7371	:	12-Jan-2018	:	Draft
  */
public class TransferContainerCommand extends BaseCommand  {
	
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS= "transfer_success";	
	private static final String MODULE_NAME = "mail.operations";	
    private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
	private static final String SCREEN_ID_TRA = "mailtracking.defaults.transfercontainer";
	private static final String SCREEN_STATUS="showTransferContainerScreen";	



	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("TransferContainerCommand","execute");
		
		MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		
    	TransferContainerSession transferContainerSession = 
        		(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_TRA);

    
		MailAcceptanceVO mailAcceptanceVO=mailExportListSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=mailAcceptanceVO.getContainerDetails();
        String[] primaryKey=mailExportListForm.getSelectedContainer().split(",");   
        
        int cnt=0;
 	   int count = 0;
        int primaryKeyLen = primaryKey.length;

        Collection<ContainerVO> containerVOs =  new ArrayList<ContainerVO>();
        ContainerVO containerVO=new ContainerVO();
        if (containerDetailsVOs != null && containerDetailsVOs.size() != 0) {
        	for (ContainerDetailsVO containerDetailsVO : containerDetailsVOs) {
        		String primaryKeyFromVO = containerDetailsVO.getCompanyCode()
        				+String.valueOf(count);
        		if ((cnt < primaryKeyLen) &&(primaryKeyFromVO.trim()).
        				          equalsIgnoreCase(primaryKey[cnt].trim())) {
        		
        			containerVO =	convertToContainer(transferContainerSession,containerDetailsVO
        					,primaryKeyLen);
        			containerVOs.add(containerVO);
        			cnt++;
        		}
        		count++;
        	  }
        	}
        if (primaryKeyLen == 1) {
    		transferContainerSession.setContainerVO(containerVOs.iterator().next());  
    	} else {
    	transferContainerSession.setContainerVO(new ContainerVO());
    	}
    	transferContainerSession.setSelectedContainerVOs(containerVOs);
    	mailExportListForm.setTransferContainerFlag(SCREEN_STATUS);
    	
		invocationContext.target = TARGET_SUCCESS;

        log.exiting("TransferContainerCommand","execute");
		
	}
	
	
	private ContainerVO convertToContainer(TransferContainerSession transferContainerSession,
			ContainerDetailsVO containerDetailsVO,int primaryKeyLen) {
		// TODO Auto-generated method stub
		
		ContainerVO containerVO=new ContainerVO();
		containerVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		containerVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		containerVO.setCarrierId(containerDetailsVO.getCarrierId());
		containerVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		containerVO.setFlightDate(containerDetailsVO.getFlightDate());
		containerVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		containerVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		containerVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
		containerVO.setFinalDestination(containerDetailsVO.getDestination());
		containerVO.setAssignedPort(containerDetailsVO.getPol());
		containerVO.setAssignedUser(containerDetailsVO.getAssignedUser());
		containerVO.setType(containerDetailsVO.getContainerType());
		containerVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
		containerVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		containerVO.setLastUpdateTime(containerDetailsVO.getLastUpdateTime());
		containerVO.setULDLastUpdateTime(containerDetailsVO.getUldLastUpdateTime());
		containerVO.setPou(containerDetailsVO.getPou());
		/*
		 * 
		 * FOR M39 (CARDIT_1.2/RESDIT_1.0)
		 */
		containerVO.setShipperBuiltCode(containerDetailsVO.getPaCode());
		containerVO.setContainerJnyID(containerDetailsVO.getContainerJnyId());
		
		Collection<OnwardRoutingVO> onwardRoutingVos = new ArrayList<OnwardRoutingVO>();
		String onwardFlightRouting = containerDetailsVO.getOnwardFlights();
		if (onwardFlightRouting != null) {
			String [] onwardFlightRoutings = onwardFlightRouting.split(",");
			if (onwardFlightRoutings != null && onwardFlightRoutings.length > 0) {
				for (String onwardFlightRoute : onwardFlightRoutings) {
					String [] onwardFlightRouteDetails =  onwardFlightRoute.split("-");
					OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
					onwardRoutingVO.setOnwardCarrierCode(onwardFlightRouteDetails[0]);
					onwardRoutingVO.setOnwardFlightNumber(onwardFlightRouteDetails[1]);
					StringBuilder OwnflightDate = new StringBuilder(onwardFlightRouteDetails[2]);
					OwnflightDate.append("-").append(onwardFlightRouteDetails[3]).append("-").append(onwardFlightRouteDetails[4]);
					LocalDate date =  new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					date.setDate(OwnflightDate.toString());
					onwardRoutingVO.setOnwardFlightDate(date);
					onwardRoutingVO.setPou(onwardFlightRouteDetails[5]);
					onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
					onwardRoutingVos.add(onwardRoutingVO);
				}
			}
			
			containerVO.setOnwardRoutings(onwardRoutingVos);
		}
		return containerVO;
	}
	
	

     }
