<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){
   var frm=targetFormName;
   if(frm.elements.fromDate.disabled == false) {
		 frm.elements.fromDate.focus();
	   }

 	with(frm){
		evtHandler.addEvents("btnList", "onList()", EVT_CLICK);
		evtHandler.addEvents("btnClear", "onClear()", EVT_CLICK);
		evtHandler.addEvents("btnReviewed", "onReviewed()", EVT_CLICK);
		evtHandler.addEvents("btnRerate","reRate()",EVT_CLICK);
		evtHandler.addEvents("btnClose", "submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.close.do')", EVT_CLICK);
		evtHandler.addEvents("btnChangeBillingStatus","changeStatus()",EVT_CLICK);
		evtHandler.addEvents("btnNavigatetoCN38","navigate()",EVT_CLICK);
		evtHandler.addEvents("btnViewProrate","viewProrate()",EVT_CLICK);
		evtHandler.addEvents("airlinecodelov","showAirline()",EVT_CLICK);
		evtHandler.addEvents("sectfromlov","displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].sectorFrom.value,'Airport','1','sectorFrom','',0)",EVT_CLICK);
		evtHandler.addEvents("secttolov","displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].sectorTo.value,'Airport','1','sectorTo','',0)",EVT_CLICK);
		evtHandler.addEvents("checkHead","updateHeaderCheckBox(targetFormName, targetFormName.checkHead, targetFormName.check)", EVT_CLICK);
		evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.checkHead)", EVT_CLICK);
	    evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
	    evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
		evtHandler.addEvents("btnVoid", "voidAction()", EVT_CLICK);
	    evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'Subclass','1','subClass','',0)", EVT_CLICK);
	}
 applySortOnTable("listInterlineTable",new Array("None","String","None","String","String","String","String","String","String","String","String","Number","String","Number","None","String","Number","String","String"));
onScreenLoad();
}


function onScreenLoad()
{
	var clientHeight = document.body.clientHeight;
var frm=targetFormName;
if(frm.elements.isReviewEnabledFlag.value=='N')
{
	disableField(frm.elements.btnReviewed);
}
}
function invokeLov(obj,name){
var index = obj.id.split(name)[1];
 if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOfficeOfExchange.value,'OfficeOfExchange','0','originOfficeOfExchange','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOfficeOfExchange.value,'OfficeOfExchange','0','destinationOfficeOfExchange','',index);
   }
}
function showAirline(){
	displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].airlineCode.value,'Airline','0','airlineCode','',0);
 }


function onList() {

	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.list.do?paginationMode=LIST');

}


function submitPage1(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.list.do?paginationMode=LINK');
}

function onClear(){

	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.clear.do');
}

function changeStatus(){
var frm=targetFormName;
var select=0;
billingstatus=document.getElementsByName("saveBillingStatus");
if(validateSelectedCheckBoxes(targetFormName,'check','','1')){
var check = document.getElementsByName('check');
var select=0;
var dsn=new Array();
var dsns=new Array();
dsn=document.getElementsByName("dsn");
var selectedrows="";
var fromScreen="fromInterlineBilling";
var isSame='true';

// This is find the first checked status
	if(check.length>0){
	for(var i=0;i<check.length;i++){
		if(check[i].checked){
			var status=billingstatus[i].value;
			break;
		}
	    }
	}


//This to check if different status are selected
	if(check.length>0){

		for(var i=0;i<check.length;i++){
			if(check[i].checked){

			if(billingstatus[i].value!=status){
			isSame='false';
			break;
			}

			}
		}

	}

	if(isSame=='false'){
	showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.despatcheswithsamestatus' />",type:1,parentWindow:self});

	return;

	}

for(var i=0;i<check.length;i++) {

		if(check[i].checked){
					if(selectedrows != "")

					selectedrows = selectedrows+","+check[i].value;

					else if(selectedrows== "")

					selectedrows =check[i].value;

					dsns[i+1]=dsn[i+1].value;

					select=i;

					if(billingstatus[i].value=='IU'){

							showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.inwardbillingstatuscannotchange' />",type:1,parentWindow:self});
							var popup="true";
							return;

					}
				else if(billingstatus[i].value=='IB' || billingstatus[i].value=='OD'){
					showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.statuscanotbechanged' />",type:1,parentWindow:self});
					var popup="true";
					return;

				}
				else if(billingstatus[i].value=='VD'){
					 showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.statuscanotbechangedforvoided' />",type:1,parentWindow:self});
					var popup="true";
					return; 
                  
				}

			}
	}



			if(popup!="true"){

			targetFormName.elements.selectedrows.value=selectedrows;



			openPopUp("mailtracking.mra.defaults.changestatuspopup.do?select="+select+"&despatchNumbers="+dsns+"&selectedrows="+selectedrows+"&fromScreen="+fromScreen,400,280);
			}
		}


}


