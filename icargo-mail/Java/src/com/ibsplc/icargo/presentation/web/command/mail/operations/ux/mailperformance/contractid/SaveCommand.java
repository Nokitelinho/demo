package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.contractid;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class SaveCommand extends BaseCommand{

private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String AIRPORTCODE_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.arpEmpty";

	private static final String ORIGINAIRPORT_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.orgarpEmpty";
	private static final String DESTINATIONAIRPORT_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.destarpEmpty";
	private static final String CONTRACT_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.contractIDEmpty";
	private static final String REGION_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.regionEmpty";
	private static final String FROMDATE_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.fromDateEmpty";
	private static final String TODATE_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.toDateEmpty";
	private static final String DATE_PERIOD_INVALID =
			"mailtracking.defaults.ux.mailperformance.msg.err.datePeriodInvalid";
	private static final String SAVE_SUCCESS = "mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "\n\n in the save command----------> \n\n");

		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		Collection<GPAContractVO> gpaContractVOs = mailPerformanceSession.getGPAContractVOs();

		if(gpaContractVOs == null){
			gpaContractVOs = new ArrayList<GPAContractVO>();
		}

		gpaContractVOs = updateGPAContractVOs(gpaContractVOs,logonAttributes,mailPerformanceForm);

		if(gpaContractVOs != null && gpaContractVOs.size()>0){
			boolean orgErrorFlag=false;
			boolean destErrorFlag=false;
			boolean regErrorFlag=false;
			boolean conErrorFlag=false;
			boolean fromErrorFlag=false;
			boolean toErrorFlag=false;
			boolean rangeErrorFlag=false;

			for(GPAContractVO contractVO : gpaContractVOs){
				if(contractVO.getOriginAirports()==null|| ("").equals(contractVO.getOriginAirports())){
					ErrorVO error = new ErrorVO(ORIGINAIRPORT_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!orgErrorFlag){
  						errors.add(error);
  						orgErrorFlag=true;
  					}
				}
				if(contractVO.getDestinationAirports()==null || ("").equals(contractVO.getDestinationAirports())){
					ErrorVO error = new ErrorVO(DESTINATIONAIRPORT_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!destErrorFlag){
  						errors.add(error);
  						destErrorFlag=true;
  					}

				}
				if(contractVO.getContractIDs()==null || ("").equals(contractVO.getContractIDs())){
					ErrorVO error = new ErrorVO(CONTRACT_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!conErrorFlag){
  						errors.add(error);
  						conErrorFlag=true;
  					}

				}
				if(contractVO.getRegions()==null || ("").equals(contractVO.getRegions())){
					ErrorVO error = new ErrorVO(REGION_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!regErrorFlag){
  						errors.add(error);
  						regErrorFlag=true;
  					}
				}
				if(contractVO.getCidFromDates() == null|| ("").equals(contractVO.getCidFromDates())){
					ErrorVO error = new ErrorVO(FROMDATE_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!fromErrorFlag){
  						errors.add(error);
  						fromErrorFlag=true;
  					}

				}
				if(contractVO.getCidToDates() == null || ("").equals(contractVO.getCidToDates())){
					ErrorVO error = new ErrorVO(TODATE_EMPTY);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					if(!toErrorFlag){
  						errors.add(error);
  						toErrorFlag=true;
  					}

				}

				if(contractVO.getCidToDates().trim().length()!=0 && contractVO.getCidFromDates().trim().length()!=0){
			        int period = DateUtilities.getDifferenceInDays(contractVO.getCidFromDates(),
			        		contractVO.getCidToDates(), "dd-MMM-yyyy");

					if(period<0){
					    ErrorVO error = new ErrorVO(DATE_PERIOD_INVALID);
					 	mailPerformanceForm.setStatusFlag("Save_fail");
					     if(!rangeErrorFlag){
		  						errors.add(error);
		  						rangeErrorFlag=true;
		  					}

					}
					}

				if(contractVO.getOriginAirports()!=null && !contractVO.getOriginAirports().isEmpty()){

					try{

			    		new AreaDelegate().validateAirportCode
			    					(logonAttributes.getCompanyCode(),
			    							contractVO.getOriginAirports().toUpperCase().trim());

			    	}catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);
			    		invocationContext.addAllError(errors);
			    		mailPerformanceForm.setScreenFlag("serviceStandards");
			    		mailPerformanceForm.setStatusFlag("Save_fail");
						invocationContext.target = FAILURE;
				    	return;
			    	}
				}
if(contractVO.getDestinationAirports()!=null && !contractVO.getDestinationAirports().isEmpty()){

					try{

			    		new AreaDelegate().validateAirportCode
			    					(logonAttributes.getCompanyCode(),
			    							contractVO.getDestinationAirports().toUpperCase().trim());

			    	}catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);
			    		invocationContext.addAllError(errors);
			    		mailPerformanceForm.setScreenFlag("serviceStandards");
			    		mailPerformanceForm.setStatusFlag("Save_fail");
						invocationContext.target = FAILURE;
				    	return;
			    	}
				}


			}
		}

		 if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				mailPerformanceForm.setScreenFlag("cidRadiobtn");
				mailPerformanceSession.setGPAContractVOs((ArrayList<GPAContractVO>)gpaContractVOs);
				invocationContext.target = FAILURE;
			    return;

		 }

		 try {
				new MailTrackingDefaultsDelegate().saveContractDetails(gpaContractVOs);
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		 if(errors != null && errors.size()>0) {
				log.log(Log.FINE, "\n\n <============= ERROR !!!  =============> \n\n",errors);
				invocationContext.addAllError(errors);
				mailPerformanceForm.setScreenFlag("cidRadiobtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				invocationContext.target = FAILURE;
		    	return;
			}
		 if(gpaContractVOs != null && gpaContractVOs.size()>0){

			 log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
			 ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			 errors.add(error);
			 mailPerformanceForm.setStatusFlag("Save_success");
			 mailPerformanceForm.setScreenFlag("cidRadiobtn");
			 invocationContext.addAllError(errors);
		 }

		 mailPerformanceForm.setConPaCode("");
		 mailPerformanceForm.setOriginAirport("");
		 mailPerformanceForm.setDestinationAirport("");
		 mailPerformanceForm.setContractID("");
		 mailPerformanceForm.setRegion("");
		 mailPerformanceSession.removeGPAContractVOs();
		 mailPerformanceForm.setScreenFlag("cidRadiobtn");
		 mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		 invocationContext.target = SUCCESS;
	}
	/**
	 *
	 * 	Method		:	SaveCommand.updateGPAContractVOs
	 *	Added by 	:	A-6986 on 27-Jul-2018
	 * 	Used for 	:	ICRD-252821
	 *	Parameters	:	@param gpaContractVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<GPAContractVO>
	 */
	private Collection<GPAContractVO> updateGPAContractVOs(Collection<GPAContractVO> gpaContractVOs,
			LogonAttributes logonAttributes,MailPerformanceForm mailPerformanceForm){

		log.entering("SaveCommand","updateGPAContractVOs");

		String[] oprflags = mailPerformanceForm.getConOperationFlags();
		int maxsize=mailPerformanceForm.getContractIDs().length; 
    	String[] amotflag=new String[maxsize];
    	if(mailPerformanceForm.getAmot()!=null){
    		String[] counter=new String[maxsize];
    		int size=mailPerformanceForm.getAmot().length;
    		int cnt;
    		String amotform ;
    		for(cnt=0;cnt<size;cnt++){
    		    amotform = mailPerformanceForm.getAmot()[cnt];
    			counter[cnt]=amotform;
    		}
    		for(int i=0;i<size;i++){
    			amotflag[Integer.parseInt(counter[i])]="Y";
    		}
    		for(int i=0;i<maxsize;i++){
    			if(amotflag[i]==null||!amotflag[i].equals("Y")){
    				amotflag[i]="N";
    			}
    		}
    	}

    	int size = 0;
    	if(gpaContractVOs != null && gpaContractVOs.size()>0){
    		size = gpaContractVOs.size();
    	}
    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<GPAContractVO> newGPAContractVOs = new ArrayList<>();

    	for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){

					GPAContractVO gpaContractVO = new GPAContractVO();

					gpaContractVO.setCompanyCode(logonAttributes.getCompanyCode());
					gpaContractVO.setPaCode(mailPerformanceForm.getConPaCode());
					gpaContractVO.setOriginAirports(mailPerformanceForm.
							getOriginAirports()[index].toUpperCase().trim());
					gpaContractVO.setDestinationAirports(mailPerformanceForm.
							getDestinationAirports()[index].toUpperCase().trim());
					gpaContractVO.setContractIDs(mailPerformanceForm.getContractIDs()[index]);
					gpaContractVO.setRegions(mailPerformanceForm.getRegions()[index]);
					gpaContractVO.setCidFromDates(mailPerformanceForm.getCidFromDates()[index]);
					gpaContractVO.setCidToDates(mailPerformanceForm.getCidToDates()[index]);
					gpaContractVO.setLastUpdatedTime(currentDate);
					gpaContractVO.setLastUpdatedUser(logonAttributes.getUserId());
					gpaContractVO.setConOperationFlags(mailPerformanceForm.getConOperationFlags()[index]);
					if(mailPerformanceForm.getAmot()!=null){
						gpaContractVO.setAmot(amotflag[index]);}
		    			else{
		    				gpaContractVO.setAmot("N");}


					newGPAContractVOs.add(gpaContractVO);

				}
			}else{
				indexLessThanSize(gpaContractVOs, logonAttributes, mailPerformanceForm, oprflags, amotflag,
						newGPAContractVOs, index);
			}
    	}
    	log.exiting("SaveCommand","updateGPAContractVOs");
		return newGPAContractVOs;
	}
	private void indexLessThanSize(Collection<GPAContractVO> gpaContractVOs, LogonAttributes logonAttributes,
			MailPerformanceForm mailPerformanceForm, String[] oprflags, String[] amotflag,  Collection<GPAContractVO> newGPAContractVOs, int index) {
				int count = 0;
		if(gpaContractVOs!=null && !gpaContractVOs.isEmpty()){
					for(GPAContractVO gpaContractVO : gpaContractVOs){
						if(count == index){
					countEqualIndex(logonAttributes, mailPerformanceForm, oprflags, amotflag,
							newGPAContractVOs, index, gpaContractVO);
						}
						count++;
					}
				}
			}
	private void countEqualIndex(LogonAttributes logonAttributes, MailPerformanceForm mailPerformanceForm,
			String[] oprflags, String[] amotflag, Collection<GPAContractVO> newGPAContractVOs,
			int index, GPAContractVO gpaContractVO) {
		if(!"NOOP".equals(oprflags[index])){
	    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			gpaContractVO.setOriginAirports(mailPerformanceForm.
					getOriginAirports()[index].toUpperCase().trim());
			gpaContractVO.setDestinationAirports(mailPerformanceForm.
					getDestinationAirports()[index].toUpperCase().trim());
			gpaContractVO.setContractIDs(mailPerformanceForm.getContractIDs()[index]);
			gpaContractVO.setCidFromDates(mailPerformanceForm.getCidFromDates()[index]);
			gpaContractVO.setCidToDates(mailPerformanceForm.getCidToDates()[index]);
			gpaContractVO.setRegions(mailPerformanceForm.getRegions()[index]);
			gpaContractVO.setConOperationFlags(mailPerformanceForm.getConOperationFlags()[index]);
			gpaContractVO.setLastUpdatedTime(currentDate);
			gpaContractVO.setLastUpdatedUser(logonAttributes.getUserId());
			if(mailPerformanceForm.getAmot()!=null){
				gpaContractVO.setAmot(amotflag[index]);}
				else{
					gpaContractVO.setAmot("N");}
			 if("N".equals(oprflags[index])){
				 gpaContractVO.setConOperationFlags("U");}
			 else{
				 gpaContractVO.setConOperationFlags(oprflags[index]);}
			newGPAContractVOs.add(gpaContractVO);
		
			
		}
	}
	}
