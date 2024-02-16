/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-4823
 *
 */
public interface ReassignDispatchSession extends ScreenSession{
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
	 * 
	 * @return OperationalFlightVO
	 */
	public OperationalFlightVO getOperationalFlightVO();
	/**
	 * 
	 * @param operationalFlightVO
	 */
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
	 /**
     * The getter method for DespatchDetailsVO
     * @return despatchDetailsVO
     */
    
	//Added by A-4810 as part of bug-fix-icrd-13564.
   public Collection<RoutingInConsignmentVO> getRoutingInConsignmentVOs();
   /**
	 * The setter method for RoutingInConsignmentVO
	 * @param RoutingInConsignmentVO
	 */
   public void setRoutingInConsignmentVOs(Collection<RoutingInConsignmentVO> routingInConsignmentVOs);
   
   
   /**
	 * 
	 * @return RoutingInConsignmentVO
	 */
	public RoutingInConsignmentVO getRoutingInConsignmentVO();
	/**
	 * 
	 * @param RoutingInConsignmentVO
	 */
	public void setRoutingInConsignmentVO(RoutingInConsignmentVO routingInConsignmentVO);
	//Added by A-4810 as part of bug-fix-icrd-13564 ends.
	/**
	 * The setter method for DespatchDetailsVO
	 * @param despatchDetailsVO
	 */
	public Collection<DespatchDetailsVO> getDespatchDetailsVOs();
	   /**
		 * The setter method for DespatchDetailsVO
		 * @param despatchDetailsVO
		 */
	public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);
	//Added by A-4810 as part of bug-fix-icrd-13564.
	 public ConsignmentDocumentVO getConsignmentDocumentVO();
	/**
	 * 
	 * @param despatchDetailsVO
	 */
	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO);
	   
	
	
}
