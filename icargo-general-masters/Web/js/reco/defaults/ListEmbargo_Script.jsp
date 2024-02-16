<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{

   var frm=targetFormName;
     // displayParameterValue();
     //  frm.originType.focus();
     viewSelectedParameter(); 
	 setParameterValues();
  with(frm){
  
   	evtHandler.addIDEvents("originLov","showOrigin()",EVT_CLICK);
   	evtHandler.addIDEvents("destinationLov","showDestination()",EVT_CLICK);
	evtHandler.addIDEvents("viaPointLov","showViaPoint()",EVT_CLICK);
	evtHandler.addIDEvents("segmentOriginLov","showSegmentOrigin()",EVT_CLICK); //Added by A-7924 as part of ICRD-313966
   	evtHandler.addIDEvents("segmentDestinationLov","showSegmentDestination()",EVT_CLICK); //Added by A-7924 as part of ICRD-313966
   	//evtHandler.addIDEvents("parameterValueLov","findParameterValue()",EVT_CLICK);
   //	evtHandler.addIDEvents("parameterValueLov","displayParameterValue()",EVT_CLICK);
   	evtHandler.addEvents("btnList","ListEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnClear","ClearEmbargoDetails()",EVT_CLICK);
   	evtHandler.addEvents("btnCreate","createEmbargo()",EVT_CLICK);
	evtHandler.addEvents("btnCreateCompInfo","createCompInfo()",EVT_CLICK);
   	evtHandler.addEvents("btnDetails","embargoDetails()",EVT_CLICK);
   	evtHandler.addEvents("btnCancel","cancelEmbargo()",EVT_CLICK);
	evtHandler.addEvents("btnApprove","approveEmbargo()",EVT_CLICK);
	evtHandler.addEvents("btnReject","rejectEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnClose","closeFunction()",EVT_CLICK);
   	//evtHandler.addEvents("origin","validateOriginCode()",EVT_BLUR);
	evtHandler.addIDEvents("origin","checkLocationLengthOrigin()",EVT_KEYPRESS);
   //	evtHandler.addEvents("destination","validateDestinationCode()",EVT_BLUR);
    evtHandler.addIDEvents("destination","checkLocationLengthDestination()",EVT_KEYPRESS);
	evtHandler.addEvents("viaPoint","validateViaPointCode()",EVT_BLUR);
	evtHandler.addIDEvents("refNumber","validateReferenceNumber()",EVT_BLUR);
   	
      }
    applySortOnTable("embargoDetails",new Array("None","None","Number","None","None","None","None","None","None","None","Date","Date","None","None","None","None","None"));
			
	//displayParamValueField();

}

function findParameterValue()
{
	var frm = targetFormName;
	
	if(targetFormName.elements.parameterCode.value != null && targetFormName.elements.parameterCode.value.length>0){
		submitForm(frm,'reco.defaults.showParameters.do');
	}else{
		showDialog(	
		{msg :"<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		
		
	
	}

}


function displayParameterValue()
{

	var hiddenValue=targetFormName.elements.parameterCode.value;
	var paramValue = targetFormName.elements.parameterValue.value;

	if(hiddenValue == "SCC"){
		displayLOV('showScc.do','N','Y','showScc.do',paramValue,'Parameter','1','parameterValue','','0');
	}
	if(hiddenValue == "COM"){
		displayLOV('showCommodity.do','N','Y','showCommodity.do',paramValue,'Parameter','1','parameterValue','','0');

	}
	if(hiddenValue == 'ULDTYP'){
		displayLOV('showUld.do','N','Y','showUld.do',paramValue,'Parameter','1','parameterValue','',0);
	}
	if(hiddenValue == "CAR"){
		displayLOV('showAirline.do','N','Y','showAirline.do',paramValue,'Parameter','1','parameterValue','','0');

	}if(hiddenValue == "CNS"){
		displayLOV('showCustomer.do','N','Y','showCustomer.do',paramValue,'Parameter','1','parameterValue','','0');

	}if(hiddenValue == "SHP"){
		displayLOV('showCustomer.do','N','Y','showCustomer.do',paramValue,'Parameter','1','parameterValue','','0');

	}

	if(hiddenValue== "PAYTYP"){
		//A-5153
		//displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','parameterValue','parameterDescValue','','tariff.others.paymenttype','');
		displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','parameterValue','parameterDescValue','','reco.defaults.paymenttype','');
	}
	
	else{
		showDialog(		
			{msg :"<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparametercode" scope="request"/>",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

                });
	}
	targetFormName.paraValue.value = "";
	hiddenValue = "";
}


function ListEmbargo(){

	var frm = document.forms[1];
	
	document.forms[1].isButtonClicked.value = "Y";	
	document.forms[1].displayPage.value= "1";
	document.forms[1].lastPageNum.value ="0";
	setParameterValues();
	submitForm(frm,'reco.defaults.listEmbargo.do?navigationMode=FILTER');
}
function ClearEmbargoDetails(){
	var frm = document.forms[1];
	submitForm(frm,'reco.defaults.clearEmbargo.do');
}
function cancelEmbargo(){
	var pos = 0;
	var status;
	var key;
	frm=targetFormName;

	if(validateSelectedCheckBoxes(document.forms[1],'rowId','',1)){

	for(var i=0; i<frm.elements.length; i++) {
		if(frm.elements[i].type == "checkbox") {
			if(frm.elements[i].checked == true) {
			    key = frm.elements[i].value;
			    pos = key.indexOf("-");
			    status = key.substring(pos+1,(key.length));
			}
		}
	}


	if(status !='SUSPENDED'){
		
		showDialog({msg :"<common:message bundle="embargorulesresources" key="reco.defaults.listembargo.cancelembargo" scope="request"/>",
						type:4,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_2',
						onClose: function (result) {
						
						screenConfirmDialog(document.forms[1],'id_2');
						screenNonConfirmDialog(document.forms[1],'id_2');
						}

                }	);
		
		/*if(confirm("Do you want to cancel the Embargo")) {
			var frm = document.forms[1];
			frm.action = "reco.defaults.cancelEmbargo.do";
			frm.submit();
		}*/
	}

	if(status =='SUSPENDED'){
		showDialog({msg :"<common:message bundle="embargorulesresources" key="reco.defaults.listembargo.cancelsuspendedembargo" scope="request"/>",
						type:4,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_3',
						onClose: function (result) {
						
						screenConfirmDialog(document.forms[1],'id_3');
						screenNonConfirmDialog(document.forms[1],'id_3');
						}

                });
		/*if(confirm("Do you want to cancel the Suspended Embargo")) {
			var frm = document.forms[1];
			frm.action = "reco.defaults.cancelEmbargo.do";
			frm.submit();
		}*/

	}
     }
}

// Modified by A-5153 for BUG_ICRD-204648
function embargoDetails(){
	frm = targetFormName;
  	var val;
	var ruleType;
	var version;
	var primaryKey;
   	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,1)){
		for(var i=0; i<frm.elements.length; i++) {
			if(frm.elements[i].type == "checkbox") {
				if(frm.elements[i].name=="rowId") {
					if(frm.elements[i].checked) {
						primaryKey = frm.elements[i].value;
						val = primaryKey;
						var index = val.indexOf("-");
						var ruleIndex = val.indexOf("*");
						var verIndex = val.indexOf("+");
						primaryKey = primaryKey.substring(0,index);
						ruleType = val.substring(ruleIndex+1,verIndex);
						version = val.substring(verIndex+1);
					}
				}
			}
		}
		frm.isDisplayDetails.value="true";
		if(ruleType == "Embargo"){
			submitForm(frm,'reco.defaults.modifyScreenLoad.do?fromList=_'+'&embargoVersion='+version+"&refNumber="+primaryKey);
		}else{
			submitForm(frm,'reco.defaults.modifyComplianceScreenLoad.do?fromList=_'+'&embargoVersion='+version+"&refNumber="+primaryKey);
		}
    }

  }