function onReviewed(){

	var form=targetFormName;
	var select=0;
	billingstatus=document.getElementsByName("saveBillingStatus");
	var reviewCheck=document.getElementsByName("reviewCheck");
	var check = document.getElementsByName('check');
	var count=0;
	var selectedrows="";
	var checks=0;

	for(var i=0;i<check.length;i++) {
		if(check[i].checked)	{
			checks=checks+1;
			if(selectedrows.length > 0)
				selectedrows = selectedrows+","+check[i].value;
			else if(selectedrows.length == 0)
				selectedrows =check[i].value;
				count=i;
			if(billingstatus[i].value!='OB' ){
				showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.reviewedonlyoutwardbilling' />",type:1,parentWindow:self});
				return;
			}
		}
	}
	if(checks==0){
		showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.plsselectrow' />",type:1,parentWindow:self});
	}
	for(var i=0;i<check.length;i++) {
		if(check[i].checked)	{
			if(reviewCheck[i].value == "Y"){
				form.elements.review.value="Y";
				select=i;
				break;
			}
			else {
				form.elements.review.value="N";
				select=i;
				break;
			}
		}
	}
	if(checks>0){
		targetFormName.elements.selectedrows.value=selectedrows;
		submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.reviewed.do");
	}
}


function navigate(){

	var frm=targetFormName;
	var select=0;

	var check = document.getElementsByName('check');
			var select=0;

			for(var i=0;i<check.length;i++) {

					if(check[i].checked){

						select=i;

				}
		}

	if(validateSelectedCheckBoxes(targetFormName,'check','1','1')){

	submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.navigatetocn38.do?select="+select);
}

}

function viewProrate(){

	var frm=targetFormName;
	var select=0;

		var check = document.getElementsByName('check');
				var select=0;

				for(var i=0;i<check.length;i++) {

						if(check[i].checked){

							select=i;

					}
			}

		if(validateSelectedCheckBoxes(targetFormName,'check','1','1')){

	submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.viewprorate.do?select="+select);
	}
}

function reRate(){

        var frm=targetFormName;
		var chkBoxIds = document.getElementsByName('check');
		var billingstatus = document.getElementsByName("saveBillingStatus");
		var selected = "";
	    var isChecked = 0;
	    var length= chkBoxIds.length;
		var status;
		var validBillingStatus = "";
		var obExists = false;
		var ccarefnum=document.getElementsByName("ccaReferenceNumber");
		var mcastatus;

		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				isChecked = isChecked + 1;
				if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}

		}

		if(chkBoxIds.length>0){
	for(var i=0;i<chkBoxIds.length;i++){

		if(chkBoxIds[i].checked){
			status =billingstatus[i].value;
			if(status == 'VD'){
				validBillingStatus = 'VD';
			}
			if(status == 'OB'){
				obExists = true;
			}
		}

	    }
	}



	if(isChecked == 0){
			showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.selectcheckbox' />",type:1,parentWindow:self});
		}else if(status == 'IB'){
		    showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.inwardbilling' />",type:1,parentWindow:self});
		}else if(status == 'OD'){
		    showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.outwardbilling' />",type:1,parentWindow:self});
		}else if(status=='OH'){
		     showDialog({msg:"<common:message bundle='listinterlinebillingentriesBundle' key='mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.onhold'/>",type:1,parentWindow:self});
		}else if(validBillingStatus=='VD' && obExists == false){
		     showDialog({msg:"Mailbag/(s) is voided from MRA",type:1,parentWindow:self});
		}else{
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.rerate.do?&selectedrows='+selected);

		}

}


function confirmMessage() {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.void.do?');
}
function nonconfirmMessage() {
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.list.do?paginationMode=LIST');
}
function screenConfirmDialog(targetFormName, dialogId) {
	if(dialogId == 'id_1'){
		submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.void.do');
		}
	else{
	}
}
function screenNonConfirmDialog(targetFormName, dialogId) {
	if(targetFormName.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}
function voidAction(){
	var chkBoxIds = document.getElementsByName('check');
	var selected = "";
	var validBillingStatus="";
	var billingstatus = document.getElementsByName("saveBillingStatus");
	var reviewCheck=document.getElementsByName("reviewCheck");
	if(validateSelectedCheckBoxes(targetFormName,'check','','1')){
		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				if(billingstatus[i].value == 'OH' || billingstatus[i].value =='VD' || billingstatus[i].value == 'IB'|| billingstatus[i].value == 'WD'){
					validBillingStatus=billingstatus[i].value;
				}else if(billingstatus[i].value == 'OB' && reviewCheck[i].checked == true){
					validBillingStatus='OBR';
				}else if(billingstatus[i].value == 'OD'){
					validBillingStatus='OD';
				}
				if(selected != ""){
					selected = selected+","+chkBoxIds[i].value;
				}else if(selected== ""){
					selected =chkBoxIds[i].value;
				}
			}
		}
		if(selected.length > 0){
			if(validBillingStatus=='VD') {
		     showDialog({msg:"Mailbag/(s) already voided from MRA",type:1,parentWindow:self});
				return;
			}else if (validBillingStatus=='OH'){
				showDialog({msg:"Mailbags on-hold cannot be voided",type:1,parentWindow:self});
			 return;
			}else if (validBillingStatus=='IB'){
				showDialog({msg:"Mailbags on-hold cannot be voided",type:1,parentWindow:self});
			 return;
			}else if (validBillingStatus=='OBR'){
				showDialog({msg:"Outward billable Reviewed Mailbags cannot be voided",type:1,parentWindow:self});
			 return;
			}else if (validBillingStatus=='OD'){
				showDialog({msg:"Outward billed Mailbags cannot be voided",type:1,parentWindow:self});
			 return;
			}
			else if (validBillingStatus=='WD'){
				showDialog({msg:"Withdrawn Mailbags cannot be voided",type:1,parentWindow:self});
			 return;
			}
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.void.do?&selectedrows='+selected);
		}
	}
}