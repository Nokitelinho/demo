<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;

	with(frm){
evtHandler.addEvents("btnList","onList()",EVT_CLICK);
evtHandler.addEvents("btnSave","onSave()",EVT_CLICK);
evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
evtHandler.addEvents("btnPrint","onPrint()",EVT_CLICK);
//evtHandler.addEvents("invoiceNumberLov","displayLOVCodeNameAndDesc('mra.airlinebilling.defaults.showMemoLov.do','N','Y','mra.airlinebilling.defaults.showMemoLov.do',targetFormName.elements.memoCode.value,'Memo Code','1','memoCode','invoiceNumber','invoiceNumber',0)",EVT_CLICK);
evtHandler.addEvents("memoCodeLov","rejectionLOV()",EVT_CLICK);
evtHandler.addEvents("invoiceNumberLov","invoiceLOV()",EVT_CLICK);
evtHandler.addEvents("dupinvoiceNumberLov","dupinvoiceLOV()",EVT_CLICK);
evtHandler.addEvents("bilCuracceptedAmount","showAcceptedAmount()",EVT_BLUR);
evtHandler.addEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.elements.dsn.value,'DSN No','1','dsn','',0)",EVT_CLICK);

evtHandler.addEvents("addLink","addfn()",EVT_CLICK);//Added For ICRD-265471 File attachment
evtHandler.addEvents("deleteLink","deletefn()",EVT_CLICK);//Added For ICRD-265471 File attachment

}

screenload();
}
function invoiceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.elements.invoiceNumber.value,'Invoice Number','1','invoiceNumber','',0,_reqHeight);
}
function dupinvoiceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.elements.duplicateBillingInvoiceNumber.value,'Duplicate Invoice Number','1','duplicateBillingInvoiceNumber','',0,_reqHeight);
}
function rejectionLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showMemoLov.do','N','Y','mra.airlinebilling.defaults.showMemoLov.do',targetFormName.elements.memoCode.value,'Memo Number','1','memoCode','',0,_reqHeight);
}

function onList(){
	var dsn=targetFormName.elements.dsn.value;
	var dat="";
	var frmPg="rejectionmemo";
	if(dsn != "" ||(dsn == "" && targetFormName.elements.memoCode.value =="")){
		if(dsn.length!=29){
		targetFormName.elements.lovClicked.value="Y";
		openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn+"&dsnFilterDate="+dat+"&fromPage="+frmPg,725,450);
		}else{
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.rejectionmemo.list.do');
		}
	}else{

		submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.rejectionmemo.list.do');
	}
}
function onSave(){
 submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.rejectionmemo.save.do');
 }

function onClear(){
submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.rejectionmemo.clear.do');
}

function onClose(){
	var frmsrnflag=targetFormName.elements.fromScreenFlag.value;

			if(frmsrnflag=="invexpscreen" ||frmsrnflag=="invexpscreenview" ){
				var cn66flag=targetFormName.elements.cn66CloseFlag.value;
				submitFormWithUnsaveCheck('mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do?cn66CloseFlag='+cn66flag);
			}
			else{
				submitFormWithUnsaveCheck('mailtracking.mra.airlinebilling.inward.rejectionmemo.close.do');
			}
}


function screenload(){

var flag=targetFormName.elements.screenFlag.value;



	if(flag=="screenload"){
	disableField(targetFormName.elements.btnPrint);
	disableField(targetFormName.elements.btnSave);
		disableLink(document.getElementById('addLink'));
     	disableLink(document.getElementById('deleteLink'));
		
	}
	if(flag=="disablefilter"){
	disableField(targetFormName.elements.memoCode);
	disableField(targetFormName.elements.memoCodeLov);
	disableField(targetFormName.elements.invoiceNumber);
	disableField(targetFormName.elements.invoiceNumberLov);
	disableField(targetFormName.elements.listBtn);
	disableField(targetFormName.elements.clearBtn);

	}
	if(flag=="disableall"){
	disablePage();
	enableField(targetFormName.elements.btnClose);
	enableField(targetFormName.elements.btnPrint);
	}
	if(flag=="enableallfields"){

	}

	if(flag=="disableSave"){
	disableField(targetFormName.elements.btnSave);
	}
	
	
// initial focus on page load.
if(targetFormName.elements.memoCode.disabled == false) {
  targetFormName.elements.memoCode.focus();
}

var clientHeight = document.body.clientHeight;
document.getElementById('contentdiv').style.height = ((clientHeight*85)/100)+'px';


}

function onPrint(){

generateReport(targetFormName, "/mailtracking.mra.airlinebilling.inward.rejectionmemo.print.do");
}

function showAcceptedAmount(){

recreateTableDetails('mailtracking.mra.airlinebilling.inward.rejectionmemo.calculateacceptedamt.do','rejectionParentdiv');
}

var _tableDivId = "";

function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableDetails";	
	_tableDivId = divId;
	replaceDecimalSepWithDot();
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);
}

function updateTableDetails(_tableInfo){
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	_filter=_tableInfo.document.getElementById("rejectionChilddiv");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
	formatNumberForLocale();
}

function addfn(){	

	var strUrl="mail.mra.airlinebilling.inward.loadsupportingdocspopup.do?fromScreen="+'rejectionmemo';
	openPopUp(strUrl,480,168);
	
	}
	
	
	function recreateTableForDetails(strAction,divId){

	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	
	asyncSubmit(targetFormName,strAction,__extraFn,null,"xy="+new Date(),divId);


}

function updateTableCode(_tableInfo){
	_str = _tableInfo.getTableData();
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;
	targetFormName.elements.attachmentIndicator.checked =true;
	
	
}


   function onfileDownload(indexId){
    shouldDisablePage = false;
   	targetFormName.action= "mail.mra.airlinebilling.inward.downloadcommand.do?selectIndex="+indexId;
   	targetFormName.submit();
   
}

 function deletefn(){   

	var checks = document.getElementsByName('fileNameCheck');
	
	var isChecked = false;
	for(i=0; i < checks.length;i++)
	{
	if(checks[i].checked){
			isChecked=true;		
		}
	}
	if(isChecked){
	recreateAttachmentDetails("mail.mra.airlinebilling.inward.deleteattachmentcommand.do","updateAttachment");
	}
	else{
		showDialog({	
				msg		:	"Please select any attachment for deleting",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
	}
	
   
   }

function recreateAttachmentDetails(strAction, extraFunc){

   asyncSubmit(targetFormName,strAction,extraFunc,null,null);
}
function updateAttachment(_tab){
	_str = _tab.getTableData();
	document.getElementById("divDocpanel").innerHTML=_str;
	
 }
	