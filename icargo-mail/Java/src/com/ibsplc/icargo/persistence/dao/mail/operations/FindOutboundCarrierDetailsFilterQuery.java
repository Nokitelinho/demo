package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FindOutboundCarrierDetailsFilterQuery  extends PageableNativeQuery<ContainerDetailsVO> {
     private StringBuilder baseQuery1;
	 private String baseQuery2;
	 private OperationalFlightVO operationalFlightVO;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	public FindOutboundCarrierDetailsFilterQuery(OperationalFlightVO operationalFlightVO,StringBuilder baseQuery1,String baseQuery2,FindContainersinCarrierMapper mapper) throws SystemException {
	   super(operationalFlightVO.getRecordsPerPage(),0, mapper);
		this.baseQuery1 = baseQuery1;
		this.baseQuery2 = baseQuery2;
		this.operationalFlightVO = operationalFlightVO;
	}
	@Override
	public String getNativeQuery() {
		log.entering("FindOutboundCarrierDetailsFilterQuery", "Inside the NativeQuery");
		int index =0;
		LocalDate reqDeliveryTime  = this.operationalFlightVO.getReqDeliveryTime();
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
				String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
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
		    
		    this.log.exiting("FindOutboundCarrierDetailsFilterQuery", "getNativeQuery");
		    return queryBuilder.toString();
	}
}
