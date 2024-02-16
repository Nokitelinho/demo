<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

//window.onload=initForm;
//function initForm()
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	onScreenLoad();
	with(frm){

	//CLICK Events


	evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btView","viewinvoice(this.form)",EVT_CLICK);
	evtHandler.addEvents("btPrint","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("partyType","partycomboChanged()",EVT_CHANGE);
	evtHandler.addIDEvents("partycodeairlinelov","onclickPartycodeLOV()",EVT_CLICK);
	
	evtHandler.addEvents("invoiceRefNumber","validateFields(this, -1, 'InvoiceRefNumber', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("invoicedToCode","validateFields(this, -1, 'Party Code', 0, true, true)",EVT_BLUR);


	}
 applySortOnTable("invoicedetails",new Array("None","String","String","Date","String","String","Number","Number","Number"));
}

function partycomboChanged(){
	frm=targetFormName;
	//alert(frm.partyType.value);
	if(frm.elements.partyType.value=="G"){
	    //frm.partycodeairlinelov.disabled = false;
		enableField(frm.elements.partycodeairlinelov);
		frm.elements.comboFlag.value="agentlov";
		frm.elements.comboFlag.defaultValue="agentlov";
	}else if(frm.elements.partyType.value=="O"){
		frm.elements.comboFlag.value="others";
		frm.elements.comboFlag.defaultValue="others";
	}else if(frm.elements.partyType.value=="A"){
		//frm.partycodeairlinelov.disabled = false;
		enableField(frm.elements.partycodeairlinelov);
		frm.elements.comboFlag.value="airlinelov";
		frm.elements.comboFlag.defaultValue="airlinelov";
		
	}
}
function onclickPartycodeLOV(){
	frm=targetFormName;
   	if(frm.elements.comboFlag.value=="agentlov"){
		//frm.partycodeairlinelov.disabled = false;
		enableField(frm.elements.partycodeairlinelov);
		showAgentLOV();
	}else if(frm.elements.comboFlag.value=="airlinelov"){
	   
		//frm.partycodeairlinelov.disabled = false;
		enableField(frm.elements.partycodeairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.invoicedToCode.value,'invoicedToCode','1','invoicedToCode','',0)
		
	}else if(frm.elements.comboFlag.value=="others"){
		//frm.partycodeairlinelov.disabled = true;
	    disableField(frm.elements.partycodeairlinelov);
	}
}
function showAgentLOV(){
	var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=invoicedToCode&formNumber=1&textfiledDesc= "+textobj;
	var myWindow = openPopUp(StrUrl, "500","400")

}

function list(frm) {

	submitForm(frm,'uld.defaults.listuldinvoice.do?countTotalFlag=YES');
}


function clearuld(frm) {
	//submitForm(frm,'uld.defaults.screenloaduldinvoice.do');
	submitForm(frm,'uld.defaults.screenloaduldinvoice.do');
}


function closeuld(frm) {
	location.href = appPath+"/home.jsp";

}

function viewinvoice(frm) {

		var selectedRefNo = "";
		var selectedInvoiceDate = "";
		var selectedInvoiceCode = "";
		var selectedInvoiceName = "";
		var transactionType = "";
		var selectedRows = document.getElementsByName('selectedRows');
		var selectedRefNos = document.getElementsByName('invoiceRefNumbers');
		var selectedInvoiceDates = document.getElementsByName('invoicedDates');
		var selectedInvoiceCodes = document.getElementsByName('invoicedToCodes');
		var selectedInvoiceNames = document.getElementsByName('invoicedToNames');
		var selectedTransactionTypes = document.getElementsByName('transactionTypes');
		for(var i=0;i<selectedRows.length;i++) {
				if(selectedRows[i].checked)	{
					//var val = selectedUldRows[i].value;
					if(selectedRefNo != "") {
						selectedRefNo = selectedRefNo+","+selectedRefNos[i].value;
						selectedInvoiceDate = selectedInvoiceDate+","+selectedInvoiceDates[i].value;
						selectedInvoiceCode = selectedInvoiceCode+","+selectedInvoiceCodes[i].value;
						selectedInvoiceName = selectedInvoiceName+","+selectedInvoiceNames[i].value;

						if(((transactionType == 'L' || transactionType == 'B') && selectedTransactionTypes[i].value == 'P')
							|| (transactionType == 'P' && (selectedTransactionTypes[i].value == 'L' || selectedTransactionTypes[i].value == 'B'))) {
							transactionType="";
						}

					}
					else if(selectedRefNo == ""){
						selectedRefNo = selectedRefNos[i].value;
						selectedInvoiceDate = selectedInvoiceDates[i].value;
						selectedInvoiceCode = selectedInvoiceCodes[i].value;
						selectedInvoiceName = selectedInvoiceNames[i].value;
						transactionType = selectedTransactionTypes[i].value;
					}
				}
			}
		selectedInvoiceName=selectedInvoiceName+",";
		if(selectedRefNo != "") {

			if(transactionType != "" ) {
				if(transactionType == 'L' || transactionType == 'B') {
					submitForm(frm,'uld.defaults.viewinvoice.do?invoiceRefNo='+selectedRefNo+'&invoicedDate='+selectedInvoiceDate+'&invoicedToCode='+selectedInvoiceCode+'&name='+selectedInvoiceName);
				}
				else {
					//alert("tansaction Type---> Repair");
					//location.href = appPath+'/jsp/uld/defaults/RepairInvoice.jsp';
					submitForm(frm,'uld.defaults.viewrepairinvoice.do?invoiceRefNo='+selectedRefNo+'&invoicedDate='+selectedInvoiceDate+'&invoicedToCode='+selectedInvoiceCode+'&name='+selectedInvoiceName);

				}
			}
			else {
				//showDialog("<common:message bundle='listinvoice' key='uld.defaults.TransactionTypesMisMatch'/>", 1, self);
				showDialog({msg:"<common:message bundle='listinvoice' key='uld.defaults.TransactionTypesMisMatch' />",type:1,parentWindow:self});
			}
		}
		else {
			//showDialog("<common:message bundle='listinvoice' key='uld.defaults.norowselected'/>", 1, self);
			showDialog({msg:"<common:message bundle='listinvoice' key='uld.defaults.norowselected'/>",type:1,parentWindow:self});
	}

}

function displayInvoiceRefNoLov() {
	var frm=targetFormName;
	var strurl="uld.defaults.invoicerefnolovscreenload.do?invoiceRefNum";
	openPopUp(strurl+frm.invoiceRefNumber.value,400,440);
	//submitForm(frm,'uld.defaults.invoicerefnolovscreenload.do');
	//location.href = appPath+'uld.defaults.invoiceRefNoLov.do';
}



function onScreenLoad(){


	var frm = targetFormName;
	frm.elements.invoiceRefNumber.focus();
	partycomboChanged();
	
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

function onClickPrint(frm) {


 	generateReport(frm,"/uld.defaults.listinvoice.doReport.do");

 }

function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value=lastPg;
  targetFormName.elements.displayPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.listuldinvoice.do');
}

function submitLovPage(lastPg,displayPg){
  targetFormName.elements.lastLovPageNum.value=lastPg;
  targetFormName.elements.displayLovPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.invoicerefnolovscreenload.do');
}

