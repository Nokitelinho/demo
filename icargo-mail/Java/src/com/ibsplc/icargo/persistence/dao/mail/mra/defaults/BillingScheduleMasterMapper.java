package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
/**
 * @author A-9498
 *
 */
public class BillingScheduleMasterMapper implements MultiMapper<BillingScheduleDetailsVO> {
	public List<BillingScheduleDetailsVO> map(ResultSet rs) throws SQLException {
		List<BillingScheduleDetailsVO> billingScheduleDetailsVOs = new ArrayList<>();
		String currKey = "";
		String prevKey = "";
		BillingScheduleDetailsVO billingScheduleDetailsVO = null;
		while (rs.next()) {
			currKey = rs.getString("CMPCOD") + rs.getInt("PERIDONUM") + rs.getString("SERNUM") + rs.getString("BLGTYP");
			List<BillingParameterVO> billingParameterVOs = new ArrayList<>();
			if (!currKey.equals(prevKey)) {
				billingScheduleDetailsVO = new BillingScheduleDetailsVO();
				billingScheduleDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				billingScheduleDetailsVO.setPeriodNumber(rs.getString("PRDNUM"));
				billingScheduleDetailsVO.setSerialNumber(rs.getInt("SERNUM"));
				billingScheduleDetailsVO.setBillingType(rs.getString("BLGTYP"));
				billingScheduleDetailsVO
						.setBillingPeriodFromDate(new LocalDate(NO_STATION, NONE, rs.getDate("BLGPRDFRM")));
				billingScheduleDetailsVO
						.setBillingPeriodToDate(new LocalDate(NO_STATION, NONE, rs.getDate("BLGPRDTOO")));
				if (rs.getDate("GPAINVGEN") != null) {
					billingScheduleDetailsVO
							.setGpaInvoiceGenarateDate(new LocalDate(NO_STATION, NONE, rs.getDate("GPAINVGEN")));
				}
				if (rs.getDate("MSTCUTOFF") != null) {
					billingScheduleDetailsVO
							.setMasterCutDataDate(new LocalDate(NO_STATION, NONE, rs.getDate("MSTCUTOFF")));
				}
				if (rs.getDate("ARLCUTOFF") != null) {
					billingScheduleDetailsVO
							.setAirLineUploadCutDate(new LocalDate(NO_STATION, NONE, rs.getDate("ARLCUTOFF")));
				}
				if (rs.getDate("PASFILGEN") != null) {
					billingScheduleDetailsVO
							.setPassFileGenerateDate(new LocalDate(NO_STATION, NONE, rs.getDate("PASFILGEN")));
				}
				if (rs.getDate("PASINVAVL") != null) {
					billingScheduleDetailsVO
							.setInvoiceAvailableDate(new LocalDate(NO_STATION, NONE, rs.getDate("PASINVAVL")));
				}
				if (rs.getDate("POARCNUPL") != null) {
					billingScheduleDetailsVO
							.setPostalOperatorUploadDate(new LocalDate(NO_STATION, NONE, rs.getDate("POARCNUPL")));
				}
				billingScheduleDetailsVO.setBillingPeriod(rs.getString("BLGPRD"));
				billingScheduleDetailsVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
				billingScheduleDetailsVO.setTagId(rs.getInt("TAGIDX"));
				billingScheduleDetailsVO.setParamterCode(rs.getString("PARCOD"));
				billingScheduleDetailsVO.setParameterDescription(rs.getString("PARDES"));
				billingScheduleDetailsVO.setExcludeFlag(rs.getString("EXCINCFLG"));
				prevKey = currKey;
				BillingParameterVO billingParameterVO = new BillingParameterVO();
				billingParameterVO.setParamterCode(rs.getString("PARCOD"));
				billingParameterVO.setParameterDescription(rs.getString("PARDES"));
				billingParameterVO.setExcludeFlag(rs.getString("EXCINCFLG"));
				billingParameterVO.setParameterValue(rs.getString("PARVAL"));
				billingParameterVOs.add(billingParameterVO);
				billingScheduleDetailsVO.setParamsList(billingParameterVOs);
				billingScheduleDetailsVOs.add(billingScheduleDetailsVO);
				
			} else {
				BillingParameterVO billingParameterVO = new BillingParameterVO();
				billingParameterVO.setParamterCode(rs.getString("PARCOD"));
				billingParameterVO.setParameterDescription(rs.getString("PARDES"));
				billingParameterVO.setExcludeFlag(rs.getString("EXCINCFLG"));
				billingParameterVO.setParameterValue(rs.getString("PARVAL"));
				for (int i = 0; i < billingScheduleDetailsVOs.size(); i++) {
					if (billingScheduleDetailsVOs.get(i).getSerialNumber() == rs.getInt("SERNUM")) {
						billingScheduleDetailsVO.getParamsList().add(billingParameterVO);
					}
				}
			}
		}

		return billingScheduleDetailsVOs;
	}
}
