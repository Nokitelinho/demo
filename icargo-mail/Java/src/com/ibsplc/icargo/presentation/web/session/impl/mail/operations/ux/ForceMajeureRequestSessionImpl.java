package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class ForceMajeureRequestSessionImpl extends AbstractScreenSession implements ForceMajeureRequestSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.forcemajeurerequest";
	private static final String SOURCE_LEVEL="sourceLevel";
	private static final String SCAN_TYPE="scanType";
	private static final String LISTFORCEMAJEUREVOs="listforcemajeurevo";
	private static final String REQFORCEMAJEUREVOs="reqforcemajeurevo";
	private static final String TOTAL_RECORDS="totalrecords";	
	private static final String FILTER_PARAM="filterparam";
	private static final String LISTFORCEMAJEUREIDS="listForceMajeureLovVos";
	private static final String FILE_UPLOAD_ERROR_LOG_VOS = "fileUploadErrorLogVOs";

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
	public void setSource(ArrayList<OneTimeVO> sourceOneTime) {
		setAttribute(SOURCE_LEVEL, sourceOneTime);
	}
	public ArrayList<OneTimeVO> getSource() {
		return (ArrayList<OneTimeVO>)getAttribute(SOURCE_LEVEL);
	}
	
    public void setFilterParamValues(ForceMajeureRequestFilterVO filterparamvalues){
    	setAttribute(FILTER_PARAM, filterparamvalues);
    }
    public ForceMajeureRequestFilterVO getFilterParamValues(){
    	return getAttribute(FILTER_PARAM);
    }
	public void setReqforcemajeurevos(Page<ForceMajeureRequestVO> reqforcemajeurevos) {
		setAttribute(REQFORCEMAJEUREVOs,(Page<ForceMajeureRequestVO>)reqforcemajeurevos);
	}
	public Page<ForceMajeureRequestVO> getReqforcemajeurevos() {
		return (Page<ForceMajeureRequestVO>)getAttribute(REQFORCEMAJEUREVOs);
    }
	public void setListforcemajeurevos(Page<ForceMajeureRequestVO> listforcemajeurevos) {
		setAttribute(LISTFORCEMAJEUREVOs,(Page<ForceMajeureRequestVO>)listforcemajeurevos);
	}
	public Page<ForceMajeureRequestVO> getListforcemajeurevos() {
		return (Page<ForceMajeureRequestVO>)getAttribute(LISTFORCEMAJEUREVOs);
	}
	public void setTotalRecords(int totalRecords){
		setAttribute(TOTAL_RECORDS,totalRecords);
	}
	public int getTotalRecords(){
		return getAttribute(TOTAL_RECORDS);
	}
	public void setListForceMajeureLovVos(Page<ForceMajeureRequestVO> listforcemajeurevos) { //Added by A-8164 for ICRD-316302
		setAttribute(LISTFORCEMAJEUREIDS,(Page<ForceMajeureRequestVO>)listforcemajeurevos);
	}
	public Page<ForceMajeureRequestVO> getListForceMajeureLovVos() {
		return (Page<ForceMajeureRequestVO>)getAttribute(LISTFORCEMAJEUREIDS);
	}
	public void setScanType(ArrayList<OneTimeVO> scanTypeOneTime) {
		setAttribute(SCAN_TYPE, scanTypeOneTime);
	}
	public ArrayList<OneTimeVO> getScanType() {
		return (ArrayList<OneTimeVO>)getAttribute(SCAN_TYPE);
	}
	@Override
	public void setFileUploadErrorLogVOs(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs) {
		setAttribute(FILE_UPLOAD_ERROR_LOG_VOS,(ArrayList<FileUploadErrorLogVO>)fileUploadErrorLogVOs);
	}

	@Override
	public Collection<FileUploadErrorLogVO> getFileUploadErrorLogVOs() {
		return getAttribute(FILE_UPLOAD_ERROR_LOG_VOS);
	}
}
