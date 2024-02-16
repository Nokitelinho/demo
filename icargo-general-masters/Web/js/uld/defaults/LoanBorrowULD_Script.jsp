<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var _partyArr = new Hashtable();
var _customerArr = new Hashtable();
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	//frm.elements.transactionType.focus();
	onScreenLoad();
	with(frm){

	//onchange
	evtHandler.addIDEvents("transactionType","txnTypeChanged()",EVT_CHANGE);
	//evtHandler.addEvents("fromPartyCode","fromPartyCodeValidate()",EVT_BLUR);
	//evtHandler.addEvents("toPartyCode","toPartyCodeValidate()",EVT_BLUR);
	evtHandler.addEvents("fromairlinelov","onclickFromLOV()",EVT_CLICK);
	evtHandler.addEvents("toairlinelov","onclickToLOV()",EVT_CLICK);
	evtHandler.addEvents("partyType","comboChanged()",EVT_CHANGE);
	evtHandler.addEvents("transactionType","changed()",EVT_CHANGE);
	evtHandler.addEvents("btnClear","clearTransaction()",EVT_CLICK);
	evtHandler.addEvents("btnSave","saveTransaction()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeTransaction()",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
	//evtHandler.addIDEvents("masterUld","updateHeaderCheckBox(this.form,this,this.form.uldDetails)",EVT_CLICK);
	//evtHandler.addIDEvents("masterAcc","updateHeaderCheckBox(this.form,this,this.form.childAcc)",EVT_CLICK);
	//evtHandler.addIDEvents("uldDetails","toggleTableHeaderCheckbox('uldDetails',frm.elements.masterUld)",EVT_CLICK);
	//evtHandler.addIDEvents("accessoryDetails","toggleTableHeaderCheckbox(this,this.form.masterAcc)",EVT_CLICK);
	evtHandler.addEvents("btnGenerateDamageReport","damageAgreement()",EVT_CLICK);
	evtHandler.addEvents("btnAgreementReport","agreementReport()",EVT_CLICK);
	evtHandler.addEvents("btnSendLUC","sendLUC()",EVT_CLICK);
	evtHandler.addEvents("fromPartyCode","populateFromPartyName()",EVT_BLUR);
	evtHandler.addEvents("toPartyCode","populateToPartyName()",EVT_BLUR);
	evtHandler.addEvents("toShortCode","populateToCustomerDetails()",EVT_BLUR);
	evtHandler.addEvents("fromShortCode","populateFromCustomerDetails()",EVT_BLUR);
	evtHandler.addEvents("dummyLUC","dummyLUC(this.form)",EVT_CLICK);
	evtHandler.addEvents("accessoryQuantity","validateFields(this, -1, 'Accessory Quantity', 3, true, true)",EVT_BLUR);
	if(frm.elements.select!=null){
			evtHandler.addEvents("select","select(this.form)",EVT_CLICK);
		}
		//evtHandler.addEvents("selectAll","selectAll(this.form)",EVT_CLICK);
	//evtHandler.addEvents("uldNumLOV","invokeTempLOV(this,'uldNumLOV')",EVT_CLICK);

	}

}
function resetFocus(frm){
	 if(!event.shiftKey){ 
				if((!frm.elements.transactionNature.disabled)
					&& (frm.elements.pageurl.value == "fromulderrorlogforborrow" || frm.elements.pageurl.value == "fromulderrorlogforloan" 
					|| frm.elements.txnTypeDisable.value == "Y" || frm.elements.pageurl.value == "fromScmReconcileBorrow")){
					frm.elements.transactionNature.focus();
				}
				else{
					if(!frm.elements.transactionType.disabled ){
						frm.elements.transactionType.focus();
					}					
				}				
	}	
}
function sendLUC(){
	//added by a-3045 for CR QF1016 starts
	 if(targetFormName.elements.transactionStation.value == ""){
	      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
	      targetFormName.elements.transactionStation.focus();
	      return;
	  }
	
	  if(targetFormName.elements.transactionDate.value == ""){
	      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
	      targetFormName.elements.transactionDate.focus();
	      return;
	  }
	
	  if(targetFormName.elements.partyType.value == ""){
	      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
	      targetFormName.elements.partyType.focus();
	      return;
	  }
	
	  if(targetFormName.elements.fromPartyCode.value == ""){
	       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
	       targetFormName.elements.fromPartyCode.focus();
	       return;
	  }
	  if(targetFormName.elements.transactionTime.value == ""){
	  	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
	  	 targetFormName.elements.transactionTime.focus();
	  	 return;
	  }
	  if(targetFormName.elements.toPartyCode.value == ""){
		 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
		 targetFormName.elements.toPartyCode.focus();
		 return;
  	}
	var uldNumber = document.getElementsByName("uldNum");	
	var length=uldNumber.length;
	for(var i=0;i<length;i++){
		if(length>1){
			if(targetFormName.elements.uldNum[i].value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}	
		}else{
			if(targetFormName.elements.uldNum.value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}
		}
	}
	frm.elements.fromPopup.value="";
	//added by a-3045 for CR QF1016 ends
	submitAction(frm,'/uld.defaults.generateluc.do');
}
function txnTypeChanged()
{
	var frm=targetFormName;
	frm.elements.toShortCode.value="";
	frm.elements.fromShortCode.value="";
	document.getElementById("fromshortcodeLable").style.visibility="hidden";
	frm.elements.fromShortCode.style.visibility="hidden";
	if(targetFormName.elements.transactionType.value=="L"){
		if(frm.elements.partyType.value=="G"){
		     frm.elements.toShortCode.style.visibility="visible";
		     document.getElementById("shortcodeLable").style.visibility="visible";
		}
	targetFormName.elements.toPartyCode.value="";
	targetFormName.elements.toPartyName.value="";
	targetFormName.elements.fromPartyCode.value=targetFormName.elements.airlineCode.value;
	targetFormName.elements.fromPartyName.value=targetFormName.elements.airlineName.value;
	//targetFormName.elements.partyType.disabled=false;
	}
	if(targetFormName.elements.transactionType.value=="B"){
	  frm.elements.toShortCode.style.visibility="hidden";
	  document.getElementById("shortcodeLable").style.visibility="hidden";
	targetFormName.elements.fromPartyCode.value="";
	targetFormName.elements.fromPartyName.value="";
	targetFormName.elements.toPartyCode.value=targetFormName.elements.airlineCode.value;
	targetFormName.elements.toPartyName.value=targetFormName.elements.airlineName.value;
	targetFormName.elements.partyType.value="A";
	//targetFormName.elements.partyType.disabled=true;
	}
	
	if(targetFormName.elements.transactionType.value=="R"){
		 frm.elements.toShortCode.style.visibility="hidden";
		document.getElementById("shortcodeLable").style.visibility="hidden";
		if(frm.elements.partyType.value=="G"){
		document.getElementById("fromshortcodeLable").style.visibility="visible";
		frm.elements.fromShortCode.style.visibility="visible";
		}
		targetFormName.elements.fromPartyCode.value="";
		targetFormName.elements.fromPartyName.value="";
		targetFormName.elements.toPartyCode.value=targetFormName.elements.airlineCode.value;
		targetFormName.elements.toPartyName.value=targetFormName.elements.airlineName.value;
		targetFormName.elements.partyType.value="A";
	}
	/*if((frm.elements.partyType.value=="O") && (frm.elements.transactionType.value=="L")){
		frm.elements.toairlinelov.style.visibility="hidden";
		frm.elements.fromairlinelov.style.visibility="visible";
	}*/
	/*if(frm.elements.partyType.value=="A" && ((frm.elements.transactionType.value=="L")||(frm.elements.partyType.value=="B"))){
			frm.elements.toairlinelov.style.visibility="visible";
			frm.elements.fromairlinelov.style.visibility="visible";
	}*/
	/*if((frm.elements.partyType.value=="O") && (frm.elements.transactionType.value=="B")){
			frm.elements.fromairlinelov.style.visibility="hidden";
			frm.elements.toairlinelov.style.visibility="visible";
	}*/
	//submitAction(frm,'/uld.defaults.transaction.txnchanged.do');
	//recreateTableDetailsBlur("uld.defaults.transaction.txnchanged.do","div1","div2");

}


function fromPartyCodeValidate(){
var frm=targetFormName;

	if(frm.elements.comboFlag.value=="airlinelov"){
		//validateMaxLength(frm.elements.fromPartyCode,3);

	}else if(frm.elements.comboFlag.value=="agentlov"){
		validateMaxLength(frm.elements.fromPartyCode,12);

	}else if(frm.elements.comboFlag.value=="others"){
		validateMaxLength(frm.elements.fromPartyCode,12);

	}else if(frm.elements.comboFlag.value==""){
		validateMaxLength(frm.elements.fromPartyCode,3);

	}

}

function toPartyCodeValidate(){
var frm=targetFormName;

	if(frm.elements.comboFlag.value=="airlinelov"){

		//validateMaxLength(frm.elements.toPartyCode,3);
	}else if(frm.elements.comboFlag.value=="agentlov"){

		validateMaxLength(frm.elements.toPartyCode,12);
	}else if(frm.elements.comboFlag.value=="others"){

		validateMaxLength(frm.elements.toPartyCode,12);
	}else if(frm.elements.comboFlag.value==""){

		validateMaxLength(frm.elements.toPartyCode,3);
	}

}

