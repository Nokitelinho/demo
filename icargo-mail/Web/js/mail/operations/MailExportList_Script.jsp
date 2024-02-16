<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenloadSetHeight();
  
   onScreenLoad();
   initializePanels();
   with(frm){
     //CLICK Events
     	evtHandler.addEvents("btnList","listMailExportList()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearMailExportList()",EVT_CLICK);
        evtHandler.addEvents("btnReassignContainer","reassignContainer()",EVT_CLICK);
        evtHandler.addEvents("btnReassignDSN","reassignDSN()",EVT_CLICK);
        evtHandler.addEvents("btnReassignMailbag","reassignMailbag()",EVT_CLICK);
        evtHandler.addEvents("btnViewMailbag","viewMailbag()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);;
		evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
        if(frm.selectMail != null){
	    	evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',targetFormName.masterContainer)",EVT_CLICK);
		}
		evtHandler.addEvents("btnTransfer","transferContainer()",EVT_CLICK);//added by A-7371 for ICRD-133987

   	}
	applySortOnTable("mailExportListTable",new Array("None","String","String","String","String","Number","Number","String","String","String"),null,"tree",false); 
}

function resetFocus(frm){
	 if(!event.shiftKey){ 
		  var radiobuttons = document.getElementsByName("assignToFlight");
		  var focusStatus = "No";
		  for(var i=0; i<radiobuttons.length; i++){
		    if(radiobuttons[i].checked == true && !frm.elements.assignToFlight[i].disabled){
						frm.elements.assignToFlight[i].focus();
						focusStatus = "yes";
			  }
			/*if(radiobuttons[i].checked == true)
			{
					if((radiobuttons[i].value=="FLIGHT") && (!frm.flightCarrierCode.disabled)
						&& (!frm.flightCarrierCode.readOnly)){						
						frm.flightCarrierCode.focus();
						focusStatus = "Yes";
					}
					else if((radiobuttons[i].value=="CARRIER") && (!frm.carrierCode.disabled)
						&& (!frm.carrierCode.readOnly)){						
						frm.carrierCode.focus();
						focusStatus = "Yes";
					}
			}*/
		 }
		if((focusStatus == "No") && (!frm.elements.masterContainer.disabled)){
			frm.elements.masterContainer.focus();
		}	
	}	
}

function preWarningMessages(){
   collapseAllRows();
   selectDiv();

}


