/*
 * AddRepairDetailsCommand.java Created on Feb 06,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc
														.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1862
 *
 */
public class AddRepairDetailsCommand extends BaseCommand {

		private Log log = LogFactory.getLogger("Maintain Damage Report");
		/*
		 * The Module Name
		 */
		private static final String MODULE = "uld.defaults";
		/*
		 * Screen Id of maintain damage report screen
		 */
		private static final String SCREENID =
			"uld.defaults.maintaindamagereport";
		private static final String ULD_DEF_ADD_REP = 
			"uld_def_add_rep";
		/**
		 * Constants for Status Flag
		 */
		private static final String ACTION_SUCCESS = "action_success";
		
		private static final String TOTALPOINTS ="uld.defaults.totalpointstomakeuldunserviceable";
		/**
		 * The execute method for AddRepairDetailsCommand
		 * (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.web.command.Command#
		 * execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
		public void execute(InvocationContext invocationContext)
				throws CommandInvocationException { 
			/**
			 * Obtain the logonAttributes
			 */
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			MaintainDamageReportForm maintainDamageReportForm = 
					(MaintainDamageReportForm) invocationContext.screenModel;
			MaintainDamageReportSession maintainDamageReportSession = 
				(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID);  
			//Added by Chippy for icrd-3116
			CurrencyDelegate currencyDelegate = new CurrencyDelegate();
			CurrencyValidationVO currencyValidationVO  = null;
			/**
			 * Validate for client errors
			 */
			Collection<ErrorVO> errors = null;
			Collection<ErrorVO> errorscollection = null;
			errors = validateForm(maintainDamageReportForm,
										logonAttributes.getCompanyCode());
			try{
	    		currencyValidationVO = currencyDelegate.validateCurrency(logonAttributes.getCompanyCode(),maintainDamageReportForm.getCurrency().toUpperCase());
	    	}
	    	catch(BusinessDelegateException businessDelegateException){
	    		errorscollection = handleDelegateException(businessDelegateException);
			}
	    	
