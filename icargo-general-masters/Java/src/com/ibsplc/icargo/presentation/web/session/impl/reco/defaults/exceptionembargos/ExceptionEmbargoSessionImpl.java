package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.exceptionembargos;


import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.exceptionembargos.ExceptionEmbargoSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-6843
 *
 */
public class ExceptionEmbargoSessionImpl extends AbstractScreenSession implements ExceptionEmbargoSession{

	
	
	/** The Constant KEY_TOTALRECORDS. */
	private static final String KEY_TOTALRECORDS = "totalrecords";

	/** The Constant KEY_FLIGHTNOTESLISTVO. */
	private static final String  KEY_EXCEPTION_EMBARGOS = "exceptionEmbargos";

	private static final String SCREENID = "reco.defaults.exceptionembargo";
	private static final String MODULE = "reco.defaults";
	
	
	public String getModuleName() {
		return MODULE;
	}

	public String getScreenID() {
		return SCREENID;
	}
	public Page<ExceptionEmbargoDetailsVO> getExceptionEmbargoDetails(){
		return (Page<ExceptionEmbargoDetailsVO>)getAttribute(KEY_EXCEPTION_EMBARGOS);
	};
	public void setExceptionEmbargoDetails(Page<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVO)	{
		setAttribute(KEY_EXCEPTION_EMBARGOS, (Page<ExceptionEmbargoDetailsVO>) exceptionEmbargoDetailsVO);
	};
	
	public int getTotalRecords() {
		return getAttribute(KEY_TOTALRECORDS);
	}
	
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
	
	public void removeTotalRecords() {
		removeAttribute(KEY_TOTALRECORDS);
	}

	public void removeExceptionEmbargos() {
		removeAttribute(KEY_EXCEPTION_EMBARGOS);
		
	}



}
