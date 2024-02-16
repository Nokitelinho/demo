package com.ibsplc.icargo.presentation.web.model.mail.operations;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.framework.web.spring.model.AbstractScreenModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailRuleConfig;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailRuleConfigFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class MailRuleConfigModel extends AbstractScreenModel{
	

	/*
	 * The constant variable for product mail
	 */
	private static final String PRODUCT = "mail";
	/*
	 * The constant for sub product operations
	 */
	private static final String SUBPRODUCT = "operations";
	/*
	 * The constant for screen id
	 */
	private static final String SCREENID = "mail.operations.ux.messageruleconfig";
	
	private Map<String, Collection<OneTime>> oneTimeValues;
	private MailRuleConfigFilter mailRuleConfigFilter;
	private List<MailRuleConfig> mailRuleConfigList ;
	private MailRuleConfig mailRuleConfig ;

	public String getProduct() {
		return PRODUCT;
	}

	
	public String getScreenId() {
		return SCREENID;
	}

	
	public String getSubProduct() {
		return SUBPRODUCT;
	}


	public Map<String, Collection<OneTime>> getOneTimeValues() {
		return oneTimeValues;
	}


	public void setOneTimeValues(Map<String, Collection<OneTime>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}


	public MailRuleConfigFilter getMailRuleConfigFilter() {
		return mailRuleConfigFilter;
	}


	public void setMailRuleConfigFilter(MailRuleConfigFilter mailRuleConfigFilter) {
		this.mailRuleConfigFilter = mailRuleConfigFilter;
	}


	public List<MailRuleConfig> getMailRuleConfigList() {
		return mailRuleConfigList;
	}


	public void setMailRuleConfigList(List<MailRuleConfig> mailRuleConfigList) {
		this.mailRuleConfigList = mailRuleConfigList;
	}


	public MailRuleConfig getMailRuleConfig() {
		return mailRuleConfig;
	}


	public void setMailRuleConfig(MailRuleConfig mailRuleConfig) {
		this.mailRuleConfig = mailRuleConfig;
	}


	
	
	
	
	}