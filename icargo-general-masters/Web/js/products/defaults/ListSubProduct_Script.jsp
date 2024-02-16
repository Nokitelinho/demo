<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnlist","selectAction(this.form,'listsubproducts')",EVT_CLICK);
		evtHandler.addEvents("btnclear","selectAction(this.form,'clearlistsubproducts')",EVT_CLICK);
		evtHandler.addEvents("btnInactivate","selectAction(this.form,'subinactivate')",EVT_CLICK);
		evtHandler.addEvents("btnActivate","selectAction(this.form,'subactivate')",EVT_CLICK);
		evtHandler.addEvents("btnDetails","selectAction(this.form,'maintainsubproductscreenload')",EVT_CLICK);
		evtHandler.addEvents("btnClose","close()",EVT_CLICK);
		if(frm.elements.checkBox){
			evtHandler.addEvents("checkBox","singleSelect(this)",EVT_CLICK);
		}
		evtHandler.addEvents("productScc","validateFields(this.form.elements.productScc,-1,'SCC',1,true,true)",EVT_BLUR);
		initialfocus();
		DivSetVisible(true);
	}
	applySortOnTable("subProductTable",new Array("CheckBox","String","String","String","String"));
}

/**************/

function selectAction(frm,actionType)
{
	if(actionType=="listsubproducts")
	{
		document.forms[1].elements.checkList.value="Y";
		frm.elements.lastPageNum.value= 0;
		frm.elements.displayPage.value = 1;
		frm.elements.productName.disabled = false;
		submitForm(frm,"products.defaults.listsubproducts.do");
	}
	if(actionType=="clearlistsubproducts")
	{
		frm.elements.productName.disabled = false;
		submitForm(frm,"products.defaults.clearlistsubproducts.do");
	}
	if(actionType=="subactivate" || actionType=="subinactivate")
		{
           if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
			var isChecked = 'N';
			var proCodeObj;
			var subProCodeObj;
			var versionNumberObj;
			var statusObj;
			if(frm.elements.rowCount.value == 1){
				if(frm.elements.checkBox.checked==false){
					showDialog({	
						msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectatleastonerow.alert" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					});
					return;
				}else{
					proCodeObj = frm.elements.hiddenProductCode0;
					subProCodeObj = frm.elements.hiddenSubProductCode0;
					versionNumberObj = frm.elements.hiddenVersionNumber0;
					statusObj=frm.elements.subProductStatus0;
					isChecked = 'Y';
					if(actionType=="subactivate"){
						 if(statusObj.value == 'Active' || statusObj.value == 'New')
						  {
								showDialog({	
									msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectinactive.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,						
								});
								 return;
						   }
					 frm.action="products.defaults.subactivate.do";
					  }else{
					if(statusObj.value != 'Active'){
								showDialog({	
									msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectactive.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,						
								});
								return;
						}
						frm.action="products.defaults.subinactivate.do";
		        	}
				}
			}else{
			for(var i=0;i<frm.elements.checkBox.length;i++){
				statusObj = eval('frm.elements.subProductStatus'+i);
				if(frm.elements.checkBox[i].checked==true )
		        	{
		        		if( i>0&& isChecked == 'Y'){
						showDialog({	
							msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectsinglerow.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,						
						});
						return;
					}
		        		isChecked = 'Y';
		        		if(actionType=="subactivate"){
		                 if(statusObj.value == 'Active' || statusObj.value == 'New'){
							showDialog({	
								msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectinactive.alert" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,						
							});
							return;
				 		}
		        	 		frm.action="products.defaults.subactivate.do";
		        	  	}else{
		        			if(statusObj.value != 'Active'){
								showDialog({	
									msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectactive.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,						
								});
								return;
							}
		        			frm.action="products.defaults.subinactivate.do";
		        		}
		        		proCodeObj = eval('frm.elements.hiddenProductCode'+i);
					subProCodeObj = eval('frm.elements.hiddenSubProductCode'+i);
				 	versionNumberObj = eval('frm.elements.hiddenVersionNumber'+i);
		        	}
			}
			if(isChecked == 'N'){
				showDialog({	
					msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectatleastonerow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,						
				});
				return;
		     }
		   }
			frm.elements.productCode.value=proCodeObj.value;
			frm.elements.subProductCode.value=subProCodeObj.value;
			frm.elements.versionNumber.value=versionNumberObj.value;
			if (actionType=="subactivate"){
				strConfirm='<common:message bundle="ListSubProduct"
						key="products.defaults.confirmactivate" scope="request"/>';
			}else{
				strConfirm='<common:message bundle="ListSubProduct"
					key="products.defaults.confirminactivate" scope="request"/>';
				}
	          	showDialog({	
					msg		:	strConfirm,
					type	:	4, 
					parentWindow:self,
					parentForm:document.forms[1],
					dialogId:'id_1',
					onClose: function () {
						screenConfirmDialog(document.forms[1],'id_1');
						screenNonConfirmDialog(document.forms[1],'id_1');
					}
				});				
   }
   return;
	}
if(actionType=="maintainsubproductscreenload")
	{
		//added now
		var fromListSubproduct = "listsubproductmode";
		if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
			var isChecked = 'N';
			var proCodeObj;
			var subProCodeObj;
			var versionNumberObj;
			if(frm.elements.rowCount.value == 1){
				if(frm.elements.checkBox.checked==false){
					showDialog({	
						msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectatleastonerow.alert" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					});
					return;
				}else{
					proCodeObj = frm.elements.hiddenProductCode0;
					subProCodeObj = frm.elements.hiddenSubProductCode0;
					versionNumberObj = frm.elements.hiddenVersionNumber0;
					isChecked = 'Y';
				}
			}else{
				for(var i=0;i<frm.elements.checkBox.length;i++){
					if(frm.elements.checkBox[i].checked==true )
					{
						if( i>0&& isChecked == 'Y'){
							showDialog({	
								msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectsinglerow.alert" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,						
							});
							return;
						}
						isChecked = 'Y';
						proCodeObj = eval('frm.elements.hiddenProductCode'+i);
						subProCodeObj = eval('frm.elements.hiddenSubProductCode'+i);
						versionNumberObj = eval('frm.elements.hiddenVersionNumber'+i);
					}
				}
			}
			if(isChecked == 'N'){
				showDialog({	
					msg		:	"<common:message bundle="ListSubProduct" key="products.defaults.selectatleastonerow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,						
				});
				return;
			}
			frm.elements.productCode.value=proCodeObj.value;
			frm.elements.subProductCode.value=subProCodeObj.value;
			frm.elements.versionNumber.value=versionNumberObj.value;
			var startDate = targetFormName.elements.startDate.value;
			var endDate = targetFormName.elements.endDate.value;
			frm.action="products.defaults.listsubproduct.viewdetails.do?fromListSubproduct="+fromListSubproduct+"&startDate="+startDate+"&endDate="+endDate;
	frm.elements.productName.disabled = false;
	frm.method="post";
	frm.submit();
	disablePage();
			}

		}
}
/*************/

function initialfocus(){

    document.forms[1].elements.status.focus();
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal){
	for(var i=0;i<document.forms[1].elements.length;i++){
		if(document.forms[1].elements[i].type =='checkbox'){
			if(document.forms[1].elements[i].checked == true){
				if(document.forms[1].elements[i].value != checkVal.value){
					document.forms[1].elements[i].checked = false;
				}
			}
		}
	}
}


function displayLOV(mainAction,strMultiselect,strPagination,strAction,strCode,strTitle,
		currentFormCount,lovValueTxtFieldName,strLovDescriptionTxtFieldName)
{
	var strUrl = mainAction+"?multiselect="+strMultiselect+"&pagination="+strPagination+"&lovaction="+
			strAction+"&code="+strCode+"&title="+strTitle+"&formCount="+currentFormCount+"&lovTxtFieldName="+
			lovValueTxtFieldName+"&lovDescriptionTxtFieldName="+strLovDescriptionTxtFieldName;
	var myWindow = openPopUp(strUrl,'500','350') ;//Added by A-5219 for ICRD-41909
}

function submitListSubProducts(strLastPageNum,strDisplayPage)
{
	document.forms[1].elements.lastPageNum.value= strLastPageNum;
	document.forms[1].elements.displayPage.value = strDisplayPage;
	document.forms[1].action ="products.defaults.listsubproductspagination.do";
	document.forms[1].submit();
}

function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){
	}

	if(frm.elements.currentDialogOption.value == 'Y') {
			frm.elements.productName.disabled = false;
			frm.method="post";
			frm.submit();
	        disablePage();

	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){
	}

	if(frm.elements.currentDialogOption.value == 'N') {
		return;

	}
}

function close(){
	var frm=document.forms[1];
	submitForm(frm,"products.defaults.closelistsubproduct.do");
}