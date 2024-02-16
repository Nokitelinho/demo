/*
 * ImageCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.ImageCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class ImageCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("Update Multiple ULD damage screen");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of Update Multiple ULD Damage screen
	 */
	private static final String SCREENID =
		"uld.defaults.updatemultipleulddetails";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession = getScreenSession(MODULE, SCREENID);
		ImageModel image=null;	
		log.log(Log.FINE,
				"IMAGE COMMAND ****session.getULDDamagePictureVO() ---> ",
				updateMultipleULDDetailsSession.getULDDamagePictureVO());
		if(updateMultipleULDDetailsSession.getULDDamagePictureVO()!=null){
				ULDDamagePictureVO uldDamagePictureVO=updateMultipleULDDetailsSession.getULDDamagePictureVO();
				log.log(Log.FINE,
						"\n\n\n\n*****image in image command----------",
						uldDamagePictureVO.getImage());
				image=uldDamagePictureVO.getImage();
		}
		log.log(Log.FINE, "IMAGE COMMAND ****image ---> ", image);
		invocationContext.setImage( image );
	}

}
