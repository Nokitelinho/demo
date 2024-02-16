package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.operations.MailinboundActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	28-Nov-2018		:	Draft
 *
 */
@Controller
@RequestMapping("mail/operations/mailinbound")
public class MailinboundActionController extends AbstractActionController<MailinboundModel> {

	@Resource(name = "mailInboundResourceBundle")
	ICargoResourceBundle mailInboundResourceBundle;
	public ICargoResourceBundle getResourceBundle() {
		return mailInboundResourceBundle;
	}
	
	@RequestMapping("/listflightdetails")
	public @ResponseBody ResponseVO listMailinboundDetails(@RequestBody MailinboundModel listMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listflightdetails",listMailinbound);
	}
	
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadMailinbound(@RequestBody MailinboundModel screenLoadMailInbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.mailinboundscreenload",screenLoadMailInbound);
	}
	
	@RequestMapping("/ListContainers")
	public @ResponseBody ResponseVO ListContainersDetails(@RequestBody MailinboundModel listContainerMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listcontainersdetails",listContainerMailinbound);
	}
	
	@RequestMapping("/ListMailDsn")
	public @ResponseBody ResponseVO ListMailbagDetailsCommand(@RequestBody MailinboundModel listMailbagMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listmaildsndetails",listMailbagMailinbound);
	}
	
	@RequestMapping("/addContainer")
	public @ResponseBody ResponseVO AddContainerCommand(@RequestBody MailinboundModel addContainerMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.addcontainer",addContainerMailinbound);
	}
	
	@RequestMapping("/closeFlight")
	public @ResponseBody ResponseVO CloseFlightCommand(@RequestBody MailinboundModel closeFlightrMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.closeflight",closeFlightrMailinbound);
	}
	
	@RequestMapping("/reopenFlight")
	public @ResponseBody ResponseVO ReopenFlightCommand(@RequestBody MailinboundModel reopenFlightrMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.reopenflight",reopenFlightrMailinbound);
	}
	
	@RequestMapping("/autoAttachAwb")
	public @ResponseBody ResponseVO AutoAttachAWBCommand(@RequestBody MailinboundModel autoAttachAwbMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.autoattachawb",autoAttachAwbMailinbound);
	}
	
	@RequestMapping("/arriveMail")
	public @ResponseBody ResponseVO ArriveMailCommand(@RequestBody MailinboundModel arriveMailMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.arrivemail",arriveMailMailinbound);
	}
	
	@RequestMapping("/addMailbags")
	public @ResponseBody ResponseVO AddMailbagCommand(@RequestBody MailinboundModel addMailBagMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.addmailbags",addMailBagMailinbound);
	}
	
	@RequestMapping("/transfercontainer")
	public @ResponseBody ResponseVO TransferContainerCommand(@RequestBody MailinboundModel transferContainerMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.transfercontainer",transferContainerMailinbound);
	}
	
	@RequestMapping("/discrepancy")
	public @ResponseBody ResponseVO LoadDiscrepancyCommand(@RequestBody MailinboundModel discrepancyMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.discrepancy",discrepancyMailinbound);
	}
	
	@RequestMapping("/acquiltuld")
	public @ResponseBody ResponseVO AcquitUldCommand(@RequestBody MailinboundModel acquiltUldMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.acquiltuld",acquiltUldMailinbound);
	}
	
	@RequestMapping("/delivermail")
	public @ResponseBody ResponseVO DeliverMailCommand(@RequestBody MailinboundModel deliverMailMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.delivermail",deliverMailMailinbound);
	}
	
	@RequestMapping("/listattachawb")
	public @ResponseBody ResponseVO ListAttachAwbCommand(@RequestBody MailinboundModel listAttachAwbMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listattachawb",listAttachAwbMailinbound);
	}
	
	@RequestMapping("/listmailarrival")
	public @ResponseBody ResponseVO ListMailArrivalCommand(@RequestBody MailinboundModel listMailArrivalMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listmailarrival",listMailArrivalMailinbound);
	}
	
	@RequestMapping("/saveattachawb")
	public @ResponseBody ResponseVO SaveAttachAwbCommand(@RequestBody MailinboundModel SaveAttachAwbMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.saveattachawb",SaveAttachAwbMailinbound);
	}
	
	@RequestMapping("/screenloadattachawb")
	public @ResponseBody ResponseVO ScreenLoadAttachAwbCommand(@RequestBody MailinboundModel screenLoadAttachAwbMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.screenloadattachawb",screenLoadAttachAwbMailinbound);
	}
	
	@RequestMapping("/transfermail")
	public @ResponseBody ResponseVO TransferMailCommand(@RequestBody MailinboundModel transferMailMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.transfermail",transferMailMailinbound);
	}
	
	@RequestMapping("/undoarrival")
	public @ResponseBody ResponseVO UndoMailArrivalCommand(@RequestBody MailinboundModel undoArrivalMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.undoarrival",undoArrivalMailinbound);
	}
	
	@RequestMapping("/validatetransfercontainer")
	public @ResponseBody ResponseVO ValidateTransferContainerCommand(@RequestBody MailinboundModel validateTransferMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.validatetransfercontainer",validateTransferMailinbound);
	}
	
	@RequestMapping("/validatechangecontainer")
	public @ResponseBody ResponseVO ValidateContainerChangeCommand(@RequestBody MailinboundModel validateChangeContainerMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.validatechangecontainer",validateChangeContainerMailinbound);
	}
	
