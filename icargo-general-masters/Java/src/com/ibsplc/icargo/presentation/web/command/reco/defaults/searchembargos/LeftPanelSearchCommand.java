/*
 * LeftPanelSearchCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.searchembargos;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * Command class for filter Embargos from left panel values
 *
 * @author A-5867
 *
 */
public class LeftPanelSearchCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
	
	private static final String LEFT_PANEL_CATEGORY="CT";
	private static final String LEFT_PANEL_COMPLIANCE_TYPE="T";
	private static final String LEFT_PANEL_AIRPORT="AP";
	private static final String LEFT_PANEL_AIRPORT_GROUP="APG";
	private static final String LEFT_PANEL_COUNTRY="CN";
	private static final String LEFT_PANEL_COUNTRY_GROUP="CNG";
	
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
	private static final String PARAMETER_CODE_AWBPFX = "AWBPRE";
	private static final String PARAMETER_CODE_FLTNUM = "FLTNUM";
	private static final String PARAMETER_CODE_PAYTYP = "PAYTYP";
	private static final String PARAMETER_CODE_FLTTYP = "FLTTYP";
	private static final String PARAMETER_CODE_SLTIND = "SPLIT";
	private static final String PARAMETER_CODE_FLTOWN = "FLTOWR";
	private static final String PARAMETER_CODE_UNDNUM = "UNNUM";
	private static final String PARAMETER_CODE_PI = "PKGINS";
	private static final String PARAMETER_CODE_DATE="DAT";
	private static final String PARAMETER_CODE_ARLGRP="ARLGRP";
	private static final String PARAMETER_CODE_AGT = "AGT";
	private static final String PARAMETER_CODE_AGT_GROUP = "AGTGRP";
	
	private static final String VALUE_ALL="-";
	private static final String LABEL_INCLUDE="(Inc)";
	private static final String LABEL_EXCLUDE="(Exc)";
	private static final String LABEL_ALL="ALL";
	private static final String LABEL_ORIGIN="Origin";
	private static final String LABEL_DESTINATION="Destination";
	private static final String LABEL_VIAPOINT="Via point";
	
	//added by A-5799 for IASCB-23507 starts
	public static final String SERVICE_CARGO_CLASS = "SRVCRGCLS";
	public static final String AIRCRAFT_CLASSIFICATION = "ACRCLS";
	public static final String SHIPPER = "SHP";
	public static final String SHIPPER_GROUP = "SHPGRP";
	public static final String CONSIGNEE = "CNS";
	public static final String CONSIGNEE_GROUP = "CNSGRP";
	public static final String SHIPMENT_TYPE = "SHPTYP";
	public static final String CONSOL = "CNSL";
	 //added by A-5799 for IASCB-23507 ends
	private static final String SUCCESS = "success";
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("LeftPanelSearchCommand","Execute");
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
		embargoFilterVO.setLeftPanelParameters(searchEmbargoSession.getLeftPanelParameters());			
		List<EmbargoDetailsVO> embargoDetails=null;
		EmbargoSearchVO embargoSearchVO=searchEmbargoSession.getEmbargoSearchVO();
		if(null !=embargoSearchVO.getEmbargoDetails() &&  embargoSearchVO.getEmbargoDetails().size()>0){
			embargoDetails= searchEmbargos(searchEmbargoForm,embargoSearchVO.getEmbargoDetails());
				searchEmbargoSession.setEmabrgoDetailVOs(embargoDetails);
			}
		invocationContext.target =SUCCESS;
		log.exiting("LeftPanelSearchCommand","Execute");
	}
	
	private List<EmbargoDetailsVO> searchEmbargos(SearchEmbargoForm searchEmbargoForm,List<EmbargoDetailsVO> embargoDetailsSearchList){
		List<EmbargoDetailsVO> embargoDetails = new ArrayList<EmbargoDetailsVO>();
		String leftPanelKey =searchEmbargoForm.getLeftPanelKey();
		String leftPanelValue =searchEmbargoForm.getLeftPanelValue();
		StringBuilder searchString= null;
		if(null !=leftPanelKey && leftPanelKey.length()>0 && null !=leftPanelValue && leftPanelValue.length()>0){
			if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_CATEGORY)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getCategoryDescription() && embargoDtls.getCategoryDescription().equalsIgnoreCase(leftPanelValue)){
						setProcessTypes(embargoDtls);
						embargoDetails.add(embargoDtls);
					}
				}			
			}else if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_COMPLIANCE_TYPE)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getComplianceTypeDescription() && embargoDtls.getComplianceTypeDescription().equalsIgnoreCase(leftPanelValue)){
						setProcessTypes(embargoDtls);
						embargoDetails.add(embargoDtls);
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_AIRPORT)){				
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginAirportCodeInc()&& embargoDtls.getOriginAirportCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getOriginAirportCodeInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginAirportCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginAirportCodeExc()&& embargoDtls.getOriginAirportCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getOriginAirportCodeExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginAirportCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointAirportCodeInc() && embargoDtls.getViaPointAirportCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointAirportCodeInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointAirportCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointAirportCodeExc()&& embargoDtls.getViaPointAirportCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointAirportCodeExc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointAirportCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationAirportCodeInc() && embargoDtls.getDestinationAirportCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationAirportCodeInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationAirportCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationAirportCodeExc()&& embargoDtls.getDestinationAirportCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationAirportCodeExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationAirportCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_AIRPORT_GROUP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginAirportGroupInc()&& embargoDtls.getOriginAirportGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getOriginAirportGroupInc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginAirportGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginAirportGroupExc()&& embargoDtls.getOriginAirportGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getOriginAirportGroupExc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginAirportGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointAirportGroupInc() && embargoDtls.getViaPointAirportGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointAirportGroupInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointAirportGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointAirportGroupExc()&& embargoDtls.getViaPointAirportGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointAirportGroupExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointAirportGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationAirportGroupInc() && embargoDtls.getDestinationAirportGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationAirportGroupInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationAirportGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationAirportGroupExc()&& embargoDtls.getDestinationAirportGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationAirportGroupExc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationAirportGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_COUNTRY)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginCountryCodeInc()&& embargoDtls.getOriginCountryCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getOriginCountryCodeInc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginCountryCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginCountryCodeExc()&& embargoDtls.getOriginCountryCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getOriginCountryCodeExc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginCountryCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointCountryCodeInc() && embargoDtls.getViaPointCountryCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointCountryCodeInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointCountryCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointCountryCodeExc()&& embargoDtls.getViaPointCountryCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointCountryCodeExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointCountryCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationCountryCodeInc() && embargoDtls.getDestinationCountryCodeInc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationCountryCodeInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationCountryCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationCountryCodeExc()&& embargoDtls.getDestinationCountryCodeExc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationCountryCodeExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationCountryCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(LEFT_PANEL_COUNTRY_GROUP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginCountryGroupInc()&& embargoDtls.getOriginCountryGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getOriginCountryGroupInc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginCountryGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginCountryGroupExc()&& embargoDtls.getOriginCountryGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getOriginCountryGroupExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginCountryGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointCountryGroupInc() && embargoDtls.getViaPointCountryGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointCountryGroupInc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointCountryGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointCountryGroupExc()&& embargoDtls.getViaPointCountryGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getViaPointCountryGroupExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointCountryGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationCountryGroupInc() && embargoDtls.getDestinationCountryGroupInc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationCountryGroupInc()) ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationCountryGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationCountryGroupExc()&& embargoDtls.getDestinationCountryGroupExc().length()>0 &&
							!"-".equals(embargoDtls.getDestinationCountryGroupExc())){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationCountryGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue.trim()+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_SCC)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getSccInc()&& embargoDtls.getSccInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getSccInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getSccExc()&& embargoDtls.getSccExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getSccExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_SCC_GROUP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getSccGroupInc()&& embargoDtls.getSccGroupInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getSccGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getSccGroupExc()&& embargoDtls.getSccGroupExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getSccGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_AGT)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getAgtCodeInc()&& embargoDtls.getAgtCodeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAgtCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAgtCodeExc()&& embargoDtls.getAgtCodeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAgtCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_AGT_GROUP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getAgtGroupInc()&& embargoDtls.getAgtGroupInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAgtGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAgtGroupExc()&& embargoDtls.getAgtGroupExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAgtGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}
			else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_TIME)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginStartTime()&& embargoDtls.getOriginStartTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginStartTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginEndTime()&& embargoDtls.getOriginEndTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginEndTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointStartTime()&& embargoDtls.getViaPointStartTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointStartTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointEndTime()&& embargoDtls.getViaPointEndTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointEndTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationStartTime()&& embargoDtls.getDestinationStartTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationStartTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationEndTime()&& embargoDtls.getDestinationEndTime().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationEndTime()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_CARRIER)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getAirlineCodeInc()&& embargoDtls.getAirlineCodeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAirlineCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAirlineCodeExc()&& embargoDtls.getAirlineCodeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAirlineCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_WEIGHT)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getWeightStart()&& embargoDtls.getWeightStart().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getWeightStart()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getWeightEnd()&& embargoDtls.getWeightEnd().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getWeightEnd()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_HEIGHT)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getHeightStart()&& embargoDtls.getHeightStart().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getHeightStart()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getHeightEnd()&& embargoDtls.getHeightEnd().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getHeightEnd()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_WIDTH)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getWidthStart()&& embargoDtls.getWidthStart().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getWidthStart()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getWidthEnd()&& embargoDtls.getWidthEnd().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getWidthEnd()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_LENGTH)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getLengthStart()&& embargoDtls.getLengthStart().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getLengthStart()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getLengthEnd()&& embargoDtls.getLengthEnd().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getLengthEnd()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_NAT)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getNatureOfGoodsInc()&& embargoDtls.getNatureOfGoodsInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getNatureOfGoodsInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getNatureOfGoodsExc()&& embargoDtls.getNatureOfGoodsExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getNatureOfGoodsExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_COM)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getCommodityInc()&& embargoDtls.getCommodityInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getCommodityInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getCommodityExc()&& embargoDtls.getCommodityExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getCommodityExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_PRD)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getProductInc()&& embargoDtls.getProductInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getProductInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					
					if(null !=embargoDtls.getProductExc()&& embargoDtls.getProductExc().length()>0 ){      
						searchString= new StringBuilder().append(",").append(embargoDtls.getProductExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_AWBPFX)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getAwbPrefixInc()&& embargoDtls.getAwbPrefixInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAwbPrefixInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAwbPrefixExc()&& embargoDtls.getAwbPrefixExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAwbPrefixExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_FLTNUM)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//Modified by A-8146 for ICRD-337326 starts
					if(null !=embargoDtls.getFlightNumberInc()&& embargoDtls.getFlightNumberInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getFlightNumberInc()).append(",");
						if(searchString.toString().replace("~","").contains(","+leftPanelValue.trim().replace(" ", "")+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getFlightNumberExc()&& embargoDtls.getFlightNumberExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getFlightNumberExc()).append(",");
						if(searchString.toString().replace("~","").contains(","+leftPanelValue.trim().replace(" ", "")+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					//Modified by A-8146 for ICRD-337326 ends
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_PAYTYP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getPaymentTypeInc()&& embargoDtls.getPaymentTypeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getPaymentTypeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getPaymentTypeExc()&& embargoDtls.getPaymentTypeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getPaymentTypeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_FLTTYP)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getFlightTypeDescription()&& embargoDtls.getFlightTypeDescription().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getFlightTypeDescription()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_SLTIND)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getSplitIndicator()&& embargoDtls.getSplitIndicator().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getSplitIndicator()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_FLTOWN)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getFlightOwnerInc()&& embargoDtls.getFlightOwnerInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getFlightOwnerInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getFlightOwnerExc()&& embargoDtls.getFlightOwnerExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getFlightOwnerExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_UNDNUM)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getUnNumberInc()&& embargoDtls.getUnNumberInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getUnNumberInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getUnNumberExc()&& embargoDtls.getUnNumberExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getUnNumberExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_PI)){//Added by A-7534 for ICRD-226601
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getPkgInsInc() && embargoDtls.getPkgInsInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getPkgInsInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getPkgInsExc()&& embargoDtls.getPkgInsExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getPkgInsExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_ARLGRP)){
				
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getAirlineGroupInc()&& embargoDtls.getAirlineGroupInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAirlineGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAirlineGroupExc()&& embargoDtls.getAirlineGroupExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAirlineGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(PARAMETER_CODE_DATE)){
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					if(null !=embargoDtls.getOriginDateInc()&& embargoDtls.getOriginDateInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginDateInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getOriginDateExc()&& embargoDtls.getOriginDateExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getOriginDateExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationDateInc()&& embargoDtls.getDestinationDateInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationDateInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getDestinationDateExc()&& embargoDtls.getDestinationDateExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getDestinationDateExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					
					if(null !=embargoDtls.getViaPointDateInc()&& embargoDtls.getViaPointDateInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointDateInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getViaPointDateExc()&& embargoDtls.getViaPointDateExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getViaPointDateExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(SERVICE_CARGO_CLASS)){	
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//added by A-5799 starts
					//srvcrgcls
					if(null !=embargoDtls.getServiceCargoClassInc()&& embargoDtls.getServiceCargoClassInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getServiceCargoClassInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getServiceCargoClassExc()&& embargoDtls.getServiceCargoClassExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getServiceCargoClassExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(AIRCRAFT_CLASSIFICATION)){	
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//acrclsorg
					if(null !=embargoDtls.getAircraftClassOriginInc()&& embargoDtls.getAircraftClassOriginInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassOriginInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAircraftClassOriginExc()&& embargoDtls.getAircraftClassOriginExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassOriginExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					
					//acrclsdst
					if(null !=embargoDtls.getAircraftClassDestInc()&& embargoDtls.getAircraftClassDestInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassDestInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAircraftClassDestExc()&& embargoDtls.getAircraftClassDestExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassDestExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					
					//acrclsvia
					if(null !=embargoDtls.getAircraftClassViaInc()&& embargoDtls.getAircraftClassViaInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassViaInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getAircraftClassViaExc()&& embargoDtls.getAircraftClassViaExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getAircraftClassViaExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(SHIPPER)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//shp
					if(null !=embargoDtls.getShipperCodeInc()&& embargoDtls.getShipperCodeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipperCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getShipperCodeExc()&& embargoDtls.getShipperCodeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipperCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(SHIPPER_GROUP)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//shpgrp
					if(null !=embargoDtls.getShipperGroupInc()&& embargoDtls.getShipperGroupInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipperGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getShipperGroupExc()&& embargoDtls.getShipperGroupExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipperGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			
			}else if(leftPanelKey.equalsIgnoreCase(CONSIGNEE)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//CNS
					if(null !=embargoDtls.getConsigneeCodeInc()&& embargoDtls.getConsigneeCodeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsigneeCodeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getConsigneeCodeExc()&& embargoDtls.getConsigneeCodeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsigneeCodeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			
			}else if(leftPanelKey.equalsIgnoreCase(CONSIGNEE_GROUP)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//CNSGRP
					if(null !=embargoDtls.getConsigneeGroupInc()&& embargoDtls.getConsigneeGroupInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsigneeGroupInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getConsigneeGroupExc()&& embargoDtls.getConsigneeGroupExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsigneeGroupExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			
			}else if(leftPanelKey.equalsIgnoreCase(SHIPMENT_TYPE)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//SHPTYP
					if(null !=embargoDtls.getShipmentTypeInc()&& embargoDtls.getShipmentTypeInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipmentTypeInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getShipmentTypeExc()&& embargoDtls.getShipmentTypeExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getShipmentTypeExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}
			}else if(leftPanelKey.equalsIgnoreCase(CONSOL)){		
				for(EmbargoDetailsVO embargoDtls:embargoDetailsSearchList){
					//CNSL
					if(null !=embargoDtls.getConsolInc()&& embargoDtls.getConsolInc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsolInc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
					if(null !=embargoDtls.getConsolExc()&& embargoDtls.getConsolExc().length()>0 ){
						searchString= new StringBuilder().append(",").append(embargoDtls.getConsolExc()).append(",");
						if(searchString.toString().contains(","+leftPanelValue+",")){
							setProcessTypes(embargoDtls);
							embargoDetails.add(embargoDtls);
							continue;
						}
					}
				}	
				
			}
			//added by A-5799 ends	
		}
		return embargoDetails;
	}
	
	private void setProcessTypes(EmbargoDetailsVO embargoDetailsVO){
			StringBuilder processTypeOrigin= new StringBuilder().append(LABEL_ORIGIN).append(" : ");
			StringBuilder processTypeDestination=new StringBuilder().append(LABEL_DESTINATION).append(" : ");
			StringBuilder processTypeViaPoint=new StringBuilder().append(LABEL_VIAPOINT).append(" : ");
			boolean hasInclude=false;
			boolean hasExclude=false;
			/**Process Type : Origin **/
			if(null !=embargoDetailsVO.getOriginAirportCodeInc() && embargoDetailsVO.getOriginAirportCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getOriginAirportCodeInc().equalsIgnoreCase(VALUE_ALL)){
				processTypeOrigin.append(embargoDetailsVO.getOriginAirportCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getOriginAirportGroupInc() && embargoDetailsVO.getOriginAirportGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getOriginAirportGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginAirportGroupInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getOriginCountryCodeInc() && embargoDetailsVO.getOriginCountryCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getOriginCountryCodeInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginCountryCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getOriginCountryGroupInc() && embargoDetailsVO.getOriginCountryGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getOriginCountryGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginCountryGroupInc());
				hasInclude=true;
			}
			if(hasInclude){
				processTypeOrigin.append(LABEL_INCLUDE);
			}
			if(null !=embargoDetailsVO.getOriginAirportCodeExc() && embargoDetailsVO.getOriginAirportCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getOriginAirportCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginAirportCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getOriginAirportGroupExc() && embargoDetailsVO.getOriginAirportGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getOriginAirportGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginAirportGroupExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getOriginCountryCodeExc() && embargoDetailsVO.getOriginCountryCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getOriginCountryCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginCountryCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getOriginCountryGroupExc() && embargoDetailsVO.getOriginCountryGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getOriginCountryGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeOrigin.append(",");
				}
				processTypeOrigin.append(embargoDetailsVO.getOriginCountryGroupExc());
				hasExclude=true;
			}
			if(hasExclude){
				processTypeOrigin.append(LABEL_EXCLUDE);
			}
			if(!hasInclude && !hasExclude){
				processTypeOrigin.append(LABEL_ALL);
			}
			processTypeOrigin.append("~");
			
			/**Process Type : Destination **/
			hasInclude=false;
			hasExclude=false;
			if(null !=embargoDetailsVO.getDestinationAirportCodeInc() && embargoDetailsVO.getDestinationAirportCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationAirportCodeInc().equalsIgnoreCase(VALUE_ALL)){
				processTypeDestination.append(embargoDetailsVO.getDestinationAirportCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationAirportGroupInc() && embargoDetailsVO.getDestinationAirportGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationAirportGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationAirportGroupInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationCountryCodeInc() && embargoDetailsVO.getDestinationCountryCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationCountryCodeInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationCountryCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationCountryGroupInc() && embargoDetailsVO.getDestinationCountryGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationCountryGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationCountryGroupInc());
				hasInclude=true;
			}
			if(hasInclude){
				processTypeDestination.append(LABEL_INCLUDE);
			}
			if(null !=embargoDetailsVO.getDestinationAirportCodeExc() && embargoDetailsVO.getDestinationAirportCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationAirportCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationAirportCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationAirportGroupExc() && embargoDetailsVO.getDestinationAirportGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationAirportGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationAirportGroupExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationCountryCodeExc() && embargoDetailsVO.getDestinationCountryCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationCountryCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationCountryCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getDestinationCountryGroupExc() && embargoDetailsVO.getDestinationCountryGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getDestinationCountryGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeDestination.append(",");
				}
				processTypeDestination.append(embargoDetailsVO.getDestinationCountryGroupExc());
				hasExclude=true;
			}
			if(hasExclude){
				processTypeDestination.append(LABEL_EXCLUDE);
			}
			if(!hasInclude && !hasExclude){
				processTypeDestination.append(LABEL_ALL);
			}
			processTypeDestination.append("~");
			
			/**Process Type : Via point **/
			hasInclude=false;
			hasExclude=false;
			if(null !=embargoDetailsVO.getViaPointAirportCodeInc() && embargoDetailsVO.getViaPointAirportCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointAirportCodeInc().equalsIgnoreCase(VALUE_ALL)){
				processTypeViaPoint.append(embargoDetailsVO.getViaPointAirportCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointAirportGroupInc() && embargoDetailsVO.getViaPointAirportGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointAirportGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointAirportGroupInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointCountryCodeInc() && embargoDetailsVO.getViaPointCountryCodeInc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointCountryCodeInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointCountryCodeInc());
				hasInclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointCountryGroupInc() && embargoDetailsVO.getViaPointCountryGroupInc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointCountryGroupInc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointCountryGroupInc());
				hasInclude=true;
			}
			if(hasInclude){
				processTypeViaPoint.append(LABEL_INCLUDE);
			}
			if(null !=embargoDetailsVO.getViaPointAirportCodeExc() && embargoDetailsVO.getViaPointAirportCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointAirportCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasInclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointAirportCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointAirportGroupExc() && embargoDetailsVO.getViaPointAirportGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointAirportGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointAirportGroupExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointCountryCodeExc() && embargoDetailsVO.getViaPointCountryCodeExc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointCountryCodeExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointCountryCodeExc());
				hasExclude=true;
			}
			if(null !=embargoDetailsVO.getViaPointCountryGroupExc() && embargoDetailsVO.getViaPointCountryGroupExc().trim().length()>0 
					&& !embargoDetailsVO.getViaPointCountryGroupExc().equalsIgnoreCase(VALUE_ALL)){
				if(hasExclude){
					processTypeViaPoint.append(",");
				}
				processTypeViaPoint.append(embargoDetailsVO.getViaPointCountryGroupExc());
				hasExclude=true;
			}
			if(hasExclude){
				processTypeViaPoint.append(LABEL_EXCLUDE);
			}
			if(!hasInclude && !hasExclude){
				processTypeViaPoint.append(LABEL_ALL);
			}
			
			processTypeOrigin.append(processTypeDestination).append(processTypeViaPoint);
			embargoDetailsVO.setProcessType(processTypeOrigin.toString());
	}

}