function comboChanged(){

	frm=targetFormName;
	frm.elements.toShortCode.value="";   
	frm.elements.fromShortCode.value="";
	document.getElementById("fromshortcodeLable").style.visibility="hidden";
	frm.elements.fromShortCode.style.visibility="hidden";
	if(frm.elements.partyType.value=="G"){
		//frm.elements.btnSendLUC.disabled = true;
		disableField(frm.elements.btnSendLUC); 
		frm.elements.comboFlag.value="agentlov";
	}else if(frm.elements.partyType.value=="O"){
	//frm.elements.btnSendLUC.disabled = false;
	enableField(frm.elements.btnSendLUC);
		frm.elements.comboFlag.value="others";
	}else if(frm.elements.partyType.value=="A"){
	//frm.elements.btnSendLUC.disabled = false;
	enableField(frm.elements.btnSendLUC);
		frm.elements.comboFlag.value="airlinelov";
	}
	else if(frm.elements.partyType.value=="C"){
	//frm.elements.btnSendLUC.disabled = false;
	enableField(frm.elements.btnSendLUC);
		frm.elements.comboFlag.value="customerlov";
	}
	else if(frm.elements.partyType.value==""){
	//frm.elements.btnSendLUC.disabled = false;
	enableField(frm.elements.btnSendLUC);
		frm.elements.comboFlag.value="airlinelov";
	}

	if((frm.elements.partyType.value=="O") && (frm.elements.transactionType.value=="L")){
	jQuery("[name='toairlinelov']").css('visibility','hidden'); //frm.elements.toairlinelov.style.visibility="hidden"; 
	jQuery("[name='fromairlinelov']").css('visibility','visible');//frm.elements.fromairlinelov.style.visibility="visible";
	jQuery("[name='toShortCode']").css('visibility','hidden')//frm.elements.toShortCode.style.visibility="hidden";
	jQuery("[id='shortcodeLable']").css('visibility','hidden');//document.getElementById("shortcodeLable").style.visibility="hidden";
	}
	if(frm.elements.partyType.value=="A" && ((frm.elements.transactionType.value=="L")||(frm.elements.transactionType.value=="B"))){
		jQuery("[name='toairlinelov']").css('visibility','visible');//frm.elements.toairlinelov.style.visibility="visible";
		jQuery("[name='fromairlinelov']").css('visibility','visible');//frm.elements.fromairlinelov.style.visibility="visible";
		jQuery("[name='toShortCode']").css('visibility','hidden');//frm.elements.toShortCode.style.visibility="hidden";
		jQuery("[id='shortcodeLable']").css('visibility','hidden');//document.getElementById("shortcodeLable").style.visibility="hidden";
	}

	if(frm.elements.partyType.value=="G" && ((frm.elements.transactionType.value=="L")||(frm.elements.transactionType.value=="B"))){
		jQuery("[name='toairlinelov']").css('visibility','visible');//frm.elements.toairlinelov.style.visibility="visible";
		jQuery("[name='fromairlinelov']").css('visibility','visible');//frm.elements.fromairlinelov.style.visibility="visible";
		if(frm.elements.transactionType.value=="L"){
		   jQuery("[name='toShortCode']").css('visibility','visible');//frm.elements.toShortCode.style.visibility="visible";
		   jQuery("[id='shortcodeLable']").css('visibility','visible');//document.getElementById("shortcodeLable").style.visibility="visible";
		}
	}
	if(frm.elements.partyType.value=="C" && ((frm.elements.transactionType.value=="L")||(frm.elements.transactionType.value=="B"))){
		jQuery("[name='toairlinelov']").css('visibility','visible');//frm.elements.toairlinelov.style.visibility="visible";
		jQuery("[name='fromairlinelov']").css('visibility','visible');//frm.elements.fromairlinelov.style.visibility="visible";
		if(frm.elements.transactionType.value=="L"){
		   jQuery("[name='toShortCode']").css('visibility','hidden');//frm.elements.toShortCode.style.visibility="visible";
		   jQuery("[id='shortcodeLable']").css('visibility','hidden');//document.getElementById("shortcodeLable").style.visibility="visible";
		}
	}
	if((frm.elements.partyType.value=="O") && (frm.elements.transactionType.value=="B")){
			jQuery("[name='fromairlinelov']").css('visibility','hidden');//frm.elements.fromairlinelov.style.visibility="hidden";
			jQuery("[name='toairlinelov']").css('visibility','visible');//frm.elements.toairlinelov.style.visibility="visible";
			jQuery("[name='toShortCode']").css('visibility','hidden');//frm.elements.toShortCode.style.visibility="hidden";
			jQuery("[id='shortcodeLable']").css('visibility','hidden');//document.getElementById("shortcodeLable").style.visibility="hidden";
	}
	if(frm.elements.partyType.value=="G" && frm.elements.transactionType.value=="R"){
	        jQuery("[id='fromshortcodeLable']").css('visibility','visible');//document.getElementById("fromshortcodeLable").style.visibility="visible";
		jQuery("[name='fromShortCode']").css('visibility','visible');//frm.elements.fromShortCode.style.visibility="visible";
				
	}
	if(frm.elements.transactionType.value=="L"){
		frm.elements.toPartyCode.value="";
		frm.elements.toPartyName.value="";
		frm.elements.toShortCode.value="";
	}else{
		frm.elements.fromPartyCode.value="";
		frm.elements.fromPartyName.value="";
		frm.elements.fromShortCode.value="";
	}

}
function onclickFromLOV(){
	frm=targetFormName;
    if(frm.elements.transactionType.value=="B"){
	if(frm.elements.comboFlag.value=="agentlov"){
		//frm.elements.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		showFromLOV();
		frm.elements.fromPartyCode.focus();
	}else if(frm.elements.comboFlag.value=="agentlov"){	
		//frm.elements.fromairlinelov.disabled = true;
		disableField(frm.elements.fromairlinelov);
		frm.elements.fromPartyCode.focus();
	}else if(frm.elements.comboFlag.value=="airlinelov"){
		//frm.elements.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	}
	else if(frm.elements.comboFlag.value=="customerlov"){
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.customerlov);
		displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	}
	else if(frm.elements.comboFlag.value==""){
		//frm.elements.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	}}
  else if(frm.elements.transactionType.value=="R" && frm.elements.partyType.value=="G"){
  	//frm.elements.fromairlinelov.disabled = false;
	enableField(frm.elements.fromairlinelov);
	showFromLOV();
	frm.elements.fromPartyCode.focus();
  }
	else{
		//frm.elements.fromairlinelov.disabled = false;
		enableField(frm.elements.fromairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.fromPartyCode.value,'fromPartyCode','1','fromPartyCode','fromPartyName',0)
	}
}

function onclickToLOV(){
	frm=targetFormName;
	if(frm.elements.transactionType.value=="L"){
	if(frm.elements.comboFlag.value=="agentlov"){
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.toairlinelov);
		showToLOV();
		frm.elements.toPartyCode.focus();
	}else if(frm.elements.comboFlag.value=="agentlov"){
		//frm.elements.toairlinelov.disabled = true;
		disableField(frm.elements.toairlinelov);
		frm.elements.toPartyCode.focus();
	}else if(frm.elements.comboFlag.value=="airlinelov"){
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.toairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','toPartyName',0)
	}
	else if(frm.elements.comboFlag.value=="customerlov"){
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.customerlov);
		displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','toPartyName',0)
	}
	else if(frm.elements.comboFlag.value==""){
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.toairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','toPartyName',0)
	}}else
	{
		//frm.elements.toairlinelov.disabled = false;
		enableField(frm.elements.toairlinelov);
		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.toPartyCode.value,'toPartyCode','1','toPartyCode','toPartyName',0)
	}
}

function showFromLOV(){
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=fromPartyCode&formNumber=1&textfiledDesc=fromPartyName&stationCode="+frm.elements.stationCode.value;
	var myWindow = openPopUp(StrUrl, "500","400")

}
function showToLOV(){
	frm=targetFormName;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=toPartyCode&formNumber=1&textfiledDesc=toPartyName&stationCode="+frm.elements.stationCode.value;
	var myWindow = openPopUp(StrUrl, "500","400")

}



function changed(){
frm=targetFormName;
if(frm.elements.transactionType.value=="B"){	
	//frm.elements.btnGenerateDamageReport.disabled = true;
	disableField(frm.elements.btnGenerateDamageReport);
	if(frm.elements.disableStatus.value!="GHA"){
	//frm.elements.toPartyCode.disabled = true;
	//frm.elements.toairlinelov.disabled = true;

	//frm.elements.fromPartyCode.disabled = false;
	enableField(frm.elements.fromPartyCode);
	//frm.elements.fromairlinelov.disabled = false;
	enableField(frm.elements.fromairlinelov);
	}
}
else
{
 //	frm.elements.btnGenerateDamageReport.disabled = false;
    enableField(frm.elements.btnGenerateDamageReport);
	if(frm.elements.disableStatus.value!="GHA"){
	//frm.elements.toPartyCode.disabled = false;
	enableField(frm.elements.toPartyCode);
	//frm.elements.toairlinelov.disabled = false;
    enableField(frm.elements.toairlinelov);
	//frm.elements.fromPartyCode.disabled = false;
	enableField(frm.elements.fromPartyCode);
	//frm.elements.fromairlinelov.disabled = false;
	enableField(frm.elements.fromairlinelov);
	}
}

comboChanged();
}



