<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>



function screenSpecificEventRegister()
{
	var frm=targetFormName;
	//added by a-3278 for bug 41775 on 24Mar09
	if(!frm.elements.mucReferenceNum.readOnly && ! frm.elements.mucReferenceNum.disabled){
	frm.elements.mucReferenceNum.focus();
	}
	//a-3278 ends
	
	with(frm){
	window.ounload = self.opener.childWindow=null;

	//CLICK Events

	evtHandler.addEvents("btOk","oklov(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closelov()",EVT_CLICK);

	evtHandler.addEvents("btList","onClickButton('List')",EVT_CLICK);
	evtHandler.addEvents("btClear","onClickButton('Clear')",EVT_CLICK);

	}

}

function onClickButton(buttonName){

	if( buttonName == 'List'){
		submitForm(targetFormName,'uld.defaults.messaging.mucrefnolov.screenload.do');

	}else if( buttonName == 'Clear'){
		//submitForm(targetFormName,'uld.defaults.messaging.mucrefnolov.clear.do');
		submitFormWithUnsaveCheck('uld.defaults.messaging.mucrefnolov.clear.do');
	}

}

function closelov() {

	self.close();

}

function oklov(frm) {

	var selectedRows="";
	var count = 0;
	//alert("dbvdfn");
	var selectedLovRows = document.getElementsByName('selectedRowsInLov');
	//alert(selectedLovRows.length);
	var mucRefNumber = document.getElementsByName('mucRefNumbers');
	for(var i=0;i < selectedLovRows.length; i++ ) {
		if(selectedLovRows[i].checked) {
			selectedRows = mucRefNumber[i].value;
			//alert(mucRefNumber[i].value);
			++count;
		}
	}
	//alert("ster");
	if(count > 1 ) {
		showDialog({msg:'Only one row can be selected', type:1, parentWindow:self});
	}
	else if(count == 0) {
		showDialog({msg:'No row selected', type:1, parentWindow:self});
	}
	else {
	//alert("vdxfbv");
	//alert(selectedRows);
		window.opener.targetFormName.elements.mucReferenceNumber.value = selectedRows;
		window.close();		
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
  submitForm(targetFormName, 'uld.defaults.messaging.mucrefnolov.screenload.do');
}

