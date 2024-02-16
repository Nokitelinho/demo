package com.ibsplc.icargo.business.addons.mail.operations.event;

import com.ibsplc.icargo.business.addons.mail.operations.event.evaluator.ExportManifestMailAWBEvaluator;
import com.ibsplc.icargo.business.addons.mail.operations.event.evaluator.FinalizeFlightMailAWBEvaluator;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.event.MailAWBChannel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("addonsmail")
@SubModule("operations")
@EventChannel(value = "mailAWBChannel", targetClass = MailFlightSummaryVO.class, listeners = {
		@EventListener(evaluator = ExportManifestMailAWBEvaluator.class,event = "SAVEMANIFEST_EVENT"),
		@EventListener(evaluator = FinalizeFlightMailAWBEvaluator.class,event = "OPERATION_FLTHANDLING_FINALISEEXPORTFLIGHT"),
		@EventListener(event = "OPERATIONS_SHIPMENT_SAVECOLLECTIONLISTDELIVERY"),
		@EventListener(event = "SAVE_ULD_ARRIVAL"),
		@EventListener(event = "SAVE_DELIVERY_DETAILS"),
		@EventListener(event = "SAVE_BREAKDOWN"),
		@EventListener(event = "SAVE_RAMPTRANSFER_DETAILS"),
		@EventListener(event = "SAVE_CTM_DETAILS"),
		@EventListener(event = "REJECT_CTM_SHIPMENT")
})
public class MailAWBChannel  extends AbstractChannel {

	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.AbstractChannel#send(com.ibsplc.icargo.framework.event.vo.EventVO)
	 *	Added by 			: Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param eventVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException
	 */
	@Override
	public void send(EventVO eventVO) throws SystemException, BusinessException {
		MailFlightSummaryVO mailFlightSummaryVO = (MailFlightSummaryVO) eventVO.getPayload();
		despatchRequest("performMailAWBTransactions", mailFlightSummaryVO); 
		
	}

}
