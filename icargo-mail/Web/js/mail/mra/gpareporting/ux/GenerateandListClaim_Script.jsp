<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {
	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnList","submitAction('listDetails',targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitAction('clearDetails',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnGenClaimResdit","submitAction('genclaimresdit',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnGenClaim","submitAction('genclaim',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnclaimdtls","submitAction('claimdtls',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("paCode_btn","invokeLOV('PACOD')",EVT_CLICK);
		evtHandler.addIDEvents("edittab","showOverViewTab()",EVT_CLICK);
		evtHandler.addIDEvents("closetab","showOvercloseTab()",EVT_CLICK);
	
	}

	onScreenLoad();
}

function onScreenLoad(){
	var actionFlag =targetFormName.elements.actionFlag.value;
	document.getElementById("btnclaimdtls").disabled=true;
	if(actionFlag=='SHOWLIST'){	
	jquery('#headerForm').hide();	
	jquery('#headerData').show();
	document.getElementById("btnclaimdtls").disabled=false;
	}
	setTimeout(function(){
		IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
		document.getElementById('newClaimTable_wrapper').style.width = '100%';
		IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true}) 	
	}, 100);
 document.getElementById("btnList").disabled=false;
 document.getElementById("btnClose").disabled=false;
  

 }
 function showOvercloseTab(){
jquery('#headerData').show();
jquery('#headerForm').hide();
document.getElementById("btnList").disabled=true;
setTimeout(function(){
		IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
		document.getElementById('newClaimTable_wrapper').style.width = '100%';
		IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true}) 
	}, 100);	 
 }
function showOverViewTab(){
jquery('#headerData').hide();
jquery('#headerForm').show();
document.getElementById("btnList").disabled=false;
setTimeout(function(){
		IC.util.widget.createDataTable("newClaimTable",{tableId:"newClaimTable",hasChild:false,scrollingY:'5vh'});
		document.getElementById('newClaimTable_wrapper').style.width = '100%';
		IC.util.widget.recalculateTableContainerHeightForUx(jquery("section"),{hideEmptyBody:true}) 

	}, 100);
}
function setvalueOnClick(pacod,frmDat,ToDat){
var gpacode=pacod;
var fromDat = frmDat;
var toDat = ToDat;
frm.elements.paCode.value=gpacode;
frm.elements.fromDate.value=frmDat;
frm.elements.toDate.value=toDat;
}
 function submitAction(actiontype,targetFormName){
	var frm = targetFormName;
	 var type_action=actiontype;
if(type_action == 'listDetails') {
	frm.elements.lastPageNum.value="0";
	frm.elements.displayPage.value="1";
	var frmDate=frm.elements.fromDate.value;
	var toDat=frm.elements.toDate.value;
	var paCode= frm.elements.paCode.value;
	if((frmDate == "" ||frmDate == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}else if((toDat == "" || toDat == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
	if(paCode==""||paCode==null){
		showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.paCode.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.paCode.focus();
			return;
	}
	submitForm(targetFormName,"mail.mra.gpareporting.ux.generateandlistclaim.list.do");
}
if(type_action == 'clearDetails') {
		submitForm(targetFormName,"mail.mra.gpareporting.ux.generateandlistclaim.clear.do");
}
if(type_action == 'genclaimresdit') {
		var frmDate=frm.elements.fromDate.value;
	var toDat=frm.elements.toDate.value;
	var paCode= frm.elements.paCode.value;
	if((frmDate == "" ||frmDate == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}else if((toDat == "" || toDat == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
	if(paCode==""||paCode==null){
		showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.paCode.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.paCode.focus();
			return;
	}
	
		submitForm(targetFormName,"mail.mra.gpareporting.ux.generateandlistclaim.genclaim.do?fromButton='resdit'");
}
if(type_action == 'genclaim') {
		var frmDate=frm.elements.fromDate.value;
	var toDat=frm.elements.toDate.value;
	var paCode= frm.elements.paCode.value;
	if((frmDate == "" ||frmDate == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.fromDate.focus();
			return;
		}else if((toDat == "" || toDat == null)){
			showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
	if(paCode==""||paCode==null){
		showDialog({	
				msg		:	"<common:message bundle="GenerateandListClaimResourceBundle" key="mail.mra.gpareporting.ux.generateandlistclaim.paCode.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.paCode.focus();
			return;
	}
	
	submitForm(targetFormName,"mail.mra.gpareporting.ux.generateandlistclaim.genclaim.do?fromButton='claim'");
}
if(type_action == 'claimdtls') {

		if(validateSelectedCheckBoxes(targetFormName,'selectCheckBox',1,1) ){
		var paCode = "";
		var fromDate="";
		var toDate="";
		//var chkBoxIds = document.getElementsByName('selectCheckBox');
		//var paCodes = document.getElementsByName("paCode");
		//var fromDates = document.getElementsByName("fromDate");
		//var toDates=document.getElementsByName("toDate");
		//for(var i=0;i<chkBoxIds.length;i++){
		//	if(chkBoxIds[i].checked){
				paCode =frm.elements.paCode.value ;
				fromDate=frm.elements.fromDate.value;
				toDate=frm.elements.toDate.value;
		//		break;
		//	}
		//}
		if(paCode != "" && fromDate!="" && toDate!=""){
			submitForm(targetFormName,"mail.mra.gpareporting.ux.claimDetails.list.do?paCode="+paCode+"&fromDate="+fromDate+'&toDate='+toDate+"&fromScreen=MRA080");	//modified as part of ICRD-343131
		}
		}	
	}
}

function closeScreen(){
location.href = appPath + "/home.jsp";
} 
function openInvoicScreen(invoicId,statusval){
		
}
function showEntriesforlisting(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.mra.gpareporting.ux.generateandlistclaim.list.do";     
	submitForm(targetFormName, action);
}
function submitPage(lastPageNum,displayPage){
	var actionFlag =targetFormName.elements.actionFlag.value;	
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	var action = "mail.mra.gpareporting.ux.generateandlistclaim.list.do";     
	submitForm(targetFormName, action);	
}  
function invokeLOV(LOVType){
	var optionsArray =getLOVOptions(LOVType);
	if(optionsArray){
			lovUtils.displayLOV(optionsArray);
	}
}

function getLOVOptions(LOVType){
var optionsArray;
	switch(LOVType) {	
		case 'PACOD':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'PA Code',
				codeFldNameInScrn			: 'paCode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],	
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'paCode',
				lovIconId					: 'paCode_btn',
				maxlength					: 3
			}
			 break;
			default:
			optionsArray = {	
			}
		}
		return optionsArray;			 

}
