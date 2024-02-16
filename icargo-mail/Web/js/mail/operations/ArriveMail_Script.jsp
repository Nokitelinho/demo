<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){
   var frm=targetFormName;
   onScreenLoad();
   with(frm){
		evtHandler.addEvents("btnCaptureDamage","showDamagePopup()",EVT_CLICK);
     	evtHandler.addEvents("btnList","listContainerDetails()",EVT_CLICK);
     	evtHandler.addEvents("btnNew","newContainerDetails()",EVT_CLICK);
     	evtHandler.addEvents("btnDelete","deleteContainerDetails()",EVT_CLICK);
     	evtHandler.addIDEvents("addLink","addMails()",EVT_CLICK);
     	evtHandler.addIDEvents("deleteLink","deleteMails()",EVT_CLICK);
     	evtHandler.addEvents("btnOk","okMails()",EVT_CLICK);
     	evtHandler.addEvents("btnCancel","cancelMails()",EVT_CLICK);
     	evtHandler.addEvents("mailReceived","mailReceived()",EVT_CLICK);
		evtHandler.addEvents("recvdMailTag","headerChecked()",EVT_CLICK);
		evtHandler.addEvents("delvdMailTag","deliverheaderChecked()",EVT_CLICK);
        evtHandler.addEvents("mailDelivered","mailDelivered()",EVT_CLICK);
		evtHandler.addEvents("arrivalSealNo","populateScanDateTimeforExport()",EVT_BLUR);
        evtHandler.addEvents("btnScanTime","changeScanTime()",EVT_CLICK);
		evtHandler.addEvents("btnTransfer","transfer()",EVT_CLICK);
     	evtHandler.addEvents("btnMarkIntact","markIntact()",EVT_CLICK);
	evtHandler.addEvents("paBuilt","sbULD()",EVT_CLICK);
        //Invoking Lov
	evtHandler.addEvents("despatchPALov","invokeLov(this,'despatchPALov')",EVT_CLICK);
	evtHandler.addEvents("despatchOOELov","invokeLov(this,'despatchOOELov')",EVT_CLICK);
	evtHandler.addEvents("despatchDOELov","invokeLov(this,'despatchDOELov')",EVT_CLICK);
	evtHandler.addEvents("despatchSCLov","invokeLov(this,'despatchSCLov')",EVT_CLICK);
	evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
	evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
	evtHandler.addEvents("mailSCLov","invokeLov(this,'mailSCLov')",EVT_CLICK);
    evtHandler.addEvents("btnUndoArrival","undoArrival()",EVT_CLICK);
     	if(frm.selectDespatch != null){
	    evtHandler.addEvents("selectDespatch","toggleTableHeaderCheckbox('selectDespatch',targetFormName.masterDespatch)",EVT_CLICK);
		}
		if(frm.selectMailTag != null){
			evtHandler.addEvents("selectMailTag","toggleTableHeaderCheckbox('selectMailTag',targetFormName.masterMailTag)",EVT_CLICK);
		}
    //BLUR Events
        evtHandler.addEvents("barrowCheck","containerType()",EVT_BLUR);
        evtHandler.addEvents("despatchDSN","despatchDSN()",EVT_BLUR);
        evtHandler.addEvents("mailDSN","mailDSN()",EVT_BLUR);
	evtHandler.addEvents("mailRSN","mailRSN()",EVT_BLUR);
	evtHandler.addEvents("mailWt","mailWeight()",EVT_BLUR);
	evtHandler.addEvents("receivedBags","operationFlagChangeOnEdit(this,'despatchOpFlag');",EVT_BLUR);
	evtHandler.addEvents("receivedWt","operationFlagChangeOnEdit(this,'despatchOpFlag');",EVT_BLUR);
	evtHandler.addEvents("deliveredBags","operationFlagChangeOnEdit(this,'despatchOpFlag');",EVT_BLUR);
	evtHandler.addEvents("deliveredWt","operationFlagChangeOnEdit(this,'despatchOpFlag');",EVT_BLUR);
	evtHandler.addEvents("mailScanDate","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("mailScanTime","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("mailDamaged","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("mailReceived","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("mailDelivered","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("arrivalSealNo","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("containerNo","validatecontainernumberformat()",EVT_BLUR);
	evtHandler.addEvents("mailbagId","populateMailDetails(this)",EVT_BLUR);
	evtHandler.addEvents("mailOOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailDOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailCat","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailSC","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailYr","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailDSN","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("mailRSN","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("mailHNI","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailRI","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailWt","populateMailbagId(this)",EVT_BLUR);	
	evtHandler.addEvents("weightUnit","populateMailbagId(this)",EVT_CHANGE); 
   	}
}

function validatecontainernumberformat(){
var frm=targetFormName;
if(!frm.elements.barrowCheck.checked){
     validateFields(frm.containerNo,-1,'Container Number',0,true,true);
}
}

function  headerChecked(){
 var receivedHeader =document.getElementsByName("recvdMailTag");
  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");

  if(receivedHeader.length > 0){

      for(var i=0; i<received.length;i++){
         received[i].checked=true;
         }
  }

  if(!receivedHeader[0].checked){

     for(var i=0; i<received.length;i++){

	 if(opFlag[i].value !="I" && !( received[i].disabled)){
         received[i].checked=false;
         }
		 }
  }

   if(received.length > 0){

        for(var i=0; i<received.length;i++){

         if(opFlag[i].value != "NOOP"){

           if(!received[i].checked ){
                   mailScanDate[i].value ="";
                   mailScanTime[i].value = "";
           }
          }
        }
  }
  populateScanDateTime();
  }

  function  deliverheaderChecked(){
  var receivedHeader =document.getElementsByName("recvdMailTag");
 var deliverHeader =document.getElementsByName("delvdMailTag");
  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");

  if(deliverHeader.length > 0){

      for(var i=0; i<delivered.length;i++){
         delivered[i].checked=true;
         }
	receivedHeader[0].checked =true;
  }

  if(!deliverHeader[0].checked){

     for(var i=0; i<delivered.length;i++){

	 if( !(delivered[i].disabled)){
         delivered[i].checked=false;
         }
		 }
  }

   if(delivered.length > 0){

        for(var i=0; i<delivered.length;i++){

         if(opFlag[i].value != "NOOP"){

           if(!delivered[i].checked ){
                   mailScanDate[i].value ="";
                   mailScanTime[i].value = "";
           }
          }
        }
  }
  populateScanDateTime();
  }
function validatecontainernumberformat(){
var frm=targetFormName;

if(frm.elements.containerType.value =="U"){
     validateFields(frm.containerNo,-1,'Container Number',0,true,true);
}
}

function  headerChecked(){
 var receivedHeader =document.getElementsByName("recvdMailTag");
  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");

  if(receivedHeader.length > 0){

      for(var i=0; i<received.length;i++){
         received[i].checked=true;
         }
  }

  if(!receivedHeader[0].checked){

     for(var i=0; i<received.length;i++){

	 if(opFlag[i].value !="I" && !( received[i].disabled)){
         received[i].checked=false;
         }
		 }
  }

   if(received.length > 0){

        for(var i=0; i<received.length;i++){

         if(opFlag[i].value != "NOOP"){

           if(!received[i].checked ){
                   mailScanDate[i].value ="";
                   mailScanTime[i].value = "";
           }
          }
        }
  }
  populateScanDateTime();
  }

  function  deliverheaderChecked(){
  var receivedHeader =document.getElementsByName("recvdMailTag");
 var deliverHeader =document.getElementsByName("delvdMailTag");
  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");

  if(deliverHeader.length > 0){

      for(var i=0; i<delivered.length;i++){
         delivered[i].checked=true;
         }
	receivedHeader[0].checked =true;
  }

  if(!deliverHeader[0].checked){

     for(var i=0; i<delivered.length;i++){

	 if( !(delivered[i].disabled)){
         delivered[i].checked=false;
         }
		 }
  }

   if(delivered.length > 0){

        for(var i=0; i<delivered.length;i++){

         if(opFlag[i].value != "NOOP"){

           if(!delivered[i].checked ){
                   mailScanDate[i].value ="";
                   mailScanTime[i].value = "";
           }
          }
        }
  }
  populateScanDateTime();
  }



function screenSpecificTabSetup(){
   setupPanes('container1','tab1');
   displayTabPane('container1','tab2');
}

function showDamagePopup(){
	var chkbox =document.getElementsByName("selectMailTag");
	var opFlag =document.getElementsByName("mailOpFlag");
	var damagedMails =document.getElementsByName("mailDamaged");

	if(validateSelectedCheckBoxes(frm,'selectMailTag',1,1)){

		for(var i=0; i<chkbox.length;i++){

		    if(opFlag[i].value != "NOOP"){

				if(chkbox[i].checked) {

					if(damagedMails[i].checked){
						damageMailBags();
						frm.elements.popupCloseFlag.value = "showDamagePopup";
						submitForm(targetFormName,'mailtracking.defaults.mailarrival.updatearrivemail.do');

					}else{
						//showDialog("<bean:message bundle='mailArrivalResources' key='mailtracking.defaults.arrivemail.msg.alrt.damagedMailNotSelected' />", 1, self);
showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.damagedMailNotSelected" scope="request"/>',type:1,parentWindow:self});

					}
				}
		    }
		}
	}
}

function invokeLov(obj,name){
   var index = obj.id.split(name)[1];

   if(name == "despatchPALov"){
         displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.despatchPA.value,'PA','0','despatchPA','',index);
   }

   if(name == "despatchOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.despatchOOE.value,'OfficeOfExchange','0','despatchOOE','',index);
   }

   if(name == "despatchDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.despatchDOE.value,'OfficeOfExchange','0','despatchDOE','',index);
   }

   if(name == "despatchSCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.despatchSC.value,'OfficeOfExchange','0','despatchSC','',index);
   }

   if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.mailOOE.value,'OfficeOfExchange','0','mailOOE','',index);
   }

   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.mailDOE.value,'OfficeOfExchange','0','mailDOE','',index);
   }

   if(name == "mailSCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailSC.value,'OfficeOfExchange','0','mailSC','',index);
   }

}

