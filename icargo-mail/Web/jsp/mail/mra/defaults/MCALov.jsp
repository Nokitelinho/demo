<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : MRA
* File Name          	 : MCALov.jsp
* Date                 	 : 25-May-2012
* Author(s)              : A-4823
*************************************************************************/
--%>

<%@ page language="java" %>
<%@ page import=" com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MCALovForm" %>
	
			
			
	
<html:html>
<head> 
	
	
		
	
			

	<meta name="decorator" content="popuppanelrestyledui">
		<title><common:message  bundle="mcalov"  key="mailtracking.mra.defaults.maintaincca.lbl.mcalov" scope="request"/></title>
		
		<common:include type="script" src="/js/mail/mra/defaults/MCALov_Script.jsp"/>
	</head>
	<body>
	
	
	
<bean:define id="MCALovForm"
	 name="MCALovForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MCALovForm"
	 toScope="page"/>
	 
 
	 
		 <bean:define id="strAction" name="MCALovForm"  property="lovaction" scope="page" toScope="page"/>
		 <div id="divmain" class="iCargoPopUpContent" >
		 <ihtml:form action="<%=(String)strAction%>"  styleClass="ic-main-form">
		<ihtml:hidden  property="lovaction"  />
		<ihtml:hidden  property="selectedValues"  />
		<ihtml:hidden  property="lastPageNum" />
		<ihtml:hidden  property="displayPage" />
		<ihtml:hidden  property="multiselect" />
		<ihtml:hidden  property="pagination" />
		<ihtml:hidden  property="formCount" />
		<ihtml:hidden  property="lovTxtFieldName" />
		<ihtml:hidden  property="lovDescriptionTxtFieldName" />
		<ihtml:hidden  property="lovNameTxtFieldName" />
		<ihtml:hidden  property="index" />	 
		<%String checkVal = "";%>
		<%String strLovNameTxtFieldName = "";%>
<!--LOV-->


