<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{

	with(targetFormName){

		//CLICK Events
		evtHandler.addEvents("btnList","listucmexceptionlist()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearucmexceptionlist()",EVT_CLICK);
		evtHandler.addEvents("btnSave","saveucmexceptionlist()",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeucmexceptionlist()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(targetFormName)",EVT_BLUR);
		evtHandler.addEvents("addLink","adducmexceptionlist()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteucmexceptionlist()",EVT_CLICK);

		evtHandler.addEvents("airportCode","validateFields(this, -1, 'AirportCode', 1, true, true)",EVT_BLUR);
		evtHandler.addIDEvents("addLink","adducmexceptionlist()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteucmexceptionlist()",EVT_CLICK);	

	}
    applySortOnTable("ucmflightexceptionlist",new Array("None","String","Date")); 
	disableFeilds();
	onScreenLoad();
}
function resetFocus(frm){
	 if(!event.shiftKey){ 
				if((!frm.airportCode.disabled)  
				 &&	(frm.airportCode.readOnly == false)){
					frm.airportCode.focus();
				}
				else{
					 if(!document.getElementById('addLink').disabled){
						document.getElementById('addLink').focus();
					 }
				}				
	}	
}
function onScreenLoad(){

	if(targetFormName.listStatus.value != "N"){
		targetFormName.btnSave.disabled=true;
	}
	disableTableRows();
	setFocus();

	checkForDuplicateFlight();
}
function setFocus(){
	if(targetFormName.airportCode.disabled == false) {
			targetFormName.airportCode.focus();
	}
	else {
		var flightCarriers = document.getElementsByName('flightCarrier');
		if(flightCarriers != null && flightCarriers.length > 0 &&
			flightCarriers[flightCarriers.length - 1].readOnly == false) {
			flightCarriers[flightCarriers.length - 1].focus();

		}
		else {
			var addLink = document.getElementById('addLink');
			if(addLink.disabled == false) {
				addLink.focus();
			}
		}

	}
}
function disableFeilds(){
	var addLink = document.getElementById('addLink');
	var deleteLink = document.getElementById('deleteLink');
	if(targetFormName.airportCode.disabled == false) {
		disableLink(addLink);
		disableLink(deleteLink);
	}
	//disableTableRows();



}

function disableTableRows(){
	var flightSequenceNumbers = document.getElementsByName('flightSequenceNumber');
	var flightCarriers = document.getElementsByName('flightCarrier');
	var flightNumbers = document.getElementsByName('flightNumber');
	var flightDates = document.getElementsByName('flightDate');
	if(flightSequenceNumbers != null &&
		flightSequenceNumbers.length > 0) {
			for(var i = 0; i < flightSequenceNumbers.length; i++) {
				if(flightSequenceNumbers[i].value != null &&
					flightSequenceNumbers[i].value.trim().length > 0 &&
					flightSequenceNumbers[i].value != "0")  {
					flightCarriers[i].readOnly=true;
					flightNumbers[i].readOnly=true;
					//flightDates[i].readOnly=false;

				}
			}
	}
}
function checkForDuplicateFlight(){

	if(targetFormName.duplicateFlightStatus.value == "Y") {
		if(targetFormName.actionStatus.value == "add") {
			targetFormName.actionStatus.value == "add_duplicate";
		}
		else {
			targetFormName.actionStatus.value == "save_duplicate";
		}
		openPopUp("flight.operation.validateFlights.do","600","280");
	}
}


function listucmexceptionlist() {

	submitForm(targetFormName,'uld.defaults.messaging.listucmflightexceptionlist.do');
}

function clearucmexceptionlist() {

	//submitForm(targetFormName,'uld.defaults.messaging.clearucmflightexceptionlist.do');
	submitFormWithUnsaveCheck('uld.defaults.messaging.clearucmflightexceptionlist.do');
}

function saveucmexceptionlist() {
	targetFormName.actionStatus.value="save";
	submitForm(targetFormName,'uld.defaults.messaging.saveucmflightexceptionlist.do');
}

function adducmexceptionlist() {

	_currDivId="div1";
	var strAction = "uld.defaults.messaging.adducmflightexceptionlist.do";	
	recreateTableDetails(strAction,"div1");
}

function deleteucmexceptionlist() {
	if(validateSelectedCheckBoxes(targetFormName, 'selectedRows', 100000, 1)){
		recreateTableDetails('uld.defaults.messaging.deleteucmflightexceptionlist.do','div1');

	}
}

function closeucmexceptionlist() {

	location.href = appPath+"/home.jsp";
}
//Added by a-3045 for bug 37 starts
function showFocus(){
		var frm = targetFormName;
		var flightCarriers = document.getElementsByName("flightCarrier");
		var ind=flightCarriers.length-1;		
		if(ind > -1){
			flightCarriers[ind].focus();
		}
}
//Added by a-3045 for bug 37 ends


function screenConfirmDialog(frm, dialogId) {

	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deletelistuld.do');
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

function confirmMessage() {

	var frm = targetFormName;
	if(frm.closeStatus.value=="whetherToClose") {
		frm.closeStatus.value="canClose";
		submitForm(frm,'uld.defaults.closerepairinvoice.do');
	}

}

function nonconfirmMessage() {

}

function recreateTableDetails(strAction,divId) {	
	var _extraFn ="updateTableCodeBlur";
	var _currDivIdLeg = divId;
	if(arguments[2]!=null){
			_extraFn=arguments[2];
	}
	asyncSubmit(targetFormName,strAction,_extraFn,null,null,divId);
}

//function updateTableCodeBlur(_refreshInfo){
//	var _Leg = _refreshInfo.document.getElementById(_currDivIdLeg)	;
	//document.getElementById(_currDivIdLeg).innerHTML=_Leg.innerHTML;


	//setDefaultCheckbox();
	//setFocusToField();


//}



function updateTableCodeBlur(_tableInfo){
	//Added by a-3045 for bug 37 starts	
		setTimeout('showFocus()', 1000);		
	//Added by a-3045 for bug 37 ends	
	getActualData(_tableInfo);
	//_str=getActualData(_tableInfo);
	//document.getElementById(_tableInfo.currDivId).innerHTML=_str;		
	
}

function getActualData(_tableInfo){
	_frm=_tableInfo.document.getElementsByTagName("table")[0];	
	_str = _frm.outerHTML;
	document.getElementById(_tableInfo.currDivId).innerHTML=_str;	
	var _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	targetFormName.duplicateFlightStatus.value = _innerFrm.duplicateFlightStatus.value;
	targetFormName.actionStatus.value = _innerFrm.actionStatus.value;	
}