function onScreenLoad(){

if(browserType=="firefox" || browserType=="chrome"){	
var width = document.body.clientWidth;
var clientHeight = document.body.clientHeight;
var height = (clientHeight*90)/100;
//document.getElementById('div8').style.height = ((height*80)/100)+'px';
//document.getElementById('pageDiv').style.width = ((width*120)/100)+'px';
}
//targetFormName.elements.screenInfo.value="";
//targetFormName.elements.warningMsgStatus.value="";
//targetFormName.elements.isInvalidUldsPresent.value="";
//alert(targetFormName.elements.isInvalidUldsPresent.value);
  if(targetFormName.elements.screenInfo.value== null ){
		targetFormName.elements.screenInfo.value="formloanborrow";
}
if(targetFormName.elements.warningMsgStatus.value==null){
	targetFormName.elements.warningMsgStatus.value="Y";
}
if(targetFormName.elements.isInvalidUldsPresent==null){
		targetFormName.elements.isInvalidUldsPresent.value="N";
}
if(targetFormName.elements.partyType.value=="G"){		
		//targetFormName.elements.btnSendLUC.disabled = true;
	if(targetFormName.elements.saveStatus.value == "savedandcontinue" || 
			targetFormName.elements.saveStatus.value == "savedandprintucr") {
		enableField(targetFormName.elements.btnSendLUC);
	} else {
		disableField(targetFormName.elements.btnSendLUC);
	}
			}

	frm=targetFormName;
	//commented by a-3278 for bug 19987 on 05Oct08
	//frm.elements.transactionDate.readOnly = true;
	//frm.elements.transactionTime.readOnly = true;
	//commenting ends
	//added by nisha on 29JAN08 starts
	document.getElementById("fromshortcodeLable").style.visibility="hidden";
	frm.elements.fromShortCode.style.visibility="hidden";	
	//frm.elements.partyType.value="G";
	if(frm.elements.partyType.value=="G"){
		frm.elements.comboFlag.value="agentlov";
		frm.elements.toShortCode.style.visibility="visible";
		document.getElementById("shortcodeLable").style.visibility="visible";
	}else if(frm.elements.partyType.value=="O"){
		frm.elements.comboFlag.value="others";
	}else if(frm.elements.partyType.value=="A"){
		frm.elements.comboFlag.value="airlinelov";
		frm.elements.comboFlag.defaultValue="airlinelov";
		frm.elements.toShortCode.style.visibility="hidden";
		document.getElementById("shortcodeLable").style.visibility="hidden";
	}else if(frm.elements.partyType.value==""){
		frm.elements.comboFlag.value="airlinelov";
	}
	//added by nisha on 29JAN08 ends
	/**commented as part of ICRD-188218
	if(frm.elements.msgFlag.value=="TRUE"){alert(frm.elements.fromPopup.value);
		openPopUp("msgbroker.message.resendmessages.do?openPopUpFlg=ULD",800,425);
		frm.elements.msgFlag.value="FALSE";
	}*/	
	//Added by A-6841 as part of ICRD-188218
	if(frm.elements.msgFlag.value=='TRUE'){
	    //Modified by A-7359 for ICRD-285546 starts here
	    var txnStation= frm.elements.transactionStation.value;
		openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=LUC&targetAction=uld.defaults.generateluc.do?transactionStation="+txnStation,1050,320);
		//Modified by A-7359 for ICRD-285546 ends here
		frm.elements.msgFlag.value="FALSE";
		frm.elements.fromPopup.value="Y";

	}
	if(frm.elements.saveStatus.value=="borrowsaved"){
	frm.elements.saveStatus.value="";
	frm.elements.printUCR.value="";
	showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.borrowtxnsaved" />',type:2,parentWindow:self,parentForm:frm,dialogId:'id_29'});
	submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
	}
	if(frm.elements.lucPopup.value=="open" )
	{
		frm.elements.lucPopup.value="";
		frm.elements.txnTypeDisable.value = "";
		frm.elements.saveStatus.value="cleared";
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.loantxnsaved.lucgenerated" />',type:2,parentWindow:self,parentForm:frm,dialogId:'id_29'});
		submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
	}
	if(frm.elements.lucPopup.value=="open-error" )
	{
		frm.elements.lucPopup.value="";
		frm.elements.txnTypeDisable.value = "";
		frm.elements.saveStatus.value="cleared";
		//showDialog("Loan Transaction is Saved.LUC Not Generated", 1, self, frm, 'id_29');
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.configurationnotdefinedfor.lucmessage" />',type:1,parentWindow:self,parentForm:frm,dialogId:'id_29'});
		submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
	}
	if(frm.elements.saveStatus.value=="saved" && frm.elements.forDamage.value != "Y" && frm.elements.transactionType.value=="L")
	{
		//changed by nisha for CR-15
		frm.elements.saveStatus.value="";
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.loantransactionsaved" />',type:2,parentWindow:self,parentForm:frm,dialogId:'id_29'});
		submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
		//frm.elements.saveStatus.value="loanlucdone";
		//submitAction(frm,'/uld.defaults.generateluc.do');
	}
	//Added as part of bug ICRD-2490_Yasheen_08Jun11
	if(frm.elements.saveStatus.value=="savedandcontinue" )
	{
		confirmMessage();
	}
	if(frm.elements.saveStatus.value=="savedandprintucr" && frm.elements.forDamage.value != "Y" && frm.elements.transactionType.value=="L")
	{
		//Added By T-1927 for ICRD-42615 start
		frm.elements.saveStatus.value="";
		savedAndPrintUCR();
	}
	if(frm.elements.saveStatus.value=="saved" && frm.elements.forDamage.value != "Y" && frm.elements.transactionType.value=="R"){
				frm.elements.saveStatus.value="";
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.returntransactionsaved" />',type:2,parentWindow:self,parentForm:frm,dialogId:'id_29'});
				submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
	}
	if(frm.elements.saveStatus.value=="accessoriessaved" ){
		frm.elements.lucPopup.value="";
		frm.elements.txnTypeDisable.value = "";
		frm.elements.saveStatus.value="cleared";
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.transactionsaved" />',type:2,parentWindow:self,parentForm:frm,dialogId:'id_29'});
		submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
	}
	if(frm.elements.disableStatus.value=="airline"){
		/*document.getElementById("shortcodeLable").style.visibility="hidden";
		frm.elements.toShortCode.style.visibility="hidden";*/
		/*if(frm.elements.transactionType.value=="L"){
			frm.elements.fromPartyCode.disabled = true;
			frm.elements.fromairlinelov.disabled = true;
		}
		if(frm.elements.transactionType.value=="B"){
			frm.elements.toPartyCode.disabled = true;
			frm.elements.toairlinelov.disabled = true;
		}*/
	}
	if(frm.elements.disableStatus.value=="GHA"){
		//frm.elements.transactionStation.disabled = true;
		disableField(frm.elements.transactionStation);
		//frm.elements.transactionStationImg.disabled = true;
		disableField(frm.elements.transactionStationImg);

	}
	if(frm.elements.pageurl.value=="fromulderrorlogforborrow"
	&&  frm.elements.saveStatus.value=="")
	{
	    //Modified by A-7359 for ICRD-287189
		frm.elements.transactionType.value="L";
		//frm.elements.transactionType.disabled = true;
		disableField(frm.elements.transactionType);
	}
	if(frm.elements.pageurl.value=="fromulderrorlogforloan"
	&&  frm.elements.saveStatus.value=="")
	{
		frm.elements.transactionType.value="L";
		//frm.elements.transactionType.disabled = true;
		disableField(frm.elements.transactionType);
	}
	if(frm.elements.transactionType.value=="B"){
		//frm.elements.btnGenerateDamageReport.disabled = true;
		disableField(frm.elements.btnGenerateDamageReport);
	}
	if(frm.elements.txnTypeDisable.value == "Y"){
		//frm.elements.transactionType.disabled = true;
		disableField(frm.elements.transactionType);
	}
	if(frm.elements.agreementFlag.value == "Y"){
		frm.elements.agreementFlag.value == "";
		var parent = "LoanBorrow";
		submitForm(frm,'uld.defaults.loanborrowagreementdetails.do?closeStatus='+parent);
	}
	var chkboxacc = document.getElementsByName("accessoryDetails");
	var chkboxuld = document.getElementsByName("uldDetails");
	/*if(chkboxacc.length>0 || chkboxuld.length>0){
		frm.elements.partyType.disabled = true;
		frm.elements.fromPartyCode.disabled = true;
		frm.elements.toPartyCode.disabled = true;
		frm.elements.fromPartyName.disabled = true;
		frm.elements.toPartyName.disabled = true;
		frm.elements.fromairlinelov.disabled = true;
		frm.elements.toairlinelov.disabled = true;
		frm.elements.transactionTime.disabled = true;
	}*/
	if(frm.elements.pageurl.value=="fromScmReconcileBorrow"
				&&  frm.elements.saveStatus.value=="")
			{
				frm.elements.transactionType.value="B";
				//frm.elements.transactionType.disabled = true;
				disableField(frm.elements.transactionType);
	}

	if(frm.elements.showDamage.value == "Y"){

		frm.elements.showDamage.value == "";
		var listdetail = "MidLoanBorrow";
		var uldno = frm.elements.uldNumbersSelected.value;
		 submitForm(frm,'uld.defaults.ux.showdamage.do?uldNumbersSelected='+uldno+'&pageURL='+listdetail); //Modified by A-7924 as part of ICRD-304117
	}

	if((!frm.elements.transactionNature.disabled)
					&& (frm.elements.pageurl.value == "fromulderrorlogforborrow" || frm.elements.pageurl.value == "fromulderrorlogforloan" 
					|| frm.elements.txnTypeDisable.value == "Y" || frm.elements.pageurl.value == "fromScmReconcileBorrow")){
					frm.elements.transactionNature.focus();
	}
	targetFormName.elements.transactionRemarks.focus();

}
//Added By T-1927 for ICRD-42615 start
function savedAndPrintUCR(){
  frm = targetFormName;  
			//targetFormName.elements.printUCR.value="N";
			 if(frm.elements.transactionType.value=="L" && targetFormName.elements.pageurl.value==""){
				var chkboxuld = document.getElementsByName("uldDetails");
				var chkcountuld = chkboxuld.length;
				if(chkcountuld != 0 ){
					showDialog({msg:"Do you want to generate UCR Report  ? ",type:4,
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_32',
			onClose:function(result){
			screenConfirmDialog(targetFormName,'id_32');
			screenNonConfirmDialog(targetFormName,'id_32');
			}});
				}
			}
}
/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

