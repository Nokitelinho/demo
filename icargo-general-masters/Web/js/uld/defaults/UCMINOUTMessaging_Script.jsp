<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	//frm.elements.carrierCode.focus();
	frm.elements.btnView.disabled=true;
	if(targetFormName.elements.btnSend.privileged == 'Y'){
	frm.elements.btnSend.disabled=true;
	}
	with(frm){
		onScreenLoad();
		addIEonScroll();
   		//DivSetVisible(true);

		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addIDEvents("originlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.origin.value,'Origin','1','origin','',0)",EVT_CLICK);
		evtHandler.addIDEvents("destlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destination.value,'Destination','1','destination','',0)",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnView","onClickView()",EVT_CLICK);
	//	evtHandler.addEvents("ucmOut","showMessageTypeChange()",EVT_CHANGE);

		// Added as part of ICRD-238949
		evtHandler.addEvents("masterRowId","updateHeaderCheckBox(targetFormName,this, targetFormName.selectedRows)",EVT_CLICK);	
		if (frm.selectedRows != null) {
			evtHandler.addEvents("selectedRows","toggleTableHeaderCheckbox('selectedRows', targetFormName.masterRowId);",EVT_CLICK);
		}
		// Added as part of bug ICRD-238949 ends
		evtHandler.addEvents("ucmOut","changeDestStatus()",EVT_CLICK);
		evtHandler.addEvents("btnSend","sendUCM()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus(frm)",EVT_BLUR);
		evtHandler.addEvents("ucmIn","validateMaximumLength(targetFormName.elements.ucmIn,40,'SI','Invalid SI',true)",EVT_BLUR);
		highlightULDErrors();
		if(frm.elements.addStatus.value=="Y"){
			setAddFocus();
			}
		// Below lines commented as part of ICRD-238949
		/*evtHandler.addEvents("masterRowId","updateHeaderCheckBox(frm,frm.elements.masterRowId,frm.elements.selectedRows)",EVT_CLICK);
		evtHandler.addEvents("selectedRows","toggleTableHeaderCheckbox('selectedRows',frm.elements.masterRowId)",EVT_CLICK);*/
	}
}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				 if((!frm.elements.carrierCode.disabled) && (frm.elements.isUldViewed.value != 'Y')
					&& (!frm.elements.carrierCode.readOnly)){
					frm.elements.carrierCode.focus();
				  }
				else{
					 if(!document.getElementById("addUld").disabled){
						document.getElementById("addUld").focus();
					 }
				}				
	}	
}

function onScreenLoad(){
  frm=targetFormName;
  
  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.validateFlights.do","600","280");
  }
  
  if(frm.elements.ucmBlockStatus.value=="Y"){
	  frm.elements.btnView.disabled=false;
	  if(targetFormName.elements.btnSend.privileged == 'Y'){
	frm.elements.btnSend.disabled=false;
		}
	var addLink=document.getElementById("addUld");

		addLink.disabled=true;
		addLink.onclick=function(){return false;}
		var deleteLink=document.getElementById('deleteUld');
		deleteLink.disabled=true;
		deleteLink.onclick=function(){return false;}
		disableInLinks();
}
showTable(targetFormName);
modify(targetFormName);
if(frm.elements.viewUldStatus.value=="Y"){
frm.elements.btnView.disabled=false;
if(targetFormName.elements.btnSend.privileged == 'Y'){
frm.elements.btnSend.disabled=false;
}
// Added by a-3045--starts for bug 37
frm.elements.carrierCode.focus();
// Added by a-3045--ends
}

if(frm.elements.orgDestStatus.value=="Y"){
frm.elements.destination.disabled=true;
}

if(frm.elements.messageTypeStatus.value=="Y"){
	var radiobuttons = document.getElementsByName("ucmOut");
		for(var i=0; i<radiobuttons.length; i++){
			radiobuttons[i].disabled = true;
	}
	}
 if(targetFormName.elements.ucmOut[1].checked){
frm.elements.destination.disabled=true;
}

	if(frm.elements.linkStatus.value!="Y"){
	var addLink=document.getElementById("addUld");
	addLink.disabled=true;
	addLink.onclick=function(){return false;}
	var deleteLink=document.getElementById('deleteUld');
	deleteLink.disabled=true;
	deleteLink.onclick=function(){return false;}
	disableInLinks();
}

