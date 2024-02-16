<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
   	var frm=targetFormName;
    	with(frm){
		
		evtHandler.addEvents("btOk","submitFrm(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","window.close();",EVT_CLICK);
	}
}

function submitFrm(frm){
	if(validateSelectedCheckBoxes(frm,'suChecked',1,1)){
      	var textfiledObj=frm.textfiledObj.value;
		var formNumber=frm.formNumber.value;
		var textfiledDesc=frm.textfiledDesc.value;
		var str="";
		var array="";
		var desc="";
        var index=frm.rowCount.value;
		for(var i=0;i<targetFormName.elements.length;i++){
			if(targetFormName.elements[i].type =='checkbox'){
				if(targetFormName.elements[i].checked){
					array=targetFormName.elements[i].value;
					str =array;
				    desc ="";
				}
			}
		}
		if(index !=null){
		// For table
		//index++;
		var strCodeArray = eval("window.parent.document.forms["+formNumber+"]."+textfiledObj);
		//Modified by A-8160 for ICRD-311871
		if(window.parent.document.getElementById('popupContainerFrame').contentWindow.document.forms[1]){
	var strCodeArray=window.parent.document.getElementById('popupContainerFrame').contentWindow.document.forms[1].elements.location;
		}
			if(index!=0){
				for(var i=0;i<=strCodeArray.length;i++){
					if(i==index){
						strCodeArray[index].value=str;
					}
				}
			}else {
				strCodeArray[0].value=str;
			}
		}
		if(textfiledDesc!=""){
			var objDescArray = eval("window.opener.document.forms["+formNumber+"]."+textfiledDesc);
			if(index!=0){
				for(var i=0;i<=objDescArray.length;i++){
					if(i==index){
						objDescArray[index].value=str;
					}
				}
			}else {
				objDescArray[0].value=str;
			}
		}
		window.close();
    }
}

function singleCheckBoxSelect(checkVal)
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
	//targetFormName.selectedValues.value=checkVal;
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.lastPageNum.value= strLastPageNum;
	targetFormName.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.lov.screenloadfacilitycodelov.do";
	targetFormName.submit();
	
}

function submitClick(chkbox)
{			
	var textfiledObj=targetFormName.textfiledObj.value;
	var formNumber=targetFormName.formNumber.value;
	var textfiledDesc=targetFormName.textfiledDesc.value;
	var str="";
	var array="";
	var desc="";
	 array=chkbox;
	 str =array;
	 desc ="";
         var objCode = eval("window.opener.document.forms["+formNumber+"]."+textfiledObj);
	 objCode.value=str;
	 if(textfiledDesc!=""){
		var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledDesc);
		objDesc.value=desc;
	 }
	 window.close();
         self.opener.childWindow=null;	
}
//Modified by A-8160 for ICRD-311871
function setOnDoubleClick(location,strLovTxtFieldName,strLovDescriptionTxtFieldName ,arrayIndex){
	var optionsArray = {selectedCode:location,
		codeFldNameInScrn : strLovTxtFieldName,
		descriptionFldNameInScrn:strLovDescriptionTxtFieldName,
		index : arrayIndex
	}
	lovUtils.setValueOnDoubleClick(optionsArray);
}