<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);

     	evtHandler.addEvents("reassignCheckAll","updateHeaderCheckBox(this.form,targetFormName.reassignCheckAll,targetFormName.reassignSubCheck)",EVT_CLICK);
     	if(frm.elements.reassignSubCheck != null){
			evtHandler.addEvents("reassignSubCheck","toggleTableHeaderCheckbox('reassignSubCheck',targetFormName.reassignCheckAll)",EVT_CLICK);
		}

		evtHandler.addIDEvents("flightPouLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.flightPou.value,'PointOfUnlading','0','flightPou','',0)", EVT_CLICK);
		evtHandler.addIDEvents("destinationLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','0','destination','',0)", EVT_CLICK);

		evtHandler.addIDEvents("carrierLOV","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrier.value,'Airline','0','carrier','',0)", EVT_CLICK);
		//evtHandler.addIDEvents("destinationPouLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destnPou.value,'PointOfUnlading','0','destnPou','',0)", EVT_CLICK);

		//evtHandler.addIDEvents("poulov","pouLovDisplay(this)",EVT_CLICK);

		evtHandler.addEvents("addLink","addRoute()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteRoute()",EVT_CLICK);


     //BLUR Events

   	}
}

function onScreenLoad(frm){

	selectDiv();
	var mode = frm.elements.status.value;
	var fromscreen = frm.elements.fromScreen.value;

	if(mode == "showDuplicateFlights"){
		openPopUp("flight.operation.validateFlights.do","600","280");
		frm.elements.status.value = "";
	}
	else if(mode == "closeWindow"){

		if(fromscreen == "ASSIGNCONTAINER"){
			var radiobuttons = window.opener.document.getElementsByName("assignedto");
			for(var i=0; i<radiobuttons.length; i++){
				radiobuttons[i].disabled = false;
			}
			window.opener.targetFormName.elements.action=appPath+"/mailtracking.defaults.assigncontainer.listAssignContainer.do?status=REASSIGN";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
			//window.opener.recreateMultiTableDetails("mailtracking.defaults.assigncontainer.listAssignContainer.do","div1","ajaxUpdateForList");
		}
		else if(fromscreen == "SEARCHCONTAINER"){
			window.opener.targetFormName.action=appPath+"/mailtracking.defaults.searchcontainer.listsearchcontainer.do";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "MAILACCEPTANCE"){
			var assignedto=frm.elements.assignedto.value;
			//var tbaTbcWarningFlag = frm.tbaTbcWarningFlag.value;
			window.opener.targetFormName.elements.action=appPath+"/mailtracking.defaults.mailacceptance.listmailacceptance.do?assignToFlight="+assignedto+"&tbaTbcWarningFlag=Y";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "INVENTORYLIST"){
			window.opener.targetFormName.elements.action=appPath+"/mailtracking.defaults.inventorylist.list.do";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "MAILEXPORTLIST"){

			var fltno=frm.elements.fromFlightNumber.value;
			var fltcarcod=frm.elements.fromFlightCarrierCode.value;
			var assignedto=frm.elements.assignedto.value;
			var frmfltdat = frm.elements.frmFlightDate.value;
			var frmdest = frm.elements.fromdestination.value;
			window.opener.targetFormName.elements.action=appPath+="/mailtracking.defaults.mailexportlist.listflight.do?assignToFlight="+assignedto+"&flightNumber="+fltno+"&flightCarrierCode="+fltcarcod+'&carrierCode='+fltcarcod+"&depDate="+frmfltdat+"&destination="+frmdest+"&tbaTbcWarningFlag=Y";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();

		}
		window.close();
	}
	else if(mode == "close"){
		window.close();
	}


}

function addRoute(){

	submitForm(targetFormName,'mailtracking.defaults.reassigncontainer.addOnwardRouting.do');
}

function deleteRoute(){

	var chk = document.getElementsByName("reassignSubCheck");
	if(isRowSelected(chk)){
		submitForm(targetFormName,'mailtracking.defaults.reassigncontainer.deleteOnwardRouting.do');
	}
}

function reassign(){

	submitForm(targetFormName,'mailtracking.defaults.reassigncontainer.screenload.do');
}

function saveDetails(frm){

	if((frm.elements.flightNumber.value !="" && frm.elements.flightCarrierCode.value !="" && frm.elements.flightDate.value !=""
	&& frm.elements.flightPou.value!="") || (frm.elements.carrier.value !="" && frm.elements.destination.value!=""))
	submitForm(targetFormName,'mailtracking.defaults.reassigncontainer.validateContainer.do');
	else
		showDialog({msg:'Please input all mandatory fields',type:1,parentWindow:self,});
}

function closeDetails(frm){

	submitForm(targetFormName,'mailtracking.defaults.reassigncontainer.close.do');
}

function pouLovDisplay(fld){

	displayLOV('showAirport.do','N','N','showAirport.do',fld.value,'pointOfUnlading','0','pointOfUnlading','',fld.getAttribute("index"));
}

function disableOnwardRouting(){

	if(isDestinationSelected()){

		targetFormName.elements.reassignCheckAll.disabled = true;
		//Added by A-7540 as a part of ICRD-134048
		targetFormName.elements.destination.disabled=false;
	}
}

function isDestinationSelected(){
	var isdestn = true;
	var radiobuttons = document.getElementsByName("reassignedto");
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			if(radiobuttons[i].value == "FLIGHT"){
				isdestn = false;
			}
			break;
		}
	}
	return isdestn;
}


function isRowSelected(chk)
{
  var flag = false;
	for(var i=0; i<chk.length; i++)
	{
		if(chk[i].checked == true)
		{
			flag = true;
			break;
		}
	}
  if(flag == false){
	var frm = targetFormName;
	//showDialog("<common:message bundle='reassignContainerResources' key='mailtracking.defaults.reassigncontainer.msg.info.norowsselected' />", 1, self);
	showDialog({msg:'<bean:message bundle="reassignContainerResources" key="mailtracking.defaults.reassigncontainer.msg.info.norowsselected" /> ',
					type:1,
					parentWindow:self,

			});
  }
  return flag;
}

function selectDiv() {
	var radiobuttons = document.getElementsByName("reassignedto");
	var mode = targetFormName.elements.status.value;
	if(targetFormName.elements.hideRadio.value == "FLIGHT"){
				if(document.getElementById("addLink"))
					disableLink(document.getElementById("addLink"));
				if(document.getElementById("deleteLink"))
					disableLink(document.getElementById("deleteLink"));
			     togglePanel(1,'DESTINATION');
		}else if(targetFormName.elements.hideRadio.value == "CARRIER"){
				enableLink(document.getElementById("addLink"));
				enableLink(document.getElementById("deleteLink"));
			     togglePanel(1,'FLIGHT');
	}else{
			for(var i=0; i<radiobuttons.length; i++){
			if(radiobuttons[i].checked == true)
			{
				if(mode != "closeWindow"){
				if(radiobuttons[i].value=="FLIGHT"){
				enableLink(document.getElementById("addLink"));
				enableLink(document.getElementById("deleteLink"));
				}
				if(radiobuttons[i].value=="DESTINATION"){
					if(document.getElementById("addLink"))
						disableLink(document.getElementById("addLink"));
					if(document.getElementById("deleteLink"))
						disableLink(document.getElementById("deleteLink"));
	 			}
				}
				togglePanel(1,radiobuttons[i]);
				break;
			}
		}
		disableOnwardRouting();
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

	}

}