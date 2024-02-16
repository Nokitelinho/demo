package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class FindOutboundCarrierDetailsFilterQuery extends PageableNativeQuery<ContainerDetailsVO> {
	private StringBuilder baseQuery1;
	private String baseQuery2;
	private OperationalFlightVO operationalFlightVO;

	public FindOutboundCarrierDetailsFilterQuery(OperationalFlightVO operationalFlightVO, StringBuilder baseQuery1,
			String baseQuery2, FindContainersinCarrierMapper mapper) {
		super(operationalFlightVO.getRecordsPerPage(), 0, mapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.operationalFlightVO = operationalFlightVO;
	}
	@Override
	public String getNativeQuery() {
		log.debug("Entering FindOutboundCarrierDetailsFilterQuery --> Inside the NativeQuery");
		int index =0;
		ZonedDateTime reqDeliveryTime  = this.operationalFlightVO.getReqDeliveryTime();
		StringBuilder queryBuilder = new StringBuilder(this.baseQuery1);
		setParameter(++index, this.operationalFlightVO.getCompanyCode());
		setParameter(++index, Integer.valueOf(this.operationalFlightVO.getCarrierId()));

		setParameter(++index, this.operationalFlightVO.getPol());
		if ((this.operationalFlightVO.getPou() != null) && (this.operationalFlightVO.getPou().trim().length() > 0))
		{
			queryBuilder.append(" AND MST.DSTCOD = ? ");
			setParameter(++index, this.operationalFlightVO.getPou());
		}
		else
		{
			queryBuilder.append(" AND MST.DSTCOD IS NULL ");
		}

		if(operationalFlightVO.getContainerNumber()!= null && operationalFlightVO.getContainerNumber().trim().length()>0) {
			queryBuilder.append(" AND MST.CONNUM= ?");
			setParameter(++index, operationalFlightVO
					.getContainerNumber());
		}
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND MALMST.REQDLVTIM = ?");
				}
				setParameter(++index,reqDeliveryTime);
			}
		}
		queryBuilder.append(" ) ");
		queryBuilder.append(" UNION ALL ");

		queryBuilder.append(this.baseQuery2);
		setParameter(++index, this.operationalFlightVO.getCompanyCode());
		setParameter(++index, Integer.valueOf(this.operationalFlightVO.getCarrierId()));

		setParameter(++index, this.operationalFlightVO.getPol());
		if ((this.operationalFlightVO.getPou() != null) && (this.operationalFlightVO.getPou().trim().length() > 0))
		{
			queryBuilder.append(" AND MST.DSTCOD = ? ");
			setParameter(++index, this.operationalFlightVO.getPou());
		}
		else
		{
			queryBuilder.append(" AND MST.DSTCOD IS NULL ");
		}

		if(operationalFlightVO.getContainerNumber()!= null && operationalFlightVO.getContainerNumber().trim().length()>0) {
			queryBuilder.append(" AND MST.CONNUM= ?");
			setParameter(++index, operationalFlightVO
					.getContainerNumber());
		}

		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(DSNMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND DSNMST.REQDLVTIM = ?");
				}
				setParameter(++index,reqDeliveryTime);
			}
		}
		queryBuilder.append(" ) ");
		queryBuilder.append(") mst group by CONNUM,CMPCOD,ASGPRT,DSTCOD,FLTCARIDR,POAFLG, ACPFLG,OFLFLG, FLTCARCOD, CONTYP, CONLSTUPDTIM,CONJRNIDR,POACOD,TRNFLG,USRCOD,RMK,WHSCOD,LOCCOD,FRMCARCOD,ULDLSTUPDTIM,FLTNUM,FLTSEQNUM,LEGSERNUM,ACTULDWGT,CNTIDR,ULDFULIND,TXNCOD,BASCURCOD)ORDER BY CMPCOD,DSTCOD");

		log.debug("Exiting FindOutboundCarrierDetailsFilterQuery   -- >getNativeQuery");
		return queryBuilder.toString();
	}
}
