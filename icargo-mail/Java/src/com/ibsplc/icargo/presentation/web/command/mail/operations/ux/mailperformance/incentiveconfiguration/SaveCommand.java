
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class SaveCommand extends BaseCommand{
private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	

	private static final String PARAMETER_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.parameterEmpty";
	private static final String FORMULA_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.formulaEmpty";
	private static final String BASIS_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.basisEmpty";
	private static final String VALIDFROM_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.validFromEmpty";
	private static final String VALIDTO_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.validtoEmpty";
	private static final String PERCENTAGE_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.percentageEmpty";
	private static final String FROM_TO_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.fromToEmpty";
	private static final String SAVE_SUCCESS = "mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";

	private static final String INCENTIVE = "incentive";
	private static final String  USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";//Added for IASCB-333
	
	private static final String FORMULA_ALREADY_EXISTS_WITH_SAME_PERCENTAGE_FOR_SAME_PRODUCT = 
			   "mailtracking.defaults.ux.mailperformance.msg.err.formulaalreadyexistswithsamepercentforsameproduct";
	private static final String FORMULA_ALREADY_EXISTS_WITH_DIFF_PERCENTAGE_FOR_SAME_PRODUCT = "mailtracking.defaults.ux.mailperformance.msg.err.formulaalreadyexistswithdifferentpercentage";

    private static final String SAME_CONFIGURATION_EXISTS = "mailtracking.defaults.ux.mailperformance.msg.err.sameconfigurationexistsforsameperiod";
    private static final String PERCENTAGE_SLABS_OVERLAPPED = "mailtracking.defaults.ux.mailperformance.msg.err.percentageslaboverlapped";
    private static final String BOTH_CONFIGURATION_NOT_ALLOWED = "mailtracking.defaults.ux.mailperformance.msg.err.bothconfigurationnotallowed";
    private static final String SERVICE_RESPONSIVE = "Y";
    private static final String NON_SERVICE_RESPONSIVE = "N";
    private static final String BOTH = "B";
    private static final String PERCENTAGE_FROM = "PERFRM";
    private static final String PERCENTAGE_TO = "PERTOO";
    private static final String DUPLICATE_RECORD = "mailtracking.defaults.ux.mailperformance.msg.err.duplicaterecord";
    private static final String INCENTIVEPRODUCTPARAMETER = "PRD";
    private static final String MAILSERVICELEVELS= "mail.operations.mailservicelevels";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "\n\n in the save command----------> \n\n");

		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession =
				getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		Collection<IncentiveConfigurationVO> incentiveConfigurationVOs = mailPerformanceSession.
				getIncentiveConfigurationVOs();
		Collection<ErrorVO> validationErrors = new ArrayList<ErrorVO>();

		String paDom = null;//Added for IASCB-333
		
		if(incentiveConfigurationVOs == null){
			incentiveConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();
		}
		Collection<IncentiveConfigurationVO> srvIncentiveConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();
		Collection<IncentiveConfigurationVO> nonSrvIncentiveConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();
		Collection<IncentiveConfigurationVO> bothSrvIncentiveConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();
		Collection<IncentiveConfigurationVO> tempIncentiveConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();


		if(INCENTIVE.equals(mailPerformanceForm.getIncFlag())){
		
			mailPerformanceForm.setIncFlag(MailConstantsVO.FLAG_YES);
				
			incentiveConfigurationVOs = updateIncentiveVOs(incentiveConfigurationVOs,logonAttributes,mailPerformanceForm);
		
			if(incentiveConfigurationVOs != null && incentiveConfigurationVOs.size()>0){
				int count = 0;
				boolean fromToError = false;
				boolean incPercError = false;
				boolean incValidFromError = false;
				boolean incValidToError = false;
				for(IncentiveConfigurationVO incentiveVO : incentiveConfigurationVOs){
					
					
					int slabValue=0;
					//Empty Checks
					for(IncentiveConfigurationDetailVO detailVO : incentiveVO.getIncentiveConfigurationDetailVOs()){
						
						if(detailVO.getIncParameterValue() == null || detailVO.getIncParameterValue().trim().length()==0){
							slabValue++; //count of parameter value
						}
					}
					if(slabValue >0 ){
						ErrorVO error = new ErrorVO(FROM_TO_EMPTY);
	  					mailPerformanceForm.setStatusFlag("Save_fail");
						if(!fromToError){
	  					errors.add(error);
							fromToError = true;
						}
						
					}
					
					if(incentiveVO.getIncPercentage().toString()== null ){

						ErrorVO error = new ErrorVO(PERCENTAGE_EMPTY);
	  					mailPerformanceForm.setStatusFlag("Save_fail");
	  					if(!incPercError){
	  					errors.add(error);
							incPercError = true;
						}
					}
					if(incentiveVO.getIncValidFrom() == null || incentiveVO.getIncValidFrom().trim().length() == 0){
						ErrorVO error = new ErrorVO(VALIDFROM_EMPTY);
	  					mailPerformanceForm.setStatusFlag("Save_fail");
	  					if(!incValidFromError){
	  					errors.add(error);
							incValidFromError = true;
						}
					}
					if(incentiveVO.getIncValidTo() == null || incentiveVO.getIncValidTo().trim().length() == 0 ){
						ErrorVO error = new ErrorVO(VALIDTO_EMPTY);
	  					mailPerformanceForm.setStatusFlag("Save_fail");
	  					if(!incValidToError){
	  					errors.add(error);
							incValidToError = true;
						}
					}
					
				}
				
				if(errors.size() == 0){
					for(IncentiveConfigurationVO incentiveVO : incentiveConfigurationVOs){
						
						validationErrors = validateIncentiveConfiguration(incentiveConfigurationVOs,incentiveVO,count);
						if(validationErrors.size()>0){
							break;
					}
					count++;
					}
				}
			}
			
			if(validationErrors != null){
				for(ErrorVO errorVO : validationErrors){
					errors.add(errorVO);
				}
			}

		}else{
			

			mailPerformanceForm.setIncFlag(MailConstantsVO.FLAG_NO);

			srvIncentiveConfigurationVOs = updateServiceDisIncentiveVOs(incentiveConfigurationVOs,logonAttributes,mailPerformanceForm);
		
			nonSrvIncentiveConfigurationVOs = updateNonServiceDisIncentiveVOs(incentiveConfigurationVOs,logonAttributes,mailPerformanceForm);
			
			bothSrvIncentiveConfigurationVOs = updateBothServiceDisIncentiveVOs(incentiveConfigurationVOs,logonAttributes,mailPerformanceForm);
			
			if(srvIncentiveConfigurationVOs.size()>0){
				for(IncentiveConfigurationVO configVO: srvIncentiveConfigurationVOs){
					tempIncentiveConfigurationVOs.add(configVO);
				}
			}
			if(nonSrvIncentiveConfigurationVOs.size()>0){
				for(IncentiveConfigurationVO configVO: nonSrvIncentiveConfigurationVOs){
					tempIncentiveConfigurationVOs.add(configVO);
				}
			}
			if(bothSrvIncentiveConfigurationVOs.size()>0){
				for(IncentiveConfigurationVO configVO: bothSrvIncentiveConfigurationVOs){
					tempIncentiveConfigurationVOs.add(configVO);
				}
			}
			incentiveConfigurationVOs = tempIncentiveConfigurationVOs;
			try{
				paDom = findSystemParameterValue(USPS_DOMESTIC_PA);	
					
			}catch (BusinessDelegateException localBusinessDelegateException3) {
				this.log.log(7, "syspar fetch exception");
			}
			
			if(incentiveConfigurationVOs != null && incentiveConfigurationVOs.size()>0){
				
				int count = 0;
				boolean parameterError = false;
				boolean formulaError = false;
				boolean basisError = false;
				boolean disIncPercError = false;
				boolean disIncValidFromError = false;
				boolean disIncValidToError = false;
				
				for(IncentiveConfigurationVO incentiveVO : incentiveConfigurationVOs){
					if(incentiveVO.getIncentiveConfigurationDetailVOs()== null
							|| incentiveVO.getIncentiveConfigurationDetailVOs().size() == 0){
						ErrorVO error = new ErrorVO(PARAMETER_EMPTY);
						if(!parameterError){
		  					errors.add(error);
		  					parameterError = true;
						}
	  					
					}
					if(incentiveVO.getFormula() == null || incentiveVO.getFormula().trim().length() == 0){
						ErrorVO error = new ErrorVO(FORMULA_EMPTY);
						if(!formulaError){
		  					errors.add(error);
		  					formulaError = true;
						}
					}
					if(paDom.equals(mailPerformanceForm.getIncPaCode())){//Added for IASCB-333
						if(incentiveVO.getBasis() == null || incentiveVO.getBasis().trim().length() == 0){
							ErrorVO error = new ErrorVO(BASIS_EMPTY);
							if(!basisError){
			  					errors.add(error);
			  					basisError = true;
							}
						}
					}
					
					if(incentiveVO.getDisIncPercentage().toString()==null){
						ErrorVO error = new ErrorVO(PERCENTAGE_EMPTY);
						if(!disIncPercError){
		  					errors.add(error);
		  					disIncPercError = true;
						}
					}
					if(incentiveVO.getDisIncValidFrom() == null || incentiveVO.getDisIncValidFrom().trim().length() == 0){
						ErrorVO error = new ErrorVO(VALIDFROM_EMPTY);
						if(!disIncValidFromError){
		  					errors.add(error);
		  					disIncValidFromError = true;
						}
					}
					if(incentiveVO.getDisIncValidTo() == null ||incentiveVO.getDisIncValidTo().trim().length()==0 ){
						ErrorVO error = new ErrorVO(VALIDTO_EMPTY);
						if(!disIncValidToError){
		  					errors.add(error);
		  					disIncValidToError = true;
						}
					}
						
					/*if((IncentiveConfigurationVO.OPERATION_FLAG_INSERT.equals(incentiveVO.getDisIncOperationFlags())
							|| IncentiveConfigurationVO.OPERATION_FLAG_UPDATE.equals(incentiveVO.getDisIncOperationFlags() ))
							&& (errors.size() == 0)){
						validationErrors = validateDisIncentiveConfiguration(incentiveConfigurationVOs,incentiveVO,count);
						if(validationErrors.size()>0){
							break;
						}
					}*/
					
				}
				
				if(errors.size() == 0){
					for(IncentiveConfigurationVO incentiveVO : incentiveConfigurationVOs){
						validationErrors = validateDisIncentiveConfiguration(incentiveConfigurationVOs,incentiveVO,count);
						if(validationErrors.size()>0){
							break;
					}
					count++;
				}
					
				}
			}
			
			
			if(validationErrors != null && validationErrors.size()>0){
				for(ErrorVO errorVO : validationErrors){
					errors.add(errorVO);
				}
			}
		}


		 if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				mailPerformanceForm.setDisIncFlag(mailPerformanceForm.getIncFlag());
				mailPerformanceForm.setServRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
				mailPerformanceSession.setIncentiveConfigurationVOs((ArrayList<IncentiveConfigurationVO>)incentiveConfigurationVOs);
				invocationContext.target = FAILURE;
			    return;

		 }


		 try {
				new MailTrackingDefaultsDelegate().saveIncentiveConfigurationDetails(incentiveConfigurationVOs);
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

		 if(errors != null && errors.size()>0) {
				log.log(Log.FINE, "\n\n <============= ERROR !!!  =============> \n\n",errors);
				invocationContext.addAllError(errors);
				mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				mailPerformanceForm.setDisIncFlag(mailPerformanceForm.getIncFlag());
				mailPerformanceForm.setServRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
				invocationContext.target = FAILURE;
		    	return;
			}

		 if(incentiveConfigurationVOs != null && incentiveConfigurationVOs.size()>0) {

			 log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
			 ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			 errors.add(error);
			 mailPerformanceForm.setStatusFlag("Save_success");
			
			 invocationContext.addAllError(errors);
		 }

		 mailPerformanceForm.setIncPaCode("");
		 mailPerformanceSession.setIncentiveConfigurationVOs(null);
		 mailPerformanceSession.setIncentiveConfigurationDetailVOs(null);
		 mailPerformanceSession.setParameterVO(null);
		 mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
		 mailPerformanceForm.setDisIncFlag(mailPerformanceForm.getIncFlag());
		 mailPerformanceForm.setServRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
		 mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		 invocationContext.target = SUCCESS;
	}

	/**
	 *
	 * 	Method		:	SaveCommand.updateServiceDisIncentiveVOs
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */

	private Collection<IncentiveConfigurationVO> updateServiceDisIncentiveVOs(
			Collection<IncentiveConfigurationVO> incentiveVOs,LogonAttributes logonAttributes,
			MailPerformanceForm mailPerformanceForm){

		log.entering("SaveCommand","updateDisIncentiveVOs");

		String[] oprflags = mailPerformanceForm.getDisIncSrvOperationFlags();
		
    	int size = 0;

    	if(incentiveVOs != null && incentiveVOs.size()>0){
    		for(IncentiveConfigurationVO configVO : incentiveVOs){
    			if("N".equals(configVO.getIncentiveFlag()) && "Y".equals(configVO.getServiceRespFlag())){
    				size = size + 1;
    			}
    		}
    	}

    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<IncentiveConfigurationVO> newIncentiveVOs = new ArrayList<IncentiveConfigurationVO>();
    	Collection<IncentiveConfigurationDetailVO> newIncentiveDetailVOs = null;
    	

    	for(int index=0; index<oprflags.length;index++){
    		String newFormula = null;
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){

					IncentiveConfigurationVO incentiveConfigurationVO = new IncentiveConfigurationVO();
					if(mailPerformanceForm.getSrvFormula()[index]!= null 
							&& mailPerformanceForm.getSrvFormula()[index].trim().length()>0){
						if(mailPerformanceForm.getSrvFormula()[index].contains("GT"))
							newFormula = mailPerformanceForm.getSrvFormula()[index].replace("GT", ">");
						if(mailPerformanceForm.getSrvFormula()[index].contains("LT")){
						
							if(newFormula!=null){
							newFormula = newFormula.replace("LT", "<");
							}else{
								newFormula = mailPerformanceForm.getSrvFormula()[index].replace("LT", "<");
							}
						}
						if(mailPerformanceForm.getSrvFormula()[index].contains("ADD")){
							if(newFormula!=null){
							newFormula = newFormula.replace("ADD", "+");
							}else{
								newFormula = mailPerformanceForm.getSrvFormula()[index].replace("ADD", "+");
							}
						}
					}
					
					incentiveConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
					incentiveConfigurationVO.setPaCode(mailPerformanceForm.getIncPaCode());
					if(newFormula!=null){
					incentiveConfigurationVO.setFormula(newFormula.trim());
						incentiveConfigurationVO.setSrvFormula(newFormula.trim());
					}else{
						incentiveConfigurationVO.setFormula(mailPerformanceForm.getSrvFormula()[index].trim());
						incentiveConfigurationVO.setSrvFormula(mailPerformanceForm.getSrvFormula()[index].trim());
					}
					incentiveConfigurationVO.setBasis(mailPerformanceForm.getSrvBasis()[index]);
					incentiveConfigurationVO.setSrvBasis(mailPerformanceForm.getSrvBasis()[index]);
					incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncSrvPercentage()[index]);
					incentiveConfigurationVO.setDisIncSrvPercentage(mailPerformanceForm.getDisIncSrvPercentage()[index]);
					incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncSrvValidFrom()[index]);
					incentiveConfigurationVO.setDisIncSrvValidFrom(mailPerformanceForm.getDisIncSrvValidFrom()[index]);
					incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncSrvValidTo()[index]);
					incentiveConfigurationVO.setDisIncSrvValidTo(mailPerformanceForm.getDisIncSrvValidTo()[index]);
					incentiveConfigurationVO.setLastUpdatedTime(currentDate);
					incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
					incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncSrvOperationFlags()[index]);
					incentiveConfigurationVO.setDisIncSrvOperationFlags(mailPerformanceForm.getDisIncSrvOperationFlags()[index]);
					incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());

					incentiveConfigurationVO.setServiceRespFlag(SERVICE_RESPONSIVE);

					newIncentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
					String[] parameter = null;
					if(mailPerformanceForm.getDisIncSrvParameter()[index].length() > 0){
						String param = mailPerformanceForm.getDisIncSrvParameter()[index].concat("\r\n")	;					
					 parameter = param.split("\n");
					}
					else{
						parameter = mailPerformanceForm.getDisIncSrvParameter()[index].split("\n");
					}
					String[] excFlag = null;
					if(mailPerformanceForm.getSrvExcFlag()[index]!=null 
							&& mailPerformanceForm.getSrvExcFlag()[index].length()>0){
						excFlag = mailPerformanceForm.getSrvExcFlag()[index].split(",");
					 }

					for(int j=0;j<parameter.length;j++){
						
						String[] codeValue = parameter[j].split(":");
						
						 if(codeValue[0].length()>0  && codeValue[1].length()>0){

						 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
           				 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
           				 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncSrvParameterType()[index]);
						 incentiveConfigurationDetailVO.setDisIncSrvParameterCode(codeValue[0].trim());
						 incentiveConfigurationDetailVO.setDisIncSrvParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncSrvParameterType(mailPerformanceForm.getDisIncSrvParameterType()[index]);
						 if(excFlag[j]!=null && (excFlag[j].length()>0)){
							incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
							incentiveConfigurationDetailVO.setSrvExcludeFlag(excFlag[j]);
						}

						newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
					 }
					}
					if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
						incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
					}
					newIncentiveVOs.add(incentiveConfigurationVO);
					
				}
			}else{
				int count = 0;
				if(incentiveVOs != null && incentiveVOs.size()>0){
					for(IncentiveConfigurationVO incentiveConfigurationVO : incentiveVOs){
						if("N".equals(incentiveConfigurationVO.getIncentiveFlag())
								&& "Y".equals(incentiveConfigurationVO.getServiceRespFlag())){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
								if(mailPerformanceForm.getSrvFormula()[index]!= null 
										&& mailPerformanceForm.getSrvFormula()[index].trim().length()>0){
									if(mailPerformanceForm.getSrvFormula()[index].contains("GT"))
										newFormula = mailPerformanceForm.getSrvFormula()[index].replace("GT", ">");
									if(mailPerformanceForm.getSrvFormula()[index].contains("LT")){
									
										if(newFormula!=null){
										newFormula = newFormula.replace("LT", "<");
										}else{
											newFormula = mailPerformanceForm.getSrvFormula()[index].replace("LT", "<");
										}
									}
									if(mailPerformanceForm.getSrvFormula()[index].contains("ADD")){
										if(newFormula!=null){
										newFormula = newFormula.replace("ADD", "+");
										}else{
											newFormula = mailPerformanceForm.getSrvFormula()[index].replace("ADD", "+");
										}
									}
								}
								if(newFormula!=null){
									incentiveConfigurationVO.setFormula(newFormula.trim());
									incentiveConfigurationVO.setSrvFormula(newFormula.trim());
								}else{
									incentiveConfigurationVO.setFormula(mailPerformanceForm.getSrvFormula()[index].trim());
									incentiveConfigurationVO.setSrvFormula(mailPerformanceForm.getSrvFormula()[index].trim());
								}
								//incentiveConfigurationVO.setFormula(mailPerformanceForm.getFormula()[index]);
								incentiveConfigurationVO.setBasis(mailPerformanceForm.getSrvBasis()[index]);
								incentiveConfigurationVO.setSrvBasis(mailPerformanceForm.getSrvBasis()[index]);
								
								incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncSrvPercentage()[index]);
								incentiveConfigurationVO.setDisIncSrvPercentage(mailPerformanceForm.getDisIncSrvPercentage()[index]);
								incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncSrvValidFrom()[index]);
								incentiveConfigurationVO.setDisIncSrvValidFrom(mailPerformanceForm.getDisIncSrvValidFrom()[index]);
								incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncSrvValidTo()[index]);
								incentiveConfigurationVO.setDisIncSrvValidTo(mailPerformanceForm.getDisIncSrvValidTo()[index]);
								incentiveConfigurationVO.setLastUpdatedTime(currentDate);
								incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
								incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
								//incentiveConfigurationVO.setServiceRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
								
								newIncentiveDetailVOs =
						    			new ArrayList<IncentiveConfigurationDetailVO>();
								String[] parameter = mailPerformanceForm.getDisIncSrvParameter()[index].split("\n");
								
								String[] excFlag = null;
								if(mailPerformanceForm.getSrvExcFlag()[index]!=null && mailPerformanceForm.getSrvExcFlag()[index].length()>0){
									excFlag = mailPerformanceForm.getSrvExcFlag()[index].split(",");
									
								 }
								for(int j=0;j<parameter.length;j++){
									
									String[] codeValue = parameter[j].split(":");

										if(codeValue[0].length()>0 && codeValue[1].length()>0){

										 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
				           				 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncSrvParameterType()[index]);
										 
										 incentiveConfigurationDetailVO.setDisIncSrvParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncSrvParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncSrvParameterType(mailPerformanceForm.getDisIncSrvParameterType()[index]);
										 if(excFlag[j]!=null && excFlag[j].length()>0){
												incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
												incentiveConfigurationDetailVO.setSrvExcludeFlag(excFlag[j]);
											}
											 
									newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
								}

									}
								if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
									incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
								}
								
								 if("N".equals(oprflags[index])){
									 incentiveConfigurationVO.setDisIncOperationFlags(null);
									 incentiveConfigurationVO.setDisIncSrvOperationFlags(null);
								 }else{
									 incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncSrvOperationFlags()[index]);
									 incentiveConfigurationVO.setDisIncSrvOperationFlags(mailPerformanceForm.getDisIncSrvOperationFlags()[index]);
								 }
								 newIncentiveVOs.add(incentiveConfigurationVO);
							}
						}
						count++;
					}
					}
				}
			}
    	}


    	log.exiting("SaveCommand","updateDisIncentiveVOs");

		return newIncentiveVOs;
	}

	/**
	 *
	 * 	Method		:	SaveCommand.updateNonServiceDisIncentiveVOs
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */
	private Collection<IncentiveConfigurationVO> updateNonServiceDisIncentiveVOs(
			Collection<IncentiveConfigurationVO> incentiveVOs,LogonAttributes logonAttributes,
			MailPerformanceForm mailPerformanceForm){
		log.entering("SaveCommand","updateDisIncentiveVOs");
		String[] oprflags = mailPerformanceForm.getDisIncNonSrvOperationFlags();
		
    	int size = 0;
    	if(incentiveVOs != null && incentiveVOs.size()>0){
    		for(IncentiveConfigurationVO configVO : incentiveVOs){
    			if("N".equals(configVO.getIncentiveFlag()) && "N".equals(configVO.getServiceRespFlag())){
    				size = size + 1;
    			}
    		}
    	}
    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<IncentiveConfigurationVO> newIncentiveVOs = new ArrayList<IncentiveConfigurationVO>();
    	Collection<IncentiveConfigurationDetailVO> newIncentiveDetailVOs = null;
    	for(int index=0; index<oprflags.length;index++){
    		String newFormula = null;
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					IncentiveConfigurationVO incentiveConfigurationVO = new IncentiveConfigurationVO();
					if(mailPerformanceForm.getNonSrvFormula()[index]!= null 
							&& mailPerformanceForm.getNonSrvFormula()[index].trim().length()>0){
						if(mailPerformanceForm.getNonSrvFormula()[index].contains("GT"))
							newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("GT", ">");
						if(mailPerformanceForm.getNonSrvFormula()[index].contains("LT")){
							if(newFormula!=null){
							newFormula = newFormula.replace("LT", "<");
							}else{
								newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("LT", "<");
							}
						}
						if(mailPerformanceForm.getNonSrvFormula()[index].contains("ADD")){
							if(newFormula!=null){
							newFormula = newFormula.replace("ADD", "+");
							}else{
								newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("ADD", "+");
							}
						}
					}
					incentiveConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
					incentiveConfigurationVO.setPaCode(mailPerformanceForm.getIncPaCode());
					if(newFormula!=null){
					incentiveConfigurationVO.setFormula(newFormula.trim());
						incentiveConfigurationVO.setNonSrvFormula(newFormula.trim());
					}else{
						incentiveConfigurationVO.setFormula(mailPerformanceForm.getNonSrvFormula()[index].trim());
						incentiveConfigurationVO.setNonSrvFormula(mailPerformanceForm.getNonSrvFormula()[index].trim());
					}
					incentiveConfigurationVO.setBasis(mailPerformanceForm.getNonSrvBasis()[index]);
					incentiveConfigurationVO.setNonSrvBasis(mailPerformanceForm.getNonSrvBasis()[index]);
					incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncNonSrvPercentage()[index]);
					incentiveConfigurationVO.setDisIncNonSrvPercentage(mailPerformanceForm.getDisIncNonSrvPercentage()[index]);
					incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncNonSrvValidFrom()[index]);
					incentiveConfigurationVO.setDisIncNonSrvValidFrom(mailPerformanceForm.getDisIncNonSrvValidFrom()[index]);
					incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncNonSrvValidTo()[index]);
					incentiveConfigurationVO.setDisIncNonSrvValidTo(mailPerformanceForm.getDisIncNonSrvValidTo()[index]);
					incentiveConfigurationVO.setLastUpdatedTime(currentDate);
					incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
					incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncNonSrvOperationFlags()[index]);
					incentiveConfigurationVO.setDisIncNonSrvOperationFlags(mailPerformanceForm.getDisIncNonSrvOperationFlags()[index]);
					incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
					incentiveConfigurationVO.setServiceRespFlag(NON_SERVICE_RESPONSIVE);
					newIncentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
					String[] parameter = null;
					if(mailPerformanceForm.getDisIncNonSrvParameter()[index].length() > 0){
						String param = mailPerformanceForm.getDisIncNonSrvParameter()[index].concat("\r\n")	;					
					 parameter = param.split("\n");
					}
					else{
						parameter = mailPerformanceForm.getDisIncNonSrvParameter()[index].split("\n");
					}
					String[] excFlag = null;
					if(mailPerformanceForm.getNonSrvExcFlag()[index]!=null 
							&& mailPerformanceForm.getNonSrvExcFlag()[index].length()>0){
						String flag = mailPerformanceForm.getNonSrvExcFlag()[index];
						//flag = flag.substring(1);
						//flag = flag.substring(0,flag.length()-1);
						excFlag = flag.split(",");
					 }
					for(int j=0;j<parameter.length;j++){
						String[] codeValue = parameter[j].split(":");
						 if(codeValue[0].length()>0  && codeValue[1].length()>0){
						 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
           				 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
           				 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncNonSrvParameterType()[index]);
						 incentiveConfigurationDetailVO.setDisIncNonSrvParameterCode(codeValue[0].trim());
						 incentiveConfigurationDetailVO.setDisIncNonSrvParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncNonSrvParameterType(mailPerformanceForm.getDisIncNonSrvParameterType()[index]);
						 if(!"".equals(excFlag[j])&&excFlag[j]!=null && (excFlag[j].trim().length()>0)){
							incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
							incentiveConfigurationDetailVO.setNonSrvExcludeFlag(excFlag[j]);
						}
						newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
					 }
					}
					if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
						incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
					}
					newIncentiveVOs.add(incentiveConfigurationVO);
				}
			}else{
				int count = 0;
				if(incentiveVOs != null && incentiveVOs.size()>0){
					for(IncentiveConfigurationVO incentiveConfigurationVO : incentiveVOs){
						if("N".equals(incentiveConfigurationVO.getIncentiveFlag())
								&& "N".equals(incentiveConfigurationVO.getServiceRespFlag())){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
								if(mailPerformanceForm.getNonSrvFormula()[index]!= null 
										&& mailPerformanceForm.getNonSrvFormula()[index].trim().length()>0){
									if(mailPerformanceForm.getNonSrvFormula()[index].contains("GT"))
										newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("GT", ">");
									if(mailPerformanceForm.getNonSrvFormula()[index].contains("LT")){
										if(newFormula!=null){
										newFormula = newFormula.replace("LT", "<");
										}else{
											newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("LT", "<");
										}
									}
									if(mailPerformanceForm.getNonSrvFormula()[index].contains("ADD")){
										if(newFormula!=null){
										newFormula = newFormula.replace("ADD", "+");
										}else{
											newFormula = mailPerformanceForm.getNonSrvFormula()[index].replace("ADD", "+");
										}
									}
								}
								if(newFormula!=null){
									incentiveConfigurationVO.setFormula(newFormula.trim());
									incentiveConfigurationVO.setNonSrvFormula(newFormula.trim());
								}else{
									incentiveConfigurationVO.setFormula(mailPerformanceForm.getNonSrvFormula()[index].trim());
									incentiveConfigurationVO.setNonSrvFormula(mailPerformanceForm.getNonSrvFormula()[index].trim());
								}
								//incentiveConfigurationVO.setFormula(mailPerformanceForm.getFormula()[index]);
								incentiveConfigurationVO.setBasis(mailPerformanceForm.getNonSrvBasis()[index]);
								incentiveConfigurationVO.setNonSrvBasis(mailPerformanceForm.getNonSrvBasis()[index]);
								
								incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncNonSrvPercentage()[index]);
								incentiveConfigurationVO.setDisIncNonSrvPercentage(mailPerformanceForm.getDisIncNonSrvPercentage()[index]);
								incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncNonSrvValidFrom()[index]);
								incentiveConfigurationVO.setDisIncNonSrvValidFrom(mailPerformanceForm.getDisIncNonSrvValidFrom()[index]);
								incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncNonSrvValidTo()[index]);
								incentiveConfigurationVO.setDisIncNonSrvValidTo(mailPerformanceForm.getDisIncNonSrvValidTo()[index]);
								incentiveConfigurationVO.setLastUpdatedTime(currentDate);
								incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
								incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
								//incentiveConfigurationVO.setServiceRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
								newIncentiveDetailVOs =
						    			new ArrayList<IncentiveConfigurationDetailVO>();
								String[] parameter = mailPerformanceForm.getDisIncNonSrvParameter()[index].split("\n");
								String[] excFlag = null;
								if(mailPerformanceForm.getNonSrvExcFlag()[index]!=null && mailPerformanceForm.getNonSrvExcFlag()[index].length()>0){
									String flag = mailPerformanceForm.getNonSrvExcFlag()[index];
									flag = flag.substring(1);
									excFlag = flag.split(",");
									
								 }
								for(int j=0;j<parameter.length;j++){
									String[] codeValue = parameter[j].split(":");
										if(codeValue[0].length()>0 && codeValue[1].length()>0){
										 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
				           				 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncNonSrvParameterType()[index]);
										 incentiveConfigurationDetailVO.setDisIncNonSrvParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncNonSrvParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncNonSrvParameterType(mailPerformanceForm.getDisIncNonSrvParameterType()[index]);
										 if(excFlag[j]!=null && excFlag[j].length()>0){
												incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
												incentiveConfigurationDetailVO.setNonSrvExcludeFlag(excFlag[j]);
											}
									newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
								}
									}
								if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
									incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
								}
								 if("N".equals(oprflags[index])){
									 incentiveConfigurationVO.setDisIncOperationFlags(null);
									 incentiveConfigurationVO.setDisIncNonSrvOperationFlags(null);
								 }else{
									 incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncNonSrvOperationFlags()[index]);
									 incentiveConfigurationVO.setDisIncNonSrvOperationFlags(mailPerformanceForm.getDisIncNonSrvOperationFlags()[index]);
								 }
								 newIncentiveVOs.add(incentiveConfigurationVO);
							}
						}
						count++;
					}
					}
				}
			}
    	}
    	log.exiting("SaveCommand","updateDisIncentiveVOs");
		return newIncentiveVOs;
	}
	/**
	 *
	 * 	Method		:	SaveCommand.updateBothServiceDisIncentiveVOs
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */
	private Collection<IncentiveConfigurationVO> updateBothServiceDisIncentiveVOs(
			Collection<IncentiveConfigurationVO> incentiveVOs,LogonAttributes logonAttributes,
			MailPerformanceForm mailPerformanceForm){
		log.entering("SaveCommand","updateDisIncentiveVOs");
		
		String[] oprflags = mailPerformanceForm.getDisIncBothSrvOperationFlags();
    	int size = 0;
    	if(incentiveVOs != null && incentiveVOs.size()>0){
    		for(IncentiveConfigurationVO configVO : incentiveVOs){
    			if("N".equals(configVO.getIncentiveFlag()) && "B".equals(configVO.getServiceRespFlag())){
    				size = size + 1;
    			}
    		}
    	}
    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<IncentiveConfigurationVO> newIncentiveVOs = new ArrayList<IncentiveConfigurationVO>();
    	Collection<IncentiveConfigurationDetailVO> newIncentiveDetailVOs = null;
    	for(int index=0; index<oprflags.length;index++){
    		String newFormula = null;
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					IncentiveConfigurationVO incentiveConfigurationVO = new IncentiveConfigurationVO();
					if(mailPerformanceForm.getBothSrvFormula()[index]!= null 
							&& mailPerformanceForm.getBothSrvFormula()[index].trim().length()>0){
						if(mailPerformanceForm.getBothSrvFormula()[index].contains("GT"))
							newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("GT", ">");
						if(mailPerformanceForm.getBothSrvFormula()[index].contains("LT")){
							if(newFormula!=null){
							newFormula = newFormula.replace("LT", "<");
							}else{
								newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("LT", "<");
							}
						}
						if(mailPerformanceForm.getBothSrvFormula()[index].contains("ADD")){
							if(newFormula!=null){
							newFormula = newFormula.replace("ADD", "+");
							}else{
								newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("ADD", "+");
							}
						}
					}
					incentiveConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
					incentiveConfigurationVO.setPaCode(mailPerformanceForm.getIncPaCode());
					if(newFormula!=null){
						incentiveConfigurationVO.setFormula(newFormula.trim());
						incentiveConfigurationVO.setBothSrvFormula(newFormula.trim());
					}else{
						incentiveConfigurationVO.setFormula(mailPerformanceForm.getBothSrvFormula()[index].trim());
						incentiveConfigurationVO.setBothSrvFormula(mailPerformanceForm.getBothSrvFormula()[index].trim());
					}
					incentiveConfigurationVO.setBasis(mailPerformanceForm.getBothSrvBasis()[index]);
					incentiveConfigurationVO.setBothSrvBasis(mailPerformanceForm.getBothSrvBasis()[index]);
					
					incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncBothSrvPercentage()[index]);
					incentiveConfigurationVO.setDisIncBothSrvPercentage(mailPerformanceForm.getDisIncBothSrvPercentage()[index]);
					
					incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncBothSrvValidFrom()[index]);
					incentiveConfigurationVO.setDisIncBothSrvValidFrom(mailPerformanceForm.getDisIncBothSrvValidFrom()[index]);
					
					incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncBothSrvValidTo()[index]);
					incentiveConfigurationVO.setDisIncBothSrvValidTo(mailPerformanceForm.getDisIncBothSrvValidTo()[index]);
					
					incentiveConfigurationVO.setLastUpdatedTime(currentDate);
					incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
					incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncBothSrvOperationFlags()[index]);
					incentiveConfigurationVO.setDisIncBothSrvOperationFlags(mailPerformanceForm.getDisIncBothSrvOperationFlags()[index]);
					incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
					incentiveConfigurationVO.setServiceRespFlag(BOTH);
					newIncentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
					String[] parameter = null;
					if(mailPerformanceForm.getDisIncBothSrvParameter()[index].length() > 0){
						String param = mailPerformanceForm.getDisIncBothSrvParameter()[index].concat("\r\n")	;					
					 parameter = param.split("\n");
					}
					else{
						parameter = mailPerformanceForm.getDisIncBothSrvParameter()[index].split("\n");
					}
					String[] excFlag = null;
					if(mailPerformanceForm.getBothSrvExcFlag()[index]!=null 
							&& mailPerformanceForm.getBothSrvExcFlag()[index].length()>0){
						excFlag = mailPerformanceForm.getBothSrvExcFlag()[index].split(",");
					 }
					for(int j=0;j<parameter.length;j++){
						String[] codeValue = parameter[j].split(":");
						 if(codeValue[0].length()>0  && codeValue[1].length()>0){
						 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
						 
						 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
						 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncBothSrvParameterType()[index]);

						 incentiveConfigurationDetailVO.setDisIncBothSrvParameterCode(codeValue[0].trim());
						 incentiveConfigurationDetailVO.setDisIncBothSrvParameterValue(codeValue[1].trim());
						 incentiveConfigurationDetailVO.setDisIncBothSrvParameterType(mailPerformanceForm.getDisIncBothSrvParameterType()[index]);
						 if(excFlag[j]!=null && (excFlag[j].length()>0)){
						 incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
						 incentiveConfigurationDetailVO.setBothSrvExcludeFlag(excFlag[j]);
						 }
						newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
					 }
					}
					if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
						incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
					}
					newIncentiveVOs.add(incentiveConfigurationVO);
				}
			}else{
				int count = 0;
				if(incentiveVOs != null && incentiveVOs.size()>0){
					for(IncentiveConfigurationVO incentiveConfigurationVO : incentiveVOs){
						if("N".equals(incentiveConfigurationVO.getIncentiveFlag())
								&& "B".equals(incentiveConfigurationVO.getServiceRespFlag())){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
								if(mailPerformanceForm.getBothSrvFormula()[index]!= null 
										&& mailPerformanceForm.getBothSrvFormula()[index].trim().length()>0){
									if(mailPerformanceForm.getBothSrvFormula()[index].contains("GT"))
										newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("GT", ">");
									if(mailPerformanceForm.getBothSrvFormula()[index].contains("LT")){
										if(newFormula!=null){
											newFormula = newFormula.replace("LT", "<");
										}else{
											newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("LT", "<");
										}
									}
									if(mailPerformanceForm.getBothSrvFormula()[index].contains("ADD")){
										if(newFormula!=null){
										newFormula = newFormula.replace("ADD", "+");
										}else{
											newFormula = mailPerformanceForm.getBothSrvFormula()[index].replace("ADD", "+");
										}
									}
								}
								if(newFormula!=null){
									incentiveConfigurationVO.setFormula(newFormula.trim());
									incentiveConfigurationVO.setBothSrvFormula(newFormula.trim());
								}else{
									incentiveConfigurationVO.setFormula(mailPerformanceForm.getBothSrvFormula()[index].trim());
									incentiveConfigurationVO.setBothSrvFormula(mailPerformanceForm.getBothSrvFormula()[index].trim());
								}
								//incentiveConfigurationVO.setFormula(mailPerformanceForm.getFormula()[index]);
								incentiveConfigurationVO.setBasis(mailPerformanceForm.getBothSrvBasis()[index]);
								incentiveConfigurationVO.setBothSrvBasis(mailPerformanceForm.getBothSrvBasis()[index]);
								
								incentiveConfigurationVO.setDisIncPercentage(mailPerformanceForm.getDisIncBothSrvPercentage()[index]);
								incentiveConfigurationVO.setDisIncBothSrvPercentage(mailPerformanceForm.getDisIncBothSrvPercentage()[index]);
								
								incentiveConfigurationVO.setDisIncValidFrom(mailPerformanceForm.getDisIncBothSrvValidFrom()[index]);
								incentiveConfigurationVO.setDisIncBothSrvValidFrom(mailPerformanceForm.getDisIncBothSrvValidFrom()[index]);
								
								incentiveConfigurationVO.setDisIncValidTo(mailPerformanceForm.getDisIncBothSrvValidTo()[index]);
								incentiveConfigurationVO.setDisIncBothSrvValidTo(mailPerformanceForm.getDisIncBothSrvValidTo()[index]);
								
								incentiveConfigurationVO.setLastUpdatedTime(currentDate);
								incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
								incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
								
								//incentiveConfigurationVO.setServiceRespFlag(mailPerformanceForm.getServiceResponsiveFlag());
								newIncentiveDetailVOs =
						    			new ArrayList<IncentiveConfigurationDetailVO>();
								String[] parameter = mailPerformanceForm.getDisIncBothSrvParameter()[index].split("\n");
								String[] excFlag = null;
								if(mailPerformanceForm.getBothSrvExcFlag()[index]!=null && mailPerformanceForm.getBothSrvExcFlag()[index].length()>0){
									excFlag = mailPerformanceForm.getBothSrvExcFlag()[index].split(",");
								 }
								for(int j=0;j<parameter.length;j++){
									String[] codeValue = parameter[j].split(":");
										if(codeValue[0].length()>0 && codeValue[1].length()>0){
										 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
				           				 incentiveConfigurationDetailVO.setDisIncParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncParameterType(mailPerformanceForm.getDisIncBothSrvParameterType()[index]);
										 
										 incentiveConfigurationDetailVO.setDisIncBothSrvParameterCode(codeValue[0].trim());
				           				 incentiveConfigurationDetailVO.setDisIncBothSrvParameterValue(codeValue[1].trim());
										 incentiveConfigurationDetailVO.setDisIncBothSrvParameterType(mailPerformanceForm.getDisIncBothSrvParameterType()[index]);
										 if(excFlag[j]!=null && excFlag[j].length()>0){
												incentiveConfigurationDetailVO.setExcludeFlag(excFlag[j]);
												incentiveConfigurationDetailVO.setBothSrvExcludeFlag(excFlag[j]);
											}
									newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
								}
									}
								if(newIncentiveDetailVOs != null && newIncentiveDetailVOs.size()>0){
									incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);
								}
								 if("N".equals(oprflags[index])){
									 incentiveConfigurationVO.setDisIncOperationFlags(null);
									 incentiveConfigurationVO.setDisIncBothSrvOperationFlags(null);
								 }else{
									 incentiveConfigurationVO.setDisIncOperationFlags(mailPerformanceForm.getDisIncBothSrvOperationFlags()[index]);
									 incentiveConfigurationVO.setDisIncBothSrvOperationFlags(mailPerformanceForm.getDisIncBothSrvOperationFlags()[index]);
								 }
								 newIncentiveVOs.add(incentiveConfigurationVO);
							}
						}
						count++;
					}
					}
				}
			}
    	}
    	log.exiting("SaveCommand","updateDisIncentiveVOs");
		return newIncentiveVOs;
	}


	/**
	 *
	 * 	Method		:	SaveCommand.updateIncentiveVOs
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */

	private Collection<IncentiveConfigurationVO> updateIncentiveVOs(
			Collection<IncentiveConfigurationVO> incentiveVOs,LogonAttributes logonAttributes,
			MailPerformanceForm mailPerformanceForm){

		log.entering("SaveCommand","updateIncentiveVOs");
		

		String[] oprflags = mailPerformanceForm.getIncOperationFlags();

    	int size = 0;
    	int paramCount = 0;
    	
    	if(incentiveVOs != null && incentiveVOs.size()>0){
			for(IncentiveConfigurationVO configVO : incentiveVOs){
				if("Y".equals(configVO.getIncentiveFlag())){
					size = size + 1;
				}
			}
		}

    	LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	Collection<IncentiveConfigurationVO> newIncentiveVOs = new ArrayList<IncentiveConfigurationVO>();
    	Collection<IncentiveConfigurationDetailVO> newIncentiveDetailVOs = null;


    	for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					
					paramCount= paramCount+2;
					
					IncentiveConfigurationVO incentiveConfigurationVO = new IncentiveConfigurationVO();
				
					incentiveConfigurationVO.setCompanyCode(logonAttributes.getCompanyCode());
					incentiveConfigurationVO.setPaCode(mailPerformanceForm.getIncPaCode());
					incentiveConfigurationVO.setIncPercentage(mailPerformanceForm.getIncPercentage()[index]);
					incentiveConfigurationVO.setIncValidFrom(mailPerformanceForm.getIncValidFrom()[index]);
					incentiveConfigurationVO.setIncValidTo(mailPerformanceForm.getIncValidTo()[index]);
					incentiveConfigurationVO.setLastUpdatedTime(currentDate);
					incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
					incentiveConfigurationVO.setIncOperationFlags(mailPerformanceForm.getIncOperationFlags()[index]);
					incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());
					
					newIncentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();

					for(int j=paramCount-2;j<paramCount;j++){

				

						 IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
						 incentiveConfigurationDetailVO.setIncParameterCode(mailPerformanceForm.getIncParameterCode()[j]);
						 incentiveConfigurationDetailVO.setIncParameterType(mailPerformanceForm.getIncParameterType()[j]);
						 incentiveConfigurationDetailVO.setIncParameterValue(mailPerformanceForm.getIncParameterValue()[j]);
						
						newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);
				
					}
					incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);

					

					newIncentiveVOs.add(incentiveConfigurationVO);


				}
			}else{
				
				int count = 0;
				if(incentiveVOs != null && incentiveVOs.size()>0){
					for(IncentiveConfigurationVO incentiveConfigurationVO : incentiveVOs){
						if("Y".equals(incentiveConfigurationVO.getIncentiveFlag())){
						if(count == index){
							if(!"NOOP".equals(oprflags[index])){
							
								paramCount= paramCount+2;

								incentiveConfigurationVO.setIncPercentage(mailPerformanceForm.getIncPercentage()[index]);
								incentiveConfigurationVO.setIncValidFrom(mailPerformanceForm.getIncValidFrom()[index]);
								incentiveConfigurationVO.setIncValidTo(mailPerformanceForm.getIncValidTo()[index]);
								incentiveConfigurationVO.setLastUpdatedTime(currentDate);
								incentiveConfigurationVO.setLastUpdatedUser(logonAttributes.getUserId());
								incentiveConfigurationVO.setIncentiveFlag(mailPerformanceForm.getIncFlag());

								newIncentiveDetailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
						    			
								for(int j=paramCount-2;j<paramCount;j++){

									IncentiveConfigurationDetailVO incentiveConfigurationDetailVO = new IncentiveConfigurationDetailVO();
									
									incentiveConfigurationDetailVO.setIncParameterCode(mailPerformanceForm.getIncParameterCode()[j]);
									incentiveConfigurationDetailVO.setIncParameterType(mailPerformanceForm.getIncParameterType()[j]);
									incentiveConfigurationDetailVO.setIncParameterValue(mailPerformanceForm.getIncParameterValue()[j]);

									newIncentiveDetailVOs.add(incentiveConfigurationDetailVO);

								}incentiveConfigurationVO.setIncentiveConfigurationDetailVOs(newIncentiveDetailVOs);

								 if("N".equals(oprflags[index])){
									 incentiveConfigurationVO.setIncOperationFlags(null);
								 }else{
									 incentiveConfigurationVO.setIncOperationFlags(mailPerformanceForm.getIncOperationFlags()[index]);
								 }

								 newIncentiveVOs.add(incentiveConfigurationVO);
							}
						}
						count++;
					}}
				}
			}
    	}
    	log.exiting("SaveCommand","updateIncentiveVOs");
		return newIncentiveVOs;
	}
	
	public String findSystemParameterValue(String syspar) throws BusinessDelegateException{
		
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
		}
	/**
	 *
	 * 	Method		:	SaveCommand.validateIncentiveConfiguration
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */
	private Collection<ErrorVO> validateIncentiveConfiguration(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs,
			IncentiveConfigurationVO incentiveVO,int count){
		log.log(Log.FINE, " Inside IncentiveConfiguration Validation ");
		ErrorVO error = null;
	    Object[] errorCodes = new Object[3];
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    int parameterExists = 0;
	    int index = 0;
		for(IncentiveConfigurationVO incentiveConfigVO : incentiveConfigurationVOs){
			if(index!=count){
				/*if ((incentiveConfigVO.getIncValidFrom().toUpperCase().equals(incentiveVO.getIncValidFrom().toUpperCase())) &&
						(incentiveConfigVO.getIncValidTo().toUpperCase().equals(incentiveVO.getIncValidTo().toUpperCase()))) {*/
				if((incentiveConfigVO.getIncValidFrom()!=null && incentiveConfigVO.getIncValidFrom().trim().length()>0)
					&& (incentiveConfigVO.getIncValidTo()!=null && incentiveConfigVO.getIncValidTo().trim().length()>0)
					&& (incentiveVO.getIncValidFrom()!=null && incentiveVO.getIncValidFrom().trim().length()>0)
					&& (incentiveVO.getIncValidTo()!= null && incentiveVO.getIncValidTo().trim().length()>0)){
				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                fromDate.setDate(incentiveConfigVO.getIncValidFrom().toUpperCase());
                LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                toDate.setDate(incentiveConfigVO.getIncValidTo().toUpperCase());
            	LocalDate fromDate1 = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
            	 fromDate1.setDate(incentiveVO.getIncValidFrom().toUpperCase());
                LocalDate toDate1 = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                toDate1.setDate(incentiveVO.getIncValidTo().toUpperCase());
                boolean dateCheck=false;
				//if(fromDate.equals(fromDate1)||toDate.equals(toDate1) ){
				dateCheck=isWithinRange(fromDate,toDate,fromDate1,toDate1);
				if (dateCheck==(true)) {
					int duplicateCount = 0;
					int slabCount = 0;
					String prevPercFrom = null;
					String prevPercTo = null;
					//Modified for ICRD-313985
					 for (IncentiveConfigurationDetailVO detailVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
						 if(PERCENTAGE_FROM.equals(detailVO.getIncParameterCode())){
			            		prevPercFrom = detailVO.getIncParameterValue();
			            	}
			            	if(PERCENTAGE_TO.equals(detailVO.getIncParameterCode())){
			            		prevPercTo = detailVO.getIncParameterValue();
			            	}
				            for (IncentiveConfigurationDetailVO incDetailVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
				            	
				            	 if ((PERCENTAGE_FROM.equals(incDetailVO.getIncParameterCode())) ){
				            		 if((prevPercTo !=null && !("".equals(prevPercTo)))&&(prevPercFrom!=null && !("".equals(prevPercFrom))) ){
				            			 if( Double.parseDouble(prevPercFrom) > Double.parseDouble(prevPercTo)){
						            		 if((Double.parseDouble(prevPercFrom))>=(Double.parseDouble(incDetailVO.getIncParameterValue()))){
						            			 slabCount++;
						            		 }
				            			 }else{
				            				 if( Double.parseDouble(prevPercFrom)<=(Double.parseDouble(incDetailVO.getIncParameterValue()))){
					            			  	slabCount++;
							            	}
				            			 }
				            	  }
				              }else if ((PERCENTAGE_TO.equals(incDetailVO.getIncParameterCode()))){
				            	  if((prevPercTo !=null && !("".equals(prevPercTo)))&&(prevPercFrom!=null && !("".equals(prevPercFrom)))){
				            		  if(Double.parseDouble(prevPercFrom) > Double.parseDouble(prevPercTo)){
				            			  if( (Double.parseDouble(prevPercTo))<=(Double.parseDouble((incDetailVO.getIncParameterValue())))) {
							            	  slabCount++;
							            }
				            		  } else{
				            			  	if((Double.parseDouble(prevPercTo))>=Double.parseDouble((incDetailVO.getIncParameterValue()))) {
				            			  		slabCount++;
						            		 }
				            		  }
				            	  }
				            } if ((detailVO.getIncParameterCode().equals(incDetailVO.getIncParameterCode()))
				            		  && (detailVO.getIncParameterValue().equals(incDetailVO.getIncParameterValue()))
				            		  && (detailVO.getIncParameterType().equals(incDetailVO.getIncParameterType()))) {
				            	  duplicateCount++;
				              }
				          }
				            		  
				          // }
				          }
					 if(duplicateCount == 2){
						error = new ErrorVO(SAME_CONFIGURATION_EXISTS);
			            errorCodes[0] = incentiveConfigVO.getIncValidFrom();
			            errorCodes[1] = incentiveConfigVO.getIncValidTo();
			            error.setErrorData(errorCodes);
					 }else if(slabCount == 2){
						error = new ErrorVO(PERCENTAGE_SLABS_OVERLAPPED);
					 }
				}
				}
				for (IncentiveConfigurationDetailVO detailVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
		            for (IncentiveConfigurationDetailVO incDetailVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
		              if ((detailVO.getIncParameterCode().equals(incDetailVO.getIncParameterCode()))
		            		  && (detailVO.getIncParameterValue().equals(incDetailVO.getIncParameterValue()))
		            		  && (detailVO.getIncParameterType().equals(incDetailVO.getIncParameterType()))) {
		            	  		parameterExists++;
		              }
		            }
		          }
		          if ((parameterExists == 2) && (incentiveConfigVO.getIncPercentage().equals(incentiveVO.getIncPercentage()))
		        		  && (incentiveConfigVO.getIncValidFrom().equals(incentiveVO.getIncValidFrom()))
		        		  && (incentiveConfigVO.getIncValidTo().equals(incentiveVO.getIncValidTo())))   {
		        	  		error = new ErrorVO(DUPLICATE_RECORD);
		          }
			}
			index++;
			
			
		}
		 if(error != null){
       	  errors.add(error);
         }
		 return errors;
		}
	
	/**
	 *
	 * 	Method		:	SaveCommand.validateDisIncentiveConfiguration
	 *	Parameters	:	@param incentiveVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return
	 *	Return type	: 	ArrayList<IncentiveConfigurationVO>
	 */
	private Collection<ErrorVO> validateDisIncentiveConfiguration(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs,
			IncentiveConfigurationVO incentiveVO,int count){
		
		log.log(Log.FINE, " Inside DisIncentiveConfiguration Validation ");
		ErrorVO error = null;
	    Object[] errorCodes = new Object[3];
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    int parameterExists = 0;
	    int index = 0;
	    String incentiveVOParameterCode = "";
		for (IncentiveConfigurationDetailVO IncDetailVOParameterValue : incentiveVO.getIncentiveConfigurationDetailVOs()) {
			incentiveVOParameterCode =incentiveVOParameterCode.concat(IncDetailVOParameterValue.getDisIncParameterCode()).concat(IncDetailVOParameterValue.getDisIncParameterValue());
		}
	    StringBuilder incentiveKey =null;
	    StringBuilder incentiveKeyToCompare =null;
	   
	    if(BOTH.equals(incentiveVO.getServiceRespFlag())){
	    incentiveKey=new StringBuilder().append(incentiveVO.getBothSrvFormula()).append(incentiveVO.getBothSrvBasis());
	    for (IncentiveConfigurationDetailVO confDetVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
	    	incentiveKey.append(confDetVO.getDisIncBothSrvParameterCode()).append(confDetVO.getDisIncBothSrvParameterType()).append(confDetVO.getDisIncBothSrvParameterValue()) ;  
	    }
	    }
	    else if(NON_SERVICE_RESPONSIVE.equals(incentiveVO.getServiceRespFlag())){
	    incentiveKey=new StringBuilder().append(incentiveVO.getNonSrvFormula()).append(incentiveVO.getNonSrvBasis());
	    for (IncentiveConfigurationDetailVO confDetVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
	    	incentiveKey.append(confDetVO.getDisIncNonSrvParameterCode()).append(confDetVO.getDisIncNonSrvParameterType()).append(confDetVO.getDisIncNonSrvParameterValue()) ;  
	    }
	    }else{
		    incentiveKey=new StringBuilder().append(incentiveVO.getSrvFormula()).append(incentiveVO.getSrvBasis());
		    for (IncentiveConfigurationDetailVO confDetVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
		    	incentiveKey.append(confDetVO.getDisIncSrvParameterCode()).append(confDetVO.getDisIncSrvParameterType()).append(confDetVO.getDisIncSrvParameterValue()) ;  
		    }
	    }
		for(IncentiveConfigurationVO incentiveConfigVO : incentiveConfigurationVOs){
			String incentiveConfigVOParameterCode = "";
			for (IncentiveConfigurationDetailVO IncConfigDetailVOParameterValue : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
				incentiveConfigVOParameterCode = incentiveConfigVOParameterCode.concat(IncConfigDetailVOParameterValue.getDisIncParameterCode()).concat(IncConfigDetailVOParameterValue.getDisIncParameterValue());
			}
			if(incentiveConfigVO.getServiceRespFlag().equals(incentiveVO.getServiceRespFlag())){
			if(index!=count){
				/*if(incentiveConfigVO.getFormula().toUpperCase().equals(incentiveVO.getFormula().toUpperCase()))
				{*/
				if((incentiveConfigVO.getDisIncValidFrom()!= null && incentiveConfigVO.getDisIncValidFrom().trim().length()>0)
					&& (incentiveConfigVO.getDisIncValidTo()!=null && incentiveConfigVO.getDisIncValidTo().trim().length()>0)
					&& (incentiveVO.getDisIncValidFrom()!=null && incentiveVO.getDisIncValidFrom().trim().length()>0)
					&& (incentiveVO.getDisIncValidTo()!=null && incentiveVO.getDisIncValidTo().trim().length()>0)){
				
				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                fromDate.setDate(incentiveConfigVO.getDisIncValidFrom().toUpperCase());
                LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                toDate.setDate(incentiveConfigVO.getDisIncValidTo().toUpperCase());
            	LocalDate fromDate1 = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
            	 fromDate1.setDate(incentiveVO.getDisIncValidFrom().toUpperCase());
                LocalDate toDate1 = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
                toDate1.setDate(incentiveVO.getDisIncValidTo().toUpperCase());
				
				
                boolean dateCheck=false;
				//if(fromDate.equals(fromDate1)||toDate.equals(toDate1) ){
				dateCheck=isWithinRange(fromDate,toDate,fromDate1,toDate1);
			//	}
				if (dateCheck==(true)) {
				
					 if(BOTH.equals(incentiveVO.getServiceRespFlag())){
							incentiveKeyToCompare=new StringBuilder().append(incentiveConfigVO.getBothSrvFormula()).append(incentiveConfigVO.getBothSrvBasis());
						    for (IncentiveConfigurationDetailVO confDetVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
						    	incentiveKeyToCompare.append(confDetVO.getDisIncBothSrvParameterCode()).append(confDetVO.getDisIncBothSrvParameterType()).append(confDetVO.getDisIncBothSrvParameterValue()) ;  
						    }
					 }
					 else if(NON_SERVICE_RESPONSIVE.equals(incentiveVO.getServiceRespFlag())){
							incentiveKeyToCompare=new StringBuilder().append(incentiveConfigVO.getNonSrvFormula()).append(incentiveConfigVO.getNonSrvBasis());
						    for (IncentiveConfigurationDetailVO confDetVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
						    	incentiveKeyToCompare.append(confDetVO.getDisIncNonSrvParameterCode()).append(confDetVO.getDisIncNonSrvParameterType()).append(confDetVO.getDisIncNonSrvParameterValue()) ;  
						    }
					 }
					 else{
							incentiveKeyToCompare=new StringBuilder().append(incentiveConfigVO.getSrvFormula()).append(incentiveConfigVO.getSrvBasis());
						    for (IncentiveConfigurationDetailVO confDetVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
						    	incentiveKeyToCompare.append(confDetVO.getDisIncSrvParameterCode()).append(confDetVO.getDisIncSrvParameterType()).append(confDetVO.getDisIncSrvParameterValue()) ;  
						    }
					 }
					 
					 
				    if(incentiveKeyToCompare.toString().equals(incentiveKey.toString())){//added for ICRD-322316
				    	 error = new ErrorVO(DUPLICATE_RECORD,new Object[]{""});
				    }
				    
				    
		        //Modified for ICRD-311126 starts
			        	  	/*error = new ErrorVO(DATE_RANGE_OVERLAPPING);
			            errorCodes[0] = incentiveConfigurationVO.getDisIncValidFrom();
			            errorCodes[1] = incentiveConfigurationVO.getDisIncValidTo();
			            error.setErrorData(errorCodes);*/
			            
			            //Modified for ICRD-309247 starts
			            if (incentiveConfigVO.getDisIncPercentage().equals(incentiveVO.getDisIncPercentage())){//Same formula with same percent
			            	
			            	
				            /*error = new ErrorVO(FORMULA_ALREADY_EXISTS_WITH_DIFF_PERCENTAGE);
				            errorCodes[0] = incentiveConfigurationVO.getFormula().replace("~", "");
				            error.setErrorData(errorCodes);*/
		        	   
				            for (IncentiveConfigurationDetailVO detailVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
					            for (IncentiveConfigurationDetailVO incDetailVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
					            	
					            	if(INCENTIVEPRODUCTPARAMETER.equals(incDetailVO.getDisIncParameterCode())
					            			&& detailVO.getDisIncParameterValue().equals(incDetailVO.getDisIncParameterValue())	&& (incentiveVOParameterCode.equals(incentiveConfigVOParameterCode))
											&& incentiveConfigVO.getFormula().equals(incentiveVO.getFormula())){
					            		
					            		
					            		Map<String, Collection<OneTimeVO>> oneTimes = null;
										oneTimes =  findOneTimeDescription(incentiveVO.getCompanyCode(), MAILSERVICELEVELS);
										
										Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
										oneTimeVOs= oneTimes.get(MAILSERVICELEVELS);
										String serviceLevel = null;
										
										for(OneTimeVO oneTimeVO: oneTimeVOs){

											if(detailVO.getDisIncParameterValue().equals(oneTimeVO.getFieldValue())){
												serviceLevel = oneTimeVO.getFieldDescription();
												break;
											}
										}
					            		 error = new ErrorVO(FORMULA_ALREADY_EXISTS_WITH_SAME_PERCENTAGE_FOR_SAME_PRODUCT);
								           // errorCodes[0] = incentiveVO.getFormula().replace("~", "");
								            errorCodes[0] = serviceLevel;
								            error.setErrorData(errorCodes);
					            	}
					            }
				            }
				            
			           } else{ //diff percent
			        	   
			        	   if (incentiveConfigVO.getFormula().equals(incentiveVO.getFormula())){//Same formula with diff percent
					        		
			        		   for (IncentiveConfigurationDetailVO detailVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
						            for (IncentiveConfigurationDetailVO incDetailVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
						            	
						            	if(INCENTIVEPRODUCTPARAMETER.equals(incDetailVO.getDisIncParameterCode())
						            			&& detailVO.getDisIncParameterValue().equals(incDetailVO.getDisIncParameterValue()) 
						            			&& (incentiveVOParameterCode.equals(incentiveConfigVOParameterCode))){
						            		
						            		
						            		Map<String, Collection<OneTimeVO>> oneTimes = null;
											oneTimes =  findOneTimeDescription(incentiveVO.getCompanyCode(), MAILSERVICELEVELS);
											
											Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
											oneTimeVOs= oneTimes.get(MAILSERVICELEVELS);
											String serviceLevel = null;
											
											for(OneTimeVO oneTimeVO: oneTimeVOs){

												if(detailVO.getDisIncParameterValue().equals(oneTimeVO.getFieldValue())){
													serviceLevel = oneTimeVO.getFieldDescription();
													
													error = new ErrorVO(FORMULA_ALREADY_EXISTS_WITH_DIFF_PERCENTAGE_FOR_SAME_PRODUCT);
										            errorCodes[0] = serviceLevel;
										            error.setErrorData(errorCodes);
													break;
												}
											}
										            
						            	}
						            	
						            }
					            }
				            	
			        	   
			           }
			           //Modified for ICRD-311126 ends
			           //Modified for ICRD-309247 ends
		          }
			            }




		          }


		          for (IncentiveConfigurationDetailVO detailVO : incentiveConfigVO.getIncentiveConfigurationDetailVOs()) {
		            for (IncentiveConfigurationDetailVO incDetailVO : incentiveVO.getIncentiveConfigurationDetailVOs()) {
		              if ((detailVO.getDisIncParameterCode().equals(incDetailVO.getDisIncParameterCode()))
		            		  && (detailVO.getDisIncParameterValue().equals(incDetailVO.getDisIncParameterValue()))
		                      && (detailVO.getDisIncParameterType().equals(incDetailVO.getDisIncParameterType()))) {

		            	  		parameterExists ++;
		              }
		            }

		          }

		          if ((parameterExists == 3) && (incentiveConfigVO.getFormula().equals(incentiveVO.getFormula()))
		        		  &&  (incentiveConfigVO.getDisIncPercentage() == incentiveVO.getDisIncPercentage())
		        		  && (incentiveConfigVO.getBasis().equals(incentiveVO.getBasis()))
		        		  && (incentiveConfigVO.getDisIncValidFrom().equals(incentiveVO.getDisIncValidFrom()))
		        		  && (incentiveConfigVO.getDisIncValidTo().equals(incentiveVO.getDisIncValidTo()))) {

		        	  		error = new ErrorVO(DUPLICATE_RECORD);
		          }
			}
			}
			 if ((BOTH.equals(incentiveVO.getServiceRespFlag()))
	        		  && ((SERVICE_RESPONSIVE.equals(incentiveConfigVO.getServiceRespFlag()))
	        				  || (NON_SERVICE_RESPONSIVE.equals(incentiveConfigVO.getServiceRespFlag())))){
	        	  			error = new ErrorVO(BOTH_CONFIGURATION_NOT_ALLOWED);
	        	  			break;
	          } else if (((SERVICE_RESPONSIVE.equals(incentiveVO.getServiceRespFlag()))
	        		  ||  (NON_SERVICE_RESPONSIVE.equals(incentiveVO.getServiceRespFlag())))
	        		  && (BOTH.equals(incentiveConfigVO.getServiceRespFlag())))  {
	        	  		error = new ErrorVO(BOTH_CONFIGURATION_NOT_ALLOWED);
	          }
			index++;
		}
		 if(error != null){
       	  	errors.add(error);
         }
		 return errors;
		}
	
	/**
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param oneTimeCode
	 *	Parameters	:	@return
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */

	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
			String companyCode, String oneTimeCode) {
		log.entering("MailController", "findOneTimeDescription");
		Map<String, Collection<OneTimeVO>> oneTimes = null;

		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(oneTimeCode);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,
					fieldValues);
		}catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "onetime fetch exception");
		}

		log.exiting("MailController", "findOneTimeDescription");
		return oneTimes;
	}
	
	boolean isWithinRange(LocalDate fromDate,LocalDate toDate,LocalDate fromDate1,LocalDate toDate1) {
		  // return (fromDate1.before(fromDate) || fromDate1.after(toDate));
		  // return ((fromDate1.equals(fromDate) || fromDate1.after(fromDate) ) 
			//	    && (toDate1.equals(toDate)  || toDate1.before(toDate)));//commented for ICRD-322316
		   
		return  ( 
				(fromDate.isLesserThan(toDate1) || fromDate.equals(toDate1)) && 
				(fromDate1.isLesserThan(toDate)|| fromDate1.equals(toDate)));
		   
		
		}
}
