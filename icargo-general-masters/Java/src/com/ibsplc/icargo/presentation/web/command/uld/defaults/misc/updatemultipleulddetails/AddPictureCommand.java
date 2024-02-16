/*
 * AddPictureCommand.java Created on Mar 26, 2018
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.updatemultipleulddetails.AddPictureCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8176	:	26-Mar-2018	:	Draft
 */
public class AddPictureCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Update Multiple ULD damage status");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of Update Multiple ULD Damage screen
	 */
	private static final String SCREENID =
		"uld.defaults.updatemultipleulddetails";
	
	
	
	/**
	 * Constants for Status Flag
	 */
	private static final String ACTION_SUCCESS = "action_success";
	/**
	 * The execute method for AddPictureCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command
	 * #execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

	
		UpdateMultipleULDDetailsForm actionForm = 
			(UpdateMultipleULDDetailsForm) invocationContext.screenModel;
		UpdateMultipleULDDetailsSession updateMultipleULDDetailsSession =
				(UpdateMultipleULDDetailsSession)getScreenSession(MODULE,SCREENID);
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ULDDamageVO> uldDamageVos= updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getUldDamageVOs();
		ArrayList<ULDDamageVO> uldDamageVOs = 
				updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getUldDamageVOs() 
	    												!= null ?
				new ArrayList<ULDDamageVO>(updateMultipleULDDetailsSession.
						getSelectedDamageRepairDetailsVO().getUldDamageVOs()) : 
				new ArrayList<ULDDamageVO>();
				LocalDate repDate  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);

		
			
				log.log(Log.FINE, "\n\n seleted uldDamageVOs....", uldDamageVOs);
				String selectedRow=null ;
				selectedRow = actionForm.getSelectedDmgRow();
				int index= Integer.parseInt(selectedRow);
				ArrayList<ULDDamageVO> uldDamageVOlist=new ArrayList<ULDDamageVO>() ;
				HashMap<Integer , ULDDamagePictureVO> uldDamagePicMap = (updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getDamagePicMap() !=null)?updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getDamagePicMap(): new HashMap<Integer,ULDDamagePictureVO>();
				int count=0;
				int addedRow=0;
	            if (selectedRow != null) {
	         	  String[] operationFlags = actionForm.getRatingUldOperationFlag();
		    	  String[] sectionNumbers = actionForm.getSection();
				   
				//This was added to retain the damages in the popup in the session each time after a picture addition is done. The picture and the index is saved in the uldDamagePicMap.
				   for (int i = 0; i < operationFlags.length; i++) {
					      if ((!"NOOP".equalsIgnoreCase(actionForm.getRatingUldOperationFlag()[i])))
					      {
					        ULDDamageVO uldDamageVO = new ULDDamageVO();
					        uldDamageVO.setSection(actionForm.getSection()[count]);
					        uldDamageVO.setDamageDescription(actionForm.getDescription()[count]);
					       if (actionForm.getSeverity()[count] !=null || actionForm.getSeverity()[count].trim().length() >0) {
					        uldDamageVO.setSeverity(actionForm.getSeverity()[count]);
					        }
					        if (actionForm.getReportedDate() !=null || actionForm.getReportedDate()[count].trim().length() >0) {
					        	String reportedDate = actionForm.getReportedDate()[count];	
					        	uldDamageVO.setReportedDate(repDate.setDate(reportedDate));
					        }
					       if (actionForm.getRepStn()[count] !=null || actionForm.getRepStn()[count].trim().length() >0) {
					        uldDamageVO.setReportedStation(actionForm.getRepStn()[count]);
					        }
					        if (actionForm.getLocation()[count]!=null || actionForm.getLocation()[count].trim().length() >0) {
					        uldDamageVO.setLocation(actionForm.getLocation()[count]);
					        }
					        if (actionForm.getFacilityType()[count]!=null || actionForm.getFacilityType()[count].trim().length() >0) {
						    uldDamageVO.setFacilityType(actionForm.getFacilityType()[count]);
					        }
						    if (actionForm.getPartyType()[count] !=null || actionForm.getPartyType()[count].trim().length() >0) {
					        uldDamageVO.setPartyType(actionForm.getPartyType()[count]);
						    }
					        if (actionForm.getParty()[count] !=null || actionForm.getParty()[count].trim().length() >0) {
					        uldDamageVO.setParty(actionForm.getParty()[count]);
					        }
					        if (actionForm.getRemarks()[count] !=null || actionForm.getRemarks()[count].trim().length() >0) {
                            uldDamageVO.setRemarks(actionForm.getRemarks()[count]);
					        }
					        if (actionForm.getTotalPoints()!=null || actionForm.getTotalPoints().trim().length() >0) {
                                uldDamageVO.setDamagePoints(actionForm.getTotalPoints());
						   }
					        uldDamageVO.setOperationFlag(operationFlags[count]);
					        uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
					        uldDamageVO.setSequenceNumber(count);
					    	HashMap<Integer, ULDDamagePictureVO> picMap = new HashMap<Integer, ULDDamagePictureVO>();
					        picMap = updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getDamagePicMap();
					        if(picMap!=null) {
					        for (Map.Entry<Integer,ULDDamagePictureVO> entry : picMap.entrySet())  {
					        	if( entry.getKey().equals(i)){
					        		uldDamageVO.setPictureVO(entry.getValue());
			        				uldDamageVO.setPicturePresent(true);
					        	}
					        }
					        }
					        if(i==index) {
					        	 try{
					        	log
								.log(
										Log.FINE,
										"\n\n maintainDamageReportForm.getDmgPicture()---> ",
										actionForm.getDmgPicture());
			        			ULDDamagePictureVO uldDamagePictureVO=new ULDDamagePictureVO();
			        			FormFile formFile = actionForm.getDmgPicture();
			        			FormFile[] damageArr=(actionForm.getDmgPictureArr() !=null)?actionForm.getDmgPictureArr():new FormFile[5];
			        			damageArr[index]=formFile;
			        		    actionForm.setDmgPictureArr(damageArr);
			        			if(actionForm.getDmgPicture()!=null && formFile.getFileSize()>0){
			        			//log.log(Log.FINE, "The IMAGE in vo is ", uldDamagevo.getPictureVO());
								log.log(Log.FINE, "The IMAGE in form is ",
										actionForm.getDmgPicture());
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
			        				uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
			        				uldDamagePictureVO.setImage(image);
			        				uldDamagePictureVO.setUldNumber(updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().getUldNumber());
			        			    uldDamagePictureVO.setSequenceNumber(uldDamageVO.getSequenceNumber());
			        				uldDamageVO.setPictureVO(uldDamagePictureVO);
			        				uldDamageVO.setPicturePresent(true);
			        			}
			        			
			        			uldDamagePicMap.put(i,uldDamagePictureVO);
			        			}
					        catch (FileNotFoundException e) {
				   				// To be reviewed Auto-generated catch block
				   				e.getMessage();
				   			} catch (IOException e) {
				   				// To be reviewed Auto-generated catch block
				   				e.getMessage();
				   			} 
					        }
					        addedRow++;
					        uldDamageVOlist.add(uldDamageVO);
					       
					      }
					      count++;
					    }
                       
                
               }

               updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO().setUldDamageVOs(uldDamageVOlist);
               ULDDamageRepairDetailsVO ULDDamageRepairDetailsVO =updateMultipleULDDetailsSession.getSelectedDamageRepairDetailsVO();
               ULDDamageRepairDetailsVO.setDamagePoints(actionForm.getTotalPoints());
               ULDDamageRepairDetailsVO.setUldDamageVOs(uldDamageVOlist);
               ULDDamageRepairDetailsVO.setDamagePicMap(uldDamagePicMap);
               updateMultipleULDDetailsSession.setSelectedDamageRepairDetailsVO(ULDDamageRepairDetailsVO);
            
	//	log.log(Log.FINE, "\n\n maintainDamageReportSession....",
			//	updateMultipleULDDetailsSession.getULDDamageVO());
		invocationContext.target = ACTION_SUCCESS;
	}

}