			ArrayList<ULDDamageVO> uldDamageVOs=new ArrayList<ULDDamageVO> 
			(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
			log.log(Log.FINE, "INSIDE ADD REPAIR DETAILS------->>>",
					maintainDamageReportForm.getAllChecked());
			if(!"".equals(maintainDamageReportForm.getAllChecked()) && ("Y").equalsIgnoreCase(maintainDamageReportForm.getAllChecked()))
			{
				log.log(Log.FINE,"INSIDE SETTING ADD REPAIR DETAILS");
				maintainDamageReportForm.setRepairStatus("R");
			}
	/*		if(uldDamageVOs!=null && uldDamageVOs.size()>0	)
	    	{
	    		 boolean present=false;
	    		 long dmgRefNo=0;
	    		
				for(ULDDamageVO uldDamageVO:uldDamageVOs){
					if((uldDamageVO.getOperationFlag()!=null && 
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_DELETE) &&
							!uldDamageVO.getOperationFlag().equals
							(AbstractVO.OPERATION_FLAG_INSERT))|| 
							(uldDamageVO.getOperationFlag()==null)){
						
							if(Long.parseLong(maintainDamageReportForm.
									getDmgRepairRefNo())==
										uldDamageVO.getDamageReferenceNumber())
							{
								LocalDate localDate = new LocalDate(false);
						    	if(maintainDamageReportForm.getRepairDate()!= 
						    		null && 
								maintainDamageReportForm.getRepairDate()
								           .trim().length() > 0 &&
						    	localDate.setDate(maintainDamageReportForm
						    			.getRepairDate(),logonAttributes
						    			.getDateFormat()).before
						    			(uldDamageVO.getReportedDate())){
						    		errors.add(new ErrorVO(
			"uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair"));
						    	}
								present=true;
								dmgRefNo=0;
								break;
							}else
							{
								present=false;
								dmgRefNo=Long.parseLong
								(maintainDamageReportForm.getDmgRepairRefNo());
							}												
						
					}
				if(!present)
				{
					break;	
					
				}
	    		 }
	    		 
	    		log.log(Log.FINE, "\n\n\n\n dmgRefNo"+dmgRefNo);
				log.log(Log.FINE, "\n\n\n\n present"+present);
	    	}*/
			if(errorscollection!=null && errorscollection.size()>0){
				errors.addAll(errorscollection);
			}
			if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = ACTION_SUCCESS;
				return;
			}
			ArrayList<ULDRepairVO> uldRepairVO = 
	       		maintainDamageReportSession.getULDDamageVO().getUldRepairVOs() 
	       		!= null ?
	   			new ArrayList<ULDRepairVO>(maintainDamageReportSession.
	   								getULDDamageVO().getUldRepairVOs()) : 
	   			new ArrayList<ULDRepairVO>();
				log.log(Log.FINE, "\n\n\n\n uldDamageVO ---> ", uldRepairVO);
			if(("uld_def_mod_rep").equals(maintainDamageReportForm.getStatusFlag())){
	    		
	    		ArrayList<ULDRepairVO> modifiedULDRepairVO = 
					new ArrayList<ULDRepairVO>
	    				(maintainDamageReportSession.getULDRepairVOs()) ;
	    		ArrayList<String> modifiedRefNo = 
					new ArrayList<String>
	    				(maintainDamageReportSession.getRefNo()) ;
	    		log.log(Log.FINE,
						"\n\nuldDamageVOs before MODIFY OK Command ---> ",
						modifiedULDRepairVO);
				log.log(Log.FINE,
						"\n\nmodifiedRefNo before MODIFY OK Command ---> ",
						modifiedRefNo);
				if(modifiedULDRepairVO!=null && modifiedULDRepairVO.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt
	   				(maintainDamageReportForm.getRepcurrentPageNum())-1;
	   			
	   			ULDRepairVO uLDRepairVO = modifiedULDRepairVO.get(displayIndex);
	   			String refno = modifiedRefNo.get(displayIndex);
	   			//Collection<ErrorVO> errors = new ArrayList<ErrorVO>(); 
	   			
	   			updateULDRepairVO
	   			(uLDRepairVO, maintainDamageReportForm,logonAttributes);
	   			if(maintainDamageReportForm.getDmgRepairRefNo()!=null && 
	   					maintainDamageReportForm.getDmgRepairRefNo().trim().length()!=0)
				{
	        		refno=maintainDamageReportForm.getDmgRepairRefNo();
	        		log.log(Log.FINE,
							"\n\nactionForm.getDmgRepairRefNo()---> ",
							maintainDamageReportForm.getDmgRepairRefNo());
				} else {
					refno="";
				}
	   			
	   			modifiedRefNo.set(displayIndex,refno);
	   			log.log(Log.FINE, "\n\nrefno ---> ", refno);
				Collection<ErrorVO> errorsmod = null;
	   			ErrorVO error = null;
	   			errorsmod=validateMandatory(uLDRepairVO);
	   			if(refno.trim().length() == 0){
	   				error = new ErrorVO(
	   				"uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
	   				error.setErrorDisplayType(ErrorDisplayType.ERROR);
	   				errorsmod.add(error);
	   				}
	   			
	   			if(uldDamageVOs!=null && uldDamageVOs.size()>0	)
		    	{
		    		 boolean isDatePresent=true;	    		
		    		
			   				
			   				String[] selectedRefNo = refno.split(",");
			   				for(int i=0;i < selectedRefNo.length; i++) {
			   					ULDRepairVO uldRepairVOtmp= new ULDRepairVO();
			   					try{
			   					BeanHelper.copyProperties(uldRepairVOtmp,modifiedULDRepairVO.get(displayIndex));
				   				} catch (SystemException systemException) {
				   					systemException.getMessage();
				   				} 
					for(ULDDamageVO uldDamageVO:uldDamageVOs){
						if((uldDamageVO.getOperationFlag()!=null && 
								!uldDamageVO.getOperationFlag().equals
								(AbstractVO.OPERATION_FLAG_DELETE) &&
								!uldDamageVO.getOperationFlag().equals
								(AbstractVO.OPERATION_FLAG_INSERT))|| 
								(uldDamageVO.getOperationFlag()==null)){
							
								if(selectedRefNo[i].trim().length() != 0 && Long.parseLong(selectedRefNo[i])==
									uldDamageVO.getDamageReferenceNumber())
								{
									
							    	if(uldRepairVOtmp.getRepairDate()!=null && 
							    			uldRepairVOtmp.getRepairDate().isLesserThan
							    			(uldDamageVO.getReportedDate())){
							    		log
												.log(
														Log.FINE,
														"\n\n\n\n uldRepairVOtmp.getRepairDate()",
														uldRepairVOtmp.getRepairDate());
										log
												.log(
														Log.FINE,
														"\n\n\n\n uldDamageVO.getReportedDate()",
														uldDamageVO.getReportedDate());
										errorsmod.add(new ErrorVO(
				"uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair"));
							    		isDatePresent=false;
							    		break;
							    	}
								}												
						}
					
		    		}
					if(!isDatePresent)
					{
						log.log(Log.FINE, "\n\n\n\n present", isDatePresent);
						break;
					}
					}
		    		log.log(Log.FINE, "\n\n\n\n present", isDatePresent);
		    	}
	   			if(errorsmod != null && errorsmod.size() > 0) {
	   				log.log(Log.FINE, "exception");
	   				maintainDamageReportForm.setRepdisplayPage
	   				(maintainDamageReportForm.getRepcurrentPageNum());
	   				invocationContext.addAllError(errorsmod);			
	   				invocationContext.target = ACTION_SUCCESS;
	   				return;
	   			}
	   		
	   			log.log(Log.FINE,
						"\n\nuldDamageVOs after MODIFY OK Command ---> ",
						modifiedULDRepairVO);
				maintainDamageReportSession.setULDRepairVOs
		    									(modifiedULDRepairVO);
		    	maintainDamageReportSession.setRefNo(modifiedRefNo);
				
		    	
	   			int displayPage = Integer.parseInt
	   				(maintainDamageReportForm.getRepdisplayPage())-1;
	   			ULDRepairVO uldRepVO = 
	   				modifiedULDRepairVO.get(displayPage);
	   			String dmgRefNo = 
	   				modifiedRefNo.get(displayPage);
	   			populateRef(uldRepVO,dmgRefNo,maintainDamageReportForm);
	   			maintainDamageReportForm.setRepcurrentPageNum
	   					(maintainDamageReportForm.getRepdisplayPage());
				
	   			}		
	   			int index=0;
			    for(ULDRepairVO modULDRepairVO : modifiedULDRepairVO) {
					for(ULDRepairVO sessionULDRepairVO : uldRepairVO) {
						if(sessionULDRepairVO.getRepairSequenceNumber() ==
									modULDRepairVO.getRepairSequenceNumber()) {
							modULDRepairVO.setDamageReferenceNumber(Long.parseLong(modifiedRefNo.get(index)));
									sessionULDRepairVO = modULDRepairVO;
									
								}
				}index++;
					}
				    	log.log(Log.FINE,
								"\n\nuldDamageVO in SESSION AFTER MODIFY---> ",
								maintainDamageReportSession
								.getULDDamageVO());
				maintainDamageReportSession.setULDRepairVOs(null);
	   		}else
	    	{
	    		MaintainDamageReportForm actionForm = (MaintainDamageReportForm)
				invocationContext.screenModel;
	   			ArrayList<ULDRepairVO> uldRepairVOs =
	   				(ArrayList<ULDRepairVO>)
	   							maintainDamageReportSession.getULDRepairVOs();
	   			ArrayList<String> refNos =
					(ArrayList<String>)
					maintainDamageReportSession.getRefNo();
	   			
	   			log.log(Log.FINE, "\n\nuldDamageVOs before OK Command ---> ",
						uldRepairVOs);
				log.log(Log.FINE, "\n\nrefNos before OK Command ---> ", refNos);
				if(uldRepairVOs!=null && uldRepairVOs.size()!=0){
	   			int displayIndex =
	   				Integer.parseInt(actionForm.getRepcurrentPageNum())-1;
	   			
	   			ULDRepairVO uLDRepairVO = uldRepairVOs.get(displayIndex);
	   			String refno = refNos.get(displayIndex);
	   			   			
	   			updateULDRepair
	   			(uLDRepairVO, actionForm,logonAttributes,refno);
	   			
	   			if(actionForm.getDmgRepairRefNo()!=null && 
	        			actionForm.getDmgRepairRefNo().trim().length()!=0)
				{
	        		refno=actionForm.getDmgRepairRefNo();
	        		log.log(Log.FINE,
							"\n\nactionForm.getDmgRepairRefNo()---> ",
							actionForm.getDmgRepairRefNo());
				} else {
					refno="";
				}
	   			
	   			refNos.set(displayIndex,refno);
	   			log.log(Log.FINE, "\n\nrefno ---> ", refno);
				Collection<ErrorVO> errorsadd= null;
	   			errorsadd=validateMandatory(uLDRepairVO);
	   			ErrorVO error = null;
	   			if(refno.trim().length() == 0){
	   				error = new ErrorVO(
	   				"uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
	   				error.setErrorDisplayType(ErrorDisplayType.ERROR);
	   				errorsadd.add(error);
	   				}
	   			
	   			
	   			if(uldDamageVOs!=null && uldDamageVOs.size()>0	)
		    	{
		    		 boolean isDatePresent=true;
		    		
		    		
			   				
			   				String[] selectedRefNo = refno.split(",");
			   				for(int i=0;i < selectedRefNo.length; i++) {
			   					ULDRepairVO uldRepairVOtmp= new ULDRepairVO();
			   					try{
			   					BeanHelper.copyProperties(uldRepairVOtmp,uldRepairVOs.get(displayIndex));
				   				} catch (SystemException systemException) {
				   					systemException.getMessage();
				   				} 
					for(ULDDamageVO uldDamageVO:uldDamageVOs){
						if((uldDamageVO.getOperationFlag()!=null && 
								!uldDamageVO.getOperationFlag().equals
								(AbstractVO.OPERATION_FLAG_DELETE) &&
								!uldDamageVO.getOperationFlag().equals
								(AbstractVO.OPERATION_FLAG_INSERT))|| 
								(uldDamageVO.getOperationFlag()==null)){
							
								if(selectedRefNo[i].trim().length() != 0 && Long.parseLong(selectedRefNo[i])==
									uldDamageVO.getDamageReferenceNumber())
								{
									
							    	if(uldRepairVOtmp.getRepairDate()!=null && 
							    			uldRepairVOtmp.getRepairDate().isLesserThan
							    			(uldDamageVO.getReportedDate())){
							    		log
												.log(
														Log.FINE,
														"\n\n\n\n uldRepairVOtmp.getRepairDate()",
														uldRepairVOtmp.getRepairDate());
										log
												.log(
														Log.FINE,
														"\n\n\n\n uldDamageVO.getReportedDate()",
														uldDamageVO.getReportedDate());
										errorsadd.add(new ErrorVO(
				"uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair"));
							    		isDatePresent=false;
							    		break;
							    	}
								}												
						}
					
		    		}
					if(!isDatePresent)
					{
						log.log(Log.FINE, "\n\n\n\n present", isDatePresent);
						break;
					}
					}
		    		log.log(Log.FINE, "\n\n\n\n present", isDatePresent);
		    	}
	   			
	   			if(errorsadd != null && errorsadd.size() > 0) {
	   				log.log(Log.FINE, "exception");
	   				actionForm.setRepdisplayPage
	   										(actionForm.getRepcurrentPageNum());
	   				invocationContext.addAllError(errorsadd);			
	   				invocationContext.target = ACTION_SUCCESS;
	   				return;
	   			}
	   		
	   			log.log(Log.FINE, "\n\nuldDamageVOs after OK Command ---> ",
						uldRepairVOs);
				log.log(Log.FINE, "\n\nrefNos after OK Command ---> ", refNos);
				maintainDamageReportSession.setULDRepairVOs(uldRepairVOs);
		    	maintainDamageReportSession.setRefNo(refNos);
				
	   			int displayPage = Integer.parseInt
	   										(actionForm.getRepdisplayPage())-1;
	   			ULDRepairVO uldrepvo = 
	   				uldRepairVOs.get(displayPage);
	   			String refNo = refNos.get(displayPage);
	   			populateRef(uldrepvo,refNo,actionForm);
	   			actionForm.setRepcurrentPageNum(actionForm.getRepdisplayPage());	
	   			
	   			
	   			ArrayList<ULDRepairVO> uldRepairFromSessionVOs=
	   				(ArrayList<ULDRepairVO>)maintainDamageReportSession.
	   										getULDDamageVO().getUldRepairVOs();
	   			log.log(Log.FINE, "\n\nuldDamageVOs from session ---> ",
						uldRepairFromSessionVOs);
				ArrayList<ULDRepairVO> uldRepairVOstmp= new ArrayList<ULDRepairVO>();
	   			int index=0;
	   			for(String refNum:refNos)
	   			{
	   				
	   				String[] selectedRefNo = refNum.split(",");
	   				for(int i=0;i < selectedRefNo.length; i++) {
	   					ULDRepairVO uldRepairVOtmp= new ULDRepairVO();
	   					try{
	   					BeanHelper.copyProperties(uldRepairVOtmp,uldRepairVOs.get(index));
		   				} catch (SystemException systemException) {
		   					systemException.getMessage();
		   				} 
	   					log.log(Log.FINE, "\n\nuldRepairVOtmp ---> ",
								uldRepairVOtmp);
						log.log(Log.FINE, "\n\nselectedRefNo[i] ---> ", Long.parseLong(selectedRefNo[i]));
						double amt=uldRepairVOtmp.getDisplayAmount();
	   					amt=amt/selectedRefNo.length;
	   					
	   					uldRepairVOtmp.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(amt,3)));
	   					uldRepairVOtmp.setDamageReferenceNumber(Long.parseLong(selectedRefNo[i]));
	   					uldRepairVOstmp.add(uldRepairVOtmp);
	   				}
	   				index++;
	   			}			
	   			log
						.log(Log.FINE, "\n\n uldRepairVOstmp ---> ",
								uldRepairVOstmp);
				maintainDamageReportSession.setULDRepairVOs(uldRepairVOstmp);
	   			
	   			
		
	   			
	   			
	   			if(uldRepairFromSessionVOs==null)
	   			{maintainDamageReportSession.getULDDamageVO().setUldRepairVOs
	   				(maintainDamageReportSession.getULDRepairVOs());
	   			}
	   			else
	   			{
	   			uldRepairFromSessionVOs.addAll
	   			(maintainDamageReportSession.getULDRepairVOs());
	   			}
	   			log.log(Log.FINE, "\n\nuldDamageFromSessionVOs ",
						uldRepairFromSessionVOs);
				log.log(Log.FINE,
						"\n\nuldDamageVOs from session after ADD---> ",
						maintainDamageReportSession.getULDDamageVO().
		    											getUldRepairVOs());
	   			}
	   			maintainDamageReportSession.setULDRepairVOs(null);

	   			log
						.log(
								Log.FINE,
								"\n\nuldmaintainDamageReportSession.getULDDamageVO() from session after ADD---> ",
								maintainDamageReportSession.getULDDamageVO());
				if(!"".equals(maintainDamageReportForm.getAllChecked()) && ("Y").equalsIgnoreCase(maintainDamageReportForm.getAllChecked()))
				{
	   				maintainDamageReportSession.getULDDamageVO().setRepairStatus("R");
	   				log.log(Log.FINE ,
			    			"\n\n maintainDamageReportSession.getULDDamageVO().setRepairStatus;");
				}
	   		}
	    	
