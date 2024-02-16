package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

import java.util.Collection;

public class ImportFlightOperationsFilterQuery extends NativeQuery {
	private String baseQuery;
	private String baseQueryOne;
	
	private ImportOperationsFilterVO importOperationsFilterVO;
	private Collection<ManifestFilterVO> manifestFilterVOs;
	private static final String SIGHTING_COMPLETE_ULD_STATUS = "RC";


	/**
	 * 
	 * @param breakdownFilterVO
	 * @param baseQuery
	 * @throws SystemException
	 */
	public ImportFlightOperationsFilterQuery(
            ImportOperationsFilterVO importOperationsFilterVO,
            Collection<ManifestFilterVO> manifestFilterVOs, String baseQuery, String baseQueryOne)
			throws SystemException {
		super();
		this.importOperationsFilterVO = importOperationsFilterVO;
		this.manifestFilterVOs = manifestFilterVOs;
		this.baseQuery = baseQuery;
		this.baseQueryOne = baseQueryOne;
	}

	/**
	 * @return String
	 * 
	 */
	public String getNativeQuery() {
		int index = 0;
		StringBuilder query = new StringBuilder(baseQuery);
		/* In case of flight filters are not applied, Flight date set mandatory as part of IASCB-41628
		 * To prevent exceeding count of data*/
		this.setParameter(++index, importOperationsFilterVO.getCompanyCode());
		StringBuilder ulds = new StringBuilder();
		StringBuilder flightFilters = new StringBuilder();




		if (manifestFilterVOs != null && !manifestFilterVOs.isEmpty()) {
			index = appendManifestFilters(index, ulds, flightFilters);
		}
		if (flightFilters.length() > 0) {
			query.append(flightFilters);
		} else {
			query.append(" AND COALESCE(LEG.ATA, COALESCE(LEG.ETA, LEG.STA)) BETWEEN ? AND ?");
			this.setParameter(++index, importOperationsFilterVO.getFromDate());
			this.setParameter(++index, importOperationsFilterVO.getToDate());
		}

		StringBuilder flightSpecificFilter = new StringBuilder();
		index = appendUIFilters(flightSpecificFilter, index );
		if(flightSpecificFilter.length()>0){
			query.append(flightSpecificFilter);
		}

		query.append(" ) ");
		/* END With clause*/

		query.append(" ").append(baseQueryOne).append(" ");

		StringBuilder additionalFilters = new StringBuilder();
		if (!importOperationsFilterVO.isIncludeSightingCompletedULD()) {
			additionalFilters.append(" COALESCE(RMPULD.ULDSTA, 'N') !=? ");
			this.setParameter(++index, SIGHTING_COMPLETE_ULD_STATUS);
		}

		if (!isNullorEmpty(importOperationsFilterVO.getUldNumber())) {
			if (additionalFilters.length() > 0) {
				additionalFilters.append(" AND");
			}
			additionalFilters.append(" ULD.ULDNUM = ?");
			this.setParameter(++index, importOperationsFilterVO.getUldNumber());
		}

		if (ulds.length() > 0) {
			if (additionalFilters.length() > 0) {
				additionalFilters.append(" AND");
			}
			additionalFilters.append(" ULD.ULDNUM IN (").append(ulds.toString()).append(")");
		}

		if (additionalFilters.length() > 0) {
			additionalFilters.append(" AND");
		}
		additionalFilters.append(" RMPULD.RCVHNDFLG IS NULL OR RMPULD.RCVHNDFLG NOT IN ('Y')");
		if (additionalFilters.length() > 0) {
			query.append(" WHERE ").append(additionalFilters);
		}


		query.append(" GROUP BY FLTDETAIL.CMPCOD, FLTDETAIL.FLTCARIDR, FLTDETAIL.FLTNUM, FLTDETAIL.FLTSEQNUM, FLTDETAIL.LEGSERNUM,\n" +
				"ULD.SEGSERNUM, ULD.ULDNUM, FLTSEG.POU, FLTSEG.POL,FLTDETAIL.FLTDAT");
		/* END Planned ULDs */


			return query.toString();
	}

	private int appendManifestFilters(int index, StringBuilder ulds, StringBuilder flightFilters) {
		boolean isFlightFound = false;
		for (ManifestFilterVO manifestFilterVO : manifestFilterVOs) {
			if (!isNullorEmpty(manifestFilterVO.getFlightNumber())) {
				if (!isFlightFound) {
					flightFilters.append(" AND (MALFLT.FLTNUM, MALFLT.FLTCARIDR, MALFLT.FLTSEQNUM) IN ( ");
					isFlightFound = true;
				}
				flightFilters.append("(?,?,?),");
				this.setParameter(++index, manifestFilterVO.getFlightNumber());
				this.setParameter(++index, manifestFilterVO.getCarrierId());
				this.setParameter(++index, manifestFilterVO.getFlightSequenceNumber());
			}
			if (manifestFilterVO.getUlds() != null && !manifestFilterVO.getUlds().isEmpty()) {
				for (String uld : manifestFilterVO.getUlds()) {
					ulds.append("'").append(uld).append("',");
				}
			}
		}
		if (isFlightFound) {
			flightFilters.deleteCharAt(flightFilters.length() - 1);
			flightFilters.append(")");
		}
		if (ulds.length() > 0) {
			ulds.setLength(ulds.length() - 1);
		}
		return index;
	}
	private boolean isNullorEmpty(String val) {
		return (val == null || val.trim().length() < 1);
	}

	private int appendUIFilters( StringBuilder filter, int index){

		if (importOperationsFilterVO.getDestination() != null
				&& importOperationsFilterVO.getDestination().trim().length() > 0) {
			filter.append(" AND MALFLT.ARPCOD = ?");
			this.setParameter(++index, importOperationsFilterVO.getDestination());
		}
		if (!isNullorEmpty(importOperationsFilterVO.getOrigin())) {
			filter.append(" AND FLTSEG.POL = ?");
			this.setParameter(++index, importOperationsFilterVO.getOrigin());
		}
		if (!isNullorEmpty(importOperationsFilterVO.getFlightType())) {
			filter.append(" AND FLTMST.FLTTYP = ?");
			this.setParameter(++index, importOperationsFilterVO.getFlightType());
		}
		if (importOperationsFilterVO.getFlightCarrierId()!= 0) {
			filter.append(" AND FLTMST.FLTCARIDR = ?");
			this.setParameter(++index, importOperationsFilterVO.getFlightCarrierId());
		}
		return index;
	}

}
