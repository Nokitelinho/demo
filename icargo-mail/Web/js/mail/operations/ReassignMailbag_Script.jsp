<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.selectedRows)",EVT_CLICK);
   evtHandler.addEvents("selectedRows","toggleTableHeaderCheckbox('selectedRows',targetFormName.checkAll)",EVT_CLICK);
   evtHandler.addEvents("btnOk","doModify()",EVT_CLICK);
   evtHandler.addEvents("btnList","list()",EVT_CLICK);
   evtHandler.addEvents("btnClear","clear()",EVT_CLICK);  
   evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);    
  selectDiv();
   onScreenLoad(frm);
}


function onScreenLoad(frm){
  frm=targetFormName;
  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }
  if(frm.elements.initialFocus.value == "Y"){
        frm.elements.initialFocus.value = "";
        frm.elements.flightCarrierCode.focus();
  }
  if(frm.elements.reassignFocus.value == "Y"){
       frm.elements.reassignFocus.value = "";
        if(frm.elements.fromScreen.value == "INVENTORYLIST"){
     		frm.elements.flightCarrierCode.focus();
        }
        if(frm.elements.fromScreen.value == "MOVEMAILINV"){
     		frm.elements.carrierCode.focus();
        }
  }
  if(frm.elements.closeFlag.value == "Y"){
    frm.elements.closeFlag.value = "";
    
            var fltno=frm.elements.fromFlightNumber.value;
			var fltcarcod=frm.elements.fromFlightCarrierCode.value;			
			var assignedto=frm.elements.frmassignTo.value;					
			var frmfltdat = frm.elements.frmFlightDate.value;
			var frmdest = frm.elements.fromdestination.value;
			window.opener.targetFormName.action=appPath+="/mailtracking.defaults.mailexportlist.listflight.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&destination="+frmdest+"&tbaTbcWarningFlag=Y";
			window.opener.targetFormName.submit();      
			window.close();        
     
  }
  //else if(frm.closeFlag.value == "SHOWPOPUP"){
  //			frm.closeFlag.value="";
  // 			var fltno=frm.fromFlightNumber.value;
  //			var fltcarcod=frm.fromFlightCarrierCode.value;			
  //			var assignedto=frm.frmassignTo.value;					
  //			var frmfltdat = frm.frmFlightDate.value;  
  //   		var frmdest = frm.fromdestination.value;  	
  //   		var frmscreen = "REASSIGN_MAILBAG";  	
  // 			var strUrl = "mailtracking.defaults.emptyulds.screenload.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&fromScreen="+frmscreen+"&fromdestination"+frmdest;
  //	  		openPopUp(strUrl,"600","280");  			
  // }
  
}

function submitPage(lastPg,displayPg){
frm=targetFormName;
  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;
  recreateListDetails('mailtracking.defaults.reassignmailbag.listmailbag.do','divt1');
  //submitForm(frm,'mailtracking.defaults.reassignmailbag.listmailbag.do');
}


function list(){
	recreateListDetails('mailtracking.defaults.reassignmailbag.listflight.do','divt1');
	//submitForm(targetFormName, 'mailtracking.defaults.reassignmailbag.listflight.do');
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
	
	
	 
  	targetFormName.elements.lastPageNum.value = _innerFrm.lastPageNum.value;
  	targetFormName.elements.displayPage.value = _innerFrm.displayPage.value;
  	targetFormName.elements.reDSN.value = _innerFrm.reDSN.value;
  	targetFormName.elements.hideRadio.value = _innerFrm.hideRadio.value;
  	targetFormName.elements.assignToFlight.value = _innerFrm.assignToFlight.value; 
  	targetFormName.elements.selmailbags.value = _innerFrm.selmailbags.value;
  	targetFormName.elements.fromScreen.value = _innerFrm.fromScreen.value;
  	targetFormName.elements.initialFocus.value = _innerFrm.initialFocus.value;
  	targetFormName.elements.duplicateFlightStatus.value = _innerFrm.duplicateFlightStatus.value;
  	targetFormName.elements.closeFlag.value = _innerFrm.closeFlag.value;
  	targetFormName.elements.container.value = _innerFrm.container.value;
  	targetFormName.elements.carrierIdInv.value = _innerFrm.carrierIdInv.value;
  	targetFormName.elements.carrierCodeInv.value = _innerFrm.carrierCodeInv.value;
  	targetFormName.elements.selectMode.value = _innerFrm.selectMode.value;
  	targetFormName.elements.reassignFocus.value = _innerFrm.reassignFocus.value;  
  	targetFormName.elements.selectedRows.value = _innerFrm.selectedRows.value;  

	
    _divt1=_tableInfo.document.getElementById("_divt1");
	
 	document.getElementById(_tableDivId).innerHTML=_divt1.innerHTML;
	
	
 	selectDiv();
 	 onScreenLoad(targetFormName);	 
 	 
  }
  
  
  
  
 ////////////////////////////////////////////////////////////////////////////////////////
  

function clear(){

	recreateListDetails('mailtracking.defaults.reassignmailbag.clear.do','divt1');
	//submitForm(targetFormName, 'mailtracking.defaults.reassignmailbag.clear.do');
}
function closeScreen(){
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

function selectDiv() {
	
	var radiobuttons = document.getElementsByName("assignToFlight");
	for(var i=0; i<radiobuttons.length; i++){
				if(radiobuttons[i].checked == true)
				{
					//alert(radiobuttons[i].value); 
					togglePanel(1,radiobuttons[i]);
					break;
				}
		}
		
	}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {

		
		var divID = comboObj.value;		
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
		if(divID == "DESTN"){

			var divAlt = document.layers ? document.layers['FLIGHTDETAILS'] :
						 document.getElementById ?  document.getElementById('FLIGHTDETAILS').style :
						 document.all['FLIGHTDETAILS'].style;

			divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
				 (0 ? "visible" : "hidden");

			divAlt.display = document.layers ? (0 ? "" : "none") :
				 (0 ? "" : "none");
		}
		else {

			var divObj = document.layers ? document.layers['FLIGHTDETAILS'] :
						 document.getElementById ?  document.getElementById('FLIGHTDETAILS').style :
						 document.all['FLIGHTDETAILS'].style;

			divObj.visibility = document.layers ? (1 ? "show" : "hide") :
							 (1 ? "visible" : "hidden");

			divObj.display = document.layers ? (1 ? "" : "none") :
							 (1 ? "" : "none");

			divObj.zIndex = 120;

		}
		

	}

}

function doModify() {
	frm= targetFormName;
	var chkbox =document.getElementsByName("selectedRows");
	
	var selectMail="";
	 if(validateSelectedCheckBoxes(frm,'selectedRows','','1'))
	   {         
	            for(var i=0; i<chkbox.length;i++)
	       	    {
	     	  	if(chkbox[i].checked){     	  		
	     		  selectMail = selectMail+chkbox[i].value + "-";	     		  
	     		  }     		            	   
          	    }
	
	  	targetFormName.elements.selmailbags.value=selectMail;
	  	if(validateSelectedCheckBoxes(frm,'selectContainer','1','1')){
		window.opener.IC.util.common.childUnloadEventHandler();
	  	submitForm(targetFormName, 'mailtracking.defaults.reassignmailbag.save.do'); 
	  	}
	  }	
	
	  
}