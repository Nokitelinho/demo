<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){

     //CLICK Events
		
     	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","close()",EVT_CLICK);
		
		evtHandler.addEvents("stationLov","showLov(targetFormName)",EVT_CLICK);
		
	
		
   }

}


function print(){

 
generateReport(targetFormName,'/mailtracking.mra.gpabilling.pomailsummary.print.do');
   
    
}

function close(){
   submitFormWithUnsaveCheck(appPath + "/home.jsp");
}
function clear(){
    submitForm(targetFormName,'mailtracking.mra.gpabilling.pomailsummary.clear.do')
}
function showLov(frm){

 	var station = frm.elements.locationType.value;
	
	
	
	 if(station == "Country"){
		displayLOV('showCountry.do','N','Y','showCountry.do',frm.elements.location.value,'Origin','1','location','','0');
	}
	else if(station == "City"){
		displayLOV('showCity.do','N','Y','showCity.do',frm.elements.location.value,'Origin','1','location','','0');
	}
	}