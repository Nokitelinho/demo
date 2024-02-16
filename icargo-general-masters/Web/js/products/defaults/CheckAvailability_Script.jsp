<%@ include file="/jsp/includes/js_contenttype.jsp" %>

window.onload = screenSpecificEventRegister;

function screenSpecificEventRegister(){
	var frm=targetFormName;
	//window.onunload=self.opener.childWindow=null;
	with(frm){
		evtHandler.addEvents("btnCheck","check(this.form,'check')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);

		//added by a-5100 for  ICRD-18408 starts
	          evtHandler.addEvents("btnClose","setFocus(this.form)",EVT_BLUR);
	    //added by a-5100 for  ICRD-18408 ends
	}
	DivSetVisible(true);
	initialfocus();
}

function check(frm,str){

   frm.action="products.defaults.checkavailability.do";
   frm.submit();
   disablePage();
   }

function initialfocus(){

         document.forms[0].elements.origin.focus();
}
//added by a-5100 for  ICRD-18408 starts
         function setFocus(frm){
             if(!event.shiftKey){	
					 if(!frm.elements.origin.disabled){
                         frm.elements.origin.focus();
                        }
              }
           }
//added by a-5100 for  ICRD-18408 ends