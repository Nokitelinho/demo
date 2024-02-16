<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{

if(targetFormName.elements.gpacode.disabled){


targetFormName.elements.btnClear.focus();

}

else{

	targetFormName.elements.gpacode.focus();
}


	with(targetFormName){


	evtHandler.addEvents("btnClose","closeFun()",EVT_CLICK);
	evtHandler.addEvents("btnList", "submitForm(targetFormName,'mailtracking.mra.gpabilling.listgpabillingInvoice.list.do?fromScreen=listinvoice')", EVT_CLICK);
	evtHandler.addEvents("btnClear", "submitForm(targetFormName,'mailtracking.mra.gpabilling.listgpabillingInvoice.clear.do')", EVT_CLICK);
	evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.gpacode.value,'GPACode','1','gpacode','',0)",EVT_CLICK);
	evtHandler.addIDEvents("invnumberlov","invoiceLOV()",EVT_CLICK);
	evtHandler.addEvents("btnFinalizeInv", "finalizeMultipleInvoice(targetFormName)", EVT_CLICK);
	evtHandler.addEvents("btnWithdrawInv", "withdrawInvoice(targetFormName)", EVT_CLICK);
	evtHandler.addEvents("btnView", "callOnView(targetFormName)", EVT_CLICK);
	evtHandler.addEvents("btnInvoiceDetails", "viewInvoiceDetails(targetFormName)", EVT_CLICK);
	evtHandler.addEvents("btnPrint","doPrint()",EVT_CLICK);
	evtHandler.addEvents("btnPrintSQ","doPrintSQ(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btnPrintAll","printAll()",EVT_CLICK);
	evtHandler.addEvents("btnPrintAllTK","printAllTK()",EVT_CLICK);
		evtHandler.addEvents("btnPrintAllSQ","printAllSQ(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btnListAccDetails","doListAccDetails(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btnEmailInvoice","emailInvoice(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("rowCount","toggleTableHeaderCheckbox('rowCount',targetFormName.elements.check)", EVT_CLICK);
	evtHandler.addEvents("check","updateHeaderCheckBox(targetFormName, targetFormName.elements.check, targetFormName.elements.rowCount)", EVT_CLICK);
	evtHandler.addEvents("btnViewOutstndRecvbles","outstndrecv(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btnDownloadPASS","downloadPASSFile(targetFormName)",EVT_CLICK);
	evtHandler.addIDEvents("passFileNameLov","invokeLOV('PASSFileName')",EVT_CLICK);

	}
 applySortOnTable("listGPABillInvoice",new Array("None","String","Date","None","String","String","String","Number"));

	onScreenLoad();

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
		case 'PASSFileName':
		var strUrl ='mail.mra.gpabilling.ux.filenamelov.list.do?formCount=1&maxlength=5';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'PASS File Name',
				codeFldNameInScrn			: 'passFileName',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 520,
				dialogHeight				: 550,
				fieldToFocus				: 'passFileName',
				lovIconId					: 'passFileNameLov',
				maxlength					: 19
			}
			 break;	
			default:
			optionsArray = {	
			}
		}
		return optionsArray;
}


