package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.ULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.SaveDamageReportCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class SaveDamageReportCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");

	private static final String SAVE_SUCCESS = "save_success";
	private static final String SAVE_FAILURE = "save_failure";
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";

	private static final String SCREENID_ULDERRORLOG = "uld.defaults.ulderrorlog";
	private static final String SAVE_ULDERRORLOG = "save_ulderrorlog";
	private static final String MAINTAIN_DAMAGE_REPORT_INVREP_MISSING_ERROR = "uld.defaults.maintainDmgRep.msg.err.invrepmandatory";
	private static final String MAINTAIN_DAMAGE_REPORT_SUPERVISOR_MISSING_ERROR = "uld.defaults.maintainDmgRep.msg.err.supervisormandatory";
	private static final String MAINTAIN_DAMAGE_REPORT_SAVE_SUCCESS = "uld.defaults.maintaindamagereport.savedsuccessfully";
	private static final String MAINTAIN_DAMAGE_REPORT_REFNO_NOTCLOASED = "uld.defaults.maintainDmgRep.msg.err.dmgrefnonotclosed";
	private static final String MAINTAIN_DAMAGE_REPORT_DUPLICATE_DATA = "uld.defaults.maintainDmgRep.msg.err.duplicateddata";
	private static final String MAINTAIN_DAMAGE_REPORT_DMGREF_NOTPRESENT = "uld.defaults.maintainDmgRep.msg.err.damagerefnotpresent";
	private static final String MAINTAIN_DAMAGE_REPORT_DMGDATE_GREATERTHANPAIR = "uld.defaults.maintainDmgRep.msg.err.damagedategreaterthanrepair";
	private static final String MAINTAIN_DAMAGE_REPORT_DMGDETAIL_NOTPRESENT = "uld.defaults.maintainDmgRep.msg.err.damagedetailsnotpresent";
	private static final String MAINTAIN_DAMAGE_REPORT_DMGREFNO_MISSING_ERROR = "uld.defaults.maintainDmgRep.msg.err.dmgrefnomandatory";
	private static final String MAINTAIN_DAMAGE_REPORT_DMGREPAIRDATE_MISSING_ERROR = "uld.defaults.maintainDmgRep.msg.err.damagerepairdatemandatory";
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = (MaintainDamageReportSession) getScreenSession(MODULE, SCREENID);
		populateUldDamageRepairDetailsVO(maintainDamageReportForm,maintainDamageReportSession,logonAttributes);
		
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = maintainDamageReportSession
				.getULDDamageVO() != null ? maintainDamageReportSession
				.getULDDamageVO() : new ULDDamageRepairDetailsVO();
		Collection<ErrorVO> errors = null;

		if (!("continue").equals(maintainDamageReportForm.getSaveStatus())) {
			/**
			 * Validate for client errors
			 */
			if (maintainDamageReportSession.getULDDamageVO().getUldRepairVOs() != null && maintainDamageReportSession.getULDDamageVO().getUldDamageVOs() != null) {
				
				ArrayList<ULDRepairVO> uldRepairVOs = new ArrayList<ULDRepairVO>(maintainDamageReportSession.getULDDamageVO().getUldRepairVOs());
				ArrayList<ULDDamageVO> uldDamageVOs = new ArrayList<ULDDamageVO>(maintainDamageReportSession.getULDDamageVO().getUldDamageVOs());
				//Added as a part of Bug ICRD-318900 by A-8154
				handleDamageClosedStatus(uldDamageVOs,uldRepairVOs);

				if (uldDamageVOs != null && uldDamageVOs.size() > 0) {
					boolean isPresent = false;
					for (ULDDamageVO uldDamageVO : uldDamageVOs) {
						if ((uldDamageVO.getOperationFlag() != null
								&& !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE) && !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT))
								|| (uldDamageVO.getOperationFlag() == null)) {
							isPresent = true;
						}
					}
					if (!isPresent) {
						if (uldRepairVOs != null && uldRepairVOs.size() != 0) {
							ErrorVO error = null;
							error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGDETAIL_NOTPRESENT,null);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
							invocationContext.target = SAVE_FAILURE;
							return;
						}
					}
					errors = validateFormForDamageDetails(maintainDamageReportForm,uldDamageVOs,logonAttributes);
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
				}
				Collection<ErrorVO> errorsVal = new ArrayList<ErrorVO>();
				if (uldRepairVOs != null && uldRepairVOs.size() > 0	&& uldDamageVOs != null && uldDamageVOs.size() > 0) {
					boolean isPresent = false;
					boolean validOPPresent = false;
					long dmgRefNo = 0;
					
					errors = validateForm(maintainDamageReportForm,logonAttributes.getCompanyCode());

					if (errors != null && errors.size() > 0) {
						
						maintainDamageReportForm.setScreenStatusValue("REPPRESENT");
						invocationContext.addAllError(errors);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
					
					for (ULDRepairVO uldRepairVO : uldRepairVOs) {
						if((uldRepairVO.getOperationFlag() != null	&& !AbstractVO.OPERATION_FLAG_DELETE.equals(uldRepairVO.getOperationFlag()))
								|| (uldRepairVO.getOperationFlag() == null)){
							validOPPresent=true;
						for (ULDDamageVO uldDamageVO : uldDamageVOs) {
								if ((uldDamageVO.getOperationFlag() != null	&& !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE) 
										&& !uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_INSERT))
									|| (uldDamageVO.getOperationFlag() == null)) {
									if (uldRepairVO.getDamageReferenceNumber() == uldDamageVO.getDamageReferenceNumber()) {
										if (uldRepairVO.getRepairDate().isLesserThan(uldDamageVO.getReportedDate())) {
											errorsVal.add(new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGDATE_GREATERTHANPAIR));
									}
									isPresent = true;
									dmgRefNo = 0;
									break;
								} else {
									isPresent = false;
										dmgRefNo = uldRepairVO.getDamageReferenceNumber();
									}
								}else if(uldDamageVO.getOperationFlag().equals(AbstractVO.OPERATION_FLAG_DELETE)){
									validOPPresent=false;
								}
						}
						if (!isPresent) {
							break;
							}
						}
					}
					if (!isPresent && validOPPresent) {
						errorsVal.add(new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGREF_NOTPRESENT,new Object[] { dmgRefNo }));
					}
					if (errorsVal != null && errorsVal.size() > 0) {
						invocationContext.addAllError(errorsVal);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
					log.log(Log.FINE, "\n\n\n\n dmgRefNo", dmgRefNo);
					log.log(Log.FINE, "\n\n\n\n present", isPresent);
				}
				if (uldRepairVOs.size() > 0) {
					Collection<ErrorVO> errorsDup = new ArrayList<ErrorVO>();
					boolean isDupPresent = false;
					String dupRepairHead = "";
					String dupRepairStation = "";
					long dupDamageReferenceNumber = 0;
					int size = uldRepairVOs.size();
					for (int i = 0; i < size; i++) {
						int index = 0;
						for (ULDRepairVO uldRepairVO : uldRepairVOs) {
							if (index != i	&& uldRepairVO.getRepairHead().equals(uldRepairVOs.get(i).getRepairHead())
									&& uldRepairVO.getRepairStation().equals(uldRepairVOs.get(i).getRepairStation())
									&& uldRepairVO.getDamageReferenceNumber() == (uldRepairVOs.get(i).getDamageReferenceNumber())
									&& uldRepairVO.getRepairDate().equals(uldRepairVOs.get(i).getRepairDate())) {
								dupRepairHead = uldRepairVO.getRepairHead();
								dupRepairStation = uldRepairVO.getRepairStation();
								dupDamageReferenceNumber = uldRepairVO.getDamageReferenceNumber();
								isDupPresent = true;
								errorsDup.add(new ErrorVO(MAINTAIN_DAMAGE_REPORT_DUPLICATE_DATA,new Object[] { dupRepairHead,dupRepairStation,dupDamageReferenceNumber }));
								break;
							}
							index++;

							if(validateAirportCodes(uldRepairVO.getRepairStation().toUpperCase(),logonAttributes.getCompanyCode())!=null){
								errorsDup.add(new ErrorVO("uld.defaults.maintainDmgRep.msg.err.stationinvalid",null));
								isDupPresent = true;
							}

						}
						if (isDupPresent) {
							break;
						}
					}
					if (errorsDup != null && errorsDup.size() > 0) {
						invocationContext.addAllError(errorsDup);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
				}
				boolean isDmgrefnotClosed = false;
				if (uldRepairVOs != null && uldRepairVOs.size() > 0) {
					for (ULDRepairVO uldRepairVO : uldRepairVOs) {
						for (ULDDamageVO uldDamageVO : uldDamageVOs) {
							if (uldRepairVO.getDamageReferenceNumber() == uldDamageVO.getDamageReferenceNumber()
									&& !uldDamageVO.isClosed()) {
								isDmgrefnotClosed = true;
								break;
							}
						}
						if (isDmgrefnotClosed) {
							break;
						}
					}
					if (isDmgrefnotClosed) {
						maintainDamageReportForm.setSaveStatus("whethertoclose");
						ErrorVO error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_REFNO_NOTCLOASED);
						error.setErrorDisplayType(ErrorDisplayType.WARNING);
						invocationContext.addError(error);
						invocationContext.target = SAVE_FAILURE;
						return;
					}
				}
			}
		}

		if (uldDamageRepairDetailsVO != null) {
			errors = new ArrayList<ErrorVO>();
			try {
				uldDamageRepairDetailsVO.setDamageStatus(maintainDamageReportForm.getDamageStatus());
				uldDamageRepairDetailsVO.setOverallStatus(maintainDamageReportForm.getOverallStatus());
				uldDamageRepairDetailsVO.setRepairStatus(maintainDamageReportForm.getRepairStatus());
				uldDamageRepairDetailsVO.setSupervisor(maintainDamageReportForm.getSupervisor());
				uldDamageRepairDetailsVO.setInvestigationReport(maintainDamageReportForm.getInvRep());

				if (uldDamageRepairDetailsVO.getUldDamageVOs() != null && uldDamageRepairDetailsVO.getUldDamageVOs().size() > 0) {
					for (ULDDamageVO uldDamageVO : uldDamageRepairDetailsVO.getUldDamageVOs()) {
						if (uldDamageVO.getPictureVO() != null && uldDamageVO.getPictureVO().getImage() != null) {
							uldDamageVO.getPictureVO().setUldNumber(uldDamageRepairDetailsVO.getUldNumber());
						}
					}
				}
				// added by A-2883 for CR ANA1476. Modified by A-3415 for
				// ICRD-113953
				if (uldDamageRepairDetailsVO.getUldDamageVOs() != null && uldDamageRepairDetailsVO.getUldDamageVOs().size() > 0) {
					boolean checkclosed = true;
					for (ULDDamageVO uldDamageVO : uldDamageRepairDetailsVO.getUldDamageVOs()) {
						if (!uldDamageVO.isClosed()) {
							checkclosed = false;
						}
					}
					if (checkclosed) {
						uldDamageRepairDetailsVO.setRepairStatus("R");
					} else {
						if(!"P".equalsIgnoreCase(uldDamageRepairDetailsVO.getRepairStatus()))
							uldDamageRepairDetailsVO.setRepairStatus("D");
						log.log(Log.FINE,"\n\n\n\n uldDamageRepairDetailsVO.getDamageStatus(); ",uldDamageRepairDetailsVO.getDamageStatus());
					}
				}
				log.log(Log.FINE,"\n\n\n\n Populating image details");
				//populateDamageImages(uldDamageRepairDetailsVO,maintainDamageReportSession);
				log.log(Log.FINE,"\n\n\n\n uldDamageRepairDetailsVO for SAVE> ",uldDamageRepairDetailsVO);
				ArrayList<String> toRemove = maintainDamageReportSession.getRemoveDmgImage();
				ArrayList<ULDDamageVO> vos = (ArrayList<ULDDamageVO>)uldDamageRepairDetailsVO.getUldDamageVOs();;
				if(toRemove!=null){
				for(String index : toRemove){
					String dmgIndex = index.split("_")[0];
					vos.get(Integer.parseInt(dmgIndex)).setImageUpdated("Y");
				}
				} 
				uldDamageRepairDetailsVO.setUldDamageVOs(vos);
				saveDamageRepairDetails(uldDamageRepairDetailsVO);
				maintainDamageReportSession.setSavedULDDamageVO(uldDamageRepairDetailsVO);
				log.log(Log.FINE, "\n\n\n\n AFTER SAVE");
				
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				businessDelegateException.getMessage();
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}

		if (maintainDamageReportForm.getPageURL() != null
				&& ("fromulderrorlog").equals(maintainDamageReportForm
						.getPageURL())) {
			ULDErrorLogSession uldErrorLogSession = (ULDErrorLogSession) getScreenSession(
					MODULE, SCREENID_ULDERRORLOG);
			log.log(Log.FINE,
					"\n \n maintainDamageReportSession.getULDFlightMessageReconcileDetailsVO()",
					maintainDamageReportSession
							.getULDFlightMessageReconcileDetailsVO());
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try {
				log.log(Log.FINE, "\n reconcile  delegate ");
				new ULDDefaultsDelegate()
						.reconcileUCMULDError(maintainDamageReportSession
								.getULDFlightMessageReconcileDetailsVO());
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				exception = handleDelegateException(businessDelegateException);
			}
			if (exception != null && exception.size() > 0) {
				invocationContext.addAllError(exception);
				invocationContext.target = SAVE_FAILURE;
				return;
			}

			uldErrorLogSession.setPageURL("frommaintaindmgrep");
			maintainDamageReportSession.removeAllAttributes();
			invocationContext.target = SAVE_ULDERRORLOG;
			return;

		}
		maintainDamageReportSession.setRemoveDmgImage(null);
		maintainDamageReportSession.setRemoveDmgImage(null);
		maintainDamageReportSession.setDamageImageMap(null);
		ErrorVO error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_SAVE_SUCCESS);
		error.setErrorDisplayType(ErrorDisplayType.STATUS);
		errors = new ArrayList<ErrorVO>();
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target = SAVE_SUCCESS;
		maintainDamageReportSession.setULDDamageRepairDetailsVOs(null);
		maintainDamageReportForm.setStatusFlag("action_mainsave");
		maintainDamageReportForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	}

	private void populateUldDamageRepairDetailsVO(MaintainDamageReportForm form,MaintainDamageReportSession session,LogonAttributes logonAttributes){
		log.entering("SaveDamageReportCommand", "populateUldDamageRepairDetailsVO");
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = session.getULDDamageVO();
		HashMap<String,ArrayList<UploadFileModel>> damageImageMap = session.getDamageImageMap();
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
		ArrayList<String> toRemoveList = session.getRemoveDmgImage();
		//Added by A-8368 as part of user story - IASCB-35533
		String[] damageNoticePoint = form.getDamageNoticePoint();
		
		/**
		 * 
		 * Added for Screen stabilization
		 */
		Map<Integer,ULDDamagePictureVO> pictureVOs = new HashMap<Integer,ULDDamagePictureVO>();
		int index=0;
		ULDDamagePictureVO uldDamagePictureVO = null;
		ImageModel image = null;
		Collection<ULDDamagePictureVO> dmgPictureVOs = null;
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
		//Added by A-8368 as part of bug - IASCB-47907
		HashMap<String,Collection<ULDDamageChecklistVO>> damageChecklistMap = session.getDamageChecklistMap();
		Collection<ULDDamageChecklistVO> uldDamageChecklistVOs = new ArrayList<ULDDamageChecklistVO>();
		if (opFlag != null && opFlag.length > 0){
			for (int i = 0; i < opFlag.length-1; i++) {
				uldDamageVO=null;
				Collection<UploadFileModel> images = null;
				if(ULDDamageVO.OPERATION_FLAG_INSERT.equals(opFlag[i])) {
					
					uldDamageVO = new ULDDamageVO();
					uldDamageVO.setDamageCode(form.getUldNumber());
					uldDamageVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_INSERT);
					if(pictureVOs.containsKey(i)){
						uldDamageVO.setPictureVO(pictureVOs.get(i));
						uldDamageVO.setPicturePresent(true);
					}
					// populate ULDDamagePictureVO and set back
					if(damageImageMap != null){
						images = damageImageMap.get(Integer.toString(i));
					}
					if(images != null){
						for (UploadFileModel imgUpload : images){
							if(dmgPictureVOs == null){
								dmgPictureVOs = new ArrayList<ULDDamagePictureVO>();
							}
							uldDamagePictureVO = new ULDDamagePictureVO();
							uldDamagePictureVO.setCompanyCode(logonAttributes.getCompanyCode());
							uldDamagePictureVO.setUldNumber(uldDamageRepairDetailsVO.getUldNumber());
							uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
							image = new ImageModel();
							image.setName(imgUpload.getFileName().length()>50?imgUpload.getFileName().substring(0, 49):imgUpload.getFileName());
							image.setContentType(imgUpload.getContentType());
							image.setData(imgUpload.getData());
							image.setSize(imgUpload.getFileSize());
							uldDamagePictureVO.setImage(image);
							
							uldDamagePictureVO.setSequenceNumber(uldDamageVO.getSequenceNumber());
							uldDamagePictureVO.setImageSequenceNumber((int)uldDamageVO.getSequenceNumber());
							dmgPictureVOs.add(uldDamagePictureVO);
						}
					}
					uldDamageVO.setPictureVOs(dmgPictureVOs);
					dmgPictureVOs = null;
					uLDDamageVOsFromSession.add(uldDamageVO);
				}else if(ULDDamageVO.OPERATION_FLAG_UPDATE.equals(opFlag[i]) || "N".equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setDamageCode(form.getUldNumber());
					uldDamageVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_UPDATE);
					
					// populate ULDDamagePictureVO and set back
					if(damageImageMap != null){
						images = damageImageMap.get(Integer.toString(i));
					}
					
					if(images != null){
						for (UploadFileModel imgUpload : images){
							if(dmgPictureVOs == null){
								dmgPictureVOs = new ArrayList<ULDDamagePictureVO>();
							}
							uldDamagePictureVO = new ULDDamagePictureVO();
							uldDamagePictureVO.setUldNumber(form.getUldNumber());
							uldDamagePictureVO.setCompanyCode(logonAttributes.getCompanyCode());
							uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_UPDATE);
							uldDamagePictureVO.setFileName(imgUpload.getFileName().length()>50?imgUpload.getFileName().substring(0, 49):imgUpload.getFileName());
							image = new ImageModel();
							image.setName(imgUpload.getFileName().length()>50?imgUpload.getFileName().substring(0, 49):imgUpload.getFileName());
							image.setContentType(imgUpload.getContentType());
							image.setData(imgUpload.getData());
							image.setSize(imgUpload.getFileSize());
							uldDamagePictureVO.setImage(image);
							
							uldDamagePictureVO.setSequenceNumber(uldDamageVO.getSequenceNumber());
							uldDamagePictureVO.setImageSequenceNumber((int)uldDamageVO.getSequenceNumber());
							dmgPictureVOs.add(uldDamagePictureVO);
						}
						uldDamageVO.setPictureVOs(dmgPictureVOs);
						uldDamageVO.setImageUpdated("Y"); 
						dmgPictureVOs = null;
					}
				}else if(ULDDamageVO.OPERATION_FLAG_DELETE.equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_DELETE);
				}
				if (uldDamageVO!=null && (ULDDamageVO.OPERATION_FLAG_INSERT.equals(uldDamageVO.getOperationFlag())
						|| ULDDamageVO.OPERATION_FLAG_UPDATE.equals(uldDamageVO.getOperationFlag()))
						|| "N".equals(opFlag[i])) {
					
					uldDamageVO.setSection(section[i]);
					//Added by A-8368 as part of bug - IASCB-47907
					if(damageChecklistMap.containsKey(section[i])){
						uldDamageChecklistVOs = damageChecklistMap.get(section[i]);
						if(uldDamageChecklistVOs!=null && uldDamageChecklistVOs.size()>0)
							for(ULDDamageChecklistVO uldDamageChecklistVO : uldDamageChecklistVOs){
								if(uldDamageChecklistVO.getDescription().contains(damageDesc[i])){
									uldDamageVO.setDamageDescription(uldDamageChecklistVO.getDescription());
									break;
								}
							}
					}
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
					// Added by A-8368 as part of user story - IASCB-35533
					uldDamageVO.setDamageNoticePoint(damageNoticePoint[i]);
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
		Double repairAmount = 0.0;
		if (tempRepairOpFlag != null && tempRepairOpFlag.length > 0){
			for (int i = 0; i < tempRepairOpFlag.length-1; i++) {
				uldRepairVO=null;
				if(ULDRepairVO.OPERATION_FLAG_INSERT.equals(tempRepairOpFlag[i])) {
					//uldRepairVO = new ULDRepairVO();
					//uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
					//Added by A-7924 as part of ICRD-297633 starts
					if(dmgRepairRefNo[i].contains(",")){
						String[] dmgRepairRefNumber=dmgRepairRefNo[i].split(",");
						if(form.getAmount()[i]!=null && form.getAmount()[i].trim().length() > 0){
							int len = dmgRepairRefNumber.length;
							repairAmount =Double.parseDouble(form.getAmount()[i])/len;
						}
						for (String temp: dmgRepairRefNumber){
							uldRepairVO = new ULDRepairVO();
							uldRepairVO.setDamageReferenceNumber(Integer.parseInt(temp));
							uldRepairVO.setAmount(repairAmount);
							uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT); 
							populateUldRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
							uldRepairVOsFromSession.add(uldRepairVO);
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
						populateUldRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
						uldRepairVOsFromSession.add(uldRepairVO);
					}
					//Added by A-7924 as part of ICRD-297633 ends
					//uldRepairVOsFromSession.add(uldRepairVO);
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
				if (uldRepairVO!=null && (
						//ULDRepairVO.OPERATION_FLAG_INSERT.equals(uldRepairVO.getOperationFlag())						|| 
						ULDRepairVO.OPERATION_FLAG_UPDATE.equals(uldRepairVO.getOperationFlag()))
						|| "N".equals(tempRepairOpFlag[i])) {
					
					/*uldRepairVO.setRepairHead(repairHead[i]);
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
					}*/
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					if(amount[i]!=null && amount[i].trim().length() > 0){
						uldRepairVO.setAmount(Double.parseDouble(amount[i]));
						//Added by A-7390 for ICRD-234401
						//uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
					}
					populateUldRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
					/*uldRepairVO.setCurrency(currency[i]);
					uldRepairVO.setRemarks(repRemark[i]);
					uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());*/
				}
			}
		}
		
//		if(closed!=null) {
//			for(int i=0;i<closed.length;i++) {
//				uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(true);
//			}
//		}
		if(closed!=null) {
			for(int i=0;i<closed.length;i++)
		if("Y".equals(closed[i]))
		{
//			uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(true);
			uLDDamageVOsFromSession.get(i).setClosed(true);
		}
		else{
//			uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(false);
			uLDDamageVOsFromSession.get(i).setClosed(false);
			
			}
		}
		uldDamageRepairDetailsVO.setUldDamageVOs(uLDDamageVOsFromSession);
		uldDamageRepairDetailsVO.setUldRepairVOs(uldRepairVOsFromSession);
		session.setULDDamageVO(uldDamageRepairDetailsVO);
		log.entering("SaveDamageReportCommand", "populateUldDamageRepairDetailsVO");
	}
	//Added by A-7924 as part of ICRD-297633 starts
	private void populateUldRepairDetailsVO(MaintainDamageReportForm form,int j,LogonAttributes logonAttributes,ULDRepairVO uldRepairVO) {
		//ULDRepairVO uldRepairVO = new ULDRepairVO();
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
		/*if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
			uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
		}*/
		uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
		uldRepairVO.setCurrency(form.getCurrency()[j]);
		uldRepairVO.setRemarks(form.getRepRemarks()[j]);
		uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());
	}
	//Added by A-7924 as part of ICRD-297633 ends
	/**
	 * @param maintainDamageReportForm
	 * @param companyCode
	 * @return errors
	 */

	private Collection<ErrorVO> validateForm(MaintainDamageReportForm maintainDamageReportForm,	String companyCode) {
		log.entering("SaveDamageReportCommand", "validateForm");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if (maintainDamageReportForm.getSupervisor() == null|| maintainDamageReportForm.getSupervisor().trim().length() == 0) {
			error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_SUPERVISOR_MISSING_ERROR);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if (maintainDamageReportForm.getInvRep() == null || maintainDamageReportForm.getInvRep().trim().length() == 0) {
			error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_INVREP_MISSING_ERROR);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		int i=0;
		for(String opflag:maintainDamageReportForm.getTempRepairOpFlag()) {
			if(!("D".equals(opflag))&&!("NOOP".equals(opflag))){
				if (maintainDamageReportForm.getRepairDate()[i] == null || maintainDamageReportForm.getRepairDate()[i].trim().length() == 0) {
					error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGREPAIRDATE_MISSING_ERROR);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					break;
				}
				if(maintainDamageReportForm.getDmgRepairRefNo()!=null && maintainDamageReportForm.getDmgRepairRefNo().length> 0) {
					//for(String refno :maintainDamageReportForm.getDmgRepairRefNo()) {
					String refno = maintainDamageReportForm.getDmgRepairRefNo()[i];
						if(refno==null || refno.trim().length()==0) {
							error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGREFNO_MISSING_ERROR);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break;
						}
					//}
				} else {
					error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_DMGREFNO_MISSING_ERROR);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					break;
				}
			}
			i++;
		}
		
		log.exiting("SaveDamageReportCommand", "validateForm");
		return errors;
	}
	 /**
	 * @param maintainDamageReportForm
	 * @param companyCode 
	 * @return errors
	 */
	private Collection<ErrorVO> validateFormForDamageDetails(MaintainDamageReportForm maintainDamageReportForm, ArrayList<ULDDamageVO> uldDamageVOs,LogonAttributes logonAttributes){
		log.entering("SaveDamageReportCommand", "validateFormForDamageDetails");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(maintainDamageReportForm.getDamageStatus()==null||maintainDamageReportForm.getDamageStatus().length()==0){
			error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.damagestatus");
			 error.setErrorDisplayType(ErrorDisplayType.ERROR);
			 errors.add(error);
		}
		for (ULDDamageVO damageVO : uldDamageVOs) {
			if(damageVO.getDamageDescription()== null || damageVO.getDamageDescription().trim().length() == 0){
				error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.damagedescription");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 errors.add(error);
			}
			if(damageVO.getReportedStation()== null || damageVO.getReportedStation().trim().length() == 0){
				 error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.repstnmandatory");
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 errors.add(error);
			}else{
				if(validateAirportCodes(damageVO.getReportedStation().toUpperCase(),logonAttributes.getCompanyCode())!=null){
					errors.add(new ErrorVO("uld.defaults.maintainDmgRep.msg.err.stationinvalid",null));
				}
			}
			if(damageVO.getLocation() != null && damageVO.getLocation().trim().length() > 0){
				Collection<ErrorVO> newerrors = new ArrayList<ErrorVO>();
				newerrors = validatelocation(logonAttributes,damageVO.getFacilityType(),damageVO.getLocation(),damageVO.getReportedStation());
				if(newerrors.size() > 0){
					errors.addAll(newerrors);
				}
			}
			if("G".equals(damageVO.getPartyType()) && (damageVO.getParty() != null && damageVO.getParty().trim().length() > 0)){
				Collection<ErrorVO> newerrors = new ArrayList<ErrorVO>();
				newerrors = validateAgentCode(damageVO.getParty(),logonAttributes);
				if(newerrors.size() > 0){
					errors.addAll(newerrors);
				}
			}
			if("A".equals(damageVO.getPartyType()) && (damageVO.getParty() != null && damageVO.getParty().trim().length() > 0)){
				log.log(Log.FINE, " \n inside ailrine validation");
				try {
		    		AirlineDelegate delegate = new AirlineDelegate();	
		    		delegate.validateAlphaCode(logonAttributes.getCompanyCode(),damageVO.getParty());			
		    	} catch (BusinessDelegateException e) {
					e.getMessage();
					 error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.invalidstation");
				   	 errors.add(error);
		    	}
			}
		}
		log.exiting("AddDamageDetailsCommand", "validateForm");
		return errors;
	}

	public Collection<ErrorVO> validateAgentCode(String agentCode,
			LogonAttributes logonAttributes) {
		ErrorVO error = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		AgentVO agentVO = null;
		try {
			agentVO = new AgentDelegate().findAgentDetails(
					logonAttributes.getCompanyCode(), agentCode);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		if (agentVO == null) {
			error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.invalidagentcode");
			errors.add(error);
		}
		return errors;
	}
   
	public Collection<ErrorVO> validateAirportCodes(String station,
			String cmpCode) {
		log.entering("Command", "validateAirportCodes");
		log.log(Log.FINE, " Station ---> ", station);
		Collection<ErrorVO> errors = null;
		try {
			AreaDelegate delegate = new AreaDelegate();
			delegate.validateAirportCode(cmpCode, station);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			log.log(Log.FINE, " Error Airport ---> ", e.getMessageVO().getErrorType());
			errors = handleDelegateException(e);
		}
		log.exiting("Command", "validateAirportCodes");
		return errors;
	}

	private Collection<ErrorVO> validatelocation(LogonAttributes logonAttributes, String facilitycode,String location, String airportCode) {

		Collection<ULDAirportLocationVO> uldAirportLocationVOs = null;
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		String station = null;
		if (airportCode != null) {
			station = airportCode.toUpperCase();
		} else {
			station = logonAttributes.getStationCode().toUpperCase();
		}
		if (facilitycode != null) {
			facilitycode = facilitycode.toUpperCase();
		} 
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		try {
			uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation(logonAttributes.getCompanyCode(), station,facilitycode);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, " \n Location", location);
		boolean isContain = false;
		for (ULDAirportLocationVO vos : uldAirportLocationVOs) {
			if (vos.getFacilityCode().equals(location)) {
				isContain = true;
			}
		}
		if (!isContain) {
			error = new ErrorVO("uld.defaults.maintainDmgRep.msg.err.invalidlocation");
			errors.add(error);
		}
		return errors;
	}

	/**
	 * Method to save the save uldDamageRepairDetailsVO
	 * 
	 * @param uldDamageRepairDetailsVO
	 * @return void
	 * @throws BusinessDelegateException
	 */
	private void saveDamageRepairDetails(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO)	throws BusinessDelegateException {
		log.entering("SaveCommand", "saveDamageRepairDetails");
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		uldDefaultsDelegate.saveULDDamage(uldDamageRepairDetailsVO);
		log.exiting("SaveCommand", "saveDamageRepairDetails");
		return;
	}
	
	private void populateDamageImages(ULDDamageRepairDetailsVO uldDamageRepairDetailsVO , MaintainDamageReportSession maintainDamageReportSession){
		HashMap<String,ArrayList<UploadFileModel>> damageImageMap = maintainDamageReportSession.getDamageImageMap();
		long dmgRefNum = 0;
		Collection<ULDDamagePictureVO> pictureVOs = null;
		ULDDamagePictureVO uldDamagePictureVO = null;
		ImageModel image = null;
		for (ULDDamageVO uldDamageVO : uldDamageRepairDetailsVO.getUldDamageVOs()){
			dmgRefNum = uldDamageVO.getDamageReferenceNumber();
			Collection<UploadFileModel> images = damageImageMap.get(new Long(dmgRefNum).toString());
			if(images == null){
				continue;
			}
			// populate ULDDamagePictureVO and set back
			if(pictureVOs == null){
				pictureVOs = new ArrayList<ULDDamagePictureVO>();
			}
			for (UploadFileModel imgUpload : images){
				uldDamagePictureVO = new ULDDamagePictureVO();
				uldDamagePictureVO.setCompanyCode(uldDamageRepairDetailsVO.getCompanyCode());
				uldDamagePictureVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
				// Construct image
				image = new ImageModel();
				image.setName(imgUpload.getFileName().length()>50?imgUpload.getFileName().substring(0, 49):imgUpload.getFileName());
				image.setContentType(imgUpload.getContentType());
				image.setData(imgUpload.getData());
				image.setSize(imgUpload.getFileSize());
				uldDamagePictureVO.setImage(image);
				
				uldDamagePictureVO.setSequenceNumber(uldDamageVO.getSequenceNumber());
				uldDamagePictureVO.setImageSequenceNumber((int)uldDamageVO.getSequenceNumber());
				pictureVOs.add(uldDamagePictureVO);
			}
			uldDamageVO.setPictureVOs(pictureVOs);
		}
	}// End of populateDamageImages
	/**
	 * 	Method		:	handleDamageClosedStatus
	 *	Added by 	:	A-8154
	 * 	Used for 	: 	added this method for handling closed status after discussion if not populated correctly from screen for ICRD-318900
	 *	Parameters	:	@param uldDamageVOs
	 *	Parameters	:	@param uldRepairVOs 
	 *	Return type	:	void 
	 */
	private void handleDamageClosedStatus(ArrayList<ULDDamageVO> uldDamageVOs,ArrayList<ULDRepairVO> uldRepairVOs){
		log.entering("SaveDamageReportCommand", "handleDamageClosedStatus");
		if (uldDamageVOs != null && uldDamageVOs.size() > 0 && 
				uldRepairVOs != null && uldRepairVOs.size() > 0) {
			for(ULDRepairVO uldRepairVO:uldRepairVOs){
				for(ULDDamageVO uLDDamageVO: uldDamageVOs){
					if(uLDDamageVO.getDamageReferenceNumber() == uldRepairVO.getDamageReferenceNumber()){
						uLDDamageVO.setClosed(true);
					}
				}
			}
		}
		log.exiting("SaveDamageReportCommand", "handleDamageClosedStatus");
	}
}
