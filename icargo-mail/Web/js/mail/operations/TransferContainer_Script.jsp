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
     	if(frm.reassignSubCheck != null){
			evtHandler.addEvents("reassignSubCheck","toggleTableHeaderCheckbox('reassignSubCheck',targetFormName.reassignCheckAll)",EVT_CLICK);
		}

		evtHandler.addIDEvents("flightPouLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.flightPou.value,'PointOfUnlading','0','flightPou','',0)", EVT_CLICK);
		
		evtHandler.addIDEvents("destinationLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Airport','0','destination','',1)", EVT_CLICK);

		evtHandler.addIDEvents("carrierLOV","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.carrier.value,'Airline','0','carrier','',0)", EVT_CLICK);
		//evtHandler.addIDEvents("destinationPouLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destnPou.value,'PointOfUnlading','0','destnPou','',0)", EVT_CLICK);

		//evtHandler.addIDEvents("poulov","pouLovDisplay(this)",EVT_CLICK);

		evtHandler.addIDEvents("addLink","addRoute()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteRoute()",EVT_CLICK);


     //BLUR Events

   	}
}

function onScreenLoad(frm){
	if(frm.elements.embargoFlag.value == "embargo_exists"){ //Added By A-8164 for ICRD-271652 
        frm.elements.embargoFlag.value = "";
		openPopUp("reco.defaults.showEmbargo.do", 900,400);
   }
	selectDiv();
	var mode = frm.elements.status.value;
	var fromscreen = frm.elements.fromScreen.value;
	if(fromscreen == "MAIL_ARRIVAL"){
		if("N"==frm.elements.similarCarrier.value){	
			frm.elements.similarCarrier.value="NONE";
			/*showDialog('<common:message bundle="transferContainerResources" key="mailtracking.defaults.transfercontainer.msg.warn" />', 4, self, frm, 'id_1');
			screenConfirmDialog(frm,'id_1');
			screenNonConfirmDialog(frm,'id_1');*/
			showDialog({msg:'<bean:message bundle="transferContainerResources" key="mailtracking.defaults.transfercontainer.msg.warn" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){					
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1');
						}
						});
		}else{
			if("Y"==frm.similarCarrier.value){
				frm.similarCarrier.value="NONE";
		    	frm.printTransferManifestFlag.value="N";
				submitForm(frm,'mailtracking.defaults.transfercontainer.oktransfercontainer.do');	
			}
		}
	}else{		
		frm.elements.printTransferManifestFlag.value="N";
	}
	if(fromscreen =="MAIL_ACCEPTANCE"){//added by A-7371 for ICRD-133987
		if("N"==frm.elements.similarCarrier.value){	
			frm.elements.similarCarrier.value="NONE";
			showDialog({msg:'<bean:message bundle="transferContainerResources" key="mailtracking.defaults.transfercontainer.msg.warn" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){					
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1');
						}
						});
		}else{
			if("Y"==frm.similarCarrier.value){
				frm.similarCarrier.value="NONE";
		    	frm.printTransferManifestFlag.value="N";
				submitForm(frm,'mailtracking.defaults.transfercontainer.oktransfercontainer.do');	
			}
		}
		}
		
	if(fromscreen =="MAIL_EXPORT_LIST"){//added by A-7371 for ICRD-133987
		if("N"==frm.elements.similarCarrier.value){	
			frm.elements.similarCarrier.value="NONE";
			showDialog({msg:'<bean:message bundle="transferContainerResources" key="mailtracking.defaults.transfercontainer.msg.warn" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){					
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1');
						}
						});
		}else{
			if("Y"==frm.similarCarrier.value){
				frm.similarCarrier.value="NONE";
		    	frm.printTransferManifestFlag.value="N";
				submitForm(frm,'mailtracking.defaults.transfercontainer.oktransfercontainer.do');	
			}
		}
		}
		
	if(mode == "showDuplicateFlights"){
		openPopUp("flight.operation.validateFlights.do","600","280");
		//frm.status.value = "";
	}
	else if(mode == "closeWindow"){
		if(fromscreen == "SEARCHCONTAINER"){
			window.opener.targetFormName.action=appPath+"/mailtracking.defaults.searchcontainer.listsearchcontainer.do";
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "MAIL_ARRIVAL"){
			frm.elements.fromScreen.value = "NONE";
			frm = self.opener.document.forms[1];
			window.opener.targetFormName.action=appPath+"/mailtracking.defaults.mailarrival.listmailarrival.do";
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "MAIL_ACCEPTANCE"){//added by A-7371 for ICRD-133987
			frm.elements.fromScreen.value = "NONE";
			frm = self.opener.document.forms[1];
			window.opener.targetFormName.action=appPath+"/mailtracking.defaults.mailacceptance.listflight.do";
			window.opener.targetFormName.submit();
		}
		if(fromscreen == "MAIL_EXPORT_LIST"){
			frm.elements.fromScreen.value = "NONE";
			frm = self.opener.document.forms[1];
			window.opener.targetFormName.action=appPath+"/mailtracking.defaults.mailexportlist.listflight.do";
			window.opener.targetFormName.submit();
		}
		window.opener.IC.util.common.childUnloadEventHandler();
		window.close();
	}
	else if(mode == "SEARCHCONTAINER" && frm.elements.isReportNeeded.value!='Y'){
		frm.elements.printTransferManifestFlag.value="N";
		submitForm(frm,'mailtracking.defaults.transfercontainer.oktransfercontainer.do');
		frm.elements.status.value = "";
	}
	else if(mode == "close"){
	 	
		window.close();
		
	}
	
}

