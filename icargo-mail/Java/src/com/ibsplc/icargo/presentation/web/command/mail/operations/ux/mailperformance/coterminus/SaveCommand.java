package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.coterminus;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
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




public class SaveCommand extends BaseCommand {
	
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String RESDIT_EMPTY_CODE = 
							"mailtracking.defaults.ux.mailperformance.msg.err.resditempty";
	private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	private static final String ARP_EMPTY = 
				"mailtracking.defaults.ux.mailperformance.msg.err.arpEmpty";
	private static final String INVALID_RECIEVED_TRUCKFLAG = 
			"mailtracking.defaults.ux.mailperformance.msg.err.invalidtruckflag";
	private static final String SINGLE_AIRPORT_CONFIGURATION = 
			"mailtracking.defaults.ux.mailperformance.msg.err.singleairport";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the save command----------> \n\n");
		
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		mailPerformanceForm.setStatusFlag("");  
		
		ArrayList<CoTerminusVO> coTerminusVOs = mailPerformanceSession.getCoTerminusVOs();
		log.log(Log.FINE, "\n\n CoterminusVOS from session =============> \n\n",coTerminusVOs);
    	if (coTerminusVOs == null) {
    		coTerminusVOs = new ArrayList<CoTerminusVO>();
		}
    	
    	if(null!=mailPerformanceForm.getCoPacode()&&("").equals(mailPerformanceForm.getCoPacode())) {
	    	log.log(Log.FINE, "\n\n GPA_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	mailPerformanceForm.setStatusFlag("Save_fail");
	    	//error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			//error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);
	    }
    	
    	coTerminusVOs = updateMailPerformanceVOs(coTerminusVOs,
    			mailPerformanceForm,logonAttributes);
    	
		if(coTerminusVOs != null && coTerminusVOs.size()>0){
			for(CoTerminusVO coTerminusVO:coTerminusVOs){
				if(CoTerminusVO.OPERATION_FLAG_INSERT.equals(coTerminusVO.getCoOperationFlag()) && CoTerminusVO.FLAG_YES.equals(coTerminusVO.getTruckFlag() )&& 
						("21".equals(coTerminusVO.getResditModes()) || "23".equals(coTerminusVO.getResditModes()))){
					ErrorVO error = new ErrorVO(INVALID_RECIEVED_TRUCKFLAG);   
					//error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					//error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);    
				}
				if(("").equals(coTerminusVO.getResditModes())){
					ErrorVO error = new ErrorVO(RESDIT_EMPTY_CODE);
					//error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					//error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);
				}
			}
		}
		
		if(coTerminusVOs != null && coTerminusVOs.size()>0){
			for(CoTerminusVO coTerminusVO:coTerminusVOs){
				if(null==coTerminusVO.getCoAirportCodes()||("").equals(coTerminusVO.getCoAirportCodes())){
					ErrorVO error = new ErrorVO(ARP_EMPTY);
					//error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					//error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);
				}
			}
		}
		
		if(errors.size()==0){
			if(coTerminusVOs != null && coTerminusVOs.size()>0){
				for(CoTerminusVO coTerminusVO:coTerminusVOs){
					String[] airportCodes=null;
					if(coTerminusVO.getCoAirportCodes()!=null)
					airportCodes=coTerminusVO.getCoAirportCodes().split(",");
					if(CoTerminusVO.OPERATION_FLAG_INSERT.equals(coTerminusVO.getCoOperationFlag()) && airportCodes.length==1){
						ErrorVO error = new ErrorVO(SINGLE_AIRPORT_CONFIGURATION);  
						mailPerformanceForm.setStatusFlag("Save_fail");  
						errors.add(error);
					}
					try{
						for(String code:airportCodes){
			    		new AreaDelegate().validateAirportCode
			    					(logonAttributes.getCompanyCode(),
			    							code.toUpperCase().trim());
						}
			    	}catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);
			    		invocationContext.addAllError(errors);
			    		mailPerformanceForm.setScreenFlag("ctRadioBtn");
			    		mailPerformanceForm.setStatusFlag("Save_fail");
						invocationContext.target = FAILURE;
				    	return;
			    	}
				}
			}
			}
			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				mailPerformanceSession.setCoTerminusVOs(coTerminusVOs);
				mailPerformanceForm.setScreenFlag("ctRadioBtn");
				invocationContext.target = FAILURE;
		    	return;
			}
			
			mailPerformanceSession.setCoTerminusVOs(coTerminusVOs);
			
			
			try {
				new MailTrackingDefaultsDelegate().saveCoterminusDetails(coTerminusVOs);
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if(errors != null && errors.size()>0) {
				log.log(Log.FINE, "\n\n <============= ERROR !!!  =============> \n\n",errors);
				invocationContext.addAllError(errors);	
				mailPerformanceForm.setScreenFlag("ctRadioBtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				invocationContext.target = FAILURE;
		    	return;
			}
			
			if(coTerminusVOs != null && coTerminusVOs.size()>0){
				log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
				ErrorVO error = new ErrorVO(SAVE_SUCCESS);
				errors.add(error);
				mailPerformanceForm.setStatusFlag("Save_success");
				mailPerformanceForm.setScreenFlag("ctRadioBtn");
				invocationContext.addAllError(errors);
			}
			mailPerformanceForm.setCoAirport("");
			mailPerformanceSession.setCoTerminusVOs(null);
			mailPerformanceSession.setAirport(null);
			mailPerformanceForm.setScreenFlag("ctRadioBtn");
			mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SUCCESS;	
		
	}
	
	  private ArrayList<CoTerminusVO> updateMailPerformanceVOs(ArrayList<CoTerminusVO> coTerminusVOs , MailPerformanceForm mailPerformanceForm,
	    		LogonAttributes logonAttributes){
		  
		  log.entering("SaveCommand","updateMailSubClassVOs");
	    	
	    	String[] oprflags = mailPerformanceForm.getCoOperationFlag();
	    	int maxsize=mailPerformanceForm.getCoAirportCodes().length;
	    	String[] truckFlag=new String[maxsize];
	    	if(mailPerformanceForm.getTruckFlag()!=null){
	    		String[] counter=new String[maxsize];
	    		int size1=mailPerformanceForm.getTruckFlag().length;
	    		int cnt;
	    		for(cnt=0;cnt<size1;cnt++){
	    			counter[cnt]=mailPerformanceForm.getTruckFlag()[cnt];
	    		}
	    		for(int i=0;i<size1;i++){
	    			truckFlag[Integer.parseInt(counter[i])]="Y";
	    		}
	    		for(int i=0;i<maxsize;i++){
	    			if(truckFlag[i]==null||!truckFlag[i].equals("Y")){
	    				truckFlag[i]="N";
	    			}
	    		}
	    	
	    	}
	    	int size = 0;
	    	if(coTerminusVOs != null && coTerminusVOs.size() > 0){
				   size = coTerminusVOs.size();
	    	}
	    	Collection<CoTerminusVO> newCoterminusVOs = new ArrayList<CoTerminusVO>();
			for(int index=0; index<(oprflags.length-1);index++){
				
					if(!"NOOP".equals(oprflags[index])){
						CoTerminusVO coterminusVO = new CoTerminusVO();
						coterminusVO.setCompanyCode(logonAttributes.getCompanyCode());
						coterminusVO.setGpaCode(mailPerformanceForm.getCoPacode());
		    			coterminusVO.setCoAirportCodes(mailPerformanceForm.getCoAirportCodes()[index]);
		    			coterminusVO.setResditModes(mailPerformanceForm.getResditModes()[index]);
		    			coterminusVO.setCoOperationFlag(mailPerformanceForm.getCoOperationFlag()[index]);
		    			coterminusVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		    			if(mailPerformanceForm.getTruckFlag()!=null)
		    			coterminusVO.setTruckFlag(truckFlag[index]);
		    			else
		    				coterminusVO.setTruckFlag("N");
		    			if(mailPerformanceForm.getSeqnum()[index]!=0)
		    			coterminusVO.setSeqnum(mailPerformanceForm.getSeqnum()[index]);	
		    			
		    			coterminusVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		    			newCoterminusVOs.add(coterminusVO);
					}
				  
			}
	    	
	    	log.exiting("SaveCommand","updateMailSubClassVOs");
	    	
	    	return (ArrayList<CoTerminusVO>)newCoterminusVOs;
	  }

}
