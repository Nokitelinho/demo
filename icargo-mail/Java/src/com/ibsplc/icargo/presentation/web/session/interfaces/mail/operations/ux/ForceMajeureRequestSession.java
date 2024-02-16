package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface ForceMajeureRequestSession extends ScreenSession{
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    
      public void setSource(ArrayList<OneTimeVO> sourceOneTime);
      public ArrayList<OneTimeVO> getSource(); 
      public void setListforcemajeurevos(Page<ForceMajeureRequestVO> listforcemajeurevos);
      public Page<ForceMajeureRequestVO> getListforcemajeurevos();
      public void setReqforcemajeurevos(Page<ForceMajeureRequestVO> reqforcemajeurevos);
      public Page<ForceMajeureRequestVO> getReqforcemajeurevos();
      public void setFilterParamValues(ForceMajeureRequestFilterVO filterparamvalues);
      public ForceMajeureRequestFilterVO getFilterParamValues();
      public void setTotalRecords(int totalRecords) ; 
      public void setListForceMajeureLovVos(Page<ForceMajeureRequestVO> listforcemajeurevos);
      public Page<ForceMajeureRequestVO> getListForceMajeureLovVos();
      public void setScanType(ArrayList<OneTimeVO> scanTypeOneTime);
      public ArrayList<OneTimeVO> getScanType();
      public void setFileUploadErrorLogVOs(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs);
      public Collection<FileUploadErrorLogVO> getFileUploadErrorLogVOs();
    
}
