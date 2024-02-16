<%@ include file="/jsp/includes/js_contenttype.jsp" %>


<%@ include file="/jsp/includes/tlds.jsp"%>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
     	evtHandler.addEvents("btActivate","activate(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btInactivate","deActivate(this.form)",EVT_CLICK);

     	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.elements.rowId)",EVT_CLICK);

    	if(frm.elements.rowId != null){
			evtHandler.addEvents("rowId","toggleTableHeaderCheckbox('rowId',targetFormName.elements.checkAll)",EVT_CLICK);
		}

		evtHandler.addEvents("addLink","addDetail()",EVT_CLICK);
		evtHandler.addEvents("deleteLink","deleteDetail()",EVT_CLICK);
		evtHandler.addEvents("classLOV","invokeLov(this,'classLOV')",EVT_CLICK);
		//evtHandler.addEvents("billingPeriodLOV","invokeLov(this,'billingPeriodLOV')",EVT_CLICK);
		
		evtHandler.addIDEvents("countryLOV","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.countryCode.value,'Country','1','countryCode','',0)", EVT_CLICK);
		evtHandler.addIDEvents("paLOV","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.paCode.value,'PA','1','paCode','',0)", EVT_CLICK);
//		evtHandler.addIDEvents("classLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.mailClass.value,'mailClass','1','mailClass','',1)", EVT_CLICK);
                evtHandler.addIDEvents("currencyLOV","displayLOV('showCurrency.do','N','Y','showCurrency.do',targetFormName.elements.settlementCurrencyCode.value,'settlementCurrencyCode','1','settlementCurrencyCode','',0)", EVT_CLICK);
		//evtHandler.addIDEvents("cusCodeLOV","displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.cusCode.value,'cusCode','1','cusCode','',0)", EVT_CLICK);
     //BLUR Events



		evtHandler.addEvents("addBilLink","addBillDetail()",EVT_CLICK);
		evtHandler.addEvents("deleteBilLink","deleteBillDetail()",EVT_CLICK);
		evtHandler.addEvents("addSetLink","addSetDetail()",EVT_CLICK);
		evtHandler.addEvents("deleteSetLink","deleteSetDetail()",EVT_CLICK);
		evtHandler.addEvents("addOtherLink","addOtherDetail()",EVT_CLICK);
		evtHandler.addEvents("deleteOtherLink","deleteOtherDetail()",EVT_CLICK);
		//Added as part of IASCB-1176 starts
		evtHandler.addEvents("email","emailValidate(this.form)",EVT_BLUR);
		evtHandler.addEvents("secondaryEmail1","emailValidate(this.form)",EVT_BLUR);
	    evtHandler.addEvents("secondaryEmail2","emailValidate(this.form)",EVT_BLUR);
		evtHandler.addEvents("validSetTo","operationFlagChangeOnEdit(this,'stlOpFlag');",EVT_BLUR);
			//Added as part of IASCB-1176 ends
	  	}
}

