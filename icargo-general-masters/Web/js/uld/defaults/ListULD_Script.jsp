<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


//window.onload=initForm;
function screenSpecificEventRegister()
{

	var frm=targetFormName;

	frm.elements.uldNumber.focus();
	onScreenLoad(); //testcomment for uld html5 migration
	//onScreenloadSetHeight();
	with(frm){

	//CLICK Events
	evtHandler.addIDEvents("levelValuelov","listLevelTypeLOV()",EVT_CLICK);
	evtHandler.addEvents("levelType","doComboChange()",EVT_CHANGE);
	evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btDelete","deleteuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btDetails","detailuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btCreate","createuld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btDamageReport","damageReport(this.form)",EVT_CLICK);
	evtHandler.addEvents("btRelocateUld","relocateUld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btRelocateUldMovement","recordUld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btUldDiscrepency","uldDiscrepency(this.form)",EVT_CLICK);
	evtHandler.addEvents("btCreateTxn","createTxn(this.form)",EVT_CLICK);
	evtHandler.addEvents("btPrintUldInventory","onClickPrintUldInventory(this.form)",EVT_CLICK); 
	evtHandler.addEvents("btPrint","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("btPrintAll","onClickPrintAll(this.form)",EVT_CLICK);
	evtHandler.addEvents("agentlov","showAgentLOV()",EVT_CLICK);
    evtHandler.addEvents("airlineLovImg","showAirlineLOV()",EVT_CLICK);

	evtHandler.addEvents("uldNumber","validateFields(this, -1, 'Uld Number', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldGroupCode","validateFields(this, -1, 'UldGroupCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldTypeCode","validateFields(this, -1, 'UldType', 0, true, true)",EVT_BLUR, 3 , 2);
	//evtHandler.addEvents("airlineCode","validateFields(this, -1, 'OwnerAirlineCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("currentStation","validateFields(this, -1, 'CurrentStation', 1, true, true)",EVT_BLUR);
	evtHandler.addEvents("ownerStation","validateFields(this, -1, 'OwnerStation', 1, true, true)",EVT_BLUR);
	evtHandler.addEvents("location","validateFields(this, -1, 'Location', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldRangeFrom","validateFields(this, -1, 'Uld Range From', 3, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldRangeTo","validateFields(this, -1, 'Uld Range To', 3, true, true)",EVT_BLUR);
	evtHandler.addEvents("ownerAirline","validateFields(this, -1, 'OwnerAirline', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("agentCode","validateFields(this, -1, 'AgentCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("airlineCode","validateFields(this, -1, 'OperatingAirline', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("btGenScm","generateSCM()",EVT_CLICK);
	evtHandler.addEvents("levelValuelov","listLevelTypeLOV()",EVT_CLICK);
	//Added for CR QF1267..Ulds to be offloaded from the flight on clicking Offload button
	evtHandler.addEvents("btOffload","offloadULD(this.form)",EVT_CLICK);
	//QF1267 ends
	}
	//alert("test");
        applySortOnTable("listuldtable",new Array("None","String","String","String","String","String","String","String","String","String","String","String","Date","Number","String","None","String","String","None"));


}
function onScreenloadSetHeight(){
 	jquery('#outertable').height((((document.body.clientHeight)*0.69)));
	jquery('#div1').height((((document.body.clientHeight)*0.79)-350));
}


function list(frm) {
//added by a-2052 for bug 102280 on 28OCT10 starts
if(frm.elements.airlineCode.value=="" && frm.elements.uldNumber.value=="" && frm.elements.ownerAirline.value==""){
	showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.enteruld' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
	return;

}
//added by a-2052 for bug 102280 on 28OCT10 ends
frm.elements.lastPageNum.value=0;
frm.elements.displayPage.value=1;

   /* if(eitherfromortodate(frm)) {
			showDialog("Either From Date or To Date should be specified", 1, self, frm, 'id_7');
			return false;
		}*/
	if(frm.elements.levelType.value =="FAC" && frm.elements.currentStation.value ==""){
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.entercurrentairport' />",type: 1,parentWindow:self});
		
		return;
	}
	if(frm.elements.levelType.value != "" && frm.elements.levelValue.value==""){
			showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.enterlvlvalue' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
			return;

		}
		if(frm.elements.levelValue.value != "" && frm.elements.levelType.value==""){
			showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.enterlvltype' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
			return;

	}
	//added by a-3278 for bug 17982 on 06Sep08
	if(frm.elements.levelType.value == "FAC"){
	if(frm.elements.levelValue.value != "" && frm.elements.levelValue.value.indexOf("-")==-1){
	showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.invalidfacformat' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
	return;
	}
	}
	//a-3278 ends

	submitForm(frm,'uld.defaults.listuld.do?countTotalFlag=N');
}

function createTxn(frm) {
	submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do?closeStatus=ListULD');
}


function createuld(frm) {
	submitForm(frm,'uld.defaults.screenloadmaintainuld.do?screenloadstatus=listcreate');
}

function detailuld(frm) {


	var selectedUlds = "";
	var selectedRows = document.getElementsByName('selectedRows');
	var selectedUldRows = document.getElementsByName('uldNumbers');
	for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked)	{
				var val = selectedUldRows[i].value;
				if(selectedUlds != "") {
					selectedUlds = selectedUlds+","+val;
				}
				else if(selectedUlds == ""){
					selectedUlds = val;
				}
			}
		}
	//added by A-5844 for the BUG ICRD-63084 start
	if(targetFormName.elements.screenLoadStatus.value == "monitorStock"){
		submitForm(frm,'uld.defaults.detailuldinfo.do?uldNumbersSelected='+selectedUlds+'&screenloadstatus=monitorStock&transitDisable=Y');
	}//added by A-5844 for the BUG ICRD-63084 end
	if(selectedUlds != "") {
		submitForm(frm,'uld.defaults.detailuldinfo.do?uldNumbersSelected='+selectedUlds+'&screenloadstatus=listdetail&transitDisable=Y');
	}
	else {
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.norowselected' />", type:1, parentWindow:self});
		
	}
}

function damageReport(frm) {
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1, 1);
	if (check) {
	var selectedUlds = "";
	var selectedRows = document.getElementsByName('selectedRows');
	var selectedUldRows = document.getElementsByName('uldNumbers');
	for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked)	{
				var val = selectedUldRows[i].value;
				if(selectedUlds != "") {
					selectedUlds = selectedUlds+","+val;
				}
				else if(selectedUlds == ""){
					selectedUlds = val;
				}
			}
		}
	if(selectedUlds != "") {
		submitForm(frm,'uld.defaults.ux.screenloadmaintaindamagereport.do?uldNumber='+selectedUlds+'&fromScreen=ULD006');
	}
	else {
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.norowselected' />",  type:1, parentWindow:self});
		}
	}
 }