/**
*function to know whether its loan or borrow transaction
*and screenload the corresponding popup
*/
function addUldDetails(){


 	frm=targetFormName;

  if(frm.elements.transactionStation.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
      frm.elements.transactionStation.focus();
      return;
  }

  if(frm.elements.transactionDate.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
      frm.elements.transactionDate.focus();
      return;
  }

  if(frm.elements.partyType.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
      frm.elements.partyType.focus();
      return;
  }

  if(frm.elements.fromPartyCode.value == ""){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
       frm.elements.fromPartyCode.focus();
       return;
  }
  if(frm.elements.transactionTime.value == ""){
	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
	 frm.elements.transactionTime.focus();
	 return;
  }
  if(frm.elements.toPartyCode.value == ""){
         showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
         frm.elements.toPartyCode.focus();
         return;
  }

   if(frm.elements.partyType.value == "O"){
         if(frm.elements.fromPartyName.value == ""){
             showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
             frm.elements.fromPartyName.focus();
             return;
         }
         if(frm.elements.toPartyName.value == ""){
			  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
			  frm.elements.toPartyName.focus();
			  return;
         }
   }

  recreateTableDetailsBlur("uld.defaults.transaction.updateloanborrowuld.do","div1","div2","adduld");



}

function adduld(_tableInfo){

  	 	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

  	 	 targetFormName.elements.txnTypeBasis.value = _innerFrm.transactionType.value;
		  var txnType = targetFormName.elements.txnTypeBasis.value;
		  var fromPartyCode=_innerFrm.fromPartyCode.value;
		  var toPartyCode=_innerFrm.toPartyCode.value;
		  var partyType=_innerFrm.partyType.value;
		  var pageurl=_innerFrm.pageurl.value;
		  var transactionType=_innerFrm.transactionType.value;


		    if(txnType == "L"){

		       var strAction="uld.defaults.transaction.screenloadaddloandetails.do?fromPartyCode="+fromPartyCode+"&toPartyCode="+toPartyCode+"&partyType="+partyType+"&pageurl="+pageurl+"&transactionType="+transactionType;
		       frm.elements.ispopup.value = "true";
		       // if(_asyncErrorsExist) return;
		       openPopUp(strAction,560,150);
		        updateTableCodeBlur(_tableInfo);


		    }
		    else if(txnType == "B"){

		       var strAction="uld.defaults.transaction.screenloadaddborrowdetails.do?fromPartyCode="+fromPartyCode+"&toPartyCode="+toPartyCode+"&partyType="+partyType+"&pageurl="+pageurl+"&transactionType="+transactionType;
		       frm.elements.ispopup.value = "true";
		      //  if(_asyncErrorsExist) return;
		       openPopUp(strAction,560,150);
		        updateTableCodeBlur(_tableInfo);


		    }
		    else{
		      //alert("Select a Transaction Type");
   			}



  }

/**
*function to add accesories - screenload popup
*/
function addAccDetails(){
 frm=targetFormName;

  if(frm.elements.transactionStation.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
      frm.elements.transactionStation.focus();
      return;
  }

  if(frm.elements.transactionDate.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
      frm.elements.transactionDate.focus();
      return;
  }

  if(frm.elements.partyType.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
      frm.elements.partyType.focus();
      return;
  }

  if(frm.elements.fromPartyCode.value == ""){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
       frm.elements.fromPartyCode.focus();
       return;
  }
  if(frm.elements.transactionTime.value == ""){
  	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
  	 frm.elements.transactionTime.focus();
  	 return;
  }
  if(frm.elements.toPartyCode.value == ""){
	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
	 frm.elements.toPartyCode.focus();
	 return;
  }

   if(frm.elements.partyType.value == "O"){
         if(frm.elements.fromPartyName.value == ""){
             showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
             frm.elements.fromPartyName.focus();
             return;
         }
         if(frm.elements.toPartyName.value == ""){
			  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
			  frm.elements.toPartyName.focus();
			  return;
         }
   }


recreateTableDetailsBlur("uld.defaults.transaction.updateloanborrowuld.do","div1","div2","addacc");


}

function addacc(_tableInfo){

  		 _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

  	 	 targetFormName.elements.txnTypeBasis.value = _innerFrm.transactionType.value;
		  var txnType = targetFormName.elements.txnTypeBasis.value;
		  var fromPartyCode=_innerFrm.fromPartyCode.value;
		  var toPartyCode=_innerFrm.toPartyCode.value;
		  var partyType=_innerFrm.partyType.value;
		  var pageurl=_innerFrm.pageurl.value;
		  var transactionType=_innerfrm.transactionType.value;

		       var strAction="uld.defaults.transaction.screenloadaddaccessorydetails.do";
		       frm.elements.ispopup.value = "true";
		      //  if(_asyncErrorsExist) return;
		       openPopUp(strAction,530,150);
		        updateTableCodeBlur(_tableInfo);



  }

/**
*function to delete selected ulds
*/
var uldDetailstodel="";
function deleteUldDetails(){
 frm=targetFormName;

 // to get max number of records in uld table
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length+1;

 if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){
      for(var i=0; i<frm.elements.elements.length; i++) {
  	if(frm.elements.elements[i].type == "checkbox") {
  	   if(frm.elements.elements[i].name=="uldDetails") {
  	     if(frm.elements.elements[i].checked) {
  	        uldDetailstodel = frm.elements.elements[i].value;
  	     }
 	   }
         }
      }
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.warn.delete" />', type:4,
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_3',
			onClose:function(result){
			screenConfirmDialog(targetFormName,'id_3');
			screenNonConfirmDialog(targetFormName,'id_3');
			}});
   }
}


/**
*function to delete selected accessories
*/
function deleteAccDetails(){
 frm=targetFormName;

   // to get max number of records in uld table
    var chkboxacc = document.getElementsByName("accessoryDetails");
    var chkcountacc = chkboxacc.length+1;

       if(validateSelectedCheckBoxes(frm,'accessoryDetails',chkcountacc,'1')){
           for(var i=0; i<frm.elements.elements.length; i++) {
 	     if(frm.elements.elements[i].type == "checkbox") {
 	       if(frm.elements.elements[i].name=="accessoryDetails") {
 	          if(frm.elements.elements[i].checked) {
 	           accessoryDetails = frm.elements.elements[i].value;

 	        }
 	      }
 	    }
 	  }
 	  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.warn.delete" />', type:4,
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_2',
			onClose:function(result){
			screenConfirmDialog(targetFormName,'id_2');
			screenNonConfirmDialog(targetFormName,'id_2');
			}});
     }
 }

/**
*function to save Transaction
*/
function saveTransaction(){
 frm=targetFormName;
//Commented for bug 109604 by A-3767 on 07Apr11
/*if(targetFormName.elements.loaded.checked){
	targetFormName.elements.loaded.value = "Y";
}else{
	targetFormName.elements.loaded.value = "N";
}*/

  if(frm.elements.transactionStation.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
      frm.elements.transactionStation.focus();
      return;
  }

  if(frm.elements.transactionDate.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
      frm.elements.transactionDate.focus();
      return;
  }

  if(frm.elements.partyType.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
      frm.elements.partyType.focus();
      return;
  }

  if(frm.elements.fromPartyCode.value == ""){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
       frm.elements.fromPartyCode.focus();
       return;
  }
  if(frm.elements.transactionTime.value == ""){
  	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
  	 frm.elements.transactionTime.focus();
  	 return;
  }
  if(frm.elements.toPartyCode.value == ""){
	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
	 frm.elements.toPartyCode.focus();
	 return;
  }

   if(frm.elements.partyType.value == "O"){
         if(frm.elements.fromPartyName.value == ""){
             showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
             frm.elements.fromPartyName.focus();
             return;
         }
         if(frm.elements.toPartyName.value == ""){
			  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
			  frm.elements.toPartyName.focus();
			  return;
         }
   }
	if(frm.elements.fromPartyCode.value.toUpperCase() == frm.elements.toPartyCode.value.toUpperCase()){
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.sameParty" />',type:1,parentWindow:self});
		 return;
	}
	var uldNumber = document.getElementsByName("uldNum");	
	var length=uldNumber.length;
	var invalidUldNumbers="";
	var invalidUldSeparatorCheck="False";
	var invalidUld="false";
	var selectedRows = "";
	for(var i=0;i<length;i++){
		if(length>1){
			if(targetFormName.elements.uldNum[i].value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}	
		}else{
			if(targetFormName.elements.uldNum.value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}
		}
	}
	//added by a-3045 for bug ULD558 starts
	if(accessoryQtyMandatory()){
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.accessorycodemandatory" />',type:1,parentWindow:self});
		return;
	}
	if(duplicateAccessoryCodeValidation()){
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.accessorycodeexists" />',type:1,parentWindow:self});
		return;
	}
	if(accessoryQtyValidation()){
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidaccessoryquantity" />',type:1,parentWindow:self});
		return;
	}
	//added by a-3045 for bug ULD558 ends

   var chkboxuld = document.getElementsByName("uldDetails");
   var chkcountuld = chkboxuld.length;
   //var chkboxacc = document.getElementsByName("masterAcc");
   var chkboxaccchild = document.getElementsByName("childAcc");


   var chkboxacc = document.getElementsByName("accOperationFlag");
		var chkcountacc = chkboxacc.length;
		var count=0;
		for(var i=0;i<chkcountacc;i++){
			if(chkboxacc[i].value=="I" || chkboxacc[i].value=="U"){
				count++;
			}
		}
   if(chkcountuld == 0 && count==0){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.uldoraccmandatory" />',type:1,parentWindow:self});
       return;
   }
   targetFormName.elements.screenInfo.value="formloanborrow";
   targetFormName.elements.isInvalidUldsPresent.value="N";
   targetFormName.elements.warningMsgStatus.value="Y";
   //alert("save "+frm.elements.forDamage.value);
   
   	//submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do');


//alert(targetFormName.elements.isInvalidUldsPresent);
  submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do');
  //screenConfirmDialog(frm,'id_1');
 // screenNonConfirmDialog(frm,'id_1');

}

