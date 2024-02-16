package com.ibsplc.neoicargo.mail.dao;


import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.MailStatusVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
@Slf4j
public class MailStatusReportMapper  implements Mapper<MailStatusVO>{

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public MailStatusVO map(ResultSet rs) throws SQLException {
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);

        MailStatusVO mailStatusVO = new MailStatusVO();
        mailStatusVO.setCarditAvailable(rs.getString("CDTAVL"));
        mailStatusVO.setDsn(rs.getString("DSN"));
        mailStatusVO.setFlightCarrierCode(rs.getString("FLTCARCOD"));
        mailStatusVO.setFlightNumber(rs.getString("FLTNUM"));
        mailStatusVO.setIncommingFlightCarrierCode(rs.getString("INFLTCARCOD"));
        mailStatusVO.setIncommingFlightNumber(rs.getString("INFLTNUM"));
        mailStatusVO.setMailBagId(rs.getString("MALIDR"));
        mailStatusVO.setPol(rs.getString("POL"));
        mailStatusVO.setPou(rs.getString("POU"));
        Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
        mailStatusVO.setWeight(wgt);
        mailStatusVO.setCompanyCode(rs.getString("CMPCOD"));
        mailStatusVO.setContainerNumber(rs.getString("CONNUM"));
        mailStatusVO.setLegStatus(rs.getString("LEGSTA"));
        mailStatusVO.setFlightRoute(rs.getString("FLTROU"));
        if (rs.getTimestamp("STD") != null) {
            mailStatusVO.setScheduledDepartureTime(localDateUtil.getLocalDate(null, rs.getTimestamp("STD")));
        }
        if (rs.getTimestamp("FLTDAT") != null) {
            mailStatusVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT")));
        }
        if (rs.getTimestamp("INFLTDAT") != null) {
            mailStatusVO.setIncommingFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("INFLTDAT")));
        }

        return mailStatusVO;
    }


}
