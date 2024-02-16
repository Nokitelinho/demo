<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"%>
<%@ page import="java.util.Collection"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page info="lite" %>


<bean:define id="form"
	name="ForceMajeureRequestForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm"
	toScope="page" />
				  
	<div id="reqpopSort" class="show-sorting2" style="display:none">
        <div class="sort-by">
<ul>
<li>			
			
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="mailID">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('mailID','ASC');"><b>Mailbag ID</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span></a>
								<% form.setSortingField("mailID");%>
								<% form.setSortOrder("ASC");%>
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#"  onClick="sortData('mailID','DESC');"><b>Mailbag ID</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("mailID");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="mailID">
							<a href="#"  onClick="sortData('mailID','');">Mailbag ID</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('mailID','');">Mailbag ID</a>
					</logic:notPresent>
			 </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="airportCode">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('airportCode','ASC');"><b>Airport</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("airportCode");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('airportCode','DESC');"><b>Airport </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("airportCode");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="airportCode">
							<a href="#"  onClick="sortData('airportCode','');">Airport</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('airportCode','');">Airport</a>
					</logic:notPresent>
			</li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="flightNumber">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('flightNumber','ASC');"><b>Flight No </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("flightNumber");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('flightNumber','DESC');"><b>Flight No </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("flightNumber");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="flightNumber">
							<a href="#"  onClick="sortData('flightNumber','');">Flight No</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('flightNumber','');">Flight No</a>
					</logic:notPresent>
            </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="flightDate">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('flightDate','ASC');"><b>Flight Date</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("flightDate");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('flightDate','DESC');"><b>Flight Date</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("flightDate");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="flightDate">
							<a href="#" onClick="sortData('flightDate','');">Flight Date</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('flightDate','');">Flight Date</a>
					</logic:notPresent>
             </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="type">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#"  onClick="sortData('type','ASC');"><b>Type</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("type");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#"  onClick="sortData('type','DESC');"><b>Type</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span></a> 
								<% form.setSortingField("type");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="type">
							<a href="#" onClick="sortData('type','');">Type</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('type','');">Type</a>
					</logic:notPresent>
               </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="weight">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('weight','ASC');"><b>Wt.(kg)</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("weight");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('weight','DESC');"><b>Wt.(kg)</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("weight");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="weight">
							<a href="#" onClick="sortData('weight','');">Wt.(kg)</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('weight','');">Wt.(kg)</a>
					</logic:notPresent>
                 </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="originAirport">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#"  onClick="sortData('originAirport','ASC');"><b>Org </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("originAirport");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('originAirport','DESC');"><b>Org</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("originAirport");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="originAirport">
							<a href="#" onClick="sortData('originAirport','');">Org</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('originAirport','');">Org</a>
					</logic:notPresent>
               </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="destinationAirport">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('destinationAirport','ASC');"><b>Dest</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("destinationAirport");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#"  onClick="sortData('destinationAirport','DESC');"><b>Dest</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("destinationAirport");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="destinationAirport">
							<a href="#"  onClick="sortData('destinationAirport','');">Dest</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('destinationAirport','');">Dest</a>
					</logic:notPresent>
                 </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="consignmentDocNumber">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('consignmentDocNumber','ASC');"><b>Consignment No.</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("consignmentDocNumber");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('consignmentDocNumber','DESC');"><b>Consignment No.</b> <span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("consignmentDocNumber");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="consignmentDocNumber">
							<a href="#" onClick="sortData('consignmentDocNumber','');">Consignment No.</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('consignmentDocNumber');">Consignment No.</a>
					</logic:notPresent>
                </li>
				<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="Forceid">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('Forceid','ASC');"><b>Force Majeure Id </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("Forceid");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('Forceid','DESC');"><b>Force Majeure Id</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("Forceid");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="Forceid">
							<a href="#" onClick="sortData('Forceid','');">Force Majeure Id</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('Forceid','');">Force Majeure Id</a>
					</logic:notPresent>
            </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="status">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('status','ASC');"><b>Status</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("status");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('status','DESC');"><b>Status</b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
									<% form.setSortingField("status");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="status">
							<a href="#" onClick="sortData('status','');">Status</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('status','');">Status</a>
					</logic:notPresent>
            </li>
			<li>
					<logic:present name="form" property="sortOrder">
						<logic:equal name="form" property="sortingField" value="lastUpdatedUser">
							<logic:equal name="form" property="sortOrder" value="ASC">
								<a href="#" onClick="sortData('lastUpdatedUser','ASC');"><b>User ID </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-up"></i></span> </a>
								<% form.setSortingField("lastUpdatedUser");%>
								<% form.setSortOrder("ASC");%> 
							</logic:equal>
							<logic:equal name="form" property="sortOrder" value="DESC">
								<a href="#" onClick="sortData('lastUpdatedUser','DESC');"><b>User ID </b><span class="float-right"><i class="icon ico-updown-dark ico-updown-dark-down"></i></span> </a>
								<% form.setSortingField("lastUpdatedUser");%>
								<% form.setSortOrder("DESC");%> 
							</logic:equal>
						</logic:equal>
						<logic:notEqual name="form" property="sortingField" value="lastUpdatedUser">
							<a href="#" onClick="sortData('lastUpdatedUser','');">User ID</a>
						</logic:notEqual>
					</logic:present>
					<logic:notPresent name="form" property="sortOrder">
						<a href="#" onClick="sortData('lastUpdatedUser','');">User ID</a>
					</logic:notPresent>
            </li>
		</ul>
		</div>
	</div>