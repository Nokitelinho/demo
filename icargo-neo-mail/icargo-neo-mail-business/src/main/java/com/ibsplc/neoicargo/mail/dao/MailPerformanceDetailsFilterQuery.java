package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailMonitorFilterVO;

public class MailPerformanceDetailsFilterQuery extends NativeQuery {
	private StringBuilder baseQuery;
	private MailMonitorFilterVO filterVO;
	private int pageSize;
	private String type;
	private static final String FIND_SERVICE_FAILURE_DETAILS = "mail.operations.findServiceFailureDetails";
	private static final String FIND_FORCE_MAJEURE_DETAILS = "mail.operations.findForceMajeureMailbagCountDetails";

	/** 
	* @throws SystemException
	*/
	public MailPerformanceDetailsFilterQuery(int pageSize, MailbagMapper mapper, MailMonitorFilterVO filterVO,
			String type, StringBuilder baseQuery) {
		super(PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
		this.pageSize = pageSize;
		this.type = type;
	}
//TODO: Neo to correct
	@Override
	public String getNativeQuery() {
		return null;
	}
}
