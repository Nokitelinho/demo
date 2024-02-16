<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
var targetFormName=document.forms[1];
  with(targetFormName){
   	evtHandler.addEvents("btnDisplay","displayEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnClear","clearEmbargo()",EVT_CLICK);
   	//evtHandler.addEvents("embargoDesc","return validateMaxLength(document.forms[1].embargoDesc,100)",EVT_BLUR);
   	//evtHandler.addEvents("remarks","return validateMaxLength(document.forms[1].remarks,50)",EVT_BLUR);
   	evtHandler.addEvents("btnSave","submitfrm(this.form,'saveEmbargo')",EVT_CLICK);
   	evtHandler.addEvents("btnClose","submitfrm(this.form,'closeEmbargo')",EVT_CLICK);
   	evtHandler.addEvents("btnClose","navigate(this.form)",EVT_BLUR);
	evtHandler.addEvents("btnApprove","approve(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnReject","reject(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnCancel","cancel(this.form)",EVT_CLICK);
   	//evtHandler.addIDEvents("isIncluded","clearEmbargoDetails()",EVT_CHANGE);
   	//evtHandler.addEvents("values","return validateMaxLength(document.forms[1].values,75)",EVT_BLUR);
	evtHandler.addIDEvents("refNumber","validateReferenceNumber()",EVT_BLUR);
	evtHandler.addIDEvents("linkAddTemplateRow","onaddLinkForGeographicLevel()",EVT_CLICK);	
	evtHandler.addIDEvents("linkDeleteTemplateRow","ondeleteLinkForGeographicLevel()",EVT_CLICK);
	evtHandler.addEvents("linkAddParameterTemplateRow","onaddLinkForParameterType()",EVT_CLICK);	
	evtHandler.addEvents("linkDeleteParameterTemplateRow","ondeleteLinkForParameterType()",EVT_CLICK);
	//evtHandler.addEvents("geoLevelLov","showGeoLevelLov(this)",EVT_CLICK);
   }

 if(targetFormName.elements.refNumberFlag.value!=null && targetFormName.elements.refNumberFlag.value == "true"){
	targetFormName.elements.refNumber.readOnly=true;
	if(targetFormName.elements.refNumber.value == ""){
		if(targetFormName.elements.isPrivilegedUser.value=="Y"){
		disableField(targetFormName.elements.btnApprove);
		disableField(targetFormName.elements.btnReject);
		disableField(targetFormName.elements.btnCancel);
	}
	}else{
		if(targetFormName.elements.isPrivilegedUser.value=="Y"){
			enableField(targetFormName.elements.btnApprove);
			enableField(targetFormName.elements.btnReject);
			enableField(targetFormName.elements.btnCancel);
		}
	}
	if(targetFormName.elements.status.value == "INACTIVE" || targetFormName.elements.status.value == "CANCELLED" || targetFormName.elements.status.value == "REJECTED"){
		disableLink(linkAddTemplateRow);
		disableLink(linkDeleteTemplateRow);
		disableField(targetFormName.elements.refNumber);
		if(targetFormName.isPrivilegedUser.value=="Y"){
		disableField(targetFormName.elements.btnApprove);
		disableField(targetFormName.elements.btnReject);
		disableField(targetFormName.elements.btnCancel);
		}
		disableField(targetFormName.elements.btnSave);
		disableField(targetFormName.elements.category);
		disableField(targetFormName.elements.startDate);
		disableField(targetFormName.elements.endDate);
		disableField(targetFormName.elements.isSuspended);
		disableField(targetFormName.elements.embargoDesc);
		disableField(targetFormName.elements.remarks);
		disableField(targetFormName.elements.scc);
		disableField(targetFormName.elements.sccGroup);
		var geographicLevel = document.getElementsByName('geographicLevel');
		for(var i=0;i<geographicLevel.length;i++){
			disableField(targetFormName.elements.geographicLevel[i]);
			disableField(targetFormName.elements.geographicLevelType[i]);
			disableField(targetFormName.elements.geographicLevelApplicableOn[i]);
			disableField(targetFormName.elements.geographicLevelValues[i]);
		}
	}
   }
   else{
		if(targetFormName.isPrivilegedUser.value=="Y"){
			disableField(targetFormName.elements.btnApprove);
			disableField(targetFormName.elements.btnReject);
			disableField(targetFormName.elements.btnCancel);
		}
   }
   setFocus(targetFormName);
}

