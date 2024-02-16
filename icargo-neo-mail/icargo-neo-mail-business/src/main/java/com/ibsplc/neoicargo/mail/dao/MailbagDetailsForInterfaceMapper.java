/**
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.MailbagDetailsForInterfaceMapper.java
 * <p>
 * Created by	:	204082
 * Created on	:	20-Oct-2022
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.dao;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.MailbagDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MailbagDetailsForInterfaceMapper implements Mapper<MailbagDetailsVO> {

    private static final String MAILTRACKING_DEFAULTS = "MAILTRACKING_DEFAULTS";
    private static final Log LOGGER = LogFactory.getLogger(MAILTRACKING_DEFAULTS);

    /**
     * Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
     * Added by 			: 204082 on 20-Oct-2022
     * Used for 	:
     * Parameters	:	@param rs
     * Parameters	:	@return MailbagDetailsVO
     * Parameters	:	@throws SQLException
     */
    @Override
    public MailbagDetailsVO map(ResultSet rs) throws SQLException {
        LOGGER.entering(MAILTRACKING_DEFAULTS, "MailbagMasterDataDetailsMapper");
        Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
        MailbagDetailsVO mailbagDetailsVO = new MailbagDetailsVO();
        mailbagDetailsVO.setMailbagId(rs.getString("MALIDR"));

        Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("WGT")));
        mailbagDetailsVO.setWeight(wgt);
        mailbagDetailsVO.setMailSequenceNumber(Long.parseLong(rs.getString("MALSEQNUM")));
        mailbagDetailsVO.setFlightCarrierCode(rs.getString("TWOAPHCOD"));
        mailbagDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
        mailbagDetailsVO.setSegmentOrigin(rs.getString("POL"));
        mailbagDetailsVO.setSegmentDestination(rs.getString("POU"));

        if (rs.getString("POL") != null && rs.getTimestamp("STD") != null) {
            mailbagDetailsVO.setStd(new LocalDate(rs.getString("POL"), Location.ARP, rs.getTimestamp("STD")).toZonedDateTime());
        }
        if (rs.getTimestamp("STDUTC") != null) {
            mailbagDetailsVO.setStdutc(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("STDUTC")).toZonedDateTime());
        }
        if (rs.getString("POU") != null && rs.getTimestamp("STA") != null) {
            mailbagDetailsVO.setSta(new LocalDate(rs.getString("POU"), Location.ARP, rs.getTimestamp("STA")).toZonedDateTime());
        }
        if (rs.getTimestamp("STAUTC") != null) {
            mailbagDetailsVO.setStautc(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("STAUTC")).toZonedDateTime());
        }
        mailbagDetailsVO.setInboundResditEvtCode(rs.getString("INBEVTCOD"));
        mailbagDetailsVO.setInboundSource(rs.getString("INBSRC"));
        mailbagDetailsVO.setArrivalAirport(rs.getString("INBSCNPRT"));
        mailbagDetailsVO.setOutboundResditEvtCode(rs.getString("OUTEVTCOD"));
        mailbagDetailsVO.setOutboundSource(rs.getString("OUTSRC"));
        mailbagDetailsVO.setDepartureAirport(rs.getString("OUTSCNPRT"));
        mailbagDetailsVO.setContainerId(rs.getString("CONNUM"));
        mailbagDetailsVO.setHbaType(rs.getString("HBATYP"));
        mailbagDetailsVO.setHbaPosition(rs.getString("HBAPOS"));

        LOGGER.log(Log.FINE, "mailbagDetailsVO from mapper++++", mailbagDetailsVO);
        LOGGER.exiting(MAILTRACKING_DEFAULTS, "MailbagMasterDataDetailsMapper");
        return mailbagDetailsVO;
    }
}