function sbULD(){
	var frm=targetFormName;
	if(frm.elements.paBuilt.checked){
		enableField(frm.elements.arrivedStatus);
		enableField(frm.elements.deliveredStatus);
	}else {
		disableField(frm.elements.arrivedStatus);
		disableField(frm.elements.deliveredStatus);
		if(frm.elements.arrivedStatus.checked){
			frm.elements.arrivedStatus.checked=false;
		}
		if(frm.elements.deliveredStatus.checked){
			frm.elements.deliveredStatus.checked=false;
		}
	}
}

function checking(){
	var frm=targetFormName;
	var flag=0;

	if(frm.elements.deliveredStatus.checked){
		frm.elements.arrivedStatus.checked=true;
		 var received =document.getElementsByName("mailReceived");
 		 var delivered =document.getElementsByName("mailDelivered");
  		 var opFlag =document.getElementsByName("mailOpFlag");

		if(received.length > 0){

     		for(var i=0; i<received.length;i++){

      			if(opFlag[i].value != "NOOP"){
           				 delivered[i].checked = true;
        		}
     		}
  		}
  		flag=1;
		mailDelivered();
	}

	if(frm.elements.deliveredStatus.checked==false){
		 var received =document.getElementsByName("mailReceived");
 		 var delivered =document.getElementsByName("mailDelivered");
  		 var opFlag =document.getElementsByName("mailOpFlag");

		if(received.length > 0){

     		for(var i=0; i<received.length;i++){

      			if(opFlag[i].value != "NOOP"){
           				 delivered[i].checked = false;
        		}
     		}
  		}
	}

	if(frm.elements.arrivedStatus.checked && flag==0){
		var received =document.getElementsByName("mailReceived");
  		 var opFlag =document.getElementsByName("mailOpFlag");
		 var rcvdHeader =document.getElementsByName("recvdMailTag");

		if(received.length > 0){

     		for(var i=0; i<received.length;i++){

      			if(opFlag[i].value != "NOOP"){
           				 received[i].checked = true;
        		}
     		}
  		}
	}
	var count=0;
	if(frm.elements.arrivedStatus.checked == false){
		var received =document.getElementsByName("mailReceived");
  		 var opFlag =document.getElementsByName("mailOpFlag");

		if(received.length > 0){

     		for(var i=0; i<received.length;i++){

      			if(opFlag[i].value != "NOOP"){
           				 received[i].checked = false;
        		}
     		}
  		}
	}
	if(count=received.length-1){
rcvdHeader[0].checked= true;
} else if(count<received.length-1){
rcvdHeader[0].checked= false;
  }
populateScanDateTime();
}

function onScreenLoad(){
  frm=targetFormName;
   disableFields();

	if(frm.elements.popupCloseFlag.value == "showDamagePopup"){
		var selectMailBags = "";
    	var chkbox =document.getElementsByName("selectMailTag");
    	var opFlag =document.getElementsByName("mailOpFlag");

		for(var i=0; i<chkbox.length;i++){

			if(opFlag[i].value != "NOOP"){

				if(chkbox[i].checked) {
					selectMailBags = chkbox[i].value;
					var strAction="mailtracking.defaults.arrivemail.damagedetails.screenload.do";
					var strUrl=strAction+"?selectedMailBag="+selectMailBags+"&damageFromScreen=ARRIVEMAIL";
					openPopUp(strUrl,600,240);
					frm.elements.popupCloseFlag.value = "";
					break;
				}
			}
    	}
	}else if(frm.elements.popupCloseFlag.value == "Y"){
        frm = self.opener.targetFormName;
		_unsaved = targetFormName.elements.unsavedDataFlag.value;
       	frm.action="mailtracking.defaults.mailarrival.refreshmailarrival.do?unsavedDataFlag="+_unsaved;
       	frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler();
       	frm.submit();
       	window.close();
       	return;
    }else if(frm.elements.popupCloseFlag.value == "showScanTimePopup"){
	var fromScreen = "ARRIVEPOPUP";
	frm.elements.popupCloseFlag.value = "";
	var selectMail = "";
	var cnt1 = 0;
	var chkbox =document.getElementsByName("selectMailTag");

	if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox.length,1)){

	    for(var i=0; i<chkbox.length;i++){

		if(chkbox[i].checked) {

		   if(cnt1 == 0){
			  selectMail = chkbox[i].value;
			  cnt1 = 1;
		   }else{
			  selectMail = selectMail +","+chkbox[i].value;
		   }
		}
	    }
	  var strAction="mailtracking.defaults.mailacceptance.changescantime.do";
	  var strUrl=strAction+"?scanTimeFromScreen="+fromScreen+"&scanTimeFlag="+selectMail;
	  openPopUp(strUrl,470,280);
		}
	}
	var receivedHeader =document.getElementsByName("recvdMailTag");
  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
  var count=0;
  var deliverHeader =document.getElementsByName("delvdMailTag");
  var delivercount =0;

    if(received.length > 0){

        for(var i=0; i<received.length;i++){

         if(opFlag[i].value != "NOOP"){

           if(received[i].checked ){
                  count++;
				received[i].disabled=true;

           }

          }
        }
  }
