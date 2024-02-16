<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister() {
	
	var frm = targetFormName;
	with(frm) {

		evtHandler.addEvents("btnUpload","uploadfn()",EVT_CLICK);	
		evtHandler.addEvents("btnClose","closefn()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		if(frm.elements.theFile.disabled == false){
			frm.elements.theFile.focus();
		}
	}
}

function uploadfn(){
	//alert("theFile"+targetFormName.theFile.value);
	submitForm(targetFormName,'mailtracking.mra.gpareporting.uploadfileuploadgpareport.do');
}

function closefn(){

	submitForm(targetFormName,'mailtracking.mra.gpareporting.closeuploadgpareport.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.theFile.disabled == false && targetFormName.elements.theFile.readOnly== false){
			targetFormName.elements.theFile.focus();
		}
	}
}
