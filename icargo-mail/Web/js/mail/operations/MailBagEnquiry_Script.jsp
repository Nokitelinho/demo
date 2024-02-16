<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
    //onScreenloadSetHeight();
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
	//evtHandler.addEvents("btDeleteMail","deleteMail(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btViewHistory","historyDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btReassignMail","validateMail('REASSIGNMAIL')",EVT_CLICK);
	evtHandler.addEvents("btOffloadMail","offloadMail(this.form)",EVT_CLICK);
	evtHandler.addEvents("btViewDamage","viewDamagedMail(this.form)",EVT_CLICK);
	evtHandler.addEvents("btReturnMail","validateMail('RETURNMAIL')",EVT_CLICK);
	evtHandler.addEvents("btnDeliverMail","deliverMail(this.form)",EVT_CLICK);
	evtHandler.addEvents("btTransferMail","validateMail('TRANSFERMAIL')",EVT_CLICK);

	evtHandler.addIDEvents("portLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.port.value,'Airport','1','port','',0)", EVT_CLICK);
	evtHandler.addIDEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOE.value,'OfficeOfExchange','1','originOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("destnOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destnOE.value,'OfficeOfExchange','1','destnOE','',0)", EVT_CLICK);
	evtHandler.addIDEvents("subClassLov","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'SubClass','1','subClass','',0)", EVT_CLICK);

	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.subCheck)",EVT_CLICK);

	if(frm.subCheck != null){
		evtHandler.addEvents("subCheck","toggleTableHeaderCheckbox('subCheck',targetFormName.checkAll)",EVT_CLICK);
	}

   	}
   	evtHandler.addEvents("dsn","dsnPadding()",EVT_BLUR);
	evtHandler.addEvents("rsn","rsnPadding()",EVT_BLUR);
   	applySortOnTable("mailbagenquiry",new Array("None","String","Date","String","String","String","String","String","Date","String","String","String","Number")); //modified by a-8514  as part of ICRD-287277
	
}

function onScreenLoad(frm){



	var mode = frm.elements.status.value;
	if(mode == "ShowHistoryPopup"){
		submitForm(targetFormName,'mailtracking.defaults.mbHistory.list.do');
		//openPopUp("mailtracking.defaults.mbHistory.screenload.do","800","410");
		frm.elements.status.value = "";
	}
	else if(mode == "showOffloadScreen"){

		//submitForm(frm,'mailtracking.defaults.offload.listfromsearch.do?fromScreen=MAILBAGENQUIRY');
		//frm.status.value = "";
  	}
  	else if(mode == "EMPTY_ULD"){

			frm.elements.status.value = "";
			var frmscreen = "MAILBAG_ENQUIRY";
			var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen="+frmscreen;
  			openPopUp(strUrl,"600","280");
	}

  	else if(mode == "ShowDamagePopup"){
		openPopUp("mailtracking.defaults.damagedmailbag.screenload.do","600","300");
		frm.elements.status.value = "";
	}
	else if(mode == "ShowReassignMailPopup"){
		reassignMail(frm);
		frm.elements.status.value = "";
	}
	else if(mode == "ShowReturnMailPopup"){
		returnMail(frm);
		frm.elements.status.value = "";
	}
	else if(mode == "ShowTransferMailPopup"){
		transferMail(frm);
		frm.elements.status.value = "";
	}

	var statusFlag = frm.elements.screenStatusFlag.value;
	var fromScreen = frm.elements.fromScreen.value;

	if(fromScreen=="MAILBAGENQUIRY" ){
	enableDetails();
	}
	else if(statusFlag == "screenload"){

		disableDetails();
		frm.elements.mailbagId.focus();
	}
	 if(statusFlag == "detail"){

		enableDetails();
	}

	if(frm.elements.reList.value == "Y"){
		frm.elements.reList.value = "";
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
		submitForm(frm,'mailtracking.defaults.mailbagenquiry.list.do');
	}
applySortOnTable("mailbagenquiry",new Array("None","String","Date","String","String","String","String","Date","String","String","String","Number"));
}


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px';
	//alert((height*95)/100);
}


