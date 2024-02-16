/*
 * UCMINOUTSession.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * 
 * @author A-2046
 * 
 */
public interface UCMINOUTSession extends ScreenSession {
	/**
	 * 
	 * @return screenID
	 */
	public String getScreenID();

	/**
	 * 
	 * @return modulename
	 */
	public String getModuleName();

	/**
	 * 
	 * @return FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVOSession();

	/**
	 * 
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO);
/**
 * 
 * @return
 */
	public ULDFlightMessageReconcileVO getMessageReconcileVO();
/**
 * 
 * @param reconcileVO
 */
	public void setMessageReconcileVO(ULDFlightMessageReconcileVO reconcileVO);
/**
 * 
 * @return
 */
	public String getMessageStatus();
/**
 * 
 * @param messageStatus
 */
	public void setMessageStatus(String messageStatus);
/**
 * 
 * @param destinations
 */
	public void setDestinations(ArrayList<String> destinations);
/**
 * 
 * @return
 */
	public ArrayList<String> getDestinations();
/**
 * 
 * @param destinations
 */
	public void setOutDestinations(ArrayList<String> destinations);
/**
 * 
 * @return
 */
	public ArrayList<String> getOutDestinations();
	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getContentType();

	/**
	 * 
	 * @param contentType
	 */
	public void setContentType(ArrayList<OneTimeVO> contentType);
	/**
	 * 
	 * @return
	 */
	public ArrayList<OneTimeVO> getNewContentType();
	/**
	 * 
	 * @param newContentType
	 */
	public void setNewContentType(ArrayList<OneTimeVO> newContentType);
	/**
	 * 
	 * @return 
	 */
	public Collection<MessageDespatchDetailsVO> getMsgDsptcDetailsVOs();
	/**
	 * 
	 * @param msgDsptchDetailsVOs
	 */
	public void setMsgDsptcDetailsVOs(Collection<MessageDespatchDetailsVO> msgDsptchDetailsVOs);
    /**
     * 
     * 	Method		:	UCMINOUTSession.getOneTimeValues
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:   ICRD-192413
     *	Parameters	:	@return 
     *	Return type	: 	HashMap<String,Collection<OneTimeVO>>
     */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	/**
	 * 
	 * 	Method		:	UCMINOUTSession.setOneTimeValues
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param oneTimeValues 
	 *	Return type	: 	void
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	/**
	 * 
	 * 	Method		:	UCMINOUTSession.removeOnetimeValues
	 *	Added by 	:	A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	 
	 *	Return type	: 	void
	 */
	public void removeOnetimeValues();
}
