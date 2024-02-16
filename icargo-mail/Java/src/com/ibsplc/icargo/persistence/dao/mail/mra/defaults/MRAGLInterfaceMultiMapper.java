package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRAGLInterfaceMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10164	:	15-Feb-2023	:	Draft
 */
public class MRAGLInterfaceMultiMapper implements MultiMapper<GLInterfaceDetailVO> {
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
 *	Added by 			: A-10164 on 15-Feb-2023
 * 	Used for 	:	MRA GL interface
 *	Parameters	:	@param rs
 *	Parameters	:	@return
 *	Parameters	:	@throws SQLException
 */
	@Override
	public List<GLInterfaceDetailVO> map(ResultSet rs) throws SQLException {

		List<GLInterfaceDetailVO> glInterfaceDetailVOs = new ArrayList<>();
		while (rs.next()) {
			GLInterfaceDetailVO glInterfaceDetailVO = new GLInterfaceDetailVO();
			glInterfaceDetailVO.setCompanyCode(rs.getString("CMPCOD"));
			glInterfaceDetailVO.setAcctxnidr(rs.getString("ACCTXNIDR"));
			glInterfaceDetailVO.setClearancePeriod(rs.getString("CLRPRD"));
			glInterfaceDetailVO.setCostCenterCode(rs.getString("CSTCTRCOD"));
			if (Objects.nonNull(rs.getDate("CREDAT"))) {
				glInterfaceDetailVO
						.setCreatedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("CREDAT")));
			}
			glInterfaceDetailVO.setCreditAmount(rs.getDouble("CRRCUR"));
			glInterfaceDetailVO.setDebitAmount(rs.getDouble("DEBCUR"));
			glInterfaceDetailVO.setFinancialYear(rs.getString("FINYER"));
			glInterfaceDetailVO.setFlightCarrierIdr(rs.getString("FLTCARIDR"));
			glInterfaceDetailVO.setFlightNumber(rs.getString("FLTNUM"));
			glInterfaceDetailVO.setFunctionPoints(rs.getString("FUNPNT"));
			glInterfaceDetailVO.setGlrefnum(rs.getString("GLREFNUM"));
			glInterfaceDetailVO.setOwnerId(rs.getInt("DOCOWRIDR"));
			glInterfaceDetailVO.setSubSystem(rs.getString("SUBSYS"));
			glInterfaceDetailVO.setProfitCenterCode(rs.getString("PRFCTRCOD"));
			glInterfaceDetailVO.setAccountType(rs.getString("ACCTYP"));
			glInterfaceDetailVO.setCurrentDate(new LocalDate("SEA", Location.STN, true));
			glInterfaceDetailVO.setMemoNumber(rs.getString("MEMNUM"));
			glInterfaceDetailVO.setClearingHouse(rs.getString("CLRHUS"));
			glInterfaceDetailVO.setAirlineCode(rs.getString("ARLCOD"));
			glInterfaceDetailVO.setInvoiceClearancePeriod(rs.getString("INVCLRPRD"));
			glInterfaceDetailVO.setCurrencyCode(rs.getString("CURNAM"));
			glInterfaceDetailVO.setRemarks(rs.getString("RMK"));
			glInterfaceDetailVO.setGroupingField(rs.getString("GROUPFIELD"));
			glInterfaceDetailVO.setJournalLineDesc(rs.getString("JRNLINDESC"));
			glInterfaceDetailVO.setFunctionPointDesc(rs.getString("FUNDESC"));
			glInterfaceDetailVOs.add(glInterfaceDetailVO);
		}

		return glInterfaceDetailVOs;
	}

}
