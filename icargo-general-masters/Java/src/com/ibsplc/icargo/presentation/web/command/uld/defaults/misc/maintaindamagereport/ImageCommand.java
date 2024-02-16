/*
 * ImageCommand.java Created on Jul 12,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
														maintaindamagereport;


import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ImageCommand
 * @author a-1862
 *
 */
public class ImageCommand extends BaseCommand{
	
	/**
	 * Logger for Maintain DamageReport
	 */
	private Log log = LogFactory.getLogger("Maintain DamageReport");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintaindamagereport";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		MaintainDamageReportSession session =
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);		
		
		ImageModel image=null;		
		log.log(Log.FINE,
				"IMAGE COMMAND ****session.getULDDamagePictureVO() ---> ",
				session.getULDDamagePictureVO());
		if(session.getULDDamagePictureVO()!=null){
				ULDDamagePictureVO uldDamagePictureVO=session.getULDDamagePictureVO();
				log.log(Log.FINE,
						"\n\n\n\n*****image in image command----------",
						uldDamagePictureVO.getImage());
				image=uldDamagePictureVO.getImage();
		}
		log.log(Log.FINE, "IMAGE COMMAND ****image ---> ", image);
		invocationContext.setImage( image ); 
		
		
			 
	}
	}