//function invokeBillingPeriodLOV(_index) {
//     	var index1 = _index.id.split('billingPeriodLOV')[1];
//   	displayLOV('shared.billingperiod.billingperiodlovlist.do','N','Y','shared.billingperiod.billingperiodlovlist.do',targetFormName.billingFrequency.value,'billingFrequency','0','billingFrequency','',index1);
//}
function closeScreen(){
location.href = appPath + "/home.jsp";
}
function changeValue(element,value){

		if(value=='UPUCOD'){
			jquery('#parValue'+element.getAttribute('rowcount')).css('visibility','visible');
			jquery('#parValue'+element.getAttribute('rowcount')).css({"width":"100px"});
			jquery('#partyIdr'+element.getAttribute('rowcount')).css('visibility','visible');
			jquery('#partyIdr'+element.getAttribute('rowcount')).css({"width":"50px"});
			jquery('#combo'+element.getAttribute('rowcount')).css('visibility','hidden');
			jquery(element).parent().parent().find("span").css('visibility','visible');
		}
		else if(value=='HANMALTYP'){
			jquery('#combo'+element.getAttribute('rowcount')).css('visibility','visible');
			jquery('#combo'+element.getAttribute('rowcount')).css({"width":"100px"});
			jquery('#parValue'+element.getAttribute('rowcount')).css('visibility','hidden');
			jquery('#parValue'+element.getAttribute('rowcount')).css({"width":"0"});
			jquery('#partyIdr'+element.getAttribute('rowcount')).css('visibility','hidden');
			jquery('#partyIdr'+element.getAttribute('rowcount')).css({"width":"0px"});
			jquery(element).parent().parent().find("span").css('visibility','hidden');
		}
		else{
			jquery('#parValue'+element.getAttribute('rowcount')).css('visibility','visible');
			jquery('#parValue'+element.getAttribute('rowcount')).css({"width":"100px"});
			jquery('#partyIdr'+element.getAttribute('rowcount')).css('visibility','hidden');
			jquery('#partyIdr'+element.getAttribute('rowcount')).css({"width":"0"});
			jquery(element).parent().parent().find("span").css('visibility','hidden');
			jquery('#combo'+element.getAttribute('rowcount')).css('visibility','hidden');
			jquery('#combo'+element.getAttribute('rowcount')).css({"width":"0"});
		}
}
function resetFocus(){
	 if(!event.shiftKey){ 
		if((!targetFormName.elements.paCode.disabled) && (!targetFormName.elements.paCode.readOnly)
			&& (targetFormName.elements.screenStatusFlag.value == "screenload")){	
			targetFormName.elements.paCode.focus();
		}
		else if((!targetFormName.elements.paName.disabled) && (!targetFormName.elements.paName.readOnly)
			&& (targetFormName.elements.screenStatusFlag.value != "screenload")){
			targetFormName.elements.paName.focus();
		}	
	}	
}
//Added as part of IASCB-1176 starts
function emailValidate(frm) {
	if((frm.elements.email.value != null && frm.elements.email.value != "")) {
		var isValid = validateEmail(frm.elements.email.value);
		if(isValid == false) {
		    showDialog({msg:"Invalid Email Format",
									    type:1,
										parentWindow:self}); 
			
			frm.elements.claimantEmail.focus();
		}
	}
	if (frm.elements.secondaryEmail1.value != null && frm.elements.secondaryEmail1.value != ""){
		var isValid = validateEmail(frm.elements.secondaryEmail1.value);
		if(isValid == false) {
		    showDialog({msg:"Invalid Email Format",
									    type:1,
										parentWindow:self}); 
			
			frm.elements.claimantEmail.focus();
		}
	}
	if (frm.elements.secondaryEmail2.value != null && frm.elements.secondaryEmail2.value != ""){
		var isValid = validateEmail(frm.elements.secondaryEmail2.value);
		if(isValid == false) {
		    showDialog({msg:"Invalid Email Format",
									    type:1,
										parentWindow:self}); 
			
			frm.elements.claimantEmail.focus();
		}
	}
	}

//Added as part of IASCB-1176 ends
function addSetDetail(){

addTemplateRow('settlementRow','setTableBody','stlOpFlag');
}
function deleteSetDetail(){
	//Added as part of bug icrd-18097 by A-4810
	var chkbox = document.getElementsByName("setCheck");
	var opflag = document.getElementsByName("stlOpFlag");
	var del = "false";

       if(targetFormName.elements.statusActiveOrInactive.value == "ACTIVE"){
		 for(var i=0; i<chkbox.length;i++){
			  if(chkbox[i].checked) {
		if(opflag[i].value == "N" || opflag[i].value == "U"){
     del = "true";
	showDialog({msg:"Cannot delete already saved settlementdetails of an active GPA Code",type:1,parentWindow:self,parentForm:document.forms[1],dialogId:'id_1',onClose:function(result){}});

      return;

	}


   }
  }
}
	if(del=="false")
	{
deleteTableRow('setCheck','stlOpFlag');
    }
}
function activate(frm){
submitForm(frm,"mailtracking.defaults.pamaster.activate.do");

}

function deActivate(frm){

submitForm(frm,"mailtracking.defaults.pamaster.inactivate.do");



}

