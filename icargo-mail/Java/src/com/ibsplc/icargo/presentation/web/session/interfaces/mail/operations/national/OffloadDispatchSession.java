/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-4823
 *
 */
public interface OffloadDispatchSession extends ScreenSession{
	/**
	 * 
	 * @param flightValidationVO
	 */
	public void setFlightValidationVO(FlightValidationVO flightValidationVO);
	/**
	 * 
	 * @return FlightValidationVO
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
	 * @return DespatchDetailsVO
	 */
	public DespatchDetailsVO getDespatchDetailsVO();
	/**
	 * 
	 * @param despatchDetailsVO
	 */
	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO);

	/**
	 * 
	 * @return Collection<DSNVO>
	 */
	public Collection<DSNVO> getSelectedDSNVO();
	/**
	 * 
	 * @param dsnvo
	 */
	public void setSelectedDSNVO(Collection<DSNVO> dsnvo);
	/**
     * This method is used to set onetime values to the session
     * @param offloadreasoncode - Collection<OneTimeVO>
     */
	public void setOffloadReasonCode(Collection<OneTimeVO> offloadreasoncode);

	/**
     * This method returns the onetime vos
     * @return ONETIME_OFFLOAD_REASONCODE - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getOffloadReasonCode();
	 /**
     * The getter method for DespatchDetailsVO
     * @return despatchDetailsVO
     */
    
    
   public Collection<DespatchDetailsVO> getDespatchDetailsVOs();
   /**
	 * The setter method for DespatchDetailsVO
	 * @param despatchDetailsVO
	 */
   public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);
	
}

