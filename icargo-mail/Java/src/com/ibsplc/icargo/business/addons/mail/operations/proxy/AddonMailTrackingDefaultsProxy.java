package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.proxy.SubSystemProxy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class AddonMailTrackingDefaultsProxy extends SubSystemProxy {



	private static final String SERVICE_NAME = "MAIL_OPERATIONS";

	private MailTrackingDefaultsBI constructService() throws ServiceNotAccessibleException {
		return  getService(SERVICE_NAME);
	}

	public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException, ProxyException {
		Page<MailbagVO> carditMails = null;
		try {
			carditMails = constructService().findCarditMails(carditEnquiryFilterVO, pageNumber);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}
		return carditMails;
	}

	public long findMailBagSequenceNumberFromMailIdr(String mailbagId, String companyCode)
			throws SystemException, ProxyException {
		long mailSequenceNumber = 0;
		try {
			mailSequenceNumber = constructService().findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);
		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);
		}

		return mailSequenceNumber;
	}

	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs) throws SystemException, ProxyException {

		try {

			constructService().dettachMailBookingDetails(mailbagVOs);

		} catch (RemoteException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (ServiceNotAccessibleException e) {
			throw new ProxyException(e.getMessage(), e);

		}
	}

}
