/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-9619
 *
 */
public class DNATAMailTrackingDefaultsSqlDAO extends AbstractQueryDAO 
									implements DNATAMailTrackingDefaultsDAO {

	private static final Log LOG = LogFactory.getLogger("MAIL_OPERATIONS");
	private static final String MODULE = "DNATAMailTrackingDefaultsSqlDAO";
	private static final String FIND_MAILBAGHISTORIES = "mail.operations.findmailbaghistories";
	private static final String FIND_MAILBAGS_STORAGE_UNIT = "mail.operations.findmailsinstorageunit"; ////added by A-9529 for IASCB-44567
	
	/**
	 * @author A-2037 This method is used to find the History of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailbagHistoryVO> findMailbagHistories(
			String companyCode,String mailBagId, long mailSequenceNumber) throws SystemException,  /*modified by A-8149 for ICRD-248207*/
			PersistenceException {

		LOG.entering(MODULE, "findMailbagHistories");
		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(
				FIND_MAILBAGHISTORIES);
		query.setParameter(++index, companyCode);
		if(mailSequenceNumber!=0l){
			query.append(" AND MST.MALSEQNUM = ? ORDER BY HIS.UTCSCNDAT "); 
			query.setParameter(++index, mailSequenceNumber);
			
		}
		else if(mailBagId!=null && !mailBagId.isEmpty()){
		query.append(" AND MST.MALIDR = ? ORDER BY HIS.UTCSCNDAT ");
			query.setParameter(++index, mailBagId);
		}
		
		return query.getResultList(new DNATAMailbagHistoryMapper());

	}
	
	//added by A-9529 for IASCB-44567
		/**
		 * @author a-9529 This method is used to find out the Mail Bags in the
		 *         StorageUnit
		 * @param containers
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 **/
		public Collection<MailbagVO> findMailbagsInStorageUnit(String storageUnit) throws SystemException {
			LOG.entering("MailTrackingDefaultsSQLDAO", "getMailBagsInStorageUnit");
			Collection<MailbagVO> mailbagVOS =null;
			int index = 0;
			Query query = getQueryManager().createNamedNativeQuery(FIND_MAILBAGS_STORAGE_UNIT);
			query.setParameter(++index, storageUnit);
			mailbagVOS = query.getResultList(new MailBagsInStorageUnitMultiMapper());
			LOG.exiting("MailTrackingDefaultsSQLDAO", "getMailBagsInStorageUnit");
			return mailbagVOS;
		}
}
