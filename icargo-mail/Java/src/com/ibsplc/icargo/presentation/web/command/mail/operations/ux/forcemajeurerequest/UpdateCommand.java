package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestLockVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;



/**
 * Java file : com.ibsplc.icargo.presentation.web.command.mail.operations.ux.
 * forcemajeurerequest.UpdateCommand.java Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-8527 :
 * 01-Dec-2018 : Draft
 *  */
public class UpdateCommand extends BaseCommand {

private static final String MODULE_NAME = "mail.operations";
private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
private static final String TARGET = "update_success";
private Log log = LogFactory.getLogger("Mail Operations force majeure request");
public static final String ACTION_REQFORCEMAJ = "REQFORMJR";
public static final String ACTION_FORCEMAJ = "FORMJR";
private static final String FORCE_MAJEURE_INITIATED = "Force Majeure request Initiated";

@Override
public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
	log.log(Log.FINE,
			"\n\n in the list command of New tab force Majeure Request----------> \n\n");
	ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
	
	ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(
			MODULE_NAME, SCREEN_ID);
	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	String companyCode = logonAttributes.getCompanyCode();

	ForceMajeureRequestFilterVO forceMajeureRequestfilterVO=new ForceMajeureRequestFilterVO();
	ForceMajeureRequestLockVO locks=null;
	forceMajeureRequestfilterVO.setCompanyCode(companyCode);
	if(forceMajeureRequestForm.getForceid()!=null && forceMajeureRequestForm.getForceid().trim().length()>0){
	forceMajeureRequestfilterVO.setForceMajeureID(forceMajeureRequestForm.getForceid());
	}
	if(forceMajeureRequestForm.getActionFlag()!=null && forceMajeureRequestForm.getActionFlag().trim().length()>0){
		forceMajeureRequestfilterVO.setStatus(forceMajeureRequestForm.getActionFlag());
	}
	if(forceMajeureRequestForm.getReqTabRemarks()!=null && forceMajeureRequestForm.getReqTabRemarks().trim().length()>0){
		forceMajeureRequestfilterVO.setApprRemarks(forceMajeureRequestForm.getReqTabRemarks());
	}else{
		forceMajeureRequestfilterVO.setApprRemarks("");
	}
	MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
	invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
	invoiceTransactionLogVO.setInvoiceType(MailConstantsVO.FORCE_MAJEURE_REQUEST);
	invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setPeriodFrom( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setPeriodTo( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
	invoiceTransactionLogVO.setRemarks(FORCE_MAJEURE_INITIATED);
	invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
	invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
	try{
		invoiceTransactionLogVO = delegate.initTxnForForceMajeure(invoiceTransactionLogVO);
	}catch (BusinessDelegateException businessDelegateException) {
	}
	forceMajeureRequestfilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
	forceMajeureRequestfilterVO.setTxnSerialNumber(invoiceTransactionLogVO.getSerialNumber());
	forceMajeureRequestfilterVO.setLastUpdatedUser(logonAttributes.getUserId());
		try {
			Collection<LockVO> updatelocks = prepareLocksForUpdate(forceMajeureRequestfilterVO, forceMajeureRequestfilterVO.getStatus());
			delegate.updateForceMajeureRequest(forceMajeureRequestfilterVO,updatelocks,false);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		  ErrorVO error = null;
	      Collection saveerrors = new ArrayList();
	      error = new ErrorVO("mailtracking.defaults.forcemajeure.generateinvoiceconfirmation");
	      error.setErrorDisplayType(ErrorDisplayType.INFO);
	      saveerrors.add(error);
	      invocationContext.addAllError(saveerrors);
		
	invocationContext.target = TARGET;
}


	private Collection<LockVO> prepareLocksForUpdate( ForceMajeureRequestFilterVO forceMajeureRequestFilterVO, String actionCode)
	  {
	    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

	    Collection <LockVO>locks = new ArrayList<LockVO>();
	    ForceMajeureRequestLockVO lock = new ForceMajeureRequestLockVO();
	    lock.setForceLockEntity(ACTION_FORCEMAJ);
	    lock.setAction(ACTION_FORCEMAJ);
	    lock.setClientType(ClientType.WEB);
	    lock.setCompanyCode(logonAttributes.getCompanyCode());
	    lock.setDescription("FMR " + forceMajeureRequestFilterVO.getFilterParameters());
	    lock.setRemarks("REMARKS" + forceMajeureRequestFilterVO.getReqRemarks());
	    lock.setScreenId("mail.operations.ux.forcemajeure");
	    lock.setStationCode(logonAttributes.getStationCode());
	    locks.add(lock);
	    return locks;
	}
}