function onScreenLoad(frm){
disableField(document.getElementById("countryLOV"));
	resetSubClass(frm);
	if((!targetFormName.elements.paCode.disabled) && (!targetFormName.elements.paCode.readOnly)
		&& (targetFormName.elements.screenStatusFlag.value == "screenload")){	
		targetFormName.elements.paCode.focus();
	}
	else if((!targetFormName.elements.paName.disabled) && (!targetFormName.elements.paName.readOnly)
		&& (targetFormName.elements.screenStatusFlag.value != "screenload")){
		targetFormName.elements.paName.focus();
	}
	var radiobuttons = document.getElementsByName("messagingEnabled");
	if(frm.elements.screenStatusFlag.value == "screenload"){
			//radiobuttons[0].checked = true;
			disableDetails();
		}else if(frm.elements.screenStatusFlag.value == "detail"){
			frm.paLOV.disabled=true;
			
		enableField(document.getElementById("countryLOV"));
			
		}
	if(frm.elements.statusActiveOrInactive.value == "ACTIVE"){

		frm.btActivate.disabled=true;
	}
	if(frm.elements.statusActiveOrInactive.value == "INACTIVE"){
		frm.btInactivate.disabled=true;
	}
	selectRadio();


}

function invokeLov(obj,name){
	var index = obj.id.split(name)[1];
	if(name == "classLOV"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailClass.value,'mailClass','1','mailClass','',index);
   	}
   	
}
function disableDetails(){

	var frm=targetFormName;
	var radiobuttons = document.getElementsByName("messagingEnabled");
	frm.elements.paName.readonly = true;
	frm.elements.btSave.disabled = true;
	frm.elements.countryCode.readonly = true;
	frm.elements.address.readonly = true;
	disableLink(document.getElementById("addSetLink"));
	disableLink(document.getElementById("deleteSetLink"));
	disableLink(document.getElementById("addBilLink"));
	disableLink(document.getElementById("deleteBilLink"));
	disableLink(document.getElementById("addOtherLink"));
	disableLink(document.getElementById("deleteOtherLink"));
	disableLink(document.getElementById("addLink"));
	disableLink(document.getElementById("deleteLink"));
	for(var i=0; i<radiobuttons.length; i++){
		radiobuttons[i].disabled = true;
	}

}

function listDetails(frm){

	submitForm(frm,"mailtracking.defaults.pamaster.list.do");
selectRadio();
}

function saveDetails(frm){

  var partialSelect ="N";
  var mailgroup = "N";
  if(targetFormName.elements.autoEmailReqd != null && targetFormName.elements.autoEmailReqd.value=='on'){    
      targetFormName.elements.autoEmailReqd.value="Y";
      }

	var radiobuttons = document.getElementsByName("messagingEnabled");
		for(var i=0; i<radiobuttons.length; i++){
			if(radiobuttons[i].checked == true)
			{
				if(radiobuttons[i].value != "P"){
					var opFlag = eval(frm.operationFlag);
					if(opFlag != null){
					  if(opFlag.length>1){
					  for(var j=0; j<opFlag.length; j++){
						  if(!(opFlag[j].value == "D" || opFlag[j].value == "NOOP")){
						  	deleteLink.disabled = false;
							showDialog({msg:"<common:message bundle='postalAdminResources' key='mailtracking.defaults.pamaster.msg.err.rowexists'/>",type:1,parentWindow:self,onClose:function(result){}});
							return;
						 }
						}
					  }
					}
				}else{
					partialSelect='Y';
					addLink.disabled = false;
					deleteLink.disabled = false;
				}
				break;
			}
	}
	
	selectCheckbox();
	if(partialSelect=='Y'){
		var emptyrowExist = false;
		var mailClass = eval(frm.elements.mailClass);
		var mailCategory = eval(frm.elements.mailCategory);
		var opFlag = eval(frm.elements.operationFlag);
		if(opFlag!=null && opFlag.length >0){
			for(var i=0; i< opFlag.length; i++){
				if(opFlag[i].value!="NOOP" && opFlag[i].value!="D"){
					if(((mailCategory!=null && mailCategory[i].value.trim().length >0) || (mailClass!=null && mailClass[i].value.trim().length >0))){
						mailgroup = 'Y';
					}else{
						emptyrowExist =true;
						break;
					}	
				}
			
				
			}
		}else{
			emptyrowExist =true;
		}	
	//if(validateCategoryAndSubClass(frm)){
		if(frm.elements.msgSelected.value=='N' ){      
		showDialog({msg:"Please select atleast one milestone for partial messaging",type:1,parentWindow:self,onClose:function(result){}});
		return;
	}
	}

	



	//updateOperationFlag(frm);
	updateOtherDetailsOpFlg(frm);
	var chkbox = document.getElementsByName("profInv");
	for(var i=0;i<chkbox.length-1;i++) {
		if(chkbox[i].checked){
			chkbox[i].value="Y";
		}
		else{
			chkbox[i].value="N";
		}
	}
	if(!isFormModified()){
	    	showDialog({msg:"<common:message bundle='postalAdminResources' key='mailtracking.defaults.pamaster.msg.err.foundnodatatosave'/>",type:1,parentWindow:self,onClose:function(result){}});
		return;


	}
	 else{

		 submitForm(frm,"mailtracking.defaults.pamaster.save.do");
        }
}


