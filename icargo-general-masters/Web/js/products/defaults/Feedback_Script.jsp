<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

window.onload = screenSpecificEventRegister;

function screenSpecificEventRegister(){
	var frm=targetFormName;
	//window.onunload=self.opener.childWindow=null;
	with(frm){
		evtHandler.addEvents("btnSend","saveAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);

		//evtHandler.addEvents("name","validateFields(this.form.name,-1,'Name',1,true,true)",EVT_BLUR);
		evtHandler.addEvents("address","validateTextArea('address')",EVT_BLUR);
		evtHandler.addEvents("comments","validateTextArea('comments')",EVT_BLUR);
		//added by a-5100 for  ICRD-18408 starts
	          evtHandler.addEvents("btnClose","setFocus(this.form)",EVT_BLUR);
	    //added by a-5100 for  ICRD-18408 ends
	}

	DivSetVisible(true);
	initialfocus();
}
//added by a-5100 for  ICRD-18408 starts
         function setFocus(frm){
             if(!event.shiftKey){	
					 if(!frm.elements.name.disabled){
                         frm.elements.name.focus();
                        }
              }
           }
//added by a-5100 for  ICRD-18408 ends

function validateTextArea(strAction){
	 if(strAction=="comments"){
		return validateMaxLength(document.forms[0].elements.comments,750);
	}else if(strAction=="address"){
		return validateMaxLength(document.forms[0].elements.address,450);
	}
}


function initialfocus(){
		 document.forms[0].elements.name.focus();
         if(document.forms[0].elements.saveSuccessful.value=="Y"){
		 		 	window.close();
		}
}


function saveAction(frm)
{
if(document.forms[0].elements.email.value!=""){
	if(validateEmail(document.forms[0].elements.email.value)){
		     frm.action="products.defaults.savefeedback.do";
		     frm.method="post";
		     frm.submit();
		     disablePage();
	 }else{
		//showDialog('<common:message bundle="feedbackResources" key="products.defaults.validemail.alert" scope="request"/>',1,self);
		showDialog({
			msg		:	'<common:message bundle="feedbackResources"	key="products.defaults.validemail.alert" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:frm
		});
		return;
	 }
}else{
  	frm.action="products.defaults.savefeedback.do";
    	frm.method="post";
     	frm.submit();
     	disablePage();
}
}