function disableDetails(){

	var frm = targetFormName;
	disableField(frm.elements.btViewDamage);
	disableField(frm.elements.btReturnMail);
	//disableField(frm.btViewHistory);
	
	disableField(frm.elements.btOffloadMail);
	disableField(frm.elements.btReassignMail);
	disableField(frm.elements.btTransferMail);
	disableField(frm.elements.checkAll);
	disableField(frm.elements.btnDeliverMail);
}

function enableDetails(){

	var frm = targetFormName;
	enableField(frm.elements.btViewDamage);
	enableField(frm.elements.btReturnMail);
	enableField(frm.elements.btViewHistory);
	enableField(frm.elements.btOffloadMail);
	enableField(frm.elements.btReassignMail);
	enableField(frm.elements.btTransferMail);
	enableField(frm.elements.checkAll);
	enableField(frm.elements.btnDeliverMail);

	
}

function submitPage(lastPg,displayPg){

  var frm=targetFormName;
  enableField(frm.elements.category);
  enableField(frm.elements.currentStatus);
  enableField(frm.elements.damaged);

  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;
   	if(frm.elements.transit.checked){
   		frm.elements.transit.value="Y";
   		if(frm.elements.port.value ==""){
   			//showDialog('<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.dlg.msg.port" />',1,self);
			//showDialog({msg:'<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.dlg.msg.port" scope="request"/>',type:1,parentWindow:self});	
         	//return;
   		}
   	}else{
   		frm.elements.transit.value="N";
   	}
  submitForm(frm,'mailtracking.defaults.mailbagenquiry.list.do');

}

function listDetails(frm){

	enableField(frm.elements.category);
	enableField(frm.elements.currentStatus);
  	enableField(frm.elements.damaged);
	var status=frm.elements.currentStatus.value;
	var port =frm.elements.port.value;

	if(status=="NAR"){
		if(port==""){
		//	showDialog('<bean:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.msg.alrt.emptyport" />',1,self);
			showDialog({msg:'<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.msg.alrt.emptyport" scope="request"/>',type:1,parentWindow:self});	
			 frm.elements.port.focus();
	         return;
		}

	}
	frm.elements.displayPage.value=1;
   	frm.elements.lastPageNum.value=0;
   	if(frm.elements.transit.checked){
   		frm.elements.transit.value="Y";
   		if(frm.elements.port.value ==""){
   			//showDialog('<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.dlg.msg.port" />',1,self);
			//showDialog({msg:'<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.dlg.msg.port" scope="request"/>',type:1,parentWindow:self});	
         	//return;
   		}
   	}else{
   		frm.elements.transit.value="N";
   	}
   	submitForm(frm,'mailtracking.defaults.mailbagenquiry.list.do?countTotalFlag=Y');
}

function clearDetails(frm){

	submitForm(frm,'mailtracking.defaults.mailbagenquiry.clear.do');
}


