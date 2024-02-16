/*
 * ProductIcon.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.sql.Blob;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.PersistenceUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */
 @Table(name="PRDICO")
 @Entity
 @Staleable
public class ProductIcon {

	 private Log log = LogFactory.getLogger("PRODUCTICON");
	 private ProductPK productPk;

    private Blob productIcon;
    private String iconContentType;

    /**
     * @return Returns the productIconPk.
     */

    @EmbeddedId
    	@AttributeOverrides({
			 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			 @AttributeOverride(name="productCode", column=@Column(name="PRDCOD"))
		})
    public ProductPK getProductPk() {
        return productPk;
    }
    /**
     * @param productIconPk The productIconPk to set.
     */
    public void setProductPk(ProductPK productPk) {
        this.productPk = productPk;
    }
	/**
	 * Default Constructor
	 *
	 */
	public ProductIcon(){
	 }
	/**
	 * @author A-1885
	 * @param productVO
	 * @throws SystemException
	 */
	public ProductIcon(ProductVO productVO,ProductPK productPK) throws SystemException{
		log.log(Log.FINE, "*******Company Code---------->", productVO.getCompanyCode());
		log.log(Log.FINE, "*******Product Code---------->", productPK.getProductCode());
		/*
		 * Modified for EJB3 Modification
		 */
		/*ProductIconPK productIconPK = new ProductIconPK();
		productIconPK.companyCode =productVO.getCompanyCode();
		productIconPK.productCode=productCode;*/
		//productIconPK.iconId="ID";
	 	this.productPk = productPK;
	 	populateAttribute(productVO);
	 	/*try{
	 	PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}*/

	}
	/**
	 * @author A-1885
	 * Method for Hibernate find of product Icon
	 * @param companyCode
	 * @param productCode
	 * @return productIcon
	 * @throws SystemException
	 */
	public static ProductIcon find(String companyCode, String productCode)
	throws SystemException{
		ProductIcon producticon=null;
		ProductPK productPK = new ProductPK();
		productPK.setCompanyCode(companyCode);
		productPK.setProductCode(productCode);
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			producticon=entityManager.find(ProductIcon.class,productPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return producticon;
	}
	/**
	 * @author A-1885
	 * @param productVo
	 */
	public void update(ProductVO productVo)throws SystemException{
		populateAttribute(productVo);
	}
	/**
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateAttribute(ProductVO productVo)throws SystemException{
		ImageModel imageModel =	productVo.getImage();
		log.log(Log.FINE, "The Blob::>", imageModel);
		log.log(Log.FINE, "imageModel.getContentType()::>", imageModel.getContentType());
		Blob tBlob =null;
		try {
			tBlob =PersistenceUtils.createBlob(imageModel.getData());
			} catch (SystemException e) {
		// To be reviewed Auto-generated catch block
			e.getMessage();
		}
		this.setProductIcon(tBlob);
		this.setIconContentType(imageModel.getContentType());
	}


	/**
	 * @author A-1885
	 * @return productIcon
	 */
	@Lob
	@Column(name = "PRDICO")
	public Blob getProductIcon() {
	Blob tBlob =null;
		//try {
		//	tBlob =PersistenceUtils.createBlob(productIcon);
		//} catch (SystemException e) {
			// To be reviewed Auto-generated catch block
//printStackTraccee()();
		//}
		/*
		
		Byte temp[] = new Byte[productIcon.length];
		int count = 0;
		for(byte _data : productIcon){
			temp[count] = Byte.valueOf(_data);
			count++;
			
		}
		
		reurn temp;*/
		return productIcon;
	}
	/**
	 * @author A-1885
	 * @param productIcon
	 */
	public void setProductIcon(Blob productIcon) {
		this.productIcon = productIcon;
	}
	/**
	 * @return Returns the iconContentType.
	 */
	@Column(name ="ICOCONTYP")
	public String getIconContentType() {
		return iconContentType;
	}
	/**
	 * @param iconContentType The iconContentType to set.
	 */
	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

}
