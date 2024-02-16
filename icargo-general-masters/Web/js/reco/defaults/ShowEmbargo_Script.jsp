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


/*function oncontinue() {
	var embargoFrm = document.forms[0];

	if(embargoFrm.continueAction.value != "")  {
		if(window.opener.document.forms[1] != null) {


			stripMoneyEntryValuestoNumberOnlyString();


			stripMoneyEntryValuestoNumberOnlyString();
			window.close();
			if(window.opener.document.forms[1].canCheckEmbargo != null){
				window.opener.document.forms[1].canCheckEmbargo.value = false;
			}
			if(window.opener.document.forms[1].actionStatus != null){
				window.opener.document.forms[1].actionStatus.value = "from_popup";
			}

			window.opener.document.forms[1].action = embargoFrm.continueAction.value + ".do";
			window.opener.document.forms[1].submit();


		}
		// if the screen is invoked from a popup
		else {
			window.close();
			window.opener.document.forms[0].canCheckEmbargo.value = false;
			window.opener.document.forms[0].action = embargoFrm.continueAction.value + ".do";
			window.opener.document.forms[0].submit();
		}
	}
}*/
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
		if(targetFormName.elements.continueAction.value == 'operations.flthandling.savenotocdetails'){
			var notocAction = embargoFrm.elements.notocAction.value ;
			actionCode = actionCode+"?status="+notocAction;
		}
		if(targetFormName.elements.continueAction.value == 'capacity.booking.assigntoflightcommandfromFlightMonitoring'){
		
		actionCode = actionCode+"?canDiscardEmbargo=true";
		} else if((targetFormName.elements.continueAction.value == 'operations.shipment.savectoacceptancedetails') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.savemanifestdetails') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.saveandprintmanifestreport') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.reassignshipment') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.offloadshipmentsave') ||
		(targetFormName.elements.continueAction.value == 'operations.shipment.saveramptransfer') ||
		(targetFormName.elements.continueAction.value == 'operations.flthandling.dangerousgoodsassignment.autoassigncommand')){
			actionCode = actionCode+"?canCheckEmbargo=false";
		}
		stripMoneyEntryValuestoNumberOnlyString();
		if(targetFormName.elements.continueAction.value == 'capacity.allotment.saveCustomerAllotmentDetails'){
			if(window.opener.targetFormName.elements.doCheckEmbargo != null){
				window.opener.targetFormName.elements.doCheckEmbargo.value = false;
			}
		}
		else{
			//modified by A-3767 on 23Feb11 for bug 107711
			//replaced doCheckEmbargo with canCheckEmbargo for bug 108268 by Vivek.Perla on 11Mar2011
			if(window.opener.targetFormName.elements.canCheckEmbargo != null && window.opener.targetFormName.elements.canCheckEmbargo.length != 0){
						window.opener.targetFormName.elements.canCheckEmbargo.value = false;
			}
			//Added by A-3767 on 23Feb11 for bug 107711
			if(window.opener.targetFormName.elements.doCheckEmbargo != null && window.opener.targetFormName.elements.doCheckEmbargo.length != 0){
						window.opener.targetFormName.elements.doCheckEmbargo.value = false;
			}
		}

        //window.opener.targetFormName.popupCloseFlag.value = "Y";
		window.opener.submitForm(window.opener.targetFormName, actionCode);
		window.close();
	}
	if (window.parent.parentContext=="react") {
		frameId=window.parent.popupUtils.getOpenerReference(window.frameElement.id).frameId;
        if(typeof(window.parent.document.getElementById(frameId).contentWindow.appBridge.dispatchAction) != "undefined"){
			window.parent.document.getElementById(frameId).contentWindow.appBridge.dispatchAction({type:"CLOSE_UX_POPUP",data:"embargoPopup", warningCode:"capacity.booking.embargowarningexist"});
            popupUtils.closePopupDialog();
		}
	}
}

function onClose(){
	if(window.parent.parentContext=="react" ){
		popupUtils.closePopupDialog();
	}else{
		window.close();
	}
}

function onloadThispage() {
	var embargoFrm = targetFormName;

	if(embargoFrm.elements.continueAction.value == "" && window.parent.parentContext!="react")  {
		embargoFrm.elements.btContinue.disabled = true;
	}
}