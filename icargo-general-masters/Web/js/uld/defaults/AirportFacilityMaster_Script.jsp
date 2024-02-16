<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	//onScreenloadSetHeight();
	stripedTable();
	addIEonScroll();
	with(frm){
		onLoad();
	
		//DivSetVisible(true);
		evtHandler.addEvents("airportCode","validateFields(this, -1, 'AirportCode', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("facilityCode","validateFields(this, -1, 'FacilityCode', 8, true, true)",EVT_BLUR);

		evtHandler.addEvents("btList","doList(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","doClear(this.form)",EVT_CLICK);
		evtHandler.addEvents("btSave","doSave(this.form)",EVT_CLICK);
		evtHandler.addEvents("btOk","doOk(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","doClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addIDEvents("airportlovorigin","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.airportCode.value,'airportCode','1','airportCode','',0)",EVT_CLICK);
		if(frm.elements.selectedRows!=null){
			evtHandler.addEvents("selectedRows","selectedRows(this.form)",EVT_CLICK);
		}
		
		evtHandler.addEvents("selectedrowsAll","selectedrowsAll(this.form)",EVT_CLICK);
		evtHandler.addEvents("content","singleDefaultContent(this)",EVT_CHANGE);
		evtHandler.addEvents("facility","facilityChange()",EVT_CHANGE);
		//added by a-3278 for ULD676 on 22Aug08
		evtHandler.addEvents("whsFacilityCode","whsFacilityCodeChange()",EVT_CHANGE);
		//a-3278 ends
		if(frm.elements.airportCode.disabled==false){
			frm.elements.facilityType.focus();
		}
		
	}
}
function onScreenloadSetHeight(){
 	jquery('#div1').height((((document.body.clientHeight)*0.79)-170));
	
}

function resetFocus(frm){	
 if(!event.shiftKey){ 
 				if(!frm.elements.airportCode.disabled 
					&& frm.elements.afterList.value != 'Listed'){
						
			frm.elements.airportCode.focus();
		}
				else{
					
					 if(document.getElementById('addRowLink').disabled == false) {
						document.getElementById('addRowLink').focus();
						}
				}				
	}
}

function onLoad(){
	var frm = targetFormName;
	if(frm.elements.afterList.value=="Listed"){
		frm.elements.airportCode.disabled = true;
		frm.elements.facilityType.disabled = true;
		frm.elements.airportlovorigin.disabled = true;
		if(frm.elements.screenName.value!="screenLoad"){
		var addRowLink = document.getElementById('addRowLink');
				addRowLink.disabled=true;
				addRowLink.onclick = function() { return false; }
				var delRowLink = document.getElementById('delRowLink');
				delRowLink.disabled=true;
		delRowLink.onclick = function() { return false; }
		frm.elements.airportCode.disabled = true;
		}
		if(!document.getElementById('addRowLink').disabled) {			
			document.getElementById('addRowLink').focus();			
		}
	}
	else{
		//frm.btSave.disabled = true;
		var addRowLink = document.getElementById('addRowLink');
		addRowLink.disabled=true;
		addRowLink.onclick = function() { return false; }
		var delRowLink = document.getElementById('delRowLink');
		delRowLink.disabled=true;
		delRowLink.onclick = function() { return false; }
		//alert("asdfada"+frm.screenName.value);
		if(frm.elements.screenName.value == "screenLoad"){
		frm.elements.btSave.disabled = true;
		}else if(frm.elements.screenName.value == "monitorULDStock"){
			frm.elements.airportCode.disabled = false;
		}else{
		//alert("Frm else");
		frm.elements.airportCode.disabled = true;
		}
	}
}

function selectedRows(frm){
	toggleTableHeaderCheckbox('selectedRows', frm.elements.selectedrowsAll);
}

function selectedrowsAll(frm){
	updateHeaderCheckBox(frm, frm.elements.selectedrowsAll, frm.elements.selectedRows);
}

function doList(frm){
	if(frm.elements.airportCode.value == ""){
		showDialog({msg:"Enter Airport Code", type:1, parentWindow:self, parentForm:frm, dialogId:'id_1'});
		return;
	}
	submitForm(frm,'uld.defaults.airportfacilitymaster.listcommand.do');
}

