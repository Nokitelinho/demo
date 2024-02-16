<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>



function screenSpecificEventRegister()
{
   	with(targetFormName){

	//CLICK Events

	targetFormName.elements.billingSiteCode.focus();
	evtHandler.addEvents("btDisplay","display()",EVT_CLICK);
	evtHandler.addEvents("btSave","save()",EVT_CLICK);
	evtHandler.addEvents("btClear","clear()",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btDelete","del()",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addTemplateRow('billingsiteCountryTemplateRow','gpaCountriesTable','hiddenOpFlagForCountry')",EVT_CLICK);
	evtHandler.addIDEvents("add","addTemplateRow('billingsiteBankTemplateRow','bankDetailsTable','hiddenOpFlagForBank')",EVT_CLICK);
	evtHandler.addIDEvents("delLink","deleteTableRow('checkForCountry','hiddenOpFlagForCountry')",EVT_CLICK);
	evtHandler.addIDEvents("del","deleteTableRow('checkForBank','hiddenOpFlagForBank')",EVT_CLICK);
	evtHandler.addEvents("gpaCountryCodelov","showGpaCountryCodelov(this)",EVT_CLICK);
	evtHandler.addEvents("currencylov","showCurrencylov(this)",EVT_CLICK);
	evtHandler.addEvents("billingsitelov","displayLOV('mailtracking.mra.defaults.billingsitemaster.showBillingSiteLOVDetails.do','N','Y','mailtracking.mra.defaults.billingsitemaster.showBillingSiteLOVDetails.do',targetFormName.billingSiteCode.value,'billingSiteCode','1','billingSiteCode','billingSite',0)",EVT_CLICK);
	}
	onScreenLoad();

}

function onScreenLoad(){
	if(targetFormName.elements.checkFlag.value=='List'){
		disableField(document.getElementById('billingSiteCode'));
		disableField(document.getElementById('billingSite'));
		disableField(document.getElementById('billingsitelov'));
		disableField(document.getElementById('fromDate'));
		disableField(document.getElementById('btn_fromDate'));
		if(targetFormName.elements.siteExpired.value=='YES')
			disableField(document.getElementById('btDelete'));
	}
	if(targetFormName.elements.siteExpired.value=='YES')
			disableField(document.getElementById('btDelete'));
	if(targetFormName.elements.status.value=='UnSave'){
		disableField(document.getElementById('btDelete'));
	}
	if(targetFormName.elements.status.value=='Screenload'){
		disableField(targetFormName.elements.btSave);
		disableField(targetFormName.elements.btDelete);
		disableField(document.getElementById('btn_fromDate'));
		disableField(document.getElementById('btn_toDate'));
		disableField(document.getElementById('fromDate'));
		disableField(document.getElementById('toDate'));
		disableField(document.getElementById('country'));
		disableField(document.getElementById('address'));
		disableField(document.getElementById('bank'));
		disableField(document.getElementById('signator'));
		disableField(document.getElementById('freeText'));
		disableField(document.getElementById('countries'));
		disableField(document.getElementById('addLink'));
		disableField(document.getElementById('delLink'));
		disableField(document.getElementById('add'));
		disableField(document.getElementById('del'));
	}
}


function display(){
submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.listcommand.do');
}

function confirmMessage() {
	enableField(targetFormName.elements.btSave);
	enableField(document.getElementById('btn_fromDate'));
	enableField(document.getElementById('btn_toDate'));
	enableField(document.getElementById('fromDate'));
	enableField(document.getElementById('toDate'));
	enableField(document.getElementById('country'));
	enableField(document.getElementById('address'));
	enableField(document.getElementById('bank'));
	enableField(document.getElementById('countries'));
	enableField(document.getElementById('signator'));
	enableField(document.getElementById('freeText'));
	enableField(document.getElementById('airlineAddress'));
	enableField(document.getElementById('correspondenceAddress'));
	enableField(document.getElementById('addLink'));
	enableField(document.getElementById('delLink'));
	enableField(document.getElementById('add'));
	enableField(document.getElementById('del'));
}
function nonconfirmMessage() {
	clear();
}

function closeScreen(){
submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.closeCommand.do');

}

function clear(){
submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.clearCommand.do');
}

