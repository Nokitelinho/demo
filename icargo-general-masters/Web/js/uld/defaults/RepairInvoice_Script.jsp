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
	frm.elements.totalAmount.disabled=true;
	frm.elements.totalWaived.disabled=true;
	frm.elements.totalInvoiced.disabled=true;
	with(frm){
	//CLICK Events
	evtHandler.addEvents("btShowSummary","showSummary(this.form)",EVT_CLICK);
	evtHandler.addEvents("btSave","saveRepairInvoice(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeRepairInvoice(this.form)",EVT_CLICK);
	evtHandler.addEvents("btPrint","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("waivedAmounts","validateFields(this, -1, 'WaivedAmounts', 2, true, true)",EVT_BLUR);
	//evtHandler.addEvents("remarks","validateFields(this, -1, 'Remarks', 10, true, true)",EVT_BLUR);



	}
}
function showSummary(frm) {
	submitForm(frm,'uld.defaults.repairshowsummary.do');
}
function saveRepairInvoice(frm) {
	var waived = document.getElementsByName("waivedAmounts");
	var actualAmts = document.getElementsByName("actualAmounts");
		for(var i=0;i<waived.length;i++){
			if(waived[i].value<0){
				//showDialog('Cannot Enter Negative value for Waived',1,self);
				showDialog({msg :'Cannot Enter Negative value for Waived.',
				type:1,
				parentWindow:self,                                       
				parentForm:frm});
				waived[i].value ="";
				waived[i].focus();
				return;
			}
			if(eval(waived[i].value) > eval(actualAmts[i].value)){
				//showDialog('Waived amount cannot be greater than Actual Amount',1,self);
				showDialog({msg :'Waived amount cannot be greater than Actual Amount.',
				type:1,
				parentWindow:self,                                       
				parentForm:frm});
				waived[i].value ="";
				waived[i].focus();
				return;
			}
	}
	submitForm(frm,'uld.defaults.saverepairinvoice.do');
}
function closeRepairInvoice(frm) {
	submitForm(frm,appPath+'/uld.defaults.closerepairinvoice.do?displayPage=1&lastPageNum=0');
	//self.close();
}
function generate(frm) {
	var uldNumbers = "AKE12345EK";
	var txnRefNos = "3";
	var txnType = "L";
	submitForm(frm,'uld.defaults.generateinvoice.do?uldNumbers='+uldNumbers+'&txnRefNos='+txnRefNos+'&txnType='+txnType);
	//self.close();
}
function onClickPrint(frm) {
 	frm.elements.invoiceRefNo.disabled=false;
	frm.elements.invoicedDate.disabled=false;
	frm.elements.invoicedToCode.disabled=false;
	frm.elements.name.disabled=false;
 	generateReport(frm,"/uld.defaults.repairinvoice.doReport.do");
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
function confirmMessage() {
	var frm = targetFormName;
	if(frm.elements.closeStatus.value=="whetherToClose") {
		frm.elements.closeStatus.value="canClose";
		submitForm(frm,'uld.defaults.closerepairinvoice.do');
	}
}
function nonconfirmMessage() {
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
      submitForm(frm,'uld.defaults.navigaterepairinvoice.do');
  }