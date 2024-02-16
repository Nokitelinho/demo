<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()
{
	targetFormName.elements.agreementNumber.focus();
	var frm=targetFormName;
	onScreenLoad();
	with(frm){

		evtHandler.addEvents("btnList","list()",EVT_CLICK);
		evtHandler.addEvents("btDelete","onClickDelete()",EVT_CLICK);
		evtHandler.addEvents("btActivate","onClickActivate()",EVT_CLICK);
		evtHandler.addEvents("btInactivate","onClickInActivate()",EVT_CLICK);
		evtHandler.addEvents("btClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btCreate","onClickCreate()",EVT_CLICK);
		evtHandler.addEvents("btClose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btnPrint","onClickPrint()",EVT_CLICK);
		evtHandler.addIDEvents("agrlov","showAgreementNoLov()",EVT_CLICK);
		//evtHandler.addIDEvents("airlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.partyCode.value,'CurrentAirline','1','partyCode','',0)",EVT_CLICK);
		evtHandler.addEvents("agreementNumber","validateAlphanumeric(targetFormName.elements.agreementNumber,'Agreement Number','Invalid Agreement Number',true)",EVT_BLUR);
		evtHandler.addEvents("partyType","comboChanged()",EVT_CHANGE);
		evtHandler.addEvents("partyCode","validatePartyCode()",EVT_BLUR);
		evtHandler.addEvents("airlineLov","showPartyCode()",EVT_CLICK);
		evtHandler.addEvents("fromAirlineLov","showPartyCodeFromPartyType()",EVT_CLICK); 
		evtHandler.addEvents("fromPartyType","comboChangedFromPartyType()",EVT_CHANGE);
		evtHandler.addEvents("fromPartyCode","validatePartyCodeFromPartyType()",EVT_BLUR);
	}
 applySortOnTable("listULDTable",new Array("None","String","String","String","Date","String","None","String")); 
}
function onScreenLoad(){
	if(targetFormName.elements.listStatus.value!="N"){
		disableField(targetFormName.btnPrint);
		disableField(targetFormName.btDelete);
		disableField(targetFormName.btActivate);
		disableField(targetFormName.btInactivate);
	}

}
function list(){
	var frm=targetFormName;
	targetFormName.elements.displayPageNum.value="1";
	targetFormName.elements.lastPageNumber.value="0";
	if(frm.elements.agreementListDate.value != "" &&
		!chkdate(frm.elements.agreementListDate)){
		showDialog({msg:'Invalid Agreement Date',type:1,parentWindow:self,parentForm:frm,dialogId:'id_8'});
		return;
	}
	if(frm.agreementFromDate.value != "" &&
			!chkdate(frm.elements.agreementFromDate)){
			showDialog({msg:'Invalid From Date',type:1,parentWindow:self,parentForm:frm,dialogId:'id_9'});
			return;
	}
	if(frm.agreementToDate.value != "" &&
			!chkdate(frm.elements.agreementToDate)){
			showDialog({msg:'Invalid To Date',type:1,parentWindow:self,parentForm:frm,dialogId:'id_10'});
			return;
	}
	submitForm(frm,'uld.defaults.listuldagreements.do?navigationMode=LIST');
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPageNum.value = strDisplayPage;
	targetFormName.action ="uld.defaults.listuldagreements.do?navigationMode=NAVIGATION";
	targetFormName.submit();
	disablePage();
}

function onClickDelete(){
var frm=targetFormName;
if(validateSelectedCheckBoxes(frm,'check',25,1)){
	var checked = frm.check;
	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "D"){
	showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
	return;
	}
	
	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "A"){
	showDialog({msg:'Active Agreement(s) cannot be deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_6'});
	return;
	}

	var length= checked.length;
	if(length>1){
		for(var i=0;i<length;i++){

			if(checked[i].checked == true && frm.elements.statusvalues[i].value == "D"){
				showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
				return;
			}
			if(checked[i].checked == true && frm.elements.statusvalues[i].value == "A"){
				showDialog({msg:'Active Agreement(s) cannot be deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_6'});
				return;
			}
		}
	}
	else if(length==1){

		if(checked[0].checked == true && frm.elements.statusvalues[0].value == "D"){
			showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
			return;
		}
		if(checked[0].checked == true && frm.elements.statusvalues[0].value == "A"){
			showDialog({msg:'Active Agreement(s) cannot be deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_6'});
			return;
		}
	}
	//showDialog({msg:'Do you want to delete the Inactive Agreements?',type:4,parentWindow:self,parentForm:frm,dialogId:'id_7'});
		showDialog({msg:"Do you want to delete the Inactive Agreements?" ,type:4,parentWindow: self,parentForm:frm,dialogId:'id_7',onClose:function(result){
	screenConfirmDialog(frm,'id_7');
}
		});
	}
}
function onClickActivate(){
var frm=targetFormName;
if(validateSelectedCheckBoxes(frm,'check',25,1)){
	var checked = frm.check;

	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "D"){
	showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
	return;
	}
	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "A"){
	showDialog({msg:'Agreement(s) already active',type:1,parentWindow:self,parentForm:frm,dialogId:'id_3'});
	return;
	}

	var length= checked.length;
	if(length>1){
	for(var i=0;i<length;i++){
	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "D"){
	showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
	return;
	}
	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "A"){
	showDialog({msg:'Please select only inactive agreements',type:1,parentWindow:self,parentForm:frm,dialogId:'id_3'});
	return;
	}
	}
	}
	else if(length==1){
	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "A"){
	showDialog({msg:'Agreement(s) already active',type:1,parentWindow:self,parentForm:frm,dialogId:'id_3'});	
	return;
	}
	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "D"){
	showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
	return;
	}
	}
	 showDialog({ msg: "Do you want to Activate the Inactive Agreements?", type:3, parentWindow:self,parentForm:frm,dialogId:'id_1',
		 onClose:function(result){
			if(result){
			frm.elements.currentDialogOption.value="Y"  ;	
				screenConfirmDialog(frm,'id_1');		  			
			}else{
				frm.elements.currentDialogOption.value="N"  ;	 
			}
		 }
	});	
	}
}

