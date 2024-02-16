<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	with(frm){
		onLoad();
		evtHandler.addEvents("btOk","save(targetFormName)",EVT_CLICK);
		evtHandler.addEvents("btClose","windowClose()",EVT_CLICK);
		if(frm.elements.selectedValue!=null){
			evtHandler.addEvents("selectedValue","selectedValue(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("selectedValueAll","selectedValueAll(targetFormName)",EVT_CLICK);	
	}
}

function selectedValue(frm){
	
	toggleTableHeaderCheckbox('selectedValue', frm.elements.selectedValueAll);
}

function selectedValueAll(frm){
	
	updateHeaderCheckBox(frm, frm.elements.selectedValueAll, frm.elements.selectedValue);
}



function addValue(){	
	var frm=targetFormName;
	if(validateFormFields(frm)){
		if(validateValue(frm)){
			submitForm(frm,'customermanagement.defaults.maintainloyalty.addparametervalues.do');
		}
	}	
}

function delValue(){		
	var frm=targetFormName;	
	submitForm(frm,'customermanagement.defaults.maintainloyalty.delparametervalues.do');	
}

function save(frm){		
	if(validateFormFields(frm)){	
		if(validateValue(frm)){	
			submitForm(frm,'customermanagement.defaults.maintainloyalty.okparametervalues.do');	
		}
	}	
}

function onLoad()
{
	var frm=targetFormName;
	var loyaltyName=frm.elements.loyaltyName.value;
	if(frm.elements.closeWindow.value=="true")
	{
		
		window.opener.document.forms[1].loyaltyName.value=frm.elements.loyaltyName.value;
		window.opener.document.forms[1].action = "customermanagement.defaults.maintainloyalty.reloadmaintainloyalty.do?loyaltyName="+loyaltyName;
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.document.forms[1].submit(); 
		close();
	} 
}


function validateValue(frm) {   
	var amount = eval(frm.elements.valueInLov); 
	
	var opFlag = eval(frm.elements.parameterValueOpFlag); 
	var flag=false;
	
	if(amount != null && amount.length>1) {
		var size = amount.length;	
		
		for(var j=0; j<=size-1; j++) {
			if(opFlag[j].value!="D") {
			
				if(frm.elements.validateFlag.value=="Y"){
				var value = frm.elements.valueInLov[j].value;
				}else
				var value = frm.elements.valueInLov[j].value.toUpperCase();
				
				
				for(var i=0; i<=size-1; i++) {	
				
					if(i!=j){
						if(frm.elements.validateFlag.value=="Y"){
						var nextValue = frm.elements.valueInLov[i].value;
						}else
						var nextValue = frm.elements.valueInLov[i].value.toUpperCase();
						
						if(value == nextValue  &&
						   opFlag[i].value!="D") {					
							
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
	if(flag){
		showDialog({	
			msg		:	"Value "+value+" already present",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});	
		return false;
	}
	return true;	
}


function windowClose(){
close();
}

function validateFormFields(frm) {       	
       	if(validateValues(frm)) {
       		showDialog({	
				msg		:	"Value cannot be blank",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
       		return false;
       		
       	} else if(!validateValueCodes(frm)) {	
       		return false;       		
       	} 
       	else {
       		return true;
       	}
  }
  
  
  function validateValues(frm) {
            
     	var value = eval(frm.elements.valueInLov); 
     	var opFlag = eval(frm.elements.parameterValueOpFlag); 
     	if(value != null) {
     		var size = value.length;
     
     		if(size > 1) {
     			for(var i=0; i<size; i++) {
     
     				if(value[i].value == "" &&
  				opFlag[i].value!="D") {
     					return true;
     
     				}
     			}
     		} else {
     			if(value.value == "" &&
  			opFlag.value!="D") { 				
     				return true;
     			}
     		}
     	}
     	return false;
 }
 
 function validateValueCodes(frm) {
           
        if(frm.elements.validateFlag.value=="Y"){
      	var value = eval(frm.elements.valueInLov); 
      	var opFlag = eval(frm.elements.parameterValueOpFlag); 
      	if(value != null) {
      		var size = value.length;
      
      		if(size > 1) {
      			for(var i=0; i<size; i++) {
      
      				if(
  				opFlag[i].value!="D") {
      				if(!validate(value[i].value)){
      				return false;
      				}
      				
      				
      				}
      			}
      		} else {
      			
      			if(
  				opFlag.value!="D") {
      			
      			return validate(value.value);}
      		}
      	}
      	return true;
      	}
      	else
      	{
      	return true;
      	
      	}
 }
 
 
 function validate(val){			
			if (val.substring(val.length-1, val.length) == ".") {
					showDialog({	
						msg		:	"Invalid entry. This field cannot end with a '.'",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});

					return false;
			}
			 
			 
			var Valid = true;
			var asciiCode = "";
			var dotCount = 0;
			var odObj = new Array();
			var deciLength = 0;
			var intLength = 0;
			var deciPart = false;
			var intPart = false;
			var isAlpha = false;
			var isValidate = 2;
			var message = true;
	
			for (var i = 0; i < val.length; i++) {
					asciiCode = val.charCodeAt(i);
	
					var numeral       = validateCode(asciiCode, NUMERAL_ASCIISTART, NUMERAL_ASCIIEND);
					var alphabet_caps = validateCode(asciiCode, ALPHABET_CAPS_ASCIISTART, ALPHABET_CAPS_ASCIIEND);
					var alphabet      = validateCode(asciiCode, ALPHABET_ASCIISTART, ALPHABET_ASCIIEND);
					var sp_alphabet   = validateCode(asciiCode, SPECIAL_ASCIISTART, SPECIAL_ASCIIEND);
					var space 	  = validateCode(asciiCode, SPACE, SPACE);
					var hyphen 	  = validateCode(asciiCode, HYPHEN, HYPHEN);
					var plus    	  = validateCode(asciiCode, PLUS, PLUS);
					var forslash      = validateCode(asciiCode, FORSLASH, FORSLASH);
					var underscore    = validateCode(asciiCode, UNDERSCORE, UNDERSCORE);
					var colon 	  = validateCode(asciiCode, COLON, COLON);
	
					if (isValidate == 2) {
						if (!numeral) {
							if (asciiCode != 46) {
								Valid = false;
								break;
							} else
								dotCount = dotCount + 1;
						}
						if (dotCount > 1) {
							Valid = false;
							break;
						} else Valid = true ;
						
						
						if(arguments[6] != null) {
							intLength = arguments[6];
							
						}
						if(arguments[7] != null) {
							deciLength = arguments[7];
						}
						if(intLength != null && deciLength != null && dotCount == 1) {   
							odObj = val.split(".");
							if(intLength > 0) {
								if(odObj[0].length > intLength) {
									Valid = false;
									intPart = true;
									break;
								}
							}
							if(deciLength > 0) {
								if(odObj[1].length > deciLength) {
									Valid = false;
									deciPart = true;
									break;
								} 
							}
						}					
	
					} 
	
			}
	   		if (!Valid) {
				if (message) {
					showDialog({	
						msg		:	"Invalid  format.",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});														
					return false;
				}
				return false;
			} else {					
				return true;
		}
 
 
 }