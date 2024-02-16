<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	with(frm){

		targetFormName.elements.stationCode.readOnly=true;

		if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg"){
		targetFormName.elements.airlineCode.readOnly=true;
		//targetFormName.airlinelov.disabled = true;
		disableField(targetFormName.elements.airlinelov);
		targetFormName.elements.uldTypeCode.readOnly=true;
		//targetFormName.uldNature.disabled=true;
		disableField(targetFormName.elements.uldNature);
		//targetFormName.uldlovImg.disabled = true;
        disableField(targetFormName.elements.uldlovImg);
		}
			if(targetFormName.elements.fromScreen.value == "Create" && targetFormName.elements.filterStatus.value == "both") {
				targetFormName.elements.airlineCode.readOnly=true;
				//targetFormName.airlinelov.disabled = true;
				disableLink(targetFormName.elements.airlinelov);
				//targetFormName.uldNature.disabled= true;
				disableField(targetFormName.elements.uldNature);
				evtHandler.addEvents("uldlovImg","showULDType()",EVT_CLICK);


			}

			if(targetFormName.elements.fromScreen.value == "Modify") {
				targetFormName.elements.airlineCode.readOnly=true;
				//targetFormName.airlinelov.disabled = true;
				disableLink(targetFormName.elements.airlinelov);
				//targetFormName.uldNature.disabled=true;
				disableField(targetFormName.elements.uldNature);
				//targetFormName.uldlovImg.disabled = true;
				disableField(targetFormName.elements.uldlovImg);
				targetFormName.elements.uldTypeCode.readOnly=true;



			}
		evtHandler.addIDEvents("createdmg","createdmg()",EVT_CLICK);
		evtHandler.addIDEvents("deletedmg","deletedmg()",EVT_CLICK);
		evtHandler.addEvents("maximumQty","validateInt(this,'Maximum Quantity','Invalid Format',true)",EVT_BLUR);
		evtHandler.addEvents("minimumQty","validateInt(this,'Minimum Quantity','Invalid Format',true)",EVT_BLUR);

		evtHandler.addEvents("btSave","createRow(this.form)",EVT_CLICK);

		//commented line below removed by Manaf for INT ULD477
		evtHandler.addEvents("airlinelov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','0','airlineCode','',0)",EVT_CLICK);


		evtHandler.addEvents("uldlovImg","showULDType()",EVT_CLICK);
		evtHandler.addEvents("uldTypeCode","populateULDGroupCode()",EVT_BLUR);
		evtHandler.addEvents("dwellTime","validateInt(this,'Dwell Time','Invalid Format',true)",EVT_BLUR);
		onLoad();
	}
checkStatus();
	if(frm.elements.airlineCode.readOnly==false){
		frm.elements.airlineCode.focus();
	}
	if(frm.elements.airlineCode.readOnly==true){
		if(frm.elements.uldNature.disabled==false){
			frm.elements.uldNature.focus();
		}
		if(frm.elements.uldNature.disabled==true){
			if(frm.elements.minimumQty.disabled==false){
				frm.elements.minimumQty.focus();
			}
		}
	}
}

//function to create a row
function createRow(frm){
	setTimeout('saveStock()', 100);
}
//added A-2408 from bugfix on 31Jul08
function saveStock(){
	var frm=targetFormName;
		if(frm.elements.airlineCode.value == ""){
			showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.info.maintainuldstock.addstock'/>", 1, self, frm, 'id_1');
			return;
		}
		if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg")
			{

				if(isFormModified()) {

					targetFormName.elements.flag.value = "Updated";
				}else{
					targetFormName.elements.flag.value = "";
				}
			}

		var action="uld.defaults.createuldsetupstock.do";
	submitForm(frm,action);
}
//function to execute on screenload
function onLoad(){
var frm = targetFormName;
//Commented
//alert(targetFormName.statusFlag.value);
if(targetFormName.elements.statusFlag.value=="uld_def_add_dmg_success" ||
   targetFormName.elements.statusFlag.value=="uld_def_update_dmg_success" ){
   //alert("inside onload--->"+frm.stationCode.value);
   var stationCode = frm.elements.stationCode.value;
//window.opener.targetFormName.statusFlag.value = targetFormName.statusFlag.value;


window.opener.IC.util.common.childUnloadEventHandler();

//window.opener.targetFormName.action = "uld.defaults.finduldstocksetup.do?stationCode="+stationCode;
//window.opener.targetFormName.submit();
window.opener.submitForm(window.opener.targetFormName,"uld.defaults.finduldstocksetup.do?stationCode="+stationCode);
//targetFormName.statusFlag.value = "";
//alert("inside onload after submit--->"+frm.stationCode.value);
window.close();
}
if(targetFormName.elements.flag.value=="NORECORDS"  ){
//targetFormName.btSave.disabled=true;
disableField(targetFormName.elements.btSave);
targetFormName.elements.flag.value="";
}else
/*{
targetFormName.btSave.disabled=false;
}*/

//added by A-2412
if(targetFormName.elements.disableUldNature !=null){
if(targetFormName.elements.disableUldNature.value =="Y"){
                        //targetFormName.uldNature.disabled=true;
                        disableField(targetFormName.elements.uldNature);
            }
            else{
                        //targetFormName.uldNature.disabled=false;
                        enableField(targetFormName.elements.uldNature);
            }
}

}

//function to check the status
function checkStatus() {
var state = targetFormName.elements.createStatus.value;
if ("success"== (state)) {
	window.close();
}
}

