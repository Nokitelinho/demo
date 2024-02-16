<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

//window.onload=initForm;
//function initForm()
function screenSpecificEventRegister()
{

	var frm=targetFormName;
	//frm.airlineCode.focus();
	onScreenLoad();
	with(frm){

	//CLICK Events


 
	evtHandler.addEvents("btList","listmonitorstock(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearmonitorstock(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closemonitorstock()",EVT_CLICK);
	evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
	evtHandler.addEvents("btListULD","listulddo(this.form)",EVT_CLICK);
	evtHandler.addEvents("btSetupStock","setupstock(this.form)",EVT_CLICK);
	//evtHandler.addEvents("btGenerateReport","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("btGenerateMessage","sendSCMMessage()",EVT_CLICK);
	evtHandler.addEvents("btPrint","printStock(this.form)",EVT_CLICK);

	//evtHandler.addEvents("uldTypeCode","validateFields(this, -1, 'UldType', 1, true, true)",EVT_BLUR, 3 , 2);
	evtHandler.addEvents("airlineCode","validateFields(this, -1, 'OwnerAirlineCode', 0, true, true)",EVT_BLUR);
	//evtHandler.addEvents("stationCode","validateFields(this, -1, 'StationCode', 1, true, true)",EVT_BLUR);
	evtHandler.addEvents("grouplov","displayLOV('FindUldGroupLov.do','N','Y','FindUldGroupLov.do',document.forms[1].uldGroupCode.value,'uldGroupCode','1','uldGroupCode','',0)",EVT_CLICK);
	evtHandler.addEvents("uldlov","displayLOV('showUld.do','N','Y','showUld.do',document.forms[1].uldTypeCode.value,'UldType','1','uldTypeCode','')",EVT_CLICK);
	evtHandler.addEvents("agentlov","showAgentLOV()",EVT_CLICK);
	//added by a-3045 for CR QF1012 starts
	evtHandler.addEvents("levelType","comboChanged()",EVT_CHANGE);
	evtHandler.addIDEvents("levelValuelov","onclickLevelLOV()",EVT_CLICK);
	//added by a-3045 for CR QF1012 ends
	}

 	//comment by A-5264 - removed whole records sorting(Sort filter) available in the screen. 
 	//applySortOnTable("monitoruldstock",new Array("None","String","String","String","String","Number","Number","Number","Number","Number","Number","Number","Number"));
	if(frm.elements.airlineCode.disabled==false){
		frm.elements.airlineCode.focus();
	}


}

function onScreenLoad(){
	if(targetFormName.elements.screenStatus.value == ""){//added for ICRD-28379
			if(document.getElementById('checkBox')){
			document.getElementById('checkBox').focus();
			}
			else{
			targetFormName.elements.btClear.focus();
			}
	}
collapseAllRows();	
if(targetFormName.elements.screenStatus.value == "screenload"){
targetFormName.elements.screenStatus.value = '';
//targetFormName.btGenerateMessage.disabled=true;
disableField(targetFormName.elements.btGenerateMessage);
}
		/*if(targetFormName.viewByNatureFlag.checked){
			document.getElementById('VBN').style.display="block";
			document.getElementById('VWN').style.display="none";
			enableField(targetFormName.uldTypeCode);
			enableField(targetFormName.uldNature);
			enableField(targetFormName.uldlov);
			disableField(targetFormName.uldGroupCode);
			disableField(targetFormName.grouplov);
		}else{
			document.getElementById('VWN').style.display="block";
			document.getElementById('VBN').style.display="none";
			disableField(targetFormName.uldTypeCode);
			disableField(targetFormName.uldlov);
			disableField(targetFormName.uldNature);
			enableField(targetFormName.uldGroupCode);
		}*/
	/* Commenting for ICRD-1838	
	if(targetFormName.stockDisableStatus.value=="airline"){
		targetFormName.airlineCode.disabled = true;
		targetFormName.airlineLovImg.disabled = true;
	}
	*/
	/*if(targetFormName.stockDisableStatus.value=="GHA"){
		targetFormName.stationCode.disabled = true;
		targetFormName.airportLovImg.disabled = true;
	}*/
	//colorFields();
	if(targetFormName.elements.airlineCode.disabled==false){	
		targetFormName.elements.airlineCode.focus();
	}
	else if(targetFormName.elements.levelType.disabled==false){
		targetFormName.elements.levelType.focus();
	}
	targetFormName.elements.comboFlag.value="airportlov";
	targetFormName.elements.comboFlag.defaultValue="airportlov";
	//targetFormName.levelValue.value="";
	//targetFormName.elements.levelValue.defaultValue="";
	document.getElementById("agentStationLabel").style.visibility="visible";
	document.getElementById("stationCodeTextbox").style.visibility="visible";
	

}

