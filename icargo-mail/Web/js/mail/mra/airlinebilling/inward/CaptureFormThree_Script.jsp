<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){

	// Initial focus on page load.
	if(targetFormName.elements.clearancePeriod.disabled == false) {
	   targetFormName.elements.clearancePeriod.focus();	
	}
	
	with(targetFormName){
	
		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);	
		evtHandler.addEvents("btnSave","doSave()",EVT_CLICK);	
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);	
		
		
		evtHandler.addIDEvents("clearancePeriodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',targetFormName.elements.clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','clearancePeriod','',0)",EVT_CLICK);
		evtHandler.addEvents("addLink","addDetail()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteDetail()",EVT_CLICK);
		
		evtHandler.addEvents("miscAmountInBilling","updateOpFalg(this,'hiddenOperationFlag')",EVT_BLUR);
		evtHandler.addEvents("creditAmountInBilling","updateOpFalg(this,'hiddenOperationFlag')",EVT_BLUR);
		evtHandler.addEvents("totalAmountInBilling","updateOpFalg(this,'hiddenOperationFlag')",EVT_BLUR);
		evtHandler.addEvents("netValueInBilling","updateOpFalg(this,'hiddenOperationFlag')",EVT_BLUR);
		evtHandler.addEvents("airlineCode","populateAirlineDetails(targetFormName)",EVT_BLUR);
		
		

	}
	

}

/**
*Clear function done here
*/
function doClear(){

	frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.captureFormThree.clear.do');
}
/**
*List function done here
*/
function doList(){
	frm=targetFormName;	
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.captureFormThree.list.do');
	
	

}
/**
*Savefunction done here
*/
function doSave(){
	frm=targetFormName;
	/*var operationFlag = document.getElementsByName('hiddenOperationFlag'); 
	for(var i=0;i<operationFlag.length-1;i++){
	alert(operationFlag[i].value);
	}*/
	
	
	var isDuplicate="N";
	var airlineCode=targetFormName.elements.airlineCode;
	var opFlag = document.getElementsByName('hiddenOperationFlag'); 
	/*for(var i=0;i<opFlag.length-1;i++)
	{
	//alert(opFlag[i].value);
	}*/
	 
	
 if(isFormModified(targetFormName)) {
	
	
  for (var i = 0; i <airlineCode.length; i++){
 	 
 	   if((opFlag[i].value=="U") || (opFlag[i].value=="I")|| (opFlag[i].value=="N") ){
 	if((opFlag[i].value!="NOOP")){
 	 // alert("i**opflg"+i+opFlag[i].value);	
 	  for(var j=i+1;j<airlineCode.length-1;j++){
 	  if((opFlag[j].value!="NOOP")){
 	  
 	//  alert("j**opflg"+i+opFlag[j].value);	
 	   if((opFlag[j].value=="U") || (opFlag[j].value=="I")||(opFlag[i].value=="N") ){
 		  
 	// alert("i,j"+i+j);
 	  
 	 // alert(airlineCode[i].value+airlineCode[j].value+"---***");
 	  
 	   if(((airlineCode[i].value).toUpperCase())==((airlineCode[j].value).toUpperCase())){
 	  	 		  
 	  	 		 isDuplicate="Y";
 	  	 		  
 	  	} 		 
 	 		 
 	  }
 	 }
 	   }
 	}
 	}
 	}
	
	
	
	 if(isDuplicate=="Y")
		 	 	{
		 	 	//alert('duplicate');
		 	 	//showDialog('Duplicate entries present in the table', 1, self);
				showDialog({msg:"Duplicate entries present in the table",type:1,parentWindow:self});
		 	 	return;
		 	 	}
		 	 	else{
		 		
		 		submitForm(frm,'mailtracking.mra.airlinebilling.inward.captureFormThree.save.do');
		 		}
	 
	
	
	}
	
	else{	
	
	
	
			//showDialog('No Modified Data For Save', 1, self);
			 showDialog({msg:"No Modified Data For Save",type:1,parentWindow:self});
		 		
	}
	
	
	
	
		
}


