package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DamageImageUploadCloseCommad extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	private static final String MAINTAIN_DAMAGE_REPORT_REPAIRNOTFOUND_ERROR = "uld.defaults.maintainDmgRep.msg.err.repairdetailsnotpresent";
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String DISPLAY_SUCCESS = "display_success";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	
		populateUldDamageRepairDetailsVO(maintainDamageReportForm,maintainDamageReportSession,logonAttributes);
		
		// for populating details from main screen into the session-START
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = maintainDamageReportSession
				.getULDDamageVO() != null ? maintainDamageReportSession
				.getULDDamageVO() : new ULDDamageRepairDetailsVO();
		if (uldDamageRepairDetailsVO != null) {
			uldDamageRepairDetailsVO.setDamageStatus(maintainDamageReportForm
					.getDamageStatus());
			uldDamageRepairDetailsVO.setOverallStatus(maintainDamageReportForm
					.getOverallStatus());
			uldDamageRepairDetailsVO.setRepairStatus(maintainDamageReportForm
					.getRepairStatus());
			uldDamageRepairDetailsVO.setSupervisor(maintainDamageReportForm
					.getSupervisor());
			uldDamageRepairDetailsVO
					.setInvestigationReport(maintainDamageReportForm
							.getInvRep());

		}
		maintainDamageReportForm.setScreenReloadStatus("reload");
		// for populating details from main screen into the session-END
		invocationContext.target = DISPLAY_SUCCESS;
	
	}
	
private void populateUldDamageRepairDetailsVO(MaintainDamageReportForm form,MaintainDamageReportSession session,LogonAttributes logonAttributes){
		
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
		String[] closed = form.getClosed();
		String[] imageCounts = new String[opFlag.length];
		
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
				uldDamageVO=null;
				if(ULDDamageVO.OPERATION_FLAG_INSERT.equals(opFlag[i])) {
					
					uldDamageVO = new ULDDamageVO();
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_INSERT);
					uLDDamageVOsFromSession.add(uldDamageVO);
				}else if(ULDDamageVO.OPERATION_FLAG_UPDATE.equals(opFlag[i]) || "N".equals(opFlag[i])){
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
					/*Collection<UploadFileModel> images = session.getDamageImageMap().get(Integer.toString(i));
					if(images != null){
						uldDamageVO.setImageCount(Integer.toString(images.size()));
					}else{
						uldDamageVO.setImageCount("0");
					}*/
					
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
		Double repairAmount = 0.0;
		
		if (tempRepairOpFlag != null && tempRepairOpFlag.length > 0){
			for (int i = 0; i < tempRepairOpFlag.length-1; i++) {
				uldRepairVO=null;
				if(ULDRepairVO.OPERATION_FLAG_INSERT.equals(tempRepairOpFlag[i])) {
					if(dmgRepairRefNo[i].contains(",")){
						String[] dmgRepairRefNumber=dmgRepairRefNo[i].split(",");
						if(form.getAmount()[i]!=null && form.getAmount()[i].trim().length() > 0){
							int len = dmgRepairRefNumber.length;
							repairAmount =Double.parseDouble(form.getAmount()[i])/len;
						}
						for (String temp: dmgRepairRefNumber){
							if(temp != null && temp.trim().length()>0) {
					uldRepairVO = new ULDRepairVO();
								uldRepairVO.setDamageReferenceNumber(Integer.parseInt(temp));
								uldRepairVO.setAmount(repairAmount);
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
								populateRepairDetailsVO(form, i, logonAttributes, uldRepairVO);
					uldRepairVOsFromSession.add(uldRepairVO);
							}
						}
					}
					else{
						uldRepairVO = new ULDRepairVO();
						if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
							uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
						}
						if(amount[i]!=null && amount[i].trim().length() > 0){
							uldRepairVO.setAmount(Double.parseDouble(amount[i]));
							uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
						}
						uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
						populateRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
						uldRepairVOsFromSession.add(uldRepairVO);
					}
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
				if (uldRepairVO!=null 
						&&(ULDRepairVO.OPERATION_FLAG_UPDATE.equals(uldRepairVO.getOperationFlag())
						|| "N".equals(tempRepairOpFlag[i]))) {
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					if(amount[i]!=null && amount[i].trim().length() > 0){
						uldRepairVO.setAmount(Double.parseDouble(amount[i]));
					}
				}
			}
		}
		if(closed!=null) {
			for(int i=0;i<closed.length;i++)
		if("Y".equals(closed[i]))
		{
			uLDDamageVOsFromSession.get(i).setClosed(true);
		}
		else{
			uLDDamageVOsFromSession.get(i).setClosed(false);
			
		}
		}
		uldDamageRepairDetailsVO.setUldDamageVOs(uLDDamageVOsFromSession);
		uldDamageRepairDetailsVO.setUldRepairVOs(uldRepairVOsFromSession);
		session.setULDDamageVO(uldDamageRepairDetailsVO);
		session.getULDDamageVO();
	}

	private void populateRepairDetailsVO(MaintainDamageReportForm form,int j,LogonAttributes logonAttributes,ULDRepairVO uldRepairVO) {
		uldRepairVO.setRepairHead(form.getRepHead()[j]);
		uldRepairVO.setRepairStation(form.getRepairStn()[j]);
		if(form.getRepairDate()[j]!=null && form.getRepairDate()[j].trim().length() > 0) {
			LocalDate repairDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String repDateStr = form.getRepairDate()[j];
			String repdate = null;
			StringBuilder time = new StringBuilder();
			time.append(" ").append(repairDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repairDateLocal.get(LocalDate.MINUTE)).append(":").append(repairDateLocal.get(LocalDate.SECOND));
			if(repDateStr != null){
				repdate =repDateStr.concat(time.toString());
				repairDateLocal.setDateAndTime(repdate);
			}
			uldRepairVO.setRepairDate(repairDateLocal);
		}
		uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
		uldRepairVO.setCurrency(form.getCurrency()[j]);
		uldRepairVO.setRemarks(form.getRepRemarks()[j]);
		uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());
	}

}