function recordUld(frm) {

	var inTransit = "";
	var selectedRows = document.getElementsByName('selectedRows');
	var selectedTransitStat = document.getElementsByName('transitStatus');


	for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked)	{
				if(selectedTransitStat[i]!=null){
				var val = selectedTransitStat[i].value;
				if( val=="Y"){
					showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.uldintransit' />", type:1, parentWindow:self});
					return;
				}}
			}
	}

	var selectedUlds = "";
	var selectedRows = document.getElementsByName('selectedRows');


	var selectedUldRows = document.getElementsByName('uldNumbers');


	for(var i=0;i<selectedRows.length;i++) {
		if(selectedRows[i].checked)
			{
				var val = selectedUldRows[i].value;
				if(selectedUlds != "") {
					selectedUlds = selectedUlds+","+val;
				}
				else if(selectedUlds == ""){
					selectedUlds = val;
			}
		}
	}

	if(selectedUlds != "") {
           openPopUp("uld.defaults.misc.screenloadrecorduldmovement.do?uldNumbers="+selectedUlds+"&screenName=listUld","950", "520");


	}
	else {
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.norowselected' />", type:1, parentWindow:self});
	}
}


function relocateUld(frm) {
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1000000000000, 1);
	if (check){
		if(validateFromLocationFields(frm)!="") {
			showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.locrowsame' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
			return false;
		}

		if(validateCurrentStationFields(frm)!="") {
			showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.currarpsame' />", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
			return false;
		}
		var selectedULD = "";
		var selectedRows = document.getElementsByName('selectedRows');
		var selectedUldRows = document.getElementsByName('locationHidden');
		var currentStationHidden = document.getElementsByName('currentStationHidden');
		var selectedUld = document.getElementsByName('uldNumbers');
		var hiddenOccupancy = document.getElementsByName('hiddenOccupancy');
		var hiddenOverAllStatus = document.getElementsByName('hiddenOverAllStatus');
		var hiddenFacility = document.getElementsByName('hiddenFacility');
		for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked){
					var valCurrent = currentStationHidden[i].value;
					var val = selectedUldRows[i].value;
					var vals = selectedUld[i].value;
					if(hiddenOverAllStatus[i].value == "N"){
						showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.unserviceuldnorelocate' />", type:1, parentWindow:self});
						return false;
					}
					if(hiddenFacility[i].value == "AGT"){
					showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.agentuldnorelocate' />", type:1, parentWindow:self});
					return false;
					}
					if(hiddenOccupancy[i].value == "Y"){
						showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.occupieduld' />", type:1, parentWindow:self});
						return false;
					}
					if(selectedULD != "") {
						selectedULD = selectedULD+","+vals;
					}
					else if(selectedULD == ""){
						selectedULD = vals;
					}
			}
		}
		openPopUp("uld.defaults.listuld.screenloadrelocateuld.do?fromLocation="+val+"&uldNumber="+selectedULD+"&currentStation="+valCurrent,"610", "200");

	}
}


