<%@ include file="/jsp/includes/js_contenttype.jsp" %>



window.onload=screenSpecificEventRegister;

function screenSpecificEventRegister() {

	var frm = targetFormName;
	

	with(frm) {
	
	evtHandler.addEvents("btAttach","selectAction(targetFormName, 'ATTACH')",EVT_CLICK);
	evtHandler.addEvents("btCancel","selectAction(targetFormName, 'CANCEL')",EVT_CLICK);
		
	}
	
	reloadParent();
}

	function selectAction(frm,actionType) {

		if(actionType=="ATTACH") {

 		  submitForm(targetFormName,'mail.mra.airlinebilling.inward.attachfile.do');
		}

		if(actionType=="CANCEL") {

			window.close();

		}
	}
	
	
	function reloadParent(){
	
		var frm = targetFormName;
	
		if(frm.elements.actionStatus.value == "Y"){
			
			window.opener.recreateTableForDetails("mail.mra.airlinebilling.inward.refreshrejectionmemoattachment.do","divDocpanel");
			window.close();

			}
      	 }
