
/*
 * ListRegulatoryComplianceRulesMapper.java Created on Sep 17, 2014
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * @author A-5867
 * 
 */
public class ListRegulatoryComplianceRulesMapper implements MultiMapper<EmbargoDetailsVO> {

	private static final String VALUE_ALL="-";
	private static final String LABEL_INCLUDE="(Inc)";
	private static final String LABEL_EXCLUDE="(Exc)";
	private static final String LABEL_ALL="ALL";
	private static final String LABEL_ORIGIN="Origin";
	private static final String LABEL_DESTINATION="Destination";
	private static final String LABEL_VIAPOINT="Via point";
	private static final String LABEL_SEGMENTORIGIN="Segment Origin";
	private static final String LABEL_SEGMENTDESTINATION="Segment Destination";
	private Collection<OneTimeVO> categories;
	private Collection<OneTimeVO> complianceTypes;
	
	ListRegulatoryComplianceRulesMapper(){
		}
	ListRegulatoryComplianceRulesMapper(EmbargoFilterVO filterVO){
		this.categories=filterVO.getCategories();
		this.complianceTypes=filterVO.getComplianceTypes();
	}
	public List<EmbargoDetailsVO> map(ResultSet rs) throws SQLException {
	     List<EmbargoDetailsVO>  embargoDetailsVOs= new ArrayList<EmbargoDetailsVO>();
	     EmbargoDetailsVO embargoDetailsVO=null;
	    Map<String,String> categoryMasterMap= new HashMap<String, String>();
	     Map<String,String> complianceTypeMasterMap= new HashMap<String, String>();
	     Map<String,String> flightTypeMasterMap= new HashMap<String, String>();   
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
		if(null !=rs){
		     while (rs.next()) {
		    	embargoDetailsVO = new EmbargoDetailsVO();
		 		setEmbargoDetails(rs, embargoDetailsVO, categoryMasterMap,complianceTypeMasterMap, flightTypeMasterMap);
		 		setProcessTypes(rs, embargoDetailsVO);		 		
		 		embargoDetailsVOs.add(embargoDetailsVO);
		 				 		
		     }	     	
		}
	    return embargoDetailsVOs;
	}

