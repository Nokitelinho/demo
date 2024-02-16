<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
//window.onload=initForm;
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	disableLov();
	onScreenLoad();
	//onScreenloadSetHeight();
	with(frm){

	//CLICK Events
	evtHandler.addEvents("btnList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSpecifyRange","specifyRange(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClear","clear(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnSave","save(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnDelete","deleteUld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeUld(this.form)",EVT_CLICK);
	evtHandler.addEvents("btnClose","resetFocus(this.form)",EVT_BLUR);
	evtHandler.addEvents("createMultiple","createMultiple()",EVT_CLICK);
	evtHandler.addEvents("btnMovementHistory","loadMovtHistory(this.form)",EVT_CLICK);
	evtHandler.addEvents("facilitycodelov","showFacilityCode()",EVT_CLICK);
	evtHandler.addEvents("facilityType","locationChange()",EVT_CHANGE);
	evtHandler.addEvents("currentStation","locationChange()",EVT_BLUR);
	evtHandler.addEvents("uldNumber","validateFields(this, -1, 'Uld Number', 0, true, true, 10 , 2)",EVT_BLUR);
	evtHandler.addEvents("uldType","validateFields(this, -1, 'UldType', 0, true, true)",EVT_BLUR, 3 , 2);
	evtHandler.addEvents("ownerAirlineCode","validateFields(this, -1, 'OwnerAirlineCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldOwnerCode","validateFields(this, -1, 'uldOwnerCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("totalNoofUlds","validateFields(this, -1, 'TotalNoofUlds', 3, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldContour","validateFields(this, -1, 'UldContour', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("displayTareWeight","validateFields(this, -1, 'TareWeight', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("displayStructuralWeight","validateFields(this, -1, 'StructuralWeight', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("displayBaseLength","validateFields(this, -1, 'BaseLength', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("displayBaseWidth","validateFields(this, -1, 'BaseWidth', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("displayBaseHeight","validateFields(this, -1, 'BaseHeight', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("operationalAirlineCode","validateFields(this, -1, 'OperationalAirlineCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("operationalOwnerAirlineCode","validateFields(this, -1, 'operationalOwnerAirlineCode', 0, true, true)",EVT_BLUR);
	evtHandler.addEvents("currentStation","validateFields(this, -1, 'CurrentStation', 1, true, true)",EVT_BLUR);
	evtHandler.addEvents("ownerStation","validateFields(this, -1, 'OwnerStation', 1, true, true)",EVT_BLUR);
	//evtHandler.addEvents("location","validateFields(this, -1, 'Location', 12, true, true)",EVT_BLUR);
	//evtHandler.addEvents("vendor","validateFields(this, -1, 'Vendor', 12, true, true)",EVT_BLUR);
	//evtHandler.addEvents("manufacturer","validateFields(this, -1, 'Manufacturer', 12, true, true)",EVT_BLUR);
	//evtHandler.addEvents("uldSerialNumber","validateFields(this, -1, 'UldSerialNumber', 10, true, true)",EVT_BLUR);
	evtHandler.addEvents("purchaseInvoiceNumber","validateFields(this, -1, 'PurchaseInvoiceNumber', 10, true, true)",EVT_BLUR);
	evtHandler.addEvents("uldPrice","validateFields(this, -1, 'uldPrice', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("iataReplacementCost","validateFields(this, -1, 'IataReplacementCost', 2, true, true)",EVT_BLUR);
	evtHandler.addEvents("currentValue","validateFields(this, -1, 'CurrentValue', 2, true, true)",EVT_BLUR);

	evtHandler.addEvents("lifeSpan","validateFields(this, -1, 'Life Span', 3, true, true)",EVT_BLUR);
	evtHandler.addEvents("tsoNumber","validateFields(this, -1, 'TSO Number', 10, true, true)",EVT_BLUR);

    evtHandler.addEvents("operationOwnerairlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.operationalOwnerAirlineCode.value,'OwnerCodeAirline','1','operationalOwnerAirlineCode','',0)",EVT_CLICK);
    evtHandler.addEvents("operationairlineLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.operationalAirlineCode.value,'OwnerAirline','1','operationalAirlineCode','',0)",EVT_CLICK);
    evtHandler.addEvents("currentairportLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.currentStation.value,'CurrentAirport','1','currentStation','',0)",EVT_CLICK);
    evtHandler.addEvents("ownerairportLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.ownerStation.value,'OwnerAirport','1','ownerStation','',0)",EVT_CLICK);
    evtHandler.addEvents("manufacturerLov","displayLOV('uld.defaults.findmanufacturerlov.do','N','N','uld.defaults.findmanufacturerlov.do',targetFormName.manufacturer.value,'manufacturer','1','manufacturer','',0)",EVT_CLICK);

	evtHandler.addEvents("currencyLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[1].uldPriceUnit.value,'Currency','1','uldPriceUnit','',0)",EVT_CLICK);
	evtHandler.addEvents("iataReplacementCostUnitLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[1].iataReplacementCostUnit.value,'Currency','1','iataReplacementCostUnit','',0)",EVT_CLICK);
	evtHandler.addEvents("currentValueUnitLov","displayLOV('showCurrency.do','N','Y','showCurrency.do',document.forms[1].currentValueUnit.value,'Currency','1','currentValueUnit','',0)",EVT_CLICK);
	//Added by A-3415 for ICRD-113953
	evtHandler.addEvents("damageStatus","damageStatusChange()",EVT_CHANGE);



	}


}
function onScreenloadSetHeight(){
 	jquery('#pagediv').height(((document.body.clientHeight)*85)/100);
	jquery('#div1').height((((document.body.clientHeight)*0.79)-300));
	jquery('#div1').height(((document.body.clientHeight)*38)/100)
}

function resetFocus(frm){
    if(!event.shiftKey){	   
		if(!frm.elements.uldNumber.disabled
		      && frm.elements.screenloadstatus.value != 'screenload'){
		  frm.uldNumber.focus();
		}else{
			if(!frm.elements.createMultiple.disabled){
				frm.createMultiple.focus();
			}
		}
	 
	}
	
}

function locationChange() {
	if(targetFormName.elements.currentStation.defaultValue!=targetFormName.elements.currentStation.value)
	{
	targetFormName.elements.location.value="";
	targetFormName.elements.facilityType.value="";
	targetFormName.elements.facilityType.defaultValue="";

	}
	if(targetFormName.elements.facilityType.defaultValue!=targetFormName.elements.facilityType.value)
	{
	targetFormName.elements.location.value="";

	}
	if(targetFormName.elements.facilityType.value == "NIL"){
	targetFormName.elements.location.value="NIL";
	}
	targetFormName.elements.currentStation.defaultValue=targetFormName.elements.currentStation.value;
	for(var j = 0; j < targetFormName.elements.facilityType.length ; j++) {
			targetFormName.elements.facilityType.options[j].defaultSelected = targetFormName.elements.facilityType.options[j].selected;
		}

	//targetFormName.facilityType.defaultValue=targetFormName.facilityType.value;
}
//Added by A-3415 for ICRD-113953
function damageStatusChange() {
	var damageStatus = targetFormName.elements.damageStatus.value;
	if(targetFormName.elements.nonOperationalDamageCodes.value !=null && targetFormName.elements.nonOperationalDamageCodes.value.indexOf(damageStatus)>=0){
		targetFormName.elements.overallStatus.value="N";
		disableField(targetFormName.elements.overallStatus);
	}else{
		targetFormName.elements.overallStatus.value="O";
		enableField(targetFormName.elements.overallStatus);
	}
}
function damageStatusLoad() {
	var damageStatus = targetFormName.elements.damageStatus.value;
	if(targetFormName.elements.nonOperationalDamageCodes.value !=null && targetFormName.elements.nonOperationalDamageCodes.value.indexOf(damageStatus)>=0){
		targetFormName.elements.overallStatus.value="N";
		disableField(targetFormName.elements.overallStatus);
	}else{		
		enableField(targetFormName.elements.overallStatus);
	}
}



function disableLov() {
	var frm=targetFormName;
	if(frm.elements.currentStation.disabled) {
		disableField(frm.elements.currentairportLov) ;
		disableField(frm.elements.facilitycodelov) ;
	}
	if(frm.elements.ownerStation.disabled) {
		disableField(frm.elements.ownerairportLov);
	}

	if(frm.elements.manufacturer.disabled) {
		disableField(frm.elements.manufacturerLov);
	}
	if(frm.elements.operationalAirlineCode.disabled) {
		disableField(frm.elements.operationairlineLov);
	}
	//added by a-3278 for Bug 17074 on 28Aug08
	if(frm.elements.uldPriceUnit.disabled) {
		disableField(frm.elements.currencyLov);
	}
	if(frm.elements.iataReplacementCostUnit.disabled) {
		disableField(frm.elements.iataReplacementCostUnitLov);
	}
	if(frm.elements.currentValueUnit.disabled) {
		disableField(frm.elements.currentValueUnitLov);
	}
	//a-3278 ends
}
function loadMovtHistory(frm){

	//frm.uldNumber.disabled=false;
	var uldNumber = frm.elements.uldNumber.value;
	//frm.uldNumber.disabled=true;
	//added by T-1927 for the BUG icrd-30600
	if(targetFormName.elements.screenloadstatus.value == "listdetail"){
		var pageurl = "maintainuld";
		submitForm(frm,'uld.defaults.tomvthystoryscreenload.do?uldNumber='+uldNumber+'&screenloadstatus=listdetail'+'&displayPageFlag=1'+'&transactionFlag=Y'+'&pageUrl='+pageurl);
	}else if(targetFormName.elements.screenloadstatus.value == "LoanBorrow"){
		var pageurl = "maintainuld";
		submitForm(frm,'uld.defaults.tomvthystoryscreenload.do?uldNumber='+uldNumber+'&screenloadstatus=frommaintainuldlistdetail'+'&displayPageFlag=1'+'&transactionFlag=Y'+'&pageUrl='+pageurl);
	}else if(targetFormName.elements.screenloadstatus.value == "ListDamageReport"){
		var pageurl = "maintainuld";
		submitForm(frm,'uld.defaults.tomvthystoryscreenload.do?uldNumber='+uldNumber+'&screenloadstatus=ListDamageReport'+'&displayPageFlag=1'+'&transactionFlag=Y'+'&pageUrl='+pageurl);
	}else if(targetFormName.elements.screenloadstatus.value == "ListRepairReport"){
		var pageurl = "maintainuld";
		submitForm(frm,'uld.defaults.tomvthystoryscreenload.do?uldNumber='+uldNumber+'&screenloadstatus=ListRepairReport'+'&displayPageFlag=1'+'&transactionFlag=Y'+'&pageUrl='+pageurl);
	}else{
		var pageurl = "maintainuld";
		submitForm(frm,'uld.defaults.tomvthystoryscreenload.do?uldNumber='+uldNumber+'&screenloadstatus=frommaintainuld'+'&transactionFlag=Y'+'&pageUrl='+pageurl);
	}
}

function createMultiple() {
	var  frm = targetFormName;
	var checkbox = document.getElementsByName("createMultiple");
	if(frm.elements.createMultiple.checked == true) {
		frm.elements.closeStatus.value="canclear";
		frm.elements.closeStatus.defaultValue="canclear"
		targetFormName.elements.statusFlag.value ="check";
		//submitForm(frm,'uld.defaults.clearulddetails.do');
		frm.elements.createMultiple.value="Y";
		frm.elements.createMultiple.defaultValue="Y";
		frm.elements.uldNumber.value="";
		disableField(frm.elements.uldNumber);
		frm.elements.uldSerialNumber.value="";
		disableField(frm.elements.uldSerialNumber);
		enableField(frm.elements.uldType);
		frm.elements.uldType.focus();
		enableField(frm.elements.ownerAirlineCode);
		enableField(frm.elements.uldOwnerCode);
		 if(frm.elements.btnSpecifyRange.getAttribute("privileged") == 'Y'){
		   enableField(frm.elements.btnSpecifyRange);
		  }

		//frm.btnSpecifyRange.disabled=false;
		disableField(frm.btnList);
		disableField(frm.btnDelete);
	}
	else {

		if(frm.elements.totalNoofUlds.value != null && frm.elements.totalNoofUlds.value != "" ) {
				showDialog({msg:"<bean:message bundle='maintainuldResources' key='uld.defaults.multipleUld.uncheckConfirm' />",type:4,parentWindow:self,parentForm:frm,
							dialogId:'id_2',
						onClose:function(result){
			screenConfirmDialog(frm,'id_2');
			screenNonConfirmDialog(frm,'id_2');
		}
							});
			
		}
		else {
			submitForm(frm,'uld.defaults.clearulddetails.do?statusFlag=checked');
		}
	}
}

function confirmMessage() {

	var frm = targetFormName;
	if(frm.elements.closeStatus.value=="whethertoclose") {
		frm.elements.closeStatus.value="canclose";
		frm.elements.closeStatus.defaultValue="canclose";
		submitForm(frm,'uld.defaults.closeuld.do');
	}
	else if(frm.elements.closeStatus.value=="whethertoclear") {
		frm.elements.closeStatus.value="canclear";
		frm.elements.closeStatus.defaultValue="canclear";
		submitForm(frm,'uld.defaults.clearulddetails.do');
	}
	else {
		requestLock("uld.defaults.lockonlistmaintainuld.do");
		var uldNumber = targetFormName.elements.uldNumber.value;
		submitForm(frm,'uld.defaults.createnewuld.do?uldNumber='+uldNumber);
	}
}

function nonconfirmMessage() {
	var frm = targetFormName;
	if(frm.elements.closeStatus.value=="whethertoclear") {
		if(frm.elements.statusFlag.value=="checked") {
			frm.elements.createMultiple.checked = true;
			frm.elements.createMultiple.defaultChecked=true;
			frm.elements.closeStatus.value = "";
			frm.elements.closeStatus.value="";
			frm.elements.closeStatus.defaultValue="";
		}
	}
	else if(frm.elements.closeStatus.value=="whethertoclose") {
		frm.elements.closeStatus.value = "";
		frm.elements.closeStatus.defaultValue="";
	}else{
		frm.elements.createMultiple.value="N";
		frm.elements.createMultiple.defaultValue="N";
		disableField(frm.btnSpecifyRange);
		disableField(frm.btnSave);
		disableField(frm.btnDelete);
		enableField(frm.btnList);
		enableField(frm.uldNumber);
		enableField(frm.uldNumber);
		disableField(frm.uldType);
		disableField(frm.ownerAirlineCode);
		disableField(frm.uldOwnerCode);
		frm.elements.uldType.value="";
		frm.elements.ownerAirlineCode.value="";
		frm.elements.uldOwnerCode.value="";
		frm.elements.totalNoofUlds.value="";
		submitForm(frm,'uld.defaults.clearulddetails.do?closeStatus=canclear');
	}
}




function onScreenLoad(){

	var screenloadstatus = targetFormName.elements.screenloadstatus.value;
	var statusFlag = targetFormName.elements.statusFlag.value;
	var frm = targetFormName;

	//added by a-3045 for BUG_5750 starts
		//disableField(targetFormName.operationalAirlineCode);
		//disableField(targetFormName.operationairlineLov);
	//added by a-3045 for BUG_5750 ends

	if(frm.elements.createMultiple.checked == true){
		disableField(frm.btnDelete);
	}
	if(statusFlag=="createNewUld"){
			disableField(frm.btnDelete);
			disableField(frm.btnMovementHistory);
	}
	if(targetFormName.elements.transitStatus.value == "Y" &&
	   targetFormName.elements.transitDisable.value == "Y" ) {
		//alert("DISABLE ALL");
		disableField(targetFormName.displayTareWeight);
		//targetFormName.displayTareWeightUnit.disabled= true;
		disableField(targetFormName.displayStructuralWeight);
		disableField(targetFormName.displayStructuralWeightUnit);
		disableField(targetFormName.uldContour);
		disableField(targetFormName.displayBaseLength);
		disableField(targetFormName.displayBaseWidth);
		disableField(targetFormName.displayBaseHeight);
		disableField(targetFormName.displayDimensionUnit);

		disableField(targetFormName.operationalAirlineCode);
		disableField(targetFormName.currentStation);
		disableField(targetFormName.operationairlineLov);
		disableField(targetFormName.currentairportLov);
		disableField(targetFormName.ownerairportLov);
		disableField(targetFormName.ownerStation);
		disableField(targetFormName.overallStatus);
		disableField(targetFormName.cleanlinessStatus);
		disableField(targetFormName.damageStatus);
		disableField(targetFormName.facilityType);
		disableField(targetFormName.location);
		disableField(targetFormName.uldNature);
		disableField(targetFormName.facilitycodelov);

		disableField(targetFormName.vendor);
		disableField(targetFormName.manufacturer);
		disableField(targetFormName.manufacturerLov);
		disableField(targetFormName.uldSerialNumber);
		disableField(targetFormName.purchaseDate);
		disableField(targetFormName.purchaseInvoiceNumber);
		disableField(targetFormName.uldPrice);
		disableField(targetFormName.uldPriceUnit);
		disableField(targetFormName.iataReplacementCost);
		disableField(targetFormName.iataReplacementCostUnit);
		disableField(targetFormName.currentValue);
		disableField(targetFormName.currentValueUnit);
		disableField(targetFormName.btn_purchaseDate);
		// Added by Nishanth to enable/disable  fields for Bug 10146 starts
		disableField(targetFormName.lifeSpan);
		disableField(targetFormName.tsoNumber);
		//added by a-3278 for Bug 17074 on 28Aug08
		disableField(targetFormName.currencyLov);
		disableField(targetFormName.iataReplacementCostUnitLov);
		disableField(targetFormName.currentValueUnitLov);
		//a-3278 ends

	}
	if(statusFlag == "check") {
		//targetFormName.createMultiple.value="Y";
		targetFormName.elements.createMultiple.checked = true;
		targetFormName.elements.createMultiple.defaultChecked=true;
		targetFormName.elements.statusFlag.value="";
		targetFormName.elements.uldType.focus();
	}

	else if(!frm.uldNumber.disabled) {
		frm.elements.uldNumber.focus();
	}
	else {
		/*if(!frm.displayTareWeight.disabled) {
		frm.displayTareWeight.focus();
		}*/
		if(!frm.createMultiple.disabled){
			frm.elements.createMultiple.focus();
		}
	}
	disableField(frm.totalNoofUlds);
	// Added by Nisha to enable/disable currentvalue field starts
	if(statusFlag=="createNewUld"){
		disableField(frm.currentValue);
		disableField(frm.currentValueUnit);
		disableField(frm.currentValueUnitLov);
	}/*else{
		frm.currentValue.disabled=false;
		frm.currentValueUnit.disabled=false;
		frm.currentValueUnitLov.disabled=false;
	}*/
	// Added by Nisha to enable/disable currentvalue field ends
	frm.totalNoofUlds.tabindex=-1;

	if(screenloadstatus == "frommaintainuldlistdetail"){
		disableField(frm.btnDelete);
	}
	if(screenloadstatus == "listdetail" ||
		screenloadstatus == "ListRepairReport" ||
		screenloadstatus == "ListDamageReport" ||
		screenloadstatus == "LoanBorrow" ) {

			disableField(frm.btnDelete);
		if(statusFlag != "save") {
			disableField(frm.createMultiple);
			disableField(frm.btnSpecifyRange);
			disableField(frm.btnList);
			disableField(frm.btnClear);
			disableField(frm.uldNumber);
			disableField(frm.uldType);
			disableField(frm.ownerAirlineCode);
			disableField(frm.uldOwnerCode);
		}
		//else {
		//	targetFormName.screenloadstatus.value = "listcreate";
		//}


	}
	else {

		if(frm.createMultiple.checked == false) {

			disableField(frm.uldType);
			disableField(frm.ownerAirlineCode);
			disableField(frm.btnSpecifyRange);
			disableField(frm.uldOwnerCode);
			frm.elements.uldType.tabindex=-1;
			frm.elements.ownerAirlineCode.tabindex=-1;
			frm.elements.uldOwnerCode.tabindex=-1;
		}
		else {
			frm.elements.uldSerialNumber.value="";
			disableField(frm.uldSerialNumber);
			frm.elements.uldNumber.value="";
			disableField(frm.uldNumber);
			if(frm.elements.totalNoofUlds.value != "") {
				disableField(frm.uldType);
				disableField(frm.ownerAirlineCode);
				disableField(frm.uldOwnerCode);	
			}
			else {
				enableField(frm.uldType);
				enableField(frm.ownerAirlineCode);
				enableField(frm.uldOwnerCode);	
			}
			if(frm.elements.btnSpecifyRange.getAttribute("privileged") == 'Y'){
			   enableField(frm.btnSpecifyRange);
		  	}
			disableField(frm.btnList);
			var statusFlag = targetFormName.elements.statusFlag.value;
			if(statusFlag == "toPopup") {
				var uldType = targetFormName.elements.uldType.value;
				var createMultiple = targetFormName.elements.createMultiple.value;
				var ownerAirlineCode = targetFormName.elements.ownerAirlineCode.value;
				var uldOwnerCode = targetFormName.elements.uldOwnerCode.value;
				var structuralStatus = targetFormName.elements.structuralFlag.value;
				targetFormName.elements.statusFlag.value = "";
				openPopUp("uld.defaults.screenloadmultipleulds.do?uldType="+uldType+"&ownerAirlineCode="+ownerAirlineCode+"&uldOwnerCode="+uldOwnerCode+"&createMultiple="+createMultiple+"&structuralFlag="+structuralStatus,
				440,340);

			}
		}

			if(statusFlag != "createNewUld"){
			disableField(targetFormName.elements.operationalAirlineCode)
			disableField(targetFormName.elements.operationalOwnerAirlineCode)
			disableField(targetFormName.operationOwnerairlineLov)
			disableField(targetFormName.operationairlineLov)
			}
	}
	damageStatusLoad();
}

function NavigateZoneDetails(strLastPageNum, strDisplayPage) {

	if(targetFormName.elements.location.value!=""){
	if(targetFormName.elements.currentStation.value=="" &&
	   targetFormName.elements.facilityType.value==""){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterAirportnFacility' scope='request'/>",
				type:1,
				parentWindow:self});
	return;
	}
	if(targetFormName.elements.facilityType.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterFacilityType' scope='request'/>",
				type:1,
				parentWindow:self});
		return;
	}
	if(targetFormName.elements.currentStation.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterCurrentAirport' scope='request'/>",
				type:1,
				parentWindow:self});
		return;
	}}
	if(targetFormName.elements.facilityType.value!=""){

	if(targetFormName.elements.location.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterLocation' scope='request'/>",
				type:1,
				parentWindow:self});
		return;
	}}


	var frm = targetFormName;
	var uldNumber = targetFormName.elements.uldNumber.value;

    	frm.elements.lastPageNum.value = strLastPageNum;
    	frm.elements.displayPage.value = strDisplayPage;
    	//frm.action = "uld.defaults.navigateuld.do";
        //frm.submit();
      submitForm(frm,'uld.defaults.navigateuld.do?uldNumber='+uldNumber);

  }

function screenConfirmDialog(frm, dialogId) {


	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'uld.defaults.deleteuld.do');
		}
		if(dialogId == 'id_2') {
			frm.elements.createMultiple.value="N";
			frm.elements.createMultiple.defaultValue="N";
			disableField(frm.elements.btnSpecifyRange);
			disableField(frm.elements.btnSave);
			disableField(frm.elements.btnDelete);
			enableField(frm.elements.btnList);
			enableField(frm.elements.uldNumber);
			enableField(frm.elements.uldNumber);
			disableField(frm.elements.uldType);
			disableField(frm.elements.ownerAirlineCode);
			disableField(frm.elements.uldOwnerCode);	
			frm.elements.uldType.value="";
			frm.elements.ownerAirlineCode.value="";
			frm.elements.uldOwnerCode.value="";
			frm.elements.totalNoofUlds.value="";
			submitForm(frm,'uld.defaults.clearulddetails.do?closeStatus=canclear');

		}

	}
}

function screenNonConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}

	}
}

function list(frm){

	submitForm(frm,'uld.defaults.listulddetails.do');
}

function clear(frm){
	//submitForm(frm,'uld.defaults.clearulddetails.do');
	submitFormWithUnsaveCheck('uld.defaults.clearulddetails.do');
}


function save(frm){

	if(targetFormName.elements.location.value!=""){
	if(targetFormName.elements.currentStation.value=="" &&
	   targetFormName.elements.facilityType.value==""){
	showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterAirportnFacility' scope='request'/>",
					type:1,
					parentWindow:self});	
	return;
	}
	if(targetFormName.elements.facilityType.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterFacilityType' scope='request'/>",
					type:1,
					parentWindow:self});		
		return;
	}
	if(targetFormName.elements.currentStation.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterCurrentAirport' scope='request'/>",
					type:1,
					parentWindow:self});
		return;
	}}
	if(targetFormName.elements.facilityType.value!=""){

	if(targetFormName.elements.location.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterLocation' scope='request'/>",
					type:1,
					parentWindow:self});
		return;
	}}

	if(targetFormName.elements.statusFlag.value!="createNewUld" && targetFormName.elements.statusFlag.value!="refresh" && targetFormName.elements.statusFlag.value!=""){
	 if(targetFormName.elements.currentValue.value=="" ){
	 	showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterCurrentValue' scope='request'/>",
					type:1,
					parentWindow:self});
	 	return;
	}

	}
	 /* Added by A-3154 */
	         var tareWeight =targetFormName.elements.displayTareWeight.value;
		 		if(tareWeight.length != 0){
		 		if(tareWeight.indexOf(".") < 0){
		 					 if(tareWeight.length > 7){
		 						targetFormName.displayTareWeight.focus();
								showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidTareWt' scope='request'/>",
											type:1,
											parentWindow:self});
		 						return;
		 					 }
		 			}else{
		 			 	 if(tareWeight.indexOf(".") > 7){
		 					targetFormName.displayTareWeight.focus();
		 					showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidTareWt' scope='request'/>",
											type:1,
											parentWindow:self});
		 					return;
		 			 	}
		 				if(tareWeight.length > 10){
		 			   	targetFormName.displayTareWeight.focus();
						showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidTareWt' scope='request'/>",
											type:1,
											parentWindow:self});
		 				return;
		 		 		}

		 	         	}
	         }
			 var stWeight;
			 //Added by A-7978 for ICRD-238863
			 if(targetFormName.elements.displayStructuralWeight!=null){
	          stWeight = targetFormName.elements.displayStructuralWeight.value;
					if(stWeight.length != 0){
					if(stWeight.indexOf(".") < 0){
								 if(stWeight.length > 7){
									targetFormName.displayStructuralWeight.focus();
									showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidStructuralWt' scope='request'/>",
											type:1,
											parentWindow:self});
									return;
								 }
						}else{
							 if(stWeight.indexOf(".") > 7){
								targetFormName.displayStructuralWeight.focus();
								showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidStructuralWt' scope='request'/>",
											type:1,
											parentWindow:self});
								return;
							}
							if(tareWeight.length > 10){
							targetFormName.displayStructuralWeight.focus();
							showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidStructuralWt' scope='request'/>",
											type:1,
											parentWindow:self});
							return;
							}

						}
						}
	         }
	          var baseLength =targetFormName.elements.displayBaseLength.value;
					if(baseLength.length != 0){
					if(baseLength.indexOf(".") < 0){
								 if(baseLength.length > 5){
									targetFormName.displayBaseLength.focus();
									showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseLength' scope='request'/>",
											type:1,
											parentWindow:self});
									return;
								 }
						}else{
							 if(baseLength.indexOf(".") > 5){
								targetFormName.displayBaseLength.focus();
								showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseLength' scope='request'/>",
											type:1,
											parentWindow:self});
								return;
							}
							if(baseLength.length > 8){
							targetFormName.displayBaseLength.focus();
							showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseLength' scope='request'/>",
											type:1,
											parentWindow:self});
							return;
							}

						}
	         }
		var baseWidth =targetFormName.elements.displayBaseWidth.value;
					if(baseWidth.length != 0){
					if(baseWidth.indexOf(".") < 0){
								 if(baseWidth.length > 5){
									targetFormName.displayBaseWidth.focus();
									showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseWidth' scope='request'/>",
											type:1,
											parentWindow:self});
									return;
								 }
						}else{
							 if(baseWidth.indexOf(".") > 5){
								targetFormName.displayBaseWidth.focus();
								showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseWidth' scope='request'/>",
											type:1,
											parentWindow:self});
								return;
							}
							if(baseWidth.length > 8){
							targetFormName.displayBaseWidth.focus();
							showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseWidth' scope='request'/>",
											type:1,
											parentWindow:self});
							return;
							}

						}
	         }
		var baseHeight =targetFormName.elements.displayBaseHeight.value;
					if(baseHeight.length != 0){
					if(baseHeight.indexOf(".") < 0){
								 if(baseHeight.length > 5){
									targetFormName.displayBaseHeight.focus();
									showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseHeight' scope='request'/>",
											type:1,
											parentWindow:self});
									return;
								 }
						}else{
							 if(baseHeight.indexOf(".") > 5){
								targetFormName.displayBaseHeight.focus();
								showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseHeight' scope='request'/>",
											type:1,
											parentWindow:self});
								return;
							}
							if(baseHeight.length > 8){
							targetFormName.displayBaseHeight.focus();
							showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidBaseHeight' scope='request'/>",
											type:1,
											parentWindow:self});
							return;
							}

						}
	         }
