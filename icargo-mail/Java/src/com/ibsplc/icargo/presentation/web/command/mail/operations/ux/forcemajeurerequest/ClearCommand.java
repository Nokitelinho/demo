/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
	private static final String SUCCESS = "clear_success";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the service Standard clear command----------> \n\n");
		ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
		ForceMajeureRequestSession forceMajeureRequestSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		//String cleartype=
		if(forceMajeureRequestForm.getActionFlag()!=null && forceMajeureRequestForm.getActionFlag().trim().length()>0){
		if("CLEARNEW".equals(forceMajeureRequestForm.getActionFlag()))	{
		forceMajeureRequestForm.setOrigin_airport("");
		forceMajeureRequestForm.setDestination("");
		forceMajeureRequestForm.setViaPoint("");
		forceMajeureRequestForm.setAffectedAirport("");
		forceMajeureRequestForm.setPacode("");
		forceMajeureRequestForm.setFlightNumber("");//added by A-8353 as part of ICRD-293367
		forceMajeureRequestForm.setFlightDateStr("");
		forceMajeureRequestForm.setFrmDate("");
		forceMajeureRequestForm.setToDate("");
		forceMajeureRequestForm.setSource("C");
		forceMajeureRequestForm.setScanType("ALL");        
		forceMajeureRequestForm.setCarrierCode("");
		forceMajeureRequestForm.setFrmTime("");
		forceMajeureRequestForm.setToTime("");       
		forceMajeureRequestSession.setListforcemajeurevos(null);
		}
		if("CLEARREQ".equals(forceMajeureRequestForm.getActionFlag())){
		forceMajeureRequestForm.setForceid("");
		forceMajeureRequestForm.setReqTabRemarks("");
		forceMajeureRequestSession.setReqforcemajeurevos(null);
		}
		forceMajeureRequestForm.setNewTabRemarks("");
		forceMajeureRequestForm.setTotalRecords("0");
		forceMajeureRequestForm.setDefaultPageSize("100");
		forceMajeureRequestForm.setDisplayPage("1");
		forceMajeureRequestForm.setSortingField("");
		forceMajeureRequestForm.setSortOrder("");
		forceMajeureRequestSession.setFilterParamValues(null);
		forceMajeureRequestSession.setTotalRecords(0);
		invocationContext.target = SUCCESS;
	}

	}
}
