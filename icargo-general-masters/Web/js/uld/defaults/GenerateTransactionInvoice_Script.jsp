<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


//window.onload=initForm;
//function initForm()
function screenSpecificEventRegister()
{

	var frm=targetFormName;
	frm.elements.invoicedDate.focus();
	onscreenload();
	with(frm){
	window.ounload = self.opener.childWindow=null;

	//CLICK Events

	evtHandler.addEvents("invoicedToCode","partyCodeValidate()",EVT_BLUR);
	evtHandler.addEvents("airlinelov","onclickLOV()",EVT_CLICK);
	evtHandler.addEvents("btShowTotal","showTotal(this.form)",EVT_CLICK);
	evtHandler.addEvents("btGenerate","generate(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closegenerateinvoice()",EVT_CLICK);
	evtHandler.addEvents("uldNumber","populateNewDetails()",EVT_CHANGE);

	//evtHandler.addEvents("invoicedToCode","validateFields(this, -1, 'InvoicedToCode', 0, true, true)",EVT_BLUR);

	evtHandler.addEvents("name","validateFields(this, -1, 'Name', 12, true, true)",EVT_BLUR);
	evtHandler.addEvents("remarks","validateMaximumLength(this,100,'Remarks','Maximum length',true)",EVT_BLUR);
	evtHandler.addEvents("invoicedToCode","populateInvoiceName()",EVT_BLUR);

	}


}

function populateNewDetails() {
	if(targetFormName.waiver.defaultValue !=
		targetFormName.waiver.value) {
		showDialog("<common:message bundle='generatetransactioninvoice' key='uld.defaults.generatetransaction.confrm.calculatedemmurge' />", 4, self,targetFormName,'id_2');
		screenConfirmDialog(targetFormName,'id_2');
		screenNonConfirmDialog(targetFormName,'id_2');
	}
	else {
		submitForm(targetFormName,'uld.defaults.updatetransactioninvoicedemerage.do');
	}

}

function partyCodeValidate(){
	var frm=targetFormName;
	if(frm.elements.partyTypeFlag.value=="A"){
		validateMaxLength(frm.elements.invoicedToCode,3);
	}else if(frm.elements.partyTypeFlag.value=="G"){
		validateMaxLength(frm.elements.invoicedToCode,12);
	}else if(frm.elements.partyTypeFlag.value=="O"){
		validateMaxLength(frm.elements.invoicedToCode,12);
	}else if(frm.elements.partyTypeFlag.value==""){
		validateMaxLength(frm.elements.invoicedToCode,3);
	}

}