function resetFocus(){
	 if(!event.shiftKey){ 
		if((!targetFormName.elements.airlineCode.disabled)){
			targetFormName.elements.airlineCode.focus();
		}
		else{
			if(document.getElementById('checkBox')){
			document.getElementById('checkBox').focus();
			}
			else{
			targetFormName.elements.btClear.focus();
			}
		}								
	}	
}

function listmonitorstock(frm) {
	if(frm.elements.levelType.value != "" && frm.elements.levelValue.value==""){
			//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.enterlevelvalue'/>",1,self);
			  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.enterlevelvalue" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
			return;
			
		}
		if(frm.elements.levelValue.value != "" && frm.elements.levelType.value==""){

				//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.selectleveltype'/>",1,self);
				  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.selectleveltype" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
				return;
		}
		
		//added by a-2848 for bug 20315 on 03Oct08
			if(frm.elements.levelType.value == "FAC"){
			//Added by A-2934 for validating character '&'. There must be only one & in the string.
			levelvalue = frm.elements.levelValue.value;
			if(frm.elements.levelValue.value != "" && frm.elements.levelValue.value.indexOf("-")==-1 || levelvalue.replace(/[^&]/g, '').length != 1){
			//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.invalidfacilityformat'/>", 1, self);
			  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.invalidfacilityformat" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
			return;
			}
			}
	//BUG-20315 ends
	targetFormName.elements.displayPage.value = 1;
	targetFormName.elements.lastPageNum.value= 0;
if(frm.elements.levelType.value != "" && frm.elements.levelValue.value==""){
//Added by A-2052 for the bug 102764 starts
		if(frm.elements.levelType.value != "AGT" ){
			//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.enterlevelvalue'/>",1,self);
			  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.enterlevelvalue" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
			return;
		}
//Added by A-2052 for the bug 102764 ends	
		}
		if(frm.elements.levelValue.value != "" && frm.elements.levelType.value==""){

				//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.selectleveltype'/>",1,self);
				  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.selectleveltype" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
				return;
		}
		

	//BUG-20315 ends
	//Added by A-3313 for the bug ICRD-2796
	if(targetFormName.elements.btGenerateMessage.privileged == 'Y'){
			//targetFormName.btGenerateMessage.disabled=false;
			enableField(targetFormName.elements.btGenerateMessage);
		}
	
	//Added by A-3313 for the bug ICRD-2796 ends
	targetFormName.elements.displayPage.value = 1;
	targetFormName.elements.lastPageNum.value= 0;
	submitForm(frm,'uld.defaults.listuldstockdetails.do');
}

function submitPage(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName,'uld.defaults.listuldstockdetails.do');
}

function clearmonitorstock(frm) {

	submitForm(frm,'uld.defaults.screenloadmonitoruldstock.do');
	//submitFormWithUnsaveCheck('uld.defaults.screenloadmonitoruldstock.do');
}

function closemonitorstock() {

	if(targetFormName.elements.fromScreen.value == 'buildupplan'){
		submitForm(targetFormName,'addons.advancedgha.operations.flthandling.buildupplan.defaultscreenload.do?fromScreen=MonitorULDStock');
	}else{
	location.href = appPath+"/home.jsp";
	}
	
}

function listulddo(frm) {
	var parentCount = 0;
	var childCount = 0;
	var selectedRows = document.getElementsByName('selectedRows');
	var selectedChildRows = document.getElementsByName('selectedChildRows');
		for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked)	{
				parentCount = parentCount + 1;
			}
		}
		for(var i=0;i<selectedChildRows.length;i++) {
			if(selectedChildRows[i].checked)	{
				childCount = childCount + 1;
			}
		}
		if(childCount == 0 && parentCount == 0){
			//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.selectonerow'/>",1,self);
			  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.selectonerow" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });	
				return;
		}
		if(childCount > 0){
			if(validateSelectedCheckBoxes(frm, 'selectedChildRows', 1, 1)){
				submitForm(frm,"uld.defaults.viewuld.do");
			}
		}else if(parentCount > 0){
			if(validateSelectedCheckBoxes(frm, 'selectedRows', 1, 1)){
				submitForm(frm,"uld.defaults.viewuld.do");
			}
		}
}