//added by a-7871 starts
   if(frm.elements.embargoFlag.value == "embargo_exists"){
        frm.elements.embargoFlag.value = "";
		openPopUp("reco.defaults.showEmbargo.do", 1050,400);
   }
    if(frm.elements.popupCloseFlag.value == "Y"){
        frm = self.opener.targetFormName;
       	frm.action="mailtracking.defaults.mailarrival.refreshmailarrival.do?assignToFlight="+str;
       	frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler(); 
       	frm.submit();
       	window.closeNow();
       	return;
    }
  //added by a-7871 ends 
  if(count+1==received.length){
  receivedHeader[0].checked=true;
  }
  if(delivered.length>0){
    for(var i=0; i<delivered.length;i++){
         if(opFlag[i].value != "NOOP"){
           if(delivered[i].checked ){
                  delivercount++;
				delivered[i].disabled=true;
           }
          }
        }
  }
   if(delivercount+1==delivered.length){
  deliverHeader[0].checked=true;
  }

   if(frm.elements.barrowCheck.checked){
	   frm.elements.containerType.value = "B";
   }else{
	   frm.elements.containerType.value = "U";
   }

}

function selectTableRow(event){
	var _element = getFnKeyTargetField(event);

	if(_element.name == "mailDamaged"){
		return;
	}else{
		var _tr = this;		  //mozilla
		var _element = getFnKeyTargetField(event);
		var e = window.event ? window.event : event;
		var theCode=e.keyCode;

		if(theBrowser=="gko"){ // checkbox clik
			var obj = getFnKeyTargetField(e);

			if(obj)

			if(obj.type!=null){

				if(obj.type.toUpperCase()=="CHECKBOX"){
					return;
				}
			}
		}

		try{
			_firstTd = _tr.getElementsByTagName("td")[0];
			_chkBox = _firstTd.getElementsByTagName("input")[0];

			if(_chkBox.getAttribute("type") == "hidden") {
				_chkBox = _firstTd.getElementsByTagName("input")[1];
			}

			if(_chkBox.getAttribute("type") == "checkbox" || _chkBox.getAttribute("type") == "radio" ){
				_chkBox.click();
			}
		}catch(e){
		}
		_table = getParentTable(_tr);

		if(_table){
			var rowSelectionEvent = _table.getAttribute("rowSelectionEvent");

			if(rowSelectionEvent){
				eval('if(window.'+rowSelectionEvent+'){'+rowSelectionEvent+'(_tr);}');
			}
		}
	}
}






function selectTableRow(event){
	var _element = getFnKeyTargetField(event);

	if(_element.name == "mailDamaged"){
		return;
	}else{
		var _tr = this;		  //mozilla
		var _element = getFnKeyTargetField(event);
		var e = window.event ? window.event : event;
		var theCode=e.keyCode;

		if(theBrowser=="gko"){ // checkbox clik
			var obj = getFnKeyTargetField(e);

			if(obj)

			if(obj.type!=null){

				if(obj.type.toUpperCase()=="CHECKBOX"){
					return;
				}
			}
		}

		try{
			_firstTd = _tr.getElementsByTagName("td")[0];
			_chkBox = _firstTd.getElementsByTagName("input")[0];

			if(_chkBox.getAttribute("type") == "hidden") {
				_chkBox = _firstTd.getElementsByTagName("input")[1];
			}

			if(_chkBox.getAttribute("type") == "checkbox" || _chkBox.getAttribute("type") == "radio" ){
				_chkBox.click();
			}
		}catch(e){
		}
		_table = getParentTable(_tr);

		if(_table){
			var rowSelectionEvent = _table.getAttribute("rowSelectionEvent");

			if(rowSelectionEvent){
				eval('if(window.'+rowSelectionEvent+'){'+rowSelectionEvent+'(_tr);}');
			}
		}
	}
}

/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}
function transfer(){
    frm=targetFormName;

     openPopUp("mailtracking.defaults.mailarrival.transferscreenload.do","300","125");
}
function listContainerDetails(){

   frm=targetFormName;
   damageMailBags();
   submitAction(frm,'/mailtracking.defaults.mailarrival.listarrivemail.do');
}


function newContainerDetails(){
   frm=targetFormName;
   damageMailBags();
   populateScanDateTime();
   var incomplete = validateDespatchDetails();
         if(incomplete == "N"){
           return;
         }
         incomplete = validateMailDetails();
         if(incomplete == "N"){
              return;
   }
   submitAction(frm,'/mailtracking.defaults.mailarrival.newarrivemail.do');
}


function deleteContainerDetails(){
   frm=targetFormName;
   damageMailBags();
   var opflag = "NOOP";
   action="mailtracking.defaults.mailarrival.deletearrivemail.do?operationalFlag="+opflag+"&deleteFlag=Y";
	submitForm(targetFormName, action);
   //submitAction(frm,'/mailtracking.defaults.mailarrival.deletearrivemail.do');
}


/**
 * on change of awb suggest component
 * submit the form and the nextawb action
 */
function callULDSuggestion(uldField, uldNumber, selectedIndex) {
	var frm = targetFormName;

	frm.elements.suggestContainerValue.value = selectedIndex;
	action="mailtracking.defaults.mailarrival.nextarrivemail.do";
	submitForm(targetFormName, action);
}



function addMails(){

   frm=targetFormName;
   damageMailBags();
   var incomplete = "";
   //Modified by A-5237 for ICRD-66546
   if(frm.elements.btnList.disabled==true)
		frm.elements.disableFlag.value="N"
   if(frm.elements.disableFlag.value == "N"){

       if(frm.elements.TABPANE_ID_FLD.value == "tab1"){
          incomplete = validateDespatchDetails();
          if(incomplete == "N"){
  	       return;
		  }


	  //submitAction(frm,'/mailtracking.defaults.mailarrival.adddespatch.do');
	  //recreateMultiTableDetails("mailtracking.defaults.mailarrival.adddespatch.do","div1","div2","ajaxUpdate");

	  addTemplateRow('despatchTemplateRow','despatchTableBody','despatchOpFlag');
	  populateCurrentDespatchRow();
	  displayTabPane('container1','tab1');

   }else{

	  incomplete = validateMailDetails();
	  if(incomplete == "N"){
	      return;
	  }
	  //submitAction(frm,'/mailtracking.defaults.mailarrival.addmailbag.do');
	  //recreateMultiTableDetails("mailtracking.defaults.mailarrival.addmailbag.do","div1","div2","ajaxUpdate");

	  addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');
	  populateCurrentMailTagRow();
	  displayTabPane('container1','tab2');
   }

}
}