function confirmMessage() {
targetFormName.elements.linkDisable.value=="N";
addTemplateRow('captureform3Row','captureFormThreeTbody','hiddenOperationFlag');
var airlineCode = document.getElementsByName('airlineCode');
//alert('airlineCode.value'+airlineCode.value);
//alert('airlineCodelength'+airlineCode.length);
		if(airlineCode != null && airlineCode.length > 0) {
			airlineCode[airlineCode.length - 2].focus();
		}

}




function nonconfirmMessage() {


 	frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.captureFormThree.clear.do');
	targetFormName.elements.linkDisable.value=="Y";
	
}



/**
*Close function done here
*/
function doClose(){
	frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.inward.captureFormThree.close.do');
		
}
function addDetail(){
if(targetFormName.elements.linkDisable.value=="N"){

addTemplateRow('captureform3Row','captureFormThreeTbody','hiddenOperationFlag');
var airlineCode = document.getElementsByName('airlineCode');
//alert('airlineCode.value'+airlineCode.value);
//alert('airlineCodelength'+airlineCode.length);
		if(airlineCode != null && airlineCode.length > 0) {
			airlineCode[airlineCode.length - 2].focus();
		}
targetFormName.elements.linkDisable.value=="Y";
}
}
function deleteDetail(){
if(targetFormName.elements.linkDisable.value=="N"){


deleteTableRow('setCheck','hiddenOperationFlag');
}
}
function showAirlineLovForTemplateRow(obj) {
	var name = obj.name;
	
	var index = obj.id.split(name)[1];
	
	var airlineCodes = document.getElementsByName('airlineCode');	
	
	displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',airlineCodes[index].value,'Airline code','1','airlineCode', 'airlineNumber','',index);
	
}



function updateOpFalg(obj,opFlagName)
{


	_val1 = obj.value;
	_val2 = obj.defaultValue;
	
	if(_val1 != _val2) {	
		var _index = document.getElementsByName(opFlagName).length;			
		for(i=0;i<_index;i++){
		if(document.getElementsByName(opFlagName)[i] && document.getElementsByName(opFlagName)[i].value=='N') {			
			document.getElementsByName(opFlagName)[i].value = "U";
			
			}
		}
	}
}
//for updating the debittotal
function updateTotal() {

	obj = arguments[0];
	var name =obj.name;

	var rowCount =obj.id.split(name)[1];

	rowModified = rowCount;

	//var passengerAmountInBilling = document.getElementsByName('passengerAmountInBilling');

	//var uatpAmountInBilling = document.getElementsByName('uatpAmountInBilling');

	//var zonea = document.getElementsByName('zonea');

	//var zoneb = document.getElementsByName('zoneb');

	//var zonec = document.getElementsByName('zonec');

	//var zoned = document.getElementsByName('zoned');

	var miscAmountInBilling = document.getElementsByName('miscAmountInBilling');

	var totalAmountInBilling = document.getElementsByName('totalAmountInBilling');

	var creditAmountInBilling = document.getElementsByName('creditAmountInBilling');

	var netValueInBilling = document.getElementsByName('netValueInBilling');

	if(obj.value == null || obj.value.trim().length == 0) {
		setMoneyValue(obj,"0.0");
	}

	var operation = new MoneyFldOperations();

	var moneyFldArr =  new Array();

	//moneyFldArr.push(zonea[rowCount]);
	//moneyFldArr.push(zoneb[rowCount]);
	//moneyFldArr.push(zonec[rowCount]);
	//moneyFldArr.push(zoned[rowCount]);
	//moneyFldArr.push(passengerAmountInBilling[rowCount]);
	//moneyFldArr.push(uatpAmountInBilling[rowCount]);
	moneyFldArr.push(miscAmountInBilling[rowCount]);

	operation.sumUpMonies(moneyFldArr,'setTotalAmtInBlg');

}

