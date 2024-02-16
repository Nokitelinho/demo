/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;

/**
 * @author a-4823
 *
 */
public class AssignMailBagSessionImpl extends AbstractScreenSession
implements AssignMailBagSession{
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalflightvo";
	private static final String KEY_MAILMANIFESTVO = "mailmanifestvo";	
	private static final String KEY_ASSIGNMAILBAGVOS = "assignMailBagVOs";
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
	private static final String PRINT_TYPES = "printTypes";
	/**
	 * Constant for the session variable oneTimeVOs
	 */
	private static final String KEY_ONETIME_VO = "oneTimeVOs";


	public void setAssignMailBagVOs(MailManifestVO mailManifestVO) {

		setAttribute(KEY_ASSIGNMAILBAGVOS,mailManifestVO);	
	}

	public MailManifestVO getAssignMailBagVOs() {

		return getAttribute(KEY_ASSIGNMAILBAGVOS);
	}


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
	/**
	 * This method is used to set onetime values to the session
	 * @param mailCategory - Collection<OneTimeVO>
	 */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
	 * This method returns the onetime vos
	 * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	  /**
     * This method is used to set printTypes values to the session
     * @param PRINT_TYPES - Collection<String>
     */
	public void setPrintTypes(Collection<String> printTypes) {
		setAttribute(PRINT_TYPES,(ArrayList<String>)printTypes);
	}
   
    /**
     * This method returns the printTypes vos
     * @return printTypes - Collection<String>
     */
	public Collection<String> getPrintTypes() {
		return (Collection<String>)getAttribute(PRINT_TYPES);
	}

	public String getModuleName() {

		return MODULE_NAME;
	}


	public String getScreenID() {

		return SCREEN_ID;
	}

}
