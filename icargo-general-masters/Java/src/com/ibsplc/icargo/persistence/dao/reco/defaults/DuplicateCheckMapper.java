package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DuplicateCheckMapper implements MultiMapper<EmbargoDetailsVO> {
	private Log log = LogFactory.getLogger("DUPLICATE EMBARGO CHECK MAPPER");

	public List<EmbargoDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("DuplicateCheckMapper", "DuplicateCheckMapper");
		Collection<EmbargoDetailsVO> embargoDetailsList = new ArrayList<EmbargoDetailsVO>();
		EmbargoDetailsVO embargoDetailsVO = null;
		while (rs.next()) {
			embargoDetailsVO=new EmbargoDetailsVO();
			embargoDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			embargoDetailsVO.setEmbargoDescription(rs.getString("RECDES"));
			embargoDetailsVO.setEmbargoReferenceNumber(rs.getString("REFNUM"));
			embargoDetailsVO.setComplianceType(rs.getString("CMPTYP"));
			embargoDetailsVO.setComplianceTypeDescription(rs.getString("CMPTYP"));
			embargoDetailsVO.setCategory(rs.getString("CATTYP"));
			embargoDetailsVO.setCategoryDescription(rs.getString("CATTYP"));
			embargoDetailsVO.setFlightType(rs.getString("FLTTYP"));
			embargoDetailsVO.setFlightTypeDescription(rs.getString("FLTTYP"));
			
			embargoDetailsVO.setOriginAirportCodeInc(rs.getString("ORGARPCODINC"));
			embargoDetailsVO.setOriginAirportGroupExc(rs.getString("ORGARPCODEXC"));
			embargoDetailsVO.setOriginAirportGroupInc(rs.getString("ORGARPGRPINC"));
			embargoDetailsVO.setOriginAirportGroupExc(rs.getString("ORGARPGRPEXC"));
			embargoDetailsVO.setOriginCountryCodeInc(rs.getString("ORGCNTCODINC"));
			embargoDetailsVO.setOriginCountryCodeExc(rs.getString("ORGCNTCODEXC"));
			embargoDetailsVO.setOriginCountryGroupInc(rs.getString("ORGCNTGRPINC"));
			embargoDetailsVO.setOriginCountryGroupExc(rs.getString("ORGCNTGRPEXC"));
			embargoDetailsVO.setViaPointAirportCodeInc(rs.getString("VIAARPCODINC"));
			embargoDetailsVO.setViaPointAirportGroupExc(rs.getString("VIAARPCODEXC"));
			embargoDetailsVO.setViaPointAirportGroupInc(rs.getString("VIAARPGRPINC"));
			embargoDetailsVO.setViaPointAirportGroupExc(rs.getString("VIAARPGRPEXC"));
			embargoDetailsVO.setViaPointCountryCodeInc(rs.getString("VIACNTCODINC"));
			embargoDetailsVO.setViaPointCountryCodeExc(rs.getString("VIACNTCODEXC"));
			embargoDetailsVO.setViaPointCountryGroupInc(rs.getString("VIACNTGRPINC"));
			embargoDetailsVO.setViaPointCountryGroupExc(rs.getString("VIACNTGRPEXC"));
			embargoDetailsVO.setDestinationAirportCodeInc(rs.getString("DSTARPCODINC"));
			embargoDetailsVO.setDestinationAirportGroupExc(rs.getString("DSTARPCODEXC"));
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
			embargoDetailsVO.setOriginStartTime(rs.getString("ORGSTRTIM"));
			embargoDetailsVO.setOriginEndTime(rs.getString("ORGENDTIM"));
			embargoDetailsVO.setViaPointStartTime(rs.getString("VIASTRTIM"));
			embargoDetailsVO.setViaPointEndTime(rs.getString("VIAENDTIM"));
			embargoDetailsVO.setDestinationStartTime(rs.getString("DSTSTRTIM"));
			embargoDetailsVO.setDestinationEndTime(rs.getString("DSTENDTIM"));
			embargoDetailsVO.setAirlineCodeInc(rs.getString("ARLCODINC"));
			embargoDetailsVO.setAirlineCodeExc(rs.getString("ARLCODEXC"));
			embargoDetailsVO.setWidthStart(rs.getString("WIDSTR"));
			embargoDetailsVO.setWidthEnd(rs.getString("WIDEND"));
			embargoDetailsVO.setHeightStart(rs.getString("HGTSTR"));
			embargoDetailsVO.setHeightEnd(rs.getString("HGTEND"));
			embargoDetailsVO.setLengthStart(rs.getString("LENSTR"));
			embargoDetailsVO.setLengthEnd(rs.getString("LENEND"));
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
			embargoDetailsVO.setWeightStart(rs.getString("WGTSTR"));
			embargoDetailsVO.setWeightEnd(rs.getString("WGTEND"));
			embargoDetailsVO.setVolumeStart(rs.getString("VOLSTR"));
			embargoDetailsVO.setVolumeEnd(rs.getString("VOLEND"));
			embargoDetailsVO.setUldInc(rs.getString("ULDINC"));
			embargoDetailsVO.setUldExc(rs.getString("ULDEXC"));
			embargoDetailsVO.setAircraftTypeOrgInc(rs.getString("ACRTYPORGINC"));
			embargoDetailsVO.setAircraftTypeOrgExc(rs.getString("ACRTYPORGEXC"));
			embargoDetailsVO.setAircraftTypeViaInc(rs.getString("ACRTYPVIAINC"));
			embargoDetailsVO.setAircraftTypeViaExc(rs.getString("ACRTYPVIAEXC"));
			embargoDetailsVO.setAircraftTypeDstInc(rs.getString("ACRTYPDSTINC"));
			embargoDetailsVO.setAircraftTypeDstExc(rs.getString("ACRTYPDSTEXC"));
			embargoDetailsVO.setAircraftTypeGrpOrgInc(rs.getString("ACRTYPGRPORGINC"));
			embargoDetailsVO.setAircraftTypeGrpOrgExc(rs.getString("ACRTYPGRPORGEXC"));
			embargoDetailsVO.setAircraftTypeGrpViaInc(rs.getString("ACRTYPGRPVIAINC"));
			embargoDetailsVO.setAircraftTypeGrpViaExc(rs.getString("ACRTYPGRPVIAEXC"));
			embargoDetailsVO.setAircraftTypeGrpDstInc(rs.getString("ACRTYPGRPDSTINC"));
			embargoDetailsVO.setAircraftTypeGrpDstExc(rs.getString("ACRTYPGRPDSTEXC"));
			if (rs.getDate("ENDDAT") != null) {
				embargoDetailsVO.setEndDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs.getDate("ENDDAT")));
			}
			if (rs.getDate("ENDDAT") != null) {
			embargoDetailsVO.setStartDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("STRDAT")));
			}
			embargoDetailsList.add(embargoDetailsVO);
		}
		log.exiting("DuplicateCheckMapper", "DuplicateCheckMapper");
		return (List<EmbargoDetailsVO>) embargoDetailsList;
	}

	

}
