package com.ibsplc.neoicargo.mail.tk.component;

import java.util.Collection;
import java.util.Objects;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailManifestVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.tk.TKMailController.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	12-Feb-2021		:	Draft
 */
@Slf4j
public class TKMailController extends MailController {
	private static final String CLASS_NAME = "TKMailController";

	/** 
	* Method		:	TKMailController.sendULDAnnounceForFlight Added by 	:	A-8164 on 12-Feb-2021 Used for 	: Parameters	:	@param mailManifestVO Parameters	:	@throws SystemException  Return type	: 	void
	*/
	public void sendULDAnnounceForFlight(MailManifestVO mailManifestVO) {
		Collection<ContainerDetailsVO> containerDetailVOs = mailManifestVO.getContainerDetails();
		TKMailController tkMailController = ContextUtil.getInstance().getBean(TKMailController.class);
		String airportCode = getLogonAirport();
		if (Objects.nonNull(containerDetailVOs) && !containerDetailVOs.isEmpty()) {
			containerDetailVOs.forEach(containerDetailsVO -> {
				containerDetailsVO.setAirportCode(airportCode);
				tkMailController.sendUcsAnnounce(containerDetailsVO);
			});
		}
	}

	/** 
	* Method		:	TKMailController.sendUcsAnnounce Added by 	:	A-8164 on 12-Feb-2021 Used for 	:	Event to send UCS announce for mail uld. Is mapped in common event-configuration.xml in which MHS_MESSAGE_CHANNEL is mapped with event. Entry in addons-flow-beans-SpringContext.xml  to create bean for the MHSMessageGenerator and MaterialHandlingMapper Parameters	:	@param containerDetailsVO  Return type	: 	void
	*/
	public void sendUcsAnnounce(ContainerDetailsVO containerDetailsVO) {
		log.debug(CLASS_NAME + " : " + "sendUcsAnnounce" + " Entering");
		log.debug(CLASS_NAME + " : " + "sendUcsAnnounce" + " Exiting");
	}
}
