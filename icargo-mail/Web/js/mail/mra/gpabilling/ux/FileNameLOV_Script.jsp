<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("fileNameTable",{tableId:"fileNameTable",hasChild:false,scrollingY:"150px"
                                });
var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnList","onList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);
		evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
		evtHandler.addEvents("btnOk","setValue()",EVT_CLICK);
	}							
	onScreenLoad();
}

function onScreenLoad(){
	targetFormName.elements.lovAction.value="screenload";
}

function onList(){
	submitForm(targetFormName,'mail.mra.gpabilling.ux.filenamelov.list.do?lovAction=list');
}

function onClose(){
	window.close();
}

function setValue(){

targetFormName.elements.lovAction.value="OK";
	var optionsArray = {
		codeFldNameInScrn:targetFormName.elements.lovTxtFieldName.value,
		descriptionFldNameInScrn:targetFormName.elements.lovDescriptionTxtFieldName.value,	
		index:targetFormName.elements.index.value,	
		isMultiSelect:targetFormName.elements.multiselect.value,
		delimitterStr:'&'
	}	
	lovUtils.setValueForDifferentModes(optionsArray);
	if(targetFormName.elements.lovAction.value=="OK"){
	
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();
		}
}


function singleSelectRow(checkVal){
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
	if(flag==0){
		checkVal="";
	}
	targetFormName.selectedValues.value=checkVal;
}
	
								
function preserveSelectedvalues(strlastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value=strlastPageNum;
	targetFormName.elements.displayPage.value=strDisplayPage;
	targetFormName.elements.lovAction.value="list";
	var action = "mail.mra.gpabilling.ux.filenamelov.list.do"; 
	submitForm(targetFormName,action);
}	
function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	targetFormName.elements.lovAction.value="list";
	var action = "mail.mra.gpabilling.ux.filenamelov.list.do"; 
	submitForm(targetFormName, action);
}						