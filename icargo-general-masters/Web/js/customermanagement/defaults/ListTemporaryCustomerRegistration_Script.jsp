<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){


	var frm;
	var selectedRows;
	frm=document.forms[1];
	if(frm.elements.listTempId.disabled==false){
	frm.elements.listTempId.focus();}

	with(frm){
		evtHandler.addEvents("btClose","doClose(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',document.forms[1].elements.station.value,'Station Code','1','station','',0)",EVT_CLICK);

		evtHandler.addIDEvents("customercodelov","showCustomerLOV()",EVT_CLICK);
		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("tempidLOV","showTempIDLOV()",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btCreate","doCreate(this.form)",EVT_CLICK);
		evtHandler.addEvents("btDetails","getDetails()",EVT_CLICK);

		evtHandler.addEvents("btRegisterCust","regCust()",EVT_CLICK);
		evtHandler.addEvents("btAttachCust","attach()",EVT_CLICK);
		evtHandler.addEvents("btBlackListed","blackList()",EVT_CLICK);
		evtHandler.addEvents("btDelete","deleteCust()",EVT_CLICK);
		evtHandler.addEvents("btRevokeBlackListed","revokeBlackList()",EVT_CLICK);



		evtHandler.addEvents("listTempId","validateAlphanumeric(targetFormName.listTempId,'Temp ID','Invalid Temp ID',true)",EVT_BLUR);
				evtHandler.addEvents("customerName","validateAlphanumeric(targetFormName.customerName,'Customer Name','Invalid Customer Name',true)",EVT_BLUR);
		evtHandler.addEvents("station","validateAlphanumeric(targetFormName.station,'Station','Invalid station',true)",EVT_BLUR);




		if(frm.elements.rowId!=null){
				evtHandler.addEvents("rowId","select(this.form)",EVT_CLICK);
				}
				evtHandler.addEvents("masterRowId","selectAll(this.form)",EVT_CLICK);


	}

applySortOnTable("listtempcustomerreg",new Array("None","String","String","String","String","Date","String","String"));

}
function showCustomerLOV(){
	//displayLOV('showCustomer.do','N','Y','showCustomer.do',document.forms[1].elements.customerCode.value,'customerCode','1','customerCode','',0)
		var accNo = document.forms[1].elements.customerCode.value;
	var descriptionTxtFieldName = '';
	var StrUrl='showCustomer.do?code='+accNo+'&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=customerCode&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	var myWindow = openPopUp(StrUrl,570,500);
	//var options = {url:StrUrl}
	//var myWindow = CustomLovHandler.invokeCustomerLov(options);

}



function select(frm){

	toggleTableHeaderCheckbox('rowId', frm.elements.masterRowId);
}

function selectAll(frm){

	updateHeaderCheckBox(frm, frm.elements.masterRowId, frm.elements.rowId);
}


function doClose(frm) {
	submitForm(frm,'customermanagement.defaults.closelisttemporarycustomerregistration.do');
}


function list(frm){

	if(frm.elements.validFrom.value != "" &&
		!chkdate(frm.elements.validFrom)){

		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.invalid.fromdate' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		return;
	}
	if(frm.elements.validTo.value != "" &&
		!chkdate(frm.elements.validTo)){

		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.invalid.todate' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		return;
	}

	frm.elements.fromList.value="Y";
	  var a="YES";
	  document.forms[1].elements.lastPageNum.value= 0;
	  document.forms[1].elements.displayPageNum.value = 1;
	  submitForm(frm,'customermanagement.defaults.listtempcustreg.do?paginationMode='+a);

}

function doClear(frm) {
	submitForm(frm,'customermanagement.defaults.clearlisttemporarycustomerregistration.do');
}

function doCreate(frm){
	var flag="listtempcustomerformfromcreate";
	submitForm(frm,"customermanagement.defaults.screenloadtemporarycustomerregistration.do?closeFlag="+flag);
}

function getDetails(){

	var frm=document.forms[1];
	var check = validateSelectedCheckBoxes(frm, 'rowId', 1000000000000, 1);
	if (check){
	var flag="listtempcustomerform";
	submitForm(frm,"customermanagement.defaults.viewtempcustreg.do?closeFlag="+flag);
	 }
}

function submitList(strLastPageNum,strDisplayPage){


	document.forms[1].elements.lastPageNum.value= strLastPageNum;
	document.forms[1].elements.displayPageNum.value = strDisplayPage;
	document.forms[1].action ="customermanagement.defaults.listtempcustreg.do";
	document.forms[1].submit();
	disablePage();
}
function showTempIDLOV(){
var textfiledDesc="";

var strAction="customermanagement.defaults.lov.screenloadtempidlov.do";
var StrUrl=strAction+"?textfiledObj=listTempId&formNumber=1&textfiledDesc="+textfiledDesc;
//var myWindow = window.open(StrUrl, "LOV", 'width=500,height=400,screenX=100,screenY=30,left=250,top=100')
var myWindow = openPopUp(StrUrl, 600,500);

}


function attach()
{
	var frm=document.forms[1];
	if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

		var checked = frm.elements.rowId;

		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		return;
		}
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"){
		//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		return;
		}

		var length= checked.length;

		if(length>1){
		for(var i=0;i<length;i++){

		if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){

		//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}
		if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"){

		//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}
		}
		}
		else if(length==1){

		if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
		//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}
		if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"){

			//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
			showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});
			submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
			return;
		}
		}

		//displayLOV('showCustomer.do','N','Y','showCustomer.do',document.forms[1].elements.custCodeForAttach.value,'custCodeForAttach','1','custCodeForAttach','',null,"attchCust()")
		var accNo = targetFormName.elements.custCodeForAttach.value;
	   var descriptionTxtFieldName = '';
	   var StrUrl='showCustomer.do?code='+accNo+'&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=custCodeForAttach&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	   var myWindow = openPopUp(StrUrl,570,500);
	   myWindow._parentOkFnHook='attchCust()';
	   self._lovOkFnHook='attchCust()';





	}



}

