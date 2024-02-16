<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){	
		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'mra.airlinebilling.defaults.clearMemoLov.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
	}
}
function submitList(strLastPageNum,strDisplayPage){
	document.forms[0].lastPageNum.value= strLastPageNum;
	document.forms[0].displayPage.value = strDisplayPage;
	document.forms[0].submit();
	disablePage();
}