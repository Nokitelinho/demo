package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DisplayImageCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	private static final String MAINTAIN_DAMAGE_REPORT_REPAIRNOTFOUND_ERROR = "uld.defaults.maintainDmgRep.msg.err.repairdetailsnotpresent";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String DISPLAY_SUCCESS = "display_success";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULE, "execute method");
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = getScreenSession(MODULE, SCREENID);
		
		String imageIndex = maintainDamageReportForm.getImageIndex();
		String dmgIndex = maintainDamageReportForm.getDmgIndex();
		
		ULDDamageFilterVO uldDamageFilterVO = null;
		ArrayList<ULDDamagePictureVO> picturesOfDamage = null;
		
		ULDDamageRepairDetailsVO  uldDamageRepairDetailsVO = maintainDamageReportSession.getULDDamageVO();
		ArrayList<ULDDamageVO> uldDamageVos = (ArrayList<ULDDamageVO>)uldDamageRepairDetailsVO.getUldDamageVOs();
		ArrayList<Long> dmgRefNums = maintainDamageReportSession.getDmgRefNo();
		
		ImageModel imageModel = null; 
		ArrayList<UploadFileModel> imageList = null;
		if(maintainDamageReportSession.getDamageImageMap() != null){
			imageList = maintainDamageReportSession.getDamageImageMap().get(dmgIndex);
		}
		if(imageList != null && imageList.size() >0){
			UploadFileModel img = imageList.get(Integer.parseInt(imageIndex));
				imageModel = new ImageModel();
				imageModel.setContentType(img.getContentType());
				imageModel.setData(img.getData());
				imageModel.setName(img.getFileName());
				invocationContext.setImage(imageModel);
		}
		if(imageList == null || imageList.size() == 0){
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			uldDamageFilterVO = new ULDDamageFilterVO();
			uldDamageFilterVO.setCompanyCode(uldDamageRepairDetailsVO.getCompanyCode());
			uldDamageFilterVO.setUldNumber(uldDamageRepairDetailsVO.getUldNumber());
			if(uldDamageVos != null && uldDamageVos.size() > 0){
				ULDDamageVO uLDDamageVO = uldDamageVos.get(Integer.parseInt(dmgIndex));
				uldDamageFilterVO.setSequenceNumber((int)uLDDamageVO.getSequenceNumber());
			}
			try {
				picturesOfDamage = (ArrayList<ULDDamagePictureVO>)uldDefaultsDelegate.findULDDamagePictures(uldDamageFilterVO);
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "**** Delegate call to fetch Dmg images from maintaindamagereport.DisplayImageCommand FAILED ****.");
				e.printStackTrace();
			}
		}
		if(picturesOfDamage != null && picturesOfDamage.size() > 0){
			ULDDamagePictureVO vo = picturesOfDamage.get(Integer.parseInt(imageIndex));
			invocationContext.setImage(vo.getImage());
			// populating session
			HashMap<String, ArrayList<UploadFileModel>> damageImageMap = maintainDamageReportSession.getDamageImageMap();
			ArrayList<UploadFileModel> temp = new ArrayList<UploadFileModel>();
			UploadFileModel upmodel = null;
			ImageModel imgModel = null;
			for(int i = 0 ; i < picturesOfDamage.size() ; i++){
				imgModel = picturesOfDamage.get(i).getImage();
				upmodel = new UploadFileModel("UPLOAD_FILE_"+i, picturesOfDamage.get(i).getFileName(), imgModel.getData(), imgModel.getContentType(), imgModel.getSize());
				temp.add(upmodel);
			}
			if(damageImageMap == null){
				damageImageMap = new HashMap<String, ArrayList<UploadFileModel>>();
			}
			damageImageMap.put(dmgIndex, temp);
			maintainDamageReportSession.setDamageImageMap(damageImageMap);
		}
		//invocationContext.target = DISPLAY_SUCCESS;
		log.exiting(MODULE, "execute method");
	}

}
