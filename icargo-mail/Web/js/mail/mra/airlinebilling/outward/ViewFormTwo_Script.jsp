<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){


	with(targetFormName){
		//onScreenloadSetHeight();
		evtHandler.addEvents("btnList","onClickDetails()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		//evtHandler.addEvents("clearancePeriodLOV","clearanceLOV()",EVT_CLICK);
		evtHandler.addEvents("clearancePeriodLOV","displayLOV('showClearancePeriods.do','N','N','showClearancePeriods.do',document.forms[1].clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)",EVT_CLICK);
		evtHandler.addEvents("btnPrint","onPrint()",EVT_CLICK);

		if(targetFormName.clearancePeriod.readOnly==false || targetFormName.clearancePeriod.disabled==false){
			targetFormName.clearancePeriod.focus();
		}
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
	document.getElementById('div1').style.height = ((height*69)/100)+'px';
}

function onClickDetails(){
	submitForm(targetFormName,'mra.airlinebilling.outward.displayviewformtwo.do');
}

function onClickClose(){
	submitForm(targetFormName,'mra.airlinebilling.outward.closeviewformtwo.do');


}

function onClickClear(){
	submitForm(targetFormName,'mra.airlinebilling.outward.clearviewformtwo.do');

}

function onPrint(){
	generateReport(targetFormName,'/mra.airlinebilling.outward.printviewformtwo.do');


}