function deleteMail(frm){
	var chkbox =document.getElementsByName("subCheck");

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
	targetFormName.elements.selCont.value = selectContainer;
	
	showDialog({msg:'<common:message bundle="mailBagEnquiryResources" key="mailtracking.defaults.mailbagenquiry.dlg.msg.deletemailbag" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});

}


function closeDetails(frm){

	frm.status.value = "";

//	frm.subCheck.value = "";

	var frmscreen = frm.elements.fromScreen.value;
	var str = frm.elements.assignToFlight.value;
	var fltno=frm.elements.flightNumber.value;
	var fltcarcod=frm.elements.flightCarrierCode.value;
	var fltdat=frm.elements.flightDate.value;
	var depport=frm.elements.port.value;

	if(frmscreen == "DSNENQUIRY"){
		submitForm(targetFormName,'mailtracking.defaults.dsnenquiry.listdetails.do?status=ListFromMailbagEnquiry');
	}
	else if(frmscreen == "MAILEXPORTLIST"){
		submitForm(targetFormName,'mailtracking.defaults.mailexportlist.listflight.do?assignToFlight='+str+'&flightNo='+fltno+'&flightCarrierCode='+fltcarcod+'&carrierCode='+fltcarcod+'&depDate='+fltdat+'&departurePort='+depport);
	}

	else if(frmscreen == 'ASSIGNCONTAINER'){
		var screen = "MAILBAGENQUIRY";
		var strAction="mailtracking.defaults.assigncontainer.listAssignContainerFromAccept.do";
		var strUrl = "";
		var port = frm.elements.port.value;

	   if(str == "FLIGHT"){

		  var carrier = frm.elements.flightCarrierCode.value;
		  var flightnum = frm.elements.flightNumber.value;
		  var date = frm.elements.flightDate.value;

		  strUrl=strAction+"?fromScreen="+screen+"&assignedto="+str+"&departurePort="+port+"&flightCarrierCode="+carrier+"&flightNumber="+flightnum+"&flightDate="+date;

	   }else{
		  var carrier = frm.elements.flightCarrierCode.value;
		  var destn = frm.elements.destination.value;
		  strUrl=strAction+"?fromScreen="+screen+"&assignedto="+str+"&departurePort="+port+"&carrier="+carrier+"&destn="+destn;
           }

		submitForm(frm,strUrl);
    }
    else if(frmscreen == 'INVENTORYLIST'){
		var carrierCode = frm.elements.carrierInv.value;
		submitForm(targetFormName,"mailtracking.defaults.inventorylist.mailbagenquirycloselist.do?carrierCode="+carrierCode);
    }
	else if(frmscreen == "LISTCONTAINER"){
	    var screen = "LSTMALBAG";
		submitForm(targetFormName,"mailtracking.defaults.searchcontainer.listsearchcontainer.do?fromScreen="+screen);
    }
	else{
		location.href = appPath + "/home.jsp";
	}
}

function historyDetails(frm){
//if(validateSelectedCheckBoxes(frm,'subCheck',1,1)){
	/*if(frm.elements.subCheck!=null){
	var length =frm.elements.subCheck.length;
	var i=0;
	var isSelected = false;
	for(i=0; i<length; i++){
		if(frm.elements.subCheck[i].checked==true){
			isSelected = true;
			break;
		}
	}*/
	//if(isSelected == true){
		submitForm(targetFormName,'mailtracking.defaults.mailbagenquiry.showhistory.do');
		//recreateListDetails('mailtracking.defaults.mailbagenquiry.showhistory.do','mailBagEnquiryTable');
	//		}
		/*}else{
		if(length!=null && length>0){
			submitForm(frm,'mailtracking.defaults.mailbagenquiry.showhistory.do');
		}else{
		//recreateListDetails('mailtracking.defaults.mailbagenquiry.showhistory.do','mailBagEnquiryTable');
			}
		}
	}else{
		submitForm(frm,'mailtracking.defaults.mailbagenquiry.showhistory.do');*/
		//}
	//}
}

function viewDamagedMail(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck',1,1)){
		//submitForm(targetFormName,'mailtracking.defaults.mailbagenquiry.showdamage.do');
		recreateListDetails('mailtracking.defaults.mailbagenquiry.showdamage.do','mailBagEnquiryTable');
	}
}


function reassignMail(frm){

	  var chkbox =document.getElementsByName("subCheck");

	  var selectContainer = "";
	  var str = "MAILBAG_ENQUIRY";
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
	
	 var strAction="mailtracking.defaults.reassignmail.screenloadreassignmail.do";
	 var strUrl=strAction+"?container="+selectContainer+"&fromScreen="+str;
	 openPopUp(strUrl,600,470);
}

function transferMail(frm){

	  var chkbox =document.getElementsByName("subCheck");

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
	 var hideMode="FLIGHT";
	 var strAction="mailtracking.defaults.transfermail.screenload.do";
	 var strUrl=strAction+"?mailbag="+selectContainer+"&hideRadio="+hideMode+"&fromScreen=MAILBAG_ENQUIRY";
	 openPopUp(strUrl,600,355);
}
//a-8061 added for ICRD-197319 
function deliverMail(frm){

if(validateSelectedCheckBoxes(frm,'subCheck','',1)){
		submitForm(frm,'mail.opearations.mailbagenquiry.delivermail.do');
	}	
}

function offloadMail(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck','','1')){
		submitForm(frm,'mailtracking.defaults.mailbagenquiry.offloadmail.do');
	}
}