function clearDetails(frm){

	submitForm(frm,"mailtracking.defaults.pamaster.clear.do");
}

function selectRadio() {
	var radiobuttons = document.getElementsByName("messagingEnabled");
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
		
		if(radiobuttons[i].value=="N"){//added by A-7371 for ICRD-212135
		disableField(targetFormName.elements.resditTriggerPeriod);
		}else{
		enableField(targetFormName.elements.resditTriggerPeriod);
		}
		
			if(radiobuttons[i].value != "P"){
				disableLink(document.getElementById("addLink"));
	disableLink(document.getElementById("deleteLink"));
			}else if(radiobuttons[i].value == "P"){ 
				enableLink(document.getElementById("deleteLink"));
				enableLink(document.getElementById("addLink"));
				//deleteLink.disabled = false;
			}
			break;
		}
	}
}
function addBillDetail(){

	addTemplateRow('billingRow','bilTableBody','bilOpFlag');
}
function  deleteBillDetail(){
	deleteTableRow('billCheck','bilOpFlag');
}
function addOtherDetail(){
	addTemplateRow('otherRow','otherTableBody','invOpFlag');
}
function deleteOtherDetail(){
	deleteTableRow('invCheck','invOpFlag');
}

function addDetail(){
	frm = targetFormName;
	var radiobuttons = document.getElementsByName("messagingEnabled");

	for(var i=0; i<radiobuttons.length; i++){

	  if(radiobuttons[i].checked == true){

	     if(radiobuttons[i].value == "P"){

	         /*if(validateSubClass(frm)){

		       showDialog('<common:message bundle="postalAdminResources" key="mailtracking.defaults.pamaster.msg.err.subClasEmpty" />', 1, self);
		       return;
	         }*/

	         selectCheckbox();

	         //updateOperationFlag(frm);

	       //submitForm(frm,"mailtracking.defaults.pamaster.add.do");



	         addTemplateRow('paTemplateRow','paTableBody','operationFlag');

	     }

	     break;
	  }
       }

}

function deleteDetail(){

	frm = targetFormName;
	var radiobuttons = document.getElementsByName("messagingEnabled");
	for(var i=0; i<radiobuttons.length; i++){
	    if(radiobuttons[i].checked == true){


	            selectCheckbox();

	            //updateOperationFlag(frm);

	            if(validateSelectedCheckBoxes(frm,'rowId',1000000000,1)){

		        //submitForm(frm,"mailtracking.defaults.pamaster.delete.do");

		        deleteTableRow('rowId','operationFlag');

		    }

	      break;
	    }
	}
}

