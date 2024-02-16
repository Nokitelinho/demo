<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	//onScreenloadSetHeight();
	var frm = targetFormName;

	//Changed For ICRD-218734 by A-6858
	/*
	targetFormName.elements.btConfirm.disabled=true;
	targetFormName.elements.btMissing.disabled=true;
	targetFormName.elements.btBlackList.disabled=true;
	*/
	disableField(targetFormName.elements.btConfirm);
	disableField(targetFormName.elements.btMissing);
	disableField(targetFormName.elements.btBlackList);
   
	with(frm){

		
		if(targetFormName.elements.checkall){
			evtHandler.addEvents("chkBox","updateHeaderCheckBox(targetFormName,targetFormName.elements.chkBox,targetFormName.elements.checkRow)",EVT_CLICK);
		}
		if(targetFormName.elements.checkRow){
			evtHandler.addEvents("checkRow","toggleTableHeaderCheckbox('checkRow',targetFormName.elements.chkBox)",EVT_CLICK);
		}
		if(targetFormName.elements.check){
			evtHandler.addEvents("rangeFrom","validateRangeFrom(this)",EVT_BLUR);
			evtHandler.addEvents("rangeTo","validateRangeTo(this)",EVT_BLUR);
			evtHandler.addEvents("noOfDocs","validateFields(this,-1,'No Of Docs',0,true,true)",EVT_BLUR);
		}
		evtHandler.addEvents("totalNoOfDocs","validateFields(this,-1,'Total No Of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btClose","shiftFocus()",EVT_BLUR);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("btclear","onClickClear()",EVT_CLICK);
		evtHandler.addIDEvents("stockHolderCodeLov","displayStockHolderLov('stockcontrol.defaults.screenloadstockholderlov.do')",EVT_CLICK);
		evtHandler.addEvents("stockHolderCode","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("btConfirm","onClickConfirm(this.form,'CONFIRM')",EVT_CLICK);
		evtHandler.addEvents("checkedRowCount","onClickConfirm(index)",EVT_CLICK);
		evtHandler.addEvents("btBlackList","onClickBlackLIst(this.form,'BLACKLIST')",EVT_CLICK);
		evtHandler.addEvents("checkedRowCount","onClickBlackLIst(index)",EVT_CLICK);
		evtHandler.addEvents("btMissing","onClickMissingStock(this.form,'MISSINGSTOCK')",EVT_CLICK);
		evtHandler.addEvents("checkedRowCount","onClickMissingStock(index)",EVT_CLICK);
		evtHandler.addEvents("checkall",  "okEnableAll()",EVT_CLICK);
		evtHandler.addEvents("checkRow",  "okEnableOne()",EVT_CLICK);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);	
	}

	setFocus();
	//DivSetVisible(true);
	callOnLoad();
	onChangeOfDocTyp();
}



function validateRangeFrom(range){
	if(validateFields(range,-1,'Range From',0,true,true)){
		//calculateNumberOfDocs(range);
	}
}

function validateRangeTo(range){
	if(validateFields(range,-1,'Range To',0,true,true)){
		calculateNumberOfDocs(range);
	}
}

function setFocus(){
	if(!(targetFormName.elements.stockHolderType.disabled)){
		targetFormName.elements.stockHolderType.focus();
	}
	
}

function callOnLoad(){
	if(targetFormName.elements.popUpStatus.value=="M"){
		targetFormName.elements.popUpStatus.value="";
		openPopUp("stockcontrol.defaults.missingstockscreenloadpopup.do?fromScreen=confirmStock",630,400);
	}
}

function doClose(){
	var chke = document.getElementsByName("check");
	for(var i=0; i<chke.length; i++){
		chke[i].checked = false;
	}
	submitFormWithUnsaveCheck("stockcontrol.defaults.closeconfirmstock.do");
}

function shiftFocus(){
	if(!event.shiftKey){
		if(!(targetFormName.elements.stockHolderType.disabled)){
			targetFormName.elements.stockHolderType.focus();
		}
	}
}

