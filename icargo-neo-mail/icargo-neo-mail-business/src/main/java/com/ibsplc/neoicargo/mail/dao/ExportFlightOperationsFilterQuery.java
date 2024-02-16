package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

import java.util.Collection;

public class ExportFlightOperationsFilterQuery extends NativeQuery {
	private String baseQuery;
	private ImportOperationsFilterVO importOperationsFilterVO;
	private Collection<ManifestFilterVO> manifestFilterVOs;

	public ExportFlightOperationsFilterQuery(ImportOperationsFilterVO importOperationsFilterVO,
			Collection<ManifestFilterVO> manifestFilterVOs, String baseQry) {
		super(PersistenceController.getEntityManager().currentSession());
		this.importOperationsFilterVO = importOperationsFilterVO;
		this.manifestFilterVOs = manifestFilterVOs;
		this.baseQuery = baseQry;
	}
//TODO: Neo to correct
	@Override
	public String getNativeQuery() {
		return null;
	}
}