function setupstock(frm) {
	if(frm.elements.levelType.value!='ARP'){
		//showDialog("<common:message bundle='monitoruldstock' key='uld.defaults.monitoruldstock.leveltypeairport'/>",1,self);
		  showDialog({msg :'<common:message bundle="monitoruldstock" key="uld.defaults.monitoruldstock.leveltypeairport" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:frm
                         });
		return;
	}
	var airlineCode=frm.elements.airlineCode.value;
	//var stationCode=frm.stationCode.value;
	var stationCode=frm.elements.levelValue.value;
	if(frm.elements.selectedRows !=null ){
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1, 1);
		if (check){
			var airlineCode = "";
			var stationCode = "";

			var selectedRows = document.getElementsByName('selectedRows');
			var airlineCodes = document.getElementsByName('airlineCodes');
			var stationCodes = document.getElementsByName('stationCodes');
			for(var i=0;i<selectedRows.length;i++) {
				if(selectedRows[i].checked)	{

					airlineCode = airlineCodes[i].value;
					stationCode = stationCodes[i].value;

				}
			}

			submitForm(frm,'uld.defaults.finduldstocksetup.do?airlineCode='+airlineCode+'&stationCode='+stationCode+'&screenloadstatus=monitorStock');
		}
	}
	else{
		submitForm(frm,'uld.defaults.finduldstocksetup.do?airlineCode='+airlineCode+'&stationCode='+stationCode+'&screenloadstatus=monitorStock');
	}
}

function closeRepairInvoice(frm) {
	submitForm(frm,appPath+'/uld.defaults.closerepairinvoice.do?displayPage=1&lastPageNum=0');
	//self.close();

}


function generate(frm) {
	var uldNumbers = "AKE12345EK";
	var txnRefNos = "3";
	var txnType = "L";
	submitForm(frm,'uld.defaults.generateinvoice.do?uldNumbers='+uldNumbers+'&txnRefNos='+txnRefNos+'&txnType='+txnType);
	//self.close();

}

function onClickPrint(frm) {

	frm.elements.isPreview.value='GENERATE';	
 	generateReport(frm,'/uld.defaults.listuldstockdetails.doReport.do');

 }

//Added by A-8445 as a part of IASCB-43732 Starts
function onClick2DStockPrint(frm) {
   openReportDesignerPopup('GRPULDSTKRPT','true');
}   

function gatherFilterAttributes(reportId){
	var filters;
	if (reportId == 'GRPULDSTKRPT') {
		filters = [	targetFormName.elements.airlineCode.value,
					targetFormName.elements.levelValue.value];
	} 
	return filters;
}
//Added by A-8445 as a part of IASCB-43732 Ends

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deletelistuld.do');
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

function confirmMessage() {

	var frm = targetFormName;
	if(frm.elements.closeStatus.value=="whetherToClose") {
		frm.elements.closeStatus.value="canClose";
		submitForm(frm,'uld.defaults.closerepairinvoice.do');
	}

}

function nonconfirmMessage() {

}

function submitLovPage(lastPg,displayPg){
  targetFormName.elements.lastLovPageNum.value=lastPg;
  targetFormName.elements.displayLovPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.invoicerefnolovscreenload.do');
}


