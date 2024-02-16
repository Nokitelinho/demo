<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{


	with(targetFormName){
	targetFormName.gpaCodeFilter.focus();
	evtHandler.addEvents("btnList","list(targetFormName)",EVT_CLICK);
	//evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.elements.rowId)",EVT_CLICK);
	//evtHandler.addEvents("rowId","isDel(targetFormName.elements.rowId)",EVT_CLICK);
    /*if(targetFormName.elements.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.elements.checkAll)",EVT_CLICK);
	}*/
	evtHandler.addEvents("btnViewHistory","history()",EVT_CLICK);
	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
	evtHandler.addEvents("btnSave","save()",EVT_CLICK);
	//evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.elements.check)", EVT_CLICK);
	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	evtHandler.addEvents("btnClose","wdclose()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	evtHandler.addIDEvents("invnumberlov","displayLOV('showInvoiceLOV.do','N','Y','showInvoiceLOV.do',targetFormName.elements.invRefNumberFilter.value,'InvoiceNumber','1','invRefNumberFilter','',0)	",EVT_CLICK);
	evtHandler.addIDEvents("currencyLOV","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.settleCurrency.value,'settleCurrency','1','settleCurrency','',0)", EVT_CLICK);
	evtHandler.addEvents("addLink","addDetail()",EVT_CLICK);
	evtHandler.addEvents("deleteLink","deleteDetail()",EVT_CLICK);
	//evtHandler.addEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeFilter.value,'GPA Code','1','gpaCodeFilter','',0)",EVT_CLICK);
	//evtHandler.addEvents("gpaCodeFilter","populateGpaName(targetFormName)",EVT_BLUR);
	evtHandler.addIDEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeFilter.value,'Gpa Code','1','gpaCodeFilter','gpaNameFilter','',0)",EVT_CLICK);
	evtHandler.addEvents("btnInvoiceDetails","invoiceDetails()",EVT_CLICK);
	evtHandler.addEvents("btnAccDetails","listaccounting()",EVT_CLICK);
	evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.allCheck)", EVT_CLICK);
	evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.elements.allCheck, targetFormName.elements.check)", EVT_CLICK);		
	evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.billChkAll)", EVT_CLICK);
	evtHandler.addEvents("billChkAll","updateHeaderCheckBox(targetFormName, targetFormName.elements.billChkAll, targetFormName.elements.rowId)", EVT_CLICK);	
	}
//disableButtons();
onScreenLoad(targetFormName);
}
function onScreenLoad(targetFormName){
var operationflag = document.getElementsByName("stlOpFlag");
if(targetFormName.elements.screenStatus.value=='avail_popup'){		
	targetFormName.elements.screenStatus.value = "";
	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	if(document.getElementsByName("gpaCodeFilter").value != null)
	var gpaCode=document.getElementsByName("gpaCodeFilter").value;
	var invRefNo=document.getElementsByName("invRefNumberFilter").value;
	var invStatus=document.getElementsByName("invoiceStatusFilter").value;
	var fromDate=document.getElementsByName("fromDate").value;
	var toDate=document.getElementsByName("toDate").value;
	var chequeNo=document.getElementsByName("chequeNumberFilter").value;
	var displayPage=1;
	openPopUp("mailtracking.mra.gpabilling.invoicesettlement.availablepopup.do?gpaCodeFilter="+gpaCode+"&invRefNumberFilter="+invRefNo+"&invoiceStatusFilter="+invStatus+"&fromDate="+fromDate+"&toDate="+toDate+"&chequeNumberFilter="+chequeNo+"&displayPage="+displayPage+"&frmPopUp=avail_popup",700,400);
}
if(targetFormName.elements.frmPopUp.value=='avail_popup'){
	//targetFormName.elements.frmPopUp.value ="";
if(targetFormName.elements.createFlag.value!='Y'){
//targetFormName.elements.createFlag.value="";
//document.getElementById("addLink").disabled=false;
		//alert('createFlag');

	//}else{
	targetFormName.elements.createFlag.value="";
		//alert('createFlag  else');
//document.getElementById("addLink").disabled=true;
   
}
if(targetFormName.elements.createFlag.value==null){
	
	targetFormName.elements.settlementId.focus();
}
//targetFormName.elements.frmPopUp.value="";
//document.getElementById('settlementId').focus();
}

if(targetFormName.elements.frmPopUp.value==''){
	targetFormName.elements.settlementReferenceNumber.focus();
}
}

