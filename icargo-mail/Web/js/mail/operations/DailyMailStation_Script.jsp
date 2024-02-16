<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister(){
	var frm = targetFormName;
	onScreenLoad();
	with(frm){
		
		evtHandler.addEvents("btnClear","clear();",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeScreen();",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus();",EVT_BLUR);
		evtHandler.addEvents("btnPrint","prinreport();",EVT_CLICK);
		evtHandler.addEvents("btnGenreport","genreport();",EVT_CLICK);
		evtHandler.addEvents("flightlov","callFlightPopUp()",EVT_CLICK);
	}
	
	
}

function resetFocus(){
	 if(!event.shiftKey){ 
		 if((!targetFormName.elements.flightFromDate.disabled) && (!targetFormName.elements.flightFromDate.readOnly)){
			targetFormName.elements.flightFromDate.focus();
		}
	}	
}

function closeScreen(){
	submitForm(targetFormName, 'mailtracking.defaults.DailyMailStation.close.do');
}

function clear(){
	submitForm(targetFormName, 'mailtracking.defaults.DailyMailStation.clear.do');
}

function genreport(){
   validatefilter();
}

function prinreport(){
targetFormName.elements.printstatus.value="PRINT";
validatefilter();
}
function callFlightPopUp(){
	var frm = targetFormName;
	var carrierCode = 'carrierCode';
	var flightNumber = 'flightNumber';
	//var flightDate = 'flightDate';
	//openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=mailtracking.defaults.DailyMailStation&carrierCodeFldName='+carrierCode+'&flightNumberFldName='+flightNumber+'&flightDateFldName='+flightDate+"&flightNumber="+frm.elements.flightNumber.value+"&flightDate="+frm.elements.flightDate.value+'&formNumber=1','700','380');
	openPopUp('flight.operation.screenloadflightlov.do?parentScreenId=mailtracking.defaults.DailyMailStation&carrierCodeFldName='+carrierCode+'&flightNumberFldName='+flightNumber+"&flightNumber="+frm.elements.flightNumber.value+'&formNumber=1','700','380');
}

function onScreenLoad(){
	var frm = targetFormName;
	if((!frm.elements.flightFromDate.disabled) && (!frm.elements.flightFromDate.readOnly)){
		frm.elements.flightFromDate.focus();
	}
	if(frm.elements.validreport.value=="OK"){
		if(targetFormName.elements.printstatus.value=="PRINT")
		{
		generateReport(frm,'/mailtracking.defaults.dailymailstation.printreport.do');	
		}
		else
		{
	generateReport(frm,'/mailtracking.defaults.dailymailstation.genreport.do');
		}
	}
	targetFormName.elements.printstatus.value="";
}


function validatefilter()
{
var frm = targetFormName; 
	submitForm(targetFormName, 'mailtracking.defaults.dailymailstation.validate.do'); 
}

function chksize()
{
var frm = targetFormName;
var  carCode=document.getElementsByName("carrierCode");
if(carCode.length<2)
{
	 showDialog({msg:'<bean:message bundle="DailyMailStationResources" key="mailtracking.defaults.DailyMailStation.msg.alrt.flightnumnotok" />',type:1,parentWindow:self});
		return;
}
}
