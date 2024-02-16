package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailResditMapper implements Mapper<MailResditVO> {
    public MailResditVO map(ResultSet rs) throws SQLException {
        MailResditVO mailResditVO = new MailResditVO();
        mailResditVO.setCarrierId(rs.getInt("FLTCARIDR"));
        mailResditVO.setFlightNumber(rs.getString("FLTNUM"));
        mailResditVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
        mailResditVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
        mailResditVO.setEventAirport(rs.getString("EVTPRT"));
        mailResditVO.setResditSentFlag(rs.getString("RDTSND"));
        mailResditVO.setProcessedStatus(rs.getString("PROSTA"));
        return mailResditVO;
    }
}
