package com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;
/**
 * @author A-1952
 *
 */
public class UtilisedDocsInfoForm extends ScreenModel
{

	
	 private String fromRange;
	 private String fromScreen;

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	public String getFromRange() {
		return fromRange;
	}

	public void setFromRange(String fromRange) {
		this.fromRange = fromRange;
	}
	// The key attribute specified in struts_config.xml file.
	private static final String BUNDLE = "utiliseddocsinforesources";

	private String bundle;


	/**
	* @return Returns the bundle.
	*/
	public String getBundle() {
		return BUNDLE;
	}
	/**
	* @param bundle The bundle to set.
	*/
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

/**
 * @return ScreenId
 */
    public String getScreenId()
    {
        return "stockcontrol.defaults.utiliseddocsinfo";
    }
/**
 * @return Product
 */
    public String getProduct()
    {
        return "stockcontrol";
    }
/**
 * @return SubProduct
 */
    public String getSubProduct()
    {
        return "defaults";
    }


}