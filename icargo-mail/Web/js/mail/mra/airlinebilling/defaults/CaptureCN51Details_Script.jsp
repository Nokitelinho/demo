<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm = targetFormName;
	with(targetFormName){
		evtHandler.addEvents("btnNew","doAdd()",EVT_CLICK);
		evtHandler.addEvents("btSave","doSave()",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("carriageFromlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriagesFrom.value,'Carriage From','0','carriagesFrom','',0)",EVT_CLICK);
		evtHandler.addEvents("carriageTolov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriagesTo.value,'Carriage To','0','carriagesTo','',0)",EVT_CLICK);
		evtHandler.addEvents("rate","change()",EVT_BLUR);
		
		evtHandler.addEvents("wtLCAO","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtCP","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtUld","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("wtSv","restrictFloat(this,8,3)",EVT_KEYPRESS);
		evtHandler.addEvents("rate","restrictFloat(this,8,3)",EVT_KEYPRESS);
	
	}
	screenload(frm);
}
function screenload(frm){
	//frm.wtSal.disabled=true;

	if("Y" == frm.elements.popUpCloseFlag.value){
        frm = self.opener.targetFormName;
       	frm.action="mailtracking.mra.airlinebilling.defaults.capturecn51.refresh.do";
       	frm.method="post";
       	frm.submit();
       	window.close();
       	return;
	}
}
function validateFields(){
	var frm = targetFormName;
	if(frm.elements.carriagesFrom.value == '' && frm.elements.carriagesTo.value == ''){
		showDialog('Carriage From and Carriage To are mandatory', 1, self);
		frm.carriagesFrom.focus();
		return "N";
	}
	if(frm.elements.carriagesFrom.value == ''){
		showDialog('Carriage From is mandatory', 1, self);
		frm.carriagesFrom.focus();
		return "N";
	}
	if(frm.elements.carriagesTo.value == ''){
		showDialog('Carriage To is mandatory', 1, self);
		frm.carriagesTo.focus();
		return "N";
	}
	if(frm.elements.categories.value == ''){
		showDialog('Category is mandatory', 1, self);
		frm.categories.focus();
		return "N";
	}
	
   
}

function validateWeightAndRate(){
var frm = targetFormName;
	if((frm.elements.wtLCAO.value == '' || frm.elements.wtLCAO.value == '0' || frm.elements.wtLCAO.value == '0.0'|| frm.elements.wtLCAO.value == '0.00')
		&&(frm.elements.wtCP.value == '' || frm.elements.wtCP.value == '0' || frm.elements.wtCP.value == '0.0'|| frm.elements.wtCP.value == '0.00')
		&&(frm.elements.wtSv.value == '' || frm.elements.wtSv.value == '0' || frm.elements.wtSv.value == '0.0'|| frm.elements.wtSv.value == '0.00')
		&&(frm.elements.wtEms.value == '' || frm.elements.wtEms.value == '0' || frm.elements.wtEms.value == '0.0'|| frm.elements.wtEms.value == '0.00')) {
		showDialog('Weight is mandatory', 1, self);
		return "N";
	}
	
    if(frm.elements.rate.value == '' || frm.elements.rate.value == '0' || frm.elements.rate.value =='0.0' || frm.elements.rate.value =='0.00'){
		showDialog('Rate is mandatory', 1, self);
		frm.rate.focus();
		return "N";
	}

}
function doAdd(){
	var inComplete = validateFields();
	var invalidData = validateWeightAndRate();
	if("N" == inComplete || "N" == invalidData){
		return;
	}
	var frm = targetFormName;
	submitForm(frm, "mailtracking.mra.airlinebilling.defaults.capturecn51.addnewcn51details.do");
}
function doSave(){
	var inComplete = validateFields();
	var invalidData = validateWeightAndRate();
	if("N" == inComplete || "N" == invalidData){
		return;
	}
	var frm = targetFormName;
	submitForm(frm, "mailtracking.mra.airlinebilling.defaults.capturecn51.okcn51details.do");
}
function doClose(){
	var frm = targetFormName;
	submitForm(frm, "mailtracking.mra.airlinebilling.defaults.capturecn51.closecn51details.do");
}

function selectNextRateLineDetails(strLastPageNum, strDisplayPage) {

	var inComplete = validateFields();
	var invalidData = validateWeightAndRate();
	if("N" == inComplete || "N" == invalidData){
		return;
	}
	
	var frm = targetFormName;
	frm.elements.lastPageNum.value = strLastPageNum;
	frm.elements.displayPage.value = strDisplayPage;
	submitForm(frm, "mailtracking.mra.airlinebilling.defaults.capturecn51.selectnextcn51details.do");
}

function change(){
	////("*---");
	
	var rate = targetFormName.elements.rate.value;	
	var lc=targetFormName.elements.wtLCAO.value;
	var cp=targetFormName.elements.wtCP.value;
	//var uld=targetFormName.wtUld.value;
	var ems=targetFormName.elements.wtEms.value;
	var sv=targetFormName.elements.wtSv.value;
	var val=0.0;	
		
	if(lc>0.0) {
		val=lc;
	
	}else if(cp>0.0){
			val=cp;
				
		}
			
	
	
	else if(sv>0.0){
	val=sv;
			
	}			
	else if(ems>0.0){
	val=ems;
	}			
var operation = new MoneyFldOperations();

operation.multiplyRawMonies(val,rate,targetFormName.blgCurCode.value.toUpperCase(),'false','onMultiplyAdjAmt');
	
}



function onMultiplyAdjAmt(){
	setMoneyValue(targetFormName.totalAmount,arguments[0]);
}

function onAmtChange(){
	var netamt = 0;
	var operationFlag = document.getElementsByName('operationFlag');
	var amt=document.getElementsByName("totalAmount");
	if(operationFlag.value!="D" && operationFlag.value!="NOOP"){
		netamt=netamt+Number(amt.value);
	}
	document.getElementById("netamt").innerHTML= netamt;
}
function weightCheck(subGroup){


	var frm = targetFormName;
	var totalWeight = document.getElementsByName(subGroup);
	var zero = "0";	
	if('wtLCAO' == subGroup && totalWeight[0].value != zero ){
		frm.wtCP.value = zero;
		//frm.wtSal.value = zero;
		frm.elements.wtUld.value = zero;
		frm.elements.wtSv.value = zero;
		frm.elements.rate.focus();
	}else if ('wtCP'==subGroup  && totalWeight[0].value != zero ){
		frm.elements.wtLCAO.value =zero;
		//frm.wtSal.value = zero;
		frm.elements.wtUld.value = zero;
		frm.elements.wtSv.value = zero;
		frm.rate.focus();
	/*}else if ('wtSal'==subGroup  && totalWeight[0].value != zero ){
		frm.wtLCAO.value = zero;
		frm.wtCP.value = zero;
		frm.wtUld.value = zero;
		frm.wtSv.value = zero;
		frm.rate.focus();*/
	/*}else if ('wtUld'==subGroup && totalWeight[0].value != zero ){
		frm.wtCP.value = zero;
		//frm.wtSal.value = zero;
		frm.wtLCAO.value = zero;
		frm.wtSv.value = zero;
		frm.rate.focus();*/
	}else if ('wtEms'==subGroup && totalWeight[0].value != zero ){
		frm.elements.wtCP.value = zero;
		//frm.wtSal.value = zero;
		frm.elements.wtLCAO.value = zero;
		frm.elements.wtSv.value = zero;
		frm.rate.focus();
	}else if ('wtSv'==subGroup && totalWeight[0].value != zero ){
		frm.elements.wtCP.value = zero;
		//frm.wtSal.value = zero;
		frm.elements.wtUld.value = zero;
		frm.elements.wtLCAO.value = zero;
		frm.rate.focus();
	}
}