function deleteMails(){
   frm=targetFormName;
   damageMailBags();
    if(frm.elements.btnList.disabled==true)
		frm.elements.disableFlag.value="N"
   var despatchOpFlag =document.getElementsByName("despatchOpFlag");
   var mailOpFlag =document.getElementsByName("mailOpFlag");

   var chkbox1 =document.getElementsByName("selectDespatch");
   var chkbox2 =document.getElementsByName("selectMailTag");

   if(frm.elements.disableFlag.value != "Y"){
   if(frm.elements.TABPANE_ID_FLD.value == "tab1"){
      if(validateSelectedCheckBoxes(frm,'selectDespatch',chkbox1.length,'1')){
          //submitAction(frm,'/mailtracking.defaults.mailarrival.deletedespatches.do');
          //recreateMultiTableDetails("mailtracking.defaults.mailarrival.deletedespatches.do","div1","div2","ajaxUpdate");

          for(var i=0; i<chkbox1.length;i++){
		if(chkbox1[i].checked ){
		    if(despatchOpFlag[i].value != "I" && despatchOpFlag[i].value != "NOOP" ){
			 //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchcannotbedeleted" />',1,self);
			 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchcannotbedeleted" scope="request"/>',type:1,parentWindow:self});
			 return;
		    }
		}
          }

          deleteTableRow('selectDespatch','despatchOpFlag');
          displayTabPane('container1','tab1');
      }
   }else{
      if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox2.length,'1')){
          //submitAction(frm,'/mailtracking.defaults.mailarrival.deletemailbags.do');
          //recreateMultiTableDetails("mailtracking.defaults.mailarrival.deletemailbags.do","div1","div2","ajaxUpdate");

          for(var i=0; i<chkbox2.length;i++){
		if(chkbox2[i].checked ){

		    if(mailOpFlag[i].value != "I" && mailOpFlag[i].value != "NOOP"){
			// showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailbagcannotbedeleted" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailbagcannotbedeleted" scope="request"/>',type:1,parentWindow:self});
			 return;
		    }
		}
          }

          deleteTableRow('selectMailTag','mailOpFlag');
          displayTabPane('container1','tab2');
      }
   }

   }
}


function okMails(){

   frm=targetFormName;

   populateScanDateTime();

   populateScanDateTime();
   damageMailBags();

   var incomplete = validateDespatchDetails();
   if(incomplete == "N"){
     return;
   }
   incomplete = validateMailDetails();
   if(incomplete == "N"){
        return;
   }
   if(frm.elements.arrivedStatus.checked){
   	frm.elements.arrivedStatus.value="Y";
   	frm.elements.operationFlag.value="U";
   } else {
   	frm.elements.arrivedStatus.value="N";
   }
   if(frm.elements.deliveredStatus.checked){
   	frm.elements.deliveredStatus.value="Y";
   	frm.elements.operationFlag.value="U";
   } else {
   	frm.elements.deliveredStatus.value="N";
   }

   submitAction(frm,'/mailtracking.defaults.mailarrival.okarrivemail.do?fromScreen=popup');
}

function cancelMails(){
   frm=targetFormName;
   window.close();
}

function damageMailBags(){
frm=targetFormName;
  var damaged =document.getElementsByName("mailDamaged");
  if(damaged.length > 0){
    for(var i=0; i<damaged.length;i++){
       if(damaged[i].checked ){
          damaged[i].value = i;
       }
    }
  }

  var received =document.getElementsByName("mailReceived");
    if(received.length > 0){
      for(var i=0; i<received.length;i++){
         if(received[i].checked ){
            received[i].value = i;
         }
      }
  }

  var delivered =document.getElementsByName("mailDelivered");
    if(delivered.length > 0){
      for(var i=0; i<delivered.length;i++){
         if(delivered[i].checked ){
            delivered[i].value = i;
         }
      }
  }

  if(frm.elements.paBuilt.checked){

     frm.elements.paBuilt.value = "Y";

  }else{

     frm.elements.paBuilt.value = "N";

  }


}

function despatchDSN(){
 frm=targetFormName;
 var despatchDSNArr =document.getElementsByName("despatchDSN");
 var despatchDSN =document.getElementsByName("despatchDSN");
   for(var i=0;i<despatchDSNArr.length;i++){
      if(despatchDSNArr[i].value.length == 1){
          despatchDSN[i].value = "000"+despatchDSNArr[i].value;
      }
      if(despatchDSNArr[i].value.length == 2){
                despatchDSN[i].value = "00"+despatchDSNArr[i].value;
      }
      if(despatchDSNArr[i].value.length == 3){
                despatchDSN[i].value = "0"+despatchDSNArr[i].value;
      }
   }

}


function mailWeight(){
 frm=targetFormName;
 var weightArr =document.getElementsByName("mailWt");
 var weight =document.getElementsByName("mailWt");
 var weightUnit=document.getElementsByName("weightUnit");
   for(var i=0;i<weightArr.length;i++){
                  
                   var x= weight[i].value.split('.');
		        if(x.length>1){
                   if (weightUnit[i].value=='K'){
		
                   if(x[1].length > 1 ) {
                                showDialog({msg:'Weight in Kilogram can accept only 1 value after  decimal point',type:1,parentWindow:self});                
                   return;   
                       }
                     }	
			
                  	 
      else {
                                if(x[1]>0) {
                                   showDialog({msg:'Weight in pound and hectogram cannot have decimal value',type:1,parentWindow:self});
                      
                       return;   
                                } 
                  }  
				}				  
      if(weightArr[i].value.length == 1){
          weight[i].value = "000"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 2){
                weight[i].value = "00"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 3){
                weight[i].value = "0"+weightArr[i].value;
      }
   }
  //populateMailVolume();
}
function populateMailVolume(){
frm=targetFormName;
var density = frm.elements.density.value;
	var weight = document.getElementsByName("mailWt");
	var volume = document.getElementsByName("mailVolume");
	for(var i=0;i<weight.length;i++){
			if (density == ''){
				volume[i].value =  0.01;
			}else{
				var w = weight[i].value;
				var wt = w/(10*density);
				var strWt=wt.toString();
				var s = strWt.indexOf(".");
				var prefix = strWt.substring(0,s);
				var suffix = strWt.substring(s,s+5);
				if(wt != 0 && prefix == 0 && suffix < 0.01){
					volume[i].value =  0.01;
				}else{
					volume[i].value = prefix+suffix;
				}
			}
	}
	//var obj=jquery("[name='mailVolume']");
	//roundHalfUp(obj[0],"VOL");
}

function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("mailDSN");
 var mailDSN =document.getElementsByName("mailDSN");
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


function mailRSN(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("mailRSN");
 var mailRSN =document.getElementsByName("mailRSN");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}



