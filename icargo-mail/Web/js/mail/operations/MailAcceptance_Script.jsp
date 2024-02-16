<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

  // onScreenloadSetHeight();
   onScreenLoad();
   initializePanels();
   with(frm){
     //CLICK Events
     	evtHandler.addEvents("btnList","listMailAcceptance()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearMailAcceptance()",EVT_CLICK);
     //	evtHandler.addIDEvents("addLink","addMails()",EVT_CLICK);
     	evtHandler.addIDEvents("addLink","addMails()",EVT_CLICK);
     	evtHandler.addIDEvents("deleteLink","deleteMails()",EVT_CLICK);
     	evtHandler.addEvents("btnReopenFlight","reopenFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnCloseFlight","closeFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnDelete","deleteMailAcceptance()",EVT_CLICK);
     	evtHandler.addEvents("btnSave","saveMailAcceptance()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeMailAcceptance()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
        evtHandler.addEvents("btnPreAdvice","preAdvice()",EVT_CLICK);
       // evtHandler.addEvents("btnAssignContainer","assignContainer()",EVT_CLICK);
        evtHandler.addEvents("btnReassignContainer","reassignContainer()",EVT_CLICK);
		document.getElementById("ignoreHiddenCheck").value = "N";
		/********************************************************************************/
		//Commented by Gopinath M (A-3217) for the Bug Id: MTK708 @ TRV on 31-Aug-2009 
		evtHandler.addEvents("btnCaptureULDDamage","captureULDDamage()",EVT_CLICK);
		/********************************************************************************/
		
        if(frm.selectMail != null){
	    evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',targetFormName.masterContainer)",EVT_CLICK);
	}

     //BLUR Events
        //evtHandler.addEvents("flightNumber","disableFilter(this.form)",EVT_BLUR);
		evtHandler.addEvents("btTransfer","transferContainer()",EVT_CLICK);//added by A-7371 for ICRD-133987

   	}
}

function resetFocus(){	
	 if(!event.shiftKey){	 
		  var radiobuttons = document.getElementsByName("assignToFlight");
		  var focusStatus = "No";
			  for(var i=0; i<radiobuttons.length; i++){	
				if(radiobuttons[i].checked == true)
				{	
						if((radiobuttons[i].value=="FLIGHT") && (!targetFormName.elements.flightCarrierCode.disabled)
							&& (!targetFormName.elements.flightCarrierCode.readOnly)){
							targetFormName.elements.flightCarrierCode.focus();
							focusStatus = "yes";
						}
						else if((radiobuttons[i].value=="CARRIER") && (!targetFormName.elements.carrierCode.disabled)
							&& (!targetFormName.elements.carrierCode.readOnly)){
							targetFormName.elements.carrierCode.focus();
							focusStatus = "yes";
						}
				}
			}
			if((focusStatus == "No") && (!document.getElementById("addLink").disabled)){
				document.getElementById("addLink").focus();
			}	
	}
}	

function preWarningMessages(){

   collapseAllRows();

   selectDiv();

}

