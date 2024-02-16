<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];

	with(frm){

		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("clearButton","submitForm(this.form,'clearRateCardLOVDetails.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		
	}
}