//function for setting the totalAmountInBilling
function setTotalAmtInBlg(){

	var totalAmountInBilling = document.getElementsByName('totalAmountInBilling');

	var creditAmountInBilling = document.getElementsByName('creditAmountInBilling');

	setMoneyValue(totalAmountInBilling[rowModified],arguments[0]);

	var operation = new MoneyFldOperations(totalAmountInBilling[rowModified]);

	if(creditAmountInBilling[rowModified].value == null
		|| creditAmountInBilling[rowModified].value.trim().length == 0) {

		setMoneyValue(creditAmountInBilling[rowModified],"0.0");
	}

	operation.substractMoney(creditAmountInBilling[rowModified],'setNetValInBlg');


}

//function for setting the netValueInBilling
function setNetValInBlg(){


	var netValueInBilling = document.getElementsByName('netValueInBilling');

	setMoneyValue(netValueInBilling[rowModified],arguments[0]);

	rowModified = '';
	updateSummary();
}


//function for updating the sum on footer
function updateSummary(){

	var name =obj.name;

	//var passengerAmountInBilling = document.getElementsByName('passengerAmountInBilling');

	//var uatpAmountInBilling = document.getElementsByName('uatpAmountInBilling');

	//var zonea = document.getElementsByName('zonea');

	//var zoneb = document.getElementsByName('zoneb');

	//var zonec = document.getElementsByName('zonec');

	//var zoned = document.getElementsByName('zoned');

	var miscAmountInBilling = document.getElementsByName('miscAmountInBilling');

	var creditAmountInBilling = document.getElementsByName('creditAmountInBilling');

	var totalAmountInBilling = document.getElementsByName('totalAmountInBilling');

	var netValueInBilling = document.getElementsByName('netValueInBilling');

	var operationFlag = document.getElementsByName('hiddenOperationFlag');   //for operation flag


	//var passengerTotArr =  new Array();
	//var uatpAmountTotArr =  new Array();
	//var zoneaTotArr =  new Array();
	//var zonebTotArr =  new Array();
	//var zonecTotArr =  new Array();
	//var zonedTotArr =  new Array();
	var miscTotArr =  new Array();
	var credTotArr =  new Array();
	var totalAmountInBillingArr =  new Array();
	var netValueInBillingArr =  new Array()	;


	for(var rowCount=0; rowCount<miscAmountInBilling.length - 1; rowCount++) {
		if(operationFlag[rowCount].value !='D' && operationFlag[rowCount].value  != "NOOP"){
		

			/*if(passengerAmountInBilling[rowCount].value == null
				|| passengerAmountInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(passengerAmountInBilling[rowCount],"0.0");
			}
			passengerTotArr.push(passengerAmountInBilling[rowCount]);

			if(uatpAmountInBilling[rowCount].value == null
				|| uatpAmountInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(uatpAmountInBilling[rowCount],"0.0");
			}
			uatpAmountTotArr.push(uatpAmountInBilling[rowCount]);

			if(zonea[rowCount].value == null
				|| zonea[rowCount].value.trim().length == 0) {
				setMoneyValue(zonea[rowCount],"0.0");
			}
			zoneaTotArr.push(zonea[rowCount]);

			if(zoneb[rowCount].value == null
				|| zoneb[rowCount].value.trim().length == 0) {
				setMoneyValue(zoneb[rowCount],"0.0");
			}
			zonebTotArr.push(zoneb[rowCount]);

			if(zonec[rowCount].value == null
				|| zonec[rowCount].value.trim().length == 0) {
				setMoneyValue(zonec[rowCount],"0.0");
			}
			zonecTotArr.push(zonec[rowCount]);

			if(zonec[rowCount].value == null
				|| zonec[rowCount].value.trim().length == 0) {
				setMoneyValue(zonec[rowCount],"0.0");
			}
			zonedTotArr.push(zoned[rowCount]);
			*/
			if(miscAmountInBilling[rowCount].value == null
				|| miscAmountInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(miscAmountInBilling[rowCount],"0.0");
			}
			miscTotArr.push(miscAmountInBilling[rowCount]);

			if(creditAmountInBilling[rowCount].value == null
				|| creditAmountInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(creditAmountInBilling[rowCount],"0.0");
			}
			credTotArr.push(creditAmountInBilling[rowCount]);

			if(totalAmountInBilling[rowCount].value == null
				|| totalAmountInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(totalAmountInBilling[rowCount],"0.0");
			}
			totalAmountInBillingArr.push(totalAmountInBilling[rowCount]);

			if(netValueInBilling[rowCount].value == null
				|| netValueInBilling[rowCount].value.trim().length == 0) {
				setMoneyValue(netValueInBilling[rowCount],"0.0");
			}
			netValueInBillingArr.push(netValueInBilling[rowCount]);
		}
	}


	var operation = new MoneyFldOperations();
	operation.startBatch();
	/*operation.sumUpMonies(passengerTotArr);
	operation.sumUpMonies(uatpAmountTotArr);
	operation.sumUpMonies(zoneaTotArr);
	operation.sumUpMonies(zonebTotArr);
	operation.sumUpMonies(zonecTotArr);
	operation.sumUpMonies(zonedTotArr);*/
	operation.sumUpMonies(miscTotArr);
	operation.sumUpMonies(credTotArr);
	operation.sumUpMonies(totalAmountInBillingArr);
	operation.sumUpMonies(netValueInBillingArr,'setAllSummaryTotals');
	operation.endBatch();

}

