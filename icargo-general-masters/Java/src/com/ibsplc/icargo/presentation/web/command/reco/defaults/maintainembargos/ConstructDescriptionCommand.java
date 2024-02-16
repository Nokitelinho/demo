
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ConstructDescriptionCommand extends BaseCommand{
	
	private static final String SUCCESS = "success";
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.maintainembargo";
	private static final String LABEL_FROM =" from";
	private static final String LABEL_TO =" to";
	private static final String LABEL_VIA =" via";
	private static final String LABEL_EXCEPT =" except";
	private static final String LABEL_AND =" and";
	private static final String LABEL_SPLITTING_SHIPMENT =" splitting shipment";
	private static final String LABEL_SHIPMENT =" for shipments";
	private static final String LABEL_ON_DAYS =" on days";
	private static final String LABEL_CM =" cm";
	private static final String LABEL_KG =" kg";
	
	private static final String APPLICABLE_INCLUDE ="IN";
	private static final String APPLICABLE_EXCLUDE ="EX";
	private static final String APPLICABLE_ON ="ON";
	
	private static final String GEOGRAPHICAL_LEVEL_ORIGIN ="O";
	private static final String GEOGRAPHICAL_LEVEL_VIA_POINT ="V";
	private static final String GEOGRAPHICAL_LEVEL_DESTINATION ="D";
	//added by A-8387
	private static final String GEOGRAPHICAL_LEVEL_SEGMENTORIGIN ="L";
	private static final String GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION ="U";
	private static final String PARAMETER_CODE_SCC = "SCC";
	private static final String PARAMETER_CODE_SCC_GROUP = "SCCGRP";
	private static final String PARAMETER_CODE_TIME = "TIM";
	private static final String PARAMETER_CODE_CARRIER = "CAR";
	private static final String PARAMETER_CODE_HEIGHT = "HGT";
	private static final String PARAMETER_CODE_LENGTH = "LEN";
	private static final String PARAMETER_CODE_WIDTH = "WID";
	private static final String PARAMETER_CODE_WEIGHT = "WGT";
	private static final String PARAMETER_CODE_NAT = "GOODS";
	private static final String PARAMETER_CODE_COM = "COM";
	private static final String PARAMETER_CODE_PRD = "PRD";
	private static final String PARAMETER_CODE_PRD_PKGINS ="PKGINS";
	private static final String PARAMETER_CODE_AWBPFX = "AWBPRE";
	private static final String PARAMETER_CODE_FLTNUM = "FLTNUM";
	private static final String PARAMETER_CODE_PAYTYP = "PAYTYP";
	private static final String PARAMETER_CODE_FLTTYP = "FLTTYP";
	private static final String PARAMETER_CODE_SLTIND = "SPLIT";
	private static final String PARAMETER_CODE_FLTOWN = "FLTOWR";
	private static final String PARAMETER_CODE_UNDNUM = "UNNUM";
	private static final String PARAMETER_CODE_DATES = "DAT";
	
	private static final String GENERAL_GROUP_CATEGORY="GEN";
	
	private static final String PARAMETER = "P";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String OPERATION_FLAG_UPDATE = "U";

	private static final String ORIGIN_INCLUDE ="origin_in";
	private static final String ORIGIN_EXCLUDE ="origin_ex";
	private static final String DESTINATION_INCLUDE ="destination_in";
	private static final String DESTINATION_EXCLUDE ="destination_ex";
	private static final String VIAPOINT_INCLUDE ="viapoint_in";
	private static final String VIAPOINT_EXCLUDE ="viapoint_ex";
	private static final String VALUE_ALL="-";
	private Log log = LogFactory.getLogger("CONSTRUCT DESCRIPTION COMMAND");


	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ConstructDescriptionCommand","Execute");
	    MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(MODULE_NAME,SCREENID);
	    MaintainEmbargoRulesForm maintainEmbargoForm =(MaintainEmbargoRulesForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl =getApplicationSession();
		LogonAttributes logonAttributes =applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		EmbargoRulesVO embargoVO =getEmbargoDetails(maintainEmbargoForm ,maintainEmbargoSession,companyCode);
		Map<String ,String>   descriptionMap=constructMaps(embargoVO);
		String description=constructDescription(descriptionMap,embargoVO,maintainEmbargoSession);	
		maintainEmbargoForm.setEmbargoDesc(description);
		invocationContext.target =SUCCESS;
	}
	private EmbargoRulesVO getEmbargoDetails(MaintainEmbargoRulesForm maintainEmbargoForm ,
			MaintainEmbargoRulesSession session, String companyCode){
		EmbargoRulesVO embargoVO = new EmbargoRulesVO();
		embargoVO.setCompanyCode(companyCode);
		embargoVO.setRuleType("E");
		embargoVO.setEmbargoLevel(maintainEmbargoForm.getEmbargoLevel());
		embargoVO.setEmbargoDescription(maintainEmbargoForm.getEmbargoDesc());		
		embargoVO.setIsSuspended(maintainEmbargoForm.getIsSuspended());
		embargoVO.setCategory(maintainEmbargoForm.getCategory());
		embargoVO.setComplianceType(maintainEmbargoForm.getComplianceType());
		embargoVO.setApplicableTransactions(maintainEmbargoForm.getApplicableTransactions());
		embargoVO.setDaysOfOperation(maintainEmbargoForm.getDaysOfOperation());
		String[] geographicLevel = maintainEmbargoForm.getGeographicLevel();
		String[] geographicLevelType = maintainEmbargoForm.getGeographicLevelType();
		String[] geographicLevelApplicableOn= maintainEmbargoForm.getGeographicLevelApplicableOn();
		String[] geographicLevelValues= maintainEmbargoForm.getGeographicLevelValues();
		String[] glOperationFlag = maintainEmbargoForm.getGlOperationFlag();
		if(glOperationFlag!=null){
			Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs = new ArrayList<EmbargoGeographicLevelVO>();
			for(int i = 0; i < glOperationFlag.length - 1 ; i++) {
				EmbargoGeographicLevelVO embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
				if(!"NOOP".equals(glOperationFlag[i])){
					if(OPERATION_FLAG_INSERT.equals(glOperationFlag[i]) ||
							OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){  
						embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);						
					}
				}
			}
			if(embargoGeographicLevelVOs!=null){
				embargoVO.setGeographicLevels((ArrayList<EmbargoGeographicLevelVO>)embargoGeographicLevelVOs);
			}
		}
		String[] parametercodes = maintainEmbargoForm.getParameterCode();
		String[] applicablevalues = maintainEmbargoForm.getIsIncluded();
		String[] values = maintainEmbargoForm.getValues();
		String[] operationalFlags = maintainEmbargoForm.getParamOperationalFlag();
		String[] applicableLevel = maintainEmbargoForm.getApplicableOn();
		if(operationalFlags != null ){
			Collection<EmbargoParameterVO> embargoParameterVOs = new ArrayList<EmbargoParameterVO>();
			for (int i = 0; i < operationalFlags.length -1; i++) {							
				EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
				if(!"NOOP".equals(operationalFlags[i])){
					if(OPERATION_FLAG_INSERT.equals(operationalFlags[i]) ||
							OPERATION_FLAG_UPDATE.equals(operationalFlags[i])){  
						embargoParameterVO.setCompanyCode(companyCode);
						embargoParameterVO.setParameterCode(parametercodes[i].toUpperCase());
						embargoParameterVO.setParameterValues(values[i].toUpperCase());
						embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);
						embargoParameterVO.setApplicableLevel(applicableLevel[i]);
						embargoParameterVO.setParameterLevel(PARAMETER);
						embargoParameterVOs.add(embargoParameterVO);						
					}
				}				
			}
			if(embargoParameterVOs!=null){
				embargoVO.setParameters((ArrayList<EmbargoParameterVO>)embargoParameterVOs);
			}	
		}
		return embargoVO;
	}
	
	private Map<String , String> constructMaps(EmbargoRulesVO embargoVO){
			Map<String, String> descriptionMap = new HashMap<String,String>();
			 if(null !=embargoVO.getGeographicLevels() && embargoVO.getGeographicLevels().size()>0){
				 for(EmbargoGeographicLevelVO geolvl:embargoVO.getGeographicLevels()){
					 if(null !=geolvl.getGeographicLevelValues() && geolvl.getGeographicLevelValues().length()>0 &&
							 !VALUE_ALL.equals(geolvl.getGeographicLevelValues().trim())){
						 if(GEOGRAPHICAL_LEVEL_ORIGIN.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_INCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(ORIGIN_INCLUDE) && null !=descriptionMap.get(ORIGIN_INCLUDE)){
								  descriptionMap.put(ORIGIN_INCLUDE, descriptionMap.get(ORIGIN_INCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(ORIGIN_INCLUDE,geolvl.getGeographicLevelValues());
							  }					  
						  }				  
						  if(GEOGRAPHICAL_LEVEL_ORIGIN.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_EXCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(ORIGIN_EXCLUDE) && null !=descriptionMap.get(ORIGIN_EXCLUDE)){
								  descriptionMap.put(ORIGIN_EXCLUDE, descriptionMap.get(ORIGIN_EXCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(ORIGIN_EXCLUDE,geolvl.getGeographicLevelValues());
							  }	
						  }				  
//added
						  if(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_INCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(ORIGIN_INCLUDE) && null !=descriptionMap.get(ORIGIN_INCLUDE)){
								  descriptionMap.put(ORIGIN_INCLUDE, descriptionMap.get(ORIGIN_INCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(ORIGIN_INCLUDE,geolvl.getGeographicLevelValues());
							  }	
						  }		
						  
						  if(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_EXCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(ORIGIN_EXCLUDE) && null !=descriptionMap.get(ORIGIN_EXCLUDE)){
								  descriptionMap.put(ORIGIN_EXCLUDE, descriptionMap.get(ORIGIN_EXCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(ORIGIN_EXCLUDE,geolvl.getGeographicLevelValues());
							  }	
						  }					  
						  if(GEOGRAPHICAL_LEVEL_DESTINATION.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_INCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){	
							  if(descriptionMap.containsKey(DESTINATION_INCLUDE) && null !=descriptionMap.get(DESTINATION_INCLUDE)){
								  descriptionMap.put(DESTINATION_INCLUDE, descriptionMap.get(DESTINATION_INCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(DESTINATION_INCLUDE,geolvl.getGeographicLevelValues());
							  }						  
						  }				  
						  if(GEOGRAPHICAL_LEVEL_DESTINATION.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_EXCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(DESTINATION_EXCLUDE) && null !=descriptionMap.get(DESTINATION_EXCLUDE)){
								  descriptionMap.put(DESTINATION_EXCLUDE, descriptionMap.get(DESTINATION_EXCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(DESTINATION_EXCLUDE,geolvl.getGeographicLevelValues());
							  }						  
						  }				  
						  //added
						  if( GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_INCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){	
							  if(descriptionMap.containsKey(DESTINATION_INCLUDE) && null !=descriptionMap.get(DESTINATION_INCLUDE)){
								  descriptionMap.put(DESTINATION_INCLUDE, descriptionMap.get(DESTINATION_INCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(DESTINATION_INCLUDE,geolvl.getGeographicLevelValues());
							  }						  
						  }			
						  if(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_EXCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn())){
							  if(descriptionMap.containsKey(DESTINATION_EXCLUDE) && null !=descriptionMap.get(DESTINATION_EXCLUDE)){
								  descriptionMap.put(DESTINATION_EXCLUDE, descriptionMap.get(DESTINATION_EXCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(DESTINATION_EXCLUDE,geolvl.getGeographicLevelValues());
							  }						  
						  }			
						  if(GEOGRAPHICAL_LEVEL_VIA_POINT.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_INCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){
							  if(descriptionMap.containsKey(VIAPOINT_INCLUDE) && null !=descriptionMap.get(VIAPOINT_INCLUDE)){
								  descriptionMap.put(VIAPOINT_INCLUDE, descriptionMap.get(VIAPOINT_INCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(VIAPOINT_INCLUDE,geolvl.getGeographicLevelValues());
							  }						  
						  }				  
						  if(GEOGRAPHICAL_LEVEL_VIA_POINT.equalsIgnoreCase(geolvl.getGeographicLevel())&&
								  APPLICABLE_EXCLUDE.equalsIgnoreCase(geolvl.getGeographicLevelApplicableOn()) ){			  
							  if(descriptionMap.containsKey(VIAPOINT_EXCLUDE) && null !=descriptionMap.get(VIAPOINT_EXCLUDE)){
								  descriptionMap.put(VIAPOINT_EXCLUDE, descriptionMap.get(VIAPOINT_EXCLUDE)+","+geolvl.getGeographicLevelValues());
							  }else{
								  descriptionMap.put(VIAPOINT_EXCLUDE,geolvl.getGeographicLevelValues());
							  }	
						  }	
					 }
					  			  
				  }	
			 }					  
			  return descriptionMap;			  		   
	}
	
	 private String constructDescription(Map<String ,String> desmap,EmbargoRulesVO embargoVO,MaintainEmbargoRulesSession session){
		 Collection<OneTimeVO>  complianceTypes =session.getComplianceTypes();
		 Collection<OneTimeVO>  flightTypes =session.getFlightTypes();
		 Map<String,String> complianceTypeMap= new HashMap<String, String>();
		 Map<String,String> flightTypeMap= new HashMap<String, String>();
		 Map<String,String> applicableLabelMap= new HashMap<String, String>();
		 GeneralMasterGroupVO generalMasterGroup=null;
		 boolean isAnd=false;
		 boolean isShipment=true;
		 if(null !=complianceTypes && complianceTypes.size()>0){
			 for(OneTimeVO oneTimeVO:complianceTypes){
				 complianceTypeMap.put(oneTimeVO.getFieldValue(), oneTimeVO.getFieldDescription());
			 }
		 }
		 if(null !=flightTypes && flightTypes.size()>0){
			 for(OneTimeVO oneTimeVO:flightTypes){
				 flightTypeMap.put(oneTimeVO.getFieldValue(), oneTimeVO.getFieldDescription());
			 }
		 }
		 applicableLabelMap.put("IN", " for");
		 applicableLabelMap.put("EX", " except for");
		 applicableLabelMap.put("EQ", " equals");
		 applicableLabelMap.put("GT", " greater than");
		 applicableLabelMap.put("LT", " less than");
		 applicableLabelMap.put("GTEQ", " greater than or equal to");
		 applicableLabelMap.put("LTEQ", " less than or equal to");
		 applicableLabelMap.put("INON", " on");
		 applicableLabelMap.put("EXON", " except on");
		 applicableLabelMap.put("EXIF", " without");
		 StringBuilder description = new StringBuilder();
		 description=description.append(complianceTypeMap.get(embargoVO.getComplianceType())!=null?complianceTypeMap.get(embargoVO.getComplianceType()):"");
		 if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){					
			for(EmbargoParameterVO param : embargoVO.getParameters()){
				if(null !=param.getParameterValues() && param.getParameterValues().length()>0){
					if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SLTIND)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(LABEL_SPLITTING_SHIPMENT);				
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SCC_GROUP)){
						GeneralMasterGroupingDelegate delegate= new GeneralMasterGroupingDelegate();
						GeneralMasterGroupFilterVO generalMasterGroupFilterVO= new GeneralMasterGroupFilterVO();
						generalMasterGroupFilterVO.setCompanyCode(embargoVO.getCompanyCode());
						generalMasterGroupFilterVO.setGroupCategory(GENERAL_GROUP_CATEGORY);
						generalMasterGroupFilterVO.setGroupType(PARAMETER_CODE_SCC_GROUP);
						generalMasterGroupFilterVO.setGroupName(param.getParameterValues());
						try{
							generalMasterGroup= delegate.listGeneralMasterGroup(generalMasterGroupFilterVO);
						}catch(BusinessDelegateException e){
							handleDelegateException(e);							
						}
						if(null !=generalMasterGroup && generalMasterGroup.getGroupDetailsVOs().size()>0){
							int count=0;
							StringBuilder sccString= new StringBuilder();
							for(GeneralMasterGroupDetailsVO detailsVO:generalMasterGroup.getGroupDetailsVOs()){
								count++;
								sccString.append(detailsVO.getGroupedEntity());
								if(generalMasterGroup.getGroupDetailsVOs().size()>count){
									sccString.append(",");
								}
							}
							description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
							.append(sccString);
						}
						
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SCC)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_NAT)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_COM)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PAYTYP)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_AWBPFX)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ");
						if(param.getParameterValues()!=null && param.getParameterValues().trim().length()>0){
							String[] paramValue = param.getParameterValues().split(",");
							for(int i=0;i<paramValue.length;i++){
								if(i==0)
									{
									description.append("AWB ").append(paramValue[i]);
									}
								else
									{
									description.append(",AWB ").append(paramValue[i]);
							}
						}
						}
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_UNDNUM)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ");
						if(param.getParameterValues()!=null && param.getParameterValues().trim().length()>0){
							String[] paramValue = param.getParameterValues().split(",");
							for(int i=0;i<paramValue.length;i++){
								if(i==0)
									{
									description.append("UN ").append(paramValue[i]);
									}
								else
									{
									description.append(",UN ").append(paramValue[i]);
							}
						}
						}
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_HEIGHT)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						if(isShipment){
							description.append(LABEL_SHIPMENT);
						}
						description.append(" with Height").append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues()).append(LABEL_CM);
						isAnd=true;
						isShipment=false;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_WIDTH)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						if(isShipment){
							description.append(LABEL_SHIPMENT);
						}
						description.append(" with Width").append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues()).append(LABEL_CM);
						isAnd=true;
						isShipment=false;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_LENGTH)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						if(isShipment){
							description.append(LABEL_SHIPMENT);
						}
						description.append(" with Length").append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues()).append(LABEL_CM);
						isAnd=true;
						isShipment=false;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_WEIGHT)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues()).append(LABEL_KG);
						isAnd=true;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTNUM)){
						String[] flightNumber=param.getParameterValues().split("~");
						description.append(applicableLabelMap.get(param.getApplicable()+APPLICABLE_ON)).append(" ");
						if(null !=flightNumber && flightNumber.length>0){
							description.append(param.getParameterValues().split("~")[0]);
						}
						if(null !=flightNumber && flightNumber.length>1){
							description.append(" ").append(param.getParameterValues().split("~")[1]);
						}
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTOWN)){
						description.append(applicableLabelMap.get(param.getApplicable()+APPLICABLE_ON)).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_CARRIER)){
						description.append(applicableLabelMap.get(param.getApplicable()+APPLICABLE_ON)).append(" ")
						.append(param.getParameterValues());
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTTYP)){
						description.append(applicableLabelMap.get(param.getApplicable()+APPLICABLE_ON)).append(" ")
						.append(flightTypeMap.get(param.getParameterValues()));
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_DATES)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						description.append(applicableLabelMap.get(param.getApplicable()+APPLICABLE_ON)).append(" ")
						.append(param.getParameterValues());
						isAnd=true;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_TIME)){
						if(isAnd){
							description.append(LABEL_AND);
						}
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
						isAnd=true;
					}else if(param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PRD)){
						description.append(applicableLabelMap.get(param.getApplicable())).append(" ")
						.append(param.getParameterValues());
					} else if (param.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PRD_PKGINS)){
						description.append(applicableLabelMap.get(param.getApplicable()))
						.append(" ").append("UNID PI-").append("")
						.append(param.getParameterValues());
					
					}
				}				   
			}
		  }
		 if(null !=embargoVO.getDaysOfOperation()&&embargoVO.getDaysOfOperation().length()>0){
			 description.append(LABEL_ON_DAYS).append(" ").append(embargoVO.getDaysOfOperation());
		 }
		   if(desmap!=null && desmap.size()>0){
			if(desmap.containsKey(ORIGIN_INCLUDE)&& null !=desmap.get(ORIGIN_INCLUDE)){
				description.append(LABEL_FROM);
				description.append(" ").append(desmap.get(ORIGIN_INCLUDE));
			}
			if(desmap.containsKey(ORIGIN_EXCLUDE)&& null !=desmap.get(ORIGIN_EXCLUDE)){
				/*if(desmap.containsKey(ORIGIN_INCLUDE)&& null !=desmap.get(ORIGIN_INCLUDE)){
					description.append(LABEL_AND);
				}*/
				description.append(LABEL_EXCEPT).append(LABEL_FROM);
				description.append(" ").append(desmap.get(ORIGIN_EXCLUDE));
			}
			if(desmap.containsKey(VIAPOINT_INCLUDE)&& null !=desmap.get(VIAPOINT_INCLUDE)){
				description.append(LABEL_VIA);
				description.append(" ").append(desmap.get(VIAPOINT_INCLUDE));
			}
			if(desmap.containsKey(VIAPOINT_EXCLUDE)&& null !=desmap.get(VIAPOINT_EXCLUDE)){
				/*if(desmap.containsKey(VIAPOINT_INCLUDE)&& null !=desmap.get(VIAPOINT_INCLUDE)){
					description.append(LABEL_AND);
				}*/
				description.append(LABEL_EXCEPT).append(LABEL_VIA);
				description.append(" ").append(desmap.get(VIAPOINT_EXCLUDE));
			}
			if(desmap.containsKey(DESTINATION_INCLUDE)&& null !=desmap.get(DESTINATION_INCLUDE)){
				description.append(LABEL_TO);
				description.append(" ").append(desmap.get(DESTINATION_INCLUDE));
			}
			if(desmap.containsKey(DESTINATION_EXCLUDE)&& null !=desmap.get(DESTINATION_EXCLUDE)){
				/*if(desmap.containsKey(DESTINATION_INCLUDE)&& null !=desmap.get(DESTINATION_INCLUDE)){
					description.append(LABEL_AND);
				}*/
				description.append(LABEL_EXCEPT).append(LABEL_TO);
				description.append(" ").append(desmap.get(DESTINATION_EXCLUDE));
			}				  
		}			   
		   return description.toString();
	   }

}
