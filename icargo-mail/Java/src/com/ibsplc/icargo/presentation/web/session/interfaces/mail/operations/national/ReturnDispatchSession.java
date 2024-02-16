/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-4823
 *
 */
public interface ReturnDispatchSession extends ScreenSession {
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
	 * @return Collection<DSNVO>
	 */
	public Collection<DSNVO> getSelectedDSNVO();
	/**
	 * 
	 * @param dsnvo
	 */
	public void setSelectedDSNVO(Collection<DSNVO> dsnvo);
	/**
	 * 
	 * @return DamagedDSNVO
	 */
	public Collection<DamagedDSNVO> getSelectedDamagedDSNVO();
	/**
	 * 
	 * @param damagedDSNVOs
	 */
	public void setSelectedDamagedDSNVO(Collection<DamagedDSNVO> damagedDSNVOs);
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
