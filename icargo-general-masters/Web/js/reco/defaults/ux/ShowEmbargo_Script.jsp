<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
  var frm=targetFormName;
    onloadThispage();
    with(frm){
   	evtHandler.addEvents("btContinue","oncontinue()",EVT_CLICK);
   	evtHandler.addEvents("btClose","onClose()",EVT_CLICK);
   }
}
function oncontinue() {
	var embargoFrm = targetFormName;
    if(embargoFrm.elements.continueAction.value != "")  {

		var actionCode =embargoFrm.elements.continueAction.value + ".do";

		if(targetFormName.elements.continueAction.value == 'capacity.booking.ViewSummaryDetails'){
			actionCode = actionCode+"?popUpStatus=viewSummary";
		}
		if(targetFormName.elements.continueAction.value == 'capacity.booking.freeformat.SaveReservationDetails'){
			actionCode = actionCode+"?popUpStatus=bookingSummary";

		} 
		if(targetFormName.elements.continueAction.value == 'capacity.booking.assigntoflightcommandfromFlightMonitoring'){
		
		actionCode = actionCode+"?canDiscardEmbargo=true";
		} else if((targetFormName.elements.continueAction.value == 'operations.shipment.ux.cto.addSingleRowAcceptance') ||
		(targetFormName.elements.continueAction.value == 'operations.shipment.ux.cto.awbacceptance.addSingleDimAcceptance') ||
		(targetFormName.elements.continueAction.value == 'operations.shipment.ux.cto.savectoacceptancedetails') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.reassignshipment') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.offloadshipmentsave') ||
		(targetFormName.elements.continueAction.value == 'operations.shipment.saveramptransfer') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.dangerousgoodsassignment.autoassigncommand') ||
		(targetFormName.elements.continueAction.value == 'mail.operations.ux.outbound.addMailbags') ||
		(targetFormName.elements.continueAction.value == 'mail.operations.ux.mailinbound.addmailbags') ||
		(targetFormName.elements.continueAction.value == 'mail.operations.ux.mailinbound.arrivemail')){
			actionCode = actionCode+"?canCheckEmbargo=false";
		}
		stripMoneyEntryValuestoNumberOnlyString();
		if(window.parent.parentContext=="react"){
			window.parent.appBridge.dispatchAction({type:'UPDATE_POPUP_HANDLER',fromPopup:'embargopopup'});
			popupUtils.closePopupDialog();
		}else{
				if(parent.document.forms[1].elements.canCheckEmbargo != null){
						parent.document.forms[1].elements.canCheckEmbargo.value = false;
				}
			submitForm(parent.document.forms[1], actionCode);
			popupUtils.closePopupDialog();

		
		}
	}
}
  function onClose(){
	if(window.parent.parentContext=="react"){
		window.parent.appBridge.dispatchAction({type:'UPDATE_POPUP_HANDLER',popupAction:'closeembargopopup'});
		popupUtils.closePopupDialog();
	}else{
		window.close();
	}
}
function onloadThispage() {
	var embargoFrm = targetFormName;

	if(embargoFrm.elements.continueAction.value == "")  {
		embargoFrm.elements.btContinue.disabled = true;
	}
}