function updateOperationFlag(frm){

	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag
	var opFlag = eval(frm.elements.opFlag);  //for main Vos operation Flag

 	var rowId = eval(frm.elements.rowId);

	var recieved = eval(frm.elements.recieved);
	var uplifted = eval(frm.elements.uplifted);
	var assigned = eval(frm.elements.assigned);
	var returned = eval(frm.elements.returned);
	var handedOver = eval(frm.elements.handedOver);
	var pending = eval(frm.elements.pending);
	var delivered = eval(frm.elements.delivered);
	var handoverReceived = frm.elements.handoverReceived;
	loaded = frm.elements.loaded;
	onlineHandover = frm.elements.onlineHandover;

	if (rowId != null) {

	 		if (rowId.length > 1) {


	 			for (var i = 0; i < rowId.length; i++) {

	 				var checkBoxValue = rowId[i].value;




	 				if((operationFlag[checkBoxValue].value !='D')&&
	 										(operationFlag[checkBoxValue].value !='U')) {


	 					if ((recieved[checkBoxValue].checked != recieved[checkBoxValue].defaultChecked)||
	 						(uplifted[checkBoxValue].checked != uplifted[checkBoxValue].defaultChecked)||
	 						(assigned[checkBoxValue].checked != assigned[checkBoxValue].defaultChecked)||
	 						(returned[checkBoxValue].checked != returned[checkBoxValue].defaultChecked)||
	 						(handedOver[checkBoxValue].checked != handedOver[checkBoxValue].defaultChecked)||
	 						(delivered[checkBoxValue].checked != delivered[checkBoxValue].defaultChecked)||
	 						(pending[checkBoxValue].checked != pending[checkBoxValue].defaultChecked)||
	 						(handoverReceived[checkBoxValue].checked != handoverReceived[checkBoxValue].defaultChecked)||
	 						(loaded[checkBoxValue].checked != loaded[checkBoxValue].defaultChecked)||
	 						(onlineHandover[checkBoxValue].checked != onlineHandover[checkBoxValue].defaultChecked)) {

                                if(operationFlag[checkBoxValue].value !='I'){
	 								frm.elements.operationFlag[checkBoxValue].value='U';

								}
	 					}


	 				}


				}

	 		}else {


	 			if(operationFlag.value !='D'){

	 				if ((recieved.checked != recieved.defaultChecked)||
						(uplifted.checked != uplifted.defaultChecked)||
						(assigned.checked != assigned.defaultChecked)||
						(returned.checked != returned.defaultChecked)||
						(handedOver.checked != handedOver.defaultChecked)||
						(delivered.checked != delivered.defaultChecked)||
						(handoverReceived.checked != handoverReceived.defaultChecked)||
						(loaded.checked != loaded.defaultChecked)||
						(onlineHandover.checked != onlineHandover.defaultChecked)||
						(pending.checked != pending.defaultChecked)) {

							if(operationFlag.value !='I'){
	 							frm.elements.operationFlag.value = 'U';
							}
	 				}//ends if
	 			}//ends if

	 		}//ends else
	 	}//ends (if rowId!=null)



	 	var name = eval(frm.elements.paName);
	 	var country = eval(frm.elements.countryCode);
	 	var address = eval(frm.elements.address);

	 	if(opFlag.value != 'D'){

			if((name.value != name.defaultValue)||
				(country.value != country.defaultValue)||
				(address.value != address.defaultValue)){
					if(opFlag.value !='I'){
						frm.elements.opFlag.value = 'U';
					}
			}

		}



}//ends the function

function selectCheckbox(){

frm = targetFormName;
var sel = "N";
var isRecieved = "";
var isUplifted = "";
var isAssigned = "";
var isReturned = "";
var isHandedOver = "";
var isPending = "";
var isDelivered = "";
var isReadyForDelivery = "";
var isTransportationCompleted = "";
var isArrived = "";
var isHandoverRcvd = "";
var isLoaded = "";
var isOnlineHandover = "";

	var val = "";
	for(var i=0;i<frm.elements.length;i++) {

		if(frm.elements[i].name =='recieved') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isRecieved != "")
					isRecieved = isRecieved+","+val;
				else if(isRecieved == "")
					isRecieved = val;

		}

		if(frm.elements[i].name =='handoverReceived') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
				 val = "false";
			}
				if(isHandoverRcvd != "")
					isHandoverRcvd = isHandoverRcvd+","+val;
				else if(isHandoverRcvd == "")
					isHandoverRcvd = val;

		}

		if(frm.elements[i].name =='uplifted') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isUplifted != "")
					isUplifted = isUplifted+","+val;
				else if(isUplifted == "")
					isUplifted = val;

		}

		if(frm.elements[i].name =='loaded') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
				 val = "false";
			}
				if(isLoaded != "")
					isLoaded = isLoaded+","+val;
				else if(isLoaded == "")
					isLoaded = val;

		}

		if(frm.elements[i].name =='assigned') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isAssigned != "")
					isAssigned = isAssigned+","+val;
				else if(isAssigned == "")
					isAssigned = val;

		}

		if(frm.elements[i].name =='returned') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isReturned != "")
					isReturned = isReturned+","+val;
				else if(isReturned == "")
					isReturned = val;

		}

		if(frm.elements[i].name =='onlineHandover') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
				 val = "false";
			}
				if(isOnlineHandover != "")
					isOnlineHandover = isOnlineHandover+","+val;
				else if(isOnlineHandover == "")
					isOnlineHandover = val;

		}

		if(frm.elements[i].name =='handedOver') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isHandedOver != "")
					isHandedOver = isHandedOver+","+val;
				else if(isHandedOver == "")
					isHandedOver = val;

		}

		if(frm.elements[i].name =='pending') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isPending != "")
					isPending = isPending+","+val;
				else if(isPending == "")
					isPending = val;

		}
		if(frm.elements[i].name =='delivered') {

			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isDelivered != "")
					isDelivered = isDelivered+","+val;
				else if(isDelivered == "")
					isDelivered = val;

		}
		if(frm.elements[i].name =='readyForDelivery') {
			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isReadyForDelivery != "")
					isReadyForDelivery = isReadyForDelivery+","+val;
				else if(isReadyForDelivery == "")
					isReadyForDelivery = val;
		}
		if(frm.elements[i].name =='transportationCompleted') {
			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isTransportationCompleted != "")
					isTransportationCompleted = isTransportationCompleted+","+val;
				else if(isTransportationCompleted == "")
					isTransportationCompleted = val;
		}
		if(frm.elements[i].name =='arrived') {
			if(frm.elements[i].checked) {
				 val = "true";
				 sel = "Y";
			}
			else {
			 	 val = "false";
			}
				if(isArrived != "")
					isArrived = isArrived+","+val;
				else if(isArrived == "")
					isArrived = val;
		}
	}

	frm.elements.recievedArray.value = isRecieved;
	frm.elements.upliftedArray.value = isUplifted;
	frm.elements.assignedArray.value = isAssigned;
	frm.elements.returnedArray.value = isReturned;
	frm.elements.handedOverArray.value = isHandedOver;
	frm.elements.pendingArray.value = isPending;
	frm.elements.deliveredArray.value = isDelivered;
	frm.elements.readyForDeliveryArray.value = isReadyForDelivery;
	frm.elements.transportationCompletedArray.value = isTransportationCompleted;
	frm.elements.arrivedArray.value = isArrived;
	frm.elements.handoverRcvdArray.value = isHandoverRcvd;
	frm.elements.loadedArray.value = isLoaded;
	frm.elements.onlineHandoverArray.value = isOnlineHandover;
	if(sel == "Y"){
		frm.elements.msgSelected.value='Y';
	}else{
		frm.elements.msgSelected.value='N';
	}

}

