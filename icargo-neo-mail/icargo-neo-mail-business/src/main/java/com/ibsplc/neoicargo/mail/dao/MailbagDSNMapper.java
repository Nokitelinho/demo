package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class MailbagDSNMapper implements Mapper<DSNVO> {
	@Override
	public DSNVO map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		DSNVO dsnVO = new DSNVO();
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		dsnVO.setBags(rs.getInt("DSNCNT"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		dsnVO.setPaCode(rs.getString("POACOD"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		Quantity weight = quantities.getQuantity(Quantities.MAIL_WGT,
				BigDecimal.valueOf(rs.getDouble("DSNWGT")));
		if (Objects.nonNull(dsnVO.getDeliveredWeight())) {
			dsnVO.setWeight(dsnVO.getDeliveredWeight().add(weight));
		} else {
			dsnVO.setWeight(weight);
		}
		return dsnVO;
	}
}
