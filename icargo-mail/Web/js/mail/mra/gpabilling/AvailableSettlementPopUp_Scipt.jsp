<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister()
{
	var frm=targetFormName;
	with(frm){
	evtHandler.addEvents("checkAll","updateHeaderCheckBox(this.form,targetFormName.checkAll,targetFormName.check)",EVT_CLICK);
	evtHandler.addEvents("btOK","oK(this.form)",EVT_CLICK);
	evtHandler.addEvents("btCreate","create(this.form)",EVT_CLICK);	
	evtHandler.addEvents("btCancel","closeScreen()",EVT_CLICK);
	}
onScreenLoad(frm);
}
function  onScreenLoad(frm){
if(targetFormName.elements.availableSettlement.value == 'exit'){
//alert(targetFormName.createFlag.value);
	targetFormName.elements.lastPageNum.value=0;
targetFormName.elements.displayPage.value=1;
var frmPopUp = "avail_popup";
var createFlag=targetFormName.elements.createFlag.value;
var displayPage=1;
targetFormName.elements.availableSettlement.value  = "";
var action = "mailtracking.mra.gpabilling.invoicesettlement.list.do?frmPopUp="+frmPopUp+"&displayPage="+displayPage+"&createFlag="+createFlag;
		//alert(action);
		window.opener.document.forms[1].action = action;
		window.opener.document.forms[1].submit();
		window.close();


	
		}
}
function oK(frm){
if(validateSelectedCheckBoxes(targetFormName,'check',1,1)){
	var selectedRow = "";
	var count=0;
	var chkcount = 0;
	var frm=targetFormName;
		var copyCheckBoxes = document.getElementsByName('check');
		chkcount = copyCheckBoxes.length;
		for (j = 0; j < chkcount; j++) {
				if (copyCheckBoxes[j].checked == true) {
					if(count > 0){																				
						selectedRow=selectedRow+",";
					}
					selectedRow=selectedRow+j;
					count = count+1;
				}
				}
			if(selectedRow.length >0){
           targetFormName.action = "mailtracking.mra.gpabilling.invoicesettlement.ok.do?check="+selectedRow;
           targetFormName.submit();
		   }
		   else{
			showDialog("Please select atleast one row",1,self);
			return;
		}
	}
}
function closeScreen(){
	close();
}
function create(frm){
 targetFormName.action = "mailtracking.mra.gpabilling.invoicesettlement.create.do?";
 targetFormName.submit();
}