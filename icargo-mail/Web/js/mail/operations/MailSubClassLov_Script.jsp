<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;

	with(frm){
		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);//Added as part of ICRD-145967
		//evtHandler.addEvents("btnClear","submitForm(this.form,'mailtracking.defaults.subclaslov.clear.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
	}
}

