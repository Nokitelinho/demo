<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm = targetFormName;
	with( frm) {
		//onScreenloadSetHeight();
		evtHandler.addEvents("btnList",  "onList()",  EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnView",  "onView()",  EVT_CLICK);
		evtHandler.addEvents("btnClose", "onClose()", EVT_CLICK);
		evtHandler.addEvents("airlinecodelov", "displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCodeFilterField.value,'Airline Code','1','airlineCodeFilterField','airlineNumber','',0)", EVT_CLICK);
		evtHandler.addEvents("airlinenolov", "displayLOVCodeNameAndDesc('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineNumber.value,'Airline Number Code','1','airlineCodeFilterField','airlineNumber','',0)", EVT_CLICK);
		evtHandler.addEvents("clearancePeriodLOV","displayLOV('showClearancePeriods.do','N','N','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		//evtHandler.addEvents("clearancePeriodLOV","clearanceLOV()",EVT_CLICK);

		evtHandler.addEvents("selectCheckBox","toggleTableHeaderCheckbox('selectCheckBox',targetFormName.elements.allCheck)", EVT_CLICK);
		evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.elements.allCheck, targetFormName.elements.selectCheckBox)", EVT_CLICK);
		if(targetFormName.elements.clearancePeriod.readOnly==false || targetFormName.elements.clearancePeriod.disabled==false){
			targetFormName.elements.clearancePeriod.focus();
		}
 applySortOnTable("Form-1Details",new Array("None","None","None","None","None","None","None")); 
		evtHandler.addEvents("airlineCodeFilterField","populateAirlineName(targetFormName)",EVT_BLUR);
	}
}

function clearanceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
displayLOV('showUPUClearancePeriods.do','N','Y','showUPUClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0,_reqHeight);
}
/**
 * Function to set client height
 */
function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	document.getElementById('pageDiv').style.height = ((height*91)/100)+'px';
	document.getElementById('div1').style.height = ((height*63)/100)+'px';
}

function submitPage1(lastPg,displayPg){
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	submitForm(targetFormName,'mra.airlinebilling.outward.listformones.do');

}

function onClear(){

	var frm=targetFormName;
	submitForm(frm,'mra.airlinebilling.outward.clearformones.do');


}


function onView(){

	var frm=targetFormName;
	var select = document.getElementsByName('select');

	var check = document.getElementsByName('selectCheckBox');

				for(var i=0;i<check.length;i++) {

						if(check[i].checked){

							select=i;

					}
			}

	//alert("select"+select);
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(targetFormName,'selectCheckBox',1,1)){
	     submitForm(frm,"mra.airlinebilling.outward.viewformone.do?select="+select);
	}



}

function onClose(){

	var frm=targetFormName;
	submitForm(frm,'mra.airlinebilling.outward.closeformones.do');

}


function onList(){
   	frm = targetFormName;
	frm.elements.displayPage.value = "1";
    	frm.elements.lastPageNum.value = "0";
    	var clearancePeriodStr =  targetFormName.elements.clearancePeriod.value;
	if(clearancePeriodStr == null || clearancePeriodStr ==""){
		showDialog({msg:'<common:message bundle="listForm1bundle" key="mra.airlinebilling.outward.listformone.msg.err.clearanceperiodmandatory" scope="request"/>', type:1,parentWindow: self});
		targetFormName.elements.clearancePeriod.focus();
		return;
	}
	else{
		submitForm(frm,'mra.airlinebilling.outward.listformones.do');
	}

}




function populateAirlineName(targetFormName)
{


if(targetFormName.elements.airlineCodeFilterField.value != ""){
if(targetFormName.elements.airlineNumber.value == ""){

	//submitForm(targetFormName,'mailtracking.mra.airlinebilling.outward.populateairlinenameinlistformone.do');

	recreateTableDetails('mailtracking.mra.airlinebilling.outward.populateairlinenameinlistformone.do','ListFormOneParent');


		}
	}
}

///////////////////////////////////////For Ajax Starts ////////////////////////////////////////////////////////////////////////////////
function recreateTableDetails(strAction,divId){

	var __extraFn="updateTableDetails";
	_tableDivId = divId;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null,_tableDivId);
}

function updateTableDetails(_tableInfo){

	_innerFrm=_tableInfo.document.getElementsByTagName("listFormOneForm")[0];
	_filter=_tableInfo.document.getElementById("ListFormOneChild");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
}

/////////////////////////////////////For Ajax Ends ///////////////////////////////////////////////////////////////////////////////////////////////


