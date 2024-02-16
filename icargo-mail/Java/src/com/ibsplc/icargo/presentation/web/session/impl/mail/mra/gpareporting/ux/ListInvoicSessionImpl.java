package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting.ux;

import java.util.ArrayList;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class ListInvoicSessionImpl extends AbstractScreenSession implements ListInvoicSession {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String STATUS_LEVEL="statusLevel";
	private static final String LISTINVOICVOs="listinvoicvos";
	private static final String TOTAL_RECORDS="totalrecords";	
	private static final String FILTER_PARAM="filterparam";
	private static final String SELECTED_FIELDS="selectedFields";
	private static final String INVOIC_STATUS="invoicStatus";
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
		
     /**
   	 * @return ArrayList<OneTimeVO>
   	 */ 
	public void setStatus(ArrayList<OneTimeVO> sourceOneTime) {
		setAttribute(STATUS_LEVEL, sourceOneTime);
	}
	public ArrayList<OneTimeVO> getStatus() {
		return (ArrayList<OneTimeVO>)getAttribute(STATUS_LEVEL);
	}
	
    public void setFilterParamValues(InvoicFilterVO filterparamvalues){
    	setAttribute(FILTER_PARAM, filterparamvalues);
    }
    public InvoicFilterVO getFilterParamValues(){
    	return getAttribute(FILTER_PARAM);
    }
	public void setListinvoicvos(Page<InvoicVO> listinvoicvos) {
		setAttribute(LISTINVOICVOs,(Page<InvoicVO>)listinvoicvos);
	}
	public Page<InvoicVO> getListinvoicvos() {
		return (Page<InvoicVO>)getAttribute(LISTINVOICVOs);
	}
	public void setTotalRecords(int totalRecords){
		setAttribute(TOTAL_RECORDS,totalRecords);
	}
	public int getTotalRecords(){
		return getAttribute(TOTAL_RECORDS);
	}
	public void setSelectedRecords(String seletectedValue) {
		setAttribute(SELECTED_FIELDS,seletectedValue);
	}
	public String getSelectedRecords() {
		return  getAttribute(SELECTED_FIELDS);
	}
	public void setRejectInvoic(String invoicStatus) {
		setAttribute(INVOIC_STATUS,invoicStatus);
	}
	public String getRejectInvoic() {
		return  getAttribute(INVOIC_STATUS);
	}
}
