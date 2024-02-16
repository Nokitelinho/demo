/*
 * CopyRateCommand.java Created on Dec 15, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.CopyRateSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-1556
 *
 */
/**
 * Method  implementing copy of rate
 * @param invocationContext
 * @throws CommandInvocationException
 */
public class CopyRateCommand extends BaseCommand {

  private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CopyRateCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";
	/*
	 * Copy Rate popup screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyrate";

	private static final String OPEN_COPYRATE = "open_copyrate";
	private static final String RATELINE_STATUS_NEW="N";
	/**
	 * Method  implementing copy of rate
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        log.entering(CLASS_NAME,"execute");

    	ListUPURateLineForm listUPURateLineForm = (ListUPURateLineForm)invocationContext.screenModel;

    	ListUPURateLineSession listUPURateLineSession =
    		                  (ListUPURateLineSession)getScreenSession(MODULE_NAME, SCREENID);
    	CopyRateSession copyRateSession=getScreenSession(MODULE_NAME,SCREEN_ID);
    	Page<RateLineVO> rateLineVOs = listUPURateLineSession.getRateLineVOs();
    	String[] rowId = listUPURateLineForm.getRowId();
    	//log.log(Log.INFO,"row id in changestatus command" +listUPURateLineForm.getRowId());
    	String[] actualRowIds=rowId[0].split(",");
    	String selectedId = null;
    	Collection<RateLineVO> selRateLineVOs=new ArrayList<RateLineVO>();

    	if(rowId != null && rateLineVOs != null){
    		log.log(Log.INFO, "rowid length-->", actualRowIds.length);
			try{
    		for(int i=0; i < actualRowIds.length; i++){
    			selectedId = actualRowIds[i];
    			log.log(Log.INFO, "selected ID in loop", selectedId);
				RateLineVO rateLineVO=new RateLineVO();
    			//selRateLineVOs.add(rateLineVOs.get(Integer.parseInt( selectedId )));
    			//try {
					BeanHelper.copyProperties(rateLineVO,rateLineVOs.get(Integer.parseInt( selectedId )));
				//} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
//printStackTrrace()();
				//} catch (SystemException e) {
					// TODO Auto-generated catch block
//printStackTrrace()();
				//}
				rateLineVO.setOperationFlag(RateLineVO.OPERATION_FLAG_INSERT);
				rateLineVO.setRatelineStatus(RATELINE_STATUS_NEW);
				selRateLineVOs.add(rateLineVO);

    		}
    	}catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			} catch (SystemException e) {
//				// TODO Auto-generated catch block
				e.getMessage();
    			
   		}
    		
    		

    }
    	log.log(Log.INFO, "selected RateLines passing to copy rate popup-->",
				selRateLineVOs);
		copyRateSession.setSelectedRateLines(selRateLineVOs);
    	invocationContext.target=OPEN_COPYRATE;


}
}