/**
*function to close Transaction
*/
function closeTransaction(){

    frm=targetFormName;

    if(frm.elements.closeStatus.value == "ListULD") {
       	submitAction(frm,'/uld.defaults.closeloanborrowdetails.do');
    }else if(frm.elements.closeStatus.value == "MUCTracking") {
       	submitAction(frm,'/uld.defaults.closeloanborrowdetails.do');
    }
    else if(frm.elements.closeStatus.value == "LoanBorrowEnquiry") {
       submitAction(frm,'/uld.defaults.transaction.refreshulddetailstxn.do?listStatus=N');
    }
    else if(frm.elements.pageurl.value == "fromulderrorlogforborrow" ||
            frm.elements.pageurl.value == "fromulderrorlogforloan" ||
            frm.elements.pageurl.value == "fromScmReconcileBorrow" ||
            frm.elements.pageurl.value == "fromScmUldReconcile") {
           submitAction(frm,'/uld.defaults.closeloanborrowdetails.do');
    }
    else {
       	submitFormWithUnsaveCheck('uld.defaults.closeaction.do');

    }
}

/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			if(frm.elements.transactionType.value=="L" && targetFormName.elements.pageurl.value==""){
				var chkboxuld = document.getElementsByName("uldDetails");
				var chkcountuld = chkboxuld.length;
				if(chkcountuld != 0 ){
					showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.confirmation.generate.ucr.report" />', type:4,
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_31',
			onClose:function(result){
			screenConfirmDialog(targetFormName,'id_31');
			screenNonConfirmDialog(targetFormName,'id_31');
			}});
				}else{
					submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do');
				}
			}
			else{
			submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do');
			}
		}
		if(dialogId == 'id_2') {
			//submitAction(frm,'/uld.defaults.transaction.deleteaccessorydetails.do');
			recreateTableDetailsBlur("uld.defaults.transaction.deleteaccessorydetails.do","div1","div2");
			//	alert("MEOW");
		}
		if(dialogId == 'id_3') {
				//recreateTableDetails("uld.defaults.transaction.deleteulddetails.do","div1");
				recreateTableDetailsBlur("uld.defaults.transaction.deleteulddetails.do","div1","div2");
				//alert("MEOW");
		}
		if(dialogId == 'id_4') {
			targetFormName.elements.lucEnable.value="Y";
		}
		if(dialogId == 'id_31'){
		targetFormName.elements.printUCR.value="Y";
		targetFormName.elements.warningMsgStatus.value="N";
		//Modified as part of bug ICRD-2490 by A-3767 on 08Jun11
		//generateReport(frm,"/uld.defaults.transaction.saveuldtransaction.do",true);
		submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do');
		}
		//Added By T-1927 for ICRD-42615 start
		if(dialogId == 'id_32'){
			frm.elements.saveStatus.value="";
			generateReport(frm,"/uld.defaults.transaction.printloanucr.do");
		}
	}
}


/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}
		if(dialogId == 'id_3'){

		}

		if(dialogId == 'id_4'){
			targetFormName.elements.lucEnable.value="N";
		}
		if(dialogId == 'id_31'){
			targetFormName.elements.printUCR.value="N";
			targetFormName.elements.warningMsgStatus.value="N";
			submitForm(frm,"uld.defaults.transaction.saveuldtransaction.do");
		}
		//Added By T-1927 for ICRD-42615 start
		if(dialogId == 'id_32'){
			targetFormName.elements.printUCR.value="Y";
			targetFormName.elements.warningMsgStatus.value="N";
			frm.elements.saveStatus.value="";
			submitAction(frm,'/uld.defaults.transaction.screenloadloanborrowuld.do');
		}

	}
}

/**
*Method for excecuting after confirmation
*/
function confirmMessage(){
  frm = targetFormName;

 if(frm.elements.isInvalidUldsPresent.value=="OPEN_TXN_WARNING"){
 	frm.elements.isInvalidUldsPresent.value="IGNORE_OPEN_TXN";
 	submitAction(frm,'/uld.defaults.transaction.saveuldtransaction.do'); 
 }else{
					//showDialog('<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.confirmation.generate.ucr.report" />', 4, self, frm, 'id_31');
  var listdetail = "LoanBorrow";
  if(frm.elements.pageurl.value=="fromulderrorlogforborrow")
     {
  	listdetail = "fromulderrorlogforborrowDamage";
     }
   if(frm.elements.pageurl.value=="fromulderrorlogforloan"){
	 listdetail = "fromulderrorlogforloanDamage";
	   }

	if(frm.elements.pageurl.value=="fromScmUldReconcile"){
	 listdetail = "fromScmUldReconcileDamage";
	}
	if(frm.elements.pageurl.value=="fromScmReconcileBorrow"){
			 listdetail = "fromScmReconcileBorrowDamage";
	}
  var uldno = frm.elements.damageULD.value;

  if(frm.elements.forDamage.value == "Y"){

      submitForm(frm,'uld.defaults.showdamage.do?uldNumbersSelected='+uldno+'&pageURL='+listdetail);
  }else{
      submitForm(frm,'uld.defaults.transaction.dummymovementtxn.do?pageurl='+listdetail);
  }
 }

}

/**
*Method for excecuting after nonconfirmation
*/
function nonconfirmMessage(){

  frm = targetFormName;
  frm.elements.forDamage.value="";
  if(frm.elements.transactionType.value=="B" && frm.elements.isInvalidUldsPresent.value == "N"){
  }
  else if(frm.elements.transactionType.value=="B")
  {
	frm.elements.saveStatus.value="fromborrowclose";
  	submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
  	return;
  }
  //added by nisha for bugfix starts
  if(frm.elements.saveStatus.value=="dummysaved" && frm.elements.forDamage.value != "Y" && frm.elements.transactionType.value=="L"){

		frm.elements.saveStatus.value="saved";
	}
 //added by nisha for bugfix ends
  //onScreenLoad();

}


/**
*function to View Damage ULD Details
*/
function damageAgreement(){
frm=targetFormName;

// to get max number of records in uld table
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length+1;
 var hidUldNo = document.getElementsByName("uldNum");
 //var chkboxdmg = document.getElementsByName("damageCheck");
 var uldno = "";
 var cnt1 = 0;
 var flag = 0;
 var listdetail = "showDamage";
 if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){
     for(var i=0; i<chkboxuld.length; i++) {
       	 if(chkboxuld[i].checked) {
       	    if(cnt1 == 0){
       	    	  uldno=hidUldNo[i].value;
	 	  cnt1 = 1;
	    }else{
	          uldno=uldno+","+hidUldNo[i].value;
	    }
	    /*if(!chkboxdmg[i].checked) {
	    	flag = 1;
	    }*/

       	 }
      }
      if(flag == 0){

		submitForm(frm,'uld.defaults.transaction.updateloanborrowsession.do?uldNumbersSelected='+uldno+'&pageURL='+listdetail);
         // submitForm(frm,'uld.defaults.showdamage.do?uldNumbersSelected='+uldno+'&pageURL='+listdetail);
       }
       else{
           showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.oneofselecteduldisnotdamaged" />',type:1,parentWindow:self});
       }
    }
}


/**
*function to View Agreement Details
*/
function agreementReport(){

  frm=targetFormName;

 // to get max number of records in uld table
  var chkboxuld = document.getElementsByName("uldDetails");
  var chkcountuld = chkboxuld.length+1;
  var listdetail = "LoanBorrow";

  if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){
         submitForm(frm,'uld.defaults.transaction.agreementloanborrowdetails.do');
  }

}


/**
*function to clear Transaction Details
*/
function clearTransaction() {
     frm=targetFormName;
     frm.elements.txnTypeDisable.value = "";
     //frm.elements.saveStatus.value="cleared"
     //submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
     submitFormWithUnsaveCheck('uld.defaults.transaction.screenloadloanborrowuld.do?saveStatus=cleared');
}



var uldDetailstomod="";

function modUldDetails(){
	frm=targetFormName;

        if(validateSelectedCheckBoxes(frm,'uldDetails','','1')){
			uldDetailstomod="";
			for(var i=0; i<frm.elements.elements.length; i++) {
			if(frm.elements.elements[i].type == "checkbox") {
			if(frm.elements.elements[i].name=="uldDetails") {
				if(frm.elements.elements[i].checked) {
					uldDetails = frm.elements.elements[i].value;
					uldDetailstomod=uldDetailstomod+"-"+uldDetails;
				}
			}
				}
      		}
      		//alert("uldDetailstomod"+uldDetailstomod);
        	recreateTableDetailsBlur("uld.defaults.transaction.updateloanborrowuld.do","div1","div2","modifyuld");
       }
}

function modifyuld(_tableInfo){

  		_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];


       targetFormName.elements.txnTypeBasis.value = _innerFrm.transactionType.value;
       var txnType = targetFormName.elements.txnTypeBasis.value;
       var modifyMode="MODIFY";
       var modifyUldNum=uldDetailstomod;

       var fromPartyCode=_innerFrm.fromPartyCode.value;
       var toPartyCode=_innerFrm.toPartyCode.value;
       var partyType=_innerFrm.partyType.value;
       var pageurl=_innerFrm.pageurl.value;
       var transactionType=_innerFrm.transactionType.value;


       if(txnType == "L"){

       		var strAction="uld.defaults.transaction.screenloadaddloandetails.do?modifyMode="+modifyMode+"&modifyUldNum="+modifyUldNum+"&partyType="+partyType+"&fromPartyCode="+fromPartyCode+"&toPartyCode="+toPartyCode+"&pageurl="+pageurl+"&transactionType="+transactionType;
       		frm.elements.ispopup.value = "true";
       		// if(_asyncErrorsExist) return;
       		openPopUp(strAction,560,150);
       		 updateTableCodeBlur(_tableInfo);
       }
       else if(txnType == "B"){

            	var strAction="uld.defaults.transaction.screenloadaddborrowdetails.do?modifyMode="+modifyMode+"&modifyUldNum="+modifyUldNum+"&partyType="+partyType+"&fromPartyCode="+fromPartyCode+"&toPartyCode="+toPartyCode+"&pageurl="+pageurl+"&transactionType="+transactionType;
            	frm.elements.ispopup.value = "true";
            //	 if(_asyncErrorsExist) return;
            	openPopUp(strAction,560,150);
            	 updateTableCodeBlur(_tableInfo);
       }
       else{
           	//alert("Select a Transaction Type");
       }


  }

