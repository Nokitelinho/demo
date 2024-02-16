/*
 * DeleteCommand.java Created on Jan 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDeleteVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1617
 *
 */
public class DeleteCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("DeleteCommand");

	private static final String SCREENID = "uld.defaults.listdamagereport";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String DELETE_SUCCESS = "delete_success";

	private static final String DELETE_FAILURE = "delete_failure";

	 /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.log(log.FINE,"DeleteCommand------------------------>");
    	ListDamageReportForm form = (ListDamageReportForm) invocationContext.screenModel;
    	ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
 		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	String[] rowId = form.getRowId();
    	log.log(Log.INFO, "rowId---------------->", rowId);
		int size= rowId.length;
    	log.log(Log.INFO, "size--------------->", size);
		boolean isFlag=true;
    	ArrayList<ULDDamageDeleteVO> uLDDamageDeleteVO = new ArrayList<ULDDamageDeleteVO>();
    		Page<ULDDamageDetailsListVO> pg =session.getULDDamageRepairDetailsVOs();
    		int siz = pg.size();
    		//log.log(log.FINE,"pg getting from session--------------->"+session.getULDDamageRepairDetailsVOs());
    		//ULDDamageVO dmgVo = new ULDDamageVO();

    		//ArrayList<ULDDamageVO> uldDamageVO = new ArrayList<ULDDamageVO>();
    		//ArrayList<ULDDamageVO> uldDamageVOTemp = new ArrayList<ULDDamageVO>();
    		boolean isErrorFound = false;
    		for(int i=0;i<size;i++){
    			String[] tokens = rowId[i].split("-");
    			log.log(Log.INFO, "TOKENS***********", rowId, i);
				for(int j=0;j<siz;j++) {
    	    		ULDDamageDetailsListVO vo = pg.get(j);

    	    		log.log(Log.INFO, "vo--------------", vo);
					log.log(Log.INFO, "vo.getUldNumber()--------------", vo.getUldNumber());
					log.log(Log.INFO, "tokens[0]--------------", tokens);
					if(vo.getUldNumber().equals(tokens[0])){

    	    			//uldDamageVO = (ArrayList<ULDDamageVO>)vo.getUldDamageVOs();
    	    				//for(ULDDamageVO uLDDmgVO:uldDamageVO){


    	    					log.log(Log.INFO, "tokens[1]--------------",
										tokens);
								log.log(Log.INFO,
										"vo.getSequenceNumber()--------------",
										vo.getSequenceNumber());
								if(tokens[1].equals(Long.toString(vo.getSequenceNumber()))){
    	    						log.log(Log.INFO,
											"vo.getRepairDate()-------->", vo.getRepairDate());
									if(vo.getRepairDate()==null ) {
    	    							ULDDamageDeleteVO deleteDup = new ULDDamageDeleteVO();
    	    							deleteDup.setCompanyCode(logonAttributes.getCompanyCode());
    	    			        		deleteDup.setUldNumber(tokens[0]);
    	    				    		deleteDup.setDamageSequenceNumber(Long.parseLong(tokens[1]));
    	    				    		uLDDamageDeleteVO.add(deleteDup);
    	    						}
    	    						else {
    	    							isErrorFound = true;
    	    							error = new ErrorVO("uld.defaults.listulddamage.msg.err.alreadyclosed");
    	    			    			errors.add(error);
    	    			    			}

    	    						isFlag = false;
	    				    		//break;
    	    					}

    	    			//}
    	    			if(!isFlag){
    	    				break;
    	    			}
    	    		}
    			}
    			if(isErrorFound) {
    				break;
    			}
    		}



    		if(errors!=null && errors.size()>0){
				log.log(log.FINE,"DeleteCommand---------if errors is not null-------------------->");
				invocationContext.addAllError(errors);
				invocationContext.target=DELETE_FAILURE;
				return;
			}
    		Collection<ErrorVO> errorsDel = new ArrayList<ErrorVO>();
    		try {
				log.log(log.FINE,"boolean flag is true --------------->");
				log.log(Log.INFO, "before setting to delegate---------->",
						uLDDamageDeleteVO);
				uldDefaultsDelegate.deleteULDDamages(uLDDamageDeleteVO);
			}catch(BusinessDelegateException e)
			{
				e.getMessage();
				errorsDel = handleDelegateException(e);
			}
			log.log(log.FINE,"DeleteCommand---------if errors is  null-------------------->");
			invocationContext.target=DELETE_SUCCESS;
    }
}
/*

























    		int siz = pg.size();
    	 	log.log(log.FINE,"size of page is--------------->"+siz);
    		for(int j=0;j<siz;j++) {
	    		ULDDamageDetailsListVO vo = pg.get(j);
	    		//log.log(log.FINE,"ULDDamageDetailsListVO from pg is--------------->"+vo);
	    		uldDamageVO = (ArrayList<ULDDamageVO>)vo.getUldDamageVOs();
	    		//log.log(log.FINE,"uldDamageVO from pg is--------------->"+vo.getUldDamageVOs());
	    		int subVO = uldDamageVO.size();
	    		log.log(log.FINE,"size of uldDamageVO is--------------->"+uldDamageVO.size());
	    		for(int k=0;k<subVO;k++) {
	    			log.log(log.FINE,"uldDamageVO.get(k)--------------->"+uldDamageVO.get(k));
	    			uldDamageVOTemp.add(uldDamageVO.get(k));
	    		}
	    		log.log(log.FINE,"uldDamageVOTemp--------------->"+uldDamageVOTemp);
    		}
    		for(int x=0;x<size;x++){
    			log.log(log.FINE,"rowId[x]--------------->"+rowId[x]);
        		String[] tokens = rowId[x].split("-");
        		log.log(log.FINE,"uldDamageVOTemp--------------->"+uldDamageVOTemp);
        		for(ULDDamageVO uldDmgVO:uldDamageVOTemp){
        			log.log(log.FINE,"uldDmgVO.getDamageReferenceNumber()--------------->"+uldDmgVO.getDamageReferenceNumber());
        			log.log(log.FINE,"tokens[1]--------------->"+tokens[1]);
        		if(tokens[1].equals(Long.toString(uldDmgVO.getDamageReferenceNumber()))){
        			log.log(log.FINE,"uldDmgVO.getRepairDate()--------------->"+uldDmgVO.getRepairDate());
        		if(uldDmgVO.getRepairDate()==null ) {
        			log.log(log.FINE,"tokens[0]--------------->"+tokens[0]);
        			log.log(log.FINE,"tokens[1]--------------->"+tokens[1]);
        		deleteDup.setCompanyCode(logonAttributes.getCompanyCode());
        		deleteDup.setUldNumber(tokens[0]);
	    		deleteDup.setDamageSequenceNumber(Long.parseLong(tokens[1]));
	    		log.log(log.FINE,"deleteDup--------------->"+deleteDup);
	    		uLDDamageDeleteVO.add(deleteDup);
	    		//log.log(log.FINE,"uLDDamageDeleteVO--------------->"+uLDDamageDeleteVO);
        		}else {
        			log.log(log.FINE,"boolean flag is flase --------------->");
        			flag=false;
        		}
        		if(flag==false){
        			log.log(log.FINE,"boolean flag is flase so error msg --------------->");
        			error = new ErrorVO("uld.defaults.listulddamage.msg.err.alreadyclosed");
	    			errors.add(error);
        		}else {
        			try {
        				log.log(log.FINE,"boolean flag is true --------------->");
    					log.log(log.FINE,"before setting to delegate---------->"+uLDDamageDeleteVO);
    					uldDefaultsDelegate.deleteULDDamages(uLDDamageDeleteVO);
//printStackTrrace()();}
        		}
        		}

        	}



        		if(errors!=null && errors.size()>0){
    				log.log(log.FINE,"DeleteCommand---------if errors is not null-------------------->");
    				invocationContext.addAllError(errors);
    				invocationContext.target=DELETE_FAILURE;
    				return;
    			}
    			if(errors == null || errors.size() == 0) {
    				log.log(log.FINE,"DeleteCommand---------if errors is  null-------------------->");
    				invocationContext.target=DELETE_SUCCESS;
    				return;
    			}
    		}
    }
}


    /*












    		ArrayList<ULDDamageDetailsListVO> vos =new ArrayList<ULDDamageDetailsListVO>();
    		ULDDamageDetailsListVO uLDDamageRepairDetailsVO = new ULDDamageDetailsListVO();
    		ArrayList<ULDDamageVO> uldDamageVO = new ArrayList<ULDDamageVO>();

    		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();

    		for(int i=0;i<rowId.length;i++){

    			ULDDamageDetailsListVO vo = pg.get(Integer.parseInt(rowId[i]));
    			Collection<ULDDamageVO> col = vo.getUldDamageVOs();
    			String uldno = vo.getUldNumber();
    			long seqNo = 1;
    			for(ULDDamageVO dmgVo:col){
    				log.log(log.FINE,"dmgVo.getRepairDate()------->"+dmgVo.getRepairDate());
    				if(dmgVo.getRepairDate()==null) {
    					dmgVo.setOperationFlag(AbstractVO.OPERATION_FLAG_DELETE);
        	    		deleteDup.setCompanyCode(logonAttributes.getCompanyCode());
        	    		deleteDup.setUldNumber(uldno);
        	    		deleteDup.setDamageSequenceNumber(dmgVo.getDamageReferenceNumber());

    				}else {
    					error = new ErrorVO("uld.defaults.listulddamage.msg.err.alreadyclosed");
		    			errors.add(error);
    				}
    				//uldDamageVO.add(dmgVo);
    				uLDDamageDeleteVO.add(deleteDup);
    				try {
    					log.log(log.FINE,"before setting to delegate---------->"+uLDDamageDeleteVO);
    					uldDefaultsDelegate.deleteULDDamages(uLDDamageDeleteVO);
//printStackTrrace()();}
    			}
    			//uLDDamageRepairDetailsVO.setUldDamageVOs(uldDamageVO);

    			if(errors!=null && errors.size()>0){
    				log.log(log.FINE,"DeleteCommand---------if errors is not null-------------------->");
    				invocationContext.addAllError(errors);
    				invocationContext.target=DELETE_FAILURE;
    				return;
    			}
    			if(errors == null || errors.size() == 0) {
    				log.log(log.FINE,"DeleteCommand---------if errors is  null-------------------->");
    				invocationContext.target=DELETE_SUCCESS;
    				return;
    			}


    		}

    	}




}
   */


