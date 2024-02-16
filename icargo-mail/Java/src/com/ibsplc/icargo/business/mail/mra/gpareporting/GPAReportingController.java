/*
 * GPAReportingController.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.MRADefaultsController;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.PostalAdministrationAuditVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.MRAGPABillingDetails;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageDetailVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicSummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MsgBrokerMessageProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.webservices.rest.MailMRAWebservicesProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ForceMajeureRequestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invclaim.ClaimDetailMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.jobscheduler.SchedulerAgent;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.keygen.GenerationFailedException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.xibase.server.framework.persistence.lock.ClientType;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;

/**
 * @author A-1556
 *
 *
 */
@Module("mail")
@SubModule("mra")
public class GPAReportingController {

    private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");
	/*
	 * String ONE TIME CODES
	 */
    private static final String INVOIC_PROCESSING_LEVEL="mail.mra.gpareporting.invoicprocessinglevel";
	private static final String KEY_EXCEPTIONCODE = "mailtracking.mra.gpareporting.exceptioncodes";
    private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
    private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
    private static final String CAL_TYPE_INVOIC = "I";
    private static final String CLASS_NAME="GPAReportingController";
    private static final String OK="OK";
    private static final String MSG_TYPE ="INVCLAIMINT";
    private static final String MSG_TYP_DOM="INVOICCLAIMDOM";
    private static final String MSG_STD ="EDIFACT";
    private static final String RSDT_JOB="TriggerResditJob";
	private static final String PREP_DATE_FORMAT = "yyMMdd";
	private static final String PREP_TIME_FORMAT = "HHmm";
	private static final String KEY_RESDIT_INTERCHANGE ="RESDIT_INTERCHANGE";
	private static final String KEY_MESSAGE_REFERENCE="RESDIT_MESSAGEREF";
	private static final String COMPLETED = "C";
	private static final String PARTIALLY_COMPLETED="P";
	private static final String FAILED="F";
	private static final String CLAIM_GENERATION_TYPE="CG";
	private static final String DOM_PA="mailtracking.domesticmra.usps";
	private static final String TRIGGERPOINT_JOB="JOB";
	private static final String ASCASGCOD_INT="USPS04";
	private static final String ASCASGCOD_DOM="USPS01";
	private static final String USPS_INTERNATIONAL_AIRPORTSOWN = "mailtracking.mra.accounting.internationalairportsown";


    /**
     * This method is for saveGPAReportingDetails
     *
     * @param gpaReportingDetailsVOs
     * @throws SystemException
     */
    public void saveGPAReportingDetails(
            Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs)
            throws SystemException {
        log.entering("GPAReportingController", "saveGPAReportingDetails");

        for (GPAReportingDetailsVO gpaReportingDetailsVO : gpaReportingDetailsVOs) {
            if (GPAReportingDetailsVO.OPERATION_FLAG_INSERT
                    .equals(gpaReportingDetailsVO.getOperationFlag())) {
                insertGPAReportingDetails(gpaReportingDetailsVO);

            } else if (GPAReportingDetailsVO.OPERATION_FLAG_UPDATE
                    .equals(gpaReportingDetailsVO.getOperationFlag())) {
                updateGPAReportingDetails(gpaReportingDetailsVO);
            } else if (GPAReportingDetailsVO.OPERATION_FLAG_DELETE
                    .equals(gpaReportingDetailsVO.getOperationFlag())) {
                removeGPAReportingDetails(gpaReportingDetailsVO);
            }
        }
        log.exiting("GPAReportingController", "saveGPAReportingDetails");
    }

