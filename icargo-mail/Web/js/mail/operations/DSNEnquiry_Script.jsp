<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   //onScreenloadSetHeight();
   onScreenLoad(frm);
   with(frm){

   	//CLICK Events
   	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btViewMailBags","viewDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btViewDamage","viewDamage(this.form)",EVT_CLICK);
	evtHandler.addEvents("btReassign","reassignDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btOffload","offloadDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btReturnDsn","returnDsnDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btTransferDsn","transferDsnDetails(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("airportlov"," displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.port.value,'Airport','1','port','',0)", EVT_CLICK);
	evtHandler.addIDEvents("originLOV","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.originCity.value,'City','1','originCity','',0)", EVT_CLICK);
	evtHandler.addIDEvents("destinationLOV","displayLOV('showCity.do','N','Y','showCity.do',targetFormName.destnCity.value,'City','1','destnCity','',0)", EVT_CLICK);
	evtHandler.addIDEvents("paLOV","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.postalAuthorityCode.value,'PA Code','1','postalAuthorityCode','',0)", EVT_CLICK);

        evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.subCheck)",EVT_CLICK);
        
        //BLUR Events
	evtHandler.addEvents("dsn","paddingDSN()",EVT_BLUR);

	if(frm.subCheck != null){
		evtHandler.addEvents("subCheck","toggleTableHeaderCheckbox('subCheck',targetFormName.checkAll)",EVT_CLICK);
	}
	

   	}
   	   	  applySortOnTable("dsnenquiry",new Array("None","String","String","String","String","Number","String","String","String","String","String","String","Date","String","Number","Number","Number"));
   	   	  
   	

}

function onScreenLoad(frm){

	var statusFlag = frm.elements.screenStatusFlag.value;

	if(statusFlag == "screenload"){

		disableDetails();
		frm.elements.dsn.focus();
	}
	else if(statusFlag == "detail"){
	
		disableSearchDetails();
	}

	var mode = frm.elements.status.value;

	if(mode == "showViewMailEnquiry"){
		frm.elements.status.value = "";
		submitForm(frm,'mailtracking.defaults.mailbagenquiry.listfromdsn.do?fromScreen=DSNENQUIRY');
	}
	else if(mode == "showReassign"){
	   var chkbox =document.getElementsByName("subCheck");
	   if(validateSelectedCheckBoxes(frm,'subCheck',chkbox.length,1)){
	      var selectContainer = "";
	      var str = "DSN_ENQUIRY";
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
   	     openPopUp(strUrl,600,350);
   	   }
	   frm.elements.status.value = "";
	}
	else if(mode == "EMPTY_ULD"){
				
				frm.elements.status.value = "";
				var frmscreen = "DSN_ENQUIRY";
				var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen="+frmscreen;
	  			openPopUp(strUrl,"600","280");
	}
	
	else if(mode == "showOffloadScreen"){

		submitForm(frm,'mailtracking.defaults.offload.listfromsearch.do?fromScreen=DSNENQUIRY');
		frm.elements.status.value = "";
  	}
	else if(mode == "ShowDamagePopup"){
		openPopUp("mailtracking.defaults.damageddsn.screenload.do","700","400");
		frm.elements.status.value = "";
	}
	else if(mode == "showReturnDsnPopup"){

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
		var strAction="mailtracking.defaults.returndsn.screenload.do";
		var strUrl=strAction+"?selectedDsns="+selectContainer+"&fromScreen=DSN_ENQUIRY";
		openPopUp(strUrl,600,370);
		frm.elements.status.value = "";
	}
	else if(mode == "showTransferDsnPopup"){

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
		var strAction="mailtracking.defaults.transfermail.screenload.do";
		var strUrl=strAction+"?mailbag="+selectContainer+"&fromScreen=DSN_ENQUIRY";
		openPopUp(strUrl,600,350);
		frm.elements.status.value = "";
	}

  	selectCombo();
  	if(frm.subCheck != null){
  		toggleTableHeaderCheckbox('subCheck',targetFormName.checkAll);
  	}
  	if(frm.elements.reList.value == "Y"){
		frm.elements.reList.value = "";
		listDetails(frm);
		
	}

}


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px'; 
	//alert((height*95)/100);
}


