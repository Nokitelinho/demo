package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * 
 * @author A-7929
 *
 */
public interface ClaimHistoryPopupSession extends ScreenSession {
	
	
	public void setMailbagHistoryVOsCollection(ArrayList<MailbagHistoryVO> mailbaghistoryvos); 
	public ArrayList<MailbagHistoryVO> getMailbagHistoryVOsCollection();
	
	
	
	public Collection<OneTimeVO> getOneTimeStatus();
	public void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus);
	
	public void setInvoicDetails(Collection<InvoicDetailsVO> invoicDetails);
	public Collection<InvoicDetailsVO> getInvoicDetails();
	
	
	public void setClaimDetails(Collection<InvoicDetailsVO> claimDetails);
	public Collection<InvoicDetailsVO> getClaimDetails();
	
	public void setUspsHistoryVOs(ArrayList<ResditReceiptVO> uspsHistoryVOs);
	public ArrayList<ResditReceiptVO> getUspsHistoryVOs();
	
	public void setForceMajeureDetails(ArrayList<ForceMajeureRequestVO> forceMajeureRequestVOs); 
	public ArrayList<ForceMajeureRequestVO> getForceMajeureDetails();

}