    /**
     * @param gpaReportingDetailsVO
     * @throws SystemException
     */
    private void removeGPAReportingDetails(
            GPAReportingDetailsVO gpaReportingDetailsVO) throws SystemException {
        log.entering("GPAReportingController", "removeGPAReportingDetails");
        log
				.log(Log.INFO, "removals ", gpaReportingDetailsVO.getOperationFlag());
		log.log(Log.INFO, "removals--- ", gpaReportingDetailsVO.getBillingIdentifier());
		try {
             GPAReportingDetails gpaReportingDetails = GPAReportingDetails.find(gpaReportingDetailsVO.getCompanyCode(),
                    gpaReportingDetailsVO.getPoaCode(), gpaReportingDetailsVO.getBillingBasis(),gpaReportingDetailsVO.getBillingIdentifier(),
                            gpaReportingDetailsVO.getSequenceNumber());
             GPAReportingBilling gPAReportingBilling=GPAReportingBilling.find(gpaReportingDetailsVO.getCompanyCode(), gpaReportingDetailsVO.getBillingIdentifier(),gpaReportingDetailsVO.getPoaCode());
            // calling audit while deleting
            StringBuilder auditRemarks = new StringBuilder();
            PostalAdministrationAuditVO postalAdministrationAuditVO = new PostalAdministrationAuditVO(
           		PostalAdministrationAuditVO.AUDIT_MODULENAME,
           		PostalAdministrationAuditVO.AUDIT_SUBMODULENAME,
           		PostalAdministrationAuditVO.AUDIT_ENTITY);
           postalAdministrationAuditVO.setActionCode(PostalAdministrationAuditVO.AUDIT_GPAREPORT_DELETED);
           auditRemarks.append("GPA Report deleted for GPA--->").append(
					String.valueOf(gpaReportingDetailsVO.getPoaCode())).append(
					" for reporting period ").append(
					String.valueOf(gpaReportingDetailsVO.getReportingFrom()))
					.append(" to ").append(
							String.valueOf(gpaReportingDetailsVO
									.getReportingTo()));
           postalAdministrationAuditVO.setAdditionalInformation(auditRemarks.toString());
           postalAdministrationAuditVO = (PostalAdministrationAuditVO) AuditUtils
           						.populateAuditDetails(postalAdministrationAuditVO,gpaReportingDetails, false);
          postalAdministrationAuditVO.setUserId(gpaReportingDetailsVO.getLastUpdateUser());
          postalAdministrationAuditVO = populatePoaAuditDetails(postalAdministrationAuditVO, gpaReportingDetails);
           log.log(Log.INFO, "AuditVo before calling performAudit ->",
				postalAdministrationAuditVO);
		// remove the entity
           gpaReportingDetails.remove();
           gPAReportingBilling.remove();
          // calling perform audit
           AuditUtils.performAudit(postalAdministrationAuditVO);
           log.log(Log.FINE, "poa audit performed");
          } catch (FinderException e) {
            throw new SystemException(e.getErrorCode(), e);
        } catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.getErrorCode();
		}
        log.exiting("GPAReportingController", "removeGPAReportingDetails");

    }

    /**
     * @param gpaReportingDetailsVO
     * @throws SystemException
     */
    private void updateGPAReportingDetails(
            GPAReportingDetailsVO gpaReportingDetailsVO) throws SystemException {
        log.entering("GPAReportingController", "updateGPAReportingDetails");
        log.log(Log.INFO, "update", gpaReportingDetailsVO.getOperationFlag());
		log.log(Log.INFO, "update ", gpaReportingDetailsVO.getBillingIdentifier());
		try {
            GPAReportingDetails gpaReportingDetails = GPAReportingDetails.find(gpaReportingDetailsVO.getCompanyCode(),
                    gpaReportingDetailsVO.getPoaCode(), gpaReportingDetailsVO.getBillingBasis(),gpaReportingDetailsVO.getBillingIdentifier(),
                    gpaReportingDetailsVO.getSequenceNumber());
           // CALLING AUDIT FOR UPDATE CASE
            StringBuilder auditRemarks = new StringBuilder();
            PostalAdministrationAuditVO postalAdministrationAuditVO = new PostalAdministrationAuditVO(
           		PostalAdministrationAuditVO.AUDIT_MODULENAME,
           		PostalAdministrationAuditVO.AUDIT_SUBMODULENAME,
           		PostalAdministrationAuditVO.AUDIT_ENTITY);
           postalAdministrationAuditVO.setActionCode(PostalAdministrationAuditVO.AUDIT_GPAREPORT_UPDATED);
           auditRemarks.append("GPA Report updated for GPA--->").append(
					String.valueOf(gpaReportingDetailsVO.getPoaCode())).append(
					" for reporting period ").append(
					String.valueOf(gpaReportingDetailsVO.getReportingFrom()))
					.append(" to ").append(
							String.valueOf(gpaReportingDetailsVO
									.getReportingTo()));
           postalAdministrationAuditVO.setAdditionalInformation(auditRemarks.toString());
           postalAdministrationAuditVO = (PostalAdministrationAuditVO) AuditUtils
           						.populateAuditDetails(postalAdministrationAuditVO,gpaReportingDetails, false);
          // postalAdministrationAuditVO.setCompanyCode(gpaReportingDetailsVO.getCompanyCode());
          postalAdministrationAuditVO.setUserId(gpaReportingDetailsVO.getLastUpdateUser());
          // postalAdministrationAuditVO.setPoaCode(gpaReportingDetailsVO.getPoaCode());
          // postalAdministrationAuditVO.setAuditEntityclassName(gPAReportingDetails.getClass());



           // CALLING UPDATE AFTER POLULATING AUDIT FILEDS
           gpaReportingDetails.update(gpaReportingDetailsVO);

           postalAdministrationAuditVO = (PostalAdministrationAuditVO) AuditUtils
				.populateAuditDetails(postalAdministrationAuditVO,gpaReportingDetails, false);
           postalAdministrationAuditVO = populatePoaAuditDetails(postalAdministrationAuditVO, gpaReportingDetails);
           log.log(Log.INFO, "AuditVo before calling performAudit ->",
				postalAdministrationAuditVO);
		AuditUtils.performAudit(postalAdministrationAuditVO);
           log.log(Log.FINE, "poa audit performed");


        } catch (FinderException e) {
        	log.log(log.FINE, "Finder--->>");
            throw new SystemException(e.getErrorCode(), e);
        }
        log.exiting("GPAReportingController", "updateGPAReportingDetails");

    }

    /**
     * @param gpaReportingDetailsVO
     * @throws SystemException
     */
    private void insertGPAReportingDetails(
            GPAReportingDetailsVO gpaReportingDetailsVO) throws SystemException {
        log.entering("GPAReportingController", "insertGPAReportingDetails--->");
        String bilIdr= GPAReportingBilling.generateBillId(gpaReportingDetailsVO.getCompanyCode());
        gpaReportingDetailsVO.setBillingIdentifier(bilIdr);
        new GPAReportingBilling(gpaReportingDetailsVO);
        GPAReportingDetails  gPAReportingDetails=new GPAReportingDetails(gpaReportingDetailsVO);
       /// calling audit
        // PostalAdministration postalAdministration = new PostalAdministration();
        StringBuilder auditRemarks = new StringBuilder();
        PostalAdministrationAuditVO postalAdministrationAuditVO = new PostalAdministrationAuditVO(
       		PostalAdministrationAuditVO.AUDIT_MODULENAME,
       		PostalAdministrationAuditVO.AUDIT_SUBMODULENAME,
       		PostalAdministrationAuditVO.AUDIT_ENTITY);
       postalAdministrationAuditVO.setActionCode(PostalAdministrationAuditVO.AUDIT_GPAREPORT_CAPTURED);
       auditRemarks.append("GPA Report Captured for GPA--->").append(
				String.valueOf(gpaReportingDetailsVO.getPoaCode())).append(
				" for reporting period ").append(
				String.valueOf(gpaReportingDetailsVO.getReportingFrom()))
				.append(" to ").append(
						String.valueOf(gpaReportingDetailsVO.getReportingTo()));
       postalAdministrationAuditVO.setAdditionalInformation(auditRemarks.toString());
       postalAdministrationAuditVO = (PostalAdministrationAuditVO) AuditUtils
       						.populateAuditDetails(postalAdministrationAuditVO,gPAReportingDetails, true);
      postalAdministrationAuditVO.setUserId(gpaReportingDetailsVO.getLastUpdateUser());
       postalAdministrationAuditVO = populatePoaAuditDetails(postalAdministrationAuditVO, gPAReportingDetails);
       log.log(Log.INFO, "AuditVo before calling performAudit ->",
			postalAdministrationAuditVO);
	AuditUtils.performAudit(postalAdministrationAuditVO);
       log.log(Log.FINE, "poa audit performed");

        log.exiting("GPAReportingController", "insertGPAReportingDetails");

    }


    /**
     * @param postalAdministrationAuditVO
     * @param gPAReportingDetails
     * @return PostalAdministrationAuditVO
     */
    private PostalAdministrationAuditVO populatePoaAuditDetails(PostalAdministrationAuditVO postalAdministrationAuditVO,
    		GPAReportingDetails gPAReportingDetails) {
        StringBuffer additionalInfo = new StringBuffer();
        log.log(Log.INFO, "Inside findDcmAuditDetails -> populating fields");
        //setting pk fields
        postalAdministrationAuditVO.setCompanyCode(gPAReportingDetails.getGpaReportingDetailsPK().getCompanyCode());
        postalAdministrationAuditVO.setPoaCode(gPAReportingDetails.getGpaReportingDetailsPK().getPoaCode());
        //postalAdministrationAuditVO.setUserId("System");
        if(postalAdministrationAuditVO.getAuditFields() != null &&
        		postalAdministrationAuditVO.getAuditFields().size() > 0) {
            for(AuditFieldVO auditField : postalAdministrationAuditVO.getAuditFields()) {
                if(auditField != null) {
                    log.log(Log.INFO, "Inside AuditField Not Null");
                    additionalInfo.append(" Field Name: ")
                            .append(auditField.getFieldName())
                            .append(" Field Description: ")
                            .append(auditField.getDescription())
                            .append(" Old Value: ")
                            .append(auditField.getOldValue())
                            .append(" New Value: ")
                            .append(auditField.getNewValue());
                }
            }
        }
       // postalAdministrationAuditVO.setAdditionalInformation(additionalInfo.toString());
        return postalAdministrationAuditVO;
    }

    /**
     *
     * @param gpaReportFilterVO
     * @return
     * @throws SystemException
     */
    public Page<GPAReportingDetailsVO> findGPAReportingDetails(
            GPAReportingFilterVO gpaReportFilterVO) throws SystemException {
        log.entering("GPAReportingController", "findGPAReportingDetails");
        Page<GPAReportingDetailsVO> gpaReportingDetailsVOs = GPAReportingDetails
                .findGPAReportingDetails(gpaReportFilterVO);
        log.exiting("GPAReportingController", "findGPAReportingDetails");
        return gpaReportingDetailsVOs;
    }

    /**
     * @author A-2280
     * @param gpaReportingFilterVO
     * @return
     * @throws SystemException
     */
    public Page<GPAReportingClaimDetailsVO> findClaimDetails(
            GPAReportingFilterVO gpaReportingFilterVO) throws SystemException {
        log.entering("GPAReportingController", "findClaimDetails");
        // Page<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs=null;
        Page<GPAReportingClaimDetailsVO> gpaReportingClaimDetailsVOs = GPAReportingClaimDetails
                .findClaimDetails(gpaReportingFilterVO);
        log.exiting("GPAReportingController", "findClaimDetails");
        return gpaReportingClaimDetailsVOs;

    }

    /**
     * @author A-2280
     * @param gpaReportingClaimDetailVOs
     * @throws SystemException
     */
    public void assignClaims(
            Collection<GPAReportingClaimDetailsVO> gpaReportingClaimDetailVOs)
            throws SystemException {
        log.entering("GPAReportingController", "assignClaims");
        if (gpaReportingClaimDetailVOs != null
                && gpaReportingClaimDetailVOs.size() > 0) {
            GPAReportingClaimDetails gpaReportingClaimDetails = null;
            for (GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO : gpaReportingClaimDetailVOs) {
                try {
                    gpaReportingClaimDetails = GPAReportingClaimDetails
                            .find(gpaReportingClaimDetailsVO.getCompanyCode(),
                                    gpaReportingClaimDetailsVO.getPoaCode(),
                                    gpaReportingClaimDetailsVO
                                            .getBillingBasis(),
                                    gpaReportingClaimDetailsVO
                                            .getReportingFromString(),
                                    gpaReportingClaimDetailsVO
                                            .getReportingToString(),
                                    gpaReportingClaimDetailsVO
                                            .getExceptionSequenceNumber());
                } catch (FinderException e) {

                    e.getErrorCode();
                    throw new SystemException(e.getMessage());
                }
                if (gpaReportingClaimDetails != null) {
                    gpaReportingClaimDetails
                            .setAssignedDate(gpaReportingClaimDetailsVO
                                    .getAssignedDate());
                    gpaReportingClaimDetails
                            .setAssignedUser(gpaReportingClaimDetailsVO
                                    .getAssignedUser());
                    gpaReportingClaimDetails.setLastUpdatedTime(
                    		gpaReportingClaimDetailsVO.getLastUpdatedime());
                }
            }
        }

        log.exiting("GPAReportingController", "assignClaims");

    }


    /**
     * @param filterVo
     * @throws SystemException
     * @throws MailTrackingMRABusinessException
     */
    public void processGpaReport(GPAReportingFilterVO filterVo)
            throws SystemException,MailTrackingMRABusinessException {
        log.entering("GPAReportingController", "processGpaReport");
        GPAReportingDetails.processGpaReport(filterVo);
        log.exiting("GPAReportingController", "processGpaReport");
    }

    /**
     * This method is to print Exceptions Report by Assignee Details
     *
     * @author A-2245
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String, Object> printExceptionsReportAssigneeDetails(
            ReportSpec reportSpec) throws SystemException, RemoteException,
            MailTrackingMRABusinessException {
        log.entering("GPAReportingController", "printExceptionsReportAssigneeDetails");
        GPAReportingFilterVO gpaReportingFilterVO = (GPAReportingFilterVO) reportSpec
                .getFilterValues().get(0);
        log.log(Log.FINE, "gpaReportingFilterVO ", gpaReportingFilterVO);
		Collection<GPAReportingClaimDetailsVO> gpaReportingDetailsVOs = null;
        gpaReportingDetailsVOs = GPAReportingClaimDetails
                .printExceptionsReportAssigneeDetails(gpaReportingFilterVO);

        log.log(Log.FINE, "gpaReportingDetailsVOs ", gpaReportingDetailsVOs);
		log.log(Log.FINE, "----------------");

        ReportMetaData parameterMetaData = new ReportMetaData();
   	 	parameterMetaData.setFieldNames(new String[] {"assignee", "poaCode", "poaName", "reportingPeriodFrom",
   	 												  "reportingPeriodTo", "exceptionCode", "country"});
   	 	reportSpec.addParameterMetaData(parameterMetaData);
   	 	reportSpec.addParameter(gpaReportingFilterVO);

        ReportMetaData reportMetaData = new ReportMetaData();
        reportMetaData.setColumnNames(new String[] { "ASDUSR", "GPACOD", "GPANAM",
                "CNTCOD", "RPTFRM", "RPTTOO", "EXPCOD", "DSN", "ASGDAT", "RSDDAT"});
        reportMetaData.setFieldNames(new String[] { "assignedUser", "poaCode",
                "poaName", "countryCode", "reportingPeriodFrom", "reportingPeriodTo",
                "exceptionCode", "dsnNumber", "assignedDate", "resolvedDate" });
        reportSpec.setReportMetaData(reportMetaData);
        if (gpaReportingDetailsVOs != null && gpaReportingDetailsVOs.size() > 0) {
			reportSpec.setData(gpaReportingDetailsVOs);
		} else {
            MailTrackingMRABusinessException mailTrackingMRABusinessException =
            							new MailTrackingMRABusinessException();
            mailTrackingMRABusinessException.addError(new ErrorVO(
                    MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA));
            throw mailTrackingMRABusinessException;
        }
        /*
         * Setting extra info
         */
        reportSpec.addExtraInfo(findOneTimeForGPAReport(gpaReportingFilterVO));
        log.exiting("GPAReportingController", "printExceptionsReportAssigneeDetails");
        return ReportAgent.generateReport(reportSpec);
    }

    /**
     * This method is to print Exceptions Report by Assignee Summary
     *
     * @author A-2245
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String, Object> printExceptionsReportAssigneeSummary(
            ReportSpec reportSpec) throws SystemException, RemoteException,
            MailTrackingMRABusinessException {
        log.entering("GPAReportingController", "printExceptionsReportAssigneeSummary");
        GPAReportingFilterVO gpaReportingFilterVO = (GPAReportingFilterVO) reportSpec
                .getFilterValues().get(0);
        log.log(Log.FINE, "gpaReportingFilterVO ", gpaReportingFilterVO);
		Collection<GPAReportingClaimDetailsVO> gpaReportingDetailsVOs = null;
        gpaReportingDetailsVOs = GPAReportingClaimDetails
                .printExceptionsReportAssigneeSummary(gpaReportingFilterVO);

        log.log(Log.FINE, "gpaReportingDetailsVOs ", gpaReportingDetailsVOs);
		log.log(Log.FINE, "----------------");

        ReportMetaData parameterMetaData = new ReportMetaData();
   	 	parameterMetaData.setFieldNames(new String[] {"assignee", "poaCode", "poaName", "reportingPeriodFrom",
   	 												"reportingPeriodTo", "exceptionCode", "country"});
   	 	reportSpec.addParameterMetaData(parameterMetaData);
   	 	reportSpec.addParameter(gpaReportingFilterVO);

        ReportMetaData reportMetaData = new ReportMetaData();
        reportMetaData.setColumnNames(new String[] { "ASDUSR", "GPACOD", "GPANAM",
                "CNTCOD", "RPTFRM", "RPTTOO", "EXPCOD", "TOTEXP", "TOTPDGEXP", "TOTRSDEXP"});
        reportMetaData.setFieldNames(new String[] { "assignedUser", "poaCode",
                "poaName", "countryCode", "reportingPeriodFrom", "reportingPeriodTo",
                "exceptionCode", "totalExceptions", "totalPendingExceptions", "totalResolvedExceptions" });
        reportSpec.setReportMetaData(reportMetaData);
        if (gpaReportingDetailsVOs != null && gpaReportingDetailsVOs.size() > 0) {
			reportSpec.setData(gpaReportingDetailsVOs);
		} else {
            MailTrackingMRABusinessException mailTrackingMRABusinessException =
            							new MailTrackingMRABusinessException();
            mailTrackingMRABusinessException.addError(new ErrorVO(
                    MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA));
            throw mailTrackingMRABusinessException;
        }
        /*
         * Setting extra info
         */
		reportSpec.addExtraInfo(findOneTimeForGPAReport(gpaReportingFilterVO));
        log.exiting("GPAReportingController", "printExceptionsReportAssigneeSummary");
        return ReportAgent.generateReport(reportSpec);
    }

    /**
     * This method is to print Exceptions Report Details
     *
     * @author A-2245
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String, Object> printExceptionsReportDetails(
            ReportSpec reportSpec) throws SystemException, RemoteException,
            MailTrackingMRABusinessException {
        log.entering("GPAReportingController", "printExceptionsReportDetails");
        GPAReportingFilterVO gpaReportingFilterVO = (GPAReportingFilterVO) reportSpec
                .getFilterValues().get(0);
        log.log(Log.FINE, "gpaReportingFilterVO ", gpaReportingFilterVO);
		Collection<GPAReportingClaimDetailsVO> gpaReportingDetailsVOs = null;
        gpaReportingDetailsVOs = GPAReportingClaimDetails
                .printExceptionsReportDetails(gpaReportingFilterVO);

        log.log(Log.FINE, "gpaReportingDetailsVOs ", gpaReportingDetailsVOs);
		log.log(Log.FINE, "----------------");

        ReportMetaData parameterMetaData = new ReportMetaData();
   	 	parameterMetaData.setFieldNames(new String[] {"poaCode", "poaName", "reportingPeriodFrom",
   	 												"reportingPeriodTo", "exceptionCode", "country", "assignee"});
   	 	reportSpec.addParameterMetaData(parameterMetaData);
   	 	reportSpec.addParameter(gpaReportingFilterVO);

        ReportMetaData reportMetaData = new ReportMetaData();
        reportMetaData.setColumnNames(new String[] {"GPACOD", "GPANAM",
                "CNTCOD", "RPTFRM", "RPTTOO", "EXPCOD", "DSN", "TOTEXP", "ASDUSR"});
        reportMetaData.setFieldNames(new String[] {"poaCode", "poaName",
        		"countryCode", "reportingPeriodFrom", "reportingPeriodTo",
        		"exceptionCode", "dsnNumber", "totalExceptions", "assignedUser" });
        reportSpec.setReportMetaData(reportMetaData);
        if (gpaReportingDetailsVOs != null && gpaReportingDetailsVOs.size() > 0) {
			reportSpec.setData(gpaReportingDetailsVOs);
		} else {
            MailTrackingMRABusinessException mailTrackingMRABusinessException =
            							new MailTrackingMRABusinessException();
            mailTrackingMRABusinessException.addError(new ErrorVO(
                    MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA));
            throw mailTrackingMRABusinessException;
        }
        /*
         * Setting extra info
         */
		reportSpec.addExtraInfo(findOneTimeForGPAReport(gpaReportingFilterVO));
        log.exiting("GPAReportingController", "printExceptionsReportDetails");
        return ReportAgent.generateReport(reportSpec);
    }

    /**
     * This method is to print Exceptions Report Summary
     *
     * @author A-2245
     * @param reportSpec
     * @return Map<String, Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     */
    public Map<String, Object> printExceptionsReportSummary(
            ReportSpec reportSpec) throws SystemException, RemoteException,
            MailTrackingMRABusinessException {
        log.entering("GPAReportingController", "printExceptionsReportSummary");
        GPAReportingFilterVO gpaReportingFilterVO = (GPAReportingFilterVO) reportSpec
                .getFilterValues().get(0);
        log.log(Log.FINE, "gpaReportingFilterVO ", gpaReportingFilterVO);
		Collection<GPAReportingClaimDetailsVO> gpaReportingDetailsVOs = null;
        gpaReportingDetailsVOs = GPAReportingClaimDetails
                .printExceptionsReportSummary(gpaReportingFilterVO);

        log.log(Log.FINE, "gpaReportingDetailsVOs ", gpaReportingDetailsVOs);
		log.log(Log.FINE, "----------------");

        ReportMetaData parameterMetaData = new ReportMetaData();
   	 	parameterMetaData.setFieldNames(new String[] {"poaCode", "poaName", "reportingPeriodFrom",
   	 												"reportingPeriodTo", "exceptionCode", "country"});
   	 	reportSpec.addParameterMetaData(parameterMetaData);
   	 	reportSpec.addParameter(gpaReportingFilterVO);

        ReportMetaData reportMetaData = new ReportMetaData();
        reportMetaData.setColumnNames(new String[] {"GPACOD", "GPANAM",
                "CNTCOD", "RPTFRM", "RPTTOO", "EXPCOD", "TOTEXP"});
        reportMetaData.setFieldNames(new String[] {"poaCode", "poaName",
        		"countryCode", "reportingPeriodFrom", "reportingPeriodTo",
        		"exceptionCode", "totalExceptions" });
        reportSpec.setReportMetaData(reportMetaData);
        if (gpaReportingDetailsVOs != null && gpaReportingDetailsVOs.size() > 0) {
			reportSpec.setData(gpaReportingDetailsVOs);
		} else {
            MailTrackingMRABusinessException mailTrackingMRABusinessException =
            							new MailTrackingMRABusinessException();
            mailTrackingMRABusinessException.addError(new ErrorVO(
                    MailTrackingMRABusinessException.MAILTACKING_MRA_EXCEPTION_NOREPORTDATA));
            throw mailTrackingMRABusinessException;
        }
        /*
         * Setting extra info
         */
		reportSpec.addExtraInfo(findOneTimeForGPAReport(gpaReportingFilterVO));
        log.exiting("GPAReportingController", "printExceptionsReportSummary");
        return ReportAgent.generateReport(reportSpec);
    }

    /**
     *
     * This is a private method for GPA Reports
     * Getting OneTimeValues for Exception Code
     *
     * @author A-2245
     * @param gpaReportingFilterVO
     * @return hashMap
     * @throws SystemException
     */
    private Map<String, Collection<OneTimeVO>> findOneTimeForGPAReport(
    								GPAReportingFilterVO gpaReportingFilterVO)
    								throws SystemException{
        /*
         * Getting OneTimeValues for Exception Code
         */
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(KEY_EXCEPTIONCODE);
		try{
			hashMap = new SharedDefaultsProxy().findOneTimeValues(
							gpaReportingFilterVO.getCompanyCode(), oneTimeList);
		}
		catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		return hashMap;
    }

    /**
     * @param gprMessageVO
     * @throws SystemException
     */
    /* Commented the method as part of ICRD-153078
    public void saveGPRMessageDetails(GPRMessageVO gprMessageVO)
            throws SystemException {
        log.entering("GPAReportingController", "saveGPRMessageDetails");
        Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs =
                null;
        try {
            gpaReportingDetailsVOs = constructGPAReportingDetailsVOs(gprMessageVO);
        } catch(ProxyException e) {
            // no Business Exception thrown from the called method
            throw new SystemException(e.getMessage(), e);
        }
        saveGPAReportingDetails(gpaReportingDetailsVOs);
        log.exiting("GPAReportingController", "saveGPRMessageDetails");
    }

    private Collection<GPAReportingDetailsVO> constructGPAReportingDetailsVOs(
            GPRMessageVO gprMessageVO) throws SystemException, ProxyException {
        log.entering("GPAReportingController",
                "constructGPAReportingDetailsVO");
        Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs =
                new ArrayList<GPAReportingDetailsVO>();
        Set<String> originOfficeOfExchanges = new HashSet<String>();
        Map<String, PostalAdministrationVO> paMap =
                new HashMap<String, PostalAdministrationVO>();
        LogonAttributes logonAttributes =
                ContextUtils.getSecurityContext().getLogonAttributesVO();
        String userId = logonAttributes.getUserId();
        for(GPAReportDetailsVO gpaReportDetailsVO : gprMessageVO
                .getGpaReportDetailsVOs()) {
            originOfficeOfExchanges.add(gpaReportDetailsVO.getOrigin());
        }
        for(String origin : originOfficeOfExchanges) {
            PostalAdministrationVO postalAdministrationVO =
                    new MailTrackingDefaultsProxy().findPoaDetails(
                            gprMessageVO.getCompanyCode(), origin);
            if(postalAdministrationVO != null) {
                paMap.put(origin, postalAdministrationVO);
            }
        }
        for(GPAReportDetailsVO gpaReportDetailsVO : gprMessageVO
                .getGpaReportDetailsVOs()) {
            if(paMap.get(gpaReportDetailsVO.getOrigin()) == null) {
                continue;
            }
            String mailSubClass = "";

            GPAReportingDetailsVO gpaReportingDetailsVO =
                    new GPAReportingDetailsVO();
            gpaReportingDetailsVOs.add(gpaReportingDetailsVO);
            gpaReportingDetailsVO.setCompanyCode(gprMessageVO.getCompanyCode());
            gpaReportingDetailsVO.setReportingFrom(gprMessageVO.getFromDate());
            gpaReportingDetailsVO.setReportingTo(gprMessageVO.getToDate());
            PostalAdministrationVO postalAdministrationVO =
                    paMap.get(gpaReportDetailsVO.getOrigin());
            gpaReportingDetailsVO.setBasistype(postalAdministrationVO.getBasisType());
            gpaReportingDetailsVO.setPoaCode(postalAdministrationVO.getPaCode());
            gpaReportingDetailsVO.setOperationFlag(
                            GPAReportingDetailsVO.OPERATION_FLAG_INSERT);
            gpaReportingDetailsVO.setDsnNumber(gpaReportDetailsVO.getDsnNumber());
            gpaReportingDetailsVO.setCountryCode(gpaReportDetailsVO.getCountry());
            gpaReportingDetailsVO.setWeight(gpaReportDetailsVO.getWeight());
            gpaReportingDetailsVO.setRate(gpaReportDetailsVO.getRate());
            //gpaReportingDetailsVO.setMailSubClass(gpaReportDetailsVO.getSubClass());
            gpaReportingDetailsVO.setActualMailSubClass(gpaReportDetailsVO.getSubClass());
            /**
             * for mailBag level Details Added by Sandeep
             *
            gpaReportingDetailsVO.setHighestNumberedReceptacle(gpaReportDetailsVO.getHsn());
            gpaReportingDetailsVO.setReceptacleSerialNumber(gpaReportDetailsVO.getRsn());
            gpaReportingDetailsVO.setRegisteredOrInsuredIndicator(gpaReportDetailsVO.getRegind());
            if (gpaReportDetailsVO.getSubClass().toUpperCase().startsWith(
            		GPAReportingDetailsVO.MAIL_SUBCLASS_LIKE_U)) {
				gpaReportingDetailsVO.setMailSubClass(GPAReportingDetailsVO.MAIL_SUBCLASS_LC);
				mailSubClass = GPAReportingDetailsVO.MAIL_SUBCLASS_LC;
			} else {
				gpaReportingDetailsVO.setMailSubClass(GPAReportingDetailsVO.MAIL_SUBCLASS_CP);
				mailSubClass = GPAReportingDetailsVO.MAIL_SUBCLASS_CP;
			}
            gpaReportingDetailsVO.setMailCategory(gpaReportDetailsVO.getCategory());
            gpaReportingDetailsVO.setDsnDate(gpaReportDetailsVO.getEtd());
            gpaReportingDetailsVO.setNoOfMailBags(gpaReportDetailsVO.getNumberOfBags());
            gpaReportingDetailsVO.setOriginOfficeExchange(gpaReportDetailsVO.getOrigin());
            gpaReportingDetailsVO.setDestinationOfficeExchange(new StringBuilder()
                            .append(gpaReportDetailsVO.getCountry())
                            .append(gpaReportDetailsVO.getOffice()).toString());
            String year = String.valueOf(
                    gpaReportingDetailsVO.getDsnDate().get(Calendar.YEAR))
                    .substring(3);
            gpaReportingDetailsVO.setYear(year);
            gpaReportingDetailsVO.setReportingStatus(
                    GPAReportingDetailsVO.MAIL_STATUS_REPORTED);
//            gpaReportingDetailsVO.setBillingBasis(new StringBuilder()
//                    .append(gpaReportingDetailsVO.getOriginOfficeExchange())
//                    .append(gpaReportingDetailsVO.getDestinationOfficeExchange())
//                    .append(gpaReportingDetailsVO.getMailCategory())
//                    .append(gpaReportingDetailsVO.getMailSubClass())
//                    .append(year)
//                    .append(gpaReportingDetailsVO.getDsnNumber()).toString());
            // setting billingBasis
            gpaReportingDetailsVO.setBillingBasis(new StringBuilder()
                    .append(gpaReportingDetailsVO.getOriginOfficeExchange())
                    .append(gpaReportingDetailsVO.getDestinationOfficeExchange())
                    .append(gpaReportingDetailsVO.getMailCategory())
                    .append(mailSubClass)
                    .append(year)
                    .append(gpaReportingDetailsVO.getDsnNumber()).toString());

            gpaReportingDetailsVO.setLastUpdateUser(userId);
            if(gpaReportDetailsVO.getFlightDetailsVOs() != null &&
                    !gpaReportDetailsVO.getFlightDetailsVOs().isEmpty()) {
                Collection<GPAReportingFlightDetailsVO> gpaReportingFlightDetailsVOs =
                        new ArrayList<GPAReportingFlightDetailsVO>();
                gpaReportingDetailsVO.setGpaReportingFlightDetailsVOs(
                        gpaReportingFlightDetailsVOs);
                for(FlightDetailsVO flightDetailsVO : gpaReportDetailsVO
                        .getFlightDetailsVOs()) {
                    GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO =
                            new GPAReportingFlightDetailsVO();
                    gpaReportingFlightDetailsVOs
                            .add(gpaReportingFlightDetailsVO);
                    gpaReportingFlightDetailsVO.setFlightCarrierCode(
                                    flightDetailsVO.getCarrierCode());
                    gpaReportingFlightDetailsVO
                            .setFlightNumber(flightDetailsVO.getFlightNumber());
                }
            }
        }
        log.log(Log.INFO, new StringBuilder("Constructed GPAReportingDetailsVOs ---> ").append(
                gpaReportingDetailsVOs).toString());
        log.exiting("GPAReportingController", "constructGPAReportingDetailsVO");
        return gpaReportingDetailsVOs;
    }*/

    /**
     * @param gpaReportMessageVO
     * @throws SystemException
     */
    public void uploadGPAReport(GPAReportMessageVO gpaReportMessageVO)
            throws SystemException {
        log.entering("GPAReportingController", "uploadGPAReport");
            MessageVO messageVO = constructMessageVO(gpaReportMessageVO);
            try {
                new MsgBrokerMessageProxy().receiveMessage(messageVO);
            } catch(ProxyException e) {
                throw new SystemException(e.getMessage(), e);
        }
        log.exiting("GPAReportingController", "uploadGPAReport");
    }

    private MessageVO constructMessageVO(
            GPAReportMessageVO gpaReportMessageVO) {
        log.entering("GPAReportingController", "constructMessageVO");
        MessageVO messageVO = new MessageVO();
        messageVO.setMessageStandard(GPAReportMessageVO.GPR_MSG_STD);
        messageVO.setMessageType(GPAReportMessageVO.GPR_MSG_TYPE);
        messageVO.setMessageVersion(GPAReportMessageVO.GPR_MSG_VERSION);
        messageVO.setOriginalMessage(gpaReportMessageVO.getMessage());
        messageVO.setRawMessage(gpaReportMessageVO.getMessage());
        messageVO.setStationCode(gpaReportMessageVO.getStationCode());
        messageVO.setReceiptOrSentDate(new LocalDate(gpaReportMessageVO
                .getStationCode(), Location.STN, true));
        messageVO.setCompanyCode(gpaReportMessageVO.getCompanyCode());
        ArrayList<MessageDespatchDetailsVO> despatchDetails =
                new ArrayList<MessageDespatchDetailsVO>();
        MessageDespatchDetailsVO despatchDetailsVO =
                new MessageDespatchDetailsVO();
        messageVO.setDespatchDetails(despatchDetails);
        despatchDetailsVO.setMode(GPAReportMessageVO.GPR_DESPATCH_MODE_FTP);
        //TODO remove this hard coding
        despatchDetailsVO.setAddress("av@avionairlines.com");
        despatchDetailsVO.setPartyType(GPAReportMessageVO.PARTY_TYPE_AIRLINE);
        despatchDetails.add(despatchDetailsVO);
        log.exiting("GPAReportingController", "constructMessageVO");
        return messageVO;
    }


    /**
     * @author A-8464
     * @param InvoicFilterVO
     * @return Page<InvoicDetailsVO>
     * @throws SystemException
     */
    public Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO) throws SystemException {
        log.entering("GPAReportingController", "listInvoicDetails");
        Page<InvoicDetailsVO> invoicDetailsVOs = MailGPAInvoicDetail.listInvoicDetails(invoicFilterVO);
        log.exiting("GPAReportingController", "listInvoicDetails");
        return invoicDetailsVOs;

    }

    /**
     * @author A-8464
     * @param invoicDetailsVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingMRABusinessException
     *
     */
     public void saveRemarkDetails(InvoicDetailsVO invoicDetailsVO)throws SystemException,RemoteException{
    	 log.entering("GPAReportingController","saveRemarkDetails");
    	 if(invoicDetailsVO.getMailbagInvoicProcessingStatus()!=null && invoicDetailsVO.getMailbagInvoicProcessingStatus().equals("AWTINC")){
    		 //MRAGPABillingDetails.saveRemarkDetails(invoicDetailsVO);
    		 MRAGPABillingDetails mRAGPABillingDetails = null;
    			try{
    				mRAGPABillingDetails = MRAGPABillingDetails.find(invoicDetailsVO.getCompanyCode(),invoicDetailsVO.getMailSequenceNumber(),invoicDetailsVO.getSerialNumber());
    				mRAGPABillingDetails.setIncrmks(invoicDetailsVO.getRemarks());
    			} catch (FinderException ex) {
    				throw new SystemException(ex.getMessage(), ex);
    			}
    	 }
    	 else
    	 {
    		 MailGPAInvoicDetail.saveRemarkDetails(invoicDetailsVO);
    	 }

    	 log.exiting("GPAReportingController","saveRemarkDetails");
  	 }

     /**
      * @author A-8464
      * @param Collection<invoicDetailsVO>
      * @throws SystemException
      * @throws RemoteException
      * @throws MailTrackingMRABusinessException
      *
      */
      @Advice(name = "mail.mra.saveClaimDetails" , phase=Phase.POST_INVOKE)
      public void saveClaimDetails(Collection<InvoicDetailsVO> invoicDetailsVOs)throws SystemException,RemoteException{
     	 log.entering("GPAReportingController","saveClaimDetails");
     	boolean changeInClaimAmount=MailGPAInvoicDetail.saveClaimDetails(invoicDetailsVOs);
     	if(changeInClaimAmount){
     	 updateMailStatus(invoicDetailsVOs,"U");
     	}
     	 log.exiting("GPAReportingController","saveClaimDetails");
   	 }

      /**
       * @author A-8464
       * @param Collection<invoicDetailsVO> invoicDetailsVOs, String processStatus
       * @throws SystemException
       * @throws RemoteException
       * @throws MailTrackingMRABusinessException
       *
       */
       public void updateProcessStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String processStatus)throws SystemException,RemoteException{
      	 log.entering("GPAReportingController","updateProcessStatus");
      	 MailGPAInvoicDetail.updateProcessStatus(invoicDetailsVOs, processStatus);
      	 log.exiting("GPAReportingController","updateProcessStatus");
    	 }

       /**
        * @author A-8464
        * @param Collection<invoicDetailsVO> invoicDetailsVOs, String groupRemarks
        * @throws SystemException
        * @throws RemoteException
        * @throws MailTrackingMRABusinessException
        *
        */
        public void saveGroupRemarkDetails(InvoicFilterVO invoicFilterVO,String groupRemarksToSave)throws SystemException,RemoteException{
       	 log.entering("GPAReportingController","saveGroupRemarkDetails");
       	 MailGPAInvoicDetail.saveGroupRemarkDetails(invoicFilterVO, groupRemarksToSave);
       	 log.exiting("GPAReportingController","saveGroupRemarkDetails");

        }
        /**
         * @author A-7371
         * @param mailInvoicMessage
         * @throws SystemException
         * @throws RemoteException
         * @throws ServiceNotAccessibleException
         */
        @Advice(name = "mail.mra.checkAutoProcessing" , phase=Phase.POST_INVOKE)
		public void saveMailInvoicDetails(Collection<MailInvoicMessageVO> mailInvoicMessage)
				throws SystemException,RemoteException, ServiceNotAccessibleException {

			  String paCode_int = null;
		        String paCode_dom = null;
		        String airports=null;
				paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
				paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
				airports=findSystemParameterValue(USPS_INTERNATIONAL_AIRPORTSOWN);
			for(MailInvoicMessageVO mailInvoicMessageVO: mailInvoicMessage){

				MessageVO messageVO=new MessageVO() ;
				LogonAttributes logonAttributes =
		                ContextUtils.getSecurityContext().getLogonAttributesVO();
				String message=mailInvoicMessageVO.getMessage();
				mailInvoicMessageVO.setCompanyCode(logonAttributes.getCompanyCode());
				messageVO.setCompanyCode(logonAttributes.getCompanyCode());
				messageVO.setRawMessage(message);
				messageVO.setOriginalMessage(message);
				if(ASCASGCOD_INT.equals(mailInvoicMessageVO.getAssocAssignCode())){
					messageVO.setMessageType("INVOICADV");
				}else if(ASCASGCOD_DOM.equals(mailInvoicMessageVO.getAssocAssignCode())){
					messageVO.setMessageType("INVOICDOM");
				}
				messageVO.setMessageStandard("EDIFACT");
				messageVO.setOperationFlag("I");
				messageVO.setMessageSource("iCx");
				messageVO.setStationCode(logonAttributes.getAirportCode());
				messageVO.setMessageIdentifier(mailInvoicMessageVO.getFileName().concat(String.valueOf(mailInvoicMessageVO.getSplitCount())));
				messageVO.setMessageIdentifierOne(mailInvoicMessageVO.getFileName().concat(String.valueOf(mailInvoicMessageVO.getSplitCount())));
				messageVO.setMessageDirection("I");
				messageVO.setMessageVersion("1");
				messageVO.setStatus("DS");
				try {
					new MsgBrokerMessageProxy().saveMessageDetails(messageVO);
				} catch (ProxyException | SystemException e1) {
					log.log(Log.FINE, "SystemException",e1);
				}

				if(ASCASGCOD_INT.equals(mailInvoicMessageVO.getAssocAssignCode())){
					mailInvoicMessageVO.setPoaCode(paCode_int);
				}else if(ASCASGCOD_DOM.equals(mailInvoicMessageVO.getAssocAssignCode())){
					mailInvoicMessageVO.setPoaCode(paCode_dom);
				}
				USPSPostalCalendarVO uspsPostalCalendarVO=	findInvoicPeriodDetails(mailInvoicMessageVO);
				if(uspsPostalCalendarVO!=null){
				mailInvoicMessageVO.setReportingPeriodFrom(uspsPostalCalendarVO.getPeriodFrom());
				mailInvoicMessageVO.setReportingPeriodTo(uspsPostalCalendarVO.getPeriodTo());
				}
				Collection<MailInvoicMessageDetailVO> mailInvoiceMessageDetailVOs=new ArrayList<>();
				if(paCode_dom.equals(mailInvoicMessageVO.getPoaCode())) {
				mailInvoicMessageVO.getMailInvoiceMessageDetailVOs().forEach(mailInvoicMessageDetailVO ->
                mailInvoicMessageDetailVO.setCompanyCode(logonAttributes.getCompanyCode())
         );
				}
				else {	
			for (MailInvoicMessageDetailVO mailInvoicMessageDetailVO : mailInvoicMessageVO
					.getMailInvoiceMessageDetailVOs()) {
				mailInvoicMessageDetailVO.setCompanyCode(logonAttributes.getCompanyCode());
				if ((airports != null && (airports.contains(mailInvoicMessageDetailVO.getOrginAirport())
						|| airports.contains(mailInvoicMessageDetailVO.getDestinationAirport())
						|| airports.equals("ALL"))) || airports == null) {
					mailInvoiceMessageDetailVOs.add(mailInvoicMessageDetailVO);
				}
			}
			mailInvoicMessageVO.setMailInvoiceMessageDetailVOs(mailInvoiceMessageDetailVOs);
				}
			if (!mailInvoicMessageVO.getMailInvoiceMessageDetailVOs().isEmpty()) {
				new MailInvoicMessageMaster(mailInvoicMessageVO);
			}
			}




	    }

		/**
        *
        * @author A-8464
        */
		// Commenting as part of ICRD-319850
