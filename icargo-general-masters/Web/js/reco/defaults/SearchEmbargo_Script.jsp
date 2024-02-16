<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){
var targetFormName=document.forms[1];
	initializePanels();
	viewSelectedParameter();
	disableControls();
  with(targetFormName){
   	evtHandler.addIDEvents("geographicLevelLov","showGeographicLevel()",EVT_CLICK);
	evtHandler.addEvents("geographicLevelType","clearGeographicLevel()",EVT_CHANGE);
	evtHandler.addEvents("originType","clearOrigin()",EVT_CHANGE);
	evtHandler.addEvents("destinationType","clearDestination()",EVT_CHANGE);
	evtHandler.addEvents("viaPointType","clearViapoint()",EVT_CHANGE);
	evtHandler.addEvents("btnSearchSimple","simpleSearch()",EVT_CLICK);
   	evtHandler.addEvents("btnClearSimple","simpleClearEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnSpecificSearch","viewSpecificSearch()",EVT_CLICK);
    evtHandler.addIDEvents("originLov","showOrigin()",EVT_CLICK);
	evtHandler.addIDEvents("destinationLov","showDestination()",EVT_CLICK);
   	evtHandler.addIDEvents("viaPointLov","showViaPoint()",EVT_CLICK);
   	evtHandler.addEvents("btnSearch","searchEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnClear","clearEmbargo()",EVT_CLICK);
   	evtHandler.addEvents("btnClose","closeEmbargo()",EVT_CLICK); 	
		//added by A-8800 for IASCB-59379
	evtHandler.addEvents("btnPopUpClose","window.close()",EVT_CLICK); 
   }
   onScreenload();
}

function onScreenload(){
if (targetFormName.elements.sourceScreen.value == "PRECHECK") {
	targetFormName.elements.sourceScreen.value = "";
	searchEmbargo();
}
}
function showGeographicLevel(){
var targetFormName=document.forms[1];
	var geographicLevelType=targetFormName.elements.geographicLevelType.value;
	var geographicLevel=targetFormName.elements.geographicLevel.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(jQuery("#simpleSearch").val()=="1"){
		if(geographicLevelType!=null && geographicLevelType.length>0){
			if(geographicLevelType == "A"){
				displayLOV('showAirport.do','N','Y','showAirport.do',geographicLevel,'geographicLevel','1','geographicLevel','','0');
			}
			if(geographicLevelType == "C"){
				displayLOV('showCountry.do','N','Y','showCountry.do',geographicLevel,'geographicLevel','1','geographicLevel','','0');
			}
			if( geographicLevelType == "ARPGRP" || geographicLevelType == "CNTGRP"){
				showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',geographicLevel,geographicLevelType,grpCategory,'geographicLevel','1','0');
			}
		}else {
			showDialog({
				msg:'<common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.geographiclevel.select" scope="request"/>',
				type:2,
				parentWindow:self,		
				onClose:function(result){
					targetFormName.elements.geographicLevel.focus();
				}
			});
		}
	}
}

function clearGeographicLevel(){
	jquery("input[name='geographicLevel']").val("");
}
function clearOrigin(){
	jquery("input[name='origin']").val("");
}
function clearDestination(){
	jquery("input[name='destination']").val("");
}
function clearViapoint(){
	jquery("input[name='viaPoint']").val("");
}

function displayProductLov(strAction,lovValue,textfiledObj,priorityValueObj,transportModeObj,formNumber,codeField,activeProduct,count){
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&priorityObject="+priorityValueObj+"&transportModeObject="+transportModeObj+"&formNumber="+formNumber+"&productCodeField="+codeField+"&activeProducts="+activeProduct+"&sourceScreen=B&rowIndex="+count;
	displayLOV(strUrl,'N','Y',strUrl,lovValue,'Parameter','1','productCode','',count);
}

