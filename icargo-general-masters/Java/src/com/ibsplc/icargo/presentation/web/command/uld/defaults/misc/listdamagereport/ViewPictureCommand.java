/*
 * ViewPictureCommand.java Created on Jul 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ViewPicture
 * 
 * @author A-1862
 */

public class ViewPictureCommand extends BaseCommand {
    
	/**
	 * Logger for List Damage
	 */
	private Log log = LogFactory.getLogger("List Damage");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";	
	
	private static final String SCREENID_DAMAGE =
		"uld.defaults.maintaindamagereport";
	private static final String SCREENID = "uld.defaults.listdamagereport";
	
	private static final String VIEWPIC_SUCCESS = "viewpic_success";
 
    /**
     * execute method
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
		String  compCode = logonAttributes.getCompanyCode();
		
		MaintainDamageReportSession maintainDamageReportSession = 
			(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID_DAMAGE);
		ListDamageReportSession session = getScreenSession(MODULE, SCREENID);
		MaintainDamageReportForm form = 
			(MaintainDamageReportForm) invocationContext.screenModel;  	
    	
		

           
        String uldNo = form.getSelectedULDNum();
        
        log.log(Log.FINE, "ULDNo", uldNo);
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();   
		ULDDamagePictureVO uldDamagePictureVO=null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
         for(ULDDamageDetailsListVO uldDamageVO:session.getULDDamageRepairDetailsVOs()){
			
			if(uldDamageVO.getSequenceNumber()==Long.parseLong(form.getSeqNum()) ){
		try {
			uldDamagePictureVO=uldDefaultsDelegate.findULDDamagePicture
			(compCode,uldNo.toUpperCase(),Long.parseLong(form.getSeqNum()));
		}
		catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			exception = handleDelegateException(businessDelegateException);
		}
			}}
		
		maintainDamageReportSession.setULDDamagePictureVO(uldDamagePictureVO);
		maintainDamageReportSession.setForPic("FROMLISTDAMAGE");
		
		invocationContext.target = VIEWPIC_SUCCESS;
        
    }

    
}
