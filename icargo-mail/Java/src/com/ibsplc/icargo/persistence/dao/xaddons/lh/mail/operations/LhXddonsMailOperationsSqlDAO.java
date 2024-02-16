package com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.AbstractQueryDAO;
import com.ibsplc.xibase.server.framework.persistence.query.Query;

/**
 * @author A-10543
 *
 */
public class LhXddonsMailOperationsSqlDAO extends AbstractQueryDAO implements LhXaddonsMailOperationsDAO {
	private static final String FIND_MAILBAGDETAILSLIST = "xaddons.lh.mail.operation.despatch.mailbaglistingquery";
	private static final String FIND_DESPATCHLIST = "xaddons.lh.mail.operation.despatch.basequery";
	private static final String FIND_PALOV = "xaddons.lh.mail.operations.findpalov";
	private static final String FIND_MAILBAGDETAILSLIST_ORACLE = "xaddons.lh.mail.operation.despatch.mailbaglistingqueryfororacle";

	@Override
	public Collection<DespatchDetailsVO> findDespatchDetails(DespatchDetailsFilterVO despatchDetailsFilter)
			throws SystemException {
		List<DespatchDetailsVO> despatchDetailsList = null;
		Collection<DespatchDetailsVO> despatchDetailsVOs = null;
		String gpoCode = despatchDetailsFilter.getGpoCode();
		Query qry = getQueryManager().createNamedNativeQuery(FIND_PALOV);
		int indx = 0;
		qry.setParameter(++indx, despatchDetailsFilter.getCompanyCode());
		qry.append(" AND POACOD =?");
		qry.setParameter(++indx, gpoCode);
		String gpoName = qry.getSingleResult(getStringMapper("POANAM"));

		int index = 0;
		Query query = getQueryManager().createNamedNativeQuery(FIND_DESPATCHLIST);
		query.setParameter(++index, despatchDetailsFilter.getCompanyCode());
		query.setParameter(++index, gpoCode);
		if (despatchDetailsFilter.getDespatchNumber() != null) {
			query.append(" AND MST.DSN =? ");
			query.setParameter(++index, despatchDetailsFilter.getDespatchNumber());
		}
		if (despatchDetailsFilter.getDestinationIMPC() != null) {
			String destinationIMPC = despatchDetailsFilter.getDestinationIMPC().trim().replace("*", "");
			if (destinationIMPC.length() < 6) {
				query.append(" AND MST.DSTEXGOFC LIKE ?");
			} else {
				query.append(" AND MST.DSTEXGOFC =? ");
			}
			query.setParameter(++index, despatchDetailsFilter.getDestinationIMPC().replace("*", "%"));
		}
		if (despatchDetailsFilter.getOriginIMPC() != null) {
			String originIMPC = despatchDetailsFilter.getOriginIMPC().trim().replace("*", "");
			if (originIMPC.length() < 6) {
				query.append(" AND MST.ORGEXGOFC LIKE ?");
			} else {
				query.append("AND MST.ORGEXGOFC =? ");
			}
			query.setParameter(++index, despatchDetailsFilter.getOriginIMPC().replace("*", "%"));
		}
		if (despatchDetailsFilter.getMailCategory() != null) {
			query.append("AND MST.MALCTG = ? ");
			query.setParameter(++index, despatchDetailsFilter.getMailCategory());
		}
		if (despatchDetailsFilter.getReceptacleID() != null) {
			query.append(" AND MST.MALIDR = ? ");
			query.setParameter(++index, despatchDetailsFilter.getReceptacleID());
		}
		if (despatchDetailsFilter.getMailClass() != null) {
			String mailClass = despatchDetailsFilter.getMailClass().trim().replace("*", "");
			if (mailClass.length() < 2) {
				query.append(" AND MST.MALSUBCLS LIKE ?");
			} else {
				query.append(" AND MST.MALSUBCLS  = ? ");
			}
			query.setParameter(++index, despatchDetailsFilter.getMailClass().replace("*", "%"));
		}
		if (despatchDetailsFilter.getFromDate() != null && despatchDetailsFilter.getToDate() != null) {
			query.append(" AND TRUNC(MST.DSPDAT) BETWEEN TO_DATE(?,'DD-MM-YY') AND TO_DATE(?,'DD-MM-YY') ");
			query.setParameter(++index, despatchDetailsFilter.getFromDate());
			query.setParameter(++index, despatchDetailsFilter.getToDate());
		}
		String mailbagqry = "";
		if (isOracleDataSource()) {
			mailbagqry = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGDETAILSLIST_ORACLE);
		} else {
			mailbagqry = getQueryManager().getNamedNativeQueryString(FIND_MAILBAGDETAILSLIST);
		}
		query.append(mailbagqry);

		despatchDetailsList = query.getResultList(new MailDespatchListMapper());
		if (despatchDetailsList != null) {
			for (DespatchDetailsVO despatchDetailVO : despatchDetailsList) {
				despatchDetailVO.setPaCode(gpoName);
			}
			despatchDetailsVOs = new ArrayList<>(despatchDetailsList);
		}

		return despatchDetailsVOs;
	}
}
