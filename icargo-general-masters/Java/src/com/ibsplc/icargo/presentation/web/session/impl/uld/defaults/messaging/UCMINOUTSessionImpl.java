/*
 * UCMINOUTSessionImpl.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;

/**
 * 
 * @author A-2046
 * 
 */
public class UCMINOUTSessionImpl extends AbstractScreenSession implements
		UCMINOUTSession {

	private static final String SCREENID = "uld.defaults.ucminoutmessaging";

	private static final String MODULE = "uld.defaults";

	private static final String KEY_FLIGHTDETAILS = "flightDetails";

	private static final String KEY_RECONCILEVO = "reconcileVO";

	private static final String KEY_MSGSTATUS = "messageStatus";

	private static final String KEY_DESTN = "destination";

	private static final String KEY_OUTDESTN = "outdestination";

	private static final String KEY_CONTENT = "content";
	
	private static final String KEY_NEW_CONTENT = "newcontent";
	
	private static final String KEY_MSGDSPTC_DETAILSVOS = "msgDsptchDetailsVOs";

	private static final String KEY_ONETIMES = "onetimeforuldsourceandstatus";//Added by a-3459 as part of ICRD-192413

	/**
	 * 
	 * /** Method to get ScreenID
	 * 
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 * 
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
/**
 * @return
 */
	public FlightValidationVO getFlightValidationVOSession() {
		return getAttribute(KEY_FLIGHTDETAILS);
	}
	/**
	 * @param flightValidationVO
	 */
	public void setFlightValidationVOSession(
			FlightValidationVO flightValidationVO) {
		setAttribute(KEY_FLIGHTDETAILS, flightValidationVO);
	}
	/**
	 * @return
	 */
	public ULDFlightMessageReconcileVO getMessageReconcileVO() {
		return getAttribute(KEY_RECONCILEVO);
	}
	/**
	 * @param reconcileVO
	 */
	public void setMessageReconcileVO(ULDFlightMessageReconcileVO reconcileVO) {
		setAttribute(KEY_RECONCILEVO, reconcileVO);
	}
	/**
	 * @param messageStatus
	 */
	public void setMessageStatus(String messageStatus) {
		setAttribute(KEY_MSGSTATUS, messageStatus);
	}
	/**
	 * @return
	 */
	public String getMessageStatus() {

		return getAttribute(KEY_MSGSTATUS);
	}
	/**
	 * @param destinations
	 */
	public void setDestinations(ArrayList<String> destinations) {
		setAttribute(KEY_DESTN, destinations);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getDestinations() {
		return getAttribute(KEY_DESTN);
	}
	/**
	 * @param destinations
	 */
	public void setOutDestinations(ArrayList<String> destinations) {
		setAttribute(KEY_OUTDESTN, destinations);
	}
	/**
	 * @return
	 */
	public ArrayList<String> getOutDestinations() {
		return getAttribute(KEY_OUTDESTN);
	}

	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getContentType() {
		return getAttribute(KEY_CONTENT);
	}

	/**
	 * @param contentType
	 */
	public void setContentType(ArrayList<OneTimeVO> contentType) {
		setAttribute(KEY_CONTENT, contentType);
	}
	
	/**
	 * @return
	 */
	public ArrayList<OneTimeVO> getNewContentType() {
		return getAttribute(KEY_NEW_CONTENT);
	}

	/**
	 * @param newContentType
	 */
	public void setNewContentType(ArrayList<OneTimeVO> newContentType) {
		setAttribute(KEY_NEW_CONTENT,newContentType);
		
	}


	/**
	 * @param msgDsptchDetailsVOs
	 */
	public void setMsgDsptcDetailsVOs(
			Collection<MessageDespatchDetailsVO> msgDsptchDetailsVOs) {
		setAttribute(KEY_MSGDSPTC_DETAILSVOS, (ArrayList<MessageDespatchDetailsVO>)msgDsptchDetailsVOs);
	}
	
/**
 * @return the keyMsgdsptcDetails
 */
	public Collection<MessageDespatchDetailsVO> getMsgDsptcDetailsVOs() {
		return (ArrayList<MessageDespatchDetailsVO>)getAttribute(KEY_MSGDSPTC_DETAILSVOS);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession#getOneTimeValues()
	 *	Added by 			: A-7359 on 24-Aug-2017
	 * 	Used for 	:     ICRD-192413
	 *	Parameters	:	@return
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues(){
		   return getAttribute(KEY_ONETIMES);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession#setOneTimeValues(java.util.HashMap)
	 *	Added by 			: A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param oneTimeValues
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues){
		   setAttribute(KEY_ONETIMES,oneTimeValues);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession#removeOnetimeValues()
	 *	Added by 			: A-7359 on 24-Aug-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:
	 */
	public void removeOnetimeValues(){
		removeAttribute(KEY_ONETIMES);
	}
}
