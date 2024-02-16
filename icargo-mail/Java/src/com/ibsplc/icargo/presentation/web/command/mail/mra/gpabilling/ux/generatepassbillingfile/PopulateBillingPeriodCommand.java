
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.generatepassbillingfile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPAPassFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GeneratePASSBillingFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class PopulateBillingPeriodCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA MAIL");

	
	private static final String BILLING_TYPE_GPA="G";
	private static final String FILTER_POPULATE_SOURCE_FIELD_PERIODNUMBER="PRDNUM";
	private static final String FILTER_POPULATE_SOURCE_FIELD_BILLINGPERIOD="BLGPRD";
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, 
	CommandInvocationException {
		this.log.entering("PopulateBillingPeriodCommand", "execute");
		GeneratePASSBillingFileModel generatepassbillingmodel = (GeneratePASSBillingFileModel)actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();
		populatePeriodNumberAndDate(generatepassbillingmodel, logonAttributes);
		ResponseVO responseVO = new ResponseVO();
		ArrayList<GeneratePASSBillingFileModel> results = new ArrayList<>();
		results.add(generatepassbillingmodel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
	    actionContext.setResponseVO(responseVO);
		log.exiting("PopulateBillingPeriodCommand","execute");
	}
	
	private void populatePeriodNumberAndDate(GeneratePASSBillingFileModel generatepassbillingmodel,
			LogonAttributes logonAttributes) {

		GPAPassFilter gPAPassFilter = generatepassbillingmodel.getPassFilter();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		Collection<BillingScheduleDetailsVO> billingScheduleDetailsVOs = null;
		BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
		billingScheduleFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		billingScheduleFilterVO.setBillingType(BILLING_TYPE_GPA);
		billingScheduleFilterVO.setSource(MRAConstantsVO.PASS_BILLINGPERIOD_VALIDATION);
		
		try {

			if (FILTER_POPULATE_SOURCE_FIELD_PERIODNUMBER.equals(generatepassbillingmodel.getPopulateSource())
					&& !isNullOrEmpty(gPAPassFilter.getPeriodNumber())) {
				billingScheduleFilterVO.setPeriodNumber(gPAPassFilter.getPeriodNumber());
				billingScheduleDetailsVOs = delegate.findBillingType(billingScheduleFilterVO, 1);

				if (billingScheduleDetailsVOs != null && !billingScheduleDetailsVOs.isEmpty()) {
					gPAPassFilter.setFromBillingDate(billingScheduleDetailsVOs.iterator().next()
							.getBillingPeriodFromDate().toDisplayDateOnlyFormat());
					gPAPassFilter.setToBillingDate(billingScheduleDetailsVOs.iterator().next().getBillingPeriodToDate()
							.toDisplayDateOnlyFormat());
					generatepassbillingmodel.setValidPeriodNumber(true);
					generatepassbillingmodel.setValidBillingPeriod(true); 
				} else {
					generatepassbillingmodel.setValidPeriodNumber(false);
				}
			} else if (FILTER_POPULATE_SOURCE_FIELD_BILLINGPERIOD.equals(generatepassbillingmodel.getPopulateSource())
					&& !isNullOrEmpty(gPAPassFilter.getFromBillingDate())
					&& !isNullOrEmpty(gPAPassFilter.getToBillingDate())) {

				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				fromDate.setDate(gPAPassFilter.getFromBillingDate());
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				toDate.setDate(gPAPassFilter.getToBillingDate());

				billingScheduleFilterVO.setBillingPeriodFromDate(fromDate);
				billingScheduleFilterVO.setBillingPeriodToDate(toDate);
				billingScheduleDetailsVOs = delegate.findBillingType(billingScheduleFilterVO, 1);

				if (billingScheduleDetailsVOs != null && !billingScheduleDetailsVOs.isEmpty()) {
					gPAPassFilter.setPeriodNumber(billingScheduleDetailsVOs.iterator().next().getPeriodNumber());
					generatepassbillingmodel.setValidBillingPeriod(true);
					generatepassbillingmodel.setValidPeriodNumber(true);
				} else {
					generatepassbillingmodel.setValidBillingPeriod(false);
				}

			}

		} catch (BusinessDelegateException businessDelegateException) {
			generatepassbillingmodel.setValidBillingPeriod(false);
			generatepassbillingmodel.setValidPeriodNumber(false);
		}

	}
	private boolean isNullOrEmpty(String string){
		return (string==null || string.trim().length()==0);
	}

}
