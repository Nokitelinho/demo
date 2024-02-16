<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;

	with(frm){

		evtHandler.addEvents("btnList","onList()",EVT_CLICK);

		evtHandler.addEvents("clearButton","submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.airlineexceptions.onClear.do')",EVT_CLICK);

		evtHandler.addEvents("closeButton","doClose()",EVT_CLICK);

		evtHandler.addEvents("saveButton","onSave()",EVT_CLICK);

		evtHandler.addEvents("printExpDetailButton","onPrintExpDetail()",EVT_CLICK);

		evtHandler.addEvents("airlineCodelov","displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);

		evtHandler.addEvents("invoiceRefNolov","invoiceLOV()",EVT_CLICK);

		evtHandler.addEvents("btnIssueRejectionMemo","issueRejectionMemo()",EVT_CLICK);

		evtHandler.addEvents("btnAccept","onAccept()",EVT_CLICK);

        evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
		
	    evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
		
	    evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'Subclass','1','subClass','',0)", EVT_CLICK);
	}
	screenload();

}
function invokeLov(obj,name){   
var index = obj.id.split(name)[1];

 if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOfficeOfExchange.value,'OfficeOfExchange','0','originOfficeOfExchange','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destinationOfficeOfExchange.value,'OfficeOfExchange','0','destinationOfficeOfExchange','',index);
   }
}
function invoiceLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',document.forms[1].invoiceRefNo.value,'Invoice Reference Number','1','invoiceRefNo','',0,_reqHeight);
}
//method to print exception details Added by Indu
function onPrintExpDetail(){
     var frm=targetFormName;
     if(targetFormName.airlineCode.value=="" ){

     		showDialog('<common:message bundle="airlineExceptions" key="mra.inwardbilling.airlineexceptions.msg.err.mandatoryfieldsblank" scope="request"/>', 1, self);
     		return;
     	}

     	if(!compDatesCheckFocus(targetFormName, targetFormName.fromDate, targetFormName.toDate)){
     		return;
     	}

     generateReport(frm,'/mailtracking.mra.airlinebilling.inward.airlineexceptions.printexceptiondtl.do');

}
function doClose(){
	var frm=targetFormName ;
	if(frm.fromScreenFlag.value=='closeinvoiceexceptions'){
		//alert('close');
		var fromScreenFlag=frm.fromScreenFlag.value;
		//submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.invoiceexceptionsscreenload.do?fromScreenFlag='+fromScreenFlag);
		submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do');
	}
	else if(frm.fromScreenFlag.value=='cn66screenclose'){
		//submitForm(frm,'mailtracking.mra.airlinebilling.defaults.captureCN66.onScreenLoad.do?fromScreenStatus='+"invoiceexception");
		submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.invoiceexceptionslist.do');
	}
	else{
		submitFormWithUnsaveCheck('mailtracking.mra.airlinebilling.inward.airlineexceptions.onClose.do');
	}

}
function screenload(){

//added for resolution starts
//var clientHeight = document.body.clientHeight;
//var clientWidth = document.body.clientWidth;

//alert(clientHeight);
//document.getElementById('contentdiv').style.height = ((clientHeight*85)/100)+'px';
//document.getElementById('outertable').style.height=((clientHeight*80)/100)+'px';

//alert(document.getElementById('outertable').style.height);

//var pageTitle=30;
//var filterlegend=80;
//var filterrow=30;
//var bottomrow=40;
//var height=(clientHeight*80)/100;
//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
//document.getElementById('div1').style.height=tableheight+'px';
// added for resolution ends

	var frm=targetFormName ;
	if(targetFormName.fromDate.readOnly==false || targetFormName.fromDate.disabled==false){
				frm.fromDate.focus();
	}
	if(frm.fromScreenFlag.value=='invoiceexceptions'){
		frm.fromScreenFlag.value='closeinvoiceexceptions';
		onList();
	}
	else if(frm.fromScreenFlag.value=='cn66screen'){
		frm.fromScreenFlag.value='cn66screenclose';
			onList();
	}

	if(frm.saveStatus.value=='Y'){

		frm.saveStatus.value='';
		//showDialog('<common:message bundle="airlineExceptions" key="mra.inwardbilling.airlineexceptions.msg.info.savesuccess" scope="request"/>', 4, self);

	}

}

function submitAction(action, path) {

	frm = targetFormName;

	if(targetFormName.rowCount == null ){
		showDialog('<common:message bundle="airlineExceptions" key="mra.inwardbilling.airlineexceptions.msg.err.datefieldsblank" scope="request"/>', 1, self);
		return;
	}

	var check = validateSelectedCheckBoxes(frm, 'rowCount', 1, 1);

	if(check){
		submitForm(frm, action);

	}else{
		return;
	}
}


