 <%--
  /***********************************************************************
 * Project	 			:  iCargo
 * Module Code & Name	:  IN - Inventory Control
 * File Name			:  MaintainProduct_Restriction_Tab.jsp
 * Date					:  15-July-2001
 * Author(s)			:  Amritha S

 *************************************************************************/
  --%>


<%@ include file="/jsp/includes/tlds.jsp" %>


 <bean:define id="form"
 	name="MaintainProductForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
 	toScope="page" />
<business:sessionBean id="KEY_WEIGHTVO"
					  moduleName="product.defaults"
					  screenID="products.defaults.maintainproduct"
					  method="get"
					  attribute="weightVO" />
					  
<business:sessionBean id="KEY_VOLUMEVO"
					  moduleName="product.defaults"
					  screenID="products.defaults.maintainproduct"
					  method="get"
					  attribute="volumeVO" />
					  
<business:sessionBean id="KEY_DIMENSIONVO"
					  moduleName="product.defaults"
					  screenID="products.defaults.maintainproduct"
					  method="get"
					  attribute="dimensionVO" />					  
					  
<div class="ic-row">
	<div class="ic-col-30">
		<div class="ic-border">
			<jsp:include page="MaintainProduct_RestrictionTab_Commodity.jsp" />
		</div>
	</div>
	<div class="ic-col-70">
		<div class="ic-border">
			<jsp:include page="MaintainProduct_Restriction_Tab_Route.jsp" />
		</div>
	</div>
</div>
<div class="ic-row">
	<div class="ic-col-30">
		<div class="ic-border">
			<jsp:include page="MaintainProduct_RestrictionTab_CustGrpTable.jsp" />
		</div>
	</div>
	<div class="ic-col-70">
		<div class="ic-row marginT5">
			<div class="ic-col-33">
				<div class="ic-border">
					<jsp:include page="MaintainProduct_RestrictionTab_PaymtTerm.jsp" />
				</div>
			</div>
			<div class="ic-col-35">
				<div class="ic-row">
					<h3><common:message  key="products.defaults.CaptyRest" scope="request"/></h3>
				</div>
				<div class="ic-row" >
				<div class="ic-col-100">
					<div class="ic-border"><div class="ic-section ic-marg-1">
						<div class="ic-row" style="height:20px">
							<div class="ic-col-15">
								&nbsp;
							</div>
							<div class="ic-col-25" style="text-align:center">
								<common:message  key="products.defaults.Min" scope="request"/>
							</div>
							<div class="ic-col-25" style="text-align:center">
								<common:message  key="products.defaults.Max" scope="request"/>
							</div>
							<div class="ic-col-35">
								&nbsp;
							</div>
						</div>
						<div class="ic-row" style="height:30px">
							<div class="ic-col-15">
								<common:message  key="products.defaults.Weight" scope="request"/>
							</div>
							<logic:present name="KEY_WEIGHTVO">
								<bean:define id="unitRoundingVO" name="KEY_WEIGHTVO" />
								<bean:define id="length" name="form" property="minWeight" />
								<bean:define id="width" name="form" property="maxWeight" />												
								<% request.setAttribute("sample",unitRoundingVO); %>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minWeight" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(length)%>" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxWeight" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(width)%>" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minWeight,maxWeight" 
										title="Dimension Unit" dataName="sample" unitListName="weightUnit" 
										unitListValue="<%=form.getWeightUnit()%>"/>
								</div>
							</logic:present>
							<logic:notPresent name="KEY_WEIGHTVO">
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minWeight" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxWeight" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minWeight,maxWeight" 
										title="Dimension Unit" dataName="" unitListName="weightUnit" unitListValue=""/>
								</div>
							</logic:notPresent>							
						</div>
						<div class="ic-row" style="height:30px">
							<div class="ic-col-15">
								<common:message  key="products.defaults.Volume" scope="request"/>
							</div>
							<logic:present name="KEY_VOLUMEVO">
								<bean:define id="unitRoundingVO" name="KEY_VOLUMEVO" />
								<bean:define id="minvol" name="form" property="minVolume" />
								<bean:define id="maxvol" name="form" property="maxVolume" />												
								<% request.setAttribute("sample",unitRoundingVO); %>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minVolume" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(minvol)%>" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxVolume" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(maxvol)%>" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minVolume,maxVolume" 
										title="Dimension Unit" dataName="sample" unitListName="volumeUnit" 
										unitListValue="<%=form.getVolumeUnit()%>"/>
								</div>
							</logic:present>
							<logic:notPresent name="KEY_VOLUMEVO">
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minVolume" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxVolume" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minVolume,maxVolume" 
										title="Dimension Unit" dataName="" unitListName="weightUnit" unitListValue=""/>
								</div>
							</logic:notPresent>							
						</div>
						<!--Added as part CR ICRD-232462 begins-->
						<div class="ic-row" style="height:30px">
							<div class="ic-col-15">
								<common:message  key="products.defaults.LinearDimension" scope="request"/>
							</div>
							<logic:present name="KEY_DIMENSIONVO">
								<bean:define id="unitRoundingVO" name="KEY_DIMENSIONVO" />
								<bean:define id="mindim" name="form" property="minDimension" />
								<bean:define id="maxdim" name="form" property="maxDimension" />												
								<% request.setAttribute("sample",unitRoundingVO); %>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minDimension" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(mindim)%>" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxDimension" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall"
										unitValue="<%=String.valueOf(maxdim)%>" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minDimension,maxDimension" 
										title="Dimension Unit" dataName="sample" unitListName="dimensionUnit" 
										unitListValue="<%=form.getDimensionUnit()%>"/>
								</div>
							</logic:present>
							<logic:notPresent name="KEY_DIMENSIONVO">
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef1" style="text-align:right" unitTxtName="minDimension" label="" 
										title="length" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-25">								
									<ibusiness:unitdef id="unitdef2" style="text-align:right" unitTxtName="maxDimension" label="" 
										title="width" dataName="sample"  unitValueStyle="iCargoTextFieldSmall" unitValue="" />
								</div>
								<div class="ic-col-35">
									<ibusiness:customUnitCombo unitTxtName="minDimension,maxDimension" 
										title="Dimension Unit" dataName="sample" unitListName="weightUnit" unitListValue=""/>
								</div>
							</logic:notPresent>							
						</div>
						<!--Added as part CR ICRD-232462 ends-->
					</div></div></div>		
				</div>
			</div>
			<div class="ic-col-30">
				<div class="ic-split-100">
					<h3><common:message  key="products.defaults.AddtnRest" scope="request"/></h3>
				</div>
				<div class="ic-split-100">
					<ihtml:textarea property="addRestriction" cols="25" rows="7" style="width:250px"
						componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ADDRESTRICTION" ></ihtml:textarea>
				</div>
			</div>
		</div>
	</div>
</div>
