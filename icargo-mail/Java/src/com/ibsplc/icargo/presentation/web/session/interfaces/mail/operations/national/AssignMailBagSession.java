/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-4823
 *
 */
public interface AssignMailBagSession extends ScreenSession{

	

	/**
	 * This method is used to set FlightValidationVO to the session
	 * @param flightValidationVO - FlightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);

	/**
	 * This method returns the FlightValidationVO
	 * @return FLIGHTVALIDATIONVO - FlightValidationVO
	 */
	public FlightValidationVO getFlightValidationVO();
	/**
	 * 
	 * @param mailManifestVO
	 */
	public void setMailManifestVO(MailManifestVO mailManifestVO);
	/**
	 * 
	 * @return MailManifestVO
	 */
	public MailManifestVO getMailManifestVO();	
	/**
	 * 
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
	/**
	 * 
	 * @return OperationalFlightVO
	 */
	public OperationalFlightVO getOperationalFlightVO();

	/**
	 * @return Returns the oneTimeVOs.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	/**
	 * @param oneTimeVOs The oneTimeVOs to set.
	 */
	public void setOneTimeVOs(
			HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
	/**
	 * 
	 * @return MailManifestVO
	 */
	public MailManifestVO getAssignMailBagVOs();
	/**
	 * 
	 * @param mailManifestVO
	 */
	public void setAssignMailBagVOs(MailManifestVO mailManifestVO);
	/**
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory);

	/**
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	  /**
     * This method is used to set printTypes values to the session
     * @param printTypes - Collection<String>
     */
	public void setPrintTypes(Collection<String> printTypes);
   
    /**
     * This method returns the printTypes vos
     * @return printTypes - Collection<String>
     */
	public Collection<String> getPrintTypes();
	
	
	

}
