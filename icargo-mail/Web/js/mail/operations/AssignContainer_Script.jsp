<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   //onScreenloadSetHeight();
   onScreenLoad(frm);
   initializePanels();
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btAcceptMail","acceptMail(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btViewMailBags","showMailBags(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btReassign","reassignContainer(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btDeassign","deassignContainer(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
     	evtHandler.addEvents("btPreAdvice","showPreAdvice(this.form)",EVT_CLICK);
        evtHandler.addEvents("btPrintULDTag","showPrintULDTag()",EVT_CLICK);
     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.elements.checkAll,targetFormName.elements.subCheck)",EVT_CLICK);

     	if(frm.elements.subCheck != null){
			evtHandler.addEvents("subCheck","toggleTableHeaderCheckbox('subCheck',targetFormName.elements.checkAll)",EVT_CLICK);
		}

		evtHandler.addIDEvents("carrierLOV","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.carrier.value,'Airline','1','carrier','',0)", EVT_CLICK);
		evtHandler.addIDEvents("destinationLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destn.value,'Airport','1','destn','',0)", EVT_CLICK);

		evtHandler.addIDEvents("createLink","createContainer()",EVT_CLICK);
		evtHandler.addIDEvents("modifyLink","modifyContainer()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteContainer()",EVT_CLICK);

     //BLUR Events

   	}
}

function preWarningMessages(){

   selectDiv();
}

function resetFocus(){
	 if(!event.shiftKey){
		  var radiobuttons = document.getElementsByName("assignedto");
		  var focusStatus = "No";
			  for(var i=0; i<radiobuttons.length; i++){
			 if(radiobuttons[i].checked == true && !targetFormName.elements.assignedto[i].disabled){
						targetFormName.elements.assignedto[i].focus();
						focusStatus = "yes";
			  }
				/*if(radiobuttons[i].checked == true)
				{
						if((radiobuttons[i].value=="FLIGHT") && (!targetFormName.flightCarrierCode.disabled)
							&& (!targetFormName.flightCarrierCode.readOnly)){
							targetFormName.flightCarrierCode.focus();
							focusStatus = "yes";
						}
						else if((radiobuttons[i].value=="DESTINATION") && (!targetFormName.carrier.disabled)
							&& (!targetFormName.carrier.readOnly)){
							targetFormName.carrier.focus();
							focusStatus = "yes";
						}
				}*/
			}
			if((focusStatus == "No") && (!document.getElementById("createLink").disabled)){
				document.getElementById("createLink").focus();
			}
	}
}

function onScreenLoad(frm){


	var radiobuttons = document.getElementsByName("assignedto");
	var selectedvalue = "";
	var destination = "";
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			selectedvalue = radiobuttons[i].value;
			break;
		}
	}

	var statusFlag = frm.elements.screenStatusFlag.value;
	if(statusFlag == "screenload"){

		disableDetails();
	}
	else if(statusFlag == "detail"){

		disableSearchDetails();
		if(!document.getElementById("createLink").disabled){
			document.getElementById("createLink").focus();
		}
	}

	var mode = frm.elements.status.value;
	if(mode == "showDuplicateFlights"){
		openPopUp("flight.operation.validateFlights.do","600","280");
		frm.elements.status.value = "";
	}
	else if(mode == "showAddPopup"){

		var stat = frm.elements.flightStatus.value;
		var destn = frm.elements.destn.value

		var str = "mailtracking.defaults.assigncontainer.screenloadAddUpdatePopup.do?assignedto="+selectedvalue+"&status="+stat+"&destination="+destn;
		openPopUp(str,"600","435");
		frm.elements.status.value = "";
	}
	else if(mode == "showReassignPopup"){
		var str = "mailtracking.defaults.reassigncontainer.screenload.do?reassignedto=FLIGHT&fromScreen=ASSIGNCONTAINER&assignedto="+selectedvalue;
		openPopUp(str,"600","417");
		frm.elements.status.value = "";
	}
	else if(mode == "showAcceptMail"){

		if(frm.elements.warningFlag.value == "Y"){
         //showDialog("Unsaved data exists.Do you wish to continue?", 4, self, frm, 'id_1');
		 showDialog({msg:'<bean:message bundle="assignContainerResources" key="mailtracking.defaults.assigncontainer.msg.warn.unsaved" />', type:4, parentWindow:self, parentForm:frm,
		 dialogId:'id_1', onClose:function(){
 screenConfirmDialog(frm,'id_2');
         screenNonConfirmDialog(frm,'id_2')
   }
  });

   		}else{
   			gotoacceptMail();
   		}
	}
	else if(mode == "viewmail"){
		var str="";

		if(selectedvalue == "DESTINATION"){
			destination = frm.elements.destn.value;
			var depPort = frm.elements.departurePort.value;
			var str = "mailtracking.defaults.mailbagenquiry.listfromassigncontainer.do?fromScreen=ASSIGNCONTAINER&assignToFlight="+selectedvalue+"&port="+depPort+"&destination="+destination;
		}else {
			var str = "mailtracking.defaults.mailbagenquiry.listfromassigncontainer.do?fromScreen=ASSIGNCONTAINER&assignToFlight="+selectedvalue+"&port="+depPort;
		}

		frm.elements.status.value = "";
		submitForm(targetFormName,str);
	}
	else if(mode == "showPreAdvice"){
		openPopUp("mailtracking.defaults.preadvice.screenload.do","640","320");
		frm.elements.status.value = "";
	}

	 if(frm.elements.disableButtonsForTBA.value == "Y"){
		disableField(frm.btAcceptMail);
		disableField(frm.btSave);
		disableField(frm.btPreAdvice);
		disableField(frm.btPrintULDTag);
		disableLink(document.getElementById("createLink"));
		disableLink(document.getElementById("modifyLink"));
		disableLink(document.getElementById("deleteLink"));
     }
}


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px';
	//alert((height*95)/100);
}


