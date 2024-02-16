/*
 * SaveCommand.java created on APR 23,2009
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.fuelsurcharge;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.FuelSurchargeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.FuelSurchargeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class SaveCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "SaveCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.fuelsurcharge";

	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String SAVE_CONFIRM="mailtracking.mra.defaults.inf.datasaved";
	
	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		FuelSurchargeSession session=getScreenSession(MODULE_NAME,SCREENID);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		FuelSurchargeVO surchargeVO=new FuelSurchargeVO();
		Collection<FuelSurchargeVO>  surchargeVOs=new ArrayList<FuelSurchargeVO>();
		FuelSurchargeForm form = (FuelSurchargeForm) invocationContext.screenModel;
		String frmDate[]=form.getValidFrom();
		String toDate[]=form.getValidTo();
		String ratecharge[]=form.getRateCharge();
		String opFlag[]=form.getOperationFlag();
		
	
	
		if (ratecharge!=null && ratecharge.length>0){
			int siz=ratecharge.length;
			LocalDate fromDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
	    	LocalDate  toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			for(int i=0;i<siz;i++){
				log.log(Log.INFO, "opflag ", opFlag, i);
				if(!("NOOP").equals(opFlag[i])){
					 toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
					fromDat=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			    fromDat.setDate(frmDate[i]);
			    toDat.setDate(toDate[i]);
				 if(fromDat.isGreaterThan(toDat) ){
					ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.fuelsurcharge.msg.err.fromdategreaterthantodate");
		 		   	errors.add(errorVO);
		 		   	invocationContext.addAllError(errors);					
					invocationContext.target = SAVE_FAILURE;
					return;
		          
				 }
				 surchargeVO=new FuelSurchargeVO();
				 surchargeVO.setCompanyCode(logonAttributes.getCompanyCode());
				 surchargeVO.setGpaCode(form.getGpaCode());
				 surchargeVO.setGpaName(form.getGpaName());
				 surchargeVO.setCountry(form.getCountry());
				 surchargeVO.setRateCharge(form.getRateCharge()[i]);
				 surchargeVO.setValues(form.getValues()[i]);
				 surchargeVO.setCurrency(form.getCurrency()[i]);
				 surchargeVO.setValidityStartDate(fromDat);
				 surchargeVO.setValidityEndDate(toDat);
				   if(("I").equals(opFlag[i])){
					   surchargeVO.setOperationFlag("I");
                  	   }
                  	   if((("N").equals(opFlag[i]))){
                  		surchargeVO.setSeqNum(Integer.parseInt(form.getSeqNum()[i]));
                  		surchargeVO.setOperationFlag("U");
                  	
                  	   }
                  	 if((("D").equals(opFlag[i]))){
                  		  surchargeVO.setSeqNum(Integer.parseInt(form.getSeqNum()[i]));
                  		  surchargeVO.setOperationFlag("D");
                   	   }
                  	surchargeVOs.add(surchargeVO);
    				log.log(Log.INFO, "vo to save ", surchargeVO);
				}
				
			}
		 
			
			
		}
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		try {
			log.log(Log.INFO, "vos to save ", surchargeVOs);
			delegate.saveFuelSurchargeDetails(surchargeVOs);
		} catch (BusinessDelegateException e) {
			//errors=handleDelegateException(e);
			ErrorVO error = new ErrorVO("mailtracking.mra.defaults.fuelsurcharge.err.msg.fuelsurchargeexist");
			log.log(Log.FINE, "Errors are returned............");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target=SAVE_FAILURE;
			return;
		}
		session.removeFuelSurchargeVOs();
		form.setGpaCode("");
		form.setGpaName("");
		form.setCountry("");
	
		ErrorVO err=new ErrorVO(SAVE_CONFIRM);
		err.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(err);
		invocationContext.addAllError(errors);
		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=SAVE_SUCCESS;
		
	}
}
