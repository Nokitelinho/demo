package com.ibsplc.neoicargo.mail.dao;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DuplicateMailBagMapper implements Mapper<MailbagVO> {
        @Override
        public MailbagVO map(ResultSet rs) throws SQLException {
            Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
            MailbagVO mailbagVO = new MailbagVO();
            String airport = rs.getString("SCNPRT");
            mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
            mailbagVO.setMailbagId(rs.getString("MALIDR"));
            mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
            if (rs.getDate("SCNDAT") != null && airport != null) {
                mailbagVO.setScannedDate(LocalDateMapper.toZonedDateTime( new LocalDate(airport, Location.ARP, rs.getTimestamp("SCNDAT"))));
            }

            String mailStatus = rs.getString("MALSTA");
            mailbagVO.setLatestStatus(mailStatus);
            mailbagVO.setScannedPort(rs.getString("SCNPRT"));
            mailbagVO.setContainerNumber(rs.getString("CONNUM"));
            mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
            mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
            Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT,
                    BigDecimal.ZERO,
                    BigDecimal.valueOf(rs.getDouble("WGT")),"K");
            mailbagVO.setWeight(wgt);
            mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
            mailbagVO.setMailDestination(rs.getString("DSTCOD"));
            mailbagVO.setOrigin(rs.getString("ORGCOD"));
            mailbagVO.setDestination(rs.getString("DSTCOD"));
            if (rs.getString("ACTWGTDSPUNT") != null) {
                mailbagVO.setActualWeight(quantities.getQuantity(Quantities.MAIL_WGT,
                        BigDecimal.ZERO,
                        BigDecimal.valueOf(rs.getDouble("ACTWGT")),"K"));
            }
            mailbagVO.setLastUpdateUser(rs.getString("SCNUSR"));
            if (rs.getTimestamp("CSGDAT") != null && airport != null) {
                mailbagVO.setConsignmentDate(LocalDateMapper.toZonedDateTime(new LocalDate(airport, Location.ARP, rs.getTimestamp("CSGDAT"))));
            }
            mailbagVO.setMailRemarks(rs.getString("MALRMK"));
            if (rs.getString("DSTEXGOFC") != null) {
                mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
            }
            if (rs.getString("ORGEXGOFC") != null) {
                mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
            }
            if (rs.getString("DSN") != null) {
                mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
            }
            if (rs.getString("MALCTG") != null) {
                mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
            }
            if (rs.getInt("YER") != 0) {
                mailbagVO.setYear(rs.getInt("YER"));
            }
            if (rs.getString("MALSUBCLS") != null) {
                mailbagVO.setMailSubclass(rs.getString("MALSUBCLS").substring(
                        0, 1));
            }
            if (rs.getString("MALCLS") != null) {
                mailbagVO.setMailClass(rs.getString("MALCLS").substring(
                        0, 1));
            }
            if (rs.getString("RSN") != null) {
                mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
            }
            mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,
                    BigDecimal.ZERO,
                    BigDecimal.valueOf(rs.getDouble("WGT")),"K"));
            if (rs.getTimestamp("REQDLVTIM") != null) {
                mailbagVO.setReqDeliveryTime(LocalDateMapper.toZonedDateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,
                        rs.getTimestamp("REQDLVTIM"))));
            }
            if (rs.getString("MALSRVLVL") != null) {
                mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
            }
            if (rs.getTimestamp("TRPSRVENDTIM") != null) {
                mailbagVO.setTransWindowEndTime(LocalDateMapper.toZonedDateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,
                        rs.getTimestamp("TRPSRVENDTIM"))));
            }
            if (rs.getString("POACOD") != null) {
                mailbagVO.setPaCode(rs.getString("POACOD"));
            }
            return mailbagVO;
        }
    }

