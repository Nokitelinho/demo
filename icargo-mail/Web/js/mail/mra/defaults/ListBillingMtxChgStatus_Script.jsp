<%@ include file="/jsp/includes/js_contenttype.jsp" %>
function screenSpecificEventRegister()
{
	var frm=targetFormName;

	with(targetFormName){
	evtHandler.addEvents("btnSave","saveDetails()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	}
    //onLoadPopuplist(targetFormName);
	//onLoadPopup(targetFormName);


}

function saveDetails(){
	submitForm(targetFormName,"mailtracking.mra.defaults.maintainbillingmatrix.savechangestatus.do");
}
function closeScreen(){
	window.close();
}