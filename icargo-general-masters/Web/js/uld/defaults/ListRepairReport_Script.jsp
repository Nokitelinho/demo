<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
		var frm = targetFormName;
		

	   	if(frm.elements.uldNo.disabled==false){
			frm.elements.uldNo.focus();
		}
	    onScreenLoad();
   		with(frm){
   		evtHandler.addEvents("btList","list(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btUldDetails","uldDetailsScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btgenerateinvoice","generateInvoice(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","closeScreen(this.form)",EVT_CLICK);
		var chkBoxObj=document.getElementsByName("rowId");
					if(chkBoxObj!=null && chkBoxObj.length>0){
						evtHandler.addEvents("rowId","checkOne(this,targetFormName.masterRowId,'rowId')",EVT_CLICK);
					}else{
				
					}
		evtHandler.addEvents("masterRowId","checkAll(this,'rowId')",EVT_CLICK);

		if(frm.elements.check!=null){
			evtHandler.addEvents("check","check(this.form)",EVT_CLICK);
		}

		evtHandler.addEvents("masterRowId","masterRowId(this.form)",EVT_CLICK);
		evtHandler.addEvents("btprint","printScreen(this.form)",EVT_CLICK);



   		}
   	applySortOnTable("repairreporttable",new Array("None","Number","Number","String","String","String","Number","String","String","String","String"));

   	}


function onScreenLoad(){
	if(targetFormName.elements.listStatus.value!="N"){
		disableField(targetFormName.elements.btgenerateinvoice);
		disableField(targetFormName.elements.btUldDetails);
		//Added by Manaf for BUG 13588
		disableField(targetFormName.elements.btprint);
	}
	if(targetFormName.elements.repairDisableStatus.value=="airline"){
	}
	if(targetFormName.elements.repairDisableStatus.value=="GHA"){
		//targetFormName.currentStn.disabled = true;
		disableField(targetFormName.elements.currentStn);
		//targetFormName.airportLovImg.disabled = true;
		disableLink(targetFormName.elements.airportLovImg);
	}		   
}

function check(frm){

	toggleTableHeaderCheckbox('check', frm.elements.masterRowId);
}

function masterRowId(frm){

	updateHeaderCheckBox(frm, frm.elements.masterRowId, frm.elements.check);
}

function list(frm){

	if(frm.elements.repairedDateFrom.value != "" &&
		!chkdate(frm.elements.repairedDateFrom)){

		
		showDialog({msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.invalidfromdate"/>',type:1,parentWindow:self, parentForm:frm, 
                                                        dialogId:'id_1'})
		return;
	}
	if(frm.elements.repairedDateTo.value != "" &&
		!chkdate(frm.elements.repairedDateTo)){
	
		
		showDialog({msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.invalidtodate"/>',type:1,parentWindow:self, parentForm:frm, 
                                                        dialogId:'id_1'})
		return;
	}

	submitForm(frm,'uld.defaults.listrepairreport.do?countTotalFlag=YES');
}

function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	if(frm.elements.repairedDateFrom.value != "" &&
		!chkdate(frm.elements.repairedDateFrom)){
alert(1);
		
		showDialog({msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.invalidfromdate"/>',type:1,parentWindow:self, parentForm:frm, 
                                                        dialogId:'id_1'})
		return;
	}
	if(frm.elements.repairedDateTo.value != "" &&
		!chkdate(frm.elements.repairedDateTo)){

		
		showDialog({msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.invalidtodate"/>',type:1,parentWindow:self, parentForm:frm, 
                                                        dialogId:'id_1'})
		return;
	}

	targetFormName.elements.lastPageNumber.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.listrepairreport.do";
	targetFormName.submit();
	disablePage();
}
function clearScreen(frm){
 
 	submitFormWithUnsaveCheck('uld.defaults.clearrepairreports.do')
}

function printScreen(frm){
 	//Modified by A-7359 for ICRD-268766
 	generateReport(frm,'/uld.defaults.listrepairreport.printscreen.do?currencyValue='+frm.elements.currencyValue.value+'');
}

function damageHistoryScreen(frm){
 	submitForm(frm,'uld.defaults.damagehistoryscreen.do');
}

function viewDamageScreen(frm){
 	submitForm(frm,'uld.defaults.viewdamagescreen.do');
}

function uldHistoryScreen(frm){
 	submitForm(frm,'uld.defaults.uldhistoryscreen.do');
}