function displayParamValueField(count) {
	var targetFormName=document.forms[1];
	var parameterCode = document.getElementsByName('parameterCode');
	var carrierCode = document.getElementsByName('carrierCode');
	var flightNumber = document.getElementsByName('flightNumber');
	var values = document.getElementsByName('values');
	for(var i = 0; i<= parameterCode.length; i++){
		if(i == count){
			var commonDiv = 'commonDiv'+''+count;
			var flightDiv = 'flightDiv'+''+count;
			var textDiv = 'textDiv'+''+count;
			if(parameterCode[i].value == 'FLTNUM'){
				document.getElementById(flightDiv).style.display='block';
				document.getElementById(commonDiv).style.display='none';
				document.getElementById(textDiv).style.display='none';
				values[i].value="";
				carrierCode[i].value="";
				flightNumber[i].value="";
				//natureOfGoods[i].value="";
			}else if(parameterCode[i].value == 'GOODS' || parameterCode[i].value == "UNNUM"){
				values[i].value="";
				carrierCode[i].value="";
				flightNumber[i].value="";
				//natureOfGoods[i].value="";
				document.getElementById(textDiv).style.display='block';
				document.getElementById(flightDiv).style.display='none';
				document.getElementById(commonDiv).style.display='none';
			}else {
				carrierCode[i].value="";
				flightNumber[i].value="";
				values[i].value="";
				document.getElementById(commonDiv).style.display='block';
				document.getElementById(flightDiv).style.display='none';
				document.getElementById(textDiv).style.display='none';
			}
		}
	}
}
function setFocus(frm){
	if(!frm.elements.refNumber.disabled && !frm.elements.refNumber.readOnly){
		frm.elements.refNumber.focus();			
	}		
}

function validateReferenceNumber(){
var targetFormName = document.forms[1];
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
		targetFormName.elements.btnClose.focus();
	}
}

//a-5165 for ICRD-28376-starts
function setFocusEndDate(frm){
	if(document.getElementById('endDate')!=null){
		if(event!=null && !event.shiftKey){
			frm.elements.isSuspended.focus();
		}
	}
}


//a-5165 for ICRD-28376-ends
/*function clearEmbargoDetails(){
targetFormName.parameterCode.value = "";
targetFormName.values.value = "";
}*/
function clearPreviousValue(args){
var targetFormName = document.forms[1];
	if(args == 'destination'){
		targetFormName.elements.destination.value="";
	}else if (args =='origin'){
		targetFormName.elements.origin.value="";
	} else if (args =='viaPoint'){
		targetFormName.elements.viaPoint.value="";
	}
}

