package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

public class RemoveImageCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String REMOVE_SUCCESS = "remove_success";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("RemoveImageCommand", "execute");
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(MODULE, SCREENID);
		String imageIndex = maintainDamageReportForm.getImageIndex();
		String dmgIndex = maintainDamageReportForm.getDmgIndex();
		ArrayList<String> toRemoveList = maintainDamageReportSession.getRemoveDmgImage();
		if(toRemoveList == null){
			toRemoveList = new ArrayList<String>();
		}
		//adding to toRemoveList will be picked up while save
		toRemoveList.add(dmgIndex+"_"+imageIndex);
		maintainDamageReportSession.setRemoveDmgImage(toRemoveList);
		if(imageIndex != null 
				&& imageIndex.trim().length() > 0 
				&& dmgIndex != null 
				&& dmgIndex.trim().length() > 0
				&& maintainDamageReportSession.getDamageImageMap() != null
				&& maintainDamageReportSession.getDamageImageMap().get(dmgIndex) != null){
			HashMap<String, ArrayList<UploadFileModel>> tempMap = maintainDamageReportSession.getDamageImageMap();
			tempMap.get(dmgIndex).remove(Integer.parseInt(imageIndex));
			maintainDamageReportSession.setDamageImageMap(tempMap);
		}
		invocationContext.target = REMOVE_SUCCESS;
		log.exiting("RemoveImageCommand", "execute");
		return;

	}

}
