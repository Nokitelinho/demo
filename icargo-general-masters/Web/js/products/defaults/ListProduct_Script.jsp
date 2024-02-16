<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btList","selectAction(this.form,'products.defaults.listproducts')",EVT_CLICK);
		evtHandler.addEvents("btClear","selectAction(this.form,'products.defaults.clearlistproducts')",EVT_CLICK);
		evtHandler.addEvents("btnListSubProd","selectAction(this.form,'nextscreenload')",EVT_CLICK);
		evtHandler.addEvents("btnSaveAs","selectAction(this.form,'saveas')",EVT_CLICK);
		evtHandler.addEvents("btnInactivate","selectAction(this.form,'inactivate')",EVT_CLICK);
		evtHandler.addEvents("btnActivate","selectAction(this.form,'activate')",EVT_CLICK);
		evtHandler.addEvents("btnCreate","selectAction(this.form,'create')",EVT_CLICK);
		evtHandler.addEvents("btnDetails","selectAction(this.form,'details')",EVT_CLICK);
		evtHandler.addEvents("btnDelete","selectAction(this.form,'delete')",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("productScc","validateFields(this.form.productScc,-1,'SCC',1,true,true)",EVT_BLUR);
		initialfocus();
		DivSetVisible(true);
	} 
	applySortOnTable("productTable",new Array("None","String","String","String","String","Number","String","Date","Date"));
}

function closeScreen() {
	location.href = appPath + "/home.jsp";
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;	
	var MainDivHeight=((height*90)/100);	
	document.getElementById('pageDiv').style.height = MainDivHeight+'px';
	document.getElementById('div1').style.height = (MainDivHeight-245)+'px';
}

function submitListProducts(strLastPageNum,strDisplayPage){
	document.forms[1].elements.lastPageNum.value = strLastPageNum;
	document.forms[1].elements.displayPage.value = strDisplayPage;
	document.forms[1].action = "products.defaults.listproductspagination.do";
	document.forms[1].submit();
}

