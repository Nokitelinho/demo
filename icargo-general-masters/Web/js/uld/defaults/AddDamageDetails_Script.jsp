<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister()
{

	var frm=targetFormName;
	/*if(frm.damageCode.disabled==false){
			frm.damageCode.focus();
		}*/
	with(frm){

		evtHandler.addEvents("btnOK","OnClickOK(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose(this.form)",EVT_CLICK);

		evtHandler.addIDEvents("createdmg","createdmg()",EVT_CLICK);
		evtHandler.addIDEvents("deletedmg","deletedmg()",EVT_CLICK);

		evtHandler.addEvents("remarks","validateMaximumLength(this,400,'Remarks','Maximum length',true)",EVT_BLUR);
		evtHandler.addEvents("facilitycodelov","showFacilityCode()",EVT_CLICK);
		evtHandler.addEvents("partycodelov","showPartyCode()",EVT_CLICK);
		 evtHandler.addEvents("fromPartyCode","fromPartyCodeValidate()",EVT_BLUR);
		onLoad();
		evtHandler.addEvents("section","populateOnChange()",EVT_CHANGE);
		evtHandler.addEvents("checkBxVal","findSumofSelectedRows();toggleTableHeaderCheckbox('checkBxVal',this.form.selectAll);",EVT_CLICK);
			
		evtHandler.addEvents("selectAll","updateHeaderCheckBox(this.form,this,this.form.checkBxVal);findSumofSelectedRows();",EVT_CLICK);
		evtHandler.addEvents("partyType","doComboChange()",EVT_CHANGE);
			
	}
	
}

function findSumofSelectedRows(){
//alert("From function");
	var chkboxes = document.getElementsByName('checkBxVal');
	var sectionCodes = document.getElementsByName('sectionCode');
	var multipleSections = "";
	var count = 0;
	
	 var points=targetFormName.elements.noofPoints;
		
	var sum=0;
	var damageDesc="";
	var sectionDesc="";
	
	
	for(var index=0; index < chkboxes.length; index++ ){
	
		if(chkboxes[index].checked == true){
		
			if(chkboxes.length==1)
			{
				sum=targetFormName.elements.noofPoints.value	;
				if(damageDesc==""){
				damageDesc=targetFormName.elements.description.value;
				}
				//alert("from if");
			}
			else
			{
					//alert("from else");
					
			sum =sum+parseInt(points[index].value);
			if(damageDesc==""){
				damageDesc=targetFormName.elements.description[index].value;
			}
			else{
			
			damageDesc=damageDesc+","+targetFormName.elements.description[index].value;
			sectionDesc=sectionDesc+","+targetFormName.elements.section[index+1].value;
			}
			multipleSections = multipleSections+sectionCodes[index].value+",";
			}
		}
		
	}

	targetFormName.elements.totalPoints.value=sum;
	targetFormName.elements.damageDescription.value=damageDesc;
	//alert("sum"+sum);
	//alert("damageDescription"+damageDesc);
	
}


function populateOnChange()
{
//alert("targetFormName.section.value"+targetFormName.section.value);
			targetFormName.elements.totalPoints.value=0;
			//submitForm(targetFormName, 'uld.defaults.displaydetails.do');
			submitForm(targetFormName, 'uld.defaults.damageDetailsListCommand.do');
			

}