function uldDetailsScreen(frm){


	if(validateSelectedCheckBoxes(frm,'check',25,1)){

	var checked = frm.elements.check;
	var selectedRows = "";
	var selected = frm.elements.check;
	if(checked.value==0 && checked.checked == true ){

	selectedRows=frm.elements.uldNoTable.value;

	}

	var length= checked.length;


	if(length>1){
	var count=0;
	for(var i=0;i<length;i++){

	if(checked[i].checked == true ){
	var isPresent="";
	selected[count].value=frm.elements.uldNoTable[i].value;

    for(var j=0;j<count;j++){
    if(frm.elements.uldNoTable[i].value==selected[j].value)
	{
	isPresent="true";
    }
    }

    if(isPresent!="true"){
	if(selectedRows != "")
	selectedRows = selectedRows+","+frm.elements.uldNoTable[i].value;
	else if(selectedRows == "")
	selectedRows = frm.elements.uldNoTable[i].value;
	}
	count++;

	}

	}

	}
    submitForm(frm,'uld.defaults.detailuldinfo.do?uldNumbersSelected='+selectedRows+'&screenloadstatus=ListRepairReport');
   }
}

function generateInvoice(frm){
    if(validateSelectedCheckBoxes(frm,'check',25,1)){
    var checked = frm.check;
	var selectedRows = "";
	var selectedSeq = "";
	var selectedRepDate = "";
	var selected = frm.check;
	if(checked.value==0 && checked.checked == true ){
	if(frm.elements.currentStnTable.value!="null")
	{
		
		showDialog({
		msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.info.alreadyinvoiced"/>',
		type:1,
		parentWindow: self, 
		parentForm:frm, 
        dialogId:'id_1'})
		return;
	}
	selectedRows=frm.elements.uldNoTable.value;
	selectedSeq=frm.elements.repairHeadTable.value;
	selectedRepDate=frm.elements.repairDateTable.value;

	}

	var length= checked.length;


	if(length>1){
	var count=0;
	for(var i=0;i<length;i++){

	if(checked[i].checked == true ){
	if(frm.elements.currentStnTable[i].value!="null")
	{
	
	showDialog({msg:'<common:message bundle="listRepairReportResources" key="uld.defaults.info.alreadyinvoiced"/>',
	type:1,
		parentWindow:self, 
	parentForm:frm, 
		dialogId:'id_2'});
	return;
	}
	selected[count].value=frm.elements.uldNoTable[i].value;

    if(selectedRows != ""){
    selectedRows = selectedRows+","+frm.uldNoTable[i].value;
	selectedSeq = selectedSeq+","+frm.repairHeadTable[i].value;
	selectedRepDate=selectedRepDate+","+frm.repairDateTable[i].value;
    }
	else if(selectedRows == "")
    selectedRows = frm.elements.uldNoTable[i].value;

	if(selectedSeq == "") {

		selectedSeq = frm.elements.repairHeadTable[i].value;
	}
	if(selectedRepDate == "") {

		selectedRepDate = frm.elements.repairDateTable[i].value;
	}

    count++;

	}

	}

	}
    var txntype = "P";

	var strAction="uld.defaults.generateinvoicescreenload.do?uldNumbers="+selectedRows+"&txnRefNos="+selectedSeq+"&txnType="+txntype+"&txndates="+selectedRepDate;
        openPopUp(strAction,700,180);
    }
    }
function closeScreen(frm){

 	submitForm(frm,'uld.defaults.closeaction.do');

}

function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value=lastPg;
  targetFormName.elements.displayPage.value=displayPg;
  submitForm(targetFormName, appPath + '/operations.shipment.clearancelistinglistcommand.do');
}

function checkAll(parentbox,name){
	var chkBoxObj=document.getElementsByName(name);
	if(chkBoxObj!=null){
		var chkBoxLength = chkBoxObj.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			chkBoxObj[i].checked=parentbox.checked;
		}
	}
}

function checkOne(checkbox,parentObj,childbox){

	var childObjs=document.getElementsByName(childbox);
	var isAllCheck=true;
	if(checkbox.checked){
		var chkBoxLength = childObjs.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			if(!childObjs[i].checked)
			{
				isAllCheck=false;
				break;
			}
		}

	}else{
		isAllCheck=false;

	}
	if(isAllCheck){
		parentObj.checked=false;
		//checkAll(parentObj,"rowId");
	}else{
	parentObj.checked=false;
	}
}

function chkDate(date){

 	if(date.value==""){

 	return;
 	}
         else
 	{

 	checkdate(date,'DD-MMM-YYYY');
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
//Added by A-7359 for ICRD - 224586	 starts here
function callbackListRepair (collapse,collapseFilterOrginalHeight,mainContainerHeight){       
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               IC.util.widget.updateTableContainerHeight("#div1",+collapseFilterOrginalHeight);
}
//Added by A-7359 for ICRD - 224586	 ends here  