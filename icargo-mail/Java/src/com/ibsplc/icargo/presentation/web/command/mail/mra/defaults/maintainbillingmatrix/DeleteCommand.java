/*
 * DeleteCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2398
 *
 */
public class DeleteCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String DELETE_SUCCESS = "delete_success";
	
	private static final String OPFLAG_DEL = "D";
	
	private static final String OPFLAG_INS = "I"; 

	/**
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");    	
    	
    	BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Page<BillingLineVO> page = session.getBillingLineDetails(); 
		ArrayList<BillingLineVO> billingLineVOs=new ArrayList<BillingLineVO>();
		ArrayList<BillingLineVO> unsavedVosToDelete=new ArrayList<BillingLineVO>();
		BillingLineVO voDeleted = null;
		for(BillingLineVO vo : page ){
			billingLineVOs.add(vo);
			log.log(Log.FINE, "Vo *****===> ", vo);
		}
		log.log(Log.INFO, "Selected index string===>", form.getSelectedIndex());
		int numberOfRecordsToBeDeleted = 0;
		if(billingLineVOs.size() > 0){
			log
					.log(Log.INFO, "Billing line vos size==>", billingLineVOs.size());
			if(form.getSelectedIndex().length() > 0){
			String [] strIndexes = form.getSelectedIndex().split("-");
			for(String index : strIndexes){
				log.log(Log.INFO, "Selected Index is..", index);
				int selectedIndex = Integer.parseInt(index);
				log.log(Log.INFO, "Selected Index is..", selectedIndex);
				if(OPFLAG_INS.equals(billingLineVOs.get(selectedIndex).
								getOperationFlag())){
					BillingLineVO vo =billingLineVOs.get(selectedIndex);
					//billingLineVOs.remove(vo);
					unsavedVosToDelete.add(vo);
					numberOfRecordsToBeDeleted++;
				}
				else{
					billingLineVOs.get(selectedIndex).
						setOperationFlag(OPFLAG_DEL);
				voDeleted = billingLineVOs.get(selectedIndex);
				numberOfRecordsToBeDeleted++;
				}
				if(voDeleted != null){
					log.log(Log.INFO, "The vo deleted from session is--->",
							voDeleted);
				}
			}
			if(unsavedVosToDelete.size() > 0){
				billingLineVOs.removeAll(unsavedVosToDelete);
				log.log(Log.INFO, "Unsaved vos being deleted  ==>",
						unsavedVosToDelete);
			}
			billingLineVOs.trimToSize();
			Page<BillingLineVO> newPages=
				new Page<BillingLineVO>(
						billingLineVOs, page.getPageNumber(), page
								.getDefaultPageSize(), page.getActualPageSize()-numberOfRecordsToBeDeleted,
						page.getStartIndex(), page.getEndIndex(), page.getAbsoluteIndex(), page.hasNextPage());

		session.setBillingLineDetails(newPages);
		log.log(1,"New page set in session....");
		}
		} else {
			log.log(1,"No page set in session....");
		}
		
		
		invocationContext.target = DELETE_SUCCESS;
		log.exiting(CLASS_NAME,"execute"); 
    }

    }
