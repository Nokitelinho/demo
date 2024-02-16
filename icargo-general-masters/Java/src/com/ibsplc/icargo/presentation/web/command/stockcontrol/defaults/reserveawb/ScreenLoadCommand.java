/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private static final String SYSTEMPARAMETERVALUES = "stock.defaults.reserveawb.defaultdaysforexpiry";

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ReserveAWBSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.reservestock");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		String expiryDate = getExpDate();
		form.setAirline("");
		form.setAwbType("");
		form.setCustCode("");
		form.setRemarks("");
		form.setExpiryDate("");
		form.setTotAwb("");
		form.setGeneral("");
		form.setSpecific("");
		log.log(Log.FINE, "\n\nexpiryDate-------------------->", expiryDate);
		LocalDate expDate = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, false).addDays(Integer.parseInt(expiryDate));
		form.setExpiryDate(expDate.toDisplayDateOnlyFormat());

		DocumentTypeDelegate documentTypeDelegate = new DocumentTypeDelegate();
		Collection<DocumentVO> documentCol = new ArrayList<DocumentVO>();
		DocumentFilterVO filterVO = new DocumentFilterVO();
		filterVO.setCompanyCode(companyCode);
		try {
			documentCol = documentTypeDelegate.findDocumentDetails(filterVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		Collection<String> awbTypes = (Collection<String>) getMapForDocument(documentCol);
		session.setDocumentVO(documentCol);
		session.setAWBTypes(awbTypes);
		ReserveAWBVO reserveAWBVO = new ReserveAWBVO();
		session.setReserveAWBVO(reserveAWBVO);
		invocationContext.target = "screenload_success";

	}

	private String getExpDate() {

		HashMap<String, String> expDateVal = new HashMap<String, String>();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> sysPar = new ArrayList<String>();
		sysPar.add(SYSTEMPARAMETERVALUES);
		try {
			expDateVal = (HashMap<String, String>) sharedDefaultsDelegate
					.findSystemParameterByCodes(sysPar);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		return expDateVal.get(SYSTEMPARAMETERVALUES);

	}

	private Collection<String> getMapForDocument(
			Collection<DocumentVO> documentCol) {

		Map<String, Collection<String>> documentMap = new HashMap<String, Collection<String>>();
		String docType = "";
		Collection<String> col = new ArrayList<String>();
		Collection<String> arr = null;
		for (DocumentVO vo : documentCol) {
			docType = vo.getDocumentType();
			if (documentMap.get(docType) == null) {
				arr = new ArrayList<String>();
				for (DocumentVO documentVO : documentCol) {
					if (docType.equals(documentVO.getDocumentType())) {
						arr.add(documentVO.getDocumentSubTypeDes());
					}
				}
				documentMap.put(docType, arr);
			}
		}
		return documentMap.get("AWB");
	}

}