function validateCategoryAndSubClass(frm){

var mailClass = eval(frm.elements.mailClass);
	var mailCategory = eval(frm.elements.mailCategory);
var opFlag = eval(frm.elements.operationFlag);

    if(mailClass != null){

	if(mailClass.length>1){
	   for(var i=0;i<mailClass.length;i++){
             if(opFlag[i].value != "NOOP"){
					if(mailCategory[i].value=="" && mailClass[i].value.trim()!=""){

			return true;
					}else if(mailCategory[i].value!="" && mailClass[i].value.trim()==""){
						return true;
					}
				}
			}
	}
    }

   return false;
}

function confirmMessage() {
	targetFormName.elements.btSave.disabled  = false;

}

function nonconfirmMessage() {
}
function updateOtherDetailsOpFlg(frm){
var code=frm.elements.parCode;
var opFlg=frm.elements.invOpFlag;
var parValue=frm.elements.parameterValue;
var frmDate=frm.elements.validInvFrom;
var toDate=frm.elements.validInvTo;
var handoverMailTypValue= frm.elements.handoverMailTypValue;
var remarks=frm.elements.detailedRemarks;
for(var index = 0; index<opFlg.length;index++){
if(isElementModified(code[index]) || isElementModified(parValue[index]) || isElementModified(frmDate[index]) || isElementModified(toDate[index])|| isElementModified(remarks[index]) || isElementModified(handoverMailTypValue[index]))	{
			
			if("NOOP" != opFlg[index].value && "I" != opFlg[index].value && "D" != opFlg[index].value){
			opFlg[index].value = "U";
			frm.elements.invOpFlag[index].value = "U";
			
			}
}
}
}


function resetSubClass(frm){
	var mailClass = eval(frm.elements.mailClass);
	var opFlag = eval(frm.elements.operationFlag);
	if(mailClass != null){
		if(mailClass.length>1){
			for(var i=0;i<mailClass.length;i++){
				if(opFlag[i].value != "NOOP"){
					if(mailClass[i].value=="ALL"){
						mailClass[i].value="";
					}
				}
			}
}
}
}

function invokeBillingPeriodLOV(_index){
var index1 = _index.id.split("billingPeriodLOV")[1];
displayLOV('shared.billingperiod.billingperiodlovlist.do','N','Y','shared.billingperiod.billingperiodlovlist.do',targetFormName.elements.billingFrequency.value,'billingFrequency','1','billingFrequency','',index1)

}