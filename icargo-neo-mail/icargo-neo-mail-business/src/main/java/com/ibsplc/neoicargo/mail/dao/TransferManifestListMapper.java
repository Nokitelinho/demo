package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.TransferManifestVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/** 
 * @author a-1936
 */
public class TransferManifestListMapper implements Mapper<TransferManifestVO> {
	/** 
	* @author a-1936
	* @param rs
	* @return
	* @throws SQLException
	*/
	public TransferManifestVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		TransferManifestVO transferManifestVo = new TransferManifestVO();
		transferManifestVo.setCompanyCode(rs.getString("CMPCOD"));
		transferManifestVo.setTransferManifestId(rs.getString("TRFMFTIDR"));
		transferManifestVo.setTransferredFromCarCode(rs.getString("FRMCARCOD"));
		transferManifestVo.setTransferredToCarrierCode(rs.getString("TOCARCOD"));
		if ("-1".equals(rs.getString("TOFLTNUM"))) {
			transferManifestVo.setTransferredToFltNumber("");
		} else {
			transferManifestVo.setTransferredToFltNumber(rs.getString("TOFLTNUM"));
		}
		if ("-1".equals(rs.getString("FRMFLTNUM"))) {
			transferManifestVo.setTransferredFromFltNum("");
		} else {
			transferManifestVo.setTransferredFromFltNum(rs.getString("FRMFLTNUM"));
		}
		transferManifestVo.setTotalBags(rs.getInt("TOTBAG"));
		transferManifestVo.setTotalWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("TOTWGT"))));
		transferManifestVo.setAirPort(rs.getString("ARPCOD"));
		if (rs.getDate("TRFDAT") != null) {
			transferManifestVo.setTransferredDate(
					localDateUtil.getLocalDate(transferManifestVo.getAirPort(), rs.getTimestamp("TRFDAT")));
		}
		if (rs.getDate("FRMDAT") != null) {
			transferManifestVo
					.setFromFltDat(localDateUtil.getLocalDate(transferManifestVo.getAirPort(), rs.getDate("FRMDAT")));
		}
		if (rs.getDate("TODAT") != null) {
			transferManifestVo
					.setToFltDat(localDateUtil.getLocalDate(transferManifestVo.getAirPort(), rs.getDate("TODAT")));
		}
		if (rs.getString("TRFSTA") != null && rs.getString("TRFSTA").equals("TRFINT")) {
			transferManifestVo.setTransferStatus("Transfer Initiated");
		} else if (rs.getString("TRFSTA") != null && rs.getString("TRFSTA").equals("TRFREJ")) {
			transferManifestVo.setTransferStatus("Transfer Rejected");
		} else if (rs.getString("TRFSTA") != null && rs.getString("TRFSTA").equals("TRFEND")) {
			transferManifestVo.setTransferStatus("Transfer Ended");
		}
		transferManifestVo.setTransferredfrmSegSerNum(rs.getInt("FRMSEGSERNUM"));
		transferManifestVo.setTransferredfrmFltSeqNum(rs.getLong("FRMFLTSEQNUM"));
		return transferManifestVo;
	}
}
