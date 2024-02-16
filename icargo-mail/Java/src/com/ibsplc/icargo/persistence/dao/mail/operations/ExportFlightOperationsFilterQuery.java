package com.ibsplc.icargo.persistence.dao.mail.operations;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.icargo.framework.util.time.LocalDate;

import java.util.Collection;

public class ExportFlightOperationsFilterQuery extends NativeQuery {
	private String baseQuery;
	private ImportOperationsFilterVO importOperationsFilterVO;
	private Collection<ManifestFilterVO> manifestFilterVOs;
	public ExportFlightOperationsFilterQuery(ImportOperationsFilterVO importOperationsFilterVO,
			Collection<ManifestFilterVO> manifestFilterVOs, String baseQry) throws SystemException {
		super();
		this.importOperationsFilterVO = importOperationsFilterVO;
		this.manifestFilterVOs = manifestFilterVOs;
		this.baseQuery = baseQry;		
	}

	@Override
	public String getNativeQuery() {
		int index = 0;
		StringBuilder stringBuilder = new StringBuilder(baseQuery);

		stringBuilder.append(" WHERE LEG.CMPCOD =  ?");
		this.setParameter(++index, importOperationsFilterVO.getCompanyCode());
		if (ImportOperationsFilterVO.FLAG_YES.equals(importOperationsFilterVO.getHandoverCompletionFlag())) {
			stringBuilder.append(" AND RMPULD.RMPHDLSTF IS NOT NULL");
		} else {
			if (ImportOperationsFilterVO.FLAG_NO.equals(importOperationsFilterVO.getHandoverCompletionFlag())) {
				stringBuilder.append(" AND RMPULD.RMPHDLSTF IS NULL");
			}
		}
		index = this.populateFlightFilter(stringBuilder, index);
		appendFlightAndUldFilterQuery(stringBuilder, index);
		stringBuilder
		.append(" order by FLTMST.FLTNUM,FLTMST.FLTCARIDR,FLTMST.FLTSEQNUM");

		return stringBuilder.toString();
	}

	private int populateFlightFilter(StringBuilder stringBuilder, int index){

	
		if (importOperationsFilterVO.getFromDate() != null) {
			stringBuilder.append(" AND LEG.STD >= ? ");				
			this.setParameter(++index, new LocalDate(importOperationsFilterVO.getFromDate(),true));
		}	   

		if (importOperationsFilterVO.getToDate() != null) {
			stringBuilder.append("AND LEG.STD <= ? ");				
			this.setParameter(++index, new LocalDate(importOperationsFilterVO.getToDate(),true));
		}
		
		if (!isNullorEmpty(importOperationsFilterVO.getDestination())) {
			stringBuilder.append(" AND LEG.LEGDST = ?");
			this.setParameter(++index, importOperationsFilterVO.getDestination());
		}
		
		if (!isNullorEmpty(importOperationsFilterVO.getOrigin())) {
			stringBuilder.append(" AND LEG.LEGORG = ?");
			this.setParameter(++index, importOperationsFilterVO.getOrigin());
		}
		
		if (!isNullorEmpty(importOperationsFilterVO.getFlightType())) {
			stringBuilder.append(" AND FLTMST.FLTTYP = ?");
			this.setParameter(++index, importOperationsFilterVO.getFlightType());
		}
		
		if (importOperationsFilterVO.getFlightCarrierId()!= 0) {
			stringBuilder.append(" AND LEG.FLTCARIDR = ?");
			this.setParameter(++index, importOperationsFilterVO.getFlightCarrierId());
		}
		return index;
	}
	
	private void appendFlightAndUldFilterQuery(StringBuilder stringBuilder, int index) {
		if (manifestFilterVOs != null && !manifestFilterVOs.isEmpty()) {
			StringBuilder ulds = new StringBuilder();
			boolean isFlightFound = false;
			for (ManifestFilterVO manifestFilterVO : manifestFilterVOs) {
				if (manifestFilterVO.getFlightNumber() != null) {
					if (!isFlightFound) {
						stringBuilder.append(" AND (LEG.FLTNUM,LEG.FLTCARIDR,LEG.FLTSEQNUM) IN ( ");
						isFlightFound = true;
					}
					stringBuilder.append("(?,?,?),");
				this.setParameter(++index, manifestFilterVO.getFlightNumber());
				this.setParameter(++index, manifestFilterVO.getCarrierId());
					this.setParameter(++index, manifestFilterVO.getFlightSequenceNumber());
				}
				appendUldDetails(manifestFilterVO, ulds);
			}
			if (isFlightFound) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				stringBuilder.append(")");
			}
			if (ulds.length() > 0) {
				ulds.setLength(ulds.length() - 1);
				stringBuilder.append(" AND ULD.ULDNUM IN (").append(ulds.toString()).append(")");
			}
		}
	}

	private void appendUldDetails(ManifestFilterVO manifestFilterVO, StringBuilder ulds) {
		if (manifestFilterVO.getUlds() != null && !manifestFilterVO.getUlds().isEmpty()) {
			for (String uld : manifestFilterVO.getUlds()) {
				ulds.append("'").append(uld).append("',");
			}
		}
	}

	private boolean isNullorEmpty(String val) {
		return (val == null || val.trim().length() <= 0);
	}

}