var asyncRetrycount=0;
function onScreenLoad(){
  frm=targetFormName;
asyncRetrycount=0;
   if(frm.elements.carrierCode.disabled)
  {
   var carlov = document.getElementById("carLov");
   var desLov = document.getElementById("desLov");
  disableField(carlov);
  disableField(desLov);
  }

  if(frm.elements.existingMailbagFlag.value == "Y"){
  		frm.elements.existingMailbagFlag.value = "";
        openPopUp('mailtracking.defaults.mailacceptance.existingmailbag.screenload.do',650,330);
  }
  if(frm.elements.operationalStatus.value == "OPEN"){
       //targetFormName.btnReopenFlight.disabled=true;
	   disableField(targetFormName.elements.btnReopenFlight);
	   enableField(targetFormName.elements.btTransfer);
	   //Privilege chceking done by A-4443 for icrd-3698 
	   if(frm.elements.btnCloseFlight.privileged == 'Y'){
			//frm.btnCloseFlight.disabled = false;
			enableField(targetFormName.elements.btnCloseFlight);
			}
    	//targetFormName.btnCloseFlight.disabled=false;
  }else if(frm.elements.operationalStatus.value == "CLOSED"){
  //Privilege chceking done by A-4443 for icrd-3698 
   if(frm.elements.btnReopenFlight.privileged == 'Y'){
			//frm.btnReopenFlight.disabled = false;
			enableField(targetFormName.elements.btnReopenFlight);
			}
      //targetFormName.btnReopenFlight.disabled=false;
    	//targetFormName.btnCloseFlight.disabled=true;
		disableField(targetFormName.elements.btnCloseFlight);
  }else{
        //targetFormName.btnReopenFlight.disabled=true;
		disableField(targetFormName.elements.btnReopenFlight);
      	//targetFormName.btnCloseFlight.disabled=true;
		disableField(targetFormName.elements.btnCloseFlight);
  }

  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }
  if(frm.elements.uldsSelectedFlag.value == "N"){
	  frm.elements.uldsSelectedFlag.value = "";
  		openPopUp("mailtracking.defaults.mailacceptance.selectemptyulds.do","400","290");
  }

  if(frm.elements.reassignScreenFlag.value == "Y"){
  		var radiobuttons = document.getElementsByName("assignToFlight");
  		var assignto;
  		for(var i=0; i<radiobuttons.length; i++){
  	   		if(radiobuttons[i].checked == true)
  	   		{
  	     		assignto = radiobuttons[i].value;
  	     		break;
  	   		}
  		}
  	var reassignMode = "FLIGHT";
  	var str = "mailtracking.defaults.reassigncontainer.screenload.do?assignedto="+assignto+"&reassignedto="+reassignMode+"&fromScreen=MAILACCEPTANCE";
       	openPopUp(str,"600","417");
        frm.elements.reassignScreenFlag.value = "";
  }
  // added by A-7371 for ICRD-133987
  if(frm.elements.transferContainerFlag.value == "showTransferContainerScreen"){
	frm.elements.transferContainerFlag.value= "";
	var reassignMode="DESTINATION";
	var str = "mailtracking.defaults.transfercontainer.screenload.do?reassignedto="+reassignMode+"&fromScreen=MAIL_ACCEPTANCE";
	openPopUp(str,"600","398");
  }
  if(frm.elements.disableSaveFlag.value == "Y"){
  	//frm.btnSave.disabled = true;
	disableField(frm.elements.btnSave);
  }else{
  //Privilege chceking done by A-4443 for icrd-3698 
  if(frm.elements.btnSave.privileged == 'Y'){
        //frm.btnSave.disabled = false;
		enableField(targetFormName.elements.btnSave);
  }
        //frm.btnSave.disabled = false;
  }

  if(frm.elements.disableDestnFlag.value == "Y"){

    	//frm.btnCloseFlight.disabled = true;
    	//frm.btnReopenFlight.disabled = true;
    	//frm.btnPreAdvice.disabled = true;
		disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnPreAdvice);
  }
  if(frm.elements.preAdviceFlag.value == "Y"){
  	  frm.elements.preAdviceFlag.value = "";
      openPopUp("mailtracking.defaults.preadvice.screenload.do","720","320");
  }
  if(frm.elements.initialFocus.value == "Y"){
      frm.elements.initialFocus.value = "";
      frm.elements.initialFocus.defaultValue = "";
      var radiobuttons = document.getElementsByName("assignToFlight");
	  for(var i=0; i<radiobuttons.length; i++){
   		if(radiobuttons[i].checked == true)
  	 	{
     			if(radiobuttons[i].value=="FLIGHT"){
     				frm.elements.flightCarrierCode.focus();
     			}
     			else if(radiobuttons[i].value=="CARRIER"){
     				frm.elements.carrierCode.focus();
     			}
   		}
  	 }
  }
  else if(!document.getElementById("addLink").disabled){	
		document.getElementById("addLink").focus();
  }
  if(frm.elements.captureULDDamageFlag.value=="Y"){
  	frm.elements.captureULDDamageFlag.value="";
  	CaptureDamage();
  }
  if(frm.elements.closeflight.value == "N"){
     frm.elements.closeflight.value = "";
     var str = "";
     var radiobuttons = document.getElementsByName("assignToFlight");
	for(var i=0; i<radiobuttons.length; i++){
	    if(radiobuttons[i].checked == true)
	    {
		str = radiobuttons[i].value;
		break;
	    }
  	}
     var chkbox =document.getElementsByName("selectMail");

     var flag = "N";
     if(frm.elements.preassignFlag.value == "Y"){
	if(validateSelectedCheckBoxes(frm,'selectMail','1000000000','1')){
            flag = "Y";
	}
     }else{
        flag = "Y";
     }

     if(flag == "Y"){
         var selectMail = "";
         var cnt1 = 0;
         for(var i=0; i<chkbox.length;i++){
         	if(chkbox[i].checked) {
         	    if(cnt1 == 0){
         		  selectMail = chkbox[i].value;
         		  cnt1 = 1;
         	    }else{
         		  selectMail = selectMail + "," + chkbox[i].value;
         	    }
         	}
         }

	 frm.elements.warningFlag.value = "Y";
         var strAction="mailtracking.defaults.mailacceptance.addmailacceptance.do";
         var strUrl=strAction+"?selectMail="+selectMail+"&assignToFlight="+str;
         openPopUp(strUrl,1260,560);

     }
  }

   if(frm.elements.disableButtons.value == "Y"){    	
		disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnPreAdvice);
		disableField(frm.elements.btnDelete);
		disableField(frm.elements.btnSave);		
		disableField(frm.elements.btTransfer);		
		
  }

