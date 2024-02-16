<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

var lovWindow;

function screenSpecificEventRegister(){
	var frm=targetFormName;
	collapseAllRows();
	stripedTable();
	addIEonScroll();
	//DivSetVisible(true);
	convertToString('daysOfOperation');
	initializeMultiSelectedValues();
	
	with(frm){
		evtHandler.addEvents("btnDisplay","displayEmbargo()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearEmbargo()",EVT_CLICK);
		//evtHandler.addEvents("embargoDesc","return validateMaxLength(document.forms[1].embargoDesc,100)",EVT_BLUR);
		//evtHandler.addEvents("remarks","return validateMaxLength(document.forms[1].remarks,50)",EVT_BLUR);
		evtHandler.addIDEvents("addEmbargo","addrow()",EVT_CLICK);
		evtHandler.addIDEvents("deleteEmbargo","deleteRow()",EVT_CLICK);
		evtHandler.addEvents("btnSave","submitfrm(this.form,'saveEmbargo')",EVT_CLICK);
		evtHandler.addEvents("btnClose","submitfrm(this.form,'closeEmbargo')",EVT_CLICK);
		evtHandler.addEvents("btnClose","navigate(this.form)",EVT_BLUR);
		evtHandler.addEvents("btnApprove","approve(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnReject","reject(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnCancel","cancel(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnConstruct","constructDescription()",EVT_CLICK);
		evtHandler.addEvents("btnDuplicate","duplicatecheck(this.form)",EVT_CLICK);

		evtHandler.addIDEvents("originLov","showOrigin()",EVT_CLICK);
		evtHandler.addIDEvents("destinationLov","showDestination()",EVT_CLICK);
		evtHandler.addIDEvents("viaPointLov","showViaPoint()",EVT_CLICK);
		//evtHandler.addIDEvents("isIncluded","clearEmbargoDetails()",EVT_CHANGE);
		//evtHandler.addEvents("values","return validateMaxLength(document.forms[1].values,75)",EVT_BLUR);
		 evtHandler.addIDEvents("origin","checkLocationLengthOrigin()",EVT_KEYPRESS);
		evtHandler.addIDEvents("destination","checkLocationLengthDestination()",EVT_KEYPRESS);
		evtHandler.addIDEvents("destinationType","clearPreviousValue('destination')",EVT_CHANGE);
		evtHandler.addIDEvents("originType","clearPreviousValue('origin')",EVT_CHANGE);
		evtHandler.addIDEvents("refNumber","validateReferenceNumber()",EVT_BLUR);
		//a-5165 for ICRD-28376
		//evtHandler.addEvents("endDate","setFocusEndDate(this.form)",EVT_BLUR);//Commented by A-5234 for ICRD-41588
		evtHandler.addEvents("embargoLevel","setFocusEmbargoLevel(this.form)",EVT_BLUR);
		evtHandler.addIDEvents("viaPoint","checkLocationLengthViaPoint()",EVT_KEYPRESS);
		evtHandler.addIDEvents("viaPointType","clearPreviousValue('viaPoint')",EVT_CHANGE);

		evtHandler.addIDEvents("linkAddTemplateRow","onaddLinkForGeographicLevel()",EVT_CLICK);	
		evtHandler.addIDEvents("linkDeleteTemplateRow","ondeleteLinkForGeographicLevel()",EVT_CLICK);
		evtHandler.addEvents("linkAddParameterTemplateRow","onaddLinkForParameterType()",EVT_CLICK);	
		evtHandler.addEvents("linkDeleteParameterTemplateRow","ondeleteLinkForParameterType()",EVT_CLICK);
		//evtHandler.addEvents("geoLevelLov","showGeoLevelLov(this)",EVT_CLICK);
	}
	
	 if(targetFormName.elements.refNumberFlag.value != null 
			&& targetFormName.elements.refNumberFlag.value == "true"){
		frm.elements.refNumber.readOnly=true;
		if(targetFormName.elements.refNumber.value == ""){
			if(targetFormName.elements.isPrivilegedUser.value=="Y"){
				disableField(frm.elements.btnApprove);
				disableField(frm.elements.btnReject);		
				disableField(frm.elements.btnCancel);
			}
		}else{
			if(targetFormName.elements.isPrivilegedUser.value=="Y"){
				enableField(frm.elements.btnApprove);
				enableField(frm.elements.btnReject);
				enableField(frm.elements.btnCancel);
			}
		}
		if(targetFormName.elements.status.value == "INACTIVE" 
				|| targetFormName.elements.status.value == "CANCELLED" 
				|| targetFormName.elements.status.value == "REJECTED"){
			disableLink(linkAddTemplateRow);
			disableLink(linkDeleteTemplateRow);
			disableLink(moreLink);
			disableLink(linkAddParameterTemplateRow);
			disableLink(linkDeleteParameterTemplateRow);
			disableField(frm.elements.refNumber);
			if(targetFormName.elements.isPrivilegedUser.value=="Y"){
				disableField(frm.elements.btnApprove);
				disableField(frm.elements.btnReject);
				disableField(frm.elements.btnCancel);
			}
			
			disableField(frm.elements.btnSave);
			disableField(frm.elements.btnDuplicate);
			disableField(frm.elements.btnConstruct);
			disableField(frm.elements.category);
			disableField(frm.elements.complianceType);
			disableField(frm.elements.applicableTransactions);
			disableField(frm.elements.embargoLevel);
			disableField(frm.elements.startDate);
			disableField(frm.elements.endDate);
			disableField(frm.elements.isSuspended);
			disableField(frm.elements.embargoDesc);
			disableField(frm.elements.remarks);
			disableField(frm.elements.daysOfOperation);
			disableField(frm.elements.daysOfOperationApplicableOn);
			var geographicLevel = document.getElementsByName('geographicLevel');
			for(var i=0;i<geographicLevel.length;i++){
				disableField(frm.elements.geographicLevel[i]);
				disableField(frm.elements.geographicLevelType[i]);
				disableField(frm.elements.geographicLevelApplicableOn[i]);
				disableField(frm.elements.geographicLevelValues[i]);
			}
			var parameters = document.getElementsByName('parameterCode');
			for(var i=0;i<parameters.length;i++){
				disableField(frm.elements.parameterCode[i]);
				if(frm.elements.applicableOn[i]){
					disableField(frm.elements.applicableOn[i]);
				}
				if(frm.elements.isIncluded[i]){
					disableField(frm.elements.isIncluded[i]);
				}
				if(frm.elements.defaultText[i]){
					disableField(frm.elements.defaultText[i]);
					
				}
				if(frm.elements.carrierCode[i]){
					disableField(frm.elements.carrierCode[i]);
						
				}
				if(frm.elements.flightNumber[i]){
					disableField(frm.elements.flightNumber[i]);
							
				}
				if(frm.elements.flightType[i]){
					disableField(frm.elements.flightType[i]);
						
				}
				if(frm.elements.uldPos[i]){
					disableField(frm.elements.uldPos[i]);						
				}
				if(frm.elements.serviceCargoClass[i]){
					disableField(frm.elements.serviceCargoClass[i]);						
				}
				if(frm.elements.uldTyp[i]){
					disableField(frm.elements.uldTyp[i]);				
				}
				if(frm.elements.flightOwner[i]){
					disableField(frm.elements.flightOwner[i]);
					
				}
				if(frm.elements.splitIndicator[i]){
					disableField(frm.elements.splitIndicator[i]);
					
				}
				if(frm.elements.daysOfWeek[i]){
					disableField(frm.elements.daysOfWeek[i]);
					
				}
				if(frm.elements.carrier[i]){
					disableField(frm.elements.carrier[i]);
					
				}
				if(frm.elements.commodity[i]){
					disableField(frm.elements.commodity[i]);
				
				}				
				if(frm.elements.productCode[i]){
					disableField(frm.elements.productCode[i]);
				
				}
				if(frm.elements.agentCode[i]){
					disableField(frm.elements.agentCode[i]);
				
				}
				if(frm.elements.agentGroup[i]){
					disableField(frm.elements.agentGroup[i]);
				}
				if(frm.elements.shipperCode[i]){
					disableField(frm.elements.shipperCode[i]);
				}
				if(frm.elements.shipperGroup[i]){
					disableField(frm.elements.shipperGroup[i]);
				}
				if(frm.elements.consigneeCode[i]){
					disableField(frm.elements.consigneeCode[i]);
				}
				if(frm.elements.consigneeGroup[i]){
					disableField(frm.elements.consigneeGroup[i]);
				}
				
				/*if(frm.elements.time[i]){
					disableField(frm.elements.time[i]);
				
				}*/
				if(frm.elements.scc[i]){
					disableField(frm.elements.scc[i]);
				}
				if(frm.elements.sccGroup[i]){
					disableField(frm.elements.sccGroup[i]);
								
				}
				
				if(frm.elements.aircraftClassification[i]){
					disableField(frm.elements.aircraftClassification[i]);
				
				}	
			}
		}
	} else {
		if(targetFormName.elements.isPrivilegedUser.value=="Y"){
			disableField(frm.elements.btnApprove);
			disableField(frm.elements.btnReject);
			disableField(frm.elements.btnCancel);
		}
	}

	setFocus(frm);
	if(targetFormName.elements.isDuplicatePresent.value!=null){
		if(targetFormName.elements.isDuplicatePresent.value == "Y"){
			openPopUp("reco.defaults.duplicateembargos.do",650,400);
			targetFormName.elements.isDuplicatePresent.value = "N"
		}
	}
}

