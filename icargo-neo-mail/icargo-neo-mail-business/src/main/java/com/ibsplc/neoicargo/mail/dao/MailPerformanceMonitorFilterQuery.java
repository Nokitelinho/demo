package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailMonitorFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

public class MailPerformanceMonitorFilterQuery extends PageableNativeQuery<MailbagVO> {
	private StringBuilder baseQuery;
	private MailMonitorFilterVO filterVO;
	private int pageSize;
	private String type;

	public MailPerformanceMonitorFilterQuery(int pageSize, MailbagMapper mapper, MailMonitorFilterVO filterVO,
			String type, StringBuilder baseQuery) {
		super(filterVO.getPageSize(), -1, mapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
		this.pageSize = pageSize;
		this.type = type;
	}
}