function validateDespatchDetails(){
frm=targetFormName;
   var conDocNo =document.getElementsByName("conDocNo");
   var despatchDate =document.getElementsByName("despatchDate");
   var despatchPA =document.getElementsByName("despatchPA");
   var despatchOOE =document.getElementsByName("despatchOOE");
   var despatchDOE =document.getElementsByName("despatchDOE");
   var despatchClass =document.getElementsByName("despatchClass");
   var despatchDSN =document.getElementsByName("despatchDSN");
   var despatchYear =document.getElementsByName("despatchYear");
   var receivedBags =document.getElementsByName("receivedBags");
   var receivedWeight =document.getElementsByName("receivedWt");
   var deliveredBags =document.getElementsByName("deliveredBags");
   var deliveredWt =document.getElementsByName("deliveredWt");
    var despOprFlag =document.getElementsByName("despOprFlag");
   var opFlag =document.getElementsByName("despatchOpFlag");
    for(var i=0;i<conDocNo.length;i++){

      if(opFlag[i].value != "NOOP"){

	       if(conDocNo[i].value.length == 0){
	          //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.condocnoempty" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.condocnoempty" scope="request"/>',type:1,parentWindow:self});
	          showPane(event,'pane1', frm.tab1);
	          conDocNo[i].focus();
	          return "N";
	       }
	       if(despatchDate[i].value.length == 0){
	           // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.consigndateempty" />',1,self);
				 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.consigndateempty" scope="request"/>',type:1,parentWindow:self});
	            showPane(event,'pane1', frm.tab1);
	            despatchDate[i].focus();
	            return "N";
	       }
	       if(despatchPA[i].value.length == 0){
	         //  showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchpaempty" />',1,self);
			   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchpaempty" scope="request"/>',type:1,parentWindow:self});
	           showPane(event,'pane1', frm.tab1);
	           despatchPA[i].focus();
	           return "N";
	       }
		   if(despatchOOE[i].value.length == 0){
	          // 	showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchooeempty" />',1,self);
				showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchooeempty" scope="request"/>',type:1,parentWindow:self});
	           	showPane(event,'pane1', frm.tab1);
	           	despatchOOE[i].focus();
	           	return "N";
	       }
	       if(despatchOOE[i].value.length != 6){
		  	//	showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchooelength" />',1,self);
				showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchooelength" scope="request"/>',type:1,parentWindow:self});
	     	 	showPane(event,'pane1', frm.tab1);
			 	despatchOOE[i].focus();
				return "N";
	       	}
	         if(despatchDOE[i].value.length == 0){
	            //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdoeempty" />',1,self);
				showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdoeempty" scope="request"/>',type:1,parentWindow:self});
	            showPane(event,'pane1', frm.tab1);
	            despatchDOE[i].focus();
	            return "N";
	        }
	        if(despatchDOE[i].value.length != 6){
			   // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdoelength" />',1,self);
				showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdoelength" scope="request"/>',type:1,parentWindow:self});
			    showPane(event,'pane1', frm.tab1);
			    despatchDOE[i].focus();
		    	return "N";
	        }
	        if(despatchClass[i].value.length == 0){
	         //   showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchclassempty" />',1,self);
				showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchclassempty" scope="request"/>',type:1,parentWindow:self});
	            showPane(event,'pane1', frm.tab1);
	            despatchClass[i].focus();
	            return "N";
	        }
	        if(despatchDSN[i].value.length == 0){
	            // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdsnempty" />',1,self);
				 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchdsnempty" scope="request"/>',type:1,parentWindow:self});
	             showPane(event,'pane1', frm.tab1);
	             despatchDSN[i].focus();
	             return "N";
	        }
	        if(despatchYear[i].value.length == 0){
	             //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchyearempty" />',1,self);
				 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchyearempty" scope="request"/>',type:1,parentWindow:self});
	             showPane(event,'pane1', frm.tab1);
	             despatchYear[i].focus();
	             return "N";
	        }
			if(opFlag[i].value == "I"){
				if(Number(receivedBags[i].value) > 0){
			      if(Number(receivedWeight[i].value) == 0 ){
				    // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.recvdwgtzero" />',1,self);
					 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.recvdwgtzero" scope="request"/>',type:1,parentWindow:self});
				     showPane(event,'pane1', frm.tab1);
				     receivedWeight[i].focus();
				     return "N";
				   }
				}
			   if(Number(receivedWeight[i].value) > 0){
			      if(Number(receivedBags[i].value) == 0){
			       // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.recvdbagzero" />',1,self);
					showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.recvdbagzero" scope="request"/>',type:1,parentWindow:self});
			        showPane(event,'pane1', frm.tab1);
			        receivedBags[i].focus();
			        return "N";
			      }
			  }
			  if(Number(deliveredBags[i].value) > 0){
			      if(Number(deliveredWt[i].value) == 0){
				    //   showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdwgtzero" />',1,self);
					   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdwgtzero" scope="request"/>',type:1,parentWindow:self});
				       showPane(event,'pane1', frm.tab1);
				       deliveredWt[i].focus();
				       return "N";
				   }
		       }
			   if(Number(deliveredWt[i].value) > 0){
			   	  if(Number(deliveredBags[i].value) == 0){
			    	   // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdbagzero" />',1,self);
						showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdbagzero" scope="request"/>',type:1,parentWindow:self});
			       		showPane(event,'pane1', frm.tab1);
			       		deliveredBags[i].focus();
			       		return "N";
			       }
			   }
			  	if(Number(receivedBags[i].value) == 0 ){
			          // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchrcvdbagsempty" />',1,self);
					   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchrcvdbagsempty" scope="request"/>',type:1,parentWindow:self});
			           showPane(event,'pane1', frm.tab1);
			           receivedBags[i].focus();
			           return "N";
			    }
			  	if(Number(receivedWeight[i].value) == 0){
			           //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchrcvdwgtempty" />',1,self);
					   showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.despatchrcvdwgtempty" scope="request"/>',type:1,parentWindow:self});
			           showPane(event,'pane1', frm.tab1);
			           receivedWeight[i].focus();
			           return "N";
		        }
			}
	       if(Number(deliveredBags[i].value) > Number(receivedBags[i].value)){
			     //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdbagsgreater" />',1,self);
				 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdbagsgreater" scope="request"/>',type:1,parentWindow:self});
			     showPane(event,'pane1', frm.tab1);
			     deliveredBags[i].focus();
			     return "N";
			}
		    if(Number(deliveredWt[i].value) > Number(receivedWeight[i].value)){
		  		 //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdwgtgreater" />',1,self);
				 showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.dlvdwgtgreater" scope="request"/>',type:1,parentWindow:self});
			     showPane(event,'pane1', frm.tab1);
			     deliveredWt[i].focus();
			     return "N";
		  	}
	   }
   }
}




