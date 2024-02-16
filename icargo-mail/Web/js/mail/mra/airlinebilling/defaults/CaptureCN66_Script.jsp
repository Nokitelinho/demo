<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister()
{


	var frm=targetFormName;
	with(targetFormName){



	evtHandler.addEvents("btncn66ListAccDtls","doAccEntries()",EVT_CLICK);
	evtHandler.addEvents("btnList","list()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	evtHandler.addEvents("btncn66Print","cn66Print()",EVT_CLICK);
	evtHandler.addEvents("btncn66PrintInv","cn66PrintInv()",EVT_CLICK);
	evtHandler.addEvents("btncn51enquiry","cn51Enquiry()",EVT_CLICK);
	evtHandler.addEvents("btnProcess","process()",EVT_CLICK);
	evtHandler.addEvents("btnWithdraw","withdraw()",EVT_CLICK);
	evtHandler.addEvents("btnAssignException","assignExceptions()",EVT_CLICK);
	evtHandler.addEvents("btnSave","save()",EVT_CLICK);
	evtHandler.addEvents("btnClose","wdClose()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	evtHandler.addIDEvents("btnAdd","add()",EVT_CLICK);
	evtHandler.addIDEvents("btnDelete","deleterow()",EVT_CLICK);
	evtHandler.addIDEvents("btnModify","categorypopup()",EVT_CLICK);
	evtHandler.addIDEvents("invoiceNumberLov","invoiceLOV()",EVT_CLICK);
	evtHandler.addIDEvents("clearanePeriodLov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
	evtHandler.addIDEvents("airlineCodeLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("carriageFromLov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageFromFilter.value,'Carriage From','1','carriageFromFilter','',0)",EVT_CLICK);
	evtHandler.addIDEvents("carriageToLov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageToFilter.value,'Carriage To','1','carriageToFilter','',0)",EVT_CLICK);
	evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.allCheck, targetFormName.check)", EVT_CLICK);
	evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.allCheck)", EVT_CLICK);
	}
	disableButtons();

}

function invoiceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOVCodeNameAndDesc('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.invoiceRefNo.value,'Invoice Number','1','invoiceRefNo','clearancePeriod','airlineCode',0,_reqHeight);
}

function clear(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.clear.do');

}

function save(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.savecn66Details.do');
}

function deleterow(){
	if(validateSelectedCheckBoxes(targetFormName,'check','','1')){
		if(targetFormName.elements.cn51Status.value =="E" || targetFormName.elements.cn51Status.value =="P"){
			showDialog({msg:'CN51 status with Exception or Processed cannot be deleted',type:1,parentWindow:self});
			return;
		}
		submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.deletecn66Details.do');
	}
}



function doAccEntries(){

		var checkBox = document.getElementsByName('check');
		var count=0;
		if(validateSelectedCheckBoxes(targetFormName,'check','1','1')){
			for(var i=0;i<checkBox.length;i++) {
				if(checkBox[i].checked)	{
					count=i;
				}
			}


targetFormName.elements.rowCounter.value=count;
submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.listacc.do');
}
}



function categorypopup(){

if(validateSelectedCheckBoxes(targetFormName,'check','1','1')){
	var index=new Array();
	index=targetFormName.elements.check;
	var count=0;
	var outerRow=new Array();
	var innerRow=new Array();
	var isFirst=false;
	var x=0;
var sta = document.getElementsByName('despatchStatus');
if(index.value==0){

		innerRow[0]=0;
	}

	for(var i=0;i<index.length;i++){
		if(index[i].checked){
		if(sta[i].value == "P" ){

		//showDialog('Processed records cannot be modifed ',1,self);
		showDialog({msg:'Processed records cannot be modifed',type:1,parentWindow:self});
		return;
		}

		else{
		innerRow[i]=index[i].value;
		}

		}
	}
        openPopUp("mailtracking.mra.airlinebilling.defaults.captureCN66.modifyCN66Details.do?innerRowSelected="+innerRow+"&blgCurCode="+targetFormName.elements.blgCurCode.value,950,500);
	}
}

function add(){
	openPopUp("mailtracking.mra.airlinebilling.defaults.captureCN66.addCN66Details.do?blgCurCode="+targetFormName.elements.blgCurCode.value,890,505);
}

function cn66Print(){

  var frm = targetFormName;
  	generateReport(frm,'/mailtracking.mra.airlinebilling.defaults.captureCN66.print.do');

}
function cn66PrintInv(){

  var frm = targetFormName;
  	generateReport(frm,'/mailtracking.mra.airlinebilling.defaults.captureCN66.printInv.do');

}


function cn51Enquiry(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.cn51enquiry.do');
}

function assignExceptions(){
	 var frm=targetFormName;
	var airlinecode=frm.airlineCode.value;
	var invnumber=frm.elements.invoiceRefNo.value;
	var clrperiod=frm.elements.clearancePeriod.value;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.assignexception.do?airlineCode='+airlinecode+'&invoiceNumber='+invnumber+'&clearencePeriod='+clrperiod);
}

function process(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.processmail.do');
}

function withdraw(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.withdrawmail.do');
}



function wdClose() {
	var option;
	var flag=targetFormName.elements.screenStatus.value;
	if(isFormModified(targetFormName)||flag=="reload"||rowDeleted()){
		/*option=showWarningDialog('Unsaved data exists.Do you want to continue?');
			if(option==true){
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.wdclose.do');
			}*/
		showDialog({msg:'Unsaved data exists.Do you want to continue?',type :3, parentWindow:self,
						onClose:function(result){
						if(result){
						submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.wdclose.do');
						}
						}
						});
		}
	else{
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.wdclose.do');
	}
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.invoiceRefNo.disabled == false && targetFormName.elements.invoiceRefNo.readOnly== false){
			targetFormName.elements.invoiceRefNo.focus();
		}
	}
}
function rowDeleted() {
   	var operationFlag = document.getElementsByName('operationFlag');   //for operation flag
   	var flag=0;
   	for(var i=0;i<operationFlag.length;i++) {
   		if(operationFlag[i].value=="D" || operationFlag[i].value=="U" ||operationFlag[i].value=="I")  {
   			flag=1;
   			break;
   		}
       }
       if(flag==0){
       	return false;
       }
       else {
        return true;
       }
}