function modAccDetails(){
	frm=targetFormName;
        var chkboxuld = document.getElementsByName("accessoryDetails");
        var chkcountuld = chkboxuld.length+1;
	if(validateSelectedCheckBoxes(frm,'accessoryDetails',1,'1')){
        	for(var i=0; i<frm.elements.elements.length; i++) {
  			if(frm.elements.elements[i].type == "checkbox") {
  	   		if(frm.elements.elements[i].name=="accessoryDetails") {
  	     		if(frm.elements.elements[i].checked) {
  	        		accessoryDetails = frm.elements.elements[i].value;
  	        	}
 	   		}
         		}
      		}

        recreateTableDetailsBlur("uld.defaults.transaction.updateloanborrowuld.do","div1","div2","modifyacc");

        }
}

function modifyacc(_tableInfo){

  		_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];


       targetFormName.elements.txnTypeBasis.value = _innerFrm.transactionType.value;
       var txnType = targetFormName.elements.txnTypeBasis.value;
        var modifyMode="MODIFYACC";
        var modifyAcc=accessoryDetails;

       var fromPartyCode=_innerFrm.fromPartyCode.value;
       var toPartyCode=_innerFrm.toPartyCode.value;
       var partyType=_innerFrm.partyType.value;
       var pageurl=_innerFrm.pageurl.value;
       var transactionType=_innerFrm.transactionType.value;

	var strAction="uld.defaults.transaction.screenloadaddaccessorydetails.do?modifyMode="+modifyMode+"&modifyAcc="+modifyAcc;
	frm.elements.ispopup.value = "true";
	 //if(_asyncErrorsExist) return;
	openPopUp(strAction,550,150);
	 updateTableCodeBlur(_tableInfo);



  }

////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivId="";

function clearTable(){
	document.getElementById(_currDivId).innerHTML="";
}

function recreateTableDetails(strAction,divId){
	var __extraFn="updateTableCode";
	if(arguments[2]!=null){
		__extraFn=arguments[2];
	}
	_currDivId=divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);


}

function updateTableCode(_tableInfo){
	_str=getActualData(_tableInfo);
	document.getElementById(_currDivId).innerHTML=_str;
	cleanupTmpTable();
	reapplyEvents();
	applyScrollTableStyles();
}

function getActualData(_tableInfo){
	document.getElementById("tmpSpan").innerHTML=_tableInfo;
	_frm=document.getElementById("tmpSpan").getElementsByTagName("table")[0];
	return _frm.elements.outerHTML;
}

function cleanupTmpTable(){
	document.getElementById("tmpSpan").innerHTML="";
}


/////////////////////////////////////////////////////////////////////////////////////////

////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////
var _currDivIdLeg="";
var _currDivIdSeg="";

function recreateTableDetailsBlur(strAction,divIdLeg, divIdSeg){
//alert("1:::::::::");
       var _extraFn ="updateTableCodeBlur";
	if(arguments[3]!=null){
			_extraFn=arguments[3];
	}
	asyncSubmit(targetFormName,strAction,_extraFn,null,null,divIdLeg);


}


function updateTableCodeBlur(_tableInfo){
//alert("1:::::::::"+_tableInfo.document.getElementById('_legTableOfBlur').innerHTML);
	_frm=_tableInfo.document.getElementsByTagName("table")[0];


	tableleg = _tableInfo.document.getElementById('_legTableOfBlur').innerHTML;
	tablesegment = _tableInfo.document.getElementById('_segmentTableOfBlur').innerHTML;
	document.getElementById('div1').innerHTML=tableleg;
	document.getElementById('div2').innerHTML=tablesegment;

}


/////////////////////////////////////////////////////////////////////////////////////////
//Added by nisha for action that will be called after UCR printing
function reloadPage(){

	submitForm(frm,'uld.defaults.transaction.screenloadloanborrowuld.do');
}

//////////////////////////////////////For populating party names on tab out by Nisha starts on 28JAN08 //////////////////////////////////////////////////////
function populateFromPartyName(){

		if(targetFormName.elements.fromPartyCode.value !=""){
			if(targetFormName.elements.fromPartyCode.value != targetFormName.elements.fromPartyCode.defaultValue){
				targetFormName.elements.partyCode.value=targetFormName.elements.fromPartyCode.value;
				checkPartyCode(targetFormName.elements.partyCode.value, targetFormName.elements.fromPartyName);

				if(targetFormName.elements.fromPartyName.value == ""){
				
					if(targetFormName.elements.comboFlag.value=="airlinelov" || 
					  (targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="L") ){					   
						recreatePartyDetails("uld.defaults.transaction.populate.do","updateFromPartyName");
					}else if(targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="R" ){					
						recreateFromCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateFromCustomerDetails");
					}else if(targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="B" ){					
						recreateFromCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateFromCustomerDetails");
					}
				}

			}else{
				if(targetFormName.elements.errorStatusFlag.value=="Y"){
					targetFormName.elements.fromPartyCode.value = "";
					showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidpartycode" />',type:1,parentWindow:self});
					targetFormName.elements.fromPartyCode.focus();
				}
			}
		}else{
			targetFormName.elements.fromPartyName.value = "";
			targetFormName.elements.toShortCode.value = "";
		}
	
}

function updateFromPartyName(_partyNameInfo){

	_str = "";
	_innerFrm=_partyNameInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N"){
		_str=getPartyNameData(_partyNameInfo);
		addToPartyArray(targetFormName.elements.partyCode.value,_str);
	}else {
			targetFormName.elements.fromPartyCode.value = "";
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidpartycode" />',type:1,parentWindow:self});
		targetFormName.elements.fromPartyCode.focus();
	}

	targetFormName.elements.fromPartyName.value=_str;
}
function getPartyNameData(_partyNameInfo){
	_partyName = _partyNameInfo.document.getElementById("partyDiv").innerHTML;
	return _partyName;
}
function addToPartyArray(partyCode, partyName){

	_partyArr.put(partyCode, partyName);

}

function doesPartyExist(partyCode){
	var _party = _partyArr.get(partyCode);

	return _party;

}

function checkPartyCode(_val, partyNameFld){
	var party = doesPartyExist(_val);
	if(party!=null){

		partyNameFld.value = party;

	}
	else {

		partyNameFld.value = "";
	}
}
function recreatePartyDetails(strAction){

	var __extraFn="updateFromPartyName";
	if(arguments[1]!=null){
		__extraFn=arguments[1];
	}
	var key = "partyCode="+targetFormName.elements.partyCode.value;

	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}

function populateToPartyName(){
	

			if(targetFormName.elements.toPartyCode.value !=""){
				if(targetFormName.elements.toPartyCode.value != targetFormName.elements.toPartyCode.defaultValue){
					targetFormName.elements.partyCode.value=targetFormName.elements.toPartyCode.value;
					checkPartyCode(targetFormName.elements.partyCode.value, targetFormName.elements.toPartyName);

					if(targetFormName.elements.toPartyName.value == ""){	
					
						if(targetFormName.elements.comboFlag.value=="airlinelov" ||
						  (targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="R")){
							recreatePartyDetails("uld.defaults.transaction.populate.do","updateToPartyName");
						}else if(targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="L"){							
							recreateFromCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateToCustomerDetails");
						}else if(targetFormName.elements.comboFlag.value=="agentlov" && targetFormName.elements.transactionType.value=="B"){
							recreatePartyDetails("uld.defaults.transaction.populate.do","updateToPartyName");
						}else if(targetFormName.elements.comboFlag.value=="customerlov"){
							recreateFromCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateToCustomerDetails");
						}
					}

				}else{
					if(targetFormName.elements.errorStatusFlag.value=="Y"){
						targetFormName.elements.toPartyCode.value = "";
						showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidpartycode" />',type:1,parentWindow:self});
						targetFormName.elements.errorStatusFlag.value = 'N';
						targetFormName.elements.toPartyCode.focus();
					}
				}
			}else{
				targetFormName.elements.toPartyName.value = "";
				targetFormName.elements.toShortCode.value = "";
			}
	
}


function updateToPartyName(_partyNameInfo){

	_str = "";
	_innerFrm=_partyNameInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N"){
		_str=getPartyNameData(_partyNameInfo);
		addToPartyArray(targetFormName.elements.partyCode.value,_str);
	}else {
		targetFormName.elements.toPartyCode.value = "";
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidpartycode" />',type:1,parentWindow:self});
		targetFormName.elements.errorStatusFlag.value = 'N';
		targetFormName.elements.toPartyCode.focus();
	}

	targetFormName.elements.toPartyName.value=_str;
}

