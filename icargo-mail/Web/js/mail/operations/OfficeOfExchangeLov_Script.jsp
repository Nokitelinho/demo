<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	frm.elements.code.focus();
	with(frm){
		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
	}
}