function doClear(frm) {
	//submitForm(frm,'uld.defaults.airportfacilitymaster.clearcommand.do');
	
	if(frm.elements.afterList.value=="Listed"){
	submitFormWithUnsaveCheck('uld.defaults.airportfacilitymaster.clearcommand.do');
	}
	else{
	
	submitForm(frm,'uld.defaults.airportfacilitymaster.clearcommand.do');
	}
	
}

function doSave(frm) {
	if(validateFacilityCode(frm)) {
		showDialog({msg:"Location Code cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
	if(validateDescription(frm)) {
		showDialog({msg:"Description Field cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
    	if(DuplicateFacilityCode(frm)!="") {
		var dupl = DuplicateFacilityCode(frm);
		showDialog({msg:dupl +" is already present",type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
    	getHiddenData();
	updateOperationFlags(frm);
	submitForm(frm,'uld.defaults.airportfacilitymaster.savecommand.do');
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

function doOk(frm){


	var facilitytypes="";
	var selectedUlds = "";
	var levelValues = "";
	var facilityvalues="";
	var facility="";
	var facilitytypes = document.getElementsByName('selectedRows');
	var selectedRows = document.getElementsByName('selectedRows');
	var selectedRowsLOV = document.getElementsByName('selectedRowsLOV');
	var selectedRow_Type = document.getElementsByName('selectedRow_Type');
	var selectedAirportCode = document.getElementsByName('selectedAirportCode');
	for(var i=0;i<selectedRows.length-1;i++) {
			if(selectedRows[i].checked)	{			
			if(levelValues != ""){
				selectedUlds= selectedRowsLOV[i].value; //selectedRowsLOV
				facilityvalues=selectedRow_Type[i].value;
				facility=facilityvalues+'-'+selectedUlds;
				levelValues=levelValues+','+facility;				
			}else {
					levelValues = selectedAirportCode[i].value+'&'+selectedRow_Type[i].value+'-'+selectedRowsLOV[i].value;
				}
			}
		}		
	//setMultipleValues(frm,'levelValue','','');
	window.opener.targetFormName.elements.levelValue.value = levelValues;
	window.close();

}

function updateOperationFlags(frm){
	var operationFlags = frm.elements.operationFlag;
	var facilityCodes = frm.elements.facilityCode;
	var descriptions = frm.elements.description;
	var facilityTypes = frm.elements.facility;
	var defaultFlags = frm.elements.defaultFlag;
	var contents = frm.elements.content;
	for(var index = 0; index<operationFlags.length;index++){
		if(isElementModified(facilityTypes[index]) || isElementModified(facilityCodes[index]) || isElementModified(descriptions[index]) || isElementModified(defaultFlags[index])
		|| isElementModified(contents[index])){
			if("NOOP" != operationFlags[index].value && "I" != operationFlags[index].value && "D" != operationFlags[index].value){
				operationFlags[index].value = "U";
			}
		}
	}
}
function addRow(){
	var frm=targetFormName;
	if(validateFacilityCode(frm)) {
		showDialog({msg:"Location Code cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
    	if(validateDescription(frm)) {
		showDialog({msg:"Description Field cannot be blank", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
    	if(DuplicateFacilityCode(frm)!="") {
		var dupl = DuplicateFacilityCode(frm);
		showDialog({msg:dupl +" is already present", type:1, parentWindow:self, parentForm:frm, dialogId:'id_7'});
		return false;
    	}
    	getHiddenData();
	//submitForm(frm,'uld.defaults.airportfacilitymaster.addcommand.do');
	addTemplateRow('facilityTemplateRow','facilityTableBody','operationFlag');
	var facility = document.getElementsByName('facility');
	var operationFlags = document.getElementsByName('operationFlag');
	var contents = document.getElementsByName('content');
	
	for(var i = 0;i<facility.length;i++){	
		if("I" == operationFlags[i].value){
			if(facility[i].value == "AGT" || facility[i].value == "RPR" || facility[i].value == "ULD"){
				contents[i].style.visibility = "hidden";				
    				facilityChange();
			}else{
				contents[i].style.visibility = "visible";				
    				facilityChange();
			}
		}
	}
}


function delRow(){

	var frm=targetFormName;
	var check = validateSelectedCheckBoxes(frm, 'selectedRows', 1000000000000, 1);
	if (check){
	getHiddenData();
		//submitForm(frm,'uld.defaults.airportfacilitymaster.deletecommand.do');
		deleteTableRow('selectedRows','operationFlag');
	}
}

function getHiddenData(){
	var flags = document.getElementsByName('operationFlag');
	var chkBoxFlag = document.getElementsByName('defaultFlag');
	var str ="";
	if(document.forms[1].chkBoxFlag){
		for(var i=0; i<flags.length; i++){
			if (chkBoxFlag.length > 1) {
				if(i==0){
					if(chkBoxFlag[i].checked){
						str=str.concat("Y");
					}else{
						str=str.concat("N");
					}
				}else{
					if(chkBoxFlag[i].checked){
						str=str.concat("-Y");
					}else{
						str=str.concat("-N");
					}
				}
			  }else{
			  	if(i==0){
			  		if(chkBoxFlag[i].checked){
	  					str=str.concat("Y");
	  				}else{
	  					str=str.concat("N");
	  				}
				}else{
					if(chkBoxFlag[i].checked){
						str=str.concat("-Y");
					}else{
						str=str.concat("-N");
					}
				}
			}
	  	}
		document.forms[1].chkBoxFlag.value=str;
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

function validateFacilityCode(frm) {
	var code = eval(frm.elements.facilityCode);
	var opFlags=eval(frm.elements.operationFlag);
	if(code != null) {
		var size = code.length;
		if(size > 1) {
			for(var i=0; i<size-1; i++) {
				if(code[i].value == "" && opFlags[i].value =="I") {
					return true;
				}
			}
		}
	}
	return false;
}

function validateDescription(frm) {
	var code = eval(frm.elements.description);
	var opFlags=eval(frm.elements.operationFlag);
	if(code != null) {
		var size = code.length;
		if(size > 1) {
			for(var i=0; i<size-1; i++) {
				if(code[i].value == "" && opFlags[i].value =="I") {
					return true;
				}
			}
		}
	}
	return false;
}

function DuplicateFacilityCode(frm) {
	var description = eval(frm.elements.description);
	var dup = "";
	var opFlag = eval(frm.elements.operationFlag);
	var flag=false;
	if(description != null && description.length>1) {
		var size = description.length;
		var contents = document.getElementsByName('content');	
		for(var j=0; j<=size-2; j++) {
			if(opFlag[j].value!="D" && opFlag[j].value!="NOOP") {
				var facilityCode = frm.elements.facilityCode[j].value.toUpperCase();
				var facility = frm.elements.facility[j].value.toUpperCase();		


				//Added under BUG_ICRD-21732_ArunKumarM_25Oct2012 starts
				 var content;
				if(contents[j].style.visibility=="hidden") {
				 content = frm.elements.contentVal[j].value.toUpperCase();	
				}
				else {
				 content = frm.elements.content[j].value.toUpperCase();	
				}
				//Added under BUG_ICRD-21732_ArunKumarM_25Oct2012 ends
				//alert(frm.facility[j].value);
				for(var i=j+1; i<=size-2; i++) {
					//if(i!=j){
						var next = frm.elements.facilityCode[i].value.toUpperCase();
						
						//Added under BUG_ICRD-21732_ArunKumarM_25Oct2012 starts
						var newVal = frm.elements.facility[i].value.toUpperCase();
                        var newContent;  
						if(contents[i].style.visibility=="hidden") {
				  newContent = frm.elements.contentVal[i].value.toUpperCase();	
				}
				else {
				  newContent = frm.elements.content[i].value.toUpperCase();	
				} 
												
                        //Added under BUG_ICRD-21732_ArunKumarM_25Oct2012 ends
						

                         														
												
						//removed  content == newContent
						if(facilityCode == next && opFlag[i].value!="D" && opFlag[i].value!="NOOP" && facility == newVal) {
							if(content!=""){
								if(content == newContent){
							dup = facilityCode;
							flag=true;
							break;
						}
							}else{
								dup = facilityCode;
								flag=true;
								break;
							}
						}
					//}
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
 function singleDefaultSelect(checkVal) {
	 var contents = document.getElementsByName('content');
	 var contentValue = document.getElementsByName('contentVal'); 
	 var chkBoxFlag = document.getElementsByName('defaultFlag');
	 var facility = document.getElementsByName('facility'); 
	 var chked = checkVal.id.split('-')[0];
	 var contentVal= "";
	 var facilityCode = facility[chked].value;
	
	 if(facilityCode == "AGT" || facilityCode == "RPR" || facilityCode == "ULD"){
		contentVal = contentValue[chked].value;
	 }else{
		contentVal = contents[chked].value;
	 }
	 if(checkVal.checked){
	 for(var i=0;i<contentValue.length;i++){	
 		if(facility[i].value == "AGT" || facility[i].value == "RPR" || facilityCode == "ULD"){
		if(contentValue[i].value == contentVal){				
			if(chkBoxFlag[i].id != checkVal.id){
					chkBoxFlag[i].checked = false;
				}
			}
		}else{		
		if(contents[i].value == contentVal){						
			if(chkBoxFlag[i].id != checkVal.id){
					chkBoxFlag[i].checked = false;
				}
			}
		}
		}
	}

}

 function singleDefaultContent(checkVal) {
	 var contents = document.getElementsByName('content');
	 var chkBoxFlag = document.getElementsByName('defaultFlag');
	 var contentVal= checkVal.value;
	 var chkFlag='N';
	for(var i=0;i<contents.length;i++){
		if(contents[i].value == contentVal){
			if(chkBoxFlag[i].checked){

				if(chkFlag=='N'){
					chkFlag = 'Y';
				}else{
					chkBoxFlag[i].checked = false;
				}
			}
		}


	}

}
function facilityChange(){
	var facility = document.getElementsByName('facility');
		var operationFlags = document.getElementsByName('operationFlag');
		var contents = document.getElementsByName('content');
		var descriptions =document.getElementsByName('description');
		var facilityCodes =document.getElementsByName('whsFacilityCode');
		var facCodes =document.getElementsByName('facilityCode');
		var defaultFlags =document.getElementsByName('defaultFlag');
		var facilityCodeValue = targetFormName.elements.facilityCodeVal;
		var contentValue = document.getElementsByName('contentVal'); 
		
		for(var i = 0;i<operationFlags.length;i++){
			if("I" == operationFlags[i].value){

				if(facility[i].value == "AGT" ){				
					contents[i].style.visibility = "hidden";
					//added by a-3278 for bug ULD704 on 29Aug08
					contentValue[i].value = "AGT";					
					//a-3278 ends
				}else if(facility[i].value == "RPR"){				
					contents[i].style.visibility = "hidden";
					//added by a-3278 for bug ULD704 on 29Aug08
					contentValue[i].value = facility[i].value;					
					//a-3278 ends
				}else if(facility[i].value == "ULD"){				
					contents[i].style.visibility = "hidden";					
					contentValue[i].value = facility[i].value;						
				}else{				
					contents[i].style.visibility = "visible";
					//added by a-3278 for bug ULD704 on 29Aug08
					contentValue[i].value = contents[i].value;
					//a-3278 ends
					
				}
				
//added by a-3278 for ULD676 on 22Aug08

				if(facility[i].value == "WHS"){	
					if(facilityCodeValue[0].value == ""){					
						showDialog({msg:"No WareHouses found for this airport", type:1, parentWindow:self, parentForm:targetFormName, dialogId:'id_7'});				
						facCodes[i].style.visibility = "hidden";
						facilityCodes[i].style.visibility = "hidden";
						descriptions[i].style.visibility = "hidden";
						contents[i].style.visibility = "hidden";
						defaultFlags[i].style.visibility = "hidden";
					}

					else{
					
						facilityCodes[i].style.visibility = "visible";						
						facCodes[i].style.visibility = "hidden";						
						facCodes[i].value = facilityCodes[i].value;
						descriptions[i].style.visibility = "visible";
						defaultFlags[i].style.visibility = "visible";
					}
				}else{				
					facilityCodes[i].style.visibility = "hidden";
					facCodes[i].style.visibility = "visible";
					descriptions[i].style.visibility = "visible";
						defaultFlags[i].style.visibility = "visible";
				
				}
//a-3278 ends
			}
	}
}
//added by a-3278 for ULD676 on 22Aug08
function whsFacilityCodeChange(){
	var operationFlags = document.getElementsByName('operationFlag');
	var facilityCodes =document.getElementsByName('whsFacilityCode');
	var facCodes =document.getElementsByName('facilityCode');
	for(var i = 0;i<operationFlags.length;i++){
			if("I" == operationFlags[i].value){			
			facCodes[i].value = facilityCodes[i].value;
			}
	
}
}
//a-3278 ends