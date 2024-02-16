<%@ include file="/jsp/includes/js_contenttype.jsp" %>


<%@ include file="/jsp/includes/tlds.jsp" %>

window.onload = screenSpecificEventRegister;

function screenSpecificEventRegister(){
	var frm=targetFormName;
	//window.onunload=self.opener.childWindow=null;
	with(frm){
		evtHandler.addEvents("btnSend","saveAction(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);

		DivSetVisible(true);

		//added by a-5100 for  ICRD-18408 starts
	          evtHandler.addEvents("btnClose","setFocus(this.form)",EVT_BLUR);
	    //added by a-5100 for  ICRD-18408 ends
	}
	//evtHandler.addEvents("name","validateFields(this.form.name,-1,'Name',1,true,true)",EVT_BLUR);
	//evtHandler.addEvents("friendName","validateFields(this.form.friendName,-1,'FriendName',1,true,true)",EVT_BLUR);
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


function initialfocus(){
    document.forms[0].elements.name.focus();
}


function saveAction(frm)
{
      if(frm.elements.email.value!="" && frm.elements.friendEmail.value!=""){
            if(validateEmail(frm.elements.email.value)){
	  	       if(validateEmail(frm.elements.friendEmail.value)){

	  			frm.action="products.defaults.saveemail.do";
	  			frm.method="post";
	  			frm.submit();
	  			disablePage();
	  		}else{

	  			//showDialog('<common:message bundle="sendEmailResources" key="products.defaults.validemail.alert" scope="request"/>',1,self);
				showDialog({
					msg		:	'<common:message bundle="sendEmailResources" key="products.defaults.validemail.alert" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:frm
				});
	  			//alert('Please enter a valid e-mail');
				frm.elements.friendEmail.focus();
				return;
	  		}

      	}else{
      	//showDialog('<common:message bundle="sendEmailResources" key="products.defaults.validemail.alert" scope="request"/>',1,self);
		showDialog({
			msg		:	'<common:message bundle="sendEmailResources" key="products.defaults.validemail.alert" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:frm
		});
		//alert('Please enter a valid e-mail');
		frm.elements.email.focus();
		return;
	}
      }
      else{
      			frm.action="products.defaults.saveemail.do";
	   			frm.method="post";
	   			frm.submit();
	  			disablePage();
      }


}