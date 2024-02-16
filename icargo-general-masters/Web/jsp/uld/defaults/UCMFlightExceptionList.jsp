<%------------------------------------------------------------------------
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: UCMFlightExceptionList.jsp
* Date				: 9-Jan-2007
* Author(s)			: A-2001
 --------------------------------------------------------------------------%>

<%@ page import = "com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<html:html locale="true">
<head>
		
	
<title>
	<common:message bundle="ucmflightexceptionlistResources" key="uld.defaults.ucmflightexceptionlist.icargoucmflightexceptionlist" />
</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include src="/js/uld/defaults/UCMFlightExceptionList_Script.jsp" type="script"/>

</head>
<body  style="width:80%;height:90%; margin: 0 auto;">
	
	<bean:define id="form"
			 name="UCMFlightExceptionListForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMFlightExceptionListForm"
		 toScope="page" />
	<business:sessionBean
		id="ucmExceptionFlightVOs"
		moduleName="uld.defaults"
		screenID="uld.defaults.ucmflightexceptionlist"
		method="get"
		attribute="ucmExceptionFlightVOs"/>
		<div  id="pageDiv" class="iCargoContent" style="overflow:auto">
	<ihtml:form action="/uld.defaults.messaging.screenloaducmflightexceptionlist.do">

	
		<input type="hidden" name="mySearchEnabled" />

	  <ihtml:hidden property="duplicateFlightStatus" />
	  <ihtml:hidden property="actionStatus" />
	 <ihtml:hidden property="listStatus" />

     <div class="ic-content-main">
      <span class="ic-page-title ic-display-none">
          	<common:message key="uld.defaults.ucmflightexceptionlist.ucmflightexceptionlist" />
       </span>
        <div class="ic-head-container">	
				      <div class="ic-filter-panel">   
                        <div class="ic-input-container">
			  		    <div class="ic-row">
					    <div class="ic-row ">
	<h4> Search Criteria </h4>
	  </div>
	</div>
	
	 <div class="ic-row">
	 <div class="ic-input ic-split-80 ic-mandatory" >
						    <label><common:message key="uld.defaults.ucmflightexceptionlist.lbl.airport" />  </label>
                           <ihtml:text property="airportCode" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_AIRPORT" maxlength="3" />                             
							</div>
          <!--DWLayoutTable-->
        
 <div class="ic-input ic-split-20 ic-mandatory" >
           <div class="ic-button-container paddR5">
            	<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_LIST" accesskey="L">
						<common:message key="uld.defaults.ucmflightexceptionlist.btn.List" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_CLEAR" accesskey="C">
						<common:message key="uld.defaults.ucmflightexceptionlist.btn.Clear" />
				</ihtml:nbutton>
        	</div>
         
		  </div>
		   </div>
         </div>
         
		  </div>
		   </div>
	 
	 
	 <div class="ic-main-container">		
					    <div class="ic-row">
						 <h3>    Flight Details   </h3>
                        </div>
			           <!--  -->
				 <div class="ic-row paddR5">
                <!--DWLayoutTable-->
                  <div class="ic-button-container">
					  <a href="javascript:void(0)" id="addLink" class="iCargoLink">
					  	<common:message key="uld.defaults.ucmflightexceptionlist.lnk.add" />
					  </a> | <a href="javascript:void(0)" id="deleteLink" class="iCargoLink">
					  	<common:message key="uld.defaults.ucmflightexceptionlist.lnk.delete" />
					  </a>
                  </div>
                  </div>	  
				  
				  
				 <div class="ic-row">

						<div class="tableContainer"  id="div1"  style="height:550px; ">
						  <table width="100%" class="fixed-header-table"  id="ucmflightexceptionlist">
							<thead>
							  <tr class="iCargoTableHeadingLeft">
                                 <td width="24%" class="iCargoTableHeader">
					             <input type="checkbox" name="masterCheckbox" value="checkbox" onclick="updateHeaderCheckBox(this.form,document.forms[1].allCheck,document.forms[1].selectedUsers)" /><span></span>
                                </td>
                            <td width="38%">
                        	<common:message key="uld.defaults.ucmflightexceptionlist.lbl.flightnumber" />
                           <span></span></td>
                             <td width="38%">
                        	<common:message key="uld.defaults.ucmflightexceptionlist.lbl.flightdate" />
                           <span></span></td>
						   </tr>
						   
				         </thead>
                        <tbody>
                        	<logic:present name="ucmExceptionFlightVOs">
								<logic:iterate id="ucmExceptionFlightVO" name="ucmExceptionFlightVOs" indexId="index" type="UCMExceptionFlightVO">
								<logic:present name="ucmExceptionFlightVO" property="opeartionalFlag">
									<input type="hidden" name="operationFlags" value="<%=ucmExceptionFlightVO.getOpeartionalFlag()%>"/>
								</logic:present>
								<logic:notPresent name="ucmExceptionFlightVO" property="opeartionalFlag">
									<input type="hidden" name="operationFlags" value=""/>
								</logic:notPresent>
								<logic:notEqual name="ucmExceptionFlightVO" property="opeartionalFlag" value="D">
									<common:rowColorTag index="index">
										<tr bgcolor="<%=color%>">
											<td  class="iCargoTableDataTd ic-center" >
											
												<input type="checkbox" name="selectedRows" value="<%=index%>" onclick="toggleTableHeaderCheckbox('selectedRows',this.form.masterCheckbox)" />
											
											</td>
											
											
											<td  class="iCargoTableDataTd" >
												<logic:present name="ucmExceptionFlightVO" property="flightNumber">
													<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="<%=(String)ucmExceptionFlightVO.getCarrierCode()%>" flightcodevalue="<%=(String)ucmExceptionFlightVO.getFlightNumber()%>"  indexId="index"/>
												</logic:present>
												<logic:notPresent name="ucmExceptionFlightVO" property="flightNumber">
													<ibusiness:flightnumber id="flightNo" componentID="TXT_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTNO" carrierCodeProperty="flightCarrier" flightCodeProperty="flightNumber" carriercodevalue="" flightcodevalue=""  indexId="index"/>
												</logic:notPresent>
											</td>
											<td  class="iCargoTableDataTd" >

												<logic:present name="ucmExceptionFlightVO" property="flightDate">
													<logic:present name="ucmExceptionFlightVO" property="opeartionalFlag">
														<logic:equal name="ucmExceptionFlightVO" property="opeartionalFlag" value="I">
													<ibusiness:calendar
														  id="flightDate"
														  type="image"
														  property="flightDate"
														  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
																  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>" indexId="index"/>
														</logic:equal>	
														<logic:notEqual name="ucmExceptionFlightVO" property="opeartionalFlag" value="I">
															<ibusiness:calendar
																  id="flightDate"
																  type="image"
																  property="flightDate"
																  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
																  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>" indexId="index" disabled="true"/>
														</logic:notEqual>															
													</logic:present>
													<logic:notPresent name="ucmExceptionFlightVO" property="opeartionalFlag">
													<ibusiness:calendar
															  id="flightDate"
															  type="image"
															  property="flightDate"
															  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
															  value="<%=ucmExceptionFlightVO.getFlightDate().toDisplayDateOnlyFormat()%>" indexId="index" disabled="true"/>
													</logic:notPresent>
												</logic:present>

												<logic:notPresent name="ucmExceptionFlightVO" property="flightDate">
													<ibusiness:calendar
														  id="flightDate"
														  type="image"
														  property="flightDate"
														  componentID="CAL_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_FLIGHTDATE"
														  value="" indexId="index"/>
												</logic:notPresent>




											</td>

										</tr>
								  	</common:rowColorTag>
								  			<logic:present name="ucmExceptionFlightVO" property="flightSequenceNumber">
												<input type="hidden" name="flightSequenceNumber"  value="<%=String.valueOf(ucmExceptionFlightVO.getFlightSequenceNumber())%>" maxlength="12" />
											</logic:present>
											<logic:notPresent name="ucmExceptionFlightVO" property="flightSequenceNumber">
												<input type="hidden" name="flightSequenceNumber" value="" maxlength="12" />
											</logic:notPresent>

								</logic:notEqual>

							</logic:iterate>
			  			  </logic:present>
                        </tbody>
                      </table>
                    </div></div>
        </div>

        <div class="ic-foot-container">						
				    <div class="ic-row">
						<div class="ic-button-container paddR5">
			   <ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_SAVE" accesskey="S">
					<common:message key="uld.defaults.ucmflightexceptionlist.btn.save" />
				</ihtml:nbutton>
			   <ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_UCMFLIGHTEXCEPTIONLIST_CLOSE" accesskey="O">
					<common:message key="uld.defaults.ucmflightexceptionlist.btn.Close" />
				</ihtml:nbutton>
			          </div></div>
        </div>
 
 
  <span id="tmpSpan" style="display:none"></span>
 </div>
  </ihtml:form>
</div>


				
		
	</body>
</html:html>