/* Added by A-3154 ends */
	/* Added by A-2412 */
		var uldPrice =targetFormName.elements.uldPrice.value;
		if(uldPrice.length != 0){
		if(uldPrice.indexOf(".") < 0){
					 if(uldPrice.length > 10){
						targetFormName.uldPrice.focus();
						showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldPrice' scope='request'/>",
											type:1,
											parentWindow:self});
						return;
					 }
			}else{
			 	 if(uldPrice.indexOf(".") > 10){
					targetFormName.uldPrice.focus();
					showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldPrice' scope='request'/>",
									type:1,
									parentWindow:self});
					return;
			 	}
				if(uldPrice.length > 13){
			   	targetFormName.uldPrice.focus();
				showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldPrice' scope='request'/>",
								type:1,
								parentWindow:self}); 
				return;
		 		}

	         	}
	         }
	         var iataReplacementCost =targetFormName.iataReplacementCost.value;
		 if(iataReplacementCost.length != 0){
		 		 if(iataReplacementCost.indexOf(".") < 0){
		 				 if(iataReplacementCost.length > 10){
		 					targetFormName.iataReplacementCost.focus();
							showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidIATACost' scope='request'/>",
											type:1,
											parentWindow:self});
		 					return;
		 				 }
		 		}else{
		 		 	 if(iataReplacementCost.indexOf(".") > 10){
		 				targetFormName.iataReplacementCost.focus();
		 				showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidIATACost' scope='request'/>",
											type:1,
											parentWindow:self}); 
		 				return;
		 		 	}
		 			if(iataReplacementCost.length > 13){
		 		   	targetFormName.iataReplacementCost.focus();
		 			showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidIATACost' scope='request'/>",
											type:1,
											parentWindow:self});; 
		 			return;
		 	 		}

		          	}
	 	}
		var currentValue =targetFormName.elements.currentValue.value;
		if(currentValue.length != 0){
		if(currentValue.indexOf(".") < 0){
					 if(currentValue.length > 10){
						targetFormName.currentValue.focus();
						showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldValue' scope='request'/>",
											type:1,
											parentWindow:self});
						return;
					 }
			}else{
			 	 if(currentValue.indexOf(".") > 10){
					targetFormName.currentValue.focus();
					showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldValue' scope='request'/>",
											type:1,
											parentWindow:self});
					return;
			 	}
				if(currentValue.length > 13){
			   	targetFormName.currentValue.focus();
				showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.invalidUldValue' scope='request'/>",
											type:1,
											parentWindow:self});
		 			return;
		 	 		}

		          	}
	       	}
	/* Addition by A-2412 ends */



	var uldNumber = targetFormName.elements.uldNumber.value;
	var uldType = frm.elements.uldType.value;
	var ownerAirlineCode = frm.elements.ownerAirlineCode.value;
	//since screen does not have unit selection displayTareWeightUnit will be displayStructuralWeightUnit
	//targetFormName.elements.displayTareWeightUnit.value = targetFormName.elements.displayStructuralWeightUnit.value;
	var uldOwnerCode = frm.elements.uldOwnerCode.value;
	submitForm(frm,'uld.defaults.saveuld.do?uldNumber='+uldNumber+'&uldType='+uldType+'&ownerAirlineCode='+ownerAirlineCode+'&uldOwnerCode='+uldOwnerCode);


}