//-------------------------------------To go Directly to Mail Acceptance screen--------------------------------------
function gotoacceptMail()
{
	var frm=targetFormName;
	var radiobuttons = document.getElementsByName("assignedto");
	var selectedvalue = "";
	var destination = "";
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			selectedvalue = radiobuttons[i].value;
			break;
		}
	}

 	if(selectedvalue == "DESTINATION"){
		selectedvalue = "CARRIER";
		}
		var str = "mailtracking.defaults.mailacceptance.listmailacceptance.do?fromScreen=ASSIGNCONTAINER&assignToFlight="+selectedvalue;
		submitForm(targetFormName,str);
		frm.elements.status.value = "";
}


//---------------------------------------------------------------------------------------------------------------





function disableDetails(){

	var frm=targetFormName;
	frm.checkAll.checked = false;
	//frm.checkAll.disabled = true;
	disableField(frm.elements.checkAll);
	//frm.btAcceptMail.disabled = true;
	//frm.btViewMailBags.disabled = true;
	//frm.btReassign.disabled = true;
	//frm.btDeassign.disabled = true;
	//frm.btSave.disabled = true;
	//frm.btPreAdvice.disabled = true;
	disableField(frm.btAcceptMail);
	disableField(frm.btViewMailBags);
	disableField(frm.btReassign);
	disableField(frm.btDeassign);
	disableField(frm.btSave);
	disableField(frm.btPreAdvice);

	/*var link= document.getElementsByName("formlink");
	if(link != null){
		for(var i = 0;i<link.length;i++){
			disableLink (link[i]);
			link[i].disabled = true;
		}
	}*/

	var radiobuttons = document.getElementsByName("assignedto");
	for(var i=0; i<radiobuttons.length; i++){
	if(radiobuttons[i].checked == true){
		frm.elements.assignedto[i].focus();
	}
 }

	/*if(isFlightSelected()){
		if((!frm.flightCarrierCode.disabled) && (!frm.flightCarrierCode.readOnly)){
		frm.flightCarrierCode.focus();
	}
	}
	else if((!frm.carrier.disabled) && (!frm.carrier.readOnly)){
		frm.carrier.focus();
	}*/
}

