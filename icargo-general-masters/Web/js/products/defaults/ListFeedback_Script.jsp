<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnView","selectAction(this.form,'view')",EVT_CLICK);
		evtHandler.addEvents("btnClose","selectAction(this.form,'close')",EVT_CLICK);
		evtHandler.addEvents("btnList","selectAction(this.form,'list')",EVT_CLICK);
		evtHandler.addEvents("btnClear","selectAction(this.form,'clear')",EVT_CLICK);

    //added by a-5100 for  ICRD-18408 starts
	evtHandler.addEvents("btnClose","setFocus(this.form)",EVT_BLUR);
	 //added by a-5100 for  ICRD-18408 ends           
	}
	applySortOnTable("feedbackTable",new Array("None","String","String","Date"));
	DivSetVisible(true);
	initialfocus();
}

//added by a-5100 for  ICRD-18408 starts
         function setFocus(frm){
             if(!event.shiftKey){	
					 if(!frm.elements.productName.disabled){
                         frm.elements.productName.focus();
                        }
              }
           }
//added by a-5100 for  ICRD-18408 ends
function initialfocus(){
      document.forms[1].elements.lovimage.focus();
}

function callFunction(lastPg,displayPg){
	document.forms[1].action="products.defaults.listfeedback.do";
	document.forms[1].elements.lastPageNumber.value = lastPg;
	document.forms[1].elements.displayPage.value = displayPg;
	document.forms[1].submit();
	disablePage();
}

function displayProductLov(strAction,lovValue,textfiledObj,formNumber,productCode){
    var index=0;
	var strUrl = strAction+"?productName="+lovValue+"&productObject="+textfiledObj+"&formNumber="+formNumber+"&productCodeField="+productCode+"&rowIndex="+index;
	//var myWindow = openPopUpWithHeight(strUrl,590);
	
	displayLOV(strUrl,'Y','Y',strUrl,document.forms[1].elements.productName.value,'productName','1','productName','',index);
}

function selectAction(frm,actionType)
{

      if(actionType=="list"){
	   if(!frm.elements.productName.disabled){
			 frm.elements.productName.focus();
			 }
             frm.action="products.defaults.listfeedback.do?countTotalFlag=YES";//Added by A-5201 as part from the ICRD-22065			 
             frm.method="post";
      	     frm.submit();
      	     disablePage();

      }
      if(actionType=="clear"){
	      frm.action="products.defaults.clearlistfeedback.do";
	      frm.method="post";
              frm.submit();
              disablePage();

      }
      if(actionType=="view"){
         if(validateSelectedCheckBoxes(frm,'checkbox',1,1)){
             for(var i=0;i<document.forms[1].elements.length;i++)
             		{
             			if(document.forms[1].elements[i].type =='checkbox')
             			{
             				if(document.forms[1].elements[i].checked)
             				{

             					prdCode = document.forms[1].elements[i].id;
                       				var strAction="products.defaults.viewfeedback.do";
             					var strUrl=strAction+"?code="+prdCode;
             					openPopUpWithHeight(strUrl,500);

             				}
             			}
      		}


            }

      }
	  if(actionType=="close"){
	      location.href = appPath + "/home.jsp";
      }
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
