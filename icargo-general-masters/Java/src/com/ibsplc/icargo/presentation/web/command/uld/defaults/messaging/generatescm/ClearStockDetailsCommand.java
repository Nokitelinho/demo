package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearStockDetailsCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS= "clear_success";

	private static final String BLANK = "";
	
	private static final String MSGMODULE_NAME = "msgbroker.message";
	
	private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	
	/**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {		
    	ListMessageSession msgsession = 
			getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
    	msgsession.removeAllAttributes();
		GenerateSCMForm generateSCMForm = 
			(GenerateSCMForm) invocationContext.screenModel;
		generateSCMForm.setPageURL("");
		generateSCMForm.setUldNumberStock(BLANK);
		generateSCMForm.setListedDynamicQuery(BLANK);
		generateSCMForm.setDynamicQuery(BLANK);
		generateSCMForm.setUldStatus("S,M,F");
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		invocationContext.addAllError(error);
		invocationContext.target =CLEAR_SUCCESS;
    }
}
