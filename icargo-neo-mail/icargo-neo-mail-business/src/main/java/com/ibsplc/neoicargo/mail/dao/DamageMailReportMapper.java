package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.DamagedMailbagVO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class DamageMailReportMapper implements Mapper<DamagedMailbagVO> {

// private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
/**
 * @param rs
 * @return
 * @throws SQLException
 */
public DamagedMailbagVO map(ResultSet rs) throws SQLException {

        LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        String airportCode = rs.getString("ARPCOD");
        String doe=rs.getString("DSTEXGOFC");
        String ooe=rs.getString("ORGEXGOFC");
        DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
        damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
        damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
        if (rs.getTimestamp("DMGDAT") != null) {
                damagedMailbagVO.setDamageDate(localDateUtil.getLocalDate(airportCode, rs.getTimestamp("DMGDAT")));
        }
        damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
        damagedMailbagVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
        damagedMailbagVO.setMailbagId(rs.getString("MALIDR"));
        damagedMailbagVO.setRemarks(rs.getString("RMK"));
        damagedMailbagVO.setCarrierCode(rs.getString("TWOAPHCOD"));
        damagedMailbagVO.setFlightNumber(rs.getString("FLTNUM"));
        damagedMailbagVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
        damagedMailbagVO.setPaCode(rs.getString("POACOD"));
        damagedMailbagVO.setDsn(rs.getString("DSN"));
        damagedMailbagVO.setSubClassCode(rs.getString("SUBCLSCOD"));
        damagedMailbagVO.setSubClassGroup(rs.getString("SUBCLSGRP"));
        damagedMailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT ,BigDecimal.valueOf(rs.getDouble("WGT")), BigDecimal.valueOf(0.0), MailConstantsVO.WEIGHTCODE_KILO));
        damagedMailbagVO.setDeclaredValue(rs.getDouble("DCLVAL"));
        damagedMailbagVO.setFlightOrigin(rs.getString("POL"));
        damagedMailbagVO.setFlightDestination(rs.getString("POU"));
        damagedMailbagVO.setCurrencyCode(rs.getString("CURCOD"));
        damagedMailbagVO.setDeclaredValueTot(rs.getDouble("DCLVALCONVERTED"));
        damagedMailbagVO.setTotCurrencyCode(rs.getString("TOTDCLVALCUR"));
        if (rs.getTimestamp("FLTDAT") != null) {
                damagedMailbagVO.setFlightDate(localDateUtil.getLocalDate(airportCode, rs.getTimestamp("FLTDAT")));
        }

        return damagedMailbagVO;

        }

}

