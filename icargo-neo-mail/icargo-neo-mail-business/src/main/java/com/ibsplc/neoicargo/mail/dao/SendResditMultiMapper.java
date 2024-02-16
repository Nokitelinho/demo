package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.CarditReferenceInformationVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-4072
 */
@Slf4j
public class SendResditMultiMapper implements MultiMapper<CarditReferenceInformationVO> {
	public List<CarditReferenceInformationVO> map(ResultSet rs) throws SQLException {
		log.debug("SendResditMultiMapper" + " : " + "Map Method" + " Entering");
		List<CarditReferenceInformationVO> carditRefInfoVos = new ArrayList<CarditReferenceInformationVO>();
		while (rs.next()) {
			CarditReferenceInformationVO carditRefInfoVo = new CarditReferenceInformationVO();
			carditRefInfoVo.setCarditKey(rs.getString("CDTKEY"));
			carditRefInfoVo.setRefNumber(rs.getString("AHIRFFNUM"));
			carditRefInfoVo.setContractRef(rs.getString("CNTRFF"));
			carditRefInfoVo.setOrgin(rs.getString("ORGCOD"));
			carditRefInfoVo.setDestination(rs.getString("DSTCOD"));
			carditRefInfoVos.add(carditRefInfoVo);
		}
		log.debug("SendResditMultiMapper" + " : " + "Map Method" + " Exiting");
		return carditRefInfoVos;
	}
}
