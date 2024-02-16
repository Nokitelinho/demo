package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MailTagDetailsMapper implements Mapper<MailbagVO> {
    LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
    Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);

    public MailbagVO map(ResultSet rs) throws SQLException {
        log.debug("MailTagDetailsMapper", "Resultset");
        MailbagVO mailbagVO = new MailbagVO();
        mailbagVO.setOperatorOrigin(rs.getString("OPRORG"));

        mailbagVO.setOperatorDestination(rs.getString("OPRDST"));

        mailbagVO.setType(rs.getString("CSGTYP"));
        mailbagVO.setReceptacleType(rs.getString("RCPTYP"));
        mailbagVO.setOrgPaName(rs.getString("POANAM"));
        mailbagVO.setDstPaName(rs.getString("DSTPOANAM"));
        mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
        mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
        if (rs.getDate("CSGDAT") != null) {
            mailbagVO.setConsignmentDate(localDateUtil.getLocalDate(null,
                            rs.getTimestamp("CSGDAT")));
        }
        mailbagVO.setDestCityDesc(rs.getString("DSTEXGCOD"));
        mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
        mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")), BigDecimal.valueOf(rs.getDouble("WGT")),
                "K"));
        mailbagVO.setOrgCityDesc(rs.getString("EXGCODDES"));
        mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
        mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
        mailbagVO.setMailbagId(rs.getString("MALIDR"));

        mailbagVO.setSealNumber(rs.getString("SELNUM"));

        mailbagVO.setPou(rs.getString("POU"));
        mailbagVO.setPol(rs.getString("POL"));
        if (rs.getDate("FLTDAT") != null) {
            mailbagVO.setFlightDate(localDateUtil.getLocalDate(null,
                    rs.getTimestamp("FLTDAT")));

        }
        mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
        mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
        mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
        mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
        mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
        mailbagVO.setYear(rs.getInt("YER"));
        log.debug("MailTagDetailsMapper", "Resultset");
        return mailbagVO;
    }


}
