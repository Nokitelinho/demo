<%@ include file="/jsp/includes/js_contenttype.jsp" %>
function screenSpecificEventRegister()
{
    var frm=targetFormName;
   
	with(frm){
		frm.fromDate.focus();
	   evtHandler.addEvents("btnGenerate","selectAction(targetFormName)",EVT_CLICK);
	   evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	   evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	}
}


function selectAction(frm) {
	shouldDisablePage = false;
	frm.action = 'mailtracking.mra.defaults.mailexceptionreports.generatereport.do';
	frm.submit();
}

function closeScreen(){
	//window.close();	
	location.href = appPath + "/home.jsp";
}

//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.elements.fromDate.disabled == false&&targetFormName.elements.fromDate.readOnly == false){
			targetFormName.elements.fromDate.focus();
		}
	}
}