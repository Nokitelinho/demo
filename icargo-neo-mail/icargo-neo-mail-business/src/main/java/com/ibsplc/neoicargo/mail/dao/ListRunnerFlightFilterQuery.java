package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

import java.util.Objects;

/** 
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListRunnerFlightFilterQuery.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-5526	:	12-Oct-2018	:	Draft
 */
public class ListRunnerFlightFilterQuery extends PageableNativeQuery<RunnerFlightVO> {
	private RunnerFlightFilterVO runnerFlightFilterVO;
	private String baseQry;
	private String additionalQuery;
	private boolean isOracleDataSource;
	private static final String LISTAGG_DWSBLK_CONIDR = "listagg(DWSBLK.CONIDR,',') within GROUP(ORDER BY DWSBLK.CONIDR)";
	private static final String STRINGAGG_DWSBLK_CONIDR = "string_agg(DWSBLK.CONIDR, ',' ORDER BY DWSBLK.CONIDR)";
	private static final String CAST_INTEGER = ":: INTEGER";
	private static final String EMPTY = "";
	private static final String LIKE_BULK = "%BULK%";

	public ListRunnerFlightFilterQuery(int pageSize, int totalRecordsCount, boolean isOracleDataSource,
			RunnerFlightFilterVO runnerFlightFilterVO, String baseQuery, String additionalQuery,
			RunnerFlightMultiMapper runnerFlightMultiMapper) {
		super(pageSize, totalRecordsCount, runnerFlightMultiMapper, PersistenceController.getEntityManager().currentSession());
		this.runnerFlightFilterVO = runnerFlightFilterVO;
		this.baseQry = getDBSpecificQueryForListRunners(baseQuery);
		this.additionalQuery = additionalQuery;
		this.isOracleDataSource = isOracleDataSource;
	}

	private String getDBSpecificQueryForListRunners(String qry) {
		if (Objects.isNull(qry)) {
			return null;
		}
		if (this.isOracleDataSource) {
			return String.format(qry, LISTAGG_DWSBLK_CONIDR, EMPTY, LIKE_BULK);
		} else {
			return String.format(qry, STRINGAGG_DWSBLK_CONIDR, CAST_INTEGER, LIKE_BULK);
		}
	}
}
