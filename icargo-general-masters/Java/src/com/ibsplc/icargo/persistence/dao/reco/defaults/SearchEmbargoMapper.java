/*
 * ListEmbargoMultiMapper.java Created on Aug 02, 2013
 *
 * Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLeftPanelParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * @author A-5867
 * 
 */
public class SearchEmbargoMapper implements MultiMapper<EmbargoDetailsVO> {

	private EmbargoSearchVO embargoSearchVO;
	private Collection<OneTimeVO> leftPanelParameters;
	private Collection<OneTimeVO> categories;
	private Collection<OneTimeVO> complianceTypes;
	private Collection<OneTimeVO> parameterCodes;
	private Collection<OneTimeVO> flightTypes;
	private static final String LEFT_PANEL_CATEGORY="CT";
	private static final String LEFT_PANEL_COMPLIANCE_TYPE="T";
	private static final String LEFT_PANEL_AIRPORT="AP";
	private static final String LEFT_PANEL_AIRPORT_GROUP="APG";
	private static final String LEFT_PANEL_COUNTRY="CN";
	private static final String LEFT_PANEL_COUNTRY_GROUP="CNG";
	private static final String LEFT_PANEL_PARAMETERS="PRM";
	
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
	private static final String PARAMETER_CODE_PKGINS = "PKGINS";
	private static final String PARAMETER_CODE_DATE="DAT";
	private static final String PARAMETER_CODE_ARLGRP="ARLGRP";
	private static final String PARAMETER_CODE_AGT = "AGT";
	private static final String PARAMETER_CODE_AGT_GROUP = "AGTGRP";
	//added by A-5799 for IASCB-23507 starts
	public static final String SERVICE_CARGO_CLASS = "SRVCRGCLS";
	public static final String AIRCRAFT_CLASSIFICATION="ACRCLS";
	public static final String AIRCRAFT_CLASS_ORIGIN="ACRCLSORG";
	public static final String AIRCRAFT_CLASS_VIA_POINT="ACRCLSVIA";
	public static final String AIRCRAFT_CLASS_DESTINATION ="ACRCLSDST";
	public static final String AIRCRAFT_CLASS_ALL= "ACRCLSALL";
	public static final String SHIPPER = "SHP";
	public static final String SHIPPER_GROUP = "SHPGRP";
	public static final String CONSIGNEE = "CNS";
	public static final String CONSIGNEE_GROUP = "CNSGRP";
	public static final String SHIPMENT_TYPE = "SHPTYP";
	public static final String CONSOL = "CNSL";
	 //added by A-5799 for IASCB-23507 ends
	
	private static final String VALUE_ALL="-";
	private static final String LABEL_INCLUDE="(Inc)";
	private static final String LABEL_EXCLUDE="(Exc)";
	private static final String LABEL_ALL="ALL";
	private static final String LABEL_ORIGIN="Origin";
	private static final String LABEL_DESTINATION="Destination";
	private static final String LABEL_VIAPOINT="Via point";
	
	SearchEmbargoMapper(){
		}
	
