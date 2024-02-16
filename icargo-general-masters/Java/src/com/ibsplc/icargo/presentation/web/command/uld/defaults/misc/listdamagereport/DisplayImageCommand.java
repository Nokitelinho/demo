package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listdamagereport;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DisplayImageCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ListDamageReportCommand");
	private static final String SCREENID = "uld.defaults.listdamagereport";
	private static final String MODULE_NAME = "uld.defaults";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(MODULE_NAME, "execute method");
		ListDamageReportForm form = (ListDamageReportForm) invocationContext.screenModel; 
    	ListDamageReportSession session = getScreenSession(MODULE_NAME, SCREENID);
    	ApplicationSessionImpl applicationSessionImpl=getApplicationSession(); 
    	LogonAttributes logonAttributes=applicationSessionImpl.getLogonVO();
    	ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
    	ULDDamageFilterVO uldDamageFilterVO = null;
    	ArrayList<ULDDamagePictureVO> picturesOfDamage = null;
    	ImageModel imageModel = null; 
    	// from form
    	String uldNumber = form.getUldNo();
    	String dmgRefNumber = form.getDamageRefNo();
    	String seqNum = form.getSeqNum();
    	HashMap<String, ArrayList<UploadFileModel>> damageImgMap = session.getDamageImageMap();
    	if(damageImgMap == null){
    		damageImgMap = new HashMap<String, ArrayList<UploadFileModel>>();
    	}
    	// key
    	String key = new StringBuilder()
    				.append(uldNumber)
    				.append("_")
    				.append(dmgRefNumber)
    				.toString();
    	if(damageImgMap.get(key) != null){
    		UploadFileModel img = damageImgMap.get(key).get(Integer.parseInt(seqNum));
			imageModel = new ImageModel();
			imageModel.setContentType(img.getContentType());
			imageModel.setData(img.getData());
			imageModel.setName(img.getFileName());
			invocationContext.setImage(imageModel);
			return;
    	}else{
    		// server call to fetch images
    		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
    		uldDamageFilterVO = new ULDDamageFilterVO();
    		uldDamageFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    		uldDamageFilterVO.setUldNumber(uldNumber);
    		uldDamageFilterVO.setSequenceNumber(Integer.parseInt(dmgRefNumber));
    		
    		try {
				picturesOfDamage = (ArrayList<ULDDamagePictureVO>)uldDefaultsDelegate.findULDDamagePictures(uldDamageFilterVO);
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "**** Delegate call to fetch Dmg images from listdamagereport.DisplayImageCommand FAILED ****.");
				e.printStackTrace();
			}
    	}
    	if(picturesOfDamage != null && picturesOfDamage.size() > 0){
    		imageModel = picturesOfDamage.get(Integer.parseInt(seqNum)).getImage();
    		invocationContext.setImage(imageModel);
    		// populating session
    		ImageModel imgModel = null;
    		UploadFileModel upmodel = null;
    		ArrayList<UploadFileModel> temp = new ArrayList<UploadFileModel>();
    		for(int i = 0 ; i < picturesOfDamage.size() ; i++){
				imgModel = picturesOfDamage.get(i).getImage();
				upmodel = new UploadFileModel("UPLOAD_FILE_"+i, imgModel.getName(), imgModel.getData(), imgModel.getContentType(), imgModel.getSize());
				temp.add(upmodel);
			}
			damageImgMap.put(key, temp);
			session.setDamageImageMap(damageImgMap);
    	}
    	log.exiting(MODULE_NAME, "execute method");

	}

}