/*
Method to restrict the length of the location value depending on the
value selected frim the combo.

if country then the max lengtth wil be 2
and if stqation max legnth is 3
*/
function checkLocationLengthOrigin(){
var targetFormName = document.forms[1];
var locationtype = targetFormName.elements.originType.value;
var locationvalue = document.getElementById('origin');
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
function checkLocationLengthDestination(){
var targetFormName = document.forms[1];
var locationtype = targetFormName.elements.destinationType.value;
var locationvalue = document.getElementById('destination');
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

/*
Method to restrict the length of the location value depending on the
value selected frim the combo.
if country then the max lengtth wil be 2
and if stqation max legnth is 3
*/
function checkLocationLengthViaPoint(){
var targetFormName = document.forms[1];
var locationtype = targetFormName.elements.viaPointType.value;
var locationvalue = document.getElementById('viaPoint');
	if(locationtype != null){
		if(locationtype =='C'){
			if(locationvalue != null){
				if(locationvalue.value.length>1){
					cancelPlatformEvent();
					return;
				}
			}
		}else{
			if(locationvalue != null){
				if(locationvalue.value.length>2){
					cancelPlatformEvent();
					return;
				}
			}
		}
	}
}
function selectLovType(param,count){
var targetFormName = document.forms[1];
var opFlagUpdate = targetFormName.elements.opFlagUpdate.value;
var parameterColl= targetFormName.elements.parameterColl.value;
var parameterCode = document.getElementsByName('parameterCode');
var values = document.getElementsByName('values');
var grpCategory ='GEN';
var counter = count;
	if(opFlagUpdate != 'U'){
	for(var i = 0; i<= parameterCode.length; i++){
		if(i == count){
			if(parameterCode[i].value != 'SCC' && parameterCode[i].value != 'COM' && parameterCode[i].value != 'CAR' && parameterCode[i].value != 'PAYTYP' &&parameterCode[i].value != 'SCCGRP' && parameterCode[i].value != 'PRD' ){
				showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
			}else if(parameterCode[i].value == 'SCC'){
				displayLOV('showScc.do','Y','Y','showScc.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'COM'){
				displayLOV('showCommodity.do','Y','Y','showCommodity.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'CAR'){
				displayLOV('showAirline.do','Y','Y','showAirline.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'PAYTYP'){
				//Modified by A-5153 for ICRD-50247
				//displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'tariff.others.paymenttype','');
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'reco.defaults.paymenttype','');
			}else if(parameterCode[i].value == 'SCCGRP'){
				showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',parameterCode,parameterCode[i].value,grpCategory,'values','1',count);
			}else if(parameterCode[i].value == 'PRD'){
				displayLOV('showProduct.do','Y','Y','showProduct.do',parameterCode,'Parameter','1','values','',count);
			}
		}
		}
	}else if(opFlagUpdate == 'U'){
	//if its U, then the lov to be displayed after the value of the count
		//alert("count -- "+count);
		/*for(var i=0; i<10; i++){
			if(parameterCode[i] != null){
				alert("parameterCode[i] -- "+parameterCode[i].value+ " count --"+i );
			}
		}*/
		var j = 0;
		if(count != "0"){
			j = count;
			//j++;
			//j++;

		}

		if(parameterCode[j] == null){
			j--;
			if(parameterCode[j] == null){
				j--;
			}
		}


//alert(j);


		if(parameterCode[j] != null){
			if(parameterCode[j].value != 'SCC' && parameterCode[j].value != 'COM' && parameterCode[j].value != 'CAR' && parameterCode[j].value != 'PAYTYP'){
				showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
			}else if(parameterCode[j].value == 'SCC'){
				displayLOV('showScc.do','Y','Y','showScc.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'COM'){
				displayLOV('showCommodity.do','Y','Y','showCommodity.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'CAR'){
				displayLOV('showAirline.do','Y','Y','showAirline.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'PAYTYP'){
				//Modified by A-5153 for ICRD-50247
				//displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'tariff.others.paymenttype','');
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'reco.defaults.paymenttype','');
			}
		}
	/*	parameterColl++;
		count++;
	for(var j = 0 ; j< parameterColl; j++){
		if(j == count){
			if(parameterCode[j] != null){
					alert("j --- "+j+" value "+parameterCode[j].value);
					j++;
					if(parameterCode[j] == null){
						j--;
					}
				if(parameterCode[j].value != 'SCC' && parameterCode[j].value != 'COM' && parameterCode[j].value != 'CAR' && parameterCode[j].value != 'PAYTYP'){
						showDialog('<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>', 1, self,frm);
					}else if(parameterCode[j].value == 'SCC'){
						displayLOV('showScc.do','Y','Y','showScc.do',parameterCode,'Parameter','1','values','',counter);
					}else if(parameterCode[j].value == 'COM'){
						displayLOV('showCommodity.do','Y','Y','showCommodity.do',parameterCode,'Parameter','1','values','',counter);
					}else if(parameterCode[j].value == 'CAR'){
						displayLOV('showAirline.do','Y','Y','showAirline.do',parameterCode,'Parameter','1','values','',counter);
					}else if(parameterCode[i].value == 'PAYTYP'){
					displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'tariff.others.paymenttype','');
				}
			}
		}
		}*/

	}
}

function addrow(){
	var targetFormName = document.forms[1];
	targetFormName.action = "reco.defaults.addrow.do";
	targetFormName.submit();
}

function deleteRow(){
   if(validateSelectedCheckBoxesForEmbargo(document.forms[1],'rowId',10,1)){
        //doCheckEmbargoDetails(document.forms[0]);
		document.forms[1].action = "reco.defaults.deleterow.do";
		document.forms[1].submit();
	}
}

function submitfrm(frm,strAction){
	if(strAction=='saveEmbargo'){
		frm.action="reco.defaults.saveCompliance.do";
		frm.submit();
	}else if(strAction=='closeEmbargo'){
		if(frm.isDisplayDetails.value=="true"){
			frm.elements.isButtonClicked.value = "Y";
			frm.action="reco.defaults.maintaincomplianceinfoclosecommand.do";
			frm.submit();
		}else{
			frm.action= "home.do";
			frm.submit();
		}
	}
}

function validateSelectedCheckBoxesForEmbargo(frmObj,childCheckBoxName,maxNumSelected,minSelected){
var nMaxNumSelected = 0;
	if(maxNumSelected != null)
		nMaxNumSelected  = parseInt(maxNumSelected);
	var nMinSelected = 0;
	if(minSelected != null)
		nMinSelected  = parseInt(minSelected);
	if(nMinSelected > nMaxNumSelected){
		showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.minimumRecordsGreater" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						targetFormName.elements.geographicLevel.focus();
					}
				});
		return false;
	}
	var nNumSelected = 0;
	for(var i=0;i<frmObj.elements.length;i++){
		if(frmObj.elements[i].type=="checkbox"){
		if(frmObj.elements[i].name == childCheckBoxName){
				if(frmObj.elements[i].checked == true){
					nNumSelected++;
				}
			}
		}
	}
	if(nNumSelected < nMinSelected){
		showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseSelectMinimumRows" scope="request"/>' +minSelected+ ' row.',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
		return false;
	}else if(nNumSelected > nMaxNumSelected ){
		showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.maximumrowstobeselected" scope="request"/>' +maxNumSelected+ ' row(s) can be selected',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
		return false;
	}else
		return true;
}

function makeNonEditable(formObj, fieldObj, isEnabled,image){
	fieldObj.disabled = isEnabled;
	image.disabled=true;
}

/**
 * Method to handle operation flags in the Embargo Parameter table
 */
 
function doCheckEmbargoDetails(frm) {
 	var rowIds = eval(frm.elements.rowId);
 	var operationFlag = eval(frm.elements.operationFlag);   //for operation flag
 	var parameterCode= eval(frm.elements.parameterCode); //for column1
 	var isIncluded= eval(frm.elements.isIncluded);   //for column2
 	var values = eval(frm.elements.values);
 	if (rowIds != null) {
 		if (rowIds.length > 1) {
 			for (var i = 0; i < rowIds.length; i++) {
 				var checkBoxValue = rowIds[i].value;
 				if(operationFlag[checkBoxValue].value !='D' &&
 				   operationFlag[checkBoxValue].value !='U'){
 					if (parameterCode[checkBoxValue].value != parameterCode[checkBoxValue].defaultValue
 						|| isIncluded[checkBoxValue].value != isIncluded[checkBoxValue].defaultValue
						|| values[checkBoxValue].value != values[checkBoxValue].defaultValue){
 						if(operationFlag[checkBoxValue].value != 'I') {
 							operationFlag[checkBoxValue].value='U';
 						}
 					}
 				}
 			}
 		}else {
 			if(operationFlag.value !='D'){
 				if (parameterCode.value != parameterCode.defaultValue
 					|| isIncluded.value != isIncluded.defaultValue
 					|| values.value != values.defaultValue){
 					if(operationFlag.value !='I') {
 						operationFlag.value = 'U';
 					}
 				}
 			}
 		}
 	}
}

function checkStation(){
var targetFormName=document.forms[1];
var origin=targetFormName.elements.originStation.value;
var destination=targetFormName.elements.destnStation.value;
	if(origin!="" && destination!="") {
		if(origin==destination){
		  showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.originanddestinationsame" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
		  return false;
		}
	}
  	return true;
}

function displayEmbargo(){
var targetFormName=document.forms[1];
	if(targetFormName.elements.refNumber.value !=""){
		submitForm(targetFormName,"reco.defaults.modifyComplianceScreenLoad.do?fromList=true");
	}else{
		showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.entercompliancenumber" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
	}
}

function clearEmbargo(){
var targetFormName=document.forms[1];
	targetFormName=document.forms[1];
	targetFormName.action="reco.defaults.maintaincomplianceinfoscreenload.do";
	targetFormName.method="post";
	targetFormName.submit();
}

function confirmMessage(){
var targetFormName=document.forms[1];
targetFormName.elements.refNumber.value="";
targetFormName.elements.refNumber.disabled=true;
}

function nonconfirmMessage(){
var targetFormName=document.forms[1];
	targetFormName.elements.refNumber.value="";
	targetFormName.action="reco.defaults.maintaincomplianceinfoscreenload.do";
	targetFormName.method="post";
	targetFormName.submit();
}

function showOrigin(){
var targetFormName=document.forms[1];
var originType=targetFormName.elements.originType.value;
var origin=targetFormName.elements.origin.value;
var grpCategory ='GEN';
	if(originType!=null && originType.length>0){
		if(originType == "S"){
			displayLOV('showStation.do','N','Y','showStation.do',origin,'origin','1','origin','','0');
		}
		if(originType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',origin,'origin','1','origin','','0');
		}
		if(originType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',origin,'origin','1','origin','','0');
		}
		if( originType == "ARPGRP" || originType == "CNTGRP" || originType ==  "STNGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',origin,originType,grpCategory,'origin','1','0');
		}
	}else {
		showDialog({
					msg:'Please Select a Origin Type',
					type:2,
					parentWindow:self,		
					onClose:function(result){
	
					}
				});
	}
}
function showDestination(){
var targetFormName=document.forms[1];
var destinationType=targetFormName.elements.destinationType.value;
var destination=targetFormName.elements.destination.value;
var grpCategory ='GEN';
	if(destinationType!=null && destinationType.length>0){
		if(destinationType == "S"){
			displayLOV('showStation.do','N','Y','showStation.do',destination,'destination','1','destination','','0');
		}
		if(destinationType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',destination,'destination','1','destination','','0');
		}
		if(destinationType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',destination,'destination','1','destination','','0');
		}
		if( destinationType == "ARPGRP" || destinationType == "CNTGRP" || destinationType ==  "STNGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',destination,destinationType,grpCategory,'destination','1','0');
		}
	}else {
		showDialog({
					msg:'Please Select a Destination Type',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
	}
}
function showViaPoint(){
var targetFormName=document.forms[1];
var viaPointType = targetFormName.elements.viaPointType.value;
var viaPoint = targetFormName.elements.viaPoint.value;
	if(viaPointType!=null && viaPointType.length>0){
		if(viaPointType == "S"){
			displayLOV('showStation.do','N','Y','showStation.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
		if(viaPointType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
	}else {
		showDialog({
					msg:'Please Select a Via Point Type',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
	}
}
//added by A-5135 for bug ICRD-18408 starts
function navigate(frm){
 	if(!event.shiftKey){
 		if(!frm.elements.refNumber.disabled && !frm.elements.refNumber.readOnly){
 			frm.elements.refNumber.focus();
		}
 	}else {
		frm.elements.btnSave.focus();
	}
}
//added by A-5135 for bug ICRD-18408 ends

function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,textfieldObj,formNumber,index){
var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);
	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}
	var strUrl = strAction+"?multiSelect="+multiSelect+"&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&textfieldObj="+textfieldObj+"&formNumber="+formNumber+"&index="+index;
	//openPopUp(strUrl,width=370,height=330);
	displayLOV(strUrl,'Y','Y',strUrl,groupNameVal,'groupNameVal','1','geographicLevelValues','',index,'restrictGeoValue('+index+')');
	//openPopUp(strUrl,370,425);
	//var myWindow = window.open(strUrl, "LOV", 'status,width=370,height=330,screenX=100,screenY=30,left=250,top=200');
}

function onaddLinkForGeographicLevel(){
	addTemplateRow('listSelectedDataTemplateRow','listSelectedData','glOperationFlag');
}

function ondeleteLinkForGeographicLevel(){
	deleteTableRow('index','glOperationFlag');
}
	
function showGeographicLevelLov(frm,index){
var geographicLevelType=frm.elements.geographicLevelType[index].value;
var geographicLevel=frm.elements.geographicLevelValues[index].value;
var grpCategory ='GEN';
	if(geographicLevelType == "C"){
		displayLOV('showCountry.do','Y','Y','showCountry.do',geographicLevel,'geographicLevel','4','geographicLevelValues','',index,'restrictGeoValue('+index+')');
	}
	if(geographicLevelType == "A"){
		displayLOV('showAirport.do','Y','Y','showAirport.do',geographicLevel,'geographicLevel','4','geographicLevelValues','',index,'restrictGeoValue('+index+')');
	}
	if( geographicLevelType == "ARPGRP" || geographicLevelType == "CNTGRP" ){
		showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',geographicLevel,geographicLevelType,grpCategory,'geographicLevelValues','1',index);
	}
}

function showGeoLevelLov(obj,frm) {
var index = obj.id.split('geoLevelLov')[1];
var geographicLevelType=frm.elements.geographicLevelType[index].value;
var geographicLevel=frm.elements.geographicLevelValues[index].value;
var grpCategory ='GEN';
	if(geographicLevelType == "C"){
		displayLOV('showCountry.do','Y','Y','showCountry.do',geographicLevel,'geographicLevel','4','geographicLevelValues','',index,'restrictGeoValue('+index+')');
	}
	if(geographicLevelType == "A"){
		displayLOV('showAirport.do','Y','Y','showAirport.do',geographicLevel,'geographicLevel','4','geographicLevelValues','',index,'restrictGeoValue('+index+')');
	}
	if( geographicLevelType == "ARPGRP" || geographicLevelType == "CNTGRP" ){
		showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',geographicLevel,geographicLevelType,grpCategory,'geographicLevelValues','1',index);
	}
}

function onaddLinkForParameterType(){
	addTemplateRow('listParameterDataTemplateRow','listParameterData','paramOperationalFlag');
}

function ondeleteLinkForParameterType(){
	deleteTableRow('check','paramOperationalFlag');	
}

function approve(frm){
	frm.action="reco.defaults.saveCompliance.do?isApproveFlag=Y";
	frm.submit();
}

function reject(frm){
	submitForm(frm,"reco.defaults.rejectCompliance.do");
}

function cancel(frm){
	submitForm(frm,"reco.defaults.cancelCompliance.do");
}

/*function showLovForTemplate(obj,frm,parameterCode){
	var index = obj.id.split('parameterLov')[1];
	selectLovType(parameterCode,index);
}*/

function clearValues(obj,frm){
var index = obj.id.split('index')[1];
	if(frm.elements.geographicLevelValues[index]){
		frm.elements.geographicLevelValues[index].value="";
	}
}

function showParameterLov(){
var targetFormName=document.forms[1];
var paramCode = targetFormName.elements.parameterCode.value;
var values = targetFormName.elements.parameterValue.value;
var grpCategory ='GEN';
	if(paramCode != 'SCC' && paramCode != 'SCCGRP' && paramCode != 'PRD'){
		showDialog({
					msg:'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>',
					type:2,
					parentWindow:self,		
					onClose:function(result){

					}
				});
	}else if(paramCode == 'SCC'){
		displayLOV('showScc.do','Y','Y','showScc.do',paramCode,'Parameter','1','parameterValue','',0);
	}else if(paramCode == 'SCCGRP'){
		showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',paramCode,paramCode,grpCategory,'parameterValue','1',0);
	}else if(paramCode == 'PRD'){
		displayProductLov('products.defaults.screenloadProductLov.do',values,'productName','','','0','productCode','Y',0)
	}	
}

function displayProductLov(strAction,lovValue,textfiledObj,priorityValueObj,transportModeObj,formNumber,codeField,activeProduct,count){
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&priorityObject="+priorityValueObj+"&transportModeObject="+transportModeObj+"&formNumber="+formNumber+"&productCodeField="+codeField+"&activeProducts="+activeProduct+"&sourceScreen=B&rowIndex="+count;
	displayLOV(strUrl,'Y','Y',strUrl,lovValue,'Parameter','1','parameterValue','',count);
}

function clearParamValues(obj,frm){
	frm.elements.parameterValue.value="";
}

function restrictValues(obj){
	var val=obj.value.split(',');
	if(val.length>4){
		showDialog({
					msg:'Maximum 4 values allowed',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
		obj.value="";
	}
}

function restrictGeoValue(index){
var targetFormName=document.forms[1];
var handlCodes=targetFormName.elements.geographicLevelValues[index].value;
var val=handlCodes.split(',');
	if(val.length>4){
		showDialog({
					msg:'Maximum 4 values allowed',
					type:2,
					parentWindow:self,		
					onClose:function(result){
						
					}
				});
		targetFormName.elements.geographicLevelValues[index].value="";
	}
}