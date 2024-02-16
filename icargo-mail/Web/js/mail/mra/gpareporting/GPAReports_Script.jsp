<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister() {
	
	var frm = targetFormName;
	with(frm) {
		evtHandler.addEvents("reportId","togglePanel(1,this)",EVT_CHANGE);
		evtHandler.addEvents("btnPrint","printfn(this.form)",EVT_CLICK);	
		evtHandler.addEvents("btnClose","closefn(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		//field validations
		evtHandler.addEvents("exceptionSummaryGPACode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("exceptionSummaryGPAName","validateFields(this,-1,'GPA Name',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("exceptionDetailsGPACode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("exceptionDetailsGPAName","validateFields(this,-1,'GPA Name',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeSummaryGPACode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeSummaryGPAName","validateFields(this,-1,'GPA Name',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeDetailsGPACode","validateFields(this,-1,'GPA Code',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeDetailsGPAName","validateFields(this,-1,'GPA Name',8,true,true)",EVT_BLUR);
		
		
		evtHandler.addEvents("exceptionSummaryCountryCode","validateFields(this,-1,'Country',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("exceptionDetailsCountryCode","validateFields(this,-1,'Country',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeSummaryCountryCode","validateFields(this,-1,'Country',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeDetailsCountryCode","validateFields(this,-1,'Country',0,true,true)",EVT_BLUR);
		
		evtHandler.addEvents("exceptionDetailsAssignee","validateFields(this,-1,'Assignee',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeSummaryAssignee","validateFields(this,-1,'Assignee',8,true,true)",EVT_BLUR);
		evtHandler.addEvents("assigneeDetailsAssignee","validateFields(this,-1,'Assignee',8,true,true)",EVT_BLUR);
		

		//LOVS
		//gpa lov
		evtHandler.addIDEvents("gpaCodeSummLov","showLov('exceptionSummaryGPACode','gpa', 'exceptionSummaryGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaNameSummLov","showLov('exceptionSummaryGPACode','gpa', 'exceptionSummaryGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaCodeDtlsLov","showLov('exceptionDetailsGPACode','gpa', 'exceptionDetailsGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaNameDtlsLov","showLov('exceptionDetailsGPACode','gpa', 'exceptionDetailsGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaCodeAsgnSummLov","showLov('assigneeSummaryGPACode','gpa', 'assigneeSummaryGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaNameAsgnSummLov","showLov('assigneeSummaryGPACode','gpa', 'assigneeSummaryGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaCodeAsgnDtlsLov","showLov('assigneeDetailsGPACode','gpa', 'assigneeDetailsGPAName')", EVT_CLICK);
		evtHandler.addIDEvents("gpaNameAsgnDtlsLov","showLov('assigneeDetailsGPACode','gpa', 'assigneeDetailsGPAName')", EVT_CLICK);
		
		//country lov
		evtHandler.addIDEvents("countrySummLov","showLov('exceptionSummaryCountryCode','country')",EVT_CLICK);
		evtHandler.addIDEvents("countryDtlsLov","showLov('exceptionDetailsCountryCode','country')",EVT_CLICK);
		evtHandler.addIDEvents("countryAsgnSummLov","showLov('assigneeSummaryCountryCode','country')",EVT_CLICK);
		evtHandler.addIDEvents("countryAsgnDtlsLov","showLov('assigneeDetailsCountryCode','country')",EVT_CLICK);
		
		//assignee lov
		evtHandler.addIDEvents("assigneeDtlsLov","showLov('exceptionDetailsAssignee','assignee')",EVT_CLICK);
		evtHandler.addIDEvents("assigneeAsgnSummLov","showLov('assigneeSummaryAssignee','assignee')",EVT_CLICK);
		evtHandler.addIDEvents("assigneeAsgnDtlsLov","showLov('assigneeDetailsAssignee','assignee')",EVT_CLICK);
		
		
		//For displaying the current div on screenload
		togglePanel(1,reportId);
		if(frm.elements.reportId.disabled==false)
			frm.elements.reportId.focus();
	}
}

/**
 * Method to display LOV
 */
function showLov(codeField, lovType){

	if(lovType == 'country'){
	
	//Country LOV
		var countryCode = document.getElementsByName(codeField);
		displayLOV('showCountry.do','N','Y','showCountry.do',countryCode.value,'Country','1',codeField,'',0);
	}else if(lovType == 'assignee'){
	
	//Assignee LOV
		var assignee = document.getElementsByName(codeField);
		displayLOV('showUserLOV.do','N','Y','showUserLOV.do',assignee.value,'Assignee','1',codeField,'',0);
	}else if(lovType == 'gpa'){
	
	//GPA LOV
		var gpaCodeObj = document.getElementsByName(codeField);
		var gpaName = '';
		if(arguments[2]!=null)
			gpaName = arguments[2];
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',gpaCodeObj.value,'GPA Code','1',codeField,gpaName,0)

	}
}

/**
 * Method to perform print action
 */
function printfn(frm){

	var printScreen = frm.elements.reportId.value;

	if(printScreen != null && printScreen != ""){
	//alert(printScreen);
		if(printScreen=='RPTMRA006'){
			generateReport(frm,'/mailtracking.mra.gpareporting.exceptionsummaryreport.do');
		}
		if(printScreen=='RPTMRA007'){
			generateReport(frm,'/mailtracking.mra.gpareporting.exceptiondetailsreport.do');
		}
		if(printScreen=='RPTMRA008'){
			generateReport(frm,'/mailtracking.mra.gpareporting.exceptionsassigneesummaryreport.do');
		}
		if(printScreen=='RPTMRA009'){
			generateReport(frm,'/mailtracking.mra.gpareporting.exceptionsassigneedetailsreport.do');
		}
	}
}

/**
 * Method to close screen
 */
function closefn(frm){
	submitForm(frm,'mailtracking.mra.gpareporting.closegpareport.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.reportId.disabled == false){
			targetFormName.elements.reportId.focus();
		}
	}
}

/**
 * Method to toggle Panel using combo box
 */
function togglePanel(iState,comboObj) 
{
 
	if(comboObj != null) {

	    var divID = comboObj.value;
	    var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;
		var divValues = [
				 'RPTMRA006',
				 'RPTMRA007',
				 'RPTMRA008',
				 'RPTMRA009'];

		var length = divValues.length;

		for(var index=0; index<length; index++) {
		
			if(divValues[index] == divID) {
			
				document.getElementById(divValues[index]).style.display = "block";
				
			
			}
			else {
				document.getElementById(divValues[index]).style.display = "none";

		        }

		}
  	}
}