if(frm.elements.operationalStatus.value == "CLOSED" && frm.elements.disableButtons.value == "TBC"){
enableField(frm.elements.btnReopenFlight);
disableField(frm.elements.btnPreAdvice);
		disableField(frm.elements.btnDelete);
		disableField(frm.elements.btnSave);

}
else if(frm.elements.operationalStatus.value == "OPEN" && frm.elements.disableButtons.value == "TBC"){
disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnPreAdvice);
		disableField(frm.elements.btnDelete);
		disableField(frm.elements.btnSave);
}
	if(frm.elements.disableAddModifyDeleteLinks.value == "Y"){
		disableLink(addLink);
		disableLink(deleteLink);
	}
	if(frm.elements.disableButtonsForAirport.value == "Y"){
        disableField(frm.elements.btnReassignContainer);		
		disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnPreAdvice);
		disableField(frm.elements.btnDelete);
		disableField(frm.elements.btnSave);		
		disableField(frm.elements.btTransfer);		
		
	}
	showSaveDialog(frm);
 }


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*98)/100)+'px';
	//alert((height*95)/100);
}


/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

/******************************************************************************/
//Commented by Gopinath M (A-3217) for the Bug Id: MTK708 @ TRV on 31-Aug-2009 
/*function captureULDDamage(){
	var frm=targetFormName;

    if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){

		//submitAction(frm,'/mailtracking.defaults.mailacceptance.captureulddamage.do');
		recreateListDetails('mailtracking.defaults.mailacceptance.captureulddamage.do','acceptanceTable');
	}
}*/
/******************************************************************************/
 

function listMailAcceptance(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.mailacceptance.listflight.do');
}

function clearMailAcceptance(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.mailacceptance.clearmailacceptance.do');

}