function onClickClear(){
	var frm=targetFormName;
	submitFormWithUnsaveCheck('uld.defaults.clearuldagreements.do');
}
function onClickCreate(){
	var frm=targetFormName;
	submitForm(frm,'uld.defaults.createuldagreements.do');
}
function onClickClose(){
		var frm=targetFormName;
		submitForm(frm,'uld.defaults.closeaction.do');
}
function onClickInActivate(){
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'check',25,1)){
	var checked = frm.check;
	
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "D"){
		showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
		return;
		}
		
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"){
		showDialog({msg:'Agreement(s) already inactive',type:1,parentWindow:self,parentForm:frm,dialogId:'id_4'});
		return;
		}
		
		var length= checked.length;
		if(length>1){
			for(var i=0;i<length;i++){
			
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "D"){
				showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
				return;
				}
				
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"){
				showDialog({msg:'Please select only active agreements',type:1,parentWindow:self,parentForm:frm,dialogId:'id_4'});
				return;
				}
			}
		}
		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"){
				showDialog({msg:'Agreement(s) already inactive',type:1,parentWindow:self,parentForm:frm,dialogId:'id_4'});
				return;
			}
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "D"){
				showDialog({msg:'Agreement(s) already deleted',type:1,parentWindow:self,parentForm:frm,dialogId:'id_5'});
				return;
			}
		}
	 showDialog({ msg: "Do you want to Inactivate the active Agreements?", type:3, parentWindow:self,parentForm:frm,dialogId:'id_2',
		 onClose:function(result){
			if(result){
			frm.elements.currentDialogOption.value="Y"  ;	
				screenConfirmDialog(frm,'id_2');		  			
			}else{
					frm.elements.currentDialogOption.value="N"  ;	 
			}
		 }
	});	
	
	}
 }
 function onClickView(){
	 var frm=targetFormName;
	 var canClear="canClearTrue";
	 if(validateSelectedCheckBoxes(frm,'check',25,1)){
	  submitForm(frm,"uld.defaults.viewuldagreements.do?canClear="+canClear);
		}
 }

 function screenConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){
 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {
 		if(dialogId == 'id_1'){
 			var selection="true";
 			submitForm(frm,"uld.defaults.updatestatusuldagreements.do?flag="+selection);
 		}

 		if(dialogId == 'id_2'){
 			var selection = "false";
 			submitForm(frm,"uld.defaults.updatestatusuldagreements.do?flag="+selection);
 			}
 		if(dialogId == 'id_7'){
			submitForm(frm,"uld.defaults.deleteuldagreements.do");
 		}
		
		frm.elements.currentDialogOption.value == 'N';
 	}
 }

 function screenNonConfirmDialog(frm, dialogId) {

 	while(frm.elements.currentDialogId.value == ''){
 	}

 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_1'){
		return;
 		}
 		if(dialogId == 'id_2'){
		return;
 		}
 		if(dialogId == 'id_7'){
		return;
 		}

 	}
}

function onClickPrint() {
	var frm=targetFormName;
	generateReport(frm,"/uld.defaults.listuldagreement.doReport.do");
 }

function chkDate(date){

 	if(date.value==""){
 	return;
 	}
    else
 	{
 	checkdate(date, 'DD-MMM-YYYY');
 	}
}

function showAgreementNoLov(){
		var agrNo=targetFormName.elements.agreementNumber.value;
		var strUrl="uld.defaults.screenloadagreementnolov.do?agreementNo="+agrNo;
		openPopUpWithHeight(strUrl,"500");
	}