function validateMailDetails(){

  frm=targetFormName;
        var mailOOE =document.getElementsByName("mailOOE");
        var mailDOE =document.getElementsByName("mailDOE");
        var mailSC =document.getElementsByName("mailSC");
        var mailYr =document.getElementsByName("mailYr");
        var mailDSN =document.getElementsByName("mailDSN");
        var mailRSN =document.getElementsByName("mailRSN");
        var mailWt =document.getElementsByName("mailWt");
		var mailVolume =document.getElementsByName("mailVolume");
        var mailScanDate =document.getElementsByName("mailScanDate");
        var mailDlvFlag =document.getElementsByName("mailDelivered");
        var mailbagTransferFlag = document.getElementsByName("mailbagTransferFlag");
        var mailScanTime =document.getElementsByName("mailScanTime");
        var opFlag =document.getElementsByName("mailOpFlag");

        for(var i=0;i<mailOOE.length;i++){

        if(opFlag[i].value != "NOOP"){

  	  if(mailOOE[i].value.length == 0){
  //	     showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailooeempty" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailooeempty" scope="request"/>',type:1,parentWindow:self});
  	     showPane(event,'pane2', frm.tab2);
  	     mailOOE[i].focus();
  	     return "N";
  	  }
  	  if(mailOOE[i].value.length != 6){
	//     showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailooelength" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailooelength" scope="request"/>',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailOOE[i].focus();
	     return "N";
          }
   	  if(mailDOE[i].value.length == 0){
   //	     showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildoeempty" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildoeempty" scope="request"/>',type:1,parentWindow:self});
   	     showPane(event,'pane2', frm.tab2);
	     mailDOE[i].focus();
   	     return "N";
   	 }
   	 if(mailDOE[i].value.length != 6){
	 //    showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildoelength" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildoelength" scope="request"/>',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailDOE[i].focus();
	     return "N";
         }
   	  if(mailSC[i].value.length == 0){
   	   //  showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailscempty" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailscempty" scope="request"/>',type:1,parentWindow:self});
   	     showPane(event,'pane2', frm.tab2);
   	     mailSC[i].focus();
   	     return "N";
   	  }
   	  if(mailSC[i].value.length != 2){
	   //  showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailsclength" />',1,self);
		  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailsclength" scope="request"/>',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailSC[i].focus();
	     return "N";
          }
    	  if(mailYr[i].value.length == 0){
    	    // showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailyearempty" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailyearempty" scope="request"/>',type:1,parentWindow:self});
    	     showPane(event,'pane2', frm.tab2);
    	     mailYr[i].focus();
    	     return "N";
    	 }
    	  if(mailDSN[i].value.length == 0){
    	   //  showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildsnempty" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildsnempty" scope="request"/>',type:1,parentWindow:self});
    	     showPane(event,'pane2', frm.tab2);
    	     mailDSN[i].focus();
    	     return "N";
    	 }
     	  if(mailRSN[i].value.length == 0){
     	     //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailrsnempty" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailrsnempty" scope="request"/>',type:1,parentWindow:self});
     	     showPane(event,'pane2', frm.tab2);
     	     mailRSN[i].focus();
     	     return "N";
     	 }
		 if(mailWt[i].value.length == 0){
		     //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailwgtempty" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailwgtempty" scope="request"/>',type:1,parentWindow:self});
		     showPane(event,'pane2', frm.tab2);
		     mailWt[i].focus();
		     return "N";
		 }
		 if(mailWt[i].value == 0){
		     //showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailwgtzero" />',1,self);
			  showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailwgtzero" scope="request"/>',type:1,parentWindow:self});
		     showPane(event,'pane2', frm.tab2);
		     mailWt[i].focus();
		     return "N";
		 }
		 /* frm=targetFormName;
          var weightArr =document.getElementsByName("mailWt");
          var weight =document.getElementsByName("mailWt");
          var weightUnit=document.getElementsByName("weightUnit");
          for(var i=0;i<weightArr.length;i++){
                  
                   var x= weight[i].value.split('.');
		      if(x.length>1){
                   if (weightUnit[i].value=='K'){					
                   if(x[1].length>1) {
                                showDialog({msg:'Weight in Kilogram can accept only 1 value after  decimal point',type:1,parentWindow:self});
                
                   return "N";   
                   }               
		       }
      else {
                                if(x[1]>0) {
                                   showDialog({msg:'Weight in pound and hectogram cannot have decimal value',type:1,parentWindow:self});
                     
                       return "N";    
                                } 
                  }
		  }
	} */
		 	 /*if(mailVolume[i].value == 0){
			   showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailvolumezero" />',1,self);
		     showPane(event,'pane2', frm.tab2);
		     mailVolume[i].focus();
		     return "N";
		 }*/
     	  /*if(mailScanDate[i].value.length == 0){
     	     showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.maildateempty" />',1,self);
     	     showPane(event,'pane2', frm.tab2);
     	     mailScanDate[i].focus();
     	     return "N";
     	  }



	  if(mailScanTime[i].value.length == 0){
	     showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.mailtimeempty" />',1,self);
	     showPane(event,'pane2', frm.tab2);
	     mailScanTime[i].focus();
	     return "N";
	  }*/

	  /*
	  Now done in command class to handle multiple cases
	  if(mailDlvFlag[i].checked && mailbagTransferFlag[i].value=='Y') {
	  	showDialog('<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.transferredmail" />',1,self);
	     showPane(event,'pane2', frm.tab2);
	     mailScanDate[i].focus();
	     return "N";
	  }*/

	}
        }
 }



 /**
  *Method for excecuting after confirmation
  */
 function confirmPopupMessage(){

   frm = targetFormName;
   frm.elements.disableFlag.value = "N";
   disableFields();

 }

  /**
  *Method for excecuting after nonconfirmation
  */
 function nonconfirmPopupMessage(){

   frm = targetFormName;
   frm.elements.disableFlag.value = "";
   frm.elements.isContainerValidFlag.value = "E";
   disableFields();

}

function disableFields(){
frm=targetFormName;
   if(frm.elements.disableFlag.value == "Y"){
       // addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');  //commented by A-7540 for ICRD-322097 as it is not required for POSM
	    displayTabPane('container1','tab2');
	    populateScanDateTime();
		disableField(frm.elements.containerNo);
		disableField(frm.elements.btnList);
		enableField(frm.elements.btnNew);
		enableField(frm.elements.btnDelete);
		enableField(frm.elements.btnOk);
   }else{
		enableField(frm.elements.containerNo);
		enableField(frm.elements.btnList);
		disableField(frm.elements.btnNew);
		disableField(frm.elements.btnDelete);
		enableField(frm.elements.btnOk);
   }
if(frm.elements.containerType.value=="B" && !frm.elements.barrowCheck.checked){
frm.elements.barrowCheck.checked=true;
}
   if(document.getElementById('containerNo')!=null
		&& document.getElementById('containerNo').disabled==true){
			disableField(frm.elements.barrowCheck);
	}
   /*if(frm.containerType.value == "B"){
        disableField(frm.containerNo);
		disableField(frm.paBuilt);
   }*/
   if(frm.elements.barrowCheck.checked){
        disableField(frm.elements.containerNo);
		disableField(frm.elements.paBuilt);
   }

	if( "Y" != frm.elements.isContainerValidFlag.value){
		disableField(frm.elements.btnCaptureDamage);
disableField(frm.elements.btnUndoArrival);
if("E"==frm.elements.isContainerValidFlag.value){
	disableField(frm.elements.btnOk);
   }
	}

	else{
		enableField(frm.elements.btnCaptureDamage);
enableField(frm.elements.btnUndoArrival);
	}
}

function mailReceived(){

  frm=targetFormName;

  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
   var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
var receivedHeader =document.getElementsByName("recvdMailTag");
  if(received.length > 0){
      for(var i=0; i<received.length;i++){
       if(opFlag[i].value != "NOOP"){
         if(!received[i].checked ){
             delivered[i].checked = false;
         }
      }
  }
  }
  var count = 0;
   if(received.length > 0){
      for(var i=0; i<received.length;i++){
       if(opFlag[i].value != "NOOP"){
         if(received[i].checked ){
             count++;
			         }
      }
  }
   }
if(count< received.length-1){
receivedHeader[0].checked= false;
}else if(count=received.length-1){
receivedHeader[0].checked= true;
}

  populateScanDateTime();
 for(var i=0; i<received.length;i++){
			if(opFlag[i].value != "NOOP"){
					if(!received[i].checked){
						mailScanDate[i].value = "";
						mailScanTime[i].value = "";

		 }
  }
}

}


function populateScanDateTime(){

  frm=targetFormName;

  var received =document.getElementsByName("mailReceived");
   var delivered =document.getElementsByName("mailDelivered");
  var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
  var opFlag =document.getElementsByName("mailOpFlag");

  if(received.length > 0){
        for(var i=0; i<received.length;i++){
         if(opFlag[i].value != "NOOP"){
           if(received[i].checked ){

              if(mailScanDate[i].value == "" || mailScanTime[i].value==""){
                   mailScanDate[i].value = frm.elements.hiddenScanDate.value;
                   mailScanTime[i].value = frm.elements.hiddenScanTime.value;
              }
           }
          }
        }
  }

  if(delivered.length > 0){
        for(var i=0; i<delivered.length;i++){
         if(opFlag[i].value != "NOOP"){
           if(delivered[i].checked ){
		  received[i].checked=true;
		  received[i].value = i
              if(mailScanDate[i].value == "" || mailScanTime[i].value==""){
                   mailScanDate[i].value = frm.elements.hiddenScanDate.value;
                   mailScanTime[i].value = frm.elements.hiddenScanTime.value;
              }
           }
          }
        }
  }


}

