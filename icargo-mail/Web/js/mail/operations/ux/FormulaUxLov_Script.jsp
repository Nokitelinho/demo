<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("formulaTable",{tableId:"formulaTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"50vh"});
                                
	var frm=targetFormName;
	with(frm){
		
		evtHandler.addEvents("btnClear","clearLOV(this)",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addIDEvents("btnformulaExpAdd","addFormulaExp()",EVT_CLICK);
		evtHandler.addEvents("btnDelete","deleteDetails()",EVT_CLICK);
		evtHandler.addEvents("btnOk","OkFunc(this)",EVT_CLICK);
		evtHandler.addEvents("offset","validateOffset(this)",EVT_BLUR);
		
	}
}

function addFormulaExp(){
	
	addTemplateRow('formulaTemplateRow','formulaTableBody','operationFlags');
	
}
function deleteDetails(){
	
	frm = targetFormName;
	if(validateSelectedCheckBoxes(frm,'selectCheckBox',1000000000,1))
	  deleteTableRow('selectCheckBox','operationFlags');
}

		
	
	
function OkFunc(){
	var index = targetFormName.elements.index.value;
	var frm = targetFormName;
	var checkValue = "false";
	var checkMultipleValue = "false";
	var selectedRows = 0;
	var newValue = '';
	var newValue1 = '';
	for(var i=0; i < frm.elements.basis.length-1; i++){
		if(frm.elements.selectCheckBox[i].checked == true){
			if(i>0){ //For checking logical operator is null
				if((frm.elements.operationFlags[i].value == "I") && (frm.elements.basis[i].value.length == 0 
					|| frm.elements.condition[i].value.length == 0
					|| frm.elements.component[i].value.length == 0
					|| frm.elements.logicOperator[i-1].value.length == 0)){
					 
						checkMultipleValue = "true";
				
				}else if (frm.elements.offset[i].value.length > 0){
					
				var IsNum ;
	            IsNum = IsItNumeric(frm.elements.offset[i].value);
	             if(!IsNum){
		        showDialog({msg:"Invalid offset", type:1, parentWindow:self, parentForm:targetFormName});
		         frm.elements.offset[i].select();
		          frm.elements.offset[i].focus();
		       return "STOP";
	            }
			    else
				{
		      validateTimeOffset1(frm.elements.offset[i]);
	             }	
					
				}else{
				
				
				
					if(frm.elements.offset[i].value.length > 0
						&& frm.elements.operator[i].value.length == 0){
							checkMultipleValue = "true";
					}
				}
			}else{
				if((frm.elements.operationFlags[i].value == "I") && (frm.elements.basis[i].value.length == 0 
					|| frm.elements.condition[i].value.length == 0
					|| frm.elements.component[i].value.length == 0)){
						checkValue = "true";
				}
				else if (frm.elements.offset[i].value.length > 0)
				{
                var IsNum ;
	            IsNum = IsItNumeric(frm.elements.offset[i].value);
	            if(!IsNum){
		        showDialog({msg:"Invalid offset", type:1, parentWindow:self, parentForm:targetFormName});
		        frm.elements.offset[i].select();
		        frm.elements.offset[i].focus();
		        return "STOP";
	             }
		         else 
		         {
		       validateTimeOffset1(frm.elements.offset[i]);
	             }	
            
			    }else{
			
			
					if(frm.elements.offset[i].value.length > 0
						&& frm.elements.operator[i].value.length == 0){
							checkMultipleValue = "true";
					}
				}
			}
		}else{
			selectedRows++;
		}
	}
	if(checkValue == "true"){
		showDialog({
					msg :'Select values for Basis,Condition,Component',
					type:1,
					parentWindow:self
					});
	    return;
	}else if(checkMultipleValue == "true"){
		showDialog({
					msg :'Select values for Basis,Condition,Component,Operator',
					type:1,
					parentWindow:self
					});
	    return;
	}else if(selectedRows == frm.elements.basis.length){
		showDialog({
					msg :'Select Rows.',
					type:1,
					parentWindow:self
					});
	    return;
	}else{
		for(var i=0; i < frm.elements.basis.length; i++){
			if(frm.elements.selectCheckBox[i].checked == true && frm.elements.operationFlags[i].value == "I"){
				if(newValue==''){
					newValue = frm.elements.basis[i].value
								+'~'+frm.elements.condition[i].value
								+'~'+frm.elements.component[i].value
								+'~'+frm.elements.operator[i].value
								+'~'+frm.elements.offset[i].value
								+'~ '+frm.elements.logicOperator[i].value+' ~';
				}else{
					newValue = newValue+frm.elements.basis[i].value
						+'~'+frm.elements.condition[i].value
						+'~'+frm.elements.component[i].value
						+'~'+frm.elements.operator[i].value
						+'~'+frm.elements.offset[i].value
						+'~ '+frm.elements.logicOperator[i].value+' ~';
				}
			}
		}
		//setting value to field
		var temp = newValue; 
		if(temp.search("ADD") != -1)
			newValue1=temp.replace(/ADD/g, "+");
		if(newValue1 !="")
		newValue1=newValue1.replace(/~/g, "");
		else
			newValue1=newValue.replace(/~/g, "");
        if(newValue.search(">") != -1)
			newValue = newValue.replace(/>/g,"GT");
		if(newValue.search("<") != -1)
			newValue = newValue.replace(/</g,"LT");
		var index= targetFormName.elements.index.value;
		
		if(targetFormName.elements.serviceResponseFlag.value == "Y"){
			window.parent.document.getElementById("srvformula"+index).value = newValue.substring(0,newValue.length-1);
			window.parent.document.getElementById("srvDisplayformula"+index).value = newValue1.substring(0,newValue1.length-1);
		}else if(targetFormName.elements.serviceResponseFlag.value == "N"){
			window.parent.document.getElementById("nonSrvformula"+index).value = newValue.substring(0,newValue.length-1);
			window.parent.document.getElementById("nonSrvDisplayformula"+index).value = newValue1.substring(0,newValue1.length-1);
		}else{
			window.parent.document.getElementById("bothSrvformula"+index).value = newValue.substring(0,newValue.length-1);
			window.parent.document.getElementById("bothSrvDisplayformula"+index).value = newValue1.substring(0,newValue1.length-1);
		}
		popupUtils.closePopupDialog();
	}
}
function clearLOV(obj){
	for(var i=0; i < targetFormName.elements.basis.length; i++){
			targetFormName.elements.selectCheckBox[i].checked == false;
			targetFormName.elements.basis[i].value = "";
			targetFormName.elements.condition[i].value = "";
			targetFormName.elements.component[i].value = "";
			targetFormName.elements.operator[i].value = "";
			targetFormName.elements.offset[i].value = "";
			targetFormName.elements.logicOperator[i].value = "";
	}
}
  function validateOffset(obj){
	var IsNum ;
	IsNum = IsItNumeric(obj.value);
	if(!IsNum){
		showDialog({msg:"Invalid offset", type:1, parentWindow:self, parentForm:targetFormName});
		obj.select();
		obj.focus();
		return "STOP";
	}else{
		validateTimeOffset1(obj);
	}
}  
 
function validateTimeOffset1(fld){
	fld.value=fld.value.trim();
	var _str="";
	if(fld.value.trim().length==0 )
	return;

	if(fld.value.charAt(0)=="+" || fld.value.charAt(0)=="-")
	{
		if(fld.value.length>4)
		{
			fnKeyGlobalErrors = true;
			showDialog({msg:"Offset exceeded maximum limit", type:1, parentWindow:self, parentForm:targetFormName});
			fld.select();
			fld.focus();
			return "STOP";

		}

		_str=fld.value.substring(1);
      return _str;

	}
	else{
		if(fld.value.length>4)
		{
			fnKeyGlobalErrors = true;
			//showDialog(fld.name +" exceeded maximum limit.",1,self);
			showDialog({msg:"Offset exceeded maximum limit", type:1, parentWindow:self, parentForm:targetFormName});
			fld.select();
			fld.focus();
			return "STOP";

		}
		 _str=fld.value;
		 return _str;

	}



	if(!IsItNumeric(_str)){
		fnKeyGlobalErrors = true;
		//showDialog(" Invalid Number.",1,self);
		showDialog({msg:"Offset is not a valid Number", type:1, parentWindow:self, parentForm:targetFormName});
		fld.select();
		fld.focus();
		return "STOP";
	}
}
	 