function paddingDSN(){
 frm=targetFormName;
   
   var dsn = frm.elements.dsn.value;
      if(frm.elements.dsn.value.length == 1){
          dsn = "000"+frm.elements.dsn.value;
      }
      if(frm.dsn.value.length == 2){
                dsn = "00"+frm.elements.dsn.value;
      }
      if(frm.elements.dsn.value.length == 3){
                dsn = "0"+frm.elements.dsn.value;
      }
      
      frm.elements.dsn.value = dsn;
 
}


function listDetails(frm){

	frm.elements.displayPage.value=1;
   	frm.elements.lastPageNum.value=0;
   	frm.elements.status.value="";
   	if(frm.elements.transit.checked){   	
   		frm.elements.transit.value="Y";
   		if(frm.elements.port.value ==""){
   			showDialog('<common:message bundle="dsnEnquiryResources" key="mailtracking.defaults.dsnenquiry.dlg.msg.port" />',1,self);
         	return;
   		}
   	}else{
   		frm.elements.transit.value="N";
   	}
   	submitForm(frm,'mailtracking.defaults.dsnenquiry.listdetails.do?countTotalFlag=Y');
   //	recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
}

function clearDetails(frm){

	submitForm(frm,'mailtracking.defaults.dsnenquiry.clear.do');
}

function closeDetails(frm){

	location.href = appPath + "/home.jsp";
}

function submitPage(lastPg,displayPg){

  var frm=targetFormName;
  enableField(frm.elements.category);
  enableField(frm.elements.mailClass);
  enableField(frm.elements.containerType);

  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;
  //submitForm(frm,'mailtracking.defaults.dsnenquiry.list.do');
 recreateTableDetails("mailtracking.defaults.dsnenquiry.listdetails.do?countTotalFlag=Y","div1","chkListFlow");
  

}

function viewDetails(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck',1,1)){
	
	//	submitForm(frm,'mailtracking.defaults.dsnenquiry.validate.do?status=VIEW');
		recreateTableDetails("mailtracking.defaults.dsnenquiry.validate.do?status=VIEW","div1","chkListFlow");
	}
}

function reassignDetails(frm){

//	var chk = document.getElementsByName("subCheck");
	if(validateSelectedCheckBoxes(frm,'subCheck',30,1)){
//		submitForm(frm,'mailtracking.defaults.dsnenquiry.validate.do?status=REASSIGN');
		recreateTableDetails("mailtracking.defaults.dsnenquiry.validate.do?status=REASSIGN","div1","chkListFlow");
	}
}

function viewDamage(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck',1,1)){
	//	submitForm(targetFormName,'mailtracking.defaults.dsnenquiry.showdamage.do');
		recreateTableDetails("mailtracking.defaults.dsnenquiry.showdamage.do","div1","chkListFlow");
	}
}

function offloadDetails(frm){

	if(validateSelectedCheckBoxes(frm,'subCheck',30,1)){
	//	submitForm(frm,'mailtracking.defaults.dsnenquiry.offload.do');
		recreateTableDetails("mailtracking.defaults.dsnenquiry.offload.do","div1","chkListFlow");
	}
}

function returnDsnDetails(frm){
	
var chkbox =document.getElementsByName("subCheck");	
	var accport =document.getElementsByName("dsnPort");
	var contNum =document.getElementsByName("contNumber");
	var loginPort = frm.elements.loginAirport.value;
	var isSamePort = false;
	var isPltEnabled = false;
	if(document.getElementById("capNotAcp").checked)
	{
	   //showDialog('111Despatch is not accepted ',1,self);
	   //showDialog('<common:message bundle="dsnEnquiryResources" key="mailtracking.defaults.err.capturedbutnotaccepted" />',1,self);
	   showDialog({msg:'<common:message bundle="dsnEnquiryResources" key="mailtracking.defaults.err.capturedbutnotaccepted" />',type:1,parentWindow:self});			
		           	return;
	}
	if(validateSelectedCheckBoxes(frm,'subCheck',chkbox.length,1)){
			var selectContainer = "";
			var acpPort="";
			var pltFlag="";
			var cnt1 = 0;
			  for(var i=0; i<chkbox.length;i++){
				 if(chkbox[i].checked) {
					if(cnt1 == 0){
						selectContainer = chkbox[i].value;
						acpPort=accport[chkbox[i].value];
						if(loginPort==acpPort.value){
						isSamePort=true;
						}
						pltFlag=contNum[chkbox[i].value];
						if(pltFlag!=null&&pltFlag.value=='true'){
						isPltEnabled=true;
						}
						cnt1 = 1;
					}else{
						selectContainer = selectContainer + "," + chkbox[i].value;
						acpPort=accport[chkbox[i].value];
						if(loginPort==acpPort.value){
						isSamePort=true;
						}else{
						isSamePort=false;
						}
						pltFlag=contNum[chkbox[i].value];
						if(pltFlag!=null&&pltFlag.value=='true'){
						isPltEnabled=true;
						}else{
						isPltEnabled=false;
						}
					   }
				    }
		        }
				
		
		  	  
			      if(isSamePort)
				  {
				    recreateTableDetails("mailtracking.defaults.dsnenquiry.returndsn.do","div1","chkListFlow");
				  }
				  else{
					showDialog({msg:'<bean:message bundle="dsnEnquiryResources" key="mailtracking.defaults.dsnenquiry.dlg.msg.dsnreturn" />',type:1,parentWindow:self});			
		           	return;
		           }
			 
		      
		        	
		}

}

