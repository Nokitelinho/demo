<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){

var frm=targetFormName;
with(targetFormName){

evtHandler.addEvents("btClose","closefn()",EVT_CLICK);
evtHandler.addEvents("btCreate","createfn(this.form)",EVT_CLICK);
evtHandler.addEvents("btOK","oK(this.form)",EVT_CLICK);
evtHandler.addIDEvents("currencylov","invokeLOV()",EVT_CLICK);


}

onscreenload();
}

function onscreenload(frm)
{


if(targetFormName.elements.flagFilter.value=="Y"){

IC.util.widget.createDataTable("container",{tableId:"container",hasChild:false,scrollingY:'22.4vh'
                                });
}								
if(targetFormName.elements.popupFlag.value == "false" ) {
if(targetFormName.elements.actionFlag.value=="CREATE"||targetFormName.elements.actionFlag.value=="OK"){

var actionFlag=targetFormName.elements.actionFlag.value;

var action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do?actionFlag="+actionFlag+"&displayPage=1"+"&lastPageNum=0";
		
		
		window.parent.document.forms[1].action = action;
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();

}
if(targetFormName.elements.createButtonFlag.value=="true")
{
	targetFormName.elements.btCreate.disabled=true;
	var actionFlag=targetFormName.elements.actionFlag.value;
var action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.list.do?actionFlag="+actionFlag+"&displayPage=1"+"&lastPageNum=0";
}
		}
		
		jquery('#tabs').tabs();
		
		/*jquery(document).ready(function(){
	
	jquery('ul.tabs li').click(function(){
		var tab_id = jquery(this).attr('data-tab');

		jquery('ul.tabs li').removeClass('current');
		jquery('.tab-content').removeClass('current');

		jquery(this).addClass('current');
		jquery("#"+tab_id).addClass('current');
	});

});*/
}



function closefn(){
targetFormName.elements.actionFlag.value="CLOSE";
popupUtils.closePopupDialog();
}

function createfn(frm){

var createAction="CREATE"
 targetFormName.action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.create.do?actionFlag="+createAction;
 targetFormName.submit();
}

function oK(frm){

var okAction="OK"
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
           targetFormName.action = "mailtracking.mra.gpabilling.ux.invoicesettlementmailbaglevel.ok.do?check="+selectedRow+"&actionFlag="+okAction;
           targetFormName.submit();
		   }
		   else{
			showDialog("Please select atleast one row",1,self);
			return;
		}
	}
}


function invokeLOV(){

	var optionsArray =getLOVOptions();
	if(optionsArray){
		//lovUtils.showLovPanel(optionsArray);
		lovUtils.displayLOV(optionsArray);
	           }
}


function getLOVOptions(){

var optionsArray;
var strUrl ='ux.showCurrency.do?formCount=1';
					optionsArray = {	
						mainActionUrl				: strUrl,
						isMultiSelect				: 'N' ,
						isPageable					: 'Y',
						paginationActionUrl			: strUrl,
						lovTitle					: 'Select Currency',
						codeFldNameInScrn			: 'settlementCurrency',
						descriptionFldNameInScrn	: '' ,
						index						: '0',
						closeButtonIds 				: ['CMP_Shared_Currency_UX_CurrencyLov_Close','btnOk'],	
						dialogWidth					: 600,
						dialogHeight				: 480,
						fieldToFocus				: 'settlementCurrency',
						lovIconId					: 'invnumberlov_btn',
						maxlength					: 10
						};
 
					return optionsArray;
}
	
	
	

