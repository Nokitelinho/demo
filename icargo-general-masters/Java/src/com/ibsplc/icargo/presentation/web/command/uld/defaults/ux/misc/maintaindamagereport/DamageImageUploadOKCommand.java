package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;
import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DamageImageUploadOKCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Image Upload ");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	
	private static final String FLAG_SUCCESS = "ok_success";
	
	private static final String OK_SUCCESS = "ok_success";
	private static final String OK_FAILURE = "ok_failure";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainDamageReportForm maintainDamageReportForm = 
				(MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(MODULE, SCREENID);
		log.entering("DamageImageUploadOKCommand", "execute method");
		HashMap<String,UploadFileModel> fileUploadMap = new HashMap<String,UploadFileModel>();
		fileUploadMap =(HashMap<String,UploadFileModel>)maintainDamageReportSession.getFromScreenSessionMap("fileUploadMap");
		String imageIndex = maintainDamageReportForm.getImageIndex();
		HashMap<String,ArrayList<UploadFileModel>> damageImageMap = 
				(HashMap<String,ArrayList<UploadFileModel>>)maintainDamageReportSession.getDamageImageMap();
		if(damageImageMap == null){
			damageImageMap = new HashMap<String,ArrayList<UploadFileModel>>();
		}
		Collection<ErrorVO> errors = new ArrayList<>();
		ArrayList<UploadFileModel> temp;
		if(fileUploadMap != null && fileUploadMap.size()>0) {
			Set<String> fileNames = fileUploadMap.keySet();
			for(String key : fileNames){
				UploadFileModel modelFile =  fileUploadMap.get(key);
				if(Objects.nonNull(modelFile) && !modelFile.getFileName().toUpperCase().endsWith(".JPG") && !modelFile.getFileName().toUpperCase().endsWith(".PNG")){
					ErrorVO errorVO = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.filetype");
					errorVO.setErrorDisplayType(ERROR);
					errors.add(errorVO);
				}else {
					if(damageImageMap.get(imageIndex) != null){
						damageImageMap.get(imageIndex).add(fileUploadMap.get(key));
					}else{
						temp = new ArrayList<>();
						temp.add(fileUploadMap.get(key));
						damageImageMap.put(imageIndex,temp);
					}
				}
			}
		// setting toRemove : this is doing as a temp
		// damageIndex_imageIndex in case of adding image then imageIndex will be XX
		ArrayList<String> toadd = maintainDamageReportSession.getRemoveDmgImage();
		if(toadd == null){
			toadd = new ArrayList<String>();
		}
		toadd.add(imageIndex+"_XX");
		maintainDamageReportSession.setRemoveDmgImage(toadd);
		}
		// setting session map
		maintainDamageReportSession.setDamageImageMap(damageImageMap);
//		ArrayList<ULDDamageVO> countUpdatedVos = (ArrayList<ULDDamageVO>) maintainDamageReportSession.getULDDamageVO().getUldDamageVOs();
//		int count = damageImageMap.get(imageIndex).size();
//		countUpdatedVos.get(Integer.parseInt(imageIndex)).setImageCount(Integer.toString(count));
//		maintainDamageReportSession.getULDDamageVO().setUldDamageVOs(countUpdatedVos);
		// clearing session map
		maintainDamageReportSession.setFromScreenSessionMap("fileUploadMap",null);
		log.exiting("DamageImageUploadOKCommand", "execute method");
		if(!errors.isEmpty()){
			log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target = OK_FAILURE;
			return;
		}
		maintainDamageReportForm.setStatusFlag(FLAG_SUCCESS);
		invocationContext.target = OK_SUCCESS;
		return;
	}

}
