<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();

   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnList","listContainers(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clearContainers(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeContainers(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnUnassign","unassignContainers()",EVT_CLICK);
     	evtHandler.addEvents("btnReassign","reassignContainers()",EVT_CLICK);
     	evtHandler.addEvents("btnOffload","offloadContainers(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btnTransfer","transferContainers(this.form)",EVT_CLICK);
		//evtHandler.addEvents("offload_link","prepareAttributes(event,this,'offload_','offloadDetails',500,90)",EVT_CLICK);
		evtHandler.addEvents("btnViewMailBag","viewMailbag(this.form)",EVT_CLICK);

     	if(frm.selectContainer != null){
		evtHandler.addEvents("selectContainer","toggleTableHeaderCheckbox('selectContainer',targetFormName.masterContainer)",EVT_CLICK);
	}

     //BLUR Events
        evtHandler.addEvents("flightNumber","disableFilter(this.form)",EVT_BLUR);
        evtHandler.addEvents("flightCarrierCode","disableFilter(this.form)",EVT_BLUR);
        evtHandler.addEvents("flightDate","disableFilter(this.form)",EVT_BLUR);
        evtHandler.addEvents("carrier","disableFilter(this.form)",EVT_BLUR);
        evtHandler.addEvents("destination","disableFilter(this.form)",EVT_BLUR);
     //CHANGE Events
        evtHandler.addEvents("operationType","disableTobeTrasferred(this.form)",EVT_CHANGE);
        evtHandler.addEvents("operationTypeAll","disableTobeTrasferredAll(this.form)",EVT_CHANGE);
         evtHandler.addIDEvents("airportlov"," displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.departurePort.value,'Airport','1','departurePort','',0)", EVT_CLICK);

   	}
	var options_arry = new Array();
	options_arry = {
		  "autoOpen" : false,
		  "width" : 500,
		  "height": 5,
		   "draggable" :false,
		  "resizable" :true
		};
	initDialog('offloadDetails',options_arry);
   		applySortOnTable("searchcontainer",new Array("None","None","String","String","Date","String","String","String","String","Date","String","String","Number","Number","None"));
}

function preWarningMessages(){

   selectDiv();
}


function onScreenLoad(){





frm=targetFormName;

if(frm.elements.showEmptyContainer.checked){

  frm.elements.showEmptyContainer.value = "Y";
}else{

frm.elements.showEmptyContainer.value = "N";
}
	disableTobeTrasferred();
	disableTobeTrasferredAll();

	/*var radiobuttons = document.getElementsByName("assignedto");
		var selectedvalue = "";
		var destination = "";
		for(var i=0; i<radiobuttons.length; i++){
			if(radiobuttons[i].checked == true)
			{
				selectedvalue = radiobuttons[i].value;
				break;
			}
	}*/

	selectDiv();

  var selectedvalue = "";
  selectedvalue = targetFormName.elements.assignedTo.value;

  var selectedvalue = "SEARCHCONTAINER";
  var defaultReasgn = "FLIGHT";
  if(frm.elements.status.value == "showReassignScreen"){
       var str = "mailtracking.defaults.reassigncontainer.screenload.do?assignedto=FLIGHT&reassignedto="+defaultReasgn+"&fromScreen="+selectedvalue;
       openPopUp(str,"600","500");
       frm.elements.status.value = "";
  }

  if(frm.elements.status.value == "showTransferScreen"){
         var str = "mailtracking.defaults.transfercontainer.screenload.do?assignedto=FLIGHT&reassignedto="+"DESTINATION"+"&fromScreen="+selectedvalue;//modified by A-7371 as part of ICRD-133998
         openPopUp(str,"600","417");
         frm.elements.status.value = "";
  }

  if(frm.elements.status.value == "showOffloadScreen"){

  	submitForm(frm,'mailtracking.defaults.offload.listfromsearch.do?fromScreen=SEARCHCONTAINER');
  	frm.elements.status.value = "";
  }

  if(frm.elements.status.value == "unassignContainer"){
       frm.elements.status.value = "";
       submitForm(frm,'mailtracking.defaults.searchcontainer.listsearchcontainer.do');

  }
  if(frm.elements.reList.value == "Y"){
		frm.elements.reList.value = "";
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
		listContainers();
	}
  selectCheckbox();
}


function offloadContainers(frm){

	if(validateSelectedCheckBoxes(frm,'selectContainer','','1')){
		submitForm(frm,'mailtracking.defaults.searchcontainer.offloadsearchcontainer.do');
	}
}


function selectCheckbox(){
	toggleTableHeaderCheckbox('selectContainer',targetFormName.masterContainer)
}


/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function listContainers(){
frm=targetFormName;

/*var radiobuttons = document.getElementsByName("assignedto");
		var selectedvalue = "";
		var destination = "";
		for(var i=0; i<radiobuttons.length; i++){
			if(radiobuttons[i].checked == true)
			{
				selectedvalue = radiobuttons[i].value;
				break;
			}
	}



if(selectedvalue=="FLIGHT"){
if(frm.flightNumber.value.trim().length > 0
   || frm.flightCarrierCode.value.trim().length > 0
   || frm.flightDate.value.trim().length > 0){

   if(frm.flightCarrierCode.value.trim().length < 1){
      	showDialog('<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.alrt.formflightcarriercode" />',1,self);
      	frm.flightCarrierCode.focus();
      	return;
   }

   if(frm.flightNumber.value.trim().length < 1){
	showDialog('<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.alrt.formflightnumber" />',1,self);
	frm.flightNumber.focus();
	return;
   }

   if(frm.flightDate.value.trim().length < 1){
   	showDialog('<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.alrt.formflightdate" />',1,self);
   	frm.flightDate.focus();
   	return;
   }

}
}

if(selectedvalue=="DESTINATION"){
if(frm.carrier.value.trim().length > 0
   || frm.destination.value.trim().length > 0){

   if(frm.carrier.value.trim().length < 1){
   	showDialog('<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.alrt.formcarriercode" />',1,self);
   	frm.carrier.focus();
   	return;
   }

   if(frm.destination.value.trim().length < 1){
   	showDialog('<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.alrt.formdestination" />',1,self);
   	frm.destination.focus();
   	return;
   }

}}*/



if(frm.elements.transferable.checked){

  frm.elements.transferable.value = "Y";

}else{

  frm.elements.transferable.value = "N";

}

if(frm.elements.notClosedFlag.checked){
  frm.elements.notClosedFlag.value = "Y";
}else{
  frm.elements.notClosedFlag.value = "N";
}
if(frm.elements.mailAcceptedFlag.checked){
  frm.elements.mailAcceptedFlag.value = "Y";
}else{
  frm.elements.mailAcceptedFlag.value = "N";
}

var mailAccept =frm.elements.mailAcceptedFlag.value;

if(frm.elements.showEmptyContainer.checked){

  frm.elements.showEmptyContainer.value = "Y";
}else{
frm.elements.showEmptyContainer.value = "N";
}
var showEmpty = frm.elements.showEmptyContainer.value;


   frm.elements.displayPage.value=1;
   frm.elements.lastPageNum.value=0;

   submitForm(frm,'mailtracking.defaults.searchcontainer.listsearchcontainer.do?countTotalFlag=YES&showEmptyContainer='+showEmpty+"&mailAcceptedFlag="+mailAccept);//Added by A-5201 as part from the ICRD-21098
   //recreateMultipleTableDetails("mailtracking.defaults.searchcontainer.listsearchcontainer_new.do","div1","chkListFlow");
}

function submitPage(lastPg,displayPg){
frm=targetFormName;
  frm.elements.lastPageNum.value=lastPg;
  frm.elements.displayPage.value=displayPg;

  if(frm.elements.notClosedFlag.checked){
  frm.elements.notClosedFlag.value = "Y";
}else{
  frm.elements.notClosedFlag.value = "N";
}
if(frm.elements.mailAcceptedFlag.checked){
  frm.elements.mailAcceptedFlag.value = "Y";
}else{
  frm.elements.mailAcceptedFlag.value = "N";
}
if(frm.elements.showEmptyContainer.checked){
  frm.elements.showEmptyContainer.value = "Y";
}else{
  frm.elements.showEmptyContainer.value = "N";
}

  submitAction(frm,'/mailtracking.defaults.searchcontainer.listsearchcontainer.do');
}

function clearContainers(){
frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.searchcontainer.clearsearchcontainer.do');
}

function closeContainers(){
    frm=targetFormName;
    // window.close();
	 submitAction(frm,'/mailtracking.defaults.searchcontainer.closesearchcontainer.do');


}

function disableFilter(frm){

if(frm.elements.containerNo.value.trim().length > 0 ){
   if(frm.elements.flightNumber.value.trim().length > 0
     || frm.elements.flightCarrierCode.value.trim().length > 0
     || frm.elements.flightDate.value.trim().length > 0){
         frm.elements.carrier.disabled = true;
         frm.elements.destination.disabled = true;
         frm.elements.flightNumber.disabled = false;
         frm.elements.flightCarrierCode.disabled = false;
         frm.elements.flightDate.disabled = false;
   }

   if(frm.elements.carrier.value.trim().length > 0
   || frm.elements.destination.value.trim().length > 0){
         frm.elements.carrier.disabled = false;
         frm.elements.destination.disabled = false;
         frm.elements.flightNumber.disabled = true;
         frm.elements.flightCarrierCode.disabled = true;
         frm.elements.flightDate.disabled = true;
   }

  }else{
     frm.elements.carrier.disabled = false;
     frm.elements.destination.disabled = false;
     frm.elements.flightNumber.disabled = false;
     frm.elements.flightCarrierCode.disabled = false;
     frm.elements.flightDate.disabled = false;

  }
}

function unassignContainers(){
  frm=targetFormName;
  if(validateSelectedCheckBoxes(frm,'selectContainer','','1')){
         for(var i=0; i<frm.elements.length; i++) {
            if(frm.elements[i].type == "checkbox") {
	       if(frm.elements[i].name=="selectContainer") {
	          if(frm.elements[i].checked) {
	             selectContainer = frm.elements[i].value;

	          }
	        }
	     }
	   }
	  recreateMultipleTableDetails("mailtracking.defaults.searchcontainer.unassignsearchcontainer_ajax.do","div1","chkListFlow");
	 //submitAction(frm,'/mailtracking.defaults.searchcontainer.unassignsearchcontainer.do');
        }
 }

 function reassignContainers(){
   frm=targetFormName;
   if(validateSelectedCheckBoxes(frm,'selectContainer','','1')){
        for(var i=0; i<frm.elements.length; i++) {
           if(frm.elements[i].type == "checkbox") {
 	     if(frm.elements[i].name=="selectContainer") {
 	        if(frm.elements[i].checked) {
 	            selectContainer = frm.elements[i].value;
 	        }
 	     }
 	 }
       }
       recreateMultipleTableDetails("mailtracking.defaults.searchcontainer.reassignsearchcontainer_ajax.do","div1","chkListFlow");
       //submitAction(frm,'/mailtracking.defaults.searchcontainer.reassignsearchcontainer.do');
   }

 }

 function transferContainers(){
    frm=targetFormName;
	var chkbox=document.getElementsByName("selectContainer");
	var uldType = eval(frm.elements.uldType);
    if(validateSelectedCheckBoxes(frm,'selectContainer','','1')){
		for(var i=0; i<chkbox.length;i++)
       	    {
			if(chkbox[i].checked) {
				  if(uldType[i]==null){  //added by A-8149 for ICRD-270524
						var type=uldType.value;
						}else{
						var type=uldType[i].value;
						}
						if(type=="B"){
						showDialog({msg:'<bean:message bundle="searchContainerResources" key="mailtracking.defaults.searchcontainer.msg.warn.bulkcontainer" scope="request"/>',type:1,parentWindow:self});
						return;
					}
     	 	 }
      		}
         for(var i=0; i<frm.elements.length; i++) {
            if(frm.elements[i].type == "checkbox") {
  	     if(frm.elements[i].name=="selectContainer") {
  	        if(frm.elements[i].checked) {
  	            selectContainer = frm.elements[i].value;
  	        }
  	     }
  	 }
        }
        recreateMultipleTableDetails("mailtracking.defaults.searchcontainer.transfersearchcontainer.do","div1","chkListFlow");
        //submitAction(frm,'/mailtracking.defaults.searchcontainer.reassignsearchcontainer.do');
    }

 }


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function recreateMultipleTableDetails(strAction,divId){

	var __extraFn="updateMultipleTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	_currDivId = divId;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}

function chkListFlow(_tableInfo){

	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

	var _lastPageNum = _innerFrm.lastPageNum.value;
 	targetFormName.lastPageNum.value = _lastPageNum;

 	var _displayPage = _innerFrm.displayPage.value;
 	targetFormName.displayPage.value = _displayPage;

 	var _currentDialogOption = _innerFrm.currentDialogOption.value;
 	targetFormName.currentDialogOption.value = _currentDialogOption;

 	var _currentDialogId = _innerFrm.currentDialogId.value;
 	targetFormName.currentDialogId.value = _currentDialogId;

	var _screenStatusFlag = _innerFrm.screenStatusFlag.value;
 	targetFormName.screenStatusFlag.value = _screenStatusFlag;

 	var _status = _innerFrm.status.value;
 	targetFormName.status.value = _status;

 	var _reassignFlag = _innerFrm.reassignFlag.value;
 	targetFormName.reassignFlag.value = _reassignFlag;

	var _warningFlag = _innerFrm.warningFlag.value;
	targetFormName.warningFlag.value = _warningFlag;
	onScreenLoad();

	updateMultipleTableCode(_tableInfo);

	if(_asyncErrorsExist) return;

}


function updateMultipleTableCode(_tableInfo){

	_searchContainer=_tableInfo.document.getElementById("_searchContainer")	;
	document.getElementById(_currDivId).innerHTML=_searchContainer.innerHTML;
	applySortOnTable("searchContainerTemp",new Array("None","None","String","String","Date","String","String","String","String","Date","String","String","Number","Number","None"));
}

////////////////////////////////////////////////////////////////////////////////////////
function disableTobeTrasferred(){
	var frm = targetFormName;
	if(frm.elements.operationType.value == "O"){
		frm.transferable.disabled=true;
	}
	else{
		frm.elements.transferable.disabled=false;
	}

}
function disableTobeTrasferredAll(){
	var frm = targetFormName;
	if(frm.elements.operationTypeAll.value == "O"){
		frm.elements.transferable.disabled=true;
		frm.elements.transferable.checked=false;
	}
	else{
		frm.elements.transferable.disabled=false;
	}

}


function selectDiv() {
				togglePanel(1,targetFormName.elements.assignedTo);
}

function togglePanel(iState,comboObj) // 1 visible, 0 hidden
{
	if(comboObj != null) {

		var divID = comboObj.value;

		var comboLength = comboObj.length;
		var obj = null;
		var comboValues = null;

		var divValues = ['FLT','DESTN','ALL'];
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


	}

}

function prepareAttributes(event,obj,div,divName,dialogWidth,dialogHeight){
	var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[2];
	if(indexId != null && indexId != ""){
	 divId=div+indexId;
	}else{
	 divId=div+'';
	}
	IC.util.event.stopPropagation(event);
	//showInfoMessage(event,divId,invId,divName);
	//getPlatformEvent().cancelBubble = true;
	showInfoMessage(event,divId,invId,divName,dialogWidth,dialogHeight);
}
function viewMailbag(frm){

	if(validateSelectedCheckBoxes(frm,'selectContainer','1','1')){
		submitForm(frm,'mailtracking.defaults.searchcontainer.viewmailbag.do');
	}
}
function confirmMessage(){

var frm = targetFormName;
 	var warningCode = frm.elements.warningFlag.value;
	//if(warningCode != null){
	//	if('deliverable_mails_present' ==  warningCode) {

		//  submitForm(frm,'mailtracking.defaults.searchcontainer.transfersearchcontainer.do?warningOveride=Y');
	//	}
	//}
			 recreateMultipleTableDetails("mailtracking.defaults.searchcontainer.transfersearchcontainer.do?warningOveride=Y","div1","chkListFlow");
}
function nonconfirmMessage(){
}

//Added by A-7929 for ICRD - 224586  starts here
function callbackSearchContainer(collapse,collapseFilterOrginalHeight,mainContainerHeight){   
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               //IC.util.widget.updateTableContainerHeight(jquery("div1"),+collapseFilterOrginalHeight);
               
}
//Added by A-7929 for ICRD - 224586  ends here
