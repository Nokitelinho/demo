package com.ibsplc.neoicargo.mail.util;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.measure.MeasureUtil;
import com.ibsplc.icargo.framework.util.unit.vo.MeasureUnitVO;

public class MeasureUtils {

	MeasureUnitVO getMeasureUnitInSystemUnit(String unitType, String displayUnit, double displayValue,
			MeasureUtil measureUtil) throws UnitException {
		String systemUnit = measureUtil.getSystemUnit(unitType, displayUnit);
		MeasureUnitVO vo = new MeasureUnitVO();
		vo.setDisplayUnit(displayUnit);
		vo.setDisplayValue(displayValue);
		vo.setUnitType(unitType);
		vo.setSystemUnit(systemUnit);
		measureUtil.populateUnitConvertedValues(systemUnit, displayUnit, vo);
		return vo;
	}

	public Measure getNewMeasureValue(String unitType, double displayValue, String displayUnit,
			MeasureUtil measureUtil) throws UnitException {
		MeasureUnitVO unitVO = getMeasureUnitInSystemUnit(unitType, displayUnit, displayValue, measureUtil);
		unitVO.setUnitType(unitType);
		return new Measure(measureUtil, unitType, unitVO.getSystemValue(), displayValue, displayUnit);
	}
}