/*       public Page<InvoicSummaryVO> findInvoicLov(InvoicFilterVO invoicFilterVO) throws SystemException {
           log.entering("GPAReportingController", "findInvoicLov");
           Page<InvoicSummaryVO> invoicSummaryVOs = MailGPAInvoicMaster
                   .findInvoicLovDetails(invoicFilterVO);
           log.exiting("GPAReportingController", "findInvoicLov");
           return invoicSummaryVOs;
       }*/
     /**
         * @author A-8527
         * @param InvoicFilterVO
         * @return Page<InvoicDetailsVO>
         * @throws SystemException
         */
        public Page<InvoicVO> listInvoic(InvoicFilterVO invoicFilterVO, int pageNumber) throws SystemException {
            log.entering("GPAReportingController", "listInvoic");
            Page<InvoicVO> invoicVOs = GPAInvoicMaster.listInvoic(invoicFilterVO,pageNumber);
            log.exiting("GPAReportingController", "listInvoic");
            return invoicVOs;

        }
        /**
         * @author A-8527
         * @param InvoicFilterVO
         * @return Page<InvoicDetailsVO>
         * @throws SystemException
         * @throws PersistenceException
         */
        public void updateInvoicReject(Collection <InvoicVO> rejectrecords) throws SystemException {
            log.entering("GPAReportingController", "updateInvoicReject");
            //Modified by A-8176 for the bug ICRD-348686
            try {
    
            	MailInvoicMessageMaster gpaInvoicMessageMaster = null;
        		String levelOfProcessing = findSystemParameterValue(INVOIC_PROCESSING_LEVEL);
        		String invoicStatus="";
        		if (rejectrecords != null && !rejectrecords.isEmpty()) {
        			MailInvoicMessageMasterPK  pk = null;
        			if(levelOfProcessing != null && "F".equals(levelOfProcessing)){
        				InvoicVO invoicVO = rejectrecords.iterator().next();
        				invoicStatus = invoicVO.getInvoicStatusCode();
        				List<Long> invoics = MailInvoicMessageMaster.constructDAO().findInvoicsByFileName(invoicVO.getCompanyCode(),invoicVO.getFileName());
        				if(invoics != null && !invoics.isEmpty()){
        					for(Long sernum : invoics){
        						pk = new MailInvoicMessageMasterPK();
        						pk.setCompanyCode(invoicVO.getCompanyCode());
        						pk.setSerialNumber(sernum);
        						
        							gpaInvoicMessageMaster = MailInvoicMessageMaster.find(pk);
        							gpaInvoicMessageMaster.setProcessStatus("RJ");
        							gpaInvoicMessageMaster.setRemark(invoicVO.getRemarks());
        							PersistenceController.getEntityManager().flush();
        				            PersistenceController.getEntityManager().clear();
        					
        					}
        				}
        				if("PR".equals(invoicStatus) || "PE".equals(invoicStatus)){
        				
        						invoicVO.setProcessingType("J");
        						invoicVO.setInvoicRefId(" ");
        		    		    invoicVO.setPayType(" ");
        		    		    String response = new MailGPAInvoicMaster().updateBatchNumForInvoic(invoicVO); 
								String dbJobCount = findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
    	   if(response != null && !(response.isEmpty()) && dbJobCount != null ) {
    	   String appJobReq = response.split("-")[0];
    	   String batchUpdStatus = response.split("-")[1];
    	  if("OK".equals(batchUpdStatus)&&"N".equals(appJobReq)){
	    	  String status = new MailGPAInvoicMaster().processInvoic(invoicVO);
	    	  log.log(Log.FINE, " OUT Parameter ok ", status); 
    	  } else if("OK".equals(batchUpdStatus)&&"Y".equals(appJobReq)){
        		    		    createAppJobForInvoicProcessing(invoicVO,dbJobCount); 
    	  }else
    	  {
    		  log.log(Log.FINE, " OUT Parameter not ok ", response); 
    	  }
        					}
    	
        					
        				}
        			}
        			else{
        				for (InvoicVO invoicVO : rejectrecords) {
        			
        					String[] seq = null;
        					if(invoicVO.getSerNums() != null){
        						seq = invoicVO.getSerNums().split(",");
        						for(String s : seq){
        							long serNum = Long.parseLong(s);
        							pk = new MailInvoicMessageMasterPK();
        							pk.setCompanyCode(invoicVO.getCompanyCode());
        							pk.setSerialNumber(serNum);
        							gpaInvoicMessageMaster = MailInvoicMessageMaster.find(pk);
        							gpaInvoicMessageMaster.setProcessStatus("RJ");
        							PersistenceController.getEntityManager().flush();
        				            PersistenceController.getEntityManager().clear();
        						}
        					}
        	
        				}
        			}
        		}
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
            catch (FinderException e) {
            	
            	log.log(Log.SEVERE,e);
			}
            if(rejectrecords != null && !rejectrecords.isEmpty() &&
            		("PR".equals(rejectrecords.iterator().next().getInvoicStatus()) || "PE".equals(rejectrecords.iterator().next().getInvoicStatus()))){
	            LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
	            Collection<LockVO> lockvos = new ArrayList<>();
	    	    TransactionLockVO processINVOICLockVO = new TransactionLockVO("PROINVMSG");
	    	    processINVOICLockVO.setAction("PROINV");
	    	    processINVOICLockVO.setClientType(ClientType.APPLICATION);
	    	    processINVOICLockVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	    processINVOICLockVO.setDescription("PROCESS INVOIC LOCK");
	    	    processINVOICLockVO.setRemarks("MANUAL LOCK");
	    	    processINVOICLockVO.setStationCode(logonAttributes.getStationCode());
	    	    processINVOICLockVO.setScreenId("MRA078");
	    	    lockvos.add(processINVOICLockVO);
	    	    releaseLocks(lockvos);
            }
            log.exiting("GPAReportingController", "updateInvoicReject");


        }
        /**
         * @author A-7929
         * @param Collection<invoicDetailsVO>,raiseClaimFlag
         * @throws SystemException
         *
         */
        @Advice(name = "mail.mra.updateMailStatus" , phase=Phase.POST_INVOKE)
		public void updateMailStatus(Collection<InvoicDetailsVO> invoicDetailsVOs, String raiseClaimFlag) throws SystemException {
			 log.entering("GPAReportingController","updateMailStatus");
	     	 try {
				MailGPAInvoicDetail.updateMailStatus(invoicDetailsVOs,raiseClaimFlag);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	     	 log.exiting("GPAReportingController","updateMailStatus");

        }
		 /**
         * @author A-7929
         * @param companyCode,code
		 * @throws ProxyException
         * @throws SystemException
		 * @throws ServiceNotAccessibleException
		 * @throws RemoteException
         *
         */
		public Collection<MailSubClassVO> findMailSubClass(String companyCode, String code) throws ProxyException, SystemException,RemoteException, ServiceNotAccessibleException {
			 log.entering("GPAReportingController","findMailSubClass");

				return  new MailTrackingDefaultsProxy().findMailSubClass(companyCode, code);


		}
		/**
         * @author A-7929
         * @param companyCode,mailIdr,mailSeqNum
		 * @throws ServiceNotAccessibleException
		 * @throws ProxyException
         * @throws SystemException
		 * @throws RemoteException
         *
         */

		public Collection<MailbagHistoryVO> findMailbagHistories(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ServiceNotAccessibleException {
			return  new MailTrackingDefaultsProxy().findMailbagHistories(companyCode, mailIdr,mailSeqNum);
		}

		  /**
         * @author A-8527
         * @param InvoicFilterVO
         * @return Page<InvoicDetailsVO>
         * @throws SystemException
         */
        public Page<ClaimDetailsVO> listClaimDetails(InvoicFilterVO invoicFilterVO, int pageNumber) throws SystemException {
            log.entering("GPAReportingController", "listClaimDetails");
            Page<ClaimDetailsVO> claimdtlsVOs = GPAClaimDetails.listClaimDetails(invoicFilterVO,pageNumber);
            log.exiting("GPAReportingController", "listClaimDetails");
            return claimdtlsVOs;
        }
        /**
         * @author A-8527
         * @param InvoicFilterVO
         * @return Page<InvoicDetailsVO>
         * @throws SystemException
         */
        public Page<ClaimDetailsVO> listGenerateClaimDetails(InvoicFilterVO invoicFilterVO, int pageNumber) throws SystemException {
            log.entering("GPAReportingController", "listGenerateClaimDetails");
            Page<ClaimDetailsVO> claimdtlsVOs = GPAClaimMaster.listGenerateClaimDetails(invoicFilterVO,pageNumber);
            log.exiting("GPAReportingController", "listGenerateClaimDetails");
            return claimdtlsVOs;
        }
       public Collection <USPSPostalCalendarVO> validateFrmToDateRange(InvoicFilterVO invoicFilterVO)throws RemoteException,SystemException, ServiceNotAccessibleException  {
           log.entering("GPAReportingController", "validateFrmToDateRange");
           USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO = new USPSPostalCalendarFilterVO();
           uSPSPostalCalendarFilterVO.setCompanyCode(invoicFilterVO.getCmpcod());
           uSPSPostalCalendarFilterVO.setCalPacode(invoicFilterVO.getGpaCode());
           LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
           fromDate.setDate(invoicFilterVO.getFromDate());
           uSPSPostalCalendarFilterVO.setCalValidFrom(fromDate);
           LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
           toDate.setDate(invoicFilterVO.getToDate());
           uSPSPostalCalendarFilterVO.setCalValidTo(toDate);
           Collection <USPSPostalCalendarVO> uspsPostalCalenderVOs=null;
           uspsPostalCalenderVOs = new MailTrackingDefaultsProxy().validateFrmToDateRange(uSPSPostalCalendarFilterVO);
           log.exiting("GPAReportingController", "validateFrmToDateRange");
           return uspsPostalCalenderVOs;
       }
       public  Map <String,String> validateuspsPacode(Collection<String> systemParameters)throws SystemException, ProxyException {
    	   log.entering("GPAReportingController", "validateuspsPacode");
    	   Map<String, String> systemParameterMap = new SharedDefaultsProxy().findSystemParameterByCodes(systemParameters);
    	   log.exiting("GPAReportingController", "validateuspsPacode");
    	   return systemParameterMap;
       }
/**
 * @author A-7371
 * @param mailInvoicMessage
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws ServiceNotAccessibleException
 */
       public USPSPostalCalendarVO findInvoicPeriodDetails(MailInvoicMessageVO mailInvoicMessage) throws SystemException, RemoteException, ServiceNotAccessibleException{

	        USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO = new USPSPostalCalendarFilterVO();
	        LocalDate date = null;
	        uspsPostalCalendarFilterVO.setCompanyCode(mailInvoicMessage.getCompanyCode());
	        uspsPostalCalendarFilterVO.setCalPacode(mailInvoicMessage.getPoaCode());
	        if(mailInvoicMessage.getMailInvoiceMessageDetailVOs() != null && !mailInvoicMessage.getMailInvoiceMessageDetailVOs().isEmpty()
	        		&& mailInvoicMessage.getMailInvoiceMessageDetailVOs().iterator().next().getRouteDepatureDate() != null){
	        	date = mailInvoicMessage.getMailInvoiceMessageDetailVOs().iterator().next().getRouteDepatureDate();
	        }else{
	        	date = mailInvoicMessage.getInvoiceDate();
	        }
	        uspsPostalCalendarFilterVO.setInvoiceDate(date);
	        uspsPostalCalendarFilterVO.setCalendarType(CAL_TYPE_INVOIC);
	        USPSPostalCalendarVO uspsPostalCalenderVO=new USPSPostalCalendarVO();
	        uspsPostalCalenderVO = new MailTrackingDefaultsProxy().findInvoicPeriodDetails(uspsPostalCalendarFilterVO);

    	   return uspsPostalCalenderVO;

       }
       public String findSystemParameterValue(String syspar)
   			throws SystemException {
   		String sysparValue = null;
   		ArrayList<String> systemParameters = new ArrayList<String>();
   		systemParameters.add(syspar);
   		Map<String, String> systemParMap;
		try {
			systemParMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameters);
			if (systemParMap != null) {
	   			sysparValue = systemParMap.get(syspar);
	   		}
	   		log.log(Log.FINE, " systemParameterMap ", systemParMap);

		} catch (ProxyException e) {
			log.log(Log.SEVERE,e.getMessage());
		}

   		return sysparValue;
   	}

/**
        *
        * @param invoicVO
        * @throws SystemException
        */
       public void processInvoic(InvoicVO invoicVO) throws SystemException{

    	   String batchUpdStatus = "OK";
    	   String response ="";
    	   String appJobReq ;
    	   long totalMailCount =1; 
    	   String dbJobCount ;
    	   long noofrecordsinBatches =0;
    	   long startBatchNum=0;
    	   long endBatchNum=0;
    	   long jobCount =1;
    	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
    	   dbJobCount = findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
    	   String levelOfProcessing = findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
    	   if(levelOfProcessing != null && "F".equals(levelOfProcessing)){
    		   invoicVO.setInvoicRefId("");
    		   invoicVO.setProcessingType("J");
    		   invoicVO.setPayType(" ");
    	   }
    	   else{
    		   invoicVO.setFileName(" ");
    		   invoicVO.setProcessingType("I");
    	   }
    	   if(dbJobCount != null && "1".equals(dbJobCount)){
    		   invoicVO.setNoDbJobRequired(true);
    	   }else{
    		   response = new MailGPAInvoicMaster().updateBatchNumForInvoic(invoicVO);
    	   }
    	   String actionCode = ("PR".equals(invoicVO.getInvoicStatusCode()) || "PE".equals(invoicVO.getInvoicStatusCode())) ? "RJ" : "PR";
    	 
    	   if(response != null && !(response.isEmpty()) && dbJobCount != null ) {
    	   appJobReq = response.split("-")[0];
    	   batchUpdStatus = response.split("-")[1];
    	  if("OK".equals(batchUpdStatus)&&"N".equals(appJobReq)){
	    	  String status = new MailGPAInvoicMaster().processInvoic(invoicVO);
	    	   /*if(invoicVO.isNoDbJobRequired()){
		    	   InvoiceTransactionLogVO txnLogVO = new InvoiceTransactionLogVO();
			   		txnLogVO.setCompanyCode(invoicVO.getCompanyCode());
			   		txnLogVO.setInvoiceType("MRAINVPRC");
			   		txnLogVO.setTransactionCode(invoicVO.getTxnCode());
			   		txnLogVO.setSerialNumber(invoicVO.getTxnSerialNum());
		    	   if("OK".equals(status)){
		    		   txnLogVO.setInvoiceGenerationStatus("C");
		    		   txnLogVO.setRemarks(new StringBuilder("Invoic processing completed for Invoice :").append(invoicVO.getInvoicRefId()).toString());
					}else{
						txnLogVO.setInvoiceGenerationStatus("F");
						txnLogVO.setRemarks(new StringBuilder("Invoic processing failed for Invoice :").append(invoicVO.getInvoicRefId()).toString());
					}
					try{
						new CRADefaultsProxy().updateTransactionandRemarks(txnLogVO);
					}catch(ProxyException exception){

				    }
	    	   }*/
	    	  log.log(Log.FINE, " OUT Parameter ok ", status); 
    	  }else if("OK".equals(batchUpdStatus)&&"Y".equals(appJobReq)){
   		   

   		createAppJobForInvoicProcessing(invoicVO,dbJobCount); 
   	   }
    	   
    	  else
    	  {
    		  log.log(Log.FINE, " OUT Parameter not ok ", response); 
    	  }
    	   }
    	   Collection<LockVO> lockvos = new ArrayList<LockVO>();
    	    TransactionLockVO processINVOICLockVO = new TransactionLockVO("PROINVMSG");
    	    processINVOICLockVO.setAction("PROINV");
    	    processINVOICLockVO.setClientType(ClientType.APPLICATION);
    	    processINVOICLockVO.setCompanyCode(logonAttributes.getCompanyCode());
    	    processINVOICLockVO.setDescription("PROCESS INVOIC LOCK");
    	    processINVOICLockVO.setRemarks("MANUAL LOCK");
    	    processINVOICLockVO.setStationCode(logonAttributes.getStationCode());
    	    processINVOICLockVO.setScreenId("MRA078");
    	   lockvos.add(processINVOICLockVO);
    	    releaseLocks(lockvos);
       }

       /**
        *
        * @param invoicDetailsVOs
        * @return
        * @throws SystemException
        */
       public String reprocessInvoicMails(Collection<InvoicDetailsVO> invoicDetailsVOs) throws SystemException{
    	   String status = new MailGPAInvoicMaster().reprocessInvoicMails(invoicDetailsVOs);
    	   return status;
       }


       /**
        *
        * @param companyCode
        * @return
        * @throws SystemException
        */
       public int checkForProcessCount(String companyCode,InvoicVO invoicVO) throws SystemException{
    	   int count = new MailGPAInvoicMaster().checkForProcessCount(companyCode);
    	   String levelOfProcessing = findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
    	   if(levelOfProcessing != null && "F".equals(levelOfProcessing)){
    	   String invoicStatus="";
    	   if(count ==0){
    		   Collection<MailInvoicMessageVO> mailInvoicMessage = new ArrayList<MailInvoicMessageVO>();
    		   MailInvoicMessageVO messageVO = new MailInvoicMessageVO();
    		   messageVO.setCompanyCode(companyCode);
    		   messageVO.setFileName(invoicVO.getFileName());
    		   messageVO.setInvoiceStatus("IN");
    		   messageVO.setRemark(invoicVO.getRemarks());
    		   mailInvoicMessage.add(messageVO);
    		   try{
    		    invoicStatus=MailInvoicMessageMaster.updateInvoicStatus(mailInvoicMessage);
    		   }catch(Exception e){
    			   e.getMessage();
	    		   }
    		   }
    	   }
    	   return count;
       }
       /**
        * @author A-7371
        * @param mailInvoicMessageVO
        * @throws SystemException
        */
       public void checkAutoProcessing(Collection<MailInvoicMessageVO> mailInvoicMessage) throws SystemException {
	      	 log.entering("GPAReportingController","checkAutoProcessing");
	      	 String messageCompletionFlag=MailInvoicMessageMaster.checkAutoProcessing(mailInvoicMessage);


	      	 if(MailConstantsVO.FLAG_YES.equals(messageCompletionFlag)){
	      		 if(mailInvoicMessage != null && !mailInvoicMessage.isEmpty()){
	      			mailInvoicMessage.iterator().next().setInvoiceStatus("NW");
	      		 }
	      		String invoicStatus=MailInvoicMessageMaster.updateInvoicStatus(mailInvoicMessage);

	      		String sysparValue = null;
				String sysparCode = "mail.mra.gpareporting.autoinvoicprocessingenabled";

				ArrayList<String> systemParameters = new ArrayList<String>();
				systemParameters.add(sysparCode);

				HashMap<String, String> systemParameterMap = null;
				try {
					systemParameterMap = (HashMap<String, String>) new SharedDefaultsProxy()
					.findSystemParameterByCodes(systemParameters);
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage());
				}
				log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
				if (systemParameterMap != null) {
					sysparValue = systemParameterMap.get(sysparCode);
					if ("Y".equals(sysparValue) && "OK".equals(invoicStatus)) {
						LogonAttributes logonAttributes =
				                ContextUtils.getSecurityContext().getLogonAttributesVO();
						MailInvoicMessageVO msgVO = mailInvoicMessage.iterator().next();
						InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
						invoiceTransactionLogVO.setCompanyCode(msgVO.getCompanyCode());
						invoiceTransactionLogVO.setInvoiceType("MRAINVPRC");
						invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setPeriodFrom( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setPeriodTo( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
				   		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
				   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
						invoiceTransactionLogVO.setRemarks("Invoic Auto processing initiated for Invoice :"+msgVO.getInvoiceReferenceNumber());
						invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
						invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
					    try{
					    	invoiceTransactionLogVO = new CRADefaultsProxy().initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
					    }catch(ProxyException exception){

					    }
						InvoicVO vo = new InvoicVO();
						vo.setCompanyCode(msgVO.getCompanyCode());
						vo.setSeqNumber(msgVO.getSerialNumber());
						vo.setInvoicRefId(" ");
						vo.setPoaCode(msgVO.getPoaCode());
						vo.setLastupdatedUser(msgVO.getLastUpdatedUser());
						vo.setFileName(msgVO.getFileName());
						vo.setProcessingType("J");
						vo.setTxnCode(invoiceTransactionLogVO.getTransactionCode());
						vo.setTxnSerialNum(invoiceTransactionLogVO.getSerialNumber());
						String batchUpdStatus = new MailGPAInvoicMaster().updateBatchNumForInvoic(vo);
						if("OK".equals(batchUpdStatus)){
							String status =new MailGPAInvoicMaster().processInvoic(vo);
							/*if("OK".equals(status)){
								invoiceTransactionLogVO.setInvoiceGenerationStatus("C");
								invoiceTransactionLogVO.setRemarks("Invoic Auto processing completed for Invoice :"+msgVO.getInvoiceReferenceNumber());
							}else{
								invoiceTransactionLogVO.setInvoiceGenerationStatus("F");
								invoiceTransactionLogVO.setRemarks("Invoic Auto processing failed for Invoice :"+msgVO.getInvoiceReferenceNumber());
							}
							try{
								new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
							}catch(ProxyException exception){

						    }*/
						}

					}
				}
	      	 }
	      	 log.exiting("GPAReportingController","checkAutoProcessing");
       }

       /**
        *
        * @param companyCode
        * @param mailSeqnum
        * @return
        * @throws SystemException
        */
       public Collection<InvoicDetailsVO> findInvoicAndClaimDetails(String companyCode, long mailSeqnum)
   		  	throws SystemException{
    	   Collection<InvoicDetailsVO> invoicDetails = MailGPAInvoicDetail.findInvoicAndClaimDetails(companyCode,mailSeqnum);
    	   return invoicDetails;
       }
      /**
        * Method		:	GPAReportingController.generateClaimAndResdits
        *	Added by 	:	A-4809 on May 16, 2019
        * Used for 	:
        *	Parameters	:	@param filterVO
        *	Parameters	:	@param txnlogInfo
        *	Parameters	:	@throws SystemException
        *	Return type	: 	void
        */
         public void generateClaimAndResdits(InvoicFilterVO filterVO, String txnlogInfo)throws SystemException{
           log.entering(CLASS_NAME, "generateClaimAndResdits");
           GPAClaimMaster gPAClaimMaster=null;
           LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
		   int count=0;
            String paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
			String paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
           
           Collection<ClaimDetailsVO> claimDetails=GPAClaimDetails.listClaimDetails(filterVO);
			                  
        
			  performAuditForEDIGeneration(claimDetails,"Y",filterVO.getFromDate(),filterVO.getToDate());

           if(InvoicFilterVO.FLAG_YES.equals(filterVO.getResditRequired())){
         		generateResdits(filterVO);

         	}
           String outStatus = saveClaimDetails(filterVO);
           String claimRefNum = null;
           ClaimVO claimVO = null;
           Collection<ClaimVO> intClaimVO =null;
           ClaimDetailMessageVO claimDetailMessageVO =null;
           Collection<MessageVO> messageVOs= null;
           MessageVO messageVO = null;
           StringBuilder messageText = null;
           String processStatus =null;
           String remarks =null;
   		   String txnInfo[] = null;
   		   String txnCod=null;
   		   	int serNum=0;
   		   if(txnlogInfo!=null){
   			txnInfo = txnlogInfo.split("-");
 		    txnCod = txnInfo[0];
 		    serNum = Integer.parseInt(txnInfo[1]);
   		   }

           if(outStatus!=null && !outStatus.isEmpty()){
        	   String[] outParam = outStatus.split("#");
        	  if(OK.equals(outParam[0]) && !"null".equals(outParam[1])){

        		  claimRefNum = outParam[1];
        		  filterVO.setClaimRefNum(claimRefNum);
        		  if(filterVO.getGpaCode().equals(paCode_dom)) {
        		  claimVO = generateClaimMessage(filterVO);
        		  LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
        		  StringBuilder claimFileName = new StringBuilder().append("CLMD_").append(date.toDisplayFormat("ddMMyyyyHHmmss")).append("_01");

        			  generateClaimMessageForInternationalandDomestic(claimVO,filterVO,claimFileName);
        			  try {
        					gPAClaimMaster = GPAClaimMaster.find(new GPAClaimMasterPK(logonAttributes
        							.getCompanyCode(), filterVO.getGpaCode(),claimVO.getInvoiceNumber()));
        				} catch (FinderException e) {
        					e.printStackTrace();
        				}
        			 if(gPAClaimMaster!=null) {
      				  gPAClaimMaster.setClaimgenflag("Y");
      				  gPAClaimMaster.setClaimGenFilname(claimFileName.toString());
        			 }
        		  }
        		  else{
        			  intClaimVO= generateClaimVOsForInternational(filterVO);
        			  for(ClaimVO interClaimVO :intClaimVO ) {
        				  count= count+1;
        				  LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
        				  String counter="_"+count;
        				  StringBuilder claimFileName = new StringBuilder().append("CLMI_").append(date.toDisplayFormat("ddMMyyyyHHmmss")).append(counter);
        				  generateClaimMessageForInternationalandDomestic(interClaimVO,filterVO,claimFileName);
        				   try {
          					gPAClaimMaster = GPAClaimMaster.find(new GPAClaimMasterPK(logonAttributes
          							.getCompanyCode(), filterVO.getGpaCode(),interClaimVO.getInvoiceNumber()));
          				} catch (FinderException e) {
          					e.printStackTrace();
          				}
        				   if(gPAClaimMaster!=null) {
        				    gPAClaimMaster.setClaimgenflag("Y");
        				    gPAClaimMaster.setClaimGenFilname(claimFileName.toString());
        				   }
        				}
        		  }







              	processStatus =COMPLETED;
              	remarks="Claims Generation Completed";
        	  }else{
           		processStatus =FAILED;
           		remarks="Claims Generation Failed";
        	  }
           }
           if(txnlogInfo!=null){
  		 InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();

  		    invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
  			invoiceTransactionLogVO.setInvoiceType(CLAIM_GENERATION_TYPE);
  			invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
  	   		invoiceTransactionLogVO.setInvoiceGenerationStatus(processStatus);
  	   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
  			invoiceTransactionLogVO.setRemarks(remarks.toString());
  			invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
  			invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
  		    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
  		    invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
  		    invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
  		    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
  		    invoiceTransactionLogVO.setSerialNumber(serNum);
  		    invoiceTransactionLogVO.setTransactionCode(txnCod);
  			try {
  			     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
  			} catch (ProxyException e) {
  				throw new SystemException(e.getMessage());
  			}
         }
         }
         private String  generateClaimMessageForInternationalandDomestic(ClaimVO claimVO, InvoicFilterVO filterVO,StringBuilder fileName) throws SystemException{
        	 Collection<MessageVO> messageVOs= null;
        	 ClaimDetailMessageVO claimDetailMessageVO= null;
        	 MessageVO messageVO = null;
             StringBuilder messageText = null;
             String processStatus =null;
        	 claimDetailMessageVO = constructBaseMessageVO(claimVO);
  		   claimDetailMessageVO.setMessageReferenceNumber(claimVO.getInvoiceNumber());
  		   MsgBrokerMessageProxy messageProxy = new MsgBrokerMessageProxy();
        	   try {
        		   messageVOs = messageProxy.getEncodedMessage(claimDetailMessageVO);
                 if(messageVOs!=null && messageVOs.size()>0) {
        		   String rawMessage = messageVOs.iterator().next().getRawMessage();
        		   if(rawMessage.trim().startsWith("UNB")){
        		   messageText = new StringBuilder(rawMessage.subSequence(
        				 rawMessage.indexOf("UNB"), rawMessage.indexOf("UNS")));
        		   }else{
        			  messageText = new StringBuilder(rawMessage.subSequence(
             				 rawMessage.indexOf("UNH"), rawMessage.indexOf("UNS")));
        		   }
        		   messageText = messageText.append(claimVO.getMessageText());
        		   messageText.append(rawMessage.subSequence(rawMessage.indexOf("UNS"), rawMessage.length()-1));
        		   messageVO = messageVOs.iterator().next();
        		   messageVO.setRawMessage(messageText.toString());
        		   messageVO.setOriginalMessage(messageText.toString());
        		   fileName.append(".TXT");
        		   messageVO.setFileName(fileName.toString());
        		   messageVO.setRawMessageBlob(messageText.toString());
        		  messageVO= messageProxy.sendMessage(messageVOs.iterator().next());
        		   log.log(Log.FINE, "MessageVOssss--------",messageVOs);
                 }
        	   } catch (ProxyException e) {
        		   log.log(Log.SEVERE, "ProxyException",e.getMessage());
        		processStatus =FAILED;
        	 }
        	   return processStatus;
         }


    private List<String> splitResditMessage(Collection<ResditEventVO> resditEventVOs ){

    	StringBuilder contentTemp = new StringBuilder();;
    	String content = null;
		int counter=0;
		StringBuilder cniTemp = new StringBuilder();
		List<String> cniList= new ArrayList<>();

		for(ResditEventVO resditEventVO : resditEventVOs){
			contentTemp = contentTemp.append(resditEventVO.getMsgText());
		}
		content = contentTemp.toString().replace("?'","##");
		for(String cni : content.split("CNI\\+")){

			  if (counter==0){
	                counter++;
	                continue;
	            }

			  cni = cni.replace("##","?'");
			  cniTemp.append("CNI+").append(cni);

			  if (counter>=999){
				  cniList.add(cniTemp.toString());
				  cniTemp = new StringBuilder();
				  counter=0;
			  }
			  counter++;
		  }
		  if (cniTemp.length()>0){
			  cniList.add(cniTemp.toString());
		  }
		  return cniList;
    }

      /**
	 * 	Method		:	GPAReportingController.generateResdits
	 *	Added by 	:	A-4809 on Jun 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Return type	: 	void
     * @throws SystemException
     * @throws GenerationFailedException
	 */
	private void generateResdits(InvoicFilterVO filterVO) throws GenerationFailedException, SystemException {
		log.entering(CLASS_NAME, "generateResdits");
		ClaimDetailsVO claimDetailsVO=new ClaimDetailsVO();
		String claimRefNumber=null;
		GPAClaimMaster gPAClaimMaster=null;
		String resditSendFlag=null;
		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils
					.getSecurityContext().getLogonAttributesVO();
		String claimDetail=GPAClaimMaster.findClaimMasterDetails(filterVO);

		if(claimDetail==null){
			claimRefNumber=GPAClaimMaster.findClaimReferenceNumber(filterVO);
			claimDetailsVO.setClaimRefNumber(claimRefNumber);
			claimDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			claimDetailsVO.setGpaCode(filterVO.getGpaCode());
			claimDetailsVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(filterVO.getFromDate()));
			claimDetailsVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(filterVO.getToDate()));
			claimDetailsVO.setResditSendFlag("Y");
			claimDetailsVO.setClaimGenerateFlag("N");
			new GPAClaimMaster(claimDetailsVO);
		}else{
			claimRefNumber=claimDetail.split("~")[0];
			resditSendFlag=claimDetail.split("~")[1];
			try {
				gPAClaimMaster = GPAClaimMaster.find(new GPAClaimMasterPK(logonAttributes
						.getCompanyCode(), filterVO.getGpaCode(), claimRefNumber));
			} catch (FinderException e) {
				e.printStackTrace();
			}

		}

		if(!"Y".equals(resditSendFlag)){
		Collection<ResditEventVO> resditEventVOs = findGeneratedResditMessages(filterVO);
		 Collection<MessageVO> messageVOs= null;
		 MessageVO messageVO = null;
		 List<String> resditList= new ArrayList<>();

		 if(resditEventVOs!=null && !resditEventVOs.isEmpty()){

			 resditList= splitResditMessage(resditEventVOs);

		if(resditList!=null && !resditList.isEmpty()){
			for(String resditMessage : resditList){

					ResditMessageVO resditMessageVO = constructResditMessageVO(resditEventVOs,resditMessage);

		MsgBrokerMessageProxy messageProxy = new MsgBrokerMessageProxy();
		StringBuilder messageText = null;

   	   try {
  		   messageVOs = messageProxy.getEncodedMessage(resditMessageVO);
  		 String rawMessage=null;
  		   if(messageVOs!=null)
  		    rawMessage = messageVOs.iterator().next().getRawMessage();

  		   if(rawMessage!=null && rawMessage.trim().startsWith("UNB")){
  		   messageText = new StringBuilder(rawMessage.subSequence(
  				 rawMessage.indexOf("UNB"), rawMessage.indexOf("UNT")));
  		   }else{
  			   if(rawMessage!=null)
  			   messageText = new StringBuilder(rawMessage.subSequence(
  	  				 rawMessage.indexOf("UNH"), rawMessage.indexOf("UNT")));
  		   }

			  			/*for(ResditEventVO resditEventVO : resditEventVOs){
  				messageText = messageText.append(resditEventVO.getMsgText());
			  			}*/

			  		   messageText = messageText.append(resditMessage);

  			if(rawMessage!=null)
  			messageText.append(rawMessage.subSequence(rawMessage.indexOf("UNT"), rawMessage.length()-1));
  			messageVO = messageVOs.iterator().next();
  			messageVO.setRawMessage(messageText.toString());
  			messageVO.setOriginalMessage(messageText.toString());
  			messageVO.setRawMessageBlob(messageText.toString());
  			messageVO= messageProxy.sendMessage(messageVO);
  		   log.log(Log.FINE, "MessageVOssss--------",messageVOs);
  	      } catch (ProxyException e) {
  		   log.log(Log.SEVERE, "ProxyException",e.getMessage());
  	      }


			 	}
			 }

		 }
			if(gPAClaimMaster!=null){
				gPAClaimMaster.setResditSendFlag("Y");
			}

		}
		log.exiting(CLASS_NAME, "generateResdits");
	}
	/**
	 * 	Method		:	GPAReportingController.constructResditMessageVO
	 *	Added by 	:	A-4809 on Jun 7, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param resditEventVOs
	 *	Parameters	:	@return
	 *	Return type	: 	ResditMessageVO
	 * @throws SystemException
	 * @throws GenerationFailedException
	 */
	private ResditMessageVO constructResditMessageVO(Collection<ResditEventVO> resditEventVOs,String resditMessage) throws GenerationFailedException, SystemException {
		log.entering(CLASS_NAME, "constructResditMessageVO");
		ResditMessageVO resditMessageVO = new ResditMessageVO();
		String resditVersion="";
		String pa = "";
		ResditEventVO firstResditEvent = resditEventVOs.iterator().next();
		resditVersion = firstResditEvent.getResditVersion();
		pa= firstResditEvent.getPaCode();
		String domPa = findSystemParameterValue(DOM_PA);
		resditMessageVO.setCompanyCode(firstResditEvent.getCompanyCode());
		resditMessageVO.setStationCode(firstResditEvent.getEventPort());

		Criterion mesrefCriterion = KeyUtils.getCriterion(
				resditMessageVO.getCompanyCode(),
				KEY_MESSAGE_REFERENCE, firstResditEvent.getPaCode());

		if(domPa.equals(pa)){
		resditMessageVO.setMessageType("IFTSTADOMMAIN");
		}else{
		  if("1.1".equals(resditVersion)){
			  resditMessageVO.setMessageType("IFTSTAMAIN1.1");
		  }else{
			  resditMessageVO.setMessageType("IFTSTAMAIN");
		  }
		}
		resditMessageVO.setMessageStandard(ResditMessageVO.EDIFACT);
		resditMessageVO.setRecipientID(firstResditEvent.getPaCode());
		resditMessageVO.setMessageReferenceNumber(KeyUtils.getKey(mesrefCriterion));
		resditMessageVO.setConfigCheckRequired(true);

		/*LocalDate preparationDate =
			new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			resditMessageVO.setPreparationDate(
				preparationDate.toDisplayFormat(PREP_DATE_FORMAT));
		resditMessageVO.setPreparationTime(
				preparationDate.toDisplayFormat(PREP_TIME_FORMAT));
		Criterion interchangeCriterion = KeyUtils.getCriterion(
				resditMessageVO.getCompanyCode(),
				KEY_RESDIT_INTERCHANGE, resditMessageVO.getRecipientID());
		resditMessageVO.setInterchangeControlReference(
				KeyUtils.getKey(interchangeCriterion));
		Criterion mesrefCriterion = KeyUtils.getCriterion(
				resditMessageVO.getCompanyCode(),
				KEY_MESSAGE_REFERENCE, resditMessageVO.getRecipientID());
		resditMessageVO.setMessageReferenceNumber(
				KeyUtils.getKey(mesrefCriterion));
		if(!MailConstantsVO.RESDIT_XX.equals(
				resditEventVO.getResditEventCode())){
				CarditVO carditVO = Cardit.findCarditDetailsForResdit(
					resditEventVO.getCompanyCode(), resditEventVO.getConsignmentNumber());
				if(carditVO!=null){
					resditMessageVO.setTestIndicator(String.valueOf(carditVO.getTstIndicator()));
				}
			}	*/
		String resditFileName="";
		LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		resditFileName = new StringBuilder().append("RESDIT_").append(date.toDisplayFormat("yyyyMMddHHmmssms")).append(firstResditEvent.getPaCode()).append(firstResditEvent.getCarditExist()).append(firstResditEvent.getEventPort()).toString();
		resditMessageVO.setResditFileName(resditFileName);
		int totalSegmentCount = 0;
//		for(ResditEventVO resditEventVO : resditEventVOs){
//			   int segmentCount = 0;
//			   segmentCount= StringUtils.countMatches(resditEventVO.getMsgText(), "'");
//			   totalSegmentCount=totalSegmentCount+segmentCount;
//		}
		totalSegmentCount= StringUtils.countMatches(resditMessage, "'");
  		resditMessageVO.setSegmentCount(totalSegmentCount);
		log.exiting(CLASS_NAME, "constructResditMessageVO");
		return resditMessageVO;
	}
	/**
	 * 	Method		:	GPAReportingController.findGeneratedResditMessages
	 *	Added by 	:	A-4809 on Jun 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Return type	: 	Collection<ResditEventVO>
	 * @throws SystemException
	 */
	private Collection<ResditEventVO> findGeneratedResditMessages(InvoicFilterVO filterVO) throws SystemException {
		log.entering(CLASS_NAME, "findGeneratedResditMessages");
		Collection<ResditEventVO> resditEventVOs =null;
		resditEventVOs = GPAClaimMaster.findGeneratedResditMessages(filterVO);
		return resditEventVOs;
         }

      /**
	 * 	Method		:	GPAReportingController.generateClaimMessage
	 *	Added by 	:	A-4809 on May 30, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Return type	: 	void
	 */
	private ClaimVO generateClaimMessage(InvoicFilterVO filterVO) throws SystemException{
		log.entering(CLASS_NAME, "generateClaimMessage");
		return GPAClaimMaster.findMailbagsForClaim(filterVO);

	}  /**
	 * 	Method		:	GPAReportingController.generateClaimMessageForInternational
	 *	Added by 	:	A-8176 on Aug 07, 2020
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Return type	: 	void
	 */
	private Collection<ClaimVO> generateClaimVOsForInternational(InvoicFilterVO filterVO) throws SystemException{
		log.entering(CLASS_NAME, "generateClaimMessage");
		return GPAClaimMaster.findMailbagsForClaimForInternational(filterVO);
	}

	/**
	 * 	Method		:	GPAReportingController.saveClaimDetails
	 *	Added by 	:	A-4809 on May 28, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Return type	: 	void
     * @throws SystemException
	 */
	private String saveClaimDetails(InvoicFilterVO filterVO) throws SystemException {
		log.entering(CLASS_NAME, "saveClaimDetails");
		return GPAClaimMaster.saveClaimDetails(filterVO);
	}

	/**
       * 	Method		:	GPAReportingController.constructBaseMessageVO
       *	Added by 	:	A-4809 on May 16, 2019
       * 	Used for 	:
       *	Parameters	:	@param filterVO
       *	Parameters	:	@return
       *	Return type	: 	BaseMessageVO
     * @throws SystemException
       */
      	private ClaimDetailMessageVO constructBaseMessageVO(ClaimVO claimVO)
      			throws SystemException {
            LogonAttributes logonAttributes =
                    ContextUtils.getSecurityContext().getLogonAttributesVO();
      		ClaimDetailMessageVO baseMessageVO = new ClaimDetailMessageVO();
      		String domPA = findSystemParameterValue(DOM_PA);
      		if(domPA.equals(claimVO.getPoaCode())){
      		baseMessageVO.setMessageType(MSG_TYP_DOM);
      		}else{
    		baseMessageVO.setMessageType(MSG_TYPE);
    		}

    		baseMessageVO.setMessageSubType("");
    		baseMessageVO.setMessageStandard(MSG_STD);
    		baseMessageVO.setCompanyCode(logonAttributes.getCompanyCode());;
    		baseMessageVO.setStationCode(logonAttributes.getAirportCode());
      		baseMessageVO.setRecipientID(claimVO.getPoaCode());
      		baseMessageVO.setInvDate(claimVO.getInvDate());
      		baseMessageVO.setClaimVO(claimVO);
      		baseMessageVO.setConfigCheckRequired(true);

      		int segmentCount = 0;
      		segmentCount= StringUtils.countMatches(claimVO.getMessageText(), "'");
      		claimVO.setSegmentCount(String.valueOf(segmentCount));

      		return baseMessageVO;

      	}
/**
 * 	Method		:	GPAReportingController.generateClaimAndResditsFromJob
 *	Added by 	:	A-4809 on May 31, 2019
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Return type	: 	void
 */
      public void generateClaimAndResditsFromJob(String companyCode)throws SystemException{
    	  Collection<USPSPostalCalendarVO> calendarVOs =new MRADefaultsController().
    			  findUSPSInternationalIncentiveJobDetails(companyCode);
    	  LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
    	  InvoicFilterVO invoicFilterVO = new InvoicFilterVO();
    	  if(calendarVOs!=null && !calendarVOs.isEmpty()){
    		  boolean eDIGenerated=false;
    		  for(USPSPostalCalendarVO uspsPostalCalendarVO : calendarVOs){
    			  invoicFilterVO.setCmpcod(companyCode);
    			  invoicFilterVO.setFromDate(uspsPostalCalendarVO.getPeriodFrom().toDisplayDateOnlyFormat());
    			  invoicFilterVO.setToDate(uspsPostalCalendarVO.getPeriodTo().toDisplayDateOnlyFormat());
    			  invoicFilterVO.setGpaCode(uspsPostalCalendarVO.getGpacod());
    			  invoicFilterVO.setTriggerPoint(TRIGGERPOINT_JOB);
    			  if(uspsPostalCalendarVO.getClmGenarationDate()!=null){
    					if (currentDate.toDisplayDateOnlyFormat().equals(uspsPostalCalendarVO.getClmGenarationDate().toDisplayDateOnlyFormat())){
    						if(!isClaimGenerated(invoicFilterVO)){
    						generateClaimAndResdits(invoicFilterVO,null);
    						eDIGenerated=true;

    						}

    						}else if(currentDate.toDisplayDateOnlyFormat().equals(uspsPostalCalendarVO.getClmGenarationDate().addDays(-1).toDisplayDateOnlyFormat())){
    							 generateResdits(invoicFilterVO);
    						}

//    					if(eDIGenerated){
//    						 Collection<ClaimDetailsVO> claimDetails=GPAClaimDetails.listClaimDetails(invoicFilterVO);
//    						  performAuditForEDIGeneration(claimDetails,"Y");
//    					}
//    					else{
//						return;
//					}
    			}


    		  }



    	  }
}
     // @Advice(name = "mail.mra.performAuditForEDIGeneration" , phase=Phase.POST_INVOKE)
       public void performAuditForEDIGeneration(Collection<ClaimDetailsVO> claimDetails, String claimFlag, String fromDate, String toDate) throws SystemException {
    	  LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			 Calendar time = null;
			 time = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true).toGMTDate().toCalendar();
			//MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
			if(claimDetails!=null && claimDetails.size()>0){
				for(ClaimDetailsVO invoicDetailsVO:claimDetails){
					MailbagAuditVO mailbagAuditVO = new MailbagAuditVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_MRA,MailbagAuditVO.ENTITY_MAILBAG);
					mailbagAuditVO.setCompanyCode(invoicDetailsVO.getCmpcod());
					mailbagAuditVO.setMailbagId(invoicDetailsVO.getMailBagId());
					mailbagAuditVO.setMailSequenceNumber(invoicDetailsVO.getMailSeqNumber());
//					if("Y".equals(raiseClaimFlag)){
//					mailbagAuditVO.setActionCode("USPS RAISE CLAIM");
//					StringBuilder additionalInformation = new StringBuilder("Claim raised for amount of ").append(invoicDetailsVO.getClaimamount());
//					mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());
//					}
//					else{
						mailbagAuditVO.setActionCode("USPS Claim EDI generation");
						StringBuilder additionalInformation = new StringBuilder("USPS EDI generated for the period ").append(fromDate).append(" to ").append(toDate);
						mailbagAuditVO.setAdditionalInformation(additionalInformation.toString());

					//}
					mailbagAuditVO.setLastUpdateUser(logonAttributes.getUserId());  //last updated user
					mailbagAuditVO.setStationCode(logonAttributes.getStationCode());
					mailbagAuditVO.setTxnLocalTime(time);//transaction time
					mailbagAuditVO.setTxnTime(time);
					mailbagAuditVO.setAuditTriggerPoint("ADM032");

					AuditUtils.performAudit(mailbagAuditVO);
				}
			}

}

       /**
        * @author A-7540
        * @param mailbagID
        * @return
        * @throws ProxyException
        * @throws SystemException
        * @throws RemoteException
        * @throws ServiceNotAccessibleException
        * @throws WebServiceException
        */
       public Collection<ResditReceiptVO> getResditInfofromUSPS(String mailbagID) throws ProxyException,
         SystemException,RemoteException, ServiceNotAccessibleException, WebServiceException {
			 log.entering("GPAReportingController","getResditInfofromUSPS");
			 Collection<ResditReceiptVO> resditReceiptVOs;
			 resditReceiptVOs = new MailMRAWebservicesProxy().getResditInfofromUSPS(mailbagID);
             return resditReceiptVOs;

		}


