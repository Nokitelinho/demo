<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister()
{

	var frm=targetFormName;
	if(frm.elements.repHead.disabled==false){
			frm.elements.repHead.focus();
		}
	with(frm){

		evtHandler.addEvents("btnOK","OnClickOK(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClickClose(this.form)",EVT_CLICK);

		evtHandler.addIDEvents("createrep","createrep()",EVT_CLICK);
		evtHandler.addIDEvents("deleterep","deleterep()",EVT_CLICK);
		onLoad();



		 //BLUR Events

		evtHandler.addEvents("amount","validateFloat(this,'Amount','Invalid Format',true)",EVT_BLUR);

		//evtHandler.addEvents("repRemarks","validateMaximumLength(this,25,'Remarks','Maximum length',true)",EVT_BLUR);
	}
}


function OnClickOK(frm){

var frm=targetFormName;

if(frm.elements.repairDate.value == "" ){
	//showDialog('Please Enter Date.',1,self);
	  showDialog({msg :'Please Enter Date.',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
                });
	return false;
}
if(frm.elements.currency.value == "" ){

	//showDialog('Please Enter currency.',1,self);
	  showDialog({msg :'Please Enter currency.',
				type:1,
				parentWindow:self,                                       
				parentForm:targetFormName,
                });
	frm.elements.currency.focus();
	return false;
}
if(frm.elements.repairDate.value != "" &&
	!chkdate(frm.elements.repairDate)){

	//showDialog("Invalid Repair Date", 1, self, frm, 'id_1');
	  showDialog({msg :'Invalid Repair Date',
				type:1,
				parentWindow:self,                                       
				parentForm:frm,
				dialogId:'id_1'
                });
	return;
}

/*var amount = (frm.amount.value).split(".");
if(amount!=null && amount[1]!=null && amount[1].length>4)
{
showDialog("Invalid Amount", 1, self, frm, 'id_2');
return;

}*/

var amount =targetFormName.elements.amount.value;
if(amount.length != 0){
		if(amount.indexOf(".") < 0){
					 if(amount.length > 7){
						targetFormName.elements.amount.focus();
						//showDialog('Invalid Amount.',1,self);
						  showDialog({msg :'Invalid Amount.',
			            	type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName,
                         });
						return;
					 }
			}else{
			 	 if(amount.indexOf(".") > 7){
					targetFormName.elements.amount.focus();
					//showDialog('Invalid Amount.',1,self);
					  showDialog({msg :'Invalid Amount.',
			            	type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName,
                         });
					return;
			 	}
				if(amount.length > 10){
			   	targetFormName.elements.amount.focus();
				//showDialog('Invalid Amount.',1,self);
				  showDialog({msg :'Invalid Amount.',
			            	type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName,
                         });
				return;
		 		}
			             
	         	}
}


if(targetFormName.elements.statusFlag.value=="uld_def_mod_rep")
	{
	//alert(isFormModified());
	if(isFormModified()) {

		targetFormName.elements.flag.value = "Updated";
	}else{
	    	targetFormName.elements.flag.value = "";
	}  }

var action="uld.defaults.saverepdetails.do";
submitForm(frm,action);
}


function onLoad(){
targetFormName.elements.screenStatusFlag.value="screenload";
targetFormName.elements.dmgRepairRefNo.readOnly = true;
//alert(targetFormName.statusFlag.value);

if(targetFormName.elements.statusFlag.value=="uld_def_add_rep_success" ||
   targetFormName.elements.statusFlag.value=="uld_def_update_rep_success" ){

window.opener.targetFormName.elements.screenStatusValue.value = targetFormName.elements.screenStatusValue.value;
window.opener.targetFormName.elements.statusFlag.value = targetFormName.elements.statusFlag.value;
//window.opener.targetFormName.action = "uld.defaults.listulddmgdetails.do?screenReloadStatus="+"reload";
window.opener.targetFormName.elements.allChecked.value=targetFormName.elements.allChecked.value;
//Added by A-7359 for ICRD-258425 starts here
window.opener.targetFormName.elements.screenReloadStatus.value = "reload";
window.opener.targetFormName.action = "uld.defaults.listulddmgdetails.do";
//Added by A-7359 for ICRD-258425 ends here
window.opener.targetFormName.submit();
window.close();
}
else{


if(targetFormName.elements.flag.value=="NORECORDS"  ){
targetFormName.elements.btnOK.disabled="true";
targetFormName.elements.flag.value="";

}else
{
targetFormName.elements.btnOK.disabled=false;

}
//alert(targetFormName.flag.value);
if(targetFormName.elements.flag.value=="SCREENLOADNORECORDS"  ){
targetFormName.elements.btnOK.disabled="true";
targetFormName.elements.flag.value="";
if(targetFormName.elements.statusFlag.value=== "uld_def_add_rep")
{

var createrep = document.getElementById('createrep');
var deleterep = document.getElementById('deleterep');
createrep.onclick = function() { return true; }
deleterep.onclick = function() { return true; }

}
}
}
}


function onClickClose(frm){
window.close();
}

function selectNextDmgDetail(strLastPageNum, strDisplayPage) {

	if(targetFormName.elements.repairDate.value != "" &&
	!chkdate(targetFormName.elements.repairDate)){
	//alert('Invalid Repair Date');
	//added by A-4772 for ICRD-5569
	//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.msg.error.invalidrepairdate' />",1, self);
	  showDialog({msg :'<common:message bundle="maintainDamageReportResources" key="uld.defaults.msg.error.invalidrepairdate" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName
                         });
	return;
	}

	if(targetFormName.elements.statusFlag.value=="uld_def_mod_rep")
	{
	//alert(isFormModified());
	if(isFormModified()) {

		targetFormName.elements.flag.value = "Updated";
	}else{
	    	targetFormName.elements.flag.value = "";
	}  }

   	targetFormName.elements.replastPageNum.value = strLastPageNum;
   	targetFormName.elements.repdisplayPage.value = strDisplayPage;
   	var action = "uld.defaults.selectnextrepcommand.do";
     	var frm = targetFormName;

     	submitForm(frm, action);
 }

function createrep() {


	var frm=targetFormName;
	if(frm.elements.repairDate.value != "" &&
			!chkdate(frm.elements.repairDate)){
			//alert('Invalid Repair Date');
			//added by A-4772 for ICRD-5569
	//showDialog("<common:message bundle='maintainDamageReportResources' key='uld.defaults.msg.invalidrepairdate' />",1, self);
	  showDialog({msg :'<common:message bundle="maintainDamageReportResources" key="uld.defaults.msg.invalidrepairdate" scope="request"/>',
							type:1,
				            parentWindow:self,                                       
				            parentForm:targetFormName
                         });		
			return;
	}

	var action = "uld.defaults.addrep.do";
	submitForm(frm, action);
}


function deleterep() {
	var frm=targetFormName;

	var action = "uld.defaults.deleterep.do";
	submitForm(frm, action);
}



 function chkDate(date){

 	if(date.value==""){

 	return;
 	}
         else
 	{

 	checkdate(date, 'DD-MMM-YYYY');
 	}
}


function showDmgRefNoLov(){
var statusFlag=targetFormName.elements.statusFlag.value;

openPopUpWithHeight("uld.defaults.showdmgreflov.do?statusFlag="+statusFlag,"500");
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