function onclickLOV(){
	frm=targetFormName;
	if(frm.partyTypeFlag.value=="G"){
		frm.airlinelov.disabled = false;
		showLOV();
	}else if(frm.partyTypeFlag.value=="O"){
		frm.airlinelov.disabled = true;
	}else if(frm.partyTypeFlag.value=="A"){
		//frm.airlinelov.disabled = false;
		enableField(frm.airlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',frm.invoicedToCode.value,'invoicedToCode','0','invoicedToCode','name',0)
	}else if(frm.partyTypeFlag.value==""){
		//frm.airlinelov.disabled = false;
		enableField(frm.airlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',frm.invoicedToCode.value,'invoicedToCode','0','invoicedToCode','name',0)
	}
}

function showLOV(){
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=invoicedToCode&formNumber=0&textfiledDesc=name";
	var myWindow = openPopUp(StrUrl, "500","400")

}

function closegenerateinvoice() {

	self.close();

}
function onscreenload() {
	var frm= targetFormName;
	var statusFlag=frm.statusFlag.value;
	var txnType=frm.txnType.value;
	var invoiceId=frm.invoiceId.value;
	if(statusFlag=="generate") {
		if(txnType == "P") {
		    frm = self.opener.targetFormName;
		    frm.action="uld.defaults.listrepairreport.do?invoiceId="+invoiceId;
		    frm.method="post";
		    frm.submit();
       		    window.close();

		}
		else {
		    frm = self.opener.targetFormName;
		    frm.action="uld.defaults.transaction.refreshlistgenerateinvoice.do?invoiceId="+invoiceId;
		    frm.method="post";
		    frm.submit();
       		    window.close();
		}
	}

}

function generate(frm) {
	if(targetFormName.waiver.defaultValue !=
		targetFormName.waiver.value) {
		
		showDialog({msg:'<bean:message bundle="generatetransactioninvoice" key="uld.defaults.generatetransaction.confrm.calculatedemmurge" />',type:4,
				parentWindow:self,
				parentForm:targetFormName,
				dialogId:'id_3',
				onClose:function(result){
		screenConfirmDialog(targetFormName,'id_3');
		screenNonConfirmDialog(targetFormName,'id_3');
	}
				});
	}
	else {
		var partyType = frm.partyTypeFlag.value;
		window.opener.IC.util.common.childUnloadEventHandler();
		submitForm(frm,'uld.defaults.generatetransactioninvoice.do?partyTypeFlag='+partyType);
		//window.close();
	}
	//showTotal(frm);
	//var partyType = frm.partyTypeFlag.value;
	//submitForm(frm,'uld.defaults.generatetransactioninvoice.do?partyTypeFlag='+partyType);
	//self.close();

}

function showTotal(frm) {

    var oldDemValuetoformat = frm.demAmt.defaultValue;
	var oldWaivedValuetoformat = frm.waiver.defaultValue;
	var oldDemValue = parseFloat(removeFormatting(oldDemValuetoformat));
	var oldWaivedValue = parseFloat(removeFormatting(oldWaivedValuetoformat));
	var nextDemValue = 0.0;
	var nextWaivedValue = 0.0;

	if(frm.waiver.value == null || frm.waiver.value.trim().length == 0){
		frm.waiver.value=0.0;
	}
	else {
	    var nextWaivedValuetoformat = frm.waiver.value;
		nextWaivedValue = parseFloat(removeFormatting(nextWaivedValuetoformat));
	}
	if((oldDemValue + oldWaivedValue) < nextWaivedValue) {
		
		showDialog({msg:'<bean:message bundle="generatetransactioninvoice" key="uld.defaults.generatetransaction.waivercannotgreaterthanDemmurage" />',type:1,
				parentWindow:self
				});
		frm.waiver.focus();
		return false;
	}
	//Change made by Sreekumar S
	if(removeFormatting(frm.waiver.value) < 0){
		
		showDialog({msg:'<bean:message bundle="generatetransactioninvoice" key="uld.defaults.generatetransaction.waivercannotbenegative" />',type:1,
				parentWindow:self
				});
		frm.waiver.value=0.0;
		frm.waiver.focus();
	}
	else{
	nextDemValue = oldDemValue + (oldWaivedValue - nextWaivedValue);
	frm.demAmt.value=nextDemValue.toFixed(2);
	formatNumForLocale(frm.demAmt.value);
	frm.demAmt.defaultValue=nextDemValue;
	formatNumForLocale(frm.demAmt.defaultValue);
	frm.waiver.defaultValue=frm.waiver.value;
	formatNumForLocale(frm.waiver.defaultValue);
	return true;
	}


}

function screenConfirmDialog(frm, dialogId) {

	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deletelistuld.do');
		}
		if(dialogId == 'id_2'){
			if(showTotal(frm)) {
				submitForm(frm,'uld.defaults.updatetransactioninvoicedemerage.do');
			}
		}
		if(dialogId == 'id_3'){
			if(showTotal(frm)) {
				var partyType = frm.partyTypeFlag.value;
				submitForm(frm,'uld.defaults.generatetransactioninvoice.do?partyTypeFlag='+partyType);

			}
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){
			frm.waiver.value=frm.waiver.defaultValue;
			submitForm(frm,'uld.defaults.updatetransactioninvoicedemerage.do');
		}
		if(dialogId == 'id_3'){
			frm.waiver.value=frm.waiver.defaultValue;
			var partyType = frm.partyTypeFlag.value;
			submitForm(frm,'uld.defaults.generatetransactioninvoice.do?partyTypeFlag='+partyType);
		}



	}
}


function submitLovPage(lastPg,displayPg){
  targetFormName.lastLovPageNum.value=lastPg;
  targetFormName.displayLovPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.invoicerefnolovscreenload.do');
}

function populateInvoiceName(){
	recreateTableDetails('uld.defaults.transaction.populateinvoicetoname.do','invoicediv');
}


function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableDetails";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);
}
function updateTableDetails(_tableInfo){
	_filter=_tableInfo.document.getElementById("invoiceNameDiv");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
}
