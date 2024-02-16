<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	onScreenReady();
	onScreenLoad(frm);
	with(frm){
		evtHandler.addIDEvents("btnNewList","submitAction('listNewDetails',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnNewClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("btnReqClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("btnNewClear","submitAction('clearNewDetails',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnsave","submitAction('reqForcemaj',this.form)",EVT_CLICK);
		
		evtHandler.addIDEvents("btnReqList","submitAction('listReqDetails',targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnreqClear","submitAction('clearReqDetails',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnaccept","submitAction('reqAccept',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnReject","submitAction('reqReject',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnDelete","submitAction('delete',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnFileUpload","submitAction('upload',this.form)",EVT_CLICK);
		//LOV Events	
		evtHandler.addIDEvents("origin_airport_btn","invokeLOV('ORG_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("destination_btn","invokeLOV('DEST_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("viaPoint_btn","invokeLOV('VIA_POINT')",EVT_CLICK);
		evtHandler.addIDEvents("affectedAirport_btn","invokeLOV('AFF_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("pacode_btn","invokeLOV('PACOD')",EVT_CLICK);
		evtHandler.addIDEvents("edittab","showOverViewnewTab()",EVT_CLICK);
		evtHandler.addIDEvents("editreqtab","showOverViewreqTab()",EVT_CLICK);
		evtHandler.addIDEvents("reqseltab","showReqfilterview()",EVT_CLICK);
		evtHandler.addIDEvents("newseltab","showListfilterview()",EVT_CLICK);
		evtHandler.addIDEvents("btndeleteall","deleteAll()",EVT_CLICK);
		evtHandler.addIDEvents("forceid_btn","invokeLOV('FORCEID')",EVT_CLICK);
		/* evtHandler.addIDEvents("sortOption","popSortoption()",EVT_CLICK);
		evtHandler.addIDEvents("reqsortOption","popReqSortoption()",EVT_CLICK); */
		evtHandler.addIDEvents("btnPrint","submitAction('btnPrint',this.form)",EVT_CLICK);
		evtHandler.addIDEvents("airportFilter_btn","invokeLOV('ARP')",EVT_CLICK);
	}
}

function onScreenLoad(frm){

var reqstatus =targetFormName.elements.reqStatus.value;
var actionFlag =targetFormName.elements.actionFlag.value;
var displaytype=targetFormName.elements.displaytype.value;	
	document.getElementById("btnsave").disabled=true;	
	document.getElementById("btnaccept").disabled=true;
	document.getElementById("btnReject").disabled=true;
	document.getElementById("btnsave").disabled=true;
	if(actionFlag=='LISTNEW'){	 
	 
	 jquery('#tabs-new').show();
	 jquery('#tabs-req').hide();
	 jquery('#tabs').tabs({});
     
	 jquery('#newforcetabid').show();
	 jquery('#newMajeure').show();
	 jquery('#new_Tbl').show();
	 jquery('#reqforcetabid').hide();
	 jquery('#headerDataReq').hide();
     jquery('#reqMajeure').hide();
     if(displaytype=="SHOWNEW"){
	document.getElementById("btnsave").disabled=false;
     jquery('#headerFormNew').hide();
     jquery('#headerDataNew').show();
	 IC.util.widget.createDataTable("newForceTable",{tableId:"newForceTable",hasChild:false,scrollingY:'34vh'});
     }else{
     jquery('#headerFormNew').show();
     jquery('#headerDataNew').hide();
     }
	jquery('#headerFormReq').hide();
	
	jquery('#req_Tbl').hide();

	
	}else if(actionFlag=='LISTREQ'|| actionFlag=='APR'|| actionFlag=='REJ'){
	
	jquery('#headerFormReq').show();
	jquery('#headerDataNew').hide();
	jquery('#reqforcetabid').show();
	jquery('#headerFormNew').hide();
	jquery('#newforcetabid').hide();
	jquery('#newMajeure').hide();
	jquery('#reqMajeure').show();
	jquery('#new_Tbl').hide();
	jquery('#req_Tbl').show();
		if(displaytype=="SHOWREQ"){
	jquery('#headerDataReq').show();
     jquery('#headerFormReq').hide();
	 IC.util.widget.createDataTable("reqForceTable",{tableId:"reqForceTable",hasChild:false,scrollingY:'36vh'});
	 document.getElementById('reqForceTable_wrapper').style.width = '100%';
	 if(reqstatus=="Requested"){
	 document.getElementById("btnaccept").disabled=false;
	 document.getElementById("btnReject").disabled=false;
     }else{
	document.getElementById("btnaccept").disabled=true;
	 document.getElementById("btnReject").disabled=true;
	 }
     }else{
     jquery('#headerFormReq').show();
     jquery('#headerDataReq').hide();
     }
	jquery('#tabs').tabs({ active: (targetFormName.elements.actionFlag.value == 'LISTREQ'|| targetFormName.elements.actionFlag.value == 'APR' || targetFormName.elements.actionFlag.value == 'REJ' || targetFormName.elements.actionFlag.value =='CLEARREQ'  )? 1 : 0});
	jquery('#tabs-new').hide();
	jquery('#tabs-req').show();	
	}else if(actionFlag=='CLEARREQ'){
	document.getElementById("btnaccept").disabled=true;
	 document.getElementById("btnReject").disabled=true;
	jquery('#headerFormReq').show();
	jquery('#headerDataNew').hide();
	jquery('#reqforcetabid').show();
	jquery('#headerFormNew').hide();
	jquery('#newforcetabid').hide();
	jquery('#newMajeure').hide();
	jquery('#reqMajeure').show();
	jquery('#new_Tbl').hide();
	jquery('#req_Tbl').show();
	jquery('#headerFormReq').show();
     jquery('#headerDataReq').hide();
	}else{
	jquery('#tabs-new').show();
	jquery('#tabs-req').hide();
	jquery('#tabs').tabs({ active: (targetFormName.elements.actionFlag.value == 'LISTREQ' || targetFormName.elements.actionFlag.value == 'APR' || targetFormName.elements.actionFlag.value == 'REJ' || targetFormName.elements.actionFlag.value =='CLEARREQ' )? 1 : 0});	
	}
	if(jquery('#ForceMajeureRequestVOs') && jquery('#ForceMajeureRequestVOs').eq(0)) {
	   if(targetFormName.elements.defaultPageSizeTemp && targetFormName.elements.defaultPageSizeTemp.value.trim().length >0 &&
	   targetFormName.elements.defaultPageSizeTemp.value.trim()=='100') {
		   jquery('#ForceMajeureRequestVOs').eq(0).append('<option value="100" selected>100 </option>')
	   } else {
	   jquery('#ForceMajeureRequestVOs').eq(0).append('<option value="100">100 </option>')
	   }
	}
	jquery('#reqpopFilter').hide();
}
/* function popSortoption(){
jquery('.open-sorting').webuiPopover({
            url: '.show-sorting',
            placement: 'auto',
            closeable: true,
            title: "Sort By",
            width: '200',
            padding: false
        });	
}
function popReqSortoption(){
jquery('.open-sorting2').webuiPopover({
            url: '.show-sorting2',
            placement: 'auto',
            closeable: true,
            title: "Sort By",
            width: '200',
            padding: false
        });
} */

function sortData(sortField,sortorder){
	var frm=document.forms[1];
	if(frm.elements.sortingField.value == sortField){
		frm.elements.sortOrder.value=sortorder;
		if(frm.elements.sortOrder.value == ''){
		frm.elements.sortOrder.value = 'ASC';
		}else if(frm.elements.sortOrder.value == 'ASC'){
		frm.elements.sortOrder.value = 'DESC';
		}else if(frm.elements.sortOrder.value == 'DESC'){
			frm.elements.sortOrder.value = 'ASC';
		}
	}else{
		frm.elements.sortOrder.value = 'ASC';
	}
	frm.elements.sortingField.value = sortField; 
	var sortval=frm.elements.sortOrder.value;
	var actionFlag =targetFormName.elements.actionFlag.value;
	targetFormName.elements.displayPage.value='1';
	if(actionFlag=='LISTNEW'){	 
	submitForm(targetFormName,"mail.operations.ux.forcemajeure.list.do?sortingField="+sortField+"&sortOrder="+sortval);
	}else if(actionFlag=='LISTREQ'){
	submitForm(targetFormName,"mail.operations.ux.forcemajeure.reqlist.do?sortingField="+sortField+"&sortOrder="+sortval);
	}
}
 function enabledeletebtn(){
	var reqstatus =targetFormName.elements.reqStatus.value;
	var isChecked = 0;
	var chkBoxIds = document.getElementsByName('checkall');
	for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
			isChecked = isChecked + 1;		
			}
		}
	if(isChecked>0 && reqstatus=="Requested"){
		jquery('#openDel').show();
		}else{
		jquery('#openDel').hide();
	}
} 
function checkOnEnableDelbtn(){
var reqstatus =targetFormName.elements.reqStatus.value;
var selected = "";
var isChecked = 0;
	var chkBoxIds = document.getElementsByName('checkSel');
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
	if(isChecked>0 && reqstatus=="Requested"){
		jquery('#openDel').show();
		}else{
		jquery('#openDel').hide();
	}	
}
function showOverViewnewTab(){

jquery('#headerDataNew').hide();
jquery('#headerFormNew').show();
document.getElementById("btnNewList").disabled=false;
}
function showOverViewreqTab(){
jquery('#tabs-new').hide();
	jquery('#tabs-req').show();
	jquery('#tabs').tabs({});
jquery('#headerDataReq').hide();
jquery('#headerFormReq').show();
document.getElementById("btnReqList").disabled=false;

}
function showListfilterview(){
	
	var displaytype=targetFormName.elements.displaytype.value;
	
	jquery('#tabs-new').show();
	jquery('#tabs-req').hide();
	jquery('#tabs').tabs({});
	jquery('#newforcetabid').show();
	 jquery('#reqforcetabid').hide();
	jquery('#headerDataReq').hide();
	 jquery('#newMajeure').show();
     jquery('#reqMajeure').hide();
	jquery('#new_Tbl').show();
	jquery('#req_Tbl').hide(); 
	jquery('#headerFormReq').hide();
     jquery('#headerDataReq').hide();	
	
     jquery('#headerFormNew').show();
     jquery('#headerDataNew').hide();
}
function showReqfilterview(){
	
var displaytype=targetFormName.elements.displaytype.value;

	jquery('#tabs-new').hide();
	jquery('#tabs-req').show();
	jquery('#tabs').tabs({});
	jquery('#headerFormReq').show();
     jquery('#headerDataReq').hide();	
	jquery('#reqforcetabid').show();
	jquery('#newforcetabid').hide();
	 jquery('#newMajeure').hide();
    jquery('#reqMajeure').show();
	jquery('#new_Tbl').hide();
	jquery('#req_Tbl').show();
	jquery('#headerDataNew').hide();
	jquery('#headerFormNew').hide();

     jquery('#headerFormReq').show();
     jquery('#headerDataReq').hide();

}
 function submitAction(actiontype,frm){
	 var frm=targetFormName;
	 var type_action=actiontype;
	if(type_action == 'listNewDetails') {
		frm.elements.displayPage.value="1";
		frm.elements.lastPageNum.value="0";
	var fltNo=frm.elements.flightNumber.value;
	var fltdate=frm.elements.flightDate.value;
		var fromdat=frm.elements.frmDate.value;
		var toDat=frm.elements.toDate.value;
		var fltflag="N";
		var dateflg="N";
	if(fltNo){
		if(fltdate==""){
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.flightDate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.frmDate.focus();
			return;
	
			}else{
			fltflag="Y";
			}
		}
	if((fltflag=='N')&&(frm.elements.frmDate.value == "" || frm.elements.frmDate.value == null)){
			showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.frmDate.focus();
			return;
		}else if((fltflag=='N')&& (frm.elements.toDate.value == "" || frm.elements.toDate.value == null)){
			showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.fromdate_todate.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.toDate.focus();
			return;
		}
		if(fromdat && toDat){
			dateflg="Y";
		}
		if((dateflg=='Y')&& (frm.elements.affectedAirport.value == "" || frm.elements.affectedAirport.value == null  )){
			if(frm.elements.viaPoint.value == "" ){
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.affair.viapoint.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.affectedAirport.focus();
			return;
		}
		}else if((dateflg=='Y')&& (frm.elements.viaPoint.value == "" || frm.elements.viaPoint.value == null )){
			if(frm.elements.affectedAirport.value == "" ){
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.affair.viapoint.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.viaPoint.focus();
			return;
			}
		}
		if((fltflag=='Y')&& (frm.elements.affectedAirport.value == "" || frm.elements.affectedAirport.value == null  )){
			
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.affectedairport.mandatory.forflight" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.affectedAirport.focus();
			return;
		
		}
		if(frm.elements.scanType.value == "" || frm.elements.scanType.value == null  ){
			
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.scantype.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.scanType.focus();
			return;
		
		}
		//showListfilterview();
		frm.elements.displayPage.value=1;
		frm.elements.lastPageNum.value=0;
		var sortOrder='';
		frm.elements.sortingField.value='';
		frm.elements.actionFlag.value="LISTNEW";
		submitForm(frm,"mail.operations.ux.forcemajeure.list.do?sortOrder="+sortOrder);
		
	
	}
	if(type_action == 'listReqDetails') {
	var forceid=frm.elements.forceid.value;
	if(forceid){
	frm.elements.actionFlag.value="LISTREQ";
	var sortOrder='';
	frm.elements.sortingField.value='';
	var airportFilter = "";
	var carrierFilter = "";
	var flightNumberFilter = "";
	var flightDateFilter = ""; 
	var consignmentNo = "";
	var mailbagId = "";
	submitForm(frm,"mail.operations.ux.forcemajeure.reqlist.do?sortOrder="+sortOrder+"&airportFilter=" + airportFilter+ "&carrierFilter="+carrierFilter+"&flightNumberFilter="+flightNumberFilter+"&flightDateFilter="+flightDateFilter+"&consignmentNo="+consignmentNo+"&mailbagId="+mailbagId);
	}else{
			showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.forcid.required" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.forceid.focus();
			return;
		}	
	}
	if(type_action == 'clearNewDetails'){
	type_action="CLEARNEW";
	document.getElementById("btnsave").disabled=true;
	frm.elements.displaytype.value="CLEARNEW";
	submitForm(frm,"mail.operations.ux.forcemajeure.clear.do?actionFlag="+type_action);	
	}
	if(type_action == 'clearReqDetails'){
	type_action="CLEARREQ";
	var airportFilter = "";
	var carrierFilter = "";
	var flightNumberFilter = "";
	var flightDateFilter = ""; 
	var consignmentNo = "";
	var mailbagId = "";
	submitForm(frm,"mail.operations.ux.forcemajeure.clear.do?actionFlag="+type_action+"&airportFilter=" + airportFilter+ "&carrierFilter="+carrierFilter+"&flightNumberFilter="+flightNumberFilter+"&flightDateFilter="+flightDateFilter+"&consignmentNo="+consignmentNo+"&mailbagId="+mailbagId);	
	}
	if(type_action == "reqForcemaj"){
		var frm=targetFormName;
		if(frm.elements.scanType.value == "" || frm.elements.scanType.value == null  ){
			
				showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.scantype.mandatory" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			frm.elements.scanType.focus();
			return;
		
		}
	
	submitForm(frm,"mail.operations.ux.forcemajeure.requestforceMajeure.do");	
	}
	if(type_action == "reqAccept"){
		type_action='APR';
	submitForm(frm,"mail.operations.ux.forcemajeure.update.do?actionFlag="+type_action);		
	}
	if(type_action == "reqReject"){
		type_action='REJ';
	submitForm(frm,"mail.operations.ux.forcemajeure.update.do?actionFlag="+type_action);	
	}
	if(type_action == "delete"){
	submitForm(frm,"mail.operations.ux.forcemajeure.delete.do");	
	}
	if(type_action == "btnPrint"){
	  openReportDesignerPopup('MALFORMJRREQRPT','true')	
	}
	if(type_action == "upload"){
		var popupTitle = "Force Majeure Upload";
		var urlString = "mail.operations.ux.forcemajeure.screenloadupload.do";
		var closeButtonIds = ['btnClosePopUp'];
		var optionsArray = {
			actionUrl : urlString,
			dialogWidth:"650",
			dialogHeight:"280",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle
	   }
		popupUtils.openPopUp(optionsArray);		
	}
	
	
	
}
function gatherFilterAttributes(reportId){
	var filters; 
	if (reportId == "MALFORMJRREQRPT") {
			filters = [targetFormName.elements.forceid.value];
	} 			
	return filters; 
}

function formatFlt(fldObj){
    var flightNum = fldObj.value
	var fltNumLength = 4;
	if(flightNum.length!=0){
		if(flightNum.length < fltNumLength){
			var diffInLength = fltNumLength - flightNum.length;
			for(var i=0;i<diffInLength;i++){
				flightNum = '0'+flightNum;
			}
		}
		fldObj.value = flightNum;
	}

}

function onApplyFilter() {
	
	var airportFilter = jquery('#airportFilter').val();
	var carrierFilter = jquery('#flightNumber_carrierFilter').val();
	var flightNumberFilter = jquery('#flightNumber_flightNumberFilter').val();
	var flightDateFilter = jquery('#flightDateFilter').val(); 
	var consignmentNo = jquery('#CMP_Mail_Operations_ForceMajeure_ConsignmentFilter').val();
	var mailbagId = jquery('#CMP_Mail_Operations_ForceMajeure_MailbagIdFilter').val();
	
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	targetFormName.elements.actionFlag.value="LISTREQ";
	var action = "mail.operations.ux.forcemajeure.reqlist.do?airportFilter=" + airportFilter+ "&carrierFilter="+carrierFilter+"&flightNumberFilter="+flightNumberFilter+"&flightDateFilter="+flightDateFilter+"&consignmentNo="+consignmentNo+"&mailbagId="+mailbagId;     
	submitForm(targetFormName, action);
}

function onClearFilter() {
	
	var airportFilter = "";
	var carrierFilter = "";
	var flightNumberFilter = "";
	var flightDateFilter = ""; 
	var consignmentNo = "";
	var mailbagId = "";
	targetFormName.elements.actionFlag.value="LISTREQ";
	if(targetFormName.elements.defaultPageSize) {
	  targetFormName.elements.defaultPageSize.value= targetFormName.elements.defaultPageSizeTemp;
	}
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	
	var action = "mail.operations.ux.forcemajeure.reqlist.do?airportFilter=" + airportFilter+ "&carrierFilter="+carrierFilter+"&flightNumberFilter="+flightNumberFilter+"&flightDateFilter="+flightDateFilter+"&consignmentNo="+consignmentNo+"&mailbagId="+mailbagId;     
	submitForm(targetFormName, action);
}

function deleteAll(){
	var selected = "";
	var isChecked = 0;
	var selectrowval = "";
	var isSelCheck=0;
	var chkBoxIds = document.getElementsByName('checkall');
	var chkBoxSel = document.getElementsByName('checkSel');
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
	for(var i=0;i<chkBoxSel.length;i++){
			if(chkBoxSel[i].checked){
			isSelCheck = isSelCheck + 1;
				if(selectrowval != ""){
					selectrowval = selectrowval+","+chkBoxSel[i].value;
				}else if(selectrowval== ""){
					selectrowval =chkBoxSel[i].value;
				}				
			}
	}
	if(isChecked>0){
	submitForm(targetFormName, "mail.operations.ux.forcemajeure.delete.do?checkall="+selectrowval);	
	}
	if(isSelCheck>0){
	submitForm(targetFormName, "mail.operations.ux.forcemajeure.delete.do");		
	}
}
function deleteRow(obj){
	var reqstatus =targetFormName.elements.reqStatus.value;
	if(reqstatus=="Requested"){
	var index = obj.id.split('delete')[1];
	submitForm(targetFormName, "mail.operations.ux.forcemajeure.delete.do?checkSel="+index);
	}else{
		showDialog({	
				msg		:	"<common:message bundle="ForceMajeureResources" key="mail.operations.ux.forcemajeure.delete.notallowed" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
	
			return;
	}
}
function showEntriesforlisting(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.operations.ux.forcemajeure.list.do";     
	submitForm(targetFormName, action);
}

function showEntriesforreqlisting(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "mail.operations.ux.forcemajeure.reqlist.do";     
	submitForm(targetFormName, action);
}
function submitPage(lastPageNum,displayPage){
	var actionFlag =targetFormName.elements.actionFlag.value;	
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	if(actionFlag=="LISTNEW"){
	var action = "mail.operations.ux.forcemajeure.list.do";     
	submitForm(targetFormName, action);	
	}else{
	var action = "mail.operations.ux.forcemajeure.reqlist.do";     
	submitForm(targetFormName, action);	
}
}            
function closeScreen(){
location.href = appPath + "/home.jsp";
} 

function invokeLOV(LOVType){
	var optionsArray =getLOVOptions(LOVType);
	if(optionsArray){
		if(LOVType=='AGT' || LOVType=='CST'){
			lovUtils.showLovPanel(optionsArray)
		}
		else
			lovUtils.displayLOV(optionsArray);
	}
}

function getLOVOptions(LOVType){
var optionsArray;
	switch(LOVType) {		
		case 'AFF_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=5';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'affectedAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'affectedAirport',
				lovIconId					: 'affectedAirport_btn',
				maxlength					: 19
			}
			 break;	
		case 'ORG_ARP':
		var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Origin',
				codeFldNameInScrn			: 'origin_airport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 600,
				dialogHeight				: 620, //modified by A-8353 for ICRD-293368
				fieldToFocus				: 'origin_airport',
				lovIconId					: 'origin_airport_btn',
				maxlength					: 3
			}
			 break;
		case 'DEST_ARP':
		var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Destination',
				codeFldNameInScrn			: 'destination',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destination',
				lovIconId					: 'destination_btn',
				maxlength					: 3
			}
			 break;			 
			 case 'VIA_POINT':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'viaPoint',
				codeFldNameInScrn			: 'viaPoint',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'viaPoint',
				lovIconId					: 'viaPoint_btn',
				maxlength					: 3
			}
			 break;	
			case 'PACOD':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'PA Code',
				codeFldNameInScrn			: 'pacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],	
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'pacode',
				lovIconId					: 'pacode_btn',
				maxlength					: 3
			}
			 break;
			case 'FORCEID':
			var forceval=targetFormName.elements.forceid.value;
		var strUrl ='mail.operations.ux.forcemajeure.reqforcelov.do?formCount=1&forceid='+forceval;
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Request ID LOV',
				codeFldNameInScrn			: 'forceid',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],	
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'forceid',
				lovIconId					: 'forceid_btn',
				maxlength					: 25
			}
			 break;
		case 'ARP':
		var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'airportFilter',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 600,
				dialogHeight				: 620, //modified by A-8353 for ICRD-293368
				fieldToFocus				: 'airportFilter',
				lovIconId					: 'airportFilter_btn',
				maxlength					: 3,
				getValueWithoutTargetForm   :true
			}
			 break;
			default:
			optionsArray = {	
			}
		}
		return optionsArray;
}

function onScreenReady() { 
		/* hide show panel in Filter section
		---------------------------------*/
	jQuery('.more-less').click(function() {
       jQuery('#morelessPane').slideToggle(500);
       jQuery(this).find('img, label').toggle();
});
		

		}