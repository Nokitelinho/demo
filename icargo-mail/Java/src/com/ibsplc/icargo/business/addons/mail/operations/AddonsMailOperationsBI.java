package com.ibsplc.icargo.business.addons.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interfaces.BusinessInterface;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

public interface AddonsMailOperationsBI extends BusinessInterface{


	public Page<MailBookingDetailVO> findMailBookingAWBs(
			MailBookingFilterVO mailBookingFilterVO,int pageNumber)
				throws SystemException, RemoteException;
	
	public void  validateMailTags(Collection<MailbagVO> selectedMailBagVO) 
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ProxyException;
	
	public int saveMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,MailBookingDetailVO mailBookingDetailVO,CarditEnquiryFilterVO carditEnquiryFilterVO)
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ProxyException;

	
	/**
	 * 
	 * 	Method		:	AddonsMailOperationsBI.performMailAWBTransactions
	 *	Added by 	:	A-8061 on 08-Oct-2021
	 * 	Used for 	:
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@param eventCode
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException,RemoteException;
	
	public void dettachMailBookingDetails(Collection<MailbagVO> selectedMailBagVO,
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException, RemoteException,
			MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, FinderException;
	
	/**
	 * 
	 * 	Method		:	AddonsMailOperationsBI.saveContainerTransfer
	 *	Added by 	:	A-8061 on 19-Oct-2021
	 *	Parameters	:	@param containerDetailsVO
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	boolean
	 */
	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO,MailFlightSummaryVO mailFlightSummaryVO)  throws SystemException, RemoteException;
	
	public abstract Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, RemoteException;
	public void saveMailBookingFlightDetails(Collection<MailbagVO> selectedMailBagVO,Collection<MailBookingDetailVO> selectedMailBookingDetailVOs)
			   throws SystemException,RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException, ProxyException, FinderException;
	
	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException, RemoteException;
	
	public  Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException, RemoteException;   

    public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO,int pageNumber) throws SystemException, RemoteException;
    public int  findSplitCount(String mstDocunum,String docOwnerId,String companyCode,int seqNum,int dupNum) throws SystemException,RemoteException; 
}