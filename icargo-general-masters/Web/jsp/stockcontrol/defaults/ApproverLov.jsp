<%@ page language="java" %>

<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO" %>
<%@ page import = "com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO" %>

<html:html>
<head>
		
			
	
<bean:define id="form"
	name="ApproverLovForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ApproverLovForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="approver.title"/></title>

<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/stockcontrol/defaults/ApproverLov_Script.jsp" />
</head>
<body id="bodyStyle">
	



<business:sessionBean id="stockHoldersFromSession"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.maintainstockholder"
		method="get"
        attribute="prioritizedStockHolders" />
<div class="iCargoPopUpContent ic-masterbg" id="mainDiv" style="height:100%;overflow:auto;">		
<ihtml:form enctype="multipart/form-data" action="/stockcontrol.defaults.screenloadapproverlov.do" styleClass="ic-main-form">
<html:hidden property="displayPage" />
<html:hidden property="lastPageNum" />
<html:hidden property="isValueSelected" />
<bean:define id="submitParent"
	name="form"
	property="selectedValues"
	toScope="page" />
	<div class="ic-content-main">
		<span class="ic-page-title ic-display-none">
			<common:message key="stockholder.ApproverLOV"/>
		</span>	
	</div>
	<div class="ic-head-container">
		<div class="ic-filter-panel">		
			<div class="ic-row">
				<div class="ic-col-100">
					<div class="ic-input-container">
						<div class="ic-row">
							<div class="ic-input ic-split-25">
								<label>
									Type
								</label>	
								<ihtml:select property="stockHolderType"
								componentID="CMB_STOCKCONTROL_DEFAULTS_APPROVERLOV_STOCKHOLDERTYPE" >
									<logic:present name="stockHoldersFromSession">
										<bean:define id="stockHolderList" name="stockHoldersFromSession" />
											<html:option value=""><common:message key="combo.select"/></html:option>
												<logic:iterate id="priorityVO" name="stockHolderList" >
													<html:option value= "<%=((StockHolderPriorityVO)priorityVO).getStockHolderType()%>">
													<%=((StockHolderPriorityVO)priorityVO).getStockHolderDescription()%>
													</html:option>
												</logic:iterate>
									</logic:present>
								</ihtml:select>	
							</div>
							<div class="ic-input ic-split-25">
								<label>
									<common:message key="stockholder.Code"/>
								</label>	
								<ihtml:text property="code"
								componentID="TXT_STOCKCONTROL_DEFAULTS_APPROVERLOV_STOCKHOLDERCODE"
								maxlength="12"
								/>
							</div>
							<div class="ic-input ic-split-25">
								<label>
									<common:message key="stockholder.Description"/>
								</label>	
								<ihtml:text property="description"
								componentID="TXT_STOCKCONTROL_DEFAULTS_APPROVERLOV_DESCRIPTION"
								maxlength="12"
								/>
							</div>
                            <div class="ic-button-container">
									<ihtml:button property="btlist"
										componentID="BTN_STOCKCONTROL_DEFAULTS_APPROVERLOV_LIST" >
										<common:message key="approver.list"/>
									</ihtml:button>
									<ihtml:button property="btClear" accesskey="C" styleClass="btn-inline btn-secondary"
										componentID="BTN_STOCKCONTROL_DEFAULTS_APPROVERLOV_CLEAR" >
										<common:message key="approver.clear"/>
									</ihtml:button>		
								</div>
						</div>
						<!--<div class="ic-row">
							<div class="ic-col-100">
								
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<business:sessionBean id="stockHoldersPageFromSession"
		moduleName="stockcontrol.defaults"
		screenID="stockcontrol.defaults.maintainstockholder"
		method="get"
        attribute="pageStockLovVO" />
		<div class="ic-row" id="listTable">
			<div class="ic-col-100">
				<div class="ic-row">
					<div class="ic-col-50">
						<logic:present name="stockHoldersPageFromSession">

                        <common:paginationTag pageURL="javascript:submit('lastPageNum','displayPage')"
						name="stockHoldersPageFromSession"
						display="label"
						labelStyleClass="iCargoResultsLabel"
						lastPageNum="<%=form.getLastPageNum() %>" />
						</logic:present>
						<logic:notPresent name="stockHoldersPageFromSession">
									&nbsp;
						</logic:notPresent>				    
					</div>
					<div class="ic-col-50">
						<div class="ic-button-container paddR5">
							<logic:present name="stockHoldersPageFromSession">
						  <common:paginationTag 
						  linkStyleClass="iCargoLink"
					          disabledLinkStyleClass="iCargoLink"
						  pageURL="javascript:submit('lastPageNum','displayPage')"
						  name="stockHoldersPageFromSession"
						  display="pages"
						  lastPageNum="<%=form.getLastPageNum()%>"/>
						  </logic:present>

						  <logic:notPresent name="stockHoldersPageFromSession">
								&nbsp;
							</logic:notPresent>							
						</div>
					</div>
				</div>
				<div class="ic-row">
					<div class="tableContainer" id="div1" style="height:200px;">
							<table id="approverTable"  class="fixed-header-table">
							<thead>
					<tr class="iCargoTableHeadingCenter">
						<td class="iCargoTableHeaderLabel" style="width:5%" >&nbsp;</td>
						<td class="iCargoTableHeaderLabel" style="width:45%"><common:message key="stockholder.Code"/></td>
						<td class="iCargoTableHeaderLabel" style="width:50%"><common:message key="stockholder.Description"/></td>
					</tr></thead><tbody>


					<business:sessionBean id="stockHoldersPageFromSession"
							moduleName="stockcontrol.defaults"
							screenID="stockcontrol.defaults.maintainstockholder"
							method="get"
							attribute="pageStockLovVO" />
					<logic:present  name="stockHoldersPageFromSession">
						<bean:define id="pageList"  name="stockHoldersPageFromSession" />
						  <logic:iterate id="vo" name="pageList" indexId="nIndex">
											 

							
							
							<business:sessionBean id="stockApproverCodeFromSession"
							moduleName="stockcontrol.defaults"
							screenID="stockcontrol.defaults.maintainstockholder"
							method="get"
							attribute="stockApprovercode" />
							<bean:define id="stkApproverCodeFromSession"  name="stockApproverCodeFromSession" />
							<business:sessionBean id="idFromSession"
							moduleName="stockcontrol.defaults"
							screenID="stockcontrol.defaults.maintainstockholder"
							method="get"
							attribute="id" />
							<bean:define id="idFrmSession"  name="idFromSession" />
							
							<tr ondblclick="setValueOnDoubleClick('<%=((StockHolderLovVO)vo).getStockHolderCode() %>','','<%=stkApproverCodeFromSession%>','',0)">
						
								<td class="iCargoTableDataTd ic-center" >
									<ihtml:checkbox property="checkBox"
										value="<%=((StockHolderLovVO)vo).getStockHolderCode()%>"
										componentID="CHK_STOCKCONTROL_DEFAULTS_APPROVERLOV_CHECKBOX"/></td>
								<td class="iCargoTableDataTd"><bean:write name="vo" property="stockHolderCode"/></td>
								<td class="iCargoTableDataTd"><bean:write name="vo" property="stockHolderName"/></td>
							</tr>


						 </logic:iterate>
					</logic:present>

					</tbody></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container paddR5">
				<ihtml:button property="btok"
					componentID="BTN_STOCKCONTROL_DEFAULTS_APPROVERLOV_OK" >
					<common:message key="approver.ok"/>
				</ihtml:button>
				<ihtml:button property="btcancel"
					componentID="BTN_STOCKCONTROL_DEFAULTS_APPROVERLOV_CANCEL" accesskey="O" styleClass="btn-inline btn-secondary" >
					<common:message key="approver.cancel"/>
				</ihtml:button>
			</div>
		</div>
	</div>
<script language="javascript">
callFun('<%=(String)submitParent%>');
</script>
</ihtml:form>
</div>

			
		   
	</body>
</html:html>



