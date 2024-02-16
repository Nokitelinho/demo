<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
	
function screenSpecificEventRegister(){
	var frm=targetFormName;

	with(frm){

		evtHandler.addEvents("btnList","submitForm(this.form,'mailtracking.defaults.mailboxidlov.list.do')",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'mailtracking.defaults.mailboxidlov.clear.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
	}
}