function list(){

	targetFormName.elements.lastPageNum.value=0;

	targetFormName.elements.displayPage.value=1;

	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.listcn66.do');
}

function submitPage1(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;

	submitForm(targetFormName, 'mailtracking.mra.airlinebilling.defaults.captureCN66.listcn66.do');
}

function disableButtons(){

//targetFormName.elements.blgCurCode.readOnly=true;

document.forms[1].invoiceRefNo.focus();
	var screenstatus=new Array();
	screenstatus=targetFormName.elements.screenStatus;
	var flag=screenstatus.value;
	if (targetFormName.elements.billingType.value=='I')
	targetFormName.btncn66PrintInv.disabled=true;

	if(flag=="screenload"){
	disableField(targetFormName.btncn51enquiry);
	disableField(targetFormName.btnProcess);
	disableField(targetFormName.btnAssignException);
	disableField(targetFormName.btnSave);
	disableField(targetFormName.btncn66Print);
	disableField(targetFormName.btncn66PrintInv);
	disableLink(document.getElementById('btnAdd'));
	disableLink(document.getElementById('btnModify'));
	disableLink(document.getElementById('btnDelete'));
	disableField(targetFormName.btnWithdraw);


	}
	if(flag=="disableall"){
	//disablePage();
	enableField(targetFormName.btnClose);
	enableField(targetFormName.btncn66ListAccDtls);
	enableField(targetFormName.btncn66Print);
	disableField(targetFormName.btnProcess);
	disableField(targetFormName.btnAssignException);
	if (targetFormName.elements.billingType.value=='I')
	disableField(targetFormName.btnWithdraw);

	}
	if(flag=="list"){
	disableField(targetFormName.btnAssignException);
	disableField(targetFormName.btnProcess);
	disableField(targetFormName.btnList);
	}
	if(flag=="reload"){
	disableField(targetFormName.btnProcess);
	disableField(targetFormName.btnList);
	}

	if(flag=="outward"){

	disableLink(document.getElementById('btnAdd'));
	disableLink(document.getElementById('btnModify'));
	disableLink(document.getElementById('btnDelete'));

	disableField(targetFormName.btnSave);
	disableField(targetFormName.btnProcess);
	disableField(targetFormName.btnAssignException);
	enableField(targetFormName.btncn66PrintInv);
	}


	 /*added by Sandeep for disabling the process button */
		       if (document.getElementById('cn51Status')!=null){
			   	       		if (document.getElementById('cn51Status').value!=null){
			   	        		if (document.getElementById('cn51Status').value=='P'){
									disableField(targetFormName.btnProcess);
									disableField(targetFormName.btnAssignException);
									disableLink(document.getElementById('btnAdd'));
									disableLink(document.getElementById('btnModify'));
									disableLink(document.getElementById('btnDelete'));
			   	        	}

 /*added by Muralee for disabling the process  & save button  */
						if (document.getElementById('cn51Status').value=='U'){
									disableField(targetFormName.btnProcess);
									disableField(targetFormName.btnAssignException);

			   	        	}


			   	        	if (document.getElementById('cn51Status').value=='B'){
							enableField(targetFormName.btnProcess);
							disableField(targetFormName.btnAssignException);
			   	        	}

			   	        	if (document.getElementById('cn51Status').value=='E'){
									disableField(targetFormName.btnProcess);
									disableField(targetFormName.btnSave);
									disableLink(document.getElementById('btnAdd'));
									disableLink(document.getElementById('btnModify'));
									disableLink(document.getElementById('btnDelete'));
			   	        	}
						/*added by Muralee for disabling the process  & save button ends  */

			   	       	   }

	      }


}
function confirmMessage() {

targetFormName.elements.screenStatus.value='list';
disableField(targetFormName.btnList);
targetFormName.elements.invoiceRefNo.readOnly=true;
targetFormName.elements.clearancePeriod.readOnly=true;
targetFormName.elements.airlineCode.readOnly=true;
enableLink(document.getElementById('btnAdd'));
document.getElementById('btnAdd').focus();
enableLink(document.getElementById('btnDelete'));

}

function nonconfirmMessage() {

       submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.clear.do');


}

function updateCheck(){

  var chkBoxIds = document.getElementsByName('check');
	    var isChecked = 0;
	    var length= chkBoxIds.length;

		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				isChecked = isChecked + 1;
			}

		}

		if(isChecked != length){
		document.forms[1].allCheck.checked=false;
		}

}