function deleteMailAcceptance(){
   frm=targetFormName;

   var chkbox =document.getElementsByName("selectMail");

   if(validateSelectedCheckBoxes(frm,'selectMail','1000000000','1')){
   
		var selectMail = "";
        var cnt1 = 0;
        for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkbox[i].value;
     			  cnt1 = 1;
     	  	  }else{
     			  selectMail = selectMail + "," + chkbox[i].value;
     	  	  }
     	 	}
        }
		//showDialog("Do you wish to delete the selected ULDs from flight?", 4, self, frm, 'id_2');
  	   // screenConfirmDialog(frm,'id_2');
   		//screenNonConfirmDialog(frm,'id_2');

		var strAction="/mailtracking.defaults.mailacceptance.deletemailacceptance.do";
	    var strUrl=strAction+"?selectMail="+selectMail;
	    submitAction(frm,strUrl);

   }

}
function reassignContainer(){
	frm=targetFormName;
   var chkbox =document.getElementsByName("selectMail");

   var str = "";
     var radiobuttons = document.getElementsByName("assignToFlight");
	for(var i=0; i<radiobuttons.length; i++){
	    if(radiobuttons[i].checked == true)
	    {
		str = radiobuttons[i].value;
		break;
	    }
  	}
   if(validateSelectedCheckBoxes(frm,'selectMail','1000000000','1')){

   	var selectMail = "";
            var cnt1 = 0;
            for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkbox[i].value;
     			  cnt1 = 1;
     	  	  }else{
     			  selectMail = selectMail + "," + chkbox[i].value;
     	  	  }
     	 	}
            }
       targetFormName.elements.selCont.value =  selectMail;

       var strAction="mailtracking.defaults.mailacceptance.reassignmailacceptance.do?assignedto="+str;
       recreateListDetails(strAction,'acceptanceTable','reassig');
   }
}
//added by A-7371 for ICRD-133987
function transferContainer(){

var frm=targetFormName;
var chkbox =document.getElementsByName("selectMail");
var uldType = eval(frm.elements.uldType);
//To get the selected ulds and to validate if none is checked or more than one [arg 3 determines how many can be selected at a time]
   if(validateSelectedCheckBoxes(frm,'selectMail','20000000','1'))
   {
   
   	var selectMail = "";
            var cnt1 = 0;
            for(var i=0; i<chkbox.length;i++)
       	    {
			if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkbox[i].value;
				  if(uldType[i]==null){  //added by A-8149 for ICRD-270524
						var type=uldType.value;
						}else{
						var type=uldType[i].value;
						}
						if(type=="B"){
						showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.warn.bulkcontainer" scope="request"/>',type:1,parentWindow:self});
						return;
					}
     			  cnt1 = 1;
     	  	    }else{
     			  selectMail = selectMail + "," + chkbox[i].value;
     	  	     }
     	 	 }
      		}
			targetFormName.elements.selectedContainer.value = selectMail;
	
		var strAction='/mailtracking.defaults.mailacceptance.transfermail.do';
     submitAction(frm,strAction);
	}

}

function saveMailAcceptance(){
   frm=targetFormName;
   if(frm.elements.fromScreen.value !="carditEnquiry"){
   	frm.elements.fromScreen.value = "";
   }
   var chkbox =document.getElementsByName("selectMail");

   if(chkbox.length > 0){
      submitAction(frm,'/mailtracking.defaults.mailacceptance.savemailacceptance.do');
   }else{
		showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.nosave" scope="request"/>',type:1,parentWindow:self});
   }
}

function preAdvice(){
   frm=targetFormName;
   recreateListDetails('mailtracking.defaults.mailacceptance.preadvice.do','acceptanceTable','preadv');

}

function assignContainer(){
   frm=targetFormName;
   if(frm.elements.warningFlag.value == "Y"){
         //showDialog("Unsaved data exists.Do you wish to continue?", 4, self, frm, 'id_1');
		 showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.unsave" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
   }else{
         //submitAction(frm,'/mailtracking.defaults.mailacceptance.assigncontainer.do');
         gotoassignContainer();
     }
}

function gotoassignContainer(){
 //------------------------------------------To go Directly to assign container screen--------------------//

		frm=targetFormName;
       	frm.elements.closeFlag.value = "";
      	var str = "";
  	var radiobuttons = document.getElementsByName("assignToFlight");
  	for(var i=0; i<radiobuttons.length; i++){
  	   if(radiobuttons[i].checked == true)
  	   {
  	     str = radiobuttons[i].value;
  	     break;
  	   }
  	}
  	var screen = "MAILACCEPTANCE";
  	var strAction="/mailtracking.defaults.assigncontainer.listAssignContainerFromAccept.do";
          if(str == "FLIGHT"){
             var carrier = frm.elements.flightCarrierCode.value;
             var flightnum = frm.elements.flightNumber.value;
             var date = frm.elements.depDate.value;
  	   var strUrl=strAction+"?fromScreen="+screen+"&assignedto="+str+"&flightCarrierCode="+carrier+"&flightNumber="+flightnum+"&flightDate="+date;
             submitAction(frm,strUrl);
          }else{
             var carrier = frm.elements.carrierCode.value;
  	   var destn = frm.elements.destination.value;
  	   var strUrl=strAction+"?fromScreen="+screen+"&assignedto=DESTINATION&carrier="+carrier+"&destn="+destn;
             submitAction(frm,strUrl);
          }
}




