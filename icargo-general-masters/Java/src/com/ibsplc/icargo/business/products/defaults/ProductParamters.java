/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductParamters.java
 *
 *	Created by	:	A-7740
 *	Created on	:	03-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductParamters.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7740	:	03-Oct-2018	:	Draft
 */
@Staleable
@Table(name="PRDPARDTL")
@Entity
public class ProductParamters {
	private Log log = LogFactory.getLogger("ProductParamters");	
	private ProductParamterPK ProductParamterPK;
	private String parameterValue;
	public ProductParamters() {
	}
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
		@AttributeOverride(name="parameterCode", column=@Column(name="PARCOD"))})
	@Audit(name = "ProductParamterPK")
	public ProductParamterPK getProductParamterPK() {
		return ProductParamterPK;
	}
	public void setProductParamterPK(ProductParamterPK productParamterPK) {
		this.ProductParamterPK = productParamterPK;
	}
	@Column(name = "PARVAL")
	@Audit(name="ParameterValue")
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public ProductParamters(ProductVO ProductVO,ProductParamterVO ProductParamterVO) throws SystemException{
		populatePK(ProductVO,ProductParamterVO);
		populateAttributes(ProductParamterVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			new SystemException(createException.getErrorCode(),createException);
		}
	}
	private void populatePK(ProductVO ProductVO,ProductParamterVO ProductParamterVO){
		ProductParamterPK productParamterPK = new ProductParamterPK();
		productParamterPK.setCompanyCode(ProductVO.getCompanyCode());
		productParamterPK.setProductCode(ProductVO.getProductCode());
		productParamterPK.setParameterCode(ProductParamterVO.getParameterCode());
		this.setProductParamterPK(productParamterPK);
	}
	private void populateAttributes(ProductParamterVO ProductParamterVO){
		log.log(Log.FINE, "THE ProductParamterVO.getParameterValue()",
				ProductParamterVO.getParameterValue());
		log.log(Log.FINE, "THE ProductParamterVO.getParameterValue()",
				ProductParamterVO.getParameterValue());
		this.setParameterValue(ProductParamterVO.getParameterValue());
	}
	public static ProductParamters find(ProductVO ProductVO,ProductParamterVO ProductParamterVO)throws SystemException,FinderException{
		ProductParamterPK productParamterPK =new ProductParamterPK();
		productParamterPK.setCompanyCode(   ProductVO.getCompanyCode());
		productParamterPK.setProductCode(   ProductVO.getProductCode());
		productParamterPK.setParameterCode(ProductParamterVO.getParameterCode());
		return PersistenceController.getEntityManager().find(ProductParamters.class, productParamterPK);
	}
	public void update(ProductParamterVO ProductParamterVO) throws SystemException{
		populateAttributes(ProductParamterVO);
	}
	public void remove()throws SystemException{
		try{
			PersistenceController.getEntityManager().remove(this);
			PersistenceController.getEntityManager().flush();
		}catch(RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode(), persistenceException);
		}
	}
}