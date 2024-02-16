<%@ include file="/jsp/includes/tlds.jsp" %>
<bean:define id="MailArrivalForm" name="MailArrivalForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm" toScope="page" scope="request"/>
<business:sessionBean id="ContainerDetailsVOsSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="containerDetailsVOs" />
<logic:present name="ContainerDetailsVOsSession">
	<bean:define id="ContainerDetailsVOsSession" name="ContainerDetailsVOsSession" toScope="page"/>
</logic:present>
<business:sessionBean id="ContainerDetailsVOSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="containerDetailsVO" />
<logic:present name="ContainerDetailsVOSession">
	<bean:define id="ContainerDetailsVOSession" name="ContainerDetailsVOSession" toScope="page"/>
</logic:present>
<business:sessionBean id="polSession" moduleName="mail.operations" screenID="mailtracking.defaults.mailarrival" method="get" attribute="pols" />
<div class="ic-row ">
						<fieldset class ="ic-field-set">
						 <legend >
             <common:message key="mailtracking.defaults.arrivemail.lbl.contdtls" />
        </legend>
						 <%boolean toDisableNC = false;%>
	  <logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
	       <% toDisableNC = true;%>
	  </logic:notEqual>

							<div class="ic-input ic-split-30 borrowUld marginT10">
								<ibusiness:uld id="containerNo" uldProperty="containerNo" barrowFlag="true" barrowFlagProperty="barrowCheck"
		style="text-transform:uppercase;"  suggestCollection="ContainerDetailsVOsSession"  suggestAttribute="containerNumber"
		isSuggestible="true" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CONTAINERNUM" disabled="<%=toDisableNC%>"/>
							</div>
							<div class="ic-input ic-split-20 marginT10">
								<common:message key="mailtracking.defaults.arrivemail.lbl.pol"/>
							
								<% String polVal = ""; %>
									<logic:present name="MailArrivalForm" property="pol">
									<bean:define id="polObj" name="MailArrivalForm" property="pol" />
									<% polVal = (String) polObj; %>
									</logic:present>
									<ihtml:select property="pol" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_POL" value="<%=polVal%>" style="width:60px" disabled="<%=toDisableNC%>">
									  <logic:notPresent name="polSession">
										 <ihtml:option value="<%=polVal%>"><%=polVal%></ihtml:option>
									  </logic:notPresent>
									  <logic:present name="polSession">
										<bean:define id="polsSess" name="polSession" toScope="page" />
										<logic:iterate id="polVO" name="polsSess" >
										  <bean:define id="pol" name="polVO" toScope="page" />
										  <html:option value="<%=(String)pol %>"><%=(String)pol %></html:option>
										</logic:iterate>
									  </logic:present>
									</ihtml:select>
								</div>
								<div class="ic-input ic-split-10">
								 <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
			 <logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
				  <logic:present name="ContainerDetailsVOSession" property="arrivedStatus">
				      <logic:equal name="ContainerDetailsVOSession" property="arrivedStatus" value="Y">
					  <input type="checkbox" name="arrivedStatus" value="checkbox" checked  disabled onClick="checking()">
				      </logic:equal>
				      <logic:equal name="ContainerDetailsVOSession" property="arrivedStatus" value="N">
					  <input type="checkbox" name="arrivedStatus" value="checkbox" onClick="checking()">
				      </logic:equal>
				  </logic:present>
				  <logic:notPresent name="ContainerDetailsVOSession" property="arrivedStatus">
					<input type="checkbox" name="arrivedStatus" value="checkbox" onClick="checking()">
				  </logic:notPresent>
			 </logic:notEqual>
			 <logic:equal name="ContainerDetailsVOSession" property="operationFlag" value="I">
				  <logic:present name="ContainerDetailsVOSession" property="arrivedStatus">
				      <logic:equal name="ContainerDetailsVOSession" property="arrivedStatus" value="Y">
					  <input type="checkbox" name="arrivedStatus" value="checkbox" checked onClick="checking()" >
				      </logic:equal>
				      <logic:equal name="ContainerDetailsVOSession" property="arrivedStatus" value="N">
					  <input type="checkbox" name="arrivedStatus" value="checkbox" onClick="checking()">
				      </logic:equal>
				  </logic:present>
				  <logic:notPresent name="ContainerDetailsVOSession" property="arrivedStatus">
					<input type="checkbox" name="arrivedStatus" value="checkbox" onClick="checking()" >
				  </logic:notPresent>
			 </logic:equal>
	         </logic:present>
			  <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
			<input type="checkbox" name="arrivedStatus" value="checkbox" disabled onClick="checking()">
		 </logic:notPresent>
		  <common:message key="mailtracking.defaults.arrivemail.lbl.recvd" />
								</div>
									<div class="ic-input ic-split-10">
									<logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
									 <logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
				  <logic:present name="ContainerDetailsVOSession" property="deliveredStatus">
							<logic:equal name="ContainerDetailsVOSession" property="deliveredStatus" value="Y">
								<input type="checkbox" name="deliveredStatus" value="checkbox" checked disabled onClick="checking()">
							</logic:equal>
							<logic:equal name="ContainerDetailsVOSession" property="deliveredStatus" value="N">
								<input type="checkbox" name="deliveredStatus" value="checkbox" onClick="checking()">
							</logic:equal>
				  </logic:present>
				  <logic:notPresent name="ContainerDetailsVOSession" property="deliveredStatus">
					<input type="checkbox" name="deliveredStatus" value="checkbox" onClick="checking()">
				  </logic:notPresent>
					</logic:notEqual>
					<logic:equal name="ContainerDetailsVOSession" property="operationFlag" value="I">
				  <logic:present name="ContainerDetailsVOSession" property="deliveredStatus">
							<logic:equal name="ContainerDetailsVOSession" property="deliveredStatus" value="Y">
								<input type="checkbox" name="deliveredStatus" value="checkbox" checked onClick="checking()">
							</logic:equal>
							<logic:equal name="ContainerDetailsVOSession" property="deliveredStatus" value="N">
								<input type="checkbox" name="deliveredStatus" value="checkbox" onClick="checking()">
							</logic:equal>
				</logic:present>
					<logic:notPresent name="ContainerDetailsVOSession" property="deliveredStatus">
						<input type="checkbox" name="deliveredStatus" value="checkbox" onClick="checking()" >
					</logic:notPresent>
			 </logic:equal>
			</logic:present>
			<logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
				<input type="checkbox" name="deliveredStatus" value="checkbox" disabled onClick="checking()">
			</logic:notPresent>
			<common:message key="mailtracking.defaults.arrivemail.lbl.delvd" />
								</div>
									<div class="ic-input ic-split-10">
									 <logic:notEqual name="ContainerDetailsVOSession" property="operationFlag" value="I">
			       <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
				     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
					  <input type="checkbox" name="paBuilt" value="checkbox" checked  >
				     </logic:equal>
				     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
						  <input type="checkbox" name="paBuilt" value="checkbox" >
				     </logic:equal>
			       </logic:present>
			       <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
					<input type="checkbox" name="paBuilt" value="checkbox" >
			       </logic:notPresent>
		       </logic:notEqual>
		       <logic:equal name="ContainerDetailsVOSession" property="operationFlag" value="I">
			       <logic:present name="ContainerDetailsVOSession" property="paBuiltFlag">
				     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="Y">
					  <input type="checkbox" name="paBuilt" value="checkbox" checked >
				     </logic:equal>
				     <logic:equal name="ContainerDetailsVOSession" property="paBuiltFlag" value="N">
						  <input type="checkbox" name="paBuilt" value="checkbox" >
				     </logic:equal>
			       </logic:present>
			       <logic:notPresent name="ContainerDetailsVOSession" property="paBuiltFlag">
					<input type="checkbox" name="paBuilt" value="checkbox" >
			       </logic:notPresent>
		       </logic:equal>
			    <common:message key="mailtracking.defaults.arrivemail.lbl.pabuilt" />
									</div>
									<div class="ic-input ic-split-10">
									<logic:present name="ContainerDetailsVOSession" property="intact">
					<logic:equal name="ContainerDetailsVOSession" property="intact" value="Y">
			    		<input type="checkbox" name="intact" value="checkbox" checked disabled />
			    		</logic:equal>
					<logic:notEqual name="ContainerDetailsVOSession" property="intact" value="Y">
			    		<input type="checkbox" name="intact" value="checkbox" disabled />
		    		</logic:notEqual>
		    	</logic:present>
		    	<logic:notPresent name="ContainerDetailsVOSession" property="intact">
		    		<input type="checkbox" name="intact" value="checkbox" disabled/>
		    	</logic:notPresent>
				<common:message key="mailtracking.defaults.arrivemail.lbl.intact" />
									</div>
									<div class="ic-button-container">
									 <ihtml:nbutton property="btnList" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_LIST" >
	        	  <common:message key="mailtracking.defaults.arrivemail.btn.list" />
      	       </ihtml:nbutton>
      	       <ihtml:nbutton property="btnNew" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_NEW" >
	     	       	  <common:message key="mailtracking.defaults.arrivemail.btn.new" />
      	       </ihtml:nbutton>
      	       <ihtml:nbutton property="btnDelete" componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_DELETE" >
	    	       	  <common:message key="mailtracking.defaults.arrivemail.btn.delete" />
      	       </ihtml:nbutton>
									</div>
							</fieldset>
							</div>