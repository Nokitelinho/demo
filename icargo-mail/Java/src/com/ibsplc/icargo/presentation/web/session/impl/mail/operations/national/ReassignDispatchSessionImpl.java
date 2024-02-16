/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.national;

import java.util.ArrayList;
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
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReassignDispatchSession;

/**
 * @author a-4823
 *
 */
public class ReassignDispatchSessionImpl extends AbstractScreenSession
implements ReassignDispatchSession {
	private static final String SCREEN_ID = "mailtracking.defaults.national.reassign";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_FLIGHTVALIDATIONVO = "flightvalidationvo";
	private static final String KEY_OPERATIONALFLIGHTVO = "operationalflightvo";
	private static final String KEY_MAILMANIFESTVO = "mailmanifestvo";	
	private static final String KEY_DESPATCHDETAILSVO = "despatchdetailsvo";
	private static final String KEY_ROUTINGDETAILSVO = "routinginconsiginmentvo";
	private static final String KEY_ROUTINGDETAILSVOS = "routinginconsiginmentvos";
	private static final String KEY_SELECTEDDSNVO = "dsnvo";
	private static final String KEY_DISPATCHDETAILSVOS = "dispatchDetailsVOs";
	private static final String KEY_CONSIGNMENTDETAILSVO = "consignmentDetailsVOs";
	
	
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

	//Added by A-4810 for bug-fix icrd-13564
	public RoutingInConsignmentVO getRoutingInConsignmentVO() {

		return getAttribute(KEY_ROUTINGDETAILSVO);
	}

	public void setRoutingInConsignmentVO(RoutingInConsignmentVO routingInConsignmentVO) {
		setAttribute(KEY_ROUTINGDETAILSVO,routingInConsignmentVO);

	}
	
	public Collection<RoutingInConsignmentVO> getRoutingInConsignmentVOs() {
		
		return (Collection<RoutingInConsignmentVO>)getAttribute(KEY_ROUTINGDETAILSVOS);
	}


	public void setRoutingInConsignmentVOs(Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
	
		setAttribute(KEY_ROUTINGDETAILSVOS,(ArrayList<RoutingInConsignmentVO>) routingInConsignmentVOs);
	}
	//Added by A-4810 for bug-fix icrd-13564 ends
	
	public DespatchDetailsVO getDespatchDetailsVO() {

		return getAttribute(KEY_DESPATCHDETAILSVO);
	}

	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO) {
		setAttribute(KEY_DESPATCHDETAILSVO,despatchDetailsVO);

	}


	public Collection<DSNVO> getSelectedDSNVO() {
	
		return (Collection<DSNVO>)getAttribute(KEY_SELECTEDDSNVO);
	}


	public void setSelectedDSNVO(Collection<DSNVO> dsnvo) {
	
		setAttribute(KEY_SELECTEDDSNVO,(ArrayList<DSNVO>) dsnvo);
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
	
	
	//Added by A-4810 for bug-fix icrd-13564 
	public ConsignmentDocumentVO getConsignmentDocumentVO() {

		return getAttribute(KEY_CONSIGNMENTDETAILSVO);
	}

	public void setConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO) {
		setAttribute(KEY_CONSIGNMENTDETAILSVO,consignmentDocumentVO);

	}
	//Added by A-4810 for bug-fix icrd-13564 ends

}