//For seting values of summary totals aftr running batch operation

function setAllSummaryTotals(){


	var resultMap = arguments[1];

	//firstResult  = resultMap.getResult(0);
	
	//secondResult = resultMap.getResult(1);
	//thirdResult = resultMap.getResult(2);
	//forthresult = resultMap.getResult(3);
	//fifthresult = resultMap.getResult(4);
	//sixthresult = resultMap.getResult(5);
	seventhresult = resultMap.getResult(0);
	eighthresult = resultMap.getResult(1);
	ninthresult = resultMap.getResult(2);
	tenthresult = resultMap.getResult(3);	
	

	/*document.getElementById("summaryPasgr").innerHTML = firstResult;
	

	document.getElementById("summaryUatp").innerHTML = secondResult;
	

	document.getElementById("summaryzonea").innerHTML = thirdResult;
	
	document.getElementById("summaryzoneb").innerHTML = forthresult;
	

	document.getElementById("summaryzonec").innerHTML = fifthresult;
	

	document.getElementById("summaryzoned").innerHTML = sixthresult;
	*/

	document.getElementById("summaryMisc").innerHTML = seventhresult;
	setMoneyValue(targetFormName.elements.miscTotalAmountInBilling,seventhresult);

	document.getElementById("summaryTotal").innerHTML = ninthresult;
	setMoneyValue(targetFormName.elements.grossTotalAmountInBilling,ninthresult);

	document.getElementById("summaryCredit").innerHTML = eighthresult;
	setMoneyValue(targetFormName.elements.creditTotalAmountInBilling,eighthresult);

	document.getElementById("summaryNet").innerHTML = tenthresult;
	setMoneyValue(targetFormName.elements.netTotalValueInBilling,tenthresult);
	

	

}




function populateAirlineDetails(targetFormName)
{

	var operationFlag = document.getElementsByName('hiddenOperationFlag'); 
		
			for(var i=0;i<operationFlag.length-1;i++){

				if(operationFlag[i].value=="I"){	

					if(targetFormName.elements.airlineCode[i].value != ""){

							if(targetFormName.elements.airlineNumber[i].value == ""){		
							targetFormName.elements.rowCounter.value=i;

								}
					}
				}	
			}
			
			recreateTableDetails('mailtracking.mra.airlinebilling.inward.captureFormThree.ajaxOnScreenLoad.do','parentNumericCode');
	
	
}
	
	
///////////////////////////////////////For Ajax Starts ////////////////////////////////////////////////////////////////////////////////
function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableDetails";
	_tableDivId = divId;
	
	asyncSubmit(document.forms[1],strAction,__extraFn,null,null,_tableDivId);

}

function updateTableDetails(_refreshInfo){
if(_asyncErrorsExist == false) {
document.getElementsByName("airlineNumber")[targetFormName.elements.rowCounter.value].value=_refreshInfo.document.getElementById("childNumericCode").innerHTML;
}


}

/////////////////////////////////////For Ajax Ends ///////////////////////////////////////////////////////////////////////////////////////////////
	