function showPartnerAirlines(){	
	if(targetFormName.elements.partnerAirline.checked){
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		targetFormName.elements.airlineName.value = "";
		targetFormName.elements.awbPrefix.value = "";
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){	
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}else{
		targetFormName.elements.airlineName.value = "";
	}
}

function onClickClear(){
	submitForm(targetFormName,"stockcontrol.defaults.clearconfirmstock.do");
}

function displayStockHolderLov(strAction){
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=targetFormName.elements.stockHolderCode.value;
	var typeVal=targetFormName.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added  for setting width and height dynamically starts
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	//Added for setting width and height dynamically ends
	openPopUp(strUrl,600,468);
}

function onClickList(){
	submitForm(targetFormName,"stockcontrol.defaults.listconfirmstock.do?btn=L");
}

function onClickConfirm(indexId){

	var frm = targetFormName;
	var checkValues = document.getElementsByName('checkRow');
	targetFormName.elements.hiddenOpFlag.value = "D";
	var hiddenOpFlag = document.getElementsByName('hiddenOpFlag');
	var count = 0;
	var n=document.getElementsByName("checkRow");
	var r=document.getElementById("confirmStock").rows;
	var checkRow=new Array();
	var flg="n";
	var count=0;
	var ch = new Number(0);
	for(var i=0;i< n.length;i++){
		if(n[i].checked==true){
			var cell=r[i].cells;
			ch = n[i];
			checkRow[count]= i;
			count=count+1;
			flg="y";
		}
	}
	if(flg=="n"){
		showDialog({	
			msg		:	"Select a record.",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
	}

	showDialog({	
		msg		:	'<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.doyouwanttoconfirmthestock" scope="request"/>',
		type	:	4, 
		parentWindow: self,
		parentForm: targetFormName,
		dialogId:'id_1',
		onClose: function () {
					screenConfirmDialog(frm,'id_1',checkRow);
					screenNonConfirmDialog(targetFormName,'id_1');
				}
	});
	
}

function screenConfirmDialog(frm, dialogId,indexId) {
	while(frm.elements.currentDialogId.value == ''){
	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){		
			submitForm(frm,'stockcontrol.defaults.confirmtransitstock.do?checkBox='+indexId);
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

function onClickBlackLIst(indexId){
	var frm = targetFormName;
	var checkValues = document.getElementsByName('checkRow');
	targetFormName.elements.hiddenOpFlag.value = "D";
	var hiddenOpFlag = document.getElementsByName('hiddenOpFlag');
	var count = 0;
	var n=document.getElementsByName("checkRow");
	var r=document.getElementById("confirmStock").rows;
	var confirmStatus=document.getElementsByName('confirm');
	var checkRow;
	var flg="n";
	var count=0;
	var ch = new Number(0);
	for(var i=0;i< n.length;i++){
		if(n[i].checked==true){
			var cell=r[i].cells;
			ch = n[i];
			count=count+1;
			flg="y";
			checkRow = i;
		}
	}
	
	if(count>1){
		showDialog({	
			msg		:	"Please select 1 row.",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
	
	if(count==1){
		var str=new String(confirmStatus[checkRow].innerHTML).trim();
		if(str=='Not Confirmed'){
			showDialog({	
				msg		:	'<common:message bundle="confirmstockresources" key="stockcontrol.defaults.notconfirmedstockscannotblacklist" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
		}else{
		
			showDialog({	
				msg		:	'<common:message bundle="confirmstockresources" key="stockcontrol.defaults.confirmstock.doyouwanttoblacklistthestock" scope="request"/>',
				type	:	4, 
				parentWindow: self,
				parentForm: targetFormName,
				dialogId:'id_1',
				onClose: function () {
							screenConfirmDialogForBlackLising(frm,'id_1',checkRow);
							screenNonConfirmDialogForBlackLising(frm,'id_1');
						}
			});
		}
	}
	
	if(flg=="n"){
		showDialog({	
			msg		:	"Select a record.",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
}

function screenConfirmDialogForBlackLising(frm, dialogId,indexId) {

	while(frm.elements.currentDialogId.value == ''){
	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,"stockcontrol.defaults.blacklisttransitstock.do?checkBox="+indexId+"&btn=B"+"&fromScreen=confirmStock");
			
			if(targetFormName.elements.status.value == 'M' || targetFormName.elements.status.value == 'ALL' ){
				//submitForm(frm,"stockcontrol.defaults.screenloadblackliststock.do?fromScreen=confirmStock");
				submitForm(targetFormName,"stockcontrol.defaults.screenloadblackliststock.do");
				//openPopUp("stockcontrol.defaults.screenloadblackliststock.do?fromScreen=confirmStock",800,200);
			}
		
		}
	}
}

function screenNonConfirmDialogForBlackLising(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){
	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

function onClickMissingStock(indexId){

	var frm = targetFormName;
	var checkValues = document.getElementsByName('checkRow');
	var hiddenOpFlag = document.getElementsByName('hiddenOpFlag');
	var count = 0;
	var n=document.getElementsByName("checkRow");
	var r=document.getElementById("confirmStock").rows;
	var checkRow;
	var flg="n";
	var count=0;
	var ch = new Number(0);
	
	for(var i=0;i< n.length;i++){
		if(n[i].checked==true){
			var cell=r[i].cells;
			ch = n[i];
			count=count+1;
			flg="y";
			checkRow = i;
		}
	}
		
	if(count>1){
		showDialog({	
			msg		:	"Please select 1 row.",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
	
	if(count==1){
		submitForm(frm,"stockcontrol.defaults.selectedmissingtransitstock.do?checkBox="+checkRow+"&btn=M"+"&fromScreen=confirmStock");
		//if(targetFormName.elements.status.value == 'N' || targetFormName.elements.status.value == 'ALL'){
		//openPopUp("stockcontrol.defaults.missingstockscreenloadpopup.do?fromScreen=confirmStock"+"&stockHolderCode="+targetFormName.elements.stockHolderCode.value,630,400);
		//}
	}
	
	if(flg=="n"){
		showDialog({	
			msg		:	"Select a record.",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
	
}

function okEnableAll(){
	var checks = document.getElementsByName ('checkall');
	var count = 0;
	for(var i=0; i<checks.length;i++){
		if(checks[i].checked == true){
			count++;
		}
	}
	if(count >= 1){
		targetFormName.elements.btConfirm.disabled=false;
		targetFormName.elements.btMissing.disabled=false;
		targetFormName.elements.btBlackList.disabled=false;
	}else{
		targetFormName.elements.btConfirm.disabled=true;
		targetFormName.elements.btMissing.disabled=true;
		targetFormName.elements.btBlackList.disabled=true;
	}
}

function okEnableOne(){
	var checks = document.getElementsByName ('checkRow');
	var count = 0;
	for(var i=0; i<checks.length;i++){
		if(checks[i].checked == true){
			count++;
		}
	}
	if(count >= 1){
		//Changed For ICRD-218734 by A-6858
		/*targetFormName.elements.btConfirm.disabled=false;
		targetFormName.elements.btMissing.disabled=false;
		targetFormName.elements.btBlackList.disabled=false;
		*/
		enableField(targetFormName.elements.btConfirm);
		enableField(targetFormName.elements.btMissing);
		enableField(targetFormName.elements.btBlackList);
	}else{
		/*targetFormName.elements.btConfirm.disabled=true;
		targetFormName.elements.btMissing.disabled=true;
		targetFormName.elements.btBlackList.disabled=true;
		*/
		disableField(targetFormName.elements.btConfirm);
		disableField(targetFormName.elements.btMissing);
		disableField(targetFormName.elements.btBlackList);
	}
}

function onChangeOfDocTyp(){
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.partnerAirline.disabled=true;	
		targetFormName.elements.partnerAirline.checked=false;
		showPartnerAirlines();
	} else {
		targetFormName.elements.partnerAirline.disabled=false;		
		showPartnerAirlines();
	}
}