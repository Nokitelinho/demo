package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailEvent;
import com.ibsplc.icargo.business.mail.operations.MailEventPK;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailEventRetrievalStrategy implements RetrievalStrategy {
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private Log logWriter = LogFactory.getLogger(MAIL_OPERATIONS);

	@Override
	public Collection<MailEventVO> retrieve(Object... args) throws CacheException {
		logWriter.entering(this.getClass().getSimpleName(), "retrieve");
		MailEventVO mailEventVO = (MailEventVO) args[0];
		MailEventPK mailEventPK = new MailEventPK();
		mailEventPK.setCompanyCode(mailEventVO.getCompanyCode());
		mailEventPK.setMailboxId(mailEventVO.getMailboxId());
		try {
			return MailEvent.findMailEvent(mailEventPK);
		} catch (SystemException e) {
			throw new CacheException(CacheException.CACHE_UNAVAILABLE, e);
		}
	}
}