function addRoute(){

	submitForm(targetFormName,'mailtracking.defaults.transfercontainer.addonwardroute.do');
}

function deleteRoute(){

	var chk = document.getElementsByName("reassignSubCheck");
	if(isRowSelected(chk)){
		submitForm(targetFormName,'mailtracking.defaults.transfercontainer.deleteonwardroute.do');
	}
}

function reassign(){
	targetFormName.elements.isScreenLoad.value="N"
	submitForm(targetFormName,'mailtracking.defaults.transfercontainer.screenload.do');
}

function saveDetails(frm){

	submitForm(targetFormName,'mailtracking.defaults.transfercontainer.validatecontainer.do');
}

function closeDetails(frm){

	window.close();
}

function pouLovDisplay(fld){

	displayLOV('showAirport.do','N','N','showAirport.do',fld.value,'pointOfUnlading','0','pointOfUnlading','',fld.getAttribute("index"));
}

function disableOnwardRouting(){

	if(isDestinationSelected()){

		targetFormName.elements.reassignCheckAll.disabled = true;
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
	//showDialog("<common:message bundle='transferContainerResources' key='mailtracking.defaults.reassigncontainer.msg.info.norowsselected' />", 1, self, frm, 'id_1');
	showDialog({msg:'<common:message bundle="transferContainerResources" key="mailtracking.defaults.reassigncontainer.msg.info.norowsselected" scope="request"/>',type:1,parentWindow:self,dialogId:'id_1'});			
  }
  return flag;
}

function selectDiv() {
	var radiobuttons = document.getElementsByName("reassignedto");
	
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			togglePanel(1,radiobuttons[i]);
			break;
		}
	}
	disableOnwardRouting();
	
}





function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {

		var divID = comboObj.value;
		
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
/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'Y') {
	
	    if(dialogId == 'id_1'){	
	    	frm.elements.printTransferManifestFlag.value="Y";
			frm.elements.isReportNeeded.value="Y";
			frm.elements.status.value="";
			generateReport(frm,'/mailtracking.defaults.transfercontainer.oktransfercontainer.do',true);
		}
	}
}

function reloadPage(arg) {
frm=targetFormName;
frm.elements.isReportNeeded.value="";
//submitForm(frm,"mailtracking.defaults.transfercontainer.screenload.do?status=closeWindow");Commenetd by A-7540 for ICRD-322301
}
/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){	
	    	frm.elements.printTransferManifestFlag.value="N";	
				submitForm(frm,'mailtracking.defaults.transfercontainer.oktransfercontainer.do');	
		}
	}
}