package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.PreAdviceDetailsVO;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * @author This method is used to Map the ResultSet into PreAdviceDetailsVO
 */
public class MailbagsInCARDITMapper implements Mapper<PreAdviceDetailsVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public PreAdviceDetailsVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		PreAdviceDetailsVO preAdviceDetailsVO = new PreAdviceDetailsVO();
		preAdviceDetailsVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFF"));
		preAdviceDetailsVO.setMailCategory(rs.getString("MALCTGCOD"));
		preAdviceDetailsVO.setOriginExchangeOffice(rs.getString("ORGEXGOFF"));
		preAdviceDetailsVO.setTotalbags(rs.getInt("COUNT"));
		preAdviceDetailsVO.setTotalWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCPWGT")),BigDecimal.valueOf(rs.getDouble("RCPWGT")),"K"));
		preAdviceDetailsVO.setUldNumbr(rs.getString("CONNUM"));
		return preAdviceDetailsVO;
	}
}
