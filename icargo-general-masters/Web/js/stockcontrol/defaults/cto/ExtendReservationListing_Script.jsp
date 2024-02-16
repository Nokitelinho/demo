<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = document.forms[0];

	with(frm) {

		evtHandler.addEvents("PopupClose","popupCloseAction(this.form)",EVT_CLICK);
		//evtHandler.addEvents("OkCancel","okCancelAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("OkExtend","okExtendAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("cancelRemarks","validateFields(this,-1,'Remarks',0,true,true)",EVT_BLUR);
	}
	checkError();

}


function okCancelAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.OkCancelCommand.do";
	frm.submit();
}

function okExtendAction(frm) {
	frm.action = "stockcontrol.defaults.reservationlisting.OkExtendCommand.do";
	frm.submit();
 }
 
function popupCloseAction(frm) {

	frm.action = "stockcontrol.defaults.reservationlisting.CloseCancelCommand.do";
	frm.submit();
}
 
function fnToNavigate(strLastPageNum, strDisplayPage) {

   	var frm = document.forms[0];
 	frm.elements.lastPageNum.value = strLastPageNum;
 	frm.elements.displayPage.value = strDisplayPage;

 	var action = "stockcontrol.defaults.reservationlisting.ExtendNavigateCommand.do";
 	submitForm(frm, action);
}
 
function checkError() {

  	var frm = document.forms[0];
  	var errorPresent = frm.elements.errorStatus.value;

  	if(errorPresent == "N") {
		frm.elements.errorStatus.value="";
        window.opener.recreateTableDetails('stockcontrol.defaults.reservationlisting.ListCommand.do','div1');
		//window.opener.document.forms[1].action="stockcontrol.defaults.reservationlisting.ListCommand.do";
		//window.opener.document.forms[1].submit();
 		window.close();
  	}
}