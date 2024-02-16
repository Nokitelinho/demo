package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ViewPictureCommand extends BaseCommand{
    
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("Maintain Damage Report");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.ux.maintaindamagereport";
	
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
			(MaintainDamageReportSession)getScreenSession(MODULE,
					SCREENID);		
		ArrayList<ULDDamageVO> uldDamageVOs = 
    		maintainDamageReportSession.getULDDamageVO()
    											.getUldDamageVOs() != null ?
			new ArrayList<ULDDamageVO>(maintainDamageReportSession
									.getULDDamageVO().getUldDamageVOs()) : 
			new ArrayList<ULDDamageVO>();
			
		MaintainDamageReportForm maintainDamageReportForm = 
			(MaintainDamageReportForm) invocationContext.screenModel;
		maintainDamageReportForm.setPicturePresent("false");
		
		
		
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO=maintainDamageReportSession.getULDDamageVO();
		if (uldDamageRepairDetailsVO != null ){
			
		uldDamageRepairDetailsVO.setDamageStatus
						(maintainDamageReportForm.getDamageStatus());
		uldDamageRepairDetailsVO.setOverallStatus
						(maintainDamageReportForm.getOverallStatus());
		uldDamageRepairDetailsVO.setRepairStatus
						(maintainDamageReportForm.getRepairStatus());
		uldDamageRepairDetailsVO.setSupervisor
							(maintainDamageReportForm.getSupervisor());
		uldDamageRepairDetailsVO.setInvestigationReport
							(maintainDamageReportForm.getInvRep());

				
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();   
		ULDDamagePictureVO uldDamagePicturevo=null;
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "\n\n\n\n uldDamageVOs in view pic command---> ",
				uldDamageVOs);
		for(ULDDamageVO uldDamageVO:uldDamageVOs){
			
			if(uldDamageVO.getSequenceNumber()==Long.parseLong(maintainDamageReportForm.getSeqNum()) ){
				if(uldDamageVO.getOperationFlag()==null || 
						(("U").equals(uldDamageVO.getOperationFlag()) && uldDamageVO.getPictureVO()==null	&& uldDamageVO.isPicturePresent())){
			try {
				uldDamagePicturevo=uldDefaultsDelegate.findULDDamagePicture
			(compCode,uldDamageRepairDetailsVO.getUldNumber().toUpperCase(),Long.parseLong(maintainDamageReportForm.getSeqNum()));
			}
			catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			log.log(Log.FINE, "\n\n\n\n uldDamagePicturevo1---> ",
					uldDamagePicturevo);
			}else
			if((("I").equals(uldDamageVO.getOperationFlag()) ||
					("U").equals(uldDamageVO.getOperationFlag())) &&
					uldDamageVO.getPictureVO()!=null){
				uldDamagePicturevo=uldDamageVO.getPictureVO();
				log.log(Log.FINE, "\n\n\n\n uldDamagePicturevo2---> ",
						uldDamagePicturevo);
			}
				
				if(uldDamagePicturevo!=null && uldDamagePicturevo.getImage()!=null){
					maintainDamageReportForm.setPicturePresent("true");
				}
				else
				{
					maintainDamageReportForm.setPicturePresent("notpresent");
				}
				//uldDamageRepairDetailsVO.setUldDamagePictureVO(uldDamagePicturevo);
				maintainDamageReportSession.setULDDamagePictureVO(uldDamagePicturevo);
				maintainDamageReportSession.setForPic(null);
			
		}
		
		}
		invocationContext.target = VIEWPIC_SUCCESS;
        
    }

    
}}
