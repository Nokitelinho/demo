<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
//window.onload=initForm;
//function initForm()
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	frm.elements.invoiceRefNo.disabled=true;
	frm.elements.invoicedDate.disabled=true;
	frm.elements.invoicedToCode.disabled=true;
	frm.elements.name.disabled=true;
	frm.elements.demerageAccured.disabled=true;
	frm.elements.waivedAmount.disabled=true;
	frm.elements.demerageAmount.disabled=true;
	//frm.invoicedDate.focus();
	with(frm){
	//CLICK Events
	//evtHandler.addEvents("btGenerate","generate(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeviewinvoice(this.form)",EVT_CLICK);
	//evtHandler.addEvents("invoicedToCode","validateFields(this, -1, 'InvoicedToCode', 0, true, true)",EVT_BLUR);
	//evtHandler.addEvents("name","validateFields(this, -1, 'Name', 12, true, true)",EVT_BLUR);
	//evtHandler.addEvents("remarks","validateFields(this, -1, 'Remarks', 1, true, true)",EVT_BLUR);


	}
}
function closeviewinvoice(frm) {
	submitForm(frm,appPath+'/uld.defaults.refreshlistinvoice.do?displayPage=1&lastPageNum=0');
	//self.close();
}
function generate(frm) {
	var uldNumbers = "AKE12345EK";
	var txnRefNos = "3";
	var txnType = "L";
	submitForm(frm,'uld.defaults.generateinvoice.do?uldNumbers='+uldNumbers+'&txnRefNos='+txnRefNos+'&txnType='+txnType);
	//self.close();
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
  submitForm(targetFormName, appPath + '/uld.defaults.invoicerefnolovscreenload.do');
}
function NavigateZoneDetails(strLastPageNum, strDisplayPage) {
		var frm = targetFormName;
    	frm.elements.lastPageNum.value = strLastPageNum;
    	frm.elements.displayPage.value = strDisplayPage;
      submitForm(frm,'uld.defaults.navigateinvoice.do');
  }