<%@ include file="/jsp/includes/js_contenttype.jsp" %>

	<%@ include file="/jsp/includes/tlds.jsp" %>

/* to call the product lov
var strAction = 'products.defaults.screenloadProductLov.do';
var lovValue='';	//product name
var textfiledObj='agents'; //product code
var formNumber = '1'; //form count
var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&formNumber="+formNumber+"&rowIndex="+rowCount;
var myWindow = window.open(strUrl, "LOV", 'width=590,height=360,screenX=100,screenY=30,left=250,top=100');
*/

	function screenSpecificEventRegister(){
		var frm=targetFormName;
		IC.util.widget.createDataTable("productDataTable",{tableId:"productDataTable",sorting:false,scrollingY:"34vh",});
		with(frm){
			evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
			evtHandler.addEvents("btnclear","lovUtils.clearLOVForm()",EVT_CLICK);
			evtHandler.addEvents("btnclose","window.close()",EVT_CLICK);		
			evtHandler.addEvents("btok","setValue()",EVT_CLICK);
		}
		DivSetVisible(true);
		initialfocus();
		if(frm.elements.selectedValues.value=="" && targetFormName.elements.btok)
			disableField(targetFormName.elements.btok);
		}
// commented the enableOk() function by A-5247 for BUG_ICRD-66909
	/*function enableOk(frm){
		alert(targetFormName.productChecked.checked);
		enableField(targetFormName.btok);
	}*/
/*	function traverse(obj,frm) {
		var productChecked=document.getElementsByName('productChecked');
		var selectedIndex=obj.parentNode.parentNode.parentNode.parentNode.rowIndex;
		var selectedCount=0;
		for(var i=0;i<productChecked.length;i++) {
			selectedCount=noOfSelected(productChecked[i],selectedCount);
			if(frm.singleSelect.value=="Y" && productChecked[i].checked==true && selectedIndex-1!=i) {
			productChecked[i].checked=false;
			}
		}
		if(selectedCount>0){
			frm.btok.style.backgroundColor=frm.btnclose.style.backgroundColor;
			frm.btok.style.color="white";
		}else if(frm.selectedValues.value=="" && targetFormName.btok){
			frm.btok.style.backgroundColor="#e0dbd9";
			frm.btok.disabled = true;
		}
	} 
*/
	

	function noOfSelected(obj,selectedCount){
		if(obj.checked==true) {
		selectedCount++;
		}
		return selectedCount;
	}
	
	function onClickList(){
		var frm = targetFormName;
		frm.elements.bookingDate.value="";
		submitForm(frm,'products.defaults.ux.screenloadProductLov.do')
	}

	function initialfocus(){
	      document.forms[0].elements.productName.focus();
	}

	function submitAction(frm,strAction){
			var codeName = document.forms[0].elements.productObject.value;
			var formNum =  document.forms[0].elements.formNumber.value;
			var bookingDate = document.forms[0].elements.bookingDate.value;
			var activeProducts = document.forms[0].elements.activeProducts.value;
			document.forms[0].elements.bookingDate.value = bookingDate;
			document.forms[0].elements.activeProducts.value = activeProducts;
			document.forms[0].elements.productObject.value=codeName;
			document.forms[0].elements.formNumber.value = formNum;
			var prdCodeField= document.forms[0].elements.productCodeField.value;
			document.forms[0].elements.productCodeField.value = prdCodeField;
			frm.action=strAction;
			document.forms[0].elements.sourceScreen.value=document.forms[0].elements.sourceScreen.value;
			frm.submit();
			disablePage();

	}


	function closeScreen(frm,strAction,actn,nextAction){

		if(actn=='OK'){
			if(validateSelectedCheckBoxes(frm,'productChecked',5,1)){
				frm.action=strAction;
				frm.submit();
				disablePage();

			}
		}else{
			frm.action=strAction;
			frm.submit();
			disablePage();

		}
		window.parent.document.forms[1].action=nextAction;
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
		close();
	}


	function submitFrm(codeObject,formNum)
	{
		var str = "";
		var prdCode="";

		for(var i=0;i<document.forms[0].elements.length;i++)
		{
			if(document.forms[0].elements[i].type =='checkbox')
			{
				if(document.forms[0].elements[i].checked)
				{
					str = str+","+document.forms[0].elements[i].value;
					prdCode = document.forms[0].elements[i].id;

					//alert("str===="+str);
				}
			}
		}
		str = str.substring(1,str.length);
		var rowIndex = eval(targetFormName.elements.rowIndex);
		if(rowIndex == null || rowIndex.value=='' || rowIndex.value ==  null){
		var objCode = eval("window.parent.document.forms["+formNum+"]."+codeObject);
		objCode.value=str;
		if(document.forms[0].elements.productCodeField.value!=""){
			var prdCodeField = eval("window.parent.document.forms["+formNum+"]."+document.forms[0].elements.productCodeField.value);
			prdCodeField.value=prdCode;
			}
		}else{

			var rowCnt = rowIndex.value;
			var objCode = eval("window.parent.targetFormName.elements."+codeObject);
			objCode[rowCnt].value=str;
			if(targetFormName.elements.productCodeField.value!=""){
				var prdCodeField = eval("window.parent.targetFormName"+targetFormName.elements.productCodeField.value);
				prdCodeField[rowCnt].value=prdCode;
			}
		}

	var invokeFunction = document.forms[0].elements.invokeFunction.value;
	if(window.parent) {
				if(invokeFunction!=null){
					try{
						eval("window.parent."+invokeFunction);
					}
					catch(e){
						//TODO
					}
				}
	}		
	document.forms[0].elements.invokeFunction.value = null;
		window.close();
        self.opener.childWindow=null;	
}
/**
 *  Method to populate value to parent screen on double click
 */
 