function submitPage(lastPg,displayPg){
    var frm=targetFormName;
	document.forms[1].lastPageNum.value=lastPg;
	document.forms[1].displayPage.value=displayPg;
	submitForm(frm,'reco.defaults.listEmbargo.do?navigationMode=NAVIGATION');
}

function createEmbargo()
{

	frm=document.forms[1];
	showDialog({msg :"<common:message bundle="embargorulesresources" key="reco.defaults.listembargo.createembargo" scope="request"/>",
						type:4,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_4',
						onClose: function (result) {
						
						screenConfirmDialog(document.forms[1],'id_4');
						screenNonConfirmDialog(document.forms[1],'id_4');
						}

                });

	
}
function createCompInfo()
{
	
	frm=document.forms[1];
	showDialog({msg :"<common:message bundle="embargorulesresources" key="reco.defaults.listembargo.createcomplianceinfo" scope="request"/>",
						type:4,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_5',
						onClose: function (result) {
						
						screenConfirmDialog(document.forms[1],'id_5');
						screenNonConfirmDialog(document.forms[1],'id_5');
						}

                });
	
	
}

function closeFunction(){
	var frm=document.forms[1];
	submitFormWithUnsaveCheck('home.do');
}


function screenConfirmDialog(frm, dialogId) {
	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'reco.defaults.saveEmbargoDetails.do');
		}
		
		if(dialogId == 'id_2'){
			submitForm(frm,'reco.defaults.cancelEmbargo.do');
		}
		
		if(dialogId == 'id_3'){
			submitForm(frm,'reco.defaults.cancelEmbargo.do');
		}
		if(dialogId == 'id_4'){
			frm.isDisplayDetails.value="true";	
			submitForm(frm,'reco.defaults.maintainscreenload.do');
		}
		if(dialogId == 'id_5'){
			frm.isDisplayDetails.value="true";	
			submitForm(frm,'reco.defaults.maintaincomplianceinfoscreenload.do');
		}
	}
}
function screenNonConfirmDialog(frm, dialogId){

	while(frm.currentDialogId.value == ''){

	}

	if(frm.currentDialogOption.value == 'N') {
	}
}
function showOrigin(){
	var frm=targetFormName;
	var originType=targetFormName.elements.originType.value;
	var origin=targetFormName.elements.origin.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(originType!=null && originType.length>0){
		if(originType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',origin,'origin','1','origin','','0');
		}
		if(originType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',origin,'origin','1','origin','','0');
		}
		if( originType == "ARPGRP" || originType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',origin,originType,grpCategory,'origin','1','0');
		}
		if(originType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',origin,'origin','1','origin','','0');    
		}
		if(originType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',origin,'origin','0','origin','','0'); 
		}
	}else {
		showDialog
		({msg :"Please Select a Origin Type",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						}

                });
		
	}
}
function showDestination(){
	var frm=targetFormName;
	var destinationType=targetFormName.elements.destinationType.value;
	var destination=targetFormName.elements.destination.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(destinationType!=null && destinationType.length>0){
		if(destinationType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',destination,'destination','1','destination','','0');
		}
		if(destinationType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',destination,'destination','1','destination','','0');
		}
		if( destinationType == "ARPGRP" || destinationType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',destination,destinationType,grpCategory,'destination','1','0');
		}
		if(destinationType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',destination,'destination','1','destination','','0');    
		}
		if(destinationType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',destination,'destination','0','destination','','0'); 
		}
	}else {
		showDialog
		(	
		{msg :"Please Select a Destination Type",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		
	}
}

function showViaPoint(){
	var frm=targetFormName;
	var viaPointType=targetFormName.elements.viaPointType.value;
	var viaPoint=targetFormName.elements.viaPoint.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(viaPointType!=null && viaPointType.length>0){
		if(viaPointType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
		if(viaPointType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
		if( viaPointType == "ARPGRP" || viaPointType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',viaPoint,viaPointType,grpCategory,'viaPoint','1','0');
		}
		if(viaPointType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',viaPoint,'viaPoint','1','viaPoint','','0');    
		}
		if(viaPointType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',viaPoint,'viaPoint','0','viaPoint','','0'); 
		}
	}else {
		showDialog(	
		{msg :"Please Select a Via Point Type",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
	}
}
//Added by A-7924 as part of ICRD-313966 starts
function showSegmentOrigin(){
	var frm=targetFormName;
	//var segmentOriginType=targetFormName.elements.segmentOriginType.value;
	//var segmentOriginType=targetFormName.elements.CMP_Reco_Defaults_ListEmbargo_SegmentOriginType.value;
    var segmentOriginType =document.getElementById('segmentOriginType').value;
	//var segmentOrigin=targetFormName.elements.segmentOrigin.value;
	//var segmentOrigin=targetFormName.elements.CMP_Reco_Defaults_ListEmbargo_SegmentOrigin.value;
	var segmentOrigin=document.getElementById('segmentOrigin').value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(segmentOriginType!=null && segmentOriginType.length>0){
		if(segmentOriginType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',segmentOrigin,'segmentOrigin','1','segmentOrigin','','0');
		}
		if(segmentOriginType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',segmentOrigin,'segmentOrigin','1','segmentOrigin','','0');
		}
		if( segmentOriginType == "ARPGRP" || segmentOriginType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',segmentOrigin,segmentOriginType,grpCategory,'segmentOrigin','1','0');
		}
		if(segmentOriginType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',segmentOrigin,'segmentOrigin','1','segmentOrigin','','0');    
		}
		if(segmentOriginType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',segmentOrigin,'segmentOrigin','0','segmentOrigin','','0'); 
		}
	}else {
		showDialog
		({msg :"Please Select a Segment Origin Type",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						}

                });
		
	}
}
function showSegmentDestination(){
	var frm=targetFormName;
	//var segmentDestinationType=targetFormName.elements.segmentDestinationType.value;
	var segmentDestinationType =document.getElementById('segmentDestinationType').value;
	//var segmentdestination=targetFormName.elements.segmentDestination.value;
	var segmentDestination =document.getElementById('segmentDestination').value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(segmentDestinationType!=null && segmentDestinationType.length>0){
		if(segmentDestinationType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',segmentDestination,'segmentDestination','1','segmentDestination','','0');
		}
		if(segmentDestinationType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',segmentDestination,'segmentDestination','1','segmentDestination','','0');
		}
		if( segmentDestinationType == "ARPGRP" || segmentDestinationType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',segmentDestination,segmentDestinationType,grpCategory,'segmentDestination','1','0');
		}
		if(segmentDestinationType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',segmentDestination,'segmentDestination','1','segmentDestination','','0');    
		}
		if(segmentDestinationType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',segmentDestination,'segmentDestination','0','segmentDestination','','0'); 
		}
	}else {
		showDialog
		(	
		{msg :"Please Select a Segment Destination Type",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		
	}
}



//Added by A-7924 as part of ICRD-313966 ends

/*
function validateOriginCode(){
	var frm=targetFormName;
	var originType=targetFormName.originType.value;
	if(originType!=null && originType.length>0){
		if(originType == "C"){
		//evtHandler.addEvents("origin","validateMaximumLength(targetFormName.origin,2,'country',true,true);",EVT_BLUR);
		validateMaximumLength(targetFormName.origin,2,'country',true,true);	
			
		}
		
	}
}*/

function validateDestinationCode(){
	var frm=targetFormName;
	var destinationType=targetFormName.elements.destinationType.value;
	if(destinationType!=null && destinationType.length>0){
		if(destinationType == "C"){
		//evtHandler.addEvents("destination","validateMaximumLength(targetFormName.destination,2,'country',true,true);",EVT_BLUR);
		validateMaximumLength(targetFormName.destination,2,'country',true,true);
			
		}
		
	}
}
function validateViaPointCode(){
	var frm=targetFormName;
	var viaPointType=targetFormName.elements.viaPointType.value;
	if(viaPointType!=null && viaPointType.length>0){
		if(viaPointType == "C"){
			validateMaximumLength(targetFormName.viaPoint,2,'country',true,true);	
		}
		
	}
}
function displayParamValueField() {
	var frm = targetFormName;
	if(frm.parameterCode.value=="FLTNUM") {
		document.getElementById("flightDiv").style.display='block';
		document.getElementById("commonDiv").style.display='none';
		frm.parameterValue.value="";
	} else {
		document.getElementById("commonDiv").style.display='block';
		document.getElementById("flightDiv").style.display='none';
		frm.carrierCode.value="";
		frm.flightNumber.value="";
	}
}
function validateReferenceNumber(){
	if(targetFormName.elements.refNumber.value.length == 1){
		targetFormName.elements.refNumber.value = '0000'+targetFormName.elements.refNumber.value;
	}else if(targetFormName.elements.refNumber.value.length == 2){
		targetFormName.elements.refNumber.value = '000'+targetFormName.elements.refNumber.value;
	}else if(targetFormName.elements.refNumber.value.length == 3){
		targetFormName.elements.refNumber.value = '00'+targetFormName.elements.refNumber.value;
	}else if(targetFormName.elements.refNumber.value.length == 4){
		targetFormName.elements.refNumber.value = '0'+targetFormName.elements.refNumber.value;
	}
	//a-5165 for ICRD-28376
	if(event!=null && event.shiftKey){ 
		targetFormName.btnClose.focus();
	}
}
function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,textfieldObj,formNumber,index){

	var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);

	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}

	var strUrl = strAction+"?pagination=Y"+"&multiSelect="+multiSelect+"&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&textfieldObj="+textfieldObj+"&formNumber="+formNumber+"&index="+index;
	//openPopUp(strUrl,width=370,height=330);
	//openPopUp(strUrl,370,425);
	var lovWindow = displayLOV(strUrl,'Y','Y',strUrl,groupNameVal,'groupNameVal','1','origin','',index,null,null,null,null,10);
	//var myWindow = window.open(strUrl, "LOV", 'status,width=370,height=330,screenX=100,screenY=30,left=250,top=200');
}
function checkLocationLengthDestination(){
	var frm = targetFormName;
	var locationtype = targetFormName.elements.destinationType.value;
	//var locationtype = document.getElementById(locationType);
	var locationvalue = document.getElementById('destination');
	//var locationvalue =document.getElementById(locationValue);
	if(locationtype != null){
		if(locationtype =='C'){
			if(locationvalue != null){
				if(locationvalue.value.length>1){
					cancelPlatformEvent();
					return;
				}
			}
		}else if(locationtype =='A' || locationtype == 'S'){
			if(locationvalue != null){
				if(locationvalue.value.length>2){
					cancelPlatformEvent();
					return;
				}
			}
		}
		else {
			if(locationvalue != null){
				if(locationvalue.value.length>29){
					cancelPlatformEvent();
					return;
				}
			}
		}
	}
}
function checkLocationLengthOrigin(){
	var frm = targetFormName;
	var locationtype = targetFormName.elements.originType.value;
	//var locationtype = document.getElementById(locationType);
	var locationvalue = document.getElementById('origin');
	//var locationvalue =document.getElementById(locationValue);
	if(locationtype != null){
		if(locationtype =='C'){
			if(locationvalue != null){
				if(locationvalue.value.length>1){
					cancelPlatformEvent();
					return;
				}
			}
		}else if(locationtype =='A' || locationtype == 'S'){
			if(locationvalue != null){
				if(locationvalue.value.length>2){
					cancelPlatformEvent();
					return;
				}
			}
		}
		else {
			if(locationvalue != null){
				if(locationvalue.value.length>29){
					cancelPlatformEvent();
					return;
				}
			}
		}
	}
}
function approveEmbargo(){
	var pos = 0;
	var status;
	var key;
	frm=targetFormName;
	
	if(validateSelectedCheckBoxes(document.forms[1],'rowId','',1)){

	for(var i=0; i<frm.elements.length; i++) {
		if(frm.elements[i].type == "checkbox") {
			if(frm.elements[i].checked == true) {
			    key = frm.elements[i].value;
			    pos = key.indexOf("-");
			    status = key.substring(pos+1,(key.length));
			}
		}
	}
	submitForm(frm,'reco.defaults.approve.do');
	}
}
function rejectEmbargo(){
	var pos = 0;
	var status;
	var key;
	frm=targetFormName;

	if(validateSelectedCheckBoxes(document.forms[1],'rowId','',1)){

	for(var i=0; i<frm.elements.length; i++) {
		if(frm.elements[i].type == "checkbox") {
			if(frm.elements[i].checked == true) {
			    key = frm.elements[i].value;
			    pos = key.indexOf("-");
			    status = key.substring(pos+1,(key.length));
			}
		}
	}
	submitForm(frm,'reco.defaults.reject.do');
	}
}


function setAllWeekdays(obj) {
 if(jquery(obj).is(":checked")){
	jquery(obj).parent().find("input[type='checkbox']").each(function( i ) {		
		jquery(this).prop("checked",true);	
	});
 }else{
	jquery(obj).parent().find("input[type='checkbox']").each(function( i ) {
		jquery(this).removeAttr("checked");	
	});
 }
}

function setIndividualDays(obj) {
 var allCheckFlag=true;
	jquery(obj).parent().find("input[type='checkbox']").each(function( i ) {
		 if(!jquery(this).is(":checked") && i!=0){
			allCheckFlag=false;
		 }
	});
	if(allCheckFlag){
		jquery(obj).parent().find("input[name='weekDaysAll']").prop("checked",true);
	}else{
		jquery(obj).parent().find("input[name='weekDaysAll']").removeAttr("checked");
	}
}
function setParameterValues(){
var parameterCode;

	
	var parameterCode=jquery("select[name='parameterCode']").val();
	var parameterValue=jquery("select[name='parameterValue']").val();
	if(null !=parameterCode && "" !=parameterCode){
	if(parameterCode=="FLTNUM"){
			jquery("input[name='parameterValue']").val(jquery("input[name='carrierCode']").val()+"~"+jquery("input[name='flightNumber']").val());
		}else if(parameterCode=="CAR"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='carrier']").val());
	}else if(parameterCode=="COM"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='commodity']").val());
	}else if(parameterCode=="DAT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='dates']").val());
	}else if(parameterCode=="TIM"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='time']").val());
	}else if(parameterCode=="FLTOWR"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='flightOwner']").val());
	}else if(parameterCode=="FLTTYP"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='flightType']").val());
	}else if(parameterCode=="ULDPOS"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='uldPos']").val());
	}else if(parameterCode=="ULDTYP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='uldTyp']").val());
	}else if(parameterCode=="GOODS"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='natureOfGoods']").val());
	}else if(parameterCode=="PKGINS"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='packingInstruction']").val());
	}else if(parameterCode=="PAYTYP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='paymentType']").val());
	}else if(parameterCode=="PRD"){ 
			jquery("input[name='parameterValue']").val((jquery("input[name='productCode']").val())?jquery("input[name='productCode']").val():jquery("input[name='parameterValue']").val());
			jquery("input[name='productCode']").val((jquery("input[name='productCode']").val())?jquery("input[name='productCode']").val():jquery("input[name='parameterValue']").val());
	}else if(parameterCode=="SCC"){ 
			jquery("input[name='parameterValue']").val((jquery("input[name='scc']").val())?jquery("input[name='scc']").val():jquery("input[name='parameterValue']").val());
			jquery("input[name='scc']").val((jquery("input[name='scc']").val())?jquery("input[name='scc']").val():jquery("input[name='parameterValue']").val());
	}else if(parameterCode=="SCCGRP"){ 
			jquery("input[name='parameterValue']").val((jquery("input[name='sccGroup']").val())?jquery("input[name='sccGroup']").val():jquery("input[name='parameterValue']").val());
			jquery("input[name='sccGroup']").val((jquery("input[name='sccGroup']").val())?jquery("input[name='sccGroup']").val():jquery("input[name='parameterValue']").val());
	}else if(parameterCode=="AGT"){ 
			jquery("input[name='parameterValue']").val((jquery("input[name='agentCode']").val())?jquery("input[name='agentCode']").val():jquery("input[name='parameterValue']").val());
			jquery("input[name='agentCode']").val((jquery("input[name='agentCode']").val())?jquery("input[name='agentCode']").val():jquery("input[name='parameterValue']").val());
	}else if(parameterCode=="AGTGRP"){ 
			jquery("input[name='parameterValue']").val((jquery("input[name='agentGroup']").val())?jquery("input[name='agentGroup']").val():jquery("input[name='parameterValue']").val());
			jquery("input[name='agentGroup']").val((jquery("input[name='agentGroup']").val())?jquery("input[name='agentGroup']").val():jquery("input[name='parameterValue']").val());
	}else if(parameterCode=="SPLIT"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='splitIndicator']").val());
	}else if(parameterCode=="UNKSHP"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='unknownShipper']").val());
	}else if(parameterCode=="UNNUM"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='unNumber']").val());
	}else if(parameterCode=="WGT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='weight']").val());
		}
		else if(parameterCode=="NUMSTP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='numberOfstops']").val());
		}
	else if(parameterCode=="VOL"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='volume']").val());
		}	
		else if(parameterCode=="ULD"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='uldType']").val());
		}	
		else if(parameterCode=="ACRTYP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='aircraftType']").val());
		}	
		else if(parameterCode=="ACRTYPGRP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='aircraftGroupType']").val());
		}	
	else if(parameterCode=="LEN"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='parameterLength']").val());
		}else if(parameterCode=="WID"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='width']").val());
		}else if(parameterCode=="UNCLS"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='unIds']").val());
	}else if(parameterCode=="HGT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='height']").val());
	}else if(parameterCode=="AWBPRE"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='awbPrefix']").val());
	}
	else if(parameterCode=="MALCLS"){ 
	jquery("input[name='parameterValue']").val(jquery("select[name='mailClass']").val());
	}
	else if(parameterCode=="MALSUBCLS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='mailSubClass']").val());
		// Modified by A-5153 for ICRD-256937
		jquery("input[name='parameterValue']").val(jquery("input[name='mailSubClass']").val());
	}else if(parameterCode=="MALCAT"){ 
		jquery("input[name='parameterValue']").val(jquery("select[name='mailCategory']").val());
	}else if(parameterCode=="MALSUBCLSGRP"){ 
	jquery("input[name='parameterValue']").val(jquery("select[name='mailSubClsGrp']").val());
	}	
	else if(parameterCode=="SRVCRGCLS"){
		jquery("input[name='parameterValue']").val(jquery("select[name='serviceCargoClass']").val());
		//jquery(this).find("input[name='values']").val(jquery(this).find("input[name='serviceCargoClass']").val()); 
		//jquery("input[name='parameterValue']").val(jquery("input[name='serviceCargoClass']").val());
	}else if(parameterCode=="ACRCLS"){
		jquery("input[name='parameterValue']").val(jquery("input[name='aircraftClassification']").val());
	}else if(parameterCode=="SHP"){
		jquery("input[name='parameterValue']").val(jquery("input[name='shipperCode']").val());
	}else if(parameterCode=="CNS"){
		jquery("input[name='parameterValue']").val(jquery("input[name='consigneeCode']").val());
	}else if(parameterCode=="SHPGRP"){
		jquery("input[name='parameterValue']").val(jquery("input[name='shipperGroup']").val());
	}else if(parameterCode=="CNSGRP"){ 
		jquery("input[name='parameterValue']").val(jquery("input[name='consigneeGroup']").val());
	}else if(parameterCode=="SHPTYP"){ 
		jquery("input[name='parameterValue']").val(jquery("select[name='shipmentType']").val());
	}else if(parameterCode=="SRVCTYP"){ 
		jquery("input[name='parameterValue']").val(jquery("select[name='serviceType']").val());
	}else if(parameterCode=="PKGGRP"){
		jquery("input[name='parameterValue']").val(jquery("select[name='unPg']").val());
	}else if(parameterCode=="SRVCTYPFRTECSTP"){ 
		jquery("input[name='parameterValue']").val(jquery("select[name='serviceTypeForTechnicalStop']").val());
	}else if(parameterCode=="SUBRSK"){
		jquery("input[name='parameterValue']").val(jquery("select[name='subRisk']").val());
	}else if(parameterCode=="CNSL"){ 
		jquery("input[name='parameterValue']").val(jquery("select[name='consol']").val());
	}else if(parameterCode=="ARLGRP"){
			jquery("input[name='parameterValue']").val(jquery("input[name='airlineGroup']").val());
	}
	else{
			jquery("input[name='parameterValue']").val(jquery("input[name='defaultText']").val());
		}
	}
	else{
		jquery("input[name='parameterValue']").val(jquery("input[name='defaultText']").val());
	}
}
function validateTime(txtObj){
	

	if(txtObj.value.indexOf(':')<0){
	if(isNaN(txtObj.value)){
		showDialog(	
		{msg :"Invalid Time!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		txtObj.value="";
		}
	else{
			if(txtObj.value.indexOf('.')>0){
			showDialog(	
		{msg :"Invalid Time!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				
				txtObj.value="";
			}
			else{
		var hours = "0";
		var minutes = "00";
		var length = 0;

		if(txtObj.value != ""){
			hours = txtObj.value;
			length = hours.length;
		}
		
		else if(txtObj.value.trim().length==0){
		return true;
		}

		if(length>2) {
			minutes=hours.substring(2);
			hours = hours.substring(0,2);
		}

		
		var sTime = getFormattedMinutes(hours, minutes);
		
		txtObj.value = sTime;
	}
		}
	}
	else{
		if(txtObj.value.indexOf(',')>0){
		showDialog(	
		{msg :"Invalid Time!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				
				txtObj.value="";
		}
		else{
		time=txtObj.value.split(":");
			if(isNaN(time[0]) || isNaN(time[1]) || time.length>2){
				showDialog(	
		{msg :"Invalid Time!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				txtObj.value="";
			}
			else{
				var hours=time[0];
			var minutes="00";
				if(time[1]!=null)
			{
					if(time[1].length>0){
					minutes=time[1];	
				}
			}
			
			var sTime = getFormattedMinutes(hours, minutes);
			
			txtObj.value = sTime;
			}
		}
		
	}
	_pTime=txtObj.value.split(":");
	if(parseInt(_pTime[0])>23){
		showDialog(	
		{msg :"Invalid Time!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		txtObj.value="";
		txtObj.focus();	
	}
	
}
function getFormattedMinutes(hours, minutes) {

	var iHours, iMinutes;
	var sHours, sMinutes;
	var minutesToAdd = 0;
	
	if(hours.length>1){
		if(hours.charAt(0)=="0")
		iHours = parseInt(hours.substring(1));
		else
		iHours = parseInt(hours);
	}
	else{
		iHours = parseInt(hours);
	}

	//alert(hours);
	//alert(minutes);
	
	if(minutes.length>1){
			if(minutes.charAt(0)=="0")
			iMinutes = parseInt(minutes.substring(1));
			else
			iMinutes = parseInt(minutes);
	}
	else{
		iMinutes = parseInt(minutes);
	}
	
	if(iMinutes > 59) {
		minutesToAdd = iMinutes/60;
		iMinutes = iMinutes%60;
	}

	if(iHours<23){
	iHours = iHours + parseInt(minutesToAdd);
	}
	
	sHours = ""+iHours; sMinutes = ""+iMinutes;

	if(sHours.length<2) sHours = "0"+sHours;
	if(sMinutes.length<2) sMinutes = "0"+sMinutes;

	return sHours+":"+sMinutes;
}
function validateDims(object){
	var val = object.value.toUpperCase(); 
	
	if(val.indexOf('X')<1){
		showDialog(	
		{msg :"Invalid Dimension, Format is LXWXH!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		object.value="";
	}
	else{
		var str = val.split('X');
		if(!str[0] || !str[1] || !str[2] || str[3]){
			showDialog(	
		{msg :"Invalid Dimension, Format is LXWXH!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
			object.value="";
		}
		else{
			if(isNaN(str[0])){
			showDialog(	
		{msg :"Invalid Dimension, Format is LXWXH!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				object.value="";
			}
			else if(isNaN(str[1])){
				showDialog(	
		{msg :"Invalid Dimension, Format is LXWXH!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				object.value="";
			}
			else if(isNaN(str[2])){
				showDialog(	
		{msg :"Invalid Dimension, Format is LXWXH!!",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
				object.value="";
			}
		}
	}
}
function dateCheck(objName)
{
    var datefield = objName;
	var originalVal = objName.value;
	var invalid=false;
    trimFieldValue(objName);
	var changedVal;
	var splitDate = objName.value.split(',');
	if(splitDate.length>4){
	showDialog(	
		{msg :"Maximum 4 values allowed",
						type:1,
						parentWindow:self,                                       
						parentForm:document.forms[1],
						dialogId:'id_0',
						onClose: function (result) {
						
						}

        });
		
		objName.value="";
	}
	else{
	for(var i=0;i<splitDate.length;++i){
		datefield.value=splitDate[i];
		if(datefield.value.length>0){
			if (chkdate(datefield) == false){
				//alertPopup(2,"Invalid date",objName);
				//datefield.focus();
				showDialog(	
					{msg :"Invalid Date. Format should be dd-mmm-yyyy !!",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
			  
			  objName.value="";
			  invalid=true;
			}
			else{			
				if(changedVal!=null){
					changedVal = changedVal+','+datefield.value;
				}
				else
					changedVal = datefield.value;
			}
		}
	}
	if(changedVal!=null && !invalid)
		objName.value=changedVal;
	else
		objName.value="";
		}
    //alert("ok");
}
function trimFieldValue(field){

  var value = field.value;
  for(var i=0;i<value.length;++i){
   if(value.length>=1)   {
     var r = value.substring(0,1);
   	 if(r==" ")
   	   value = value.substring(1,value.length);
   	 else
   	    break;
   }
  }//for
  for(var i=value.length-1;i>=0;--i){
      if(value.length>=1){
      	var r = value.substring(value.length-1,value.length);
      	if(r==" ")
      	  value = value.substring(0,value.length-1);
      	else
      	   break;
      }
   }
   field.value = value;
   return true;
}


function chkdate(objName) {
    trimFieldValue(objName);

    //var strDatestyle = "US"; //United States date style
    var strDatestyle = "EU";  //European date style
    var strDate;
    var strDateArray;
    var strDay;
    var strMonth;
    var strYear;
    var intday;
    var intMonth;
    var intYear;
    var booFound = false;
    var datefield = objName;
    var strSeparatorArray = new Array("-"," ","/",".");
    var intElementNr;
    var err = 0;
    var strMonthArray = new Array(12);
    strMonthArray[0] = "Jan";
    strMonthArray[1] = "Feb";
    strMonthArray[2] = "Mar";
    strMonthArray[3] = "Apr";
    strMonthArray[4] = "May";
    strMonthArray[5] = "Jun";
    strMonthArray[6] = "Jul";
    strMonthArray[7] = "Aug";
    strMonthArray[8] = "Sep";
    strMonthArray[9] = "Oct";
    strMonthArray[10] = "Nov";
    strMonthArray[11] = "Dec";
    strDate = datefield.value;
    if (strDate.length < 6)
    {
        return false;
    }
    for (intElementNr = 0; intElementNr < strSeparatorArray.length;
                                                    intElementNr++)
    {
        if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
        strDateArray = strDate.split(strSeparatorArray[intElementNr]);
        if (strDateArray.length != 3) {
        err = 1;
        return false;
    }
    else
    {
        strDay = strDateArray[0];
        strMonth = strDateArray[1];
        strYear = strDateArray[2];
    }
    booFound = true;
       }
    }
    if (booFound == false)
    {
        if (strDate.length>5)
        {
            strDay = strDate.substr(0, 2);
            strMonth = strDate.substr(2, 2);
            strYear = strDate.substr(4);
            //if (strYear.length>4)
            //    strYear=strYear.substring(0,4);
        }
    }
    if (strYear.length == 2)
    {
        strYear = '20' + strYear;
    }
    else if (strYear.length == 3 || strYear.length == 1 || strYear.length >= 5)
    {
        return false;
    }
    else if (strYear.length == 1)
    {
        return false;
    }
    if(strYear.length==4 && strYear=="0000")
    {
        return false;
    }
    /*if(strYear.length>=4 && strYear.substring(0,4)=="0000")
    {
       return false;
    }
    else */
    if(isNaN(strYear))
    {
            return false;
    }

    // US style
    if (strDatestyle == "US")
    {
        strTemp = strDay;
        strDay = strMonth;
        strMonth = strTemp;
    }
    intday = parseInt(strDay, 10);
    if (isNaN(intday))
    {
        err = 2;
        return false;
    }
    intMonth = parseInt(strMonth, 10);
    if (isNaN(intMonth))
    {
        for (i = 0;i<12;i++)
        {
            if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase())
            {
                intMonth = i+1;
                strMonth = strMonthArray[i];
                i = 12;
            }
        }
        if (isNaN(intMonth))
        {
            err = 3;
            return false;
        }
    }
    intYear = parseInt(strYear, 10);
    if (isNaN(intYear))
    {
        err = 4;
        return false;
    }
    if (intMonth>12 || intMonth<1)
    {
        err = 5;
        return false;
    }
    if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7
                    || intMonth == 8 || intMonth == 10 || intMonth == 12)
                    && (intday > 31 || intday < 1))
    {
        err = 6;
        return false;
    }
    if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11)
                    && (intday > 30 || intday < 1))
    {
        err = 7;
        return false;
    }
    if (intMonth == 2)
    {
        if (intday < 1)
        {
            err = 8;
            return false;
        }
        if (LeapYear(intYear) == true)
        {
            if (intday > 29)
            {
                err = 9;
                return false;
            }
        }
        else
        {
            if (intday > 28)
            {
                err = 10;
                return false;
            }
        }
    }
    if(intday<10)
    {
      intday = "0"+intday;
    }
    if (strDatestyle == "US")
    {
        datefield.value = strMonthArray[intMonth-1] + " " + intday+" " + strYear;
    }
    else
    {
        datefield.value = intday + "-" + strMonthArray[intMonth-1] + "-" + strYear;
    }
    datefield.value=datefield.value.substring(0,11);
    
    	//added by Shiby
     
	if(strYear > 2999) {
		fnKeyGlobalErrors = true;
		showDialog(	
					{msg :"Year should not be greater than 2999",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
		
		datefield.focus();
		return true;
	}
					
    	return true;
}
function restrictValues(obj){
	var val=obj.value.split(',');
	if(val.length>4){
	showDialog(	
					{msg :"Maximum 4 values allowed",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
		
		
		obj.value="";
	}
	else if(obj.value.endsWith(',')){
		restrictSpecialChar(obj);
	}
}
function restrictSpecialChar(obj){
	if(obj.value.endsWith(',')){
		/*showDialog("Special characters not allowed",1,self);
		obj.value="";*/
		obj.value=obj.value.substring(0, obj.value.length - 1);
	}
}
function restrictToSingleNumber(obj){
	if(isNaN(obj.value)){
	showDialog(	
					{msg :"Enter valid number",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
		
		obj.value="";
	}
}
function validateNumber(obj){
	if(isNaN(obj.value)){
		if(obj.value==','){
		showDialog(	
					{msg :"Enter valid value",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
			
			obj.value="";
		}
		else{
			var val=obj.value.split(',');
			for(var i=0;i<val.length;++i){
				if(isNaN(val[i])){
					showDialog(	
					{msg :"Enter valid value",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
					obj.value="";
				}
			}
		}
	}
	else if(obj.value.indexOf('.')>-1){
		showDialog(	
					{msg :"Enter valid value",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
		obj.value="";
	}
}

function validateaplphanum(obj){
    var regx = /^[A-Za-z0-9]+$/;
	if(obj.value){
		if (!regx.test(obj.value)){
			if(obj.value==','){
				showDialog(	
					{msg :"Enter valid value",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
				obj.value="";
			}
			else{
				var val=obj.value.split(',');
				for(var i=0;i<val.length;++i){
					if(!val[i]==""){
					
						if(!regx.test(val[i])){
							showDialog(	
					{msg :"Enter valid value",
									type:1,
									parentWindow:self,                                       
									parentForm:document.forms[1],
									dialogId:'id_0',
									onClose: function (result) {
									
									}

					});
							obj.value="";
						}
					}
				}
			}
		}
	}
}
function displayProductLov(strAction,lovValue,textfiledObj,priorityValueObj,transportModeObj,formNumber,codeField,activeProduct,count){
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&priorityObject="+priorityValueObj+"&transportModeObject="+transportModeObj+"&formNumber="+formNumber+"&productCodeField="+codeField+"&activeProducts="+activeProduct+"&sourceScreen=B&rowIndex="+count;
	displayLOV(strUrl,'N','Y',strUrl,lovValue,'Parameter','1','productCode','',count);
}
function viewSelectedParameter(){
var parameterCode=jquery("select[name='parameterCode']").val();
	if(null !=parameterCode && "" !=parameterCode){
		jquery("div #parameter_control_"+parameterCode).removeAttr("style");
	}else{
		jquery("div #parameter_control_").removeAttr("style");
	}
}
function changeParameterValues(ctrl){
	jquery("div .parameter_control").css('display','none');
	jquery("div .parameter_control input").each(function(){
		jquery(this).val("");			
	});
	jquery("div #parameter_control_"+jquery(ctrl).val()).removeAttr("style");
}
function checkFlightNumber(obj) {
	if(obj.value.length==1) obj.value = "000"+obj.value;
	else if(obj.value.length==2) obj.value = "00"+obj.value;
	else if(obj.value.length==3) obj.value = "0"+obj.value;
}
//added for ICRD-254170 starts
function viewGroupingDetails(obj){
	prepareAttributes(obj,'parameterValuesPopUp','parameterValuesDiv');
}
function prepareAttributes(obj,div,divName){
		var invId=obj.id;
		var divId;
		var indexId=invId.split('_~|')[1];
		if(indexId != null && indexId != ""){
		 divId=div;
		}else{
		 divId=div+'';
		}
    var nw_arry = new Array();
      nw_arry = {
        "autoOpen" : false,
        "width" : 500,
        "height": 400,
         "draggable" :false,
          "resizable" :false
    };
    initDialog(divName,nw_arry);
	showInfoMessage(event,divId,invId,divName,280,120);
	getParameterGroupingDetails(obj,div);
}
function getParameterGroupingDetails(obj,div){
	if(obj != null) {
		var index = obj.id.split("_~|")[1];
		targetFormName.elements.groupType.value = obj.id.split("_~|")[3];
		targetFormName.elements.groupName.value = obj.id.split("_~|")[4];
		_divIdSeg="displayGroupingDetails";
		_currDivId=div;
		var strAction = "reco.defaults.findParamterGroupingDetailsForListEmbargo.do";
		asyncSubmit(targetFormName,strAction,_divIdSeg,null,null,_currDivId);	
	}
}
function displayGroupingDetails(_tableInfo){
	_resultDiv = _tableInfo.document.getElementById("ajax_groupingDetailsBodyForListEmbargo").innerHTML;
	jQuery('#'+_tableInfo.currDivId).find(".groupingDetailsBody").html(_resultDiv);  
}
//added for ICRD-254170 ends
function showAgentLOV(){
	var textfiledDesc="";
    var formCount=1;
	var code = targetFormName.elements.agentCode.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agentCode&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCode="+code;
	//openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=agentCode"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCode="+code,'700');
	openPopUpWithHeight(StrUrl,"500");
}
function showShipperLOV(){
	var code = "";
	if(targetFormName.elements.shipperCode.value != null){
		code = targetFormName.elements.shipperCode.value;
	}
	var StrUrl='showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code='+code+
	'&title=Customer Lov&formCount=1&lovTxtFieldName=shipperCode&lovDescriptionTxtFieldName=&index=0';
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
}
function showConsigneeLOV(){
	var code = "";
	if(targetFormName.elements.consigneeCode.value != null){
		code = targetFormName.elements.consigneeCode.value;
	}
	var StrUrl='showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code='+code+
	'&title=Customer Lov&formCount=1&lovTxtFieldName=consigneeCode&lovDescriptionTxtFieldName=&index=0';
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
}