	SearchEmbargoMapper(EmbargoFilterVO filterVO,EmbargoSearchVO embargoSearchVO){
		this.leftPanelParameters=filterVO.getLeftPanelParameters();
		this.categories=filterVO.getCategories();
		this.complianceTypes=filterVO.getComplianceTypes();
		this.parameterCodes=filterVO.getParameterCodes();
		this.flightTypes=filterVO.getFlightTypes();
		this.embargoSearchVO=embargoSearchVO;
	}
	public List<EmbargoDetailsVO> map(ResultSet rs) throws SQLException {
	     List<EmbargoDetailsVO>  embargoDetailsVOs= new ArrayList<EmbargoDetailsVO>();
	     EmbargoDetailsVO embargoDetailsVO=null;
	     List<EmbargoLeftPanelParameterVO> leftPanelParameterVOs = new ArrayList<EmbargoLeftPanelParameterVO>();
	     Map<String,EmbargoLeftPanelParameterVO> leftPanelParameterMap= new HashMap<String, EmbargoLeftPanelParameterVO>();
	     Map<String,String> categoryMasterMap= new HashMap<String, String>();
	     Map<String,String> complianceTypeMasterMap= new HashMap<String, String>();
	     Map<String,String> flightTypeMasterMap= new HashMap<String, String>();
	     List<EmbargoParameterVO> parameters = new ArrayList<EmbargoParameterVO>();
	     Map<String,EmbargoParameterVO> parameterMap= new HashMap<String, EmbargoParameterVO>();
	     
	     Map<String,String> categoryMap= new LinkedHashMap<String, String>();
	     Map<String,String> complianceTypeMap= new LinkedHashMap<String, String>();
	     Map<String,String> airportMap= new LinkedHashMap<String, String>();
	     Map<String,String> airportGroupMap= new LinkedHashMap<String, String>();
	     Map<String,String> countryMap= new LinkedHashMap<String, String>();
	     Map<String,String> countryGroupMap= new LinkedHashMap<String, String>();
	     Map<String,String> sccMap= new LinkedHashMap<String, String>();
	     Map<String,String> sccGroupMap= new LinkedHashMap<String, String>();
	     Map<String,String> timeMap= new LinkedHashMap<String, String>();
	     Map<String,String> carrierMap= new LinkedHashMap<String, String>();
	     Map<String,String> heightMap= new LinkedHashMap<String, String>();
	     Map<String,String> widthMap= new LinkedHashMap<String, String>();
	     Map<String,String> lengthMap= new LinkedHashMap<String, String>();
	     Map<String,String> weightMap= new LinkedHashMap<String, String>();
	     Map<String,String> natureOfGoodsMap= new LinkedHashMap<String, String>();
	     Map<String,String> commodityMap= new LinkedHashMap<String, String>();
	     Map<String,String> productMap= new LinkedHashMap<String, String>();
	     Map<String,String> awbPrifixMap= new LinkedHashMap<String, String>();
	     Map<String,String> flightOwnerMap= new LinkedHashMap<String, String>();
	     Map<String,String> flightNumberMap= new LinkedHashMap<String, String>();
	     Map<String,String> payTypeMap= new LinkedHashMap<String, String>();
	     Map<String,String> splitIndicatorMap= new LinkedHashMap<String, String>();
	     Map<String,String> flightTypeMap= new LinkedHashMap<String, String>();
	     Map<String,String> unNumberMap= new LinkedHashMap<String, String>();
	     Map<String,String> pkginsMap= new LinkedHashMap<String, String>();
	     Map<String,String> dateMap= new LinkedHashMap<String, String>();
	     Map<String,String> airlineGrpMap= new LinkedHashMap<String, String>();
	     Map<String,String> agtMap= new LinkedHashMap<String, String>();
	     Map<String,String> agtGroupMap= new LinkedHashMap<String, String>();
	     //added by A-5799 for IASCB-23507 starts
	     Map<String,String> srvCrgClsMap= new LinkedHashMap<String, String>();
	     Map<String,String> aircraftClassMap = new LinkedHashMap<String, String>();
	     Map<String,String> shipperMap = new LinkedHashMap<String, String>();
	     Map<String,String> consigneeMap = new LinkedHashMap<String, String>();
	     Map<String,String> shpGroupMap = new LinkedHashMap<String, String>();
	     Map<String,String> cnsGroupMap = new LinkedHashMap<String, String>();
	     Map<String,String> shpTypMap = new LinkedHashMap<String, String>();
	     Map<String,String> consolMap = new LinkedHashMap<String, String>();
	     //added by A-5799 for IASCB-23507 ends
	     
	     List<String>  submodules=null;
		 String columnValue=null;
		 StringBuilder columnValueStr= null;
		 Double doubleValue=null;
	     if(null !=this.getLeftPanelParameters()){
	    	 EmbargoLeftPanelParameterVO leftPanelParameter= null;
			 for(OneTimeVO  oneTimeVO:this.getLeftPanelParameters()){
				leftPanelParameter= new EmbargoLeftPanelParameterVO();
				leftPanelParameter.setFieldValue(oneTimeVO.getFieldValue());
				leftPanelParameter.setFieldDescription(oneTimeVO.getFieldDescription());
				leftPanelParameterVOs.add(leftPanelParameter);
				leftPanelParameterMap.put(oneTimeVO.getFieldValue(), leftPanelParameter);
			}
	     }
	     if(null !=this.getParameterCodes()){
	    	 EmbargoParameterVO embargoParameterVO= null;
			 for(OneTimeVO  oneTimeVO:this.getParameterCodes()){
				 embargoParameterVO= new EmbargoParameterVO();
				 embargoParameterVO.setParameterCode(oneTimeVO.getFieldValue());
				 embargoParameterVO.setParameterDescription(oneTimeVO.getFieldDescription());
				 parameters.add(embargoParameterVO);
				 parameterMap.put(embargoParameterVO.getParameterCode(), embargoParameterVO);
			}
	     }
	     if(null !=this.getCategories()){
			for(OneTimeVO  oneTimeVO:this.getCategories()){
				categoryMasterMap.put(oneTimeVO.getFieldValue(), oneTimeVO.getFieldDescription());
			}
	     }
	     if(null !=this.getComplianceTypes()){
			for(OneTimeVO  oneTimeVO:this.getComplianceTypes()){
				complianceTypeMasterMap.put(oneTimeVO.getFieldValue(), oneTimeVO.getFieldDescription());
			}
		 }
	     if(null !=this.getFlightTypes()){
			for(OneTimeVO  oneTimeVO:this.getFlightTypes()){
				flightTypeMasterMap.put(oneTimeVO.getFieldValue(), oneTimeVO.getFieldDescription());
			}
		 }	     
		if(null !=rs){
		     while (rs.next()) {
		    	embargoDetailsVO = new EmbargoDetailsVO();
		 		setEmbargoDetails(rs, embargoDetailsVO, categoryMasterMap,complianceTypeMasterMap, flightTypeMasterMap);
		 		//setProcessTypes(rs, embargoDetailsVO);		 		
		 		embargoDetailsVOs.add(embargoDetailsVO);

		 		/**Left panel parameter creation**/
		 		/**COMPLIANCE TYPE*/
		 		columnValue=rs.getString("CMPTYP");
		 		if(null !=columnValue && columnValue.length()>0 && !columnValue.equals(VALUE_ALL)){
		 			complianceTypeMap.put(columnValue.trim(), complianceTypeMasterMap.get(columnValue.trim()));
		 		}
		 		/**CATEGORY*/
		 		columnValue=rs.getString("CATTYP");
		 		if(null !=columnValue && columnValue.length()>0 && !columnValue.equals(VALUE_ALL)){
		 			categoryMap.put(columnValue.trim(),categoryMasterMap.get(columnValue.trim()));
		 		}
		 		/**AIRPORT*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("ORGARPCODINC") && rs.getString("ORGARPCODINC").length()>0){
		 			columnValueStr.append(rs.getString("ORGARPCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("ORGARPCODEXC") && rs.getString("ORGARPCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("ORGARPCODEXC")).append(",");
		 		}
		 		if(null !=rs.getString("VIAARPCODINC") && rs.getString("VIAARPCODINC").length()>0){
		 			columnValueStr.append(rs.getString("VIAARPCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("VIAARPCODEXC") && rs.getString("VIAARPCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("VIAARPCODEXC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTARPCODINC") && rs.getString("DSTARPCODINC").length()>0){
		 			columnValueStr.append(rs.getString("DSTARPCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTARPCODEXC") && rs.getString("DSTARPCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("DSTARPCODEXC"));
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String airport:columnValue.split(",")){
		 				if(null !=airport && airport.length()>0 && !airport.equals(VALUE_ALL)){
		 					airportMap.put(airport.trim(), airport.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**AIRPORT GROUP*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("ORGARPGRPINC") && rs.getString("ORGARPGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("ORGARPGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("ORGARPGRPEXC") && rs.getString("ORGARPGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("ORGARPGRPEXC")).append(",");
		 		}
		 		if(null !=rs.getString("VIAARPGRPINC") && rs.getString("VIAARPGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("VIAARPGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("VIAARPGRPEXC") && rs.getString("VIAARPGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("VIAARPGRPEXC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTARPGRPINC") && rs.getString("DSTARPGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("DSTARPGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTARPGRPEXC") && rs.getString("DSTARPGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("DSTARPGRPEXC"));
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String airportGroup:columnValue.split(",")){
		 				if(null !=airportGroup && airportGroup.length()>0 && !airportGroup.equals(VALUE_ALL)){
		 					airportGroupMap.put(airportGroup.trim(), airportGroup.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**COUNTRY*/
		 		columnValueStr= new StringBuilder();			
		 		if(null !=rs.getString("ORGCNTCODINC") && rs.getString("ORGCNTCODINC").length()>0){
		 			columnValueStr.append(rs.getString("ORGCNTCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("ORGCNTCODEXC") && rs.getString("ORGCNTCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("ORGCNTCODEXC")).append(",");
		 		}
		 		if(null !=rs.getString("VIACNTCODINC") && rs.getString("VIACNTCODINC").length()>0){
		 			columnValueStr.append(rs.getString("VIACNTCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("VIACNTCODEXC") && rs.getString("VIACNTCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("VIACNTCODEXC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTCNTCODINC") && rs.getString("DSTCNTCODINC").length()>0){
		 			columnValueStr.append(rs.getString("DSTCNTCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTCNTCODEXC") && rs.getString("DSTCNTCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("DSTCNTCODEXC"));
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String country:columnValue.split(",")){
		 				if(null !=country && country.length()>0 && !country.equals(VALUE_ALL)){
		 					countryMap.put(country.trim(), country.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**COUNTRY GROUP*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("ORGCNTGRPINC") && rs.getString("ORGCNTGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("ORGCNTGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("ORGCNTGRPEXC") && rs.getString("ORGCNTGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("ORGCNTGRPEXC")).append(",");
		 		}
		 		if(null !=rs.getString("VIACNTGRPINC") && rs.getString("VIACNTGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("VIACNTGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("VIACNTGRPEXC") && rs.getString("VIACNTGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("VIACNTGRPEXC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTCNTGRPINC") && rs.getString("DSTCNTGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("DSTCNTGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTCNTGRPEXC") && rs.getString("DSTCNTGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("DSTCNTGRPEXC"));
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String countryGroup:columnValue.split(",")){
		 				if(null !=countryGroup && countryGroup.length()>0 && !countryGroup.equals(VALUE_ALL)){
		 					countryGroupMap.put(countryGroup.trim(), countryGroup.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**SCC*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SCCCODINC") && rs.getString("SCCCODINC").length()>0){
		 			columnValueStr.append(rs.getString("SCCCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("SCCCODEXC") && rs.getString("SCCCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("SCCCODEXC")).append(",");
		 		}
		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String scc:columnValue.split(",")){
		 				if(null !=scc && scc.length()>0 && !scc.equals(VALUE_ALL)){
		 					sccMap.put(scc.trim(), scc.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**SCC Group*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SCCGRPINC") && rs.getString("SCCGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("SCCGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("SCCGRPEXC") && rs.getString("SCCGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("SCCGRPEXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String sccGrp:columnValue.split(",")){
		 				if(null !=sccGrp && sccGrp.length()>0 && !sccGrp.equals(VALUE_ALL)){
		 					sccGroupMap.put(sccGrp.trim(), sccGrp.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**AGT*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("AGTCODINC") && rs.getString("AGTCODINC").length()>0){
		 			columnValueStr.append(rs.getString("AGTCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("AGTCODEXC") && rs.getString("AGTCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("AGTCODEXC")).append(",");
		 		}
		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String agt:columnValue.split(",")){
		 				if(null !=agt && agt.length()>0 && !agt.equals(VALUE_ALL)){
		 					agtMap.put(agt.trim(), agt.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**AGT Group*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("AGTGRPINC") && rs.getString("AGTGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("AGTGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("AGTGRPEXC") && rs.getString("AGTGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("AGTGRPEXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String agtGrp:columnValue.split(",")){
		 				if(null !=agtGrp && agtGrp.length()>0 && !agtGrp.equals(VALUE_ALL)){
		 					agtGroupMap.put(agtGrp.trim(), agtGrp.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**TIME **/
		 		if(null !=rs.getString("ORGSTRTIM") && rs.getString("ORGSTRTIM").length()>0){
		 			if(rs.getString("ORGSTRTIM").length()>5){
		 				timeMap.put(rs.getString("ORGSTRTIM").substring(0, 5),rs.getString("ORGSTRTIM").substring(0, 5));
		 				embargoDetailsVO.setOriginStartTime(rs.getString("ORGSTRTIM").substring(0, 5));
		 			}
		 		}
		 		if(null !=rs.getString("ORGENDTIM") && rs.getString("ORGENDTIM").length()>0){
		 			if(rs.getString("ORGENDTIM").length()>5){
		 				timeMap.put(rs.getString("ORGENDTIM").substring(0, 5),rs.getString("ORGENDTIM").substring(0, 5));
		 				embargoDetailsVO.setOriginEndTime(rs.getString("ORGENDTIM").substring(0, 5));
		 			}
		 		}
		 		if(null !=rs.getString("VIASTRTIM") && rs.getString("VIASTRTIM").length()>0){
		 			if(rs.getString("VIASTRTIM").length()>5){
		 				timeMap.put(rs.getString("VIASTRTIM").substring(0, 5),rs.getString("VIASTRTIM").substring(0, 5));
		 				embargoDetailsVO.setViaPointStartTime(rs.getString("VIASTRTIM").substring(0, 5));
		 			}
		 		}
		 		if(null !=rs.getString("VIAENDTIM") && rs.getString("VIAENDTIM").length()>0){
		 			if(rs.getString("VIAENDTIM").length()>5){
		 				timeMap.put(rs.getString("VIAENDTIM").substring(0, 5),rs.getString("VIAENDTIM").substring(0, 5));
		 				embargoDetailsVO.setViaPointEndTime(rs.getString("VIAENDTIM").substring(0, 5));
		 			}
		 		}
		 		if(null !=rs.getString("DSTSTRTIM") && rs.getString("DSTSTRTIM").length()>0){
		 			if(rs.getString("DSTSTRTIM").length()>5){
		 				timeMap.put(rs.getString("DSTSTRTIM").substring(0, 5),rs.getString("DSTSTRTIM").substring(0, 5));
		 				embargoDetailsVO.setDestinationStartTime(rs.getString("DSTSTRTIM").substring(0, 5));
		 			}
		 		}
		 		if(null !=rs.getString("DSTENDTIM") && rs.getString("DSTENDTIM").length()>0){
		 			if(rs.getString("DSTENDTIM").length()>5){
		 				timeMap.put(rs.getString("DSTENDTIM").substring(0, 5),rs.getString("DSTENDTIM").substring(0, 5));
		 				embargoDetailsVO.setDestinationEndTime(rs.getString("DSTENDTIM").substring(0, 5));
		 			}
		 		}		 		
		 		/**CARRIER */
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("ARLCODINC") && rs.getString("ARLCODINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLCODINC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLCODEXC") && rs.getString("ARLCODEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLCODEXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String carrier:columnValue.split(",")){
		 				if(null !=carrier && carrier.length()>0 && !carrier.equals(VALUE_ALL)){
		 					carrierMap.put(carrier.trim(), carrier.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**HEIGHT*/	 		
		 		if(null !=rs.getString("HGTSTR") && rs.getString("HGTSTR").length()>0){
		 			doubleValue= new Double(rs.getString("HGTSTR"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					heightMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setHeightStart(String.format( "%.2f", doubleValue));					
		 		}
		 		if(null !=rs.getString("HGTEND") && rs.getString("HGTEND").length()>0){
		 			doubleValue= new Double(rs.getString("HGTEND"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					heightMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setHeightEnd(String.format( "%.2f", doubleValue));
		 		}		 		
		 		/**WIDTH*/
		 		if(null !=rs.getString("WIDSTR") && rs.getString("WIDSTR").length()>0){
		 			doubleValue= new Double(rs.getString("WIDSTR"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					widthMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setWidthStart(String.format( "%.2f", doubleValue));
		 		}
		 		if(null !=rs.getString("WIDEND") && rs.getString("WIDEND").length()>0){
		 			doubleValue= new Double(rs.getString("WIDEND"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					widthMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setWidthEnd(String.format( "%.2f", doubleValue));
		 		}		 		
		 		
		 		/**LENGTH*/	 		
		 		if(null !=rs.getString("LENSTR") && rs.getString("LENSTR").length()>0){
		 			doubleValue= new Double(rs.getString("LENSTR"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					lengthMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setLengthStart(String.format( "%.2f", doubleValue));
		 		}
		 		if(null !=rs.getString("LENEND") && rs.getString("LENEND").length()>0){
		 			doubleValue= new Double(rs.getString("LENEND"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					lengthMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setLengthEnd(String.format( "%.2f", doubleValue));
		 		}
		 		/**WEIGHT*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("WGTSTR") && rs.getString("WGTSTR").length()>0){
		 			doubleValue= new Double(rs.getString("WGTSTR"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					weightMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setWeightStart(String.format( "%.2f", doubleValue));
		 		}
		 		if(null !=rs.getString("WGTEND") && rs.getString("WGTEND").length()>0){
		 			doubleValue= new Double(rs.getString("WGTEND"));
 					doubleValue=Math.round(doubleValue * 100.0) / 100.0;
 					weightMap.put(String.format( "%.2f", doubleValue),String.format( "%.2f", doubleValue));
 					embargoDetailsVO.setWeightEnd(String.format( "%.2f", doubleValue));
		 		}		 		
		 		
		 		/**NATURE OF GOODS*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("NATINC") && rs.getString("NATINC").length()>0){
		 			columnValueStr.append(rs.getString("NATINC")).append(",");
		 		}	
		 		if(null !=rs.getString("NATEXC") && rs.getString("NATEXC").length()>0){
		 			columnValueStr.append(rs.getString("NATEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String nat:columnValue.split(",")){
		 				if(null !=nat && nat.length()>0 && !nat.equals(VALUE_ALL)){
		 					natureOfGoodsMap.put(nat.trim(), nat.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**COMMODITY*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("COMINC") && rs.getString("COMINC").length()>0){
		 			columnValueStr.append(rs.getString("COMINC")).append(",");
		 		}
		 		if(null !=rs.getString("COMEXC") && rs.getString("COMEXC").length()>0){
		 			columnValueStr.append(rs.getString("COMEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String com:columnValue.split(",")){
		 				if(null !=com && com.length()>0 && !com.equals(VALUE_ALL)){
		 					commodityMap.put(com.trim(), com.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**PRODUCT*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("PRDINC") && rs.getString("PRDINC").length()>0){
		 			columnValueStr.append(rs.getString("PRDINC")).append(",");
		 		}
		 		if(null !=rs.getString("PRDEXC") && rs.getString("PRDEXC").length()>0){
		 			columnValueStr.append(rs.getString("PRDEXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String product:columnValue.split(",")){
		 				if(null !=product && product.length()>0 && !product.equals(VALUE_ALL)){
		 					productMap.put(product.trim(), product.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**AWB PRIFIX*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("AWBPFXINC") && rs.getString("AWBPFXINC").length()>0){
		 			columnValueStr.append(rs.getString("AWBPFXINC")).append(",");
		 		}
		 		if(null !=rs.getString("AWBPFXEXC") && rs.getString("AWBPFXEXC").length()>0){
		 			columnValueStr.append(rs.getString("AWBPFXEXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String awb:columnValue.split(",")){
		 				if(null !=awb && awb.length()>0 && !awb.equals(VALUE_ALL)){
		 					awbPrifixMap.put(awb.trim(), awb.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**FLIGHT NUMBER*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("FLTNUMINC") && rs.getString("FLTNUMINC").length()>0){
		 			columnValueStr.append(rs.getString("FLTNUMINC")).append(",");
		 		}	
		 		if(null !=rs.getString("FLTNUMEXC") && rs.getString("FLTNUMEXC").length()>0){
		 			columnValueStr.append(rs.getString("FLTNUMEXC")).append(",");
		 		}	
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String flightNo:columnValue.split(",")){
		 				if(null !=flightNo && flightNo.length()>0 && !flightNo.equals(VALUE_ALL)){
		 					flightNumberMap.put(flightNo.trim(), flightNo.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**PAY TYPE*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("PAYTYPINC") && rs.getString("PAYTYPINC").length()>0){
		 			columnValueStr.append(rs.getString("PAYTYPINC")).append(",");
		 		}
		 		if(null !=rs.getString("PAYTYPEXC") && rs.getString("PAYTYPEXC").length()>0){
		 			columnValueStr.append(rs.getString("PAYTYPEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String payType:columnValue.split(",")){
		 				if(null !=payType && payType.length()>0 && !payType.equals(VALUE_ALL)){
		 					payTypeMap.put(payType.trim(), payType.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**FLIGHT TYPE*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("FLTTYP") && rs.getString("FLTTYP").length()>0){
		 			columnValueStr.append(rs.getString("FLTTYP")).append(",");
		 		}	 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String flightType:columnValue.split(",")){
		 				if(null !=flightType && flightType.length()>0 && !flightType.equals(VALUE_ALL)){
		 					flightTypeMap.put(flightType.trim(),flightTypeMasterMap.get(flightType.trim()));
		 				}		 				
		 			}		 			
		 		}
		 		/**SPLIT INDICATOR*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SLTIND") && rs.getString("SLTIND").length()>0){
		 			columnValueStr.append(rs.getString("SLTIND")).append(",");
		 		}	 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String splitIndicator:columnValue.split(",")){
		 				if(null !=splitIndicator && splitIndicator.length()>0 && !splitIndicator.equals(VALUE_ALL)){
		 					splitIndicatorMap.put(splitIndicator.trim(), splitIndicator.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**FLIGHT OWNER*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("FLTOWRINC") && rs.getString("FLTOWRINC").length()>0){
		 			columnValueStr.append(rs.getString("FLTOWRINC")).append(",");
		 		}
		 		if(null !=rs.getString("FLTOWREXC") && rs.getString("FLTOWREXC").length()>0){
		 			columnValueStr.append(rs.getString("FLTOWREXC")).append(",");
		 		}		 		
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String flightOwner:columnValue.split(",")){
		 				if(null !=flightOwner && flightOwner.length()>0 && !flightOwner.equals(VALUE_ALL)){
		 					flightOwnerMap.put(flightOwner.trim(), flightOwner.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**UN NUMBER*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("UNDNUMINC") && rs.getString("UNDNUMINC").length()>0){
		 			columnValueStr.append(rs.getString("UNDNUMINC")).append(",");
		 		}
		 		if(null !=rs.getString("UNDNUMEXC") && rs.getString("UNDNUMEXC").length()>0){
		 			columnValueStr.append(rs.getString("UNDNUMEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String unNumber:columnValue.split(",")){
		 				if(null !=unNumber && unNumber.length()>0 && !unNumber.equals(VALUE_ALL)){
		 					unNumberMap.put(unNumber.trim(), unNumber.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**UNID PI**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("PKGINSINC") && rs.getString("PKGINSINC").length()>0){
		 			columnValueStr.append(rs.getString("PKGINSINC")).append(",");
		 		}
		 		if(null !=rs.getString("PKGINSEXC") && rs.getString("PKGINSEXC").length()>0){
		 			columnValueStr.append(rs.getString("PKGINSEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String pkgins:columnValue.split(",")){
		 				if(null !=pkgins && pkgins.length()>0 && !pkgins.equals(VALUE_ALL)){
		 					pkginsMap.put(pkgins.trim(), pkgins.trim());
		 				}		 				
		 			}		 			
		 		}
		 		/**DATE*/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("ORGDATINC") && rs.getString("ORGDATINC").length()>0){
		 			columnValueStr.append(rs.getString("ORGDATINC")).append(",");
		 		}
		 		if(null !=rs.getString("ORGDATEXC") && rs.getString("ORGDATEXC").length()>0){
		 			columnValueStr.append(rs.getString("ORGDATEXC")).append(",");
		 		}
		 		if(null !=rs.getString("VIADATINC") && rs.getString("VIADATINC").length()>0){
		 			columnValueStr.append(rs.getString("VIADATINC")).append(",");
		 		}
		 		if(null !=rs.getString("VIADATEXC") && rs.getString("VIADATEXC").length()>0){
		 			columnValueStr.append(rs.getString("VIADATEXC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTDATINC") && rs.getString("DSTDATINC").length()>0){
		 			columnValueStr.append(rs.getString("DSTDATINC")).append(",");
		 		}
		 		if(null !=rs.getString("DSTDATEXC") && rs.getString("DSTDATEXC").length()>0){
		 			columnValueStr.append(rs.getString("DSTDATEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String date:columnValue.split(",")){
		 				if(null !=date && date.length()>0 && !date.equals(VALUE_ALL)){
		 					dateMap.put(date.trim(), date.trim());
		 				}		 				
		 			}		 			
		 		}
		 		 /**ARLGRP*/
			 	columnValueStr= new StringBuilder();
			 	
			 	if(null !=rs.getString("ARLGRPORGINC") && rs.getString("ARLGRPORGINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPORGINC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPORGEXC") && rs.getString("ARLGRPORGEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPORGEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPDSTEXC") && rs.getString("ARLGRPDSTEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPDSTEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPDSTINC") && rs.getString("ARLGRPDSTINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPDSTINC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPVIAINC") && rs.getString("ARLGRPVIAINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPVIAINC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPVIAEXC") && rs.getString("ARLGRPVIAEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPVIAEXC")).append(",");
		 		}	
		 		if(null !=rs.getString("ARLGRPALLINC") && rs.getString("ARLGRPALLINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPALLINC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPALLEXC") && rs.getString("ARLGRPALLEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPALLEXC")).append(",");
		 		}	
		 		if(null !=rs.getString("ARLGRPANYEXC") && rs.getString("ARLGRPANYEXC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPANYEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ARLGRPANYINC") && rs.getString("ARLGRPANYINC").length()>0){
		 			columnValueStr.append(rs.getString("ARLGRPANYINC")).append(",");
		 		}	
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String airlineGrp:columnValue.split(",")){
		 				if(null !=airlineGrp && airlineGrp.length()>0 && !airlineGrp.equals(VALUE_ALL)){
		 					airlineGrpMap.put(airlineGrp.trim(), airlineGrp.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		 /**ACRCLS*/
			 	columnValueStr= new StringBuilder();
			 	
			 	if(null !=rs.getString("ACRCLSORGINC") && rs.getString("ACRCLSORGINC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSORGINC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSORGEXC") && rs.getString("ACRCLSORGEXC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSORGEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSDSTEXC") && rs.getString("ACRCLSDSTEXC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSDSTEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSDSTINC") && rs.getString("ACRCLSDSTINC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSDSTINC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSVIAINC") && rs.getString("ACRCLSVIAINC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSVIAINC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSVIAEXC") && rs.getString("ACRCLSVIAEXC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSVIAEXC")).append(",");
		 		}	
		 		if(null !=rs.getString("ACRCLSALLINC") && rs.getString("ACRCLSALLINC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSALLINC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSALLEXC") && rs.getString("ACRCLSALLEXC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSALLEXC")).append(",");
		 		}	
		 		if(null !=rs.getString("ACRCLSANYEXC") && rs.getString("ACRCLSANYEXC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSANYEXC")).append(",");
		 		}
		 		if(null !=rs.getString("ACRCLSANYINC") && rs.getString("ACRCLSANYINC").length()>0){
		 			columnValueStr.append(rs.getString("ACRCLSANYINC")).append(",");
		 		}	
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String airlineGrp:columnValue.split(",")){
		 				if(null !=airlineGrp && airlineGrp.length()>0 && !airlineGrp.equals(VALUE_ALL)){
		 					aircraftClassMap.put(airlineGrp.trim(), airlineGrp.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**SRVCRGCLS**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SRVCRGCLSINC") && rs.getString("SRVCRGCLSINC").length()>0){
		 			columnValueStr.append(rs.getString("SRVCRGCLSINC")).append(",");
		 		}
		 		if(null !=rs.getString("SRVCRGCLSEXC") && rs.getString("SRVCRGCLSEXC").length()>0){
		 			columnValueStr.append(rs.getString("SRVCRGCLSEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String SRVCRGCLS:columnValue.split(",")){
		 				if(null !=SRVCRGCLS && SRVCRGCLS.length()>0 && !SRVCRGCLS.equals(VALUE_ALL)){
		 					srvCrgClsMap.put(SRVCRGCLS.trim(), SRVCRGCLS.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**SHP**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SHPINC") && rs.getString("SHPINC").length()>0){
		 			columnValueStr.append(rs.getString("SHPINC")).append(",");
		 		}
		 		if(null !=rs.getString("SHPEXC") && rs.getString("SHPEXC").length()>0){
		 			columnValueStr.append(rs.getString("SHPEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String SHP:columnValue.split(",")){
		 				if(null !=SHP && SHP.length()>0 && !SHP.equals(VALUE_ALL)){
		 					shipperMap.put(SHP.trim(), SHP.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**CNS**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("CNSINC") && rs.getString("CNSINC").length()>0){
		 			columnValueStr.append(rs.getString("CNSINC")).append(",");
		 		}
		 		if(null !=rs.getString("CNSEXC") && rs.getString("CNSEXC").length()>0){
		 			columnValueStr.append(rs.getString("CNSEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String CNS:columnValue.split(",")){
		 				if(null !=CNS && CNS.length()>0 && !CNS.equals(VALUE_ALL)){
		 					consigneeMap.put(CNS.trim(), CNS.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**SHPGRP**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SHPGRPINC") && rs.getString("SHPGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("SHPGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("SHPGRPEXC") && rs.getString("SHPGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("SHPGRPEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String SHPGRP:columnValue.split(",")){
		 				if(null !=SHPGRP && SHPGRP.length()>0 && !SHPGRP.equals(VALUE_ALL)){
		 					shpGroupMap.put(SHPGRP.trim(), SHPGRP.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**CNSGRP**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("CNSGRPINC") && rs.getString("CNSGRPINC").length()>0){
		 			columnValueStr.append(rs.getString("CNSGRPINC")).append(",");
		 		}
		 		if(null !=rs.getString("CNSGRPEXC") && rs.getString("CNSGRPEXC").length()>0){
		 			columnValueStr.append(rs.getString("CNSGRPEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String CNSGRP:columnValue.split(",")){
		 				if(null !=CNSGRP && CNSGRP.length()>0 && !CNSGRP.equals(VALUE_ALL)){
		 					cnsGroupMap.put(CNSGRP.trim(), CNSGRP.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		/**SHPTYP**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("SHPTYPINC") && rs.getString("SHPTYPINC").length()>0){
		 			columnValueStr.append(rs.getString("SHPTYPINC")).append(",");
		 		}
		 		if(null !=rs.getString("SHPTYPEXC") && rs.getString("SHPTYPEXC").length()>0){
		 			columnValueStr.append(rs.getString("SHPTYPEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String SHPTYP:columnValue.split(",")){
		 				if(null !=SHPTYP && SHPTYP.length()>0 && !SHPTYP.equals(VALUE_ALL)){
		 					shpTypMap.put(SHPTYP.trim(), SHPTYP.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		
		 		/**CNSL**/
		 		columnValueStr= new StringBuilder();
		 		if(null !=rs.getString("CNSLINC") && rs.getString("CNSLINC").length()>0){
		 			columnValueStr.append(rs.getString("CNSLINC")).append(",");
		 		}
		 		if(null !=rs.getString("CNSLEXC") && rs.getString("CNSLEXC").length()>0){
		 			columnValueStr.append(rs.getString("CNSLEXC")).append(",");
		 		}
		 		columnValue=columnValueStr.toString();
		 		if(null !=columnValue && columnValue.length()>0){
		 			for(String CNSL:columnValue.split(",")){
		 				if(null !=CNSL && CNSL.length()>0 && !CNSL.equals(VALUE_ALL)){
		 					consolMap.put(CNSL.trim(), CNSL.trim());
		 				}		 				
		 			}		 			
		 		}
		 		
		 		
		     }
		    
		     
		     /*** LEFT PANEL CREATION**/
		     if(null !=leftPanelParameterMap  && leftPanelParameterMap.size()>0 ){
		    	 if(null !=leftPanelParameterMap.get(LEFT_PANEL_CATEGORY) && categoryMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:categoryMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(categoryMap.get(key));
			    		 }		    		 
			    	 }
			    	 leftPanelParameterMap.get(LEFT_PANEL_CATEGORY).setSubModules(submodules);
			 	 }
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_COMPLIANCE_TYPE) && complianceTypeMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:complianceTypeMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(complianceTypeMap.get(key));
			    		 }
			    	 }
			    	 leftPanelParameterMap.get(LEFT_PANEL_COMPLIANCE_TYPE).setSubModules(submodules);
			 	 }
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_AIRPORT) && airportMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:airportMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 Collections.sort(submodules);
			    	 leftPanelParameterMap.get(LEFT_PANEL_AIRPORT).setSubModules(submodules);
			 	 }
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_AIRPORT_GROUP) && airportGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:airportGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 leftPanelParameterMap.get(LEFT_PANEL_AIRPORT_GROUP).setSubModules(submodules);
			 	 }
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_COUNTRY) && countryMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:countryMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 Collections.sort(submodules);
			    	 leftPanelParameterMap.get(LEFT_PANEL_COUNTRY).setSubModules(submodules);
			 	 }
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_COUNTRY_GROUP) && countryGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:countryGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 leftPanelParameterMap.get(LEFT_PANEL_COUNTRY_GROUP).setSubModules(submodules);
			 	 }
			     
			     /**SCC**/
			     if(null !=parameterMap.get(PARAMETER_CODE_SCC) && sccMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:sccMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 Collections.sort(submodules);
			    	 parameterMap.get(PARAMETER_CODE_SCC).setSubModules(submodules);
			 	 }
			     /**SCC Group*/
			     if(null !=parameterMap.get(PARAMETER_CODE_SCC_GROUP) && sccGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:sccGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_SCC_GROUP).setSubModules(submodules);
			 	 }
			     /**Agent*/
			     if(null !=parameterMap.get(PARAMETER_CODE_AGT) && agtMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:agtMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 Collections.sort(submodules);
			    	 parameterMap.get(PARAMETER_CODE_AGT).setSubModules(submodules);
			 	 }
			     /**Agent Group*/
			     if(null !=parameterMap.get(PARAMETER_CODE_AGT_GROUP) && agtGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:agtGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_AGT_GROUP).setSubModules(submodules);
			 	 }
			     /**TIME **/
			     if(null !=parameterMap.get(PARAMETER_CODE_TIME) && timeMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:timeMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_TIME).setSubModules(submodules);
			 	 }
			     /**CARRIER */
			     if(null !=parameterMap.get(PARAMETER_CODE_CARRIER) && carrierMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:carrierMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_CARRIER).setSubModules(submodules);
			 	 }
			     /**HEIGHT*/
			     if(null !=parameterMap.get(PARAMETER_CODE_HEIGHT) && heightMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:heightMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_HEIGHT).setSubModules(submodules);
			 	 }
			     /**WIDTH*/
			     if(null !=parameterMap.get(PARAMETER_CODE_WIDTH) && widthMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:widthMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_WIDTH).setSubModules(submodules);
			 	 }
			     /**LENGTH*/
			     if(null !=parameterMap.get(PARAMETER_CODE_LENGTH) && lengthMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:lengthMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_LENGTH).setSubModules(submodules);
			 	 }
			     /**WEIGHT*/
			     if(null !=parameterMap.get(PARAMETER_CODE_WEIGHT) && weightMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:weightMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_WEIGHT).setSubModules(submodules);
			 	 }
			     /**NATURE OF GOODS*/
			     if(null !=parameterMap.get(PARAMETER_CODE_NAT) && natureOfGoodsMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:natureOfGoodsMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_NAT).setSubModules(submodules);
			 	 }
			     /**COMMODITY*/
			     if(null !=parameterMap.get(PARAMETER_CODE_COM) && commodityMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:commodityMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_COM).setSubModules(submodules);
			 	 }
			     /**PRODUCT*/
			     if(null !=parameterMap.get(PARAMETER_CODE_PRD) && productMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:productMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 Collections.sort(submodules);
			    	 parameterMap.get(PARAMETER_CODE_PRD).setSubModules(submodules);
			 	 }
			     /**AWB PRIFIX*/
			     if(null !=parameterMap.get(PARAMETER_CODE_AWBPFX) && awbPrifixMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:awbPrifixMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_AWBPFX).setSubModules(submodules);
			 	 }
			     /**FLIGHT NUMBER*/
			     if(null !=parameterMap.get(PARAMETER_CODE_FLTNUM) && flightNumberMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 String flightNumber=null;
			    	 for(String key:flightNumberMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 flightNumber=key.split("~").length>0?key.split("~")[1]:"";
			    			 submodules.add(key.split("~")[0]+" " +flightNumber);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_FLTNUM).setSubModules(submodules);
			 	 }
			     /**PAY TYPE*/
			     if(null !=parameterMap.get(PARAMETER_CODE_PAYTYP) && payTypeMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:payTypeMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_PAYTYP).setSubModules(submodules);
			 	 }
			     /**FLIGHT TYPE*/
			     if(null !=parameterMap.get(PARAMETER_CODE_FLTTYP) && flightTypeMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:flightTypeMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(flightTypeMasterMap.get(key));
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_FLTTYP).setSubModules(submodules);
			 	 }
			     /**SPLIT INDICATOR*/
			     if(null !=parameterMap.get(PARAMETER_CODE_SLTIND) && splitIndicatorMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:splitIndicatorMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_SLTIND).setSubModules(submodules);
			 	 }
			     /**UN NUMBER*/
			     if(null !=parameterMap.get(PARAMETER_CODE_UNDNUM) && unNumberMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:unNumberMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_UNDNUM).setSubModules(submodules);
			 	 }
			     /**UNID PI*/
			     if(null !=parameterMap.get(PARAMETER_CODE_PKGINS) && pkginsMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:pkginsMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_PKGINS).setSubModules(submodules);
			 	 }
			     /**DATE*/
			     if(null !=parameterMap.get(PARAMETER_CODE_DATE) && dateMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:dateMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_DATE).setSubModules(submodules);
			 	 }
			     /**FLIGHT OWNER**/
			     if(null !=parameterMap.get(PARAMETER_CODE_FLTOWN) && flightOwnerMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:flightOwnerMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_FLTOWN).setSubModules(submodules);
			 	 }
			     /**AIRLINE GROUP**/
			     if(null !=parameterMap.get(PARAMETER_CODE_ARLGRP) && airlineGrpMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:airlineGrpMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(PARAMETER_CODE_ARLGRP).setSubModules(submodules);
			 	 }
			     
			     /**SRVCRGCLS*/
			     if(null !=parameterMap.get(SERVICE_CARGO_CLASS) && srvCrgClsMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:srvCrgClsMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(SERVICE_CARGO_CLASS).setSubModules(submodules);
			 	 }
			     
			     /**ACRCLS*/
			     if(null !=parameterMap.get(AIRCRAFT_CLASSIFICATION) && aircraftClassMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:aircraftClassMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(AIRCRAFT_CLASSIFICATION).setSubModules(submodules);
			 	 }
			     
			     /**SHP*/
			     if(null !=parameterMap.get(SHIPPER) && shipperMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:shipperMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(SHIPPER).setSubModules(submodules);
			 	 }
			     
			     /**CNS*/
			     if(null !=parameterMap.get(CONSIGNEE) && consigneeMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:consigneeMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(CONSIGNEE).setSubModules(submodules);
			 	 }
			     
			     /**SHPGRP*/
			     if(null !=parameterMap.get(SHIPPER_GROUP) && shpGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:shpGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(SHIPPER_GROUP).setSubModules(submodules);
			 	 }
			     
			     /**CNSGRP*/
			     if(null !=parameterMap.get(CONSIGNEE_GROUP) && cnsGroupMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:cnsGroupMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(CONSIGNEE_GROUP).setSubModules(submodules);
			 	 }
			     
			     /**SHPTYP*/
			     if(null !=parameterMap.get(SHIPMENT_TYPE) && shpTypMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:shpTypMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(SHIPMENT_TYPE).setSubModules(submodules);
			 	 }
			     
			     /**CNSL*/
			     if(null !=parameterMap.get(CONSOL) && consolMap.size()>0){
			    	 submodules= new ArrayList<String>();
			    	 for(String key:consolMap.keySet()){
			    		 if(null !=key && key.length()>0){
			    			 submodules.add(key);
			    		 }
			    	 }
			    	 parameterMap.get(CONSOL).setSubModules(submodules);
			 	 }
			     
			     /**PARAMETERS**/
			     if(null !=leftPanelParameterMap.get(LEFT_PANEL_PARAMETERS)){
			    	 leftPanelParameterMap.get(LEFT_PANEL_PARAMETERS).setParameters(parameters);
			 	 }
		     }
		     	
		}
		this.embargoSearchVO.setEmbargoLeftPanelParameterVOs(leftPanelParameterVOs);
	    return embargoDetailsVOs;
	}

	private void setProcessTypes(ResultSet rs, EmbargoDetailsVO embargoDetailsVO)
			throws SQLException {
		StringBuilder processTypeOrigin= new StringBuilder().append(LABEL_ORIGIN).append(" : ");
		StringBuilder processTypeDestination=new StringBuilder().append(LABEL_DESTINATION).append(" : ");
		StringBuilder processTypeViaPoint=new StringBuilder().append(LABEL_VIAPOINT).append(" : ");
		boolean hasInclude=false;
		boolean hasExclude=false;
		/**Process Type : Origin **/
		if(null !=rs.getString("ORGARPCODINC") && rs.getString("ORGARPCODINC").length()>0 
				&& !rs.getString("ORGARPCODINC").equals(VALUE_ALL)){
			processTypeOrigin.append(rs.getString("ORGARPCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("ORGARPGRPINC") && rs.getString("ORGARPGRPINC").length()>0 
				&& !rs.getString("ORGARPGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGARPGRPINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("ORGCNTCODINC") && rs.getString("ORGCNTCODINC").length()>0 
				&& !rs.getString("ORGCNTCODINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGCNTCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("ORGCNTGRPINC") && rs.getString("ORGCNTGRPINC").length()>0 
				&& !rs.getString("ORGCNTGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGCNTGRPINC"));
			hasInclude=true;
		}
		if(hasInclude){
			processTypeOrigin.append(LABEL_INCLUDE);
		}
		if(null !=rs.getString("ORGARPCODEXC") && rs.getString("ORGARPCODEXC").length()>0 
				&& !rs.getString("ORGARPCODEXC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGARPCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("ORGARPGRPEXC") && rs.getString("ORGARPGRPEXC").length()>0 
				&& !rs.getString("ORGARPGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGARPGRPEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("ORGCNTCODEXC") && rs.getString("ORGCNTCODEXC").length()>0 
				&& !rs.getString("ORGCNTCODEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGCNTCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("ORGCNTGRPEXC") && rs.getString("ORGCNTGRPEXC").length()>0 
				&& !rs.getString("ORGCNTGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeOrigin.append(",");
			}
			processTypeOrigin.append(rs.getString("ORGCNTGRPEXC"));
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
		if(null !=rs.getString("DSTARPCODINC") && rs.getString("DSTARPCODINC").length()>0 
				&& !rs.getString("DSTARPCODINC").equals(VALUE_ALL)){
			processTypeDestination.append(rs.getString("DSTARPCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("DSTARPGRPINC") && rs.getString("DSTARPGRPINC").length()>0 
				&& !rs.getString("DSTARPGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTARPGRPINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("DSTCNTCODINC") && rs.getString("DSTCNTCODINC").length()>0 
				&& !rs.getString("DSTCNTCODINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTCNTCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("DSTCNTGRPINC") && rs.getString("DSTCNTGRPINC").length()>0 
				&& !rs.getString("DSTCNTGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTCNTGRPINC"));
			hasInclude=true;
		}
		if(hasInclude){
			processTypeDestination.append(LABEL_INCLUDE);
		}
		if(null !=rs.getString("DSTARPCODEXC") && rs.getString("DSTARPCODEXC").length()>0 
				&& !rs.getString("DSTARPCODEXC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTARPCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("DSTARPGRPEXC") && rs.getString("DSTARPGRPEXC").length()>0 
				&& !rs.getString("DSTARPGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTARPGRPEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("DSTCNTCODEXC") && rs.getString("DSTCNTCODEXC").length()>0 
				&& !rs.getString("DSTCNTCODEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTCNTCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("DSTCNTGRPEXC") && rs.getString("DSTCNTGRPEXC").length()>0 
				&& !rs.getString("DSTCNTGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeDestination.append(",");
			}
			processTypeDestination.append(rs.getString("DSTCNTGRPEXC"));
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
		if(null !=rs.getString("VIAARPCODINC") && rs.getString("VIAARPCODINC").length()>0 
				&& !rs.getString("VIAARPCODINC").equals(VALUE_ALL)){
			processTypeViaPoint.append(rs.getString("VIAARPCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("VIAARPGRPINC") && rs.getString("VIAARPGRPINC").length()>0 
				&& !rs.getString("VIAARPGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIAARPGRPINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("VIACNTCODINC") && rs.getString("VIACNTCODINC").length()>0 
				&& !rs.getString("VIACNTCODINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIACNTCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("VIACNTGRPINC") && rs.getString("VIACNTGRPINC").length()>0 
				&& !rs.getString("VIACNTGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIACNTGRPINC"));
			hasInclude=true;
		}
		if(hasInclude){
			processTypeViaPoint.append(LABEL_INCLUDE);
		}
		if(null !=rs.getString("VIAARPCODEXC") && rs.getString("VIAARPCODEXC").length()>0 
				&& !rs.getString("VIAARPCODEXC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIAARPCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("VIAARPGRPEXC") && rs.getString("VIAARPGRPEXC").length()>0 
				&& !rs.getString("VIAARPGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIAARPGRPEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("VIACNTCODEXC") && rs.getString("VIACNTCODEXC").length()>0 
				&& !rs.getString("VIACNTCODEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIACNTCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("VIACNTGRPEXC") && rs.getString("VIACNTGRPEXC").length()>0 
				&& !rs.getString("VIACNTGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeViaPoint.append(",");
			}
			processTypeViaPoint.append(rs.getString("VIACNTGRPEXC"));
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

	private void setEmbargoDetails(ResultSet rs,
			EmbargoDetailsVO embargoDetailsVO,
			Map<String, String> categoryMasterMap,
			Map<String, String> complianceTypeMasterMap,
			Map<String, String> flightTypeMasterMap) throws SQLException {
		embargoDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		embargoDetailsVO.setEmbargoDescription(rs.getString("RECDES"));
		embargoDetailsVO.setEmbargoReferenceNumber(rs.getString("REFNUM"));
		embargoDetailsVO.setComplianceType(rs.getString("CMPTYP"));
		embargoDetailsVO.setComplianceTypeDescription(null !=rs.getString("CMPTYP")? complianceTypeMasterMap.get(rs.getString("CMPTYP")):"");
		embargoDetailsVO.setCategory(rs.getString("CATTYP"));
		embargoDetailsVO.setCategoryDescription(null !=rs.getString("CATTYP")? categoryMasterMap.get(rs.getString("CATTYP")):"");
		embargoDetailsVO.setFlightType(rs.getString("FLTTYP"));
		embargoDetailsVO.setFlightTypeDescription(null !=rs.getString("FLTTYP")? flightTypeMasterMap.get(rs.getString("FLTTYP").trim()):"");
		
		embargoDetailsVO.setOriginAirportCodeInc(rs.getString("ORGARPCODINC"));
		embargoDetailsVO.setOriginAirportCodeExc(rs.getString("ORGARPCODEXC"));
		embargoDetailsVO.setOriginAirportGroupInc(rs.getString("ORGARPGRPINC"));
		embargoDetailsVO.setOriginAirportGroupExc(rs.getString("ORGARPGRPEXC"));
		embargoDetailsVO.setOriginCountryCodeInc(rs.getString("ORGCNTCODINC"));
		embargoDetailsVO.setOriginCountryCodeExc(rs.getString("ORGCNTCODEXC"));
		embargoDetailsVO.setOriginCountryGroupInc(rs.getString("ORGCNTGRPINC"));
		embargoDetailsVO.setOriginCountryGroupExc(rs.getString("ORGCNTGRPEXC"));
		embargoDetailsVO.setViaPointAirportCodeInc(rs.getString("VIAARPCODINC"));
		embargoDetailsVO.setViaPointAirportCodeExc(rs.getString("VIAARPCODEXC"));
		embargoDetailsVO.setViaPointAirportGroupInc(rs.getString("VIAARPGRPINC"));
		embargoDetailsVO.setViaPointAirportGroupExc(rs.getString("VIAARPGRPEXC"));
		embargoDetailsVO.setViaPointCountryCodeInc(rs.getString("VIACNTCODINC"));
		embargoDetailsVO.setViaPointCountryCodeExc(rs.getString("VIACNTCODEXC"));
		embargoDetailsVO.setViaPointCountryGroupInc(rs.getString("VIACNTGRPINC"));
		embargoDetailsVO.setViaPointCountryGroupExc(rs.getString("VIACNTGRPEXC"));
		embargoDetailsVO.setDestinationAirportCodeInc(rs.getString("DSTARPCODINC"));
		embargoDetailsVO.setDestinationAirportCodeExc(rs.getString("DSTARPCODEXC"));
		embargoDetailsVO.setDestinationAirportGroupInc(rs.getString("DSTARPGRPINC"));
		embargoDetailsVO.setDestinationAirportGroupExc(rs.getString("DSTARPGRPEXC"));
		embargoDetailsVO.setDestinationCountryCodeInc(rs.getString("DSTCNTCODINC"));
		embargoDetailsVO.setDestinationCountryCodeExc(rs.getString("DSTCNTCODEXC"));
		embargoDetailsVO.setDestinationCountryGroupInc(rs.getString("DSTCNTGRPINC"));
		embargoDetailsVO.setDestinationCountryGroupExc(rs.getString("DSTCNTGRPEXC"));		 		
		embargoDetailsVO.setSccInc(rs.getString("SCCCODINC"));
		embargoDetailsVO.setSccExc(rs.getString("SCCCODEXC"));
		embargoDetailsVO.setSccGroupInc(rs.getString("SCCGRPINC"));
		embargoDetailsVO.setSccGroupExc(rs.getString("SCCGRPEXC"));
		embargoDetailsVO.setAirlineCodeInc(rs.getString("ARLCODINC"));
		embargoDetailsVO.setAirlineCodeExc(rs.getString("ARLCODEXC"));
		embargoDetailsVO.setNatureOfGoodsInc(rs.getString("NATINC"));
		embargoDetailsVO.setNatureOfGoodsExc(rs.getString("NATEXC"));
		embargoDetailsVO.setNatureOfGoods(rs.getString("NAT"));
		embargoDetailsVO.setCommodity(rs.getString("COM"));
		embargoDetailsVO.setCommodityInc(rs.getString("COMINC"));
		embargoDetailsVO.setCommodityExc(rs.getString("COMEXC"));
		embargoDetailsVO.setProductInc(rs.getString("PRDINC"));
		embargoDetailsVO.setProductExc(rs.getString("PRDEXC"));
		embargoDetailsVO.setAwbPrefixInc(rs.getString("AWBPFXINC"));
		embargoDetailsVO.setAwbPrefixExc(rs.getString("AWBPFXEXC"));
		embargoDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		embargoDetailsVO.setPaymentType(rs.getString("PAYTYP"));
		embargoDetailsVO.setFlightType(rs.getString("FLTTYP"));
		embargoDetailsVO.setSplitIndicator(rs.getString("SLTIND"));
		embargoDetailsVO.setFlightOwnerInc(rs.getString("FLTOWRINC"));
		embargoDetailsVO.setFlightOwnerExc(rs.getString("FLTOWREXC"));
		embargoDetailsVO.setUnNumberInc(rs.getString("UNDNUMINC"));
		embargoDetailsVO.setUnNumberExc(rs.getString("UNDNUMEXC"));
		embargoDetailsVO.setOriginDateInc(rs.getString("ORGDATINC"));
		embargoDetailsVO.setOriginDateExc(rs.getString("ORGDATEXC"));
		embargoDetailsVO.setViaPointDateInc(rs.getString("VIADATINC"));
		embargoDetailsVO.setViaPointDateExc(rs.getString("VIADATEXC"));
		embargoDetailsVO.setDestinationDateInc(rs.getString("DSTDATINC"));
		embargoDetailsVO.setDestinationDateExc(rs.getString("DSTDATEXC"));
		embargoDetailsVO.setPaymentTypeInc(rs.getString("PAYTYPINC"));
		embargoDetailsVO.setPaymentTypeExc(rs.getString("PAYTYPEXC"));
		embargoDetailsVO.setAirlineGroupInc(rs.getString("ARLGRPINC"));
		embargoDetailsVO.setAirlineGroupExc(rs.getString("ARLGRPEXC"));
		embargoDetailsVO.setFlightNumberInc(rs.getString("FLTNUMINC"));
		embargoDetailsVO.setFlightNumberExc(rs.getString("FLTNUMEXC"));
		embargoDetailsVO.setPkgInsInc(rs.getString("PKGINSINC"));//Added by A-7534 for ICRD-226601
		embargoDetailsVO.setPkgInsExc(rs.getString("PKGINSEXC"));
		embargoDetailsVO.setAgtCodeInc(rs.getString("AGTCODINC"));
		embargoDetailsVO.setAgtCodeExc(rs.getString("AGTCODEXC"));
		embargoDetailsVO.setAgtGroupInc(rs.getString("AGTGRPINC"));
		embargoDetailsVO.setAgtGroupExc(rs.getString("AGTGRPEXC"));
		embargoDetailsVO.setServiceCargoClassInc(rs.getString("SRVCRGCLSINC"));
		embargoDetailsVO.setServiceCargoClassExc(rs.getString("SRVCRGCLSEXC"));
		embargoDetailsVO.setAircraftClassOriginInc(rs.getString("ACRCLSORGINC"));
		embargoDetailsVO.setAircraftClassDestInc(rs.getString("ACRCLSDSTINC"));
		embargoDetailsVO.setAircraftClassViaInc(rs.getString("ACRCLSVIAINC"));
		embargoDetailsVO.setAircraftClassOriginExc(rs.getString("ACRCLSORGEXC"));
		embargoDetailsVO.setAircraftClassDestExc(rs.getString("ACRCLSDSTEXC"));
		embargoDetailsVO.setAircraftClassViaExc(rs.getString("ACRCLSVIAEXC"));
		embargoDetailsVO.setShipperCodeInc(rs.getString("SHPINC"));
		embargoDetailsVO.setShipperGroupInc(rs.getString("SHPGRPINC"));
		embargoDetailsVO.setConsigneeCodeInc(rs.getString("CNSINC"));
		embargoDetailsVO.setConsigneeGroupInc(rs.getString("CNSGRPINC"));
		embargoDetailsVO.setShipmentTypeInc(rs.getString("SHPTYPINC"));
		embargoDetailsVO.setConsolInc(rs.getString("CNSLINC"));
		embargoDetailsVO.setShipperCodeExc(rs.getString("SHPEXC"));
		embargoDetailsVO.setShipperGroupExc(rs.getString("SHPGRPEXC"));
		embargoDetailsVO.setConsigneeCodeExc(rs.getString("CNSEXC"));
		embargoDetailsVO.setConsigneeGroupExc(rs.getString("CNSGRPEXC"));
		embargoDetailsVO.setShipmentTypeExc(rs.getString("SHPTYPEXC"));
		embargoDetailsVO.setConsolExc(rs.getString("CNSLEXC"));
		
		if (rs.getDate("ENDDAT") != null) {
			embargoDetailsVO.setEndDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("ENDDAT")));
		}
		if (rs.getDate("STRDAT") != null) {
		embargoDetailsVO.setStartDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, rs.getDate("STRDAT")));
		}
	}

	public EmbargoSearchVO getEmbargoSearchVO() {
		return embargoSearchVO;
	}

	public void setEmbargoSearchVO(EmbargoSearchVO embargoSearchVO) {
		this.embargoSearchVO = embargoSearchVO;
	}

	public Collection<OneTimeVO> getLeftPanelParameters() {
		return leftPanelParameters;
	}

	public void setLeftPanelParameters(Collection<OneTimeVO> leftPanelParameters) {
		this.leftPanelParameters = leftPanelParameters;
	}

	public Collection<OneTimeVO> getCategories() {
		return categories;
	}

	public void setCategories(Collection<OneTimeVO> categories) {
		this.categories = categories;
	}

	public Collection<OneTimeVO> getComplianceTypes() {
		return complianceTypes;
	}

	public void setComplianceTypes(Collection<OneTimeVO> complianceTypes) {
		this.complianceTypes = complianceTypes;
	}

	public void setParameterCodes(Collection<OneTimeVO> parameterCodes) {
		this.parameterCodes = parameterCodes;
	}

	public Collection<OneTimeVO> getParameterCodes() {
		return parameterCodes;
	}

	public void setFlightTypes(Collection<OneTimeVO> flightTypes) {
		this.flightTypes = flightTypes;
	}

	public Collection<OneTimeVO> getFlightTypes() {
		return flightTypes;
	}
	
	
}
