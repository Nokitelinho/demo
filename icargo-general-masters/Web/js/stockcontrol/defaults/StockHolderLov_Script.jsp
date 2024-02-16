<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){

		evtHandler.addEvents("btList","onClickList(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","onClickClear(this.form)",EVT_CLICK);
		if(frm.elements.stationChecked){
			evtHandler.addEvents("stationChecked","singleSelect(this)",EVT_CLICK);
		}
		evtHandler.addEvents("btOk","doActionOk(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","doActionClose()",EVT_CLICK);

	}
	//heightOnScreenload();
}

function doActionClose(){
	window.close();
}

function heightOnScreenload(){
	
	var clientHeight = document.body.clientHeight;
	var _mainDivHeight = clientHeight;
	var _tableDivHeight=(clientHeight*57)/100;     //   Modified the height value by A-5200 for the BUG-ICRD-24913 
	document.getElementById('mainDiv').style.height=_mainDivHeight+'px';
	document.getElementById('div1').style.height=_tableDivHeight+'px';
}

function doActionOk(frm){
	if(targetFormName.elements.multiselect.value!='Y'){
		var formNumber;
		if(targetFormName.elements.formNumber.value=="0"){
			formNumber = "0";
			targetFormName.elements.formNumber.value = "";
		}else{
			formNumber = "1";
		}
		if(validateSelectedCheckBoxes(frm,'stationChecked',1,1)){
			var objCode=window.opener.document.getElementsByName(document.forms[0].codeName.value);		
			if(document.forms[0].typeName.value!=""){
				var objType = eval("window.opener.document.forms[formNumber]."+document.forms[0].typeName.value);
			}
			var str;
			for(var i=0;i<document.forms[0].elements.length;i++){
				if(document.forms[0].elements[i].type =='checkbox'){
					if(document.forms[0].elements[i].checked == true){
						str=document.forms[0].elements[i].value;
					}
				}
			}
	 
			var parentRow = targetFormName.elements.index.value;	
			var stockHolderType = document.forms[0].stkHolderType.value;
			objCode[parentRow].value = str;		
			if(document.forms[0].typeName.value!=""){
				if(objType && objType.options) {
				for(var i=0;i<objType.options.length;i++){
					if(objType.options[i].value == stockHolderType){
						objType.options[i].selected = true;
					}
					}
				}
			}
			window.close();
		}
	}else{
		var strmultiselect = targetFormName.elements.multiselect.value;
		var strformCount = targetFormName.elements.formCount.value;
		var strlovTxtFieldName = targetFormName.elements.lovTxtFieldName.value;
		var strlovDescriptionTxtFieldName = targetFormName.elements.lovDescriptionTxtFieldName.value;
		var strindex = targetFormName.elements.index.value;	
		var strDescriptionField;
		if(strlovDescriptionTxtFieldName!=""){
			strDescriptionField = strlovDescriptionTxtFieldName;
		}
		setValueForDifferentModes(strmultiselect,strformCount,strlovTxtFieldName,strDescriptionField,strindex);
	
	}
}

function onClickList(frm){
	if(frm.elements.stockHolderType.value==""){
		showDialog({	
			msg		:	"<common:message bundle="stockholderlovresources" key="stockcontrol.defaults.stockholderlov.pleaseenterstockholdertype" scope="request"/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		frm.elements.stockHolderType.focus();
		return;
	}
	var codeName = frm.elements.codeName.value;
	var typeName = frm.elements.typeName.value;
	var stockHolderTypeValue = frm.elements.stockHolderTypeValue.value;
	var stkHolderType = frm.elements.stkHolderType.value;
	var formNumber = frm.elements.formNumber.value;
	frm.elements.codeName.value = codeName;
	frm.elements.typeName.value = typeName;
	frm.elements.stkHolderType.value = stkHolderType;
	frm.elements.stockHolderTypeValue.value = stockHolderTypeValue;
	frm.elements.formNumber.value = formNumber;
	frm.action = 'stockcontrol.defaults.screenloadstockholderlov.do';
	frm.submit();
}

function onClickClear(frm){
	var codeName = frm.elements.codeName.value;
	var typeName = frm.elements.typeName.value;
	var stockHolderTypeValue = frm.elements.stockHolderTypeValue.value;
	var stkHolderType = frm.elements.stkHolderType.value;
	var formNumber = frm.elements.formNumber.value;
	frm.elements.codeName.value = codeName;
	frm.elements.typeName.value = typeName;
	frm.elements.stkHolderType.value = stkHolderType;
	frm.elements.stockHolderTypeValue.value = stockHolderTypeValue;
	frm.elements.formNumber.value = formNumber;
	frm.action = 'stockcontrol.defaults.clearstockholderlov.do';
	frm.submit();
}

function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	document.forms[0].lastPageNumber.value= strLastPageNum;
	document.forms[0].displayPage.value = strDisplayPage;
	var codeName = frm.elements.codeName.value;
	var typeName = frm.elements.typeName.value;
	var stockHolderTypeValue = frm.elements.stockHolderTypeValue.value;
	var stkHolderType = frm.elements.stkHolderType.value;
	var formNumber = frm.elements.formNumber.value;
	frm.elements.codeName.value = codeName;
	frm.elements.typeName.value = typeName;
	frm.elements.stkHolderType.value = stkHolderType;
	frm.elements.stockHolderTypeValue.value = stockHolderTypeValue;
	frm.elements.formNumber.value = formNumber;
	document.forms[0].action ="stockcontrol.defaults.screenloadstockholderlov.do";
	document.forms[0].submit();
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal){
	for(var i=0;i<document.forms[0].elements.length;i++){
		if(document.forms[0].elements[i].type =='checkbox'){
			if(document.forms[0].elements[i].checked == true){
				if(document.forms[0].elements[i].value != checkVal.value){
					document.forms[0].elements[i].checked = false;
				}
			}
		}
	}
}