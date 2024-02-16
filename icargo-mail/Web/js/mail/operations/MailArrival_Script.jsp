<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   //onScreenloadSetHeight();
   onScreenLoad();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnList","listMailArrival()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearMailArrival()",EVT_CLICK);
     	//evtHandler.addEvents("addLink","checkCloseFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnArriveMail","arriveMail()",EVT_CLICK);
     	evtHandler.addEvents("btnDeliverMail","deliverMail()",EVT_CLICK);
     	evtHandler.addEvents("btnCloseFlight","closeFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnReopenFlight","reopenFlight()",EVT_CLICK);
     	evtHandler.addEvents("btnSave","saveMailArrival()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeMailArrival()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
     	evtHandler.addEvents("btnDiscrepancy","loadDiscrepancy()",EVT_CLICK);
     	evtHandler.addEvents("btnTransferMail","transferMail()",EVT_CLICK);
     	evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
	    evtHandler.addEvents("btnAttachRouting","attachRouting()",EVT_CLICK);
	    evtHandler.addEvents("btnAcquit","acquit()",EVT_CLICK);
     	evtHandler.addEvents("mailStatus","mailStatus()",EVT_CLICK);
	    evtHandler.addEvents("btnUndoArrival","undoArrival()",EVT_CLICK);
		evtHandler.addEvents("btnChangeFlight","changeFlight()",EVT_CLICK);
		evtHandler.addEvents("btnAttachAWB","attachAWB()",EVT_CLICK);
		evtHandler.addEvents("btnAutoAttachAWB","autoAttachAWB()",EVT_CLICK);
		evtHandler.addEvents("btnDetachAWB","detachAWB()",EVT_CLICK);


   }
   //applySortOnTable("mailarrival",new Array("None","String","String","Number","Number","Number","Number","String"));

}

function resetFocus(){
	 if(!event.shiftKey){
		if((!targetFormName.elements.flightCarrierCode.disabled) && (!targetFormName.elements.flightCarrierCode.readOnly)){
			targetFormName.elements.flightCarrierCode.focus();
		}
		else if((targetFormName.elements.listFlag.value != "FAILURE") && (!document.getElementById('addLink').disabled)){
			document.getElementById('addLink').focus();
		}
	}
}

function loadDiscrepancy(){

	var opflags =document.getElementsByName("operationalFlag");
	var csgdocs =document.getElementsByName("csgDocNum");
	for(var i=0; i<opflags.length;i++){
        if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
           // showDialog("Please save the new Data.", 1, self);
			showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
			return;
        }
    }
    openPopUp("mailtracking.defaults.disrepancy.loaddiscrepancy.do","320","340");
}

function preWarningMessages(){
   collapseAllRows();
}


