<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("paTable",{tableId:"paTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"42vh",
                                });
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnList","lovUtils.callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearAction()",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeAction()",EVT_CLICK);
		evtHandler.addEvents("btnOk","setValue()",EVT_CLICK);
	}
	     targetFormName.elements.code.focus();
		 onScreenLoad();
}
function clearAction() {
	targetFormName.action = "mailtracking.defaults.ux.palov.clear.do";
	targetFormName.submit();
}

function setValueOnDoubleClick(strFormCount, strCode,strLovTxtFieldName, strLovDescriptionTxtFieldName,index){
	targetFormName.selectedValues.value = strCode;
	setValues(strFormCount,strCode, strLovTxtFieldName, strLovDescriptionTxtFieldName, index);
}
function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;    
    var action = "mailtracking.defaults.ux.palov.list.do"; 	
	submitForm(targetFormName,action);
	}
function preserveSelectedvalues(strlastPageNum,strDisplayPage){
	targetFormName.lastPageNum.value=strlastPageNum;
	targetFormName.displayPage.value=strDisplayPage;
	var action = "mailtracking.defaults.ux.palov.list.do"; 
	submitForm(targetFormName,action);
}
function setValue(){
	if(targetFormName.elements.screenMode.value=="Navigate" && targetFormName.elements.fromLOV.value !=''){
		closeAction();
		return;
	}
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
function singleSelectRow(checkVal)	//Added by A-8164 for ICRD-272988
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
function onScreenLoad(){
	if(targetFormName.elements.fromLOV.value==''){
		targetFormName.elements.screenMode.value='';
	}
	if(IC.util.common.isLov()
					&& targetFormName.elements.screenMode && targetFormName.elements.screenMode.value == "Navigate"){				
					var lovTitle = 'PA Code';
					var optionsArray = {				
						"lovTitle":lovTitle,					
						"actionType":"onload",
						"dialogWidth":600,
						"lovContainerHeight":850,
						"closeAction":"closeAction()"
					}
					lovUtils.setLovAttributesOnNavigate(optionsArray);											
					lovUtils.resetLovHeightAndPosition(optionsArray);
				}
}
function closeAction(){	
   if(IC.util.common.isLov()
			&& targetFormName.elements.screenMode && targetFormName.elements.screenMode.value == "Navigate"){ 
		if(targetFormName.elements.fromLOV.value=="officeOfExchange"){
						var concatenatedValue=targetFormName.selectedValues.value;					
						var mainAction = "";
						if (concatenatedValue != ""){					
							var codeAndValue=concatenatedValue.split("-");
                            mainAction = "mailtracking.defaults.ux.oelov.list.do";
							mainAction = mainAction+"?multiselect=N&pagination=Y&lovaction="+
							mainAction+"&poaCode="+codeAndValue[0]+"&screenMode=Navigate&code= ";
						}
						else{
						    mainAction = "mailtracking.defaults.ux.oelov.list.do";
							mainAction = mainAction+"?multiselect=N&pagination=Y&lovaction="+
							mainAction+"&screenMode=Navigate&code= ";
						}
					var lovTitle = 'Office Of Exchange';
						var optionsArrayNav = {				
							"lovTitle":lovTitle,					
							"actionType":"onclose",
							"dialogWidth":600,
							"lovContainerHeight":500
						}
						lovUtils.setLovAttributesOnNavigate(optionsArrayNav);	
						lovUtils.resetLovHeightAndPosition(optionsArrayNav);						
						targetFormName.elements.screenMode.value="";
						targetFormName.elements.fromLOV.value=""; 
						submitForm(targetFormName,mainAction);	
		        }
			}
			else{
			window.close();
		}
}