function blackList()
{
var frm=document.forms[1];
if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

	var checked = frm.elements.rowId;

	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"){
		//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
	}

	var length= checked.length;

	if(length>1){
	for(var i=0;i<length;i++){

	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){

	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"){

		//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
	}
	}
	}
	else if(length==1){

	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"){

			//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
			showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
			});
			submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
			return;
	}
	}

	//showDialog('Do you want to BlackList the selected customers?', 4, self, frm, 'id_2');
	showDialog({	
			msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.cnf.customers.blacklist' scope='request'/>",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_2',
			onClose: function (result) {
					if(frm.elements.currentDialogOption.value == 'Y') {
						screenConfirmDialog(frm,'id_2');
					}else if(frm.elements.currentDialogOption.value == 'N') {
						screenNonConfirmDialog(frm,'id_2');
					}
				}
		});
	}

}

function deleteCust()
{

var frm=document.forms[1];
if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

	var checked = frm.elements.rowId;


	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"
	&& frm.elements.custcode.value != "null"){
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.cannotdelete.customerid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:frm
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}

	var length= checked.length;

	if(length>1){
	for(var i=0;i<length;i++){


	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"
	&& frm.elements.custcode[i].value != "null"){

	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.cannotdelete.customerid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:frm
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	}
	}
	else if(length==1){


	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"
	&& frm.elements.custcode[0].value != "null"){

		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.cannotdelete.customerid' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:frm
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
	}
	}
	showDialog({	
			msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.cnf.delete.customer' scope='request'/>",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_3',
			onClose: function (result) {
					if(frm.elements.currentDialogOption.value == 'Y') {
						screenConfirmDialog(frm,'id_3');
					}else if(frm.elements.currentDialogOption.value == 'N') {
						screenNonConfirmDialog(frm,'id_3');
					}
				}
		});



	}



}






function attchCust(){

	var frm =  document.forms[1];
	//alert(frm.custCodeForAttach.value);
	submitForm(frm,'customermanagement.defaults.attachcustomer.do');
}


function regCust()
{
var frm=document.forms[1];
if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

	var checked = frm.elements.rowId;

	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"){
	//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}

	var length= checked.length;

	if(length>1){
	for(var i=0;i<length;i++){

	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){

	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"){

	//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	}
	}
	else if(length==1){

	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
	//showDialog('Customer(s) are blacklisted', 1, self, frm, 'id_5');
	showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.blocklisted.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
	submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
	return;
	}
	if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"){

		//showDialog('Customer(s) are inactive', 1, self, frm, 'id_6');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.inactive.customers' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
	}
	}


	document.forms[1].action ="customermanagement.defaults.registercustomer.do";
	document.forms[1].submit();
	}

}
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

 	}

 	if(frm.elements.currentDialogOption.value == 'Y') {

 		if(dialogId == 'id_2'){
 			document.forms[1].action ="customermanagement.defaults.blacklistcustomer.do?flag=";
				document.forms[1].submit();

 		}

 		if(dialogId == 'id_8'){
		 			var selection="revoke";
		 			document.forms[1].action ="customermanagement.defaults.blacklistcustomer.do?flag="+selection;
						document.forms[1].submit();

 		}

 			if(dialogId == 'id_3'){
		 			document.forms[1].action ="customermanagement.defaults.deletetempcustomer.do";
						document.forms[1].submit();

		 		}



 		}
 		}


 		function screenNonConfirmDialog(frm, dialogId) {
			while(frm.elements.currentDialogId.value == ''){

		 	}

		 	if(frm.elements.currentDialogOption.value == 'N') {


		 		if(dialogId == 'id_2'){
				submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
				}
				if(dialogId == 'id_3'){
					submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
				}
				if(dialogId == 'id_2'){
					submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
				}
				if(dialogId == 'id_8'){
					submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
				}
				}
}

function revokeBlackList(){
var frm=document.forms[1];
	if(validateSelectedCheckBoxes(frm,'rowId',25,1)){

		var checked = frm.elements.rowId;

		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value != "B"){
		//showDialog('Select only blacklisted Customer(s)', 1, self, frm, 'id_7');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.customers.onlyblacklisted' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}


		var length= checked.length;

		if(length>1){
		for(var i=0;i<length;i++){

		if(checked[i].checked == true && frm.elements.statusvalues[i].value != "B"){

		//showDialog('Select only blacklisted Customer(s)', 1, self, frm, 'id_7');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.customers.onlyblacklisted' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}

		}
		}
		else if(length==1){

		if(checked[0].checked == true && frm.elements.statusvalues[0].value != "B"){
		//showDialog('Select only blacklisted Customer(s)', 1, self, frm, 'id_7');
		showDialog({	
				msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.err.customers.onlyblacklisted' scope='request'/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName
		});
		submitForm(targetFormName,"customermanagement.defaults.refreshtempcustdetails.do");
		return;
		}

		}

	showDialog({	
			msg		:	"<common:message bundle='listtempcustomerform' key='cm.defaults.listtemporarycustomerregistration.cnf.revoke.customers.blacklisted' scope='request'/>",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_8',
			onClose: function (result) {
					if(frm.elements.currentDialogOption.value == 'Y') {
						screenConfirmDialog(frm,'id_8');
					}else if(frm.elements.currentDialogOption.value == 'N') {
						screenNonConfirmDialog(frm,'id_8');
					}
				}
		});

}
}