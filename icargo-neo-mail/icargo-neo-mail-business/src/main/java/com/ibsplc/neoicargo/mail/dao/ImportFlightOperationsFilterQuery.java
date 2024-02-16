package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import java.util.Collection;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;

public class ImportFlightOperationsFilterQuery extends NativeQuery {
	private String baseQuery;
	private String baseQueryOne;
	private ImportOperationsFilterVO importOperationsFilterVO;
	private Collection<ManifestFilterVO> manifestFilterVOs;
	private static final String SIGHTING_COMPLETE_ULD_STATUS = "RC";

	/**
	* @param baseQuery
	* @throws SystemException
	*/
	public ImportFlightOperationsFilterQuery(ImportOperationsFilterVO importOperationsFilterVO,
			Collection<ManifestFilterVO> manifestFilterVOs, String baseQuery, String baseQueryOne) {
		super(PersistenceController.getEntityManager().currentSession());
		this.importOperationsFilterVO = importOperationsFilterVO;
		this.manifestFilterVOs = manifestFilterVOs;
		this.baseQuery = baseQuery;
		this.baseQueryOne = baseQueryOne;
	}
//TODO: Neo to correct
	@Override
	public String getNativeQuery() {
		return null;
	}
}