function populateScanDateTimeforExport(){
  var selectedRows =document.getElementsByName("selectMailTag");
  var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
  var arrivalSealNo =document.getElementsByName("arrivalSealNo");
  var received =document.getElementsByName("mailReceived");
  if(selectedRows.length > 0){
   for(var i=0; i<selectedRows.length;i++){
   if(selectedRows[i].checked){
     if(mailScanDate[i].value!= "" ){

	  }else{
	  if( arrivalSealNo[i].value!= ""){
	  received[i].checked = true;
	  mailScanDate[i].value = frm.elements.hiddenScanDate.value;
	  mailScanTime[i].value = frm.elements.hiddenScanTime.value;
	  }
	  }
	  }
  }
  }
}


function populateCurrentMailTagRow(){
   var mailOOE =document.getElementsByName("mailOOE");
   var mailOOElength=mailOOE.length-1;

	 if(mailOOElength==1){

		 frm.elements.mailScanTime[mailOOElength-1].value=frm.elements.hiddenScanTime.value;
	     frm.elements.mailScanDate[mailOOElength-1].value=frm.elements.hiddenScanDate.value;
		 frm.elements.weightUnit[mailOOElength-1].value=frm.elements.weightUnit[mailOOElength-2].value;//added by A-7540 for ICRD-274933
	  }
	  if(mailOOElength>1){
		  frm.elements.mailOOE[mailOOElength-1].value=frm.elements.mailOOE[mailOOElength-2].value;
		  frm.elements.mailDOE[mailOOElength-1].value=frm.elements.mailDOE[mailOOElength-2].value;
		  frm.elements.mailCat[mailOOElength-1].value=frm.elements.mailCat[mailOOElength-2].value;
		  frm.elements.mailSC[mailOOElength-1].value=frm.elements.mailSC[mailOOElength-2].value;
		  frm.elements.mailYr[mailOOElength-1].value=frm.elements.mailYr[mailOOElength-2].value;
		  frm.elements.mailDSN[mailOOElength-1].value=frm.elements.mailDSN[mailOOElength-2].value;
		  frm.elements.mailHNI[mailOOElength-1].value=frm.elements.mailHNI[mailOOElength-2].value;
		  frm.elements.mailRI[mailOOElength-1].value=frm.elements.mailRI[mailOOElength-2].value;
		  frm.elements.mailScanTime[mailOOElength-1].value=frm.elements.hiddenScanTime.value;
		  frm.elements.mailScanDate[mailOOElength-1].value=frm.elements.hiddenScanDate.value;
		  frm.elements.weightUnit[mailOOElength-1].value=frm.elements.weightUnit[mailOOElength-2].value;//added by A-7540 for ICRD-274933
	  }
	  mailOOE[mailOOElength-1].focus();

}

function populateCurrentDespatchRow(){
   var conDocNo =document.getElementsByName("despatchPA");
   var conDocNolength=conDocNo.length-1;
 	if(conDocNolength==1){
		frm.elements.despatchDate[conDocNolength-1].value=frm.elements.hiddenScanDate.value;
	}
	if(conDocNolength>1){
		frm.elements.conDocNo[conDocNolength-1].value=frm.elements.conDocNo[conDocNolength-2].value;
		frm.elements.despatchPA[conDocNolength-1].value=frm.elements.despatchPA[conDocNolength-2].value;
		frm.elements.despatchOOE[conDocNolength-1].value=frm.elements.despatchOOE[conDocNolength-2].value;
		frm.elements.despatchDOE[conDocNolength-1].value=frm.elements.despatchDOE[conDocNolength-2].value;
		frm.elements.despatchSC[conDocNolength-1].value=frm.elements.despatchSC[conDocNolength-2].value;
		frm.elements.despatchDSN[conDocNolength-1].value=frm.elements.despatchDSN[conDocNolength-2].value;
		frm.elements.despatchYear[conDocNolength-1].value=frm.elements.despatchYear[conDocNolength-2].value;
		frm.elements.despatchCat[conDocNolength-1].value=frm.elements.despatchCat[conDocNolength-2].value;
		frm.elements.despatchClass[conDocNolength-1].value=frm.elements.despatchClass[conDocNolength-2].value;
		frm.elements.despatchDate[conDocNolength-1].value=frm.elements.hiddenScanDate.value;

	}
		conDocNo[conDocNolength-1].focus();
}


function mailDelivered(){
  frm=targetFormName;

  var received =document.getElementsByName("mailReceived");
  var delivered =document.getElementsByName("mailDelivered");
  var opFlag =document.getElementsByName("mailOpFlag");
var deliverHeader =document.getElementsByName("delvdMailTag");
  if(received.length > 0){
     for(var i=0; i<received.length;i++){
      if(opFlag[i].value != "NOOP"){
        if(delivered[i].checked ){
            received[i].checked = true;
        }
     }
  }
  }
    var count = 0;
   if(delivered.length > 0){
      for(var i=0; i<delivered.length;i++){
       if(opFlag[i].value != "NOOP"){
         if(delivered[i].checked ){
             count++;
			         }
      }
  }
   }
if(count< delivered.length-1){
deliverHeader[0].checked= false;
} else if(count=delivered.length-1){
deliverHeader[0].checked= true;
  }

  populateScanDateTime();

}


function containerType(){

frm=targetFormName;
   if(frm.elements.barrowCheck.checked){
	   frm.elements.containerType.value = "B";
	   disableField(frm.elements.containerNo);
       frm.elements.containerNo.value = "";
       disableField(frm.elements.paBuilt);

   }else{
	   frm.elements.containerType.value = "U";
	   enableField(frm.elements.containerNo);
	   enableField(frm.elements.paBuilt);
   }

}


function populateScanDate(){

}