function isFlightSelected(){
	var isflight = true;
	var radiobuttons = document.getElementsByName("assignedto");
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			if(radiobuttons[i].value == "DESTINATION"){
				isflight = false;
			}
			break;
		}
	}
	return isflight;
}


function disableSearchDetails(){

	var frm=targetFormName;

	var radiobuttons = document.getElementsByName("assignedto");
	for(var i=0; i<radiobuttons.length; i++){
		//radiobuttons[i].disabled = true;
		disableField(radiobuttons[i]);
	}
	frm.elements.flightCarrierCode.readOnly = true;
	frm.elements.flightNumber.readOnly = true;
	frm.elements.flightDate.readOnly = true;
	frm.elements.carrier.readOnly = true;
	frm.elements.destn.readOnly = true;
	//frm.btn_incalender.disabled = true;
	//frm.carrierLOV.disabled = true;
	//frm.destinationLOV.disabled = true;
	//disableField(frm.destn);
	disableField(frm.carrierLOV);
	disableField(frm.destinationLOV);
	//frm.checkAll.disabled = false;
	enableField(frm.elements.checkAll);
	 //Privilege chceking done by A-4443 for icrd-3698 starts
   if(frm.btAcceptMail.privileged == 'Y'){
	//frm.btAcceptMail.disabled = false;
	enableField(frm.btAcceptMail);
			}
	//frm.btAcceptMail.disabled = false;
	 if(frm.btViewMailBags.privileged == 'Y'){
	//frm.btViewMailBags.disabled = false;
	enableField(frm.btViewMailBags);
			}
	//frm.btViewMailBags.disabled = false;
	if(frm.btReassign.privileged == 'Y'){
	//frm.btReassign.disabled = false;
	enableField(frm.btReassign);
			}
	//frm.btReassign.disabled = false;
	if(frm.btDeassign.privileged == 'Y'){
	//frm.btDeassign.disabled = false;
	enableField(frm.btDeassign);
			}
	//frm.btDeassign.disabled = false;
	if(frm.btSave.privileged == 'Y'){
	//frm.btSave.disabled = false;
	enableField(frm.btSave);
			}
	//frm.btSave.disabled = false;
	if(frm.btPreAdvice.privileged == 'Y'){
	//frm.btPreAdvice.disabled = false;
	enableField(frm.btPreAdvice);
			}
	//frm.btPreAdvice.disabled = false;
	//Privilege chceking done by A-4443 for icrd-3698 ends

	/*var link= document.getElementsByName("formlink");
	if(link != null){
		for(var i = 0;i<link.length;i++){
			enableLink(link[i]);
			link[i].disabled = false;
		}
	}*/

	if(!isFlightSelected()){
		//frm.btPreAdvice.disabled = true;
		 disableField(frm.btPreAdvice);
	}

}

function reassignContainer(frm){

	var chk = document.getElementsByName("subCheck");
	if(isRowSelected(chk)){

		//submitForm(targetFormName,'mailtracking.defaults.assigncontainer.reassignContainer.do');
		recreateListDetails('mailtracking.defaults.assigncontainer.reassignContainer.do','assigncontainerTable');
	}
}

function deassignContainer(frm){

	var chk = document.getElementsByName("subCheck");
	if(isRowSelected(chk)){

	   showDialog({msg:'<bean:message bundle="assignContainerResources" key="mailtracking.defaults.assigncontainer.msg.warn.unassign" />',
	   type:4, parentWindow:self, parentForm:frm, dialogId:'id_1', onClose:function(){
  screenConfirmDialog(frm,'id_1');
  screenNonConfirmDialog(frm,'id_1')
   }
  });


	}
}

function closeDetails(frm){

	if(frm.elements.fromScreen.value == 'MAILACCEPTANCE'){
		if(isFlightSelected()){
			submitForm(frm,'mailtracking.defaults.mailacceptance.listmailacceptance.do?assignToFlight=FLIGHT');
		}
		else{
			submitForm(frm,'mailtracking.defaults.mailacceptance.listmailacceptance.do?assignToFlight=CARRIER');

		}
	}
	else{
		location.href = appPath + "/home.jsp";
	}
}

