/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReturnDispatchSession;

/**
 * @author a-4823
 *
 */
public class ReturnDispatchSessionImpl extends AbstractScreenSession
implements ReturnDispatchSession{
	private static final String SCREEN_ID = "mailtracking.defaults.national.return";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalflightvo";
	private static final String KEY_MAILMANIFESTVO = "mailmanifestvo";	
	private static final String KEY_SELECTEDDSNVO = "dsnvo";
	private static final String KEY_SELECTEDDAMAGEDDSNVO = "damageddsnvo";
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
	public Collection<DSNVO> getSelectedDSNVO() {

		return (Collection<DSNVO>)getAttribute(KEY_SELECTEDDSNVO);
	}


	public void setSelectedDSNVO(Collection<DSNVO> dsnvo) {

		setAttribute(KEY_SELECTEDDSNVO,(ArrayList<DSNVO>) dsnvo);
	}
	/**
	 * 
	 */
	public Collection<DamagedDSNVO> getSelectedDamagedDSNVO() {

		return (Collection<DamagedDSNVO>)getAttribute(KEY_SELECTEDDAMAGEDDSNVO);
	}
	/**
	 * 
	 */
	public void setSelectedDamagedDSNVO(Collection<DamagedDSNVO> damagedDSNVOs) {

		setAttribute(KEY_SELECTEDDAMAGEDDSNVO,(ArrayList<DamagedDSNVO>) damagedDSNVOs);
		
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
	public String getModuleName() {

		return MODULE_NAME;
	}


	public String getScreenID() {

		return SCREEN_ID;
	}

}
