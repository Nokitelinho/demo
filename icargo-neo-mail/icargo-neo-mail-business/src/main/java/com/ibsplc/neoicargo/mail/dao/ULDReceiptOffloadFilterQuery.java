package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO;

/** 
 * Created by A-9529 on 19-08-2020.
 */
public class ULDReceiptOffloadFilterQuery extends NativeQuery {
	private String baseQuery;
	private OffloadFilterVO offloadFilterVO;
//TODO: Neo to verify
	public ULDReceiptOffloadFilterQuery(String baseQuery, OffloadFilterVO offloadFilterVO) {
		super(PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.offloadFilterVO = offloadFilterVO;
	}
	@Override
	public String getNativeQuery() {
		StringBuilder stringBuilder =new StringBuilder().append(baseQuery);
		return stringBuilder.toString();
	}
}
