<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){


	with( targetFormName) {

		evtHandler.addEvents("btnList",  "onList()",  EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose()", EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addEvents("btnSave",  "onSave()",  EVT_CLICK);
		evtHandler.addEvents("btnDelete",  "onDeletebtn()",  EVT_CLICK);
		evtHandler.addEvents("clearancePeriodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		evtHandler.addEvents("airlinecodelov","showAirline()",EVT_CLICK);
		evtHandler.addIDEvents("invnumberlov","invoiceLOV()",EVT_CLICK);
		evtHandler.addEvents("btnRejectInvoice","onRejectInvoice()",EVT_CLICK);
		evtHandler.addEvents("airlineCode","populateAirlineNo(targetFormName)",EVT_BLUR);
		//evtHandler.addEvents("exchangeRate","restrictFloat(this,8,8)",EVT_KEYPRESS);
		//evtHandler.addEvents("exchangeRate","populateAmtInUsd(targetFormName)",EVT_BLUR);
		evtHandler.addEvents("exgRate","populateAmtInUsd(targetFormName)",EVT_BLUR);



	}
onScreenLoad();
}


/*
For Rejecting invoice
*/

function onRejectInvoice(){

		submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.rejectinvoice.do');

	}


/*
For showing invoice Lov
*/

function invoiceLOV(){
	var height = document.body.clientHeight;
	var _reqHeight = (height*49)/100;
displayLOVCodeNameAndDesc('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.invoiceRefNo.value,'Invoice Number','1','invoiceRefNo','clearancePeriod','airlineCode','invoiceDate',0,_reqHeight);
//displayLOV('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.invoiceRefNo.value,'Invoice Number','1','invoiceRefNo','',0,_reqHeight);

}
/*
For showing  Airline Lov
*/


function showAirline(){

		displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',
		document.forms[1].airlineCode.value,'Airline code','1',
		'airlineCode', 'airlineNo','',0)
 }

/**

OnScreenLoad Method :For checking invoice status on load itself
*/

function onScreenLoad() {
	// initial focus on page load.
	if(targetFormName.elements.clearancePeriod.disabled == false) {
	  targetFormName.elements.clearancePeriod.focus();
	}

	var invoiceStatus  = targetFormName.elements.invSatusCheckFlag.value;
	var invoiceFormOneStatus  = targetFormName.elements.invForm1SatusCheckFlag.value;
	var ichFlag=targetFormName.elements.ichFlag.value;

	if(targetFormName.elements.noFormOneCaptured.value=="false"){
		targetFormName.elements.btnRejectInvoice.disabled=true;
		targetFormName.elements.btnSave.disabled=true;
		targetFormName.elements.btnDelete.disabled=true;
		targetFormName.elements.btnList.disabled=true;

	}
	
	if(invoiceFormOneStatus == "U"){
			if(targetFormName.elements.btnRejectInvoice.privileged == 'Y'){
			targetFormName.elements.btnRejectInvoice.disabled=false;
			}
	}
	

	if(invoiceStatus == "U"){
		if(targetFormName.elements.btnRejectInvoice.privileged == 'Y'){
		targetFormName.elements.btnRejectInvoice.disabled=false;
		}
	}

	if(invoiceStatus == "R"){
		targetFormName.elements.btnRejectInvoice.disabled=true;
		targetFormName.elements.btnSave.disabled=true;
		targetFormName.elements.btnDelete.disabled=true;
		}

	if(invoiceStatus == "B"){

		targetFormName.elements.btnRejectInvoice.disabled=true;
	}

	if(invoiceStatus == ""){

			targetFormName.elements.btnRejectInvoice.disabled=true;
		}



	if(invoiceStatus == "S"){

			//targetFormName.btnRejectInvoice.disabled=true;
			//targetFormName.btnSave.disabled=true;
			//targetFormName.btnDelete.disabled=true;

		}


if(invoiceStatus == "E"|| invoiceStatus == "P"){

			targetFormName.elements.btnRejectInvoice.disabled=true;
			targetFormName.elements.btnSave.disabled=true;
			targetFormName.elements.btnDelete.disabled=true;

		}


	/*if(ichFlag=="ich"){

	showDialog('Form1 need to be captured for ICH Airline', 1, self);

	}*/
}
/***
For Clearing Screen
*/


function onClear(){


    submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.clear.do');

}


/**

For Closing Screen
*/
function onClose(){


	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.close.do');


}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.clearancePeriod.disabled == false&&targetFormName.elements.clearancePeriod.readOnly == false){
			targetFormName.elements.clearancePeriod.focus();
		}
		else{
			if(targetFormName.elements.listingCurrency.disabled == false&&targetFormName.elements.listingCurrency.readOnly == false){
				targetFormName.elements.listingCurrency.focus();
			}
		}
	}
}
/**

For Listing the Details
*/
function onList(){


	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.list.do');



}
/**
For Saving the Details
*/
function onSave(){


targetFormName.elements.buttonFlag.value="S";
if(targetFormName.elements.listingCurrency.value==""){
showDialog('Listing Currency Code Cannot be blank', 1, self);
}else{
    if(isFormModified(targetFormName)) {

	targetFormName.elements.buttonFlag.value="S";
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.update.do');
		}else {
		showDialog('No Modified Data For Save', 1, self);

	 }
	 }

}
/**
For Deleting the Details
*/

