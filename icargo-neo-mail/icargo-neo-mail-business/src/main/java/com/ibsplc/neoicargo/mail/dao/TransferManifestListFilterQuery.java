package com.ibsplc.neoicargo.mail.dao;

import java.time.format.DateTimeFormatter;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.TransferManifestFilterVO;
import com.ibsplc.neoicargo.mail.vo.TransferManifestVO;

/** 
 * @author a-1936
 */
public class TransferManifestListFilterQuery extends PageableNativeQuery<TransferManifestVO> {
	private static final String DATE_FORMAT = "yyyyMMdd";
	private String baseQuery;
	private TransferManifestFilterVO transferManifestFilterVO;

	/** 
	* @author a-1936
	* @param baseQuery
	* @throws SystemException
	*/
	public TransferManifestListFilterQuery(TransferManifestListMapper mapper,
			TransferManifestFilterVO transferManifestFilterVO, String baseQuery) {
		super(transferManifestFilterVO.getTotalRecordsCount(), mapper, PersistenceController.getEntityManager().currentSession());
		this.transferManifestFilterVO = transferManifestFilterVO;
		this.baseQuery = baseQuery;
	}
	public String getNativeQuery() {

		StringBuilder builder = new StringBuilder(baseQuery);

		String fromFlightDateString = null;
		String toFlightDateString = null;

		int index = 0;
		ZonedDateTime fromDate = transferManifestFilterVO.getFromDate();
		ZonedDateTime toDate = transferManifestFilterVO.getToDate();
		ZonedDateTime fromFlightDate = transferManifestFilterVO.getInFlightDate();
		ZonedDateTime toFlightDate = transferManifestFilterVO.getOutFlightDate();
		String transferStatus =  transferManifestFilterVO.getTransferStatus();
		String airportCode  = transferManifestFilterVO.getAirportCode();


		if (fromFlightDate != null) {
			fromFlightDateString = fromFlightDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		if (toFlightDate != null) {
			toFlightDateString = toFlightDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
			this.setParameter(++index, Integer.valueOf(Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))));
		}

		if (toDate != null) {
			builder
					.append(" AND TO_NUMBER(TO_CHAR(MFT.TRFDAT,'YYYYMMDD')) <= ? ");
			this.setParameter(++index, Integer.valueOf(Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))));
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

		return builder.toString();

	}
}
