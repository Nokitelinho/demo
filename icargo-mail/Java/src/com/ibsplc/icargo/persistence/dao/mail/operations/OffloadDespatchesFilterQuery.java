/*
 * OffloadDespatchesFilterQuery.java Created on Jun 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936 This class is used to append the Query dynamically
 */
public class OffloadDespatchesFilterQuery extends NativeQuery { 

	private OffloadFilterVO offloadFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * The constructor..
	 * 
	 * @param offloadFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public OffloadDespatchesFilterQuery(OffloadFilterVO offloadFilterVO,
			String baseQuery) throws SystemException {
		super();
		this.offloadFilterVO = offloadFilterVO;
		this.baseQuery = baseQuery;
	}

	/**
	 * This method is used to append the query dynamically
	 * 
	 * @author a-1936
	 * @return
	 */
	public String getNativeQuery() {
		String companyCode = offloadFilterVO.getCompanyCode();
		int carrierId = offloadFilterVO.getCarrierId();
		String flightNumber = offloadFilterVO.getFlightNumber();
		int legserialNumber = offloadFilterVO.getLegSerialNumber();
		long flightSequenceNumber = offloadFilterVO.getFlightSequenceNumber();
		String assignmentPort = offloadFilterVO.getPol();
		String containerNumber = offloadFilterVO.getContainerNumber();
		String dsn = offloadFilterVO.getDsn();
		String dsnOOE = offloadFilterVO.getDsnOriginExchangeOffice();
		String dsnDOE = offloadFilterVO.getDsnDestinationExchangeOffice();
		String dsnMailClass = offloadFilterVO.getDsnMailClass();
		String year = offloadFilterVO.getDsnYear();
		StringBuilder builder = new StringBuilder(baseQuery);
		StringBuilder joinBuilder = new StringBuilder();
		String containerType = offloadFilterVO.getContainerType();
		int index = 0;
		log.entering("OffloadDespatchesFilterQuery",
				"OffloadDespatchesFilterQuery");
		/*
		 * This StringBuilder Contains The Joins Conditions to be Appended to
		 * the BaseQuery.
		 * 
		 */
		String joins = joinBuilder
		        .append(" ASG.CMPCOD= FLTSEG.CMPCOD AND ")
				.append(" ASG.FLTCARIDR =FLTSEG.FLTCARIDR AND ").append(
						" ASG.FLTNUM=FLTSEG.FLTNUM AND ").append(
						" ASG.FLTSEQNUM=FLTSEG.FLTSEQNUM AND ").append(
						" ASG.ARPCOD=FLTSEG.POL AND ").append(
						" FLTSEG.CMPCOD=ULDSEG.CMPCOD AND ").append(
						" FLTSEG.FLTCARIDR=ULDSEG.FLTCARIDR AND ").append(
						" FLTSEG.FLTNUM=ULDSEG.FLTNUM AND ").append(
						" FLTSEG.FLTSEQNUM=ULDSEG.FLTSEQNUM AND ").append(
						" FLTSEG.SEGSERNUM=ULDSEG.SEGSERNUM AND ").append(
						" ULDSEG.CMPCOD=MST.CMPCOD AND  ").append(
						" ULDSEG.FLTCARIDR=MST.FLTCARIDR AND ").append(
						" ULDSEG.FLTNUM =MST.FLTNUM AND ").append(
						" ULDSEG.FLTSEQNUM=MST.FLTSEQNUM AND ").append(
						" ULDSEG.SEGSERNUM=MST.SEGSERNUM AND ").toString();
		/*
		 * Append the JoinConditions For ContainerType - ALL||ULD
		 */
		if (MailConstantsVO.ALL.equals(containerType)
				|| MailConstantsVO.ULD_TYPE.equals(containerType)) {
			builder
					.append(
							"MST.FLTCARCOD,MST.CONNUM ULDNUM,ULDSEG.ACPBAG BAGS,ULDSEG.ACPWGT WGT, ")
					.append(" MALMST.DSN,MALMST.MALCLS,MALMST.MALSUBCLS,MALMST.MALCTG MALCTGCOD,MALMST.CSGDOCNUM, ")
					.append(" MALMST.ORGEXGOFC,MALMST.DSTEXGOFC,MALMST.YER,	")
					.append(
							" NULL CSGDAT,MALMST.DSPDAT ACPDAT,ULDSEG. STDBAG,ULDSEG. STDWGT,NULL ACPUSR ")
					.append("  ,MALMST.POACOD,MALMST.CSGSEQNUM ").append(
							" FROM MALFLT ASG,MALFLTSEG FLTSEG,").append(
							" MALULDSEGDTL ULDSEG ,MALFLTCON MST,MALMST MALMST ").append(
							" WHERE ").append(joins).append("ULDSEG.MALSEQNUM=MALMST.MALSEQNUM AND ULDSEG.CMPCOD = MALMST.CMPCOD AND").append(
							" ULDSEG.ULDNUM=MST.CONNUM AND");
		}

		if (MailConstantsVO.ALL.equals(containerType)
				|| MailConstantsVO.ULD_TYPE.equals(containerType)) {

			if (companyCode != null && companyCode.trim().length() > 0) {
				builder.append(" MST.CMPCOD =?");
				this.setParameter(++index, companyCode);
			}

			if (carrierId > 0) {
				builder.append(" AND MST.FLTCARIDR=?  ");
				this.setParameter(++index, carrierId);
			}

			if (flightNumber != null && flightNumber.trim().length() > 0) {
				builder.append(" AND MST.FLTNUM=? ");
				this.setParameter(++index, flightNumber);
			}

			/*if (legserialNumber > 0) {
				builder.append(" AND ASG.LEGSERNUM = ? ");
				this.setParameter(++index, legserialNumber);
			}*/

			if (flightSequenceNumber > 0) {
				builder.append(" AND MST.FLTSEQNUM =? "); 				this.setParameter(++index, flightSequenceNumber);
			}

			if (assignmentPort != null && assignmentPort.trim().length() > 0) {
				builder.append(" AND MST.ASGPRT=? ");
				this.setParameter(++index, assignmentPort);
			}

			if (containerNumber != null && containerNumber.trim().length() > 0) {
				builder.append(" AND MST.CONNUM= ? ");
				this.setParameter(++index, containerNumber);
			}

			if (dsn != null && dsn.trim().length() > 0) {
				builder.append(" AND MALMST.DSN =? ");
				this.setParameter(++index, dsn);
			}

			if (dsnOOE != null && dsnOOE.trim().length() > 0) {
				builder.append(" AND MALMST.ORGEXGOFC=? ");
				this.setParameter(++index, dsnOOE);
			}

			if (dsnDOE != null && dsnDOE.trim().length() > 0) {
				builder.append(" AND MALMST.DSTEXGOFC=? ");
				this.setParameter(++index, dsnDOE);
			}

			if (dsnMailClass != null && dsnMailClass.trim().length() > 0) {
				if(dsnMailClass.contains(MailConstantsVO.MALCLS_SEP)) {
					builder.append(" AND MALMST.MALCLS IN ( ");
					index = setMultipleMailClass(dsnMailClass, builder, this, index);
				} else {
					builder.append(" AND MALMST.MALCLS = ? ");
					this.setParameter(++index, dsnMailClass);
				}
			}

			if (year != null && year.trim().length() > 0) {
				builder.append(" AND MALMST.YER= ?");
				this.setParameter(++index, year);
			}
			/*
			 * THIS CHECK IS ADDED TO AVOID THE ARRIVED DSNS FROM BEING LISTED
			 */
			builder.append("  AND ULDSEG.RCVBAG= ? ");
			this.setParameter(++index, MailConstantsVO.ZERO);

		}

/*		if (MailConstantsVO.ALL.equals(containerType)) {
			builder
					.append("UNION")
					.append("(")
					.append(baseQuery)
					.append(
							"MST.FLTCARCOD,ULDSEG.ULDNUM,ULDSEG.ACPBAG BAGS,ULDSEG.ACPWGT WGT,")
					.append("MALMST.DSN, MALMST.MALCLS,MALMST.MALSUBCLS,MALMST.MALCTG MALCTGCOD,MALMST.CSGDOCNUM ,")
					.append("MALMST.ORGEXGOFC, MALMST.DSTEXGOFC,MALMST.YER,")
					.append(
							" NULL CSGDAT,MALMST.DSPDAT ACPDAT,ULDSEG. STDBAG,ULDSEG. STDWGT,NULL ACPUSR")
					.append("  ,MALMST.POACOD,MALMST.CSGSEQNUM ").append(
							" FROM MALFLT ASG,MALFLTSEG FLTSEG, ")
					.append(" MALULDSEGDTL ULDSEG ,MALFLTCON MST, MALMST MALMST").append(
							" WHERE ").append(" ASG.CMPCOD    = FLTSEG.CMPCOD AND ASG.FLTCARIDR   =FLTSEG.FLTCARIDR AND ASG.FLTNUM      =FLTSEG.FLTNUM ")
.append(" AND ASG.FLTSEQNUM   =FLTSEG.FLTSEQNUM AND ASG.ARPCOD      =FLTSEG.POL AND FLTSEG.CMPCOD   =ULDSEG.CMPCOD ")
.append(" AND FLTSEG.FLTCARIDR=ULDSEG.FLTCARIDR AND FLTSEG.FLTNUM   =ULDSEG.FLTNUM AND FLTSEG.FLTSEQNUM=ULDSEG.FLTSEQNUM ")
.append(" AND FLTSEG.SEGSERNUM=ULDSEG.SEGSERNUM AND ULDSEG.CMPCOD   =MST.CMPCOD AND ULDSEG.FLTCARIDR=MST.FLTCARIDR ")
.append(" AND ULDSEG.FLTNUM   =MST.FLTNUM AND ULDSEG.FLTSEQNUM=MST.FLTSEQNUM AND ULDSEG.SEGSERNUM=MST.SEGSERNUM ")
.append(" AND ULDSEG.CMPCOD   = MALMST.CMPCOD AND ULDSEG.MALSEQNUM=MALMST.MALSEQNUM AND ULDSEG.CONNUM     =MST.CONNUM AND ");
		} else */
			if (MailConstantsVO.BULK_TYPE.equals(containerType)) {
			builder
					.append(
							"MST.FLTCARCOD,ULDSEG.ULDNUM,ULDSEG.ACPBAG BAGS,ULDSEG.ACPWGT WGT,")
					.append("MALMST.DSN, MALMST.MALCLS,MALMST.MALSUBCLS,MALMST.MALCTG MALCTGCOD, ")
					.append(
							"MALMST.ORGEXGOFC, MALMST.DSTEXGOFC,MALMST.YER,MALMST.CSGDOCNUM,")
					.append(
							" NULL CSGDAT,MALMST.DSPDAT ACPDAT,ULDSEG. STDBAG,ULDSEG. STDWGT,NULL ACPUSR")
					.append("  ,MALMST.POACOD,MALMST.CSGSEQNUM ").append(
							" FROM MALFLT ASG,MALFLTSEG FLTSEG, ")
					.append(" MALULDSEGDTL ULDSEG ,MALFLTCON MST, MALMST MALMST").append(
							" WHERE ").append(joins).append("ULDSEG.MALSEQNUM=MALMST.MALSEQNUM AND ULDSEG.CMPCOD = MALMST.CMPCOD AND").append(
							"  ULDSEG.CONNUM=MST.CONNUM AND");
		}

		/*
		 * The filter Conditiond for UNion Query in case of Container Type as
		 * All .Repeat the Whole set of FilterConditions For Union Part to fetch
		 * DSNS from both ULD and Barrows...
		 */
		if (MailConstantsVO.BULK_TYPE.equals(containerType)) { 

			if (companyCode != null && companyCode.trim().length() > 0) {
				builder.append(" MST.CMPCOD =?");
				this.setParameter(++index, companyCode);
			}

			if (carrierId > 0) {
				builder.append(" AND MST.FLTCARIDR=?  ");
				this.setParameter(++index, carrierId);
			}

			if (flightNumber != null && flightNumber.trim().length() > 0) {
				builder.append(" AND MST.FLTNUM=? ");
				this.setParameter(++index, flightNumber);
			}

			/*if (legserialNumber > 0) {
				builder.append(" AND ASG.LEGSERNUM = ? ");
				this.setParameter(++index, legserialNumber);
			}*/

			if (flightSequenceNumber > 0) {
				builder.append(" AND MST.FLTSEQNUM =? ");
				this.setParameter(++index, flightSequenceNumber);
			}

			if (assignmentPort != null && assignmentPort.trim().length() > 0) {
				builder.append(" AND MST.ASGPRT=? ");
				this.setParameter(++index, assignmentPort);
			}

			if (containerNumber != null && containerNumber.trim().length() > 0) {
				builder.append(" AND MST.CONNUM= ? ");
				this.setParameter(++index, containerNumber);
			}

			if (dsn != null && dsn.trim().length() > 0) {
				builder.append(" AND MALMST.DSN =? ");
				this.setParameter(++index, dsn);
			}

			if (dsnOOE != null && dsnOOE.trim().length() > 0) {
				builder.append(" AND MALMST.ORGEXGOFC=? ");
				this.setParameter(++index, dsnOOE);
			}

			if (dsnDOE != null && dsnDOE.trim().length() > 0) {
				builder.append(" AND MALMST.DSTEXGOFC=? ");
				this.setParameter(++index, dsnDOE);
			}

			if (dsnMailClass != null && dsnMailClass.trim().length() > 0) {
				if(dsnMailClass.contains(MailConstantsVO.MALCLS_SEP)) {
					builder.append(" AND MALMST.MALCLS IN ( ");
					index = setMultipleMailClass(dsnMailClass, builder, this, index);					
				} else {
					builder.append(" AND MALMST.MALCLS = ? ");
					this.setParameter(++index, dsnMailClass);
				}
			}

			if (year != null && year.trim().length() > 0) {
				builder.append(" AND MALMST.YER= ?");
				this.setParameter(++index, year);
			}
			/*
			 * THIS CHECK IS ADDED TO AVOID THE ARRIVED DSNS FROM BEING LISTED
			 */
			builder.append("  AND ULDSEG.RCVBAG= ? ");
			this.setParameter(++index, MailConstantsVO.ZERO);

			if (MailConstantsVO.ALL.equals(containerType)) {
				builder
						.append(" ORDER BY  CMPCOD,DSN,MALSUBCLS,MALCTGCOD,ORGEXGOFC,DSTEXGOFC,YER,CSGDOCNUM ");
			}
		}

		return builder.toString();
	}

	/**
	 * Splits and set the classes as each qry param
	 * Feb 7, 2007, A-1739
	 * @param mailClass
	 * @param stringBuilder
	 * @param query
	 * @param index
	 * @return 
	 */
	private int setMultipleMailClass(String mailClass, 
			StringBuilder stringBuilder, NativeQuery query, int index) {
		String[] mailClasses = mailClass.split(MailConstantsVO.MALCLS_SEP);
		for(String mClass : mailClasses) {
			stringBuilder.append(" ?,");
			setParameter(++index, mClass);
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		stringBuilder.append( " ) ");		
		return index;
	}
}
