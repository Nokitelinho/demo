<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister(){

	var frm=targetFormName;
	//onScreenloadSetHeight();
	frm.elements.custCode.focus();
	with(frm){
		evtHandler.addIDEvents("btnAttach","onClickAttach()",EVT_CLICK);
		evtHandler.addIDEvents("btnAcc","onClickAccumulate()",EVT_CLICK);
		evtHandler.addIDEvents("btnRed","onClickRedeem()",EVT_CLICK);
		evtHandler.addIDEvents("loyaltylov","showLoyaltyProgLOV()",EVT_CLICK);
		evtHandler.addIDEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.custStation.value,'custStation','1','custStation','',0)",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickList(targetFormName.elements.iataCode)",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnclose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btnActivate","onClickActivate()",EVT_CLICK);
		evtHandler.addEvents("btnInactivate","onClickInActivate()",EVT_CLICK);
		evtHandler.addEvents("btnDelete","onClickDelete()",EVT_CLICK);
		evtHandler.addEvents("btnRevoke","onClickRevoke()",EVT_CLICK);
		evtHandler.addEvents("btnBlacklist","onClickBlackList()",EVT_CLICK);
		evtHandler.addEvents("btnDetails","onClickDetails()",EVT_CLICK);
		evtHandler.addEvents("btnLoyalty","onClickLoyaltyDetails()",EVT_CLICK);
		evtHandler.addIDEvents("customerlov","showCustomerLOV()",EVT_CLICK);
		evtHandler.addEvents("custCode","validateAlphanumeric(targetFormName.elements.custCode,'Customer Code','Invalid Customer Code',true)",EVT_BLUR);
		evtHandler.addEvents("custStation","validateAlphanumeric(targetFormName.elements.custStation,'Station','Invalid Station',true)",EVT_BLUR);
		evtHandler.addEvents("iataCode","validateAlphanumericWithMod(targetFormName.elements.iataCode)",EVT_BLUR);
		evtHandler.addEvents("btnPrint","onClickPrint(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnCreate","onClickCreateCustomer()",EVT_CLICK);
		//added by a-3045 for CR HA68 on 05Jun09 starts
		evtHandler.addEvents("btnUploadTSA","onClickUpload()",EVT_CLICK);
		//added by a-3045 for CR HA68 on 05Jun09 ends
		evtHandler.addIDEvents("locationValueLov","showLocationValue()",EVT_CLICK);
		onScreenLoad();
	}
	applySortOnTable("listCustomerTable",new Array("None","String","String","String","String","String","Date","Date","String","String","String","String","String"));
}

/* function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	var width = document.body.clientWidth;
	var pageHeight = ((height*90)/100) ;
	var tableHeight = pageHeight - 220;
}*/


function onClickList(text){
	if(validateAlphanumericWithMod(text))
	{
	var navigationmode = "LIST";
	targetFormName.elements.lastPageNumber.value="0";
	targetFormName.elements.displayPageNum.value="1";
	submitForm(targetFormName,"customermanagement.defaults.listcustomerdetails.do?navigationMode="+navigationmode);
}
}

function onClickAttach(){
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
		checkedValue = getCheckedValues();
		openPopUp("customermanagement.defaults.screenloadattachloyaltyprogram.do?check="+checkedValue,750,370);
	}
}


function onClickAccumulate(){
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
		checkedValue = getCheckedValues();
		openPopUp("customermanagement.defaults.screenloadpointaccumulation.do?check="+checkedValue,680,350);
	}
}


function onClickRedeem(){
	var check = validateSelectedCheckBoxes(targetFormName, 'check', 1, 1);
	if (check){
		var addRowId = "";
		for(var i=0;i<document.forms[1].elements.length;i++) {
			if(document.forms[1].elements[i].name =='check') {
				if(document.forms[1].elements[i].checked) {
					var checkedValue=document.forms[1].elements[i].value;
					if(addRowId == ""){
						addRowId = checkedValue;
					}else{
						addRowId = addRowId + "," + checkedValue;
					}
				}
			}
		}
		submitForm(targetFormName,"customermanagement.defaults.screenloadpointredemption.do?rows="+addRowId);
	}
}