function onDeletebtn(){

targetFormName.elements.buttonFlag.value="D";
submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.update.do');


}

function confirmMessage() {

targetFormName.elements.btnDelete.disabled=true;
//targetFormName.amountInusd.disabled=false;
targetFormName.elements.btnRejectInvoice.disabled=true;
targetFormName.elements.listingCurrency.focus();

}




function nonconfirmMessage() {


 submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.clear.do');

}



function screenConfirmDialog(targetFormName, dialogId) {

//alert("dialogId"+"----"+dialogId.value);

    	while(targetFormName.elements.dialogId.value == ''){

 	}

 	   	if(targetFormName.elements.currentDialogOption.value == 'Y') {
 			if(dialogId == 'id_1'){

 			if(isFormModified(targetFormName)) {
					targetFormName.elements.buttonFlag.value="S";
				submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.update.do');
		   	}else {
				showDialog('No Modified Data For Save', 1, self);

		  	 }


  			}


	 		if(dialogId == 'id_2'){

					targetFormName.elements.buttonFlag.value="D";
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.update.do');

	 	 		}
 		}
 }


 function screenNonConfirmDialog(targetFormName, dialogId) {

	 //alert("dialogId screenNonConfirmDialog"+"----"+dialogId.value);


  	while(targetFormName.elements.currentDialogId.value == ''){

  	}

  	if(targetFormName.elements.currentDialogOption.value == 'N') {
  		if(dialogId == 'id_1'){

  		}

  		if(dialogId == 'id_2'){

  		}
  	}
 }








function populateAirlineNo(targetFormName)
{

targetFormName.elements.buttonFlag.value="airlineno";
if(targetFormName.elements.airlineCode.value != ""){
//if(targetFormName.elements.airlineNo.value == ""){

	//submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.captureinvoice.ajaxScreenLoad.do');

	recreateTableDetails('mailtracking.mra.airlinebilling.inward.captureinvoice.ajaxScreenLoad.do','captureInvoiceParent');


		//}
	}
}






function populateAmtInUsd(targetFormName){

//alert(targetFormName);
//alert("-----------"+targetFormName.listingCurrency.value.toUpperCase()+targetFormName.invoiceRefNo.value);

if(targetFormName.elements.listingCurrency.value.toUpperCase()!=""){

	var exgrate = targetFormName.elements.exgRate.value;
	var misamt=targetFormName.elements.netAmount.value;
	//alert(misamt+"---"+exgrate);
	var operation = new MoneyFldOperations();
	operation.multiplyRawMonies(misamt,exgrate,
	targetFormName.elements.listingCurrency.value.toUpperCase(),'false','onMultiplyAdjAmt');


}
else{

showDialog('Currency Code Cannot be blank', 1, self);

}

}

