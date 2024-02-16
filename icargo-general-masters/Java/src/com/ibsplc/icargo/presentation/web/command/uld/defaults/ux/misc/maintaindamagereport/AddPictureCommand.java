package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AddPictureCommand extends BaseCommand{


	private Log log = LogFactory.getLogger("Maintain Damage Report ");
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
		populateUldDamageRepairDetailsVO(maintainDamageReportForm,maintainDamageReportSession,logonAttributes);
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
			String selectedDamageRowId = maintainDamageReportForm.getSelectedDamageRowId();
            int index = 0;
//            if (selectedRows != null && selectedDamageRowId != null && !selectedDamageRowId.isEmpty()) {
//               int size = selectedRows.length;
                       
               try{
                   for (ULDDamageVO uldDamagevo :uldDamageVO) {
//                       for (int i = 0; i < uldDamageVO.size(); i++) {
                if (index == Integer.parseInt(selectedDamageRowId)) {
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
        		  
//        	   }
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
//               }
               log.log(Log.FINE, "\n\n uldDamageVO....", uldDamageVO);
		log.log(Log.FINE, "\n\n maintainDamageReportSession....",
				maintainDamageReportSession.getULDDamageVO());
		invocationContext.target = ACTION_SUCCESS;
	}

private void populateUldDamageRepairDetailsVO(MaintainDamageReportForm form,MaintainDamageReportSession session,LogonAttributes logonAttributes){
		session.setULDDamageChecklistVO(null);
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = session.getULDDamageVO();
		String[] section = form.getSection();
		String[] damageDesc = form.getDescription();
		String[] severity = form.getSeverity();
		String[] reportedAirport = form.getRepStn();
		String[] reportedDate = form.getReportedDate();
		String[] facilityType = form.getFacilityType();
		String[] location = form.getLocation();
		String[] partyType = form.getPartyType();
		String[] party = form.getParty();
		String[] remarks = form.getRemarks();
		String[] opFlag = form.getTempOperationFlag();
		
		ArrayList<ULDDamageChecklistVO>damageChecklistVOs;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		/**
		 * 
		 * Added for Screen stabilization
		 */
		Map<Integer,ULDDamagePictureVO> pictureVOs = new HashMap<Integer,ULDDamagePictureVO>();
		int index=0;
		if(uldDamageRepairDetailsVO!=null && uldDamageRepairDetailsVO.getUldDamageVOs()!=null){
			for(ULDDamageVO uldDamageVO :uldDamageRepairDetailsVO.getUldDamageVOs()){
				if(uldDamageVO.getPictureVO()!=null){
					pictureVOs.put(index, uldDamageVO.getPictureVO());
				}
				index++;
			}
		}
		
		
		uldDamageRepairDetailsVO.setDamageStatus(form.getDamageStatus());
		uldDamageRepairDetailsVO.setOverallStatus(form.getOverallStatus());
		uldDamageRepairDetailsVO.setRepairStatus(form.getRepairStatus());
		uldDamageRepairDetailsVO.setSupervisor(form.getSupervisor());
		uldDamageRepairDetailsVO.setInvestigationReport(form.getInvRep());
		
		
		ULDDamageVO uldDamageVO = null;
		ULDRepairVO uldRepairVO = null;
		ArrayList<ULDDamageVO> uLDDamageVOsFromSession = (ArrayList)uldDamageRepairDetailsVO.getUldDamageVOs();
		ArrayList<ULDRepairVO> uldRepairVOsFromSession = (ArrayList)uldDamageRepairDetailsVO.getUldRepairVOs();
		
		ArrayList<ULDDamageVO> uLDDamageVOsToSession = new ArrayList<ULDDamageVO>();
		ArrayList<ULDRepairVO> uldRepairVOsToSession = new ArrayList<ULDRepairVO>();
		
		if(uLDDamageVOsFromSession != null && uLDDamageVOsFromSession.size() > 0){
			for(ULDDamageVO vo : uLDDamageVOsFromSession){
				if(!ULDDamageVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
					uLDDamageVOsToSession.add(vo);
				}
			}
		}
		if(uldRepairVOsFromSession != null && uldRepairVOsFromSession.size() > 0){
			for(ULDRepairVO vo : uldRepairVOsFromSession){
				if(!ULDDamageVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
					uldRepairVOsToSession.add(vo);
				}
			}
		}

			uldRepairVOsFromSession = uldRepairVOsToSession;
		
	
			uLDDamageVOsFromSession = uLDDamageVOsToSession;
		
		
		if (opFlag != null && opFlag.length > 0){
			for (int i = 0; i < opFlag.length-1; i++) {
				damageChecklistVOs=new ArrayList <ULDDamageChecklistVO>();	
				uldDamageVO=null;
				if(ULDDamageVO.OPERATION_FLAG_INSERT.equals(opFlag[i])) {
					
					uldDamageVO = new ULDDamageVO();
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_INSERT);
					if(pictureVOs.containsKey(i)){
						uldDamageVO.setPictureVO(pictureVOs.get(i));
						uldDamageVO.setPicturePresent(true);
					}
					uLDDamageVOsFromSession.add(uldDamageVO);
				}else 
					if(ULDDamageVO.OPERATION_FLAG_UPDATE.equals(opFlag[i]) || "N".equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_UPDATE);
				}else if(ULDDamageVO.OPERATION_FLAG_DELETE.equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_DELETE);
				}
				if (uldDamageVO!=null && (ULDDamageVO.OPERATION_FLAG_INSERT.equals(uldDamageVO.getOperationFlag())
						|| ULDDamageVO.OPERATION_FLAG_UPDATE.equals(uldDamageVO.getOperationFlag()))
						|| "N".equals(opFlag[i])) {
					
					uldDamageVO.setSection(section[i]);
					if(section[i]!=null && section[i].trim().length()>0){
						try {	        	
							damageChecklistVOs = (ArrayList<ULDDamageChecklistVO>)delegate.listULDDamageChecklistMaster(logonAttributes.getCompanyCode(),section[i]);
							log.log(Log.FINE,"damageChecklistVOs getting from delegate---------#######>",damageChecklistVOs);        	
						} catch (BusinessDelegateException e) {
							e.getMessage();
							
						} 
						if(damageChecklistVOs!=null && damageChecklistVOs.size()>0){
							if(session.getULDDamageChecklistVO()!=null){
								session.getULDDamageChecklistVO().addAll(damageChecklistVOs);
							}
							else{
								session.setULDDamageChecklistVO(damageChecklistVOs);
							}
							
						}
					}
					uldDamageVO.setDamageDescription(damageDesc[i]);
					uldDamageVO.setSeverity(severity[i]);
					uldDamageVO.setReportedStation(reportedAirport[i]);
					if(reportedDate[i]!=null && reportedDate[i].trim().length() > 0) { 	
						LocalDate repDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
						String repDateStr = reportedDate[i];
						String repdate = null;
						StringBuilder time = new StringBuilder();
						time.append(" ").append(repDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repDateLocal.get(LocalDate.MINUTE)).append(":").append(repDateLocal.get(LocalDate.SECOND));
						if(repDateStr != null){
							repdate =repDateStr.concat(time.toString());
							repDateLocal.setDateAndTime(repdate);  		
						}
						uldDamageVO.setReportedDate(repDateLocal);
					}
					uldDamageVO.setFacilityType(facilityType[i]);
					uldDamageVO.setLocation(location[i]);
					uldDamageVO.setPartyType(partyType[i]);
					uldDamageVO.setParty(party[i]);
					uldDamageVO.setRemarks(remarks[i]);
					uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
					//uldDamageVO.setSequenceNumber(i);
					
				}
			}
		}
		//populating Repailr Details
		String[] repairHead = form.getRepHead();
		String[] repairStn = form.getRepairStn();
		String[] repairDate = form.getRepairDate();
		String[] dmgRepairRefNo = form.getDmgRepairRefNo();
		String[] amount = form.getAmount();
		String[] currency = form.getCurrency();
		String[] repRemark = form.getRepRemarks();
		String[] tempRepairOpFlag = form.getTempRepairOpFlag();
		String[] closed = form.getClosed();
		
		if (tempRepairOpFlag != null && tempRepairOpFlag.length > 0){
			for (int i = 0; i < tempRepairOpFlag.length-1; i++) {
				uldRepairVO=null;
				if(ULDRepairVO.OPERATION_FLAG_INSERT.equals(tempRepairOpFlag[i])) {
					uldRepairVO = new ULDRepairVO();
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
					uldRepairVOsFromSession.add(uldRepairVO);
				}else if(ULDRepairVO.OPERATION_FLAG_UPDATE.equals(tempRepairOpFlag[i]) || "N".equals(tempRepairOpFlag[i])){
					uldRepairVO = uldRepairVOsFromSession.get(i);
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_UPDATE);
				}else if(ULDRepairVO.OPERATION_FLAG_DELETE.equals(tempRepairOpFlag[i])){
					uldRepairVO = uldRepairVOsFromSession.get(i);
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_DELETE);
				}
				if (uldRepairVO!=null && (ULDRepairVO.OPERATION_FLAG_INSERT.equals(uldRepairVO.getOperationFlag())
						|| ULDRepairVO.OPERATION_FLAG_UPDATE.equals(uldRepairVO.getOperationFlag()))
						|| "N".equals(tempRepairOpFlag[i])) {
					
					uldRepairVO.setRepairHead(repairHead[i]);
					uldRepairVO.setRepairStation(repairStn[i]);
					if(repairDate[i]!=null && repairDate[i].trim().length() > 0) { 	
						LocalDate repairDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
						String repDateStr = repairDate[i];
						String repdate = null;
						StringBuilder time = new StringBuilder();
						time.append(" ").append(repairDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repairDateLocal.get(LocalDate.MINUTE)).append(":").append(repairDateLocal.get(LocalDate.SECOND));
						if(repDateStr != null){
							repdate =repDateStr.concat(time.toString());
							repairDateLocal.setDateAndTime(repdate);  		
						}
						uldRepairVO.setRepairDate(repairDateLocal);
					}
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					if(amount[i]!=null && amount[i].trim().length() > 0){
						uldRepairVO.setAmount(Double.parseDouble(amount[i]));
						//Added by A-7390 for ICRD-234401
						uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
					}
					uldRepairVO.setCurrency(currency[i]);
					uldRepairVO.setRemarks(repRemark[i]);
					uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());
				}
			}
		}
		if(closed!=null) {
			for(int i=0;i<closed.length;i++) {
				uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(true);
			}
		}
		uldDamageRepairDetailsVO.setUldDamageVOs(uLDDamageVOsFromSession);
		uldDamageRepairDetailsVO.setUldRepairVOs(uldRepairVOsFromSession);
		session.setULDDamageVO(uldDamageRepairDetailsVO);
	}
}
