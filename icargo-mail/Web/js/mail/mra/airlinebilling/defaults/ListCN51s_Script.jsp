<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;


	with(targetFormName){
		evtHandler.addEvents("btList","listCN51Action()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearCN51Action()",EVT_CLICK);
		evtHandler.addEvents("btnCN51Details","viewCN51DetailsAction()",EVT_CLICK);
		evtHandler.addEvents("btnCN66Details","viewCN66DetailsAction()",EVT_CLICK);
		evtHandler.addEvents("btnAccEntries","listAccEntries()",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeviewCN51ScreenAction()",EVT_CLICK);
		//evtHandler.addEvents("tableRowId","toggleTableHeaderCheckbox('tableRowId',targetFormName.allCheck)", EVT_CLICK);
		evtHandler.addEvents("airlineCodelov",
		"displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineCode.value,'AirlineCode','1','airlineCode','',0)",
		EVT_CLICK);
		
		evtHandler.addEvents("btnFinalizeInvoice","FinalizeInvoice()",EVT_CLICK); //Added by A-7929 as part of ICRD-265471
		evtHandler.addIDEvents("invoiceNumberLov","invoiceLOV()",EVT_CLICK); //Added by A-7929 as part of ICRD-265471

	}
 applySortOnTable("captureAgtSettlementMemo",new Array("None","String","String","String","Number","String")); 

screenload();

}

function screenload(){
// initial focus on page load.
if(targetFormName.elements.blgFromDateStr.disabled == false) {
    targetFormName.elements.blgFromDateStr.focus();	
}

if(targetFormName.elements.accEntryFlag.value == "N"){
			disableField(targetFormName.btnAccEntries);
		}else{
			enableField(targetFormName.btnAccEntries);
		}
if(targetFormName.elements.screenStatus.value=="screenload"){
disableField(targetFormName.btnCN66Details);
disableField(targetFormName.btnCN51Details);
disableField(targetFormName.btnAccEntries);
disableField(targetFormName.btnFinalizeInvoice); //Added By a-7929 as part of ICRD-265471
disableField(targetFormName.printMiscReportButtonId);
}
if(targetFormName.elements.screenStatus.value=="finalizeList"){
submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listCN51s.listCN51.do');
targetFormName.elements.screenStatus.value="";
}

	//Added by A-8464 as part of ICRD-235879
	var billingtype=targetFormName.elements.interlineBlgType.value;
	if(billingtype=='O'){
		enableField(targetFormName.printMiscReportButtonId);
	}
	else{
		disableField(targetFormName.printMiscReportButtonId);
	}

}

function listCN51Action()
{


		submitForm(targetFormName,
			   'mailtracking.mra.airlinebilling.defaults.listCN51s.listCN51.do');



}
function submitPage(lastPg,displayPg){
var frm = targetFormName;
	frm.elements.lastPageNumber.value=lastPg;
	frm.elements.displayPage.value=displayPg;
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listCN51s.listCN51.do');
}



function clearCN51Action()
{
	//alert(' USER ACTION CLEAR ');
	submitForm(targetFormName,
		   'mailtracking.mra.airlinebilling.defaults.listCN51s.clearCN51.do');
}

function viewCN51DetailsAction()
{
	var chkbox = document.getElementsByName("tableRowId");

	if(validateSelectedCheckBoxes(targetFormName,'tableRowId',1,1)) {
		//alert(' USER ACTION VIEWCN51DETAILSACTION ');
		submitForm(targetFormName,
			'mailtracking.mra.airlinebilling.defaults.listCN51s.viewCN51Details.do');
	}



}

function viewCN66DetailsAction()
{
	var chkbox = document.getElementsByName("tableRowId");
	if(validateSelectedCheckBoxes(targetFormName,'tableRowId',1,1)) {
		//alert(' USER ACTION VIEWCN66DETAILSACTION ');
		submitForm(targetFormName,
				'mailtracking.mra.airlinebilling.defaults.listCN51s.viewCN66Details.do');
	}


}

function closeviewCN51ScreenAction()
{
	//alert(' USER ACTION CLOSEVIEWCN51SCREENACTION ');
	submitForm(targetFormName,
				'mailtracking.mra.airlinebilling.defaults.listCN51s.closeCN51.do');

}

function listAccEntries(){

	if(validateSelectedCheckBoxes(targetFormName,'tableRowId',1,1)){
		var checkIndex;
		var selectedRow=document.getElementsByName('tableRowId');
		for(var i=0;i<selectedRow.length;i++){
				if(selectedRow[i].checked){

					checkIndex=selectedRow[i].value;
					break;
					}
			}

		var invoiceNumber=document.getElementsByName('invoiceNumber');
		var invNo;
		if(invoiceNumber!=null && invoiceNumber.length>0){
			invNo=invoiceNumber[checkIndex].value;
		}
		var billingtype=targetFormName.elements.interlineBlgType.value;
		var blgtype;

		if(billingtype=='I'){
			blgtype="MI";
			}else{
			blgtype="MO";
			}
	var subsystem="M";
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listCN51s.viewAccDetails.do?functionPoint='+blgtype+'&invoiceNumber='+invNo+'&subSystem='+subsystem);
	//submitForm(targetFormName,'cra.accounting.listaccountingentrieslist.do?functionPoint='+"IB"+'&invoiceNumber='+invNo+'&invokingScreen='+"listcn51s");
	}
}
	//Added by A-7929 as part of ICRD-265471
	function FinalizeInvoice(){
	if(validateSelectedCheckBoxes(targetFormName,'tableRowId','',1)){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.listCN51s.finalizeInvoice.do');
	}
    }
	
	function invoiceLOV(){
	var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
        displayLOVCodeNameAndDesc('mra.airlinebilling.defaults.showInvoiceLov.do','N','Y','mra.airlinebilling.defaults.showInvoiceLov.do',targetFormName.invoiceNo.value,'Invoice Number','1','invoiceNo','clearancePeriod','airlineCode',0,_reqHeight);
}
	//Added by A-7929 as part of ICRD-265471 ends
	
	
	//Added by A-8464 as part of ICRD-235879
	function gatherFilterAttributes(reportId){
		
		var filters;
		if (reportId == 'MRASISRPT')  
		{ 
	    	filters = [
	    			'AA',
					targetFormName.elements.blgFromDateStr.value,
					targetFormName.elements.blgToDateStr.value,
					targetFormName.elements.airlineCode.value
					];
			  
		} 
		return filters;
	}