function deleteUld(frm){

	/*showDialog('<bean:message bundle="maintainuldResources" key="uld.defaults.multipleUld.deleteConfirm" />', 4, self, frm, 'id_1');
	screenConfirmDialog(frm,'id_1');
	screenNonConfirmDialog(frm,'id_1');*/
	showDialog({msg:"<bean:message bundle="maintainuldResources" key="uld.defaults.multipleUld.deleteConfirm" />",type:4,parentWindow:self,parentForm:frm,
		dialogId:'id_1',onClose:function(result){
	screenConfirmDialog(frm,'id_1');
	screenNonConfirmDialog(frm,'id_1');

		}
		});
}


//Added for Pessimistic Locking starts
////////////////////////////////////////////////////////////////////////
function requestLock(lockaction){



	var forRequest = "taken_request";

	var lockRequest = new	 LockRequest(lockaction,
					   {

					   },
					   {
					   	statusFlag:forRequest
					   },
					   {
					   }
					  );
	lockRequest.sendRequest();
}
//////////////////////////////////////////
//Added for Pessimistic Locking ends






function closeUld(frm){
//alert("from close");
	//submitForm(frm,'uld.defaults.closeuld.do');
	submitFormWithUnsaveCheck('uld.defaults.closeuld.do');
}


function specifyRange(frm){
	/*var uldType = frm.uldType.value;
	var ownerAirlineCode = frm.ownerAirlineCode.value;
	var uldOwnerCode = frm.uldOwnerCode.value;

	var myWindow = window.open("uld.defaults.screenloadmultipleulds.do?uldType="+uldType+"&ownerAirlineCode="+ownerAirlineCode+"&uldOwnerCode="+uldOwnerCode,
	"POPUPPAGE",'scrollbars,width=400,height=350,screenX=100,screenY=30,left=250,top=100');
 	*/
 	var createMultiple = frm.elements.createMultiple.value;
 	submitForm(frm,'uld.defaults.validatebeforepopup.do?createMultiple='+createMultiple);

}

//initForm();

function showFacilityCode(){
	if(targetFormName.elements.currentStation.value=="" &&
	   targetFormName.elements.facilityType.value==""){
	showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterAirportnFacility' scope='request'/>",
				type:1,
				parentWindow:self});
	return;
	}
	if(targetFormName.elements.facilityType.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterFacilityType' scope='request'/>",
				type:1,
				parentWindow:self});
		return;
	}
	if(targetFormName.elements.currentStation.value=="" ){
		showDialog({msg:"<common:message bundle='maintainuldResources' key='uld.defaults.multipleUld.enterCurrentAirport' scope='request'/>",
				type:1,
				parentWindow:self});
		return;
	}
	targetFormName.elements.currentStation.defaultValue=targetFormName.elements.currentStation.value;
	targetFormName.elements.facilityType.defaultValue=targetFormName.elements.facilityType.value;
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.currentStation.value;
	var facilityTypeForLov=targetFormName.elements.facilityType.value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";

	var StrUrl=strAction+"?textfiledObj=location&formNumber=1&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	var myWindow = openPopUpWithHeight(StrUrl, "500");
}
