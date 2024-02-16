<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){
		evtHandler.addEvents("listButton","callList()",EVT_CLICK);
		evtHandler.addEvents("clearButton","clearLOVForm()",EVT_CLICK);
		evtHandler.addEvents("closeButton","window.close()",EVT_CLICK);
	}
}