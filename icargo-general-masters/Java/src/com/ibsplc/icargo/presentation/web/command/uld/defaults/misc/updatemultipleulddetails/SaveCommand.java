/*
 * SaveCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.SaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class SaveCommand extends BaseCommand{
	
	
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";

	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.updatemultipleulddetails";
	private static final String DUPLICATE_ULDS_EXIST ="uld.defaults.updateMultipleULDDetails.msg.err.duplicateUldNumber";
	private static final String BLANK ="";

	@Override


	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		
		log.entering("SaveCommand", "updatemultipleulddetails");
		UpdateMultipleULDDetailsForm updateMultipleULDDetailsForm =
				(UpdateMultipleULDDetailsForm) invocationContext.screenModel;
		updateMultipleULDDetailsForm.setStatusFlag("");
		UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession =
					(UpdateMultipleULDDetailsSession)getScreenSession(MODULE,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVOs = new ArrayList<ULDDamageRepairDetailsVO>();
		
		ArrayList<String> uldNumbersForValidation = new ArrayList<String>();
		uldDamageRepairDetailsVOs=(ArrayList<ULDDamageRepairDetailsVO>) updateMultipleULDDetailsSession.getULDDamageRepairDetailsVOs();
		if (uldDamageRepairDetailsVOs != null && uldDamageRepairDetailsVOs.size() > 0) {
			for (ULDDamageRepairDetailsVO uldRepairDetailsVO : uldDamageRepairDetailsVOs) {
				if(!(BLANK.equalsIgnoreCase(uldRepairDetailsVO.getUldNumber()))){
					uldNumbersForValidation.add(uldRepairDetailsVO.getUldNumber());
				}
			}
		}
		errors=validateULDforDuplicates(uldNumbersForValidation);
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			log.log(Log.INFO, "reconcileVO after dup error---->>",uldDamageRepairDetailsVOs);
			return;
			}
		try {
			new ULDDefaultsDelegate().updateMultipleULDDamageDetails(uldDamageRepairDetailsVOs);
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "inside errors--------------------->$$$$$$$$$$$$$$$$$$$$$");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
			return;
			}
			invocationContext.addError(new ErrorVO("uld.defaults.updatemultipleuld.msg.info.savesuccess"));	
			invocationContext.target = SAVE_SUCCESS;
		    updateMultipleULDDetailsSession.setULDDamageRepairDetailsVOs(null);
		    updateMultipleULDDetailsForm.setStatusFlag("action_mainsave");
		    updateMultipleULDDetailsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			log.exiting("SaveCommand", "updatemultipleulddetails");
			
			
		
	}


	/**
	 * 	Method		:	SaveCommand.validateULDforDuplicates
	 *	Added by 	:	A-7359 on 11-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param uldNumbersForValidation
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
		*/
	private Collection<ErrorVO> validateULDforDuplicates(
			ArrayList<String> uldNumbersForValidation) {
		
		ArrayList<String> duplicateULDs=new ArrayList<String>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		int len=uldNumbersForValidation.size();
		boolean dupUldFlag=false;
		for(int i=0 ;i<len ;i++){
			String firstULD=uldNumbersForValidation.get(i);
			for(int j=i+1 ;j < len ;j++){
				String secondULD=uldNumbersForValidation.get(j);
				if(firstULD==secondULD || firstULD.equals(secondULD)){
					log.log(Log.FINE, "firstULD and secondULD are equal");
					dupUldFlag=true;
					if(!duplicateULDs.contains(firstULD)){
						duplicateULDs.add(firstULD);
					}
				}
			}
		}
		if (dupUldFlag) {
			int size = duplicateULDs.size();
			for (int i = 0; i < size; i++) {
				ErrorVO error = new ErrorVO(DUPLICATE_ULDS_EXIST,
						new Object[] { duplicateULDs.get(i) });
				errors.add(error);
			}
		}
		return errors;
	}
}
