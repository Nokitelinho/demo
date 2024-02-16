<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnSave","submitAction('listDetails',targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeMailPopup()",EVT_CLICK);
		


	}

	onScreenLoad();
}

function onScreenLoad(){

}
 function submitAction(actiontype,targetFormName){
	var frm = targetFormName;
	 var type_action=actiontype;
	var remarks=targetFormName.elements.remarks.value;
	if((remarks == "" || remarks == null)){
	showDialog({
				msg		:	"Remark is mandatory",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
	}
if(type_action == 'listDetails') {
	window.opener.targetFormName.action=appPath+"/mail.mra.gpareporting.ux.listinvoic.reject.do?rejectFlag=true"+"&remarks="+remarks;
	window.opener.IC.util.common.childUnloadEventHandler();
	window.opener.targetFormName.submit();
	window.close();
	//submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.reject.do");
	//window.close();
}




}


function confirmMessage() {
    
    openPopUp("mail.mra.gpareporting.ux.listinvoic.rejectConfirmation.do",500,200);
}

function closeMailPopup(){
    var frm=targetFormName;
	submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.remarksClose.do");
   window.close();
}

