/*
 * ViewTemporaryCustomerDetailsCommand.java Created on Aprl, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package  com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-2135
 *
 */

public class ViewTemporaryCustomerDetailsCommand  extends BaseCommand {

    private static final String VIEW_SUCCESS = "view_success";
    private static final String MODULE_NAME = "customermanagement.defaults";
    private static final String SCREENID ="customermanagement.defaults.listtempcustomerform";
    private static final String SCREEN_ID = "customermanagement.defaults.maintaintempcustomerform";
    private Log log = LogFactory.getLogger("ViewBillingCommand");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		MaintainTempCustomerForm form = (MaintainTempCustomerForm)invocationContext.screenModel;
		ListtempCustomerSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
		ListtempCustomerSession listSession = getScreenSession(MODULE_NAME,SCREENID);

 		Collection<TempCustomerVO> tempCustomerVOs = listSession.getListCustomerRegistration();
 		ArrayList<String> tempIDs = new ArrayList<String>();

 		String[] checked =form.getRowId();

 		for(TempCustomerVO tempCustomerVO:tempCustomerVOs){
 			String tempID=tempCustomerVO.getTempCustCode();
 			tempIDs.add(tempID);
 		}

 		ArrayList<String> selectedTempIDs = new ArrayList<String>();
        for(int i=0;i<checked.length;i++){

        	String selectedTempID = tempIDs.get(Integer.parseInt(checked[i]));
        	selectedTempIDs.add(selectedTempID);
        }
        session.setTempIDs(selectedTempIDs);
        session.setPageURL("listtempcustomerform");
        String tempID = selectedTempIDs.get(0);
        form.setTempId(tempID);
        form.setDisplayPage("1");
        log.log(Log.INFO, "!!!!!!!!!!!!!!!!TEMPIDVALUE!!!!!!!!!!!!!!!!!!!!!",
				form.getTempId());
		form.setCloseFlag("listtempcustomerform");
        //form.setSaveFlag("save");
        form.setDetailsFlag("detailsflag");
        //form.setCheckFlag("fromdetails");
        invocationContext.target=VIEW_SUCCESS;
    	}

    }