function setProductLovOnDblClick(codeObject,codeValue,formNum){

	//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix starts
	var objCode = eval("window.parent.targetFormName.elements."+codeObject);
	
	
	//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix ends
	//objCode.value=codeValue; 
	
	// Added by A-5253 as part of BUG ICRD-37266 Start
	//Added by A-5526 as part of ICRD-69468 start (Modified the changed done by A-5253)
	if(typeof(targetFormName.elements.rowIndex)=="number")
	{
	var rowIndex = eval(targetFormName.elements.rowIndex);     
	 var rowCnt = rowIndex.value;	
	 objCode[rowCnt].value=codeValue;        	
	}
	else
   {	
		objCode.value=codeValue;
	}
	//Added by A-5526 as part of ICRD-69468 end
	// Added by A-5253 as part of BUG ICRD-37266 End
	
	window.close();
}

function submitFrmForBooking(codeObject,priorityObj,tranModeObj,formNum){

                  var str = "";
		var prdCode="";

     //submitForm(targetFormName,"products.defaults.setProductLovValue.do");
     for(var i=0;i<document.forms[0].elements.length;i++)
     		{
     			if(document.forms[0].elements[i].type =='checkbox')
     			{
     				if(document.forms[0].elements[i].checked)
     				{
     				//alert(document.forms[0].elements[i].value);
     					str =document.forms[0].elements[i].value.split(",");
     					prdCode = document.forms[0].elements[i].id;

     					//alert("str===="+str);
     				

     		//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix starts
     		var objCode = eval("window.parent.targetFormName.elements."+codeObject);
     		//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix ends
     		objCode.value=str[0];
     		//alert(str[2]);

     		//var priorCode = eval("window.opener.document.forms["+formNum+"]."+priorityObj);
     		//priorCode.value=str[1];
     		//alert(tranModeObj);

     		//var transportMode = eval("window.opener.document.forms["+formNum+"]."+tranModeObj);
     		//transportMode.value=str[2];

     		if(document.forms[0].elements.productCodeField.value!=""){
     			//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix starts
     			var prdCodeField = eval("window.parent.targetFormName.elements.productCode.value");
     			window.parent.targetFormName.elements.productCode.value=prdCode;
     			//Modified by A-1927 @ NRT on 12-Jul-2007 for NCA Bug Fix ends
     			//alert(prdCodeField.value);
     			//alert(prdCodeField.value);
     		  } 
     		
     		 }
               
                }
     		
     	     }
     		


        //window.opener.document.forms[0].action=appPath+"/capacity.booking.reloadProductDetail.do";
        //window.opener.document.forms[0].submit();
        window.close();
}

function callFunction(lastPg,displayPg){
	var codeName = document.forms[0].elements.productObject.value;
	var formNum =  document.forms[0].elements.formNumber.value;
	var prdCodeField= document.forms[0].elements.productCodeField.value;
	var bookingDate = document.forms[0].elements.bookingDate.value;
	var activeProducts = document.forms[0].elements.activeProducts.value;
	document.forms[0].elements.bookingDate.value = bookingDate;
	document.forms[0].elements.activeProducts.value = activeProducts;
	document.forms[0].action="products.defaults.ux.screenloadProductLov.do";
	document.forms[0].elements.productObject.value = codeName;
	document.forms[0].elements.lastPageNumber.value = lastPg;
	document.forms[0].elements.displayPage.value = displayPg;
	document.forms[0].elements.formNumber.value = formNum;
	document.forms[0].elements.productCodeField.value = prdCodeField;
	document.forms[0].elements.sourceScreen.value=document.forms[0].elements.sourceScreen.value;
	document.forms[0].submit();
	disablePage();
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 * modified by A-5247 for BUG_ICRD-66909
 */
/*function singleSelect(checkVal)
{
	var checkval=checkVal.value;	
	var flag=0;
	for(var i=0;i<document.forms[0].elements.length;i++)
	{
		if(document.forms[0].elements[i].type =='checkbox')
		{
			if(document.forms[0].elements[i].checked == true)
			{
			 flag=1;			
				if(document.forms[0].elements[i].value != checkval)
				{
					document.forms[0].elements[i].checked = false;
				}
			}
		}
		if(flag==0)
		{
			checkVal="";
			disableField(targetFormName.btok);		
		}else {	
			enableField(targetFormName.btok);			
		}
	}

}*/


function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "products.defaults.ux.screenloadProductLov.do";   
	submitForm(targetFormName, action);
}

function preserveNavigationValues(strlastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value=strlastPageNum;
	targetFormName.elements.displayPage.value=strDisplayPage; 
	
	var action = "products.defaults.ux.screenloadProductLov.do";     
	submitForm(targetFormName, action);
}
function setValue(){
var optionsArray = {
		codeFldNameInScrn:targetFormName.elements.lovTxtFieldName.value,
		descriptionFldNameInScrn:targetFormName.elements.lovDescriptionTxtFieldName.value,	
		index:targetFormName.elements.rowIndex.value,	
		isMultiSelect:targetFormName.elements.multiselect.value		
	}
	//Commenting for ICRD-318846 (Below fix caused regression, after that framework changes were done as part of BUG_ICRD-318432_KiranSP_21Feb2019)
	//to fix
	// if(!window.parent.isReactContext){
		// window.isReactContext=false;
	// }
lovUtils.setValueForDifferentModes(optionsArray);
}
function preserveNavigationValues(strlastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value=strlastPageNum;
	targetFormName.elements.displayPage.value=strDisplayPage; 
	var action = "products.defaults.ux.screenloadProductLov.do";     
	submitForm(targetFormName, action);
}