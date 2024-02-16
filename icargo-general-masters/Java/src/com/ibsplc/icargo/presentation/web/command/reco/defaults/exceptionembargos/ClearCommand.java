package com.ibsplc.icargo.presentation.web.command.reco.defaults.exceptionembargos;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.exceptionembargos.ExceptionEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ExceptionEmbargoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6843
 *
 */
public class ClearCommand extends BaseCommand{

	
	/** Logger for Exception AWB Embargos Log. */
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.exceptionembargo";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";
	private static final String BLANK="";

	/**
	 * execute method.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
 		log.entering("ClearCommand","execute"); 
 		ExceptionEmbargoForm exceptionEmbargoForm = (ExceptionEmbargoForm) invocationContext.screenModel;
 		ExceptionEmbargoSession session = (ExceptionEmbargoSession) getScreenSession(MODULE, SCREENID);
 		exceptionEmbargoForm.setShipmentPrefixFilter(BLANK);
 		exceptionEmbargoForm.setMasterDocumentNumberFilter(BLANK);
 		exceptionEmbargoForm.setStartDateFilter(BLANK);	
 		exceptionEmbargoForm.setEndDateFilter(BLANK);
    	session.removeExceptionEmbargos();
		invocationContext.target = SUCCESS;
		log.exiting("ClearCommand","execute");
	}
}
