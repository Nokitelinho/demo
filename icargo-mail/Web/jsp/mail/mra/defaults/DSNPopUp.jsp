<%--************************************************************
* Project	 		: iCargo
* Module Code & Name            : mra-defaults
* File Name			: DSNSelectLov.jsp
* Date				: 08/28/2008
* Author(s)			: A-2391
 ****************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNPopUpForm" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO" %>


	
	
<html:html locale="true">
<head> 
	
		
			
	

	<title><common:message   bundle="despatchselectpopup" key="mailtracking.mra.defaults.dsnpopuptitle" scope="request"/></title>
	<meta name="decorator" content="popuppanelrestyledui"> <!-- Modified by A-8236 for ICRD-249058 -->
	<common:include type="script" src="/js/mail/mra/defaults/DSNPopUp_Script.jsp"/>

</head>

<body id="bodyStyle">
	
	
<business:sessionBean id="DSNPopUpVO"
     moduleName="mailtracking.mra.defaults"
     screenID="mailtracking.mra.defaults.dsnselectpopup"
     method="get"
attribute="despatchDetails"/>

<bean:define id="DSNPopUpForm" name="DSNPopUpForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNPopUpForm" toScope="page" />


<div id="divmain" class="iCargoPopUpContent ic-masterbg">
<ihtml:form action="/mailtracking.mra.defaults.despatchenquiry.popup.do" styleClass="ic-main-form">


<ihtml:hidden name="DSNPopUpForm" property="lastPageNum" />
<ihtml:hidden name="DSNPopUpForm" property="displayPage" />
<ihtml:hidden name="DSNPopUpForm" property="okFlag" />
<ihtml:hidden name="DSNPopUpForm" property="fromPage" />


<div class="ic-content-main">
				<div class="ic-head-container">	
					<span class="ic-page-title ic-display-none">
						<common:message   key="mailtracking.mra.defaults.dsnselect" scope="request"/>
					</span>
					<div class="ic-filter-panel">
						<div class="ic-row">
							<div class="ic-input ic-split-35">
								<label><common:message   key="mailtracking.mra.defaults.dsnno" scope="request"/></label>
								<ihtml:text property="code" maxlength="4" name="DSNPopUpForm" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DSNNO" />
							</div>
							<div class="ic-input ic-split-35">
								<label><common:message   key="mailtracking.mra.defaults.dsndate" scope="request"/></label>
								<ibusiness:calendar
								property="dsnFilterDate"
								componentID="CMP_MAILTRACKING_MRA_DEFAULTS_DESPATCHDATE_FILTER"
								type="image"
								id="dsnFilterDate"
								value="<%=DSNPopUpForm.getDsnFilterDate()%>" />
							</div>
							<div class="ic-input ic-split-30">
								<div class="ic-button-container">
									<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_LIST" >
										<common:message   key="mailtracking.mra.defaults.btn.btlist" />
									</ihtml:nbutton>
									<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_CLEAR">
										<common:message   key="mailtracking.mra.defaults.btn.btclear" />
									</ihtml:nbutton>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="ic-main-container">
					<div class="ic-row">
						<div class="ic-input ic-split-50 paddL0">
						<logic:present name="DSNPopUpVO">
							<common:paginationTag pageURL="mailtracking.mra.defaults.dsnpopup.list.do"
							name="DSNPopUpVO"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=((DSNPopUpForm)DSNPopUpForm).getLastPageNum()%>" />
						</logic:present>
						<logic:notPresent name="DSNPopUpVO">
							&nbsp;
						</logic:notPresent>
						</div>
						<div class="ic-input ic-split-50 paddR5">
							<div class="ic-button-container">
								<logic:present name="DSNPopUpVO">
									<common:paginationTag pageURL="javascript:submitPage1('lastPageNum','displayPage')"
									linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
									name="DSNPopUpVO" display="pages" lastPageNum="<%=(String)((DSNPopUpForm)DSNPopUpForm).getLastPageNum()%>"/>
								</logic:present>
								<logic:notPresent name="DSNPopUpVO">
									&nbsp;
								</logic:notPresent>							
							</div>
						</div>
					</div>
					<div class="ic-row">
						<div id="div1" class="tableContainer"  style="height:280px">
							<table id="lovListTable"  class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingCenter" >
										<td class="iCargoTableHeader" width="3%" rowspan="2"> &nbsp;</td>
										<td width="25%"  class="iCargoLabelleftAligned">
										<common:message   key="mailtracking.mra.defaults.blgbasis" scope="request"/>
										</td>
										<td width="15%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.defaults.dsndate" scope="request"/>
										</td>
										<td width="10%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.defaults.dsn" scope="request"/>
										</td>
										<td width="15%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.defaults.csgdocnum" scope="request"/>
										</td>
										<td width="15%" class="iCargoLabelLeftAligned">
										<common:message   key="mailtracking.mra.defaults.gpacode" scope="request"/>
										</td>
									</tr>
								</thead>
								<tbody>
									<logic:present name="DSNPopUpVO">
										<logic:iterate id="iterator" name="DSNPopUpVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO" indexId="rowCount">
											<tr ondblclick="doOk()">
												<td>
													<input type="checkbox" name="check" value="<%=String.valueOf(rowCount)%>" onclick="singleSelectCb(this.form,'<%= rowCount.toString() %>','check');"/>
												</td>
												<td>
												<logic:present	name="iterator" property="blgBasis">
												<bean:write name="iterator" property="blgBasis" />
												</logic:present>
												</td>
												<td>
												<logic:present	name="iterator" property="dsnDate">
												<bean:write name="iterator" property="dsnDate" />
												</logic:present>
												</td>
												<td>
												<logic:present	name="iterator" property="dsn">
												<bean:write name="iterator" property="dsn" />
												</logic:present>
												</td>
												<td>
												<logic:present	name="iterator" property="csgdocnum">
												<bean:write name="iterator" property="csgdocnum" />
												</logic:present>												
												</td>
												<td>
												<logic:present	name="iterator" property="gpaCode">
												<bean:write name="iterator" property="gpaCode" />
												</logic:present>
												</td>
											</tr>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>

					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-row">
						<div class="ic-button-container paddR5">
							<ihtml:nbutton property="okButton" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_BUTTON_OK" >
								<common:message   key="mailtracking.mra.defaults.btn.btok" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btnClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_BUTTON_CLOSE">
								<common:message   key="mailtracking.mra.defaults.btn.btclose" />
							</ihtml:nbutton>
						</div>
					</div>
				</div>
</div>


</ihtml:form>
</div>

	</body>
</html:html>

