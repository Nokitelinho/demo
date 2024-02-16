package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundCarrierFilterQuery  extends PageableNativeQuery<MailAcceptanceVO> {
	 private StringBuilder baseQuery1;
	 private String baseQuery2;
	 private String baseQuery3;
	private OperationalFlightVO operationalFlightVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	public OutboundCarrierFilterQuery(int recordsPerPage, OperationalFlightVO operationalFlightVO,StringBuilder baseQuery1,String baseQuery2,String baseQuery3,OutboundCarrierMapper mapper) throws SystemException {
	   super(recordsPerPage,0, mapper);
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.baseQuery3 = baseQuery3;
		this.operationalFlightVO = operationalFlightVO;
	}
	@Override
	public String getNativeQuery() {
		log.entering("OutboundFlightFilterQuery", "Inside the NativeQuery");
		int index =0;
		StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append(this.baseQuery1);
		String companyCode = operationalFlightVO.getCompanyCode();
		log.log(Log.FINE, "Company Code-------------->>>>>>>>>>", companyCode);
		//String flightNumber = flightFilterVO.getFlightNumber();
		//LocalDate flightDate = flightFilterVO.getFlightDate();
		int carrierId =operationalFlightVO.getCarrierId();
		//LocalDate fromDate = flightFilterVO.getFromDate();
		//LocalDate toDate = flightFilterVO.getToDate();
		//String flightstatus = flightFilterVO.getFlightStatus();
		//long flightSeqNum = flightFilterVO.getFlightSequenceNumber();

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
		//stringBuilder.append(" ");
		
		//stringBuilder.append(" ");
		//stringBuilder.append(this.baseQuery3);
		//this.setParameter(++index,companyCode);
		//this.setParameter(++index,carrierId);
		//if(operationalFlightVO.getPol()!=null)
		//{
		//	stringBuilder.append("AND MST.ASGPRT = ?");
		//	this.setParameter(++index,operationalFlightVO.getPol());
		//}
		//if(operationalFlightVO.getPou()!=null)
		//{
		//	stringBuilder.append("AND MST.DSTCOD = ?");
		//	this.setParameter(++index,operationalFlightVO.getPou());
		//}
		stringBuilder.append(" )) INNERMST GROUP BY CMPCOD,ASGPRT, DSTCOD,FLTCARIDR,FLTNUM,FLTSEQNUM,LEGSERNUM, CONNAM,FLTCARCOD  ) MST GROUP BY CMPCOD, ASGPRT, FLTCARIDR, DSTCOD, FLTCARCOD");
		log.log(Log.FINE, "builder-------------->>>>>>>>>>", stringBuilder.toString());
		log.exiting("OutboundFlightFilterQuery", "getNativeQuery");
		return stringBuilder.toString();
		
		
	}
	
	
}