/**
   * @author A-7371
   * @param companyCode
 * @throws SystemException
   */
	public void generateClaimMessageText(String companyCode) throws SystemException {

		Collection<ClaimDetailsVO> claimDetailsVOs=null;

		String paCode_int = null;
        String paCode_dom = null;
		paCode_int = findSystemParameterValue(USPS_INTERNATIONAL_PA);
		paCode_dom = findSystemParameterValue(USPS_DOMESTIC_PA);
		  claimDetailsVOs =new GPAInvoicMsgRef().findMailBagsForMessageGeneration(companyCode);
		  if(claimDetailsVOs!=null){
			  for(ClaimDetailsVO claimDetailsVO:claimDetailsVOs){
		  ClaimDetailMessageVO claimDetailMessageVO =new ClaimDetailMessageVO();

		  claimDetailMessageVO.setClaimDetailsVO(claimDetailsVO);
		  claimDetailMessageVO.setConfigCheckRequired(false);

		  LogonAttributes logonAttributes =
                  ContextUtils.getSecurityContext().getLogonAttributesVO();
		  if(paCode_int.equals(claimDetailsVO.getGpaCode())){
	    		claimDetailMessageVO.setMessageType("INVOICCLMINTSUB");
			}else if(paCode_dom.equals(claimDetailsVO.getGpaCode())){
			  	claimDetailMessageVO.setMessageType("INVOICCLMDOMSUB");
			}
    		claimDetailMessageVO.setMessageSubType("");
    		claimDetailMessageVO.setMessageStandard(MSG_STD);
    		claimDetailMessageVO.setCompanyCode(logonAttributes.getCompanyCode());;
    		claimDetailMessageVO.setStationCode(logonAttributes.getAirportCode());

		  MsgBrokerMessageProxy messageProxy = new MsgBrokerMessageProxy();
          Collection<MessageVO> messageVOs= null;
     	   try {
     		   messageVOs = messageProxy.getEncodedMessage(claimDetailMessageVO);
     		   log.log(Log.FINE, "MessageVOssss--------",messageVOs);
     	   } catch (ProxyException e) {
     		   log.log(Log.SEVERE, "ProxyException",e.getMessage());
     	 }
     	  GPAInvoicMsgRefPK gPAInvoicMsgRefPK=new GPAInvoicMsgRefPK();
     	 gPAInvoicMsgRefPK.setCompanyCode(claimDetailsVO.getCompanyCode());
     	gPAInvoicMsgRefPK.setMailSequenceNumber(claimDetailsVO.getMailSeqNumber());
     	gPAInvoicMsgRefPK.setSerialNumber(claimDetailsVO.getSernum());
     	GPAInvoicMsgRef gPAInvoicMsgRef=null;
     	try {
        gPAInvoicMsgRef = GPAInvoicMsgRef.find(gPAInvoicMsgRefPK);
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			log.log(Log.INFO,"FinderException", e.getMessage());
		}
     	if(messageVOs.iterator().next()!=null && gPAInvoicMsgRef!=null){
     	gPAInvoicMsgRef.setMessageText(messageVOs.iterator().next().getOriginalMessage());
     	gPAInvoicMsgRef.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
     	gPAInvoicMsgRef.setLastUpdateUser(logonAttributes.getUserName());

     	}
	 }
		  }
	}
