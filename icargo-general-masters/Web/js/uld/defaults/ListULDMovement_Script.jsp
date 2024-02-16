<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	onScreenLoad();
	with(frm){
	//CLICK Events
	evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btRecord","recordMvt()",EVT_CLICK);
	evtHandler.addEvents("btReport","onClickPrint(this.form)",EVT_CLICK);
	evtHandler.addEvents("btDelete","deleteMvt(this.form)",EVT_CLICK);
	evtHandler.addEvents("uldNumber","validateFields(this, -1, 'ULDNo', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("movement","populateMovement()",EVT_CHANGE);
	evtHandler.addEvents("btHistory","ActionHistory()",EVT_CLICK);
	}
	applySortOnTable("movementdetails",new Array("None","String","String","String","String","Date","String"));
	applySortOnTable("listuldtable",new Array("None","String","String","String","String","Date","String"));
	applySortOnTable("Int",new Array("Date","String","String","String","String"));
	applySortOnTable("Txn",new Array("Date","String","String","String","Number","Date","String","Number","Number","String","String"));
	applySortOnTable("damagedetails",new Array("String","String","Date","String","String","Date","String","Number","Number","String","String"));
	applySortOnTable("break",new Array("Date","String","None","String"));
		
}

function list(frm){
	if(frm.elements.listStatus.value==""){
			//frm.btHistory.disabled = true;
			disableField(frm.btHistory);
	}
	submitForm(frm,'uld.defaults.misc.externallistuldmovement.do');
}

function onScreenLoad(){
	var frm = targetFormName;

	if(frm.elements.isUldValid.value=="N"){
		//frm.btHistory.disabled = true;
		disableField(frm.btHistory);
		//frm.movement.disabled = true;
		disableField(frm.movement);
		frm.elements.uldNumber.readOnly=false;
		frm.elements.toDate.readOnly=false;
		frm.elements.fromDate.readOnly=false;
		//frm.elements.btn_toDate.disabled=false;
		enableField(frm.btn_toDate);
		//frm.btn_fromDate.disabled=false;
		enableField(frm.btn_fromDate);
	}else{
		//frm.btHistory.disabled = false;
		enableField(frm.btHistory);
		//frm.movement.disabled = false;
		enableField(frm.movement);
		frm.elements.uldNumber.readOnly=true;
		frm.elements.toDate.readOnly=true;
		frm.elements.fromDate.readOnly=true;
		//frm.btn_toDate.disabled=true;
		disableField(frm.btn_toDate);
		//frm.btn_fromDate.disabled=true;
		disableField(frm.btn_fromDate);
	}

	if(!frm.elements.uldNumber.readOnly) {
		frm.elements.uldNumber.focus();
	}
	if(targetFormName.elements.transactionFlag.value=="Y"){
		//frm.btHistory.disabled = false;
		enableField(frm.btHistory);
		populateMovement();
	}
}

function clearScreen(frm){
	targetFormName.elements.transactionFlag.value="N";
	targetFormName.elements.noOfMovements.value="";
	targetFormName.elements.noOfLoanTxns.value="";
	targetFormName.elements.noOfTimesDmged.value="";
	targetFormName.elements.noOfTimesRepaired.value="";
	submitForm(frm,'uld.defaults.misc.clearuldmovement.do');
	//submitFormWithUnsaveCheck('uld.defaults.misc.clearuldmovement.do');
}
//added by jisha for QF1022


function recordMvt() {
	var frm=targetFormName;
	var uldNumber=frm.elements.uldNumber.value;
	var listmvt = "listUldMvt";
	var strAction = "uld.defaults.misc.screenloadrecorduldmovement.do?screenName="+listmvt+"&uldNumbers="+uldNumber;
	openPopUp(strAction,950,620);
}

function ActionHistory() {
	var frm=targetFormName;
	var uldNumber=frm.elements.uldNumber.value;
	var listmvt = "listUldMvt";
	var strAction = "uld.defaults.findULDActionHistory.do?screenName="+listmvt+"&uldNumber="+uldNumber;
	openPopUp(strAction,600,400);
}
function closeScreen()
{
	var frm = targetFormName;
	submitForm(frm,'uld.defaults.misc.closeuldmovement.do');
}

function onClickPrint(frm) {
 	var frm =  targetFormName;
	frm.elements.uldNumber.readOnly=false;
	frm.elements.fromDate.readOnly=false;
	frm.elements.toDate.readOnly=false;
	//generateReport(frm,"/uld.defaults.uldmvthistory.doReport.do");
	if(frm.elements.movement.value=="External Mvmts"){
		generateReport(frm,'/uld.defaults.uldmvthistory.generateextmvtreport.do');
	}else if(targetFormName.elements.movement.value=="Internal Mvmts"){
		generateReport(frm,"/uld.defaults.uldmvthistory.generateintmvtreport.do");
	}else if(targetFormName.elements.movement.value=="Transaction Dtls"){
		generateReport(frm,'/uld.defaults.misc.uldmvthistory.generatetxndetails.do');
	}else if(targetFormName.elements.movement.value=="Damage/Repair Dtls"){
		generateReport(frm,"/uld.defaults.misc.mvthistory.report.printdamagerepairdetails.do");
	}else if(targetFormName.elements.movement.value=="BulidUp/BreakDown Dtls"){
		 generateReport(frm,"/uld.defaults.uldmvthistory.buildUpReport.do");
	}

}


