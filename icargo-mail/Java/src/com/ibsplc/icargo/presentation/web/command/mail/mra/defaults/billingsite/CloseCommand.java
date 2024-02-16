package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.BillingSiteSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteMasterForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class CloseCommand.
 */
public class CloseCommand extends BaseCommand {
  
  /** The log. */
  private Log log = LogFactory.getLogger("Capacity");
  
  /** The Constant PRODUCT_SUBPRODUCT. */
  private static final String PRODUCT_SUBPRODUCT = "mailtracking.mra";
  
  /** The Constant SCREEN_ID. */
  private static final String SCREEN_ID = "mailtracking.mra.defaults.billingsitemaster";

 
  public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
	  log.entering("CloseCommand", "execute");
	  BillingSiteMasterForm billingSiteMasterForm = (BillingSiteMasterForm)invocationContext.screenModel;

	  billingSiteMasterForm.reset();
	  BillingSiteSession session = (BillingSiteSession)getScreenSession(PRODUCT_SUBPRODUCT, SCREEN_ID);
	  
    session.removeAllAttributes();
    
    invocationContext.target = "close_success";
  }
}