function onScreenLoad(){	
  frm=targetFormName; 
  if(frm.elements.carrierCode.disabled)
  {
   var carlov = document.getElementById("carLov");
   var desLov = document.getElementById("desLov");
  disableField(carlov);
  disableField(desLov);
  }    
  if(targetFormName.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","640","280");
  } 
  if(targetFormName.elements.initialFocus.value == "Y"){
      frm.elements.initialFocus.value = "";
      frm.elements.initialFocus.defaultValue = "";
      var radiobuttons = document.getElementsByName("assignToFlight");
	  for(var i=0; i<radiobuttons.length; i++){
		 if(radiobuttons[i].checked == true){
						frm.elements.assignToFlight[i].focus();
			  }
		/*if(radiobuttons[i].checked == true)
  	 	{
     			if(radiobuttons[i].value=="FLIGHT"){
     				frm.assignedto.value="FLIGHT";
					if((!frm.flightCarrierCode.disabled) && (!frm.flightCarrierCode.readOnly)){
     				frm.flightCarrierCode.focus();
					}
     			}
     			else if(radiobuttons[i].value=="CARRIER"){
     				frm.assignedto.value="CARRIER";
					if((!frm.carrierCode.disabled) && (!frm.carrierCode.readOnly)){
     				frm.carrierCode.focus();
     			}
   		}
  	 }*/
  	 }	
  }
  else if((!frm.elements.masterContainer.disabled)){	
			frm.elements.masterContainer.focus();	
  }
  var frmScreen="MAILEXPORTLIST";
  var reassignMode = "FLIGHT"; 
  var assigned=frm.elements.assignedto.value;  
  if(frm.elements.status.value == "showReassignScreen"){
  		frm.elements.status.value="NONE";
  		var frmFlightNumber = frm.elements.flightNumber.value;
  		var flightCarrierCode = frm.elements.flightCarrierCode.value;
  		var flightCarrierCode = frm.elements.carrierCode.value;
  		var dep = frm.elements.depDate.value;
  		var dest = frm.elements.destination.value;
		var action = "mailtracking.defaults.reassigncontainer.screenload.do";
		var strURL = action+"?assignedto="+assigned+"&reassignedto="+reassignMode+"&fromScreen="+frmScreen+"&frmFlightDate="+dep+"&fromFlightCarrierCode="+flightCarrierCode+"&fromFlightNumber="+frmFlightNumber+"&fromdestination="+dest;
       openPopUp(strURL,"600","417");
  }
  if(frm.elements.status.value == "showViewMailEnquiry"){
  		frm.elements.status.value="NONE";	
		submitForm(frm,'mailtracking.defaults.mailbagenquiry.listfromdsn.do?fromScreen='+frmScreen);
  }
  if(frm.elements.status.value == "showReassignDSNScreen"){
  		frm.elements.status.value="NONE";	
  		var selectDSN=frm.elements.selDSN.value;
  		var frmFlightNumber = frm.elements.flightNumber.value;
  		var flightCarrierCode = frm.elements.flightCarrierCode.value;
  		var flightCarrierCode = frm.elements.carrierCode.value;
  		var dep = frm.elements.depDate.value;
		var dest = frm.elements.destination.value;
		
		var strAction="mailtracking.defaults.reassigndsn.screenloadreassigndsn.do";
		var strUrl=strAction+"?seldsn="+selectDSN;
		strUrl = strUrl+"&frmassignTo="+assigned+"&reassignedto="+reassignMode+"&fromScreen="+frmScreen+"&frmFlightDate="+dep+"&fromFlightCarrierCode="+flightCarrierCode+"&fromFlightNumber="+frmFlightNumber+"&fromdestination="+dest;
	    openPopUp(strUrl,1155,425);
  }
  if(frm.elements.status.value == "showReassignMailbag"){
  		frm.elements.status.value="NONE";	
  		var reDSN=frm.elements.reCON.value+"-"+frm.elements.reDSN.value;  		
		var strAction="mailtracking.defaults.reassignmailbag.screenloadreassignmailbag.do";	
		
  		var frmFlightNumber = frm.elements.flightNumber.value;
  		var flightCarrierCode = frm.elements.flightCarrierCode.value;
  		var flightCarrierCode = frm.elements.carrierCode.value;
  		var dep = frm.elements.depDate.value;
		var dest = frm.elements.destination.value;
		var strUrl=strAction+"?reDSN="+reDSN;
		strUrl = strUrl+"&frmassignTo="+assigned+"&reassignedto="+reassignMode+"&fromScreen="+frmScreen+"&frmFlightDate="+dep+"&fromFlightCarrierCode="+flightCarrierCode+"&fromFlightNumber="+frmFlightNumber+"&fromdestination="+dest;

	    //alert("URL"+strUrl);
	    openPopUp(strUrl,1255,500);

  }
  if(frm.elements.status.value == "SHOW_EMPTY_ULD_POPUP"){
  			frm.elements.status.value="NONE";
   			var fltno=frm.elements.flightNumber.value;
			var fltcarcod=frm.elements.flightCarrierCode.value;
			var fltcarcod=frm.elements.carrierCode.value;					
			var frmfltdat = frm.elements.depDate.value;  
    		var frmdest = frm.elements.destination.value; 
  			var strUrl = "mailtracking.defaults.emptyulds.screenload.do?frmassignTo="+assigned+"&fromFlightNumber="+fltno+"&fromFlightCarrierCode="+fltcarcod+"&frmFlightDate="+frmfltdat+"&fromScreen="+frmScreen+"&fromdestination"+frmdest;
	  		openPopUp(strUrl,"600","280");  	
	  			  			
   }
	if(frm.elements.disableButtonsForTBA.value == "Y"){  
		disableField(frm.elements.btnViewMailbag);		
   } 
   if(frm.elements.disableButtonsForAirport.value == "Y"){
		disableField(frm.elements.btnReassignContainer);	
		disableField(frm.elements.btnReassignDSN);	
		disableField(frm.elements.btnReassignMailbag);	
		disableField(frm.elements.btnViewMailbag);	
		disableField(frm.elements.btnTransfer);	

   }
   // added by A-7371 for ICRD-133987
  if(frm.elements.transferContainerFlag.value == "showTransferContainerScreen"){
	frm.elements.transferContainerFlag.value= "";
	var reassignMode="DESTINATION";
	var str = "mailtracking.defaults.transfercontainer.screenload.do?reassignedto="+reassignMode+"&fromScreen=MAIL_EXPORT_LIST";
	openPopUp(str,"600","398");
  }
   if(frm.elements.transferContainerFlag.value == "showTransferMailScreen"){
	frm.elements.transferContainerFlag.value= "";
	var hideFlight = "FLIGHT";//field value for variable 'hideFlight' to get the carrier selected by default
	var reassignMode="DESTINATION";
	
	var chkbox =document.getElementsByName("selectDSN");
	  var selectContainer = "";
	  var cnt1 = 0;
	  for(var i=0; i<chkbox.length;i++){
		 if(chkbox[i].checked) {
			 if(cnt1 == 0){
				selectContainer = chkbox[i].value;
				cnt1 = 1;
			 }else{
				selectContainer = selectContainer + "," + chkbox[i].value;
			 }
		}
	 }
	
	var str = "mailtracking.defaults.transfermail.screenload.do?reassignedto="+reassignMode+"&mailbag="+selectContainer+"&fromScreen=MAIL_EXPORT_LIST";
	openPopUp(str,"600","398");
   }
   
}


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	
	//document.getElementById('pageDiv').style.height = ((height*98)/100)+'px';
	
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