	@RequestMapping("/savechangecontainer")
	public @ResponseBody ResponseVO SaveContainerChangeCommand(@RequestBody MailinboundModel saveChangeContainerMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.savechangecontainer",saveChangeContainerMailinbound);
	}
	
	@RequestMapping("/changescantime")
	public @ResponseBody ResponseVO ChangeScanTimeCommand(@RequestBody MailinboundModel changeScanTimeMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.changescantime",changeScanTimeMailinbound);
	}
	
	@RequestMapping("/savemailarrival")
	public @ResponseBody ResponseVO SaveMailArrivalCommand(@RequestBody MailinboundModel saveMailArrivalMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.savemailarrival",saveMailArrivalMailinbound);
	}
	
	@RequestMapping("/savedamagecapture")
	public @ResponseBody ResponseVO SaveMailDamageCommand(@RequestBody MailinboundModel saveDamageCaptureMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.savedamagecapture",saveDamageCaptureMailinbound);
	}
	
	@RequestMapping("/screenloaddamagecapture")
	public @ResponseBody ResponseVO ListMailDamageCommand(@RequestBody MailinboundModel screenloadDamageCaptureMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.screenloaddamagecapture",screenloadDamageCaptureMailinbound);
	}
	
	@RequestMapping("/validateattachrouting")
	public @ResponseBody ResponseVO ValidateAttachRoutingCommand(@RequestBody MailinboundModel validateAttachRoutingeMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.validateattachrouting",validateAttachRoutingeMailinbound);
	}
	
	@RequestMapping("/listattachrouting")
	public @ResponseBody ResponseVO ListAttachRoutingCommand(@RequestBody MailinboundModel listAttachRoutingMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.listattachrouting",listAttachRoutingMailinbound);
	}
	
	@RequestMapping("/saveattachrouting")
	public @ResponseBody ResponseVO SaveAttachRoutingCommand(@RequestBody MailinboundModel saveAttachRoutingMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.saveattachrouting",saveAttachRoutingMailinbound);
	}
	
	@RequestMapping("/validatemailchange")
	public @ResponseBody ResponseVO ValidateMailChangeCommand(@RequestBody MailinboundModel validateMailChangeMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.validatemailchange",validateMailChangeMailinbound);
	}
	
	@RequestMapping("/savechangemail")
	public @ResponseBody ResponseVO SaveMailChangeCommand(@RequestBody MailinboundModel saveMailChangeMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.savechangemail",saveMailChangeMailinbound);
	}
	
	@RequestMapping("/validatetransfermail")
	public @ResponseBody ResponseVO ValidateTransferMailCommand(@RequestBody MailinboundModel validateMailTransferMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.validatetransfermail",validateMailTransferMailinbound);
	}
	
	@RequestMapping("/generatemanifest")
	public @ResponseBody ResponseVO GenerateManifestCommand(@RequestBody MailinboundModel generateManifestMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.generatemanifest",generateManifestMailinbound);
	}
	
	@RequestMapping("/readyfordelivery")
	public @ResponseBody ResponseVO ReadyForDeliveryCommand(@RequestBody MailinboundModel readyfordeliveryMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.readyfordelivery",readyfordeliveryMailinbound);
	}

	@RequestMapping("/populatemailbag")
	public @ResponseBody ResponseVO PopulateMailbagDetailsCommand(@RequestBody MailinboundModel populateMailbagMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.populatemailbag",populateMailbagMailinbound);
	}
	@RequestMapping("/list")
	public @ResponseBody ResponseVO listOutbound(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listoutbound",outboundModel);
	}
	@RequestMapping("/listContainersOutbound")
	public @ResponseBody ResponseVO listContainers(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listContainers", outboundModel);
	}
	@RequestMapping("/saveContainerDetailsForEnquiry")
	public @ResponseBody ResponseVO saveContainerDetailsForEnquiry(@RequestBody MailbagEnquiryModel mailbagEnquiryModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailbagenquiry.saveContainerDetailsForEnquiry", mailbagEnquiryModel);
	}
	@RequestMapping("/transferManifestPrint")
	public @ResponseBody ResponseVO TransferManifestPrintCommand(@RequestBody MailinboundModel transferManifestMailinbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.transferManifestPrint",transferManifestMailinbound);
	}
	@RequestMapping("/removemailbag")
	public @ResponseBody ResponseVO removeMailbag(@RequestBody MailinboundModel updateRetainFlagInbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.removemailbag",updateRetainFlagInbound);
	}
	@RequestMapping("/updateRetainFlagForContainer")
	public @ResponseBody ResponseVO updateRetainFlagForContainer(@RequestBody MailinboundModel updateRetainFlagInbound)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.updateRetainFlagForContainer",updateRetainFlagInbound);
	}

	@RequestMapping("/detachAWBCommand")
	public @ResponseBody ResponseVO detachAttachAwbCommand(@RequestBody MailinboundModel inboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailinbound.detachAWBCommand",inboundModel);
	}

	@RequestMapping("/printCN46ForFlightInbound")
	public @ResponseBody ResponseVO cN46PrintCommand(@RequestBody MailinboundModel inboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailinbound.printCN46ForFlightInbound", inboundModel);
	}

}