function closeuld(frm) {
	submitForm(frm,'uld.defaults.closelistuld.do');
}
function clearuld(frm) {
	//submitForm(frm,'uld.defaults.clearlistuld.do');
	submitForm(frm,'uld.defaults.clearlistuld.do');
}

function onClickPrint(frm) {


 	//generateReport(frm,'/uld.defaults.listuld.doReport.do');
 	generateReport(frm,'/uld.defaults.printListUld.doReport.do');

}
function onClickPrintUldInventory(frm) {
 	generateReport(frm,'/uld.defaults.listuld.printUldInventory.do?statusFlag=PRINTULDINVENTORY');
}

function onClickPrintAll(frm) {


 	generateReport(frm,'/uld.defaults.listuld.doReport.do');

}

function deleteuld(frm) {
	var selectedRow = document.getElementsByName('selectedRows');
	var hiddenOccupancy = document.getElementsByName('hiddenOccupancy');
	for(var i=0;i<selectedRow.length;i++) {
		if(selectedRow[i].checked){
			if(hiddenOccupancy[i].value == "Y"){
				showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.uldinuse' />", type:1, parentWindow:self});
				return;
			}
		}
	}

var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].name =='selectedRows')
		{
			if(targetFormName.elements[i].checked)
			{
				var val = targetFormName.elements[i].value;
				if(selectedRows != "") {
					selectedRows = selectedRows+","+val;
				}
				else if(selectedRows == ""){
					selectedRows = val;
				}
			}
		}
	}

	if(selectedRows != "") {
	//	showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.deleteConfirm' />",  
		//type:4, parentWindow:self,parentForm:frm, dialogId:'id_1'});//commented for ICRD-141299
		
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.deleteConfirm' />", type:4, parentWindow:self, parentForm:frm, dialogId:'id_1',
						onClose: function (result) {//Added for ICRD-141299 by A-5117
							if(frm.elements.currentDialogOption.value == 'Y') {
		screenConfirmDialog(frm,'id_1');
							}else if(frm.elements.currentDialogOption.value == 'N') {
		screenNonConfirmDialog(frm,'id_1');
	}
						}});
		//screenConfirmDialog(frm,'id_1');
		//screenNonConfirmDialog(frm,'id_1');
	}
	else {
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.norowselected' />", type:1, parentWindow:self});
	}


}

