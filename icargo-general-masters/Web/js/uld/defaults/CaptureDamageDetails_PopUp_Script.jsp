<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>



function screenSpecificEventRegister() {

	var frm = targetFormName;

	with(frm){
		evtHandler.addIDEvents("btnOk","savedamageULDDetails(targetFormName)",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","damageDetailsSubmitAction(targetFormName,'Close')",EVT_CLICK);
		evtHandler.addIDEvents("AddULD","damageDetailsSubmitAction(targetFormName,'AddULD')",EVT_CLICK);
		evtHandler.addIDEvents("DeleteULD","damageDetailsSubmitAction(targetFormName,'DelULD')",EVT_CLICK);
		evtHandler.addEvents("facilitycodelov","showFacilityCode(targetFormName,this)",EVT_CLICK);
		evtHandler.addEvents("partycodelov","showPartyCode(targetFormName,this)",EVT_CLICK);
		evtHandler.addEvents("fromPartyCode","fromPartyCodeValidate()",EVT_BLUR);
		evtHandler.addEvents("partyType","onPartyTypeChange(this)",EVT_CHANGE);
		 evtHandler.addEvents("section","populateOnChange(this)",EVT_CHANGE);
        evtHandler.addEvents("airportLovImg","populateAirport(targetFormName,this)",EVT_CLICK);
		evtHandler.addEvents("btnAddPic","picFocus()",EVT_CLICK); 
		evtHandler.addEvents("selectedDmgRowId","selectRow(this)",EVT_CLICK); 
		evtHandler.addEvents("description","populateTotalPoints(this)",EVT_CHANGE);
	}
	damagePopupOnloadAction();
}

function savedamageULDDetails(targetFormName){
	var validate=validateSave();
	if(validate==0){
	var frm = targetFormName;
	damageDetailsSubmitAction(targetFormName,'Ok');
	
}

}
function validateSave(){
	var sectionArr= new Array();
	var a=0;
	var frm = targetFormName;
	for(var i=0; i<targetFormName.elements.ratingUldOperationFlag.length; i++){
		if(targetFormName.elements.ratingUldOperationFlag[i] !=null){
			if(targetFormName.elements.ratingUldOperationFlag[i].value == "I"){
				if(targetFormName.elements.section[i].value.length==0 || targetFormName.elements.description[i].value.length==0){
					if(a==0){
						//showDialog('<common:message bundle="sccResources" key="shared.scc.invalidscclength" scope="request"/>', 1, self);
					   showDialog({msg:"please select a damage", type:1, parentWindow:self});
						targetFormName.elements.section[i].focus();
						a++;
					}
				}
				else{
					if(sectionArr.length !=0) {
						for(var j=0;j<sectionArr.length;j++) {
						   if(targetFormName.elements.section[i].value == sectionArr[j]) {
							  showDialog({msg:"Same damage", type:1, parentWindow:self});
						      targetFormName.elements.section[i].focus();
							  a++
						  }
						}
					}
					sectionArr.push(targetFormName.elements.section[i].value);
				}
			}
		}
	}
	return a;
}
/**
 * Method to handle the main page on clicking popup ok button
 */
function damagePopupOnloadAction() {

	var frm = document.forms[0];
	var statusFlag = frm.statusFlag.value;

	if(statusFlag == "action_damagesuccess") {
		window.opener.document.forms[1].statusFlag.value = "action_damagesuccess";
		window.opener.document.forms[1].action = "uld.defaults.misc.updatemultipleulddamage.do";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit();
		window.close();
	}
}

/**
 * Methods to handle the actions in
 * Add/Update Rating Details popup
 */
 function damageDetailsSubmitAction(frm, actionString) {

 	if(actionString == 'Close') {
		/**
        * This is to maintain the values in the 
        * main page even after clicking popup close.
        */
		window.opener.document.forms[1].statusFlag.value = "action_damagesuccess";
		window.opener.document.forms[1].action = "uld.defaults.misc.updatemultipleulddamage.do";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit();
 		window.close();
 	}

 	else if(actionString == 'Ok') {
	
		var action="uld.defaults.misc.addDamageDetails.do";
		submitForm(frm, action);
		
	}

 	else if(actionString == 'AddULD') {
 		
		addTemplateRow('uldTemplateRow','uldTableBody','ratingUldOperationFlag');
 	}

 	else if(actionString == 'DelULD') {
 		
		deleteTableRow('selectedDmgRowId','ratingUldOperationFlag');
		asyncSubmit(targetFormName,"uld.defaults.misc.populateTotalPoints.do","showTotalPoints",null,null);
 	}
	

}

 function flagValueChange(field){
	operationFlagChangeOnEdit(field,'ratingUldOperationFlag');
 }
 function showFacilityCode(form,obj){
	var index = obj.id.split("facilitycodelov")[1];
	if(targetFormName.elements.facilityType[index].value=="" ){
		//showDialog("Facility Type cannot be blank", 1, self, targetFormName, 'id_77');
		  showDialog({msg :'Facility Type cannot be blank',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_77'
                });
		return;
	}
	if(targetFormName.elements.repStn[index].value==""){
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
	var currentStationForLov=targetFormName.elements.repStn[index].value;
	var facilityTypeForLov=targetFormName.elements.facilityType[index].value;
	var strAction="uld.defaults.lov.screenloadfacilitycodelov.do";
	var StrUrl=strAction+"?textfiledObj=location["+index+"]&formNumber=0&textfiledDesc="+textfiledDesc+'&facilityTypeForLov='+facilityTypeForLov+'&currentStationForLov='+currentStationForLov;
	//Modified by A-5170 for ICRD-32241 starts
	//var myScreen = openPopUpWithHeight(StrUrl, "500");
	var myScreen = openPopUp(StrUrl,500,350);
	//Modified by A-5170 for ICRD-32241 ends
}
function showPartyCode(form,obj){
var frm=form;
var index = obj.id.split("partycodelov")[1];
if(frm.elements.partyType[index].value=='A'){
		
	displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.party[index].value,'party','0','party','',index)
}else if(frm.elements.partyType[index].value=='G'){
	var textobj = "";
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=party["+index+"]&formNumber=0&textfiledDesc="+textobj;
	//Modified by A-5170 for ICRD-32241 starts
	//var myWindow = openPopUpWithHeight(StrUrl, "500");
	var myWindow = openPopUp(StrUrl,500,400);
	//Modified by A-5170 for ICRD-32241 ends
}else if(frm.elements.partyType[index].value=='O'){
/*}else if(frm.partyType[index].value==""){
	showDialog("Party Type cannot be blank", 1, self, targetFormName, 'id_77');
		return; */
}
 }
  function onPartyTypeChange(obj){
 var index =  obj.getAttribute("rowCount");
 targetFormName.elements.party[index].value="";
 }
 function populateOnChange(obj)
{
	globalObj=obj;
	 var ind =  obj.getAttribute("rowCount");
	asyncSubmit(targetFormName,"uld.defaults.misc.listDamageDescription.do?selectedDmgRow="+ind,"showDescriptionCodes",null,null);
}
function showDescriptionCodes(_result) {
	 var _rowCount = globalObj.getAttribute("rowCount");
	 _billDiv=_result.document.getElementById("_ajBillingPeriods");
	 _billDivs=_billDiv.getElementsByTagName("div");
	 targetFormName.elements.description[_rowCount].options.length=0;
	 targetFormName.elements.description[_rowCount].options[0]= new Option("Select","");
	 for(var t=0;t<_billDivs.length;t++){
		_dmgDescCode=_billDivs[t].getAttribute("dmgDescCode");
		targetFormName.elements.description[_rowCount].options[t+1]=new Option(_dmgDescCode,_dmgDescCode);
	 }
}
function populateAirport(form,obj) {
var frm=form;
var index = obj.id.split("airportLovImg")[1];
displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.repStn[index].value,'CurrentAirport','0','repStn','',index);
}	
function selectRow(obj) {
	var index = obj.id.split("selectedDmgRowId")[1];
	targetFormName.elements.selectedDmgRow.value = index;
}
function picFocus(){
	for(var i=0;i<targetFormName.elements.selectedDmgRowId.length;i++) {
		 if(targetFormName.elements.selectedDmgRowId[i].checked==true) {
			 targetFormName.elements.selectedDmgRow.value = i;
		 }
	}
		var check = validateSelectedCheckBoxes(targetFormName, 'selectedDmgRowId', 1, 1);
	if (check){
	if(targetFormName.elements.dmgPicture.value!=""){
	    //var size = document.appletsupport.getFileSize(targetFormName.dmgPicture.value);
		//var size =0;
		try{
        var size = document.appletsupport.getFileSize(targetFormName.elements.dmgPicture.value);
		if(size > 2097152) {
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>", 1, self, targetFormName, 'id_3');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_3'
                });
		}else{
			//var picNamArr = targetFormName.dmgPicture.value.split(".");
			if(targetFormName.elements.dmgPicture.value.lastIndexOf(".")>0){
			var pic = targetFormName.elements.dmgPicture.value.substring(targetFormName.elements.dmgPicture.value.lastIndexOf(".")+1,targetFormName.elements.dmgPicture.value.length);
				if((pic.toUpperCase() == "JPG") ||
				(pic.toUpperCase() == "JPEG") ||
				(pic.toUpperCase() == "BMP")||
				(pic.toUpperCase() == "GIF") ||
				(pic.toUpperCase() == "PNG")) {
				submitForm(targetFormName,"uld.defaults.misc.adddmgpic.do");
			}else{
				//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			      showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
			else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
		}		
        }catch(e){
        var size = document.getElementsByName("dmgPicture")[0].files[0].size ;
        if(size > 2097152) {
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>", 1, self, targetFormName, 'id_3');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.picturesize'/>',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
				dialogId:'id_3'
                });
		}else{
			//var picNamArr = targetFormName.dmgPicture.value.split(".");
			if(targetFormName.elements.dmgPicture.value.lastIndexOf(".")>0){
			var pic = targetFormName.elements.dmgPicture.value.substring(targetFormName.elements.dmgPicture.value.lastIndexOf(".")+1,targetFormName.elements.dmgPicture.value.length);
				if((pic.toUpperCase() == "JPG") ||
				(pic.toUpperCase() == "JPEG") ||
				(pic.toUpperCase() == "BMP")||
				(pic.toUpperCase() == "GIF") ||
				(pic.toUpperCase() == "PNG")) {
				submitForm(targetFormName,"uld.defaults.misc.adddmgpic.do");
			}else{
				//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			      showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
			else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>", 1, self, targetFormName, 'id_2');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.err.invalidpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_2'
                });
			}
			}
		}
	}else{
			//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.info.uploadpicture'/>", 1, self, targetFormName, 'id_1');
			  showDialog({msg :'<common:message bundle='maintainDamageReportResources' key='uld.defaults.info.uploadpicture'/>',
				    type:1,
				    parentWindow:self,                                       
				    parentForm:targetFormName,
				    dialogId:'id_1'
                });
			}
		}
	}
function viewPic(seqno){
  var newWindow = openPopUp("uld.defaults.misc.viewpicture.do?seqNum="+seqno,"800","500");
}
function populateTotalPoints(obj){
	 var descind =  obj.getAttribute("rowCount");
	 asyncSubmit(targetFormName,"uld.defaults.misc.populateTotalPoints.do","showTotalPoints",null,null);
}
function showTotalPoints(_tableInfo){
if(_tableInfo.document.getElementById("_ajax_totalpoints")!=null) {
targetFormName.elements.totalPoints.value=_tableInfo.document.getElementById("_ajax_totalpoints").textContent;
}
}