function OnClickOK(frm){
//alert("IN");
var frm=targetFormName;

if(frm.elements.reportedDate.value.trim()==""){
	//showDialog('ReportedDate Type cannot be blank', 1, self, targetFormName, 'id_2');
	  showDialog({msg :'ReportedDate Type cannot be blank',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
	return;
}

/*if(frm.severity.value.trim()==""){
	showDialog('severity  cannot be blank', 1, self, targetFormName, 'id_2');
	return;
}
if(frm.location.value.trim()==""){
	showDialog('Location cannot be blank', 1, self, targetFormName, 'id_2');
	return;
}
if(frm.partyType.value.trim()==""){
	showDialog('Party Type cannot be blank', 1, self, targetFormName, 'id_2');
	return;
}*/
if(frm.elements.facilityType.value.trim()!="" && frm.elements.location.value.trim()==""){
	//showDialog('Please Enter Location', 1, self, targetFormName, 'id_2');
	  showDialog({msg :'Please Enter Location',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
	return;
}
if(frm.elements.facilityType.value.trim()!="" && frm.elements.location.value.trim()==""){
	 //showDialog('Please Enter Location', 1, self, targetFormName, 'id_2');
	   showDialog({msg :'Please Enter Location',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
	return;
}
if(frm.elements.partyType.value.trim()!="" && frm.elements.party.value.trim()==""){
	//showDialog('Please Enter party', 1, self, targetFormName, 'id_2');
	  showDialog({msg :'Please Enter party',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
	return;
}

if(frm.elements.totalPoints.value.trim()==""){
	//showDialog('Select at least one damage', 1, self, targetFormName, 'id_2');
	  showDialog({msg :'Select at least one damage',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
	return;
}

if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg")
	{



	if(isFormModified()) {

		targetFormName.elements.flag.value = "Updated";
	}else{
	    	targetFormName.elements.flag.value = "";
	}  }

	if(targetFormName.elements.section.value == null || targetFormName.elements.section.value == ""){
		var chkboxes = document.getElementsByName('checkBxVal');
		var sectionCodes = document.getElementsByName('sectionCode');
		var multipleSections = "";
		for(var index=0; index < chkboxes.length; index++ ){
			if(chkboxes[index].checked == true){
				multipleSections = multipleSections+sectionCodes[index].value+",";
			}
		}		
		targetFormName.elements.sections.value=multipleSections;
	}
var chkbox =document.getElementsByName("checkBxVal");
    var desicion=false;
	for(var i=0; i<chkbox.length;i++){
	if(chkbox[i].checked== true) {
            desicion=  true; 
	         break;
	 }
	 }
	   if(desicion){
var action="uld.defaults.savedmgdetails.do";
submitForm(frm,action);
			  }
			  else{
			   showDialog({msg :'Select at least one damage',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_2'
                }); 
			  }
}


function onLoad(){
var frm=targetFormName;





findSumofSelectedRows();
//alert("from Load");
//alert("!!!!"+targetFormName.overStatus.value);
//alert(targetFormName.statusFlag.value);
if(targetFormName.elements.statusFlag.value=="uld_def_add_dmg_success" ||
   targetFormName.elements.statusFlag.value=="uld_def_update_dmg_success" ){

window.opener.targetFormName.elements.screenStatusValue.value = targetFormName.elements.screenStatusValue.value;
window.opener.targetFormName.elements.statusFlag.value = targetFormName.elements.statusFlag.value;
//alert("from onload");
if(targetFormName.elements.overStatus.value!=null){
//alert("targetFormName.overStatus.value"+targetFormName.overStatus.value);
window.opener.targetFormName.elements.overallStatus.value=targetFormName.elements.overStatus.value;
//alert("after setting");
}
//alert("before refresh");
window.opener.targetFormName.action = "uld.defaults.listulddmgdetails.do?screenReloadStatus="+"reload";
window.opener.targetFormName.submit();
window.opener.IC.util.common.childUnloadEventHandler();
window.close();}
if(targetFormName.elements.flag.value=="NORECORDS"  ){
targetFormName.elements.btnOK.disabled="true";
targetFormName.elements.flag.value="";
}else
{
targetFormName.elements.btnOK.disabled=false;

}
if(targetFormName.elements.statusFlag.value=="uld_def_add_dmg" ){
targetFormName.elements.closed.disabled=true;
targetFormName.elements.dmgRefNo.value="";
}else
{
targetFormName.elements.closed.disabled=false;
if(targetFormName.elements.dmgRefNo.value=="0" ){
targetFormName.elements.dmgRefNo.value="";
}

}

if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg" ){
//alert(1);
targetFormName.elements.section.disabled=true;
//var x=document.getElementById("mySection")
//    x.disabled=true
//alert('frm.section.disabled--->'+frm.section.disabled);
}
}


function onClickClose(frm){
window.close();
}

function selectNextDmgDetail(strLastPageNum, strDisplayPage) {
	if(targetFormName.elements.statusFlag.value=="uld_def_mod_dmg")
	{
	//alert(isFormModified());
	if(isFormModified()) {

		targetFormName.elements.flag.value = "Updated";
	}else{
	    	targetFormName.elements.flag.value = "";
	}  }
   	targetFormName.elements.dmglastPageNum.value = strLastPageNum;
   	targetFormName.elements.dmgdisplayPage.value = strDisplayPage;
   	var action = "uld.defaults.selectnextdmgcommand.do";
     	var frm = targetFormName;

     	submitForm(frm, action);
 }

function createdmg() {

	var frm=targetFormName;
/*	if(frm.reportedDate.value==""){

		showDialog('Reported Date cannot be blank', 1, self);
		return;
	}
	if(frm.repStn.value==""){

		showDialog('Reported Airport Type cannot be blank', 1, self);
		return;
	}
	if(frm.location.value==""){
		showDialog('Location cannot be blank', 1, self);
		return;
	}
	if(frm.partyType.value==""){
		showDialog('Party Type cannot be blank', 1, self);
		return;
	}
	if(frm.party.value==""){
		showDialog('Party  cannot be blank', 1, self);
		return;
	}*/
	var action = "uld.defaults.adddmg.do";
	submitForm(frm, action);
}


function deletedmg() {
	var frm=targetFormName;
	var action = "uld.defaults.deletedmg.do";
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
	return hasFormChanged;
 }

function showFacilityCode(){

	if(targetFormName.elements.facilityType.value=="" ){
		//showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_77');
		  showDialog({msg :'Facility Type cannot be blank',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_77'
                });
		return;
	}
	if(targetFormName.elements.repStn.value==""){

		//showDialog('Reported Airport cannot be blank', 1, self);
		  showDialog({msg :'Reported Airport cannot be blank',
			            	type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName,
                         }); 
		return;
	}
	//targetFormName.currentStation.defaultValue=targetFormName.currentStation.value;
	//targetFormName.facilityType.defaultValue=targetFormName.facilityType.value;
	var textfiledDesc="";
	var currentStationForLov=targetFormName.elements.repStn.value;
	var facilityTypeForLov=targetFormName.elements.facilityType.value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";
	var StrUrl=strAction+"?textfiledObj=location&formNumber=0&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	
	//Modified by A-5170 for ICRD-32241 starts
	//var myScreen = openPopUpWithHeight(StrUrl, "500");
	var myScreen = openPopUp(StrUrl,500,350);
	//Modified by A-5170 for ICRD-32241 ends
	
}

function showPartyCode(){
var frm=targetFormName;
if(frm.elements.partyType.value=='A'){
	displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.party.value,'party','0','party','',0)
}else if(frm.elements.partyType.value=='G'){
	var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=party&formNumber=0&textfiledDesc="+textobj;
	//Modified by A-5170 for ICRD-32241 starts
	//var myWindow = openPopUpWithHeight(StrUrl, "500");
	var myWindow = openPopUp(StrUrl,500,400);
	//Modified by A-5170 for ICRD-32241 ends
}else if(frm.elements.partyType.value=='O'){

/*}else if(frm.partyType.value==""){
	showDialog("Party Type cannot be blank", 1, self, targetFormName, 'id_77');
		return; */
}


 }
 
 function doComboChange(){
 //alert(111);
 targetFormName.elements.party.value="";
 }