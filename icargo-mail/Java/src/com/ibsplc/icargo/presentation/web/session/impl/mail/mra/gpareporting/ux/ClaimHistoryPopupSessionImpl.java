package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting.ux;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimHistoryPopupSession;
/**
 * 
 * @author A-7929
 *
 */
public class ClaimHistoryPopupSessionImpl extends AbstractScreenSession implements ClaimHistoryPopupSession{

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.claimhistory";
	private static final String MAILBAGHISTORY_COLLECTION="mailbagcollection";
	private static final String USPSHISTORY_COLLECTION="uspscollection";
	private static final String ONETIME_STATUS = "oneTimeStatus";
	private static final String INVOICDETAILS_COLLECTION="invoicdetails";
	private static final String CLAIMDETAILS_COLLECTION="claimdetails";
	private static final String FORCEMAJEURE_COLLECTION="forceMajeureDetails";
	
    public String getScreenID() {
		
		return SCREEN_ID;
	}

	public String getModuleName() {
		
		return MODULE_NAME;
	}

	
	public void setMailbagHistoryVOsCollection(ArrayList<MailbagHistoryVO> mailbaghistoryvos) {
		setAttribute(MAILBAGHISTORY_COLLECTION,(ArrayList<MailbagHistoryVO>)mailbaghistoryvos);
		
	}

	public ArrayList<MailbagHistoryVO> getMailbagHistoryVOsCollection() {
		return (ArrayList<MailbagHistoryVO>)getAttribute(MAILBAGHISTORY_COLLECTION);
		
	}
	
	public void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus) {
		setAttribute(ONETIME_STATUS,(ArrayList<OneTimeVO>)oneTimeStatus);
	}
	
	public Collection<OneTimeVO> getOneTimeStatus() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_STATUS);
	}

	@Override
	public void setInvoicDetails(Collection<InvoicDetailsVO> invoicDetails) {
		setAttribute(INVOICDETAILS_COLLECTION,(ArrayList<InvoicDetailsVO>)invoicDetails);
		
	}

	@Override
	public Collection<InvoicDetailsVO> getInvoicDetails() {
		// TODO Auto-generated method stub
		return (Collection<InvoicDetailsVO>)getAttribute(INVOICDETAILS_COLLECTION);
	}

	@Override
	public void setClaimDetails(Collection<InvoicDetailsVO> claimDetails) {
		setAttribute(CLAIMDETAILS_COLLECTION,(ArrayList<InvoicDetailsVO>)claimDetails);
		
	}

	@Override
	public Collection<InvoicDetailsVO> getClaimDetails() {
		// TODO Auto-generated method stub
		return (Collection<InvoicDetailsVO>)getAttribute(CLAIMDETAILS_COLLECTION);
	}

	public void setUspsHistoryVOs(ArrayList<ResditReceiptVO> uspsHistoryVOs) {
		setAttribute(USPSHISTORY_COLLECTION,(ArrayList<ResditReceiptVO>)uspsHistoryVOs);
		
	}

	public ArrayList<ResditReceiptVO> getUspsHistoryVOs() {
		return (ArrayList<ResditReceiptVO>)getAttribute(USPSHISTORY_COLLECTION);
		
	}
	
	public void setForceMajeureDetails(ArrayList<ForceMajeureRequestVO> forceMajeureRequestVOs) {
		setAttribute(FORCEMAJEURE_COLLECTION,(ArrayList<ForceMajeureRequestVO>)forceMajeureRequestVOs);
		
	}

	public ArrayList<ForceMajeureRequestVO> getForceMajeureDetails() {
		return (ArrayList<ForceMajeureRequestVO>)getAttribute(FORCEMAJEURE_COLLECTION);
		
	}

}