function onScreenLoad(){
	if(!targetFormName.elements.gpacode.disabled){
		targetFormName.elements.gpacode.focus();
	}
	//targetFormName.gpacode.focus();

	/*if(targetFormName.elements.invoiceFinalizedStatus.value=="FINALIZED"){

	targetFormName.elements.invoiceFinalizedStatus.value="";
	showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.invoicefinalized" />',type:2,parentWindow:self});
	//submitForm(targetFormName,'mailtracking.mra.gpabilling.listgpabillingInvoice.list.do');

	}*/


	if(targetFormName.elements.gpaHKGPostConfirmFinalize.value=="ASKCONFIRM")
	{



	showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.hkgposteinvoice" />',type:4, parentWindow:self, parentForm:targetFormName, dialogId:'id_noDetails'});

	if(targetFormName.elements.currentDialogOption.value=='Y') {
		 targetFormName.action="mailtracking.mra.gpabilling.listgpabillingInvoice.finalizeInvoice.do?gpaHKGPostConfirmFinalize=Y";
		targetFormName.submit();
		}
	else{

	}


	}

}
function submitPage(lastPg,displayPg){
var frm = targetFormName;
	frm.elements.lastPageNumber.value=lastPg;
	frm.elements.displayPage.value=displayPg;
	submitForm(targetFormName,'mailtracking.mra.gpabilling.listgpabillingInvoice.list.do');
}
function viewInvoiceDetails (targetFormName){


var frm=targetFormName;
var counter=0;
var check=0;
var checkBoxesId = document.getElementsByName('rowCount');
var popupheight = document.body.clientHeight*50/100;
var popupwidth = document.body.clientWidth*30/100;

		for (var i=0;i<checkBoxesId.length;i++)
		{
			if( checkBoxesId[i].checked){

				//alert("checkBoxesId[i]"+checkBoxesId[i].value);
					check=check+1;

				}


			}

			if(check==0)
			{
				showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
			}

			else if(check>1)
			{
				showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectonerow" />',type:1,parentWindow:self});
			}
			else{

			for (var i =0; i < checkBoxesId.length; i++)
			{
				if(checkBoxesId[i].checked)
				{

				counter=i;
				}

			}

		targetFormName.elements.selectedRow.value=counter;
		//targetFormName.invokingScreen.value='invoiceenquiry';
		var invokingScreen='invoiceenquiry';
		frm.action="mailtracking.mra.gpabilling.listgpabillingInvoice.invoicedetails.do?invokingScreen="+invokingScreen;
		frm.submit();

		}



}


function invoiceLOV(){

var height = document.body.clientHeight;
var _reqHeight = (height*49)/100;

displayLOV('showInvoiceLOV.do','N','Y','showInvoiceLOV.do',targetFormName.elements.invoiceNo.value,'InvoiceNo','1','invoiceNo','',0,_reqHeight);

}

function closeFun(){


	submitFormWithUnsaveCheck(appPath + "/home.jsp");

}

function finalizeMultipleInvoice(targetFormName){
	var rowIds = document.getElementsByName('rowCount');
	var invStatus = document.getElementsByName("invStatus");
	var invCount=0;
	for(var i=0;i<rowIds.length;i++) {
		if(rowIds[i].checked)	{
			if(invStatus != null && invStatus[i].value =='Finalized'){
				invCount++;
			}
		}
	}
	if(invCount > 0){
		showDialog({msg:'Please select Proforma invoices only for Finalizing Invoices',type:1,parentWindow:self});
		return;
	}

var invoiceStatus=new Array();
invoiceStatus=document.getElementsByName("invStatus");
			var selectedRow="";
			var len=0;

			for(var i=0;i<rowIds.length;i++) {
				if(rowIds[i].checked)	{
                    if(invoiceStatus[i].value=='Finalized'){
			        showDialog({msg:"Selected Invoice(s) is/are already finalized",type:1,parentWindow:self});
			        return;
		  	       }else{
					len=len+1;
					selectedRow=selectedRow+i+",";
				}
			}
			}
			if(selectedRow.length == 0){
			showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
			}
			 str=selectedRow.substr(0,(selectedRow.length-1));
			 targetFormName.elements.selectedRow.value=str;

showDialog({
				msg		:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.finalize.msg.confirm" scope="request"/>',
				type	:	4,
				parentWindow:self,
				parentForm:targetFormName,
                dialogId:'id_1',
            onClose:function(result){
			screenConfirmDialog(targetFormName,'id_1');
		    screenNonConfirmDialog(targetFormName,'id_1');
			}
 	    });

		function screenConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'Y') {
		if (action == "id_1") {
			 targetFormName.action="mailtracking.mra.gpabilling.listgpabillingInvoice.finalizeInvoice.do?gpaHKGPostConfirmFinalize=N";
		         targetFormName.submit();

}

}
}
function screenNonConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'N') {
		if (action == "id_1") {
		}
	}
}
}
//Added by A-6991 for ICRD-211662 Starts
function withdrawInvoice(targetFormName) {
		showDialog({
				msg		:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.withdarw.msg.confirm" scope="request"/>',
				type	:	4,
				parentWindow:self,
				parentForm:targetFormName,
                dialogId:'id_1',
            onClose:function(result){
			screenConfirmDialog(targetFormName,'id_1');
		    screenNonConfirmDialog(targetFormName,'id_1');
			}
 	    });


//screenNonConfirmDialog(frm,'id_btdelete');
function screenConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'Y') {
		if (action == "id_1") {
		var frm=targetFormName;
	var counter=0;
		var check=0;
		var checkBoxesId = document.getElementsByName('rowCount');
		var popupheight = document.body.clientHeight*50/100;
		var popupwidth = document.body.clientWidth*30/100;
		var rebillcheck=validateSubrowcheckbox();
		if(rebillcheck==false){
			for (var i=0;i<checkBoxesId.length;i++)
						{

										if( checkBoxesId[i].checked){

											//alert("checkBoxesId[i]"+checkBoxesId[i].value);
											check=check+1;

										}


						}

						if(check==0)
									{
										showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
									}

						else if(check>1)
									{
										showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectonerow" />',type:1,parentWindow:self});
									}
							else{

									for (var i =0; i < checkBoxesId.length; i++)
										{
										if(checkBoxesId[i].checked)
										{

										counter=i;
										}

									}



		targetFormName.elements.selectedRow.value=counter;
		frm.action="mailtracking.mra.gpabilling.listgpabillingInvoice.withdrawInvoice.do";
		frm.submit();
  }
 }
}
}
}
function screenNonConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'N') {
		if (action == "id_1") {
		}
	}
}
}