// Added for ULD287--starts
// To disable fields after sucessful list
if(frm.elements.isUldViewed.value=='Y'){	
	frm.elements.carrierCode.disabled=true;
	frm.elements.flightNo.disabled=true;
	frm.elements.flightDate.disabled=true;
	frm.elements.origin.disabled=true;
	// Added by Preet--ends
	// Added by a-3045--starts for bug 37
	var addLink=document.getElementById("addUld");
	if(addLink.disabled == false){
		addLink.focus();
	}
	// Added by a-3045--ends
}
if(frm.elements.isUcmSent.value=='Y'){	
	var addLink=document.getElementById("addUld");
	addLink.disabled=true;
	addLink.onclick=function(){return false;}
	var deleteLink=document.getElementById('deleteUld');
	deleteLink.disabled=true;
	deleteLink.onclick=function(){return false;}
	//frm.btnList.disabled=true;
	disableField(frm.elements.btnList);
	disableInLinks();
	//showDialog('UCM Processed Successfully', 2, self, targetFormName, 'id_3');
	showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.successful"/>',
						type:2, 
						parentWindow:self, 
						parentForm:targetFormName, 
						dialogId:'id_3',
						onClose:function(result){
			screenConfirmDialog(targetFormName,'id_3');
						}
			});
}else{	
	var addLink=document.getElementById("addUld");
	addLink.disabled=false;
	var deleteLink=document.getElementById('deleteUld');
	deleteLink.disabled=false;
	//Added by A-7359 fro ICRD-259943 starts here
	var addInLink=document.getElementById("addInUld");
		addInLink.disabled=false;
		var deleteInLink=document.getElementById('deleteInUld');
		deleteInLink.disabled=false;
		//Added by A-7359 fro ICRD-259943 ends here
	//frm.btnList.disabled=false;	
	enableField(frm.elements.btnList);
}
//Added for ULD287 ends

	  if(!frm.elements.carrierCode.disabled && !frm.elements.carrierCode.readOnly){
			frm.elements.carrierCode.focus();
	  }
	  else{
			 if(!document.getElementById("addUld").disabled){
				document.getElementById("addUld").focus();
			 }
		}
}


function onClickList(){
	frm.elements.isUldViewed.value="N";
	submitForm(targetFormName,"uld.defaults.ucminout.listucminoutmessaging.do");
}

	function onClickClear(){
	frm.elements.isUldViewed.value="N";
	//submitForm(targetFormName,"uld.defaults.ucminout.clearucminoutmessaging.do");
	submitFormWithUnsaveCheck('uld.defaults.ucminout.clearucminoutmessaging.do');
}


function onClickView(){
	frm.elements.isUldViewed.value="Y";
	submitForm(targetFormName,"uld.defaults.ucminout.viewulds.do")
	}

// Added as part of bug ICRD-238795
function modify(frm){
var uldSource = eval(frm.elements.uldSource);
var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
if(hiddenOpFlags != null && hiddenOpFlags.length>0){
var size = hiddenOpFlags.length;
	if(size > 1) {
		for(var i=0; i<size; i++) {
			if(uldSource != null){
				if(uldSource.value == 'UCM' || 
				   uldSource.value == 'MFT' || 
				   uldSource.value == 'MAL'){
					//frm.elements.contentInd[i].disabled = true; Commented by A-7359 for ICRD-256142
					frm.elements.damageCodes[i].disabled = true;
				}
				if(uldSource[i] != null 
				&&(uldSource[i].value == 'UCM' || 
				uldSource[i].value == 'MFT' || 
				uldSource[i].value == 'MAL')){
					//frm.elements.contentInd[i].disabled = true; Commented by A-7359 for ICRD-256142
					frm.elements.damageCodes[i].disabled = true;
					}	
				}		
			}
		}
	}
}
	function deleteDetails(){
	var frm = targetFormName;   

//Added as part of bug ICRD-223807
	var uldSource = eval(frm.elements.uldSource);
	var selectedRows = eval(frm.elements.selectedRows);
	
	//Added as part of bug ICRD-223807 ends
	//if(validateSelectedCheckBoxes(targetFormName,'selectedRows',1000000,1)){
			selectCheckbox(targetFormName);

		//showDialog('Do you want to delete the selected rows?', 4, self, frm, 'id_1');
	
	var pou = eval(frm.elements.pou); 
	if(pou != null && uldSource != null) {
		var size = pou.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(selectedRows[i].checked 
					&& (uldSource.value == 'UCM' || 
						uldSource.value == 'MFT' || 
						uldSource.value == 'MAL')){
							showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.err.cannotdelete" />',
							type:1,
							parentWindow:self,
							parentForm:frm
							})
						}
			if(uldSource[i] != null){
					if(selectedRows[i].checked 
						&& (uldSource[i].value == 'UCM' 
							|| uldSource[i].value == 'MFT' 
							|| uldSource[i].value == 'MAL')){
					showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.err.cannotdelete" />',
                    type:1,
                    parentWindow:self,
					parentForm:frm
					})
				}
			}
		}
	}
}
//Added as part of bug ICRD-223807 ends
		showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.deletrow" />',
		type:4,
		parentWindow:self,
		parentForm:targetFormName,
		dialogId:'id_1',
		onClose:function(result){
		screenConfirmDialog(targetFormName,'id_1');
		screenNonConfirmDialog(targetFormName,'id_1');
		}});
		

	}

 function screenConfirmDialog(frm, dialogId) {


	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {

 	if(dialogId=='id_1'){

	//submitForm(targetFormName,"uld.defaults.ucminout.deleteulddetails.do");
	deleteTableRow('selectedRows','hiddenOpFlag');
	}

	}
}

 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'N') {
	if(dialogId=='id_1'){
	}

	}
 	}

