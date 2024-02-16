/*
 * AddPictureCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
														maintaindamagereport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */
public class AddPictureCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report ");
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
	 * Constants for Status Flag
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/**
	 * The execute method for DeleteDamageDetailsCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command
	 * #execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

	
		MaintainDamageReportForm maintainDamageReportForm = 
			(MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = 
			(MaintainDamageReportSession)getScreenSession(MODULE,SCREENID);
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
// 		for populating details from main screen into the session-START
	    ULDDamageRepairDetailsVO uldDamageRepairDetailsVO =
    		maintainDamageReportSession.getULDDamageVO() != null ?
			maintainDamageReportSession.getULDDamageVO() :
			new ULDDamageRepairDetailsVO();
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

		}
		maintainDamageReportForm.setScreenReloadStatus("reload");
//		 for populating details from main screen into the session-END
		
		 	ArrayList<ULDDamageVO> uldDamageVO = 
    		maintainDamageReportSession.getULDDamageVO().getUldDamageVOs() 
    												!= null ?
			new ArrayList<ULDDamageVO>(maintainDamageReportSession.
										getULDDamageVO().getUldDamageVOs()) : 
			new ArrayList<ULDDamageVO>();
			
			log.log(Log.FINE, "\n\n seleted uldDamageVOs....", uldDamageVO);
			String[] selectedRows=null ;
			selectedRows = maintainDamageReportForm.getSelectedDmgRowId();
            int index = 0;
            if (selectedRows != null) {
               int size = selectedRows.length;
                       
               try{
                   for (ULDDamageVO uldDamagevo :uldDamageVO) {
                       for (int i = 0; i < size; i++) {
                if (index == Integer.parseInt(selectedRows[i])) {
                	log.log(Log.FINE, "\n\n seleted uldDamagevo....",
							uldDamagevo);
					log
							.log(
									Log.FINE,
									"\n\n maintainDamageReportForm.getDmgPicture()---> ",
									maintainDamageReportForm.getDmgPicture());
					//        			start upload picture
        			ULDDamagePictureVO uldDamagePictureVO=new ULDDamagePictureVO();
        			FormFile formFile = maintainDamageReportForm.getDmgPicture();
        			if(maintainDamageReportForm.getDmgPicture()!=null && formFile.getFileSize()>0){
        			log.log(Log.FINE, "The IMAGE in vo is ", uldDamagevo.getPictureVO());
					log.log(Log.FINE, "The IMAGE in form is ",
							maintainDamageReportForm.getDmgPicture());
						byte[] picDetails = null;
        				ImageModel image = null;

        				
        				if(formFile.getFileSize()>0){
        					log
									.log(
											Log.FINE,
											"\n\n****************formFile.getFileSize()",
											formFile.getFileSize());
							log
									.log(
											Log.FINE,
											"\n\n****************formFile.getFileName()",
											formFile.getFileName());
							log
									.log(
											Log.FINE,
											"\n\n***************formFile.getContentType()",
											formFile.getContentType());
							log
									.log(
											Log.FINE,
											"\n\n****************formFile.getFileData() ",
											formFile.getFileData());
							image = new ImageModel();
        					image.setName(formFile.getFileName());
        					image.setContentType(formFile.getContentType());
        					image.setData(formFile.getFileData());
        					image.setSize(formFile.getFileSize());
        					//image.setData(formFile.getInputStream());
        					
        				}


        				uldDamagePictureVO.setCompanyCode(logonAttributes.getCompanyCode());				
        				if(!uldDamagevo.isPicturePresent() && uldDamagevo.getPictureVO()==null && uldDamagevo.getOperationFlag()==null){
        					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
        				}
        				if((AbstractVO.OPERATION_FLAG_INSERT).equals(uldDamagevo.getOperationFlag())){
        					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
        				}
        				if(uldDamagevo.isPicturePresent() && uldDamagevo.getOperationFlag()==null){
        					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
        				}
        				if(uldDamagevo.isPicturePresent() && (AbstractVO.OPERATION_FLAG_UPDATE).equals(uldDamagevo.getOperationFlag())){
        					uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
        				}
        				uldDamagePictureVO.setImage(image);
        				uldDamagePictureVO.setSequenceNumber(uldDamagevo.getSequenceNumber());
        				uldDamagevo.setPictureVO(uldDamagePictureVO);
        				uldDamagevo.setPicturePresent(true);
        				log.log(Log.FINE, "The IMAGE in vo is ", uldDamagevo.getPictureVO());
						if(uldDamagevo.getOperationFlag()==null)
                    	{
        					uldDamagevo.setOperationFlag
                    		(AbstractVO.OPERATION_FLAG_UPDATE);        					
                    	}
        				uldDamagevo.setLastUpdateUser(logonAttributes.getUserId());

        			

        			}   		 
        		  
        	   }
        	   }index++;
               }
               }
   			catch (FileNotFoundException e) {
   				// To be reviewed Auto-generated catch block
   				e.getMessage();
   			} catch (IOException e) {
   				// To be reviewed Auto-generated catch block
   				e.getMessage();
   			}    
               }
               log.log(Log.FINE, "\n\n uldDamageVO....", uldDamageVO);
		log.log(Log.FINE, "\n\n maintainDamageReportSession....",
				maintainDamageReportSession.getULDDamageVO());
		invocationContext.target = ACTION_SUCCESS;
	}

}
