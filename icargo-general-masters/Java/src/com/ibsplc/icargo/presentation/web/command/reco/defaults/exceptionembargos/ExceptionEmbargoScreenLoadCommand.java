package com.ibsplc.icargo.presentation.web.command.reco.defaults.exceptionembargos;


import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
public class ExceptionEmbargoScreenLoadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE = "reco.defaults";
	private static final String SCREENID ="reco.defaults.exceptionembargo";

	
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ExceptionEmbargoScreenLoadCommand","Execute");
		ExceptionEmbargoForm exceptionEmbargoForm =(ExceptionEmbargoForm)invocationContext.screenModel;
		LocalDate startDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		exceptionEmbargoForm.setStartDateFilter(startDate.toDisplayDateOnlyFormat());
		exceptionEmbargoForm.setEndDateFilter(endDate.toDisplayDateOnlyFormat());
		ExceptionEmbargoSession exceptionEmbargoSession= getScreenSession( MODULE,SCREENID);
		exceptionEmbargoSession.removeAllAttributes();			
		
		
		exceptionEmbargoForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	   	
		invocationContext.target = "screenload_success";
		log.exiting("ExceptionEmbargoScreenLoadCommand","Execute");
	}
}
