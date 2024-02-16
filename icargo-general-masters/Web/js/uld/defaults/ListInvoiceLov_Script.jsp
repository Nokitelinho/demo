<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


//window.onload=initForm;
//function initForm()
function screenSpecificEventRegister()
{

	var frm=targetFormName;
	if(frm.elements.invoiceRefNum.disabled==false){
			frm.elements.invoiceRefNum.focus();
		}
	//onScreenLoad();
	with(frm){
	window.ounload = self.opener.childWindow=null;

	//CLICK Events



	evtHandler.addEvents("btOk","oklov(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closelov()",EVT_CLICK);

	evtHandler.addEvents("btList","onClickButton('List')",EVT_CLICK);
	evtHandler.addEvents("btClear","onClickButton('Clear')",EVT_CLICK);

	//evtHandler.addEvents("invoiceRefNumber","validateFields(this, -1, 'UldNumber', 0, true, true)",EVT_BLUR);
	//evtHandler.addEvents("invoicedToCode","validateFields(this, -1, 'UldGroupCode', 1, true, true)",EVT_BLUR);


	}


}

function onClickButton(buttonName){

	if( buttonName == 'List'){
		submitForm(targetFormName,'uld.defaults.invoicerefnolovscreenload.do');

	}else if( buttonName == 'Clear'){
		//submitForm(targetFormName,'uld.defaults.invoicerefnolovclear.do');
		submitFormWithUnsaveCheck('uld.defaults.invoicerefnolovclear.do');
	}

}


function closelov() {

	self.close();

}

function oklov(frm) {

	var selectedRows="";
	var count = 0;
	var selectedLovRows = document.getElementsByName('selectedRowsInLov');
	var invoiceRefNumbers = document.getElementsByName('invoiceRefNumbers');
	for(var i=0;i < selectedLovRows.length; i++ ) {
		if(selectedLovRows[i].checked) {
			selectedRows = invoiceRefNumbers[i].value;
			++count;
		}
	}
	if(count > 1 ) {
		//showDialog('Only one row can be selected', 1, self);
		showDialog({msg:'Only one row can be selected.',type : 1,parentWindow:self,parentForm:frm});
	}
	else if(count == 0) {
		//showDialog('No row selected', 1, self);
		showDialog({msg:'No row selected.',type : 1,parentWindow:self,parentForm:frm});
	}
	else {
		window.opener.targetFormName.elements.invoiceRefNumber.value = selectedRows;
		window.close();
		//submitForm(frm,'uld.defaults.okinvoicerfnolov.do?selectedLovRows='+selectedRows);
	}

}

function onScreenLoad(){


	var frm = targetFormName;

	if(frm.elements.lovStatusFlag.value == "ok") {

		//window.opener.targetFormName.invoiceRefNumber.value = frm.invoiceRefNumber.value;
		//alert("inside close");
		close();
		return;

	}
}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deletelistuld.do');
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}


function submitLovPage(lastPg,displayPg){
  targetFormName.elements.lastLovPageNum.value=lastPg;
  targetFormName.elements.displayLovPage.value=displayPg;
  submitForm(targetFormName, 'uld.defaults.invoicerefnolovscreenload.do');
}

