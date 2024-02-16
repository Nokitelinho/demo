/*
 * CloseULDStockSetUpCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.stock.maintainuldstock;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MonitorULDStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainULDStockForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class CloseULDStockSetUpCommand  extends BaseCommand {

	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_FAILURE = "close_failure";
	private static final String CLOSE_MONITORSTOCK = "close_monitorstock";
    private static final String MODULE = "uld.defaults";
	private static final String SCREENID ="uld.defaults.maintainuldstock";
	private static final String MODULE_MONITORSTOCK = "uld.defaults";

	private static final String SCREENID_MONITORSTOCK =
		"uld.defaults.monitoruldstock";
	private Log log = LogFactory.getLogger("CloseULDStockSetUpCommand");
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(log.FINE,"CloseULDStockSetUpCommand------------------>");
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
		MaintainULDStockForm actionForm = 
			(MaintainULDStockForm) invocationContext.screenModel;
		ListULDStockSetUpSession listULDStockSession = getScreenSession(MODULE, SCREENID);
		MonitorULDStockSession monitorULDStockSession = 
    		(MonitorULDStockSession)getScreenSession(MODULE_MONITORSTOCK,SCREENID_MONITORSTOCK);
		
		if(!("canclose").equals(actionForm.getCloseStatus())) {
			if(listULDStockSession.getULDStockDetails() != null &&
					listULDStockSession.getULDStockDetails().size() > 0) {
						
				
				Collection<ULDStockConfigVO> uldstockvos = new ArrayList<ULDStockConfigVO>();
				uldstockvos = (Collection<ULDStockConfigVO>)listULDStockSession.getULDStockDetails();
				log.log(Log.INFO, "uldstockvos-------->", uldstockvos);
				log.log(Log.INFO,
						"listULDStockSession.getULDStockDetails()-------->",
						listULDStockSession.getULDStockDetails());
				if(uldstockvos!=null){
			    	for(ULDStockConfigVO vo : uldstockvos) {
			    		log.log(Log.INFO, "vo.getOperationFlag()--------->", vo.getOperationFlag());
						if((ULDStockConfigVO.OPERATION_FLAG_UPDATE.equals(vo.getOperationFlag()))||
			  					(ULDStockConfigVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag()))||
			  							(ULDStockConfigVO.OPERATION_FLAG_DELETE.equals(vo.getOperationFlag()))) {
			  				ErrorVO error  = new ErrorVO("uld.defaults.uldstocksetup.msg.err.unsaveddataexistsclose");
			  				error.setErrorDisplayType(ErrorDisplayType.WARNING);
			      			errors.add(error);
			  			}
			  		}
				}
		    	if(errors != null && errors.size() > 0) {
					log.log(Log.FINE, "exception--------------------->");	
					actionForm.setCloseStatus("whethertoclose");
					invocationContext.addAllError(errors);			
					invocationContext.target = CLOSE_FAILURE;
					return;
				}else {
					listULDStockSession.removeULDStockConfigVOs();
					listULDStockSession.removeULDStockDetails();
					if("monitorStock".equals(actionForm.getScreenloadstatus())) {
						monitorULDStockSession.setListStatus("fromAnotherScreen");
						invocationContext.target = CLOSE_MONITORSTOCK;
					}
					else {
						 invocationContext.target=CLOSE_SUCCESS;
					}
					
					log.log(Log.FINE, " no exception--------------------->");		
				}
			}else {
				listULDStockSession.removeULDStockConfigVOs();
				listULDStockSession.removeULDStockDetails();
				if("monitorStock".equals(actionForm.getScreenloadstatus())) {
					monitorULDStockSession.setListStatus("fromAnotherScreen");
					invocationContext.target = CLOSE_MONITORSTOCK;
				}
				else {
					 invocationContext.target=CLOSE_SUCCESS;
				}
				
				log.log(Log.FINE, " no exception--------------------->");		
			}
		}else {
			listULDStockSession.removeULDStockConfigVOs();
			listULDStockSession.removeULDStockDetails();
			if("monitorStock".equals(actionForm.getScreenloadstatus())) {
				monitorULDStockSession.setListStatus("fromAnotherScreen");
				invocationContext.target = CLOSE_MONITORSTOCK;
			}
			else {
				 invocationContext.target=CLOSE_SUCCESS;
			}
			
			log.log(Log.FINE, " no exception--------------------->");		
			
		}  
    }
}
