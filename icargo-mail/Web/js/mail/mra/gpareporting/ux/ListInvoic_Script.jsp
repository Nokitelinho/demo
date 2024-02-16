<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnList","submitAction('listDetails',targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitAction('clearDetails',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnReject","submitAction('rejectRow',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnProcess","submitAction('processRow',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btninvoicdtls","submitAction('InvoiceDtls',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("paCode_btn","invokeLOV('PACOD')",EVT_CLICK);
		evtHandler.addIDEvents("invoicRefId_btn","invokeLOV('INVREF')",EVT_CLICK);
		evtHandler.addIDEvents("edittab","showOverViewTab()",EVT_CLICK);






	}

	onScreenLoad();
}

function onScreenLoad(){
	var actionFlag =targetFormName.elements.actionFlag.value;
	if(actionFlag=='SHOWLIST'){
	jquery('#headerForm').hide();
	jquery('#headerData').show();
	IC.util.widget.createDataTable("newForceTable",{tableId:"newForceTable",hasChild:false,scrollingY:'5vh'});
	document.getElementById('newForceTable_wrapper').style.width = '100%';
	IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true})
	}
 document.getElementById("btnList").disabled=false;
 document.getElementById("btnClose").disabled=false;
 document.getElementById("btnProcess").disabled=true;
 document.getElementById("btnReject").disabled=true;
 }
function showOverViewTab(){
jquery('#headerData').hide();
jquery('#headerForm').show();
document.getElementById("btnList").disabled=false;
IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true})
}
function checkOnEnablebtn(frm,status,id,invoicId,payType){
var rowStatus=status;
var rowid=id;
var invoicVal=invoicId;
var selected = "";
var isChecked = 0;
var payTyp = payType;
	var chkBoxIds = document.getElementsByName('selectCheckBox');
	for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
					if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}

	if(isChecked==1 && rowStatus=='NW'){
		frm.elements.invoicId.value=invoicVal;
		document.getElementById("btninvoicdtls").disabled=true;
		document.getElementById("btnReject").disabled=false;
		document.getElementById("btnProcess").disabled=false;
	}else if(isChecked==1 && (payTyp =='8' || payTyp == '20')){
		document.getElementById("btninvoicdtls").disabled=true;
		if(rowStatus == 'PR' || rowStatus == 'RJ' || rowStatus == 'IN'){
			document.getElementById("btnReject").disabled=true;
			document.getElementById("btnProcess").disabled=true;
			document.getElementById("btninvoicdtls").disabled=true;
		}else{
			document.getElementById("btnReject").disabled=false;
			document.getElementById("btnProcess").disabled=false;
		}
	}

	else
	{
		document.getElementById("btninvoicdtls").disabled=false;
		document.getElementById("btnProcess").disabled=true;
		document.getElementById("btnReject").disabled=true;
	}
	if(isChecked==1 && (rowStatus != 'RJ' && rowStatus != 'IN')){
		document.getElementById("btnReject").disabled=false;
	}else if(isChecked==1 &&  rowStatus == 'PR' ){
		document.getElementById("btninvoicdtls").disabled=false;
		document.getElementById("btnProcess").disabled=true;
		document.getElementById("btnReject").disabled=false;
	}
	else{
		document.getElementById("btnReject").disabled=true;
		document.getElementById("btninvoicdtls").disabled=true;
	}
}
 function submitAction(actiontype,targetFormName){
	var frm = targetFormName;
	 var type_action=actiontype;
	 var frmDate=frm.elements.fromDate.value;
	var toDat=frm.elements.toDate.value;
if(type_action == 'listDetails') {
	frm.elements.lastPageNum.value="0";
	frm.elements.displayPage.value="1";

	if((frmDate == "" ||frmDate == null)){
			showDialog({
				msg		:	"<common:message bundle="ListInvoicResourceBundle" key="mail.mra.gpareporting.ux.listinvoic.fromdate_todate.mandatory" scope="request"/>",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}else if((toDat == "" || toDat == null)){
			showDialog({
				msg		:	"<common:message bundle="ListInvoicResourceBundle" key="mail.mra.gpareporting.ux.listinvoic.fromdate_todate.mandatory" scope="request"/>",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
	submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.list.do");
}
if(type_action == 'clearDetails') {
		submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.clear.do");
}
if(type_action == 'rejectRow') {
var selected = "";
var isChecked = 0;
	var chkBoxIds = document.getElementsByName('selectCheckBox');
	for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
					if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}
if(isChecked>0 && isChecked==1){
submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.reject.do?selectedValues="+selected);
}else{
	document.getElementsByName('selectCheckBox').checked=false;
	showDialog({
				msg		:	"<common:message bundle="ListInvoicResourceBundle" key="mail.mra.gpareporting.ux.listinvoic.select.singleReject.row" scope="request"/>",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
}
}
if(type_action == 'processRow') {
	var selected = "";
var isChecked = 0;
	var chkBoxIds = document.getElementsByName('selectCheckBox');
	for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
					if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}
if(isChecked>0 && isChecked==1){
	var invoicId=targetFormName.elements.invoicId.value;
	submitForm(targetFormName,"mail.mra.gpareporting.ux.listinvoic.process.do?invoicId="+invoicId+"&selectedValues="+selected);
}else{
	document.getElementsByName('selectCheckBox').checked=false;
	showDialog({
				msg		:	"Please select one Invoic Reference ID only",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
}
}
if(type_action == 'InvoiceDtls') {
var selected = "";
var isChecked = 0;
	var chkBoxIds = document.getElementsByName('selectCheckBox');
	for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;
					if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}
if(isChecked>0 && isChecked==1){
	var invoicId=targetFormName.elements.invoicId.value;
	var poaCode=targetFormName.elements.paCode.value
	submitForm(targetFormName,"mail.mra.gpareporting.ux.invoicenquiry.defaultscreenload.do?invoicId="+invoicId+"&gpaCode="+poaCode+"&fromScreen=List_INVOIC_SCREEN");
}else{
	document.getElementsByName('selectCheckBox').checked=false;
	showDialog({
				msg		:	"<common:message bundle="ListInvoicResourceBundle" key="mail.mra.gpareporting.ux.listinvoic.select.singleInvoic.row" scope="request"/>",
				type	:	1,
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
}
}
}

function closeScreen(){
location.href = appPath + "/home.jsp";
}
function openInvoicScreen(invoicId,statusval,payTyp){
	var payType=payTyp;
	if(invoicId && payType != '8'){
	var poaCode=targetFormName.elements.paCode.value;
	submitForm(targetFormName,"mail.mra.gpareporting.ux.invoicenquiry.defaultscreenload.do?invoicId="+invoicId+"&gpaCode="+poaCode+"&fromScreen=List_INVOIC_SCREEN");
	}
}
function showEntriesforlisting(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.mra.gpareporting.ux.listinvoic.list.do";
	submitForm(targetFormName, action);
}
function submitPage(lastPageNum,displayPage){
	var actionFlag =targetFormName.elements.actionFlag.value;
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	var action = "mail.mra.gpareporting.ux.listinvoic.list.do";
	submitForm(targetFormName, action);
}
function invokeLOV(LOVType){
	var optionsArray =getLOVOptions(LOVType);
	if(optionsArray){
			lovUtils.displayLOV(optionsArray);
	}
}

function getLOVOptions(LOVType){
var optionsArray;
	switch(LOVType) {
		case 'PACOD':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'PA Code',
				codeFldNameInScrn			: 'paCode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'paCode',
				lovIconId					: 'paCode_btn',
				maxlength					: 3
			}
			 break;
		case 'INVREF':
		var strUrl ='showNewInvoicLOV.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Invoic ID',
				codeFldNameInScrn			: 'invoicRefId',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'invoicRefId',
				lovIconId					: 'invoicRefId_btn',
				maxlength					: 36
			}
			 break;
			default:
			optionsArray = {
			}
		}
		return optionsArray;

}
function confirmMessage() {
    openPopUp("mail.mra.gpareporting.ux.listinvoic.rejectConfirmation.do",500,200);
}