function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value = lastPg;
  targetFormName.elements.displayPage.value = displayPg;
  //submitForm(targetFormName, appPath + '/uld.defaults.misc.listintuldmovement.do');
  populateMovement();
}


function confirmMessage() {
}

function nonconfirmMessage() {
}

function deleteMvt(frm){
	var frm =  targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 100, 1);
	if (check){
		var selectedRows = document.getElementsByName('selectedRows');
		submitForm(frm,'uld.defaults.misc.deleteuldmovementhistory.do');
	}
}

function populateMovement(){
	 /* 
	  * screenloadstatus.value=="frommaintainuld" if MaintainULD > ULDHistory 
      * screenloadstatus.value=="frommaintainuldlistdetail" if LOANBORROWDetails Enquiry > MaintainULD > ULDHistory 
	  * screenloadstatus.value=="listdetail" if LISTULD > MaintainULD > ULDHistory
	  * screenloadstatus.value=="ListDamageReport" if ListDamageReport > MaintainULD > ULDHistory
	  * screenloadstatus.value=="ListRepairReport" if ListRepairReport > MaintainULD > ULDHistory
      */
	  //modified by T-1927 for the BUG icrd-30600
	if((targetFormName.elements.screenloadstatus.value=="frommaintainuld" 
	      || targetFormName.elements.screenloadstatus.value=="frommaintainuldlistdetail"
		  || targetFormName.elements.screenloadstatus.value=="listdetail"
		  || targetFormName.elements.screenloadstatus.value == "ListDamageReport"
		  || targetFormName.elements.screenloadstatus.value == "ListRepairReport")
		  && targetFormName.elements.movementStatusFlag.value==""){
		 //targetFormName.elements.screenloadstatus.value = targetFormName.elements.screenloadstatus.value+"_movementsuccess";
		targetFormName.elements.movementStatusFlag.value ="Y";
	submitForm(targetFormName,'uld.defaults.misc.externallistuldmovement.do');
}
if(targetFormName.elements.movement.value=="External Mvmts"){
	//targetFormName.btRecord.disabled=false;
	enableField(targetFormName.btRecord);
		recreateULDMvmtDetails('uld.defaults.misc.listuldmovement.do','uldHistoryContents');
		//submitForm(targetFormName,'uld.defaults.misc.listuldmovement.do');
	}
	else if(targetFormName.elements.movement.value=="Internal Mvmts")
	{
		//targetFormName.btRecord.disabled=true;
		disableField(targetFormName.btRecord);
	recreateULDMvmtDetails('uld.defaults.misc.listintuldmovement.do','uldHistoryContents');
	
	}
	else if(targetFormName.elements.movement.value=="Transaction Dtls")
	{
		//targetFormName.btRecord.disabled=true;
		disableField(targetFormName.btRecord);
	recreateULDMvmtDetails('uld.defaults.misc.listuldtrns.do','uldHistoryContents');
	}

	else if(targetFormName.elements.movement.value=="Damage/Repair Dtls")
	{
		//targetFormName.btRecord.disabled=true;
		disableField(targetFormName.btRecord);
	recreateULDMvmtDetails('uld.defaults.listdamagerepairdetails.do','uldHistoryContents');
	//submitForm(targetFormName,'uld.defaults.listdamagerepairdetails.do');
	}
	else if(targetFormName.elements.movement.value=="BulidUp/BreakDown Dtls")
	{
		//targetFormName.btRecord.disabled=true;
		disableField(targetFormName.btRecord);
	recreateULDMvmtDetails('uld.defaults.listbuildupbreakdowndetails.do','uldHistoryContents');
	//submitForm(targetFormName,'uld.defaults.listbuildupbreakdowndetails.do');
	}
}
 var _tableDivId = "";
 function recreateULDMvmtDetails(strAction,divId){
 	var __extraFn="update";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}
 function update(_tableInfo){
		if(targetFormName.elements.movement.value=="External Mvmts")
		{
		_exter=_tableInfo.document.getElementById('_exter');
		document.getElementById(_tableDivId).innerHTML=_exter.innerHTML;
		applySortOnTable("listuldtable",new Array("String","Date","String","String","String","String","Date","String"));
		}
		else if(targetFormName.elements.movement.value=="Internal Mvmts")
		{
		_divmain1=_tableInfo.document.getElementById('_divmain1');
		document.getElementById(_tableDivId).innerHTML=_divmain1.innerHTML;
		applySortOnTable("Int",new Array("Date","String","String","String","String"));
		}
		else if(targetFormName.elements.movement.value=="Transaction Dtls")
		{
		_transaction=_tableInfo.document.getElementById('_transaction');
		document.getElementById(_tableDivId).innerHTML=_transaction.innerHTML;
		applySortOnTable("Txn",new Array("Date","String","String","String","Number","Date","String","Number","Number","String","String"));
		}
		else if(targetFormName.elements.movement.value=="Damage/Repair Dtls")
		{
		_damage=_tableInfo.document.getElementById('_damage');
		document.getElementById(_tableDivId).innerHTML=_damage.innerHTML;
		applySortOnTable("damagedetails",new Array("String","String","Date","String","String","Date","String","Number","Number","String","String"));
		}
		else if (targetFormName.elements.movement.value=="BulidUp/BreakDown Dtls")
		{
		_build=_tableInfo.document.getElementById('_build');
		document.getElementById(_tableDivId).innerHTML=_build.innerHTML;
		applySortOnTable("break",new Array("Date","String","String","String","String"));
		}
}




