package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailTransitFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailTransitVO;

public class MailTransitFilterQuery extends PageableNativeQuery<MailTransitVO> {
	String baseQuery;
	private MailTransitFilterVO mailTransitFilterVO;

	public MailTransitFilterQuery(MailTransitMapper mailTransitMapper, MailTransitFilterVO mailTransitFilterVO,
			String baseQuery) {
		super(mailTransitFilterVO.getPageSize(), mailTransitFilterVO.getTotalRecordsCount(), mailTransitMapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.mailTransitFilterVO = mailTransitFilterVO;
	}
}
