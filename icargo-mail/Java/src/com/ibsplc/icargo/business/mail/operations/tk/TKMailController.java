package com.ibsplc.icargo.business.mail.operations.tk;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.tk.TKMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Feb-2021		:	Draft
 */
@Module("mail")
@SubModule("operations")
public class TKMailController extends MailController {
	
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String CLASS_NAME = "TKMailController";
	
	/**
	 * 
	 * 	Method		:	TKMailController.sendULDAnnounceForFlight
	 *	Added by 	:	A-8164 on 12-Feb-2021
	 * 	Used for 	:
	 *	Parameters	:	@param mailManifestVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void sendULDAnnounceForFlight(MailManifestVO mailManifestVO) 
			throws SystemException{
		Collection<ContainerDetailsVO> containerDetailVOs = 
				mailManifestVO.getContainerDetails();
		TKMailController tkMailController= 
				(TKMailController)SpringAdapter.getInstance().getBean("tkMailController");
		String airportCode=getLogonAirport();
		if(Objects.nonNull(containerDetailVOs) && !containerDetailVOs.isEmpty()){
			containerDetailVOs.forEach(containerDetailsVO -> {
				containerDetailsVO.setAirportCode(airportCode);
				tkMailController.sendUcsAnnounce(containerDetailsVO);  
			});
		}
	}
	
	/**
	 * 
	 * 	Method		:	TKMailController.sendUcsAnnounce
	 *	Added by 	:	A-8164 on 12-Feb-2021
	 * 	Used for 	:	Event to send UCS announce for mail uld. Is mapped in common event-configuration.xml in which
	 * 					MHS_MESSAGE_CHANNEL is mapped with event. Entry in addons-flow-beans-SpringContext.xml 
	 * 					to create bean for the MHSMessageGenerator and MaterialHandlingMapper
	 *	Parameters	:	@param containerDetailsVO 
	 *	Return type	: 	void
	 */
	@Raise(module="mail",submodule="operations",event="SEND_UCS_ANNOUNCE_EVENT_FOR_MAILULD",methodId="mail.operations.senducsannounceeventformailuld")
	public void sendUcsAnnounce(ContainerDetailsVO containerDetailsVO){
		log.entering(CLASS_NAME, "sendUcsAnnounce");
		log.exiting(CLASS_NAME, "sendUcsAnnounce");		
	}
}
