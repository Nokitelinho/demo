package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
public class FindOutboundFlightDetailsFilterQuery extends PageableNativeQuery<ContainerDetailsVO> {
	private StringBuilder baseQuery1;
	private String baseQuery2;
	private OperationalFlightVO operationalFlightVO;

	public FindOutboundFlightDetailsFilterQuery(OperationalFlightVO operationalFlightVO, StringBuilder baseQuery1,
												String baseQuery2, FindContainersinFlightMapper mapper) {
		super(operationalFlightVO.getRecordsPerPage(), 0, mapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.operationalFlightVO = operationalFlightVO;
	}

	@Override
	public String getNativeQuery(){
		log.debug("Entering FindOutboundFlightDetailsFilterQuery --> Inside the NativeQuery");
		ZonedDateTime reqDeliveryTime  = this.operationalFlightVO.getReqDeliveryTime();
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
			String rqdDlvTime=reqDeliveryTime.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND MALMST.REQDLVTIM = ?");
				}
				setParameter(++index, LocalDateMapper.toLocalDate(reqDeliveryTime));
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
			String rqdDlvTime=reqDeliveryTime.format(MailConstantsVO.DATE_TIME_FORMATTER_YYYYMMDDHHMM);
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					queryBuilder.append(" AND TRUNC(MALMST.REQDLVTIM) = ?");
				}else{
					queryBuilder.append(" AND MALMST.REQDLVTIM = ?");
				}
				setParameter(++index, LocalDateMapper.toLocalDate(reqDeliveryTime));
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
		log.debug("Exiting FindOutboundFlightDetailsFilterQuery --> getNativeQuery");
		return queryBuilder.toString();

	}

}