//Added by A-6991 for ICRD-211662 Ends
function finalizeInv(targetFormName) {
//alert('***');

	var frm=targetFormName;
	var counter=0;
		var check=0;
		var checkBoxesId = document.getElementsByName('rowCount');
		var popupheight = document.body.clientHeight*50/100;
		var popupwidth = document.body.clientWidth*30/100;
		var rebillcheck=validateSubrowcheckbox();
		if(rebillcheck==false){
			for (var i=0;i<checkBoxesId.length;i++)
						{

										if( checkBoxesId[i].checked){

											//alert("checkBoxesId[i]"+checkBoxesId[i].value);
											check=check+1;

										}


						}

						if(check==0)
									{
										showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
									}

						else if(check>1)
									{
										showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectonerow" />',type:1,parentWindow:self});
									}
							else{

									for (var i =0; i < checkBoxesId.length; i++)
										{
										if(checkBoxesId[i].checked)
										{

										counter=i;
										}

									}



		targetFormName.elements.selectedRow.value=counter;
		frm.action="mailtracking.mra.gpabilling.listgpabillingInvoice.finalizeInvoice.do";
		frm.submit();


			}


		}
		}





function callOnView(frm) {


			var frm=targetFormName;
			var counter=0;
			var check=0;
			var checkBoxesId = document.getElementsByName('rowCount');
			var popupheight = document.body.clientHeight*50/100;
			var popupwidth = document.body.clientWidth*30/100;
			var rebillcheck=validateSubrowcheckbox();
			if(rebillcheck==false){
				for (var i=0;i<checkBoxesId.length;i++)
							{

									if( checkBoxesId[i].checked){

									//alert("checkBoxesId[i]"+checkBoxesId[i].value);
										check=check+1;

									}


							}

							if(check==0)
										{
											showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
										}

							else if(check>1)
										{
											showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectonerow" />',type:1,parentWindow:self});
										}
								else{

										for (var i =0; i < checkBoxesId.length; i++)
											{
											if(checkBoxesId[i].checked)
											{

											counter=i;
											}

										}



			targetFormName.elements.selectedRow.value=counter;
			frm.action="mailtracking.mra.gpabilling.listgpabillingInvoice.viewcn66.do";
			frm.submit();


				}


			}


}

function doPrint(){

	generateReport(targetFormName,'/mailtracking.mra.gpabilling.listgpabillingInvoice.print.do');
}

function doPrintSQ(targetFormName){
		 var selectedRows = document.getElementsByName('rowCount');
		var selectedRow = "";
		var len = 0;
		for (var i = 0; i < selectedRows.length; i++) {
			if (selectedRows[i].checked) {
				len = len + 1;
				selectedRow = selectedRow + i + ",";
			}
		}
		if (len == 0) {
			showDialog({
				msg: 'Please select a row',
				type: 1,
				parentWindow: self
			});
			return;
		}
		if (len > 1) {
			showDialog({
				msg: 'Select only one record to continue',
				type: 1,
				parentWindow: self
			});
			return;
		}

		shouldDisablePage=false;
		str = selectedRow.substr(0, (selectedRow.length - 1));
		targetFormName.elements.selectedRow.value = str;
		
		generateReport(targetFormName, "/mailtracking.mra.gpabilling.listgpabillingInvoicesq.print.do");

}

