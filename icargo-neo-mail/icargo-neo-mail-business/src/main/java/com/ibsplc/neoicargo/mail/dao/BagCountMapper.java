package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.currency.Money;

/** 
 * This class is used to calculate the No of Bags in case of the Barrow for the Flight or Destination
 */
public class BagCountMapper implements Mapper<ContainerVO> {
	/** 
	* @author a-1936
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	*/
	public ContainerVO map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		ContainerVO containerVO = new ContainerVO();
		containerVO.setBags(rs.getInt("ACPBAG"));
		containerVO.setContainerNumber(rs.getString("CONNUM"));
		containerVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
		containerVO.setReceivedBags(rs.getInt("RCVBAG"));
		containerVO.setReceivedWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("RCVWGT"))));
		containerVO.setWarehouseCode(rs.getString("WHSCOD"));
		containerVO.setLocationCode(rs.getString("LOCCOD"));
		return containerVO;
	}
}