/**
 *
 * 	Method		:	GPAReportingController.isClaimGeneraetd
 *	Added by 	:	A-8061 on 20-Jun-2019
 * 	Used for 	:	ICRD-262451
 *	Parameters	:	@param invoicFilterVO
 *	Return type	: 	void
 */
public Boolean isClaimGenerated(InvoicFilterVO invoicFilterVO)throws SystemException {

	String claimMasterDetail=null;
	String [] claimMasterDetails=null;
	boolean isClaimGenerated=false;
	claimMasterDetail=GPAClaimMaster.findClaimMasterDetails(invoicFilterVO);
	if(claimMasterDetail!=null){
		claimMasterDetails=claimMasterDetail.split("~");
		if(claimMasterDetails.length == 3 && "Y".equals(claimMasterDetails[2])){
			isClaimGenerated=true;
		}
	}


	return isClaimGenerated;


}



/**
 *
 * 	Method		:	GPABillingController.isInitiatedInvoic
 *	Added by 	:	A-5219 on 20-Apr-2020
 * 	Used for 	:
 *	Parameters	:	@param invoicVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Return type	: 	boolean
 */
public boolean isInitiatedInvoic(InvoicVO invoicVO)
		throws SystemException {
	boolean isInitiatedInvoic = false;
	isInitiatedInvoic = MailInvoicMessageMaster.isInitiatedInvoic(invoicVO);
	return isInitiatedInvoic;
}


