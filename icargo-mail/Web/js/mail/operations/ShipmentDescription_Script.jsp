<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btOk","autoAttachAWB()",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancelAutoAttachAWB()",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancelFocus()",EVT_BLUR);
   }

}

function autoAttachAWB(){
	frm=targetFormName;	
	var selectMail =targetFormName.elements.parentContainer.value;
	var selectDSN =targetFormName.elements.selectChild.value;
	var attachAWB =targetFormName.elements.autoAttach.value;	
	var shipmentDesc = targetFormName.elements.shipmentDesc.value;
	
	frm = self.opener.targetFormName;
	frm.elements.selectChild.value=selectDSN;
	frm.elements.parentContainer.value=selectMail;	
	frm.elements.autoAttach.value=attachAWB;		
	frm.elements.shipmentDesc.value=shipmentDesc;	
	frm.action="mailtracking.defaults.mailmanifest.autoattachawb.do";
	
	frm.elements.method="post";
	frm.submit();
	window.closeNow();
	return;	
}

function cancelAutoAttachAWB(){
    window.closeNow();
}
function cancelFocus(){
    targetFormName.elements.shipmentDesc.focus();
}