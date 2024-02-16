<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		onLoad();
		
		evtHandler.addEvents("btList","doList(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btSave","doSave(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
		if(frm.selectedRows!=null){
			evtHandler.addEvents("selectedRows","selectedRows(this.form)",EVT_CLICK);
		}
		evtHandler.addEvents("selectedrowsAll","selectedrowsAll(this.form)",EVT_CLICK);
		
	}
}

function onLoad(){
	var frm = targetFormName;
	if(frm.elements.afterList.value=="Listed"){
		frm.elements.partyType.disabled = true;
		if(frm.elements.screenName.value!="screenLoad"){
			var addRowLink = document.getElementById('addRowLink');
			addRowLink.disabled=true;
			addRowLink.onclick = function() { return false; }
			var delRowLink = document.getElementById('delRowLink');
			delRowLink.disabled=true;
			delRowLink.onclick = function() { return false; }			
		}
	}else{
		var addRowLink = document.getElementById('addRowLink');
		addRowLink.disabled=true;
		addRowLink.onclick = function() { return false; }
		var delRowLink = document.getElementById('delRowLink');
		delRowLink.disabled=true;
		delRowLink.onclick = function() { return false; }
		if(frm.elements.screenName.value == "screenLoad"){
			frm.elements.btSave.disabled = true;
		}
	}
	
	if((!frm.elements.partyType.disabled) && (frm.elements.afterList.value !="Listed" )){
		frm.elements.partyType.focus();
	}
	else if(!document.getElementById('addRowLink').disabled){
		document.getElementById('addRowLink').focus();
	}
}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				if((!frm.elements.partyType.disabled)  
				  && (frm.elements.afterList.value !="Listed" )){
					frm.elements.partyType.focus();
				}
				else if(!document.getElementById('addRowLink').disabled){
					document.getElementById('addRowLink').focus();					 
		}
	}
}

function selectedRows(frm){
	toggleTableHeaderCheckbox('selectedRows', frm.selectedrowsAll);
}

function selectedrowsAll(frm){
	updateHeaderCheckBox(frm, frm.selectedrowsAll, frm.selectedRows);
}

function doList(frm){
	submitForm(frm,'uld.defaults.uldserviceability.listcommand.do');
}

function doClear(frm) {
	submitFormWithUnsaveCheck('uld.defaults.uldserviceability.clearcommand.do');
}

function doSave(frm) {
	if(validateServiceCode(frm)) {
		//showDialog("Service Code cannot be blank", 1, self, frm, 'id_7');
		showDialog({msg:"Service Code cannot be blank",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}
	if(validateDescription(frm)) {
		//showDialog("Description Field cannot be blank", 1, self, frm, 'id_7');
		showDialog({msg:"Description Field cannot be blank",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}
    	if(DuplicateServiceCode(frm)!="") {
		var dupl = DuplicateServiceCode(frm);
		//showDialog(dupl +" is already present", 1, self, frm, 'id_7');
		showDialog({msg:dupl +" is already present",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}    	
	updateOperationFlags(frm);
	submitForm(frm,'uld.defaults.uldserviceability.savecommand.do');
}
function updateOperationFlags(frm){
	
	var operationFlags = frm.elements.operationFlag;
	var servicecodes = frm.elements.serviceCode;
	var servicedescriptions = frm.elements.serviceDescription;
	for(var index = 0; index <  operationFlags.length;index++) {
		if(isElementModified(servicecodes[index]) || isElementModified(servicedescriptions[index])) {
			if("NOOP" != operationFlags[index].value && "I" != operationFlags[index].value && "D" != operationFlags[index].value){
				operationFlags[index].value = "U";
			}
		}
	}
	
}
function doClose(frm) {	
	if(frm.elements.screenName.value=="screenLoad"){	
		frm.elements.afterList.value="";
		submitForm(frm,'uld.defaults.closeaction.do');
	}
	else
	{	
	window.close();
	}
}

function addRow(){
	var frm=targetFormName;
	if(validateServiceCode(frm)) {
		//showDialog("Service Code cannot be blank", 1, self, frm, 'id_7');
		showDialog({msg:"Service Code cannot be blank",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}
    	if(validateDescription(frm)) {
		//showDialog("Description Field cannot be blank", 1, self, frm, 'id_7');
		showDialog({msg:"Description Field cannot be blank",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}
    	if(DuplicateServiceCode(frm)!="") {
		var dupl = DuplicateServiceCode(frm);
		//showDialog(dupl +" is already present", 1, self, frm, 'id_7');
		showDialog({msg:dupl +" is already present",
						type:1,
						parentWindow:self,                                       
						parentForm:frm					
                });
		return false;
    	}    	
	addTemplateRow('facilityTemplateRow','facilityTableBody','operationFlag');	
}


function delRow(){
	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1000000000000, 1);
	if (check){
		
		deleteTableRow('selectedRows','operationFlag');
	}
}

 /**
  * This function submits the form to preserve the current selected values while clicking next and prev.
  *
  * @param strlastPageNum - The last page number
  * @param strDisplayPage - The page which has to be displayed.
  */
 function singleSelect(checkVal)
 {
 	for(var i=0;i<document.forms[1].elements.length;i++)
 	{
 		if(document.forms[1].elements[i].type =='checkbox')
 		{
 			if(document.forms[1].elements[i].checked == true)
 			{
 				if(document.forms[1].elements[i].id != checkVal.id
 				&& document.forms[1].elements[i].name !="selectedRows"
 				&& document.forms[1].elements[i].name !="selectedrowsAll")
 				{
 					document.forms[1].elements[i].checked = false;
 				}
 			}
 		}
 	}
}

function validateServiceCode(frm) {
	var code = eval(frm.serviceCode);
	var opFlags=eval(frm.operationFlag);
	if(code != null) {
		var size = code.length;
		if(size > 1) {
			for(var i=0; i<size-1; i++) {
				if(code[i].value == "" && opFlags[i].value !="D" && opFlags[i].value !="NOOP") {
					return true;
				}
			}
		}
	}
	return false;
}

function validateDescription(frm) {
	var code = eval(frm.elements.serviceDescription);
	var opFlags=eval(frm.elements.operationFlag);
	if(code != null) {
		var size = code.length;
		if(size > 1) {
			for(var i=0; i<size-1; i++) {
				if(code[i].value == "" && opFlags[i].value !="D" && opFlags[i].value !="NOOP") {
					return true;
				}
			}
		}
	}
	return false;
}

function DuplicateServiceCode(frm) {
	var description = eval(frm.elements.serviceDescription);
	var dup = "";
	var opFlag = eval(frm.elements.operationFlag);
	var flag=false;
	if(description != null && description.length>1) {
		var size = description.length;
		for(var j=0; j<=size-1; j++) {
			if(opFlag[j].value!="D" && opFlag[j].value!="NOOP") {
				var facilityCode = frm.elements.serviceCode[j].value.toUpperCase();				
				for(var i=0; i<=size-1; i++) {
					if(i!=j){
						var next = frm.elements.serviceCode[i].value.toUpperCase();					
						if(facilityCode == next && opFlag[i].value!="D" && opFlag[i].value!="NOOP") {
							dup = facilityCode;
							flag=true;
							break;
						}
					}
				}
				if(flag){
					break;
				}
			}
		}
	}
	if(dup != "") {
		//alert(dup +"is duplicate");
	}
	return dup;
}
 