function closeMailAcceptance(){

     frm=targetFormName;

	    var str = "";
		var radiobuttons = document.getElementsByName("assignToFlight");
		for(var i=0; i<radiobuttons.length; i++){
		   if(radiobuttons[i].checked == true)
		   {
			 str = radiobuttons[i].value;
			 break;
		   }
		}

   if(frm.elements.warningFlag.value == "Y"){
         //showDialog("Unsaved data exists.Do you wish to continue?", 4, self, frm, 'id_3');
		// showDialog('<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.unsave" />', 4, self, targetFormName, 'id_3');
	//	showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.unsave" />', 4, self, targetFormName,'id_3');
		 showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.unsave" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_3',
						onClose:function(){
						screenConfirmDialog(frm,'id_3');
						screenNonConfirmDialog(frm,'id_3'); 
						}
						});
		
         
   }else if(frm.elements.fromScreen.value == 'ASSIGNCONTAINER'){
    	var screen = "FROMMAILACCEPTANCE";
		var strAction="mailtracking.defaults.assigncontainer.listAssignContainerFromAccept.do";
		var strUrl = "";
	   if(str == "FLIGHT"){
		  var carrier = frm.elements.flightCarrierCode.value;
		  var flightnum = frm.elements.flightNumber.value;
		  var date = frm.elements.depDate.value;
		  strUrl=strAction+"?fromScreen="+screen+"&assignedto="+str+"&flightCarrierCode="+carrier+"&flightNumber="+flightnum+"&flightDate="+date;

	   }else{
		  var carrier = frm.elements.carrierCode.value;
		  var destn = frm.elements.destination.value;
		  var strUrl=strAction+"?fromScreen="+screen+"&assignedto=DESTINATION&carrier="+carrier+"&destn="+destn;

	   }
		submitForm(frm,strUrl);
    }else if(frm.elements.fromScreen.value == 'carditEnquiry'){
		var fromScreen = "FROMMAILACCEPTANCE";
		frm.elements.fromScreen.value ="";
		var carCode = "";
		var fltNumber = "";
	 	var fromScreenForCardit = frm.elements.displayPageForCardit.value;
		var strAction="mailtracking.defaults.mailsearch.listload.do";
		//alert('fromScreenForCardit==>>'+fromScreenForCardit);
		var strUrl=strAction+"?invokingScreen="+fromScreen+"&displayPage="+fromScreenForCardit+"&carrierCode="+carCode+"&flightNumber="+fltNumber;
		submitForm(frm,strUrl);
	}
    else{
		 location.href = appPath + "/home.jsp";
    }

}

function reopenFlight(){
    frm=targetFormName;
    frm.elements.disableSaveFlag.value = "";
    submitAction(frm,'/mailtracking.defaults.mailacceptance.reopenflight.do');
  //recreateTreeTableDetails("mailtracking.defaults.mailacceptance.reopenflight.do","div2","chkListFlow");
}

function closeFlight(){
    frm=targetFormName;
    frm.elements.uldsSelectedFlag.value="";
    frm.elements.disableSaveFlag.value = "Y";
    submitAction(frm,'/mailtracking.defaults.mailacceptance.closeflight.do');
  //recreateTreeTableDetails("mailtracking.defaults.mailacceptance.closeflight.do","div2","chkListFlow");

}

function addMails(){

   frm=targetFormName;
   if(frm.elements.flightCarrierCode.disabled){
        recreateListDetails('mailtracking.defaults.mailacceptance.checkcloseflight.do','acceptanceTable','add');
   }
}