function saveDetails(frm){

	submitForm(frm,'mailtracking.defaults.assigncontainer.saveContainerDetails.do');
}

function listDetails(frm){

	frm.elements.status.value = "NO";
	submitForm(frm,'mailtracking.defaults.assigncontainer.listAssignContainer.do');

	//recreateMultiTableDetails("mailtracking.defaults.assigncontainer.listAssignContainer.do","div1","ajaxUpdateForList");
}

function clearDetails(frm){

	submitForm(frm,'mailtracking.defaults.assigncontainer.clearAssignContainer.do');
}

function showPreAdvice(frm){

	recreateListDetails('mailtracking.defaults.assigncontainer.showpreadvice.do','assigncontainerTable');
}

function createContainer(){

	if(targetFormName.elements.screenStatusFlag.value != "screenload"){
		targetFormName.elements.flightStatus.value = "CREATE";
		targetFormName.elements.warningFlag.value = "Y";
		recreateListDetails('mailtracking.defaults.assigncontainer.validateFlight.do','assigncontainerTable');
	}
}

function modifyContainer(){

	if(targetFormName.elements.screenStatusFlag.value != "screenload"){
		var chk = document.getElementsByName("subCheck");
		if(isRowSelected(chk)){

			targetFormName.elements.flightStatus.value = "MODIFY";
			targetFormName.elements.warningFlag.value = "Y";
			recreateListDetails('mailtracking.defaults.assigncontainer.validateFlight.do','assigncontainerTable');
		}
	}

}

function deleteContainer(){

	if(targetFormName.elements.screenStatusFlag.value != "screenload"){
		var chk = document.getElementsByName("subCheck");
		if(isRowSelected(chk)){

			targetFormName.elements.flightStatus.value = "DELETE";
			targetFormName.elements.warningFlag.value = "Y";
			recreateListDetails('mailtracking.defaults.assigncontainer.validateFlight.do','assigncontainerTable');
		}
	}
}

function showMailBags(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck',1,1)){
		recreateListDetails('mailtracking.defaults.assigncontainer.viewmails.do','assigncontainerTable');
	}
}

function acceptMail(frm){

	frm.elements.status.value="showAcceptMail";
	onScreenLoad(frm);
	//submitForm(targetFormName,'mailtracking.defaults.assigncontainer.showacceptmail.do');

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
	showDialog({msg:"<common:message bundle='assignContainerResources' key='mailtracking.defaults.assigncontainer.msg.info.norowsselected' />", type:1, parentWindow:self, parentForm:frm,
	dialogId:'id_1'});
  }
  return flag;
}