	    	ArrayList<ULDRepairVO> uldRepairVOAfter = 
	    		maintainDamageReportSession.getULDDamageVO()
	    											.getUldRepairVOs() != null ?
				new ArrayList<ULDRepairVO>(maintainDamageReportSession
										.getULDDamageVO().getUldRepairVOs()) : 
				new ArrayList<ULDRepairVO>();
				boolean isRepPresent=false;
				
					 if(uldRepairVOAfter!=null && uldRepairVOAfter.size()>0)
					 {
						 isRepPresent=true;
						
					 }
					 
				     
				 if(isRepPresent)
				 {
					 maintainDamageReportForm.setScreenStatusValue("REPPRESENT"); 
					 //maintainDamageReportForm.setScreenStatusFlag("REPPRESENT"); 
				 }
				 
				 
	    	if(ULD_DEF_ADD_REP.equals(maintainDamageReportForm.getStatusFlag())) 
	    	{
	    		maintainDamageReportForm.setStatusFlag
	    									("uld_def_add_rep_success");
	    	} else {
	    		maintainDamageReportForm.setStatusFlag
	    									("uld_def_update_rep_success");
	    	}
	    	
	    	String totalpoints=null;
	    	
	    		
	    	Collection<String> parameterTypes = new ArrayList<String>();
			Map<String, String> systemParameterMap = null;
			
