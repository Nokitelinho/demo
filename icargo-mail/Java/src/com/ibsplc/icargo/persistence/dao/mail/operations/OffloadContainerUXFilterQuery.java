/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7929
 *
 */
public class OffloadContainerUXFilterQuery extends PageableNativeQuery<ContainerVO>{

	private OffloadFilterVO offloadFilterVO;

	private String baseQuery;
	private Query offloadContainerquery;

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");


/*	public OffloadContainerUXFilterQuery(int defaultPageSize, int recordCount,OffloadFilterVO offloadFilterVO,
			String baseQuery) throws SystemException {
		super(defaultPageSize,recordCount);
		this.offloadFilterVO = offloadFilterVO;
		this.baseQuery = baseQuery;
	}*/

	public OffloadContainerUXFilterQuery(int pageSize, int totalRecords,
			OffloadContainerMultiMapper offloadContainerMultiMapper, String baseQuery,
			OffloadFilterVO offloadFilterVO, Query offloadContainerquery) throws SystemException {
		super(pageSize,totalRecords,offloadContainerMultiMapper); 
    	log.entering("OffloadContainerUXFilterQuery","constructor");
		this.offloadFilterVO = offloadFilterVO;
		this.baseQuery = baseQuery;
		this.offloadContainerquery = offloadContainerquery;
		
	}

