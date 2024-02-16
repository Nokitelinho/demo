<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : OriginDestPopUp.jsp
* Date                 	 : 24-Jan-2007
* Author(s)              : A-2391
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm" %>


<bean:define id="form"
		 name="MaintainUPURateCardForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm"
		 toScope="page" />

<html:html>
<head>



<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.orgdstn.title" /></title>
<meta name="decorator" content="popuppanelrestyledui">

<common:include type="script" src="/js/mail/mra/defaults/AddRateLine_Script.jsp" />
</head>

<body>


<business:sessionBean id="RateLineVO"
	     	     moduleName="mailtracking.mra.defaults"
	     	     screenID="mailtracking.mra.defaults.upuratecard.maintainupuratecard"
	     	     method="get"
	     attribute="newRateLineDetails"/>
<business:sessionBean id="onetimemap"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.upuratecard.maintainupuratecard"
	method="get" attribute="oneTimeVOs" />
<div class="iCargoPopUpContent" style="width:640px;height:400px;">
  <ihtml:form action="/mailtracking.mra.defaults.maintainupuratecard.addratelinepopup.do" styleClass="ic-main-form">
  <ihtml:hidden name="MaintainUPURateCardForm" property="okFlag" />
    <div class="ic-content-main">
<span class="ic-page-title ic-display-none">
		  <label><common:message key="mailtracking.mra.defaults.orgdstn.pagetitle" /></label>
		</span>

			  <div class="ic-main-container">
				<div class="ic-input-container">
					<div class="ic-row">
						<div class="ic-input ic-split-5 ic-label-35 ">
						</div>
						<div class="ic-input ic-split-20 ic-label-35 ">
							<label>
								<common:message key="mailtracking.mra.defaults.maintianupuratecard.level" />
							</label>
							<ihtml:select componentID="CMP_MRA_DEFAULTS_VIEWUPURATE_LEVEL" property="orgDstLevel" tabindex="1">
													<logic:present name="onetimemap">
														<logic:iterate id="oneTimeValue" name="onetimemap">
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
						</div>
					</div>
				</div>
		   <div class="ic-button-container">
			                 <a href="#" class="iCargoLink" id="add" onclick="addRateLine()">Add</a>&nbsp;
                             <a href="#" class="iCargoLink" id="delete" onclick="deleteRateLine()">Delete</a>&nbsp;
                            </td>
			   </div>

			      <div id="div1"  class="tableContainer" style="height:290px;">

                    <table  class="fixed-header-table" id="captureAgtSettlementMemo" >

			                         <thead>
											   <tr>
												 <td  class="iCargoTableHeader" width="1%">&nbsp;&nbsp;
												 </td>
												 <td  class="iCargoTableHeader" width="5%">
												 <common:message key="mailtracking.mra.defaults.maintianupuratecard.origin" />
												  &nbsp;<span class="iCargoMandatoryFieldIcon">*</span>
												 </td>
												 <td class="iCargoTableHeader" width="5%">
												 <common:message key="mailtracking.mra.defaults.maintianupuratecard.destination" />
												  &nbsp;<span class="iCargoMandatoryFieldIcon">*</span>
												 </td>
											</tr>
                                     </thead>

              					<tbody id="targetWeightTableBody">
                    				<logic:present name="RateLineVO">
                    				<logic:iterate id="iterator" name="RateLineVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO" indexId="rowCount">

										<tr>
            	                       <td>
            	                       <center>
            	                       <html:checkbox property="popCheck" value="<%=String.valueOf(rowCount)%>"/>
            	                       </center>
            	                       </td>
            	                      <td>
									  <center>
									 <ihtml:hidden property="operationFlag" name="form" value="<%=iterator.getOperationFlag()%>"/>
									 <ihtml:text name="form" property="popupOrigin" value="<%=iterator.getOrigin()%>" componentID="CMP_MRA_DEFAULTS_POPUPORIGIN" maxlength="4" />
									 <div class="lovImgTbl valignT">
									  <img src="<%=request.getContextPath()%>/images/lov.png" name="orglov" id="orglov<%=rowCount%>" height="16" width="16"/></div>

									</center>
							               </td>
						                       <td>
									<center>

									<ihtml:text name="form" property="popupDestn" value="<%=iterator.getDestination()%>" componentID="CMP_MRA_DEFAULTS_POPUPDESTN"
															  maxlength="3"/>
									<div class="lovImgTbl valignT">
								 	<img src="<%=request.getContextPath()%>/images/lov.png" name="destlov" id="destlov<%=rowCount%>" height="16" width="16"	alt="" /></div>


									</center>
	              							</td>
	              						</tr>

					       </logic:iterate>
						</logic:present>
						 <!-- template row starts-->
								   <bean:define id="templateRowCount" value="0"/>
									<tr template="true" id="targetWeightRow" style="display:none">
									<td width="1%" class="iCargoTableDataTd" >
											<center>
										   <html:checkbox property="popCheck"/>
										   </center>
										<ihtml:hidden property="operationFlag" value="NOOP"/>
									</td>
									<td width="1%" class="iCargoTableDataTd">
									<center>
									 <ihtml:text indexId="templateRowCount" property="popupOrigin" value="" componentID="CMP_MRA_DEFAULTS_POPUPORIGIN" maxlength="4" />
									 <div class="lovImgTbl valignT">
									 <img src="<%=request.getContextPath()%>/images/lov.png" name="orglov" id="orglov" height="16" width="16"	/>
									 </div>
									 </center>
									</td>
									<td width="1%" class="iCargoTableDataTd">
									<center>
									 <ihtml:text indexId="templateRowCount" property="popupDestn" value="" componentID="CMP_MRA_DEFAULTS_POPUPDESTN" maxlength="4" />
									 <div class="lovImgTbl valignT">
									 <img src="<%=request.getContextPath()%>/images/lov.png" name="destlov" id="destlov" height="16" width="16"	/>
									 </div>
									 </center>
									</td>

								   </tr>
						<!-- template row ends -->

                                            </tbody>
					   </table>
					</div>
                     </div>

			   <div class="ic-foot-container paddR5">
			<div class="ic-row">
					<div class="ic-button-container">

				   <ihtml:nbutton property="btnOk" componentID="CMP_MRA_DEFAULTS_OK" >
					<common:message key="mailtracking.mra.defaults.orgdstn.tooltip.ok" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btClose" componentID="CMP_MRA_DEFAULTS_CLOSE" >
					<common:message key="mailtracking.mra.defaults.orgdstn.tooltip.close" />
					</ihtml:nbutton>
</div>
                     </div>
				  </div>
</div>


</ihtml:form>
</div>

	</body>
</html:html>
