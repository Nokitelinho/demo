<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister(){
		var frm=targetFormName;
		with(frm){
			evtHandler.addEvents("btnview","onClickView(this.form)",EVT_CLICK);
			evtHandler.addEvents("btnprint","onClickPrint(this.form)",EVT_CLICK);
			evtHandler.addEvents("btnViewDtl","submitAction(this.form,'view')",EVT_CLICK);
			evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
            evtHandler.addEvents("btnClose","setFocus()",EVT_BLUR);//added  by A-5274
	}
	
	applySortOnTable("prdCatalougeTable",new Array("None","String","String"));
	 initialfocus();

}
function closeScreen(){
	location.href = appPath + "/home.jsp";	
}
function setFocus(){
if(!event.shiftKey){
  initialfocus();
}
}
function callFunction(lastPg,displayPg){

	document.forms[1].action="products.defaults.screenloadproductcataloguelist.do?navigationMode=NAVIGATION";
	document.forms[1].elements.lastPageNumber.value = lastPg;
	document.forms[1].elements.displayPage.value = displayPg;
	document.forms[1].submit();
	disablePage();
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal)
{
	for(var i=0;i<document.forms[1].elements.length;i++)
	{
		if(document.forms[1].elements[i].type =='checkbox')
		{
			if(document.forms[1].elements[i].checked == true)
			{
				if(document.forms[1].elements[i].id != checkVal.id)
				{
					document.forms[1].elements[i].checked = false;
				}
			}
		}
	}

}

function submitAction(frm,strAction){
    if(validateSelectedCheckBoxes(frm,'productChecked',5,1)){
       for(var i=0;i<document.forms[1].elements.length;i++)
       		{
       			if(document.forms[1].elements[i].type =='checkbox')
       			{
       				if(document.forms[1].elements[i].checked)
       				{

       					//prdCode = document.forms[1].elements[i].id;
       					prdCode = document.forms[1].elements[i].value;
             			        var strAction="products.defaults.screenloadproductcatalogue.do";
       					var strUrl=strAction+"?code="+prdCode;
      	                                openPopUp(strUrl,800,450);

       				}
       			}
		}


     }

}
function initialfocus(){
/*var productChecked = document.getElementsByName('productChecked');
	if(document.forms[1].productChecked){
		if(!document.forms[1].productChecked.length){
		  document.forms[1].productChecked.focus();
		}else{
		  document.forms[1].productChecked[0].focus();
		}
	}  */	 
    if(document.getElementsByName('productChecked')){
	if(!document.getElementsByName('productChecked').length){
		  document.getElementsByName('productChecked').focus();
		}else{
	document.getElementsByName('productChecked')[0].focus();
	}
}

}

function onClickView(frm){
   if(validateSelectedCheckBoxes(frm,'productChecked',5,1)){

       for(var i=0;i<document.forms[1].elements.length;i++)
       		{
       			if(document.forms[1].elements[i].type =='checkbox')
       			{
       				if(document.forms[1].elements[i].checked)
       				{

       					var preview="true";

       					//prdCode = document.forms[1].elements[i].id;
       					prdCode = document.forms[1].elements[i].value;
             			        var strAction="/products.defaults.printProductDetails.do";
             			        document.forms[1].action = strAction;
             			        document.forms[1].elements.productCode.value = prdCode;
             			        document.forms[1].elements.hasPreview.value = preview;
             			        generateReport(document.forms[1],document.forms[1].action);

       				}
       			}
		}


     }

}

function onClickPrint(frm){
   if(validateSelectedCheckBoxes(frm,'productChecked',5,1)){

       for(var i=0;i<document.forms[1].elements.length;i++)
       		{
       			if(document.forms[1].elements[i].type =='checkbox')
       			{
       				if(document.forms[1].elements[i].checked)
       				{

       					var preview="false";
       					//prdCode = document.forms[1].elements[i].id;

       					prdCode = document.forms[1].elements[i].value;
             			        var strAction="/products.defaults.printProductDetails.do";
             			        document.forms[1].action = strAction;
             			        document.forms[1].elements.productCode.value = prdCode;
             			        document.forms[1].elements.hasPreview.value = preview;
             			        generateReport(document.forms[1],document.forms[1].action);
       				}
       			}
		}


     }

}