	@Override
	public String getNativeQuery() {
		String companyCode = offloadFilterVO.getCompanyCode();
		int carrierId = offloadFilterVO.getCarrierId();
		String flightNumber = offloadFilterVO.getFlightNumber();
		int legserialNumber = offloadFilterVO.getLegSerialNumber();
		long flightSequenceNumber = offloadFilterVO.getFlightSequenceNumber();
		String assignmentPort = offloadFilterVO.getPol();
		String containerNumber = offloadFilterVO.getContainerNumber();
		StringBuilder builder = new StringBuilder(baseQuery);
		
		StringBuilder offloadUldJoins = null;
		StringBuilder offloadBulkJoins = null;
		String containerType = offloadFilterVO.getContainerType();
		int index = 0;
		log.entering("OffloadContainerFilterQuery", "getNativeQuery");
		
		/*
		 * This part is used to append the Join Conditions when the Container
		 * Type is ULD Or ALL
		 */
		if (MailConstantsVO.ALL.equals(containerType)
				|| MailConstantsVO.ULD_TYPE.equals(containerType)) {
			offloadUldJoins = new StringBuilder().append(
					"MST.FLTCARCOD, SUM(COALESCE(SEGDTL.ACPWGT,0)) WGT,SUM(COALESCE(SEGDTL.ACPBAG,0)) BAGS,SEG.ULDNUM,ASG.FLTDAT ")
					.append(" FROM MALFLTCON MST,MALULDSEG SEG " )
					.append(" LEFT OUTER JOIN MALULDSEGDTL SEGDTL ON ").append(
						    " SEG.CMPCOD    = SEGDTL.CMPCOD AND ") .append(
						    " SEG.ULDNUM    = SEGDTL.ULDNUM AND ").append(
							" SEG.FLTCARIDR = SEGDTL.FLTCARIDR AND ").append(
							" SEG.FLTNUM    = SEGDTL.FLTNUM AND ").append(
							" SEG.FLTSEQNUM = SEGDTL.FLTSEQNUM AND ").append(
							" SEG.SEGSERNUM = SEGDTL.SEGSERNUM, ").append(
							" MALFLT ASG ").append(
							" WHERE ").append(" MST.CMPCOD=SEG.CMPCOD AND ")
					.append(" MST.FLTCARIDR=SEG.FLTCARIDR AND  ").append(
							" MST.FLTNUM=SEG.FLTNUM AND ").append(
							" MST.FLTSEQNUM= SEG.FLTSEQNUM AND").append(
							" MST.SEGSERNUM=SEG.SEGSERNUM AND ").append(
							" MST.CONNUM=SEG.ULDNUM AND ").append(
						    " MST.CMPCOD =ASG.CMPCOD AND ").append(
							" MST.FLTCARIDR=ASG.FLTCARIDR AND ").append(
							" MST.FLTNUM   =ASG.FLTNUM AND ").append(
							" MST.FLTSEQNUM= ASG.FLTSEQNUM AND ").append(
							" MST.FLTSEQNUM= ASG.FLTSEQNUM AND MST.ASGPRT = ASG.ARPCOD AND ");
			builder.append(offloadUldJoins);
		}
		/*
		 * This part is used to append the Join Conditions when the Container
		 * Type is BULK Or ALL
		 */
		if (MailConstantsVO.ALL.equals(containerType)
				|| MailConstantsVO.BULK_TYPE.equals(containerType)) {
			offloadBulkJoins = new StringBuilder()
					.append(
							"MST.FLTCARCOD,SUM(COALESCE(CONSEG.ACPWGT,0)) WGT,SUM(COALESCE(CONSEG.ACPBAG,0)) BAGS,CONSEG.ULDNUM,ASG.FLTDAT")
					.append(
							" FROM MALFLTCON MST, MALULDSEG SEG, MALULDSEGDTL CONSEG, MALFLT ASG ")
					.append(" WHERE ").append(" MST.CMPCOD = SEG.CMPCOD ")
					.append(" AND MST.FLTCARIDR = SEG.FLTCARIDR ").append(
							" AND MST.FLTNUM = SEG.FLTNUM ").append(
							" AND MST.FLTSEQNUM = SEG.FLTSEQNUM ").append(
							" AND MST.SEGSERNUM = SEG.SEGSERNUM   ").append(
							" AND SEG.FLTCARIDR=CONSEG.FLTCARIDR ").append(
							" AND SEG. FLTNUM=CONSEG.FLTNUM   ").append(
							" AND SEG.FLTSEQNUM=CONSEG.FLTSEQNUM  ").append(
							" AND SEG.SEGSERNUM=CONSEG.SEGSERNUM  ").append(
							" AND SEG.ULDNUM=CONSEG.ULDNUM  ").append(
							" AND SEG.CMPCOD=CONSEG.CMPCOD   ").append(
							" AND MST.CONNUM=CONSEG.CONNUM AND  ").append(
							//" MST.CONNUM=SEG.ULDNUM AND ").append(
						    " MST.CMPCOD =ASG.CMPCOD AND ").append(
							" MST.FLTCARIDR=ASG.FLTCARIDR AND ").append(
							" MST.FLTNUM   =ASG.FLTNUM AND ").append(
							" MST.FLTSEQNUM= ASG.FLTSEQNUM AND ");
		}

		// Appends The Filter Conditions For Uld
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
				builder.append(" AND MST.LEGSERNUM = ? ");
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
			builder.append(" AND MST.CONTYP= ? ");
			this.setParameter(++index, MailConstantsVO.ULD_TYPE);

			/*
			 * THIS CHECK IS ADDED TO AVOID THE ARRIVED DSNS FROM BEING LISTED
			 */
			builder.append("  AND SEG.RCVBAG= ? ");
			this.setParameter(++index, MailConstantsVO.ZERO);
			/*
			 * This check is actuually added to fetch the Uld which are not
			 * arrived Ones.
			 */

			builder.append("  AND  MST.ARRSTA= ? ");
			this.setParameter(++index, MailConstantsVO.FLAG_NO);

			builder
			.append(" group by MST.CMPCOD,MST.CONNUM,MST.ASGPRT,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.LEGSERNUM,")
			.append(" MST.ACPFLG,MST.POAFLG,MST.SEGSERNUM,MST.CONTYP,MST.POU,MST.DSTCOD,MST.CONJRNIDR," )
			.append(" MST.POACOD,MST.ULDFULIND,MST.FLTCARCOD,SEG.ULDNUM,ASG.FLTDAT,MST.ACTWGTSTA,MST.ULDFULIND ");

		}
		/*
		 * Enters this part when the ContainerType is ALL||BULK In case of
		 * ContainerType Being ALL construct a Union Query
		 */
		if (MailConstantsVO.ALL.equals(containerType)) {
			builder.append("UNION ALL ").append("(").append(offloadContainerquery).append(
					offloadBulkJoins);
		} else if (MailConstantsVO.BULK_TYPE.equals(containerType)) {
			builder.append(offloadBulkJoins);
		}
		// Appends The Filter Conditions For Uld
		if (MailConstantsVO.ALL.equals(containerType)
				|| MailConstantsVO.BULK_TYPE.equals(containerType)) {

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
				builder.append(" AND MST.LEGSERNUM = ? ");
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

			builder.append(" AND MST.CONTYP= ? ");
			this.setParameter(++index, MailConstantsVO.BULK_TYPE);

			/*
			 * THIS CHECK IS ADDED TO AVOID THE ARRIVED DSNS FROM BEING LISTED
			 */
			builder.append("  AND SEG.RCVBAG= ? ");
			this.setParameter(++index, MailConstantsVO.ZERO);

			/*
			 * This check is actually added to fetch the Uld which are not
			 * arrived .
			 */

			builder.append("  AND  MST.ARRSTA= ? ");
			this.setParameter(++index, MailConstantsVO.FLAG_NO);
			
			builder
			.append(" group by MST.CMPCOD,MST.CONNUM,MST.ASGPRT,MST.FLTCARIDR,MST.FLTNUM,MST.FLTSEQNUM,MST.LEGSERNUM,")
			.append(" MST.ACPFLG,MST.POAFLG,MST.SEGSERNUM,MST.CONTYP,MST.POU,MST.DSTCOD,MST.CONJRNIDR," )
			.append(" MST.POACOD,MST.ULDFULIND,MST.FLTCARCOD,CONSEG.ULDNUM,ASG.FLTDAT,MST.ACTWGTSTA,MST.ULDFULIND ");


			if (MailConstantsVO.ALL.equals(containerType)) {
				builder
						.append(" ) ORDER BY CMPCOD,CONNUM,FLTCARIDR,FLTNUM,FLTSEQNUM,SEGSERNUM,ULDNUM");
			}
		}

		return builder.toString();
	}

}
