<%--/********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : MaintainUPURateCard.jsp
* Date                 	 : 23-Jan-2007
* Author(s)              : A-2391
*************************************************************************/
--%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateCardVO"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
		 name="MaintainUPURateCardForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainUPURateCardForm"
		 toScope="page" />



	
	
<html:html>
<head>
	
	

<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.maintianupuratecard.title"/></title>
<meta name="decorator" content="mainpanelrestyledui">

<common:include type="script" src="/js/mail/mra/defaults/MaintainUPURateCard_Script.jsp" />
</head>

<body>
	
	
<%System.out.println("jiii");%>
<business:sessionBean id="RateCardVO"
	     moduleName="mailtracking.mra.defaults"
	     screenID="mailtracking.mra.defaults.upuratecard.maintainupuratecard"
	     method="get"
	     attribute="rateCardDetails"/>
<business:sessionBean id="RateLineVO"
	     	     moduleName="mailtracking.mra.defaults"
	     	     screenID="mailtracking.mra.defaults.upuratecard.maintainupuratecard"
	     	     method="get"
	     attribute="rateLineDetails"/>
<business:sessionBean id="KEY_ONETIMES"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.upuratecard.maintainupuratecard"
	method="get" attribute="oneTimeVOs" />

  <!--CONTENT STARTS-->
<div id="mainDiv" class="iCargoContent ic-masterbg" style="overflow:auto; width:100%;height:100%">
<ihtml:form action="/mailtracking.mra.defaults.maintainupuratecard.screenload.do">