function comboChanged(){

	frm=targetFormName;
	if(frm.elements.partyType.value=="G"){
		frm.elements.partyCode.value="";
		frm.elements.comboFlag.value="agentlov";
		frm.elements.airlineLov.disabled = false;
	}else if(frm.elements.partyType.value=="O"){
		frm.elements.partyCode.value="";
		frm.elements.comboFlag.value="others";
	}else if(frm.elements.partyType.value=="A"){
		frm.elements.partyCode.value="";
		frm.elements.comboFlag.value="airlinelov";
		frm.elements.airlineLov.disabled = false;
	}else if(frm.elements.partyType.value=="C"){
		frm.elements.partyCode.value="";
		frm.elements.comboFlag.value="customerlov";
		frm.elements.airlineLov.disabled = false;
	}else {
		frm.elements.comboFlag.value="others";
		frm.elements.airlineLov.disabled = false;
		frm.elements.partyCode.value="";
	}
}
function showPartyCode(){
	var frm=targetFormName;
	if(frm.elements.comboFlag.value=='airlinelov'){
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.partyCode.value,'partyCode','1','partyCode','',0)
	}else if(frm.elements.comboFlag.value=='agentlov'){
		var textobj = "";
		var strAction="shared.defaults.agent.screenloadagentlov.do";
		var StrUrl=strAction+"?textfiledObj=partyCode&formNumber=1&textfiledDesc="+textobj;
		var myWindow = openPopUp(StrUrl, "500","400")
	}else if(frm.elements.comboFlag.value=='customerlov'){
		var textobj = "";
		var strAction="shared.defaults.agent.screenloadcustomerlov.do";
		var StrUrl=strAction+"?textfiledObj=partyCode&formNumber=1&textfiledDesc="+textobj;
		var myWindow = openPopUp(StrUrl, "500","400")
	}else if(frm.elements.comboFlag.value=='others'){
		frm.elements.airlineLov.disabled = true;
	}
}
	function validatePartyCode(){
	var frm=targetFormName;
	if(frm.elements.partyType.value=="A"){

		if(frm.elements.partyCode.value.length > 3){
				showDialog({msg:'Please enter character(s) less than 3 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.partyCode.focus();
				return;
		}
	}else if(frm.elements.partyType.value=="G"){

		if(frm.elements.partyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.partyCode.focus();
				return;
		}
	}else if(frm.elements.partyType.value=="ALL"){

		if(frm.elements.partyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.partyCode.focus();
				return;
		}
	}
	else if(frm.elements.partyType.value=="O"){

		if(frm.elements.partyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.partyCode.focus();
				return;
		}
	}
}
function comboChangedFromPartyType(){

	frm=targetFormName;
	if(frm.elements.fromPartyType.value=="G"){
		frm.elements.fromPartyCode.value="";
		frm.elements.comboFlag.value="agentlov";
		frm.elements.fromAirlineLov.disabled = false;
	}else if(frm.elements.fromPartyType.value=="O"){
		frm.elements.fromPartyCode.value="";
		frm.elements.comboFlag.value="others";
	}else if(frm.elements.fromPartyType.value=="A"){
		frm.elements.fromPartyCode.value="";
		frm.elements.comboFlag.value="fromAirlineLov";
		frm.elements.fromAirlineLov.disabled = false;
	}else if(frm.elements.fromPartyType.value=="C"){
		frm.elements.fromPartyCode.value="";
		frm.elements.comboFlag.value="customerlov";
		frm.elements.fromAirlineLov.disabled = false;
	}else {
		frm.elements.comboFlag.value="others"; 
		frm.elements.fromAirlineLov.disabled = false;
		frm.elements.fromPartyCode.value="";
	}
}
function showPartyCodeFromPartyType(){
	var frm=targetFormName;
	if(frm.elements.comboFlag.value=='fromAirlineLov'){
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','',0)
	}else if(frm.elements.comboFlag.value=='agentlov'){
		var textobj = "";
		var strAction="shared.defaults.agent.screenloadagentlov.do";
		var StrUrl=strAction+"?textfiledObj=fromPartyCode&formNumber=1&textfiledDesc="+textobj;
		var myWindow = openPopUp(StrUrl, "500","400")
	}else if(frm.elements.comboFlag.value=='customerlov'){
		var textobj = "";
		var strAction="shared.defaults.agent.screenloadcustomerlov.do";
		var StrUrl=strAction+"?textfiledObj=fromPartyCode&formNumber=1&textfiledDesc="+textobj;
		var myWindow = openPopUp(StrUrl, "500","400")
	}else if(frm.elements.comboFlag.value=='others'){
		frm.elements.fromAirlineLov.disabled = true;
	}
}
function validatePartyCodeFromPartyType(){
	var frm=targetFormName;
	if(frm.elements.fromPartyType.value=="A"){

		if(frm.elements.fromPartyCode.value.length > 3){
				showDialog({msg:'Please enter character(s) less than 3 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.fromPartyCode.focus();
				return;
		}
	}else if(frm.elements.fromPartyType.value=="G"){

		if(frm.elements.fromPartyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.fromPartyCode.focus();
				return;
		}
	}else if(frm.elements.fromPartyType.value=="ALL"){

		if(frm.elements.fromPartyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.fromPartyCode.focus();
				return;
		}
	}
	else if(frm.elements.fromPartyType.value=="O"){

		if(frm.elements.fromPartyCode.value.length > 12){
				showDialog({msg:'Please enter character(s) less than 12 for  Party Code',type:1,parentWindow:self});
				targetFormName.elements.fromPartyCode.focus();
				return;
		}
	}
}