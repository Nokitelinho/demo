package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailOnHandDetailsVO;
import com.ibsplc.neoicargo.mail.vo.SearchContainerFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class MailOnHandListFilterQuery extends PageableNativeQuery<MailOnHandDetailsVO> {
	private SearchContainerFilterVO searchContainerFilterVO;
	private String baseQuery;
	private static final String ASSSIGNED_ALL = "ALL";

	public MailOnHandListFilterQuery(String baseQuery, SearchContainerFilterVO searchContainerFilterVO,
			MultiMapper<MailOnHandDetailsVO> multimapper) {
		super(searchContainerFilterVO.getTotalRecords(), multimapper, PersistenceController.getEntityManager().currentSession());
		this.searchContainerFilterVO = searchContainerFilterVO;
		this.baseQuery = baseQuery;
	}
}
