<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
 
  var frm=targetFormName;
  
  //initial focus on page load
    if(frm.elements.reportIdentifiers.disabled == false){
       frm.elements.reportIdentifiers.focus();
  }
	with(frm){	   
	   evtHandler.addEvents("reportIdentifiers","togglePanel(1,targetFormName.elements.reportIdentifiers)",EVT_CHANGE);
	   evtHandler.addEvents("btnPrint","Print()",EVT_CLICK);
	   evtHandler.addEvents("btnPrintTK","PrintTK()",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov1","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodePeriodBillSmy.value,'Gpa Code','1','gpaCodePeriodBillSmy','gpaNamePeriodBillSmy','gpaNamePeriodBillSmy',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov2","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeGpaBillSmy.value,'Gpa Code','1','gpaCodeGpaBillSmy','gpaNameGpaBillSmy','gpaNameGpaBillSmy',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov3","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodePeriod51.value,'Gpa Code','1','gpaCodePeriod51','gpaNamePeriod51','gpaNamePeriod51',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov4","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeGpa51.value,'Gpa Code','1','gpaCodeGpa51','gpaNameGpa51','gpaNameGpa51',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov5","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodePeriod66.value,'Gpa Code','1','gpaCodePeriod66','gpaNamePeriod66','gpaNamePeriod66',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov6","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCodeGpa66.value,'Gpa Code','1','gpaCodeGpa66','gpaNameGpa66','gpaNameGpa66',0)",EVT_CLICK);
	   evtHandler.addIDEvents("gpaCodelov7","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'Gpa Code','1','gpaCode','gpaName','gpaName',0)",EVT_CLICK);
	   evtHandler.addIDEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.country.value,'Country Code','1','country','',0)",EVT_CLICK);
	   evtHandler.addEvents("btnClose","wdclose()",EVT_CLICK);
	   evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	   
	   //added by A-5117 
	    evtHandler.addEvents("gpaCodePeriodBillSmy","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCodeGpaBillSmy","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCodePeriod51","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCodeGpa51","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCodePeriod66","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCodeGpa66","populateGpaName(targetFormName)", EVT_CHANGE);
		evtHandler.addEvents("gpaCode","populateGpaName(targetFormName)", EVT_CHANGE);
	   
	   
	   }
	   
   }