			parameterTypes.add(TOTALPOINTS);

	    	SharedDefaultsDelegate sharedDefaultsDelegate =	new SharedDefaultsDelegate();
	    	
	    	try {
				systemParameterMap= sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
				e.getMessage();
			}
			if(systemParameterMap!=null){
				totalpoints=systemParameterMap.get(TOTALPOINTS);
				log
						.log(
								Log.FINE,
								"The Total Value frm SystemParameter in ADDRepairDetailsCommand",
								totalpoints);
			}
//			to mark the corresponding damages as CLOSED
			 int totalDamagePoints=0;
   			ArrayList<ULDDamageVO> uldDamageVO=new ArrayList<ULDDamageVO>
			(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
   			log.log(Log.FINE, "The uldDamageVO details ffrom AddRepairDetails",
					uldDamageVO);
			ArrayList<ULDRepairVO> uldRepairsVO=new ArrayList<ULDRepairVO>
			(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs());
   			for(ULDDamageVO ULDDamageVO:uldDamageVO){					
						ULDDamageVO.setClosed(false);
			}
   			for(ULDRepairVO ULDRepairVO:uldRepairsVO){
   				for(ULDDamageVO ULDDamageVO:uldDamageVO){
   					if(ULDRepairVO.getDamageReferenceNumber()==ULDDamageVO.getDamageReferenceNumber()
   							&& !ULDDamageVO.isClosed()){
   						ULDDamageVO.setClosed(true);
   						ULDDamageVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
   						ULDDamageVO.setLastUpdateUser(logonAttributes.getUserId());
   						
   					}else if(ULDDamageVO.getDamagePoints()!=null ){
   						//log.log(Log.FINE, "From ELSE IN THE command class");
     						 totalDamagePoints=totalDamagePoints+Integer.parseInt(ULDDamageVO.getDamagePoints());
     					 }
   					}
   			}
   			
   			log.log(Log.FINE,
					"The damageChecklistVO after main screen details",
					totalDamagePoints);
			//Modified for ICRD-2329 by A-3767 on 06Jun11
			 if(totalDamagePoints<Integer.parseInt(totalpoints) || totalDamagePoints==0){
				 //Modified by A-3415 for ICRD-113953
				 //User can manually set overall status. prevent modification unless damage points satisfied.
				 // maintainDamageReportForm.setOverStatus("O");
				}
				else{
					maintainDamageReportForm.setOverStatus("N");
				}
				log.log(Log.FINE, "THe Form value of OverStatus is",
						maintainDamageReportForm.getOverStatus());
				if(maintainDamageReportSession.getULDDamageVO()!=null){
				//	 log.log(Log.FINE,"The Total VO before going to manin SCreen from ADdrepairDetailscommand"+maintainDamageReportSession.getULDDamageVO());
					 maintainDamageReportSession.getULDDamageVO().setOverallStatus(maintainDamageReportForm.getOverStatus());
					 log
							.log(
									Log.FINE,
									"The OverStatus frm the sessionfrom ADdrepairDetailscommand",
									maintainDamageReportSession.getULDDamageVO().getOverallStatus());
   			}
   			  			
//			to mark the corresponding damages as CLOSED   
   			
	    	invocationContext.target = ACTION_SUCCESS;
		}
		/**
		 * @param maintainDamageReportForm
		 * @param companyCode 
		 * @return errors
		 */
	    private Collection<ErrorVO> validateForm(MaintainDamageReportForm 
	    						maintainDamageReportForm, String companyCode){
			log.entering("AddRepairDetailsCommand", "validateForm");
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			String dateFormat = logonAttributes.getDateFormat();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			if(maintainDamageReportForm.getRepairStn()== null || 
					maintainDamageReportForm.getRepairStn().trim().length() 
													== 0){
				 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repairstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				if(validateAirportCodes(maintainDamageReportForm.getRepairStn()
						.toUpperCase(),
			    		logonAttributes)!=null){
					errors.add(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
							null));
				}else
					{
					//Modified by A-5169 for ICRD-71942
					LocalDate localDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					String repairDate = maintainDamageReportForm.getRepairDate();
					String repdate =null;
					int hour = localDate.get(LocalDate.HOUR_OF_DAY);
					int minute = localDate.get(LocalDate.MINUTE);
					int seconds = localDate.get(LocalDate.SECOND);
					StringBuilder time = new StringBuilder();
					
					time.append(" ");
					time.append(hour);
					time.append(":");
					time.append(minute);
					time.append(":");
					time.append(seconds);
					String tim = time.toString();
					if(repairDate != null){
				    repdate =repairDate.concat(tim);	
					}
					localDate.setDateAndTime(repdate);  			
					log.log(Log.FINE, "\n\n\n\n currentdatvxcve", currentdate);
					log.log(Log.FINE, "\n\n\n\n repdate", localDate.setDate(maintainDamageReportForm.getRepairDate()));
					if(currentdate.isLesserThan(localDate)){
			    		errors.add(new ErrorVO(
			"uld.defaults.maintainDmgRep.msg.err.repairdategreaterthancurrent"));
			    	}}
				
			}
			if(maintainDamageReportForm.getRepairDate()== null || 
				maintainDamageReportForm.getRepairDate().trim().length() == 0){
				 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repdatemandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(maintainDamageReportForm.getDmgRepairRefNo()== null || 
			maintainDamageReportForm.getDmgRepairRefNo().trim().length() == 0){
				 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(maintainDamageReportForm.getAmount()== null || 
					maintainDamageReportForm.getAmount().trim().length() == 0 ||
				Double.parseDouble(maintainDamageReportForm.getAmount())==0.0 ){
				 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.amountmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				/* Added by A-2412 */
				if(Double.parseDouble(maintainDamageReportForm.getAmount())<0){
					error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.negativeamount");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
			
			log.exiting("AddRepairDetailsCommand", "validateForm");
			return errors;
		}
		/**
		 * Method to return a sequence number
		 * @param uldRepairVOs
		 * @return dmgseq
		 */
		 public long populateSequence(ArrayList<ULDRepairVO> uldRepairVOs) {
	    	log.entering("AddRepairDetailsCommand", "populateSequence");
	    	long dmgseq=0;
	    	for(ULDRepairVO uldRepairVO:uldRepairVOs)
	    	{
	    		if(uldRepairVO.getRepairSequenceNumber()>dmgseq)
	    		{
	    			dmgseq=uldRepairVO.getRepairSequenceNumber();
	    		}
	    	}
	    	dmgseq=dmgseq+1;
	    	log.exiting("AddRepairDetailsCommand", "populateSequence");
	    	return dmgseq;
	    }
		 
			private Collection<ErrorVO> validateMandatory
												(ULDRepairVO uLDRepairVO){
				log.entering("AddRepairDetailsCommand", "validateForm");
				ApplicationSessionImpl applicationSession = 
										getApplicationSession();
				LogonAttributes logonAttributes = 
												applicationSession.getLogonVO();
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				if(uLDRepairVO.getRepairStation()== null || 
						uLDRepairVO.getRepairStation().trim().length() == 0){
					 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
					 error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					
				}else{
					if(validateAirportCodes(uLDRepairVO.getRepairStation()
											.toUpperCase(),
													logonAttributes)!=null){
						errors.add(new ErrorVO(
						"uld.defaults.maintainDmgRep.msg.err.stationinvalid",
								null));
			}}
				if(uLDRepairVO.getRepairDate()== null || 
				String.valueOf(uLDRepairVO.getRepairDate())
												.trim().length() == 0){
						 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.repdatemandatory");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}else
					{
						
						LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				    	if(currentdate.isLesserThan(uLDRepairVO.getRepairDate())){
				    		errors.add(new ErrorVO(
		"uld.defaults.maintainDmgRep.msg.err.repairdategreaterthancurrent"));
				    	}
						
					}
				/*	if(String.valueOf(uLDRepairVO.getDamageReferenceNumber())
														.trim().length() == 0){
						 error = new ErrorVO(
					 "uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}*/
					if(uLDRepairVO.getDisplayAmount()== 0.0 || 
							String.valueOf(uLDRepairVO.getDisplayAmount())
												.trim().length() == 0){
						 error = new ErrorVO(
						 "uld.defaults.maintainDmgRep.msg.err.amountmandatory");
						 error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}

			log.exiting("AddRepairDetailsCommand", "validateForm");
			return errors;
			}
			/**
			 * 
			 * @param uldRepairVO
			 * @param actionForm
			 * @param logonAttributes
			 */
			public void updateULDRepairVO
			(ULDRepairVO uldRepairVO,
					MaintainDamageReportForm actionForm,LogonAttributes 
											logonAttributes) {
			
			log.entering("AddRepairDetailsCommand", "updateULDRepairVO");
			if(uldRepairVO != null) {
				uldRepairVO.setRepairHead
	        	(actionForm.getRepHead());
				
				uldRepairVO.setRepairStation
	        	(actionForm.getRepairStn().toUpperCase());
				//Modified by A-5169 for ICRD-71942
				LocalDate localDate = new LocalDate(actionForm.getRepairStn().toUpperCase(),Location.ARP,true);
				if(actionForm.getRepairDate()!=null &&
								actionForm.getRepairDate().trim().length()!=0)
				{
					String repairDate = actionForm.getRepairDate();
					String repdate =null;
					int hour = localDate.get(LocalDate.HOUR_OF_DAY);
					int minute = localDate.get(LocalDate.MINUTE);
					int seconds = localDate.get(LocalDate.SECOND);
					StringBuilder time = new StringBuilder();
					
					time.append(" ");
					time.append(hour);
					time.append(":");
					time.append(minute);
					time.append(":");
					time.append(seconds);
					String tim = time.toString();
					if(repairDate != null){
					repdate =repairDate.concat(tim);
					}
					localDate.setDateAndTime(repdate);  			
					uldRepairVO.setRepairDate(localDate);
					
	        	//uldRepairVO.setRepairDate(localDate.setDate(actionForm.getRepairDate()));
			    } else {
					uldRepairVO.setRepairDate(null);
				}
				
				if(actionForm.getAmount()!=null && 
									actionForm.getAmount().trim().length()!=0)
				{
	        	uldRepairVO.setDisplayAmount
	        	(Double.parseDouble(actionForm
	        								.getAmount()));
				} else {
					uldRepairVO.setDisplayAmount(0.0);
				}
	        	uldRepairVO.setCurrency
	        			(actionForm.getCurrency());
	        	uldRepairVO.setRemarks
	        			(actionForm.getRepRemarks());
	        	uldRepairVO.setLastUpdateUser
	        	(logonAttributes.getUserId());
			}
			log.exiting("AddRepairDetailsCommand", "updateULDRepairVO");
		}
			/**
			 * 
			 * @param uldRepairVO
			 * @param actionForm
			 * @param logonAttributes
			 * @param refno
			 */
			public void updateULDRepair
			(ULDRepairVO uldRepairVO,
					MaintainDamageReportForm actionForm,LogonAttributes 
											logonAttributes,String refno) {
			
			log.entering("AddRepairDetailsCommand", "updateULDRepairVO");
			if(uldRepairVO != null) {
				uldRepairVO.setRepairHead
	        	(actionForm.getRepHead());
				
				uldRepairVO.setRepairStation
	        	(actionForm.getRepairStn().toUpperCase());
				//Modified by A-5169 for ICRD-71942
				LocalDate localDate = new LocalDate(actionForm.getRepairStn().toUpperCase(),Location.ARP,true);
				if(actionForm.getRepairDate()!=null &&
								actionForm.getRepairDate().trim().length()!=0)
				{
							
					String repairDate = actionForm.getRepairDate();
					String repdate = null;
					int hour = localDate.get(LocalDate.HOUR_OF_DAY);
					int minute = localDate.get(LocalDate.MINUTE);
					int seconds = localDate.get(LocalDate.SECOND);
					StringBuilder time = new StringBuilder();
					
					time.append(" ");
					time.append(hour);
					time.append(":");
					time.append(minute);
					time.append(":");
					time.append(seconds);
					String tim = time.toString();
					if(repairDate!= null){
					repdate =repairDate.concat(tim);
					}
					localDate.setDateAndTime(repdate);  			
					uldRepairVO.setRepairDate(localDate);
	        	//uldRepairVO.setRepairDate(localDate.setDateandT(actionForm.getRepairDate()));
			    } else {
					uldRepairVO.setRepairDate(null);
				}
				
				if(actionForm.getAmount()!=null && 
									actionForm.getAmount().trim().length()!=0)
				{
	        	uldRepairVO.setDisplayAmount
	        	(Double.parseDouble(actionForm
	        								.getAmount()));
				} else {
					uldRepairVO.setDisplayAmount(0.0);
				}
	        	uldRepairVO.setCurrency
	        			(actionForm.getCurrency());
	        	uldRepairVO.setRemarks
	        			(actionForm.getRepRemarks());
	        	uldRepairVO.setLastUpdateUser
	        	(logonAttributes.getUserId());
			}
			log.exiting("AddRepairDetailsCommand", "updateULDRepairVO");
		}
			/**
			 * 
			 * @param uldRepairVO
			 * @param actionForm
			 */
			public void populateRep(
					  ULDRepairVO uldRepairVO,
						MaintainDamageReportForm actionForm) {
		    	log.entering("AddRepairDetailsCommand", "populateRep");

		    	if (uldRepairVO!=null) {
			    	
		    		actionForm.setRepHead(uldRepairVO.getRepairHead());
		    		actionForm.setRepairStn(uldRepairVO.getRepairStation());
		    		
		    		String date = TimeConvertor.toStringFormat
		    			(uldRepairVO.getRepairDate().toCalendar(),
		    				TimeConvertor.CALENDAR_DATE_FORMAT);
		    		actionForm.setRepairDate(date);
		    		
		    		actionForm.setDmgRepairRefNo(String.valueOf
		    						(uldRepairVO.getDamageReferenceNumber()));
		    		actionForm.setAmount(String.valueOf
		    							(uldRepairVO.getDisplayAmount()));
		    		actionForm.setCurrency(uldRepairVO.getCurrency());
		    		actionForm.setRepRemarks(uldRepairVO.getRemarks());
		    		
		    	}
		    	log.exiting("AddRepairDetailsCommand", "populateRep");
		    }
			
			/**
			 * 
			 * @param uLDRepairVO
			 * @param refno
			 * @param actionForm
			 */
			public void populateRef(
					  ULDRepairVO uLDRepairVO,String refno,
						MaintainDamageReportForm actionForm) {
		    	log.entering("SelectNextCommand", "populateRep");

		    	if (uLDRepairVO!=null) {
			    	
		    		actionForm.setRepHead(uLDRepairVO.getRepairHead());
		    		actionForm.setRepairStn(uLDRepairVO.getRepairStation());
		    		if(uLDRepairVO.getRepairDate()!=null){
		    		String date = TimeConvertor.toStringFormat
		    						(uLDRepairVO.getRepairDate().toCalendar(),
		    				TimeConvertor.CALENDAR_DATE_FORMAT);
		    		actionForm.setRepairDate(date);
		    		} else {
						actionForm.setRepairDate("");
					}
		    		actionForm.setDmgRepairRefNo
		    				(refno);
		    		actionForm.setAmount(String.valueOf
		    				                      (uLDRepairVO.getDisplayAmount()));
		    		actionForm.setCurrency(uLDRepairVO.getCurrency());
		    		actionForm.setRepRemarks(uLDRepairVO.getRemarks());
		    		
		    	}
		    	log.exiting("SelectNextCommand", "ULDRepairVO");
		    }
			
			
			/**
		     * @param station
		     * @param logonAttributes
		     * @return errors
		     */
		   public Collection<ErrorVO> validateAirportCodes(
		    		String station,
		    		LogonAttributes logonAttributes){
		    	log.entering("Command", "validateAirportCodes");
		    	log.log(Log.FINE, " Station ---> ", station);
				Collection<ErrorVO> errors = null;
		    	
		    	try {
		    		AreaDelegate delegate = new AreaDelegate();
					delegate.validateAirportCode(
							logonAttributes.getCompanyCode(),station);			

		    	} catch (BusinessDelegateException e) {
					e.getMessage();
					log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
					errors = handleDelegateException(e);
				}
				log.exiting("Command", "validateAirportCodes");
		    	return errors;
		    }
	    
}
