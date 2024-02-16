<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   selectDiv();
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnList","listReassignDSN()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearReassignDSN()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeReassignDSN()",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveReassignDSN()",EVT_CLICK);
    evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.selectedRows)",EVT_CLICK);
    evtHandler.addEvents("selectedRows","toggleTableHeaderCheckbox('selectedRows',targetFormName.checkAll)",EVT_CLICK);

     //BLUR Events

   	}
}

function onScreenLoad(frm){
 	 frm=targetFormName;
 	 var frmscreen=frm.elements.fromScreen.value;
 	 if(frm.elements.duplicateFlightStatus.value == "Y"){
 	    openPopUp("flight.operation.duplicateflight.do","600","280");
 	 }
	if(frm.elements.initialFocus.value == "Y"){
	    frm.elements.initialFocus.value = "";
    	frm.elements.flightCarrierCode.focus();
	}
  
	
	if(frm.elements.closeFlag.value == "Y"){
		frm.elements.closeFlag.value="";
        var fltno=frm.elements.fromFlightNumber.value;
		var fltcarcod=frm.elements.fromFlightCarrierCode.value;			
		var assignedto=frm.elements.frmassignTo.value;					
		var frmfltdat = frm.elements.frmFlightDate.value;
		var frmdest = frm.elements.fromdestination.value;
		window.opener.targetFormName.action=appPath+="/mailtracking.defaults.mailexportlist.listflight.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&destination="+frmdest;
		window.opener.targetFormName.submit();
		window.close();  
	}
}

/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function clearReassignDSN(){
   frm=targetFormName;
   //submitAction(frm,'/mailtracking.defaults.reassigndsn.clearreassigndsn.do');
   recreateListDetails('mailtracking.defaults.reassigndsn.clearreassigndsn.do','divt1');
}

function listReassignDSN(){
   frm=targetFormName;
   //submitAction(frm,'/mailtracking.defaults.reassigndsn.listflight.do');
   recreateListDetails('mailtracking.defaults.reassigndsn.listflight.do','divt1');
}

function closeReassignDSN(){
    frm=targetFormName;
	var fltno=frm.elements.fromFlightNumber.value;
	var fltcarcod=frm.elements.fromFlightCarrierCode.value;			
	var assignedto=frm.elements.frmassignTo.value;					
	var frmfltdat = frm.elements.frmFlightDate.value;
	var frmdest = frm.elements.fromdestination.value;
	window.opener.targetFormName.action=appPath+="/mailtracking.defaults.mailexportlist.listflight.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&destination="+frmdest;
	window.opener.targetFormName.submit();      
	window.close(); 
}

function saveReassignDSN(){
   frm=targetFormName;
   frm.elements.fromScreen.value="MAILEXPORTLIST";
	var chkbox =document.getElementsByName("selectedRows");
	var selectMail="";
	 if(validateSelectedCheckBoxes(frm,'selectedRows','','1')){         
        for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked){     	  		
     		  selectMail = selectMail+chkbox[i].value + "-";
     		}     		            	   
  	    }	
  	    targetFormName.elements.selectedConsignment.value=selectMail;
	    if(validateSelectedCheckBoxes(frm,'selectContainer','1','1')){
	        submitAction(frm,'/mailtracking.defaults.reassigndsn.okreassigndsn.do');
	    }
	  }	
}


function selectDiv() {
	var radiobuttons = document.getElementsByName("assignToFlight");
	
	
	if(targetFormName.elements.hideRadio.value == "FLIGHT"){
		     togglePanel(1,'DESTINATION');
	}else if(targetFormName.elements.hideRadio.value == "CARRIER"){
	     togglePanel(1,'FLIGHT');
	}else{
	
		for(var i=0; i<radiobuttons.length; i++){
			if(radiobuttons[i].checked == true)
			{
				togglePanel(1,radiobuttons[i]);
				break;
			}
		}
	
	}

}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {

		if(targetFormName.elements.hideRadio.value == "FLIGHT"){
		    var divID = "DESTINATION";
		}else if(targetFormName.elements.hideRadio.value == "CARRIER"){
		     var divID = "FLIGHT";
		}else{
		    var divID = comboObj.value;
		}

		var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

		var divValues = ['FLIGHT','DESTN'];
		var length = divValues.length;

		for(var index=0; index<length; index++) {

			if(divValues[index] == divID) {

				var divObj = document.layers ? document.layers[divID] :
							 document.getElementById ?  document.getElementById(divID).style :
							 document.all[divID].style;

				divObj.visibility = document.layers ? (1 ? "show" : "hide") :
								 (1 ? "visible" : "hidden");

				divObj.display = document.layers ? (1 ? "" : "none") :
								 (1 ? "" : "none");

				divObj.zIndex = 120;

			}
			else {

				var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
							 document.all[divValues[index]].style;

				divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
					 (0 ? "visible" : "hidden");

				divAlt.display = document.layers ? (0 ? "" : "none") :
						 (0 ? "" : "none");

			}

		}
		
	}

}

function singleSelectCb(frm,checkVal,type)
{
	for(var i=0;i<frm.elements.length;i++)
	{
		if((frm.elements[i].type =='checkbox' && frm.elements[i].name == type))
		{
			if(frm.elements[i].checked == true)
			{
				if(frm.elements[i].value != checkVal )
				{
					frm.elements[i].checked = false;
				}
			}
		}
	}
}
////////////////// FOR ASYNC SUBMIT - AJAX - LIST///////////////////////////////////////////////
//--------------------------- Global---------------------------------------------
  var _tableDivId = ""; 
//-------------------------------------------------------------------------------  
  
  function recreateListDetails(strAction,divId1){
  	var __extraFn="updateListCode";
  	_tableDivId = divId1;
  	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
  }
  
  
   function updateListCode(_tableInfo){
  	
  	 _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
  	targetFormName.elements.fromScreen.value = _innerFrm.fromScreen.value;
  	targetFormName.elements.initialFocus.value = _innerFrm.initialFocus.value;
  	targetFormName.elements.duplicateFlightStatus.value = _innerFrm.duplicateFlightStatus.value;
  	targetFormName.elements.closeFlag.value = _innerFrm.closeFlag.value;
  	targetFormName.elements.container.value = _innerFrm.container.value;
  	targetFormName.elements.carrierIdInv.value = _innerFrm.carrierIdInv.value; 
  	targetFormName.elements.carrierCodeInv.value = _innerFrm.carrierCodeInv.value;
  	targetFormName.elements.hideRadio.value = _innerFrm.hideRadio.value;
  	targetFormName.elements.selectMode.value = _innerFrm.selectMode.value;
  	targetFormName.elements.reassignFocus.value = _innerFrm.reassignFocus.value;
  	targetFormName.elements.screenStatus.value = _innerFrm.screenStatus.value;
  	targetFormName.elements.selectedConsignment.value = _innerFrm.selectedConsignment.value;
  	targetFormName.elements.selectedRows.value = _innerFrm.selectedRows.value;
  		
    _divt1=_tableInfo.document.getElementById("_divt1");
 	document.getElementById(_tableDivId).innerHTML=_divt1.innerHTML;
 	selectDiv();
 	 onScreenLoad(targetFormName);	 
 	 
  }
  
  