<ihtml:hidden name="MaintainUPURateCardForm" property="screenStatus" />
<ihtml:hidden name="MaintainUPURateCardForm" property="fromPage" />
<ihtml:hidden name="MaintainUPURateCardForm" property="lastPageNum" />
<ihtml:hidden name="MaintainUPURateCardForm" property="displayPage" />
<ihtml:hidden name="MaintainUPURateCardForm" property="okFlag" />
<ihtml:hidden name="MaintainUPURateCardForm" property="opFlag" />
<ihtml:hidden name="MaintainUPURateCardForm" property="listStatus" />
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.mra.defaults.maintianupuratecard.pagetitle" />
			</span>
		<div class="ic-head-container">	
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class="ic-row ">
						<div class="ic-row">
							<div class="ic-input ic-split-33 ic-label-35 ic-mandatory">
								<label>
				   <common:message key="mailtracking.mra.defaults.maintianupuratecard.ratecardid" />
								</label>

				   <ihtml:text property="rateCardId" componentID="CMP_MRA_DEFAULTS_RATECARDID" readonly="false"/>
								<div class="lovImg">
                                    <img src="<%=request.getContextPath()%>/images/lov.png" id="rateCardIdlov" height="22" width="22" alt="" />
									</div>
				            </div>
				            <div class="ic-button-container">
					   	   <ihtml:nbutton property="btnList" accesskey="L" componentID="CMP_MRA_DEFAULTS_LIST" >
						   <common:message key="mailtracking.mra.defaults.maintianupuratecard.button.list" />
						   </ihtml:nbutton>

						  <ihtml:nbutton property="btnClear" accesskey="C" componentID="CMP_MRA_DEFAULTS_CLEAR" >
						  <common:message key="mailtracking.mra.defaults.maintianupuratecard.button.clear" />
					          </ihtml:nbutton>
					  </div>
				        </div>
				    </div>
					<div class="hr-line"></div>
						<div class="ic-row">
						<div class="ic-row">
							<div class="ic-input ic-split-25 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.maintianupuratecard.status" /> 
								</label>


              			   <ihtml:text property="status"  componentID="CMP_MRA_DEFAULTS_STATUS" readonly="true"/>
							</div>
						    <div class="ic-input ic-split-25 ic-label-35 ic-mandatory">
								<label>
              			    <common:message key="mailtracking.mra.defaults.maintianupuratecard.description" />
              			   		</label>
				   <ihtml:text property="description" componentID="CMP_MRA_DEFAULTS_DESCRIPTION" />
              			    </div>
              			    <div class="ic-input ic-split-25 ic-label-35 ic-mandatory">
								<label>
              			   <common:message key="mailtracking.mra.defaults.maintianupuratecard.validfrom" />
								</label>
				    <ibusiness:calendar
				     property="validFrom"
					componentID="CMP_MRA_DEFAULTS_VALIDFROMFILTER"
				     type="image"
				     id="validFrom"
                                     value="<%=form.getValidFrom()%>"/>
							</div>
							<div class="ic-input ic-split-25 ic-label-35 ic-mandatory">
								<label>
			  	   <common:message key="mailtracking.mra.defaults.maintianupuratecard.validto" />
								</label>
				    <ibusiness:calendar
				   				     property="validTo"
				   					componentID="CMP_MRA_DEFAULTS_VALIDTOFILTER"
				   				     type="image"
				   				     id="validTo"
                                     value="<%=form.getValidTo()%>"/>
							</div>
						</div>
					</div>
					<div class="hr-line"></div>
		            <div class="ic-row ">
						<div class="ic-row">
							<div class="ic-input ic-split-25 ic-label-35">
                    			<label>
									<common:message key="mailtracking.mra.defaults.maintianupuratecard.maildistfactor" />
								</label>
						


                    					<ihtml:text property="mialDistFactor" componentID="CMP_MRA_DEFAULTS_MAILDISTFACTOR" />
                   			</div>
							<div class="ic-input ic-split-25 ic-label-35">
									<label>
										<common:message key="mailtracking.mra.defaults.maintianupuratecard.airmailtkm" />
									</label>
										<ihtml:text property="airmialTkm" componentID="CMP_MRA_DEFAULTS_AIRMAILTKM" />
                    		</div>
                    		<div class="ic-input ic-split-25 ic-label-35">
								<label>
									<common:message key="mailtracking.mra.defaults.maintianupuratecard.saltkm" /></label>

							<ihtml:text property="salTkm" componentID="CMP_MRA_DEFAULTS_SALTKM" />
                    					</div>
                    					<div class="ic-input ic-split-25 ic-label-35">
											<label>
												<common:message key="mailtracking.mra.defaults.maintianupuratecard.svtkm" />
											</label>
							<ihtml:text property="svTkm" componentID="CMP_MRA_DEFAULTS_SVTKM" />
                    					</div>
                        </div>
					</div>
                </div>
            </div>
	    </div>
		<div class="ic-main-container">				
	        <div class="ic-row">
				<div class="ic-col-55">

						


								 <logic:present name="RateLineVO">
									<common:paginationTag pageURL="mailtracking.mra.defaults.maintainupuratecard.list.do"
									 name="RateLineVO"
									 display="label"
									 labelStyleClass="iCargoResultsLabel"
									 lastPageNum="<%=form.getLastPageNum()%>" />
								 </logic:present>
				</div>
				<div  class="ic-col-35">
				<div class="ic-button-container">
								<logic:present name="RateLineVO">
									<common:paginationTag pageURL="javascript:submitPage('lastPageNum','displayPage')"
									linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
									name="RateLineVO" display="pages" lastPageNum="<%=form.getLastPageNum()%>"
									exportToExcel="true"
									exportTableId="captureAgtSettlementMemo"
									exportAction="mailtracking.mra.defaults.maintainupuratecard.list.do"/>
								 </logic:present>
								 <logic:notPresent name="RateLineVO">	
									&nbsp;
								</logic:notPresent>
				</div>				
				</div>	
				<div  class="ic-col-10">
				<div class="ic-button-container paddR5">
				<a href="#" class="iCargoLink" id="add">Add</a>
				
				<a href="#" class="iCargoLink" id="delete">Delete</a>
				</div>	
				</div>			
			</div>
	        <div class="ic-row">
	            <div  class="tableContainer" id="div1" style="height:570px;">
		            <table class="fixed-header-table" id="captureAgtSettlementMemo">



                    <thead>
                      <tr >
							<td  class="iCargoTableHeader ic-center" width="1%">
                         <input type="checkbox" name="headChk" id="headChk" />
                        </td>
                        <td  class="iCargoTableHeader" width="4%" >
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.origin" /></td>
                        <td class="iCargoTableHeader" width="6%" >
                         <common:message key="mailtracking.mra.defaults.maintianupuratecard.destination" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.iatakm" /></td>
                        <td    class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.mailkm" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.airmailsdr" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.salsdr" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.svsdr" /></td>
                        <!--<td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.cpsdr" /></td>-->
                       <!-- <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.emsbase" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.salbase" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.cpbase" /></td>
                        <td  class="iCargoTableHeader" width="5%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.airmailbase" /></td>-->
                        <td  class="iCargoTableHeader" width="10%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.validfrm" /></td>
                        <td  class="iCargoTableHeader" width="10%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.validto" /></td>
                        <td  class="iCargoTableHeader" width="6%">
                        <common:message key="mailtracking.mra.defaults.maintianupuratecard.ratelinestatus" /></td>
                      </tr>
                    </thead>
                    <tbody>
                    <logic:present name="RateLineVO">
                    <logic:iterate id="iterator" name="RateLineVO" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO" indexId="rowCount">



                     <logic:equal name="iterator" property="operationFlag" value="D">
                     <ihtml:hidden name="form" property="origin"/>
                     <ihtml:hidden name="form" property="destination"/>
                     <ihtml:hidden name="form" property="iataKilometre"/>
                     <ihtml:hidden name="form" property="mailKilometre"/>
                     <ihtml:hidden name="form" property="svSdr"/>
                     <ihtml:hidden name="form" property="salSdr"/>
                     <!--<ihtml:hidden name="form" property="cpSdr"/>-->
                     <ihtml:hidden name="form" property="airmailSdr"/>
                     <ihtml:hidden name="form" property="svBaseCurr"/>
                     <ihtml:hidden name="form" property="salBaseCurr"/>
                     <ihtml:hidden name="form" property="cpBaseCurr"/>
                     <ihtml:hidden name="form" property="airmailBaseCurr"/>
                     <ihtml:hidden name="iterator" property="operationFlag"/>
                     <%

						 String startDate = TimeConvertor.toStringFormat(((LocalDate)iterator.getValidityStartDate()).toCalendar(),"dd-MMM-yyyy");
						 
					 %>
							 <ihtml:hidden name="form" property="validFromRateLine" value="<%=startDate%>"/>
							<%

					   String toDate = TimeConvertor.toStringFormat(((LocalDate)iterator.getValidityEndDate()).toCalendar(),"dd-MMM-yyyy");
					   
					 %>
							<ihtml:hidden name="form" property="validToRateLine" value="<%=toDate%>"/>

							 <ihtml:hidden name="form" property="rateLineStatus" value="iterator.getRatelineStatus()"/>
							  
							 </logic:equal>

							  <logic:notEqual name="iterator" property="operationFlag" value="D">
					  
						<ihtml:hidden name="iterator" property="operationFlag"/>
					<tr>
				  <td  class="ic-center">
					<ihtml:checkbox property="check" value="<%=String.valueOf(rowCount)%>"/>
				  </td>
					<td class="ic-center">
						<logic:present	name="iterator" property="origin">
						<bean:write name="iterator" property="origin" />
						</logic:present>
					</td>
                      <td class="ic-center">
						<logic:present	name="iterator" property="destination">
						<bean:write name="iterator" property="destination" />
						</logic:present>
					</td>
					<td class="ic-center">
						<logic:present	name="iterator" property="iataKilometre">
						<bean:write name="iterator" property="iataKilometre" localeformat="true"/>
						</logic:present>
					</td>
					<td class="ic-center">
						<logic:present	name="iterator" property="mailKilometre">
						<bean:write name="iterator" property="mailKilometre" localeformat="true" />
						</logic:present>
					</td>
					<td class="ic-center">
						<logic:present	name="iterator" property="rateInSDRForCategoryRefThree">
						<bean:write name="iterator" property="rateInSDRForCategoryRefThree"  localeformat="true"/>
						</logic:present>
					</td>
					<td class="ic-center">
						<logic:present name="iterator" property="rateInSDRForCategoryRefTwo">
						<bean:write name="iterator" property="rateInSDRForCategoryRefTwo" localeformat="true" />
						</logic:present>
					</td>
					<td class="ic-center">
						<logic:present	name="iterator" property="rateInSDRForCategoryRefOne">
						<bean:write name="iterator" property="rateInSDRForCategoryRefOne" localeformat="true" />
						</logic:present>
					</td>
                    
					<td class="ic-center">
						<logic:present name="iterator" property="validityStartDate">
						<bean:define id="validityStartDate" name="iterator" property="validityStartDate" />
						<%
						String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validityStartDate).toCalendar(),"dd-MMM-yyyy");
						%>
						<ibusiness:calendar
						property="validFromRateLine"
						componentID="CMP_MRA_DEFAULTS_VALIDFROM"
						type="image"
						id="validFrom1"
						value="<%=assignedLocalDate%>" indexId="rowCount"/>
						</logic:present>
						<logic:notPresent name="iterator" property="validityStartDate">
						<ibusiness:calendar
						property="validFromRateLine"
						componentID="CMP_MRA_DEFAULTS_VALIDFROM"
						type="image"
						id="validFrom1"
						value= "<%=form.getValidFrom()%>" indexId="rowCount"/>
						</logic:notPresent>
					</td>
					<td class="ic-center">
						<logic:present name="iterator" property="validityEndDate">





			
		


						<bean:define id="validityEndDate" name="iterator" property="validityEndDate" />
						<%
						String assignedLocalDate = TimeConvertor.toStringFormat(((LocalDate)validityEndDate).toCalendar(),"dd-MMM-yyyy");
						%>
						<ibusiness:calendar
						property="validToRateLine"
						componentID="CMP_MRA_DEFAULTS_VALIDTO"
						type="image"
						id="validTo1"
						indexId="rowCount"
						value="<%=assignedLocalDate%>"/>
						</logic:present>
						<logic:notPresent name="iterator" property="validityEndDate">
						<ibusiness:calendar
						property="validToRateLine"
						componentID="CMP_MRA_DEFAULTS_VALIDTO"
						type="image"
						id="validTo1"
						indexId="rowCount"
						value= "<%=form.getValidTo()%>" />
						</logic:notPresent>
					</td>
                    <td class="ic-center">
						<logic:present name="iterator" property="ratelineStatus">
						<logic:present name="KEY_ONETIMES">
						<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
						<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
						<logic:equal name="parameterCode" value="mra.gpabilling.ratestatus">
						<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
						<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
						<logic:present name="parameterValue" property="fieldValue">
						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
						<bean:define id="ratelineStatus" name="iterator" property="ratelineStatus"/>
						<%String field=ratelineStatus.toString();%>
						<logic:equal name="parameterValue" property="fieldValue" value="<%=field%>">
						<bean:write name="parameterValue" property="fieldDescription" />
						<ihtml:hidden property="rateLineStatus" value="<%=field%>"/>
						</logic:equal>
						</logic:present>
						</logic:iterate>
						</logic:equal>
						</logic:iterate>
						</logic:present>
						</logic:present>
						<logic:notPresent name="iterator" property="ratelineStatus">
						<ihtml:hidden property="rateLineStatus" value=""/>
						</logic:notPresent>
					</td>
					</tr>
                 </logic:notEqual>

		
                      
                    
			</logic:iterate>
	</logic:present>

                    </tbody>
                  </table>
			     </div>
				</div>
				</div>
		<div class="ic-foot-container paddR5">						
			<div class="ic-row">
				<div class="ic-button-container">

		   


						   <ihtml:nbutton property="btnActivate" accesskey="I" componentID="CMP_MRA_DEFAULTS_BTN_ACTIVATE">
								<common:message key="mailtracking.mra.defaults.maintianupuratecard.button.activate" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btnInactivate" accesskey="V" componentID="CMP_MRA_DEFAULTS_BTN_INACTIVATE">
								<common:message	key="mailtracking.mra.defaults.maintianupuratecard.button.inactivate" />
							</ihtml:nbutton>


							<ihtml:nbutton property="btnCancel" accesskey="A" componentID="CMP_MRA_DEFAULTS_BTN_CANCEL">
								<common:message key="mailtracking.mra.defaults.maintianupuratecard.button.cancel" />
							</ihtml:nbutton>

						   <ihtml:nbutton property="btnCopy" accesskey="R" componentID="CMP_MRA_DEFAULTS_BTN_COPY" >
					           <common:message key="mailtracking.mra.defaults.maintianupuratecard.button.copy" />
						   </ihtml:nbutton>

						   <ihtml:nbutton property="btnSave" accesskey="S" componentID="CMP_MRA_DEFAULTS_BTN_SAVE" >
					            <common:message key="mailtracking.mra.defaults.maintianupuratecard.button.save" />
						   </ihtml:nbutton>

						  <ihtml:nbutton property="btnClose" accesskey="O" componentID="CMP_MRA_DEFAULTS_BTN_CLOSE" >
					           <common:message key="mailtracking.mra.defaults.maintianupuratecard.button.close" />
						   </ihtml:nbutton>


				</div>
			</div>
        </div>
	</div>				

</ihtml:form>
</div>
<!---CONTENT ENDS-->

	</body>
</html:html>
