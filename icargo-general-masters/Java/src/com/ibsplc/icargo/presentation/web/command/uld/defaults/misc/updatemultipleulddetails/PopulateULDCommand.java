/*
 * PopulateULDCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.PopulateULDCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class PopulateULDCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	private static final String MODULE_NAME = "uld.defaults";
	private final String SCREENID = "uld.defaults.updatemultipleulddetails";
	private static final String MODULE = "uld.defaults";
	private static final String INVALID_ULD="uld.defaults.invaliduld";
	
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
		
		log.entering("PopulateULDCommand", "INSIDE PopulateULDCommand");
		UpdateMultipleULDDetailsForm form = (UpdateMultipleULDDetailsForm)invocationContext.screenModel;
	    try
	    {
	    	errors = validateULD(form);
	    }
	    
	    catch (BusinessDelegateException businessDelegateException)
	    {
	      handleDelegateException(businessDelegateException);
	    }
	    if(errors != null && errors.size() > 0){
			//awbForm.setPltScreenStatus(ERROR);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}
	   this.log.exiting("EXIT", "PopulateULDCommand");
	    invocationContext.target = "screenload_success";
		
  }
	private Collection<ErrorVO> validateULD(UpdateMultipleULDDetailsForm form)
			    throws BusinessDelegateException {
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 ErrorVO error = null;
		 int index = Integer.parseInt(form.getSelectedRow());
		 ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		 int len = form.getUldNumbers().length;
		 try {
	    	// com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO uldvo=new ULDDelegate().validateULD(logonAttributes.getCompanyCode(), form.getUldNumbers()[index]);
			 ULDValidationVO uldvo = null;
			 if(form.getUldNumbers()[index] != null && !"".equals(form.getUldNumbers()[index])) {
			     uldvo=new ULDDefaultsDelegate().validateULD(logonAttributes.getCompanyCode(), form.getUldNumbers()[index]);
	    	 }else{
	    		return null;
	    	 }if(uldvo!=null){
		    	 form.setNewDamagedStatus(uldvo.getDamageStatus() !=null ? uldvo.getDamageStatus() :"");
		         form.setNewOperationalStatus(uldvo.getOverallStatus() !=null ?uldvo.getOverallStatus() :"");

	    	 }
	    	  else{
	    		  form.setNewDamagedStatus("");
			      form.setNewOperationalStatus("");
	    		  error= new ErrorVO(INVALID_ULD,new Object[] { form.getUldNumbers()[index] });
				  errors.add(error);
				  return errors;	    	 
	    	  }
		 } catch (BusinessDelegateException e) {
				e.getMessage();
				//exceptions = handleDelegateException(e);
			}
		return errors;
		 
	      
	 }
	 

}



