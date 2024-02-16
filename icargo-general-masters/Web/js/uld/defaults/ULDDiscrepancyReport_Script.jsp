<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
	    evtHandler.addEvents("btnGenerateReport","onGenerateReport(this.form);",EVT_CLICK);
		evtHandler.addEvents("btnPrint","onPrint(this.form);",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear(this.form);",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose();",EVT_CLICK);	
		evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);	
	} 
	
	if((!targetFormName.elements.fromDate.disabled)&&(!targetFormName.elements.fromDate.readOnly)){
		targetFormName.elements.fromDate.focus();
	}
	}
function resetFocus(frm){
    if(!event.shiftKey){	   
		if((!frm.elements.fromDate.disabled)
		      && (!frm.elements.fromDate.readOnly)){
		  frm.elements.fromDate.focus();
		}	 
	}	
}		
function onGenerateReport(frm){ 
  generateReport(frm,"/uld.defaults.ulddiscrepancyreport.generateulddiscrepancyreport.do");
 
}

function onPrint(frm){
 generateReport(frm,"/uld.defaults.ulddiscrepancyreport.printulddiscrepancyreport.do");
 
}

function onClear(frm){
//submitForm(frm,'uld.defaults.ulddiscrepancyreport.clearulddiscrepancyreport.do');
submitFormWithUnsaveCheck('uld.defaults.ulddiscrepancyreport.clearulddiscrepancyreport.do');
}

function onClose(){
location.href = appPath+"/home.jsp";

}