//function for pagination
function selectNextDmgDetail(strLastPageNum, strDisplayPage) {
	if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg") {
		//Commented
		//alert(isFormModified());
		if(isFormModified()) {
			targetFormName.elements.flag.value = "Updated";
		}else{
			targetFormName.elements.flag.value = "";
		}
	}
   	targetFormName.elements.dmglastPageNum.value = strLastPageNum;
   	targetFormName.elements.dmgdisplayPage.value = strDisplayPage;
   	var station = targetFormName.elements.stationCode.value;

   	//Added by Manaf for INT ULD474 starts
   	if(targetFormName.elements.minimumQty.value==""){
		showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.info.maintainuldstock.minimumquantitynotnull'/>", 1, self);
		return;
	}
	if(targetFormName.elements.maximumQty.value==""){
		showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.info.maintainuldstock.maxquantitynotnull'/>", 1, self);
		return;
	}
	//Added by Manaf for INT ULD474 ends

   	var action = "uld.defaults.stock.maintainstocksetup.selectnext.do?station="+station;
     	var frm = targetFormName;

     	submitForm(frm, action);
 }

//function for create link
function createdmg() {
	//alert("inside create link");
	var frm=targetFormName;
	var station = targetFormName.elements.stationCode.value;

	//Added by Manaf for INT ULD477 starts

	if(targetFormName.elements.minimumQty.value==""){
		showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.info.maintainuldstock.minimumquantitynotnull'/>", 1, self);
		return;
	}
	if(targetFormName.elements.maximumQty.value==""){
		showDialog("<common:message bundle='maintainuldstock' key='uld.defaults.info.maintainuldstock.maxquantitynotnull'/>", 1, self);
		return;
	}
	//Added by Manaf for INT ULD477 ends
	var action = "uld.defaults.stock.maintainstocksetup.adddmg.do?station="+station;
	submitForm(frm, action);
}

//function for delete link
function deletedmg() {
	var frm=targetFormName;
	var action = "uld.defaults.stock.maintainstocksetup.deletedmg.do";
	submitForm(frm, action);
}

/**
 * Method to check whether form has been modified or not
 */
 function isFormModified() {
	var CHECK_BOX     = "CHECKBOX";
	var TEXT_FIELD    = "TEXT";
	var TEXT_AREA     = "TEXTAREA";
	var COMBO_BOX     = "SELECT";
	var RADIO_BUTTON  = "RADIO";
	var FIELD_SET     = "FIELDSET";
	var hasFormChanged=false;
	var formObj  = document.forms[document.forms.length - 1];

	var formElementObj = null;
	var elementType = null;
	if(document.forms != null){
		for(var cnt=0; cnt<document.forms.length;cnt++){
		formObj = document.forms[cnt];
			for (var i = 0; i < formObj.elements.length; i++) {
				formElementObj = formObj.elements[i];
				if (formElementObj.tagName != FIELD_SET) {
					elementType = formElementObj.type.toUpperCase();
					if (elementType == TEXT_FIELD) {
							if (formElementObj.value != formElementObj.defaultValue) {
									hasFormChanged = true;
									break;
							}
					} // End of IF TEXT
					else if (elementType == TEXT_AREA) {
							if (formElementObj.value != formElementObj.defaultValue) {
									hasFormChanged = true;
									break;
							}
					} // End of if TEXTAREA
					else  if ((elementType == CHECK_BOX) || (elementType == RADIO_BUTTON)) {
							if (formElementObj.length  > 1){
									for (var j = 0; j < formElementObj.length; j++) {
											if (formElementObj[j].checked != formElementObj[j].defaultChecked){
													hasFormChanged = true;
													break;
											}
									}
							} //END more than one checkbox
							else {
									if (formElementObj.checked != formElementObj.defaultChecked){
											hasFormChanged = true;
											break;
									}
							}
					} // END of IF CHECKBOX OR RADIOBUTTON
					else if (elementType == COMBO_BOX){
							for (var j = 0; j < formElementObj.length; j++) {
									if (formElementObj.options[j].selected != formElementObj.options[j].defaultSelected){
											hasFormChanged = true;
											break;
									}
							}
					}
					if (hasFormChanged) { break; }
				}
			}
		}
	}
	//alert("hasFormChanged-->>"+hasFormChanged);
	return hasFormChanged;
 }

 // Added by Preet -- For AirNZ 448 on 2 nd Apr
 // To populate ULD Group Code on tab out --starts
 function populateULDGroupCode(){
if(targetFormName.elements.uldTypeCode.value!=""){
		recreateULDGroupDetails('uld.defaults.stock.maintainstocksetup.findULDGroupCode.do','uldcode');
	}else{
		targetFormName.elements.uldGroupCode.value="";
	}

 }
 var _tableDivId = "";
 function recreateULDGroupDetails(strAction,divId){
	var __extraFn="updateULDGroupCode";
	_tableDivId = divId;
	asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}
 function updateULDGroupCode(_tableInfo){
		//_filter=_tableInfo.document.getElementById("uldGroupCodeDiv");
		//document.getElementById(_tableDivId).innerHTML=_filter.innerHTML;
		targetFormName.elements.uldGroupCode.value=_tableInfo.document.getElementById("uldGroupCodeDiv").innerHTML;
		document.getElementById('uldGroupCodeDiv').innerHTML = targetFormName.elements.uldGroupCode.value;

}
function showULDType(){
	displayLOV('showUld.do','N','Y','showUld.do',targetFormName.elements.uldTypeCode.value,'uldTypeCode','0','uldTypeCode','',0);
	targetFormName.elements.uldTypeCode.focus();
}
//// Added by Preet - To populate ULD Group Code on tab out --ends