function duplicatePopupClose(){
	window.close();
}

function displayParamValueField(count) {
	var frm = targetFormName;
	var parameterCode = document.getElementsByName('parameterCode');
	var carrierCode = document.getElementsByName('carrierCode');
	var flightNumber = document.getElementsByName('flightNumber');
	//var natureOfGoods = document.getElementsByName('natureOfGoods');
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
			} 
			else if(parameterCode[i].value == 'GOODS' || parameterCode[i].value == "UNNUM"){
				values[i].value="";
				carrierCode[i].value="";
				flightNumber[i].value="";
				//natureOfGoods[i].value="";
				document.getElementById(textDiv).style.display='block';
				document.getElementById(flightDiv).style.display='none';
				document.getElementById(commonDiv).style.display='none';
			}
			else {
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
	} else {
		frm.elements.embargoLevel.focus();
		setFocusEmbargoLevel();
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

	if(event != null && event.shiftKey){ 
		targetFormName.elements.btnClose.focus();
	}
}

function setFocusEndDate(frm){
	if(document.getElementById('endDate')!=null){
		if(event!=null && !event.shiftKey){
			frm.elements.isSuspended.focus();
		}
	}
}

function setFocusEmbargoLevel(frm){
	if(event!=null && event.shiftKey){
		frm.elements.btnClose.focus();
	}
}

//a-5165 for ICRD-28376-ends
/*function clearEmbargoDetails(){
targetFormName.elements.parameterCode.value = "";
targetFormName.elements.values.value = "";
}*/

