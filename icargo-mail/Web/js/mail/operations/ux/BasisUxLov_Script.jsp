<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ taglib uri="/WEB-INF/icargo-common.tld" prefix="common" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	//IC.util.widget.createDataTable("basisTable",{tableId:"basisTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"42vh",
                               // });
	var frm=targetFormName;
	with(frm){
		
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOV()",EVT_CLICK);
		//evtHandler.addEvents("btnOk","setValues(targetFormName.formCount.value,targetFormName.lovTxtFieldName.value,targetFormName.lovDescriptionTxtFieldName.value,targetFormName.index.value)",EVT_CLICK);
		evtHandler.addEvents("btnOk","OkFunc()",EVT_CLICK);
	}
}
/* function clearAction() {
	targetFormName.action = "mail.operations.ux.mailperformance.incentiveconfiguration.basis.clear";
	targetFormName.submit();
} */
function setValueForDifferentModes(strMultiselect, strFormCount, strLovTxtFieldName, strLovDescriptionTxtFieldName, arrayIndex){
	var optionsArray = {
				isMultiSelect:strMultiselect,
				codeFldNameInScrn:strLovTxtFieldName,
				descriptionFldNameInScrn:''	
			}
			lovUtils.setValueForDifferentModes(optionsArray);
}
function populateExpression(obj){

	var frm = targetFormName;
	var str =new Array();
	var uncheckValue = '';
	if(document.getElementById("selectedValues").value != null){
	  var newValue = document.getElementById("selectedValues").value;
	  str = newValue.split(",");
	  str = jquery.grep(str,function(n){ return n});//For removing empty array values
	}else{
var newValue = '';
		
	}
	var index = obj.value;
	if(frm.elements.selectCheckBox[index].checked == true){
			if(newValue==''){
			
			newValue = frm.elements.formula[index].value+",";
			str.push(frm.elements.formula[index].value);
			}else{
			if(str.indexOf(frm.elements.formula[index].value) == -1){
				
				if(frm.elements.operator[index].value != ''){
					str.push(frm.elements.operator[index].value);
				str.push(frm.elements.formula[index].value);
					newValue = newValue+frm.elements.operator[index].value+","+frm.elements.formula[index].value+",";
				}else{
					//frm.elements.selectCheckBox[index].checked == false;
					targetFormName.elements.selectCheckBox[index].checked=false;
					showDialog({
							msg :'Please Select Operator.',
						type: 1,
						parentWindow: self
							});
						return;
				}
			}
		}
	}else{//for removing rateline from expression on unticking checkbox
		
	
		for(var i=0;i<str.length;i+=2){
			if(i>0){
				if((str[i] == frm.elements.formula[index].value) 
					&& (str[i-1] == frm.elements.operator[index].value)){
					targetFormName.elements.operator[index].value="";
					continue;
				}else{
					if(uncheckValue.length==0){
						uncheckValue=str[i]+",";
					}else{
					uncheckValue=uncheckValue+","+str[i-1]+","+str[i]+",";
				}
				}
			}else{
				if(str[i] == frm.elements.formula[index].value){
					targetFormName.elements.operator[index].value="";
					continue;
				}else{
					uncheckValue=str[i]+",";
				}
			}
		}
		/* if(str.indexOf(frm.elements.formula[index].value) > 0){//search whether the rate line exist in exp
			if(newValue.indexOf(frm.elements.formula[index].value) >0){
				var ind = newValue.indexOf(frm.elements.formula[index].value);
				newValue=replaceAt(newValue,ind-2,"");//removing operator
				newValue=newValue.replace(frm.elements.formula[index].value, ""); //removing rate line
			}else{
				var ind = newValue.indexOf(frm.elements.formula[index].value);
				newValue=replaceAt(newValue,ind,"");//removing operator
				newValue=newValue.replace(frm.elements.formula[index].value, ""); //only removing rate line
			}
			str.splice(str.indexOf('frm.elements.formula[index-1].value'), 1);
			str.splice(str.indexOf('frm.elements.formula[index].value'), 1);
		} */
		}
	if( uncheckValue != ''){
		newValue = uncheckValue;
	}
	document.getElementById("selectedValues").value=newValue; 
	if(newValue.search(",") != -1)
			newValue=newValue.replace(/,/g, "");
	if(newValue.search("ADD") != -1)
		newValue=newValue.replace(/ADD/g, "+");
	document.getElementById("formulaPanel").value=newValue; 
}

function replaceAt(string, index, replace) {
  return string.substring(0, index) + replace + string.substring(index + 2);
}


function clearLOV(obj){
		targetFormName.elements.formulaPanel.value = "";
		targetFormName.elements.selectedValues.value = "";
		targetFormName.elements.operator[0].value = "";
		targetFormName.elements.operator[1].value = "";
		targetFormName.elements.operator[2].value = "";
		targetFormName.elements.operator[3].value = "";
		targetFormName.elements.selectCheckBox[0].checked=false;
		targetFormName.elements.selectCheckBox[1].checked=false;
		targetFormName.elements.selectCheckBox[2].checked=false;
		targetFormName.elements.selectCheckBox[3].checked = false;
}
function OkFunc(){
	var frm = targetFormName;
	var formulaValue = document.getElementById("formulaPanel").value; 
	var tempFormula = document.getElementById("selectedValues").value;
	if(formulaValue.search("ADD") != -1)
		tempFormula=tempFormula.replace(/ADD/g, "+");
	var index = frm.elements.index.value;
	if(frm.elements.serviceResponseFlag.value == "Y"){
		window.parent.document.getElementById("srvBasis"+index).value = formulaValue;
		window.parent.document.getElementById("tempSrvBasis"+index).value = tempFormula;
	}else if(frm.elements.serviceResponseFlag.value == "N"){
		window.parent.document.getElementById("nonSrvBasis"+index).value = formulaValue;
		window.parent.document.getElementById("tempNonSrvBasis"+index).value = tempFormula;
	}else{
		window.parent.document.getElementById("bothSrvBasis"+index).value = formulaValue;
		window.parent.document.getElementById("tempBothSrvBasis"+index).value = tempFormula;
	}
	popupUtils.closePopupDialog();
}


// added by A-9002 
function onChangeBasis(index) {	//alert ('index :='+index);
     	var fieldVal = targetFormName.elements.operator[index].value;
		// alert ('fieldVal :='+fieldVal);
		if(fieldVal != null && fieldVal != ''){
			targetFormName.elements.selectCheckBox[index].checked = true;
		} else{
		   	targetFormName.elements.selectCheckBox[index].checked = false; 
		}
		this.value = index;
		populateExpression(this);
		
}