<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("subClassTable",{tableId:"subClassTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"42vh",
                                });
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnList","lovUtils.callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearAction()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		
		evtHandler.addEvents("btnOk","setValue()",EVT_CLICK);
		
	}onload();
}
function clearAction() {
	targetFormName.action = "mailtracking.defaults.ux.subclaslov.clear.do";
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
	if(targetFormName.elements.lovaction.value=="OK"){
		window.parent.IC.util.common.childUnloadEventHandler();
		//added by A-7815 as part of IASCB-27947
		if(window.parent.document.forms && window.parent.document.forms[1].name!="MailPerformanceForm") {
			window.parent.document.forms[1].submit();
			popupUtils.closePopupDialog();
		}
		}
}
function setValues(){
	targetFormName.elements.lovaction.value="OK";
	var optionsArray = {
		codeFldNameInScrn:targetFormName.elements.lovTxtFieldName.value,
		descriptionFldNameInScrn:targetFormName.elements.lovDescriptionTxtFieldName.value,	
		index:targetFormName.elements.index.value,	
		isMultiSelect:targetFormName.elements.multiselect.value,
		delimitterStr:'&'
	}	
	lovUtils.setValueForDifferentModes(optionsArray);
	if(targetFormName.elements.lovaction.value=="OK"){
	
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();
		}
	var optionsArray = {
		codeFldNameInScrn:strLovTxtFieldName,
		descriptionFldNameInScrn:strLovDescriptionTxtFieldName,	
		index:index,	
		isMultiSelect:"N"		
	}
	lovUtils.setValueForDifferentModes(optionsArray);
}


function RENAMED_setValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,index){
	debugger;
	console.log('is react context test from sub class lov ',window.parent.isReactContext);
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



						
						