function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value=lastPg;
  targetFormName.elements.displayPage.value=displayPg;
  submitForm(targetFormName, appPath + '/uld.defaults.listuld.do?countTotalFlag=Y&autoCollapse=true');// changed for for icrd-274441
}

function onScreenLoad(){


//added for resolution starts
//var clientHeight = document.body.clientHeight;
//var clientWidth = document.body.clientWidth;

//alert(clientHeight);
//document.getElementById('contentdiv').style.height = ((clientHeight*87)/100)+'px';
//document.getElementById('outertable').style.height=((clientHeight*79)/100)+'px';

//alert(document.getElementById('outertable').style.height);

//var pageTitle=50;
//var filterlegend=60;
//var filterrow=80;
//var bottomrow=50;
//var height=(clientHeight*79)/100;
//var tableheight=height-(pageTitle+filterlegend+filterrow+bottomrow);
//document.getElementById('div1').style.height=tableheight+'px';
// added for resolution ends
//alert(targetFormName.disableStatus.value);
//added by A-4443 for icrd-3722 starts
if(targetFormName.elements.disableStatus.value == 'errors_present'){
targetFormName.elements.btDetails.disabled = true;
targetFormName.elements.btDelete.disabled = true;
targetFormName.elements.btPrint.disabled = true;
targetFormName.elements.btPrintAll.disabled = true;
targetFormName.elements.btRelocateUld.disabled = true;
targetFormName.elements.btRelocateUldMovement.disabled = true;
targetFormName.elements.btDamageReport.disabled = true;
targetFormName.elements.btUldDiscrepency.disabled = true;
targetFormName.elements.btGenScm.disabled = true;
targetFormName.elements.btOffload.disabled = true;
targetFormName.elements.disableStatus.value='';
}
//added by A-4443 for icrd-3722 ends
/*Commenting for ICRD-1838
//added by a-3045 for BUG_5750 starts
if(targetFormName.airlineCode.value != ""){
	targetFormName.airlineCode.disabled = true;
	targetFormName.airlineLovImg.disabled = true;
}
//added by a-3045 for BUG_5750 ends

	if(targetFormName.disableStatus.value=="airline"){
		targetFormName.airlineCode.disabled = true;
		targetFormName.airlineLovImg.disabled = true;
	}
*/
	/*
	Commented by A-3069 for CR1503
	if(targetFormName.disableStatus.value=="GHA"){
		targetFormName.currentStation.disabled = true;
		targetFormName.airportLovImg.disabled = true;
	}*/
}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deletelistuld.do');
		}

		if(dialogId == 'id_2'){
			submitForm(frm,'uld.defaults.listuld.offloaduld.do');
		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}
	}
}

function validateFromLocationFields(frm) {
	var selectedRows = document.getElementsByName('selectedRows');
	var returnLoc = "";
	var flag=false;
	if(selectedRows != null && selectedRows.length>1) {
		var size = selectedRows.length;
		for(var j=0; j<=size-1; j++) {
			if(selectedRows[j].checked){
				var fromLocation = document.getElementsByName('locationHidden');
				var first = fromLocation[j].value;
				for(var i=0; i<=size-1; i++) {
					if(selectedRows[i].checked){
					if(i!=j){
						var locHidden = document.getElementsByName('locationHidden');
						var next = locHidden[i].value;
						if(first != next) {
							returnLoc = fromLocation;
							flag=true;
							break;
						}
					}
					}
				}
				if(flag){
					break;
				}
			}

		}
	}
	if(returnLoc != "") {
	}
	return returnLoc;
}

