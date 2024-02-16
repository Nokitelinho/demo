package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.OffloadFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

/** 
 * This class is used to append the Query dynamically
 */
public class OffloadDespatchesUXFilterQuery extends PageableNativeQuery<DespatchDetailsVO> {
	private OffloadFilterVO offloadFilterVO;
	private String baseQuery;

	/** 
	* @author A-7929The constructor..
	* @param offloadFilterVO
	* @param baseQuery
	* @throws SystemException
	*/
	public OffloadDespatchesUXFilterQuery(int pageSize, int totalRecords, OffloadDSNMapper offloadDSNMapper,
			String baseQuery, OffloadFilterVO offloadFilterVO) {
		super(pageSize, totalRecords, offloadDSNMapper, PersistenceController.getEntityManager().currentSession());
		this.offloadFilterVO = offloadFilterVO;
		this.baseQuery = baseQuery;
	}

	public String getNativeQuery(){
		String companyCode=offloadFilterVO.getCompanyCode();
		int carrierId=offloadFilterVO.getCarrierId();
		String flightNumber=offloadFilterVO.getFlightNumber();
		int legserialNumber=offloadFilterVO.getLegSerialNumber();
		long flightSequenceNumber=offloadFilterVO.getFlightSequenceNumber();
		String assignmentPort=offloadFilterVO.getPol();
		String containerNumber=offloadFilterVO.getContainerNumber();
		String dsnDOE=offloadFilterVO.getDsnDestinationExchangeOffice();
		String dsn=offloadFilterVO.getDsn();
		String dsnOOE=offloadFilterVO.getDsnOriginExchangeOffice();
		String dsnMailClass=offloadFilterVO.getDsnMailClass();
		StringBuilder builder= new StringBuilder(baseQuery);
		String containerType=offloadFilterVO.getContainerType();
		String mailbagId=offloadFilterVO.getMailbagId();
		int index=0;


		if(companyCode!=null && companyCode.trim().length()>0){
			builder.append(" MST.CMPCOD =?");
			this.setParameter(++index,companyCode);
		}

		if(carrierId>0){
			builder.append(" AND MST.FLTCARIDR=?  ");
			this.setParameter(++index,carrierId);
		}

		if(flightNumber!=null && flightNumber.trim().length()>0){
			builder.append(" AND MST.FLTNUM=? ");
			this.setParameter(++index,flightNumber);
		}

		  /*if(legserialNumber>0){
		         builder.append(" AND ASG.LEGSERNUM = ? ");
		    	   this.setParameter(++index,legserialNumber);
		  }*/

		if(flightSequenceNumber>0){
			builder.append(" AND MST.FLTSEQNUM =? ");
			this.setParameter(++index,flightSequenceNumber);
		}

		if(assignmentPort!=null && assignmentPort.trim().length()>0){
			builder.append(" AND MST.ASGPRT=? ");
			this.setParameter(++index,assignmentPort);
		}

		if(containerNumber!=null && containerNumber.trim().length()>0){
			builder.append(" AND MST.CONNUM= ? ");
			this.setParameter(++index,containerNumber);
		}

		if(dsnOOE!=null && dsnOOE.trim().length()>0){
			builder.append(" AND MAL.ORGEXGOFC=? ");
			this.setParameter(++index,dsnOOE);
		}

		if(dsn!=null && dsn.trim().length()>0){
			builder.append(" AND MAL.DSN= ?");
			this.setParameter(++index,dsn);
		}

		if(dsnDOE!=null && dsnDOE.trim().length()>0){
			builder.append(" AND MAL.DSTEXGOFC= ?  ");
			this.setParameter(++index,dsnDOE);
		}



		//Code added by A-9477 for bug id: IASCB-87753 START
		if(offloadFilterVO.getDsnYear() != null && offloadFilterVO.getDsnYear().trim().length() > 0){
			builder.append(" AND MAL.YER=?  ");
			this.setParameter(++index,Integer.parseInt(offloadFilterVO.getDsnYear()));
		}
		//CODE ADDED BY A-9477 FOR BUG ID : IASCB-87753 END

		if(dsnMailClass!= null && dsnMailClass.trim().length()>0 ){
			builder.append(" AND MAL.MALCLS=?  ");
			this.setParameter(++index,dsnMailClass);
		}


		//Added as part of ICRD-205027 starts
		if(mailbagId!=null && mailbagId.trim().length()>0){
			builder.append(" AND MAL.MALIDR=? ");
			this.setParameter(++index,mailbagId);
		}
		//Added as part of ICRD-205027 ends
		/*
		 * This check was added to avoid the Inbound
		 * MailBags From Being displayed .
		 *
		 */
		builder.append("  AND ( SEGDTL.ARRSTA <> ?  " );
		this.setParameter(++index, MailConstantsVO.FLAG_YES);
		builder.append("  OR SEGDTL.ARRSTA  IS  NULL ) " );

		/*
		 * Added To handle the
		 * Check for Container Type ULD or Bulk in case of
		 * Offload Type being MailBags
		 */

		if(!MailConstantsVO.ALL.equals(containerType)){
			if(MailConstantsVO.ULD_TYPE.equals(containerType)){
				builder.append("AND MST.CONTYP= ?");
				this.setParameter(++index,MailConstantsVO.ULD_TYPE);
			}else{
				builder.append("AND MST.CONTYP= ?");
				this.setParameter(++index,MailConstantsVO.BULK_TYPE);
			}
		}

		builder.append("GROUP BY MST.CMPCOD,MAL.DSN");
		return builder.toString();
	}
}