function addDetails(){
	addTemplateRow('ucmoutTemplateRow','ucmoutbody','hiddenOpFlag');
}


function validateULDNumbers(frm){
	var uldNumbers = eval(frm.elements.uldNumbers); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(uldNumbers != null) {
		var size = uldNumbers.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(uldNumbers[i].value == "") {
			return true;
			}
		}
			}
	}else{
			 if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(uldNumbers.value == "") { 				
			return true;
		}
	}
	}
	}
	return false;
}

function validateDestination(frm){
	var pou = eval(frm.elements.pou); 
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(pou != null) {
		var size = pou.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
			if(hiddenOpFlags[i].value != "NOOP" && hiddenOpFlags[i].value != "D"){
				if(pou[i].value == "") {
					return true;					
				}
				}
			}
		 }else {
			 if(hiddenOpFlags.value != "NOOP" && hiddenOpFlags.value != "D"){
				if(pou.value == "") { 				
					return true;
				}
			}
		}
	}
	return false;
}



function changeDestStatus(){
 if(targetFormName.elements.ucmOut[1].checked){


 targetFormName.destination.disabled=true;

}else{

	targetFormName.destination.disabled=false;

}
	}


function sendUCM(){
	
	if(validateRowCount(targetFormName)){
		selectCheckbox(targetFormName);
		if(validateULDNumbers(targetFormName)){
			//showDialog('Enter ULD Number', 1, self, targetFormName, 'id_1');
			showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.enteruld" />',type:1,parentWindow:self});
			return;
		}		
		if(validateDestination(targetFormName)){
			//showDialog('Enter Destination', 1, self, targetFormName, 'id_2');
			showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.enterdestination" />',type:1,parentWindow:self});
			return;
		}
		//submitForm(targetFormName,"uld.defaults.ucminout.senducmmessage.do");
			openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=UCM&targetAction=uld.defaults.ucminout.senducmmessage.do",1050,320);
	}
	else{
		if(frm.elements.isUldViewed.value=='N'){
			//showDialog("Click on View CPM ULDs. ", 1);
			showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.clickonview" />',type:1,parentWindow:self});
		}
		else{
			//showDialog("Add ULD/s. ", 1);
			showDialog({msg:'<bean:message bundle="ucminoutResources" key="uld.defaults.ucminout.msg.warn.adduld" />',type:1,parentWindow:self});
		}
		
	}	
}




	function selectCheckbox(frm){


	var isDamaged = "";


	var val = "";
	for(var i=0;i<targetFormName.elements.length;i++) {

		if(targetFormName.elements[i].name =='damageCodes') {

			if(targetFormName.elements[i].checked) {
				 val = "Y";
			}
			else {
			 	 val = "N";
			}
				if(isDamaged != "")
					isDamaged = isDamaged+","+val;
				else if(isDamaged == "")
					isDamaged = val;

		}
}

targetFormName.elements.damagedStatus.value=isDamaged;

}


