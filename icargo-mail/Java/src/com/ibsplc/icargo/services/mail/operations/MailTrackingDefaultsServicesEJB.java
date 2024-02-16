/*
 * MailTrackingDefaultsServicesEJB.java Created on May 30, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.mail.operations;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacityFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentCapacitySummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.operations.*;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentVolumeDetailsVO;
import com.ibsplc.icargo.business.mail.operations.aa.AAMailController;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.lh.LHMailController;
import com.ibsplc.icargo.business.mail.operations.tk.TKMailController;
import com.ibsplc.icargo.business.mail.operations.vo.*;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.AutoForwardDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mailsecurityandscreening.SecurityAndScreeningMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.mld.MLDMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.DWSMasterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.FlightListingFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportFlightOperationsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ImportOperationsFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RunnerFlightVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.LocationEnquiryFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.template.TemplateRenderingException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.Auditor;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;
//import com.ibsplc.icargo.business.xaddons.oz.mail.operations.AsianaMailController;

/**
 * @author
 * @ejb.bean description= MODULE
 * display-name= MODULE jndi-name=
 * "com.ibsplc.icargo.services.mail.operations.MailTrackingDefaultsServices"
 * name="MailTrackingDefaultsServices" type="Stateless"
 * view-type="remote" remote-business-interface=
 * "com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI"
 * @ejb.transaction type="Supports" Bean implementation class for Enterprise
 * Bean: MailTrackingDefaultsServices
 */

