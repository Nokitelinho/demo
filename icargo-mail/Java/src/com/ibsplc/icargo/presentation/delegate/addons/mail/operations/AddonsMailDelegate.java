package com.ibsplc.icargo.presentation.delegate.addons.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

@Module("addonsmail")
@SubModule("operations")
public class AddonsMailDelegate extends BusinessDelegate{
	
	private static final String MODULE = "AddonsMailDelegate";

	public Page<MailBookingDetailVO> findMailBookingAWBs(
		MailBookingFilterVO mailBookingFilterVO,int pageNumber ) throws BusinessDelegateException {
		log.entering(MODULE, "findMailBookingAWBs");
		return despatchRequest("findMailBookingAWBs",mailBookingFilterVO,pageNumber);
	}
	public void dettachMailBookingDetails(
			Collection<MailbagVO> selectedMailBagVO,CarditEnquiryFilterVO carditEnquiryFilterVO) throws BusinessDelegateException{
	
		 despatchRequest("dettachMailBookingDetails", selectedMailBagVO,carditEnquiryFilterVO);
		
	}
	
	public void validateMailTags(Collection<MailbagVO> selectedMailBagVO) throws BusinessDelegateException{
		 log.entering(MODULE, "validateMailTags");
		 despatchRequest("validateMailTags", selectedMailBagVO);
	}
	
	public int saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,MailBookingDetailVO mailBookingDetailVO,CarditEnquiryFilterVO carditEnquiryFilterVO)throws BusinessDelegateException{
		log.entering(MODULE, "saveMailBookingDetails");
		return despatchRequest("saveMailBookingDetails", selectedMailBagVO,mailBookingDetailVO,carditEnquiryFilterVO);	
	}

	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws BusinessDelegateException {
		this.log.entering(MODULE, "execute");
		return despatchRequest("fetchBookedFlightDetails", companyCode, shipmentPrefix, masterDocumentNumber);
	}

	public void saveMailBookingFlightDetails(Collection<MailbagVO> selectedMailBagVO,
			Collection<MailBookingDetailVO> selectedMailBookingDetailVOs) throws BusinessDelegateException {
		log.entering(MODULE, "saveMailBookingFlightDetails");
		despatchRequest("saveMailBookingFlightDetails", selectedMailBagVO, selectedMailBookingDetailVOs);

	}
	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws BusinessDelegateException{
		log.entering(MODULE, "findLoadPlanBookings");
		return despatchRequest("findLoadPlanBookings",mailBookingFilterVO,pageNumber);
	}
	
	public Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws BusinessDelegateException {
		this.log.entering(MODULE, "execute");
		return despatchRequest("findFlightDetailsforAWB", companyCode, shipmentPrefix, masterDocumentNumber);
	}   
    
    public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws BusinessDelegateException{
		log.entering(MODULE, "findManifestBookings");
		return despatchRequest("findManifestBookings",mailBookingFilterVO,pageNumber);
	}
    
	public int findSplitCount(String mstDocunum, String docOwnerId, String companyCode, int seqNum, int dupNum)
			throws BusinessDelegateException {
		log.entering(MODULE, "findSplitCount");
		return despatchRequest("findSplitCount", mstDocunum, docOwnerId, companyCode, seqNum, dupNum);
	}

}