function deleteMails(){
   frm=targetFormName;

   var str = "";
   var radiobuttons = document.getElementsByName("assignToFlight");
   for(var i=0; i<radiobuttons.length; i++){
       if(radiobuttons[i].checked == true)
       {
   	  str = radiobuttons[i].value;
   	  break;
       }
   }

  if(frm.flightCarrierCode.disabled){
   var chkbox =document.getElementsByName("selectMail");
   if(validateSelectedCheckBoxes(frm,'selectMail','1000000000','1')){

            var selectMail = "";
            var cnt1 = 0;
            for(var i=0; i<chkbox.length;i++){
     	  if(chkbox[i].checked) {
     	     if(cnt1 == 0){
     		  selectMail = chkbox[i].value;
     		  cnt1 = 1;
     	    }else{
     		  selectMail = selectMail + "," + chkbox[i].value;
     	    }
     	 }
          }
          var strAction="mailtracking.defaults.mailacceptance.deletecontainer.do";
          var strUrl=strAction+"?selectMail="+selectMail;
          //openPopUp(strUrl,875,505);
       recreateListDetails(strUrl,'acceptanceTable','delete');
   }
   }
}

function selectDiv() {
	var radiobuttons = document.getElementsByName("assignToFlight");
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

		var divValues = ['FLIGHT','CARRIER'];
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

		if(divID == "CARRIER"){

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


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearMultipleTable(){
      document.getElementById(_currDivId).innerHTML="";
}

function recreateTreeTableDetails(strAction,divId){

	var __extraFn="updateTreeTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	_currDivId = divId;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}

function chkListFlow(_tableInfo){

	document.getElementById("tmpSpan").innerHTML=_tableInfo;
	_innerFrm=document.getElementById("tmpSpan").getElementsByTagName("form")[0];


	var _initialFocus = _innerFrm.initialFocus.value;
 	targetFormName.elements.initialFocus.value = _initialFocus;

 	var _duplicateFlightStatus = _innerFrm.duplicateFlightStatus.value;
 	targetFormName.elements.duplicateFlightStatus.value = _duplicateFlightStatus;

 	var _disableDestnFlag = _innerFrm.disableDestnFlag.value;
 	targetFormName.elements.disableDestnFlag.value = _disableDestnFlag;

 	var _disableSaveFlag = _innerFrm.disableSaveFlag.value;
 	targetFormName.elements.disableSaveFlag.value = _disableSaveFlag;

	var _uldsSelectedFlag = _innerFrm.uldsSelectedFlag.value;
 	targetFormName.elements.uldsSelectedFlag.value = _uldsSelectedFlag;

 	var _preAdviceFlag = _innerFrm.preAdviceFlag.value;
 	targetFormName.elements.preAdviceFlag.value = _preAdviceFlag;

 	var _fromScreen = _innerFrm.fromScreen.value;
 	targetFormName.elements.fromScreen.value = _fromScreen;

	var _closeflight = _innerFrm.closeflight.value;
	targetFormName.elements.closeflight.value = _closeflight;

	var _closeFlag = _innerFrm.closeFlag.value;
	targetFormName.elements.closeFlag.value = _closeFlag;

	var _uldsPopupCloseFlag = _innerFrm.uldsPopupCloseFlag.value;
 	targetFormName.elements.uldsPopupCloseFlag.value = _uldsPopupCloseFlag;

        preWarningMessages();


	onScreenLoad();


	updateTreeTableCode(_tableInfo);



	if(_asyncErrorsExist) return;

}


function updateTreeTableCode(_tableInfo){

	if(_currDivId != ""){
		_strAltDet=getActualData(_tableInfo, "AltDet");
		document.getElementById(_currDivId).innerHTML=_strAltDet;
	}


	cleanupMultipleTmpTable();

	reapplyEvents();

	applyScrollTableStyles();

}

function getActualData(_tableInfo, _identifier){

document.getElementById("tmpSpan").innerHTML=_tableInfo;
var _tmpSpanId="";
	if(_identifier=='AltDet'){
		_tmpSpanId = document.getElementById("_mailAcceptance");
	}

	return _tmpSpanId.innerHTML;

}

function cleanupMultipleTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
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
		  frm.elements.warningFlag.value="";
		  gotoassignContainer();
	    }
		if(dialogId == 'id_2'){
		var chkbox =document.getElementsByName("selectMail");
		var selectMail = "";
        var cnt1 = 0;
        for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkbox[i].value;
     			  cnt1 = 1;
     	  	  }else{
     			  selectMail = selectMail + "," + chkbox[i].value;
     	  	  }
     	 	}
        }
		var strAction="/mailtracking.defaults.mailacceptance.deletemailacceptance.do";
	    var strUrl=strAction+"?selectMail="+selectMail;
	    submitAction(frm,strUrl);
	    }
    if(dialogId == 'id_3'){
	  frm.elements.warningFlag.value="";
	    var str = "";
		var radiobuttons = document.getElementsByName("assignToFlight");
		for(var i=0; i<radiobuttons.length; i++){
		   if(radiobuttons[i].checked == true)
		   {
			 str = radiobuttons[i].value;
			 break;
		   }
		}
	  if(frm.elements.fromScreen.value == 'ASSIGNCONTAINER'){
		var screen = "FROMMAILACCEPTANCE";
			var strAction="mailtracking.defaults.assigncontainer.listAssignContainerFromAccept.do";
			var strUrl = "";
		   if(str == "FLIGHT"){
			  var carrier = frm.elements.flightCarrierCode.value;
			  var flightnum = frm.elements.flightNumber.value;
			  var date = frm.elements.depDate.value;
			  strUrl=strAction+"?fromScreen="+screen+"&assignedto="+str+"&flightCarrierCode="+carrier+"&flightNumber="+flightnum+"&flightDate="+date;

		   }else{
			  var carrier = frm.elements.carrierCode.value;
			  var destn = frm.elements.destination.value;
			  var strUrl=strAction+"?fromScreen="+screen+"&assignedto=DESTINATION&carrier="+carrier+"&destn="+destn;

		   }
			submitForm(frm,strUrl);
	    }else if(frm.elements.fromScreen.value == 'carditEnquiry'){
			var fromScreen = "FROMMAILACCEPTANCE";
			frm.elements.fromScreen.value ="";
			var carCode = "";
			var fltNumber = "";
			var fromScreenForCardit = frm.elements.displayPageForCardit.value;
			var strAction="mailtracking.defaults.mailsearch.listload.do";
			//alert('fromScreenForCardit==>>'+fromScreenForCardit);
			var strUrl=strAction+"?invokingScreen="+fromScreen+"&displayPage="+fromScreenForCardit+"&carrierCode="+carCode+"&flightNumber="+fltNumber;
			submitForm(frm,strUrl);
		}
	    else{
			 location.href = appPath + "/home.jsp";
	    }
    }      
	if(dialogId == 'id_4'){
		  frm.elements.saveSuccessFlag.value="N";
		  listMailAcceptance();
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
		if(dialogId == 'id_3'){

		}
		if(dialogId == 'id_4'){
		frm.elements.saveSuccessFlag.value="N";
		  clearMailAcceptance();
		}
	}
}

 ////////////////// FOR ASYNC SUBMIT - AJAX - LIST///////////////////////////////////////////////

  var _tableDivId = "";
  var act="";
	

  function recreateListDetails(strAction,divId1,action){
  	var __extraFn="updateListCode";
  	_tableDivId = divId1;
	act=action;
  	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
  }

  function updateListCode(_tableInfo){

	  try{

    _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
  	targetFormName.elements.initialFocus.value = _innerFrm.initialFocus.value;
    targetFormName.elements.duplicateFlightStatus.value = _innerFrm.duplicateFlightStatus.value;
  	targetFormName.elements.disableDestnFlag.value = _innerFrm.disableDestnFlag.value;
  	targetFormName.elements.disableSaveFlag.value = _innerFrm.disableSaveFlag.value;
  	targetFormName.elements.uldsSelectedFlag.value = _innerFrm.uldsSelectedFlag.value;
  	targetFormName.elements.preAdviceFlag.value = _innerFrm.preAdviceFlag.value;
  	targetFormName.elements.fromScreen.value = _innerFrm.fromScreen.value;
  	targetFormName.elements.closeflight.value = _innerFrm.closeflight.value;
  	targetFormName.elements.closeFlag.value = _innerFrm.closeFlag.value;
  	targetFormName.elements.uldsPopupCloseFlag.value = _innerFrm.uldsPopupCloseFlag.value;
  	targetFormName.elements.operationalStatus.value = _innerFrm.operationalStatus.value;
  	targetFormName.elements.preassignFlag.value = _innerFrm.preassignFlag.value;
  	targetFormName.elements.warningFlag.value = _innerFrm.warningFlag.value;
  	targetFormName.elements.reassignScreenFlag.value = _innerFrm.reassignScreenFlag.value;
  	targetFormName.elements.selCont.value = _innerFrm.selCont.value;
  	targetFormName.elements.captureULDDamageFlag.value = _innerFrm.captureULDDamageFlag.value;

    _acceptance=_tableInfo.document.getElementById("_acceptance");
 	document.getElementById(_tableDivId).innerHTML=_acceptance.innerHTML;

 	preWarningMessages();
 	onScreenLoad();

	  }catch(error){
		 asyncRetrycount++;
		  if('add'==act && asyncRetrycount<2){	
			  addMails();
		  }
	  }

  }

 ////////////////////////////////////////////////////////////////////////////////////////