/**
 * @author A-5526
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws ObjectAlreadyLockedException
 */
public Collection<LockVO> addLocks(String companyCode)
	    throws SystemException, ObjectAlreadyLockedException
	  {
	    Collection<LockVO> lockvos = new ArrayList<LockVO>();

	    TransactionLockVO processINVOICLockVO = new TransactionLockVO("PROINVMSG");
	    LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
	    processINVOICLockVO.setAction("PROINV");
	    processINVOICLockVO.setClientType(ClientType.APPLICATION);
	    processINVOICLockVO.setCompanyCode(logonAttributes.getCompanyCode());
	    processINVOICLockVO.setDescription("PROCESS INVOIC LOCK");
	    processINVOICLockVO.setRemarks("MANUAL LOCK");
	    processINVOICLockVO.setStationCode(logonAttributes.getStationCode());
	    processINVOICLockVO.setScreenId("MRA078");
	    lockvos.add(processINVOICLockVO);
	    Collection<LockVO> acquiredLockVOs = new ArrayList<LockVO>();
	    try {
	      acquiredLockVOs = new FrameworkLockProxy().addLocks(lockvos);
	    } catch (ProxyException exception) {
	      this.log.log(7, new Object[] { "Proxy ExceptionCaught", exception });
	    }
	    catch (SystemException ex) {
	      this.log.log(7, " SystemException......." + ex.getErrors());

	      boolean isFound = false;
	      if ((ex.getErrors() != null) && (ex.getErrors().size() > 0)) {
	        for (ErrorVO errvo : ex.getErrors())
	        {
	          if ("persistence.lock.objectalreadylocked"
	            .equals(errvo.getErrorCode())) {
	            isFound = true;
	            break;
	          }
	        }
	      }
	      if (isFound) {
	        throw new ObjectAlreadyLockedException(ex.getErrors());
	      }
	      throw new SystemException(ex.getErrors());
	    }
	    this.log.exiting("Processor", "addLocks");
	    return acquiredLockVOs;
	  }