function clearPreviousValue(args){
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
	var frm = targetFormName;
	var locationtype = frm.elements.originType.value;
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

function checkLocationLengthDestination(){
	var frm = targetFormName;
	var locationtype = frm.elements.destinationType.value;
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
		}else {
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
	var frm = targetFormName;
	var locationtype = frm.elements.viaPointType.value;
	//var locationtype = document.getElementById(locationType);
	var locationvalue = document.getElementById('viaPoint');
	//var locationvalue =document.getElementById(locationValue);
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

	var opFlagUpdate = targetFormName.elements.opFlagUpdate.value;
	var parameterColl= targetFormName.elements.parameterColl.value;
	var frm = targetFormName;
	var parameterCode = document.getElementsByName('parameterCode');
	var values = document.getElementsByName('values');
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	var counter = count;
	if(opFlagUpdate != 'U'){
	for(var i = 0; i<= parameterCode.length; i++){
		if(i == count){
			if(parameterCode[i].value != 'SCC' && parameterCode[i].value != 'COM' && parameterCode[i].value != 'CAR' && parameterCode[i].value != 'PAYTYP' &&parameterCode[i].value != 'SCCGRP' && parameterCode[i].value != 'PRD' && parameterCode[i].value != 'ULDTYP' && parameterCode[i].value != 'ACRCLS' ){
				showDialog({	
					msg		:	"<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
			
			}else if(parameterCode[i].value == 'SCC'){
				displayLOV('showScc.do','Y','Y','showScc.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'COM'){
				displayLOV('showCommodity.do','Y','Y','showCommodity.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'ULDTYP'){
				displayLOV('showUld.do','Y','Y','showUld.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'CAR'){
				displayLOV('showAirline.do','Y','Y','showAirline.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'PAYTYP'){
				//Modified by A-5153 for ICRD-50247
				//displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'tariff.others.paymenttype','');
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'reco.defaults.paymenttype','');
			}else if(parameterCode[i].value == 'SCCGRP' || parameterCode[i].value == 'ARLGRP'){
				showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',parameterCode,parameterCode[i].value,grpCategory,'values','1',count);
			}else if(parameterCode[i].value == 'PRD'){
				displayLOV('showProduct.do','Y','Y','showProduct.do',parameterCode,'Parameter','1','values','',count);
			}else if(parameterCode[i].value == 'ACRCLS'){
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Aircraft Classification','1','values','values',count,'shared.aircraft.aircraftClassification','');
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
			if(parameterCode[j].value != 'SCC' && parameterCode[j].value != 'COM' && parameterCode[j].value != 'CAR' && parameterCode[j].value != 'PAYTYP' && parameterCode[j].value != 'ULDTYP' && parameterCode[i].value != 'ACRCLS'){
				showDialog({	
					msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
			}else if(parameterCode[j].value == 'SCC'){
				displayLOV('showScc.do','Y','Y','showScc.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'COM'){
				displayLOV('showCommodity.do','Y','Y','showCommodity.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'ULDTYP'){
				displayLOV('showUld.do','Y','Y','showUld.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'CAR'){
				displayLOV('showAirline.do','Y','Y','showAirline.do',parameterCode,'Parameter','1','values','',counter);
			}else if(parameterCode[j].value == 'PAYTYP'){
				//Modified by A-5153 for ICRD-50247
				//displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'tariff.others.paymenttype','');
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Payment Type','1','values','values',count,'reco.defaults.paymenttype','');
			}else if(parameterCode[j].value == 'ACRCLS'){
				displayOneTimeLOV('screenloadOneTime.do','Y','Y','screenloadOneTime.do','','','Aircraft Classification','1','values','values',count,'shared.aircraft.aircraftClassification','');
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
						showDialog({	
							msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseentertheparameter" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
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
	var frm = document.forms[1];
	//doCheckEmbargoDetails(frm);
	frm.action = "reco.defaults.addrow.do";
	frm.submit();
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
		frm.action="reco.defaults.saveEmbargoDetails.do";
		setParameterValues();
		frm.submit();
	}else if(strAction=='closeEmbargo'){
		if(frm.elements.isDisplayDetails.value=="true"){
			frm.elements.isButtonClicked.value = "Y";
			frm.action="reco.defaults.closecommand.do";
			frm.submit();
		}else{
			frm.action=appPath + "/home.jsp";
			frm.submit();
		}
	}
}

function validateSelectedCheckBoxesForEmbargo(frmObj,childCheckBoxName,maxNumSelected,minSelected)
{
	var nMaxNumSelected = 0;
	if(maxNumSelected != null)
		nMaxNumSelected  = parseInt(maxNumSelected);

	var nMinSelected = 0;
	if(minSelected != null)
		nMinSelected  = parseInt(minSelected);

	if(nMinSelected > nMaxNumSelected){
		//alert('Minimum number of records cannot be more than the maximum number of records selected');
		showDialog({	
			msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.minimumRecordsGreater" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
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
		//alert('Please select '+minSelected+ ' row.');
		showDialog({	
			msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.pleaseSelectMinimumRows" scope="request"/>' +minSelected+ ' row.',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return false;
	}
	else if(nNumSelected > nMaxNumSelected ){
		//alert('A maximum of only '+maxNumSelected+ ' row(s) can be selected');
		showDialog({	
			msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.maximumrowstobeselected" scope="request"/>' +maxNumSelected+ ' row(s) can be selected',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return false;
	}
	else
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
						|| values[checkBoxValue].value != values[checkBoxValue].defaultValue
 						)
 					{
 						if(operationFlag[checkBoxValue].value != 'I') {
 							operationFlag[checkBoxValue].value='U';
 						}
 					}
 				}

 			}
 		} else {
 			if(operationFlag.value !='D'){
 				if (parameterCode.value != parameterCode.defaultValue
 					|| isIncluded.value != isIncluded.defaultValue
 					|| values.value != values.defaultValue
 					)
 				{
 					if(operationFlag.value !='I') {
 						operationFlag.value = 'U';
 					}
 				}
 			}

 		}
 	}

}

function checkStation(){
	var origin=document.forms[1].originStation.value;
	var destination=document.forms[1].destnStation.value;
	if(origin!="" && destination!="") {
		if(origin==destination){
			showDialog({	
				msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.originanddestinationsame" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
		  return false;
		}
	}
  	return true;
}

function displayEmbargo(){
var frm = targetFormName;
	if(frm.elements.refNumber.value !=""){

		//frm.elements.rowId[1].value = frm.elements.refNumber.value;
		//frm.action="reco.defaults.modifyScreenLoad.do";
		//frm.method="post";
		//frm.submit();

		submitForm(frm,"reco.defaults.modifyScreenLoad.do?fromList=true");
	}else{
		showDialog({	
			msg		:	'<common:message bundle="embargorulesresources" key="reco.defaults.enterembargonumber" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
}

function clearEmbargo(){
	frm=document.forms[1];
	frm.action="reco.defaults.maintainscreenload.do";
	frm.method="post";
	frm.submit();
}

function initializeMultiSelectedValues(){
	var index;
	var multiSelectId;
	jquery(".column4_control_div select[name='mul_mailCategory']").each(function(index, value) { 
		index=jquery(this).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(this).prop("id");
		jquery(this).prop("id",multiSelectId+index);
		bindMultiSelect(jquery(this).prop("id"),"Select","180");
		jquery(this).parent().find("input[name='mailCategory']").val(jquery(this).val());	
	});
	jquery(".column4_control_div select[name='mul_mailClass']").each(function(index, value) { 
		index=jquery(this).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(this).prop("id");
		jquery(this).prop("id",multiSelectId+index);
		bindMultiSelect(jquery(this).prop("id"),"Select","180");
		jquery(this).parent().find("input[name='mailClass']").val(jquery(this).val());	
	});
	jquery(".column4_control_div select[name='mul_mailSubClassGrp']").each(function(index, value) { 
		index=jquery(this).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(this).prop("id");
		jquery(this).prop("id",multiSelectId+index);
		bindMultiSelect(jquery(this).prop("id"),"Select","180");
		jquery(this).parent().find("input[name='mailSubClassGrp']").val(jquery(this).val());	
	});
}

function confirmMessage(){
	frm=targetFormName;
	frm.elements.refNumber.value="";
	frm.elements.refNumber.disabled=true;
		/*frm=document.forms[1];
		frm.action="reco.defaults.maintainscreenload.do";
		frm.method="post";
		frm.submit();*/

}

function nonconfirmMessage(){
	frm=targetFormName;
	frm.elements.refNumber.value="";
	frm.action="reco.defaults.maintainscreenload.do";
	frm.method="post";
	frm.submit();

}

function showOrigin(){
	var frm=targetFormName;
	var originType=targetFormName.elements.originType.value;
	var origin=targetFormName.elements.origin.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
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
		if(originType == "OFCEXG"){
			displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',origin,'origin','1','origin','','0');    
		}
		if(originType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',origin,'origin','0','origin','','0'); 
		}
	}else {
		showDialog({	
			msg		:	'Please Select a Origin Type',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
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
			msg		:	'Please Select a Destination Type',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
}

function showViaPoint(){
	var frm = targetFormName;
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
			msg		:	'Please Select a Via Point Type',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
}

//added by A-5135 for bug ICRD-18408 starts
function navigate(frm){
 	if(!event.shiftKey){
 		if(!frm.elements.refNumber.disabled && !frm.elements.refNumber.readOnly){
 			frm.elements.refNumber.focus();
		}
		else{
			frm.elements.embargoLevel.focus();
		}
 	}
	else {
		frm.elements.btnSave.focus();
	}
}

function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,textfieldObj,formNumber,index){

	var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);

	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}

	var strUrl = strAction+"?multiSelect="+multiSelect+"&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&textfieldObj="+textfieldObj+"&formNumber="+formNumber+"&index="+index;
	//openPopUp(strUrl,width=370,height=330);
	//openPopUp(strUrl,370,425);
	lovWindow = displayLOV(strUrl,'Y','Y',strUrl,groupNameVal,'groupNameVal','1','geographicLevelValues','',index,null,null,null,null,10);
	
//	lovWindow = displayLOV(strUrl,'Y','Y',strUrl,groupNameVal,'groupNameVal','1','geographicLevelValues','',index,null,null,null,null,4);
	
	//var myWindow = window.open(strUrl, "LOV", 'status,width=370,height=330,screenX=100,screenY=30,left=250,top=200');
}

function onaddLinkForGeographicLevel(){
	addTemplateRow('listSelectedDataTemplateRow','listSelectedData','glOperationFlag');	
}

function ondeleteLinkForGeographicLevel(){
	deleteTableRow('index','glOperationFlag');
}
	
 function showGeographicLevelLov(frm,index){
	//alert(index);
	var geographicLevelType=frm.elements.geographicLevelType[index].value;
	var geographicLevel=frm.elements.geographicLevelValues[index].value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	
	if(geographicLevelType == "C"){
		lovWindow = displayLOV('showCountry.do','Y','Y','showCountry.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);
	}
	if(geographicLevelType == "A"){
		lovWindow = displayLOV('showAirport.do','Y','Y','showAirport.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);
	}
	if( geographicLevelType == "ARPGRP" || geographicLevelType == "CNTGRP" ){
		showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','Y',geographicLevel,geographicLevelType,grpCategory,'geographicLevelValues','1',index);
	}
	if(geographicLevelType == "OFCEXG"){
		displayLOV('mailtracking.defaults.oelov.list.do','Y','Y','mailtracking.defaults.oelov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index);    
	}
	if(geographicLevelType == "GPA"){
		displayLOV('mailtracking.defaults.palov.list.do','Y','Y','mailtracking.defaults.palov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index); 
	}
}

function showGeoLevelLov(obj,frm) {

	var index = obj.id.split('geoLevelLov')[1];
	
	var geographicLevelType=frm.elements.geographicLevelType[index].value;
	var geographicLevel=frm.elements.geographicLevelValues[index].value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	// Modified by A-5153 for BUG_ICRD-91377
	if(geographicLevelType == "C"){
		lovWindow = displayLOV('showCountry.do','Y','Y','showCountry.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);
	}
	if(geographicLevelType == "A"){
		lovWindow = displayLOV('showAirport.do','Y','Y','showAirport.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);
	}
	if(geographicLevelType == "OFCEXG"){
			lovWindow = displayLOV('mailtracking.defaults.oelov.list.do','Y','Y','mailtracking.defaults.oelov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);    
	}
	if(geographicLevelType == "GPA"){
		lovWindow = displayLOV('mailtracking.defaults.palov.list.do','Y','Y','mailtracking.defaults.palov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4); 
			lovWindow = displayLOV('mailtracking.defaults.oelov.list.do','Y','Y','mailtracking.defaults.oelov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4);    
		lovWindow = displayLOV('mailtracking.defaults.palov.list.do','Y','Y','mailtracking.defaults.palov.list.do',geographicLevel,'geographicLevel','1','geographicLevelValues','',index,null,null,null,null,4); 
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
	//submitForm(frm,"reco.defaults.approveembargo.do");
	frm.action="reco.defaults.saveEmbargoDetails.do?isApproveFlag=Y";
	setParameterValues();
	frm.submit();
}

function reject(frm){
	submitForm(frm,"reco.defaults.rejectembargo.do?isModifyFlag=Y");
}

function cancel(frm){
	submitForm(frm,"reco.defaults.cancel.do?isModifyFlag=Y");
}

function prepareAttributesForDesc(evt,object,tagid,tagname){
	var objId=object.id;
	evt.cancelBubble = true;
	var button = ['btnmoreDescOk','btnmoreDescClose'];
	var divObj=jquery('#'+tagid);
	setTimeout(function(){
		divObj.find('#languages option[value="en_US"]').attr("selected",true);
	},50);	
	divObj.find("textarea[name='embargoDescPanel']").val(targetFormName.elements.embargoDesc.value)
	showEditablePanel(tagid,objId,500,300,true,true,null,button,closeIt);
}

function closeIt(divObj) {
	divObj.dialog("close");
	divObj.find("textarea[name='embargoDescPanel']").val(targetFormName.elements.embargoDesc.value);
}

function ok(divID){
	var divObj=jquery('#'+divID);
	targetFormName.elements.embargoDesc.value=divObj.find("textarea[name='embargoDescPanel']").val();
	divObj.dialog("close");
}

function changeParameterValues(ctrl){
	jquery(ctrl).parent().next().next().next().find(".column5_control_div").val("");
	var index;
	var multiSelectId;
	var defaultApplicableOn = targetFormName.elements.daysOfOperationApplicableOn.value;
	if(jquery(ctrl).val()=="AWBPRE"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_awbprefix").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='awbPrefix']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.awbprefix" scope="request"/>');
	}else if(jquery(ctrl).val()=="DOW"){	
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_daysOfWeek").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
	}else if(jquery(ctrl).val()=="CAR"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_carrier").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='carrier']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.carrier" scope="request"/>');
	}else if(jquery(ctrl).val()=="COM"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_commodity").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='commodity']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.Commodity" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="ULD"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_uldType").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='uldType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.uldType" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="DAT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_date").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='dates']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.date" scope="request"/>');
	}else if(jquery(ctrl).val()=="FLTNUM"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_fltnum").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_flight_number").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().find("#column5_control_div").css("display","block");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
			
		}
		jquery(ctrl).parent().parent().find("input[name='carrierCode']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.flightcarriercode" scope="request"/>');
		jquery(ctrl).parent().parent().find("input[name='flightNumber']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.flightnumber" scope="request"/>');
	}else if(jquery(ctrl).val()=="UNCLS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_unid").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_unid_number").clone().html());
		jquery(ctrl).parent().parent().find(".column5_control_div").css("display","block");
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='unIds']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unids" scope="request"/>');
	}else if(jquery(ctrl).val()=="FLTOWR"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_flight_owner").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='flightOwner']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.flightowner" scope="request"/>');
	}else if(jquery(ctrl).val()=="FLTTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_flight_type").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("select[name='flightType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.flighttype" scope="request"/>');
	}else if(jquery(ctrl).val()=="ULDPOS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_uldpos").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().parent().find("select[name='uldPos']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.uldpos" scope="request"/>');
	}else if(jquery(ctrl).val()=="ULDTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_uldtyp").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='uldTyp']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.uldtyp" scope="request"/>');
	}else if(jquery(ctrl).val()=="GOODS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_text").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='defaultText']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.natureofgoods" scope="request"/>');
	}else if(jquery(ctrl).val()=="PAYTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_paymenttype").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='paymentType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.paymenttype" scope="request"/>');
	}else if(jquery(ctrl).val()=="PRD"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_product").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='productCode']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.product" scope="request"/>');
	}else if(jquery(ctrl).val()=="AGT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_agentCode").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='agentCode']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.agent" scope="request"/>');
	}else if(jquery(ctrl).val()=="AGTGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_agent_group").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='agentGroup']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.agentGroup" scope="request"/>');
	}else if(jquery(ctrl).val()=="SCC"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_exif").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_scc").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='scc']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.scc" scope="request"/>');
	}else if(jquery(ctrl).val()=="SCCGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_exif").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_scc_group").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='sccGroup']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.sccgroup" scope="request"/>');
	}else if(jquery(ctrl).val()=="SPLIT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_split_indicator").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='splitIndicator']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.splitindicator" scope="request"/>');
	}else if(jquery(ctrl).val()=="TIM"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_time").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='time']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.time" scope="request"/>');
	}else if(jquery(ctrl).val()=="UNNUM"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_unnumber").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='unNumber']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unnumber" scope="request"/>');
	}else if(jquery(ctrl).val()=="PKGINS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_unid_pi").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='packingInstruction']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.pi" scope="request"/>');
	}else if(jquery(ctrl).val()=="WGT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_weight").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column6_control_div").clone().html());
		//for defaulting selected value from Weight
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column6_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='weight']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.weight" scope="request"/>');
		}else if(jquery(ctrl).val()=="NUMSTP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_numstp").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().parent().find("input[name='numberOfStops']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.numberOfStops" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="VOL"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_volume").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().parent().find("input[name='volume']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.volume" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="LEN"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_length").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='parameterLength']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.length" scope="request"/>');
	}else if(jquery(ctrl).val()=="WID"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_width").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='width']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.width" scope="request"/>');
	}else if(jquery(ctrl).val()=="HGT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_height").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='height']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.height" scope="request"/>');
	}else if(jquery(ctrl).val()=="MALCAT"){
			jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_mailcat").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='mailCategory']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.mailcategory" scope="request"/>');
	
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}	
	else if(jquery(ctrl).val()=="MALCLS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_mailcls").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='mailClass']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.mailClass" scope="request"/>');
		
		
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}	
	else if(jquery(ctrl).val()=="SRVCRGCLS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_servicecargoclass").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='serviceCargoClass']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.servicecargoclass" scope="request"/>');
		
		
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}	
	else if(jquery(ctrl).val()=="MALSUBCLSGRP"){
			jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_mailsubclsgrp").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='mailSubClassGrp']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.mailsubclsgrp" scope="request"/>');
		
		
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}		
	else if(jquery(ctrl).val()=="MALSUBCLS"){
			jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_mailsubcls").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='mailSubClass']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.mailsubcls" scope="request"/>');
	}	
	else if(jquery(ctrl).val()=="ARLGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_airline_group").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='airlineGroup']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.airlinegroup" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="ACRTYPGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_aircraftType_group").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='aircraftGroupType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.aircraftGroupType" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="ACRTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_aircraftType").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		//for defaulting selected value from days of week level
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
		}
		jquery(ctrl).parent().parent().find("input[name='aircraftType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.aircraftType" scope="request"/>');
	}
	else if(jquery(ctrl).val()=="UNWGT"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex_oth").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_unWgt_input").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='unWeight']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unidWeight" scope="request"/>');
	}else if(jquery(ctrl).val()=="DVCRG"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_exRate").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_dvcrg_input").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='dvForCustoms']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.dvcst" scope="request"/>');
	}else if(jquery(ctrl).val()=="DVCST"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_exRate").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_dvcst_input").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='dvForCarriage']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.dvcrg" scope="request"/>');
	}else if(jquery(ctrl).val()=="ACRCLS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_aircraftclassification").clone().html());
		jquery(ctrl).parent().next().next().next().html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		if (defaultApplicableOn != null && defaultApplicableOn.trim().length > 0){
			jquery(ctrl).parent().parent().find(".column5_control_div select").prop("value",defaultApplicableOn);
	}	
		jquery(ctrl).parent().parent().find("select[name='aircraftClassification']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.aircraftclassification" scope="request"/>');
	}else if(jquery(ctrl).val()=="SHP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_shipperCode").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='shipperCode']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.shipper" scope="request"/>');
	}else if(jquery(ctrl).val()=="CNS"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_consigneeCode").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='consigneeCode']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.consignee" scope="request"/>');
	}else if(jquery(ctrl).val()=="SHPGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_shipper_group").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='shipperGroup']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.shipperGroup" scope="request"/>');
	}else if(jquery(ctrl).val()=="CNSGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_consignee_group").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='consigneeGroup']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.consigneeGroup" scope="request"/>');
	}else if(jquery(ctrl).val()=="SHPTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_shipmentType").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='shipmentType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.shipmenttype" scope="request"/>');
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}else if(jquery(ctrl).val()=="SRVCTYP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_serviceType").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='serviceType']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.servicetype" scope="request"/>');
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
		}else if(jquery(ctrl).val()=="SRVCTYPFRTECSTP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_serviceTypeForTechnicalStop").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='serviceTypeForTechnicalStop']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.servicetypefortechnicalstop" scope="request"/>');
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}else if(jquery(ctrl).val()=="PKGGRP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_unpg").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='unPg']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unidpackaginggroup" scope="request"/>');
		index=jquery(ctrl).parents("tr:first")[0].rowIndex;
		multiSelectId=jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id");
		jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id",multiSelectId+index);
		bindMultiSelect(jquery(ctrl).parent().parent().find(".column4_control_div select").prop("id"),"Select","180");
	}else if(jquery(ctrl).val()=="SUBRSK"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().parent().find(".column4_control_div").html(jquery("#column4_control_unid_subRisk").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("input[name='subRisk']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unidsubrisk" scope="request"/>');
	}else if(jquery(ctrl).val()=="CNSL"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_ex").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_consol").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='consol']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.consol" scope="request"/>');
	}else if(jquery(ctrl).val()=="UNKSHP"){
		jquery(ctrl).parent().next().html(jquery("#column3_control_in_eq").clone().html());
		jquery(ctrl).parent().next().next().find(".column4_control_div").html(jquery("#column4_control_unknownShipper").clone().html());
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().parent().find("select[name='unknownShipper']").prop('title','<common:message bundle="embargorulesresources" key="reco.defaults.maintainembargo.tooltip.unknownShipper" scope="request"/>');
	 }else{
		jquery(ctrl).parent().next().html("");
		jquery(ctrl).parent().parent().find(".column4_control_div").html("");
		jquery(ctrl).parent().next().next().next().html(jquery("#column5_control_div").clone().html());
		jquery(ctrl).parent().next().next().next().find("#column5_control_div_inline").css("display","none");
		jquery(ctrl).parent().next().next().next().html("");
	}
}

