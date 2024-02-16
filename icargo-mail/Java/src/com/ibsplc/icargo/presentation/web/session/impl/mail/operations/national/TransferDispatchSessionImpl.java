/* TransferDispatchSessionImpl.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.TransferDispatchSession;
/**
 * @author A-4810
 *
 */

public class TransferDispatchSessionImpl extends AbstractScreenSession
implements TransferDispatchSession {

private static final String SCREEN_ID = "mailtracking.defaults.national.transfermail";
private static final String MODULE_NAME = "mail.operations";

private static final String KEY_MAILARRIVALVO = "mailArrivalVO";

private static final String KEY_CONTAINERDETAILSVOS = "containerDetailsVOs";
private static final String KEY_CONTAINERDETAILSVO = "containerDetailsVO";
private static final String KEY_DISPATCHDETAILSVOS = "dispatchDetailsVOs";
private static final String KEY_DISPATCHDETAILSVO = "dipatchDetailsVO";



/**
* @return SCREEN_ID - String
*/
@Override
public String getScreenID() {
return SCREEN_ID;
}

/**
* @return MODULE_NAME - String
*/
@Override
public String getModuleName() {
return MODULE_NAME;
}

/**
* The setter method for MailArrivalVO
* @param mailArrivalVO
*/
public void setMailArrivalVO(MailArrivalVO mailArrivalVO) {
setAttribute(KEY_MAILARRIVALVO,mailArrivalVO);
}
/**
* The getter method for mailArrivalVO
* @return mailArrivalVO
*/
public MailArrivalVO getMailArrivalVO() {
return getAttribute(KEY_MAILARRIVALVO);
}


/**
 * The setter method for containerDetailsVO
 * @param containerDetailsVO
 */
public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
	setAttribute(KEY_CONTAINERDETAILSVO,containerDetailsVO);
}
/**
 * The getter method for containerDetailsVO
 * @return containerDetailsVO
 */
public ContainerDetailsVO getContainerDetailsVO() {
	return getAttribute(KEY_CONTAINERDETAILSVO);
}

/**
 * This method is used to set ContainerDetailsVOs values to the session
 * @param containerDetailsVOs - Collection<ContainerDetailsVO>
 */
public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs) {
	setAttribute(KEY_CONTAINERDETAILSVOS,(ArrayList<ContainerDetailsVO>)containerDetailsVOs);
}

/**
 * This method returns the ContainerDetailsVOs
 * @return KEY_CONTAINERDETAILSVOS - Collection<ContainerDetailsVO>
 */
public Collection<ContainerDetailsVO> getContainerDetailsVOs() {
	return (Collection<ContainerDetailsVO>)getAttribute(KEY_CONTAINERDETAILSVOS);
}

public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO) {
	setAttribute(KEY_DISPATCHDETAILSVO,despatchDetailsVO);
}
/**
 * The getter method for containerDetailsVO
 * @return containerDetailsVO
 */
public DespatchDetailsVO getDespatchDetailsVO() {
	return getAttribute(KEY_DISPATCHDETAILSVO);
}

/**
 * This method is used to set ContainerDetailsVOs values to the session
 * @param containerDetailsVOs - Collection<ContainerDetailsVO>
 */
public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs) {
	setAttribute(KEY_DISPATCHDETAILSVOS,(ArrayList<DespatchDetailsVO>)despatchDetailsVOs);
}

/**
 * This method returns the ContainerDetailsVOs
 * @return KEY_CONTAINERDETAILSVOS - Collection<ContainerDetailsVO>
 */
public Collection<DespatchDetailsVO> getDespatchDetailsVOs() {
	return (Collection<DespatchDetailsVO>)getAttribute(KEY_DISPATCHDETAILSVOS);
}

}