function listMailExportList(){
   frm=targetFormName;
   targetFormName.elements.warningOveride.value = "";
   submitAction(frm,'/mailtracking.defaults.mailexportlist.listflight.do');
}

function clearMailExportList(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.mailexportlist.clearmailexportlist.do');

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
   if(validateSelectedCheckBoxes(frm,'selectMail','1','1')){

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
            //alert(selectMail);
       targetFormName.elements.selCont.value =  selectMail;    
       
		submitAction(frm,'/mailtracking.defaults.mailexportlist.reassigncontainer.do');
   }
}
function reassignDSN(){	
	frm=targetFormName;
	
   var chkbox =document.getElementsByName("selectDSN");
  	 
   if(validateSelectedCheckBoxes(frm,'selectDSN','1','1')){

   	var selectDSN = "";
            var cnt1 = 0;
            for(var i=0; i<chkbox.length;i++){
	     	  	if(chkbox[i].checked) {
	     	  	   if(cnt1 == 0){
	     			  selectDSN = chkbox[i].value;
	     			  cnt1 = 1;
	     	  	   }else{
	     			  selectDSN = selectDSN + "," + chkbox[i].value;
		     	  	}
    			}
		   }
		
       var str= selectDSN.split("~")[0];
     	var redsn= selectDSN.split("~")[1];
		var seldsn=selectDSN.split("~")[2];
		var con = str.substring(2,4);    	
        targetFormName.elements.reCON.value =  str; 
        targetFormName.elements.reDSN.value =  redsn;
	    targetFormName.elements.selDSN.value =  seldsn;
       	targetFormName.elements.status.value = "reassignDSN";
		submitForm(frm,'mailtracking.defaults.mailexportlist.validate.do');			
    }	
}

function reassignMailbag(){

frm=targetFormName;	
	var chkbox =document.getElementsByName("selectDSN");
	
	if(validateSelectedCheckBoxes(frm,'selectDSN','1','1')){	
		var selectDSN = "";
        var cnt1 = 0;
        for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectDSN = chkbox[i].value;
     			  cnt1 = 1;
     	  	   }else{
     			  selectDSN = selectDSN + "," + chkbox[i].value;
	     	  	}
			}
	    }	
	   
 		var str= selectDSN.split("~")[0];
     	var redsn= selectDSN.split("~")[1];
		var seldsn=selectDSN.split("~")[2];
		var con = str.substring(2,4);    
        targetFormName.elements.reCON.value =  str; 
        targetFormName.elements.reDSN.value =  redsn;
	    targetFormName.elements.selDSN.value =  seldsn;
        targetFormName.elements.status.value = "reassignMailbag";
	  submitForm(frm,'mailtracking.defaults.mailexportlist.validate.do');
	}
	
}
	
