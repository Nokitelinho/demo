<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("oeTable",{tableId:"oeTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"42vh",
                                });
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnList","lovUtils.callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearAction()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnOk","setValue()",EVT_CLICK);
		evtHandler.addIDEvents("airportCode","displayAirportLov()",EVT_CLICK);
		evtHandler.addIDEvents("poaCode","displayPACodeLov()",EVT_CLICK);
	}
	    targetFormName.elements.code.focus();
}
function clearAction() {
	targetFormName.action = "mailtracking.defaults.ux.oelov.clear.do";
	targetFormName.submit();
}
function setValueOnDoubleClick(strFormCount, strCode,strLovTxtFieldName, strLovDescriptionTxtFieldName,index){
	targetFormName.selectedValues.value = strCode;
	setValues(strFormCount,strCode, strLovTxtFieldName, strLovDescriptionTxtFieldName, index);
}
function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;    
	submitForm(targetFormName,targetFormName.action);
	}
function preserveSelectedvalues(strlastPageNum,strDisplayPage){
	targetFormName.lastPageNum.value=strlastPageNum;
	targetFormName.displayPage.value=strDisplayPage;
	submitForm(targetFormName,targetFormName.action);
}
function setValue(){
	targetFormName.elements.lovaction.value="OK";
	var optionsArray = {
		codeFldNameInScrn:targetFormName.elements.lovTxtFieldName.value,
		descriptionFldNameInScrn:targetFormName.elements.lovDescriptionTxtFieldName.value,	
		index:targetFormName.elements.index.value,	
		isMultiSelect:targetFormName.elements.multiselect.value,
		delimitterStr:'&'
	}	
	lovUtils.setValueForDifferentModes(optionsArray);
	/*Commented as part of ICRD-340199
	if(targetFormName.elements.lovaction.value=="OK"){
	
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();
		}*/
}
function setValues(strFormCount,strCode,strLovTxtFieldName,strLovDescriptionTxtFieldName,index){
	var optionsArray = {
		codeFldNameInScrn:strLovTxtFieldName,
		descriptionFldNameInScrn:strLovDescriptionTxtFieldName,	
		index:index,	
		isMultiSelect:'N',
		delimitterStr:'&'
	}	
	lovUtils.setValueForDifferentModes(optionsArray);
	//if(targetFormName.elements.lovaction.value=="OK"){
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();
		//}
}
function singleSelectRow(checkVal)	
{
	var flag=0;//if nothing is checked
	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].type =='checkbox')
		{
			_tr = targetFormName.elements[i].parentElement.parentElement;
			if(_tr.tagName=='A'){
				_tr =_tr.parentElement;
			}
			if(targetFormName.elements[i].checked == true)
			{
				if(_tr.getAttribute("prevClass")==null){
					_tr.setAttribute("prevClass",_tr.className);
				}
				_tr.className = "iCargoTableDataRowSelected";
				flag=1;
				if(targetFormName.elements[i].value != checkVal)
				{
					targetFormName.elements[i].checked = false;
					if(_tr.className == "iCargoTableDataRowSelected"){
						if(_tr.getAttribute("prevClass")!=null){
							_tr.className=_tr.getAttribute("prevClass");
						}
					}
				}
			}
			else if(targetFormName.elements[i].checked == false){
				if(_tr.className == "iCargoTableDataRowSelected"){
					if(_tr.getAttribute("prevClass")!=null){
						_tr.className=_tr.getAttribute("prevClass");
					}
				}
			}
		}
	}
	if(flag==0)
	{
		checkVal="";
	}
	targetFormName.selectedValues.value=checkVal;
}
 function displayAirportLov(){
  var mainAction = "ux.showAirport.do";
				mainAction = mainAction+"?multiselect=N&pagination=Y&lovaction="+
				mainAction+"&code="+targetFormName.elements.airportCode.value+"&screenMode=Navigate"+"&fromLOV=officeOfExchange";
				submitForm(targetFormName,mainAction);	
 }
  function displayPACodeLov(){
  var mainAction = "mailtracking.defaults.ux.palov.list.do";
				mainAction = mainAction+"?multiselect=N&pagination=Y&lovaction="+
				mainAction+"&code="+targetFormName.elements.poaCode.value+"&screenMode=Navigate"+"&fromLOV=officeOfExchange";
				submitForm(targetFormName,mainAction);	
}