function validateCurrentStationFields(frm) {
	var selectedRows = document.getElementsByName('selectedRows');
	var returnLoc = "";
	var flag=false;
	if(selectedRows != null && selectedRows.length>1) {
		var size = selectedRows.length;
		for(var j=0; j<=size-1; j++) {
			if(selectedRows[j].checked){
				var fromLocation = document.getElementsByName('currentStationHidden');
				var first = fromLocation[j].value;
				for(var i=0; i<=size-1; i++) {
					if(selectedRows[i].checked){
					if(i!=j){
						var locHidden = document.getElementsByName('currentStationHidden');
						var next = locHidden[i].value;
						if(first != next) {
							returnLoc = fromLocation;
							flag=true;
							break;
						}
					}
					}
				}
				if(flag){
					break;
				}
			}

		}
	}
	if(returnLoc != "") {
	}
	return returnLoc;
}


function uldDiscrepency(frm) {
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1, 1);
	if (check){
		var selectedUlds = "";
		var selectedRows = document.getElementsByName('selectedRows');


		var selectedUldRows = document.getElementsByName('uldNumbers');


		for(var i=0;i<selectedRows.length;i++) {
			if(selectedRows[i].checked)
				{
					var val = selectedUldRows[i].value;
					if(selectedUlds != "") {
						selectedUlds = selectedUlds+","+val;
					}
					else if(selectedUlds == ""){
						selectedUlds = val;
				}
			}
		}
		if(selectedUlds != "") {
		   submitForm(frm,"uld.defaults.listulddiscrepancies.ulddiscrepancy.do?uldNumber="+selectedUlds);
		}
	}
}

function showAgentLOV(){
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agentCode&formNumber=1&textfiledDesc=";
	var myWindow = openPopUp(StrUrl, "500","400");

}

function eitherfromortodate(frm){
		if(frm.elements.fromDate.value=="" && frm.elements.toDate.value==""){
			return true;
		}else{
			return false;
		}
}

function showAirlineLOV(){
displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline','1','airlineCode','',0);

}

function generateSCM(){
	var selectedRows = document.getElementsByName('selectedRows');
	var currentStationHidden = document.getElementsByName('currentStationHidden');
	var airline = document.getElementsByName('operatingAirlineHidden');
	var loanedToParty = document.getElementsByName('loanedToParty');
	var levelType=targetFormName.elements.levelType.value;
	var levelValue=targetFormName.elements.levelValue.value;
	var facility="";
	var location="";
	if(levelType == "FAC"){
	var facVals= levelValue.split("-");
		if(facVals.length == 2){
			facility = facVals[0];
			location = facVals[1];
		}else{
			facility = facVals[0];
		}

	}
	var uldSelected = "N";
	var scmAirline ='';
	var scmAirport ='';
	var key='';
	var primaryKey='';
	var dupValue="N";
	var i=0;
	var loanedParties="N";
	if(selectedRows != null && selectedRows.length>0) {

			var size = selectedRows.length;

			for(var j=0; j<=size-1; j++) {
			if(selectedRows[j].checked){
				scmAirline=airline[j].value;
				scmAirport = currentStationHidden[j].value;
				if(loanedToParty[j].value != ""){
					loanedParties = "Y";
					break;
				}
				if(uldSelected=="N"){
					primaryKey = scmAirline+'-'+scmAirport;
				}
				uldSelected = "Y";
				key = scmAirline+'-'+scmAirport;

				if(key != primaryKey){
					dupValue = "Y";
					break;
				}
			}
		}
	}
	if(loanedParties == "Y"){
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.msg.warn.loanedULDsCannotSendSCM' />",  type:1, parentWindow:self});
		return;
	}
	if(uldSelected == "Y"){
		if(dupValue == "Y"){
			showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.selectuldorparl' />", type:1, parentWindow:self});
				return;
		}
		submitForm(targetFormName,"uld.defaults.listuld.generatescm.do?scmAirline="+scmAirline+
		"&scmAirport="+scmAirport+"&pageURL=LISTULDS"+"&facilityFilter="+facility+"&locationFilter="+location);
	}else{
			var airline = targetFormName.airlineCode.value;
			var station = targetFormName.currentStation.value;
			if(airline =="" ){
				showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.enteroprairline' />",  type:1, parentWindow:self});
				return;
			}
			if(station ==""){
				showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.warn.entercurrarp' />", type:1, parentWindow:self});
				return;
			}
		submitForm(targetFormName,"uld.defaults.listuld.generatescm.do?scmAirline="+airline+
		"&scmAirport="+station+"&pageURL=LISTULD"+"&facilityFilter="+facility+"&locationFilter="+location);
	}
}

