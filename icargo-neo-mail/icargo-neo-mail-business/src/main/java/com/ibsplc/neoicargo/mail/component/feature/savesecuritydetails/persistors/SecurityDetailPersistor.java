package com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.persistors;

import com.ibsplc.neoicargo.mail.component.ConsignmentScreeningDetails;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mail.operations.securitydetailpersistor")
public class SecurityDetailPersistor {
	public void persist(ConsignmentScreeningVO consignmentScreeningVO) {
		log.debug("Invoke SecurityDetailPersistor");
		new ConsignmentScreeningDetails(consignmentScreeningVO);
	}
}
