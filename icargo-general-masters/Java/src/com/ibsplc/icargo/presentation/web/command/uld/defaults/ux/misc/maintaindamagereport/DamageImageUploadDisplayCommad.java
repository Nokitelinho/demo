package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DamageImageUploadDisplayCommad extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report upload popup");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULE, "execute method");
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = getScreenSession(MODULE, SCREENID);
		String imageIndex = maintainDamageReportForm.getImageIndex();
		String dmgIndex = maintainDamageReportForm.getDmgIndex();
		// Existing image
			if(imageIndex != null 
					&& imageIndex.trim().length() >0 
					&& dmgIndex != null 
					&& dmgIndex.trim().length() > 0){
				ArrayList<UploadFileModel> imageList = maintainDamageReportSession.getDamageImageMap().get(dmgIndex);
				if(imageList != null){
					UploadFileModel img = imageList.get(Integer.parseInt(imageIndex));
					ImageModel imageModel = new ImageModel();
					imageModel.setContentType(img.getContentType());
					imageModel.setData(img.getData());
					imageModel.setName(img.getFileName());
					invocationContext.setImage(imageModel);
				}
			}
			// Newly uploaded image
			else{
				HashMap<String,UploadFileModel> fileUploadMap = new HashMap<String,UploadFileModel>();
				fileUploadMap =(HashMap<String,UploadFileModel>)maintainDamageReportSession.getFromScreenSessionMap("fileUploadMap");
				if(fileUploadMap != null){
					Set<String> fileNames = fileUploadMap.keySet();
					for(String key : fileNames){
						
					}
				}
			}
		log.exiting(MODULE, "execute method");
	}
}
