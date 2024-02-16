package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

public class PureTransferContainerMapper implements MultiMapper<MailAcceptanceVO> {
	private static final String PURE = "PURE";

	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ArrayList<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<>();
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		int count = 0;
		while (rs.next()) {
			if (PURE.equals(rs.getString("ULDTYP"))) {
				count++;
				mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));
				if (rs.getTimestamp("FLTDAT") != null) {
					mailAcceptanceVO.setFlightDate(localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT")));
				}
				mailAcceptanceVO.setFlightDestination(rs.getString("POU"));
				mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setContainercount(count);
				containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
				containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
				containerDetailsVO.setContainerNumber(rs.getString("ULDNUM"));
				containerDetailsVO.setContainerType(rs.getString("ULDTYP"));
				containerDetailsVO.setAssignedPort(rs.getString("ASGPRT"));
				containerDetailsVO.setPou(rs.getString("POU"));
				containerDetailsVO.setMailbagcount(rs.getInt("MALCNT"));
				containerDetails.add(containerDetailsVO);
			}
		}
		mailAcceptanceVO.setContainerDetails(containerDetails);
		mailAcceptanceVOs.add(mailAcceptanceVO);
		return mailAcceptanceVOs;
	}
}
