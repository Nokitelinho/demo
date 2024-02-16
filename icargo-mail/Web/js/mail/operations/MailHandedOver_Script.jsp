<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

	   	
	   	evtHandler.addEvents("btnGenerateReport","createReport()",EVT_CLICK);
	   	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearDetails()",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
	   	evtHandler.addIDEvents("airlineLOV","showAirlineLOV()", EVT_CLICK);
		evtHandler.addEvents("flightlov","callFlightPopUp()",EVT_CLICK);
   	
   	}
   	
}
  
function resetFocus(){
	 if(!event.shiftKey){ 
		if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
			targetFormName.elements.fromDate.focus();
		}
	}	
}
  
  function showAirlineLOV(){
        displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carrierCode.value,'Airline','1','carrierCode','',0);
    }
function onScreenLoad(){
	if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
		targetFormName.elements.fromDate.focus();
	}
	if(targetFormName.elements.validFlag.value=="Y"){
		if(targetFormName.elements.status.value=="GENERATE"){		
			targetFormName.elements.validFlag.value="N";	
			generateReport(targetFormName,'/mailtracking.defaults.mailhandedover.generatereport.do');
		}else{		
			targetFormName.elements.validFlag.value="N";
			generateReport(targetFormName,'/mailtracking.defaults.mailhandedover.printreport.do');
		}
		targetFormName.elements.status.value = "";
	}
}
function flightCheck(){
	var flag="Y";
	if((targetFormName.elements.carrierCode.value != "")&&
		(targetFormName.elements.flightCarrierCode.value != "") &&
		(targetFormName.elements.flightNumber.value!="")){		
    	  
		showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.carrierorflight" />',1,self);
    	return flag;
    }
    if((targetFormName.elements.flightCarrierCode.value!="")&&
    	(targetFormName.elements.flightNumber.value=="")){
    	
		showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightnumbernull" />',1,self);
    	return flag;
    }
    if((targetFormName.elements.flightCarrierCode.value=="")&&
    	(targetFormName.elements.flightNumber.value!="")){
    	
		showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightcarrierCodenull" />',1,self);
    	return flag;
    }
}

function createReport(){
		targetFormName.elements.status.value	="GENERATE";		
		submitForm(targetFormName,'mailtracking.defaults.mailhandedover.validate.do');
}
function print(){
		targetFormName.elements.status.value	="PRINT";	
		submitForm(targetFormName,'mailtracking.defaults.mailhandedover.validate.do');
}
function clearDetails(){

	submitForm(targetFormName,'mailtracking.defaults.mailhandedover.clear.do');
}
function closeScreen(){
	
	submitForm(targetFormName,'mailtracking.defaults.mailhandedover.close.do');
}
function callFlightPopUp(){
	var frm = targetFormName;
	var carrierCode = 'flightCarrierCode';
	var flightNumber = 'flightNumber';
	
	openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=mailtracking.defaults.DailyMailStation&carrierCodeFldName=flightCarrierCode&flightNumberFldName=flightNumber&formNumber=1','700','380');
}