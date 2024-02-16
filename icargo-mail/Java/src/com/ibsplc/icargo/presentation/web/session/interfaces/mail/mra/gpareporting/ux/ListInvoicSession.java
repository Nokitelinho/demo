package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface ListInvoicSession extends ScreenSession{
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    
      public void setStatus(ArrayList<OneTimeVO> sourceOneTime);
      public ArrayList<OneTimeVO> getStatus(); 
      public void setListinvoicvos(Page<InvoicVO> listinvoicvos);
      public Page<InvoicVO> getListinvoicvos();
      public void setFilterParamValues(InvoicFilterVO filterparamvalues);
      public InvoicFilterVO getFilterParamValues();
      public void setTotalRecords(int totalRecords) ;
      public void setSelectedRecords(String seletectedValue);
      public String getSelectedRecords();
      public void setRejectInvoic(String invoicStatus);
      public String getRejectInvoic();
    
}
