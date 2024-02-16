<%@ include file="/jsp/includes/tlds.jsp" %>
<thead>
									<tr>
										<td class="iCargoTableHeaderLabel" width="2%">
													<input name="masterMail" type="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.selectMail);" />
												</td>
												<td width="15%">
													<common:message key="mailtracking.defaults.consignment.lbl.mailbagid"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>												
												<td width="6%">
													<common:message key="mailtracking.defaults.consignment.lbl.ooe"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="7%">
													<common:message key="mailtracking.defaults.consignment.lbl.doe"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.cat"/>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.class"/>
												</td>
												<td width="6%">
													<common:message key="mailtracking.defaults.consignment.lbl.sc"/>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.yr"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="6%">
													<common:message key="mailtracking.defaults.consignment.lbl.dsn"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="5%">
													<common:message key="mailtracking.defaults.consignment.lbl.rsn"/>
												</td>
												<td width="6%">
													<common:message key="mailtracking.defaults.consignment.lbl.numbags"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.hni"/>
												</td>
												<td width="4%">
													<common:message key="mailtracking.defaults.consignment.lbl.ri"/>
												</td>
												<td width="10%">
													<common:message key="mailtracking.defaults.consignment.lbl.wt"/><span class="iCargoMandatoryFieldIcon">*</span>
												</td>
												       <common:xgroup>
                                                          <common:xsubgroup id="TURKISH_SPECIFIC">
														  <ihtml:hidden  property="reportFlag" value="Y"/>
												<td width="10%">
													<common:message key="mailtracking.defaults.consignment.lbl.decalredvalue"/>
												</td>
												<td width="8%">
													<common:message key="mailtracking.defaults.consignment.lbl.currencycode"/>
												</td>
												          </common:xsubgroup> 
                                                        </common:xgroup >
												<td width="10%">
													<common:message key="mailtracking.defaults.consignment.lbl.uldnum"/>
												</td>
									</tr>
								</thead>