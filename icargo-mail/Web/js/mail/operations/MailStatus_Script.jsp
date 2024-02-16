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
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus()",EVT_KEYDOWN);//Modified EVT_BLUR to EVT_KEYDOWN as part of ICRD-154386
	evtHandler.addIDEvents("flightlov","callFlightPopUp()",EVT_CLICK);
   }   
}

function onScreenLoad(){
	if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
		targetFormName.elements.fromDate.focus();
	}
	if(targetFormName.elements.validFlag.value=="Y"){
		if(targetFormName.elements.status.value=="GENERATE"){		
			targetFormName.elements.validFlag.value="N";
			generateReport(targetFormName,'/mailtracking.defaults.mailstatus.generateReport.do');
		}else{	
			targetFormName.elements.validFlag.value="N";
			generateReport(targetFormName,'/mailtracking.defaults.mailstatus.print.do');
		}
		targetFormName.elements.status.value = "";
	}
}

function resetFocus(){
//Modified as part of ICRD-154386
var evt = getPlatformEvent(); 
	 if(!evt.shiftKey){ 
		if((!targetFormName.elements.fromDate.disabled) && (!targetFormName.elements.fromDate.readOnly)){
			setTimeout(function(){
				targetFormName.elements.fromDate.focus();
			},0);
		}
	}	
}

function flightCheck(){
	var flag="Y";
	if((targetFormName.elements.carrierCode.value != "")&&
		(targetFormName.elements.flightCarrierCode.value != "") &&
		(targetFormName.elements.flightNumber.value!="")){		
    	  
		//showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.carrierorflight" />',1,self);
		showDialog({msg:'<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.carrierorflight" /> ',
					type:1,
					parentWindow:self,                                       
									
			});
    	return flag;
    }
    if((targetFormName.elements.flightCarrierCode.value!="")&&
    	(targetFormName.elements.flightNumber.value=="")){
    	
		//showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightnumberincomplete" />',1,self);
		showDialog({msg:'<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightnumberincomplete" /> ',
					type:1,
					parentWindow:self,                                       
									
			});
    	return flag;
    }
    if((targetFormName.elements.flightCarrierCode.value=="")&&
    	(targetFormName.elements.flightNumber.value!="")){
    	
		//showDialog('<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightnumberincomplete" />',1,self);
		showDialog({msg:'<bean:message bundle="mailStatusResources" key="mailtracking.defaults.msg.err.flightnumberincomplete" /> ',
					type:1,
					parentWindow:self,                                       
									
			});
    	return flag;
    }
}

function createReport(){
	var checkFlag=flightCheck();
	if(checkFlag != "Y"){
		targetFormName.elements.status.value	="GENERATE";		
		submitForm(targetFormName,'mailtracking.defaults.mailstatus.ValidateMailStatusFilterCommand.do');
	}
}
function print(){
	var checkFlag=flightCheck();
	if(checkFlag != "Y"){
		targetFormName.elements.status.value	="PRINT";	
		submitForm(targetFormName,'mailtracking.defaults.mailstatus.ValidateMailStatusFilterCommand.do');
	}
}
function clearDetails(){


	submitForm(targetFormName,'mailtracking.defaults.mailstatus.clear.do');
}
function closeScreen(){
	
									
	
	submitForm(targetFormName,'mailtracking.defaults.mailstatus.close.do');
}
function callFlightPopUp(){
	var frm = targetFormName;
	var carrierCode = 'flightCarrierCode';
	var flightNumber = 'flightNumber';
	openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=mailtracking.defaults.DailyMailStation&carrierCodeFldName=flightCarrierCode&flightNumberFldName=flightNumber&formNumber=1','700','380');
	//openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=mailtracking.defaults.mailstatus&carrierCodeFldName='+flightCarrierCode+'&flightNumberFldName='+flightNumber+'&formNumber=1','700','380');
}