function transferDsnDetails(frm){
	var chkbox =document.getElementsByName("subCheck");
	if(validateSelectedCheckBoxes(frm,'subCheck',chkbox.length,1)){
		//submitForm(frm,'mailtracking.defaults.dsnenquiry.transferdsn.do');
		recreateTableDetails("mailtracking.defaults.dsnenquiry.transferdsn.do","div1","chkListFlow");
	}
}

function disableDetails(){

	var frm = targetFormName;
	disableField(frm.elements.btViewMailBags);
	disableField(frm.elements.btReturnDsn);
	disableField(frm.elements.btTransferDsn);
	disableField(frm.elements.btReassign);
	disableField(frm.elements.btViewDamage);
	disableField(frm.elements.btOffload);
	disableField(frm.elements.btTransferDsn);
	disableField(frm.elements.checkAll);
}

function disableSearchDetails(){

	var frm = targetFormName;
	enableField(frm.elements.btViewMailBags);
	enableField(frm.elements.btReturnDsn);
	enableField(frm.elements.btTransferDsn);
	enableField(frm.elements.btReassign);
	enableField(frm.elements.btViewDamage);
	enableField(frm.elements.btOffload);
	enableField(frm.elements.btTransferDsn);
	enableField(frm.elements.checkAll);
	
}

function isRowSelected(chk){
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
	showDialog("<common:message bundle='dsnEnquiryResources' key='mailtracking.defaults.dsnenquiry.msg.info.norowsselected' />", 1, self, frm, 'id_1');
  }

  return flag;
}

function selectCombo(){

	var str = targetFormName.elements.containerType.value;
	if(str == 'ALL'){
		targetFormName.elements.uldNo.value = "";
		targetFormName.elements.uldNo.readOnly = true;
	}
	else{
		targetFormName.elements.uldNo.readOnly = false;
	}
}


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////

var _currDivId="";

function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	_currDivId=divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}

function chkListFlow(_tableInfo){
	
	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];
	var _screenStatusFlag = _innerFrm.screenStatusFlag.value;
 	targetFormName.elements.screenStatusFlag.value = _screenStatusFlag;

 	var _status = _innerFrm.status.value;
 	targetFormName.status.value = _status;
 	var _currentDialogOption = _innerFrm.currentDialogOption.value;
 	targetFormName.elements.currentDialogOption.value = _currentDialogOption;
 	var _currentDialogId = _innerFrm.currentDialogId.value;
 	targetFormName.elements.currentDialogId.value = _currentDialogId;
	onScreenLoad(targetFormName);
	
	
	if(_asyncErrorsExist) return;
	
	updateTableCode(_tableInfo);

}



function updateTableCode(_tableInfo){

	_str=_tableInfo.getTableData();
	
	document.getElementById(_currDivId).innerHTML=_str;
}

function updatePaginationLinks(_tableInfo){
	
	// to update the pagination Links
	_pLabels=_tableInfo.document.getElementById("_paginationLabels");
	_pLinks=_tableInfo.document.getElementById("_paginationLinks")	;
	document.getElementById("paginationLabels").innerHTML=_pLabels.innerHTML;
	document.getElementById("paginationLinks").innerHTML=_pLinks.innerHTML;

}

/////////////////////////////////////////////////////////////////////////////////////////