function onClickClose(){
	
	submitForm(targetFormName,"uld.defaults.closeaction.do");
	}


	function confirmMessage(){	
		 //Modified by A-7359 for ICRD-230380 
	  if(targetFormName.elements.isUcmSent.value == "N"){
	      submitForm(frm,"uld.defaults.messaging.listucmerrorlog.do");
	   }else{
	submitForm(targetFormName,"uld.defaults.ucminout.viewulds.do?outConfirmStatus=Y&ucmVOStatus=Y");
		}
		}

		function nonconfirmMessage(){
       if(targetFormName.elements.isUcmSent.value == "N"){
		//Added by A-7359 for ICRD-230380 starts here
		frm.elements.isUldViewed.value="N";
	    submitForm(targetFormName,"uld.defaults.ucminout.clearucminoutmessaging.do");
		//Added by A-7359 for ICRD-230380 ends here
	   }else{
submitForm(targetFormName,"uld.defaults.ucminout.viewulds.do?outConfirmStatus=Y&ucmVOStatus=N");

			}
}
function highlightULDErrors(){
var errorCodes=document.getElementsByName('errorCodes');
var opFlags=document.getElementsByName('operationFlag');
if(errorCodes != null){
	for(var i=0;i<errorCodes.length;i++){
		if(opFlags[i].value != "D"){
		if(errorCodes[i].value != null && errorCodes[i].value.trim().length>0){
			var currentUldNumber = document.getElementById('uldNumber'+i);
			currentUldNumber.style.color = "red";
			currentUldNumber.style.fontWeight = "bold";

			}
		}
	}

	}
	}
	
	
	function setAddFocus(){
	var uldNumbers=document.getElementsByName('uldNumbers');
			if(targetFormName.elements.addStatus.value=="Y" && uldNumbers != null){
			var uldLength=uldNumbers.length;


			if(uldLength>1){
				uldNumbers[uldLength-1].focus();
				}else{
				uldNumbers[0].focus();

					}
			}
			targetFormName.elements.addStatus.value="";
		}

/* Added by A-2412 */

function validateRowCount(frm){
	var hiddenOpFlags = eval(frm.elements.hiddenOpFlag); 
	if(hiddenOpFlags ==null){
		return false;
	}
	else{
		var size = hiddenOpFlags.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(hiddenOpFlags[i].value == "I" || hiddenOpFlags[i].value == "U"){	
					return true;	
				}		
			}
		 }		 
	}
	return false;
}
/* Added by A-7359 for ICRD 192413 */
function valueChange() {
    var radios = document.getElementsByName('ucmOut');
	var ucmProperty;
    for (var i = 0, length = radios.length; i < length; i++) {
    if (radios[i].checked) {
        ucmProperty=radios[i].value;
		break;
    }
   }
	var ucmOut = document.getElementById("ucmOutTable");
	var ucmIn = document.getElementById("ucmInTable");
	targetFormName.elements.ucmOut.value=ucmProperty;
    if(ucmProperty=="OUT"){
	ucmOut.style.display = (ucmOut.style.display == 'none') ? 'block' : 'none';
	ucmIn.style.display='none';
	}else{
	ucmIn.style.display = (ucmIn.style.display == 'none') ? 'block' : 'none';
	ucmOut.style.display='none';
	}
}
//Added by A-7359 fro ICRD-259943 starts here
//Method for Add/Delete rows for UCMIN starts here
function addInDetails(){
	addTemplateRow('ucminTemplateRow','ucminbody','hiddenOpFlag');
}
function deleteInDetails(){
	deleteTableRow('selectedRows','hiddenOpFlag');
}
//Method for disabling Add/Delete for UCMIN
function disableInLinks(){
		var addInLink=document.getElementById("addInUld");
		addInLink.disabled=true;
		addInLink.onclick=function(){return false;}
		var deleteInLink=document.getElementById('deleteInUld');
		deleteInLink.disabled=true;
		deleteInLink.onclick=function(){return false;}
}
//For showing the corresponding tabel after UCM IN/OUT List call
function showTable(frm){
	 var ucmOutValues = document.getElementsByName('ucmOut');
	 var ucmProperty;
    for (var i = 0, length = ucmOutValues.length; i < length; i++) {
    if (ucmOutValues[i].checked) {
        ucmProperty=ucmOutValues[i].value;
		break;
    }
   }
	targetFormName.elements.ucmOut.value=ucmProperty;
	var ucmOut = document.getElementById("ucmOutTable");
	var ucmIn = document.getElementById("ucmInTable");
    if(ucmProperty == "IN"){  
	ucmIn.style.display = 'block';
	ucmOut.style.display='none';
	}
  }
//Added by A-7359 fro ICRD-259943 ends here
		