function onMultiplyAdjAmt(){


	setMoneyValue(targetFormName.amountInusd,arguments[0]);
	//updateSummary();
}


///////////////////////////////////////For Ajax Starts ////////////////////////////////////////////////////////////////////////////////
function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableDetails";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);
}

function updateTableDetails(_tableInfo){
	targetFormName.elements.invoiceRefNo.focus();
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	_filter=_tableInfo.document.getElementById("Childdiv");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
}

/////////////////////////////////////For Ajax Ends ///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////For Ajax  for Amount in usd Starts ////////////////////////////////////////////////////////////////////////////////



function recreateTableDetailsforAmtinUSD(strAction,divId){

	var __extraFn="updateTableDetailsforAmtinUSD";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);
}



function updateTableDetailsforAmtinUSD(_tableInfo){
	
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	_filter=_tableInfo.document.getElementById("Childdiv2");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
}
function displayLOVCodeNameAndDesc(mainAction,strMultiselect,strPagination,strAction,strCode,strTitle,
		currentFormCount,lovValueTxtFieldName,strLovNameTxtFieldName,strLovDescriptionTxtFieldName,strLovDateField,index)
{
if(serverReachable()){
		var ind = mainAction.indexOf('?');

		if(ind != -1) {
			mainAction = mainAction + '&';
		} else {
			mainAction = mainAction + '?';
		}
		
		 ind = strAction.indexOf('?');

		if(ind != -1) {
			strAction = strAction + '&';
		} else {
			strAction = strAction + '?';
		}
		
   	var objCode = eval("targetFormName."+lovValueTxtFieldName);
	   	if(objCode.length == null){
	   		strCode = objCode.value;
	   	}else if(objCode.length != null){
			strCode = objCode[index].value;
	   	}

	var encodedStrCode = encodeURIComponent(strCode);

   	var strUrl = mainAction+"multiselect="+strMultiselect+"&pagination="+strPagination+"&lovaction="+
			strAction+"&code="+encodedStrCode+"&title="+strTitle+"&formCount="+currentFormCount+"&lovTxtFieldName="+
			lovValueTxtFieldName+"&lovNameTxtFieldName="+strLovNameTxtFieldName+"&lovDescriptionTxtFieldName="+strLovDescriptionTxtFieldName+"&index="+index+"&lovNameDateFieldName="+strLovDateField;

	//var lov=openPopUp(strUrl,"500","350");
	
	var _reqHeight = getDimensions(strMultiselect);
	var lov = openPopUp(strUrl,"500",_reqHeight);

	if(arguments[12]!=null){
		lov._parentOkFnHook=arguments[11];
		self._lovOkFnHook=arguments[11];
	}
	if(arguments[13]!=null){
		lov._parentCloseFnHook=arguments[12];
		self._lovCloseFnHook=arguments[12];
	}

	lov._isLov = "true";
	window._isLov="true";
	
	_elems = document.getElementsByName(lovValueTxtFieldName);
	if(_elems) {
		if(_elems.length > 1) {
			if(_elems[index] && !_elems[index].disabled && _elems[index].type != "hidden")
				_elems[index].focus();
		} else if(_elems.length == 1) {
			if(_elems[0] && !_elems[0].disabled && _elems[0].type != "hidden")
				_elems[0].focus();
		}
	}	    
}else{
	alert('<common:message bundle="CommonMessageResources"  key="framework.web.network.Lookslikenetworkdown" scope="request"/>');
}
}




///////////////////////////////////////For Ajax  Amount in USD Ends ////////////////////////////////////////////////////////////////////////////////