function onScreenLoad(){
  frm=targetFormName;

  var chkbox=document.getElementsByName("selectContainer");
  if(chkbox.length < 1 ){
  	disableDetails();
  }
  if(frm.elements.duplicateFlightStatus.value == "Y"){
         openPopUp("flight.operation.duplicateflight.do","600","280");
  }

  if(frm.elements.initialFocus.value == "Y"){
      frm.elements.initialFocus.value = "";
      frm.elements.initialFocus.defaultValue = "";
      frm.elements.flightCarrierCode.focus();
  }
  else if((targetFormName.elements.listFlag.value != "FAILURE") && (!document.getElementById('addLink').disabled)){
		document.getElementById('addLink').focus();
  }

  if(frm.elements.checkFlight.value == "N"){
     frm.elements.checkFlight.value = "";
  	addMails();
  }
  if(frm.elements.warningFlag.value=="Unsaved Data"){
  frm.elements.warningFlag.value="";
showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.unsaveddata" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_7',
						onClose:function(){

						screenConfirmDialog(frm,'id_7');
						confirmMessage(frm,'id_7');

						}
						});
  }

  if(frm.elements.listFlag.value == "FAILURE"){
    var addLink = document.getElementById('addLink');
	disableLink(addLink);
	addLink.onclick = function() { return false; }
  }
  else
  {
	  var addLink = document.getElementById('addLink');
		//enableLink(addLink);
	addLink.onclick = function() { checkCloseFlight(); }

   }

  if(frm.elements.operationalStatus.value == "OPEN"){
	   enableField(targetFormName.elements.btnUndoArrival);
	   enableField(targetFormName.elements.btnChangeFlight);
	   enableField(targetFormName.elements.btnArriveMail);
	   enableField(targetFormName.elements.btnDeliverMail);
	   enableField(targetFormName.elements.btnTransferMail);
	   disableField(targetFormName.elements.btnReopenFlight);
	   enableField(targetFormName.elements.btnCloseFlight);
	   enableField(targetFormName.elements.btnAcquit);
	   enableField(targetFormName.elements.btnAttachAWB);
	   enableField(targetFormName.elements.btnAutoAttachAWB);
	   enableField(targetFormName.elements.btnDetachAWB);
  }else if(frm.elements.operationalStatus.value == "CLOSED"){
  		disableField(targetFormName.elements.btnUndoArrival);
		disableField(targetFormName.elements.btnChangeFlight);
		disableField(targetFormName.elements.btnArriveMail);
		disableField(targetFormName.elements.btnDeliverMail);
		disableField(targetFormName.elements.btnTransferMail);
		disableField(targetFormName.elements.btnCloseFlight);
		disableField(targetFormName.elements.btnAcquit);
		disableField(targetFormName.elements.btnAttachAWB);
		disableField(targetFormName.elements.btnAutoAttachAWB);
		disableField(targetFormName.elements.btnDetachAWB);
		enableField(targetFormName.elements.btnReopenFlight);
  }else{
	   disableField(targetFormName.elements.btnReopenFlight);
	   disableField(targetFormName.elements.btnCloseFlight);
  }
  if(frm.elements.chkFlag.value == "showTransferScreen"){
	frm.elements.chkFlag.value = "";
	var hideCarrier = "CARRIER";
	var str = "mailtracking.defaults.transfercontainer.screenload.do?hideRadio="+hideCarrier+"&assignedto=FLIGHT&reassignedto=FLIGHT&fromScreen=MAIL_ARRIVAL";
	openPopUp(str,"600","398");
  }
  if(frm.elements.chkFlag.value == "showTransferMail"){
  	//THIS CALL IS FROM MAILBAG ENQUIRY
	frm.elements.chkFlag.value = "";
	var str = "mailtracking.defaults.transfermail.screenload.do?fromScreen=MAIL_ARRIVAL";
	openPopUp(str,"600","398");
  }
  if(frm.elements.chkFlag.value == "showTransferMailScreen"){
  	//THIS CALL IS FROM ARRIVAL
	frm.elements.chkFlag.value = "";
	var selectContainer=frm.elements.container.value;
	var fltNo = frm.elements.flightNumber.value;
	var fltDat = frm.elements.arrivalDate.value;
	var frmCarCode = frm.elements.flightCarrierCode.value;
	var strAction="mailtracking.defaults.transfermail.screenload.do";
	var strUrl=strAction+"?mailbag="+selectContainer+"&fromScreen=MAIL_ARRIVAL"+"&frmFltNum="+fltNo+"&frmFltDat="+fltDat+"&frmCarCod="+frmCarCode;
	openPopUp(strUrl,600,355);
  }
     if(frm.elements.changePopUpFlag.value=='showContainerPopUp'){
	frm.elements.changePopUpFlag.value="";
	var selectContainer=frm.elements.container.value;
	strAction="mailtracking.defaults.containerchange.screenload.do";
	var strUrl=strAction+"?childCont="+selectContainer;
	openPopUp(strUrl,"600","200");
	}
	if(frm.elements.changePopUpFlag.value=='showMailPopUpPopUp'){
	frm.elements.changePopUpFlag.value="";
	var selectContainer=frm.elements.container.value;
	strAction="mailtracking.defaults.mailchange.screenload.do";
	var strUrl=strAction+"?childCont="+selectContainer;
	openPopUp(strUrl,"600","325");
	}
	  /*if(frm.chkFlag.value == "showChangeScanTimeScreen"){
	  	frm.chkFlag.value = "";
	  	var selectContainer = frm.selectCont.value;
	  	var childContainer = frm.childCont.value;
	  	var selectMainContainer = frm.selectMainCont.value;

	  	 	var strToDelivery =selectContainer+"/"+childContainer+"/"+selectMainContainer;
			var fromScreen = "ARRIVE";
	    	var strAction="mailtracking.defaults.mailacceptance.changescantime.do";
		 	var strUrl=strAction+"?scanTimeFromScreen="+fromScreen+"&strToDelivery="+strToDelivery;
		 	openPopUp(strUrl,370,190);
	  	}
	  */
	  if(frm.elements.chkFlag.value == "showAttachAWB"){
		frm.elements.chkFlag.value = "";
		 var strAction="mailtracking.defaults.attachawb.screenload.do";
		 var strUrl=strAction+"?screenStatus=SCREENLOAD&fromScreen=MTK007";
		 openPopUp(strUrl,600,228);
	  }
	  if(frm.elements.attachRouting.value=="OK"){
	  	targetFormName.elements.attachRouting.value = "";
	  	var csgDocNum = targetFormName.elements.csgDocNumForRouting.value;
	  	var paCode = targetFormName.elements.paCodeForRouting.value;
	  	var S_fromScreen="MAILARRIVAL";
	  	if(csgDocNum!=""){
	  		var strAction="mailtracking.defaults.mailmanifest.attachroutinglist.do";
	  		var strUrl=strAction+"?conDocNo="+csgDocNum+"&paCode="+paCode+"&fromScreen="+S_fromScreen;
	  		openPopUp(strUrl,800,360);
	  	}else{
	  		var strAction="mailtracking.defaults.mailmanifest.attachroutingscreenload.do";
           var strUrl=strAction+"?fromScreen="+S_fromScreen;
	  		openPopUp(strUrl,800,360);
	  	}
	  }
	// Added by A-5153 for BUG_ICRD-90139
	//Commented for ICRD-194306
	if(frm.elements.disableButtonsForTBA.value == "Y"){
		/*disableField(frm.elements.btnAttachRouting);
		disableField(frm.elements.btnPrint);
		disableField(frm.elements.btnAcquit);
		disableField(frm.elements.btnUndoArrival);
		disableField(frm.elements.btnChangeFlight);
		disableField(frm.elements.btnArriveMail);
		disableField(frm.elements.btnDeliverMail);
		disableField(frm.elements.btnDiscrepancy);
		disableField(frm.elements.btnCloseFlight);
		disableField(frm.elements.btnReopenFlight);
		disableField(frm.elements.btnSave);*/
     }
	if(frm.elements.disableButtonsForAirport.value == "Y"){
	
	disableField(frm.elements.btnChangeFlight);
	disableField(frm.elements.btnUndoArrival);
	disableField(frm.elements.btnAttachRouting);
	disableField(frm.elements.btnPrint);
	disableField(frm.elements.btnAcquit);
	disableField(frm.elements.btnArriveMail);
	disableField(frm.elements.btnDeliverMail);
	disableField(frm.elements.btnDiscrepancy);
	disableField(frm.elements.btnTransferMail);
	disableField(frm.elements.btnCloseFlight);
	disableField(frm.elements.btnReopenFlight);
	disableField(frm.elements.btnSave);
	disableField(targetFormName.elements.btnAttachAWB);
	disableField(targetFormName.elements.btnAutoAttachAWB);
	disableField(targetFormName.elements.btnDetachAWB);
	var addLink = document.getElementById('addLink');
	disableLink(addLink);
	addLink.onclick = function() { return false; }
	
	}

	  if(frm.elements.embargoFlag.value == "embargo_exists"){
        frm.elements.embargoFlag.value = "";
		openPopUp("reco.defaults.showEmbargo.do", 1050,400);
	}
   

	showSaveDialog(frm);
}