/**
 * @author A-5526
 * @param lockVOs
 * @throws ObjectNotLockedException
 * @throws SystemException
 */
	   void releaseLocks(Collection<LockVO> lockVOs)
	    throws ObjectNotLockedException, SystemException
	  {
	    try
	    {
	    	Proxy.getInstance().get(FrameworkLockProxy.class).releaseLocks(lockVOs);
	    } catch (ProxyException ex) {
	      throw new SystemException(ex.getMessage(), ex);
	    } catch (SystemException ex) {
	      this.log.log(7, "System Exception");
	      boolean isFound = false;
	      if ((ex.getErrors() != null) && (ex.getErrors().size() > 0)) {
	        for (ErrorVO errvo : ex.getErrors()) {
	          if ("persistence.lock.objectnotlocked".equals(errvo.getErrorCode())) {
	            isFound = true;
	            break;
	          }
	        }
	      }
	      if (isFound) {
	        throw new ObjectNotLockedException(ex);
	      }
	      throw new SystemException(ex.getErrors());
	    }
	  }


	  /**
       * @author A-5526
       * @param companyCode,mailIdr,mailSeqNum
		 * @throws ServiceNotAccessibleException
		 * @throws ProxyException
       * @throws SystemException
		 * @throws RemoteException
       *
       */

		public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(String companyCode, String mailIdr, long mailSeqNum) throws RemoteException, SystemException, ServiceNotAccessibleException {
			return  new MailTrackingDefaultsProxy().findApprovedForceMajeureDetails(companyCode, mailIdr,mailSeqNum);
		}
		 public int checkForRejectionMailbags(String companyCode,InvoicVO invoicVO) throws SystemException{
	        log.entering("checkForRejectionMailbags", "listInvoicDetails");
	      return MailGPAInvoicDetail.checkForRejectionMailbags(companyCode,invoicVO);
		}
		public void processInvoicFileFromJob(InvoicVO invoicVO) throws SystemException {
			
			new MailGPAInvoicMaster().processInvoicFileFromJob(invoicVO);
		}

		public void updateInvoicProcessingStatusFromJob(String companyCode) throws SystemException {
			new MailGPAInvoicMaster().updateInvoicProcessingStatusFromJob(companyCode);
			
		}
		 public void createAppJobForInvoicProcessing(InvoicVO invoicVO,String dbJobCount) throws SystemException{
			 long jobCount=Long.parseLong(dbJobCount);
			   		   long totalMailCount=new MailGPAInvoicMaster().findBatchNo(invoicVO);
			   		long noofrecordsinBatches = Math.floorDiv(totalMailCount,jobCount) ;
			   		String actionCode = ("PR".equals(invoicVO.getInvoicStatusCode()) || "PE".equals(invoicVO.getInvoicStatusCode())) ? "RJ" : "PR";
			   	LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
			              if( totalMailCount < 2) {
			            	  noofrecordsinBatches = totalMailCount;
			                jobCount = 1;
			              }
			              long startBatchNum = 1;
			              long endBatchNum = noofrecordsinBatches; 
			              for( int i=1;i<=jobCount;i++){
			           	   if( i == jobCount)
						   {
			           		   endBatchNum = totalMailCount;              	   
						   }        
			           InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
			           jobScheduleVO.setCompanyCode(invoicVO.getCompanyCode());
			           jobScheduleVO.setStartBatchnum(startBatchNum);
			      		jobScheduleVO.setEndBatchnum(endBatchNum);       		
			      		jobScheduleVO.setFileName(invoicVO.getFileName()); 
			      		jobScheduleVO.setPoacod(invoicVO.getPoaCode());  
			      		jobScheduleVO.setTxnCode(invoicVO.getTxnCode()); 
			      		jobScheduleVO.setTxnSerialNum(invoicVO.getTxnSerialNum()); 
			      		jobScheduleVO.setActionCode(actionCode);
			      		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
			      		jobScheduleVO.setRepeatCount(0);
			      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
			      		jobScheduleVO.setScheduleId(String.valueOf(i)); 
			      		jobScheduleVO.setJobidx(i); 
			      		LocalDate startTime =new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true);       		
			      		LocalDate endTime =startTime.addHours(5);
			      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
			      		jobScheduleVO.setEndTime(endTime); 
			      		try {
			      			SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
			      		} catch (JobSchedulerException e) {
			      			throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR,e);
			      		} 
			      		startBatchNum=endBatchNum+1; 
			      		endBatchNum=endBatchNum+noofrecordsinBatches;
			   	   } 
		}




}