// QF1506
function populateToCustomerDetails(){
	if(targetFormName.elements.comboFlag.value=="agentlov"){
		if(targetFormName.elements.toShortCode.value !=""){
				//if(targetFormName.elements.toShortCode.value != targetFormName.elements.toShortCode.defaultValue){
					//targetFormName.elements.partyCode.value=targetFormName.elements.toPartyCode.value;
					checkCustomerCode(targetFormName.elements.toShortCode.value, targetFormName.elements.toPartyCode);
					checkPartyCode(targetFormName.elements.toPartyCode.value, targetFormName.elements.toPartyName);
					if(targetFormName.elements.toPartyCode.value == ""){
							recreateCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateToCustomerDetails");
					}
				//}
				//else{
					/*if(targetFormName.elements.errorStatusFlag.value=="Y"){
						targetFormName.elements.toShortCode.value = "";
						showDialog({msg:'Please enter a valid Short code.', 1, self);
						targetFormName.elements.errorStatusFlag.value = 'N';
						targetFormName.elements.toPartyCode.focus();
					}*/
				//}
			}else{
				// do nothing targetFormName.elements.toPartyCode.value = "";
			}
	}
}
function updateToCustomerDetails(_partyCodeInfo){
	 _str = "";
	 _name="";
	 _shortCode="";
	_innerFrm=_partyCodeInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N" ||targetFormName.elements.errorStatusFlag.value== ""){
		_str=getCustomerData(_partyCodeInfo);
		_name=getCustomerName(_partyCodeInfo);
		_shortCode=getShortCode(_partyCodeInfo);
		addToCutomerArray(targetFormName.elements.toShortCode.value,_str);
		addToPartyArray(_str,_name);
	}else {
		targetFormName.elements.toShortCode.value = "";
		//showDialog('Please enter a valid Short code.', 1, self);
		targetFormName.elements.errorStatusFlag.value = 'N';
		targetFormName.elements.toPartyCode.focus();
	}
	targetFormName.elements.toPartyCode.value=_str;
	targetFormName.elements.toPartyName.value=_name;
	targetFormName.elements.toShortCode.value=_shortCode;
}
function recreateCustomerDetails(strAction){
	var __extraFn="updateToCustomerDetails";
	if(arguments[1]!=null){
		__extraFn=arguments[1];
	}
	var key = "toShortCode="+targetFormName.elements.toShortCode.value;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}
function getCustomerName(_partyNameInfo){
	_partyName = _partyNameInfo.document.getElementById("ajaxToPartyNameDiv").innerHTML;
	return _partyName;
}
function getCustomerData(_partyCodeInfo){
	_partyCode = _partyCodeInfo.document.getElementById("ajaxToPartyCodeDiv").innerHTML;
	return _partyCode;
}
function getShortCode(_shortCodeInfo){
	_shortCode = _shortCodeInfo.document.getElementById("ajaxToShortCodeDiv").innerHTML;
	return _shortCode;
}
function addToCutomerArray(shortCode, partyCode){
	_customerArr.put(shortCode,partyCode);
}
function doesCutomerExist(shortCode){
	var _party = _customerArr.get(shortCode);
	return _party;
}
function checkCustomerCode(_val, partyNameFld){
	var party = doesCutomerExist(_val);
	if(party!=null){
		partyNameFld.value = party;
	}
	else {
		partyNameFld.value = "";
	}
}
// QF1506
function populateFromCustomerDetails(){
		if(targetFormName.elements.fromShortCode.value !=""){
					checkCustomerCode(targetFormName.elements.fromShortCode.value,targetFormName.elements.fromPartyCode);
					checkPartyCode(targetFormName.elements.fromPartyCode.value, targetFormName.elements.fromPartyName);
					if(targetFormName.elements.fromPartyCode.value == ""){
						recreateFromCustomerDetails("uld.defaults.transaction.listcustomerdetails.do","updateFromCustomerDetails");
					}
		}
}
function recreateFromCustomerDetails(strAction){
	var __extraFn="updateFromCustomerDetails";
	if(arguments[1]!=null){
		__extraFn=arguments[1];
	}
	var key = "fromShortCode="+targetFormName.elements.fromShortCode.value;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}
function updateFromCustomerDetails(_partyCodeInfo){
	 _str = "";
	 _name="";
	 _shortCode="";
	_innerFrm=_partyCodeInfo.document.getElementsByTagName("form")[0];
	targetFormName.elements.errorStatusFlag.value=_innerFrm.errorStatusFlag.value;
	if(targetFormName.elements.errorStatusFlag.value=="N" ||targetFormName.elements.errorStatusFlag.value== ""){
		_str=getFromCustomerCode(_partyCodeInfo);
		_name=getFromCustomerName(_partyCodeInfo);
		_shortCode=getFromShortCode(_partyCodeInfo);
		addToCutomerArray(targetFormName.elements.fromShortCode.value,_str);
		addToPartyArray(_str,_name);
	}else {
		targetFormName.elements.fromShortCode.value = "";
		//showDialog('Please enter a valid Short code.', 1, self);
		targetFormName.elements.errorStatusFlag.value = 'N';
		targetFormName.elements.fromPartyCode.focus();
	}
	targetFormName.elements.fromPartyCode.value=_str;
	targetFormName.elements.fromPartyName.value=_name;
	targetFormName.elements.fromShortCode.value=_shortCode;
}
function getFromShortCode(_shortCodeInfo){
	_shortCode = _shortCodeInfo.document.getElementById("ajaxFromShortCodeDiv").innerHTML;
	return _shortCode;
}
function getFromCustomerName(_partyNameInfo){
	_partyName = _partyNameInfo.document.getElementById("ajaxFromPartyNameDiv").innerHTML;
	return _partyName;
}
function getFromCustomerCode(_partyCodeInfo){
	_partyCode = _partyCodeInfo.document.getElementById("ajaxFromPartyCodeDiv").innerHTML;
	return _partyCode;
}
//////////////////////////////////////For populating party names on tab out by Nisha ends //////////////////////////////////////////////////////

//added by a-3045 for CR QF1016 starts
function addUldDetails1(){

		frm=targetFormName;

  if(frm.elements.transactionStation.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
      frm.elements.transactionStation.focus();
      return;
  }

  if(frm.elements.transactionDate.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
      frm.elements.transactionDate.focus();
      return;
  }

  if(frm.elements.partyType.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
      frm.elements.partyType.focus();
      return;
  }

  if(frm.elements.fromPartyCode.value == ""){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
       frm.elements.fromPartyCode.focus();
       return;
  }
  if(frm.elements.transactionTime.value == ""){
  	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
  	 frm.elements.transactionTime.focus();
  	 return;
  }
  if(frm.elements.toPartyCode.value == ""){
	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
	 frm.elements.toPartyCode.focus();
	 return;
  }

   if(frm.elements.partyType.value == "O"){
         if(frm.elements.fromPartyName.value == ""){
             showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
             frm.elements.fromPartyName.focus();
             return;
         }
         if(frm.elements.toPartyName.value == ""){
			  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
			  frm.elements.toPartyName.focus();
			  return;
         }
   }
	if(frm.elements.fromPartyCode.value.toUpperCase() == frm.elements.toPartyCode.value.toUpperCase()){
		showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.sameParty" />',type:1,parentWindow:self});
		 return;
	}
		if(targetFormName.elements.agreementFound.value=="N"){
				 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.noagreementfound" />',type:1,parentWindow:self});
				 return;
		}
	//added by a-3045 for bug ULD558 starts
	var uldNumber = document.getElementsByName("uldNum");
	var length=uldNumber.length;
	for(var i=0;i<length;i++){
		if(length>1){
			if(targetFormName.elements.uldNum[i].value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}
		}else{
			if(targetFormName.elements.uldNum.value == ""){
				showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.uldnumbermandatory" />',type:1,parentWindow:self});
				return;
			}
		}
	}
	//added by a-3045 for bug ULD558 ends
		//targetFormName.elements.transactionType.disabled = true;
		disableField(targetFormName.elements.transactionType);
		populateULDDetails();
}

 function populateULDDetails(){
	recreateULDGroupDetails('uld.defaults.transaction.generatecrn.do','parentulddiv');
 }
 var _tableDivId = "";
 function recreateULDGroupDetails(strAction,divId){
	var __extraFn="updateULDDetailsWithFocus";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

}
 function updateULDDetails(_tableInfo){
	renderTable(_tableInfo);

}
 function updateULDDetailsWithFocus(_tableInfo){
	setTimeout('showFocus()', 100);
	renderTable(_tableInfo);

}
function renderTable(_tableInfo){
	_filter=_tableInfo.document.getElementById("uldDetailsDiv");
	document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
	//added by a-3045 for CR QF1142 starts
	var uldDetails = document.getElementsByName("uldDetails");
	var length = uldDetails.length;
	//commented as a part of hiding the dummyLUC checkbox from the screen(bug 42813)
	/*if(frm.elements.dummyLUC.checked){		
		frm.elements.transactionStation.value="HDQ";
		frm.elements.transactionTime.value="00:01";
		if(length > 1){
			for(var i=0;i<length;i++){		
				frm.elements.destnAirport[i].value="HDQ";
				frm.elements.destnAirport[i].readOnly = true;
				frm.elements.destnAirportImg[i].disabled = true;				
			}
		}
		if(length == 1){
			frm.elements.destnAirport.value="HDQ";
			frm.elements.destnAirport.readOnly = true;
			frm.elements.destnAirportImg.disabled = true;
		}					
	}*/
	//commented as a part of hiding the dummyLUC checkbox from the screen(bug 42813) ends
	//added by a-3045 for CR QF1142 ends
}

function showFocus(){
	var hidUldNo = document.getElementsByName("uldNum");
		var ind=hidUldNo.length-1;
		if(ind > -1){
			hidUldNo[ind].focus();
		}
}

function updateDamage(ind){
	var dam = document.getElementsByName("damageChk");
	var damVal = document.getElementsByName("damageCheck");
	var len=dam.length;
	if(dam[ind].checked){
		damVal[ind].value="Y";
	}else{
		damVal[ind].value="N";
	}
}

function invokeTempLOV(obj,name){
	var index = obj.id.split(obj.name)[1];
	if(name == "uldNumLOV"){
	    displayLOV('uld.defaults.transaction.finduldnumberlov.do','N','Y','uld.defaults.transaction.finduldnumberlov.do',targetFormName.elements.uldNum[index].value,'uldNum','0','uldNum','',index);
	}
}

function deleteUldDetails1(){
 var frm=targetFormName;
 var chkboxuld = document.getElementsByName("uldDetails");
 var chkcountuld = chkboxuld.length+1;
 if(validateSelectedCheckBoxes(frm,'uldDetails',chkcountuld,'1')){
	var __extraFn="updateULDDetails";
	_tableDivId = "parentulddiv";
	asyncSubmit(targetFormName,"uld.defaults.transaction.deleteulddetails.do",__extraFn,null,null);
   }
}

function selectAll(frm){
	updateHeaderCheckBox(frm, frm.elements.selectAll, frm.elements.select);
}