function doListAccDetails (targetFormName){


var frm=targetFormName;
var counter=0;
var check=0;
var checkBoxesId = document.getElementsByName('rowCount');
var popupheight = document.body.clientHeight*50/100;
var popupwidth = document.body.clientWidth*30/100;
var rebillcheck=validateSubrowcheckbox();
if(rebillcheck==false){
		for (var i=0;i<checkBoxesId.length;i++)
		{
			if( checkBoxesId[i].checked){

				//alert("checkBoxesId[i]"+checkBoxesId[i].value);
					check=check+1;

				}


			}

			if(check==0)
			{
				showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
			}

			else if(check>1)
			{
				showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectonerow" />',type:1,parentWindow:self});
			}
			else{

			for (var i =0; i < checkBoxesId.length; i++)
			{
				if(checkBoxesId[i].checked)
				{

				counter=i;
				}

			}

		targetFormName.elements.selectedRow.value=counter;

		submitForm(targetFormName,'mra.gpabilling.listgpabillingInvoice.listaccdetails.do');
			}

	}



}
/**
* Added by A-4809
* for ICRD-42160
*/
function emailInvoice(targetFormName){
var frm = targetFormName;

			//starts
			var checkBoxesId = document.getElementsByName('rowCount');
	var rowID = document.getElementsByName('rowId');
	var check=0;
	var row=0;
	var counter="";
	var subRowCheck="";
	var rowCheck="";
	for (var i=0;i<checkBoxesId.length;i++){
		if( checkBoxesId[i].checked){
			check=check+1;
			rowCheck = rowCheck+","+checkBoxesId[i].value;
		}
	}
	for (var i=0;i<rowID.length;i++){
		if( rowID[i].checked){
			row=row+1;
			subRowCheck = subRowCheck+","+rowID[i].value;
		}
	}
		//Modified by A-8527 for ICRD-334845
			if(check == 0&& row==0){
			showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectrow" />',type:1,parentWindow:self});
			}else if(check > 0 && row > 0){
				showDialog({msg:'Please select Prime invoice or Rebill invoice only, but not both ',type:1,parentWindow:self});
			}
			else {
			if(subRowCheck.length > 0){
				counter = subRowCheck.substr(1,(subRowCheck.length));
			}else if(rowCheck.length > 0){
				counter = rowCheck.substr(1,(rowCheck.length));
			}
			 targetFormName.elements.selectedRow.value=counter;
			 frm.action="mailtracking.mra.gpabilling.listgpabillingInvoice.sendemailinvoice.do";
		     frm.submit();
		}

	}