function selectAction(frm,actionType){
	if(actionType!="products.defaults.listproducts" && actionType!="create"&& actionType!="products.defaults.clearlistproducts"){
		if(frm.elements.rowCount.value == 0){
			showDialog({	
				msg		:	"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
	}
	if(actionType=="create"){
		var fromListProduct = frm.elements.screenMode.value;
		frm.action="products.defaults.viewmaintainprd.do?fromListProduct="+fromListProduct;
		frm.method="post";
	    frm.submit();
	    disablePage();
	}
	if(actionType=="products.defaults.listproducts"){
		if(isValid(frm)){
			frm.elements.lastPageNum.value= 0;
			frm.elements.displayPage.value = 1;
			frm.action="products.defaults.listproducts.do?countTotalFlag=YES";//Added by A-5201 as part from the ICRD-22065			
		}
		frm.method="post";
	    frm.submit();
	    disablePage();
	}
	if(actionType=="screenload"){
		frm.action="products.defaults.screenloadlistproducts.do";
		frm.method="post";
	    frm.submit();
	    disablePage();
	}
	if(actionType=="products.defaults.clearlistproducts"){
		frm.action="products.defaults.clearlistproducts.do";
		frm.method="post";
	    frm.submit();
	    disablePage();
	}
	if(actionType=="activate" || actionType=="inactivate"){
		var isChecked = 'N';
		var statusObj;
		var rateObj;
		if(frm.elements.rowCount.value == 1){
			if(frm.elements.checkBox.checked==false){
				showDialog({	
					msg		:	"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return;
			}else{
				nameObj = frm.elements.hiddenProductName0;
				codeObj = frm.elements.hiddenProductCode0;
				isChecked = 'Y';
				/*statusObj = frm.elements.productStatus0;
				rateObj = frm.elements.rateDefined0;*/
				jQuery('.iCargoTableDataRowSelected').each(function(index, obj) {
							statusObj = jQuery(obj).find('td')[3].innerText;
							rateObj = jQuery(obj).find('td')[4].innerText;
						});
				if(actionType=="activate"){
					if(statusObj.toString().indexOf('Active')!= -1){
						showDialog({	
							msg		:	"<common:message bundle="ListProduct" key="products.defaults.selectinactive.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
					if(rateObj.value == 'No'){
						showDialog({	
							msg		:	"<common:message bundle="ListProduct" key="products.defaults.ratenotdefined.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
					frm.action="products.defaults.activateproduct.do";
				}else{
					if(statusObj.toString().indexOf('Inactive')!= -1){
						showDialog({	
							msg		:	"<common:message bundle="ListProduct" key="products.defaults.selectactive.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
					frm.action="products.defaults.inactivateproduct.do";
				}
				if (actionType=="activate"){
					strConfirm='<common:message bundle="ListProduct" key="products.defaults.confirmactivate" scope="request"/>';
				}else{
					strConfirm = '<common:message bundle="ListProduct" key="products.defaults.confirminactivate" scope="request"/>';
				}
				showDialog({	
					msg		:	strConfirm,
					type	:	4, 
					parentWindow:self,
					parentForm:document.forms[1],
					dialogId:'id_1',
					onClose: function (result) {
						if(document.forms[1].elements.currentDialogOption.value == 'Y') {
							screenConfirmDialog(document.forms[1],'id_1');
						}else if(document.forms[1].elements.currentDialogOption.value == 'N') {
							screenNonConfirmDialog(document.forms[1],'id_1');
						}
					}
				});
			}
		}else{
			if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
				for(var i=0;i<frm.elements.checkBox.length;i++){
					if(frm.elements.checkBox[i].checked==true ){
						if(i>0&& isChecked == 'Y'){
							showDialog({	
								msg		:"<common:message bundle="ListProduct" key="products.defaults.selectsinglerow.alert" scope="request"/>",
								type	:	1, 
								parentWindow:self,
								parentForm:targetFormName,
							});
							return;
						}
						isChecked = 'Y';
						//var statusObj = eval('frm.elements.productStatus'+i);
						//var rateObj = eval('frm.elements.rateDefined'+i);
						var statusObj;
						var rateObj;
						jQuery('.iCargoTableDataRowSelected').each(function(index, obj) {
							statusObj = jQuery(obj).find('td')[3].innerText;
							rateObj = jQuery(obj).find('td')[4].innerText;
						});							
						if(actionType=="activate"){
							if(statusObj.indexOf('Active')!= -1){
								showDialog({	
									msg	:"<common:message bundle="ListProduct" key="products.defaults.selectinactive.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return;
							}
							if(rateObj== 'No'){
								showDialog({	
									msg	   :"<common:message bundle="ListProduct" key="products.defaults.ratenotdefined.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return;
							}
							frm.action="products.defaults.activateproduct.do";
						}else{
							if(statusObj.indexOf('Inactive')!= -1){
								showDialog({	
									msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectactive.alert" scope="request"/>",
									type	:	1, 
									parentWindow:self,
									parentForm:targetFormName,
								});
								return;
							}
							frm.action="products.defaults.inactivateproduct.do";
						}
					}
				}
				if(isChecked == 'N'){
					showDialog({	
						msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});
					return;
				}
				if (actionType=="activate"){
					strConfirm = 'Do you want to activate the product?';
					strConfirm='<common:message bundle="ListProduct" key="products.defaults.confirmactivate" scope="request"/>';
				}else{
					strConfirm = 'Do you want to inactivate the product?';
					strConfirm = '<common:message bundle="ListProduct" key="products.defaults.confirminactivate" scope="request"/>';
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
		}
	}
	if(actionType=="nextscreenload"){
		var isChecked = 'N';
		var nameObj;
		var codeObj;
		var startDateObj;
		var endDateObj;
		if(frm.elements.rowCount.value == 1){
			if(frm.elements.checkBox.checked==false){
				showDialog({	
					msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return;
			}else{
				nameObj = frm.elements.hiddenProductName0;
		  		codeObj = frm.elements.hiddenProductCode0;
		  		startDateObj = frm.elements.hiddenStartDate0;
		  		endDateObj = frm.elements.hiddenEndDate0;
		  		isChecked = 'Y';
			}
		}else{
		    for(var i=0;i<frm.elements.checkBox.length;i++){
				if(frm.elements.checkBox[i].checked==true ){
					if( i>0&& isChecked == 'Y'){
						showDialog({	
							msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectsinglerow.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
					isChecked = 'Y';
					nameObj = eval('frm.elements.hiddenProductName'+i);
					codeObj = eval('frm.elements.hiddenProductCode'+i);
					startDateObj = eval('frm.elements.hiddenStartDate'+i);
					endDateObj = eval('frm.elements.hiddenEndDate'+i);
			  	}
		     }
		}
	    if(isChecked == 'Y'){
	      	frm.elements.buttonStatusFlag.value="fromListProduct";
			frm.action="products.defaults.listproducts.listsubproducts.do?productName="+nameObj.value;
			frm.elements.productCode.value=codeObj.value;
			frm.elements.productName.value=nameObj.value;
			frm.elements.startDate.value=startDateObj.value;
			frm.elements.endDate.value=endDateObj.value;
		}else{
			showDialog({	
				msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
		  	return;
		}
		frm.method="post";
		frm.submit();
		disablePage();
	}
	if(actionType=="delete"){
		var isChecked = 'N';
		if(frm.elements.rowCount.value == 1){
			if(frm.elements.checkBox.checked==false){
				showDialog({	
					msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return;
			}else{
				nameObj = frm.elements.hiddenProductName0;
				codeObj = frm.elements.hiddenProductCode0;
				statusObj=frm.elements.productStatus0;
				isChecked = 'Y';
			}
		}else{
	       	for(var i=0;i<frm.elements.checkBox.length;i++){
				if(frm.elements.checkBox[i].checked==true ){
					if(i>0 && isChecked == 'Y'){
						showDialog({	
							msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectsinglerow.alert" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}else{
						isChecked = 'Y';
					}
					var nameObj = eval('frm.elements.hiddenProductName'+i);
					var codeObj = eval('frm.elements.hiddenProductCode'+i);
					var statusObj= eval('frm.elements.productStatus'+i);
					if(statusObj.value=='Active') {
						showDialog({	
							msg	    :"Product is in Active status. Cannot be deleted",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
						return;
					}
				}

	      	}
	    }
	    if(isChecked == 'Y'){
			frm.action="products.defaults.deleteproducts.do";
		}else{
			showDialog({	
				msg	    :"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
			return;
		}
		showDialog({	
			msg		:	"<common:message bundle="ListProduct" key="products.defaults.confirmdelete" scope="request"/>",
			type	:	4, 
			parentWindow:self,
			parentForm:document.forms[1],
			dialogId:'id_2',
			onClose: function () {
				screenConfirmDialog(document.forms[1],'id_2');
				screenNonConfirmDialog(document.forms[1],'id_2');
			}
		});
	}
	if(actionType=="saveas"||actionType=="details"){
		var isChecked = 'N';
		var nameObj;
		var codeObj;
		if(frm.elements.rowCount.value == 1){
			if(frm.checkBox.checked==false){
				showDialog({	
					msg	    :	"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return;
			}else{
				nameObj = frm.elements.hiddenProductName0;
				codeObj = frm.elements.hiddenProductCode0;
				isChecked = 'Y';
			}
		}else{
		for(var i=0;i<frm.elements.checkBox.length;i++){
			if(frm.elements.checkBox[i].checked==true ){
				if( i>0&& isChecked == 'Y'){
					showDialog({	
						msg	    :	"<common:message bundle="ListProduct" key="products.defaults.selectonlyonerow.alert" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					});
					return;
				}
				isChecked = 'Y';
				nameObj = eval('frm.elements.hiddenProductName'+i);
				codeObj = eval('frm.elements.hiddenProductCode'+i);
			  }
		}
	}	
	if(isChecked == 'Y'){
	    var mode;
	    var productCode;
		var fromListProduct = "listproductmode";
		if(actionType=="saveas"){
			frm.action="products.defaults.findProducts.do?fromListProduct="+fromListProduct;
			frm.elements.mode.value="saveas";
			frm.elements.productCode.value=codeObj.value;
		}else{
			frm.action="products.defaults.viewproductdetails.do?productCode="+codeObj.value+"&fromListProduct="+fromListProduct;
			frm.elements.productCode.value=codeObj.value;
		}
	}else{
		showDialog({	
			msg	    :	"<common:message bundle="ListProduct" key="products.defaults.selectarow.alert" scope="request"/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
	}
	frm.method="post";
	frm.submit();
	disablePage();
	}
}

function initialfocus(){
    document.forms[1].elements.productName.focus();
}

function isValid(frm){
	return true;
}

function displayProductLov(strAction,lovValue,textfiledObj,formNumber){
	var index=0;
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&formNumber="+formNumber+"&rowIndex="+index+"&sourceScreen=PDS002"+"&multiselect=N"+"&activeProducts=Y";
	displayLOV(strUrl,'Y','Y',strUrl,document.forms[1].elements.productName.value,'productName','1','productName','',index);
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal){
	for(var i=0;i<document.forms[1].elements.length;i++){
		if(document.forms[1].elements[i].type =='checkbox'&& document.forms[1].elements[i].name=="checkBox"){
			if(document.forms[1].elements[i].checked == true){
				if(document.forms[1].elements[i].value != checkVal.value){
					document.forms[1].elements[i].checked = false;
				}
			}
		}
	}
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){
	}
	if(frm.elements.currentDialogOption.value == 'Y') {
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