function disableDetails(){
    disableField(targetFormName.elements.btnUndoArrival);
	disableField(targetFormName.elements.btnChangeFlight);
    disableField(targetFormName.elements.btnArriveMail);
	disableField(targetFormName.elements.btnDeliverMail);
	disableField(targetFormName.elements.btnCloseFlight);
	disableField(targetFormName.elements.btnReopenFlight);
	disableField(targetFormName.elements.btnSave);
	disableField(targetFormName.elements.btnDiscrepancy);
	disableField(targetFormName.elements.btnTransferMail);
	disableField(targetFormName.elements.btnPrint);
	disableField(targetFormName.elements.btnAttachAWB);
	disableField(targetFormName.elements.btnAutoAttachAWB);
	disableField(targetFormName.elements.btnDetachAWB);	
}
function onScreenloadSetHeight(){
    var height = document.body.clientHeight;;
    document.getElementById('pageDiv').style.height = ((height*95)/100)+'px';
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



function listMailArrival(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailarrival.listflight.do');
}


function clearMailArrival(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailarrival.clearmailarrival.do');
}


function addMails(){
   frm=targetFormName;
   var chkbox =document.getElementsByName("selectContainer");
   var selectContainer = "NA";
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

    if(frm.elements.arrivalDate.disabled){

    	var strAction="mailtracking.defaults.mailarrival.screenloadarrivemailpopup.do";
    	var strUrl=strAction+"?selectContainer="+selectContainer;
    	openPopUp(strUrl,1200,550);

    }

}




function checkCloseFlight(){
    frm=targetFormName;
    submitAction(frm,'/mailtracking.defaults.mailarrival.checkcloseflight.do');
}

function closeFlight(){
    frm=targetFormName;
    	var opflags =document.getElementsByName("operationalFlag");
    	var csgdocs =document.getElementsByName("csgDocNum");
    	for(var i=0; i<opflags.length;i++){
            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                //showDialog("Please save the new Data.", 1, self);
				showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
    			return;
            }
    }
    submitAction(frm,'/mailtracking.defaults.mailarrival.closeflight.do');
}

function reopenFlight(){
    frm=targetFormName;
    	var opflags =document.getElementsByName("operationalFlag");
    	var csgdocs =document.getElementsByName("csgDocNum");
    	for(var i=0; i<opflags.length;i++){
            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
               // showDialog("Please save the new Data.", 1, self);
				showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
    			return;
            }
    }
    submitAction(frm,'/mailtracking.defaults.mailarrival.reopenflight.do');
}

function saveMailArrival(){
    frm=targetFormName;
    var chkbox =document.getElementsByName("selectContainer");

   if(chkbox.length > 0){
	  submitAction(frm,'/mailtracking.defaults.mailarrival.savemailarrival.do');
   }else{
		 //showDialog("No data to save", 1, self);
		// showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.noData" />',1,self);
		 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.noData" scope="request"/>',type:1,parentWindow:self});
   }

}

