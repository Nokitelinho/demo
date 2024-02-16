package com.ibsplc.icargo.services.addons.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.AddonsMailOperationsBI;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.business.addons.mail.operations.AddonsMailController;
import com.ibsplc.xibase.server.framework.audit.Auditor;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

public class AddonsMailOperationsServicesEJB extends AbstractFacadeEJB implements AddonsMailOperationsBI, Auditor {
	@Override
	public Page<MailBookingDetailVO> findMailBookingAWBs(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		return new AddonsMailController().findMailBookingAWBs(mailBookingFilterVO,pageNumber);
	}

	public int saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO, MailBookingDetailVO mailBookingDetailVO,
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException, RemoteException, ProxyException {
		return new AddonsMailController().saveMailBookingDetails(selectedMailBagVO, mailBookingDetailVO,
				carditEnquiryFilterVO);
	}
	
	public void  validateMailTags(Collection<MailbagVO> selectedMailBagVO) 
			   throws SystemException,RemoteException, ProxyException{
		new AddonsMailController().validateMailTags(selectedMailBagVO);
		
	}
	/**
	 * 
	 * Overriding Method : @see
	 * com.ibsplc.icargo.business.addons.mail.operations.AddonsMailOperationsBI#performMailAWBTransactions(com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO)
	 * Added by : A-8061 on 08-Oct-2021 Used for : Parameters : @param
	 * mailFlightSummaryVO Parameters : @throws SystemException Parameters
	 * : @throws RemoteException
	 */
	public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException,RemoteException{
		new AddonsMailController().performMailAWBTransactions(mailFlightSummaryVO);
	}
	

	@Override
	public void dettachMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException, RemoteException, FinderException{
		
		new AddonsMailController().dettachMailBookingDetails(selectedMailBagVO, carditEnquiryFilterVO);
	}
	
	/**
	 * 
	 * Overriding Method : @see
	 * com.ibsplc.icargo.business.addons.mail.operations.AddonsMailOperationsBI#saveContainerTransfer(com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO,
	 * com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO) Added
	 * by : A-8061 on 19-Oct-2021 Parameters : @param containerDetailsVO
	 * Parameters : @param mailFlightSummaryVO Parameters : @return Parameters
	 * : @throws SystemException Parameters : @throws RemoteException
	 */
	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO, MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException, RemoteException {
 		return new AddonsMailController().saveContainerTransfer(containerDetailsVO,mailFlightSummaryVO);
	}
	@Override
	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, RemoteException {
		return new AddonsMailController().fetchBookedFlightDetails(companyCode, shipmentPrefix, masterDocumentNumber);
	}
	@Override
	public void saveMailBookingFlightDetails(Collection<MailbagVO> selectedMailBagVO,
			Collection<MailBookingDetailVO> selectedMailBookingDetailVO)
			throws SystemException, RemoteException, ProxyException, FinderException {
		new AddonsMailController().saveMailBookingFlightDetails(selectedMailBagVO, selectedMailBookingDetailVO);
	}

	@Override
	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		return new AddonsMailController().findLoadPlanBookings(mailBookingFilterVO,pageNumber);
	}
	
	@Override
	public Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, RemoteException {
		return new AddonsMailController().findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);
	} 
   
    @Override
	public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		return new AddonsMailController().findManifestBookings(mailBookingFilterVO,pageNumber);
	}

	@Override
	public int findSplitCount(String mstDocunum, String docOwnerId, String companyCode, int seqNum, int dupNum)
			throws SystemException,RemoteException {

		return new AddonsMailController().findSplitCount(mstDocunum, docOwnerId, companyCode, seqNum, dupNum);
	}
}