function markIntact(){
	damageMailBags();
	submitForm(targetFormName,'mailtracking.defaults.mailarrival.intactarrivemail.do');

}



 ////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////


 var _currDivIdDespatch = "";
 var _currDivIdMailTag = "";


 function recreateMultiTableDetails(strAction,divId1,divId2){

 	var __extraFn="updateMultiTableCode";

 	if(arguments[3]!=null){
 		__extraFn=arguments[3];
 	}

 	_currDivIdDespatch = divId1;
 	_currDivIdMailTag = divId2;

 	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

 }

 function ajaxUpdate(_tableInfo){

 	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

 	var _popupCloseFlag = _innerFrm.popupCloseFlag.value;
  	targetFormName.elements.popupCloseFlag.value = _popupCloseFlag;

  	var _suggestContainerValue = _innerFrm.suggestContainerValue.value;
  	targetFormName.elements.suggestContainerValue.value = _suggestContainerValue;

  	var _disableFlag = _innerFrm.disableFlag.value;
  	targetFormName.elements.disableFlag.value = _disableFlag;

 	onScreenLoad();

        if(_asyncErrorsExist) return;

        updateMultiTableCode(_tableInfo);


 }


 function updateMultiTableCode(_tableInfo){

 	_despatch=_tableInfo.document.getElementById("_despatch");
	_mailTag=_tableInfo.document.getElementById("_mailTag")	;
	document.getElementById(_currDivIdDespatch).innerHTML=_despatch.innerHTML;
	document.getElementById(_currDivIdMailTag).innerHTML=_mailTag.innerHTML;
 }


 ////////////////////////////////////////////////////////////////////////////////////////


 function changeScanTime(){

         frm=targetFormName;

         damageMailBags();
         var incomplete = validateDespatchDetails();
         if(incomplete == "N"){
             return;
         }
         incomplete = validateMailDetails();
         if(incomplete == "N"){
                return;
         }

         var chkbox =document.getElementsByName("selectMailTag");
         if(chkbox.length > 0){
  	   if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox.length,1)){

  	        frm.elements.popupCloseFlag.value = "showScanTimePopup";
  	        submitForm(frm,'mailtracking.defaults.mailarrival.updatearrivemail.do');
             }
         }

    }
	function undoArrival(){
	var chkbox =document.getElementsByName("selectMailTag");
	var opFlag =document.getElementsByName("mailOpFlag");
	var received =document.getElementsByName("mailReceived");
	var delivered =document.getElementsByName("mailDelivered");
	var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
	var selectedMails="N";
	//if(validateSelectedCheckBoxes(frm,'selectMailTag',1,1)){
		for(var i=0; i<chkbox.length;i++){
		     if(opFlag[i].value != "NOOP"){
				if(chkbox[i].checked) {
				if(!received[i].checked || !received[i].disabled|| opFlag[i].value == "I")
				{
				//  showDialog("The selected mail bag(s) is not arrived. Undo arrival can't be done", 1, self);
				  showDialog({msg:'The selected mail bag(s) is not arrived. Undo arrival cannot be done',type:1,parentWindow:self});
				return;
				}
				else if(delivered[i].checked){
			//	showDialog("There are transferred/delivered mailbags.Undo arrival can't be done ", 1, self);
				showDialog({msg:'There are transferred/delivered mailbags.Undo arrival cannot be done ',type:1,parentWindow:self});
				return;
				}
				else{
				selectedMails="Y";
					}
					}
				}
			}
		//}
		if(selectedMails=="Y"){
			showDialog({msg:'<bean:message bundle="mailArrivalResources" key="mailtracking.defaults.arrivemail.msg.alrt.undoarrival" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){

						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1');

						}
						});
				}else
				{
				// showDialog("Please select a row", 1, self);
				 showDialog({msg:'Please select a row',type:1,parentWindow:self});
				}
}
/**
    *function to Confirm Dialog
    */
    function screenConfirmDialog(frm, dialogId) {
    var chkbox =document.getElementsByName("selectMailTag");
	var opFlag =document.getElementsByName("mailOpFlag");
	var received =document.getElementsByName("mailReceived");
	var delivered =document.getElementsByName("mailDelivered");
	var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
   var damaged =document.getElementsByName("mailDamaged");
    	while(frm.elements.currentDialogId.value == ''){
    	}
    	if(frm.elements.currentDialogOption.value == 'Y') {
			if(dialogId == 'id_1'){
			for(var i=0; i<chkbox.length;i++){
		    if(opFlag[i].value != "NOOP"){
				if(chkbox[i].checked) {
				if(received[i].checked && received[i].disabled )
				{
				 received[i].checked=false;
				 //mailScanDate[i].value = "";
                  // mailScanTime[i].value = "";
				 if(damaged[i].checked){
				damaged[i].checked =false;
				 }
				}
					}
				}
		    }
			}
    	}
    }
	function confirmMessage(){
    }

function populateMailDetails(obj){
	var  _rowCount =null;
	try{
		_rowCount =obj.getAttribute("rowCount");
		
	}catch(err){
		return;
	}
if(targetFormName.elements.mailbagId[_rowCount].value.length==12){

		disableField(targetFormName.elements.mailOOE);
		disableField(targetFormName.elements.mailDOE);
		disableField(targetFormName.elements.mailCat);
		disableField(targetFormName.elements.mailSC);
		disableField(targetFormName.elements.mailYr);
		disableField(targetFormName.elements.mailDSN);
		disableField(targetFormName.elements.mailRSN);
		disableField(targetFormName.elements.mailHNI);
		disableField(targetFormName.elements.mailRI);
		disableField(targetFormName.elements.mailWt);
		disableField(targetFormName.elements.mailVolume);
	}
	globalObj=obj;
	var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'mailtracking.defaults.mailarrival.populatemailtagdetails.do?selectMailTag='+_rowCount;

  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}

 function refreshMailDetails(_tableInfo){
var _rowCount = globalObj.getAttribute("rowCount");
 //added by A-7371 for ICRD-224610
if(_tableInfo.document.getElementById("_ajax_inValidId").innerHTML=='true'){
	showDialog({msg:'Invalid Mail Bag ID',type:1,parentWindow:self});
	targetFormName.elements.mailWt[_rowCount].focus();
	return;
}

if(targetFormName.elements.mailbagId.length>0){
targetFormName.elements.mailOOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailOOE").innerHTML;
targetFormName.elements.mailDOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDOE").innerHTML;
targetFormName.elements.mailCat[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailCat").innerHTML;
targetFormName.elements.mailSC[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailSC").innerHTML;
targetFormName.elements.mailYr[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailYr").innerHTML;
targetFormName.elements.mailDSN[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDSN").innerHTML;
targetFormName.elements.mailRSN[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRSN").innerHTML;
targetFormName.elements.mailHNI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailHNI").innerHTML;
targetFormName.elements.mailRI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRI").innerHTML;
targetFormName.elements.mailWt[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailWt").innerHTML;
targetFormName.elements.mailVolume[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailVol").innerHTML;
targetFormName.elements.mailbagId[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailbagId").innerHTML;
}
//populateMailVolume();
mailWeight();
mailDSN();
mailRSN();
  }
//------------------------------------------------------------------------//	
function populateMailbagId(obj){
	var  _rowCount =null;
	try{
		_rowCount =obj.getAttribute("rowCount");
		
	}catch(err){
		return;
	}
	if(targetFormName.elements.mailOOE[_rowCount].value!=null&&targetFormName.elements.mailOOE[_rowCount].value!=""&&
	targetFormName.elements.mailDOE[_rowCount].value!=null&&targetFormName.elements.mailDOE[_rowCount].value!=""&&
	targetFormName.elements.mailCat[_rowCount].value!=null&&targetFormName.elements.mailCat[_rowCount].value!=""&&
	targetFormName.elements.mailSC[_rowCount].value!=null&&targetFormName.elements.mailSC[_rowCount].value!=""&&
	targetFormName.elements.mailYr[_rowCount].value!=null&&targetFormName.elements.mailYr[_rowCount].value!=""&&
	targetFormName.elements.mailDSN[_rowCount].value!=null&&targetFormName.elements.mailDSN[_rowCount].value!=""&&
	targetFormName.elements.mailRSN[_rowCount].value!=null&&targetFormName.elements.mailRSN[_rowCount].value!=""&&
	targetFormName.elements.mailHNI[_rowCount].value!=null&&targetFormName.elements.mailHNI[_rowCount].value!=""&&
	targetFormName.elements.mailRI[_rowCount].value!=null&&targetFormName.elements.mailRI[_rowCount].value!=""&&
	targetFormName.elements.mailWt[_rowCount].value!=null&&targetFormName.elements.mailWt[_rowCount].value!=""){
	targetFormName.elements.mailbagId[_rowCount].value="";
	populateMailDetails(obj);
	}else{
	targetFormName.elements.mailbagId[_rowCount].value="";
	}
}