function list(targetFormName){
if (compDatesCheckFocus(targetFormName,targetFormName.elements.fromDate,targetFormName.elements.toDate)){
	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	targetFormName.elements.frmPopUp.value ="";
submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.list.do');
}
else{
	   return;
	  }
}
function save(){
var selectedRow;
var index=document.getElementsByName("check");
	for(var i=0;i<index.length;i++){
		if(index[i].checked){
			selectedRow=i;
		}
	}
//targetFormName.elements.check.value=selectedRow;
isDelete();
updateOperationFlags(targetFormName);
if(targetFormName.elements.stlOpFlag.value!='NOOP'){
submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.save.do?check='+selectedRow);
}
else{
	showDialog({ msg:"No Modified Data For Save", type:1,parentWindow:self, onClose:function(result){}	});
}
}
function updateOperationFlags(frm){
	var operationFlags = frm.elements.stlOpFlag;
	var chequeNo = frm.elements.chequeNumber;
	var chequeDate = frm.elements.chequeDate;
	var bankName = frm.elements.bankName;
	var branchName = frm.elements.branchName;
	var chequeAmount = frm.elements.chequeAmount;
	var deleteFlag = frm.elements.isDelete;
	var settlementRemarks = frm.elements.chequeRemarks;
	for(var index = 0; index<operationFlags.length;index++){
		if(isElementModified(chequeNo[index]) || isElementModified(chequeDate[index]) || isElementModified(bankName[index]) || isElementModified(branchName[index])|| isElementModified(chequeAmount[index])	|| isElementModified(settlementRemarks[index])||isElementModified(deleteFlag[index])){
			if("NOOP" != operationFlags[index].value && "I" != operationFlags[index].value && "D" != operationFlags[index].value){
				operationFlags[index].value = "U";
				//alert(operationFlags[index].value);
				frm.elements.stlOpFlag[index].value = "U";
			}
		}
	}

}
function history(){
/*var index=new Array();
var gpaCode=new Array();
gpaCode=document.getElementsByName("gpaCode");
var invnumber=new Array();
invnumber=document.getElementsByName("invoiceNumber");
var currency=new Array();
currency=document.getElementsByName("settlementCurrency");
var gpa;
var inv;
var curr;*/



if(validateSelectedCheckBoxes(targetFormName,'check','1','1')){
	/*index=document.getElementsByName("check");
	for(var i=0;i<index.length;i++){

		if(index[i].checked){
			gpa=gpaCode[i].value;
			inv=invnumber[i].value;
			curr=currency[i].value;
			break;
		}
	}*/


        submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.history.do');
		//openPopUp("mailtracking.mra.gpabilling.invoicesettlement.history.do?gpaCodeHistory="+gpa+"&settlementCurrencyHistory="+curr+"&invoiceNumberHistory="+inv,500,400);
	}

}

function print(){
	generateReport(targetFormName,'/mailtracking.mra.gpabilling.invoicesettlement.printsettlement.do');
}

function checkOversettlement(obj){
obj = arguments[0];
	var name=obj.name;
	/*if(obj.value==""){
		obj.value=0.0
	}*/

	var rowCount =obj.id.split(name)[1];

	var totalsettled = document.getElementsByName('amountSettled');
	var billedamt=document.getElementsByName('totalBilledAmountSettlementCurr');

				if(obj.value != null && obj.value.trim().length >0) {
				if(parseFloat(obj.value) >0) {
					if((parseFloat(obj.value)+parseFloat(totalsettled[rowCount].value))>parseFloat(billedamt[rowCount].value)){
						showDialog({msg:"Cannot settle more than the billed amount",type:1,parentWindow:self});
						obj.value=0.0;

					}
				}
				}
}

function disableButtons(){

//added for resolution starts
		var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;

		
		document.getElementById('contentdiv').style.height = ((clientHeight*86)/100)+'px';
		document.getElementById('outertable').style.height=((clientHeight*82)/100)+'px';

		

		var pageTitle=30;
		var filterlegend=40;
		var tabpanel=30;
		var bottomrow=40;
		var height=(clientHeight*83)/100;
		var tableheight=height-(pageTitle+filterlegend+bottomrow+tabpanel);
		document.getElementById('div1').style.height=tableheight+'px';

// added for resolution ends

//targetFormName.elements.gpaCodeFilter.focus();
var screenflag=new Array();
screenflag=document.forms[1].screenStatus;

if(screenflag.value=="screenload"){
targetFormName.elements.btnSave.disabled=true;
targetFormName.elements.btnInvoiceDetails.disabled=true;
targetFormName.elements.btnAccDetails.disabled=true;
targetFormName.elements.btnViewHistory.disabled=true;
}

}

