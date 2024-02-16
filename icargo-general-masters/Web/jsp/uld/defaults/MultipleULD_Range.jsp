<%--
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: MaintainULD.jsp
* Date				: 18-Jan-2006
* Author(s)			: A-2001
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<!DOCTYPE html>	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<html:html locale="true">
<head>

<title>
	<common:message bundle="maintainuldResources" key="uld.defaults.multipleUld.lbl.title" />
</title>
<meta name="decorator" content="popup_panel">
<common:include src="/js/uld/defaults/MultipleULD_Range_Script.js" type="script"/>

</head>
<body >
	
	<bean:define id="form"
			 name="multipleULDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm"
		 toScope="page" />
	<business:sessionBean
		id="uldNos"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="uldNumbers"/>

	<div class="iCargoPopUpContent"> 
  
	<ihtml:form action="/uld.defaults.screenloadmultipleulds.do" styleClass="ic-main-form">
	  <ihtml:hidden property="onloadStatusFlag" />
	  <ihtml:hidden property="structuralFlag" />
	  <ihtml:hidden property="uldType" />
	  <ihtml:hidden property="ownerAirlineCode" />
	  <ihtml:hidden property="uldOwnerCode" />
	   <div class="ic-content-main" id="outerTab">
	 <div class="ic-head-container">	
	    <div class="ic-row">
			<span class="ic-page-title ic-display-none">  
               	<common:message key="uld.defaults.multipleUld.lbl.heading" />
			</span>	
		</div>
       <div class="ic-row">
					<div class="ic-filter-panel">
						<div class="ic-row">
								 <h4>
								  ULD Details
								 </h4>
						</div>
                 	<div class="ic-row">
							<div class="ic-input-container">
								<div class="ic-row">	
										<div class="ic-input ic-col-33 ic-label-40">	
											<label>
												<common:message key="uld.defaults.multipleUld.lbl.startNo" />
											</label>
											<ihtml:text property="startNo" componentID="TXT_ULD_DEFAULTS_MULTIPLEULD_STARTNO" name="multipleULDForm" maxlength="5" />
									    </div>
									<div class="ic-input ic-col-30 ic-label-40">	
											<label>
												<common:message key="uld.defaults.multipleUld.lbl.noofunits" />
											</label>
											<ihtml:text property="noOfUnits" componentID="TXT_ULD_DEFAULTS_MULTIPLEULD_NOOFUNITS" name="multipleULDForm" maxlength="5" />
									</div>
									<div class="ic-input ic-col-25" >	
											<ihtml:nbutton property="btnGenerate" componentID="BTN_ULD_DEFAULTS_MULTIPLEULD_GENERATE">
										<common:message key="uld.defaults.multipleUld.btn.generate" />
											</ihtml:nbutton>
									</div>
								</div>
									
							</div>
						</div>
						
				 </div>
 </div>		
 </div> 		
         			<div class="ic-main-container">	
					<div class="ic-row">
							<div class="ic-button-container">
							  
					  <a href="#" id="addLink" class="iCargoLink">
					  	<common:message key="uld.defaults.multipleUld.add" />
					  </a> | <a href="#" id="deleteLink" class="iCargoLink">
					  	<common:message key="uld.defaults.multipleUld.delete" />
					  </a>
                          </div>
					</div>
	<div class="ic-row">				
				<div class="tableContainer"  id="div1" style="width:100%;height:220px"> 
								<table class="fixed-header-table">
									<thead>
										   <tr class="iCargoTableHeadingLeft">
											<td width="15%"> <input type="checkbox" name="masterCheckbox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.masterCheckbox,this.form.selectedRows)" />
											</td>
											<td  width="85%" class="iCargoTableDataTd" >
												<common:message key="uld.defaults.multipleUld.lbl.uldno" />
											</td>

										  </tr>
								</thead>
                        <tbody id="uldTableBody">	
                        	<logic:present name="uldNos">
								<bean:define id="uldNos" name="uldNos" toScope="page"/>
								<logic:iterate id="uldNo" name="uldNos" indexId="index">
								<common:rowColorTag index="index">
									<tr bgcolor="<%=color%>">
									<ihtml:hidden property="uldOpFlag"/>
									<% String style="iCargoEditableTextFieldRowColor1";
									   if( index%2 == 0 ) {
											style="background :#F6F7F9;border : 0px solid #77879D;";
										}
										else {
											style="background :#EDF0F4;border : 0px solid #77879D;";
										}
									   %>
									<td  class="iCargoTableDataTd ic-center" >
										<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)" /></td>
									<td  class="iCargoTableDataTd" >
									<ibusiness:uld id="uldNos" uldProperty="uldNos" componentID="TXT_ULD_DEFAULTS_MULTIPLEULD_ULDNO" name="multipleULDForm" uldValue="<%=(String)uldNo%>"/>
									</td>

								  </tr>
								  </common:rowColorTag>
							</logic:iterate>
									<bean:define id="templateRowCount" value="0"/>
									<tr template="true" id="uldTemplateRow" style="display:none">
									<ihtml:hidden property="uldOpFlag"/>
									<td  class="iCargoTableDataTd ic-center" >
										<input type="checkbox" name="selectedRows" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)" /></td>
									<td  class="iCargoTableDataTd" >
									<ibusiness:uld id="uldNos" uldProperty="uldNos" componentID="TXT_ULD_DEFAULTS_MULTIPLEULD_ULDNO" name="multipleULDForm" indexId="templateRowCount" uldValue=""/>
									</td>

								  </tr>
			  			  </logic:present>
                        </tbody>
                     </table>
                    </div>
                </div>
           </div>
           
      <div class="ic-foot-container">	
					<div class="ic-row">
						<div class="ic-button-container">
			   <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_MULTIPLEULD_SAVE">
					<common:message key="uld.defaults.maintainUld.btn.Ok" />
				</ihtml:nbutton>
			   <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_MULTIPLEULD_CLOSE">
					<common:message key="uld.defaults.maintainUld.btn.Close" />
				</ihtml:nbutton>
			  </div>
                </div>
           </div>
		   </div>
      </ihtml:form>
   	</div>
	
	</body>
</html:html>


