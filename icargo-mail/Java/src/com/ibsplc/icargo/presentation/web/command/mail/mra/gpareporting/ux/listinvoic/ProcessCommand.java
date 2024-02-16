/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicLockVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class ProcessCommand extends BaseCommand {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String TARGET = "process_success";
	private Log log = LogFactory.getLogger("List invoic Screen ");
	private static final String INVOIC_PROCESSING_INITIATED = "Invoic Processing from List Invoic Screen initiated";
	private static final String INVOIC_PROCESSING = "MRAINVPRC";
	private static final String SYS_PARAM_INCPRCLEVEL="mail.mra.gpareporting.invoicprocessinglevel";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		 boolean isLocked = false;
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Page<InvoicVO> invoicVOs = listinvoicsession.getListinvoicvos();
		String selected = listInvoicForm.getSelectedValues();
		InvoicVO invoicVO = invoicVOs.get(Integer.parseInt(selected));
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
	    int processCount = 0;
	    //boolean isInitiatedInvoic = false;
	    try{
	    	processCount = delegate.checkForProcessCount(companyCode,invoicVO);
	    }catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
	    /*try{
	    	isInitiatedInvoic = delegate.isInitiatedInvoic(invoicVO);
	    }catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
	    if(isInitiatedInvoic){
	    	ErrorVO error = null;
		      error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.aninitiatedinvoic");
		      error.setErrorDisplayType(ErrorDisplayType.ERROR);
		      errors.add(error);
		      invocationContext.addAllError(errors);
		      invocationContext.target = TARGET;
		      return;
	    }*/
	    if(processCount >= 1){
	    	  ErrorVO error = null;
	    	  if("I".equals(levelOfInvoicProcessing())){
	    		  error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.processcountexceeded");
	    	  }else{
	    		  error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.fileprocesscountexceeded");
	    	  }
		      error.setErrorDisplayType(ErrorDisplayType.ERROR);
		      errors.add(error);
		      invocationContext.addAllError(errors);
		      invocationContext.target = TARGET;
		      return;
	    }else{
	    	Collection<LockVO> acquiredLocks = new ArrayList<LockVO>();
	        try {
	          acquiredLocks = delegate.generateINVOICProcessingLock(logonAttributes.getCompanyCode());
	          this.log.log(3, " Lock VOs acquiredLocks-=-=->" + acquiredLocks);
	        }
	        catch (Exception exception)
	        {
	          this.log.log(5, new Object[] { " error msg \n\n ", exception.getMessage() });
	          isLocked = true;
	          this.log.log(7, "Already locked");
	          errors = new ArrayList<ErrorVO>();
	          errors.add(new ErrorVO("mailtracking.mra.gpareporting.objectalreadylocked"));
	        }

	        if ((errors != null) && (errors.size() > 0)) {
	          invocationContext.target = "process_success";
	          invocationContext.addAllError(errors);
	          return;
	        }
	        if (!isLocked)
	        {

		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(companyCode);
		invoiceTransactionLogVO.setInvoiceType(INVOIC_PROCESSING);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodFrom( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodTo( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(INVOIC_PROCESSING_INITIATED);
		invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());

		try{
			invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		invoicVO.setTxnCode(invoiceTransactionLogVO.getTransactionCode());
		invoicVO.setTxnSerialNum(invoiceTransactionLogVO.getSerialNumber());
		Collection<LockVO> savelocks = prepareLocksForSave(invoicVO);
		try {
			delegate.processInvoic(savelocks,false,invoicVO);
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		  ErrorVO error = null;
	      error = new ErrorVO("mailtracking.mra.gpareporting.listinvoic.invoicprocessingstarted");
	      error.setErrorDisplayType(ErrorDisplayType.INFO);
	      errors.add(error);
	      invocationContext.addAllError(errors);
	      invocationContext.target = TARGET;
	    }
	    }
	}


	private Collection<LockVO> prepareLocksForSave(InvoicVO invoicVO)
	  {
	    LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

	    Collection <LockVO>locks = new ArrayList<LockVO>();
	    InvoicLockVO lock = new InvoicLockVO();
	    lock.setForceLockEntity(InvoicLockVO.ACTION_INVOICLOCK);
	    lock.setAction(InvoicLockVO.ACTION_INVOICLOCK);
	    lock.setClientType(ClientType.WEB);
	    lock.setCompanyCode(logonAttributes.getCompanyCode());
	    lock.setDescription("GPA INVOIC LOCK ON" + invoicVO.getInvoicRefId());
	    lock.setRemarks("REMARKS " + invoicVO.getInvoicRefId());
	    lock.setScreenId("mail.mra.gpareporting.ux.listinvoic");
	    lock.setStationCode(logonAttributes.getStationCode());

	    locks.add(lock);

	    return locks;
	  }
	
	/**
	 * 
	 * 	Method		:	ProcessCommand.levelOfInvoicProcessing
	 *	Added by 	:	A-5219 on 23-Jun-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	String
	 */
	private String levelOfInvoicProcessing(){
		String level="F";
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_INCPRCLEVEL);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			level="F";
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			if("I".equals(systemParameters.get(SYS_PARAM_INCPRCLEVEL))){
				level="I";
			}
		}
		return level;
	}
}

