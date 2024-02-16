package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Controller
@RequestMapping("mail/operations/outbound")
public class OutboundActionController extends
		AbstractActionController<OutboundModel> {

	@Resource(name = "mailOutboundResourceBundle")
	ICargoResourceBundle mailOutboundResourceBundle;

	public ICargoResourceBundle getResourceBundle() {
		return mailOutboundResourceBundle;
	}

	@RequestMapping("/screenload")
	public @ResponseBody
	ResponseVO screenloadOutbound(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.screenload",
				outboundModel);
	}

	@RequestMapping("/list")
	public @ResponseBody
	ResponseVO listOutbound(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listoutbound",
				outboundModel);
	}

	@RequestMapping("/fetchFlightCapacityDetails")
	public @ResponseBody
	ResponseVO fetchFlightCapacityDetails(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.fetchFlightCapacityDetails",
				outboundModel);
	}
	@RequestMapping("/listContainers")
	public @ResponseBody
	ResponseVO listContainers(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listContainers",
				outboundModel);
	}

	@RequestMapping("/listMailbags")
	public @ResponseBody
	ResponseVO listMailbags(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listMailbags",
				outboundModel);
	}

	@RequestMapping("/listMailbagsdsnview")
	public @ResponseBody
	ResponseVO listMailbagsdsnview(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listMailbagsdsnview",
				outboundModel);
	}

	@RequestMapping("/closeFlight")
	public @ResponseBody
	ResponseVO closeFlight(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.closeFlight",
				outboundModel);
	}

	@RequestMapping("/reopenFlight")
	public @ResponseBody
	ResponseVO reopenFlight(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.reopenFlight",
				outboundModel);
	}

	@RequestMapping("/preAdvice")
	public @ResponseBody
	ResponseVO preadvice(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.preadvice",
				outboundModel);
	}

	@RequestMapping("/autoattachAWB")
	public @ResponseBody
	ResponseVO autoattachAWB(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.autoattachAWB",
				outboundModel);
	}
	@RequestMapping("/MailbagManifestPrint")
	public @ResponseBody
	ResponseVO mailbagManifestPrint(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.mailbagManifestPrint",
				outboundModel);
	}
	@RequestMapping("/AWBManifestPrint")
	public @ResponseBody
	ResponseVO awbManifestPrint(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.awbManifestPrint",
				outboundModel);
	}
	@RequestMapping("/DSNMailbagManifestPrint")
	public @ResponseBody
	ResponseVO dsnManifestPrint(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.dsnMailbagManifestPrint",
				outboundModel);
	}
	@RequestMapping("/DestCategoryManifestPrint")
	public @ResponseBody
	ResponseVO destCategoryManifestPrint(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.destCategoryManifestPrint",
				outboundModel);
	}
	@RequestMapping("/checkCloseFlight")
	public @ResponseBody
	ResponseVO checkCloseFlight(
			@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction(
				"mail.operations.ux.outbound.checkCloseFlight",
				outboundModel);
	}
	@RequestMapping("/screenloadAddModifyContainer")
	public @ResponseBody
	ResponseVO screenloadAddModifyContainer(
			@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction(
				"mail.operations.ux.outbound.screenloadAddModifyContainer",
				outboundModel);
	}

	@RequestMapping("/onListNewContainer")
	public @ResponseBody
	ResponseVO onListNewContainer(
			@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction(
				"mail.operations.ux.outbound.onListNewContainer",
				outboundModel);
	}
	@RequestMapping("/onAddContainer")
	public @ResponseBody
	ResponseVO saveContainer(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.onAddContainer",
				outboundModel);
	}
	
	@RequestMapping("/onModifyContainer")
	public @ResponseBody
	ResponseVO modifyContainer(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.modifyContainer",
				outboundModel);
	}

	@RequestMapping("/AddMailbag")
	public @ResponseBody
	ResponseVO addMailbags(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.addMailbags",
				outboundModel);
	}

	@RequestMapping("/importMailbags")
	public @ResponseBody
	ResponseVO importMailbags(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.importMailbags",
				outboundModel);
	}
	@RequestMapping("/validateFlight")
	public @ResponseBody
	ResponseVO validateFlight(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.validateFlight",
				outboundModel);
	}

	@RequestMapping("/validateFlightDetailsForEnquiry")
	public @ResponseBody
	ResponseVO validateFlightDetailsForEnquiry(
			@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction(
				"mail.operations.ux.outbound.validateFlightDetailsForEnquiry",
				outboundModel);
	}

	@RequestMapping("/listCardit")
	public @ResponseBody
	ResponseVO carditListView(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listCardit",
				outboundModel);
	}

	@RequestMapping("/listLyingList")
	public @ResponseBody
	ResponseVO lyingListView(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listLyingList",
				outboundModel);
	}
	
	@RequestMapping("/validateContainerForReassign")
	public @ResponseBody
	ResponseVO validateContainerForReassign(@RequestBody ListContainerModel listContainer)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.listcontainer.validateContainerForReassign",listContainer);
	}
	
	@RequestMapping("/validateContainerForReassignss")
	public @ResponseBody
	ResponseVO validateContainerForReassign11(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.listcontainer.validateContainerForReassign",outboundModel);
	}
	@RequestMapping("/screenloadattachawb")
	public @ResponseBody ResponseVO ScreenLoadAttachAwbCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.containerScreenloadattachawb",outboundModel);
	}
	@RequestMapping("/listattachawb")
	public @ResponseBody ResponseVO ListAttachAwbCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.listAttachAwbCommand",outboundModel);
	}
	@RequestMapping("/saveattachawb")
	public @ResponseBody ResponseVO SaveAttachAwbCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.saveAttachAwbCommand",outboundModel);
	}
	@RequestMapping("/detachAWBCommand")
	public @ResponseBody ResponseVO DetachAttachAwbCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.detachAWBCommand",outboundModel);
	}
	@RequestMapping("/validateAttachRouting")
	public @ResponseBody ResponseVO validateAttachRoutingCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.validateAttachRouting",outboundModel);
	}
	@RequestMapping("/listAttachRouting")
	public @ResponseBody ResponseVO listAttachRoutingCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.listAttachRouting",outboundModel);
	}
	@RequestMapping("/saveAttachRouting")
	public @ResponseBody ResponseVO saveAttachRoutingCommand(@RequestBody OutboundModel outboundModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.saveAttachRouting",outboundModel);
	}
	@RequestMapping("/screenloadReturnMail")
	public @ResponseBody ResponseVO screenloadReturnMailCommand(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
			return performAction("mail.operations.ux.mailoutbound.screenloadReturnMail",outboundModel);
	}
	
	@RequestMapping("/deletecontainer")
	public @ResponseBody ResponseVO deleteEmptyContainer(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
			return performAction("mail.operations.ux.mailoutbound.deleteEmptyContainer",outboundModel);
	}
	
	
	
	/**
	 * 
	 * @author A-5437
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/unassignContainer")
	public @ResponseBody ResponseVO unassignContainer(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.unassignContainer",listContainer);
	}
	
	/**
	 * 
	 * 	Method		:	ListContainerActionController.reassignContainer
	 *	Added by 	:	a-7779 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param reassignContainer
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/reassignContainer")
	public @ResponseBody ResponseVO reassignContainer(@RequestBody ListContainerModel listContainer)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.reassignContainer",listContainer);
	}
	
	@RequestMapping("/listDeviationList")
	public @ResponseBody
	ResponseVO listDeviationList(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.listDeviationList",outboundModel);
	}
	
	@RequestMapping("/populateMailbagId")
	public @ResponseBody ResponseVO populateMailbagId(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.populateMailbagId",outboundModel);
	}
	
	@RequestMapping("/reassignExistingMailbags")
	public @ResponseBody ResponseVO reassignExistingMailbags(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.reassignExistingMailbags",outboundModel);
	}
	
	@RequestMapping("/sendULDAnnounce")
	public @ResponseBody ResponseVO sendULDAnnounce(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.sendULDAnnounce",outboundModel);
	}
	@RequestMapping("/showImportPopUp")
	public @ResponseBody ResponseVO showImportPopUp(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.mailoutbound.showImportPopUp",outboundModel);
	}	

	@RequestMapping("/markUnmarkIndicator")
	public @ResponseBody ResponseVO SaveULDFullIndictorCommand(@RequestBody ListContainerModel listContainer)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.mailoutbound.markUnmarkIndicator", listContainer);
	}
	
	@RequestMapping("/fetchFlightPreAdviceDetails")
	public @ResponseBody
	ResponseVO fetchFlightPreAdviceDetails(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.fetchFlightPreAdviceDetails",
				outboundModel);
	}
	@RequestMapping("/PrintCNForFlight")
	public @ResponseBody
	ResponseVO cN46PrintCommand(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.printCNForFlight",
				outboundModel);
	}
	@RequestMapping("/fetchFlightVolumeDetails")
	public @ResponseBody
	ResponseVO fetchFlightVolumeDetails(@RequestBody OutboundModel outboundModel)
			throws BusinessDelegateException, SystemException {
		return performAction("mail.operations.ux.outbound.fetchFlightVolumeDetails",
				outboundModel);
	}
}