/*function listLevelTypeLOV( lovIdentifier ) {
alert('111');
	var frm =targetFormName ;

	if ( lovIdentifier == "LEVELTYPELOV" ) {
		alert("From if"+frm.levelType.value);
		if ( frm.levelType.value == "ARP" ) {
		alert(2);
		displayLOV('showAirport.do','Y','Y','showAirport.do',frm.levelValue.value,'Airport','1','levelValue','','',0);
				//displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].ownerStation.value,'OwnerStation','1','ownerStation','',0)" alt="Airport LOV"/>
		}
		if ( frm.levelType.value == "CNT" ) {
		alert("country");
		displayLOV('showCountry.do','Y','Y','showCountry.do',frm.levelValue.value,'Country','1','levelValue','','',0);

		}
		if ( frm.levelType.value == "REG" ) {
			alert("region");
		displayLOV('showRegion.do','Y','Y','showRegion.do',frm.levelValue.value,'Region','1','levelValue','','',0);

		}
		if ( frm.levelType.value == "HDQ" ) {
					alert("Head");
			frm.levelValuelov.disabled = true;
			frm.levelValue.value="HQ";
			alert("after region");

		}
		if ( frm.levelType.value == "FAC") {
		alert("FAC11");
		//var strUrl = strAction+"?airportCode="+airportVal+"&warehouseCode="+warehouseVal+"&textfiledObj="+textfiledObj+"&formNumber="+formNumber+"&textfiledDesc="+textfiledDesc;
		//var strUrl="/uld.defaults.airportfacilitymaster.screenloadcommand.do";
		//openPopUp(strUrl,500,350);
		//displayLOV('uld.defaults.airportfacilitymaster.screenloadcommand.do','Y','Y','uld.defaults.airportfacilitymaster.screenloadcommand.do',frm.levelValue.value,'facilityType','1','levelValue','','',0);
		displayFacilityType(this.frm);
		}
	}
	}*/

function displayFacilityType(frm){
//alert("2eee");
var currentAirport = frm.elements.currentStation.value;
openPopUp("uld.defaults.airportfacilitymaster.screenloadcommand.do?screenName=listUld&airportCode="+currentAirport,"750", "570");

}

function doComboChange(){
var frm=targetFormName;
//alert("frm combo");
	frm.levelValuelov.disabled = false;
	frm.elements.levelValue.disabled=false;
	if(frm.elements.levelType.value=="ARP"){
		frm.elements.comboFlag.value="airportlov";
		frm.elements.levelValue.value="";
	}else if(frm.elements.levelType.value=="CNT"){
		frm.elements.comboFlag.value="countrylov";
		frm.elements.levelValue.value="";
	}else if(frm.elements.levelType.value=="REG"){
		frm.elements.comboFlag.value="regionlov";
		frm.elements.levelValue.value="";
	}else if(frm.elements.levelType.value=="HDQ"){
		frm.elements.comboFlag.value="headquarters";
		frm.elements.levelValue.value="HQ";
		frm.elements.levelValue.disabled=true;
	}else if(frm.elements.levelType.value=="FAC"){
		frm.elements.comboFlag.value="facilitytypelov";
		frm.elements.levelValue.value="";
	}else{
		frm.levelValuelov.disabled = true;
		frm.elements.levelValue.value="";
	}
}