function showLoyaltyProgLOV(){

 	var textfiledDesc="";
 	var loyaltyName=targetFormName.elements.loyaltyName.value;
 	var strAction="customermanagement.defaults.lov.screenloadloyaltyproglov.do";
 	var StrUrl=strAction+"?textfiledObj=loyaltyName&formNumber=1&textfiledDesc="+textfiledDesc+"&loyaltyProgramName="+loyaltyName;
 	//var myWindow = window.open(StrUrl, "LOV", 'width=510,height=390,screenX=100,screenY=30,left=250,top=100');
	var myWindow = openPopUp(StrUrl,'510','390') ;//Added by A-5219 for ICRD-41909
}


function submitList(strLastPageNum,strDisplayPage){

	var navigationmode = "NAVIGATION";
	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPageNum.value = strDisplayPage;
	targetFormName.action ="customermanagement.defaults.listcustomerdetails.do?navigationMode="+navigationmode;
	targetFormName.submit();
	disablePage();
}


function onClickClear(){
	var screenFlag = "Screenload";
	submitForm(targetFormName,"customermanagement.defaults.clearcustomerlisting.do?screenFlag="+screenFlag);
}

function onClickInActivate(){
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'check',25,1)){
		var checked = frm.elements.check;
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "I"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerinactive' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
		}
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotinactive' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
		}
		var length= checked.length;
		if(length>1){
			for(var i=0;i<length;i++){
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "I"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerinactive' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotinactive' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
			}
		}
		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "I"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerinactive' />",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}	
				});
				return;
			}
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotinactive' />",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
		}
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.inactiveselectedcustomer' />",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_2',
			onClose: function (result) {
				if(targetFormName.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(targetFormName,'id_2');
				}else if(targetFormName.elements.currentDialogOption.value == 'N') {
					screenNonConfirmDialog(targetFormName,'id_2');
				}
			}
		});
	}
}

function onClickActivate(){
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'check',25,1)){
		var checked = frm.elements.check;
		if(checked.value == 0 && checked.checked == true && frm.elements.statusvalues.value == "A"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customeractive' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
		}
		//added
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotactive' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
		}
		var length= checked.length;
		if(length>1){
			for(var i=0;i<length;i++){
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "A"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customeractive' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotactive' /> ",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
			}
		}
		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "A"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customeractive' /> ",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistcannotactive' /> ",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
		}
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.activateselectedcustomer' /> ",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_4',
			onClose: function (result) {
				screenConfirmDialog(targetFormName,'id_4');
				screenNonConfirmDialog(targetFormName,'id_4');
			}
		});
	}
}

 function onClickBlackList(){
 	var frm=targetFormName;
 	if(validateSelectedCheckBoxes(frm,'check',25,1)){
		var checked = frm.elements.check;
 		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "B"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerblacklisted' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
 		}
 		var length= checked.length;
 		if(length>1){
			for(var i=0;i<length;i++){
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "B"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerblacklisted' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
			}
 		}
 		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "B"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerblacklisted' />",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
 		}
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.blacklistselectedcustomer' />",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_6',
			onClose: function (result) {
				if(targetFormName.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(targetFormName,'id_6');
				}else if(targetFormName.elements.currentDialogOption.value == 'N') {
					screenNonConfirmDialog(targetFormName,'id_6');
				}
			}
		});
 	}
}


function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){
 	}
 	if(frm.elements.currentDialogOption.value == 'Y') {
		if(validateSelectedCheckBoxes(targetFormName,'check',25,1)){
			for(var i=0;i<targetFormName.elements.length;i++) {
				if(targetFormName.elements[i].name =='check') {
					if(targetFormName.elements[i].checked) {
						checkedValue = targetFormName.elements[i].value;
					}
				}
			}
		}
 		if(dialogId == 'id_2'){
 			var selection="inactivate";
			var navigationmode = "INACTIVATE";
 			submitForm(frm,"customermanagement.defaults.updatestatusforcustomers.do?flag="+selection+"&check="+
			checkedValue+"&navigationMode="+navigationmode);
 		}
 		if(dialogId == 'id_4'){
 			var selection = "activate";
			var navigationmode = "ACTIVATE";
 			submitForm(frm,"customermanagement.defaults.updatestatusforcustomers.do?flag="+selection+"&check="+
			checkedValue+"&navigationMode="+navigationmode);
 		}
 		if(dialogId == 'id_6'){
			var selection = "blacklist";
			submitForm(frm,"customermanagement.defaults.updatestatusforcustomers.do?flag="+selection+"&check="+checkedValue);
 		}
 		if(dialogId == 'id_10'){
			var selection = "revoke";
			submitForm(frm,"customermanagement.defaults.updatestatusforcustomers.do?flag="+selection+"&check="+checkedValue);
		}
		if(dialogId == 'id_12'){
			submitForm(targetFormName,"customermanagement.defaults.deletecustomers.do");
		}
 	}
}