function arriveMail(){

    frm=targetFormName;
    var chkbox =document.getElementsByName("selectContainer");
	var opflags =document.getElementsByName("operationalFlag");
	var csgdocs =document.getElementsByName("csgDocNum");
	for(var i=0; i<opflags.length;i++){
        if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
          //  showDialog("Please save the new Data.", 1, self);
			showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
			return;
        }
    }
    var selectContainer = "NA";
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
    if(selectContainer=='NA'){
    	var chkboxChild = document.getElementsByName("childContainer");
   		var childContainer = "NA";
		var selectMainContainer;
  		var cntChild = 0;
   		for(var i=0; i<chkboxChild.length;i++){
	         if(chkboxChild[i].checked) {
	         	if(cntChild == 0){
        	      childContainer = chkboxChild[i].value;
        	      selectMainContainer= (chkboxChild[i].value.split("~"))[0];
   	      	      cntChild = 1;
            	}else{
					selectSameCont = (chkboxChild[i].value.split("-"))[0];
					containerCheck = (selectSameCont.split("~"))[0];
					if(containerCheck == selectMainContainer){
            	  childContainer = childContainer + "," + chkboxChild[i].value;
					}else{
						showDialog({msg:"Select DSNs from one container only", type:1, parentWindow:self});
						return;
					}
   	    	     }
              }
         }
     }
	if(childContainer == 'NA'){

		showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.arriveAllMail" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_5',
						onClose:function(){

						screenConfirmDialog(frm,'id_5');
						screenNonConfirmDialog(frm,'id_5');

						}
						});
	}
	else{

		showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.arriveMail" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_5',
						onClose:function(){

						screenConfirmDialog(frm,'id_5');
						screenNonConfirmDialog(frm,'id_5');

						}
						});
	}
}


    /**
    *function to Confirm Dialog
    */
    function screenConfirmDialog(frm, dialogId) {

    	while(frm.elements.currentDialogId.value == ''){

    	}

    	if(frm.elements.currentDialogOption.value == 'Y') {
    	    if(dialogId == 'id_5'){
  			  submitAction(frm,'/mailtracking.defaults.mailarrival.arrivemail.do?fromScreen=main');
    	    }
    	    if(dialogId == 'id_2'){
    	        location.href = appPath + "/home.jsp";
    	    }
			if(dialogId == 'id_1'){
			 submitAction(frm,'/mailtracking.defaults.mailarrival.undoarrivemail.do');
			}
			if(dialogId == 'id_7'){
			 submitAction(frm,'/mailtracking.defaults.mailarrival.undoarrivemail.do');
			}
			if(dialogId == 'id_4'){
			frm.elements.saveSuccessFlag.value="N";
			listMailArrival();
			}
			if(dialogId == 'id_8'){
			submitAction(frm,'/mailtracking.defaults.mailarrival.autoattachawb.do');
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
    		if(dialogId == 'id_5'){

    		}
    		if(dialogId == 'id_2'){

    		}
			if(dialogId == 'id_4'){
			frm.elements.saveSuccessFlag.value="N";
			clearMailArrival();
			}
			if(dialogId == 'id_8'){

    		}
    	}
}


function deliverMail(){
    frm=targetFormName;

    	var opflags =document.getElementsByName("operationalFlag");
    	var csgdocs =document.getElementsByName("csgDocNum");
    	for(var i=0; i<opflags.length;i++){
            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
               // showDialog("Please save the new Data.", 1, self);
				showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
    			return;
            }
    }
       var chkbox = document.getElementsByName("selectContainer");
       var pabuild = document.getElementsByName("paBuiltFlag");
       var selectContainer = "NA";
       var childContainer = "";
       var top = "N";
       var cnt1 = 0;
       var pa="";
       var selectMainContainer="";
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

       if(selectContainer == "NA"){
       	    var chkboxChild = document.getElementsByName("childContainer");
       		var childContainer = "NA";
      		var cntChild = 0;
       		for(var i=0; i<chkboxChild.length;i++){
    	         if(chkboxChild[i].checked) {
    	         	if(cntChild == 0){
            	      childContainer = chkboxChild[i].value;
            	      selectMainContainer= (chkboxChild[i].value.split("~"))[0];
       	      	      cntChild = 1;
     	      	      pa = pabuild[selectMainContainer].value;
                	}else{
                	  var nextMainContainer = (chkboxChild[i].value.split("~"))[0];
                	  if(selectMainContainer != nextMainContainer){

							//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.dsnContainer" />',1,self);
							showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.dsnContainer" scope="request"/>',type:1,parentWindow:self});
         					return;
       	    	      }
       	    	      else{
       	    	      	childContainer = childContainer + "," + chkboxChild[i].value;
       	    	      	pa =pa + "," + pabuild[nextMainContainer].value;
       	    	      }
                    }
                  }
      		 }
      		 if(childContainer == "NA"){
      		 	top="Y";
      		 	var fromScreen = "ARRIVE";
  			  	var strAction="mailtracking.defaults.mailacceptance.changescantime.do";
	            var strUrl=strAction+"?scanTimeFromScreen="+fromScreen;
		      	openPopUp(strUrl,370,190);
       		}

       }

       if(top == "N"){

	       var strToDelivery =selectContainer+"/"+childContainer+"/"+selectMainContainer+"/"+pa;
		    var fromScreen = "ARRIVE";
		    //submitAction(frm,'/mailtracking.defaults.mailarrival.delivermail.do');
		    var strAction="mailtracking.defaults.mailacceptance.changescantime.do";
	        var strUrl=strAction+"?scanTimeFromScreen="+fromScreen+"&strToDelivery="+strToDelivery;
		    openPopUp(strUrl,470,280);
	   }
}

function closeMailArrival(){
    location.href = appPath + "/home.jsp";
}

function mailStatus(){
  frm=targetFormName;

  if(frm.elements.mailStatus.value == "TERM"){
     frm.elements.transferCarrier.value = "";
     frm.elements.arrivalPA.readOnly = false;
     frm.elements.transferCarrier.readOnly = true;
  }

  if(frm.elements.mailStatus.value == "TRNSHP"){
     frm.elements.arrivalPA.value = "";
     frm.elements.arrivalPA.readOnly = true;
     frm.elements.transferCarrier.readOnly = false;
  }

  if(frm.elements.mailStatus.value == "ALL"){
       frm.elements.arrivalPA.readOnly = false;
       frm.elements.transferCarrier.readOnly = false;
  }

}