function NavigateZoneDetails(strLastPageNum, strDisplayPage) {

		var frm = targetFormName;

    	frm.elements.lastPageNum.value = strLastPageNum;
    	frm.elements.displayPage.value = strDisplayPage;

      submitForm(frm,'uld.defaults.navigaterepairinvoice.do');

  }

  function colorFields(){
  var total=document.getElementsByName('total');
  var available=document.getElementsByName('available');
  var maxQty=document.getElementsByName('maxQty');
  var minQty=document.getElementsByName('minQty');

  if(total != null){

  	for(var i=0;i<total.length;i++){

		/*var a=total[i].value;
		var b=maxQty[i].value;

  		if(parseInt(a) > parseInt(b)){
  			var totalTxt = document.getElementById('total'+i);
  			totalTxt.style.color = "red";
  			totalTxt.style.fontWeight = "bold";
  			var maxTxt = document.getElementById('maxQty'+i);
			maxTxt.style.color = "red";
  			maxTxt.style.fontWeight = "bold";
		}

        var c=available[i].value;
        var d=minQty[i].value;

		if(parseInt(c) < parseInt(d)){
  			var totalTxt = document.getElementById('available'+i);
  			totalTxt.style.color = "red";
  			totalTxt.style.fontWeight = "bold";
  			var maxTxt = document.getElementById('minQty'+i);
			maxTxt.style.color = "red";
  			maxTxt.style.fontWeight = "bold";
		}*/
			//Change made by Sreekumar S - AirNZCR449 on 02Apr08
			var a=total[i].value;
			var b=maxQty[i].value;
			var c=available[i].value;
	        var d=minQty[i].value;
			var avail =available[i].value;
			
			if(parseInt(avail) > parseInt(b)){
		  		var totalTxt = document.getElementById('available'+i);
				totalTxt.style.color = "red";
				totalTxt.style.fontWeight = "bold";
			}
			else if (parseInt(avail) < parseInt(d)){
				var totalTxt = document.getElementById('available'+i);
				totalTxt.style.color = "red";
				totalTxt.style.fontWeight = "bold";
			}
	        else if(parseInt(avail) == parseInt(b) ||  parseInt(avail) == parseInt(d)){
				var totalTxt = document.getElementById('available'+i);
				totalTxt.style.color = "orange";
				totalTxt.style.fontWeight = "bold";
			}
			else{
				var totalTxt = document.getElementById('available'+i);
				totalTxt.style.color = "green";
				totalTxt.style.fontWeight = "bold";
			}
			//Change made by Sreekumar S - AirNZCR449 on 02Apr08 -ends
  	}
 }
 }


 function sendSCMMessage(){
 	 if(validateSelectedCheckBoxes(targetFormName, 'selectedRows', 1, 1)){
 	 submitForm(targetFormName,"uld.defaults.monitoruldstock.sendscmmessage.do")
  }
	 }
	 
	 /*function natureView(){
		if(targetFormName.viewByNatureFlag.checked){
			document.getElementById('VBN').style.display="block";
			document.getElementById('VWN').style.display="none";
			enableField(targetFormName.uldTypeCode);
			enableField(targetFormName.uldNature);
			enableField(targetFormName.uldlov);
			disableField(targetFormName.uldGroupCode);
			disableField(targetFormName.grouplov);
			targetFormName.uldGroupCode.value="";
		}else{
		
			document.getElementById('VWN').style.display="block";
			document.getElementById('VBN').style.display="none";
			targetFormName.uldTypeCode.value="";
			targetFormName.uldNature.value="ALL";
			disableField(targetFormName.uldTypeCode);
			disableField(targetFormName.uldlov);
			disableField(targetFormName.uldNature);
			enableField(targetFormName.uldGroupCode);
			enableField(targetFormName.grouplov);
  }
	 }*/

function printStock(frm) {

		frm.elements.isPreview.value='PRINT';		
	  	generateReport(frm,'/uld.defaults.listuldstockdetails.doReport.do');

}

function showAgentLOV(){
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=levelValue&formNumber=1&textfiledDesc=&multiselect=Y&stationCode="+targetFormName.elements.loginStation.value;
	var myWindow = openPopUp(StrUrl, "500","400");

}
//added by a-3045 for CR QF1012 starts
function comboChanged(){
	frm=targetFormName;
	//frm.levelValuelov.disabled = false;
	enableField(frm.elements.levelValuelov);
	document.getElementById("agentStationLabel").style.visibility="hidden";
	document.getElementById("stationCodeTextbox").style.visibility="hidden";	
	//added as part of ICRD-3970 by A-3767 on 08Sep11
	//frm.levelValue.disabled=false;
	enableField(frm.elements.levelValue);
	if(frm.elements.levelType.value=="ARP"){
		frm.elements.comboFlag.value="airportlov";
		frm.elements.comboFlag.defaultValue="airportlov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
	}else if(frm.elements.levelType.value=="CNT"){
		frm.elements.comboFlag.value="countrylov";
		frm.elements.comboFlag.defaultValue="countrylov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
	}else if(frm.elements.levelType.value=="REG"){
		frm.elements.comboFlag.value="regionlov";
		frm.elements.comboFlag.defaultValue="regionlov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
	}else if(frm.elements.levelType.value=="HDQ"){
		frm.elements.comboFlag.value="headquarters";
		frm.elements.comboFlag.defaultValue="headquarters";
		frm.elements.levelValue.value="HQ";
		frm.elements.levelValue.defaultValue="HQ";
		//frm.levelValue.disabled=true;
		disableField(frm.elements.levelValue);
	}else if(frm.elements.levelType.value==""){
		document.getElementById("agentStationLabel").style.visibility="hidden";
		document.getElementById("stationCodeTextbox").style.visibility="hidden";
		frm.elements.comboFlag.value="airportlov";
		frm.elements.comboFlag.defaultValue="airportlov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
	}else if ( frm.elements.levelType.value == "FAC") {
		//alert("FAC11");
		frm.elements.comboFlag.value="facilitylov";
		frm.elements.comboFlag.defaultValue="facilitylov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
		
	}
	else if ( frm.elements.levelType.value == "AGT") {
			frm.elements.comboFlag.value="agentlov";
			frm.elements.comboFlag.defaultValue="agentlov";
			frm.elements.levelValue.value="";
			frm.elements.levelValue.defaultValue="";
			document.getElementById("agentStationLabel").style.visibility="visible";
			document.getElementById("stationCodeTextbox").style.visibility="visible";
	}else if ( frm.elements.levelType.value == "ARPGRP") {
		frm.elements.comboFlag.value="airportgrouplov";
		frm.elements.comboFlag.defaultValue="airportgrouplov";
		frm.elements.levelValue.value="";
		frm.elements.levelValue.defaultValue="";
	}
	//alert("length--"+targetFormName.levelType.length);
	for(var j = 0; j < targetFormName.elements.levelType.length ; j++) {
				targetFormName.elements.levelType.options[j].defaultSelected = targetFormName.elements.levelType.options[j].selected;
	}
}