function populateGpaName(targetFormName) {
var reportidentifier = targetFormName.elements.reportIdentifiers.value;
if(targetFormName.elements.gpaCode.value != ""){
	gpacode = targetFormName.elements.gpaCode.value;
	var strAction = "mailtracking.defaults.gpa.list.do?gpaCode="
		+gpacode+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname");
}
if(targetFormName.elements.gpaCodePeriodBillSmy.value != ""){
var gpacodeperiod = targetFormName.elements.gpaCodePeriodBillSmy.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodePeriodBillSmy="+gpacodeperiod+"&selectedReport="+reportidentifier;
	recreateTableForGpa(strAction,"gpaname1");
}
if(targetFormName.elements.gpaCodeGpaBillSmy.value != ""){
var gpaCodegpaBillSmy = targetFormName.elements.gpaCodeGpaBillSmy.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodeGpaBillSmy="+gpaCodegpaBillSmy+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname2");
}
if(targetFormName.elements.gpaCodePeriod51.value != ""){
var gpaCodePrd51 = targetFormName.elements.gpaCodePeriod51.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodePeriod51="+gpaCodePrd51+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname3");
}
if(targetFormName.elements.gpaCodeGpa51.value != ""){
var gpaCodegpa51 = targetFormName.elements.gpaCodeGpa51.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodeGpa51="+gpaCodegpa51+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname4");
}
if(targetFormName.elements.gpaCodePeriod66.value != ""){
var gpaCodePrdd66 = targetFormName.elements.gpaCodePeriod66.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodePeriod66="+gpaCodePrdd66+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname5");
}
if(targetFormName.elements.gpaCodeGpa66.value != ""){
var gpaCodgpa66 = targetFormName.elements.gpaCodeGpa66.value;
var strAction = "mailtracking.defaults.gpa.list.do?gpaCodeGpa66="+gpaCodgpa66+"&selectedReport="+reportidentifier;
		recreateTableForGpa(strAction,"gpaname6");
}

   
}
function recreateTableForGpa(strAction,divId){


	_tableDivId = divId;
	var str = "";
	if(targetFormName.elements.gpaCode.value != ""){
	str = targetFormName.elements.gpaCode.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodePeriodBillSmy.value != ""){	
	str = targetFormName.elements.gpaCodePeriodBillSmy.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodeGpaBillSmy.value != ""){
	str = targetFormName.elements.gpaCodeGpaBillSmy.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodePeriod51.value != ""){
	str = targetFormName.elements.gpaCodePeriod51.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodeGpa51.value != ""){
	str = targetFormName.elements.gpaCodeGpa51.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodePeriod66.value != ""){
	str = targetFormName.elements.gpaCodePeriod66.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	if(targetFormName.elements.gpaCodeGpa66.value != ""){
	str = targetFormName.elements.gpaCodeGpa66.value;
	asyncSubmit(targetFormName,strAction,_tableDivId,null,str);
	}
	
	
	
}
function gpaname(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualData(_tableInfo);
	document.getElementById("gpaname").innerHTML=_str;
	}
function gpaname1(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname1(_tableInfo);
	document.getElementById("gpaname1").innerHTML=_str;
	}
function gpaname2(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname2(_tableInfo);
	document.getElementById("gpaname2").innerHTML=_str;
	}
function gpaname3(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname3(_tableInfo);
	document.getElementById("gpaname3").innerHTML=_str;
	}
function gpaname4(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname4(_tableInfo);
	document.getElementById("gpaname4").innerHTML=_str;
	}
function gpaname5(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname5(_tableInfo);
	document.getElementById("gpaname5").innerHTML=_str;
	}
function gpaname6(_tableInfo){
	_str = "";
	_innerFrm=_tableInfo.document.getElementsByTagName("form");
	_str=getActualDataOfGpaname6(_tableInfo);
	document.getElementById("gpaname6").innerHTML=_str;
	}
	
 function getActualData(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv");
	return _frm.outerHTML;
}
function getActualDataOfGpaname1(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv1");
	return _frm.outerHTML;
}
function getActualDataOfGpaname2(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv2");
	return _frm.outerHTML;
}
function getActualDataOfGpaname3(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv3");
	return _frm.outerHTML;
}
function getActualDataOfGpaname4(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv4");
	return _frm.outerHTML;
}
function getActualDataOfGpaname5(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv5");
	return _frm.outerHTML;
}
function getActualDataOfGpaname6(_referenceDetails){
	_frm=_referenceDetails.document.getElementById("gpaNameDiv6");
	return _frm.outerHTML;
}

	   
function togglePanel(iState,comboObj) // 1 visible, 0 hidden
	{
clearFields();
		if(comboObj != null) {

	   	    var divID = comboObj.value;

	  	    var comboLength = comboObj.length;
			var obj = null;
			var comboValues = null;

			var divValues = ['RPTMRA033','RPTMRA034','RPTMRA035','RPTMRA036','RPTMRA037','RPTMRA038','RPTMRA039'];
			var length = divValues.length;

			for(var index=0; index<length; index++) {
			
				if(divValues[index] == divID) {
					/* var divObj = document.layers ? document.layers[divID] :
								 document.document ?  document.getElementById(divID).style :
						 		 document.all[divID].style; */
					
					document.getElementById(divValues[index]).style.display = "block";
					//divObj.visibility = document.layers ? (1 ? "show" : "hide") : (1 ? "visible" : "hidden");

				}
				else {

					/* var divAlt = document.layers ? document.layers[divValues[index]] :
								 document.getElementById ?  document.getElementById(divValues[index]).style :
								 document.all[divValues[index]].style; */

					//divAlt.visibility = document.layers ? (0 ? "show" : "hide") : (0 ? "visible" : "hidden");
					document.getElementById(divValues[index]).style.display = "none";
		    	}

			}
		}

	}
	
function Print(){
targetFormName.elements.selectedReport.value=targetFormName.elements.reportIdentifiers.value;
			var reports = ['RPTMRA033','RPTMRA034','RPTMRA035','RPTMRA036','RPTMRA037','RPTMRA038','RPTMRA039'];
			var actions = ['/mailtracking.mra.gpabilling.reports.printblgsmyperiodwise.do','/mailtracking.mra.gpabilling.reports.printblgsmygpawise.do',
			'/mailtracking.mra.gpabilling.reports.printcn51periodwise.do','/mailtracking.mra.gpabilling.reports.printcn51gpawise.do',
			'/mailtracking.mra.gpabilling.reports.printcn66periodwise.do','/mailtracking.mra.gpabilling.reports.printcn66gpawise.do',
			'/mailtracking.mra.gpabilling.reports.printgpabillablereport.do'];
				
			for(var index=0;index<actions.length;index++){
				if(targetFormName.elements.selectedReport.value == reports[index]){			
					if(targetFormName.elements.gpaCodePeriodBillSmy.value==""){
						targetFormName.elements.gpaNamePeriodBillSmy.value="";
					}
					if(targetFormName.elements.gpaCodePeriod66.value==""){
						targetFormName.elements.gpaNamePeriod66.value="";
					}
					if(targetFormName.elements.gpaCodeGpa51.value==""){
						targetFormName.elements.gpaNameGpa51.value="";
					}
					if(targetFormName.elements.gpaCodePeriod51.value==""){
						targetFormName.elements.gpaNamePeriod51.value="";
					}
					if(targetFormName.elements.gpaCodeGpaBillSmy.value==""){
						targetFormName.elements.gpaNameGpaBillSmy.value="";
					}
					
					generateReport(targetFormName,actions[index]);
					
					}
			}
}
function PrintTK(){
targetFormName.elements.selectedReport.value=targetFormName.elements.reportIdentifiers.value;
			var reports = ['RPTMRA033','RPTMRA034','RPTMRA035','RPTMRA036','RPTMRA037','RPTMRA038','RPTMRA039'];
			var actions = ['/mailtracking.mra.gpabilling.reports.printblgsmyperiodwise.do','/mailtracking.mra.gpabilling.reports.printblgsmygpawise.do',
			'/mailtracking.mra.gpabilling.reports.printcn51periodwise.do','/mailtracking.mra.gpabilling.reports.printcn51gpawise.do',
			'/mailtracking.mra.gpabilling.reports.printcn66periodwise.do','/mailtracking.mra.gpabilling.reports.printcn66gpawise.do',
			'/mailtracking.mra.gpabilling.reports.printgpabillablereport.do'];
			for(var index=0;index<actions.length;index++){
				if(targetFormName.elements.selectedReport.value == reports[index]){			
					if(targetFormName.elements.gpaCodePeriodBillSmy.value==""){
						targetFormName.elements.gpaNamePeriodBillSmy.value="";
					}
					if(targetFormName.elements.gpaCodePeriod66.value==""){
						targetFormName.elements.gpaNamePeriod66.value="";
					}
					if(targetFormName.elements.gpaCodeGpa51.value==""){
						targetFormName.elements.gpaNameGpa51.value="";
					}
					if(targetFormName.elements.gpaCodePeriod51.value==""){
						targetFormName.elements.gpaNamePeriod51.value="";
					}
					if(targetFormName.elements.gpaCodeGpaBillSmy.value==""){
						targetFormName.elements.gpaNameGpaBillSmy.value="";
					}
					targetFormName.elements.specificFlag.value="Y";
					generateReport(targetFormName,actions[index]);	

					}
			}

}

function wdclose(){

location.href = appPath + "/home.jsp";

}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.reportIdentifiers.disabled == false){
			targetFormName.elements.reportIdentifiers.focus();
		}
	}
}
function clearFields(){
 var frm=targetFormName;
 frm.elements.gpaCodeGpa51.value="";
  frm.elements.gpaCodePeriod51.value="";
  frm.elements.gpaNamePeriod51.value="";
   frm.elements.gpaNameGpa51.value="";
     frm.elements.toDateGpa51.value="";
  frm.elements.fromDateGpa51.value="";
  frm.elements.toDatePeriod51.value="";
   frm.elements.fromDatePeriod51.value="";
   
 frm.elements.fromDatePeriodBillSmy.value="";
  frm.elements.toDatePeriodBillSmy.value="";
  frm.elements.gpaCodePeriodBillSmy.value="";
   frm.elements.gpaNamePeriodBillSmy.value="";
     frm.elements.fromDateGpaBillSmy.value="";
  frm.elements.toDateGpaBillSmy.value="";
  frm.elements.gpaCodeGpaBillSmy.value="";
   frm.elements.gpaNameGpaBillSmy.value="";
     
   frm.elements.fromDatePeriod66.value="";
  frm.elements.toDatePeriod66.value="";
  frm.elements.gpaCodePeriod66.value="";
   frm.elements.gpaCodePeriod66.value="";
     frm.elements.fromDateGpa66.value="";
  frm.elements.toDateGpa66.value="";
  frm.elements.gpaCodeGpa66.value="";
   frm.elements.gpaNameGpa66.value="";
  frm.elements.country.value="";
   frm.elements.gpaCode.value=""; 
  frm.elements.billingStatus.value="BB"; 
  frm.elements.gpaName.value="";  
 
}