function transferMail(){
	frm=targetFormName;
	var opflags =document.getElementsByName("operationalFlag");
	var csgdocs =document.getElementsByName("csgDocNum");
	for(var i=0; i<opflags.length;i++){
        if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
            //showDialog("Please save the new Data.", 1, self);
			showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
			return;
        }
    }
	var which="";
	var chkboxx =document.getElementsByName("childContainer");
	for(var i=0; i<chkboxx.length;i++){
		if(chkboxx[i].checked) {
			check=validateSelectedCheckBoxes(frm, 'childContainer', 20000000000, 1);
			if(check){
				var chkboxxs =document.getElementsByName("selectContainer");
				for(var i=0; i<chkboxxs.length;i++){
					if(chkboxxs[i].checked) {
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.selectDSN" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',type:1,parentWindow:self});
						return;
					}
				}
				which="childContainer";
				break;
			}else{
				return;
			}
		}
	}
	chkboxx =document.getElementsByName("selectContainer");
	for(var i=0; i<chkboxx.length;i++){
		if(chkboxx[i].checked) {
			check=validateSelectedCheckBoxes(frm, 'selectContainer', 20000000000, 1);
			if(check){
				var chkboxxs =document.getElementsByName("childContainer");
				for(var i=0; i<chkboxxs.length;i++){
					if(chkboxxs[i].checked) {
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.selectDSN" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.alrTrsf" scope="request"/>',type:1,parentWindow:self});
						return;
					}
				}
				which="selectContainer";
				break;
			}else{
				return;
			}
		}
	}
	var uldType = eval(frm.elements.uldType);
	var contOpFlag = eval(frm.elements.contOpFlag);
	var contTransferFlag = eval(frm.elements.contTransferFlag);
	var contReleasedFlag = eval(frm.elements.contReleasedFlag);
	var dsnTransferFlag = document.getElementsByName("dsnTransferFlag");

	if(which=="selectContainer"){

		var chkbox =document.getElementsByName("selectContainer");
		var selectContainer = "";
		var cnt1 = 0;
		for(var i=0; i<chkbox.length;i++){
			if(chkbox[i].checked) {


				if(cnt1 == 0){
					selectContainer = chkbox[i].value;

					if(uldType[parseInt(selectContainer)]==null){
						var type=uldType.value;
						}else
					{
						var type=uldType[parseInt(selectContainer)].value;
						}

					if(type=="B"){
						//showDialog("Bulk Containers cannot be transferred",1,self, targetFormName);
						//showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.bulkContainers" />', 1, self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.bulkContainers" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					if(contOpFlag[parseInt(selectContainer)]==null){
						var opFlag=contOpFlag.value;
						}else
					{
						var opFlag=contOpFlag[parseInt(selectContainer)].value;
						}


					if(opFlag=="I"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.unSaved" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.unSaved" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					if(contTransferFlag[parseInt(selectContainer)]==null){
						var trnsFlag=contTransferFlag.value;
						}else
					{
						var trnsFlag=contTransferFlag[parseInt(selectContainer)].value;
						}

					if(trnsFlag=="Y"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.alrTrsf" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.alrTrsf" scope="request"/>',type:1,parentWindow:self});
						return;
					}

					if(contReleasedFlag[parseInt(selectContainer)]==null){
						var relFlag=contReleasedFlag.value;
					}else{
						var relFlag=contReleasedFlag[parseInt(selectContainer)].value;
					}
					if(relFlag=="Y"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.uldTrsf" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.uldTrsf" scope="request"/>',type:1,parentWindow:self});
						return;
					}

					cnt1 = 1;
				}else{
					if(uldType[parseInt(chkbox[i].value)]==null){
						var type=uldType.value;
					}else{
					var type=uldType[parseInt(chkbox[i].value)].value;
					}
					if(type=="B"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.bulkContainer" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.bulkContainer" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					if(contOpFlag[parseInt(chkbox[i].value)]==null){
						var opFlag=contOpFlag.value;
						}else{
					var opFlag=contOpFlag[parseInt(chkbox[i].value)].value;
					}
					if(opFlag=="I"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.unSavedULD" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.unSavedULD" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					if(contTransferFlag[parseInt(chkbox[i].value)]==null){
						var trnsFlag=contTransferFlag.value;
						}else{
					var trnsFlag=contTransferFlag[parseInt(chkbox[i].value)].value;
					}
					if(trnsFlag=="Y"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.alrTrsf" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.alrTrsf" scope="request"/>',type:1,parentWindow:self});
						return;
					}

					if(contReleasedFlag[parseInt(chkbox[i].value)]==null){
						var relFlag=contReleasedFlag.value;
					}else{
						var relFlag=contReleasedFlag[parseInt(chkbox[i].value)].value;
					}
					if(relFlag=="Y"){
						//showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.uldTrsf" />',1,self, targetFormName, 'id_1');
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.uldTrsf" scope="request"/>',type:1,parentWindow:self});
						return;
					}
					selectContainer = selectContainer + "," + chkbox[i].value;
				}
			}
		}
           frm.elements.container.value=selectContainer;
           submitAction(frm,'/mailtracking.defaults.mailarrival.transfercontainer.do');
	}
	if(which=="childContainer"){
		var chkbox =document.getElementsByName("childContainer");

		var selectContainer = "";
		var cnt1 = 0;
		for(var i=0; i<chkbox.length;i++){
		   if(chkbox[i].checked == true) {
			if(cnt1 == 0){
				selectContainer = chkbox[i].value;
				var trnsFlag = dsnTransferFlag[i].value;
				if(trnsFlag =="Y"){
				//   showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.mailbags" />',1,self, targetFormName, 'id_1');
				   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.mailbags" scope="request"/>',type:1,parentWindow:self});
				   return;
				}
				cnt1 = 1;
			}else{
				var trnsFlag=dsnTransferFlag[i].value;
				if(trnsFlag=="Y"){
				  // showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.mailbags" />',1,self, targetFormName, 'id_1');
				    showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.mailbags" scope="request"/>',type:1,parentWindow:self});
				   return;
				}
				selectContainer = selectContainer + "," + chkbox[i].value;
			}
		   }
		}
		frm.elements.container.value=selectContainer;
		submitAction(frm,'/mailtracking.defaults.mailarrival.transfermail.do');

	}
}

function acquit(){
	var chkboxMail =document.getElementsByName("selectContainer");
	var chkboxDSN =document.getElementsByName("childContainer");
	var B_chkboxMail = true;
	var B_chkboxDSN = true;
	if(chkboxMail.length>0){
		 for(var i=0;i<chkboxMail.length;i++){
			if(chkboxMail[i].checked){
			   B_chkboxMail = false;
			   break;
			}
		 }
	 }
	 if(chkboxDSN.length>0){
	    for(var i=0;i<chkboxDSN.length;i++){
		    if(chkboxDSN[i].checked){
			   B_chkboxDSN = false;
			   break;
			}
		 }
	 }
	 if(B_chkboxMail && B_chkboxDSN ){
		showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectRow" scope="request"/>',type:1,parentWindow:self});
		return;
	 }
      var selectedContainerS =document.getElementsByName("selectContainer");
      	var opflags =document.getElementsByName("operationalFlag");
      	var csgdocs =document.getElementsByName("csgDocNum");
      	for(var i=0; i<opflags.length;i++){
              if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                 showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
      			return;
              }
    }
	 if(validateSelectedCheckBoxes(this.form,'selectContainer',selectedContainerS.length,1)){
	   submitForm(frm,'mailtracking.defaults.mailarrival.acquituld.do');
	   }
}

function print(){
   frm=targetFormName;

   	var opflags =document.getElementsByName("operationalFlag");
   	var csgdocs =document.getElementsByName("csgDocNum");
   	for(var i=0; i<opflags.length;i++){
           if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
                  showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
   			return;
           }
    }
   //var strAction="mailtracking.defaults.mailarrival.print.do";
   //openPopUp(strAction,400,190);
   generateReport(frm,"/mailtracking.defaults.mailarrival.generatereport.do");

}

