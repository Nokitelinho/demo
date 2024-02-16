<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnGenerateReport","createReport()",EVT_CLICK);
   	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearDetails()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeReport()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
	evtHandler.addIDEvents("lovOrigin","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.flightOrigin.value,'Origin','1','flightOrigin','',0)",EVT_CLICK);
	evtHandler.addIDEvents("lovDestination","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.flightDestination.value,'Destination','1','flightDestination','',0)",EVT_CLICK);
    evtHandler.addIDEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOE.value,'OfficeOfExchange','1','originOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("destnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOE.value,'OfficeOfExchange','1','destinationOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("SCLov","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.subClassCode.value,'OfficeOfExchange','1','subClassCode','',0)",EVT_CLICK);
    evtHandler.addIDEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpaCode.value,'GPACode','1','gpaCode','',0)",EVT_CLICK);
   }   
}

function resetFocus(){
	 if(!event.shiftKey){ 
		 if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
			targetFormName.elements.fromDate.focus();
		}
   }   
}

function onScreenLoad(){
	if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
		targetFormName.elements.fromDate.focus();
	}
	if(targetFormName.elements.validFlag.value=="Y"){
		if(targetFormName.elements.status.value=="GENERATE"){	
			targetFormName.elements.validFlag.value="N";
			generateReport(targetFormName,'/mailtracking.defaults.damagemailreport.generateReport.do');
		}else{
			targetFormName.elements.validFlag.value="N";
			generateReport(targetFormName,'/mailtracking.defaults.damagemailreport.print.do');
		}
		targetFormName.elements.status.value = "";
	}
}
function createReport(){
		targetFormName.elements.status.value	="GENERATE";		
		submitForm(targetFormName,'mailtracking.defaults.damagemailreport.validate.do');
}
function print(){
		targetFormName.elements.status.value	="PRINT";	
		submitForm(targetFormName,'mailtracking.defaults.damagemailreport.validate.do');
}
function clearDetails(){

	submitForm(targetFormName,'mailtracking.defaults.damagemailreport.clear.do');
}
function closeReport(){
	
	submitForm(targetFormName,'mailtracking.defaults.damagemailreport.close.do');
}