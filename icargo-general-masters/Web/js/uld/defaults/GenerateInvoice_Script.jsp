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
	evtHandler.addIDEvents("airlinelov","onclickLOV()",EVT_CLICK);
	evtHandler.addEvents("btShowTotal","showTotal(this.form)",EVT_CLICK);
	evtHandler.addEvents("btGenerate","generate(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closegenerateinvoice()",EVT_CLICK);

	//evtHandler.addEvents("invoicedToCode","validateFields(this, -1, 'InvoicedToCode', 0, true, true)",EVT_BLUR);

	evtHandler.addEvents("name","validateFields(this, -1, 'Name', 12, true, true)",EVT_BLUR);
	evtHandler.addEvents("remarks","validateMaximumLength(this,100,'Remarks','Maximum length',true)",EVT_BLUR);


	}


}

function partyCodeValidate(){
	var frm=targetFormName;
	if(frm.elements.partyTypeFlag.value=="Airline"){
		validateMaxLength(frm.elements.invoicedToCode,3);
	}else if(frm.elements.partyTypeFlag.value=="Agent"){
		validateMaxLength(frm.elements.invoicedToCode,12);
	}else if(frm.elements.partyTypeFlag.value=="Others"){
		validateMaxLength(frm.elements.invoicedToCode,12);
	}else if(frm.elements.partyTypeFlag.value==""){
		validateMaxLength(frm.elements.invoicedToCode,3);
	}

}

function onclickLOV(){
	frm=targetFormName;
	if(frm.elements.partyTypeFlag.value=="Agent"){
		frm.airlinelov.disabled = false;
		showLOV();
	}else if(frm.elements.partyTypeFlag.value=="Agent"){
		frm.airlinelov.disabled = true;
	}else if(frm.elements.partyTypeFlag.value=="Airline"){
		frm.airlinelov.disabled = false;
		displayLOV('showAirline.do','N','Y','showAirline.do',frm.elements.invoicedToCode.value,'invoicedToCode','0','invoicedToCode','name',0)
	}else if(frm.elements.partyTypeFlag.value==""){
		frm.airlinelov.disabled = false;
		displayLOV('showAirline.do','N','Y','showAirline.do',frm.elements.invoicedToCode.value,'invoicedToCode','0','invoicedToCode','name',0)
	}
}

function showLOV(){
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=invoicedToCode&formNumber=0&textfiledDesc=name";
	var myWindow = openPopUp(StrUrl, "500","400");

}

function closegenerateinvoice() {

	self.close();

}
function onscreenload() {
	var frm= targetFormName;
	var statusFlag=frm.elements.statusFlag.value;
	var txnType=frm.elements.txnType.value;
	var invoiceId=frm.elements.invoiceId.value;
	if(statusFlag=="generate") {
		if(txnType == "P") {
		    frm = self.opener.targetFormName;
		    frm.action="uld.defaults.listrepairreport.do?invoiceId="+invoiceId;
		    frm.method="post";
		    window.opener.IC.util.common.childUnloadEventHandler();
			frm.submit();
       		    window.close();

		}
		else {
		    frm = self.opener.targetFormName;
		    frm.action="uld.defaults.transaction.refreshlistgenerateinvoice.do?invoiceId="+invoiceId;
		    frm.method="post";
		    window.opener.IC.util.common.childUnloadEventHandler();
			frm.submit();
       		    window.close();
		}
	}

}

function generate(frm) {
showTotal(frm);
var partyType = frm.elements.partyTypeFlag.value;
	submitForm(frm,'uld.defaults.generateinvoice.do?partyTypeFlag='+partyType);
	//self.close();

}

function showTotal(frm) {
	var dmgAmt = parseFloat(frm.elements.hiddenDmgAmt.value);
	var originalWaiver = parseFloat(frm.elements.hiddenWaiver.value);
	var waiverAmt = parseFloat(frm.elements.waiver.value);
	var nextValue = 0.0;
	if(frm.elements.waiver.value == ''){
		frm.demAmt.value=dmgAmt;
	}else {
		if(originalWaiver != waiverAmt){
			if(dmgAmt < waiverAmt) {
				showDialog({msg:'Waiver cannot be greater than Demmurage', type:1,parentWindow:self});
			}else{
				nextValue = parseFloat(dmgAmt - waiverAmt);
			}
			frm.elements.demAmt.value=nextValue;
		}
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
  submitForm(targetFormName, appPath + '/uld.defaults.invoicerefnolovscreenload.do');
}

