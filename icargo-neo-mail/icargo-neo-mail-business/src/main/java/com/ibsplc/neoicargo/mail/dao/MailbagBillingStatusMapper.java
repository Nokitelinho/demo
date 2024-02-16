package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MailbagBillingStatusMapper implements Mapper<MailbagVO> {
    MailbagVO mailbagVO = null;
    public MailbagVO map(ResultSet rs) throws SQLException {
        mailbagVO = new MailbagVO();
        mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
        mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
        mailbagVO.setBillingStatus(rs.getString("BLGSTA"));
        return mailbagVO;
    }
}