	private void setProcessTypes(ResultSet rs, EmbargoDetailsVO embargoDetailsVO)
			throws SQLException {
		StringBuilder processTypeOrigin= new StringBuilder().append(LABEL_ORIGIN).append(" : ");
		StringBuilder processTypeDestination=new StringBuilder().append(LABEL_DESTINATION).append(" : ");
		StringBuilder processTypeViaPoint=new StringBuilder().append(LABEL_VIAPOINT).append(" : ");
		//Added by A-7924 as part of ICRD-318460 starts
		StringBuilder processTypeSegmentOrigin= new StringBuilder().append(LABEL_SEGMENTORIGIN).append(" : ");
		StringBuilder processTypeSegmentDestination=new StringBuilder().append(LABEL_SEGMENTDESTINATION).append(" : ");
		//Added by A-7924 as part of ICRD-318460 ends
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
		//Added by A-7924 as part of ICRD-318460 starts
		processTypeViaPoint.append("~");
		/**Process Type :Segment Origin **/
		if(null !=rs.getString("SEGORGARPCODINC") && rs.getString("SEGORGARPCODINC").length()>0 
				&& !rs.getString("SEGORGARPCODINC").equals(VALUE_ALL)){
			processTypeSegmentOrigin.append(rs.getString("SEGORGARPCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGORGARPGRPINC") && rs.getString("SEGORGARPGRPINC").length()>0 
				&& !rs.getString("SEGORGARPGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGARPGRPINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGORGCNTCODINC") && rs.getString("SEGORGCNTCODINC").length()>0 
				&& !rs.getString("SEGORGCNTCODINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGCNTCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGORGCNTGRPINC") && rs.getString("SEGORGCNTGRPINC").length()>0 
				&& !rs.getString("SEGORGCNTGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGCNTGRPINC"));
			hasInclude=true;
		}
		if(hasInclude){
			processTypeSegmentOrigin.append(LABEL_INCLUDE);
		}
		if(null !=rs.getString("SEGORGARPCODEXC") && rs.getString("SEGORGARPCODEXC").length()>0 
				&& !rs.getString("SEGORGARPCODEXC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGARPCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGORGARPGRPEXC") && rs.getString("SEGORGARPGRPEXC").length()>0 
				&& !rs.getString("SEGORGARPGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGARPGRPEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGORGCNTCODEXC") && rs.getString("SEGORGCNTCODEXC").length()>0 
				&& !rs.getString("SEGORGCNTCODEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGCNTCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGORGCNTGRPEXC") && rs.getString("SEGORGCNTGRPEXC").length()>0 
				&& !rs.getString("SEGORGCNTGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentOrigin.append(",");
			}
			processTypeSegmentOrigin.append(rs.getString("SEGORGCNTGRPEXC"));
			hasExclude=true;
		}
		if(hasExclude){
			processTypeSegmentOrigin.append(LABEL_EXCLUDE);
		}
		if(!hasInclude && !hasExclude){
			processTypeSegmentOrigin.append(LABEL_ALL);
		}
		processTypeSegmentOrigin.append("~");
		/**Process Type : Segment Destination **/
		hasInclude=false;
		hasExclude=false;
		if(null !=rs.getString("SEGDSTARPCODINC") && rs.getString("SEGDSTARPCODINC").length()>0 
				&& !rs.getString("SEGDSTARPCODINC").equals(VALUE_ALL)){
			processTypeSegmentDestination.append(rs.getString("SEGDSTARPCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGDSTARPGRPINC") && rs.getString("SEGDSTARPGRPINC").length()>0 
				&& !rs.getString("SEGDSTARPGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTARPGRPINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGDSTCNTCODINC") && rs.getString("SEGDSTCNTCODINC").length()>0 
				&& !rs.getString("SEGDSTCNTCODINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTCNTCODINC"));
			hasInclude=true;
		}
		if(null !=rs.getString("SEGDSTCNTGRPINC") && rs.getString("SEGDSTCNTGRPINC").length()>0 
				&& !rs.getString("SEGDSTCNTGRPINC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTCNTGRPINC"));
			hasInclude=true;
		}
		if(hasInclude){
			processTypeSegmentDestination.append(LABEL_INCLUDE);
		}
		if(null !=rs.getString("SEGDSTARPCODEXC") && rs.getString("SEGDSTARPCODEXC").length()>0 
				&& !rs.getString("SEGDSTARPCODEXC").equals(VALUE_ALL)){
			if(hasInclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTARPCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGDSTARPGRPEXC") && rs.getString("SEGDSTARPGRPEXC").length()>0 
				&& !rs.getString("SEGDSTARPGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTARPGRPEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGDSTCNTCODEXC") && rs.getString("SEGDSTCNTCODEXC").length()>0 
				&& !rs.getString("SEGDSTCNTCODEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTCNTCODEXC"));
			hasExclude=true;
		}
		if(null !=rs.getString("SEGDSTCNTGRPEXC") && rs.getString("SEGDSTCNTGRPEXC").length()>0 
				&& !rs.getString("SEGDSTCNTGRPEXC").equals(VALUE_ALL)){
			if(hasExclude){
				processTypeSegmentDestination.append(",");
			}
			processTypeSegmentDestination.append(rs.getString("SEGDSTCNTGRPEXC"));
			hasExclude=true;
		}
		if(hasExclude){
			processTypeSegmentDestination.append(LABEL_EXCLUDE);
		}
		if(!hasInclude && !hasExclude){
			processTypeSegmentDestination.append(LABEL_ALL);
		}
		processTypeSegmentDestination.append("~");
		//Added by A-7924 as part of ICRD-318460 ends
		processTypeOrigin.append(processTypeDestination).append(processTypeViaPoint).append(processTypeSegmentOrigin).append(processTypeSegmentDestination); //Modified by A-7924 as part of ICRD-318460
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
		embargoDetailsVO.setFlightTypeDescription(null !=rs.getString("FLTTYP")? flightTypeMasterMap.get(rs.getString("FLTTYP")):"");
		
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
		embargoDetailsVO.setNatureOfGoods(rs.getString("NAT"));
		embargoDetailsVO.setCommodity(rs.getString("COM"));
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
		if (rs.getDate("ENDDAT") != null) {
		embargoDetailsVO.setStartDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, rs.getDate("STRDAT")));
		}
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
}