/**
*Added by A-4809 for ICRD-42160
*Function to print all/selected invoices
*For turkish invoice front page alone need to be printed
*directly to printer - Turkish specific
*/
function printAllTK(){
var frm = targetFormName;
var rowIds = document.getElementsByName('rowCount');
			var selectedRow="";
			var len=0;
			for(var i=0;i<rowIds.length;i++) {
				if(rowIds[i].checked)	{
					len=len+1;
					selectedRow=selectedRow+i+",";
				}
			}
			 str=selectedRow.substr(0,(selectedRow.length-1));
			 frm.elements.selectedRow.value=str;
			 generateReport(targetFormName,'/mailtracking.mra.gpabilling.listgpabillingInvoice.printallinvoicedetails.do');
			 //generateReport(frm,'/mtk.mra.gpabilling.generatecnInvoicetkrPrint.do');
}
/**
*Added by A-4809 for ICRD-42160
*Function to print all/selected invoices
*Invoice frontpage+cn51 print+Cn66 print required
*for base
*/
function printAll(){
var frm = targetFormName;
var rebillcheck=validateSubrowcheckbox();
if(rebillcheck==false){
var rowIds = document.getElementsByName('rowCount');
			var selectedRow="";
			var len=0;
			for(var i=0;i<rowIds.length;i++) {
				if(rowIds[i].checked)	{
					len=len+1;
					selectedRow=selectedRow+i+",";
				}
			}
			 str=selectedRow.substr(0,(selectedRow.length-1));
			 frm.elements.selectedRow.value=str;
			 generateReport(targetFormName,'/mailtracking.mra.gpabilling.listgpabillingInvoice.printall.do');
			 //generateReport(frm,'/mtk.mra.gpabilling.generatecnInvoicetkrPrint.do');
}
}
function validateSubrowcheckbox()
{
	var checkBoxesId = document.getElementsByName('rowCount');
	var rowID = document.getElementsByName('rowId');
	var rebillcheck=false;
	var check=0;
	var row=0;
	var counter=0;
	for (var i=0;i<checkBoxesId.length;i++){
		if( checkBoxesId[i].checked){
			check=check+1;
		}
	}
	for (var i=0;i<rowID.length;i++){
		if( rowID[i].checked){
			row=row+1;
		}
	}
	if(check >=1 && row >= 1){
		rebillcheck=true;
		showDialog({msg:'<common:message bundle="listgpabillinginvoiceresources" key="mailtracking.mra.gpabilling.listgpabillinginvoice.dlg.msg.selectprimeInvoice" />',type:1,parentWindow:self});
	}
	return rebillcheck;
}
function enablebasecheckbox(rowID,subrowId){
var rowindex=rowID;
var subindex=subrowId;
var checkBoxesId = document.getElementsByName('rowCount');
var rowID = document.getElementsByName('rowId');
/* for (var i=0;i<rowID.length;i++){
	if( rowID[i].checked){
		checkBoxesId[rowindex].checked=true;
		break;
	}else{
		checkBoxesId[rowindex].checked=false;
	}
 } *///commented  by A-8353 for ICRD-326058

}
function outstndrecv(targetFormName){
	var checkBoxesId = document.getElementsByName('rowCount');
	var rowID = document.getElementsByName('rowId');
	var check=0;
	var row=0;
	var counter=0;
	var rowVal=0;
	for (var i=0;i<checkBoxesId.length;i++){
		if( checkBoxesId[i].checked){
			check=check+1;
		}
	}
	for (var i=0;i<rowID.length;i++){
		if( rowID[i].checked){   //modified by A-8353 for ICRD-326058
			rowVal=rowID[i].value;
			row=row+1;
		}
	}
	if(row == 0){
		showDialog({msg:'Please select one Rebill invoice',type:1,parentWindow:self});
	}else if(check>1 || row > 1){
		showDialog({msg:'Please select one Prime Invoice and one Rebill invoice only',type:1,parentWindow:self});
	}
	else{
		counter=rowVal;
		targetFormName.elements.selectedRow.value=counter;
		submitForm(targetFormName,'mailtracking.mra.gpabilling.listgpabillingInvoice.outstandingreceivables.do');
	}
}

function downloadPASSFile(targetFormName) {
    var selectedRows = document.getElementsByName('rowCount');
    var selectedRow = "";
    var len = 0;
    for (var i = 0; i < selectedRows.length; i++) {
        if (selectedRows[i].checked) {
            len = len + 1;
            selectedRow = selectedRow + i + ",";
        }
    }
    if (len == 0) {
        showDialog({
            msg: 'Please select a row',
            type: 1,
            parentWindow: self
        });
        return;
    }
    if (len > 1) {
        showDialog({
            msg: 'Please select only one record to download PASS File',
            type: 1,
            parentWindow: self
        });
        return;
    }	
	shouldDisablePage=false;
    str = selectedRow.substr(0, (selectedRow.length - 1));
    targetFormName.elements.selectedRow.value = str;
    submitForm(targetFormName, "mail.mra.gpabilling.listgpabillinginvoice.downloadpassfile.do");
}
function printAllSQ(targetFormName){	 

 var selectedRows = document.getElementsByName('rowCount');
			var selectedRow="";
			var len=0;
		for (var i = 0; i < selectedRows.length; i++) {
			if (selectedRows[i].checked) {
					len=len+1;
					selectedRow=selectedRow+i+",";
				}
			}
		if (len == 0) {
			showDialog({
				msg: 'Please select a row',
				type: 1,
				parentWindow: self
			});
			return;
		}
		if (len > 1) {
			showDialog({
				msg: 'Select only one record to continue',
				type: 1,
				parentWindow: self
			});
			return;
		}
		shouldDisablePage=false;
			 str=selectedRow.substr(0,(selectedRow.length-1));
		targetFormName.elements.selectedRow.value = str;
					
			
		generateReport(targetFormName,'/mailtracking.mra.gpabilling.listgpabillingInvoice.printallSQ.do');
			
}