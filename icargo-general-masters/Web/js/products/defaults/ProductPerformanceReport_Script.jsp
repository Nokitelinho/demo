<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){

		var frm=targetFormName;
		with(frm){alert(1);
			evtHandler.addEvents("btView","viewReport(this.form)",EVT_CLICK);
			evtHandler.addEvents("btPrint","printReport(this.form)",EVT_CLICK);
			evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);
			evtHandler.addEvents("btClose","location.href('home.jsp')",EVT_CLICK);

			//added by a-5100 for  ICRD-18408 starts
	          evtHandler.addEvents("btClose","setFocus(this.form)",EVT_BLUR);
		     frm.elements.productCode.focus();
	       //added by a-5100 for  ICRD-18408 ends		
	}
}
//added by a-5100 for  ICRD-18408 starts
         function setFocus(frm){
             if(!event.shiftKey){	
					 if(!frm.elements.productCode.disabled){
                         frm.elements.productCode.focus();
                        }
              }
           }
//added by a-5100 for  ICRD-18408 ends


function viewReport(frm){
	var frm =  document.forms[1];
	var isPreview ="true";
	frm.isView.value = isPreview;
	var strAction="/products.defaults.viewproductperformancereport.do";
	generateReport(frm,strAction);
}

function printReport(){
	var frm =  document.forms[1];
	var isPreview ="false";
	frm.isView.value = isPreview;
	var strAction="/products.defaults.viewproductperformancereport.do";
	generateReport(frm,strAction);
}

function clearScreen(){
	var frm =  document.forms[1];
	frm.action="products.defaults.screenloadproductperformancereport.do";
	frm.submit();
}
function closeFunction(){
	var frm=document.forms[1];
	frm.action= "home.do";
	frm.submit();
}