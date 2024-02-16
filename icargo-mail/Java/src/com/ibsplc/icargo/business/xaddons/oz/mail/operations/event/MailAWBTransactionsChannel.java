package com.ibsplc.icargo.business.xaddons.oz.mail.operations.event;

import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailSummaryDeatilsVO;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * @author A-8816
 *
 */
@Module("mail")
@SubModule("operations")
@EventChannel(value = "OZ-performMailAWBTransactions", targetClass = MailSummaryDeatilsVO.class, listeners = {
		@com.ibsplc.icargo.framework.event.annotations.EventListener(event = "OPERATIONS_SHIPMENT_SAVECOLLECTIONLISTDELIVERY") })
public class MailAWBTransactionsChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws SystemException, BusinessException {
		MailSummaryDeatilsVO  mailSummaryVO = (MailSummaryDeatilsVO) eventVO.getPayload();
		despatchRequest("performMailAWBTransactions",  mailSummaryVO.getMailFlightSummaryVO(),mailSummaryVO.getEventCode());
	}
}
