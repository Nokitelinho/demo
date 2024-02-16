package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OffloadFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

public class OffloadMailBagsUXFilterQuery extends PageableNativeQuery<MailbagVO> {
	private OffloadFilterVO offloadFilterVO;
	private String baseQuery;

	/** 
	* @author A-7929The constructor..
	* @param offloadFilterVO
	* @param baseQuery
	* @throws SystemException
	*/
	public OffloadMailBagsUXFilterQuery(int pageSize, int totalRecords, OffloadMailBagMapper offloadMailBagMapper,
			String baseQuery, OffloadFilterVO offloadFilterVO) {
		super(pageSize, totalRecords, offloadMailBagMapper, PersistenceController.getEntityManager().currentSession());
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
		String mailbagDOE=offloadFilterVO.getMailbagDestinationExchangeOffice();
		String mailbagDSN=offloadFilterVO.getMailbagDsn();
		String mailbagOOE=offloadFilterVO.getMailbagOriginExchangeOffice();
		String mailBagRSN=offloadFilterVO.getMailbagRsn();
		String mailbagCategory=offloadFilterVO.getMailbagCategoryCode();
		int mailbagYear=offloadFilterVO.getMailbagYear();
		String mailSubClass=offloadFilterVO.getMailbagSubclass();
		StringBuilder builder= new StringBuilder(baseQuery);
		String containerType=offloadFilterVO.getContainerType();
		String mailbagId=offloadFilterVO.getMailbagId();//Added as part of ICRD-205027
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

		if(mailbagOOE!=null && mailbagOOE.trim().length()>0){
			builder.append(" AND MAL.ORGEXGOFC=? ");
			this.setParameter(++index,mailbagOOE);
		}

		if(mailbagDSN!=null && mailbagDSN.trim().length()>0){
			builder.append(" AND MAL.DSN= ?");
			this.setParameter(++index,mailbagDSN);
		}

		if(mailbagDOE!=null && mailbagDOE.trim().length()>0){
			builder.append(" AND MAL.DSTEXGOFC= ?  ");
			this.setParameter(++index,mailbagDOE);
		}

		if(mailBagRSN!=null && mailBagRSN.trim().length()>0){
			builder.append(" AND MAL.RSN= ? ");
			this.setParameter(++index,mailBagRSN);
		}

		if(mailbagCategory!=null && mailbagCategory.trim().length()>0){
			builder.append(" AND MAL.MALCTG=? ");
			this.setParameter(++index,mailbagCategory);
		}

		if(mailbagYear>0){
			builder.append(" AND MAL.YER=?  ");
			this.setParameter(++index,mailbagYear);
		}

		if(mailSubClass!=null && mailSubClass.trim().length()>0){
			builder.append(" AND MAL.MALSUBCLS=? ");
			this.setParameter(++index,mailSubClass);
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

		return builder.toString();
	}
}
