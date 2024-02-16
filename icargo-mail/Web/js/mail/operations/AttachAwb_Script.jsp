<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

/**
  *Method for excecuting after confirmation
  */
 function confirmPopupMessage(){

   frm = targetFormName;
   frm.elements.stdPieces.readOnly = false;
   		frm.elements.stdWeight.readOnly = false;
   		frm.elements.weightStandard.disabled = false;
   		frm.elements.shipmentDesc.readOnly = false;
   		frm.elements.btSave.disabled = false;
   
   		frm.elements.btList.disabled = true;
   		frm.elements.shipmentPrefix.readOnly = true;
   		frm.elements.documentNumber.readOnly = true;
		frm.elements.stdPieces.focus();
   
 }
 function nonconfirmPopupMessage(){
  var frm=targetFormName;
		frm.elements.btList.disabled = false;
		frm.elements.stdPieces.readOnly = true;
		frm.elements.stdWeight.readOnly = true;
		frm.elements.weightStandard.disabled = true;
		frm.elements.shipmentDesc.readOnly = true;
		frm.elements.btSave.disabled = true;
		frm.elements.shipmentPrefix.focus();
}

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad(frm);
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btList","listAttachAwb()",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearAttachAwb()",EVT_CLICK);
		evtHandler.addEvents("btSave","saveAttachAwb()",EVT_CLICK);
		evtHandler.addEvents("btClose","closeAttachAwb()",EVT_CLICK);
   }

}

function onScreenLoad(frm){

	var status = frm.elements.screenStatus.value;
	if(status == "SCREENLOAD"){
		frm.elements.stdPieces.readOnly = true;
		frm.elements.stdWeight.readOnly = true;
		frm.elements.weightStandard.disabled = true;
		frm.elements.shipmentDesc.readOnly = true;
		frm.elements.btSave.disabled = true;
		frm.elements.shipmentPrefix.focus();
	}
	else if(status == "LISTED"){
		frm.elements.stdPieces.readOnly = false;
		frm.elements.stdWeight.readOnly = false;
		frm.elements.weightStandard.disabled = false;
		frm.elements.shipmentDesc.readOnly = false;
		frm.elements.btSave.disabled = false;

		frm.elements.btList.disabled = true;
		frm.elements.shipmentPrefix.readOnly = true;
		frm.elements.documentNumber.readOnly = true;
		frm.elements.stdPieces.focus();
	}
	else if(status == "CLOSE"){
		if(frm.elements.fromScreen.value!="MTK007"){//modified by a-7871 for ICRD-262855
		window.opener.targetFormName.action = "mailtracking.defaults.mailmanifest.listmailmanifest.do";
		window.opener.targetFormName.submit();				
		window.closeNow();
		}else{
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.targetFormName.action = "mailtracking.defaults.mailarrival.listflight.do";
		window.opener.targetFormName.submit();				
		window.closeNow();
		}
	}
}

function listAttachAwb(){
    window.opener.IC.util.common.childUnloadEventHandler();
	submitForm(targetFormName,'mailtracking.defaults.attachawb.list.do');
}

function clearAttachAwb(){

	submitForm(targetFormName,'mailtracking.defaults.attachawb.clear.do');
}

function saveAttachAwb(){
    window.opener.IC.util.common.childUnloadEventHandler();
	submitForm(targetFormName,'mailtracking.defaults.attachawb.save.do');
}

function closeAttachAwb(){
     window.opener.IC.util.common.childUnloadEventHandler();
	submitForm(targetFormName,'mailtracking.defaults.attachawb.close.do');
}