function setParameterValues(){
	var parameterCode;
	var selectValue;
	var inputValue;
	jquery("#embargo_parameter_table tbody tr").each(function( i ) {
	parameterCode=jquery(this).find("select[name='parameterCode'] option:selected").val();
	if(parameterCode=="FLTNUM"){
		jquery(this).find("input[name='values']").val(
		jquery(this).find("input[name='carrierCode']").val()+"~"+jquery(this).find("input[name='flightNumber']").val());
	}/*else if(parameterCode=="DOW"){
		var dateOfWeekValues="";
		jquery(this).find(".column4_control_div input[type='checkbox']").each(function( i ) {
			if(jquery(this).is(":checked") && i !=0){
				dateOfWeekValues=dateOfWeekValues+i+",";
			}
		});
		if(dateOfWeekValues.length>0){
			jquery(this).find("input[name='values']").val(dateOfWeekValues.substring(0,dateOfWeekValues.length - 1));
		}	
	}*/else if(parameterCode=="CAR"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='carrier']").val());
	}else if(parameterCode=="AWBPRE"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='awbPrefix']").val());
	}else if(parameterCode=="COM"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='commodity']").val());
	}else if(parameterCode=="DAT"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='dates']").val());
	}else if(parameterCode=="TIM"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='time']").val());
	}else if(parameterCode=="FLTOWR"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='flightOwner']").val());
	}else if(parameterCode=="FLTTYP"){ 
		//alert(jquery(this).find("select[name='flightType']").val());
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='flightType']").val());
	}else if(parameterCode=="ULDPOS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='uldPos']").val());
	}else if(parameterCode=="ULDTYP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='uldTyp']").val());
	}else if(parameterCode=="GOODS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='defaultText']").val());
	}else if(parameterCode=="PAYTYP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='paymentType']").val());
	}else if(parameterCode=="PRD"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='productCode']").val());
	}else if(parameterCode=="AGT"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='agentCode']").val());
	}else if(parameterCode=="AGTGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='agentGroup']").val());
	}else if(parameterCode=="SCC"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='scc']").val());
	}else if(parameterCode=="SCCGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='sccGroup']").val());
	}else if(parameterCode=="ARLGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='airlineGroup']").val());
	}
	else if(parameterCode=="ACRTYPGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='aircraftGroupType']").val());
	}
	else if(parameterCode=="ACRTYP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='aircraftType']").val());
	}
	else if(parameterCode=="SPLIT"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='splitIndicator']").val());
	}else if(parameterCode=="UNNUM"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='unNumber']").val());
	}else if(parameterCode=="WGT"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='weight']").val());
	}else if(parameterCode=="NUMSTP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='numberOfStops']").val());
	}
	else if(parameterCode=="VOL"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='volume']").val());
	}
	else if(parameterCode=="ULD"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='uldType']").val());
	}
	else if(parameterCode=="LEN"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='parameterLength']").val());
	}else if(parameterCode=="WID"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='width']").val());
	}else if(parameterCode=="HGT"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='height']").val());
	}else if(parameterCode=="PKGINS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='packingInstruction']").val());
	}else if(parameterCode=="UNWGT"){
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='unWeight']").val());
	}else if(parameterCode=="DVCST"){
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='dvForCustoms']").val());
	}else if(parameterCode=="DVCRG"){
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='dvForCarriage']").val());
	}else if(parameterCode=="UNCLS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='unIds']").val());
	}
	else if(parameterCode=="MALCLS"){ 
		selectValue=jquery(this).find("select[name='mailClass']").val();
		inputValue=jquery(this).find("input[name='mailClass']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='mailClass']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='mailClass']").val());
		}
	}
	else if(parameterCode=="SRVCRGCLS"){ 
		selectValue=jquery(this).find("select[name='serviceCargoClass']").val();
		inputValue=jquery(this).find("input[name='serviceCargoClass']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='serviceCargoClass']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='serviceCargoClass']").val());
		}
	}
	else if(parameterCode=="MALSUBCLS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='mailSubClass']").val());
		
	}else if(parameterCode=="MALCAT"){ 
		selectValue=jquery(this).find("select[name='mailCategory']").val();
		inputValue=jquery(this).find("input[name='mailCategory']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='mailCategory']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='mailCategory']").val());
		}	
	}else if(parameterCode=="MALSUBCLSGRP"){ 
		selectValue=jquery(this).find("select[name='mailSubClassGrp']").val();
		inputValue=jquery(this).find("input[name='mailSubClassGrp']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='mailSubClassGrp']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='mailSubClassGrp']").val());
		}
	}else if(parameterCode=="ACRCLS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='aircraftClassification']").val());
	}else if(parameterCode=="SHP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='shipperCode']").val());
	}else if(parameterCode=="SHPGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='shipperGroup']").val());
	}else if(parameterCode=="CNS"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='consigneeCode']").val());
	}else if(parameterCode=="CNSGRP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='consigneeGroup']").val());
	}else if(parameterCode=="SHPTYP"){ 
		selectValue=jquery(this).find("select[name='shipmentType']").val();
		inputValue=jquery(this).find("input[name='shipmentType']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='shipmentType']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='shipmentType']").val());
		}
	}else if(parameterCode=="SRVCTYP"){ 
		selectValue=jquery(this).find("select[name='serviceType']").val();
		inputValue=jquery(this).find("input[name='serviceType']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='serviceType']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='serviceType']").val());
		}
	}else if(parameterCode=="SRVCTYPFRTECSTP"){ 
		selectValue=jquery(this).find("select[name='serviceTypeForTechnicalStop']").val();
		inputValue=jquery(this).find("input[name='serviceTypeForTechnicalStop']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='serviceTypeForTechnicalStop']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='serviceTypeForTechnicalStop']").val());
		}
	}else if(parameterCode=="PKGGRP"){ 
		selectValue=jquery(this).find("select[name='unPg']").val();
		inputValue=jquery(this).find("input[name='unPg']").val();
		if(null !=selectValue && "undefind" !=selectValue){
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='unPg']").val());
		}else if(null !=inputValue && "undefind" !=inputValue){
			jquery(this).find("input[name='values']").val(jquery(this).find("input[name='unPg']").val());
		}
	}else if(parameterCode=="SUBRSK"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("input[name='subRisk']").val());
	}
	else if(parameterCode=="CNSL"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='consol']").val());
	}
	else if(parameterCode=="UNKSHP"){ 
		jquery(this).find("input[name='values']").val(jquery(this).find("select[name='unknownShipper']").val());
	}else{
		jquery(this).find("input[name='values']").val("");
	}	
	});
}