function showOrigin(){
var targetFormName=document.forms[1];
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
	}else {
		showDialog({
			msg:'<common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.origintype.select" scope="request"/>',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				targetFormName.elements.origin.focus();
			}
		});
	}
}
function showDestination(){
	var targetFormName=document.forms[1];
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
	}else {
		showDialog({
			msg:'<common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.destinationtype.select" scope="request"/>',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				targetFormName.elements.destination.focus();
			}
		});
	}
}
function showViaPoint(){
var targetFormName=document.forms[1];
	var viaPointType=targetFormName.elements.viaPointType.value;
	var viaPoint=targetFormName.elements.viaPoint.value;
	//var grpCategory ='GEN';
	var grpCategory ='EMB';
	if(viaPointType!=null && viaPointType.length>0){
		if(viaPointType == "A"){
			displayLOV('showAirport.do','N','Y','showAirport.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
		if(viaPointType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',viaPoint,'viaPoint','1','viaPoint','','0');
		}
		if( viaPointType == "ARPGRP" || viaPointType == "CNTGRP"){
			showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',viaPoint,viaPointType,grpCategory,'viaPoint','1','0');
		}
	}else {
		showDialog({
			msg:'<common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.viapointtype.select" scope="request"/>',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				targetFormName.elements.viaPoint.focus();
			}
		});
	}
}

function showParameterValue(){
var targetFormName=document.forms[1];
var parameterCode=targetFormName.elements.parameterCode.value;
var parameterValue=targetFormName.elements.defaultText.value;
if((parameterValue!=null && parameterValue.length>0) &&(parameterCode==null || parameterCode.length<1)){
	showDialog({
		msg:'<common:message bundle="searchembargoresources" key="reco.defaults.searchembargo.parametercode.select" scope="request"/>',
		type:2,
		parentWindow:self,		
		onClose:function(result){
			targetFormName.elements.defaultText.focus();
		}
	});
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
	openPopUp(strUrl,370,425);
}
function disableControls(){
var targetFormName=document.forms[1];
	if(jQuery("#simpleSearch").val()=="1"){
		targetFormName.elements.geographicLevelType.disabled=false;
		targetFormName.elements.geographicLevel.disabled=false;
		targetFormName.elements.dayOfOperationApplicableOn.disabled=false
		targetFormName.elements.btnSearchSimple.disabled=false;
	}else{
		IC.util.dom.show(document.getElementById("specific_search_div"));
		targetFormName.elements.geographicLevelType.disabled=true;
		targetFormName.elements.geographicLevel.disabled=true;
		targetFormName.elements.dayOfOperationApplicableOn.disabled=true;
		targetFormName.elements.btnSearchSimple.disabled=true;
	}
}

function simpleSearch(){
var targetFormName=document.forms[1];
	jQuery("#simpleSearch").val("1");
	targetFormName.elements.displayPage.value= "1";
	targetFormName.elements.lastPageNum.value ="0";
	submitForm(targetFormName, 'reco.defaults.simplesearch.do?navigationMode=FILTER');
}
function simpleClearEmbargo(){
var targetFormName=document.forms[1];
	jQuery("#simpleSearch").val("1");
	submitForm(targetFormName,'reco.defaults.clearsearch.do');
	
}
function clearEmbargo(){
var targetFormName=document.forms[1];
	jQuery("#simpleSearch").val("0");
	submitForm(targetFormName,'reco.defaults.clearsearch.do');
}
function searchEmbargo(){
var targetFormName=document.forms[1];
	jQuery("#simpleSearch").val("0");
	var parameterCode=jquery("select[name='parameterCode']").val();
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
			jquery("input[name='parameterValue']").val(jquery("input[name='productCode']").val());
		}else if(parameterCode=="SCC"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='scc']").val());
		}else if(parameterCode=="SCCGRP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='sccGroup']").val());
		}else if(parameterCode=="AGT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='agentCode']").val());
		}else if(parameterCode=="AGTGRP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='agentGroup']").val());
		}else if(parameterCode=="SPLIT"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='splitIndicator']").val());
		}else if(parameterCode=="UNNUM"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='unNumber']").val());
		}else if(parameterCode=="WGT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='weight']").val());
		}else if(parameterCode=="HGT"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='height']").val());
		}else if(parameterCode=="UNCLS"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='unIds']").val());
		}else if(parameterCode=="WID"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='width']").val());
		}else if(parameterCode=="LEN"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='parameterLength']").val());
		}else if(parameterCode=="AWBPRE"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='awbPrefix']").val());
		}else if(parameterCode=="ARLGRP"){ 
			jquery("input[name='parameterValue']").val(jquery("input[name='airlineGroup']").val());
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
		else if(parameterCode=="SRVCRGCLS"){
			jquery("input[name='parameterValue']").val(jquery("select[name='serviceCargoClass']").val());
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
		}else if(parameterCode=="CNSL"){ 
			jquery("input[name='parameterValue']").val(jquery("select[name='consol']").val());
		}else if(parameterCode=="ARLGRP"){
			jquery("input[name='parameterValue']").val(jquery("input[name='airlineGroup']").val());
		}	
		else{
			jquery("input[name='parameterValue']").val("");
		}
	}else{
		jquery("input[name='parameterValue']").val("");
	}
	targetFormName.elements.displayPage.value= "1";
	targetFormName.elements.lastPageNum.value ="0";
	submitForm(targetFormName,'reco.defaults.simplesearch.do?navigationMode=FILTER');
}

