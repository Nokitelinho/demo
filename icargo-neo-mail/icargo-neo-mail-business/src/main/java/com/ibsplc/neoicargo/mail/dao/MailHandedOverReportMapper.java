package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailHandedOverVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailHandedOverReportMapper implements Mapper<MailHandedOverVO> {
    public MailHandedOverVO map(ResultSet rs) throws SQLException {

        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        //String airport=rs.getString("SCNPRT");
        MailHandedOverVO mailHandedOverVO = new MailHandedOverVO();
        mailHandedOverVO.setDoe(rs.getString("DESARPCOD"));
        mailHandedOverVO.setOoe(rs.getString("ORGARPCOD"));
        //mailHandedOverVO.setWeight(rs.getDouble("WGT"));
        mailHandedOverVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT"))));//added by A-7371
        mailHandedOverVO.setDsn(rs.getString("DSN"));
        mailHandedOverVO.setMailbagId(rs.getString("MALIDR"));
        mailHandedOverVO.setInwardFlightCarrierCode(rs.getString("INBFLTCARCOD"));
        mailHandedOverVO.setInwardFlightNum(rs.getString("INBFLTNUM"));
        mailHandedOverVO.setOnwardCarrier(rs.getString("ONWFLTCARCOD"));
        int onwardFlightnumber=0;
        if(rs.getString("ONWFLTNUM")!=null){
            onwardFlightnumber=Integer.parseInt(rs.getString("ONWFLTNUM"));
        }
        if(onwardFlightnumber>0)
        {
            mailHandedOverVO.setOnwardFlightNum(rs.getString("ONWFLTNUM"));
        }
        if (rs.getString("INBFLTDAT") != null) {
            mailHandedOverVO.setInwardFlightDate(LocalDateMapper.toZonedDateTime(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, rs.getTimestamp("INBFLTDAT"))));
        }
        if (rs.getString("ONWFLTDAT") != null) {
            mailHandedOverVO.setOnwardFlightDate(LocalDateMapper.toZonedDateTime(new LocalDate(LocalDate.NO_STATION,
                    Location.NONE, rs.getTimestamp("ONWFLTDAT"))));
        }

        return mailHandedOverVO;
    }

}