function returnMail(frm){

	  var chkbox =document.getElementsByName("subCheck");

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
	 var strAction="mailtracking.defaults.returnmail.screenload.do";
	 var strUrl=strAction+"?selectedContainers="+selectContainer+"&fromScreen=MAILBAG_ENQUIRY";
	 openPopUp(strUrl,600,295);

}

function validateMail(str){
	if(validateSelectedCheckBoxes(targetFormName,'subCheck','','1')){

		var strURL='mailtracking.defaults.mailbagenquiry.validatemail.do?status='+str;
		//submitForm(targetFormName,'mailtracking.defaults.mailbagenquiry.validatemail.do?status='+str);
		recreateListDetails(strURL,'mailBagEnquiryTable');
	}
}

function dsnPadding(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsn");
 var mailDSN =document.getElementsByName("dsn");
   for(var i=0;i<mailDSNArr.length;i++){
      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }
   }

}


function rsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsn");
 var mailRSN =document.getElementsByName("rsn");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
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
 		submitForm(frm,"mailtracking.defaults.mailbagenquiry.delete.do");
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
 	}
 }


 ////////////////// FOR ASYNC SUBMIT - AJAX - VIEW HISTORY,VIEW DAMAGE,REASSIGN MAIL,RETURN MAIL,TRANSFER MAIL///////////////////////////////////////////////

   var _tableDivId = "";

   function recreateListDetails(strAction,divId1){

   	var __extraFn="updateListCode";
   	_tableDivId = divId1;
   	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

   }

   function updateListCode(_tableInfo){

  	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
  	targetFormName.elements.screenStatusFlag.value = _innerFrm.screenStatusFlag.value;
   	targetFormName.elements.lastPageNum.value = _innerFrm.lastPageNum.value;
   	targetFormName.elements.displayPage.value = _innerFrm.displayPage.value;
   	targetFormName.elements.status.value = _innerFrm.status.value;
   	targetFormName.elements.fromScreen.value = _innerFrm.fromScreen.value;
   	targetFormName.elements.assignToFlight.value = _innerFrm.assignToFlight.value;
   	targetFormName.elements.destination.value = _innerFrm.destination.value;
   	targetFormName.elements.destinationCity.value = _innerFrm.destinationCity.value;
   	targetFormName.elements.carrierInv.value = _innerFrm.carrierInv.value;
   	targetFormName.elements.selCont.value = _innerFrm.selCont.value;
   	targetFormName.elements.reList.value = _innerFrm.reList.value;
   	targetFormName.elements.successMailFlag.value = _innerFrm.successMailFlag.value;
   	targetFormName.elements.currentDialogId.value = _innerFrm.currentDialogId.value;
   	targetFormName.elements.currentDialogOption.value = _innerFrm.currentDialogOption.value;

        _mailBagEnquiryTable=_tableInfo.document.getElementById("_mailBagEnquiryTable");
  	document.getElementById(_tableDivId).innerHTML=_mailBagEnquiryTable.innerHTML;

  	onScreenLoad(targetFormName);

   }

   //Added by A-7929 for ICRD - 224586  starts here
function callbackMailBag(collapse,collapseFilterOrginalHeight,mainContainerHeight){   
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);
               
}
//Added by A-7929 for ICRD - 224586  ends here

 ////////////////////////////////////////////////////////////////////////////////////////