function closeEmbargo(){
var targetFormName=document.forms[1];
	var closeBtnFlag=jQuery("input[name='closeBtnFlag']").val();
	if(closeBtnFlag=="1"){
		targetFormName.action= "home.do";
		targetFormName.submit();
	}else{
		jQuery("#simpleSearch").val("1");
		submitForm(targetFormName,'reco.defaults.closeembargo.do');
	}
}
function initializePanels(){
var ldp_arry = new Array();	
	ldp_arry = {
	  "autoOpen" : false,
	  "width" : 500,
	  "height": 220,
	  "draggable" :false,
	  "resizable" :false
           };
            initDialog('descriptionDiv',ldp_arry);

}

function showDescription(obj,div,divName){
var invId=obj.id;
	var divId;
	var indexId=invId.split('_')[1];
	if(indexId != null && indexId != ""){
	 divId=div;
	}else{
	 divId=div+'';
	}
	getPlatformEvent().cancelBubble = true;
    showInfoMessage(obj,divId,invId,divName,480,200);	
}

function getEmbargoDetails(key,value){
	jQuery("#leftPanelKey").val(key);
	jQuery("#leftPanelValue").val(value);
	recreateTableDetails("reco.defaults.asyncsearch.do","data-div");
}


////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
function recreateTableDetails(strAction,divId){
jquery('#'+divId).html('<table style="width:100%;height:100%;"><tr><td width="47%"></td><td width="53%"><img src="<%=request.getContextPath()%>/images/ajaxloader.gif" alt="Processing...."/><br>Loading....</br></td></tr></table>')
	var __extraFn="updateTableCode";
	//ON AJAX FRAMEWORK CHANGES STARTS
	asyncSubmit(targetFormName,strAction,__extraFn,null,null,divId);
	//ON AJAX FRAMEWORK CHANGES ENDS
}

function updateTableCode(_tableInfo){
	//ON AJAX FRAMEWORK CHANGES STARTS
	_str=_tableInfo.document.getElementById("_embargolistTable");
	document.getElementById(_tableInfo.currDivId).innerHTML=_str.innerHTML;
	initializePanels();
	//ON AJAX FRAMEWORK CHANGES ENDS
}

/////////////////////////////////////////////////////////////////////////////////////////


function viewSpecificSearch(){
	IC.util.widget.updateTableContainerHeight("#maindiv",-165);
	IC.util.dom.show(document.getElementById("specific_search_div"));
	jQuery("#simpleSearch").val("0");
	jQuery("select[name='geographicLevelType']").prop("disabled",true);
	jQuery("input[name='geographicLevel']").prop("disabled",true);
	jQuery("select[name='dayOfOperationApplicableOn']").prop("disabled",true);
	jQuery("button[name='btnSearchSimple']").css("background-color","#DDDDDD");
	jQuery("button[name='btnSearchSimple']").prop("disabled",true);	
}

function submitPage(lastPg,displayPg){
var targetFormName=document.forms[1];
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	submitForm(targetFormName,'reco.defaults.simplesearch.do?navigationMode=NAVIGATION');
}

function changeParameterValues(ctrl){
	jquery("div .parameter_control").attr("style","display:none");
	jquery("div .parameter_control :input").each(function(){
		jquery(this).val("");			
	});
	jquery("div .parameter_control select").each(function(){
		jquery(this).find("option:first").prop('selected',true);		

	});
	jquery("div #parameter_control_"+jquery(ctrl).val()).removeAttr("style");
}

