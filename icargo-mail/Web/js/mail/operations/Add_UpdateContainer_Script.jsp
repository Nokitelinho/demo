<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad(frm);
   with(frm){
   	//CLICK Events
   	evtHandler.addEvents("btClose","closePopup(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btAddNew","addNewContainer(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btOk","okPopup(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btPopupList","listDetails(this.form)",EVT_CLICK);
   	evtHandler.addEvents("addLink","addOnwardRouting()",EVT_CLICK);
	evtHandler.addEvents("deleteLink","deleteOnwardRouting()",EVT_CLICK);

	evtHandler.addIDEvents("destinationLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.containerDestination.value,'Airport','0','containerDestination','',0)", EVT_CLICK);
	evtHandler.addEvents("poulov","invokeLov(this,'poulov')",EVT_CLICK);

	evtHandler.addEvents("contCheckAll","updateHeaderCheckBox(this.form,targetFormName.elements.contCheckAll,targetFormName.elements.contSubCheck)",EVT_CLICK);

	if(frm.elements.contSubCheck != null){
		evtHandler.addEvents("contSubCheck","toggleTableHeaderCheckbox('contSubCheck',targetFormName.elements.contCheckAll)",EVT_CLICK);
	}

     //BLUR Events
       evtHandler.addEvents("fltCarrier","operationFlagChangeOnEdit(this,'opFlag');",EVT_BLUR); 
       evtHandler.addEvents("fltNo","operationFlagChangeOnEdit(this,'opFlag');",EVT_BLUR); 
       evtHandler.addEvents("depDate","operationFlagChangeOnEdit(this,'opFlag');",EVT_BLUR); 
       evtHandler.addEvents("pointOfUnlading","operationFlagChangeOnEdit(this,'opFlag');",EVT_BLUR); 
	   evtHandler.addEvents("containerNumber","validatecontainernumberformat()",EVT_BLUR);
       
     //CHANGE Events
     	//evtHandler.addEvents("pou", "defaultDestn(this.form)", EVT_CHANGE);
     	evtHandler.addEvents("barrowCheck", "disableBulkDestn(this.form)", EVT_CHANGE);
     	evtHandler.addEvents("pou", "populateBulkDestn(this.form)", EVT_CHANGE);
		evtHandler.addEvents("barrowCheck","setValue()",EVT_BLUR);
     	

   	}
}
function validatecontainernumberformat(){
if(!targetFormName.elements.barrowCheck.checked){
     validateFields(targetFormName.elements.containerNumber,-1,'Container Number',0,true,true);
}
}

function onScreenLoad(frm){
	frm=targetFormName;
	var str = frm.elements.assignedto.value;
	var mode = frm.elements.status.value;
	var type = frm.elements.containerType.value;
	if(frm.elements.barrowCheck.checked){
	type="B";
	}
	if(str == "DESTINATION"){

		frm.elements.pou.disabled = true;
		//frm.containerDestination.value=frm.destn.value;
		frm.elements.containerDestination.style.backgroundColor = "#f0f0f0";
		frm.elements.containerDestination.disabled = true;
		disableField(frm.destinationLOV);
			
		disableOnwardRouting();
		frm.elements.pou.value="";
		if("B" == type){
			targetFormName.elements.paBuilt.checked = false;
		}

		if(mode == "CREATE"){
			frm.elements.btPopupList.disabled = true;
			frm.elements.btAddNew.disabled = false;
		}
		else if(mode == "MODIFY"){
			frm.elements.btPopupList.disabled = false;
			frm.elements.btAddNew.disabled = true;
			frm.elements.barrowCheck.disabled = true;
		}
	}
	else if(str == "FLIGHT"){

		frm.elements.pou.disabled = false;
		frm.elements.containerDestination.disabled = false;
		frm.destinationLOV.disabled = false;
		if("B" == type){
			disableOnwardRouting();
			targetFormName.elements.paBuilt.checked = false;
		}
		else{
			enableOnwardRouting();
		}

		if(mode == "CREATE"){
			frm.elements.btPopupList.disabled = true;
			frm.btAddNew.disabled = false;
			if(frm.elements.containerDestination.value == ""){
			   //frm.containerDestination.value = frm.pou.value;
			   frm.elements.prevPou.value = frm.elements.pou.value;
			}
			
		}
		else if(mode == "MODIFY"){
			frm.elements.btPopupList.disabled = false;
			frm.btAddNew.disabled = true;
			frm.elements.barrowCheck.disabled = true;
			frm.elements.pou.disabled = true;
		}
	}

	var stat = frm.currentAction.value;
	if(stat == "CLOSEWINDOW"){
		if(frm.elements.fromScreen.value == "TRANSFER_MAIL_CLOSE"){
			frm.elements.fromScreen.value = "";
			var flightCarrierCode = frm.elements.flightCarrierCode.value;
			var flightNumber = frm.elements.flightNumber.value;
			var flightDate = frm.elements.flightDate.value;
			var carrier = frm.elements.carrier.value;
			window.opener.targetFormName.action = "mailtracking.defaults.transfermail.listtransfermail.do?flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&flightDate="+flightDate+"&carrierCode="+carrier+"&assignToFlight=FLIGHT";
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
			window.closeNow();
		}else if(frm.elements.fromScreen.value == "REASSIGN_MAIL_CLOSE"){
			frm.fromScreen.value = "";
			var flightCarrierCode = frm.elements.flightCarrierCode.value;
			var flightNumber = frm.elements.flightNumber.value;
			var depDate = frm.elements.flightDate.value;
			var carrierCode = frm.elements.carrier.value;
			var destination = frm.elements.destn.value;
			var selectedRadio = frm.elements.assignedto.value;
			window.opener.targetFormName.action = "mailtracking.defaults.reassignmail.listflight.do?flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&depDate="+depDate+"&carrierCode="+carrierCode+"&destination="+destination+"&assignToFlight="+selectedRadio;
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
			window.closeNow();
		}
		else if(frm.elements.fromScreen.value == "CHANGE_FLIGHT_CLOSE"){
			frm.elements.fromScreen.value = "";
			var fltCarrierCode = frm.elements.flightCarrierCode.value;
			var fltNumber = frm.elements.flightNumber.value;
			var arrDate = frm.elements.flightDate.value;
			window.opener.targetFormName.action = "mailtracking.defaults.mailchange.listflight.do?fltCarrierCode="+fltCarrierCode+"&fltNumber="+fltNumber+"&arrDate="+arrDate;
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
			window.closeNow();
		}else{
			window.opener.targetFormName.elements.carrier.disabled = false;
			window.opener.targetFormName.elements.destn.disabled = false;
			window.opener.targetFormName.elements.flightCarrierCode.disabled = false;
			window.opener.targetFormName.elements.flightNumber.disabled = false;
			window.opener.targetFormName.elements.flightDate.disabled = false;
			window.opener.targetFormName.action = "mailtracking.defaults.assigncontainer.refreshAssignContainer.do?assignedto="+str;
			window.opener.IC.util.common.childUnloadEventHandler();
			window.opener.targetFormName.submit();
			window.closeNow();
		}
	}
	if(frm.elements.fromScreen.value == "CHANGE_FLIGHT"){
	disableField(frm.elements.btAddNew);
	disableField(document.getElementById("addLink"));
	disableField(document.getElementById("deleteLink"));
	}
}