function listLevelTypeLOV(){
//alert("from LOV TYPE");
	frm=targetFormName;
	if(frm.elements.levelType.value =="FAC" && frm.elements.currentStation.value ==""){
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.entercurrentairport' />",  type:1, parentWindow:self});
		return;
	}
	if(frm.elements.comboFlag.value=="countrylov"){
	//alert("country");
			displayLOV('showCountry.do','Y','Y','showCountry.do',frm.elements.levelValue.value,'Country','1','levelValue','','',0);

	}else if(frm.elements.comboFlag.value=="regionlov"){
		//alert("region");
			displayLOV('showRegion.do','Y','Y','showRegion.do',frm.elements.levelValue.value,'Region','1','levelValue','','',0);

	}else if(frm.elements.comboFlag.value=="headquarters"){
		//alert("Head");
					frm.levelValuelov.disabled = true;
					frm.elements.levelValue.value="HQ";
			//alert("after region");
	}else if(frm.elements.comboFlag.value=="airportlov"){
		//alert(2);
		displayLOV('showAirport.do','Y','Y','showAirport.do',frm.elements.levelValue.value,'Airport','1','levelValue','','',0);
	}else if(frm.elements.comboFlag.value=="facilitytypelov"){
	//alert("FAC11");
			//var strUrl = strAction+"?airportCode="+airportVal+"&warehouseCode="+warehouseVal+"&textfiledObj="+textfiledObj+"&formNumber="+formNumber+"&textfiledDesc="+textfiledDesc;
			//var strUrl="/uld.defaults.airportfacilitymaster.screenloadcommand.do";
			//openPopUp(strUrl,500,350);
			//displayLOV('uld.defaults.airportfacilitymaster.screenloadcommand.do','Y','Y','uld.defaults.airportfacilitymaster.screenloadcommand.do',frm.levelValue.value,'facilityType','1','levelValue','','',0);
		displayFacilityType(this.frm);
	}else if(frm.elements.comboFlag.value==""){
	frm.levelValuelov.disabled = true;
	}
}


//Added for CR QF1267..Ulds to be offloaded from the flight on clicking Offload button
function offloadULD(frm) {
	var selectedRow = document.getElementsByName('selectedRows');
	var hiddenOccupancy = document.getElementsByName('hiddenOccupancy');
	var selectedRows = "";
	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].name =='selectedRows')
		{
			if(targetFormName.elements[i].checked)
			{
				var val = targetFormName.elements[i].value;
				if(selectedRows != "") {
					selectedRows = selectedRows+","+val;
				}
				else if(selectedRows == ""){
					selectedRows = val;
				}
			}
		}
	}

	if(selectedRows != "") {
	//Added by A-6223 for ICRD-190879 starts
	var hiddenTransitStatus = document.getElementsByName('hiddenTransitStatus');
	for(var i=0;i<selectedRow.length;i++) {
		if(selectedRow[i].checked){
			if(hiddenTransitStatus[i].value == "N"){
				showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.notintransit' />", type:1, parentWindow:self});
				return;
			}
		}
	}
		//Added by A-6223 for ICRD-190879 ends
		//showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.offloadConfirm' />", type:4, parentWindow:self,parentForm:frm, dialogId:'id_1'}); //commented for ICRD-176179
		//added for ICRD-176179 by A-5117
		//Modified by A-6223 for ICRD-190879 
	showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.releaseintransitConfirm' />",  type:4, parentWindow:self, parentForm:frm, dialogId:'id_1',
						onClose: function (result) {
							if(frm.elements.currentDialogOption.value == 'Y') {
		screenConfirmDialog(frm,'id_2');
							}else if(frm.elements.currentDialogOption.value == 'N') {
		screenNonConfirmDialog(frm,'id_2');
								}
						}});
		//screenConfirmDialog(frm,'id_2');
		//screenNonConfirmDialog(frm,'id_2');
	}
	else {
		showDialog({msg:"<common:message bundle='listuldResources' key='uld.defaults.listuld.norowselected' />", type:1, parentWindow:self});
	}
}
//QF1267 ends