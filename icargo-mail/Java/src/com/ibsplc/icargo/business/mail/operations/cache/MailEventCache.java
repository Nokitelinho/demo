package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.xibase.server.framework.cache.Cache;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.Group;
import com.ibsplc.xibase.server.framework.cache.Invalidator;
import com.ibsplc.xibase.server.framework.cache.Strategy;

public interface MailEventCache extends Cache, Invalidator {

	public static final String MAIL_EVENT_CACHE_GROUP = "mail.operations.maileventcache.group";

	public static final String ENTITY_NAME = "mail.operations.mailevent";

	@Group("mail.operations.maileventcache.group")
	@Strategy("com.ibsplc.icargo.business.mail.operations.cache.MailEventRetrievalStrategy")
	Collection<MailEventVO> findMailEvent(MailEventVO mailEventVO) throws CacheException;
}