function viewSelectedParameter(){
var parameterCode=jquery("select[name='parameterCode']").val();
	if(null !=parameterCode && "" !=parameterCode){
		jquery("#parameter_control_"+parameterCode).css("display","block");
	}else{
		jquery("#parameter_control_").css("display","block");
	}
}


function validateTime(txtObj){	
	if(isNaN(txtObj.value)){
		if(!txtObj.value.indexOf(':')>0){
		showDialog({
			msg:'Invalid Time',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				txtObj.value="";
				txtObj.focus();
			}
		});
		}
	}else{
	if(txtObj.value.indexOf(':')<0){
		var hours = "0";
		var minutes = "00";
		var length = 0;
		if(txtObj.value != ""){
			hours = txtObj.value;
			length = hours.length;
		}else if(txtObj.value.trim().length==0){
			return true;
		}
		if(length>2) {
			minutes=hours.substring(2);
			hours = hours.substring(0,2);
		}
		var sTime = getFormattedMinutes(hours, minutes);		
		txtObj.value = sTime;
	}else{
		time=txtObj.value.split(":");
		var hours=_time[0];
		var minutes="00";
		if(_time[1]!=null){
			if(_time[1].length>0){
				minutes=_time[1];	
			}
		}			
		var sTime = getFormattedMinutes(hours, minutes);			
		txtObj.value = sTime;
	}
	_pTime=txtObj.value.split(":");
	if(parseInt(_pTime[0])>23){
		showDialog({
			msg:'Invalid Time',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				txtObj.value="";
				txtObj.focus();
			}
		});
	}
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

function validateWeight(obj){
	if(isNaN(obj.value)){
		showDialog({
			msg:'Enter valid weight',
			type:2,
			parentWindow:self,		
			onClose:function(result){
				obj.value="";
				obj.focus();
			}
		});
		
	}
}
function validateNumber(obj){
	if(isNaN(obj.value)){
		var val=obj.value;
		if(isNaN(val)){
			showDialog({
				msg:'Enter valid value',
				type:2,
				parentWindow:self,		
				onClose:function(result){
					obj.focus();
				}
			});
		}
	}else if(obj.value.indexOf('.')>-1){
		showDialog({
				msg:'Enter valid value',
				type:2,
				parentWindow:self,		
				onClose:function(result){
					obj.focus();
				}
			});
	}
}

function validateaplphanum(obj){
var regx = /^[A-Za-z0-9]+$/;
	if(obj.value){
		if (!regx.test(obj.value)){
			var val=obj.value;
			if(!val==""){					
				if(!regx.test(val)){
					showDialog({
						msg:'Enter valid value',
						type:2,
						parentWindow:self,		
						onClose:function(result){
							obj.focus();
						}
					});
				}
			}
		}
	}
}
//added for ICRD-254170 starts
function viewGroupingDetails(obj){
	prepareAttributes(obj,'parameterValuesPopUpForSearchCompliance','parameterValuesDivForSearchCompliance');
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
		if(obj.id.indexOf("CNTGRP")!=-1 ){
			//Added BY A-8800 for IASCB-50176	
			//Ex:obj.id=parameterValues_link_CNTGRP-SP_STATES
			var groupFilterDetails = obj.id.split("-");
			//groupFilterDetails[0] = parameterValues_link_CNTGRP
			//groupFilterDetails[1] = SP_STATES
			targetFormName.elements.groupType.value = groupFilterDetails[0].split("_")[2];
			targetFormName.elements.groupName.value = groupFilterDetails[1];
		}else{
		targetFormName.elements.groupType.value = obj.id.split("_")[2];
		targetFormName.elements.groupName.value = obj.id.split("_")[3];
		}
		var index = obj.id.split("_")[1];
		_divIdSeg="displayGroupingDetailsForSearchCompliance";
		_currDivId=div;
		var strAction = "reco.defaults.findParamterGroupingDetailsForSearchCompliance.do";
		asyncSubmit(targetFormName,strAction,_divIdSeg,null,null,_currDivId);	
	}
}
function displayGroupingDetailsForSearchCompliance(_tableInfo){
	_resultDiv = _tableInfo.document.getElementById("ajax_groupingDetailsBodyForSearchCompliance").innerHTML;
	jQuery('#'+_tableInfo.currDivId).find(".groupingDetailsBodyForSearchCompliance").html(_resultDiv);  
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
