package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.exceptionembargos;


import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * The Interface ExceptionEmbargoSession.
 * @author A-6843
 *
 */
public interface ExceptionEmbargoSession extends ScreenSession{


	
	/**
	 *	Methods for getting ExceptionEmbargoDetails
	 */
	public Page<ExceptionEmbargoDetailsVO> getExceptionEmbargoDetails();
	/**
	 * Methods for setting ExceptionEmbargoDetails
	 */
	public void setExceptionEmbargoDetails(Page<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVO);

	/**
	 * Methods for removing level
	 */
	public void removeExceptionEmbargos();

	/**
	 * Methods for getting TotalRecords
	 */
	public int getTotalRecords();
	/**
	 *Methods for removing Total Records 
	 */
	public void setTotalRecords(int totalRecords);
	/**
	 *  
	 */
	public void removeTotalRecords();

}