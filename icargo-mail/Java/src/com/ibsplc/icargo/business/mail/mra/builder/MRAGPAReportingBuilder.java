
package com.ibsplc.icargo.business.mail.mra.builder;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.GPAReportingController;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MRAGPAReportingBuilder extends AbstractActionBuilder {
	
	private Log log = LogFactory.getLogger("MRAGPAREPORTING BUILDER");
	private static final String CLASSNAME ="MRAGPAReportingBuilder";
	private static final String CHECK_PROCESS ="checkAutoProcessing";
	private static final String TGR_ACC ="triggerAccountingAtBatchLevel";
	public void checkAutoProcessing(Collection<MailInvoicMessageVO> mailInvoicMessage) throws SystemException{
		
		log.entering(CLASSNAME, CHECK_PROCESS);		
		new GPAReportingController().checkAutoProcessing(mailInvoicMessage);
		log.exiting(CLASSNAME, CHECK_PROCESS);
	}
     public void triggerAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws SystemException{
	
		log.entering(CLASSNAME, TGR_ACC);		
		new ReceivableManagementController().triggerAccountingAtBatchLevel(advancePaymentVO);
		log.exiting(CLASSNAME, TGR_ACC);
	}
public void triggerReverseAccountingAtBatchLevel(PaymentBatchDetailsVO advancePaymentVO) throws SystemException{
	
	log.entering(CLASSNAME, TGR_ACC);		
	new ReceivableManagementController().triggerReverseAccountingAtBatchLevel(advancePaymentVO);
	log.exiting(CLASSNAME, TGR_ACC);
}	

}