function selectDiv() {
	var radiobuttons = document.getElementsByName("assignedto");
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
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

function confirmMessage(){
var frm = targetFormName;
 	var warningCode = frm.elements.warningFlag.value;
 	if(warningCode != null){
		if('flight_tba_tbc' == warningCode ){
		frm.elements.status.value = "NO";
	submitForm(frm,'mailtracking.defaults.assigncontainer.listAssignContainer.do?overrideFlag=Y&disableButtonsForTBA=Y&duplicateAndTbaTbc=Y&status=NO');
		}else{
			submitForm(targetFormName,'mailtracking.defaults.assigncontainer.saveContainerDetails.do?status=OVERRIDE');
		}
	}
}
function nonconfirmMessage(){
}

function showPrintULDTag(){
	var frm = targetFormName;
	if(validateSelectedCheckBoxes(targetFormName,'subCheck','','1')){

		generateReport(frm, "/mailtracking.defaults.assigncontainer.reports.printuldtagcommand.do");
	}

}

////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
 var _currDiv1 = "";

 function clearMultipleTable(){
       document.getElementById(_currDiv1).innerHTML="";
 }

 function recreateMultiTableDetails(strAction,divId1){

 	var __extraFn="updateMultiTableCode";

 	if(arguments[2]!=null){
 		__extraFn=arguments[2];
 	}

 	_currDiv1 = divId1;

 	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

 }

 function updateMultiTableCode(_tableInfo){

 	if(_currDiv1 != ""){
 		_str1 = getActualData(_tableInfo, "Data");
 		document.getElementById(_currDiv1).innerHTML=_str1;
 	}

 	cleanupMultipleTmpTable();

 	//reapplyEvents();
 	applyScrollTableStyles();

	onScreenLoad(targetFormName);

 }

function getActualData(_tableInfo, _identifier){

 document.getElementById("tmpSpan").innerHTML=_tableInfo;
 var _tmpSpanId="";

 	if(_identifier=='Data'){
	 	_tmpSpanId = document.getElementById("_data");
 	}

 	return _tmpSpanId.innerHTML;

 }

 function cleanupMultipleTmpTable(){
 	document.getElementById("tmpSpan").innerHTML="";
 }

 function ajaxUpdateForList(_tableInfo){

  	document.getElementById("tmpSpan").innerHTML=_tableInfo;
  	_innerFrm=document.getElementById("tmpSpan").getElementsByTagName("form")[0];

  	var _screenStatusFlag = _innerFrm.screenStatusFlag.value;
   	targetFormName.elements.screenStatusFlag.value = _screenStatusFlag;

   	var _status = _innerFrm.status.value;
   	targetFormName.elements.status.value = _status;

   	var _flightStatus = _innerFrm.flightStatus.value;
   	targetFormName.elements.flightStatus.value = _flightStatus;

   	var _currentDialogOption = _innerFrm.currentDialogOption.value;
   	targetFormName.elements.currentDialogOption.value = _currentDialogOption;

   	var _currentDialogId = _innerFrm.currentDialogId.value;
   	targetFormName.elements.currentDialogId.value = _currentDialogId;

   	var _fromScreen = _innerFrm.fromScreen.value;
   	targetFormName.elements.fromScreen.value = _fromScreen;

  	updateMultiTableCode(_tableInfo);

  	if(_asyncErrorsExist) return;

 }

 ////////////////////////////////////////////////////////////////////////////////////////




 /**
 *function to Confirm Dialog
 */
 function screenConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {
 	    if(dialogId == 'id_1'){
 		submitForm(frm,'mailtracking.defaults.assigncontainer.deassignContainer.do');
 	    }
 	    if(dialogId == 'id_2'){
	    frm.elements.warningFlag.value="";
		  gotoacceptMail();
	    }

 	}
 }


 /**
 *function to Non-Confirm Dialog
 */
 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_1'){

 		}
 		if(dialogId == 'id_2'){

 		}

 	}
}
 ////////////////// FOR ASYNC SUBMIT - AJAX - LIST///////////////////////////////////////////////

  var _tableDivId = "";
  function recreateListDetails(strAction,divId1){
  	var __extraFn="updateListCode";
  	_tableDivId = divId1;
  	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
  }

  function updateListCode(_tableInfo){
    _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
  	targetFormName.elements.status.value = _innerFrm.status.value;
    targetFormName.elements.screenStatusFlag.value = _innerFrm.screenStatusFlag.value;

  	targetFormName.elements.flightStatus.value = _innerFrm.flightStatus.value;
  	targetFormName.elements.currentDialogOption.value = _innerFrm.currentDialogOption.value;
  	targetFormName.elements.currentDialogId.value = _innerFrm.currentDialogId.value;
  	targetFormName.elements.fromScreen.value = _innerFrm.fromScreen.value;


    _assignContainer=_tableInfo.document.getElementById("_assignContainer");
 	document.getElementById(_tableDivId).innerHTML=_assignContainer.innerHTML;
 	onScreenLoad(targetFormName);

  }

 ////////////////////////////////////////////////////////////////////////////////////////
function prepareAttributes(obj,div,divName){
	var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[1];
	if(indexId != null && indexId != ""){
	 divId=div;
	}else{
	 divId=div+'';
	}
	getPlatformEvent().cancelBubble = true;
	showInfoMessage(divId,invId,divName);
}

function initializePanels(){

	var options_arry_polpouInfo = new Array();
	options_arry_polpouInfo = {
	  "autoOpen" : false,
	  "width" : 100,
	  "height": 78,
	  "draggable" :false,
	  "resizable" :false
	};

	   initDialog('polpouInfo',options_arry_polpouInfo);
}