<!--  Hidden to store the selections in case of pagination-->

			
				<span class="ic-page-title"><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.mcalov" scope="request"/></span>
					<div class="ic-head-container">
						<div class="ic-filter-panel">
							<div class="ic-input-container">
								<div class="ic-section ic-border">
									<div class="ic-row ">
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.mcano" scope="request"/></label>
											<ihtml:text property="ccaNum" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CCANUM"  maxlength="20"/>
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.origin" scope="request"/></label>
											<ihtml:text property="origin" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORIGIN" maxlength="4" />
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.dest" scope="request"/></label>
											<ihtml:text property="destination" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DEST" />
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.mailtype" scope="request"/></label>
											<ihtml:text property="category" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CATEGORY"  maxlength="20"/>
										</div>
									</div>
									<div class="ic-row ">
										
										
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.subclass" scope="request"/></label>
											<ihtml:text property="subclass" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_SUBCLASS"  maxlength="20"/>
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.year" scope="request"/></label>
											<ihtml:text property="year" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_YER"  maxlength="20"/>
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
											<label><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.dsn" scope="request"/></label>
											<ihtml:text property="dsnNumber" name="MCALovForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_DSN"  maxlength="20"/>
										</div>
										<div class="ic-input ic-split-25 ic-label-33">
										&nbsp;&nbsp;&nbsp;&nbsp;
										</div>
									</div>
									
									<div class="ic-row ic-label-45">
										
										<div class="ic-button-container">
											<ihtml:nbutton property="btnList" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_LSTBTN">
												<common:message  key="mailtracking.mra.defaults.maintaincca.lbl.list" />
											</ihtml:nbutton>
												<ihtml:nbutton property="btnClear" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLEARBTN">
												<common:message  key="mailtracking.mra.defaults.maintaincca.lbl.clear" />
											</ihtml:nbutton>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="ic-main-container">
					<logic:present name="MCALovForm" property="mcaLovPage" >
							<bean:define id="lovList" name="MCALovForm" property="mcaLovPage" toScope="page"/>
							<logic:present name="lovList">
								<bean:define id="multiselect" name="MCALovForm" property="multiselect" />

								<logic:equal name="MCALovForm" property="pagination" value="Y">
									<!-- -PAGINATION TAGS -->
									<bean:define id="lastPageNum" name="MCALovForm" property="lastPageNum" />
									<div class="ic-row">
										<div class="ic-col-55">
												<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList"
													display="label" labelStyleClass="iCargoResultsLabel" lastPageNum="<%=(String)lastPageNum %>" />
										</div>	
										<div class="ic-col-45">
											 <div class="ic-button-container">
												<common:paginationTag pageURL="javascript:preserveSelectedvalues('lastPageNum','displayPage')" name="lovList" display="pages"
													linkStyleClass="iCargoLink"
													disabledLinkStyleClass="iCargoLink"
													lastPageNum="<%=(String)lastPageNum%>"/>
											</div>
									</div>
									</div>
									
									<!--  END -->
								</logic:equal>
							</logic:present>
						</logic:present>
						<bean:define id="strFormCount" name="MCALovForm" property="formCount"  />
						<bean:define id="strMultiselect" name="MCALovForm" property="multiselect" />
						<bean:define id="strLovTxtFieldName" name="MCALovForm" property="lovTxtFieldName"  />
						<bean:define id="strLovDescriptionTxtFieldName" name="MCALovForm" property="lovNameTxtFieldName" />
						
					<!-- bean:define id="strlovCodeNameTxtFieldName" value="dsnNumber"/ -->

                        <bean:define id="strlovCodeNameTxtFieldName" name="MCALovForm" property="lovDescriptionTxtFieldName"/>
						
						
						
						
						<bean:define id="strSelectedValues" name="MCALovForm" property="selectedValues" />
						<bean:define id="arrayIndex" name="MCALovForm" property="index"/>
					<logic:present name="MCALovForm" property="lovNameTxtFieldName">
				<bean:define id="strLovNameTextFieldName" name="MCALovForm" property="lovNameTxtFieldName" />
				<%strLovNameTxtFieldName = (String)strLovNameTextFieldName;%>
				</logic:present>
					
			
			<!--list panel-->
				
						<div class="tableContainer" id="div1" style="height:200px;">
							<table id="lovListTable" class="fixed-header-table">
							
								<thead>
									<tr class="iCargoTableHeadingLeft" >
										<td width="5%"> &nbsp;</td>
										<td width="45%" ><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.mcano" scope="request"/></td>
										<td width="50%"><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.despatch" scope="request"/></td>
									</tr>
								</thead>
								<tbody>
										<logic:present name="MCALovForm" property="mcaLovPage">
									<bean:define id="lovList" name="MCALovForm" property="mcaLovPage" toScope="page"/>
									<logic:present name="lovList">
									<% int i=0;%>
											<logic:iterate id = "val" name="lovList" indexId="indexId">
												
													<tr>
													<logic:notEqual name="MCALovForm" property="multiselect" value="Y">
													
														<!--	<a href="#" ondblclick="setValueOnDoubleClick('<%=((CCAdetailsVO)val).getCcaRefNumber()%>','<%=((CCAdetailsVO)val).getDsnNo()%>',
																'<%= strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)"/>  -->
														<a href="#" ondblclick="setAllValuesOnDoubleClick('<%=((CCAdetailsVO)val).getCcaRefNumber()%>','<%=((CCAdetailsVO)val).getDsnNo()%>',
														'<%=((CCAdetailsVO)val).getCsgDocumentNumber()%>','<%= strLovTxtFieldName%>','<%=strlovCodeNameTxtFieldName%>','<%=strLovDescriptionTxtFieldName%>',<%=arrayIndex%>)"/>
														</logic:notEqual>
														<logic:equal name="MCALovForm" property="multiselect" value="Y">
														<td >
																<%
																if(((String)strSelectedValues).contains(((CCAdetailsVO)val).getCcaRefNumber())){ %>
																	<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getCcaRefNumber()%>"  checked="checked"/>
																<%}else{ %>
																	<input type="checkbox" name="selectCheckBox" value="<%=((CCAdetailsVO)val).getCcaRefNumber()%>"  />
																<% } %>
														</td>
														</logic:equal>
														<logic:notEqual name="MCALovForm" property="multiselect" value="Y">
																<td >
																	<% checkVal = ((CCAdetailsVO)val).getCcaRefNumber()+"\u00A5"+((CCAdetailsVO)val).getDsnNo()+"\u00A5"+((CCAdetailsVO)val).getCsgDocumentNumber();%>

																	
																<%

																if(   ((String)strSelectedValues).equals(((CCAdetailsVO)val).getCcaRefNumber()  )){ %>
																	<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');" checked="checked" />
																<%}else{ %>
																	<input type="checkbox" name="selectCheckBox" value="<%=checkVal%>"  onclick="singleSelect('<%=checkVal%>');"/>
																<% } %>


																</td>
														</logic:notEqual>
														<td class="iCargoTableDataTd">
														<bean:write name="val" property="ccaRefNumber"/>
														</td>
														<td class="iCargoTableDataTd">
														<bean:write name="val" property="dsnNo"/>
														</td>
														</tr>
																	
												</logic:iterate>
										</logic:present>
									</logic:present>
								</tbody>	
							</table>
						</div>			
					</div>	
							
				<div class="ic-foot-container">
					<div class="ic-button-container">
							
						 <%String arr[] ={"","",""};%>
						<%String emty="";%>
						<%System.out.println("inside jsp===>>>"+checkVal);%>
						<%if(checkVal!=null && ""!=checkVal){
						arr= checkVal.split("\u00A5");
						}
						else{
						arr[0]=emty;
						arr[1]=emty;
						arr[2]=emty;
						}
						%>
						<!--<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValue('<%=strLovTxtFieldName%>','<%=strLovDescriptionTxtFieldName %>',<%=arrayIndex%>)" /> -->
						<input type="button" name="btnOk" value="OK" class="iCargoButtonSmall" onclick="setValueofInvoiceNoAndCsgNum('<%=strMultiselect%>','<%=strFormCount%>','<%=strLovTxtFieldName%>','<%=strlovCodeNameTxtFieldName %>','<%=strLovDescriptionTxtFieldName %>','<%=arrayIndex%>','<%=arr[0]%>','<%=arr[1]%>','<%=arr[2]%>')" />
						<ihtml:button property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CANCEL" >
						<common:message key="mailtracking.mra.defaults.maintaincca.lbl.cancel" />
						</ihtml:button>
						</div>
				</div>

		
	   </ihtml:form>
	</div>
	</body>
</html:html>
