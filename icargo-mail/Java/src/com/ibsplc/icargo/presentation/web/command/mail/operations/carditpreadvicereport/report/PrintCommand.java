/*
 * PrintCommand.java Created on Jul 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditpreadvicereport.report;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.CarditPreAdviseReportVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditPreAdviceReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "04MTK012";
	private static final String ACTION="generateCarditPreAdviceReport";
	private Log log = LogFactory.getLogger("List Consignment");
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ERROR_FWD = "normal-report-error-jsp";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
			String companyCode = logonAttributes.getCompanyCode().toUpperCase();
			CarditPreAdviceReportForm carditPreAdviceReportForm =
	    		(CarditPreAdviceReportForm)invocationContext.screenModel;
			Collection<ErrorVO> errors = validateForm (carditPreAdviceReportForm);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = ERROR_FWD;
				return;
			}

			CarditPreAdviseReportVO reportVo = new CarditPreAdviseReportVO();
			reportVo.setCompanyCode(companyCode);
			reportVo.setAirportCode(carditPreAdviceReportForm.getAirportCode().toUpperCase());
			LocalDate ld = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			ld.setDate(carditPreAdviceReportForm.getMailDate());
			reportVo.setMailDate(ld);
			reportVo.setStrMailDate(carditPreAdviceReportForm.getMailDate());
			reportVo.setFlightCarrierIdr(logonAttributes.getOwnAirlineIdentifier());

			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setPreview(true);
			getReportSpec().setProductCode(PRODUCTCODE);
			getReportSpec().setSubProductCode(SUBPRODUCTCODE);
			getReportSpec().setResourceBundle("carditpreadvicereport");
			getReportSpec().setAction(ACTION);
			getReportSpec().addFilterValue(reportVo);

			/*try {*/
				generateReport();
			/*} catch (Exception e) {
				e.getMessage();
			}
*/
			if(getErrors() != null && getErrors().size()>0){
				for(ErrorVO err : getErrors()){
					if("mailtracking.defaults.nopreadvisefoundforcardit".equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO("mailtracking.defaults.nopreadvisefoundforcardit");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}else{
						invocationContext.addError(err);
					}
				}
				invocationContext.target="normal-report-error-jsp";
				return;
			}

			invocationContext.target = getTargetPage();
		}

	private Collection<ErrorVO> validateForm(CarditPreAdviceReportForm carditPreAdviceReportForm){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errortemp=new ArrayList<ErrorVO>();
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<String> airportCodes = new ArrayList<String>();
		if(carditPreAdviceReportForm.getAirportCode() == null || carditPreAdviceReportForm.getAirportCode().trim().length() == 0){
			ErrorVO err = new ErrorVO("mailtracking.defaults.carditpreadvisereport.noairportspecified");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);

			errors.add(err);
		}else{
			//Added airport validation BY A-3830 for Bug :102468
			airportCodes.add(carditPreAdviceReportForm.getAirportCode().toUpperCase());
			if (airportCodes != null && airportCodes.size() > 0) {
				
				try {
				   areaDelegate.validateAirportCodes(getApplicationSession().getLogonVO().getCompanyCode(), airportCodes);
				}catch (BusinessDelegateException businessDelegateException) {
					errortemp = handleDelegateException(businessDelegateException);
					String invalidStationTemp="";
					if(errortemp != null && errortemp.size() > 0){
						for(ErrorVO errorCount:errortemp){
							invalidStationTemp=Arrays.deepToString(errorCount.getErrorData());
						}
					}
					Object[] errorData={invalidStationTemp.toString()};
					ErrorVO error=new ErrorVO("shared.area.invalidairport",errorData);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}

				log.log(Log.INFO, "After validating airport code");
			}
			
			
		}

		if(carditPreAdviceReportForm.getMailDate() == null || carditPreAdviceReportForm.getMailDate().trim().length() == 0){
			ErrorVO err = new ErrorVO("mailtracking.defaults.carditpreadvisereport.nomaildatespecified");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}

		return errors;
	}
}