//added by A-7371 for ICRD-133987
function transferContainer(){   //modified by A-8149 for ICRD-262884

var frm=targetFormName;
var chkboxCont =document.getElementsByName("selectMail");
var chkboxMail =document.getElementsByName("selectDSN");
var uldType = eval(frm.elements.uldType);

	var transferContainerFlag = "N";
	for(var i=0; i<chkboxMail.length;i++){
		if(chkboxMail[i].checked) {
			//check=validateSelectedCheckBoxes(frm, 'selectDSN', 20000, 1);
			//if(check){
		transferContainerFlag = "Y";
				for(var j=0; j<chkboxCont.length;j++){
					if(chkboxCont[j].checked) {
					transferContainerFlag = "N";
						showDialog({msg:'<bean:message bundle="mailexportListResources" key="mailtracking.defaults.mailexportlist.msg.warn.selectDSN" scope="request"/>',type:1,parentWindow:self});
						return;
					}
			//	}
				
			}
		}
	}
	
   if(transferContainerFlag=="N" && validateSelectedCheckBoxes(frm,'selectMail','20000000000','1'))
   {
   	var selectMail = "";
            var cnt1 = 0;
            for(var i=0; i<chkboxCont.length;i++)
       	    {
			if(chkboxCont[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkboxCont[i].value;
				  if(uldType[i]==null){  //added by A-8149 for ICRD-270524
						var type=uldType.value;
						}else{
						var type=uldType[i].value;
						}
						if(type=="B"){
						showDialog({msg:'<bean:message bundle="mailexportListResources" key="mailtracking.defaults.mailexportlist.msg.warn.bulkcontainer" scope="request"/>',type:1,parentWindow:self});
						return;
					}
     			  cnt1 = 1;
     	  	    }else{
     			  selectMail = selectMail + "," + chkboxCont[i].value;
     	  	     }
     	 	 }
      		}
			targetFormName.elements.selectedContainer.value = selectMail;
			var strAction='/mailtracking.defaults.mailexportlist.transfercontainer.do';
		//recreateListDetails(strAction,'acceptanceTable');
     submitAction(frm,strAction);
	}else if(validateSelectedCheckBoxes(frm,'selectDSN','20000000000','1')){
	
	var selectMail = "";
            var cnt1 = 0;
            for(var i=0; i<chkboxMail.length;i++)
       	    {
			if(chkboxMail[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectMail = chkboxMail[i].value;
     			  cnt1 = 1;
     	  	    }else{
     			  selectMail = selectMail + "," + chkboxMail[i].value;
     	  	     }
     	 	 }
      		}
			targetFormName.elements.selectedContainer.value = selectMail;
	var strAction='/mailtracking.defaults.mailexportlist.transfermail.do';
		//recreateListDetails(strAction,'acceptanceTable');
     submitAction(frm,strAction);

	
	}
		
}


function validateContCheckBox(childCheckBoxName){
	var rows=document.getElementsByName(childCheckBoxName);
	var flag = false; 
	for(var i=0;i<rows.length;i++){
		if(rows[i].checked==true){
			flag=true;
		}
	}	
 if(flag==false){
	//showDialog("<bean:message bundle='mailexportListResources' key='mailtracking.defaults.mailexportlist.info.selectDsn' />", 1, self);
	showDialog({msg:'<bean:message bundle="mailexportListResources" key="mailtracking.defaults.mailexportlist.info.selectDsn" /> ',
					type:1,
					parentWindow:self,                                       
									
			});
	return false;
	}else
	return true;
}
function viewMailbag(){
	frm=targetFormName;	
	var chkbox =document.getElementsByName("selectDSN");
	if(validateContCheckBox('selectDSN')){	
	if(validateSelectedCheckBoxes(frm,'selectDSN','1','1')){	
		var selectDSN = "";
        var cnt1 = 0;
        for(var i=0; i<chkbox.length;i++){
     	  	if(chkbox[i].checked) {
     	  	   if(cnt1 == 0){
     			  selectDSN = chkbox[i].value;
     			  cnt1 = 1;
     	  	   }else{
     			  selectDSN = selectDSN + "," + chkbox[i].value;
	     	  	}
			}
	    }	
	   var str= selectDSN.split("~")[0];
     	var redsn= selectDSN.split("~")[1];
		var seldsn=selectDSN.split("~")[2];
		var con = str.substring(2,4);    	
        targetFormName.elements.reCON.value =  str; 
        targetFormName.elements.reDSN.value =  redsn;
	    targetFormName.elements.selDSN.value =  seldsn;
       targetFormName.elements.status.value = "VIEW";
		submitForm(frm,'mailtracking.defaults.mailexportlist.validate.do');
	}
	}
}

function closeScreen(){
	submitForm(targetFormName,'mailtracking.defaults.mailexportlist.close.do');
}

function selectDiv() {	
	frm=targetFormName;
	var radiobuttons = document.getElementsByName("assignToFlight");
	for(var i=0; i<radiobuttons.length; i++){
		if(radiobuttons[i].checked == true)
		{
			frm.elements.assignedto.value=radiobuttons[i].value;
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


/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){
		submitAction(frm,'/mailtracking.defaults.mailacceptance.assigncontainer.do');
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
function confirmMessage() {
	var frm = targetFormName;
	var warningCode = frm.elements.warningFlag.value;
	if(warningCode != null){
		if('flight_tba_tbc' == warningCode ){
			submitAction(targetFormName,'/mailtracking.defaults.mailexportlist.listflight.do?warningOveride=Y&disableButtonsForTBA=Y');
		}
		if('flight_tba_tbc_status' == warningCode ){
			submitAction(targetFormName,'/mailtracking.defaults.mailexportlist.listmailexportlist.do');
		}
			if('list_flight_tba_tbc' == warningCode ){
			submitAction(targetFormName,'/mailtracking.defaults.mailexportlist.listflight.do?warningOveride=Y&disableButtons=Y&tbaTbcWarningFlag=Y&duplicateAndTbaTbc=Y');
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