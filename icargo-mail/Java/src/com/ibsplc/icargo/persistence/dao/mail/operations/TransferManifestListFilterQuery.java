/*
 * TransferManifestListFilterQuery.java  Created on 03 April 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.util.Date;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author a-1936
 *
 */
public class TransferManifestListFilterQuery extends PageableNativeQuery<TransferManifestVO> {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	private static final String DATE_FORMAT = "yyyyMMdd";
	private String baseQuery;

	private TransferManifestFilterVO transferManifestFilterVO;

	/**
	 * @author a-1936
	 * @param mailbagEnquiryFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */

	//Added by A-5220 for ICRD-21098 starts

	public TransferManifestListFilterQuery(TransferManifestListMapper mapper,
			TransferManifestFilterVO transferManifestFilterVO, String baseQuery)
			throws SystemException {
		super(transferManifestFilterVO.getTotalRecordsCount(), mapper);
		this.transferManifestFilterVO = transferManifestFilterVO;
		this.baseQuery = baseQuery;		
	}

	//Added by A-5220 for ICRD-21098 ends
	/**
	 * This method is used to append the Query dynamically based on the
	 * Different Filter Conditions
	 */

	public String getNativeQuery() {
		log.entering("INSIDE THE FILTER QUERY", "getNativeQuery()");
		StringBuilder builder = new StringBuilder(baseQuery);
		Date fromFltDate = null;
		Date toFltDate = null;

		String fromFlightDateString = null;
		String toFlightDateString = null;

		int index = 0;
		LocalDate fromDate = transferManifestFilterVO.getFromDate();
		LocalDate toDate = transferManifestFilterVO.getToDate();
		LocalDate fromFlightDate = transferManifestFilterVO.getInFlightDate();
		LocalDate toFlightDate = transferManifestFilterVO.getOutFlightDate();
		String transferStatus =  transferManifestFilterVO.getTransferStatus();
		String airportCode  = transferManifestFilterVO.getAirportCode();
		
		log.log(Log.FINE, "&&&&&**********transferManifestFilterVO",
				transferManifestFilterVO);
		if (fromFlightDate != null) {
			fromFltDate = fromFlightDate.toSqlDate();
			fromFlightDateString = String.valueOf(fromFltDate);
		}
		if (toFlightDate != null) {
			toFltDate = toFlightDate.toSqlDate();
			toFlightDateString = String.valueOf(toFltDate);
		}

		builder.append(" WHERE MFT.CMPCOD = ? ");
		this.setParameter(++index, transferManifestFilterVO.getCompanyCode());
		if (transferManifestFilterVO.getReferenceNumber() != null
				&& transferManifestFilterVO.getReferenceNumber().trim()
						.length() > 0) {
			builder.append(" AND  MFT.TRFMFTIDR = ? ");
			this.setParameter(++index, transferManifestFilterVO.getReferenceNumber());
		}
		if (transferManifestFilterVO.getInCarrierCode() != null
				&& transferManifestFilterVO.getInCarrierCode().trim().length() > 0) {
			builder.append(" AND  MFT.FRMCARCOD = ? ");
			this.setParameter(++index, transferManifestFilterVO
					.getInCarrierCode());
		}

		if (transferManifestFilterVO.getOutCarrierCode() != null
				&& transferManifestFilterVO.getOutCarrierCode().trim().length() > 0) {
			builder.append(" AND  MFT.ONWCARCOD = ? ");
			this.setParameter(++index, transferManifestFilterVO
					.getOutCarrierCode());
		}

		if (transferManifestFilterVO.getOutFlightNumber() != null
				&& transferManifestFilterVO.getOutFlightNumber().trim()
						.length() > 0) {
			builder.append(" AND  MFT.ONWFLTNUM = ? ");
			this.setParameter(++index, transferManifestFilterVO
					.getOutFlightNumber());
		}
		if (transferManifestFilterVO.getInFlightNumber() != null
				&& transferManifestFilterVO.getInFlightNumber().trim().length() > 0) {
			builder.append(" AND  MFT.FRMFLTNUM = ? ");
			this.setParameter(++index, transferManifestFilterVO
					.getInFlightNumber());
		}

		if (fromDate != null) {
			builder
					.append(" AND TO_NUMBER(TO_CHAR(MFT.TRFDAT,'YYYYMMDD')) >= ? ");
			this.setParameter(++index, Integer.valueOf(Integer.parseInt(fromDate.toStringFormat(DATE_FORMAT).substring(0, 8))));
		}

		if (toDate != null) {
			builder
					.append(" AND TO_NUMBER(TO_CHAR(MFT.TRFDAT,'YYYYMMDD')) <= ? ");
			this.setParameter(++index, Integer.valueOf(Integer.parseInt(toDate.toStringFormat(DATE_FORMAT).substring(0, 8))));
		}
		if (fromFlightDate != null) {
			builder
					.append(" AND  TRUNC( MFT.FRMFLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
			this.setParameter(++index, fromFlightDateString);
		}
		if (toFlightDate != null) {
			builder
					.append(" AND  TRUNC( MFT.ONWFLTDAT) = to_date(?, 'yyyy-MM-dd')  ");
			this.setParameter(++index, toFlightDateString);
		}
		//added by A-7815 as part of IASCB-87764
		if(transferStatus!=null && transferStatus.trim().length()>0) {
			builder.append(" AND  MFT.TRFSTA = ?  ");
			this.setParameter(++index, transferStatus);
		}
		if(airportCode!=null && airportCode.trim().length()>0) {
			builder.append(" AND  MFT.ARPCOD = ?  ");
			this.setParameter(++index, airportCode);
		}
		builder
				.append("GROUP BY MFT.CMPCOD,  MFT.TRFMFTIDR,  MFT.ARPCOD,  MFT.TRFDAT,  MFT.ONWFLTNUM,  MFT.ONWCARCOD,  MFT.FRMCARCOD,  MFT.FRMFLTNUM,  MFT.FRMFLTDAT,  MFT.ONWFLTDAT, MFT.TRFSTA, MFT.FRMFLTSEQNUM,MFT.FRMSEGSERNUM ");
		
		//Added by A-5220 for ICRD-21098 starts
		
		builder.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		
		log.log(Log.INFO, "Query in Filter Query: ", builder.toString());
		return builder.toString();

	}
}
