<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[1];
	with(frm){

		evtHandler.addEvents("reqRefNo","validateFields(this,-1,'Request Reference Number',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("status","validateFields(this,-1,'Status',1,true,true)",EVT_BLUR);
		evtHandler.addEvents("code","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("reqStock","validateFields(this,-1,'Requested Stock',3,true,true)",EVT_BLUR);
		//evtHandler.addEvents("remarks","return validateMaxLength(this,250)",EVT_BLUR);
		evtHandler.addEvents("remarks","validateRemarks()",EVT_BLUR);
		evtHandler.addEvents("allocatedStock","validateFields(this,-1,'Allocated Stock',3,true,true)",EVT_BLUR);
		//evtHandler.addEvents("appRejRemarks","return validateMaxLength(this,250)",EVT_BLUR);
		evtHandler.addEvents("appRejRemarks","validateAppRejRemarks()",EVT_BLUR);
		evtHandler.addEvents("btnSave","onClickSave(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","closefn()",EVT_CLICK);
		evtHandler.addEvents("btnClose","shiftFocus()",EVT_BLUR);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("btnList","onClickList(this.form)",EVT_CLICK);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
		
	}
	
	/*
		Enable partner airline combo if checked on screenload
	*/
	showPartnerAirlines();
	
	
	//DivSetVisible(true);
	onScreenLoad();

}

function displayStockHolderLov(strAction){
	var stockHolderCode='code';
	var stockHolderType='stockHolderType';
	var val=document.forms[1].elements.code.value;
	var typeVal=document.forms[1].elements.stockHolderType.value;
	var docType=document.forms[1].elements.docType.value;
	var docSubType=document.forms[1].elements.subType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType+"&documentType="+docType+"&documentSubType="+docSubType;
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var _reqWidth=(clientWidth*45)/100;
	var _reqHeight = (clientHeight*50)/100;
	openPopUp(strUrl,_reqWidth,_reqHeight);
}

function validateRemarks(){
	return validateMaxLength(document.forms[1].elements.remarks,250);
}

function validateAppRejRemarks(){
	return validateMaxLength(document.forms[1].elements.appRejRemarks,250);
}

function onScreenLoad(){
var fromStockRequestList= document.getElementsByName('fromStockRequestList').value;

	if(targetFormName.elements.reqRefNo.value != "" && targetFormName.elements.status.value != "" ){
		
			targetFormName.elements.reqRefNo.disabled = true;
		}
		setFocus();
}
function closefn(){
	document.forms[1].action="stockcontrol.defaults.closestockrequest.do";
	document.forms[1].submit();

}
function shiftFocus(){
	if(!event.shiftKey){
		if(!(document.forms[1].elements.reqRefNo.disabled)){
			document.forms[1].elements.reqRefNo.focus();
		}else{
		
		document.forms[1].elements.dateOfReq.focus();
		}
	}
}

function onClickSave(frm){
	if(frm.elements.docType.value==""){
		//alert('Select Document Type');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.selectdocumenttype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						 frm.elements.docType.focus();
					}
			});
		return;
	}
	if(frm.elements.subType.value==""){
		//alert('Select document sub type.');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.selectdocumenttype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						 frm.elements.subType.focus();
					}
			});
		return;
	}
	if(frm.elements.dateOfReq.value==""){
		//alert('Please enter the Date of Request');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.pleaseenterthedateofrequest" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						frm.elements.dateOfReq.focus();
					}
			});
		return;
	}
	if(frm.elements.stockHolderType.value==""){
		//alert('Please enter Stock holder type.');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.pleaseenterthestockholdertype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						frm.elements.stockHolderType.focus();
					}
			});
		return;
	}
	if(frm.elements.stockHolderType.value=="H"){
		//alert('Headquaters cannot create a stock request');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.headquaterscannotcreateastockrequest" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						frm.elements.stockHolderType.focus();
					}
			});
		return;
	}
	if(frm.elements.code.value==""){
		//alert('Please enter Stock holder code.');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.pleaseenterstockholdercode" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						frm.elements.code.focus();
					}
			});
		return;
	}
	if(frm.elements.reqStock.value==""){
		//alert('Please enter the requested stock.');
		showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.pleaseentertherequestedstock" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,		
				onClose : function () {
						frm.elements.reqStock.focus();
					}
			});
		return;
	}
	/*var stock=document.getElementsByName('stockHolder');
	var checkStock=0;
	if(stock){
		if(stock.length){
			for(var i=0;i<stock.length;i++){
				if(stock[i].value!=""){
					checkStock=1;
				}
			}
		}else{
			if(stock.value!=""){
				checkStock=1;
			}
		}
	}
	if(checkStock==0){
		alert('Enter the stock holder code');
		return;
	}*/

	showDialog({	
				msg		:	'<common:message bundle="maintainstockrequestresources" key="stockcontrol.defaults.maintainstockrequest.confirmsave" scope="request"/>',
				type	:	4, 
				parentWindow:self,
				parentForm:document.forms[1],	
				dialogId:'id_1',
				onClose : function () {
						screenConfirmDialog(document.forms[1],'id_1');
						screenNonConfirmDialog(document.forms[1],'id_1');
					}
			});
	

	/*
	if(confirm('Do you want to save the data?')){
		frm.action = 'stockcontrol.defaults.savestockreq.do';
		frm.submit();
		disablePage();
	}*/
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
		//Added for ICRD-4259 BY A-5117
			var reqRefNo = frm.elements.reqRefNo.value;
			frm.action = "stockcontrol.defaults.savestockreq.do?reqRefNo="+reqRefNo;
			frm.submit();
			disablePage();
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){
			frm.action = 'stockcontrol.defaults.clearstockreq.do';
			frm.submit();
			disablePage();

		}
	}
}

function onClickClear(frm){
	frm.action = 'stockcontrol.defaults.clearstockreq.do';
	frm.submit();
	disablePage();
}

function doActionClose(){
	window.close();
}

function setFocus(){
	if(!(document.forms[1].elements.reqRefNo.disabled)){
		document.forms[1].elements.reqRefNo.focus();
	}else{
		
		document.forms[1].elements.dateOfReq.focus();
	}
}

function showPartnerAirlines(){	
	var partnerPrefix = targetFormName.elements.partnerPrefix.value;
	if(targetFormName.elements.partnerAirline.checked){
		jquery('select[name="awbPrefix"] option[value="'+partnerPrefix+'"]').remove();
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		targetFormName.elements.airlineName.value="";		
		jquery('select[name="awbPrefix"]').append("<option value='" + partnerPrefix + "'> " + partnerPrefix + "</option>");
		jquery('select[name="awbPrefix"]').val(partnerPrefix);			
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){		
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}
}
//Added for ICRD-4259 BY A-5117 starts
function onClickList(frm){
	
	frm.action = 'stockcontrol.defaults.viewstockreq.do?reqRefNo='+targetFormName.elements.reqRefNo.value;
	
	frm.submit();
	
	disablePage();
	
	}

	function confirmMessage(){
		
	}
	function nonconfirmMessage() {
	
	targetFormName.action = 'stockcontrol.defaults.clearstockreq.do';
	targetFormName.submit();
	
	}
//Added for ICRD-4259 BY A-5117 ends	
	
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
	
	