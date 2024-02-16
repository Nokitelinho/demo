package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

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
		"uld.defaults.ux.maintaindamagereport";
	
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