//ADDED BY A-3251 SREEJITH P.C. FOR CAPTURE ULD DAMAGE BUTTON
function CaptureDamage(){
 var frm=targetFormName;
var uldnos= document.getElementsByName("uldnos");
var chkbox =document.getElementsByName("selectMail");
var selectMail = "";
var uid=0;
//To get the selected ulds and to validate if none is checked or more than one [arg 3 determines how many can be selected at a time]
   if(validateSelectedCheckBoxes(frm,'selectMail','1','1'))
   {
            for(var i=0; i<chkbox.length;i++)
       	    {
	     	  	if(chkbox[i].checked){
	     		  selectMail = chkbox[i].value;
	     		 }
      		}

     	 var str = selectMail.substring(2,3);
     	 var con = uldnos[str-1].value;
     	 var typ= con.split("-")[0];
     	 var uld= con.split("-")[1];
    	if(typ=="B")
    	{
    	showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.bulknotallowed" />',1,self);
		showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.flightclosed" scope="request"/>',type:1,parentWindow:self});
		return;
    	}
    	else
    	{

		var str = "FromScreenMAILACCEPTANCE";
     	url1="uld.defaults.listulddmgdetails.do?uldNumber="+uld+"&pageURL="+str;

		// for flight or carrier group radio button length will be 2 iterate through it find the selected one.
		var radiobuttons = document.getElementsByName("assignToFlight");
		  		var assignto;
		  		for(var i=0; i<radiobuttons.length; i++){
		  	   		if(radiobuttons[i].checked == true)
		  	   		{
		  	     		assignto = radiobuttons[i].value;
		  	     		break;
		  	   		}
  		}

  		if(assignto=="FLIGHT")
  		{
  		url1=url1+"F";
  		}
  		else
  		{
  		url1=url1+"CC";
		}

		if(frm.elements.operationalStatus.value=="OPEN")
		{
		url1=url1+"O";
		}
		else if(frm.elements.operationalStatus.value=="CLOSED")
		{
		url1=url1+"C";
		}
		if(frm.elements.operationalStatus.value=="CLOSED")
		{
		showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.flightclosed" scope="request"/>',type:1,parentWindow:self});
		return;
		}
		else
		{
			submitForm(targetFormName,url1);
		}
	   }
    }
}

function confirmMessage() {
var frm = targetFormName;
 	var warningCode = frm.elements.warningFlag.value;
 	if(warningCode != null){
		if('flight_tba_tbc' == warningCode ){		

        submitAction(frm,'/mailtracking.defaults.mailacceptance.listflight.do?warningOveride=Y&disableButtons=Y&tbaTbcWarningFlag=Y');		
		}else if('list_flight_tba_tbc'== warningCode){		

		 submitAction(frm,'/mailtracking.defaults.mailacceptance.listmailacceptance.do');	
		}
		else if('list_duplicate_tbc_tba'== warningCode){	

		 submitAction(frm,'/mailtracking.defaults.mailacceptance.listflight.do?warningOveride=Y&disableButtons=Y&tbaTbcWarningFlag=Y&duplicateAndTbaTbc=Y');	
		}
		}
}

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
function showSaveDialog(frm){
if(frm.elements.saveSuccessFlag.value=="Y"){
		  showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.msg.alrt.saveSuccessFlag" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_4',
						onClose:function(){
						screenConfirmDialog(frm,'id_4');
						screenNonConfirmDialog(frm,'id_4'); 
						}
						});
		 }
}