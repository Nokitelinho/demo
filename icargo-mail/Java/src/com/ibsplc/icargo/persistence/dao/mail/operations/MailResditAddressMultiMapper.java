package com.ibsplc.icargo.persistence.dao.mail.operations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailResditAddressMultiMapper implements MultiMapper<MailResditAddressVO>{

	@Override
	public List<MailResditAddressVO> map(ResultSet rs) throws SQLException {
		List<MailResditAddressVO> mailResditAddressVos=new ArrayList<>();
		MailResditAddressVO mailResditAddressVO;
		while(rs.next()) {
			mailResditAddressVO=new MailResditAddressVO();
			mailResditAddressVO.setCompanyCode(rs.getString("CMPCOD"));
			mailResditAddressVO.setMessageAddressSequenceNumber(rs.getLong("MSGADDSEQNUM"));
			mailResditAddressVO.setMessageType(rs.getString("MSGTYP"));
			mailResditAddressVO.setVersion(rs.getString("MSGVER"));
			mailResditAddressVO.setInterfaceSystem(rs.getString("INFSYS"));
			mailResditAddressVO.setMode(rs.getString("MSGMOD"));
			mailResditAddressVO.setAddress(rs.getString("MODADD"));
			mailResditAddressVO.setEnvelopeCode(rs.getString("ENVCOD"));
			mailResditAddressVO.setEnvelopeAddress(rs.getString("ENVADD"));
			mailResditAddressVO.setParticipantType(rs.getString("PTYTYP"));
			mailResditAddressVO.setParticipantName(rs.getString("PTYNAM"));
			mailResditAddressVO.setParticipantInterfaceSystem(rs.getString("PTYINFSYS"));
			mailResditAddressVO.setAirportCode(rs.getString("ARPCOD"));
			mailResditAddressVO.setCountryCode(rs.getString("CNTCOD"));
			mailResditAddressVO.setResditVersion(rs.getString("RDTVER"));
			mailResditAddressVos.add(mailResditAddressVO);
		}
		return mailResditAddressVos;
	}

}
