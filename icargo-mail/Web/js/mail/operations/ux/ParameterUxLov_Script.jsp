<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	IC.util.widget.createDataTable("parameterTable",{tableId:"parameterTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"50vh"
                                });
	var frm=targetFormName;
	with(frm){
		
		
		//evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		//evtHandler.addEvents("btnOk","setValues(targetFormName.elements.formCount.value,targetFormName.elements.lovTxtFieldName.value,targetFormName.elements.lovDescriptionTxtFieldName.value,targetFormName.elements.index.value)",EVT_CLICK);
		evtHandler.addEvents("btnOk","OkFunc()",EVT_CLICK);
		evtHandler.addEvents("mailSubClassLOV","mailSubClassLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOV(this)",EVT_CLICK);
	}
	onload();
}
function setValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,index){
	var newCode= new Array();
	var newFlag= new Array();
	
	var strCodeArray = "";
	var strFlagArray = "";
	var frm = targetFormName;
	if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
		  strCodeArray=window.parent.document.getElementsByName(strLovTxtFieldName);
	}
	if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
		  strFlagArray=window.parent.document.getElementsByName(strLovDescriptionTxtFieldName);
	}
	
	for(var i=0; i < frm.elements.parameterCode.length; i++){
		if(frm.elements.selectCheckBox[i].checked == true){
			if(newCode != '' ){
				newCode	= newCode+frm.elements.parameterCode[i].value+" : "+frm.elements.parameterValue[i].value+"\n" ;
			}else{
				newCode	= frm.elements.parameterCode[i].value+" : "+frm.elements.parameterValue[i].value+"\n" ;
			}
			var exclude = document.getElementsByName("excludeFlag"+i);
			for(var j=0;j<exclude.length;j++){
				if(exclude[j].checked== true){
					if(newFlag !=''){
						newFlag = newFlag+","+exclude[j].value;
					}else{
						newFlag = exclude[j].value;
					}
				}
			}
		}
	}
		
			if(index != null){
		for(var i=0; i<=strCodeArray.length ; i++){
			if(i ==  index){
				if(strCodeArray[i] != null){
					strCodeArray[i].value=newCode.toUpperCase();	
					strFlagArray[i].value=newFlag;
					}
			}
				}
			}else{
		if(strCodeArray != null){
			strCodeArray.value=newCode.toUpperCase();	
			strFlagArray.value=newFlag;
		}
	}
	window.close();
}
function mailSubClassLOVfunc(obj){
	
	/* var mainAction = "mailtracking.defaults.ux.subclaslov.list.do";
				mainAction = mainAction+"?multiselect=N&pagination=Y&lovaction="+
				mainAction+"&code="+targetFormName.elements.parameterValue[2].value+"&screenMode=Navigate";
		submitForm(targetFormName,mainAction);	 */
	var optionsArray;
	var strUrl ='mailtracking.defaults.ux.subclaslov.list.do?formCount=1&screenMode=Navigate';
		optionsArray = {	
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Mail SubClass',
				codeFldNameInScrn			: 'parameterValue',
				descriptionFldNameInScrn	: '' ,
				index						: 2,
				closeButtonIds 				: ['btnClose','btnOk'],	
				dialogWidth					: 720,
				dialogHeight				: 565,
				fieldToFocus				: 'parameterValue',
				lovIconId					: 'mailSubClassLOV_btn',
				maxlength					: 2
			}
			lovUtils.displayLOV(optionsArray);
}
function onload(){
	if(targetFormName.elements.parameter.value != ""){
		var txtfield = targetFormName.elements.txtFieldName.value;
		var displayTxtfield = targetFormName.elements.dispTxtFieldName.value;//for client side display
		var excludeTxtfield = targetFormName.elements.excludeTxtFieldName.value;
		var index= targetFormName.elements.index.value;
		window.parent.document.getElementById(txtfield+index).value = targetFormName.elements.parameter.value;
		window.parent.document.getElementById(displayTxtfield+index).value = targetFormName.elements.displayParameter.value;
		window.parent.document.getElementById(excludeTxtfield+index).value = targetFormName.elements.excludeIncludeFlag.value;
		popupUtils.closePopupDialog();
	}
	if(targetFormName.elements.mailSubClass.value != ""){
		targetFormName.elements.parameterValue[2].value = targetFormName.elements.mailSubClass.value;
	}
}
function clearLOV(obj){
		targetFormName.elements.parameterValue[0].value = "";
		targetFormName.elements.parameterValue[1].value = "";
		targetFormName.elements.parameterValue[2].value = "";
		targetFormName.elements.parameterValue[3].value = "";
		targetFormName.elements.selectCheckBox[0].checked=false;
		targetFormName.elements.selectCheckBox[1].checked=false;
		targetFormName.elements.selectCheckBox[2].checked=false;
		targetFormName.elements.selectCheckBox[3].checked=false;
}
function OkFunc(){
	var index = targetFormName.elements.index.value;
	var serviceRespFlag=targetFormName.elements.serviceResponseFlag.value;
	var frm = targetFormName;
	var checkValue = "false";
	var selectedRows = 0;
	for(var i=0; i < frm.elements.parameterCode.length; i++){
		if(frm.elements.selectCheckBox[i].checked == true){
			if(frm.elements.parameterValue[i].value == null || frm.elements.parameterValue[i].value == ""){
				checkValue = "true";
			}
		}else{
			selectedRows++;
		}
	}
	if(checkValue == "true"){
		showDialog({
					msg :'Please Select values.',
					type:1,
					parentWindow:self
					});
	    return;
	}else if(selectedRows == frm.elements.parameterCode.length){
		showDialog({
					msg :'Please Select Rows.',
					type:1,
					parentWindow:self
					});
	    return;
	}else{
		setExcludeParameters();
		submitForm(targetFormName,"mail.operations.ux.mailperformance.incentiveconfiguration.parameter.ok.do?index="+index+"&serviceResponseFlag="+serviceRespFlag);
	}
}
function setExcludeParameters(){
	var frm = targetFormName;
	var excludeValues="";
	var parameterCode = frm.parameterCode;
	for(var index=0;index<parameterCode.length;index++){
		if(frm.elements.selectCheckBox[index].checked == true){
			var excludeRadioButton = document.getElementsByName("excludeFlag"+index);
			for(var j=0;j<excludeRadioButton.length;j++){
				if(excludeRadioButton[j].checked== true){
					if(excludeValues !=''){
						excludeValues = excludeValues+","+excludeRadioButton[j].value;
					}else{
						excludeValues = excludeRadioButton[j].value;
					}
				}
			}
		}
	}
	frm.excludeIncludeFlag.value = excludeValues;
}
function selctPramValues(index) {
	 	var fieldVal = targetFormName.elements.parameterValue[index].value;
		if(targetFormName.elements.parameterValue[index].value != null && targetFormName.elements.parameterValue[index].value != ''){
			 targetFormName.elements.selectCheckBox[index].checked = true;
		} else{
			 targetFormName.elements.selectCheckBox[index].checked = false;
		}
}
     //This function is for lov onclick to select the checkbox  
	 // This index is always giving the total row count
function selctPramValuesLov(index)  {  
	 targetFormName.elements.selectCheckBox[2].checked = true;
}