function showFlightOwnerLov(obj) {
	  var frm=targetFormName;
	  // TO DO 
		alert(" TO DO : fetch LOV details");
}


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
function constructDescription(){
	var strAction="reco.defaults.constructdesc.do";
	var divId="descriptionTable";
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	//ON AJAX FRAMEWORK CHANGES STARTS
	setParameterValues();
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
	//ON AJAX FRAMEWORK CHANGES ENDS
}

function updateTableCode(_tableInfo){
	//ON AJAX FRAMEWORK CHANGES STARTS
	//_str=_tableInfo.getTableData();	
	//_str = _tableInfo.document.getElementById("descriptionTable_Temp").innerHTML;	
	//document.getElementById("descriptionTable").innerHTML = _str;
	jquery("#descriptionTable [name=embargoDesc]")[0].innerHTML = jquery(_tableInfo.document.getElementById("descriptionTable_Temp")).find("[name=embargoDesc]")[0].innerHTML;
	//ON AJAX FRAMEWORK CHANGES ENDS
}

function duplicatecheck(frm){
	//openPopUp("reco.defaults.duplicatecheck.do",600,400);
	//var action = "reco.defaults.duplicatecheck.do";
	//submitFormWithUnsaveCheck(action);
	frm.action="reco.defaults.duplicatecheck.do";
	setParameterValues();
	frm.submit();
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

function validateTime(txtObj){
	if(txtObj.value.indexOf(':')<0){
	if(isNaN(txtObj.value)){
		showDialog({	
			msg		:	"Invalid Time!!",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		txtObj.value="";
	}else{
			if(txtObj.value.indexOf('.')>0){
				showDialog({	
					msg		:	"Invalid Time!!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
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
				showDialog({	
					msg		:	"Invalid Time!!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				txtObj.value="";
		}
		else{
		time=txtObj.value.split(":");
			if(isNaN(time[0]) || isNaN(time[1]) || time.length>2){
				showDialog({	
					msg		:	"Invalid Time!!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
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
		showDialog({	
			msg		:	"Invalid Time!!",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
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

		showDialog({	
			msg		:	"Invalid Dimension, Format is LXWXH!!",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		object.value="";
	}
	else{
		var str = val.split('X');
		if(!str[0] || !str[1] || !str[2] || str[3]){
			showDialog({	
				msg		:	"Invalid Dimension, Format is LXWXH !!",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			object.value="";
		}
		else{
			if(isNaN(str[0])){
				showDialog({	
					msg		:	"Invalid Dimension, Format is LXWXH !!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				object.value="";
			}
			else if(isNaN(str[1])){
				showDialog({	
					msg		:	"Invalid Dimension, Format is LXWXH !!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				object.value="";
			}
			else if(isNaN(str[2])){
				showDialog({	
					msg		:	"Invalid Dimension, Format is LXWXH !!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				object.value="";
			}
		}
	}
}

function dateCheck(objName){
    var datefield = objName;
	var originalVal = objName.value;
	var invalid=false;
    trimFieldValue(objName);
	var changedVal;
	var splitDate = objName.value.split(',');
	if(splitDate.length>10){
		showDialog({	
			msg		:	"Maximum 10 values allowed",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
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
				showDialog({	
					msg		:	"Invalid Date. Format should be DD-MMM-YYYY !!",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
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
		showDialog({	
			msg		:	"Year should not be greater than 2999",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		datefield.focus();
		return true;
	}
					
    	return true;
}

function restrictValues(obj){
	var index=0;
	if(obj.tagName=='SELECT' && jquery(obj).prop('multiple')){
		jquery(obj).multiselect("widget").find("input:checked").each(function(){
				index++;    
  			  });
		if(index>10){
		showDialog({	
			msg		:	"Maximum 10 values allowed",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
			jquery(obj).multiselect("open");
		}
	}else{
	var val=obj.value.split(',');
	if(val.length>10){
		showDialog({	
			msg		:	"Maximum 10 values allowed",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		 /* Fix for ICRD-203359 */
		 obj.focus();
	}
	else if(obj.value.endsWith(',')){
		restrictSpecialChar(obj);
	}
}
}

function restrictSpecialChar(obj){
	if(obj.value.endsWith(',')){
		/*showDialog("Special characters not allowed",1,self);
		obj.value="";*/
		obj.value=obj.value.substring(0, obj.value.length - 1);
	}
}

function restrictGeoValue(index){
	var handlCodes=targetFormName.elements.geographicLevelValues[index].value;
	var val=handlCodes.split(',');
	if(val.length>4){
		//showDialog("Maximum 4 values allowed",1,self);
		//lovWindow.showDialog("Maximum 4 values allowed",1,self);
		showDialog({	
			msg		:	"Maximum 4 values allowed",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		targetFormName.elements.geographicLevelValues[index].value="";
	}
}

function restrictGeoLevelValue(obj){
	var val = obj.value.split(',');
	if(val.length > 4){
		showDialog({	
			msg		:	"Maximum 4 values allowed",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		obj.value="";
	}else if(obj.value.endsWith(',')){
		restrictSpecialChar(obj);
	}
}

function restrictToSingleNumber(obj){
	if(isNaN(obj.value)){
		showDialog({	
			msg		:	"Enter valid number",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		obj.value="";
	}
}

function validateNumber(obj){
	if(isNaN(obj.value)){
		if(obj.value==','){
			showDialog({	
				msg		:	"Enter valid value",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			obj.value="";
		}
		else{
			var val=obj.value.split(',');
			for(var i=0;i<val.length;++i){
				if(isNaN(val[i])){
					showDialog({	
						msg		:	"Enter valid value",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});
					obj.value="";
				}
			}
		}
	}
	else if(obj.value.indexOf('.')>-1){
		showDialog({	
			msg		:	"Enter valid value",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		obj.value="";
	}
}

function validateaplphanum(obj){
    var regx = /^[A-Za-z0-9]+$/;
	if(obj.value){
		if (!regx.test(obj.value)){
			if(obj.value==','){
				showDialog({	
					msg		:	"Enter valid value",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				obj.value="";
			}
			else{
				var val=obj.value.split(',');
				for(var i=0;i<val.length;++i){
					if(!val[i]==""){
					
						if(!regx.test(val[i])){
							showDialog({	
								msg		:	"Enter valid value",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							obj.value="";
						}
					}
				}
			}
		}
	}
}

function clearValues(obj,frm){
	var index = obj.id.split('index')[1];
	if(frm.elements.geographicLevelValues[index]){
	frm.elements.geographicLevelValues[index].value="";
	}
}

function displayProductLov(strAction,lovValue,textfiledObj,priorityValueObj,transportModeObj,formNumber,codeField,activeProduct,count){
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&priorityObject="+priorityValueObj+"&transportModeObject="+transportModeObj+"&formNumber="+formNumber+"&productCodeField="+codeField+"&activeProducts="+activeProduct+"&sourceScreen=B&rowIndex="+count;
	displayLOV(strUrl,'Y','Y',strUrl,lovValue,'Parameter','1','productCode','',count,null,null,null,null,10);
}

function restrictAndAppendTokken(obj,tknLen){
	var valenteredAscii=getPlatFormEventKeyCode();
	if((valenteredAscii >= 48 && valenteredAscii <=57) || (valenteredAscii >= 65 && valenteredAscii <=90)
			|| (valenteredAscii >= 96 && valenteredAscii <= 122) ){
			
		var fldLen = new Number(obj.value.length);
		if(fldLen>0 && ((fldLen+1)%tknLen == 0)){
			obj.value = obj.value.concat(',');
		}
		
	}
}
function restrictLength(obj){

	if(obj.value.length>0 && obj.value.length<3){
		showDialog({	
			msg		:	"Enter a 3-digit number for AWB Prefix",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		obj.value="";
	}else if(obj.value.length>3){
		var val = obj.value.split(',');
		for(var i=0;i<val.length;i++){
			if(val[i].length<3 || val[i].length>3){
				showDialog({	
					msg		:	"Enter a 3-digit number for AWB Prefix",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				obj.value="";
			}
		}
	}
}

function checkFlightNumber(obj) {
	if(obj.value.length==1) obj.value = "000"+obj.value;
	else if(obj.value.length==2) obj.value = "00"+obj.value;
	else if(obj.value.length==3) obj.value = "0"+obj.value;
}
function showLov(obj,frm) {
  var scc = document.getElementsByName('scc');
  var size = scc.length;
  displayLOV('showScc.do','Y','Y','showScc.do',scc,'scc','1','scc','',size-2,null,null,null,null,4);
}
function handleLangChange() {
	document.getElementById('languages').disabled = true;
	targetFormName.elements.prevEmbargoLang.value=document.getElementById('languages').getAttribute('_oldvalue');
	targetFormName.elements.currentEmbargoLang.value = document.getElementById('languages').value;
	targetFormName.elements.prevEmbargoDesc.value = document.getElementById('embargoDescPanel').value;
	if(targetFormName.elements.prevEmbargoLang.value == 'en_US') {
		targetFormName.elements.embargoDesc.value = document.getElementById('embargoDescPanel').value
	}
	updateEnbargoDesc("updateEmbargoDescPanel");
}
function handleLangOk() {
	targetFormName.elements.prevEmbargoLang.value=document.getElementById('languages').value;
	targetFormName.elements.currentEmbargoLang.value = "en_US";
	targetFormName.elements.prevEmbargoDesc.value = document.getElementById('embargoDescPanel').value;
	if(targetFormName.elements.prevEmbargoLang.value == 'en_US') {
		targetFormName.elements.embargoDesc.value = document.getElementById('embargoDescPanel').value
	}
	updateEnbargoDesc("updateEmbargoDescText");
}
function updateEnbargoDesc(callback) {
	asyncSubmit(targetFormName,"reco.defaults.saveMultiLnguageEbmbargoDesc.do",callback,null,null);
}
function updateEmbargoDescPanel(content) {
	document.getElementById('embargoDescPanel').value = content.document.getElementById("embargoDesc").innerHTML;
	document.getElementById('languages').disabled = false;
}
function updateEmbargoDescText(content) {
	document.getElementById('languages').disabled = false;
	targetFormName.elements.embargoDesc.value=content.document.getElementById("embargoDesc").innerHTML;
	var divObj=jquery('#moreDescdiv');
	divObj.dialog("close"); 
}
function handleLangDelete() {
	document.getElementById('languages').disabled = true;
	document.getElementById('embargoDescPanel').value = "";
	targetFormName.elements.prevEmbargoLang.value=document.getElementById('languages').value;
	targetFormName.elements.currentEmbargoLang.value = document.getElementById('languages').value;
	targetFormName.elements.prevEmbargoDesc.value = document.getElementById('embargoDescPanel').value;
	updateEnbargoDesc("updateEmbargoDescPanel");
}
function showAgentLOV(){
	var textfiledDesc="";
    var formCount=1;
	var code = targetFormName.elements.agentCode[0].value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agentCode[0]&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCode="+code;
	//openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=agentCode"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCode="+code,'700');
	openPopUpWithHeight(StrUrl,"500");
}
function showShipperLOV(){
	var code = "";
	if(targetFormName.elements.shipperCode[0].value != null){
		code = targetFormName.elements.shipperCode[0].value;
	}
	var StrUrl='showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code='+code+
	'&title=Customer Lov&formCount=1&lovTxtFieldName=shipperCode&lovDescriptionTxtFieldName=&index=0';
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
}
function showConsigneeLOV(){
	var code = "";
	if(targetFormName.elements.consigneeCode[0].value != null){
		code = targetFormName.elements.consigneeCode[0].value;
	}
	var StrUrl='showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code='+code+
	'&title=Customer Lov&formCount=1&lovTxtFieldName=consigneeCode&lovDescriptionTxtFieldName=&index=0';
	var options = {url:StrUrl}
	var myWindow = CustomLovHandler.invokeCustomerLov(options);
}
function getElementIndex(_this,name){ 
   var itemIndex=0;
   var item=jquery(_this).closest( "td" ).find('[name='+name+']');
   var allItems=jquery(_this).closest( "tbody" ).find('[name='+name+']');
   if(item && allItems){                       
	 allItems.each(function(index) {                                                 
		if( jquery(this).is(item) )  {
		   itemIndex=index;                                           
		 }
	 });                          
   }                                            
   return itemIndex;
}