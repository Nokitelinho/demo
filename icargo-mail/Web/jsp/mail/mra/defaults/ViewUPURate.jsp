<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : CRA
* File Name          	 : ListCN51CN66.jsp
* Date                 	 : 16-Jan-2006
* Author(s)              : A-2270
*************************************************************************/
--%>

<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import ="java.text.DecimalFormat"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import = "com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO" %>
<%@ page import="java.util.HashMap"%>
<%@ page import ="java.util.ArrayList"%>




<html>
<head>


	<common:include	type="script" src="/js/mail/mra/defaults/ViewUPURate_Script.jsp" />
	<title>
		<common:message bundle="viewupurate" key="mailtracking.mra.defaults.viewUPURate.title" />
	</title>
	<meta name="decorator" content="mainpanelrestyledui">


	<common:include type="script"  src="/js/calendar/formateDate.js" />





</head>

<body>



	<business:sessionBean id="rateLineVOs"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.viewupurate" method="get"
		attribute="rateLineVOs" />

		<business:sessionBean id="oneTimeVOs"
				moduleName="mailtracking.mra.defaults"
				screenID="mailtracking.mra.defaults.viewupurate"
				method="get"
		attribute="oneTimeVOs" />

		<business:sessionBean id="rateLineFilterVO"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.viewupurate"
			method="get"
		attribute="rateLineFilterVO" />
<div id="mainDiv" class="iCargoContent ic-masterbg" style="overflow:auto; width:100%;height:100%">
 <ihtml:form action="/mailtracking.mra.defaults.onScreenLoadViewUPURate.do">
<jsp:include page="/jsp/includes/tab_support.jsp" />
	<ihtml:hidden property="lastPageNum"/>
	<ihtml:hidden property="absoluteIndex"/>
	<ihtml:hidden property="displayPage" />
	<ihtml:hidden property="changeStatusFlag"/>
	<ihtml:hidden property="invokingScreen"/>

	<html:hidden property="newStatus" />

 <bean:define id="form" name="ListUPURateLineForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateLineForm" toScope="page"/>