public class MailTrackingDefaultsServicesEJB extends AbstractFacadeEJB
        implements MailTrackingDefaultsBI, Auditor {

    private Log log = LogFactory.getLogger("mail.operations");

    private static final String MODULE =  "MailTrackingDefaultsServicesEJB";
    private static final String MAILTRACKINGDEFAULTSEJB = "MailTrackingDefaultsEJB";
    private static final String MAIL_OPERATION_SERVICES="mailOperationsFlowServices";
    private static final String MAIL_CONTROLLER="mAilcontroller";
    /**
     * Added for HHT upload (job/direct upload)
     *
     * @param uploadSystemParameter
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public String findRealTimeuploadrequired(String uploadSystemParameter)
            throws SystemException, RemoteException {
        log.entering( MODULE, "updateMailOperationalstatus");
        return new MailUploadController()
                .findRealTimeuploadrequired(uploadSystemParameter);
    }


    /**
     * @param mailBagVOs
     * @return void
     * @throws RemoteException
     * @throws SystemException
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @author a-5526 This method is used to save mailbag scanned from HHT
     */
    public void saveMailScannedDetails(
            Collection<MailScanDetailVO> mailScanDetailVOs)
            throws RemoteException, SystemException {
        new MailUploadController().saveMailScannedDetails(mailScanDetailVOs);
    }

    /**
     * @param mailBagVOs
     * @return void
     * @throws RemoteException
     * @throws SystemException
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @author a-5526 This method is used to save mailbag scanned from HHT
     */
    public ScannedMailDetailsVO saveMailUploadDetails(
            Collection<MailUploadVO> mailBagVOs, String scanningPort)
            throws RemoteException, SystemException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException {
        try {
        return new MailUploadController().saveMailUploadDetails(mailBagVOs,
                scanningPort);
		} catch (ForceAcceptanceException e) {
			 throw new MailHHTBusniessException(e);
		}
    }

    public void saveMailDetailsFromJob(Collection<MailUploadVO> mailBagVOs,
                                       String scanningPort) throws RemoteException, SystemException,
            MailHHTBusniessException, MailMLDBusniessException,
            MailTrackingBusinessException {
        Transaction txn = null;
        try {
            TransactionProvider tm = PersistenceController
                    .getTransactionProvider();
            txn = tm.getNewTransaction(false);
            new MailUploadController().saveMailUploadDetails(mailBagVOs,
                    scanningPort);
            txn.commit();
        } catch (MailHHTBusniessException t) {
            if (txn != null) {
                txn.rollback();
            }
            throw t;
        } catch (Exception t) {
            if (txn != null) {
                txn.rollback();
            }
        }

    }

    /**
     * Marks group delivery of all mailbags that can be delivered Jan 29, 2007,
     * A-1739
     *
     * @param mailArrivalVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#deliverMailbags(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO)
     */
    public void deliverMailbags(MailArrivalVO mailArrivalVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "deliverMailbags");
        try {
            new MailController().deliverMailbags(mailArrivalVO);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailbagIncorrectlyDeliveredException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailTrackingBusinessException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting( MODULE, "deliverMailbags");

    }

    /**
     * @param mailbagsInInventory
     * @param poaCode
     * @author A-1936 Added By Karthick V as the part of the NCA Mail Tracking
     * Cr
     */
    public void deliverMailBagsFromInventory(
            Collection<MailInInventoryListVO> mailbagsInInventory)
            throws SystemException, RemoteException {
        new MailController().deliverMailBagsFromInventory(mailbagsInInventory);
    }

    /**
     * @param containerInInventory
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-2553
     */
    public void deliverContainersFromInventory(
            Collection<ContainerInInventoryListVO> containerInInventory)
            throws SystemException, RemoteException {
        new MailController()
                .deliverContainersFromInventory(containerInInventory);
    }

    /**
     * @param saveScannedOffloadMails
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void saveScannedDeliverMails(
            Collection<MailArrivalVO> deliverVosForSave)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "saveScannedMails");
        try {
            new MailController().saveScannedDeliverMails(deliverVosForSave);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailbagIncorrectlyDeliveredException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailTrackingBusinessException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting( MODULE, "saveScannedMails");
    }

    /***************************************************************************
     * This method is used to saveAcceptanceDetails
     *
     * @param mailAcceptanceVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public Collection<ScannedMailDetailsVO> saveAcceptanceDetails(
            MailAcceptanceVO mailAcceptanceVO) throws SystemException,
            RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveAcceptanceDetails");
        try {
            return new MailController().saveAcceptanceDetails(mailAcceptanceVO);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (DuplicateDSNException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailDefaultStorageUnitException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param scannedMailbagsToOffload
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public Collection<ScannedMailDetailsVO> offloadScannedMailbags(
            Collection<MailbagVO> scannedMailbagsToOffload)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE,
                "offloadScannedMailbags");
        try {
            return new MailController()
                    .offloadScannedMailbags(scannedMailbagsToOffload);
        } catch (MailbagAlreadyReturnedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReturnNotPossibleException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReassignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightDepartedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * @param offloadVo
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1936
     */
    public Collection<ContainerDetailsVO> offload(OffloadVO offloadVo)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        try {
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            return mailController.offload(offloadVo);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightDepartedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReassignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * @param saveScannedOffloadMails
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void saveScannedOffloadMails(Collection<OffloadVO> OffloadVosForSave)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "saveScannedMails");
        try {
            new MailController().saveScannedOffloadMails(OffloadVosForSave);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightDepartedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReassignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting( MODULE, "saveScannedMails");
    }

    /**
     * @param mailbagsToReturn
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @throws DuplicateMailBagsException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#returnMailbags(java.util.Collection)
     */
    public Collection<ContainerDetailsVO> returnMailbags(
            Collection<MailbagVO> mailbagsToReturn) throws SystemException,
            RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "returnMailbags");

        try {
            return new MailController().returnMailbags(mailbagsToReturn);
        } catch (MailbagAlreadyReturnedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReturnNotPossibleException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReassignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param mailBagVOs
     * @return void
     * @throws RemoteException
     * @throws SystemException
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @author a-5526 This method is used to save mailbag scanned from HHT
     */
    public void saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO)
            throws RemoteException, SystemException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException {
        try {
        new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}
    }

    /**
     * @param flightFilterVO
     * @return Collection<FlightValidationVO>
     * @throws RemoteException
     * @throws SystemException
     * @author a-1936 This method is used to validate the Flight
     */
    public Collection<FlightValidationVO> validateFlight(
            FlightFilterVO flightFilterVO) throws RemoteException,
            SystemException {
        log.entering(MODULE, "isFlightClosedForMailOperations");
        return new MailController().validateFlight(flightFilterVO);
    }

    /**
     * @param flightFilterVO
     * @return Collection<FlightValidationVO>
     * @throws RemoteException
     * @throws SystemException
     * @author a-5160 This method is used to validate Mail Flight
     */
    public Collection<FlightValidationVO> validateMailFlight(
            FlightFilterVO flightFilterVO) throws RemoteException,
            SystemException {
        log.entering(MODULE, "isFlightClosedForMailOperations");
        return new MailController().validateMailFlight(flightFilterVO);
    }

    /**
     * @param operationalFlightVO
     * @return MailAcceptanceVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 The method is used to find the mail acceptance details.
     */
    public MailAcceptanceVO findFlightAcceptanceDetails(
            OperationalFlightVO operationalFlightVO) throws SystemException,
            RemoteException {

        log.entering(MODULE, "findFlightAcceptanceDetails");
        return new MailController()
                .findFlightAcceptanceDetails(operationalFlightVO);

    }

    /**
     * This method is used to get the Location of the Warehouse for acceptmail
     *
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param transactionCodes
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public Map<String, Collection<String>> findWarehouseTransactionLocations(
            LocationEnquiryFilterVO filterVO) throws SystemException,
            RemoteException {
        log.entering(MODULE, "findWarehouseTransactionLocations");
        return new MailController().findWarehouseTransactionLocations(filterVO);

    }

    /**
     * @param companyCode
     * @param airportCode
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to findallthe Warehouses for the Given
     * Airport
     */
    public Collection<WarehouseVO> findAllWarehouses(String companyCode,
                                                     String airportCode) throws SystemException, RemoteException {
        log.entering(MODULE, "findAllWarehouses");
        return new MailController().findAllWarehouses(companyCode, airportCode);
    }

    /**
     * @param containers
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936
     */
    public Collection<ContainerDetailsVO> findMailbagsInContainer(
            Collection<ContainerDetailsVO> containers) throws SystemException,
            RemoteException {
        return new MailController().findMailbagsInContainer(containers);
    }

    /**
     * @param containerVO
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @author a-1936 This method is used to validate the Container whether it
     * can be assigned to a particularFlight or CarrierDestination
     */
    public ContainerVO validateContainer(String airportCode,
                                         ContainerVO containerVO) throws RemoteException, SystemException,
            MailTrackingBusinessException {
        log.entering(MODULE, "validateContainer");
        try {
            return new MailController().validateContainer(airportCode,
                    containerVO);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException uldDefaultsProxyException) {
            throw new MailTrackingBusinessException(uldDefaultsProxyException);
        }
    }

    /**
     * @param operationalFlightVO
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @author a-1936 This method is used to check whether the Flight is closed
     * for Operations
     */
    public boolean isFlightClosedForMailOperations(
            OperationalFlightVO operationalFlightVO) throws RemoteException,
            SystemException {
        log.entering(MODULE, "isFlightClosedForMailOperations");
        return new MailController()
                .isFlightClosedForOperations(operationalFlightVO);
    }

    /**
     * @param companyCode
     * @param code
     * @param description
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 Method for OfficeOfExchangeLOV containing code and
     * description
     */
    public Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO, int pageNumber, int defaultSize)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findOfficeOfExchangeLov");
        return new MailController().findOfficeOfExchangeLov(officeofExchangeVO, pageNumber, defaultSize);
    }

    /**
     * @param companyCode
     * @param code
     * @param description
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 Method for MailSubClassLOV containing code and description
     */
    public Page<MailSubClassVO> findMailSubClassCodeLov(String companyCode,
                                                        String code, String description, int pageNumber, int defaultSize)
            throws SystemException, RemoteException {
        return new MailController().findMailSubClassCodeLov(companyCode, code,
                description, pageNumber, defaultSize);
    }

    /**
     * @param companyCode
     * @param paCode
     * @return PostalAdministrationVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 This method is used to find Postal Administration Code
     * Details
     */
    public PostalAdministrationVO findPACode(String companyCode, String paCode)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findPACode");
        return new MailController().findPACode(companyCode, paCode);
    }

    /**
     * @param mailbagVos
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1936 This method is used to validate the MailBags and the
     * mailTagFormat
     */
    public boolean validateMailBags(Collection<MailbagVO> mailbagVos)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "validateMailBags");
        try {
            return new MailController().validateMailBags(mailbagVos);
        } catch (InvalidMailTagFormatException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param warehouseCode
     * @param locationCode
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to validate the location ..
     */
    public LocationValidationVO validateLocation(String companyCode,
                                                 String airportCode, String warehouseCode, String locationCode)
            throws SystemException, RemoteException {
        log.entering(MODULE, "validateLocation");
        log.entering(MODULE, "validateLocation");
        return new MailController().validateLocation(companyCode, airportCode,
                warehouseCode, locationCode);

    }

    /**
     * @param companyCode
     * @param despatchDetailsVOs
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-3227 - FEB 10, 2009
     */
    public Collection<DespatchDetailsVO> validateConsignmentDetails(
            String companyCode, Collection<DespatchDetailsVO> despatchDetailsVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE,
                "validateConsignmentDetails");
        return new MailController().validateConsignmentDetails(companyCode,
                despatchDetailsVOs);
    }

    /**
     * @param dsnVos
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1936 This method is used to validate the DSNs say DOE and OOE
     */
    public boolean validateDSNs(Collection<DSNVO> dsnVos)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "validateDSNs");
        try {
            return new MailController().validateDSNs(dsnVos);
        } catch (InvalidMailTagFormatException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }


    /**
     * @throws RemoteException
     * @throws SystemException
     */
    public Collection<ContainerVO> findAllULDsInAssignedFlight(
            FlightValidationVO reassignedFlightValidationVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findAllULDsInAssignedFlight");
        return new MailController()
                .findAllULDsInAssignedFlight(reassignedFlightValidationVO);
    }

    /**
     * @param containersToReassign
     * @param toFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#reassignContainers(java.util.Collection,
     * com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     */
    public void reassignContainers(
            Collection<ContainerVO> containersToReassign,
            OperationalFlightVO toFlightVO) throws SystemException,
            RemoteException, MailTrackingBusinessException {
        try {
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            mailController.reassignContainers(containersToReassign,
                    toFlightVO);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailDefaultStorageUnitException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param operationalFlightVO
     * @return MailAcceptanceVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 The method is used to find the mail acceptance details for
     * destination Assigned.
     */
    public MailAcceptanceVO findDestinationAcceptanceDetails(
            OperationalFlightVO operationalFlightVO) throws SystemException,
            RemoteException {
        log.entering(MODULE, "validateLocation");
        return new MailController()
                .findDestinationAcceptanceDetails(operationalFlightVO);

    }

    /**
     * @param companyCode
     * @param ownCarrierCode
     * @param airportCode
     * @return Collection<PartnerCarrierVO>
     * @throws SystemException
     * @throws RemoteException
     * @author a-1876 This method is used to list the PartnerCarriers..
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findAllPartnerCarriers(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     */
    public Collection<PartnerCarrierVO> findAllPartnerCarriers(
            String companyCode, String ownCarrierCode, String airportCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findAllPartnerCarriers");
        return new MailController().findAllPartnerCarriers(companyCode,
                ownCarrierCode, airportCode);
    }

    public Collection<CoTerminusVO> findAllCoTerminusAirports(
            CoTerminusFilterVO filterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findAllCoTerminusAirports");
        return new MailController().findAllCoTerminusAirports(filterVO);
    }

    /*added by A-8149 for ICRD-243386*/
    public Page<MailServiceStandardVO> listServiceStandardDetails(
            MailServiceStandardFilterVO mailServiceStandardFilterVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering( MODULE, "listServiceStandardDetails");
        return new MailController().listServiceStandardDetails(mailServiceStandardFilterVO, pageNumber);
    }

    public Collection<MailRdtMasterVO> findRdtMasterDetails(
            RdtMasterFilterVO filterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findAllCoTerminusAirports");
        return new MailController().findRdtMasterDetails(filterVO);
    }

    /**
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 This method is used to find Preadvice for outbound mail
     * and it gives the details of the ULDs and the receptacles based on
     * CARDIT
     */
    public PreAdviceVO findPreAdvice(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findPreAdvice");
        return new MailController().findPreAdvice(operationalFlightVO);
    }

    /**
     * @param tranferManifestFilterVo
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936
     * This method is used to find the Transfer Manifest for the Different Transactions
     */
    public Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
            throws SystemException, RemoteException {
        return new MailController().findTransferManifest(tranferManifestFilterVo);

    }

    /**
     * @param operationalFlightVo
     * @return
     * @author a-2553
     * Added By Paulson as the  part of  the Air NewZealand CR...
     */
    public Map<String, Object> generateTransferManifestReport(ReportSpec reportSpec)
            throws SystemException, RemoteException {
        return new MailController().generateTransferManifestReport(reportSpec);
    }
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#generateTransferManifestMailbagLevelReport(com.ibsplc.icargo.framework.report.vo.ReportSpec)
 *	Added by 			: A-8061 on 09-Nov-2020
 * 	Used for 	:
 *	Parameters	:	@param reportSpec
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Parameters	:	@throws RemoteException
 */
    public Map<String, Object> generateTransferManifestMailbagLevelReport(ReportSpec reportSpec)
            throws SystemException, RemoteException {
        return new MailController().generateTransferManifestMailbagLevelReport(reportSpec);
    }

    /**
     * @param operationalFlightVo
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to find the containers and its
     * associated DSNs in the Flight.
     */
    public MailManifestVO findContainersInFlightForManifest(
            OperationalFlightVO operationalFlightVo) throws SystemException,
            RemoteException {
        log.entering("MailTracking Defaults Services EJB",
                "findContainersInFlight");
        return new MailController()
                .findContainersInFlightForManifest(operationalFlightVo);
    }

    /**
     * @param containers
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to find out the Mail Bags and
     * Despatches in the Containers for the Manifest..
     */
    public Collection<ContainerDetailsVO> findMailbagsInContainerForManifest(
            Collection<ContainerDetailsVO> containers) throws SystemException,
            RemoteException {
        log.entering("MailTracking Defaults Services EJB",
                "findContainersInFlight");
        return new MailController()
                .findMailbagsInContainerForManifest(containers);
    }

    /**
     * @param companyCode
     * @param airportCode
     * @param isGHA
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     */
    public String findStockHolderForMail(String companyCode,
                                         String airportCode, Boolean isGHA) throws SystemException,
            RemoteException {
        log.entering( MODULE,
                "findStockHolderForMail");
        return new MailController().findStockHolderForMail(companyCode,
                airportCode, isGHA);
    }

    /**
     * @param aWBFilterVO
     * @return AWBDetailVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1883
     */
    public AWBDetailVO findAWBDetails(AWBFilterVO aWBFilterVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "findAWBDetails");
        try {
            return new MailController().findAWBDetails(aWBFilterVO);
        } catch (AttachAWBException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param operationalFlightVO
     * @param mailManifestVO
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @author a-3251 SREEJITH P.C.
     */

    public void closeFlightManifest(OperationalFlightVO operationalFlightVO,
                                    MailManifestVO mailManifestVO) throws SystemException,
            RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "closeFlightAcceptance");
        try {
            new MailController().closeFlightManifest(operationalFlightVO,
                    mailManifestVO);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CloseFlightException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting(MODULE, "closeFlightAcceptance");
    }

    /**
     * This method is used to find the Offload Details for a Flight say at
     * different levels say Containers,DSNS,MailBags
     *
     * @param offloadFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public OffloadVO findOffloadDetails(OffloadFilterVO offloadFilterVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findOffloadDetails");
        return new MailController().findOffloadDetails(offloadFilterVO);

    }

    /**
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to reopen the Flight
     */
    public void reopenFlight(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "reopenFlight");
        new MailController().reopenFlight(operationalFlightVO);
        log.exiting(MODULE, "reopenFlight");
    }

    /**
     * @param operationalFlightVO
     * @param mailAcceptanceVO
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @author a-3251 SREEJITH P.C.
     */

    public void closeFlightAcceptance(OperationalFlightVO operationalFlightVO, MailAcceptanceVO mailAcceptanceVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "closeFlightAcceptance");
        try {

        	MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
        	mailController.closeFlightAcceptance(operationalFlightVO, mailAcceptanceVO);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CloseFlightException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting(MODULE, "closeFlightAcceptance");
    }

    /**
     * @return
     * @throws SystemException
     */
    public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange)
            throws SystemException, RemoteException {
        log.entering("Ram MailTrackingDefaultsServicesEJB", "validateOfficeOfExchange");
        return new MailController().validateOfficeOfExchange(companyCode, officeOfExchange);
    }


    /**
     * @param mailbagEnquiryFilterVO
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to find the mailBags
     */
    public Page<MailbagVO> findMailbags(
            MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findMailbags");
        return new MailController().findMailbags(mailbagEnquiryFilterVO,
                pageNumber);
    }


    /**
     * @param containerVOs
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public TransferManifestVO transferContainers(Collection<ContainerVO>
                                                         containerVOs, OperationalFlightVO operationalFlightVO, String printFlag)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "transferContainers");
        try {
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            return mailController.transferContainers(containerVOs,
                    operationalFlightVO, printFlag);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * @param companyCode
     * @param mailbagId
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 This method is used to find the History of a Mailbag
     */
    public Collection<MailbagHistoryVO> findMailbagHistories(  /*modified by A-8149 for ICRD-248207*/
            String companyCode, String mailBagId, long mailSequenceNumber) throws SystemException,
            RemoteException {
    	
    	 log.entering( MODULE, "findMailbagHistories");     
        return new MailController()
               .findMailbagHistories(companyCode, mailBagId, mailSequenceNumber);
    }

    
    public Collection<MailHistoryRemarksVO> findMailbagNotes(String mailBagId) 
    		throws SystemException,RemoteException {
    	 log.entering( MODULE, "findMailbagHistories");     
        return new MailController().findMailbagNotes(mailBagId);
    }
    /**
     * @param postalAdministrationVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void savePACode(PostalAdministrationVO postalAdministrationVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "savePACode");
        try {
            new MailController().savePACode(postalAdministrationVO);
        } catch (SharedProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * This method is used to save office of Exchange Code
     *
     * @param officeOfExchangeVOs
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void saveOfficeOfExchange(
            Collection<OfficeOfExchangeVO> officeOfExchangeVOs)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "saveOfficeOfExchange");
        try {
            new MailController().saveOfficeOfExchange(officeOfExchangeVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode());
        } catch (OfficeOfExchangeException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }


    /**
     * This method is used to save Mail sub class codes
     *
     * @param mailSubClassVOs
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void saveMailSubClassCodes(Collection<MailSubClassVO> mailSubClassVOs)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "saveMailSubClassCodes");
        try {
            new MailController().saveMailSubClassCodes(mailSubClassVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode());
        } catch (MailSubClassException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     */
    public Map<String, Object> generateMailTag(ReportSpec reportSpec)
            throws SystemException, RemoteException, ReportGenerationException {
        log.entering( MODULE, "generateMailTag");
        return new MailController().generateMailTag(reportSpec);
    }

    /**
     * method added for new search consignment screen
     *
     * @param carditEnquiryFilterVO
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @throws RemoteException
     * @author A-2667
     */
    public Collection<MailbagVO> findConsignmentDetails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
            throws SystemException, MailTrackingBusinessException, RemoteException {

        return new MailController().findConsignmentDetails(carditEnquiryFilterVO, pageNumber);

    }


    /**
     * @param dsnVO
     * @param mode
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-3227 RENO K ABRAHAM
     */
    public Collection<DespatchDetailsVO> findDespatchesOnDSN(DSNVO dsnVO, String mode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findDespatchesOnDSN");
        return new MailController().findDespatchesOnDSN(dsnVO, mode);
    }

    /**
     * @param containerDetailsVOs
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936 This method is used to remove the EmptyULDs(ULDs with no
     * MailBags\Despatches)
     */
    public void unassignEmptyULDs(
            Collection<ContainerDetailsVO> containerDetailsVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "unassignEmptyULDs");
        MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
        mailController.unassignEmptyULDs(containerDetailsVOs);
    }

    /**
     * @param reportSpec
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     */
    public Map generateFindMailbagHistoriesReport(ReportSpec reportSpec)
            throws RemoteException, SystemException, ReportGenerationException {
        log.entering("generateFindMailbagHistoriesReport", "generateFindMailbagHistoriesReport");
        return (new MailController()).generateFindMailbagHistoriesReport(reportSpec);
    }


    /**
     * @param operationalFlightVO
     * @return Collection<ContainerVO>
     * @throws RemoteException
     * @throws SystemException
     * @author a-1936 This method is used to return all the containers assigned
     * to a particularFlight
     */
    public Collection<ContainerVO> findFlightAssignedContainers(
            OperationalFlightVO operationalFlightVO) throws RemoteException,
            SystemException {
        log.entering(MODULE, "findFlightAssignedContainers");
        return new MailController()
                .findFlightAssignedContainers(operationalFlightVO);
    }

    /**
     * @param destinationFilterVO
     * @return Collection<ContainerVO>
     * @throws RemoteException
     * @throws SystemException
     * @author a-1936 This method is used to return all the containers assigned
     * to a particular destination and Carrier
     */
    public Collection<ContainerVO> findDestinationAssignedContainers(
            DestinationFilterVO destinationFilterVO) throws RemoteException,
            SystemException {
        log.entering(MODULE, "findDestinationAssignedContainers");
        return new MailController()
                .findDestinationAssignedContainers(destinationFilterVO);
    }

    /**
     * This method is used to reassign the MailBags
     *
     * @param mailbagsToReassign
     * @param toContainerVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public Collection<ContainerDetailsVO> reassignMailbags(
            Collection<MailbagVO> mailbagsToReassign, ContainerVO toContainerVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        try {
            return new MailController().reassignMailbags(mailbagsToReassign,
                    toContainerVO);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ReassignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * @param companyCode
     * @param countryCode
     * @return Collection<PostalAdministrationVO>
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 This method is used to find Local PAs
     */
    public Collection<PostalAdministrationVO> findLocalPAs(String companyCode,
                                                           String countryCode) throws SystemException, RemoteException {
        log.entering( MODULE, "findLocalPAs");
        return new MailController().findLocalPAs(companyCode, countryCode);
    }

    /**
     * returns data for displaying manifest level
     * Jan 19, 2007, A-1739
     *
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws SystemException
     */
    public Map<String, Object> generateMailManifest(ReportSpec reportSpec)
            throws SystemException, RemoteException {
        return new MailController().generateMailManifest(reportSpec);
    }


    /**
     * @param reportSpec
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     * @author A-3227 Reno K Abraham
     */
    public Map generateFindDamageMailReport(ReportSpec reportSpec)
            throws RemoteException, SystemException, ReportGenerationException {

        log.entering("MailTracking Defaults Services EJB", "generateFindDamageMailReport");
        return new MailController().generateFindDamageMailReport(reportSpec);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateContainer(java.util.Collection)
     */

    /**
     * @param auditVO
     * @throws RemoteException
     * @throws AuditException
     */
    public void audit(AuditVO auditVO) throws AuditException, RemoteException {

    }

    /**
     * @param consignmentDocumentVO
     * @throws SystemException
     */
    public void saveConsignmentDocumentFromManifest(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
            throws SystemException, RemoteException {
        log.entering("Ram MailTrackingDefaultsServicesEJB", "saveConsignmentDocumentFromManifest");
        new DocumentController().saveConsignmentDocumentFromManifest(consignmentDocumentVOs);
    }

    /**
     * @param consignmentFilterVO
     * @return ConsignmentDocumentVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883 This method returns Consignment Details
     */
    public ConsignmentDocumentVO findConsignmentDocumentDetails(
            ConsignmentFilterVO consignmentFilterVO) throws SystemException,
            RemoteException {
        log
                .entering( MODULE,
                        "findConsignmentDocumentDetails");
        return new DocumentController()
                .findConsignmentDocumentDetails(consignmentFilterVO);
    }

    /**
     * @param containerVOs
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @author a-1936
     */
    public void deleteContainers(Collection<ContainerVO> containerVOs)
            throws RemoteException, SystemException,
            MailTrackingBusinessException {
        log.entering(MODULE, "deleteContainers");
        try {
        	MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MAIL_CONTROLLER);
            mailController.deleteContainers(containerVOs);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param companyCode
     * @param officeOfExchange
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public String findPAForOfficeOfExchange(
            String companyCode, String officeOfExchange)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findPAForOfficeOfExchange");
        return new MailController().findPAForOfficeOfExchange(companyCode,
                officeOfExchange);
    }

    /**
     * @param mailbagVOs
     * @throws SystemException
     * @throws RemoteException
     * @author a-1739
     */
    public void saveDamageDetailsForMailbag(Collection<MailbagVO> mailbagVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveDamageDetailsForMailbag");
        new MailController().saveDamageDetailsForMailbag(mailbagVOs);
        log.exiting( MODULE, "saveDamageDetailsForMailbag");
    }

    /**
     * @param companyCode
     * @param mailbagId
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 This method is used to find the Damaged Mailbag Details
     */
    public Collection<DamagedMailbagVO> findMailbagDamages(String companyCode,
                                                           String mailbagId) throws SystemException, RemoteException {
        log.entering( MODULE, "findMailbagDamages");
        return new MailController().findMailbagDamages(companyCode, mailbagId);
    }

    /**
     * @param searchContainerFilterVO
     * @param pageNumber
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @author a-1936 This method is used tom find the containers
     */
    public Page<ContainerVO> findContainers(
            SearchContainerFilterVO searchContainerFilterVO, int pageNumber)
            throws RemoteException, SystemException {
        return new MailController().findContainers(searchContainerFilterVO,
                pageNumber);
    }


    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2553 Paulson Ouseph A
     */
    public Map generateMailStatusReport(ReportSpec reportSpec)
            throws SystemException, RemoteException, ReportGenerationException {

        log.entering("MailTracking Defaults Services EJB", "generateMailStatusReport");
        return new MailController().generateMailStatusReport(reportSpec);

    }


    /**
     * @param companyCode
     * @param subclassCode
     * @return Collection<MailSubClassVO>
     * @throws SystemException
     * @throws RemoteException
     * @author a-2037 This method is used to find all the mail subclass codes
     */
    public Collection<MailSubClassVO> findMailSubClassCodes(String companyCode,
                                                            String subclassCode) throws SystemException, RemoteException {
        log.entering(MODULE, "findMailSubClassCodes");
        return new MailController().findMailSubClassCodes(companyCode,
                subclassCode);
    }

    public Collection<EmbargoDetailsVO> checkEmbargoForMail(
            Collection<ShipmentDetailsVO> shipmentDetailsVos) throws SystemException, RemoteException {
        log.entering(MODULE, "checkEmbargoForMail");
        return new MailController().checkEmbargoForMail(shipmentDetailsVos);

    }

    /**
     * @param companyCode
     * @param officeOfExchange
     * @param pageNumber
     * @return Collection<OfficeOfExchangeVO>
     * @throws SystemException
     * @throws RemoteException
     * @author a-2037 This method is used to find all the mail subclass codes
     */
    public Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
                                                         String officeOfExchange, int pageNumber) throws SystemException,
            RemoteException {
        log.entering(MODULE, "findOfficeOfExchange");
        return new MailController().findOfficeOfExchange(companyCode,
                officeOfExchange, pageNumber);
    }

    /**
     * @param companyCode
     * @param mailboxCode
     * @param mailboxDescName
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-5931 Method for MailBoxIdLovVO containing Code and Description
     */
    public Page<MailBoxIdLovVO> findMailBoxIdLov(String companyCode,
                                                 String mailboxCode, String mailboxDesc, int pageNumber, int defaultSize)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findMailBoxIdLov");
        return new MailController().findMailBoxIdLov(companyCode, mailboxCode, mailboxDesc,
                pageNumber, defaultSize);
    }

    /**
     * @param mailBoxIdVOs
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-5931 Method for saving mailbox ids
     */
    // Commented the method as part of ICRD-153078
    // Uncommented as part of ICRD-234820
    public void saveMailboxIDs(Collection<MailBoxIdLovVO> mailBoxIdVOs) throws SystemException, RemoteException {
        log.entering(MODULE, "saveMailboxIDs");
        new MailController().saveMailboxIDs(mailBoxIdVOs);

    }

    /**
     * a-2553
     *
     * @param carditEnquiryFilterVO,pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCarditMails");
        return new MailController().findCarditMails(carditEnquiryFilterVO, pageNumber);
    }


    /**
     * @param operationalFlightVO
     * @param containers
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     * @throws CapacityBookingProxyException
     * @author a-1936
     */
    public void saveContainers(OperationalFlightVO operationalFlightVO,
                               Collection<ContainerVO> containers) throws RemoteException,
            SystemException, MailTrackingBusinessException {
        try {
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            mailController
                    .saveContainers(operationalFlightVO, containers);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailDefaultStorageUnitException ex) {
            throw new MailTrackingBusinessException(ex);
        }


    }


    /**
     * @param opFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findArrivalDetails(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     */
    public MailArrivalVO findArrivalDetails(MailArrivalFilterVO mailArrivalFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findArrivalDetails");
        return new MailController().findArrivalDetails(mailArrivalFilterVO);
    }

    /**
     * For validating inb flt
     * Oct 6, 2006, a-1739
     *
     * @param flightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateInboundFlight(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     */
    public OperationalFlightVO validateInboundFlight(OperationalFlightVO flightVO)
            throws SystemException, RemoteException {
        return new MailController().validateInboundFlight(flightVO);
    }

    /**
     * This method is used to saveArrivalDetails
     *
     * @param mailArrivalVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public void saveArrivalDetails(MailArrivalVO mailArrivalVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "saveArrivalDetails");
            MailController mailController = (MailController)SpringAdapter.getInstance().getBean("mAilcontroller");
            mailController.saveArrivalDetails(mailArrivalVO);

    }


    /**
     * @param operationalFlightVO
     * @return boolean
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     */
    public boolean isFlightClosedForInboundOperations(OperationalFlightVO
                                                              operationalFlightVO) throws SystemException, RemoteException {
        log.entering( MODULE, "isFlightClosedForInboundOperations");
        return new MailController().isFlightClosedForInboundOperations(operationalFlightVO);
    }


    /**
     * @param mailArrivalVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author A-5991
     */
    public void undoArriveContainer(MailArrivalVO mailArrivalVO) throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveUndoArrivalDetails");
        try {
            new MailController().undoArriveContainer(mailArrivalVO);
        } catch (MailTrackingBusinessException ex) {
            throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1883 This method is used to close the InboundFlight
     */
    public void closeInboundFlight(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "closeInboundFlight");
        try {
            //new MailController().closeInboundFlight(operationalFlightVO);	 //added as part of ICRD-222265
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            mailController.closeInboundFlight(operationalFlightVO);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting( MODULE, "closeInboundFlight");
    }

    /**
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     */
    public Collection<MailDiscrepancyVO> findMailDiscrepancies(
            OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering("MailEJB", "findMailDiscrepancies");
        return new MailController().findMailDiscrepancies(operationalFlightVO);
    }


    /**
     * @param conatinerstoAcquit
     * @throws SystemException
     * @throws RemoteException
     */
    public void autoAcquitContainers(Collection<ContainerDetailsVO> conatinerstoAcquit)
            throws SystemException, RemoteException {
        log.entering(MODULE, "autoAcquitContainers");
        new MailController().autoAcquitContainers(conatinerstoAcquit);
        log.exiting(MODULE, "autoAcquitContainers");
    }


    /**
     * This method fetches the latest Container Assignment
     * irrespective of the PORT to which it is assigned.
     * This to know the current assignment of the Container.
     *
     * @param containerNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public ContainerAssignmentVO findLatestContainerAssignment(String containerNumber)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "findLatestContainerAssignment");
        return new MailController().findLatestContainerAssignment(containerNumber);
    }


    /**
     * Method		:	MailTrackingDefaultsServicesEJB.saveChangeFlightDetails
     * Added by 	:	A-6245
     * Used for 	:
     * Parameters	:	@param mailArrivalVOs
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	void
     */
    public void saveChangeFlightDetails(Collection<MailArrivalVO> mailArrivalVOs) throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveChangeFlightDetails");
      
			new MailController().saveChangeFlightDetails(mailArrivalVOs);
		
        log.exiting( MODULE, "saveChangeFlightDetails");
    }


    /**
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883 This method is used to reopen the InboundFlight
     */
    public void reopenInboundFlight(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "reopenInboundFlight");
        new MailController().reopenInboundFlight(operationalFlightVO);
        log.exiting( MODULE, "reopenInboundFlight");
    }


    /**
     * TODO Purpose
     * Jan 25, 2007, A-1739
     *
     * @param carditEnquiryFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findCarditDetails(com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO)
     */
    public CarditEnquiryVO findCarditDetails(CarditEnquiryFilterVO carditEnquiryFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCarditDetails");
        return new MailController().findCarditDetails(carditEnquiryFilterVO);
    }

    /**
     * Send Resdit manually
     * Feb 9, 2007, A-1739
     *
     * @param carditEnquiryVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#sendResdit(com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO)
     */
    public void sendResdit(CarditEnquiryVO carditEnquiryVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "sendResdit");
        try {
            new MailController().sendResdit(carditEnquiryVO);
            log.exiting( MODULE, "sendResdit");
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param reportSpec
     * @throws SystemException
     * @author A-3251
     */
    public Map generateDailyMailStationReport(ReportSpec reportspec)
            throws RemoteException, SystemException, ReportGenerationException {
        log.entering("generateFindDamageMailReport", "generateFindDamageMailReport");
        return (new MailController().generateDailyMailStationReport(reportspec));
    }

    /**
     * @param companyCode
     * @param paCode
     * @param paName
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-2037 Method for PALov containing PACode and PADescription
     */
    public Page<PostalAdministrationVO> findPALov(String companyCode,
                                                  String paCode, String paName, int pageNumber, int defaultSize)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findPALov");
        return new MailController().findPALov(companyCode, paCode, paName,
                pageNumber, defaultSize);
    }

    /**
     * @param consignmentDocumentVO
     * @return Integer
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1883
     */
    public Integer saveConsignmentDocument(
            ConsignmentDocumentVO consignmentDocumentVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException, DuplicateDSNException {
        log.entering( MODULE, "saveConsignmentDocument");
        Integer consignmentSeqNumber = null;
        try {
            consignmentSeqNumber = new DocumentController()
                    .saveConsignmentDocument(consignmentDocumentVO);
        } catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
            throw new MailTrackingBusinessException(
                    mailbagAlreadyAcceptedException);
        } catch (InvalidMailTagFormatException invalidMailTagFormatException) {
            throw new MailTrackingBusinessException(
                    invalidMailTagFormatException);
        } catch (DuplicateDSNException duplicateDSNException) {
            throw new MailTrackingBusinessException(
                    duplicateDSNException);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        }

        log.exiting( MODULE, "saveConsignmentDocument");
        return consignmentSeqNumber;
    }


    /**
     * This method deletes Consignment document details and its childs
     *
     * @param consignmentDocumentVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-1883
     */
    public void deleteConsignmentDocumentDetails(
            ConsignmentDocumentVO consignmentDocumentVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE,
                "deleteConsignmentDocumentDetails");
        try {
            new DocumentController()
                    .deleteConsignmentDocumentDetails(consignmentDocumentVO);
        } catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
            throw new MailTrackingBusinessException(
                    mailbagAlreadyAcceptedException);
        }
        log.exiting( MODULE,
                "deleteConsignmentDocumentDetails");
    }

    /**
     * @param consignmentFilterVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-2107
     */
    public Collection<MailbagVO> findCartIds(ConsignmentFilterVO consignmentFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCartIds");
        return new MailController().findCartIds(consignmentFilterVO);
    }

    /**
     * @param companyCode
     * @param officeOfExchanges
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-3227  - FEB 18, 2009
     */
    public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode, Collection<String> officeOfExchanges)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCityAndAirportForOE");
        return new MailController().findCityAndAirportForOE(companyCode, officeOfExchanges);
    }

    /**
     * @param companyCode
     * @param airportCode
     * @return
     * @throws SystemException Added for icrd-95515
     * @author A-4810
     */
    public Collection<String> findOfficeOfExchangesForAirport(
            String companyCode, String airportCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCityAndAirportForOE");
        return new MailController().findOfficeOfExchangesForAirport(companyCode, airportCode);
    }

    /**
     * @return
     * @throws SystemException
     */
    public Collection<MailbagVO> findDSNMailbags(DSNVO dsnVO)
            throws SystemException, RemoteException {
        log.entering("Ram MailTrackingDefaultsServicesEJB", "findDSNMailbags");
        return new MailController().findDSNMailbags(dsnVO);
    }

    /**
     * @param despatchDetailsVOs
     * @param mailbagVOs
     * @param containerVO
     * @param toPrint
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @throws InvalidFlightSegmentException
     * @author a-2553
     */
    public TransferManifestVO transferMail(Collection<DespatchDetailsVO> despatchDetailsVOs,
                                           Collection<MailbagVO> mailbagVOs, ContainerVO containerVO, String toPrint)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering("MailTracking Defaults Services EJB", "transferMail");
        try {
            return new MailController().transferMail(despatchDetailsVOs, mailbagVOs, containerVO, toPrint);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailTrackingBusinessException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * @param companyCode
     * @param officeOfExchange
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public PostalAdministrationVO findPADetails(String companyCode,
                                                String officeOfExchange)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findPADetails");
        PostalAdministrationVO postalAdministrationVO = new MailController()
                .findPADetails(companyCode, officeOfExchange);
        log.exiting( MODULE, "findPADetails");
        return postalAdministrationVO;
    }

    /**
     * @param companyCode
     * @param subclass
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author A-3227 Reno K Abraham
     */
    public boolean validateMailSubClass(String companyCode, String subclass)
            throws SystemException, RemoteException {
        log.entering("MailTracking Defaults Services EJB", "validateMailSubClass");
        return new MailController().validateMailSubClass(companyCode, subclass);
    }

    /**
     * @param postalAdministrationDetailsVO
     * @throws SystemException
     * @throws RemoteException *
     * @author A-3251
     */
    public PostalAdministrationDetailsVO validatePoaDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO)
            throws SystemException, RemoteException {
        return new MailController().validatePoaDetails(postalAdministrationDetailsVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#findAllPACodes(com.ibsplc.icargo.business.mailtracking.mra.gpabilling.vo.GenerateInvoiceFilterVO)
     * Added by 			: A-4809 on 08-Jan-2014
     * Used for 	:
     * Parameters	:	@param generateInvoiceFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Collection<PostalAdministrationVO> findAllPACodes(
            GenerateInvoiceFilterVO generateInvoiceFilterVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findAllPACodes");
        return new MailController().findAllPACodes(generateInvoiceFilterVO);
    }
    /**
     *	Overriding Method	:	@see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#auditMailbagsForMRA(java.util.Collection)
     *	Added by 			: a-4809 on Apr 3, 2014
     * 	Used for 	:	to audit mailbags
     *	Parameters	:	@param dsnAuditVOs
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws RemoteException
     */
							/* Commented the method as part of ICRD-153078
							public void auditMailbagsForMRA(Collection<MailbagAuditVO> mailbagAuditVOs)
									throws SystemException, RemoteException {
								log.entering(MODULE, "auditMailbagsForMRA");
								new MailController().auditMailbagsForMRA(mailbagAuditVOs);
								log.exiting(MODULE, "auditMailbagsForMRA");
						    }*/

    /**
     * @throws MailTrackingBusinessException
     * @throws MailMLDBusniessException
     * @throws MailHHTBusniessException
     */
    public Collection<MailScanDetailVO> fetchMailScannedDetails(String companyCode, int uploadCount)
            throws SystemException, RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException {
        log.entering( MODULE, "fetchMailScannedDetails");
        return new MailUploadController().fetchMailScannedDetails(companyCode, uploadCount);
    }

    /**
     * @throws RemoteException
     * @throws SystemException
     */
						/* Commented the method as part of ICRD-153078
						public void updateMailUploadstatus(MailScanDetailVO mailScanDetailVO,String status)
						throws SystemException, RemoteException {
							log.entering( MODULE,"updateMailOperationalstatus");
					 		 new MailUploadController().updateMailUploadstatus(mailScanDetailVO,status);
						}*/
    public void saveMailUploadDetailsFromJob(Collection<MailScanDetailVO> mailScanDetailVOs,
                                             String scanningPort) throws RemoteException, SystemException {
        new MailUploadController().saveMailUploadDetailsFromJob(mailScanDetailVOs, scanningPort);
    }

    /**
     * @param consignmentDocumentVO
     * @return ConsignmentDocumentVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @throws DuplicateDSNException
     * @author A-3227 JUN 24, 2009
     */

    public void saveConsignmentForManifestedDSN(ConsignmentDocumentVO consignmentDocumentVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveConsignmentForManifestedDSN");
        try {
            new DocumentController().saveConsignmentForManifestedDSN(consignmentDocumentVO);
        } catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
            throw new MailTrackingBusinessException(
                    mailbagAlreadyAcceptedException);
        } catch (InvalidMailTagFormatException invalidMailTagFormatException) {
            throw new MailTrackingBusinessException(
                    invalidMailTagFormatException);
        } catch (DuplicateDSNException duplicateDSNException) {
            throw new MailTrackingBusinessException(
                    duplicateDSNException);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        }

        log.exiting( MODULE, "saveConsignmentForManifestedDSN");
    }


    /**
     * @param reportSpec
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     * @author A-3227 Reno K Abraham
     */
    public Map generateMailHandedOverReport(ReportSpec reportSpec)
            throws RemoteException, SystemException, ReportGenerationException {

        log.entering("MailTracking Defaults Services EJB", "generateMailHandedOverReport");
        return new MailController().generateMailHandedOverReport(reportSpec);
    }

    /**
     * Overriding Method	:	@see saveMailUploadDetailsFromMLD(java.util.Collection)
     * Added by 			: A-4803 on 20-Oct-2014
     * Used for 	:   saving mail details from MLD messages
     * Parameters	:	@param mldMasterVOs
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     *
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     */
    public Map<String, Collection<MLDMasterVO>> saveMailUploadDetailsFromMLD(Collection<MLDMasterVO> mldMasterVOs) throws
            RemoteException, SystemException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException {
        log.entering(MODULE, "saveMailUploadDetailsFromMLD");
        try {
        return new MailUploadController().saveMailUploadDetailsFromMLD(mldMasterVOs);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}
    }

    /**
     *
     */
    public void saveMLDConfigurations(
            Collection<MLDConfigurationVO> mLDConfigurationVOs)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "saveMLDConfiguarions");

        new MLDController().saveMLDConfigurations(mLDConfigurationVOs);

    }

    /**
     *
     */
    public Collection<MLDConfigurationVO> findMLDCongfigurations(MLDConfigurationFilterVO mLDConfigurationFilterVO) throws SystemException, RemoteException {
        log.entering(MODULE, "saveMLDConfiguarions");

        return new MLDController().findMLDCongfigurations(mLDConfigurationFilterVO);

    }

    /**
     * @param partnerCarrierVOs
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author A-1936 This method is used to save the PartnerCarriers..
     */
    public void savePartnerCarriers(
            Collection<PartnerCarrierVO> partnerCarrierVOs)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "savePartnerCarriers");
        try {
            new MailController().savePartnerCarriers(partnerCarrierVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        } catch (InvalidPartnerException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    public void saveCoterminusDetails(Collection<CoTerminusVO> coterminusVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "saveCoterminusDetails");
        try {
            new MailController().saveCoterminusDetails(coterminusVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }
    }

    /*added by A-8149 for ICRD-243386*/
    public void saveServiceStandardDetails(Collection<MailServiceStandardVO> mailServiceStandardVOs, Collection<MailServiceStandardVO> mailServiceStandardVOstodelete)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "saveServiceStandardDetails");
        try {
            new MailController().saveServiceStandardDetails(mailServiceStandardVOs, mailServiceStandardVOstodelete);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }
    }

    public void saveRdtMasterDetails(Collection<MailRdtMasterVO> mailRdtMasterVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "saveCoterminusDetails");
        try {
            new MailController().saveRdtMasterDetails(mailRdtMasterVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }
    }

    public Collection<ErrorVO> saveRdtMasterDetailsXls(Collection<MailRdtMasterVO> mailRdtMasterVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveMailServiceLevelDtls");

        return new MailController().saveRdtMasterDetailsXls(mailRdtMasterVOs);


    }

    public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode, String paCode,LocalDate dspDate)
            throws SystemException, RemoteException {
        log.entering(MODULE, "validateCoterminusairports");
        return new MailController().validateCoterminusairports(actualAirport, eventAirport, eventCode, paCode,dspDate);
    }

    /**
     * TODO Purpose
     * Feb 2, 2007, A-1739
     *
     * @param resditConfigVO
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#saveResditConfiguration(com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditConfigurationVO)
     */
    public void saveResditConfiguration(ResditConfigurationVO resditConfigVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveResditConfiguration");
        new MailController().saveResditConfiguration(resditConfigVO);
        log.exiting( MODULE, "saveResditConfiguration");
    }

    /**
     * TODO Purpose
     * Feb 2, 2007, A-1739
     *
     * @param companyCode
     * @param carrierId
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#findResditConfigurationForAirline(java.lang.String, int)
     */
    public ResditConfigurationVO findResditConfigurationForAirline(
            String companyCode, int carrierId)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findResditConfigurationForAirline");
        return new MailController().findResditConfigurationForAirline(
                companyCode, carrierId);
    }


    /**
     * @author a-1936
     */
    public Map<String, Object> generateImportManifestReport(ReportSpec reportSpec)
            throws SystemException, RemoteException {
        return new MailController().generateImportManifestReport(reportSpec);
    }

    /**
     * @param reportSpec
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     * @author A-5526
     */
    public Map generateTransferManifestReportForMail(ReportSpec reportSpec)
            throws RemoteException, SystemException, ReportGenerationException {

        log.entering("MailTracking Defaults Services EJB", "generateTransferManifestReportForMail");
        return new MailController().generateTransferManifestReportForMail(reportSpec);
    }

    /**
     * @param reportSpec
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     * @author A-5526
     */
    public Map generateTransferManifestReportForContainer(ReportSpec reportSpec)
            throws RemoteException, SystemException, ReportGenerationException {
        log.entering("MailTracking Defaults Services EJB", "generateTransferManifestReportForMail");
        return new MailController().generateTransferManifestReportForContainer(reportSpec);
    }

    /**
     * This method does the ULD Acquittal at Non Mechanized port
     *
     * @param operationalFlightVO the operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-3227  RENO K ABRAHAM - 09/09/2009
     */
    public void initiateULDAcquittance(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "initiateULDAcquittance");
        new MailController().initiateULDAcquittance(operationalFlightVO);
        log.exiting( MODULE, "initiateULDAcquittance");
    }

    /**
     * @param mLDConfigurationVOs
     * @throws MailTrackingBusinessException
     * @author A-5526 This method is used to send MLD messages..
     */
    public void triggerMLDMessages(String companyCode,int recordCount) throws SystemException, RemoteException {
        log.entering(MODULE, "saveMLDConfiguarions");
        new MLDController().triggerMLDMessages(companyCode,recordCount);

    }

    /**
     *
     */
    public void closeInboundFlightForMailOperation(String companyCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "closeInboundFlightForMailOperation");
        new MailController().closeInboundFlightForMailOperation(companyCode);
        log.exiting( MODULE, "closeInboundFlightForMailOperation");
    }

	



							
    public void closeInboundFlightAfterULDAcquitalForProxy(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "closeInboundFlightForMailOperation");
        new MailController().closeInboundFlightAfterULDAcquitalForProxy(operationalFlightVO);
        log.exiting( MODULE, "closeInboundFlightForMailOperation");
    }

    public void closeInboundFlightAfterULDAcquital(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "closeInboundFlightForMailOperation");
        new MailController().closeInboundFlightAfterULDAcquital(operationalFlightVO);
        log.exiting( MODULE, "closeInboundFlightForMailOperation");
    }

    /**
     * @author A-1885
     */
    public void closeFlightForMailOperation(String companyCode, int time, String airportCode)
            throws SystemException, RemoteException {
        log.entering(MODULE, "closeFlightForMailOperation");
        MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
        mailController.closeFlightForMailOperation(companyCode, time, airportCode);
    }

    /**
     * @throws SystemException
     * @throws RemoteException
     * @author A-5166
     * Added for ICRD-36146 on 07-Mar-2013
     */
    public void initiateArrivalForFlights(ArriveAndImportMailVO arriveAndImportMailVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "initiateArrivalForFlights");
        new MailController().initiateArrivalForFlights(arriveAndImportMailVO);
        log.exiting(MODULE, "initiateArrivalForFlights");
    }

    /**
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     */
    public Map<String, Object> generateConsignmentReport(ReportSpec reportSpec)
            throws SystemException, RemoteException, ReportGenerationException {
        log.entering( MODULE, "generateConsignmentReport");
        return new DocumentController().generateConsignmentReport(reportSpec);
    }

    /**
     *
     */
    public Page<DespatchDetailsVO> findDSNs(DSNEnquiryFilterVO dSNEnquiryFilterVO, int pageNumber) throws SystemException, RemoteException {
        log.entering( MODULE, "findDSNs");
        return new MailController().findDSNs(dSNEnquiryFilterVO, pageNumber);
    }

    /**
     * This method is used to saveCarditMessages
     *
     * @param ediInterchangeVO
     * @throws RemoteException
     * @throws SystemException
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public Collection<ErrorVO> saveCarditMessages(EDIInterchangeVO ediInterchangeVO)
            throws RemoteException, SystemException, MailTrackingBusinessException, IllegalAccessException, InvocationTargetException {
        log.entering(MODULE, "saveCarditMessages");
        Collection<ErrorVO> errors = new ArrayList<>();
        try {
        	errors= new MailController().saveCarditMessages(ediInterchangeVO);
        } catch (DuplicateMailBagsException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting(MODULE, "saveCarditMessages");
        return errors;
    }

    /**
     * TODO Purpose
     * Oct 9, 2006, a-1739
     *
     * @param mailbags
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#validateScannedMailbagDetails(java.util.Collection)
     */
    public Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> validateScannedMailbagDetails(
            Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> mailbags)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "validateScannedMailbagDetails");
        try {
            return new MailController().validateScannedMailbagDetails(mailbags);
        } catch (InvalidMailTagFormatException exception) {
            throw new MailTrackingBusinessException(exception);
        }
    }

    /**
     * This method invokes the resdit reciever procedure
     *
     * @param companyCode
     * @throws SystemException
     * @throws RemoteException
     */
    public void invokeResditReceiver(String companyCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "invokeResditReceiver");
        new MailController().invokeResditReceiver(companyCode);
        log.exiting( MODULE, "invokeResditReceiver");
    }

    /**
     * Starts Resdit processing
     *
     * @param companyCode
     * @return Collection<ResditEventVO>
     * @throws SystemException
     * @throws RemoteException
     */
    public Collection<ResditEventVO> checkForResditEvents(String companyCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "invokeResditReceiver");
        return new ResditController().checkForResditEvents(companyCode);
    }


    /**
     * method: findMailbagsforFlightSegments
     *
     * @param cmpcod
     * @param carierid
     * @param fltnum
     * @param fltseqnum
     * @param segments
     * @return boolean
     * @throws SystemException
     * @throws RemoteException
     * @author A-5249
     * to change the assigned flight status to TBA if mailbag present
     */
    public boolean findMailbagsforFlightSegments(String cmpcod, int carierid, String fltnum,
                                                 long fltseqnum, Collection<FlightSegmentVO> segments, String cancellation) throws SystemException, RemoteException {
        OperationalFlightVO flightVO = new OperationalFlightVO();
        flightVO.setCompanyCode(cmpcod);
        flightVO.setFlightNumber(fltnum);
        flightVO.setCarrierId(carierid);
        flightVO.setFlightSequenceNumber(fltseqnum);
        return new MailController().findMailbagsforFlightSegments(flightVO, segments, cancellation);
    }

    /**
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-1936 This method is used to find the MailBags Accepted to a
     * ParticularFlight and Flag the Uplifted Resdits for the Same
     */
    public void flagUpliftedResditForMailbags(
            Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException,
            RemoteException {
        log.entering( MODULE, "flagUpliftedResditForMailbags");
        //Modified by bug ICRD-154762 by A-5526
        new MailController().flagUpliftedResditForMailbags(operationalFlightVOs);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#flagTransportCompletedResditForMailbags(java.util.Collection)
     * Added by 			:
     * Used for 	:
     * Parameters	:	@param operationalFlightVOs
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public void flagTransportCompletedResditForMailbags(
            Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException,
            RemoteException {
        log.entering( MODULE, "flagTransportCompletedResditForMailbags");
        new MailController().flagTransportCompletedResditForMailbags(operationalFlightVOs);
    }

    public Map<String, ContainerAssignmentVO>
    findContainerAssignments(Collection<ContainerVO> containers)
            throws SystemException, RemoteException {
        return new MailController().findContainerAssignments(containers);
    }

    /**
     * @param controlReferenceNumber
     * @param sendDate
     * @param fileName
     * @throws RemoteException
     * @throws SystemException
     * @author A-2572
     */
    public void updateResditSendStatus(ResditMessageVO resditMessageVO)
            throws RemoteException, SystemException {
        log.entering(MODULE, "updateResditSendStatus");
        new ResditController().updateResditSendStatus(resditMessageVO);
        log.exiting(MODULE, "updateResditSendStatus");
    }

    /**
     * @param controllerBean
     * @return
     * @throws SystemException
     */
    public Object getController(String controllerBean) throws SystemException {
        Object controllerobj = null;
        try {
            controllerobj = (MailUploadController) SpringAdapter.getInstance().getBean(controllerBean);
        } catch (SystemException e) {
            controllerobj = (MailUploadController) SpringAdapter.getInstance().getBean("mailUploadcontroller");
        } catch (Exception e) {
            controllerobj = (MailUploadController) SpringAdapter.getInstance().getBean("mailUploadcontroller");
        }
        return controllerobj;
    }

    /**
     * @throws TemplateRenderingException 
     *
     */
    public Collection<ErrorVO> handleMRDMessage(MailMRDVO messageVO)
            throws RemoteException, SystemException, MailHHTBusniessException, MailTrackingBusinessException, TemplateRenderingException {
        log.entering( MODULE, "handleMRDMessage");
        String controllerBeanString = "-mailUploadcontroller";
        MailUploadController mailUploadController = (MailUploadController) (getController(messageVO.getCompanyCode().concat(controllerBeanString)));
        try {
        return mailUploadController.handleMRDMessage(messageVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}
    }

    /**
     * @param resditEvents
     * @throws SystemException
     * @throws RemoteException
     */
    public void buildResdit(Collection<ResditEventVO> resditEvents)
            throws SystemException, RemoteException {
        log.entering( MODULE, "buildResdit");
        new ResditController().buildResdit(resditEvents);
        log.exiting( MODULE, "buildResdit");
    }

    public void resolveTransaction(String companyCode, String txnId, String remarks)
            throws RemoteException, SystemException {
        log.entering("AdminMonitoringServicesEJB", "resolveTransaction");
        new MailController().resolveTransaction(companyCode, txnId, remarks);
    }

    /**
     * @param reportSpec
     * @return Map<String   ,   Object>
     * @throws SystemException
     * @throws RemoteException
     * @throws ReportGenerationException
     * @author A-5526 This method is used to generateConsignmentReports for each types.Added as part of CRQ ICRD-103713
     */
    public Map<String, Object> generateConsignmentReports(ReportSpec reportSpec)
            throws SystemException, RemoteException, ReportGenerationException {
        log.entering( MODULE, "generateConsignmentReports");
        return new DocumentController().generateConsignmentReports(reportSpec);
    }

    /**
     * @param documentFilterVO
     * @return DocumentValidationVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     * @throws MailTrackingBusinessException 
     */
    public DocumentValidationVO validateDocumentInStock(
            DocumentFilterVO documentFilterVO) throws SystemException,
            RemoteException {
        log.entering( MODULE, "validateDocumentInStock");
        return new MailController().validateDocumentInStock(documentFilterVO);
    }

    /**
     * @param aWBDetailVO
     * @param containerDetailsVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     */
    public void attachAWBDetails(AWBDetailVO aWBDetailVO,
                                 ContainerDetailsVO containerDetailsVO) throws SystemException,
            RemoteException, PersistenceException {
        log.entering( MODULE, "attachAWBDetails");
        new MailController().attachAWBDetails(aWBDetailVO, containerDetailsVO);
        log.exiting( MODULE, "attachAWBDetails");
    }

    /**
     * This method deletes document from stock
     *
     * @param documentFilterVO
     * @throws SystemException
     * @throws RemoteException
     * @author a-1883
     */
    public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "deleteDocumentFromStock");
        try {
            new MailController().deleteDocumentFromStock(documentFilterVO);
        } catch (StockcontrolDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        }
        log.exiting( MODULE, "deleteDocumentFromStock");
    }

    /**
     * This method used to attach AWB details to all DSNs .
     * if they are not attached to any AWB, creates new a Shipment
     * and attach AWB details to all.
     *
     * @param containerDetailsVOs
     * @param operationalFlightVO
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws MailTrackingBusinessException
     */
    public Collection<ContainerDetailsVO> autoAttachAWBDetails(
            Collection<ContainerDetailsVO> containerDetailsVOs,
            OperationalFlightVO operationalFlightVO)
            throws RemoteException, SystemException,
            MailTrackingBusinessException {
        log.entering("MailEJB", "autoAttachAWBDetails");
        Collection<ContainerDetailsVO> containerVOs = null;
        containerVOs = new MailController().
		        autoAttachAWBDetails(containerDetailsVOs, operationalFlightVO);
        log.exiting("MailEJB", "autoAttachAWBDetails");
        return containerVOs;
    }

    /**
     * @param ContainerVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author
     */
    public void updateActualWeightForMailULD(ContainerVO containerVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "updateActualWeightForMailULD");
        MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
        mailController.updateActualWeightForMailULD(containerVO);
        log.exiting(MODULE, "updateActualWeightForMailULD");
    }

    /**
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @author A-1885
     */

    public List <MailUploadVO>
    performMailOperationForGHA(Collection<MailWebserviceVO> webServicesVos
            , String scanningPort)
            throws SystemException, RemoteException, MailHHTBusniessException, MailTrackingBusinessException {
        log.entering(MODULE, "performMailOperationForGHA");
        return  new MailUploadController().performMailOperationForGHA(webServicesVos,
                scanningPort);
    }

    /**
     * @param containers
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @author a-1936
     * This method is used to find out the Mail Bags and Despatches  in the Containers for the Manifest..
     */
    public Collection<ContainerDetailsVO> findMailbagsInContainerForImportManifest(Collection<ContainerDetailsVO> containers)
            throws SystemException, RemoteException {
        log.entering("MailTracking Defaults Services EJB", "findContainersInFlight");
        return new MailController().findMailbagsInContainerForImportManifest(containers);
    }

    /**
     * @param searchContainerFilterVO
     * @return Page<MailOnHandDetailsVO>
     * @throws RemoteException
     * @throws SystemException
     * @author a-6371 This method is used to list the mail ON HAND LIST details
     */
    public Page<MailOnHandDetailsVO> findMailOnHandDetails(
            SearchContainerFilterVO searchContainerFilterVO, int pageNumber) throws RemoteException,
            SystemException {
        log.entering(MODULE, "findMailOnHandDetails");
        log.entering("welcome", "welcomefindMailOnHandDetails");
        return new MailController().findMailOnHandDetails(searchContainerFilterVO, pageNumber);
    }

    /**
     * @throws TemplateRenderingException 
     *
     */
    public Collection<ErrorVO> handleMRDHO22Message(MailMRDVO messageVO)
            throws RemoteException, SystemException, MailHHTBusniessException, MailTrackingBusinessException, TemplateRenderingException {
        log.entering( MODULE, "handleMRDHO22Message");
        String controllerBeanString = "-mailUploadcontroller";
        MailUploadController mailUploadController = (MailUploadController) (getController(messageVO.getCompanyCode().concat(controllerBeanString)));
        try {
        return mailUploadController.handleMRDHO22Message(messageVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}
    }

    public void saveAllValidMailBags(Collection<ScannedMailDetailsVO> validScannedMailVOs)
            throws SystemException, RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException {
        log.entering( MODULE, "clearDSNAcceptanceTemp");
        Transaction txn = null;
        try {
            TransactionProvider tm = PersistenceController
                    .getTransactionProvider();
            txn = tm.getNewTransaction(false);
            txn.commit();
            new MailUploadController().saveAllValidMailBags(validScannedMailVOs);

        } catch (MailHHTBusniessException t) {
            if (txn != null) {
                txn.rollback();
            }
            throw t;
        } catch (Exception t) {
            if (txn != null) {
                txn.rollback();
            }
        }


        log.exiting( MODULE, "clearDSNAcceptanceTemp");
    }

    public Collection<MailUploadVO> createMailScanVOSForErrorStamping(
            Collection<MailWebserviceVO> mailWebserviceVOs, String scannedPort, StringBuilder errorString, String errorFromMapping) throws SystemException {
        log.entering(MODULE, "createMailScanVOS");
        return new MailUploadController().createMailScanVOSForErrorStamping(mailWebserviceVOs, scannedPort, errorString, errorFromMapping);
    }

    /**
     * TODO Purpose
     * Jan 30, 2007, A-1739
     *
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @see com.ibsplc.icargo.business.mailtracking.defaults.MailTrackingDefaultsBI#findMailAWBDetails(com.ibsplc.icargo.business.mailtracking.defaults.vo.OperationalFlightVO)
     */
    public MailManifestVO findMailAWBDetails(
            OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findMailAWBDetails");
        return new MailController().findMailAWBDetails(operationalFlightVO);
    }


    /**
     * Called from operations module to close Export side flight for mail on closing Cargo operations
     *
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @throws CloseFlightException
     * @throws ULDDefaultsProxyException
     */
    public void closeMailExportFlight(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "closeMailExportFlight");
        try {
            new MailController().closeMailExportFlight(operationalFlightVO);
        } catch (ULDDefaultsProxyException e) {
            throw new MailTrackingBusinessException(e);
        } catch (CloseFlightException e) {
            throw new MailTrackingBusinessException(e);
        }
        log.exiting( MODULE, "closeMailExportFlight");
    }

    /**
     * Called from operations module to close import side flight for mail on closing Cargo operations
     *
     * @param operationalFlightVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws CloseFlightException
     * @throws ULDDefaultsProxyException
     * @throws MailTrackingBusinessException
     */
    public void closeMailImportFlight(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "closeMailImportFlight");
        try {
            new MailController().closeMailImportFlight(operationalFlightVO);
        } catch (ULDDefaultsProxyException e) {
            throw new MailTrackingBusinessException(e);
        } catch (CloseFlightException e) {
            throw new MailTrackingBusinessException(e);
        }
        log.exiting( MODULE, "closeMailImportFlight");
    }

    /**
     * Called from shared module to extract data coming in excel to the table SHRGENEXTTAB
     *
     * @param fileUploadFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public String processMailOperationFromFile(
            FileUploadFilterVO fileUploadFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "processMailOperationFromFile");
        return new MailUploadController().processMailOperationFromFile(fileUploadFilterVO);
    }

    /**
     * fetchDataForOfflineUpload
     *
     * @param companyCode
     * @param fileType
     * @return
     * @throws SystemException
     */
    public Collection<MailUploadVO> fetchDataForOfflineUpload(
            String companyCode, String fileType)
            throws SystemException, RemoteException {
        log.entering( MODULE, "fetchDataForOfflineUpload");
        return new MailUploadController().fetchDataForOfflineUpload(companyCode, fileType);
    }

    /**
     * Added as part of CRQ ICRD-204806
     * removeDataFromTempTable
     *
     * @param fileUploadFilterVO
     * @throws SystemException
     */
    public void removeDataFromTempTable(
            FileUploadFilterVO fileUploadFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "removeDataFromTempTable");
        new MailUploadController().removeDataFromTempTable(fileUploadFilterVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findOneTimeDescription(java.lang.String)
     * Added by 			: A-6991 on 13-Jul-2017
     * Used for 	:         ICRD-208718
     * Parameters	:	@param companyCode
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
            String companyCode, String oneTimeCode) throws SystemException, RemoteException {
        log.entering( MODULE, "findOneTimeDescription");
        return new MailController().findOneTimeDescription(companyCode, oneTimeCode);
    }

    /**
     * Method		:	MailTrackingDefaultsBI.validateULDsForOperation
     * Added by 	:	A-7794 on 25-Sept-2017
     * Used for 	:   ICRD-223303
     *
     * @param flightDetailsVo
     * @return
     * @throws SystemException
     * @throws ULDDefaultsProxyException
     */
    public void validateULDsForOperation(FlightDetailsVO flightDetailsVo) throws SystemException, RemoteException {
        log.entering( MODULE, "validateULDsForOperation");
        new MailController().validateULDsForOperation(flightDetailsVo);
    }

    /**
     * @param mailAuditFilterVO
     * @return Collection<AuditDetailsVO>
     * @throws SystemException
     * @throws RemoteException ICRD-229934
     * @author a-7794 This method is used to fetch Audit details
     */
    public Collection<AuditDetailsVO> findCONAuditDetails(MailAuditFilterVO mailAuditFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findCONAuditDetails");
        return new MailController().findCONAuditDetails(mailAuditFilterVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findULDsInAssignedFlight(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     * Added by 			: A-6991 on 20-Nov-2017
     * Used for 	:       ICRD-77772
     * Parameters	:	@param operationalFlightVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Collection<ContainerVO> findULDsInAssignedFlight(OperationalFlightVO
                                                                    operationalFlightVO) throws SystemException, RemoteException {
        log.entering( MODULE, "isFlightClosedForInboundOperations");
        return new MailController().findULDsInAssignedFlight(operationalFlightVO);
    }


    /**
     * Find mail master details for mailbag
     * Method		:	MailTrackingDefaultsServicesEJB.findMailDetailsForMailTag
     * Added by 	:	a-6245 on 07-Jun-2017
     * Used for 	:
     * Parameters	:	@param companyCode
     * Parameters	:	@param mailId
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	MailInConsignmentVO
     */
    public MailbagVO findMailDetailsForMailTag(
            String companyCode, String mailId)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findMailDetailsForMailTag");
        return new MailController().findMailDetailsForMailTag(companyCode, mailId);
    }

    /**
     * Find mail id and other master from master
     * Method		:	MailTrackingDefaultsServicesEJB.findMailbagIdForMailTag
     * Added by 	:	a-6245 on 22-Jun-2017
     * Used for 	:
     * Parameters	:	@param mailbagVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	MailbagVO
     */
    public MailbagVO findMailbagIdForMailTag(
            MailbagVO mailbagVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findMailbagIdForMailTag");
        return new MailController().findMailbagIdForMailTag(mailbagVO);
    }


    /**
     *
     */
    public Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(MailAuditHistoryFilterVO mailAuditHistoryFilterVO) throws SystemException, RemoteException {
        return new MailController().findMailAuditHistoryDetails(mailAuditHistoryFilterVO);
    }

    /**
     *
     */
    public Collection<MailbagHistoryVO> findMailStatusDetails(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) throws SystemException, RemoteException {
        return new MailController().findMailStatusDetails(mailbagEnquiryFilterVO);
    }

    /**
     *
     */
    public HashMap<String, String> findAuditTransactionCodes(
            Collection<String> entities, boolean b, String companyCode) throws SystemException, RemoteException {
        return new MailController().findAuditTransactionCodes(entities, b, companyCode);
    }

    public HashMap<String, Collection<FlightValidationVO>>
    validateFlightsForAirport(Collection<FlightFilterVO> flightFilterVOs)
            throws SystemException, RemoteException {

        return new MailController().validateFlightsForAirport(flightFilterVOs);
    }


    public void closeFlight(
            OperationalFlightVO fltVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "cargoFlightStatus");

        try {
            new MailController().closeFlight(fltVO);

        } catch (CloseFlightException ex) {
            log.log(Log.FINE, ex.getMessage());
        } catch (ULDDefaultsProxyException ex) {
            log.log(Log.FINE, ex.getMessage());
        }

    }

    /**
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-5526 This method is used to find the MailBags Accepted to a
     * ParticularFlight and Flag MLD-UPL for the Same
     */
    public void flagMLDForUpliftedMailbags(
            Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException,
            RemoteException {
        log.entering( MODULE, "flagMLDForUpliftedMailbags");

        new MLDController().flagMLDForUpliftedMailbagsForATDCapture(operationalFlightVOs);
    }

    /**
     * @param containerDetailsVO
     * @throws SystemException
     * @throws RemoteException
     * @throws PersistenceException
     * @throws BusinessDelegateException
     * @author A-7871
     * for ICRD-257316
     */
    public int findMailbagcountInContainer(
            ContainerVO containerVO) throws RemoteException, SystemException, PersistenceException {
        log.entering( MODULE, "findMailbagcountInContainer");

        return new MailController().findMailbagcountInContainer(containerVO);
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.findOfficeOfExchangeForPA
     * Added by 	:	a-6245 on 10-Jul-2017
     * Used for 	:
     * Parameters	:	@param companyCode
     * Parameters	:	@param paCode
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	Map<String,String>
     */
    public Map<String, String> findOfficeOfExchangeForPA(String companyCode,
                                                         String paCode) throws SystemException, RemoteException {
        log.entering( MODULE, "findOfficeOfExchangeForAirports");
        return new MailController().findOfficeOfExchangeForPA(companyCode, paCode);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#performMailAWBTransactions(com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO, java.lang.String)
     * Added by 			: a-7779 on 31-Aug-2017
     * Used for 	:
     * Parameters	:	@param mailFlightSummaryVO
     * Parameters	:	@param eventCode
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO, String eventCode)
            throws SystemException, RemoteException {
        log.entering( MODULE, "performMailAWBTransactions");
        ((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).performMailAWBTransactions(mailFlightSummaryVO, eventCode);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#releasingMailsForULDAcquittance(com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO, com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     * Added by 			: A-5219 on 18-Aug-2018
     * Used for 	:
     * Parameters	:	@param mailArrivalVO
     * Parameters	:	@param operationalFlightVO
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public void releasingMailsForULDAcquittance(
            MailArrivalVO mailArrivalVO,
            OperationalFlightVO operationalFlightVO)
            throws SystemException,
            RemoteException {
        try {
            new MailController().releasingMailsForULDAcquittance(mailArrivalVO, operationalFlightVO);
        } catch (
                InvalidFlightSegmentException
                        | CapacityBookingProxyException
                        | MailBookingException
                        | ContainerAssignmentException
                        | DuplicateMailBagsException
                        | MailbagIncorrectlyDeliveredException
                        | FlightClosedException
                        | ULDDefaultsProxyException                  
                        | MailTrackingBusinessException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.findAgentCodeForPA
     * Added by 	:	U-1267 on 07-Nov-2017
     * Used for 	:	ICRD-211205
     * Parameters	:	@param companyCode
     * Parameters	:	@param paCode
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	String
     */
    public String findAgentCodeForPA(String companyCode,
                                     String paCode) throws SystemException, RemoteException {
        log.entering( MODULE, "findAgentCodeForPA");
        return new MailController().findAgentCodeForPA(companyCode, paCode);

    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.findNextDocumentNumber
     * Added by 	:	U-1267 on 09-Nov-2017
     * Used for 	:	ICRD-211205
     * Parameters	:	@param documnetFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws MailTrackingBusinessException
     * Return type	: 	DocumentValidationVO
     */
    public DocumentValidationVO findNextDocumentNumber(
            DocumentFilterVO documnetFilterVO) throws RemoteException,
            SystemException, MailTrackingBusinessException {
        log.entering( MODULE,
                "findNextDocumentNumber");
        try {
            return new MailController()
                    .findNextDocumentNumber(documnetFilterVO);
        } catch (StockcontrolDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        }
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.detachAWBDetails
     * Added by 	:	U-1267 on 09-Nov-2017
     * Used for 	:	ICRD-211205
     * Parameters	:	@param containerDetailsVO
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     * Return type	: 	void
     */
    public void detachAWBDetails(ContainerDetailsVO containerDetailsVO)
            throws RemoteException, SystemException {
        log.entering( MODULE, "detachAWBDetails");
        new MailController().detachAWBDetails(containerDetailsVO);
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.transferContainersAtExport
     * Added by 	:	A-7371 on 05-Jan-2018
     * Used for 	:	ICRD-133987
     *
     * @param containerVOs
     * @param operationalFlightVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     */
    public TransferManifestVO transferContainersAtExport(Collection<ContainerVO>
                                                                 containerVOs, OperationalFlightVO operationalFlightVO, String printFlag)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "transferContainers");
        try {
            return new MailController().transferContainersAtExport(containerVOs,
                    operationalFlightVO, printFlag);
        } catch (ContainerAssignmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (ULDDefaultsProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailDefaultStorageUnitException ex){
        	throw new MailTrackingBusinessException(ex);
        }catch (FlightClosedException ex){
        	throw new MailTrackingBusinessException(ex);
        }

    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.transferContainersAtExport
     * Added by 	:	A-7371 on 05-Jan-2018
     * Used for 	:	ICRD-133987
     *
     * @param despatchDetailsVOs
     * @param mailbagVOs
     * @param containerVO
     * @param toPrint
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @throws InvalidFlightSegmentException
     */
    public TransferManifestVO transferMailAtExport(
            Collection<MailbagVO> mailbagVOs, ContainerVO containerVO, String toPrint)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering("MailTracking Defaults Services EJB", "transferMail");
        try {
            return new MailController().transferMailAtExport(mailbagVOs, containerVO, toPrint);
        } catch (InvalidFlightSegmentException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (CapacityBookingProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailBookingException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (MailTrackingBusinessException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (FlightClosedException ex){
        	throw new MailTrackingBusinessException(ex);
        }
        
    }

    /**
     * @param reportPublishJobVO
     * @throws RemoteException
     * @throws SystemException
     * @author A-7540
     */
    public void generateResditPublishReport(String companyCode, String paCode, int days)
            throws RemoteException, SystemException, ProxyException {
        log.entering( MODULE, "generateResditPublishReport");
        new MailController().generateResditPublishReport(companyCode, paCode, days);
    }


    /**
     * a-8061
     *
     * @param carditEnquiryFilterVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    public String[] findGrandTotals(CarditEnquiryFilterVO carditEnquiryFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findGrandTotals");
        return new MailController().findGrandTotals(carditEnquiryFilterVO);
    }


    @Override
    public void updateGHTformailbags(
            Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException, RemoteException, ProxyException {
        log.entering( MODULE, "updateGHTformailbags");


        try {
            new MailController().updateGHTformailbags(operationalFlightVOs);
        } catch (FinderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // TODO Auto-generated catch block


    }

    /**
     * a-6986
     *
     * @param reportSpec
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throwsReportGenerationException
     */
    public Map<String, Object> generateAV7Report(ReportSpec reportSpec)
            throws SystemException, RemoteException, ReportGenerationException {
        log.entering( MODULE, "generateConsignmentReport");
        return new DocumentController().generateAV7Report(reportSpec);

    }

    /**
     * @param mailServiceLevelVOs
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelVO> mailServiceLevelVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveMailServiceLevelDtls");

        return new MailController().saveMailServiceLevelDtls(mailServiceLevelVOs);


    }

    public Collection<USPSPostalCalendarVO> listPostalCalendarDetails(
            USPSPostalCalendarFilterVO listPostalCalendarDetails)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveMailServiceLevelDtls");
        return new MailController().listPostalCalendarDetails(listPostalCalendarDetails);
    }

    public void savePostalCalendar(
            Collection<USPSPostalCalendarVO> uSPSPostalCalendarVOs)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering(MODULE, "savePostalCalendar");
        try {
        	MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
        	mailController.savePostalCalendar(uSPSPostalCalendarVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }
    }

    /**
     * @author A-7540
     */
    public ScannedMailDetailsVO doLATValidation(Collection<MailbagVO> newMailbgVOs, boolean isScanned)
            throws SystemException, RemoteException, MailHHTBusniessException {
        log.entering( MODULE, "doLATValidation");

        return new MailController().doLATValidation(newMailbgVOs, isScanned);
    }

    /**
     * @param MailHandoverVOs
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public void saveMailHandoverDetails(Collection<MailHandoverVO> MailHandoverVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveMailHandoverDetails");

        new MailController().saveMailHandoverDetails(MailHandoverVOs);


    }

    /**
     * @param mailHandoverFilterVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public Page<MailHandoverVO> findMailHandoverDetails(MailHandoverFilterVO mailHandoverFilterVO, int pageNumber)
            throws SystemException, RemoteException {
        return new MailController().findMailHandoverDetails(mailHandoverFilterVO, pageNumber);
    }

    /**
     * @param mailBagVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @throws MailTrackingBusinessException
     * @throws PersistenceException
     * @author A-8236
     */
    public ScannedMailDetailsVO saveMailUploadDetailsForAndroid(
            MailUploadVO mailBagVO, String scanningPort)
            throws RemoteException, SystemException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException, PersistenceException {
    	return  ((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).saveMailUploadDetailsForAndroid(mailBagVO, scanningPort);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#performMailAWBTransactions(com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO, java.lang.String)
     * Added by 			: a-7871 on 13-Jul-2018
     * Used for 	: ICRD-227884
     * Parameters	:	@param mailUploadVO
     *
     * @throws SystemException
     */
    public ScannedMailDetailsVO validateMailBagDetails(MailUploadVO mailUploadVO) throws SystemException, RemoteException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException {
        log.entering( MODULE, "validateMailBagDetails");
        return ((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).validateMailBagDetails(mailUploadVO);
    }


    /**
     * @param gpaContractVOs
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public void saveContractDetails(Collection<GPAContractVO> gpaContractVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveMailHandoverDetails");
        try {
            new MailController().saveContractDetails(gpaContractVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }
    }

    /**
     * @param gpaContractFilterVO
     * @param pageNumber
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public Collection<GPAContractVO> listContractDetails(
            GPAContractFilterVO gpaContractFilterVO)
            throws SystemException, RemoteException {
        return new MailController().listContractDetails(gpaContractFilterVO);
    }

    public Page<MailAcceptanceVO> findOutboundFlightsDetails(OperationalFlightVO operationalFlightVO, int pageNumber) throws SystemException, RemoteException {
        return new MailController().findOutboundFlightsDetails(operationalFlightVO, pageNumber);
    }

    public Page<ContainerDetailsVO> getAcceptedContainers(
            OperationalFlightVO operationalFlightVO, int pageNumber) throws SystemException,
            RemoteException {
        log.entering(MODULE, "findFlightDetails");
        return new MailController()
                .getContainersinFlight(operationalFlightVO, pageNumber);
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.listFlightDetails
     * Added by 	:	A-8164 on 05-Nov-2018
     * Used for 	:
     * Parameters	:	@param mailArrivalVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws PersistenceException
     * Return type	: 	Collection<MailArrivalVO>
     */
    public Page<MailArrivalVO> listFlightDetails(MailArrivalVO mailArrivalVO)
            throws SystemException, RemoteException, PersistenceException {
        return new MailController().listFlightDetails(mailArrivalVO);
    }

    public Page<MailbagVO> getMailbagsinContainer(ContainerDetailsVO containerVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findFlightDetails");
        return new MailController()
                .getMailbagsinContainer(containerVO, pageNumber);
    }

    public Page<DSNVO> getMailbagsinContainerdsnview(ContainerDetailsVO containerVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findFlightDetails");
        return new MailController()
                .getMailbagsinContainerdsnview(containerVO, pageNumber);
    }

    public MailbagVO findCarditSummaryView(CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException, RemoteException {
        log.entering(MODULE, "findFlightDetails");
        return new MailController()
                .findCarditSummaryView(carditEnquiryFilterVO);
    }

    public Page<MailbagVO> findGroupedCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) throws SystemException, RemoteException {
        log.entering(MODULE, "findGroupedCarditMails");
        return new MailController()
                .findGroupedCarditMails(carditEnquiryFilterVO, pageNumber);
    }

    public MailbagVO findLyinglistSummaryView(MailbagEnquiryFilterVO mailbagEnquiryFilterVO) throws SystemException, RemoteException {
        log.entering(MODULE, "findLyinglistSummaryView");
        return new MailController()
                .findLyinglistSummaryView(mailbagEnquiryFilterVO);
    }

    public Page<MailbagVO> findGroupedLyingList(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber) throws SystemException, RemoteException {
        log.entering(MODULE, "findGroupedCarditMails");
        return new MailController()
                .findGroupedLyingList(mailbagEnquiryFilterVO, pageNumber);
    }

    /**
     * @author a-7540
     */
    public String validateFromFile(FileUploadFilterVO fileUploadFilterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "validateFromFile");
        return new MailUploadController().validateFromFile(fileUploadFilterVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateFlightAndContainer(com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO)
     * Added by 			: A-8164 on 20-Feb-2019
     * Used for 	:
     * Parameters	:	@param mailUploadVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws MailHHTBusniessException
     * Parameters	:	@throws MailMLDBusniessException
     * Parameters	:	@throws MailTrackingBusinessException
     */
    public ScannedMailDetailsVO validateFlightAndContainer(
            MailUploadVO mailUploadVO)
            throws SystemException, RemoteException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException {
        log.entering( MODULE, "validateFlightAndContainer");
        try {
        return new MailUploadController().validateFlightAndContainer(mailUploadVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.reopenInboundFlights
     * Added by 	:	A-8164 on 11-Dec-2018
     * Used for 	:	For reopening multiple inbound flights
     * Parameters	:	@param operationalFlightVOs
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Return type	: 	void
     */
    public void reopenInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "reopenInboundFlights");
        new MailController().reopenInboundFlights(operationalFlightVOs);
        log.exiting( MODULE, "reopenInboundFlights");
    }


    /**
     * Method		:	MailTrackingDefaultsServicesEJB.closeInboundFlights
     * Added by 	:	A-8164 on 11-Dec-2018
     * Used for 	:	Closing multiple inbound flights
     * Parameters	:	@param operationalFlightVOs
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws MailTrackingBusinessException
     * Return type	: 	void
     */
    public void closeInboundFlights(Collection<OperationalFlightVO> operationalFlightVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "closeInboundFlights");
            MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
            mailController.closeInboundFlights(operationalFlightVOs);
        log.exiting( MODULE, "closeInboundFlights");
    }

    /**
     * Method		:	MailTrackingDefaultsServicesEJB.populateMailArrivalVOForInbound
     * Added by 	:	A-8164 on 27-Dec-2018
     * Used for 	:	For populating mailArrivalVo for inbound
     * Parameters	:	@param operationalFlightVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws MailTrackingBusinessException
     * Return type	: 	MailArrivalVO
     */
    public MailArrivalVO populateMailArrivalVOForInbound(OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {

        return new MailController().populateMailArrivalVOForInbound(operationalFlightVO);
    }

    public Page<ContainerDetailsVO> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {

        return new MailController().findArrivedContainersForInbound(mailArrivalFilterVO);
    }

    public Page<MailbagVO> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {

        return new MailController().findArrivedMailbagsForInbound(mailArrivalFilterVO);
    }

    public Page<DSNVO> findArrivedDsnsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {

        return new MailController().findArrivedDsnsForInbound(mailArrivalFilterVO);
    }


    public Page<MailAcceptanceVO> findOutboundCarrierDetails(OperationalFlightVO operationalFlightVO, int pageNumber) throws SystemException, RemoteException {
        return new MailController().findOutboundCarrierDetails(operationalFlightVO, pageNumber);
    }

    public Page<MailbagVO> getMailbagsinCarrierContainer(ContainerDetailsVO containerVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering(MODULE, "getMailbagsinContainer");
        return new MailController()
                .getMailbagsinCarrierContainer(containerVO, pageNumber);
    }

    public Page<DSNVO> getMailbagsinCarrierdsnview(ContainerDetailsVO containerVO, int pageNumber)
            throws SystemException, RemoteException {
        log.entering(MODULE, "getMailbagsinCarrierdsnview");
        return new MailController()
                .getMailbagsinCarrierdsnview(containerVO, pageNumber);
    }

    public Collection<DSNVO> getDSNsForContainer(ContainerDetailsVO containerVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "getDSNsForContainer");
        return new MailController()
                .getDSNsForContainer(containerVO);
    }

    public Collection<DSNVO> getRoutingInfoforDSN(
            Collection<DSNVO> dsnVos, ContainerDetailsVO containerDetailsVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "getRoutingInfoforDSN");
        return new MailController()
                .getRoutingInfoforDSN(dsnVos, containerDetailsVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI.findOffLoadDetails
     * Added by 			: A-7929 on 18-Feb-2018
     * Used for 	:
     * Parameters	:	@param offloadFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public OffloadVO findOffLoadDetails(OffloadFilterVO offloadFilterVO) throws SystemException, RemoteException {
        log.entering(MODULE, "findOffLoadDetails");
        return new MailController().findOffLoadDetails(offloadFilterVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI.findOffLoadDetails
     * Added by 			: A-7929 on 18-Feb-2018
     * Used for 	:
     * Parameters	:	@param offloadFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public MailInConsignmentVO populatePCIDetailsforUSPS(MailInConsignmentVO mailInConsignment, String airport, String companyCode, String rcpOrg, String rcpDest, String year)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findOffLoadDetails");
        return new MailController().populatePCIDetailsforUSPS(mailInConsignment, airport, companyCode, rcpOrg, rcpDest, year);
    }

    public void calculateULDContentId(Collection<ContainerDetailsVO> containerVOs, OperationalFlightVO toFlightVO)
            throws SystemException {
        log.entering( MODULE, "calculateULDContentId");
        AAMailController aaMailController = (AAMailController) SpringAdapter.getInstance().getBean("aaMailController");
        aaMailController.calculateULDContentId(containerVOs, toFlightVO);
    }

    public void saveScreeningDetails(ScannedMailDetailsVO scannedMailDetailsVO)
            throws SystemException, RemoteException {
    	log.entering( MODULE, "saveScreeningDetails");
    	((MailTrackingDefaultsBI)SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).saveScreeningDetails(scannedMailDetailsVO);
    }
    /**
     * @param incentiveConfigurationFilterVO
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public Collection<IncentiveConfigurationVO> findIncentiveConfigurationDetails(
            IncentiveConfigurationFilterVO incentiveConfigurationFilterVO) throws SystemException, RemoteException {
        log.entering( MODULE, "findIncentiveConfigurationDetails");
        return new MailController().findIncentiveConfigurationDetails(incentiveConfigurationFilterVO);
    }

    /**
     * @param incentiveConfigurationVOs
     * @throws SystemException
     * @throws RemoteException
     * @author A-6986
     */
    public void saveIncentiveConfigurationDetails(Collection<IncentiveConfigurationVO> incentiveConfigurationVOs)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering( MODULE, "saveIncentiveConfigurationDetails");
        try {
            new MailController().saveIncentiveConfigurationDetails(incentiveConfigurationVOs);
        } catch (RemoveException ex) {
            throw new SystemException(ex.getErrorCode(), ex);
        }

    }

    /**
     * Added by 			: A-5526 on 12-Oct-2018
     * Used for 	:
     * Parameters	:	@param runnerFlightFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     *
     * @throws PersistenceException
     */

    public Page<RunnerFlightVO> findRunnerFlights(RunnerFlightFilterVO runnerFlightFilterVO)
            throws RemoteException, SystemException, PersistenceException {

        return new MailController().findRunnerFlights(runnerFlightFilterVO);

    }


    /**
     * Added by 	:	A-7929 on 23-Oct-2018
     * Added for 	:   CRQ ICRD-241437
     * Parameters	:	@param mailbagEnquiryFilterVO,pageNumber
     * Parameters	:	@return
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     * Return type	: 	Page<MailbagVO>
     */
    public Page<MailbagVO> findMailbagsForTruckFlight(
            MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
            throws RemoteException, SystemException {
        log.entering( MODULE, "findMailbagsForTruckFlight");
        return new MailController().findMailbagsForTruckFlight(mailbagEnquiryFilterVO, pageNumber);
    }


    /**
     * Method		:	MailTrackingDefaultsServicesEJB.saveRoutingIndexDetails
     * Added by 	:	A-7531 on 08-Oct-2018
     * Used for 	:
     * Parameters	:	@param routingIndexVOs
     * Parameters	:	@throws RemoteException
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws MailTrackingBusinessException
     * Return type	: 	void
     *
     * @throws FinderException
     */
    public void saveRoutingIndexDetails(Collection<RoutingIndexVO> routingIndexVOs)
            throws RemoteException, SystemException, MailTrackingBusinessException, FinderException {
        log.entering(MODULE, "saveRoutingIndexDetails");
        new MailController().saveRoutingIndexDetails(routingIndexVOs);
        log.exiting(MODULE, "saveRoutingIndexDetails");
    }


    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findRotingIndex(java.lang.String, java.lang.String)
     * Added by 			: A-7531 on 30-Oct-2018
     * Used for 	:
     * Parameters	:	@param routingIndex
     * Parameters	:	@param companycode
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    @Override
    public Collection<RoutingIndexVO> findRoutingIndex(RoutingIndexVO routingIndexVO)
            throws SystemException, RemoteException {
        // TODO Auto-generated method stub
        log.entering(MODULE, "findRoutingIndex");
        return new MailController().findRoutingIndex(routingIndexVO);
    }


    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findDsnAndRsnForMailbag(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
     * Added by 			: A-7531 on 31-Oct-2018
     * Used for 	:
     * Parameters	:	@param maibagVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    @Override
    public MailbagVO findDsnAndRsnForMailbag(MailbagVO mailbagVO)
            throws SystemException, RemoteException {
        // TODO Auto-generated method stub
        return new MailController().findDsnAndRsnForMailbag(mailbagVO);
    }

    /**
     *
     */
    public Page<ForceMajeureRequestVO> listForceMajeureApplicableMails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
            throws RemoteException, SystemException {
        log.entering( MODULE, "listForceMajeureApplicableMails");
        return new MailController().listForceMajeureApplicableMails(filterVO, pageNumber);
    }

    /**
     *
     */
    public void saveForceMajeureRequest(ForceMajeureRequestFilterVO filterVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "saveForceMajeureRequest");
        new MailController().saveForceMajeureRequest(filterVO);
        log.exiting( MODULE, "saveForceMajeureRequest");

    }


    /**
     *
     */
    public InvoiceTransactionLogVO initTxnForForceMajeure(
            InvoiceTransactionLogVO invoiceTransactionLogVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "initTxnForForceMajeure");
        return new MailController().initTxnForForceMajeure(invoiceTransactionLogVO);
    }


    /**
     *
     */
    public Page<ForceMajeureRequestVO> listForceMajeureDetails(ForceMajeureRequestFilterVO filterVO, int pageNumber)
            throws RemoteException, SystemException {
        log.entering( MODULE, "listForceMajeureApplicableMails");
        return new MailController().listForceMajeureDetails(filterVO, pageNumber);
    }

    /**
     *
     */
    public Page<ForceMajeureRequestVO> listForceMajeureRequestIds(ForceMajeureRequestFilterVO filterVO, int pageNumber)
            throws RemoteException, SystemException {
        log.entering( MODULE, "listForceMajeureRequestIds");
        return new MailController().listForceMajeureRequestIds(filterVO, pageNumber);
    }


    /**
     *
     */
    public void deleteForceMajeureRequest(Collection<ForceMajeureRequestVO> requestVOs)
            throws SystemException, RemoteException {
        log.entering( MODULE, "deleteForceMajeureRequest");
        new MailController().deleteForceMajeureRequest(requestVOs);
        log.exiting( MODULE, "deleteForceMajeureRequest");

    }


    /**
     *
     */
    public void updateForceMajeureRequest(ForceMajeureRequestFilterVO requestVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "updateForceMajeureRequest");
        new MailController().updateForceMajeureRequest(requestVO);
        log.exiting( MODULE, "updateForceMajeureRequest");

    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findAllContainersInAssignedFlight(com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO)
     * Added by 			: A-7540 on 05-DEC-2018
     * Used for 	:
     * Parameters	:	@param operationalFlightVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Collection<ContainerVO> findAllContainersInAssignedFlight(FlightValidationVO reassignedFlightValidationVO) throws SystemException, RemoteException {
        log.entering( MODULE, "isFlightClosedForInboundOperations");
        return new MailController().findAllContainersInAssignedFlight(reassignedFlightValidationVO);
    }

    /**
     * @throws MailMLDBusniessException
     * @throws MailTrackingBusinessException
     * @throws MailHHTBusniessException
     * @author A-7540
     */
    public Collection<ErrorVO> processResditMails(Collection<MailScanDetailVO> mailScanDetailVO)
            throws SystemException, RemoteException, MailTrackingBusinessException, MailMLDBusniessException, MailHHTBusniessException {
        log.entering( MODULE, "saveMailServiceLevelDtls");

        return new MailUploadController().processResditMails(mailScanDetailVO);
    }

    /**
     * @author A-5526
     */

    public long findMailBagSequenceNumberFromMailIdr(String mailIdr, String companyCode)
            throws SystemException, RemoteException {
        log.entering(MODULE, "findPALov");
        return new MailController().findMailSequenceNumber(mailIdr, companyCode);
    }

    /***
     * @author A-7794
     */
    @Override
    public String processMailDataFromExcel(
            FileUploadFilterVO fileUploadFilterVO) throws PersistenceException, SystemException, RemoteException {
        log.entering(MODULE, "processMailDataFromExcel");
        return new DocumentController().processMailDataFromExcel(fileUploadFilterVO);
    }

    /**
     * @param mailArrivalVO
     * @throws BusinessDelegateException
     * @author A-5526
     * Added for CRQ ICRD-233864
     */
    public void onStatustoReadyforDelivery(MailArrivalVO mailArrivalVO)
            throws SystemException, RemoteException, MailTrackingBusinessException {
        log.entering(MODULE, "saveAutoArrivalAndReadyForDelivery");
        new MailController()
                .onStatustoReadyforDelivery(mailArrivalVO);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveContentID
     * Added by 			: A-7929 on 04-Feb-2018
     * Used for 	:
     * Parameters	:	@param containerActionData
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public void saveContentID(Collection<ContainerVO> containerVOs)
            throws SystemException, RemoteException {
        log.entering(MODULE, "saveContentID");
        MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
        mailController.saveContentID(containerVOs);

    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#updateActualWeightForMailbag
     * Added by 			: A-8672 on 04-Feb-2018
     * Used for 	:
     * Parameters	:	@param MailbagVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public void updateActualWeightForMailbag(MailbagVO mailbagVO)
            throws SystemException, RemoteException {
        this.log.entering( MODULE, "updateActualWeightForMailbag");
        new MailController().updateActualWeightForMailbag(mailbagVO);
    }

    //Added by A-8464 for ICRD-243079
    public Collection<MailMonitorSummaryVO> getPerformanceMonitorDetails(MailMonitorFilterVO filterVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "saveContentID");
        return new MailController().getPerformanceMonitorDetails(filterVO);

    }


    public Page<MailbagVO> getPerformanceMonitorMailbags(
            MailMonitorFilterVO filterVO, String type, int pageNumber)
            throws SystemException, RemoteException {

        return new MailController().getPerformanceMonitorMailbags(filterVO, type, pageNumber);
    }

    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveContentID
     * Added by 			: A-8438 on 07-Feb-2018
     * Used for 	: MailManifestVO
     * Parameters	:	@param findMailbagManifest
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public MailManifestVO findMailbagManifest(
            OperationalFlightVO operationalFlightVO)
            throws SystemException, RemoteException {
        log.entering( MODULE, "findMailbagManifest");
        return new MailController().findMailbagManifest(operationalFlightVO);
    }
    public MailManifestVO findMailAWBManifest(OperationalFlightVO operationalFlightVO) throws SystemException, RemoteException {
        log.entering( MODULE, "findMailAWBManifest");
        return new MailController().findMailAWBManifest(operationalFlightVO);   }
    public MailManifestVO findDSNMailbagManifest(OperationalFlightVO operationalFlightVO)throws SystemException, RemoteException {
        log.entering( MODULE, "findDSNMailbagManifest");
        return new MailController().findDSNMailbagManifest(operationalFlightVO);}
    public MailManifestVO findDestnCatManifest(OperationalFlightVO operationalFlightVO)throws SystemException, RemoteException {
        log.entering( MODULE, "findDestnCatManifest");
        return new MailController().findDestnCatManifest(operationalFlightVO);}  
    public Collection<MailHandedOverVO> generateMailHandedOverRT(MailHandedOverFilterVO mailHandedOverFilterVO)throws SystemException, RemoteException {
        log.entering( MODULE, "generateMailHandedOverReport"); 
        return new MailController().generateMailHandedOverRT(mailHandedOverFilterVO);} 
    /**
     * Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#validateFrmToDateRange
     * Added by 			: A-8527 on 07-Feb-2018
     * Used for 	: USPSPostalCalendarVO
     * Parameters	:	@param USPSPostalCalendarFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Collection<USPSPostalCalendarVO> validateFrmToDateRange(USPSPostalCalendarFilterVO uSPSPostalCalendarFilterVO)
            throws SystemException, RemoteException {
        log.entering(MODULE, "validateFrmToDateRange");
        return new MailController().validateFrmToDateRange(uSPSPostalCalendarFilterVO);
    }

    /**
     * Added by 			: A-8464 on 26-Mar-2019
     * Used for 	: ICRD-273761
     * Parameters	:	@param MailbagEnquiryFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public MailbagVO findMailbagDetailsForMailbagEnquiryHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
    		throws SystemException, RemoteException{
    	 log.entering(MODULE, "findMailbagDetailsForMailbagEnquiryHHT");
         return new MailController().findMailbagDetailsForMailbagEnquiryHHT(mailbagEnquiryFilterVO);
    }
    /**
     * Added by 	: A-8176 on 24-Apr-2019
     * Used for 	: ICRD-197279
     * Parameters	:	@param flightFilterVOs
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public Collection<FlightSegmentCapacitySummaryVO> fetchFlightCapacityDetails(
			Collection<FlightFilterVO> flightFilterVOs) throws SystemException,
			RemoteException {
		return new MailController().fetchFlightCapacityDetails(flightFilterVOs);
	}

    public USPSPostalCalendarVO findInvoicPeriodDetails(USPSPostalCalendarFilterVO uspsPostalCalendarFilterVO)throws SystemException,RemoteException{
		  log.entering(MODULE,"findInvoicPeriodDetails");
		 	 return new MailController().findInvoicPeriodDetails(uspsPostalCalendarFilterVO);
	  }

    /**
	 *
	 *	Overriding Method	:	populateUldManifestVOWithContentID
	 *	Added by 			: A-7929 on 04-March-2019
	 * 	Used for 	:
	 *	Parameters	:	@param DWSMasterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 * @throws FinderException
	 */
	public String fetchMailContentIDs(DWSMasterVO dwsMasterVO,String containerNumber,String assignedAirport) throws SystemException, RemoteException, FinderException {
				 log.entering( MODULE, "fetchMailContentIDs");
			     AAMailController aaMailController = (AAMailController)SpringAdapter.getInstance().getBean("aaMailController");
				return aaMailController.fetchMailContentIDs(dwsMasterVO,containerNumber,assignedAirport);

			}
	/**
	 *
	 * 	Method		:	MailTrackingDefaultsServicesEJB.findDuplicateMailbag
	 *	Added by 	:	A-7531 on 16-May-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param mailBagId
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Return type	: 	Collection<MailbagHistoryVO>
	 * @throws PersistenceException
	 */
	public ArrayList<MailbagVO> findDuplicateMailbag(String companyCode, String mailBagId) throws SystemException, RemoteException{
		// TODO Auto-generated method stub
		 log.entering(MODULE, "findDuplicateMailbag");
		 return new MailController().findDuplicateMailbag(companyCode,mailBagId);
	}

	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsServicesEJB.sendULDAnnounceForFlight
	 *	Added by 	:	A-8164 on 12-Feb-2021
	 * 	Used for 	:	Sending UCS Announce from mail outbound
	 *	Parameters	:	@param mailManifestVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException 
	 *	Return type	: 	void
	 */
	public void sendULDAnnounceForFlight(MailManifestVO mailManifestVO) 
			throws SystemException, RemoteException{
		log.entering(MODULE,"sendULDAnnounceForFlight");
		TKMailController tkMailController= (TKMailController)SpringAdapter.getInstance().getBean("tkMailController");
		tkMailController.sendULDAnnounceForFlight(mailManifestVO);
		log.exiting(MODULE,"sendULDAnnounceForFlight");
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#updateMailULDDetailsFromMHS(com.ibsplc.icargo.business.warehouse.defaults.operations.vo.StorageUnitCheckinVO)
	 *	Added by 			: A-8164 on 18-Feb-2021
	 * 	Used for 	:
	 *	Parameters	:	@param storageUnitCheckinVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException
	 */
	public boolean updateMailULDDetailsFromMHS(StorageUnitCheckinVO storageUnitCheckinVO)
			 throws SystemException,ProxyException,RemoteException{
		log.entering(MODULE,"sendULDAnnounceForFlight");
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		log.exiting(MODULE,"sendULDAnnounceForFlight");
        return mailController.updateMailULDDetailsFromMHS(storageUnitCheckinVO);
		
	}



	public MailboxIdVO findMailboxId(MailboxIdVO mailboxIdVO) throws SystemException, RemoteException, FinderException {
		        log.entering(MODULE, "findMailboxId");
		        MailboxIdVO MailboxIdVO=null;
		        MailboxIdVO= new MailController().findMailBoxId(mailboxIdVO);
		        return MailboxIdVO;
	}

	public Page<MailbagVO> findDeviationMailbags(MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int pageNumber)
			throws SystemException, RemoteException {
		log.entering(MODULE, "findDeviationMailbags");
		return new MailController().findDeviationMailbags(mailbagEnquiryFilterVO, pageNumber);
	}

	public void saveMailboxId(MailboxIdVO mailboxIdVO) throws SystemException, RemoteException, FinderException {
		  log.entering(MODULE, "saveMailboxId");
		  new MailController().saveMailboxId(mailboxIdVO);
	}
	//Added by A-8527 for IASCB-28086 Starts
	public String findProductsByName(String companyCode,String product)throws SystemException, RemoteException{
		log.entering(MODULE, "findProductsByName");
		 return new MailController().findProductsByName(companyCode,product);

	}
	//Added by A-8527 for IASCB-28086 Ends
    public void buildResditProxy(Collection<ResditEventVO> resditEvent) throws SystemException, RemoteException{
        log.entering(MODULE, "buildResditProxy");
            new ResditController().buildResditProxy(resditEvent);
    }


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveCarditTempMessages(java.util.Collection)
	 *	Added by 			: A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param carditTempMsgVOs
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws MailTrackingBusinessException
	 */
	@Override
	public void saveCarditTempMessages(Collection<CarditTempMsgVO> carditTempMsgVOs)
			throws RemoteException, SystemException, MailTrackingBusinessException {
		log.entering(MODULE, "saveCarditMessages");
        new MailController().saveCarditTempMessages(carditTempMsgVOs);
        log.exiting(MODULE, "saveCarditMessages");

	}


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#getTempCarditMessages(java.lang.String)
	 *	Added by 			: A-6287 on 01-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws FinderException
	 */
	@Override
	public Collection<CarditTempMsgVO> getTempCarditMessages(String companyCode,
                                                             String includeMailBoxIdr,String excludeMailBoxIdr,
                                                             String includedOrigins,String excludedOrigins,
                                                             int pageSize,int noOfdays)
			throws SystemException, RemoteException, FinderException {
		return new MailController().getTempCarditMessages(companyCode,includeMailBoxIdr,excludeMailBoxIdr,
                includedOrigins,excludedOrigins,pageSize,noOfdays);
	}

	@Override
	public Collection<ErrorVO> saveCarditMsgs(EDIInterchangeVO ediInterchangeVO)
            throws RemoteException, SystemException, MailTrackingBusinessException {
		log.entering(MODULE, "saveCarditMsgs");
	       Transaction tx = null;
	       String errorCode=null;
	       Collection<ErrorVO>errorVOs = new ArrayList<>();
	       boolean canCommit =false;
	       try {
               TransactionProvider tm = PersistenceController
                       .getTransactionProvider();
	           tx = tm.getNewTransaction(false); 
	           errorVOs = new MailController().saveCarditMessages(ediInterchangeVO);
	           //tx.commit();
	           performFlushAndClear();
               canCommit=true;
	       } catch (DuplicateMailBagsException|MailTrackingBusinessException ex) {
	          // tx.rollback();
               errorCode = "BSE_"+ex.getMessage();
               ErrorVO errorVO = new ErrorVO(errorCode);
               errorVO.setErrorCode(errorCode);
               errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
               errorVOs.add(errorVO);
               return errorVOs;
	          // throw new MailTrackingBusinessException(ex);
	       } /*catch (MailTrackingBusinessException ex){
	           tx.rollback();
	           throw ex;
	       } */catch (SystemException ex){
	         //  tx.rollback();
	           //throw ex;
               errorCode ="SYE_"+ex.getMessage();
               ErrorVO errorVO = new ErrorVO(errorCode);
               errorVO.setErrorCode(errorCode);
               errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
               errorVOs.add(errorVO);
               return errorVOs;
	       } catch (Exception ex){
             //  tx.rollback();
               //throw ex;
               errorCode ="RTE_"+ex.getMessage();
               ErrorVO errorVO = new ErrorVO(errorCode);
               errorVO.setErrorCode(errorCode);
               errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
               errorVOs.add(errorVO);
               return errorVOs;

           }finally {
	           log.exiting(MODULE, "saveCarditMsgs");
	           if(tx==null) {
	        	 log.log(log.FINE,errorCode);  
	           }else if( canCommit){
	               tx.commit();
               }else{
	               tx.rollback();
               }
	       }
	       return errorVOs;
	}

    //Added by A-8893 for IASCB-34152 starts
    public void deleteEmptyContainer(ContainerDetailsVO containerDetailsVO) throws SystemException, RemoteException{
    	log.entering(MODULE, "findProductsByName");
    	  MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
          mailController.deleteEmptyContainer(containerDetailsVO);

    }
  //Added by A-8893 for IASCB-34152 ends
    /**
     *
     * 	Method		:	MailTrackingDefaultsServicesEJB.updateGateClearStatus
     *	Added by 	:	U-1467 on 09-Mar-2020
     *	Parameters	:	@param operationalFlightVO
     *	Parameters	:	@param gateClearanceStatus
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException
     *	Return type	: 	void
     */
	public void updateGateClearStatus(
			com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO operationalFlightVO,
			String gateClearanceStatus) throws RemoteException, SystemException {
		new MailController().updateGateClearStatus(operationalFlightVO, gateClearanceStatus);
	}

	  /**
 * @throws MailMLDBusniessException
 * @throws MailTrackingBusinessException
 * @throws MailHHTBusniessException
 * @author U-1317
 */
public Collection<ErrorVO> processResditMailsForAllEvents(Collection<MailUploadVO> mailScanDetailVO)
        throws SystemException, RemoteException, MailTrackingBusinessException, MailMLDBusniessException, MailHHTBusniessException {
    log.entering( MODULE, "processResditMailsForAllEvents");

    return new MailUploadController().processResditMailsForAllEvents(mailScanDetailVO);
	}
/**

 * Added by     : a-8353
 * Used for 	: IASCB-45360
 * Parameters	:	@param ScannedMailDetailsVO
 *
 * @throws SystemException
 */
public void flagResditForAcceptanceInTruck(ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException, SystemException ,RemoteException{
    log.entering( MODULE, "flagResditForAcceptanceInTruck");
  ((MailTrackingDefaultsBI) SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).flagResditForAcceptanceInTruck(scannedMailDetailsVO);
}

/**
										  * 
										  *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findMailbagBillingStatus(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
										  *	Added by 			: a-8331 on 25-Oct-2019
										  * 	Used for 	:
										  *	Parameters	:	@param mailbagvo
										  *	Parameters	:	@return
										  *	Parameters	:	@throws RemoteException
										  *	Parameters	:	@throws SystemException
										  */
											 public DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws RemoteException, SystemException {
												
												log.entering("MailTrackingDefaultsDelegate","findMailbagBillingStatus");
												return new MailController().findMailbagBillingStatus(mailbagvo);
												
											}
										 /**
										  * 
										  *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#voidMailbags(java.util.Collection)
										  *	Added by 			: a-8331 on 25-Oct-2019
										  * 	Used for 	:
										  *	Parameters	:	@param documentBillingDetails
										  *	Parameters	:	@throws RemoteException
										  *	Parameters	:	@throws SystemException
										  */
										 public void voidMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetails)
													
													throws RemoteException, SystemException {
												new MailController().voidMailbags(documentBillingDetails);
											}
	 

											/**
											 * 
											 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#listCarditDsnDetails(com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO)
											 *	Added by 			: A-8164 on 04-Sep-2019
											 * 	Used for 	:	List Cardit DSN Details
											 *	Parameters	:	@param dsnEnquiryFilterVO
											 *	Parameters	:	@return
											 *	Parameters	:	@throws SystemException
											 *	Parameters	:	@throws RemoteException
											 *	Parameters	:	@throws MailTrackingBusinessException
											 */
											public Page<DSNVO> listCarditDsnDetails(
													DSNEnquiryFilterVO dsnEnquiryFilterVO) throws SystemException,
													RemoteException, MailTrackingBusinessException {
												return new MailController().listCarditDsnDetails(dsnEnquiryFilterVO);
											}	
											/**
 * @author A-7540
 * @param ediInterchangeVO
 * @throws RemoteException
 * @throws SystemException
 * @throws MailTrackingBusinessException
 */
public Collection<ErrorVO> saveCDTMessages(EDIInterchangeVO ediInterchangeVO)
        throws RemoteException, SystemException, MailTrackingBusinessException {
    log.entering(MODULE, "saveCarditMessages");
    Collection<ErrorVO> errorVO = new ArrayList<>();
    try {
    	errorVO = new MailController().saveCDTMessages(ediInterchangeVO);
         }
       catch (DuplicateMailBagsException ex) {
        throw new MailTrackingBusinessException(ex);
    }
    log.exiting(MODULE, "saveCDTMessages");
    return errorVO;
}















































   
    

			

public ErrorVO validateContainerNumberForDeviatedMailbags(ContainerDetailsVO containerDetailsVO, long mailSequenceNumber) throws RemoteException, SystemException, MailTrackingBusinessException{
	return new MailController().validateContainerNumberForDeviatedMailbags(containerDetailsVO, mailSequenceNumber);
}

/**
 * @param companyCode
 * @param mailbagId
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @author A-5526 This method is used to find the details of approved Force Meajure info of a Mailbag
 */
public Collection<ForceMajeureRequestVO> findApprovedForceMajeureDetails(
        String companyCode, String mailBagId, long mailSequenceNumber) throws SystemException,
        RemoteException {
    return new MailController()
            .findApprovedForceMajeureDetails(companyCode, mailBagId, mailSequenceNumber);
}

/**
 * @param companyCode
 * @param mailbagId
 * @return
 * @throws SystemException
 * @throws PersistenceException
 * @author A-8353
 */
public MailbagInULDForSegmentVO getManifestInfo(MailbagVO mailbagVO) throws PersistenceException, SystemException,RemoteException{
	return new MailController()
            .getManifestInfo(mailbagVO);
}

public void saveMailRuleConfiguration(MailRuleConfigVO mailRuleConfigVO) throws SystemException, RemoteException, FinderException {
	  log.entering(MODULE, "saveMailRuleConfiguration");
	  new MailController().saveMailRuleConfiguration(mailRuleConfigVO);
}
/**
 * @author A-8353
 * @throws PersistenceException
 * @throws SystemException
 * @throws RemoteException
 */
public String checkMailInULDExistForNextSeg(String containerNumber,String airpotCode, String companyCode) throws SystemException {
	return new MailController()
            .checkMailInULDExistForNextSeg(containerNumber,airpotCode,companyCode);

}


/**
 * @author A-8672
 * @param ContainerVO
 * @throws RemoteException
 * @throws SystemException
 * @throws FinderException
 */
public void updateRetainFlagForContainer(ContainerVO containerVO) throws RemoteException, FinderException, SystemException{
	new MailController().updateRetainFlagForContainer(containerVO);
}

@Override
public void createAutoAttachAWBJobSchedule(AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleVO)
		throws RemoteException, SystemException {
	new MailController().createAutoAttachAWBJobSchedule(autoAttachAWBJobScheduleVO);	
}
/**
 * 
 */
@Override
public Collection<ConsignmentDocumentVO> findTransferManifestConsignmentDetails(TransferManifestVO transferManifestVO)
		throws RemoteException, SystemException {
	 return new MailController().findTransferManifestConsignmentDetails(transferManifestVO);	
}
/**
 * @param consignmentNumber
 * @param companyCode
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws PersistenceException
 * @throws FinderException
 * @author A-9084
 */
public ConsignmentDocumentVO findConsignmentScreeningDetails(String consignmentNumber, String companyCode,String poaCode)
		throws SystemException, RemoteException, PersistenceException, FinderException {
	return new MailController().findConsignmentScreeningDetails(consignmentNumber, companyCode,poaCode);
	
}

/**
*
* 	Method		:	MailTrackingDefaultsServicesEJB.saveTransferFromManifest
*	Added by 	:	A-8893 on 06-Nov-2020
*	Parameters	:	@param TransferManifestVO
*	Parameters	:	@throws RemoteException
*	Parameters	:	@throws SystemException
*	Return type	: 	void
*/
public void saveTransferFromManifest(TransferManifestVO transferManifestVO) throws SystemException, RemoteException{
	try {
		new  MailController().saveTransferFromManifest(transferManifestVO);
	} catch (Exception e) {
		e.getMessage();
	}
}
/**
*
* 	Method		:	MailTrackingDefaultsServicesEJB.rejectTransferFromManifest
*	Added by 	:	A-8893 on 06-Nov-2020
*	Parameters	:	@param TransferManifestVO
*	Parameters	:	@throws RemoteException
*	Parameters	:	@throws SystemException
*	Return type	: 	void
*/
public void rejectTransferFromManifest(
		TransferManifestVO transferManifestVO)	throws SystemException, RemoteException{
	new MailController().rejectTransferFromManifest(transferManifestVO);
}


/**
*
* 	Method		:	MailTrackingDefaultsServicesEJB.findTransferManifestDetailsForTransfer
*	Added by 	:	A-8893 on 06-Nov-2020
*	Parameters	:	@param companyCode
*   Parameters	:	@param tranferManifestId
*	Parameters	:	@throws RemoteException
*	Parameters	:	@throws SystemException
*	Return type	: 	List<TransferManifestVO>
*/
public List<TransferManifestVO> findTransferManifestDetailsForTransfer(
		String companyCode,String tranferManifestId)throws SystemException, RemoteException{
	return new MailController().findTransferManifestDetailsForTransfer(companyCode,tranferManifestId);
}
/**
 * 
 * 	Method		:	MailTrackingDefaultsServicesEJB.performFlushAndClear
 * 	Added for 	:	IASCB-79746
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
private static void performFlushAndClear() throws SystemException {
	try {
    	PersistenceController.getEntityManager().flush();
    	PersistenceController.getEntityManager().clear();
    } catch (PersistenceException e) {
    	throw new SystemException(e.getMessage(),e);
    }	
}   
/**
 * 
 */
@Override
public void removeFromInbound(OffloadVO offloadVo)
		throws SystemException, RemoteException, MailTrackingBusinessException {
	 new MailController().removeFromInbound(offloadVo);	
}
/**
 * @param Collection<ConsignmentScreeningVO>
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @author A-9084
 * @throws FinderException 
 */
public void saveConsignmentScreeningDetails(ConsignmentDocumentVO consignmentDocumentVO)
		throws SystemException, RemoteException, FinderException {
	log.entering(MODULE, "saveConsignmentScreeningDetails");
	  new MailController().saveConsignmentScreeningDetails(consignmentDocumentVO);
	
}
/**
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws ProxyException
 * @throws PersistenceException
 * @author A-9084
 */
public HashMap<String, Object> generateConsignmentSecurityReport(ReportSpec reportSpec)
		throws SystemException, RemoteException, ProxyException, PersistenceException {
	  log.entering( MODULE, "generateConsignmentSecurityReport");
	  return new DocumentController().generateConsignmentSecurityReport(reportSpec);
	
}
/**
 * 
 * @param mailbagVO
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
public MailbagVO findMailBillingStatus(MailbagVO mailbagVO)
		throws SystemException, RemoteException {
	  log.entering( MODULE, "findMailBillingStatus");
	  return new MailController().findMailBillingStatus(mailbagVO);
	
}

/**
 * @param reportSpec
 * @return
 * @throws SystemException
 * @throws RemoteException
 * @throws ProxyException
 * @throws PersistenceException
 * @author A-9084
 * @throws ReportGenerationException 
 */
public Map<String, Object> generateConsignmentSummaryReports(ReportSpec reportSpec)
		throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException {
	log.entering( MODULE, "generateConsignmentSummaryReports");
	  return new DocumentController().generateConsignmentSummaryReports(reportSpec);
}



	@Override
	public Collection<MailEventVO> findMailEvent(MailEventVO mailEventVO) throws SystemException, RemoteException {
		return new MailController().findMailEvent(mailEventVO);
	}
    @Override
    public String saveUploadedForceMajeureData(FileUploadFilterVO fileUploadFilterVO)
            throws RemoteException,SystemException {
        log.entering(MODULE, "saveUploadedForceMajeureData");
        return new MailController().saveUploadedForceMajeureData(fileUploadFilterVO);

    }
    public String findAutoPopulateSubtype(
            DocumentFilterVO documnetFilterVO) throws RemoteException,
            SystemException, MailTrackingBusinessException {
        log.entering( MODULE,
                "findNextDocumentNumber");
        try {
            return new MailController()
                    .findAutoPopulateSubtype(documnetFilterVO);
        } catch (ProxyException ex) {
            throw new MailTrackingBusinessException(ex);
        } catch (StockcontrolDefaultsProxyException e) {
        	throw new MailTrackingBusinessException(e);
		}
    }
    
 public String  findOfficeOfExchangeForCarditMissingDomMail(String companyCode,
            String airportCode) throws SystemException, RemoteException {
    log.entering( MODULE, "findOfficeOfExchangeForCarditMissingDomMail");
    return new MailController().findOfficeOfExchangeForCarditMissingDomMail(companyCode, airportCode);
}
 /**
  * 
  *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveMailUploadDetailsFromMailOnload(java.util.Collection)
  *	Added by 			: A-8061 on 07-Apr-2021
  * 	Used for 	:
  *	Parameters	:	@param mailUploadVOs
  *	Parameters	:	@return
  *	Parameters	:	@throws RemoteException
  *	Parameters	:	@throws SystemException
  *	Parameters	:	@throws MailHHTBusniessException
  *	Parameters	:	@throws MailMLDBusniessException
  *	Parameters	:	@throws MailTrackingBusinessException
  */
 public Map<String, ErrorVO> saveMailUploadDetailsFromMailOnload(Collection<MailUploadVO> mailUploadVOs)
			throws RemoteException, SystemException, MailHHTBusniessException, MailMLDBusniessException,
			MailTrackingBusinessException {
		return new MailUploadController().saveMailUploadDetailsFromMailOnload(mailUploadVOs);
 }
/**
  * @author A-8353
  * @throws SystemException
  * @throws RemoteException
  */
 public void updateOriginAndDestinationForMailbag(Collection<MailbagVO> mailbagVOs)
         throws SystemException, RemoteException {
     this.log.entering( MODULE, "updateOriginAndDestinationForMailbag");
     new MailController().updateOriginAndDestinationForMailbag(mailbagVOs);
 }
 
 public long insertMailbagAndHistory(MailbagVO mailbagVO)
		 throws SystemException, RemoteException {
	 return new MailController().insertMailbagAndHistory(mailbagVO);
 }
		



    

    public Collection<ImportFlightOperationsVO> findInboundFlightOperationsDetails(
            ImportOperationsFilterVO filterVO,
            Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException, SystemException, RemoteException {
        return new MailUploadController().findInboundFlightOperationsDetails(filterVO, manifestFilterVOs);
    }
    public Collection<OffloadULDDetailsVO> findOffloadULDDetailsAtAirport(
            com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO filterVO)
            throws SystemException, PersistenceException, RemoteException {
        return new MailUploadController().findOffloadULDDetailsAtAirport(filterVO);
    }
    public Collection<ManifestVO> findExportFlightOperationsDetails(
            ImportOperationsFilterVO filterVO,
            Collection<ManifestFilterVO> manifestFilterVOs) throws PersistenceException, SystemException, RemoteException {
    	 log.entering(MODULE, "findExportFlightOperationsDetails");
        return new MailUploadController().findExportFlightOperationsDetails(filterVO, manifestFilterVOs);
    }
    @Override
	public void saveUploadedConsignmentData(Collection<ConsignmentDocumentVO> consignmentDocumentVOs)
			throws SystemException, RemoteException, MailTrackingBusinessException {
		log.entering(MODULE, "saveUploadedConsignmentData");
		new DocumentController().saveUploadedConsignmentData(consignmentDocumentVOs);
	}
	@Override
	public Collection<ConsignmentDocumentVO> fetchConsignmentDetailsForUpload(FileUploadFilterVO fileUploadFilterVO)
			throws RemoteException,SystemException {
		log.entering(MODULE, "fetchConsignmentDetailsForUpload");
		return new MailController().fetchConsignmentDetailsForUpload(fileUploadFilterVO);
	}
	@Override
	public void saveMailbagHistory(Collection<MailbagHistoryVO>mailbagHistoryVOs)
			throws RemoteException,SystemException {
		log.entering(MODULE, "saveMailbagHistory");
		new MailController().saveMailbagHistory(mailbagHistoryVOs);
	}
	/**
	 * @param ContainerVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @author A-8893
	 */
	public void releaseContainers(Collection<ContainerVO> containerVOsToBeReleased)throws RemoteException,SystemException {
		log.entering(MODULE, "releaseContainers");
		new MailController().releaseContainers(containerVOsToBeReleased);
	
	}
	
		
	public byte[] findMailbagDamageImages(String companyCode,
			String id)throws RemoteException,SystemException {
		log.entering(MODULE, "findMailbagDamageImages");
		return new MailController().findMailbagDamageImages(companyCode,id);
	
	}
	/**
	 * 
	 * 	Method		:	MailTrackingDefaultsServicesEJB.findContainerJourneyID
	 *	Parameters	:	@param containerFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ContainerDetailsVO>
	 */
	public Collection<ContainerDetailsVO> findContainerJourneyID(ConsignmentFilterVO consignmentFilterVO) 
			throws RemoteException,SystemException {
		log.entering(MODULE, "findContainerJourneyID");
		return new MailController().findContainerJourneyID(consignmentFilterVO);
	}
	@Override
	public void stampResdits(Collection<MailResditVO> mailResditVOs) throws RemoteException, SystemException, MailTrackingBusinessException {
		log.entering(MODULE, "stampResdits");
		new MailController().stampResdits(mailResditVOs);
	}


	@Override
	public Collection<ContainerDetailsVO> findArrivalDetailsForReleasingMails(OperationalFlightVO operationalFlightVO)
			throws RemoteException, SystemException {
		log.entering(MODULE, "findArrivalDetailsForReleasingMails");
		return new MailController().findArrivalDetailsForReleasingMails(operationalFlightVO);
	}


	@Override
	public String findMailboxIdFromConfig(MailbagVO mailbagVO) throws RemoteException, SystemException {
		log.entering(MODULE, "findMailboxIdFromConfig");
		return new MailController().findMailboxIdFromConfig(mailbagVO);
	}

	/**
	 * @author A-8353
	 * @param mailBagVO
	 * @param scanningPort
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void performErrorStampingForFoundMailWebServices(MailUploadVO mailBagVO, String scanningPort)
			throws RemoteException, SystemException {
		new MailUploadController().performErrorStampingForFoundMailWebServices(mailBagVO,scanningPort);
	}
	
	/**
	 * @author A-9084
	 * @param mailAuditFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException 
	 */
	public Collection<AuditDetailsVO> findAssignFlightAuditDetails(MailAuditFilterVO mailAuditFilterVO)
			throws BusinessDelegateException, SystemException,RemoteException {
				return new MailController().findAssignFlightAuditDetails(mailAuditFilterVO);
	}

	@Override
	public Collection<MailbagVO> getFoundArrivalMailBags(MailArrivalVO mailArrivalVO)
			throws RemoteException, SystemException {
		log.entering(MODULE, "getFoundArrivalMailBags");
		return new MailController().getFoundArrivalMailBags(mailArrivalVO);
	}
	@Override
	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs)
			throws RemoteException, SystemException {
		log.entering(MODULE, "getFoundArrivalMailBags");
	new MailController().dettachMailBookingDetails(mailbagVOs);
	}


	@Override
	public void attachAWBForMail(Collection<MailBookingDetailVO> mailBookingDetailVOs, Collection<MailbagVO> mailbagVOs)
			throws RemoteException , SystemException {
		new MailController().attachAWBForMailForAddons(mailBookingDetailVOs,mailbagVOs);
	}
	
	public String findNearestAirportOfCity(String companyCode, String exchangeCode) throws RemoteException , SystemException{
		return new MailController().findNearestAirportOfCity(companyCode,exchangeCode);
	}

	@Override
	public void markUnmarkUldIndicator(ContainerVO containerVO) throws SystemException, RemoteException {
		log.entering(MODULE, "markUnmarkUldIndicator");
		new MailController().markUnmarkUldIndicator(containerVO);

	}

	/**
     * @param mailBagVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws MailHHTBusniessException
     * @throws MailMLDBusniessException
     * @throws MailTrackingBusinessException
     * @throws PersistenceException
     * @author A-9998
     */
    public ScannedMailDetailsVO saveMailUploadDetailsForULDFULIndicator(
            MailUploadVO mailBagVO, String scanningPort)
            throws RemoteException, SystemException, MailHHTBusniessException,
            MailMLDBusniessException, MailTrackingBusinessException, PersistenceException {
    	return new MailUploadController().saveMailUploadDetailsForULDFULIndicator(mailBagVO, scanningPort);
    }
    
	public void triggerEmailForPureTransferContainers(Collection<OperationalFlightVO> operationalFlightVOs)
			throws SystemException, RemoteException {
		log.entering( MODULE, "triggerEmailForPureTransferContainers");

		new MailController().triggerEmailForPureTransferContainers(operationalFlightVOs);
	}
	public void saveSecurityDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)throws SystemException, RemoteException {
		log.entering(MAILTRACKINGDEFAULTSEJB, "saveSecurityDetails");

		new MailController().saveSecurityDetails(consignmentScreeningVOs);
	}
	
	public MailbagVO listmailbagSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo)
			throws SystemException, RemoteException {
		log.entering(MODULE, "listmailbagSecurityDetails");

		return new MailController().listmailbagSecurityDetails(mailScreeningFilterVo);
	}
	/**
	 * @author A-10383
	 * Method		:	MailTrackingDefaultsServicesEJB.editscreeningDetails
	 * @param consignmentScreeningVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void editscreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)
			throws SystemException, RemoteException {
		log.entering(MAILTRACKINGDEFAULTSEJB, "editscreeningDetails");

		new MailController().editscreeningDetails(consignmentScreeningVOs);
	}
	/**
	 * @author A-10383
	 * Method		:	MailTrackingDefaultsServicesEJB.deletescreeningDetails
	 * @param consignmentScreeningVOs
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void deletescreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs)
			throws SystemException, RemoteException {
		log.entering(MAILTRACKINGDEFAULTSEJB, "deletescreeningDetails");

		new MailController().deletescreeningDetails(consignmentScreeningVOs);
	}
	public Map<String, Object> generateMailSecurityReport(ReportSpec reportSpec)
			throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException {
		log.entering(MODULE, "generateMailSecurityReport");
		return new MailController().generateMailSecurityReport(reportSpec);

	}
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveMailSecurityStatus(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO)
	 *	Added by 			: A-4809 on 18-May-2022
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagVO
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws RemoteException
	 */
	 public void saveMailSecurityStatus(MailbagVO mailbagVO) throws SystemException,RemoteException{
		 log.entering(MAILTRACKINGDEFAULTSEJB, "saveMailSecurityStatus");
		 new MailController().saveMailSecurityStatus(mailbagVO);
	 }
	 
	 /**
		 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#saveScreeningConsginorDetails(java.util.Map)
		 *	Added by 			: A-4809 on 19-May-2022
		 * 	Used for 	:
		 *	Parameters	:	@param contTransferMap
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 */
	    public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap)
	            throws SystemException, RemoteException {
	    	log.entering( MODULE, "saveScreeningDetails");
	    	((MailTrackingDefaultsBI)SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).saveScreeningConsginorDetails(contTransferMap);
	    }

	    public MailbagVO findAirportFromMailbag(MailbagVO mailbagvo) throws SystemException, FinderException, RemoteException{
	    	log.entering(MODULE, "findAirportFromMailbag");

			return new MailController().findAirportFromMailbag(mailbagvo);
	    }
	 
	    public void saveFligthLoadPlanForMail(Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs) throws SystemException, BusinessException,RemoteException {
	    	log.entering( MODULE, "saveFligthLoadPlanForMail");
	    	new MailController().saveFligthLoadPlanForMail(flightLoadPlanContainerVOs);
	    }   
		@Override
		public Collection<FlightLoadPlanContainerVO> findLoadPlandetails(SearchContainerFilterVO searchContainerFilterVO)
				throws SystemException, RemoteException {
			log.entering(MODULE, "findLoadPlandetails");
				return new MailController().findLoadPlandetails(searchContainerFilterVO);
		}   
		/**
		 * @author A-10383
		 * Method		:	MailTrackingDefaultsServicesEJB.saveConsignmentDetailsMaster
		 * @param consignmentScreeningVOs
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public void saveConsignmentDetailsMaster(ConsignmentDocumentVO  consignmentDocumentVO)
				throws SystemException, RemoteException {
			log.entering(MAILTRACKINGDEFAULTSEJB, "saveConsignmentDetailsMaster");

			new MailController().saveConsignmentDetailsMaster(consignmentDocumentVO);
		}
        /**
	     * @author A-8353
	     * @param securityAndScreeningMessageVO
	     * @return
	     * @throws RemoteException
	     * @throws SystemException
	     * @throws MailTrackingBusinessException
	     */
	    public Map<String, ErrorVO> saveSecurityScreeningFromService(SecurityAndScreeningMessageVO securityAndScreeningMessageVO)
				throws RemoteException, SystemException,
				MailTrackingBusinessException {
			return new MailController().saveSecurityScreeningFromService(securityAndScreeningMessageVO);
	 }	 
/**
		 * @author A-9998
		 * Method		:	MailTrackingDefaultsServicesEJB.findAirportParameterCode
		 * @param flightFilterVO
		 * @param parCodes
		 * @throws SystemException
		 * @throws RemoteException
		 */
		public Map<String,String> findAirportParameterCode(FlightFilterVO flightFilterVO,Collection<String> parCodes)
				throws SystemException, RemoteException {
			log.entering(MODULE, "findAirportParameterCode");

			return new MailController().findAirportParameterCode(flightFilterVO,parCodes);
		}


		@Override
		public Collection<com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO> fetchMailIndicatorForProgress(
				Collection<FlightListingFilterVO> flightListingFilterVOs) throws SystemException, RemoteException {
			log.entering(MODULE, "fetchMailIndicatorForProgress");
			
			return new MailController().fetchMailIndicatorForProgress(flightListingFilterVOs);
		}

    /**
     * @param mailMasterDataFilterVO
     * @throws SystemException
     * @throws RemoteException
     * @author 204082
     * Added for IASCB-159276 on 27-Sep-2022
     */
    @Override
    public void publishMasterDataForMail(MailMasterDataFilterVO mailMasterDataFilterVO)
            throws SystemException, RemoteException {

        log.entering(MODULE, "publishMasterDataForMail");
        new MailController().publishMasterDataForMail(mailMasterDataFilterVO);
        log.exiting(MODULE, "publishMasterDataForMail");

    }

    public void publishMailDetails(MailMasterDataFilterVO mailMasterDataFilterVO)
            throws SystemException, RemoteException {
        new MailController().publishMasterDataForMail(mailMasterDataFilterVO);
    }
    public Collection<SecurityScreeningValidationVO> findSecurityScreeningValidations(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)
					throws RemoteException, SystemException,
					MailTrackingBusinessException {
				return new MailController().findSecurityScreeningValidations(securityScreeningValidationFilterVO);
	}


	
	 public SecurityScreeningValidationVO doSecurityAndScreeningValidationAtContainerLevel(
				OperationalFlightVO operationalFlightVO, Collection<ContainerVO> selectedContainerVOs)
				throws SystemException, RemoteException, BusinessException {
			return new MailController().doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO,selectedContainerVOs);
	}	

		@Override	
		public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingVO hbaMarkingVO) throws SystemException, RemoteException {
			log.entering("LhMailOperationsEJB", "markHba");
			new MailController().updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
		}

    @Override
    public Collection<RoutingIndexVO> getPlannedRoutingIndexDetails(RoutingIndexVO routingIndexVO) throws SystemException, RemoteException {
        log.entering(MAIL_OPERATION_SERVICES, "getPlannedRoutingIndexDetails");

        return new MailController().getPlannedRoutingIndexDetails(routingIndexVO.getDestination());
    }
		


		/**
		 * 
		 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#generateCN46ConsignmentReport(com.ibsplc.icargo.framework.report.vo.ReportSpec)
		 *	Added by 			: A-10647 on 27-Oct-2022
		 * 	Used for 	:
		 *	Parameters	:	@param reportSpec
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Parameters	:	@throws RemoteException
		 *	Parameters	:	@throws ReportGenerationException
		 *	Parameters	:	@throws PersistenceException
		 */
	public Map<String, Object> generateCN46ConsignmentReport(ReportSpec reportSpec)
			throws SystemException, RemoteException, ReportGenerationException, PersistenceException {
		log.entering(MODULE, "generateCN46ConsignmentReport");
		return new DocumentController().generateCN46ConsignmentReport(reportSpec);
	}

		 /**
		  * 
		  *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#generateCN46ConsignmentSummaryReport(com.ibsplc.icargo.framework.report.vo.ReportSpec)
		  *	Added by 			: A-10647 on 27-Oct-2022
		  * 	Used for 	:
		  *	Parameters	:	@param reportSpec
		  *	Parameters	:	@return
		  *	Parameters	:	@throws SystemException
		  *	Parameters	:	@throws RemoteException
		  *	Parameters	:	@throws ProxyException
		  *	Parameters	:	@throws PersistenceException
		  *	Parameters	:	@throws ReportGenerationException
		  */
	public Map<String, Object> generateCN46ConsignmentSummaryReport(ReportSpec reportSpec)
			throws SystemException, RemoteException, ProxyException, PersistenceException, ReportGenerationException {
		log.entering(MODULE, "generateCN46ConsignmentSummaryReport");
		return new DocumentController().generateCN46ConsignmentSummaryReport(reportSpec);
	}

    /**
     * @param companyCode
     * @param airportCode
     * @return OfficeOfExchangeVO
     * @author 204082
     * Added for IASCB-164537 on 09-Nov-2022
     */
    @Override
    public Collection<OfficeOfExchangeVO> getExchangeOfficeDetails(String companyCode, String airportCode) throws SystemException {
        log.entering(MODULE, "getExchangeOfficeDetails");
        return new MailController().getExchangeOfficeDetails(companyCode, airportCode);
    }

    @Override
    public String generateAutomaticBarrowId(String cmpcod) throws SystemException, RemoteException {
		log.entering(MODULE, "generateAutomaticBarrowId");
		return new MailController().generateAutomaticBarrowId(cmpcod);
	}
    /**
	 *
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.mail.operations.MailTrackingDefaultsBI#findCN46TransferManifestDetails(com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO)
	 *	Added by 			: A-10647 on 27-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param transferManifestVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
public Collection<ConsignmentDocumentVO> findCN46TransferManifestDetails(TransferManifestVO transferManifestVO)
		throws RemoteException, SystemException {
	return new MailController().findCN46TransferManifestDetails(transferManifestVO);
}

    @Override
    public Collection<MailbagVO> getMailbagDetailsForValidation(String companyCode, String airportCode) throws SystemException, RemoteException {
        log.entering(MAIL_OPERATION_SERVICES, "getMailbagDetailsForValidation");

        return new MailController().getMailbagDetailsForValidation(companyCode, airportCode);
    }  

    /**
	 * @author A-8353
	 */
	public Collection<SecurityScreeningValidationVO> doApplicableRegulationFlagValidationForPABuidContainer(MailbagVO mailbagVO,
			SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)
			throws SystemException, RemoteException, BusinessException {
		
		return new MailController().doApplicableRegulationFlagValidationForPABuidContainer(mailbagVO,securityScreeningValidationFilterVO);
	}
	
	    
    public Collection<MailAcceptanceVO> fetchFlightPreAdviceDetails(
 			Collection<FlightFilterVO> flightFilterVOs) throws SystemException,
 			RemoteException {
 		return new MailController().fetchFlightPreAdviceDetails(flightFilterVOs);
		}
    
	public ContainerVO updateActualWeightForMailContainer(ContainerVO containerVO) throws SystemException {
        MailController mailController = (MailController) SpringAdapter.getInstance().getBean(MailConstantsVO.MAIL_CONTROLLER_BEAN);
        return mailController.updateActualWeightForMailContainer(containerVO);
	}

	public Collection<OperationalFlightVO> findFlightsForMailInboundAutoAttachAWB(
			MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleVO) throws SystemException {
		return new MailController().findFlightsForMailInboundAutoAttachAWB(mailInboundAutoAttachAWBJobScheduleVO);
	}

	
	public Page<MailTransitVO> findMailTransit(
            MailTransitFilterVO mailTransitFilterVO, int pageNumber)
            throws SystemException, PersistenceException {
        log.entering(MODULE, "findMailTransit");
        return new MailController().findMailTransit(mailTransitFilterVO,
                pageNumber);
    }
	/**
     * Added by 			: U-1519 on 14-Mar-2023
     * Used for 	: IASCB-200804
     * Parameters	:	@param MailbagEnquiryFilterVO
     * Parameters	:	@return
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws RemoteException
     */
    public MailbagVO findMailbagDetailsForMailInboundHHT(MailbagEnquiryFilterVO mailbagEnquiryFilterVO)
    		throws SystemException, RemoteException{
    	 log.entering(MODULE, "findMailbagDetailsForMailbagEnquiryHHT");
         return new MailController().findMailbagDetailsForMailInboundHHT(mailbagEnquiryFilterVO);
    }  
  public Collection<FlightSegmentCapacitySummaryVO> findFlightListings(
			FlightFilterVO filterVo)
			throws SystemException, ProxyException {
		log.entering(MODULE, "findFlightListings");
		return new MailController().findFlightListings(filterVo);
	}
	public Page<FlightSegmentCapacitySummaryVO> findActiveAllotments(
			FlightSegmentCapacityFilterVO filterVo)
			throws SystemException, ProxyException {
		log.entering(MODULE, "findActiveAllotments");
		return new MailController().findActiveAllotments(filterVo);
	}
	public MailbagVO findMailConsumed(
			MailTransitFilterVO filterVo)
			throws SystemException {
		log.entering(MODULE, "findMailConsumed");
		return new MailController().findMailConsumed(filterVo);
    }
	
	@Override
	public void updateMLDMsgSentTime(MLDMessageVO mldMessageVO)
			throws RemoteException, SystemException {
		log.entering(MODULE, "updateMLDMsgSentTime");
        new MLDController().updateMLDMsgSentTime(mldMessageVO);
        log.exiting(MODULE, "updateMLDMsgSentTime");
	}
	
	/**
     * @param attachPAWBToConsignmentJobScheduleVO
     * @throws SystemException
     * @throws RemoteException
     * @throws PersistenceException 
     * @author A-9998
     * Added for IASCB-158296
     */
    @Override
    public void createPAWBForConsignment(int noOfDays)
            throws SystemException, RemoteException, PersistenceException {

        log.entering(MODULE, "createPAWBForConsignment");
        ((MailTrackingDefaultsBI)SpringAdapter.getInstance().getBean(MAIL_OPERATION_SERVICES)).createPAWBForConsignment(noOfDays);
        log.exiting(MODULE, "createPAWBForConsignment");

    }
    
    
    /**
     * @param carditVO
     * @return AWBDetailVO
     * @throws SystemException
     * @throws RemoteException
     * @throws MailTrackingBusinessException
     * @author a-9998
     */
    public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO)
            throws SystemException, RemoteException,
            MailTrackingBusinessException {
        log.entering( MODULE, "findMstDocNumForAWBDetails");
        return new LHMailController().findMstDocNumForAWBDetails(carditVO);
    }
	public Collection<MailbagHistoryVO> findMailbagHistoriesFromWebScreen(  
            String companyCode, String mailBagId, long mailSequenceNumber) throws SystemException,
            RemoteException {
    	 log.entering( MODULE, "findMailbagHistoriesFromWebScreen");     
        return new MailController()
               .findMailbagHistoriesFromWebScreen(companyCode, mailBagId, mailSequenceNumber);
    }
 
 /**
  * @param mailBagVOs
  * @return
  * @throws RemoteException
  * @throws SystemException
  * @throws MailHHTBusniessException
  * @throws MailMLDBusniessException
  * @throws MailTrackingBusinessException
  * @throws PersistenceException
  * @author A-9998
  */
 public void saveMailScanDetails(
         MailUploadVO mailBagVO)
         throws RemoteException, SystemException, MailHHTBusniessException,
         MailMLDBusniessException, MailTrackingBusinessException, PersistenceException {
	new MailUploadController().saveMailScanDetails(mailBagVO);
 }
	@Override
	public void saveMalRdtMsgAddDtl(Collection<MessageDespatchDetailsVO> messageAddressDetails,
            Collection<AutoForwardDetailsVO> participantDetails,MessageVO messageVo,String selectedResditVersion,Collection<String> selectedResdits,Collection<MailbagVO> selectedMailbags) throws SystemException, RemoteException {
	         new MailController().saveMalRdtMsgAddDtl(messageAddressDetails,participantDetails,messageVo,selectedResditVersion,(List<String>) selectedResdits,selectedMailbags);
    }
	public Map<String, Object> generateCN46ReportForFlightlevel(ReportSpec reportSpec) throws SystemException, RemoteException {
		return new MailController().generateCN46ReportForFlightlevel(reportSpec);
    }
	public void saveScreeningDetailsFromHHT(Collection<ConsignmentScreeningVO> consignmentScreeningVos) throws SystemException, RemoteException, FinderException, PersistenceException , MailHHTBusniessException{
		new MailController().saveScreeningDetailsFromHHT(consignmentScreeningVos);
	}

	@Override
	public Collection<FlightSegmentVolumeDetailsVO> fetchFlightVolumeDetails(Collection<FlightFilterVO> flightFilterVOs)
			throws SystemException, RemoteException {
		return new MailController().fetchFlightVolumeDetails(flightFilterVOs);
	}
	@Override
	public  Collection<ULDTypeVO> findULDTypes(ULDTypeFilterVO uldTypeFilterVO)
			throws SystemException, RemoteException, ProxyException, BusinessDelegateException {
		return new MailController().findULDTypes(uldTypeFilterVO);
	}
	public void stampACC3IdentifierForPreviousLegMailBags(
	            Collection<OperationalFlightVO> operationalFlightVOs) throws SystemException,
	            RemoteException {
	        log.entering( MODULE, "stampACC3IdentifierForPreviousLegMailBags");
	        new MailController().stampACC3IdentifierForPreviousLegMailBags(operationalFlightVOs);
	}  
    @Override
	public ScannedMailDetailsVO validateContainerMailWeightCapture(MailUploadVO mailUploadVO) throws SystemException,
			RemoteException, MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException {
		   log.entering( MODULE, "validateFlightAndContainer");
	        try {
	        return new MailUploadController().validateContainerMailWeightCapture(mailUploadVO);
			} catch (ForceAcceptanceException e) {
				throw new MailHHTBusniessException(e);
			}
	}   
  @Override
public ContainerVO saveActualWeight(ContainerVO containerVO) throws SystemException, RemoteException,
		MailHHTBusniessException, MailMLDBusniessException, MailTrackingBusinessException {
	 log.entering( MODULE, "saveActualWeight");
     try {
     return new MailUploadController().saveActualWeight(containerVO);
		} catch (ForceAcceptanceException e) {
			throw new MailHHTBusniessException(e);
		}

}
 @Override
 public void publishMailOperationDataForRapidSystem(MailOperationDataFilterVO mailOperationDataFilterVO)throws SystemException, RemoteException{
	 log.entering( MODULE, "publishMailOperationDataForRapidSystem");
	 new MailController().publishMailOperationDataForRapidSystem(mailOperationDataFilterVO);
 }

 public ConsignmentDocumentVO generateConsignmentSummaryReportDtls(ConsignmentFilterVO consignmentFilterVO)
         throws SystemException, RemoteException, PersistenceException {
     return new DocumentController().generateConsignmentSummaryReportDtls(consignmentFilterVO);

 }

 @Override
 public TransferManifestVO generateTransferManifestReportDetails(String companyCode, String transferManifestId)
         throws SystemException, RemoteException, PersistenceException {
     return new MailController().generateTransferManifestReportDetails(companyCode,transferManifestId);
 }

 @Override
 public ConsignmentDocumentVO generateConsignmentSecurityReportDtls(ConsignmentFilterVO consignmentFilterVO)
         throws SystemException, RemoteException, PersistenceException {
     return new DocumentController().generateConsignmentSecurityReportDtls(consignmentFilterVO);
 }
 public  Collection<MailbagVO> generateMailTagDetails(Collection<MailbagVO> mailbagVOs)
	        throws SystemException,RemoteException {
	    return new MailController().generateMailTagDetails(mailbagVOs);
	}  
 public ConsignmentDocumentVO generateConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
 RemoteException, PersistenceException {
		return new DocumentController().generateConsignmentReportDtls(consignmentFilterVO);
	}
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentReportDtls(ConsignmentFilterVO consignmentFilterVO) throws SystemException,
RemoteException, PersistenceException {
	return new DocumentController().generateCN46ConsignmentReportDtls(consignmentFilterVO);
}
public Collection<ConsignmentDocumentVO> generateCN46ConsignmentSummaryReportDtls(
	ConsignmentFilterVO consignmentFilterVO) throws SystemException, RemoteException, PersistenceException {
	return new DocumentController().generateCN46ConsignmentSummaryReportDtls(consignmentFilterVO);
}
    @Override
    public MailbagVO fetchMailSecurityDetails(MailScreeningFilterVO mailScreeningFilterVo)
    		throws SystemException,RemoteException, PersistenceException {
    	 log.entering( MODULE, "fetchMailSecurityDetails");
    	 return new MailController().fetchMailSecurityDetails(mailScreeningFilterVo); 
    }
    @Override
    public String findRoutingDetails(String companyCode,long malseqnum)
    		throws SystemException,RemoteException, PersistenceException {
    	 log.entering( MODULE, "findRoutingDetails");
    	 return new MailController().findRoutingDetails(companyCode,malseqnum); 
    }
@Override
public Collection<MailStatusVO> generateMailStatusRT(MailStatusFilterVO mailStatusFilterVO)
		throws SystemException, RemoteException, PersistenceException {
	 log.entering( MODULE, "generateMailStatusRT");
 	 return new MailController().generateMailStatusRT(mailStatusFilterVO);
    }


@Override
public Collection<DailyMailStationReportVO> generateDailyMailStationRT(DailyMailStationFilterVO filterVO) 
		throws SystemException,RemoteException,ReportGenerationException{
	 log.entering( MODULE, "generateDailyMailStationRT");
 	 return new MailController().generateDailyMailStationRT(filterVO);
}
@Override
public Collection<DamagedMailbagVO> findDamageMailReport(DamageMailFilterVO damageMailReportFilterVO)
		throws SystemException, RemoteException, ReportGenerationException {
	 log.entering( MODULE, "generateDailyMailStationRT");
 	 return new MailController().findDamageMailReport(damageMailReportFilterVO);
}
@Override
public MailManifestVO findImportManifestDetails(OperationalFlightVO operationalFlightVo)
		throws SystemException, RemoteException, ReportGenerationException {
	 log.entering( MODULE, "findImportManifestDetails");
 	 return new MailController().findImportManifestDetails(operationalFlightVo);
}
}
