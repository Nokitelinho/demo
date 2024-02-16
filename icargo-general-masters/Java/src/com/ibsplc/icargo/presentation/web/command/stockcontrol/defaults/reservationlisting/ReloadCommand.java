/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reservationlisting;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReservationListingSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReservationListingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 *
 */
public class ReloadCommand extends BaseCommand {

	private static final String MODULE_NAME = "stockcontrol.defaults";

	private static final String SCREEN_ID = "stockcontrol.defaults.reservationlisting";

	private static final String SCREENLOAD_SUCCESS= "reload_success";

	private Log log = LogFactory.getLogger("ReloadCommand");

	/**
	 * execute method for handling the reload action
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	ReservationListingSession reservationListingSession
										   = getScreenSession(MODULE_NAME, SCREEN_ID);

		ReservationListingForm form =
						  (ReservationListingForm)invocationContext.screenModel;
				//Collection<ReservationVO> reservationVOs = new ArrayList<ReservationVO>();

	    String[] rowValues = form.getRowId();
        form.setRowId(rowValues);

	    log.log(Log.FINE,"rowidddddssss444");
	    log.log(Log.FINE, "rowidddddssss", rowValues);
		// session.setRowValues(rowValues[0]);
	    form.setAfterReload("Y");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    }
}
