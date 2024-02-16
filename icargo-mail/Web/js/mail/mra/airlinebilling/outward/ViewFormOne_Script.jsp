<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm = targetFormName;
	with( frm) {

		evtHandler.addEvents("btnList",  "onList()",  EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose()", EVT_CLICK);
		evtHandler.addEvents("clearancePeriodlov","displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		evtHandler.addEvents("airlineCode","populateAirlineNo(targetFormName)",EVT_BLUR);
		evtHandler.addIDEvents("airlineCodeLov", "shwAirlineLov()", EVT_CLICK);
		evtHandler.addIDEvents("clearancePeriodlov","displayClearancePeriod()",EVT_CLICK);
		if(targetFormName.elements.clearancePeriod.readOnly==false || targetFormName.elements.clearancePeriod.disabled==false){
			targetFormName.elements.clearancePeriod.focus();
		}

	}
}


function shwAirlineLov(){
	displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline Code','1','airlineCode','airlineNumber','',0)
	targetFormName.elements.airlineNumber.focus();
}

function displayClearancePeriod(){

	displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0);
	targetFormName.elements.airlineCode.focus();

}
function onClear(){

	var frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.outward.viewformone.clear.do');


}



function onClose(){

	var frm=targetFormName;
	submitForm(frm,'mailtracking.mra.airlinebilling.outward.viewformone.close.do');


}


function onList(){
   var frm=targetFormName;
   submitForm(frm,'mailtracking.mra.airlinebilling.outward.viewformone.list.do');



}




/*function populateAirlineNo(frm)
{



// submitForm(frm,'mailtracking.mra.airlinebilling.outward.ajaxscreenloadviewform1.do');


if(frm.airlineCode.value != ""){
if(frm.airlineNumber.value == ""){
		
	
	frm.action="mailtracking.mra.airlinebilling.outward.ajaxscreenloadviewform1.do";
	divIdSeg="checkScreenRefresh_new";
	recreateTableDetails(frm.action,'viewFormoneParent',divIdSeg);
	

		}
	}	
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
*/
////////////////// FOR ASYNC SUBMIT ENDS///////////////////////////////////////////////



