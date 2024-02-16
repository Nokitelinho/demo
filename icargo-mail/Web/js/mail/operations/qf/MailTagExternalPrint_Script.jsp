<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	
	with(frm){

		screenLoad(frm);
	}

}

function screenLoad(form){
	if(form.elements.screenMode.value=='MAIL_TAG_LABEL_PRINT_GATEWAY'){
		var url = form.elements.redirectURL.value;
			var queryString = form.elements.mailTagLabelDetails.value;
			window.location.href = url + '?' + queryString;
	}	

}