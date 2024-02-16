package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SecurityScreeningValidatioVoRetrievalStrategy implements RetrievalStrategy {
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private static final Log LOG = LogFactory.getLogger(MAIL_OPERATIONS);

	@Override
	public Collection<SecurityScreeningValidationVO> retrieve(Object... args) throws CacheException {
		LOG.entering(this.getClass().getSimpleName(), "retrieve");
		SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = (SecurityScreeningValidationFilterVO) args[0];
		try {
			return  new MailController().checkForSecurityScreeningValidations(securityScreeningValidationFilterVO);
		} catch (SystemException e) {
			throw new CacheException(CacheException.CACHE_UNAVAILABLE, e);
		}
	}
}
