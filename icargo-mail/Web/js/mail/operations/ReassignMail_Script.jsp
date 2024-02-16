<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   selectDiv();
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
	evtHandler.addEvents("btnList","listReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeReassignMail()",EVT_CLICK);
	evtHandler.addEvents("btnOk","saveReassignMail()",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addContainer()",EVT_CLICK);

     //BLUR Events

   	}
}

function onScreenLoad(frm){
  frm=targetFormName;
 
  if(frm.elements.closeFlag.value=="ADD_UPDATE"){
  frm.elements.closeFlag.value="";
  listReassignMail();
  }
  if(frm.elements.existingMailbagFlag.value == "Y"){
  		frm.elements.existingMailbagFlag.value = "";
  		var fromScreen="ReassignMail";
      openPopUp('mailtracking.defaults.mailacceptance.existingmailbag.screenload.do?fromScreen='+fromScreen,650,330);
    }
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
    if(frm.elements.fromScreen.value == "MAILBAG_ENQUIRY"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
        frm.elements.category.disabled = false;
	frm.elements.currentStatus.disabled = false;
	frm.elements.damaged.disabled = false;
	frm.elements.displayPage.value=1;
	frm.elements.lastPageNum.value=0;
	frm.action="mailtracking.defaults.mailbagenquiry.list.do";
	frm.method="post";
	window.opener.IC.util.common.childUnloadEventHandler();
	frm.submit();
	window.close();
	return;
    }
    if(frm.elements.fromScreen.value == "INVENTORYLIST" ||
	    frm.elements.fromScreen.value == "MOVEMAILINV" ){
		frm.elements.fromScreen.value = "";
		frm = self.opener.targetFormName;
		//frm.action="mailtracking.defaults.inventorylist.list.do";
		//frm.method="post";
		//frm.submit();
		var strAction="mailtracking.defaults.inventorylist.list.do";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.recreateListDetails(strAction,'inventoryListTable','inventoryButtons','carrierText');
		window.close();
		return;
    }
    if(frm.elements.fromScreen.value == "DSN_ENQUIRY"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
    	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do";
    	frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
    	frm.submit();
    //	window.opener.recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
    	window.close();
    	return;
    }
    if(frm.elements.fromScreen.value == "CONSIGNMENT"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
    	frm.action="mailtracking.defaults.mailsearch.listload.do";
    	frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
    	frm.submit();
    	window.close();
    	return;
     }
  }
  
  else if(frm.elements.closeFlag.value == "SHOWPOPUP"){
    	  	frm.elements.closeFlag.value = "";
    	var frmscreen = "EMPTY_ULD";
    		if(frm.elements.fromScreen.value == "MAILBAG_ENQUIRY"){
    			
		        frm.elements.fromScreen.value = "";
		        frm = self.opener.targetFormName;
		        frm.elements.category.disabled = false;
				frm.elements.currentStatus.disabled = false;
				frm.elements.damaged.disabled = false;
				frm.elements.displayPage.value=1;
				frm.elements.lastPageNum.value=0;
				frm.action="mailtracking.defaults.mailbagenquiry.list.do?status="+frmscreen;
				frm.method="post";
				window.opener.IC.util.common.childUnloadEventHandler();
				frm.submit();
				window.close();
				return;
    		}
    		if(frm.elements.fromScreen.value == "DSN_ENQUIRY"){
		        frm.elements.fromScreen.value = "";
		        frm = self.opener.targetFormName;
		    	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do?status="+frmscreen;
		    	frm.method="post";
				window.opener.IC.util.common.childUnloadEventHandler();
		    	frm.submit();
		    //	window.opener.recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
		    	window.close();
		    	return;
    		}
  		//var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen="+frmscreen;
  		//submitForm(frm,strUrl);
  		//openPopUp(strUrl,"600","280");
  		//window.close();
  }

  
    if(frm.elements.preassignFlag.value == "N")
  {
  if(!frm.elements.btnOk.disabled){
  document.getElementById('addLink').disabled=false;
      //enableLink(document.getElementById("addLink"));
  }else{
      disableLink(document.getElementById("addLink"));
  }
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

function clearReassignMail(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.reassignmail.clearreassignmail.do');
}

function listReassignMail(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.reassignmail.listflight.do');
}

function closeReassignMail(){
   frm=targetFormName;
   if(frm.elements.fromScreen.value == "MAILBAG_ENQUIRY"){
        frm.elements.fromScreen.value = "";
        frm = self.opener.targetFormName;
        frm.elements.category.disabled = false;
	frm.elements.currentStatus.disabled = false;
	frm.elements.damaged.disabled = false;
	frm.elements.displayPage.value=1;
	frm.elements.lastPageNum.value=0;
	frm.action="mailtracking.defaults.mailbagenquiry.list.do";
	frm.method="post";
	window.opener.IC.util.common.childUnloadEventHandler();
	frm.submit();
   window.close();
	return;
    }else{
	window.opener.IC.util.common.childUnloadEventHandler();
   window.close();
   }
}

function saveReassignMail(){
   frm=targetFormName;
   if(validateSelectedCheckBoxes(frm,'selectContainer','1','1')){
       submitAction(frm,'/mailtracking.defaults.reassignmail.okreassignmail.do');
   }
}


function selectDiv() {
	var radiobuttons = document.getElementsByName("assignToFlight");
	
	
	if(targetFormName.elements.hideRadio.value == "FLIGHT"){
		     togglePanel(1,'DESTINATION');
	}else if(targetFormName.hideRadio.value == "CARRIER"){
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

		var divValues = ['FLIGHT','DESTINATION'];
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
		if(divID == "DESTINATION"){

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

function addContainer(){
frm=targetFormName;
	var stat = "CREATE";
	var fromScreen = "REASSIGN_MAIL";
	var flightCarrierCode = frm.elements.flightCarrierCode.value;
	var flightNumber = frm.elements.flightNumber.value;
	var flightDate = frm.elements.depDate.value;
	var carrierCode = frm.elements.carrierCode.value;
	var destination = frm.elements.destination.value;
	frm.elements.closeFlag.value="ADD_UPDATE";
	var selectedRadio = "";
	if(frm.elements.hideRadio.value == "NONE"){
	   var radiobuttons = document.getElementsByName("assignToFlight");
	   for(var i=0; i<radiobuttons.length; i++){
 	      if(radiobuttons[i].checked == true)
	      {
	         selectedRadio = radiobuttons[i].value;
	      }
	   }
	}else{
	    selectedRadio = frm.assignToFlight.value;
	}
	var str = "mailtracking.defaults.assigncontainer.screenloadAddUpdatePopup.do?fromScreen="+fromScreen+"&status="+stat+"&flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&flightDate="+flightDate+"&carrier="+carrierCode+"&destn="+destination+"&assignedto="+selectedRadio;
	if(!frm.elements.btnOk.disabled){
	   openPopUp(str,"600","435");
	}
	
}