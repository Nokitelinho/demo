/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.OffloadDispatchSession;

/**
 * @author a-4823
 *
 */
public class OffloadDispatchSessionImpl extends AbstractScreenSession
implements OffloadDispatchSession{
	private static final String SCREEN_ID = "mailtracking.defaults.national.offload";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalflightvo";
	private static final String KEY_MAILMANIFESTVO = "mailmanifestvo";
	private static final String KEY_DESPATCHDETAILSVO = "despatchdetailsvo";
	private static final String KEY_SELECTEDDSNVO = "dsnvo";
	private static final String ONETIME_OFFLOAD_REASONCODE = "offload_reasoncode";
	private static final String KEY_DISPATCHDETAILSVOS = "dispatchDetailsVOs";
	
	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";

	public void setFlightValidationVO(FlightValidationVO flightValidationVO) {

		setAttribute(KEY_FLIGHTVALIDATIONVO,flightValidationVO);	
	}


	public FlightValidationVO getFlightValidationVO() {

		return getAttribute(KEY_FLIGHTVALIDATIONVO);
	}

	public void setMailManifestVO(MailManifestVO mailManifestVO) {
		setAttribute(KEY_MAILMANIFESTVO,mailManifestVO);	

	}


	public MailManifestVO getMailManifestVO() {
		return getAttribute(KEY_MAILMANIFESTVO);
	}


	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO) {
		setAttribute(KEY_OPERATIONALFLIGHTVO,operationalFlightVO);

	}


	public OperationalFlightVO getOperationalFlightVO() {
		return getAttribute(KEY_OPERATIONALFLIGHTVO);
	}


	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {

		return (HashMap<String, Collection<OneTimeVO>>)
		getAttribute(KEY_ONETIME_VO);
	}


	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(
				HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);

	}


	public String getModuleName() {

		return MODULE_NAME;
	}


	public String getScreenID() {

		return SCREEN_ID;
	}

	public DespatchDetailsVO getDespatchDetailsVO() {
		
		return getAttribute(KEY_DESPATCHDETAILSVO);
	}
	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO) {
		setAttribute (KEY_DESPATCHDETAILSVO,despatchDetailsVO);
		
	}
	public Collection<DSNVO> getSelectedDSNVO() {
		
		return (Collection<DSNVO>)getAttribute(KEY_SELECTEDDSNVO);
	}


	public void setSelectedDSNVO(Collection<DSNVO> dsnvo) {
	
		setAttribute(KEY_SELECTEDDSNVO,(ArrayList<DSNVO>) dsnvo);
	}
	/**
     * This method is used to set onetime values to the session
     * @param offloadreasoncode - Collection<OneTimeVO>
     */
	public void setOffloadReasonCode(Collection<OneTimeVO> offloadreasoncode) {
		setAttribute(ONETIME_OFFLOAD_REASONCODE,(ArrayList<OneTimeVO>)offloadreasoncode);
	}

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_REASONCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadReasonCode() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_OFFLOAD_REASONCODE);
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
