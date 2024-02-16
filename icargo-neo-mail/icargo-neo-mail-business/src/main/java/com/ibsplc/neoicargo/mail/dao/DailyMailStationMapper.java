package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.DailyMailStationReportVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DailyMailStationMapper  implements Mapper<DailyMailStationReportVO> {

    /**
     * @param rs
     * @return
     * @throws SQLException
     */
    public DailyMailStationReportVO map(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        DailyMailStationReportVO reportVO = new DailyMailStationReportVO();
        log.debug("DailyMailStationMapper","map");
        //mapping from DB to Details
        reportVO.setFlightNumber(rs.getString("FLTNUM"));
        reportVO.setCarrierCode(rs.getString("CARCOD"));
        reportVO.setUldnum(rs.getString("ULDNUM"));
        reportVO.setDestination(rs.getString("DEST"));
        reportVO.setNetweight(quantities.getQuantity(Quantities.MAIL_WGT,
                BigDecimal.valueOf(Double.parseDouble(rs.getString("BAGWGT")))));
        reportVO.setGrossweight(quantities.getQuantity(Quantities.MAIL_WGT,
                BigDecimal.valueOf(Double.parseDouble(rs.getString("TOTWGT")))));
        reportVO.setRemark(rs.getString("RMK"));
        reportVO.setBagCount(rs.getString("BAGCNT"));
        return reportVO;
    }

}