<div class="ic-content-main">
<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.defaults.viewupurate.title"/></span>
	<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<div class="ic-row">
						<div class="ic-input ic-split-33 ic-label-35">
							<label>

								<common:message key="mailtracking.mra.defaults.viewupurate.ratecardID"/>
							</label>

									<logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="rateCardID">

									  <bean:define id="rateCardID" name="rateLineFilterVO" property="rateCardID"/>
									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_RATECARDID" property="rateCardID" value="<%=String.valueOf(rateCardID)%>" maxlength="20"  readonly="false"/>
									  <div class="lovImg">
									  <img name="rateCardLov"
									     id="rateCardLov"  height="22"  src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
									  </div>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO" property="rateCardID">

										<ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_RATECARDID" property="rateCardID"  maxlength="20"  readonly="false"/>
										 <div class="lovImg">
										<img name="rateCardLov"
										  id="rateCardLov"  height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>


									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">


									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_RATECARDID" property="rateCardID"  maxlength="20"  readonly="false"/>
									   <div class="lovImg">
									  <img name="rateCardLov"
									     id="rateCardLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
									  </div>

									</logic:notPresent>
						</div>
						<div class="ic-input ic-split-33 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.origin"/>
							</label>


									<logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="origin">

									  <bean:define id="origin" name="rateLineFilterVO" property="origin"/>
									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_ORGCOD" property="origin" value="<%=String.valueOf(origin)%>" maxlength="20"  readonly="false"/>
									   <div class="lovImg">
									  <img name="stationlov"
										id="stationlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
									   </div>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO" property="origin">

										<ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_ORGCOD" property="origin"  maxlength="20"  readonly="false"/>
										<div class="lovImg">
										<img name="rateCardLov"
										id="stationlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>


									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">


									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_ORGCOD" property="origin"  maxlength="20"  readonly="false"/>
									  <div class="lovImg">
									  <img name="stationlov"
										id="stationlov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>

									</logic:notPresent>
						</div>
						<div class="ic-input ic-split-33 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.destination"/>
							</label>

									<logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="destination">

									  <bean:define id="destination" name="rateLineFilterVO" property="destination"/>
									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_DSTCOD" property="destination" value="<%=String.valueOf(destination)%>" maxlength="20"  readonly="false"/>
									  <div class="lovImg">
									  <img name="stationCodelov"
										id="stationCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO" property="destination">

										<ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_DSTCOD" property="destination"  maxlength="20"  readonly="false"/>
										<div class="lovImg">
										<img name="stationCodelov"
										id="stationCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>


									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">


									  <ihtml:text  componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_DSTCOD" property="destination"  maxlength="20"  readonly="false"/>
									  <div class="lovImg">
									  <img name="stationCodelov"
										id="stationCodelov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" alt="" />
										</div>

									</logic:notPresent>
						</div>
						<!--<div class="ic-input ic-split-33 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.level"/>
							</label>
									<ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_LEVEL" property="orgDstLevel">
							<ihtml:option value=""></ihtml:option>
								<logic:present name="oneTimeVOs">
									<logic:iterate id="oneTimeValue" name="oneTimeVOs">
										<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
										<logic:equal name="parameterCode" value="mail.mra.ratecar.orgdstlevel">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</div>-->
                    </div>
					<div class="ic-row">
						<div class="ic-input ic-split-33 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.fmdate"/>
							</label>
									<logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="startDate">

									  <bean:define id="startDate" name="rateLineFilterVO" property="startDate"/>
									  <%
									  String fromLocalDate = TimeConvertor.toStringFormat(((LocalDate)startDate).toCalendar(),"dd-MMM-yyyy");
									   %>
									    <ibusiness:calendar id="tdate"
										 property="startDate"  value="<%=fromLocalDate%>"
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_FRMDATE"
										 type="image"
									    maxlength="11"/>
									  </logic:present>
									<logic:notPresent name="rateLineFilterVO" property="startDate">

										<ibusiness:calendar id="strtDate"
										 property="startDate" value=""
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_FRMDATE"
										 type="image"
									       maxlength="11"/>
									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">
									  <ibusiness:calendar id="strtDate"
										 property="fromDate"
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_FRMDATE"
										 type="image"
									   maxlength="11"/>

									</logic:notPresent>
						</div>
						<div class="ic-input ic-split-33 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.todate"/>
							</label>

									<logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="endDate">

									  <bean:define id="endDate" name="rateLineFilterVO" property="endDate"/>
									  <%
									  String toLocalDate = TimeConvertor.toStringFormat(((LocalDate)endDate).toCalendar(),"dd-MMM-yyyy");
									   %>
									    <ibusiness:calendar id="fdate"
										 property="endDate"  value="<%=toLocalDate%>"
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_TODATE"
										 type="image"
									    maxlength="11"/>
									  </logic:present>
									<logic:notPresent name="rateLineFilterVO" property="endDate">

										<ibusiness:calendar id="tooDate"
										 property="startDate" value=""
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_TODATE"
										 type="image"
									       maxlength="11"/>
									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">
									  <ibusiness:calendar id="fdate"
										 property="toDate"
										 componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_TODATE"
										 type="image"
									   maxlength="11"/>

									</logic:notPresent>
						</div>
						<div class="ic-input ic-split-15 ic-label-35">
							<label>
								<common:message key="mailtracking.mra.defaults.viewupurate.status"/>
							</label>
								      <%HashMap hashExps = null;%>
								       <logic:present name="rateLineFilterVO">
									  <logic:present name="rateLineFilterVO" property="ratelineStatus">
									      <bean:define id="ratelineStatus" name="rateLineFilterVO" property="ratelineStatus"/>
									     <%hashExps = new HashMap();%>
											<ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_STATUS" property="ratelineStatus" value="<%=String.valueOf(ratelineStatus)%>">
											<ihtml:option value=""></ihtml:option>
											<logic:present name="oneTimeVOs">
												<logic:iterate id="oneTimeValue" name="oneTimeVOs">
												<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue" property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
													<%= hashExps.put(fieldValue,fieldDescription)%>
												</logic:present>
												</logic:iterate>
												</logic:iterate>
											</logic:present>
										</ihtml:select>


									  </logic:present>
									<logic:notPresent name="rateLineFilterVO" property="ratelineStatus">
									        <%hashExps = new HashMap();%>
										<ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_STATUS" property="status">
										<ihtml:option value=""></ihtml:option>
										<logic:present name="oneTimeVOs">
											<logic:iterate id="oneTimeValue" name="oneTimeVOs">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
												<%= hashExps.put(fieldValue,fieldDescription)%>
											</logic:present>
											</logic:iterate>
											</logic:iterate>
										</logic:present>
										</ihtml:select>

									</logic:notPresent>
									</logic:present>
									<logic:notPresent name="rateLineFilterVO">
									        <% hashExps = new HashMap();%>
									        <ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_STATUS" property="status">
										<ihtml:option value=""></ihtml:option>
										<logic:present name="oneTimeVOs">
											<logic:iterate id="oneTimeValue" name="oneTimeVOs">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
												<%= hashExps.put(fieldValue,fieldDescription)%>
											</logic:present>
											</logic:iterate>
											</logic:iterate>
										</logic:present>
										</ihtml:select>

									</logic:notPresent>

								<%--
									     <!-- Obtaining OnetimeValues-->
									        <%HashMap hashExps = new HashMap();%>

										<ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_STATUS" property="status">
										<ihtml:option value=""></ihtml:option>
										<logic:present name="oneTimeVOs">
											<logic:iterate id="oneTimeValue" name="oneTimeVOs">
											<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue" property="fieldValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>"><%=String.valueOf(fieldDescription)%></ihtml:option>
											        <%= hashExps.put(fieldValue,fieldDescription)%>
											</logic:present>
											</logic:iterate>
											</logic:iterate>
										</logic:present>
										</ihtml:select>


								--%>
						</div>


						<div class="ic-input ic-split-15 ic-button-container">

					 <ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_BTNDETAILS">
			    		 <common:message key="mailtracking.mra.gpabilling.btn.details" />
			    		 </ihtml:nbutton>
					 <ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_BTNCLEAR">
			    		 <common:message key="mailtracking.mra.gpabilling.btn.clear" />
			    		 </ihtml:nbutton>
					</div>
					</div>
				</div>
            </div>
		</div>
	</div>
	<div class="ic-main-container">

				   <!---PAGINATION TAG STARTS-->
		 <div class="ic-row">
			<div class="ic-col-60 ic-left">

							<logic:present name="rateLineVOs">
							<common:paginationTag pageURL="/mailtracking.mra.defaults.listviewupurate.do"
							name="rateLineVOs" display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum="<%=form.getLastPageNum() %>" />

							</logic:present>
							<logic:notPresent name="rateLineVOs">
							&nbsp;
							</logic:notPresent>
			</div>
			<div  class="ic-col-40 ic-right paddR5">

							<logic:present name="rateLineVOs">
								<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
								linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
								name="rateLineVOs"
								display="pages"
								lastPageNum="<%=form.getLastPageNum()%>"
								exportToExcel="true"
								exportTableId="captureAgtSettlementMemo"
								exportAction="mailtracking.mra.defaults.listviewupurate.do"/>

							</logic:present>
							<logic:notPresent name="rateLineVOs">
								&nbsp;
							</logic:notPresent>



								  </div>
         </div>









				 <!---PAGINATION TAG ENDS-->
		<div class="ic-row">
			<div  class="tableContainer" id="div1" style="height:680px;overflow:auto;">
				<table class="fixed-header-table" id="captureAgtSettlementMemo">
							     <thead>
							        <tr >
							<td  class="iCargoTableHeader ic-center" width="1%"><input type="checkbox" name="headChk" /></td>


                        <td  class="iCargoTableHeader" width="5%" ><common:message key="mailtracking.mra.defaults.viewupurate.ratecardID"/></td>
		        <td class="iCargoTableHeader" width="6%" ><common:message key="mailtracking.mra.defaults.viewupurate.origin"/></td>
                        <td   class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.destination"/></td>
                        <td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.IATAKM"/></td>
                        <td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.MailKM"/></td>

						<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.AIRMAIL(SDR)"/></td>
						<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.SAL(SDR)"/></td>
						<td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.SV(SDR)"/></td>


                        <td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.ValidFrom"/></td>
                        <td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.ValidTo"/></td>
                        <td  class="iCargoTableHeader" width="5%"><common:message key="mailtracking.mra.defaults.viewupurate.Status"/></td>

		                </tr>

							   </thead>
							   <tbody>
							     <logic:present name="rateLineVOs" >

							     <logic:iterate id="ratelineVO" name="rateLineVOs" indexId="rowId" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO" >




									   <tr class="iCargoTableDataRow1">

										<td  class="ic-center" ><html:checkbox   property="rowId" value="<%= String.valueOf(rowId) %>" /></td>
											<td>
											   <logic:present name="ratelineVO" property="rateCardID">
												   <common:write name="ratelineVO" property="rateCardID"/>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="rateCardID">
												   &nbsp;
										           </logic:notPresent>
											</td>
											<td>
											   <logic:present name="ratelineVO" property="origin">
												   <common:write name="ratelineVO" property="origin"/> (<common:write name="ratelineVO" property="orgDstLevel"/>)
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="origin">
												   &nbsp;
											   </logic:notPresent>
											</td>
											<td>
											   <logic:present name="ratelineVO" property="destination">

												   <common:write name="ratelineVO" property="destination"/> (<common:write name="ratelineVO" property="orgDstLevel"/>)
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="destination">
												   &nbsp;
											   </logic:notPresent>
											</td>
											<td>
											   <logic:present name="ratelineVO" property="iataKilometre">

												   <common:write name="ratelineVO" property="iataKilometre"/>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="iataKilometre">
												   &nbsp;
											   </logic:notPresent>
											</td>
											<td>
											   <logic:present name="ratelineVO" property="mailKilometre">
												   <common:write name="ratelineVO" property="mailKilometre"/>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="mailKilometre">
												   &nbsp;
											   </logic:notPresent>
											</td>
											<td>
											<% String value ="";
											   DecimalFormat formatter = new DecimalFormat("0.0000");
											%>
											  <logic:present name="ratelineVO" property="rateInSDRForCategoryRefThree">
												   <!--<common:write name="ratelineVO" property="rateInSDRForCategoryRefThree" localeformat="true"/>-->
												   <bean:define id="val" name="ratelineVO" property="rateInSDRForCategoryRefThree" type="java.lang.Double"/>
												   <% if (val > 0.001){
														value = String.valueOf(val);
												   }else{
														value = formatter.format(Double.valueOf(val));
												    }
													%>
													<%= value %>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="rateInSDRForCategoryRefThree">
												   &nbsp;
											   </logic:notPresent>
											</td>
											<td>
											   <logic:present name="ratelineVO" property="rateInSDRForCategoryRefTwo">
												   <!--<common:write name="ratelineVO" property="rateInSDRForCategoryRefTwo" localeformat="true"/>-->
												   <bean:define id="val" name="ratelineVO" property="rateInSDRForCategoryRefTwo" type="java.lang.Double"/>
												    <% if (val > 0.001){
														value = String.valueOf(val);
												   }else{
														value = formatter.format(Double.valueOf(val));
												    }
													%>
													<%= value %>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="rateInSDRForCategoryRefTwo">
												   &nbsp;
											   </logic:notPresent>
											</td>

											<td>
											   <logic:present name="ratelineVO" property="rateInSDRForCategoryRefOne">
												   <!--<common:write name="ratelineVO" property="rateInSDRForCategoryRefOne" localeformat="true"/>-->
												   <bean:define id="val" name="ratelineVO" property="rateInSDRForCategoryRefOne" type="java.lang.Double"/>
												   <% if (val > 0.001){
														value = String.valueOf(val);
												   }else{
														value = formatter.format(Double.valueOf(val));
												    }
													%>
													<%= value %>
											  </logic:present>
											  <logic:notPresent name="ratelineVO" property="rateInSDRForCategoryRefOne">
												   &nbsp;
											   </logic:notPresent>
											</td>


											<td>
											  <logic:present name="ratelineVO" property="validityStartDate">


												<bean:define id="date" name="ratelineVO" property="validityStartDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<%
												  String validityStartDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
											       <%=validityStartDate%>

											  </logic:present>
											</td>
											<td>
											  <logic:present name="ratelineVO" property="validityEndDate">


												<bean:define id="date" name="ratelineVO" property="validityEndDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
												<%
												  String validityEndDate = TimeConvertor.toStringFormat(date.toCalendar(),"dd-MMM-yyyy"); %>
											       <%=validityEndDate%>

											  </logic:present>
											</td>

											<td>

											<logic:present name="ratelineVO" property="ratelineStatus">
												<bean:define id="expCode" name="ratelineVO" property="ratelineStatus"/>
												<%=hashExps.get(expCode)%>
												<%String field=expCode.toString();%>
												<ihtml:hidden property="rateLineStatus" value="<%=field%>"/>
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


					   	<ihtml:nbutton property="btnActivate" accesskey="I" componentID="CMP_MRA_DEFAULTS_BTN_ACTIVATE">
							<common:message key="mailtracking.mra.defaults.maintianupuratecard.button.activate" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnInactivate" accesskey="V" componentID="CMP_MRA_DEFAULTS_BTN_INACTIVATE">
							<common:message	key="mailtracking.mra.defaults.maintianupuratecard.button.inactivate" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnCancel" accesskey="A" componentID="CMP_MRA_DEFAULTS_BTN_CANCEL">
							<common:message key="mailtracking.mra.defaults.maintianupuratecard.button.cancel" />
						</ihtml:nbutton>
					    <ihtml:nbutton property="btnCopy" accesskey="R" componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_COPY_BTN">
					        <common:message key="mailtracking.mra.defaults.viewupurate.btn.btnCopy"/>
					    </ihtml:nbutton>
					    <ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_CLOSE_BTN">
					        <common:message key="mailtracking.mra.defaults.viewupurate.btn.btnClose"/>
					    </ihtml:nbutton>
            </div>
        </div>
   </div>
</div>

 </ihtml:form>
</div>
<!--- CONTENT ENDS -->

	</body>
</html>

