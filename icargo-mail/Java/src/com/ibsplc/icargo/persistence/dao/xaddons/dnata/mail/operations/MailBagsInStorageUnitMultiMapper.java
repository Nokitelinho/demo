/*
 * MailBagsInStorageUnitMultiMapper.java Created on May-26-2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A-9529 on 26-05-2020.
 */
public class MailBagsInStorageUnitMultiMapper implements MultiMapper<MailbagVO> {

    //added by A-9529 for IASCB-44567
    private static final Log LOGGER = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
    
    private static final String ORGCOD = "ORGCOD";
    private static final String DSTCOD = "DSTCOD";
    @Override
    public List<MailbagVO> map(ResultSet rs) throws SQLException {
        LOGGER.log(Log.INFO, "Entering the MailBagInStorageUnit Multi Mapper");
        List<MailbagVO> mailbagVOS = new ArrayList<>();
        while (rs.next()) {
            MailbagVO mailbagVO = new MailbagVO();
            String airport = rs.getString("SCNPRT");
            mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
            mailbagVO.setMailbagId(rs.getString("MALIDR"));
            mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
            mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
            mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
            mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
            mailbagVO.setMailClass(rs.getString("MALCLS"));
            mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
            mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
            mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
            mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
            mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
            mailbagVO.setUldNumber(rs.getString("CONNUM"));
            mailbagVO.setYear(rs.getInt("YER"));
            if (rs.getDate("SCNDAT") != null && airport != null) {
                mailbagVO.setScannedDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("SCNDAT")));
            }
            String mailStatus = rs.getString("MALSTA");
            mailbagVO.setLatestStatus(mailStatus);
            mailbagVO.setScannedPort(rs.getString("SCNPRT"));
            mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
            mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
            mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
            mailbagVO.setContainerNumber(rs.getString("CONNUM"));
            mailbagVO.setScannedUser(rs.getString("SCNUSR"));
            mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
            mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
            mailbagVO.setContainerType(rs.getString("CONTYP"));
            mailbagVO.setPou(rs.getString("POU"));
            mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
            mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
            mailbagVO.setPaCode(rs.getString("POACOD"));
            Measure wgt = new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"));
            mailbagVO.setWeight(wgt);
            mailbagVO.setFinalDestination(rs.getString("POU"));
            mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
            mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
            mailbagVO.setMailOrigin(rs.getString(ORGCOD));
            mailbagVO.setMailDestination(rs.getString(DSTCOD));
            Measure actualWeight = new Measure(UnitConstants.MAIL_WGT, rs.getDouble("ACTWGT"));
            mailbagVO.setActualWeight(actualWeight);
		    if (rs.getTimestamp("LSTUPDTIM") != null) {
                mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
                        Location.NONE, rs.getTimestamp("LSTUPDTIM")));
            }
            mailbagVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
            if ("ARR".equals(mailStatus)) {
                    mailbagVO.setArrivedFlag("Y");
            }
            
            if (rs.getTimestamp("REQDLVTIM") != null) {
                mailbagVO.setReqDeliveryTime(new LocalDate(
                        LocalDate.NO_STATION, Location.NONE, rs
                        .getTimestamp("REQDLVTIM")));
            }
            if (rs.getTimestamp("TRPSRVENDTIM") != null) {
                mailbagVO.setTransWindowEndTime(new LocalDate(
                        LocalDate.NO_STATION, Location.NONE, rs
                        .getTimestamp("TRPSRVENDTIM")));
            }
            mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
            mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
            mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
            mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
            mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
            mailbagVO.setOrigin(rs.getString(ORGCOD));
            mailbagVO.setDestination(rs.getString(DSTCOD));
            mailbagVO.setMailOrigin(rs.getString(ORGCOD));
            mailbagVO.setMailDestination(rs.getString(DSTCOD));

            mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
            mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
            mailbagVO.setVol(rs.getDouble("VOL"));
            mailbagVO.setVolUnit(rs.getString("VOLUNT"));
            mailbagVO.setStorageUnit(rs.getString("STGUNT"));
            if ("NEW".equals(mailStatus)) {
                mailbagVO.setAccepted("N");
            } else {
                mailbagVO.setAccepted("Y");
            }
            mailbagVOS.add(mailbagVO);

        }
        LOGGER.log(Log.FINEST, "THE MAILBAG VO IS FOUND TO BE ", mailbagVOS);
        return mailbagVOS;

    }
}