function attachRouting(){
	frm=targetFormName;
	var opflags =document.getElementsByName("operationalFlag");
	    	var csgdocs =document.getElementsByName("csgDocNum");
	    	for(var i=0; i<opflags.length;i++){
	            if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
					showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
	    			return;
	            }
    }
	var chkboxMail =document.getElementsByName("selectContainer");
	var chkboxDSN =document.getElementsByName("childContainer");
	var routingAvl=document.getElementsByName("hiddenRoutingAvl");
	var B_chkboxMail = true;
	var B_chkboxDSN = true;
	var container_flag="";
	if(targetFormName.elements.unsavedDataFlag.value=='Y'){
	  // showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.saveData" />',1,self);
	   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.saveData" scope="request"/>',type:1,parentWindow:self});
	   return;
	}
	if(chkboxMail.length>0){
	     for(var i=0;i<chkboxMail.length;i++){
		    if(chkboxMail[i].checked){
			   B_chkboxMail = false;
			   break;
			}
		 }
	 }
	 if(chkboxDSN.length>0){
	    for(var i=0;i<chkboxDSN.length;i++){
		    if(chkboxDSN[i].checked){
			   B_chkboxDSN = false;
			   break;
			}
		 }
	 }
	 if(B_chkboxMail && B_chkboxDSN ){
	    //showDialog('<bean:message bundle="mailArrivalResources" key ="mailtracking.defaults.mailarrival.msg.warn.selectRow" />',1,self);
		showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectRow" scope="request"/>',type:1,parentWindow:self});
		return;
	 }
	var csgDoc=targetFormName.csgDocNum;
	var paCode=targetFormName.paCod;
	var filterCsg="N";
	var filterPaCod="N";
	var routingFlag=0;
	checked="YES";
	cnt=0;
	selectDSN="";
	selectCont="";


	for(var i=0; i<chkboxDSN.length;i++){
		if(chkboxDSN[i].checked) {
			if(csgDoc[i]){
				if(csgDoc[i].value!="N"){
					filterCsg=csgDoc[i].value;
					filterPaCod=paCode[i].value;
				}
			}
			if(cnt==0){
				cnt=1;
				selectDSN=chkboxDSN[i].value;
			}else{
				selectDSN=selectDSN+","+chkboxDSN[i].value;
			}
			if(routingAvl[i].value == "Y"){
			//if(routingAvl[i].checked ){
				routingFlag++;
			}
			checked="NO";
		}
	}
	cnt=0;
	for(var i=0; i<chkboxMail.length;i++){
		if(chkboxMail[i].checked) {
			if(cnt==0){
				cnt=1;
				selectCont=chkboxMail[i].value;
			}else{
				selectCont=selectCont+"-"+chkboxMail[i].value;
			}
		checked="NO";
		}
	}



       if(chkboxDSN.length > 0){

		if (routingFlag>0){
			showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.alrAttached" scope="request"/>',type:1,parentWindow:self});

		}else{

			  if(selectCont.length>0){
				var container_Array =selectCont. split("-");
				var dsn_Array =selectDSN. split(",");
				var empty_string ="";
				 if(container_Array.length>0){
				   for(var i=0;i<container_Array.length;i++){
				     if(dsn_Array.length>0){
					   for(var j=0;j<dsn_Array.length;j++){
					    if(dsn_Array[j].split("~")[0] == container_Array[i] ){
						  container_flag = "Y";
						  if(j==0){
						  new_select_dsn = empty_string;
						  }else{
						  new_select_dsn = new_select_dsn+",";
						  new_select_dsn =new_select_dsn+ empty_string;
						  }
						  selectDSN = "";
						}else{
						   if(j==0){
						  new_select_dsn = dsn_Array[j];
						  }else{
						  new_select_dsn = new_select_dsn+",";
						  new_select_dsn =new_select_dsn+ dsn_Array[j];
						  }

						}
						}
					 }
				   }
				 }
				}
				if(container_flag == "Y"){
				    container_flag="";
					selectDSN = new_select_dsn;
				}

			submitAction(targetFormName,"/mailtracking.defaults.mailarrival.attachroutingvalidate.do?selectChild="+selectDSN+"&parentContainer="+selectCont);
		}

	       }

       else{

showDialog({msg:'<common:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.noMailbags" scope="request"/>',type:1,parentWindow:self});
       }


}
// Added by A-5153 for BUG_ICRD-90139
function confirmMessage(){
var frm = targetFormName;
 	var warningCode = frm.elements.warningFlag.value;
 	if(warningCode != null){
		if('flight_tba_tbc' == warningCode ){
			submitForm(frm,'mailtracking.defaults.mailarrival.listflight.do?warningOveride=Y&disableButtonsForTBA=Y');
		}
		if('deliverable_mails_present' ==  warningCode) {
		  submitForm(frm,'mailtracking.defaults.mailarrival.transfercontainer.do?warningOveride=Y');
		}
		if('partiallydelivered' ==  warningCode) {
		frm.elements.warningFlag.value="";
		var selectContainer=frm.elements.container.value;
		strAction="mailtracking.defaults.mailchange.screenload.do";
		var strUrl=strAction+"?childCont="+selectContainer;
		openPopUp(strUrl,"600","325");
		}
	}

}

