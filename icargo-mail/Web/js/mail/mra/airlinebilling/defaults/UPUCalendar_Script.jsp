<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
var frm=targetFormName;
	with(frm){
		frm.fromDateLst.focus();
		evtHandler.addEvents("btnList","onList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
		evtHandler.addEvents("btnSave","onSave()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
	}
}

function onList() {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.onlistUPUCalendar.do');
}

function onClear(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.onclearUPUCalendar.do');
}

function onSave(){
	setOperationalFlag();
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.onsaveUPUCalendar.do');
}

function onClose(){
	submitFormWithUnsaveCheck('mailtracking.mra.airlinebilling.defaults.oncloseUPUCalendar.do');
}
//added by T-1927 for ICRD-18408
function resetFocus(){

	if(!event.shiftKey){
		if(targetFormName.fromDateLst.disabled == false && targetFormName.fromDateLst.readOnly== false){
			targetFormName.fromDateLst.focus();
		}
	}
}
function setOperationalFlag(){
	if(targetFormName.operationalFlag != null){
		if(targetFormName.operationalFlag.length > 1){
    			for(var i = 0; i<targetFormName.operationalFlag.length; i++){
    				if(targetFormName.operationalFlag[i].value != "I"){
    					// Default values
    					var fromDateDefValue = targetFormName.fromDate[i].defaultValue;
    					var toDateDefValue = targetFormName.toDate[i].defaultValue;
    					var submissionDateDefValue = targetFormName.submissionDate[i].defaultValue;
    					var generateAfterToDateDefValue = targetFormName.generateAfterToDate[i].defaultValue;
    					//current values
    					var fromDateValue = targetFormName.fromDate[i].value;
   						var toDateValue = targetFormName.toDate[i].value;
   						var submissionDateValue = targetFormName.submissionDate[i].value;
					    	var generateAfterToDateValue = targetFormName.generateAfterToDate[i].value;
    					if( (fromDateDefValue != fromDateValue) 		||
						    (toDateDefValue != toDateValue) 			||
						    (submissionDateDefValue != submissionDateValue) 	||
						    (generateAfterToDateDefValue != generateAfterToDateValue)) {
    							targetFormName.operationalFlag[i].value = "U";
    					}
    				}
    			}
    		} else {
				if(targetFormName.operationalFlag.value != "I"){
					// Default values
					var fromDateDefValue = targetFormName.fromDate.defaultValue;
  					var toDateDefValue = targetFormName.toDate.defaultValue;
  					var submissionDateDefValue = targetFormName.submissionDate.defaultValue;
   					var generateAfterToDateDefValue = targetFormName.generateAfterToDate.defaultValue;
					// current values
					var fromDateValue = targetFormName.fromDate.value;
   					var toDateValue = targetFormName.toDate.value;
   					var submissionDateValue = targetFormName.submissionDate.value;
   					var generateAfterToDateValue = targetFormName.generateAfterToDate.value;
					if(	(fromDateDefValue != fromDateValue) 			||
						(toDateDefValue != toDateValue) 			||
						(submissionDateDefValue != submissionDateValue) 	||
						(generateAfterToDateDefValue != generateAfterToDateValue)) {
							targetFormName.operationalFlag.value = "U";
					}
				}
    		}
    	}
}

 /* converts string of format 0X to X */
function getvalue(strvalue) {
	if(strvalue.substring(0,1)=="0"){
		return strvalue.substring(1,2);
	}else{
		return strvalue;
	}
}

function onAddLink(){
	addTemplateRow('targetWeightRow','targetWeightTableBody','operationalFlag');
}

function onDeleteLink(){
	var chkBoxIds = document.getElementsByName('rowCount');
	var isError = 1;
	for(var i = 0; i < chkBoxIds.length; i++){
		if(chkBoxIds[i].checked){
			isError = 0;
		}
	}
	if(isError == 1) {
		showDialog('<common:message bundle="UPUCalendarResources" key="mra.airlinebilling.defaults.upucalendar.msg.err.selectrow" scope="request"/>',1,self);
	}
	else{
		deleteTableRow('rowCount','operationalFlag');
	}
}



