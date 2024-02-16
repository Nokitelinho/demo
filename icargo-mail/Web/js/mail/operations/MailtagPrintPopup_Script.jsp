<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister()
{

	var frm=targetFormName;
	with(frm){
	
	
				evtHandler.addEvents("btnPrint","submitAction('print')", EVT_CLICK);
				evtHandler.addEvents("printMailTag","enablePrintButton()",EVT_CHANGE);//added by a-7871 for ICRD-263219
	}
	 onScreenLoad();
}
function onScreenLoad(){
 var frm=targetFormName;
disableField(frm.elements.btnPrint);//added by a-7871 for ICRD-263219
  }
function submitAction(actionType){		
	if(actionType == 'print'){
		 frm=targetFormName;
   generateReport(frm,'/mailtracking.defaults.consignment.printmailtagpopup.do');
	}
}
function enablePrintButton(){
 var frm=targetFormName;
enableField(frm.elements.btnPrint);//added by a-7871 for ICRD-263219
}