function pouLovDisplay(fld){

	displayLOV('showAirport.do','Y','Y','showAirport.do',targetFormName.elements.pointOfUnlading.value,'pointOfUnlading','0','pointOfUnlading','',fld.getAttribute("index"));
}

function handleCombo(){

	targetFormName.elements.containerNumber.value = "";
	submitForm(targetFormName,'mailtracking.defaults.assigncontainer.refreshPopup.do');
}

function addNewContainer(frm){
	if(frm.elements.paBuilt.checked){
	       frm.elements.paBuilt.value="Y";
	    }else{
	       frm.elements.paBuilt.value="N";
  	}
	submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.validateContainer.do?currentAction=ADDNEW');
}

function okPopup(frm){
	if(frm.elements.paBuilt.checked){
	       frm.elements.paBuilt.value="Y";
	    }else{
	       frm.elements.paBuilt.value="N";
  	}
	submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.validateContainer.do?currentAction=OK');
}

function listDetails(frm){

	submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.listContainerDetails.do');
}

function addOnwardRouting(){

	//submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.addOnwardRouting.do');	
	var samePou="N";
	var rchdDestn="N";
	if(targetFormName.elements.pou.value != targetFormName.elements.containerDestination.value.toUpperCase()){
		for(var k=1;k<targetFormName.elements.pointOfUnlading.length;k++){
			var len=targetFormName.elements.pointOfUnlading.length;
			if(targetFormName.elements.pou.value == targetFormName.elements.pointOfUnlading[len-2].value.toUpperCase()){
				samePou="Y";
				break;
			}
			if(targetFormName.elements.containerDestination.value.toUpperCase() == targetFormName.elements.pointOfUnlading[len-2].value.toUpperCase()){
				rchdDestn="Y";
				break;
			}
		}
		if(("Y" != samePou) && ("Y" != rchdDestn)){
			addTemplateRow('assignContainerTemplateRow','assignContainerTableBody','opFlag');
		}else{
			if("Y" == samePou){
				showDialog({msg:"<common:message bundle='assignContainerResources' key='mailtracking.defaults.assigncontainer.msg.err.pouequalspou' />", type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});
			}else if("Y" == rchdDestn){
				showDialog({msg:"<common:message bundle='assignContainerResources' key='mailtracking.defaults.assigncontainer.msg.err.noRouting' />", type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});
			}
		}
	} else{				
		showDialog({msg:"<common:message bundle='assignContainerResources' key='mailtracking.defaults.assigncontainer.msg.err.pouequalsdestn' />", type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_1'});
	  }
}

function deleteOnwardRouting(){

	var chk = document.getElementsByName("contSubCheck");	
	if(isRowSelected(chk)){
		//submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.deleteOnwardRouting.do');
	for(var i=0; i<chk.length; i++)
		{
			if(chk[i].checked == true)
			{
			
				targetFormName.elements.pointOfUnlading[i].value="";   //Commented as part of ICRD-145640
			}
		}
		deleteTableRow('contSubCheck','opFlag');
	}
}

function closePopup(frm){

	//submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.close.do');
	window.closeNow();
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
	showDialog({msg:"<common:message bundle='assignContainerResources' key='mailtracking.defaults.assigncontainer.msg.info.norowsselected' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
  }
  return flag;
}

function disableOnwardRouting(){

	var obj_link = targetFormName.getElementsByTagName("a");
	if(obj_link!=null){
		for(var i = 0;i<obj_link.length;i++){
			obj_link[i].disabled = true;
		}

	}

	targetFormName.elements.contCheckAll.disabled = true;
}

function enableOnwardRouting(){

	var obj_link = targetFormName.getElementsByTagName("a");
	if(obj_link!=null){
		for(var i = 0;i<obj_link.length;i++){
			obj_link[i].disabled = false;
		}

	}

	targetFormName.elements.contCheckAll.disabled = false;
}

function confirmPopupMessage(){

	var warningCode = targetFormName.elements.warningCode.value;
	if(warningCode == "uldnotreleasedfrominboundflight") {
		submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.setReassignFlag.do');
	}
	if(warningCode == "uldalreadyassignedtocarrier"){
		submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.setReassignFlag.do');
	}
	if(warningCode == "contValidationFailed") {
		submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.validateContainer.do?overrideFlag=overrideConValidation');
	} else {
		submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.setReassignFlag.do');
		
		
		if(warningCode =="reassignContainerForDestn" && frm.elements.fromScreen.value == "REASSIGN_MAIL"){
		relistScreen();
		}
	}
}
function relistScreen(){
var frm=targetFormName;
frm.elements.fromScreen.value = "";
			var flightCarrierCode = frm.elements.flightCarrierCode.value;
			var flightNumber = frm.elements.flightNumber.value;
			var depDate = frm.elements.flightDate.value;
			var carrierCode = frm.elements.carrier.value;
			var destination = frm.elements.destn.value;
			var selectedRadio = frm.elements.assignedto.value;
			 frm = self.opener.targetFormName;
	frm.action="mailtracking.defaults.reassignmail.listflight.do?flightCarrierCode="+flightCarrierCode+"&flightNumber="+flightNumber+"&depDate="+depDate+"&carrierCode="+carrierCode+"&destination="+destination+"&assignToFlight="+selectedRadio;
	frm.method="post";
	frm.submit();
	window.close();
	return;
}
function nonconfirmPopupMessage(){

	submitForm(targetFormName,'mailtracking.defaults.assigncontainer.popup.clear.do');
}

function defaultDestn(frm) {
    
   	if(frm.elements.containerDestination.value.toUpperCase() == frm.elements.prevPou.value.toUpperCase()) {
 		frm.elements.containerDestination.value = frm.elements.pou.value; 		
 	}
 	frm.elements.prevPou.value = frm.elements.pou.value;
}

function invokeLov(obj,name){

   var index = obj.id.split(obj.name)[1];
   
   if(name == "poulov"){
         displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.pointOfUnlading[index].value,'Airport','0','pointOfUnlading','',index);    
   }
   
}
function disableBulkDestn(frm){
   
   var str = frm.elements.assignedto.value;
   
   if(frm.elements.barrowCheck.checked){
    	 if(frm.elements.paBuilt.checked){
	      	frm.elements.paBuilt.checked=false;
	 		 }
    	frm.elements.paBuilt.disabled = true;	
      	if(str != "DESTINATION"){
      		frm.elements.containerDestination.value=frm.pou.value;
			frm.elements.containerType.value="B";
      		      		}    
		frm.containerDestination.disabled = true;
		frm.elements.containerDestination.style.backgroundColor = "#f0f0f0";
  	 }else{    
   		frm.elements.paBuilt.disabled = false;
      if(str != "DESTINATION"){
	     frm.elements.containerDestination.value="";
		 frm.elements.containerType.value="U";
         frm.elements.containerDestination.disabled = false;
         frm.destinationLOV.disabled = false;
      }
   }
}


function populateBulkDestn(frm){ 
if(frm.barrowCheck.checked){
		frm.elements.containerType.value="B";
		frm.elements.containerDestination.value=frm.pou.value;
		frm.elements.containerDestination.disabled = true;
		frm.elements.containerDestination.style.backgroundColor = "#f0f0f0";
		frm.destinationLOV.disabled = true;  
	}
}
 function setValue(){
	var frm=targetFormName;
	if(frm.elements.barrowCheck.checked){
	frm.elements.containerType.value="B";
	}else{
	frm.elements.containerType.value="U";
	   }
	}