function nonconfirmMessage(){

}
function undoArrival(){

var frm = targetFormName;
	 var selectedContainerS =document.getElementsByName("selectContainer");
      	var opflags =document.getElementsByName("operationalFlag");
		var selectedDsns =document.getElementsByName("childContainer");
	var mailOpFlag =document.getElementsByName("mailOpFlag");
	 //if(validateSelectedCheckBoxes(this.form,'selectContainer',selectedContainerS.length,1)){
	//&& validateSelectedCheckBoxes(this.form,'selectContainer',selectedContainerS.length,1)){
	//	showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.undoarrival" />', 4, self, frm, 'id_1');
	//		 	screenConfirmDialog(frm,'id_1');
	//			confirmMessage(frm,'id_1');
	// }

showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.undoarrival" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){

						screenConfirmDialog(frm,'id_1');
						confirmMessage(frm,'id_1');

						}
						});

}
function showSaveDialog(frm){
if(frm.elements.saveSuccessFlag.value=="Y"){
showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.alrt.saveSuccessFlag" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_4',
						onClose:function(){
						screenConfirmDialog(frm,'id_4');
						screenNonConfirmDialog(frm,'id_4');
						}
						});
}
}
function changeFlight(){
	frm=targetFormName;
	var opflags =document.getElementsByName("operationalFlag");
	var csgdocs =document.getElementsByName("csgDocNum");
	for(var i=0; i<opflags.length;i++){
        if("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
			showDialog({msg:'Please save the new Data.',type:1,parentWindow:self});
			return;
        }
    }
	var which="";
	var chkboxx =document.getElementsByName("childContainer");
	for(var i=0; i<chkboxx.length;i++){
		if(chkboxx[i].checked) {
			check=validateSelectedCheckBoxes(frm, 'childContainer', 20000000000, 1);
			if(check){
				var chkboxxs =document.getElementsByName("selectContainer");
				for(var i=0; i<chkboxxs.length;i++){
					if(chkboxxs[i].checked) {
					showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',type:1,parentWindow:self,parentForm:frm,dialogId:'id_1'});
						return;
					}
				}
				which="childContainer";
				break;
			}else{
				return;
			}
		}
	}
	chkboxx =document.getElementsByName("selectContainer");
	for(var i=0; i<chkboxx.length;i++){
		if(chkboxx[i].checked) {
			check=validateSelectedCheckBoxes(frm, 'selectContainer', 20000000000, 1);
			if(check){
				var chkboxxs =document.getElementsByName("childContainer");
				for(var i=0; i<chkboxxs.length;i++){
					if(chkboxxs[i].checked) {
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',type:1,parentWindow:self,parentForm:frm,dialogId:'id_1'});
						return;
					}
				}
				which="selectContainer";
				break;
			}else{
				return;
			}
		}
	}
	if(which=="selectContainer"){
	var chkbox =document.getElementsByName("selectContainer");
		var selectContainer = "";
		var cnt1 = 0;
		for(var i=0; i<chkbox.length;i++){
		   if(chkbox[i].checked == true) {
			if(cnt1 == 0){
				selectContainer = chkbox[i].value;
				cnt1 = 1;
			}else{
				selectContainer = selectContainer + "," + chkbox[i].value;
			}
		   }
		}
	frm.elements.container.value=selectContainer;
	var selectContainer=frm.elements.container.value;
	strAction="mailtracking.defaults.containerchange.validatecontainer.do";
	var strUrl=strAction+"?childCont="+selectContainer;
	submitForm(frm,strUrl);
	}
	else if(which=="childContainer"){
	var chkbox =document.getElementsByName("childContainer");
		var selectContainer = "";
		var cnt1 = 0;
		for(var i=0; i<chkbox.length;i++){
		   if(chkbox[i].checked == true) {
			if(cnt1 == 0){
				selectContainer = chkbox[i].value;
				cnt1 = 1;
			}else{
				selectContainer = selectContainer + "," + chkbox[i].value;
			}
		   }
		}
	frm.elements.container.value=selectContainer;
	var selectContainer=frm.elements.container.value;
	strAction="mailtracking.defaults.mailchange.validatemail.do";
	var strUrl=strAction+"?childCont="+selectContainer;
	submitForm(frm,strUrl);
	}else{
	showDialog({msg:'Please select a Row',type:1,parentWindow:self,parentForm:targetFormName});
}
}
function autoAttachAWB() {
    frm = targetFormName;
    showDialog({
        msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.autoattachawb" scope="request"/>',
        type: 4,
        parentWindow: self,
        parentForm: frm,
        dialogId: 'id_8',
        onClose: function() {
            screenConfirmDialog(frm, 'id_8');
            screenNonConfirmDialog(frm, 'id_8');
        }
    });
}
function attachAWB() {
    frm = targetFormName;
    var opflags = document.getElementsByName("operationalFlag");
    var csgdocs = document.getElementsByName("csgDocNum");
    for (var i = 0; i < opflags.length; i++) {
        if ("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
            showDialog({
                msg: 'Please save the new Data.',
                type: 1,
                parentWindow: self
            });
            return;
        }
    }
    var which = "";
    var chkboxx = document.getElementsByName("childContainer");
    for (var i = 0; i < chkboxx.length; i++) {
        if (chkboxx[i].checked) {
            check = validateSelectedCheckBoxes(frm, 'childContainer', 20000000000, 1);
            if (check) {
                var chkboxxs = document.getElementsByName("selectContainer");
                for (var i = 0; i < chkboxxs.length; i++) {
                    if (chkboxxs[i].checked) {
                        showDialog({
                            msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',
                            type: 1,
                            parentWindow: self,
                            parentForm: frm,
                            dialogId: 'id_1'
                        });
                        return;
                    }
                }
                which = "childContainer";
                break;
            } else {
                return;
            }
        }
    }
    chkboxx = document.getElementsByName("selectContainer");
    for (var i = 0; i < chkboxx.length; i++) {
        if (chkboxx[i].checked) {
            check = validateSelectedCheckBoxes(frm, 'selectContainer', 1, 1);
            if (check) {
                var chkboxxs = document.getElementsByName("childContainer");
                for (var i = 0; i < chkboxxs.length; i++) {
                    if (chkboxxs[i].checked) {
                        showDialog({
                            msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',
                            type: 1,
                            parentWindow: self,
                            parentForm: frm,
                            dialogId: 'id_1'
                        });
                        return;
                    }
                }
                which = "selectContainer";
                break;
            } else {
                return;
            }
        }
    }
    if (which == "selectContainer") {
        var chkbox = document.getElementsByName("selectContainer");
        var selectContainer = "";
        var cnt1 = 0;
        for (var i = 0; i < chkbox.length; i++) {
            if (chkbox[i].checked == true) {
                if (cnt1 == 0) {
                    selectContainer = chkbox[i].value;
                    cnt1 = 1;
                } else {
                    selectContainer = selectContainer + "," + chkbox[i].value;
                }
            }
        }
        frm.elements.container.value = selectContainer;
        var selectContainer = frm.elements.container.value;
		var showAttachAWB="showAttachAWB";
        strAction = "mailtracking.defaults.mailarrival.autoattachawb.do";
        var strUrl = strAction + "?childCont=" + selectContainer+"&chkFlag=" + showAttachAWB;
        submitForm(frm, strUrl);
    } else if (which == "childContainer") {
        var chkbox = document.getElementsByName("childContainer");
        var selectContainer = "";
        var cnt1 = 0;
        for (var i = 0; i < chkbox.length; i++) {
            if (chkbox[i].checked == true) {
                if (cnt1 == 0) {
                    selectContainer = chkbox[i].value;
                    cnt1 = 1;
                } else {
					if(selectContainer.split("~")[0]==chkbox[i].value.split("~")[0]){
                    selectContainer = selectContainer + "," + chkbox[i].value;
					}else{
					showDialog({msg:'Please select dsns from the same container',type:1,parentWindow:self,parentForm:targetFormName});
					return;
					}
                }
            }
        }
        frm.elements.container.value = selectContainer;
        var selectContainer = frm.elements.container.value;
		var showAttachAWB="showAttachAWB";
        strAction = "mailtracking.defaults.mailarrival.autoattachawb.do";
        var strUrl = strAction + "?childCont=" + selectContainer+"&chkFlag=" + showAttachAWB;
        submitForm(frm, strUrl);
    }else{
	showDialog({msg:'Please select a Row',type:1,parentWindow:self,parentForm:targetFormName});
	}
}
function detachAWB() {
     frm = targetFormName;
    var opflags = document.getElementsByName("operationalFlag");
    var csgdocs = document.getElementsByName("csgDocNum");
    for (var i = 0; i < opflags.length; i++) {
        if ("U" == opflags[i].value && csgdocs[i].value != null && csgdocs[i].value.length > 0) {
            showDialog({
                msg: 'Please save the new Data.',
                type: 1,
                parentWindow: self
            });
            return;
        }
    }
    var which = "";
    var chkboxx = document.getElementsByName("childContainer");
    for (var i = 0; i < chkboxx.length; i++) {
        if (chkboxx[i].checked) {
            check = validateSelectedCheckBoxes(frm, 'childContainer', 20000000000, 1);
            if (check) {
                var chkboxxs = document.getElementsByName("selectContainer");
                for (var i = 0; i < chkboxxs.length; i++) {
                    if (chkboxxs[i].checked) {
                        showDialog({
                            msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',
                            type: 1,
                            parentWindow: self,
                            parentForm: frm,
                            dialogId: 'id_1'
                        });
                        return;
                    }
                }
                which = "childContainer";
                break;
            } else {
                return;
            }
        }
    }
    chkboxx = document.getElementsByName("selectContainer");
    for (var i = 0; i < chkboxx.length; i++) {
        if (chkboxx[i].checked) {
            check = validateSelectedCheckBoxes(frm, 'selectContainer', 1, 1);
            if (check) {
                var chkboxxs = document.getElementsByName("childContainer");
                for (var i = 0; i < chkboxxs.length; i++) {
                    if (chkboxxs[i].checked) {
                        showDialog({
                            msg: '<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.mailarrival.msg.warn.selectDSN" scope="request"/>',
                            type: 1,
                            parentWindow: self,
                            parentForm: frm,
                            dialogId: 'id_1'
                        });
                        return;
                    }
                }
                which = "selectContainer";
                break;
            } else {
                return;
            }
        }
    }
    if (which == "selectContainer") {
        var chkbox = document.getElementsByName("selectContainer");
        var selectContainer = "";
        var cnt1 = 0;
        for (var i = 0; i < chkbox.length; i++) {
            if (chkbox[i].checked == true) {
                if (cnt1 == 0) {
                    selectContainer = chkbox[i].value;
                    cnt1 = 1;
                } else {
                    selectContainer = selectContainer + "," + chkbox[i].value;
                }
            }
        }
        frm.elements.container.value = selectContainer;
        var selectContainer = frm.elements.container.value;
        var strAction = "mailtracking.defaults.mailarrival.detachawb.do";
        var strUrl = strAction + "?childCont=" + selectContainer;
        submitForm(frm,strUrl);
    } else if (which == "childContainer") {
        var chkbox = document.getElementsByName("childContainer");
        var selectContainer = "";
        var cnt1 = 0;
        for (var i = 0; i < chkbox.length; i++) {
            if (chkbox[i].checked == true) {
                if (cnt1 == 0) {
                    selectContainer = chkbox[i].value;
                    cnt1 = 1;
                } else {
					if(selectContainer.split("~")[0]==chkbox[i].value.split("~")[0]){
                    selectContainer = selectContainer + "," + chkbox[i].value;
					}else{
					showDialog({msg:'Please select dsns from the same container',type:1,parentWindow:self,parentForm:targetFormName});
					return;
					}
                }
            }
        }
        frm.elements.container.value = selectContainer;
        var selectContainer = frm.elements.container.value;
        var strAction = "mailtracking.defaults.mailarrival.detachawb.do";
        var strUrl = strAction + "?childCont=" + selectContainer;
        submitForm(frm,strUrl);
    }else{
	showDialog({msg:'Please select a Row',type:1,parentWindow:self,parentForm:targetFormName});
	}
}