<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister(){
   var frm=targetFormName;
	onScreenload();
   with(targetFormName){
   	//CLICK Events
     	evtHandler.addEvents("btList","listMailAuditHistoryDetails()",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearMailAuditHistory()",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeMailAuditHistory()",EVT_CLICK);
   	}
}
function closeMailAuditHistory(){
		window.close();
}
function clearMailAuditHistory(){
	submitForm(targetFormName,'mailtracking.defaults.mailBagaudithistoryclear.do');
}
function listMailAuditHistoryDetails(){
	submitForm(targetFormName,'mailtracking.defaults.mailBagaudithistorylist.do');
}
function onScreenload(){
}
function onTransactionNameChanged(frm){
if('DELETEMAL'==frm.transaction.value){
frm.auditableField.value=null;
disableField(frm.auditableField);
}else {
enableField(frm.auditableField);
}
}