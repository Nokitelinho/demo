package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FindOutboundFlightDetailsFilterQuery extends PageableNativeQuery<ContainerDetailsVO> {
	private StringBuilder baseQuery1;
	 private String baseQuery2;
	 private OperationalFlightVO operationalFlightVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	public FindOutboundFlightDetailsFilterQuery(OperationalFlightVO operationalFlightVO,StringBuilder baseQuery1,String baseQuery2,FindContainersinFlightMapper mapper) throws SystemException {
	   super(operationalFlightVO.getRecordsPerPage(),0, mapper);
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.operationalFlightVO = operationalFlightVO;
	}
	@Override
	public String getNativeQuery(){
		log.entering("FindOutboundFlightDetailsFilterQuery", "Inside the NativeQuery");
		LocalDate reqDeliveryTime  = this.operationalFlightVO.getReqDeliveryTime();
		int index =0;
		StringBuilder queryBuilder = new StringBuilder(this.baseQuery1);
		
		setParameter(++index, this.operationalFlightVO.getCompanyCode());
		setParameter(++index, this.operationalFlightVO.getCarrierId());
		setParameter(++index, this.operationalFlightVO.getFlightNumber());
		setParameter(++index, this.operationalFlightVO
				.getFlightSequenceNumber());
		setParameter(++index, this.operationalFlightVO.getPol());
		if(operationalFlightVO.getContainerNumber()!= null && operationalFlightVO.getContainerNumber().trim().length()>0) {
			queryBuilder.append(" AND CON.CONNUM= ?");
			setParameter(++index, operationalFlightVO
					.getContainerNumber());
		}
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND MALMST.REQDLVTIM = ?");
				}
			setParameter(++index,reqDeliveryTime);
			}
		} 
		if(this.operationalFlightVO.getPou()!=null && this.operationalFlightVO.getPou().trim().length()>0) {
			queryBuilder.append(" AND CON.POU = ?");
			setParameter(++index,this.operationalFlightVO.getPou());
		}
		if(this.operationalFlightVO.getDestination()!=null && this.operationalFlightVO.getDestination().trim().length()>0) {
			queryBuilder.append(" AND CON.DSTCOD = ?");
			setParameter(++index,this.operationalFlightVO.getDestination());
		}
		queryBuilder.append(" ) ");
		queryBuilder.append(" UNION ");
		
		queryBuilder.append(this.baseQuery2);
		
		setParameter(++index, this.operationalFlightVO.getCompanyCode());
		setParameter(++index, this.operationalFlightVO.getCarrierId());
		setParameter(++index, this.operationalFlightVO.getFlightNumber());
		setParameter(++index, this.operationalFlightVO
				.getFlightSequenceNumber());
		setParameter(++index, this.operationalFlightVO.getPol());
		if(operationalFlightVO.getContainerNumber()!= null && operationalFlightVO.getContainerNumber().trim().length()>0) {
			queryBuilder.append(" AND CON.CONNUM= ?");
			setParameter(++index, operationalFlightVO
					.getContainerNumber());
		}

		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND MALMST.REQDLVTIM = ?");
				}
			setParameter(++index,reqDeliveryTime);
			}
		}
		if(this.operationalFlightVO.getPou()!=null && this.operationalFlightVO.getPou().trim().length()>0) {
			queryBuilder.append(" AND CON.POU = ?");
			setParameter(++index,this.operationalFlightVO.getPou());
		}
		if(this.operationalFlightVO.getDestination()!=null && this.operationalFlightVO.getDestination().trim().length()>0) {
			queryBuilder.append(" AND CON.DSTCOD = ?");
			setParameter(++index,this.operationalFlightVO.getDestination());
		}          

		queryBuilder.append(" )) MST ");

		queryBuilder.append("group by CMPCOD,FLTCARIDR,FLTCARCOD,FLTNUM,CONNUM,FLTSEQNUM,FLTDAT,LEGSERNUM,SEGSERNUM,CONNUM,POU,ASGPRT,DSTCOD,POAFLG, ACPFLG,ARRSTA,OFLFLG,TRNFLG,CONTYP,CONLSTUPDTIM,CONJRNIDR,POACOD,ACTULDWGT,ASGDATUTC,WHSCOD,LOCCOD,TRFCARCOD,RMK,ULDFRMCARCOD,USRCOD,ULDLSTUPDTIM,LSTUPDUSR,CNTIDR,ULDFULIND,TXNCOD,BASCURCOD)order by CMPCOD,FLTCARIDR,FLTNUM,CONNUM");
		this.log.exiting("FindOutboundFlightDetailsFilterQuery", "getNativeQuery");
	    return queryBuilder.toString();
		
	}

}
