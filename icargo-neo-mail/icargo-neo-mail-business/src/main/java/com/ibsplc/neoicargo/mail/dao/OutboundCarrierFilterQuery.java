package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OutboundCarrierFilterQuery extends PageableNativeQuery<MailAcceptanceVO> {
	private StringBuilder baseQuery1;
	private String baseQuery2;
	private String baseQuery3;
	private OperationalFlightVO operationalFlightVO;

	public OutboundCarrierFilterQuery(int recordsPerPage, OperationalFlightVO operationalFlightVO,
			StringBuilder baseQuery1, String baseQuery2, String baseQuery3, OutboundCarrierMapper mapper) {
		super(recordsPerPage, 0, mapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.baseQuery3 = baseQuery3;
		this.operationalFlightVO = operationalFlightVO;
	}
	@Override
	public String getNativeQuery() {
		log.debug("OutboundFlightFilterQuery -->Inside the NativeQuery");
		int index =0;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.baseQuery1);
		String companyCode = operationalFlightVO.getCompanyCode();
		int carrierId =operationalFlightVO.getCarrierId();
		this.setParameter(++index,companyCode);
		this.setParameter(++index,carrierId);
		stringBuilder.append(" ");
		if(operationalFlightVO.getPol()!=null)
		{
			stringBuilder.append("AND MST.ASGPRT = ?");
			this.setParameter(++index,operationalFlightVO.getPol());
		}
		if(operationalFlightVO.getPou()!=null)
		{
			stringBuilder.append("AND MST.DSTCOD = ?");
			this.setParameter(++index,operationalFlightVO.getPou());
		}
		stringBuilder.append(" ");
		stringBuilder.append(this.baseQuery2);
		this.setParameter(++index,companyCode);
		this.setParameter(++index,carrierId);

		if(operationalFlightVO.getPol()!=null)
		{
			stringBuilder.append("AND MST.ASGPRT = ?");
			this.setParameter(++index,operationalFlightVO.getPol());
		}
		if(operationalFlightVO.getPou()!=null)
		{
			stringBuilder.append("AND MST.DSTCOD = ?");
			this.setParameter(++index,operationalFlightVO.getPou());
		}

		stringBuilder.append(" )) INNERMST GROUP BY CMPCOD,ASGPRT, DSTCOD,FLTCARIDR,FLTNUM,FLTSEQNUM,LEGSERNUM, CONNAM,FLTCARCOD  ) MST GROUP BY CMPCOD, ASGPRT, FLTCARIDR, DSTCOD, FLTCARCOD");
		log.debug("builder-------------->>>>>>>>>> {}", stringBuilder.toString());
		log.debug("Exiting OutboundFlightFilterQuery  ---> getNativeQuery");
		return stringBuilder.toString();


	}


}