function onclickLevelLOV(){
	frm=targetFormName;
	if(frm.elements.comboFlag.value=="countrylov"){	
		//frm.levelValuelov.disabled = false;
		enableField(frm.elements.levelValuelov);
		displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.levelValue.value,'levelValue','1','levelValue','',0)
	}else if(frm.elements.comboFlag.value=="regionlov"){
	//	frm.levelValuelov.disabled = false;
		enableField(frm.elements.levelValuelov);
		displayLOV('showRegion.do','N','Y','showRegion.do',targetFormName.elements.levelValue.value,'levelValue','1','levelValue','',0)
	}else if(frm.elements.comboFlag.value=="headquarters"){
		//frm.levelValuelov.disabled = true;
		disableField(frm.elements.levelValuelov);
	}else if(frm.elements.comboFlag.value=="airportlov"){
		//frm.levelValuelov.disabled = false;
		enableField(frm.elements.levelValuelov);
		displayLOV('showAirport.do','Y','Y','showAirport.do',targetFormName.elements.levelValue.value,'levelValue','1','levelValue','',0)		
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//frm.levelValuelov.disabled = false;
		enableField(frm.elements.levelValuelov);
		//displayLOV('shared.defaults.agent.screenloadagentlov.do','Y','Y','shared.defaults.agent.screenloadagentlov.do',targetFormName.levelValue.value,'levelValue','1','levelValue','',0)		
		showAgentLOV();
	}else if(frm.elements.comboFlag.value==""){
		//frm.levelValuelov.disabled = false;
		enableField(frm.elements.levelValuelov);
		//displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.levelValue.value,'levelValue','1','levelValue','',0)		
	}else if(frm.elements.comboFlag.value=="facilitylov"){
	//alert("From LOV");
	displayFacilityType(this.frm);
	} else if(frm.elements.comboFlag.value=="airportgrouplov"){
		enableField(frm.elements.levelValuelov);
		showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',targetFormName.elements.levelValue.value,targetFormName.elements.levelType.value,'ULD','','levelValue','','1','0');
	}
}
//added by a-3045 for CR QF1012 ends

//Added by A-8445 as a part of IASCB-43732 Starts
/**
  * showParameterGroupLov function is for displaying the ParameterGroup LOV
  * strAction - Action mapping for the ParameterGroup LOV;
  * multiSelect - Y/N flag for multiple Selection;
  * groupNameVal - the value passed for Group Name(s) field in Parent screen;
  * groupTypeVal - The value passed for Group Type field in Parent screen;
  * textfiledObj - The text field object for group name(s) in Parent screen;
  * formNumber - The Form number for the parent screen;
  * index - The index of the array.If it is a single textfield set it as 0;
  */
function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,groupDescription,textfieldObj,textfieldDescription,formNumber,index){
	var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);
	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}
	var strUrl = strAction+"?multiSelect="+multiSelect+"&pagination=Y&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&groupDescription="+groupDescription+"&textfieldObj="+textfieldObj+"&textfieldDescription="+textfieldDescription+"&formNumber="+formNumber+"&index="+index;
	openPopUp(strUrl,550,575);
	//var myWindow = window.open(strUrl, "LOV", 'status,width=370,height=330,screenX=100,screenY=30,left=250,top=200');
}
//Added by A-8445 as a part of IASCB-43732 Ends

function displayFacilityType(frm){
//alert("2eee");

openPopUp("uld.defaults.airportfacilitymaster.screenloadcommand.do?screenName=monitorULDStock","750", "570");

}