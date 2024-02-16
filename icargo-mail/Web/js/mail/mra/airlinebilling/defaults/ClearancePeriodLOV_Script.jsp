<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
	var frm=document.forms[0];

	with(frm){

		evtHandler.addEvents("btnList","onList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'mailtracking.mra.airlinebilling.defaults.onclearClearancePeriodLOV.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);

	}
}

function onList() {

        frm = targetFormName;

    	if(targetFormName.elements.fromDateLst.value!="" && targetFormName.elements.toDateLst.value != ""){

    		if(!compDatesCheckFocus(targetFormName, targetFormName.elements.fromDateLst, targetFormName.elements.toDateLst)){
			return;
	    	}
    	}

    	submitForm(frm,'mailtracking.mra.airlinebilling.defaults.onlistClearancePeriodLOV.do');

    }


 function onClickSingleSelect(checkVal){

  	singleSelect(checkVal);
  	var codeNameValue = checkVal.split("*");
  	targetFormName.elements.strClearancePeriod.value = codeNameValue[0];
  	targetFormName.elements.strFromDate.value 	= codeNameValue[1];
  	targetFormName.elements.strToDate.value 		= codeNameValue[2];
  }