function screenNonConfirmDialog(frm, dialogId) {
 	while(frm.elements.currentDialogId.value == ''){
 	}
 	if(frm.elements.currentDialogOption.value == 'N') {
 		if(dialogId == 'id_2'){
			submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
			return;
 		}
 		if(dialogId == 'id_4'){
			submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
			return;
 		}
 		if(dialogId == 'id_10'){
			submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
			return;
 		}
 		if(dialogId == 'id_6'){
			submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
			return;
 		}
 		if(dialogId == 'id_12'){
			submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
			return;
 		}
 	}
}


function onClickDetails(){
	if(validateSelectedCheckBoxes(targetFormName,'check',25,1)){
		for(var i=0;i<targetFormName.elements.length;i++) {
			if(targetFormName.elements[i].name =='check') {
				if(targetFormName.elements[i].checked) {
					var checkedValue=targetFormName.elements[i].value;
				}
			}
		}
		submitForm(targetFormName,"customermanagement.defaults.viewcustomerlistingdetails.do?statusFlag=LNKNAV");
	}
}




function onClickClose(){
	submitForm(targetFormName,"customermanagement.defaults.closeaction.do");
}

function onClickLoyaltyDetails(){
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
		for(var i=0;i<targetFormName.elements.length;i++) {
			if(targetFormName.elements[i].name =='check') {
				if(targetFormName.elements[i].checked) {
					checkedValue=targetFormName.elements[i].value;
				}
			}
		}
		submitForm(targetFormName,"customermanagement.defaults.viewloyaltydetailsofcustomer.do");
	}
}


function getCheckedValues(){
	var checkedValue="";
	if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
		for(var i=0;i<targetFormName.elements.length;i++) {
			if(targetFormName.elements[i].name =='check') {
				if(targetFormName.elements[i].checked) {
					checkedValue=targetFormName.elements[i].value;
				}
			}
		}
	}
	return checkedValue;
}

function onScreenLoad(){
	
	if(targetFormName.elements.screenFlag.value == "Screenload"){
		
		 disableField(targetFormName.elements.btnBlacklist);
		  disableField(targetFormName.elements.btnPrint);
		   disableField(targetFormName.elements.btnActivate);
		    disableField(targetFormName.elements.btnInactivate);
			 disableField(targetFormName.elements.btnRevoke);
			 disableField(targetFormName.elements.btnDetails);
		targetFormName.elements.screenFlag.value="";
	}else{
		 enableField(targetFormName.elements.btnBlacklist);
		  enableField(targetFormName.elements.btnPrint);
		   enableField(targetFormName.elements.btnActivate);
		    enableField(targetFormName.elements.btnInactivate);
			 enableField(targetFormName.elements.btnRevoke);
			 enableField(targetFormName.elements.btnDetails);
	}
	if(targetFormName.elements.canRedeem.value == "Y"){
		targetFormName.elements.canRedeem.value="";
		openPopUp("customermanagement.defaults.customerlisting.listpointredemption.do",560,390);
	}
}


function onClickPrint(frm) {
	generateReport(frm,"/customermanagement.defaults.listcustomerprint.do");
}


function onClickRevoke(){
	var frm=targetFormName;
 	if(validateSelectedCheckBoxes(frm,'check',25,1)){
		var checked = frm.elements.check;
 		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value != "B"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.selectonlyblacklistcustomer' /> ",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
 		}
 		var length= checked.length;
 		if(length>1){
			for(var i=0;i<length;i++){
				if(checked[i].checked == true && frm.elements.statusvalues[i].value != "B"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerblacklisted' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
			}
 		}
 		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value != "B"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.customerblacklisted ' />",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
 		}
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.removeblacklistforcustomer' />",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_10',
			onClose: function (result) {
				if(targetFormName.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(targetFormName,'id_10');
				}
			}
		});
 	}
}

