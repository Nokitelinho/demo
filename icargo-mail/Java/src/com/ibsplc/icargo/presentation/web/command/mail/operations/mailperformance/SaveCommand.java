
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailperformance;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class SaveCommand extends BaseCommand {

	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("MailTracking,MailPerformance");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailperformance";
	
	private static final String GPA_EMPTY = 
					"mailtracking.defaults.mailperformance.msg.err.gpaempty";
	private static final String RESDIT_EMPTY_CODE = 
									"mailtracking.defaults.mailperformance.msg.err.resditempty";
	private static final String SAVE_SUCCESS = 
					"mailtracking.defaults.mailperformance.msg.info.savesuccess";
	private static final String ARP_EMPTY = 
						"mailtracking.defaults.mailperformance.msg.err.arpEmpty";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");

    	
		
		
    	MailPerformanceForm mailPerformanceForm =
						(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
						getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ArrayList<CoTerminusVO> coTerminusVOs = mailPerformanceSession.getCoTerminusVOs();
		log.log(Log.FINE, "\n\n CoterminusVOS from session =============> \n\n",coTerminusVOs);
    	if (coTerminusVOs == null) {
    		coTerminusVOs = new ArrayList<CoTerminusVO>();
		}
		
    	
    	
    	if(null!=mailPerformanceForm.getPacode()&&("").equals(mailPerformanceForm.getPacode())) {
	    	log.log(Log.FINE, "\n\n GPA_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	errors.add(error);
	    }
    	
    	coTerminusVOs = updateMailPerformanceVOs(coTerminusVOs,
    			mailPerformanceForm,logonAttributes);
    	
		if(coTerminusVOs != null && coTerminusVOs.size()>0){
			for(CoTerminusVO coTerminusVO:coTerminusVOs){
				if(("").equals(coTerminusVO.getResditModes())){
					ErrorVO error = new ErrorVO(RESDIT_EMPTY_CODE);
					errors.add(error);
				}
			}
		}
		if(coTerminusVOs != null && coTerminusVOs.size()>0){
			for(CoTerminusVO coTerminusVO:coTerminusVOs){
				if(null==coTerminusVO.getCoAirportCodes()||("").equals(coTerminusVO.getCoAirportCodes())){
					ErrorVO error = new ErrorVO(ARP_EMPTY);
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
			invocationContext.target = FAILURE;
	    	return;
		}
		
		if(coTerminusVOs != null && coTerminusVOs.size()>0){
			log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
			ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			mailPerformanceForm.setScreenFlag("ctRadioBtn");
			invocationContext.addAllError(errors);
		}
		mailPerformanceForm.setAirport("");
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
    	
    	String[] oprflags = mailPerformanceForm.getOperationFlag();
    	int maxsize=mailPerformanceForm.getAirportCodes().length;
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
		for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					CoTerminusVO coterminusVO = new CoTerminusVO();
					coterminusVO.setCompanyCode(logonAttributes.getCompanyCode());
					coterminusVO.setGpaCode(mailPerformanceForm.getPacode());
	    			coterminusVO.setCoAirportCodes(mailPerformanceForm.getAirportCodes()[index]);
	    			coterminusVO.setResditModes(mailPerformanceForm.getResditModes()[index]);
	    			coterminusVO.setCoOperationFlag(mailPerformanceForm.getOperationFlag()[index]);
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
			}else{
				int count = 0;
				if(coTerminusVOs != null && coTerminusVOs.size() > 0){
				   for(CoTerminusVO coterminusVO:coTerminusVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   coterminusVO.setCompanyCode(logonAttributes.getCompanyCode());
							   coterminusVO.setGpaCode(mailPerformanceForm.getPacode());
				    			coterminusVO.setCoAirportCodes(mailPerformanceForm.getAirportCodes()[index]);
				    			coterminusVO.setResditModes(mailPerformanceForm.getResditModes()[index]);
				    			if(mailPerformanceForm.getTruckFlag()!=null)
					    			coterminusVO.setTruckFlag(truckFlag[index]);
				    			else
				    				coterminusVO.setTruckFlag("N");
				    			if(mailPerformanceForm.getSeqnum()[index]!=0)
					    				coterminusVO.setSeqnum(mailPerformanceForm.getSeqnum()[index]);	
				    			
							   if("N".equals(oprflags[index])){
								   coterminusVO.setCoOperationFlag(null);
							   }else{
								   coterminusVO.setCoOperationFlag(oprflags[index]);
							   }
							   coterminusVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
							   coterminusVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
							   newCoterminusVOs.add(coterminusVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}
    	
    	log.exiting("SaveCommand","updateMailSubClassVOs");
    	
    	return (ArrayList<CoTerminusVO>)newCoterminusVOs;    	
    
    }
}