function onList() {

	frm = targetFormName;

	submitForm(frm,'mailtracking.mra.airlinebilling.inward.airlineexceptions.onList.do');
}
function submitPage(lastPg,displayPg){
var frm = targetFormName;
	frm.lastPageNumber.value=lastPg;
	frm.displayPage.value=displayPg;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.airlineexceptions.onList.do');
}
function onSave(){

	var frm=targetFormName ;
	setOperationalFlag();
	submitForm(frm, 'mailtracking.mra.airlinebilling.inward.airlineexceptions.onSave.do');
}



function issueRejectionMemo(){

	frm = targetFormName;
	var checkBox = document.getElementsByName('selectedRows');
	var count=0;
	if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1,1)){
	for(var i=0;i<checkBox.length;i++) {
					if(checkBox[i].checked)	{
						count=i;
					}
				}
		//frm.selectedRow.value=count;
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.airlineexceptions.issuerejectionmemo.do');
}
}

/* resetting operational flag depending on datavalues it holds */
 function setOperationalFlag(){

 		if(targetFormName.operationalFlag != null){

			if(targetFormName.operationalFlag.length > 1){
				for(var i = 0; i<targetFormName.operationalFlag.length; i++){
					if(targetFormName.operationalFlag[i].value != "I"){

						// Default values

						var assigneeCodeDefValue = targetFormName.assigneeCode[i].defaultValue;
						var assignedDateDefValue = targetFormName.assignedDate[i].defaultValue;

						//current values
						var assigneeCodeValue = targetFormName.assigneeCode[i].value;
						var assignedDateValue = targetFormName.assignedDate[i].value;

						if( (assigneeCodeDefValue != assigneeCodeValue)||
							(assignedDateDefValue != assignedDateValue)
						  ) {
								targetFormName.operationalFlag[i].value = "U";
						}
					}
				}

			} else {

				if(targetFormName.operationalFlag.value != "I"){


					// Default values
					var assigneeCodeDefValue = targetFormName.assigneeCode.defaultValue;
					var assignedDateDefValue = targetFormName.assignedDate.defaultValue;


					// current values
					var assigneeCodeValue = targetFormName.assigneeCode.value;
					var assignedDateValue = targetFormName.assignedDate.value;

					if(	(assigneeCodeDefValue != assigneeCodeValue)||
						(assignedDateDefValue != assignedDateValue)
					) {
							targetFormName.operationalFlag.value = "U";

					}
				}
			}
		}
}

function showAssigneeLov(rowId){	
	var mode = getScreenId(targetFormName) ;
	displayLOV('showUserLOV.do','N','Y','showUserLOV.do',document.forms[1].assigneeCode.value,'Assignee Code','1','assigneeCode','',rowId,'','',mode);
}

function getScreenId(frm){
	var actCode = frm.action;
	var actCodeArr = actCode.split("/");
	var len = actCodeArr.length;
	var lastVal = actCodeArr[len-1];
	var scrid = IC.util.common.getScreenIdUsingActionCode(lastVal);
	var length = scrid.split("'");
	var mode = length[0];
	return mode ;
}

function onAccept(){

	frm = targetFormName;
	var checkBox = document.getElementsByName('selectedRows');
	var p=new Array();
	var flag=0;
	var expStatus=new Array();
	expStatus=document.getElementsByName('expStatus');

	//alert('expStatus'+expStatus);
	//alert('expStatus'+expStatus.value);
	//if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1,1)){
	for(var i=0;i<checkBox.length;i++) {
		if(checkBox[i].checked)	{
			p[i]=checkBox[i].value;
			//alert('expStatus[i]-->>'+expStatus[i].value);
			if(expStatus[i].value!="E"){
				flag++;
			}
		}
	}
	 if(flag>0){

		showDialog('<common:message bundle="airlineExceptions" key="mailtracking.mra.airlinebilling.inward.airlineexceptions.dlg.msg.exception"/>',1,self);

	 }else{
			if(validateSelectedCheckBoxes(targetFormName,'selectedRows',10000,1)){
		 		openPopUp("mailtracking.mra.airlinebilling.inward.airlineexceptions.remarkspopup.do?selectedRows="+p,520,180);
	 		}

	 }

	//submitForm(frm,'mailtracking.mra.airlinebilling.inward.airlineexceptions.accept.do');
//}
}