function addAccDetails1(){

frm=targetFormName;

  if(frm.elements.transactionStation.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txnstationempty" />',type:1,parentWindow:self});
      frm.elements.transactionStation.focus();
      return;
  }

  if(frm.elements.transactionDate.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.txndateempty" />',type:1,parentWindow:self});
      frm.elements.transactionDate.focus();
      return;
  }

  if(frm.elements.partyType.value == ""){
      showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partytypeempty" />',type:1,parentWindow:self});
      frm.elements.partyType.focus();
      return;
  }

  if(frm.elements.fromPartyCode.value == ""){
       showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
       frm.elements.fromPartyCode.focus();
       return;
  }
  if(frm.elements.transactionTime.value == ""){
  	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.txntimeismandatory" />',type:1,parentWindow:self});
  	 frm.elements.transactionTime.focus();
  	 return;
  }
  if(frm.elements.toPartyCode.value == ""){
	 showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partycode" />',type:1,parentWindow:self});
	 frm.elements.toPartyCode.focus();
	 return;
  }

   if(frm.elements.partyType.value == "O"){
         if(frm.elements.fromPartyName.value == ""){
             showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
             frm.elements.fromPartyName.focus();
             return;
         }
         if(frm.elements.toPartyName.value == ""){
			  showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.msg.err.partyname" />',type:1,parentWindow:self});
			  frm.elements.toPartyName.focus();
			  return;
         }
		 }
		 //added by a-3045 for bug ULD558 starts
		 if(accessoryQtyMandatory()){
			showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.accessorycodemandatory" />',type:1,parentWindow:self});
			return;

	 	 }
		 if(duplicateAccessoryCodeValidation()){
		 	showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.accessorycodeexists" />',type:1,parentWindow:self});
		 	return;

	 	 }
		 if(accessoryQtyValidation()){
		 	showDialog({msg:'<bean:message bundle="loanBorrowULDResources" key="uld.defaults.loanBorrowULD.entervalidaccessoryquantity" />',type:1,parentWindow:self});
		 	return;

	 	 }
		//added by a-3045 for bug ULD558 ends
		// targetFormName.elements.transactionType.disabled = true;
		 disableField(targetFormName.elements.transactionType);
	addTemplateRow('accTemplateRow','AccessoryTableBody','accOperationFlag');
}

function delAccDetails1(){
    if(validateSelectedCheckBoxes(targetFormName,'childAcc',1000000,1)){
			deleteTableRow('childAcc','accOperationFlag');

    }
}
//added by a-3045 for CR QF1016 ends

//added by a-3045 for bug ULD558 starts
function accessoryQtyMandatory(){
	frm=targetFormName;
	var accQty = document.getElementsByName("accessoryQuantity");
	var accOpFlag = eval(frm.elements.accOperationFlag);
	if(accQty != null) {
		var size = accQty.length;
		if(size > 1) {
			for(var i=0; i<size; i++) {
				if(accOpFlag[i].value != "NOOP" && accOpFlag[i].value != "D"){
					if(targetFormName.elements.accessoryQuantity[i].value == ""){
						return true;
		 			}
				}
			}
		}else {
			if(accOpFlag.value != "NOOP" && accOpFlag.value != "D"){
				if(targetFormName.elements.accessoryQuantity.value == ""){
					return true;
		 		}
			}
		}
	}
	return false;
}

function duplicateAccessoryCodeValidation(){
	frm=targetFormName;
	var accCode = document.getElementsByName("acessoryCode");
	var accOpFlag = eval(frm.elements.accOperationFlag);
	if(accCode != null) {
		var length = accCode.length;
		if(length > 1) {
			for(var i=0; i<length; i++) {
				if(accOpFlag[i].value != "NOOP" && accOpFlag[i].value != "D"){
					for(var j=i+1; j<length; j++) {
						if(accOpFlag[j].value != "NOOP" && accOpFlag[j].value != "D"){
							if(targetFormName.elements.acessoryCode[i].value == targetFormName.elements.acessoryCode[j].value){
								return true;
							}
						}
					}
				}
			}
		}

	}
	return false;
}

function accessoryQtyValidation(){
	frm=targetFormName;
	var accQty = document.getElementsByName("accessoryQuantity");
	var accOpFlag = eval(frm.elements.accOperationFlag);
		if(accQty != null) {
			var size = accQty.length;
			if(size > 1) {
				for(var i=0; i<size; i++) {
					if(accOpFlag[i].value != "NOOP" && accOpFlag[i].value != "D"){
						if(targetFormName.elements.accessoryQuantity[i].value == 0){
							return true;
					 	}
					}
				}
			}else {
				if(accOpFlag.value != "NOOP" && accOpFlag.value != "D"){
					if(targetFormName.elements.accessoryQuantity.value == 0){
						return true;
					 }
				}
			}
		}
	return false;
}

//added by a-3045 for bug ULD558 ends

//added by a-3045 for CR QF1142 starts
function dummyLUC(frm){
	var uldDetails = document.getElementsByName("uldDetails");
	var length = uldDetails.length;
	if(frm.elements.dummyLUC.checked){
	frm.elements.dummyTxnAirport.value = frm.elements.transactionStation.value;
		frm.elements.transactionStation.value="HDQ";
		frm.elements.transactionTime.value="00:01";
		frm.elements.transactionStation.readOnly = true;
		//frm.elements.transactionStationImg.disabled = true;
		disableField(frm.elements.transactionStationImg);
		if(length > 1){
			for(var i=0;i<length;i++){			
				frm.elements.destnAirport[i].value="HDQ";
				frm.elements.destnAirport[i].readOnly = true;				
			}
		}
		if(length == 1){
			frm.elements.destnAirport.value="HDQ";
			frm.elements.destnAirport.readOnly = true;
		}					
	}
	if(!frm.elements.dummyLUC.checked){
		frm.elements.transactionStation.readOnly = false;
		//frm.elements.transactionStationImg.disabled = false;
		enableField(frm.elements.transactionStationImg);
		frm.elements.transactionStation.value = frm.elements.dummyTxnAirport.value;
		if(length > 1){
			for(var i=0;i<length;i++){	
				
				frm.elements.destnAirport[i].value="";			
				frm.elements.destnAirport[i].readOnly = false;				
			}
		}
		if(length == 1){
			frm.elements.destnAirport.value="";
			frm.elements.destnAirport.readOnly = false;
		}
	}
}

//added by a-3045 for CR QF1142 ends

// Added by Preet on 25Sep08 starts
// To populate ULD Nature on tab out of ULD Number
var index;
function getULDDetails(ind){
	if(targetFormName.elements.transactionType.value !="" && ind.value != ""){
	//index = ind.id.split("uldNum")[1];
	var hidUldNo = document.getElementsByName("uldNum");	
	for(var i=0;i<hidUldNo.length;i++){
		if(ind.value == hidUldNo[i].value){
			index = i;
		}
	}
		if(hidUldNo[index].value != ""){
			var __extraFn="updateULDDetailsAddFocus";
			_tableDivId = "parentulddiv";		
			var strAction="uld.defaults.transaction.fetchulddetails.do?rowIndex="+index;		
			asyncSubmit(targetFormName,strAction,__extraFn,null,null);	
		}
		//submitForm(targetFormName,strAction);
	}

}
// This is to set focus to ULD Number field after display LOV
function uldnumberFocus(ind){
	var hidUldNo = document.getElementsByName("uldNum");
	hidUldNo[ind].focus();
}

 function updateULDDetailsAddFocus(_tableInfo){
	setTimeout('showCrnFocus()', 100);
	setTimeout('applyTableStylesAfterAjaxResponse()', 100); // to hide combobox.
	renderTable(_tableInfo);	
}
function showCrnFocus(){
	//document.getElementById('addLinkUld').focus();
	//Added as part of ICRD-3207 by A-3767 on 19Jul11
	var uldCondition = document.getElementsByName("uldCondition");
	uldCondition[index].focus();	
}
function showAddFocus(){
	document.getElementById('addLinkUld').focus();
}
function showAddAccLinkFocus(){
	document.getElementById('addLinkAcc').focus();
}
// Added by Preet on 25Sep08 ends

function groupLovDisplay(odlnObj){
	var rowIndex = odlnObj.id.split("listOdlnGroupLov")[1];	
	displayLOV('shared.defaults.screenloaduldodlnlov.do','N','N','shared.defaults.screenloaduldodlnlov.do',document.forms[1].odlnCode.value,'odlnCode','1','odlnCode','',rowIndex);
	
	//displayOneTimeLOV('screenloadOneTime.do','N','N','screenloadOneTime.do',document.forms[1].odlnCode.value,document.forms[1].damageRemark.value,'ODLN Description','1','odlnCode','damageRemark',rowIndex,'uld.defaults.odlncodes','');
}



//added by a-3278 for bug 19133 starts
function showUldNumLOV(lovButton){

	var mainAction="uld.defaults.transaction.finduldnumberlov.do";
	var multiSelectOption="N";
	var pagination="Y";

	var title="uldNum";
	var formCount="0";

	var lovTxtFieldProperty="uldNum"
	var lovTxtFieldDescription="";	
	var rowIndex = lovButton.id.split("uldNumLOV")[1];	
	var uldNumbers = document.getElementsByName('uldNum');
	var positionCode=uldNumbers[rowIndex].value;

	var stationCode=targetFormName.elements.transactionStation.value;

	uldNumberLOV = mainAction+"?multiselect="+multiSelectOption+"&pagination="+pagination+"&lovaction="+
				    mainAction+"&code="+positionCode+"&title="+title+"&formCount="+formCount+
				    "&lovTxtFieldName="+ lovTxtFieldProperty+"&lovDescriptionTxtFieldName="+lovTxtFieldDescription+
				    "&index="+rowIndex+"&txnStation="+stationCode;

	openPopUp(uldNumberLOV,450,450);

}

//added by a-3278 for bug 19133 ends