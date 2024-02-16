/*
 * ViewPictureCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.ViewPictureCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	13-Mar-2018	:	Draft
 */
public class ViewPictureCommand extends BaseCommand {
    
	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.updatemultipleulddetails";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String  compCode = logonAttributes.getCompanyCode();
		UpdateMultipleULDDetailsForm actionForm = 
				(UpdateMultipleULDDetailsForm) invocationContext.screenModel;
			UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession =
					(UpdateMultipleULDDetailsSession)getScreenSession(MODULE,SCREENID);
			/*
			 * Obtain the logonAttributes
			 */
		ULDDamageRepairDetailsVO ULDDamageRepairDetailsVO =updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO();
		ArrayList<ULDDamageVO> uldDamageVOs = 
				updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO()
    											.getUldDamageVOs() != null ?
			new ArrayList<ULDDamageVO>(updateMultipleULDDetailsSession
									.getSelectedDamageRepairDetailsVO().getUldDamageVOs()) : 
			new ArrayList<ULDDamageVO>();
			
		
		
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();   
		ULDDamagePictureVO uldDamagePicturevo=null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "\n\n\n\n uldDamageVOs in view pic command---> ",
				uldDamageVOs);
		for(ULDDamageVO uldDamageVO:uldDamageVOs){
			
			if(uldDamageVO.getSequenceNumber()==Long.parseLong(actionForm.getSeqNum()) ){
				if(uldDamageVO.getOperationFlag()==null || 
						(("U").equals(uldDamageVO.getOperationFlag()) && uldDamageVO.getPictureVO()==null	&& uldDamageVO.isPicturePresent())){
			try {
				uldDamagePicturevo=uldDefaultsDelegate.findULDDamagePicture
			(compCode,ULDDamageRepairDetailsVO.getUldNumber().toUpperCase(),Long.parseLong(actionForm.getSeqNum()));
			}
			catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "\n\n\n\n uldDamagePicturevo1---> ",
					uldDamagePicturevo);
			}else
			if((("I").equals(uldDamageVO.getOperationFlag()) ||
					("U").equals(uldDamageVO.getOperationFlag())) &&
					uldDamageVO.getPictureVO()!=null){
				uldDamagePicturevo=uldDamageVO.getPictureVO();
				log.log(Log.FINE, "\n\n\n\n uldDamagePicturevo2---> ",
						uldDamagePicturevo);
			}
				
				
				updateMultipleULDDetailsSession.setULDDamagePictureVO(uldDamagePicturevo);
	
		}
		
		}
		invocationContext.target = "viewpic_success";
        
    }
}