function save(){
var chkForBank=targetFormName.elements.checkForBank.length;
var chkForCountry=targetFormName.elements.checkForCountry.length;
if(chkForCountry>1){
	for(var index=0;index<chkForCountry;index++){
		if((targetFormName.elements.gpaCountries[index]!=null) && (targetFormName.elements.gpaCountries[index].value.length>0) && (targetFormName.elements.gpaCountries[index].value!=targetFormName.elements.gpaCountries[index].defaultValue)
			&& (targetFormName.elements.hiddenOpFlagForCountry[index].value !="I") && (targetFormName.elements.hiddenOpFlagForCountry[index].value !="D")){
				targetFormName.elements.hiddenOpFlagForCountry[index].value="U";
		}		
	}
}

if(chkForBank>1){
	for(var id=0;id<chkForBank;id++){
	
		if((targetFormName.elements.currencies[id]!=null && targetFormName.elements.currencies[id].value.length>0) || 
		(targetFormName.elements.bankName[id]!=null && targetFormName.elements.bankName[id].value.length>0) || 
		(targetFormName.elements.branch[id]!=null && targetFormName.elements.branch[id].value.length>0) || 
		(targetFormName.elements.accno[id]!=null && targetFormName.elements.accno[id].value.length>0)	|| 
		(targetFormName.elements.city[id]!=null && targetFormName.elements.city[id].value.length>0) || 
		(targetFormName.elements.country[id]!=null && targetFormName.elements.country[id].value.length>0) || 
		(targetFormName.elements.swiftCode[id]!=null && targetFormName.elements.swiftCode[id].value.length>0) || 
		(targetFormName.elements.ibanNo[id]!=null && targetFormName.elements.ibanNo[id].value.lenght>0)){
			if((targetFormName.elements.currencies[id].value!=targetFormName.elements.currencies[id].defaultValue ) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.swiftCode[id].value!=targetFormName.elements.swiftCode[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.bankName[id].value!=targetFormName.elements.bankName[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.branch[id].value!=targetFormName.elements.branch[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.accno[id].value!=targetFormName.elements.accno[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.city[id].value!=targetFormName.elements.city[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.country[id].value!=targetFormName.elements.country[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
			if((targetFormName.elements.ibanNo[id].value!=targetFormName.elements.ibanNo[id].defaultValue) && targetFormName.elements.hiddenOpFlagForBank[id].value !="I" && targetFormName.elements.hiddenOpFlagForBank[id].value !="D"){
				targetFormName.elements.hiddenOpFlagForBank[id].value="U";
			}
		}
	}
}


submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.saveCommand.do?checkFlag=Save');
}


function del(){
submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.saveCommand.do?checkFlag=Delete');
}

function doCheckAll(frm)
{
var chk = document.getElementsByName("checkForCountry");
	if(targetFormName.elements.checkAllCountriesBox.checked == true)
	{
		for(var i=0; i<chk.length; i++)
		{
			chk[i].checked = true;
		}
	}
	if(targetFormName.elements.checkAllCountriesBox.checked == false)
	{
		for(var i=0; i<chk.length; i++)
		{
			chk[i].checked = false;
		}
	}
	
	var chk1 = document.getElementsByName("checkForBank");
	if(targetFormName.elements.checkAllBanksBox.checked == true)
	{
		for(var i=0; i<chk1.length; i++)
		{
			chk1[i].checked = true;
		}
	}
	if(targetFormName.elements.checkAllBanksBox.checked == false)
	{
		for(var i=0; i<chk1.length; i++)
		{
			chk1[i].checked = false;
		}
	}
}

function showGpaCountryCodelov(lovButton){

	var index = lovButton.id.split(lovButton.name)[1];
	var value = targetFormName.elements.gpaCountries[index].value;
	displayLOV('showCountry.do','Y','Y','showCountry.do',value,'gpaCountries','1','gpaCountries','',index);
	
}

function showCurrencylov(lovButton){

	var index = lovButton.id.split(lovButton.name)[1];
	var value = targetFormName.elements.currencies[index].value;
	displayLOV('showCurrency.do','N','Y','showCurrency.do',value,'currencies','1','currencies','',index);
		
}


function screenConfirmDialog(targetFormName, dialogId) {
	
	if(dialogId == 'id_1'){
		submitForm(targetFormName,'mailtracking.mra.defaults.billingsitemaster.clearCommand.do');
		}

	else{
	
	}
}

function screenNonConfirmDialog(targetFormName, dialogId) {

	if(targetFormName.elements.currentDialogOption.value == 'N') {
		
		if(dialogId == 'id_1'){

		}
		if(dialogId == 'id_2'){

		}
		if(dialogId == 'id_3'){

		}
	}
}
