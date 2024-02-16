package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

public class DamageImageUploadScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("DamageImageUploadScreenLoadCommand", "execute");
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(MODULE, SCREENID);
		String imageIndex = maintainDamageReportForm.getImageIndex();
		ULDDamageRepairDetailsVO  uldDamageRepairDetailsVO = maintainDamageReportSession.getULDDamageVO();
		ArrayList<ULDDamageVO> uldDamageVos = (ArrayList<ULDDamageVO>)uldDamageRepairDetailsVO.getUldDamageVOs();
		//clearing the damage list
		maintainDamageReportSession.setDamageImageList(null);
		ULDDamageFilterVO uldDamageFilterVO = null;
		ArrayList<ULDDamagePictureVO> picturesOfDamage = null;
		ArrayList<UploadFileModel> temp = null;
		if(imageIndex != null 
				&& imageIndex.trim().length() >0 
				&& maintainDamageReportSession.getDamageImageMap() != null
				&& maintainDamageReportSession.getDamageImageMap().get(imageIndex) != null){
			ArrayList<UploadFileModel> imageList =  maintainDamageReportSession.getDamageImageMap().get(imageIndex);
			maintainDamageReportSession.setDamageImageList(imageList);
		}else{
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			uldDamageFilterVO = new ULDDamageFilterVO();
			uldDamageFilterVO.setCompanyCode(uldDamageRepairDetailsVO.getCompanyCode());
			uldDamageFilterVO.setUldNumber(uldDamageRepairDetailsVO.getUldNumber());
			if(uldDamageVos != null 
					&& uldDamageVos.size() > 0 
					&& (uldDamageVos.size() > Integer.parseInt(imageIndex))){ 
				ULDDamageVO uLDDamageVO = uldDamageVos.get(Integer.parseInt(imageIndex));
				uldDamageFilterVO.setSequenceNumber((int)uLDDamageVO.getSequenceNumber());
			}
			try {
				if(uldDamageFilterVO.getCompanyCode() != null 
						&& uldDamageFilterVO.getUldNumber() != null 
						&& uldDamageFilterVO.getDamageSequenceNumber()!= 0){
					picturesOfDamage = (ArrayList<ULDDamagePictureVO>)uldDefaultsDelegate.findULDDamagePictures(uldDamageFilterVO);
				}
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "**** Delegate call to fetch Dmg images from maintaindamagereport.DamageImageUploadScreenLoadCommand FAILED ****.");
				e.printStackTrace();
			}
			if(picturesOfDamage != null && picturesOfDamage.size() > 0){
				HashMap<String, ArrayList<UploadFileModel>> damageImageMap = maintainDamageReportSession.getDamageImageMap();
				temp = new ArrayList<UploadFileModel>();
				UploadFileModel upmodel = null;
				ImageModel imgModel = null;
				if(damageImageMap == null){
					damageImageMap = new HashMap<String, ArrayList<UploadFileModel>>();
				}
				for(int i = 0 ; i < picturesOfDamage.size() ; i++){
					imgModel = picturesOfDamage.get(i).getImage();
					upmodel = new UploadFileModel("UPLOAD_FILE_"+i, picturesOfDamage.get(i).getFileName(), imgModel.getData(), imgModel.getContentType(), imgModel.getSize());
					temp.add(upmodel);
				}
				damageImageMap.put(imageIndex, temp);
				maintainDamageReportSession.setDamageImageMap(damageImageMap);
				ArrayList<UploadFileModel> imageList =  maintainDamageReportSession.getDamageImageMap().get(imageIndex);
				maintainDamageReportSession.setDamageImageList(imageList);
			}
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting("DamageImageUploadScreenLoadCommand", "execute");
		return;
	}

}
