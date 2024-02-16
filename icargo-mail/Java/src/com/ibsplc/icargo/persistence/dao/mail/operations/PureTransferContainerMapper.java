package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class PureTransferContainerMapper implements MultiMapper<MailAcceptanceVO> {
	
	 private static final String PURE = "PURE";

	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<>();
		ArrayList<MailAcceptanceVO> mailAcceptanceVOs = new ArrayList<>();
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		int count=0;
		while (rs.next()) {

			if (PURE.equals(rs.getString("ULDTYP"))) {
				count++; 
				mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));

				if (rs.getTimestamp("FLTDAT") != null) {
					mailAcceptanceVO.setFlightDate(
							new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("FLTDAT")));
				}

				mailAcceptanceVO.setFlightDestination(rs.getString("POU"));
				mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
				ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
				/*This containercount  using as ULD index number in EmailNotification*/
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