function clear(){
submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.clear.do');
}

function listaccounting(){
//alert("dff");
var frm=targetFormName;
	var bStatus="false";
	var index='';
	var checks = document.getElementsByName('rowId');	
	var isChecked='N'
	
	    if(checks!=null){
		for(var i=0;i<checks.length-1;i++){
		     		if(checks[i].checked==true ){					
				
		     	  
						if(index !=''){
							index=index +","+ i;

						}else{
							index+=i;
						}
						
		      		}
		    	}
		} 
		targetFormName.elements.selectedInvoice.value=index;
		submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.listaccdetails.do');
										
}
function wdclose(){
submitForm(targetFormName,'mailtracking.mra.gpabilling.invoicesettlement.close.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.gpaCodeFilter.disabled == false && targetFormName.elements.gpaCodeFilter.readOnly== false){
			targetFormName.elements.gpaCodeFilter.focus();
		}
		else{
			if(	targetFormName.elements.settlementReferenceNumber.disabled == false && targetFormName.elements.settlementReferenceNumber.readOnly== false){
			 targetFormName.elements.settlementReferenceNumber.focus();
		    }	
		}
	}
}
function submitPage(lastPageNum,displayPage) {
		var frm=targetFormName;
    	targetFormName.elements.lastPageNum.value=lastPageNum;
		targetFormName.elements.displayPage.value=displayPage;
		submitForm(frm,'mailtracking.mra.gpabilling.invoicesettlement.list.do');
}
function addDetail(){
addTemplateRow('captureRow','settlementBody','stlOpFlag');
}
function deleteDetail(){
deleteTableRow('rowId','stlOpFlag');
}
function populateGpaName(frm)
{
if(frm.elements.gpaCodeFilter.value != ""){
if(frm.elements.gpaNameFilter.value == ""){
	frm.action="mailtracking.mra.gpabilling.invoicesettlement.populategpaname.do";
	divIdSeg="checkScreenRefresh_new";
	recreateTableDetails(frm.action,'divChild',divIdSeg);
}

}
}
function submitPage(lastPageNum,displayPage) {
		var frm=targetFormName;
    	targetFormName.elements.lastPageNum.value=lastPageNum;
		targetFormName.elements.displayPage.value=displayPage;
		submitForm(frm,'mailtracking.mra.gpabilling.invoicesettlement.list.do');
}
function isDel(rowId){
var del=document.getElementsByName('rowId');
for(var i=0;i<del.length;i++){
if( del[i].checked){
//alert("tr");	
	document.getElementById("deleteLink").disabled=true;
}
}
}
function invoiceDetails(){

var frm=targetFormName;
var counter=0;
var check=0;
var checkBoxesId = document.getElementsByName('check');
		for (var i=0;i<checkBoxesId.length;i++)
		{
			if( checkBoxesId[i].checked){				
					check=check+1;
				}
			}
			if(check==0)
			{
				showDialog({msg:"Please select a row",type:1,parentWindow:self});
			}
			else if(check>1)
			{
				showDialog({msg:"Please select only one row",type:1,parentWindow:self});
			}
			else{
			for (var i =0; i < checkBoxesId.length; i++)
			{
				if(checkBoxesId[i].checked)
				{
				counter=i;
				}
			}
		//alert(counter);
		targetFormName.elements.selectedInvoice.value=counter;		
		var invokingScreen='invoiceenquiry';
		frm.action="mailtracking.mra.gpabilling.invoicesettlement.listinvoice.do?invokingScreen="+invokingScreen;
		frm.submit();
}
}
function isDelete(){
var deletedArray ="";
var val = "";
	for(var i=0;i<targetFormName.elements.length;i++) {
	if(targetFormName.elements[i].name =='isDelete') {
			if(targetFormName.elements[i].checked) {
				 val = "true";
			}
			else {
			 	 val = "false";
			}
				if(deletedArray != "")
					deletedArray = deletedArray+","+val;
				else if(deletedArray == "")
					deletedArray = val;
		}
}
targetFormName.elements.deleteArray.value=deletedArray;
}
////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";
function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}
function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
}
function checkScreenRefresh_new(_tableInfo){
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	updateTableCode(_tableInfo);
}
function updateTableCode(_tableInfo){
	_str=getActualData(_tableInfo);
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;
}
function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementsByTagName("table")[0];
	return _frm.outerHTML;
}
////////////////// FOR ASYNC SUBMIT ENDS///////////////////////////////////////////////