function onClickDelete(){
	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'check',25,1)){
		var checked = frm.elements.check;
		if(checked.value==0 && checked.checked == true && frm.elements.statusvalues.value == "A"){
			showDialog({	
				msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.activatecustomercannotdelete' />",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
					submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
				}
			});
			return;
		}
		var length= checked.length;
		if(length>1){
			for(var i=0;i<length;i++){
				if(checked[i].checked == true && frm.elements.statusvalues[i].value == "A"){
					showDialog({	
						msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.activatecustomercannotdelete' />",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose : function () {
							submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
						}
					});
					return;
				}
			}
		}
		else if(length==1){
			if(checked[0].checked == true && frm.elements.statusvalues[0].value == "A"){
				showDialog({	
					msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.activatecustomercannotdelete' />",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					onClose : function () {
						submitForm(targetFormName,"customermanagement.defaults.refreshcustdetails.do");
					}
				});
				return;
			}
		}
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.deletecustomer' />",
			type	:	4, 
			parentWindow:self,
			parentForm:targetFormName,
			dialogId:'id_12',
			onClose: function (result) {
				if(targetFormName.elements.currentDialogOption.value == 'Y') {
					screenConfirmDialog(targetFormName,'id_12');
				}else if(targetFormName.elements.currentDialogOption.value == 'N') {
					screenNonConfirmDialog(targetFormName,'id_12');
				}
			}
		});
	}
}
 
function onClickCreateCustomer(){
	submitForm(targetFormName,"customermanagement.defaults.screenloadcustomerregistration.do?fromCustListing=Y&frmCusLisCreate=Y");
} 

function onClickUpload(){
	var action = "customermanagement.defaults.customerlisting.uploadscreenloadcommand.do"
	openPopUp(action,450,200);
}

function showLocationValue(){
	var frm=targetFormName;
	var locationType=targetFormName.elements.locationType.value;
	var locationValue=targetFormName.elements.locationValue.value;
	if(locationType!=null && locationType.length>0){
		if(locationType == "S"){
			displayLOV('showStation.do','N','Y','showStation.do',locationValue,'locationValue','1','locationValue','','0');
		}
		if(locationType == "C"){
			displayLOV('showCountry.do','N','Y','showCountry.do',locationValue,'locationValue','1','locationValue','','0');
		}
		if(locationType == "R"){
			displayLOV('showRegion.do','N','Y','showRegion.do',locationValue,'locationValue','1','locationValue','','0');
		}
		if(locationType == "CTY"){
			displayLOV('showCity.do','N','Y','showCity.do',locationValue,'locationValue','1','locationValue','','0');
		}
	}else {
		showDialog({	
			msg		:	"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.giveloaction' />",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
	}
}
function showCustomerLOV(){
	//displayLOV('showCustomer.do','N','Y','showCustomer.do',targetFormName.elements.custCode.value,'custCode','1','custCode','',0)
	var accNo = targetFormName.elements.custCode.value;
	var iataCode = targetFormName.elements.iataCode.value;
	var descriptionTxtFieldName = '';
	//Modified by A-7131 for ICRD-163305
	var StrUrl='showCustomer.do?&pagination=Y&lovaction=showCustomer.do&index=0&lovTxtFieldName=custCode&lovDescriptionTxtFieldName='+descriptionTxtFieldName+'&formCount=1&multiselect=N&mode=Y';
	// Added by A-7621 for < ICRD-132149 > 
		if(iataCode!='null'){
		StrUrl = StrUrl+'&iataCode='+iataCode;
		}
		if(accNo!='null'){
		StrUrl = StrUrl+'&code='+accNo;
		}
	var options = {url:StrUrl}
	var myWindow = openPopUp(StrUrl,570,450);
	//var myWindow = CustomLovHandler.invokeCustomerLov(options);
	myWindow._parentOkFnHook='updateCust';
	self._lovOkFnHook='updateCust';
}
function updateCust(values){
	//var values = targetFormName.elements.custCode.value;	
	var index = values.split("Ç");
	if(index[2] != 'null'){
		targetFormName.elements.iataCode.value = index[2];
		//alert(targetFormName.elements.iataCode.value);
		}else{
		targetFormName.elements.iataCode.value ='';
		}
	if(index[0] != 'null'){
		targetFormName.elements.custCode.value = index[0];
	}else{
		targetFormName.elements.custCode.value ='';
	}
	return;
}

function validateAlphanumericWithMod (text){
	debugger
	var textval=text.value;
	var reg=/^[0-9%*]+$/;
	if(textval !== '' && !reg.test(textval)){ 
	showDialog({	
			msg:"<common:message bundle='listcustomerform' key='customermanagement.defaults.listcustomer.warn.invalidIATA' />",
			type	:	1, 
			parentWindow:self
    			});
				return false;
	} 	
return true;
}



