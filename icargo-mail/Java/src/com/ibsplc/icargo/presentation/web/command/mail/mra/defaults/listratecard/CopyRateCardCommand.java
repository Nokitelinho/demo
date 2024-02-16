/*
 * CopyRateCardCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 */
public class CopyRateCardCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CopyRateCardCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

//	private static final String SCREENID_COPY = "mailtracking.mra.defaults.xxx";

	private static final String COPY_SUCCESS = "copy_success";


	/**
	 * Method to implement the copying of rate card
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateCardForm listUPURateCardForm
    	= (ListUPURateCardForm)invocationContext.screenModel;

    	ListUPURateCardSession listUPURateCardSession
    	= (ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);



    	Page<RateCardVO> rateCardVOs = listUPURateCardSession.getRateCardVOs();
    	RateCardVO rateCardVO = null;
    	String[] rowId = listUPURateCardForm.getRowCount();
    	String selectedId = null;

    	if(rowId != null && rateCardVOs != null){

    		for(int i=0; i < rowId.length; i++){
    			selectedId = rowId[i];
    		}
    		rateCardVO = rateCardVOs.get((Integer.parseInt(selectedId)));  
    		log.log(Log.INFO, "selected RateCardVO-->", rateCardVO);
			//rateCardVO.setRateCardID(BLANK);
    		// Added by A-4809 for CR ICMN-2221 --->Starts
    		listUPURateCardSession.setSelectedRateCardVO(rateCardVO); 
    		listUPURateCardForm.setMailDistFactor(Double.toString(rateCardVO.getMailDistanceFactor()));
    		listUPURateCardForm.setSvTkm(Double.toString(rateCardVO.getCategoryTonKMRefOne()));
    		listUPURateCardForm.setSalTkm(Double.toString(rateCardVO.getCategoryTonKMRefTwo()));
    		listUPURateCardForm.setAirmialTkm(Double.toString(rateCardVO.getCategoryTonKMRefThree()));
    		// Added by A-4809 for CR ICMN-2221 --->Ends
    		invocationContext.target = COPY_SUCCESS;
    		return;
    	}
    }
}
