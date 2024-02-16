<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister(){

	var frm=targetFormName;
	//onScreenloadSetHeight();
	onScreenLoad();
	with(frm){

	evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);

	evtHandler.addEvents("btSave","saveaccessories(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
	evtHandler.addEvents("airportlov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.stationCode.value,'Airport Code','1','stationCode','',0)",EVT_CLICK);
	evtHandler.addEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
	evtHandler.addEvents("accessoryDescription","validateFields(this, -1, 'Accessory Description', 8, true, true)",EVT_BLUR);
	evtHandler.addEvents("minimumQuantity","validateFields(this, -1, 'Minimum Quantity', 3, true, true)",EVT_BLUR);
	}
}
function onScreenloadSetHeight(){
 	jquery('#div1').height(((document.body.clientHeight)*70)/100);

}
function list(frm){

	submitForm(frm,'uld.defaults.stock.findaccessoriesstockdetails.do');

}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				if(!frm.elements.accessoryCode.disabled 
					&& frm.elements.modeFlag.value != 'Y'){
					frm.elements.accessoryCode.focus();
				}
				else{
					 if(!frm.elements.accessoryDescription.disabled) {
						frm.elements.accessoryDescription.focus();
						}
				}				
	}	
}


function clearScreen(frm){
	targetFormName.elements.accessoryDisableStatus.value="";
	//added by a-3045 for bug20364 starts
	targetFormName.elements.accessoryDisableStatus.defaultValue = targetFormName.elements.accessoryDisableStatus.value;
	//added by a-3045 for bug20364 ends
	//submitForm(frm,'uld.defaults.stock.clearaccessoriesstock.do');
	submitFormWithUnsaveCheck('uld.defaults.stock.clearaccessoriesstock.do');
}





function saveaccessories(frm){


	if(!(targetFormName.elements.accessoryCode.value==targetFormName.elements.accessoryCode.defaultValue&&
	     targetFormName.elements.airlineCode.value==targetFormName.elements.airlineCode.defaultValue&&
	     targetFormName.elements.stationCode.value==targetFormName.elements.stationCode.defaultValue&&
	     targetFormName.elements.accessoryDescription.value==targetFormName.elements.accessoryDescription.defaultValue&&
	     targetFormName.elements.available.value==targetFormName.elements.available.defaultValue&&
	     targetFormName.elements.loaned.value==targetFormName.elements.loaned.defaultValue&&
	     targetFormName.elements.remarks.value==targetFormName.elements.remarks.defaultValue)){

	     }
	  if(targetFormName.elements.minimumQuantity.value==""){
		showDialog({	msg		:" Minimum stock is mandatory",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
						});	
		return;
		}    
	submitForm(frm,'uld.defaults.stock.saveaccessoriesstock.do');
}

function onScreenLoad(){
	if(targetFormName.elements.screenStatusFlag.value == "detail"){
		targetFormName.elements.minimumQuantity.readOnly = false;
	}
	else{
		targetFormName.elements.minimumQuantity.readOnly = true;
	}
	var screenloadstatus = targetFormName.elements.modeFlag.value;
	var modeflag =  targetFormName.elements.detailsFlag.value;

	var lovFlag= targetFormName.elements.lovFlag.value;

	//added by a-3045 for BUG_5750 starts
	//Commented By A-6841 for ICRD-185395 
	/*if(targetFormName.elements.airlineCode.value != ""){
		//targetFormName.elements.airlineCode.disabled = true;
		disableField(targetFormName.elements.airlineCode);
		//targetFormName.airlinelov.disabled = true;
		disableField(targetFormName.elements.airlinelov);
	}*/
	//added by a-3045 for BUG_5750 ends

	if(targetFormName.elements.accessoryDisableStatus.value=="airline"){
		//targetFormName.elements.airlineCode.disabled = true;
		//disableField(targetFormName.elements.airlineCode);//Commented By A-6841 for ICRD-185395
		//targetFormName.airlinelov.disabled = true;
		//disableField(targetFormName.elements.airlinelov);//Commented By A-6841 for ICRD-185395
		//targetFormName.btClear.disabled=false;
		enableField(targetFormName.elements.btClear);
	}
	if(targetFormName.elements.accessoryDisableStatus.value=="GHA"){
		//targetFormName.stationCode.disabled = true;
		disableField(targetFormName.elements.stationCode);
		//targetFormName.airportlov.disabled = true;
		disableField(targetFormName.elements.airportlov);
		//targetFormName.btClear.disabled=false;
		enableField(targetFormName.elements.btClear);
	}

	if(lovFlag == 'Y'){
	disableField(document.getElementById('airlineLov'));
	var stationLov=document.getElementById('airportlov');
	disableField(document.getElementById('airportlov'));
	//targetFormName.btList.disabled=true;
	disableField(targetFormName.elements.btList);
	}

	var frm = targetFormName;
	if(!frm.elements.accessoryCode.disabled) {
		frm.elements.accessoryCode.focus();
	}

	if(modeflag == 'From List'){

	//frm.btList.disabled =true;
	disableField(frm.btList);
	//frm.btClear.disabled=true;
	disableField(frm.btClear);
	}

	if(screenloadstatus =='Y'){
		if(!frm.elements.accessoryDescription.disabled)
			frm.elements.accessoryDescription.focus();
	}
	if(screenloadstatus =='N'){
		if(!frm.elements.accessoryCode.disabled)
				frm.elements.accessoryCode.focus();
	}
}

function closeScreen()
{
	var frm = targetFormName;

	submitForm(frm,'uld.defaults.stock.closeaccessoriesstock.do');


}

function NavigateAcsrsDetails(strLastPageNum, strDisplayPage) {

	var frm = targetFormName;

    	frm.elements.lastPageNum.value = strLastPageNum;
    	frm.elements.displayPage.value = strDisplayPage;
    	submitForm(frm,'uld.defaults.navigateacsrs.do');

  }
function confirmMessage() {
	var frm = targetFormName;

	//frm.accessoryCode.disabled=true;
	disableField(frm.elements.accessoryCode);
	frm.elements.airlineCode.readOnly=true;
	frm.elements.stationCode.readOnly=true;
	frm.elements.statusFlag.value="I";
	submitForm(frm,'uld.defaults.stock.createaccessoriesstock.do');

}

function nonconfirmMessage() {

	var frm = targetFormName;
	//frm.accessoryCode.disabled=false;
	enableField(frm.elements.accessoryCode);
	frm.elements.airlineCode.readOnly=true;
	frm.elements.stationCode.readOnly=false;
	//frm.btList.disabled=false;
	enableField(frm.btList);
	//frm.btSave.disabled=true;
	disableField(frm.btSave);
	//frm.btClear.disabled=false;
	enableField(frm.btClear);

	
	frm.elements.accessoryDisableStatus.value="";
	frm.elements.accessoryDisableStatus.defaultValue = frm.elements.accessoryDisableStatus.value;
	submitForm(frm,'uld.defaults.stock.clearaccessoriesstock.do');
	
}