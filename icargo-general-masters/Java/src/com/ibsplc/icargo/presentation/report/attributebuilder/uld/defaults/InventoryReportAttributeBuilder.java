/*
 * InventoryReportAttributeBuilder.java Created on Dec 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 */

public class InventoryReportAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("Inventory Attribute Builder");

	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> columns = new Vector<String>();
		columns.add("CURARP");
		columns.add("ARLCOD");
		columns.add("ULDTYP");
		
		columns.add("ULDNOONE");
		columns.add("ULDNOTWO");
		columns.add("ULDNOTHR");
		columns.add("ULDNOFOU");
		columns.add("ULDNOFIV");
		columns.add("ULDNOSIX");
		columns.add("ULDNOSEV");
		columns.add("ULDNOEIG");
		columns.add("ULDNONIN");
		columns.add("ULDNOTEN");
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();

		log.log(Log.INFO, "indise attributyr", data);
		HashMap<String,ArrayList<ULDListVO>> groupedVos= new HashMap<String,ArrayList<ULDListVO>> ();
		 ArrayList<String> keyVals=new ArrayList<String>();
		for(ULDListVO vo:(ArrayList<ULDListVO>)data){
			
			String key= 
				new StringBuilder().append(vo.getCurrentStation()).append(vo.getOwnerAirlineCode()).append(vo.getUldTypeCode()).toString();
			
			if(!(groupedVos.containsKey(key))){
				groupedVos.put(key,new ArrayList<ULDListVO>());
				groupedVos.get(key).add(vo);
				keyVals.add(key);
			}else{
				groupedVos.get(key).add(vo);
			}
		}
		
		log.log(Log.INFO, "key valys", keyVals);
		for(String keyValue:keyVals){
			Vector<Object> row = new Vector<Object>();
			
			ArrayList<ULDListVO> listVOs= groupedVos.get(keyValue);
			int count=0;
			
			log.log(Log.INFO, "key value", keyValue);
			log.log(Log.INFO, "listvos key value", listVOs);
			ArrayList<ULDListVO> listVOs3=new ArrayList<ULDListVO>();
			ArrayList<ULDListVO> listVOs4=new ArrayList<ULDListVO>();
			ArrayList<ULDListVO> listVOs5=new ArrayList<ULDListVO>();
			
			if (listVOs != null && listVOs.size() > 0) {
				for(ULDListVO vo:listVOs){
					int endindex=vo.getUldNumber().length()-vo.getOwnerAirlineCode().length();
					String serialNo= vo.getUldNumber().substring(3, endindex);
					if(serialNo.length()==3){
						listVOs3.add(vo);
					}else if(serialNo.length()==4){
						listVOs4.add(vo);
					}else{
						listVOs5.add(vo);
					}
					
				}
				
			}
			ArrayList<ULDListVO> sortedVOs =new ArrayList<ULDListVO>();
			
			if (listVOs3 != null && listVOs3.size() > 0) {
				Collections.sort(listVOs3, new DesignComparator());
				sortedVOs.addAll(listVOs3);
			}
			if (listVOs4 != null && listVOs4.size() > 0) {
				Collections.sort(listVOs4, new DesignComparator());
				sortedVOs.addAll(listVOs4);
			}
			if (listVOs5 != null && listVOs5.size() > 0) {
				Collections.sort(listVOs5, new DesignComparator());
				sortedVOs.addAll(listVOs5);
			}
			
			
			log.log(Log.INFO, "sorted with value", sortedVOs);
			int size=sortedVOs.size();
			for(int i=0;i<size;i++){
				count++;
				
				if(count%10 == 1){
				row.add(sortedVOs.get(i).getCurrentStation());
				row.add(sortedVOs.get(i).getOwnerAirlineCode());
				row.add(sortedVOs.get(i).getUldTypeCode());
				}
				row.add(sortedVOs.get(i).getUldNumber());
				
				
				
				if(count%10 == 0){
					tableData.add(row);
					row=new Vector<Object>();
				}else{
					if(count==size){
						tableData.add(row);
						row=new Vector<Object>();
					}
				}
			}
			
		}
		
		
		return tableData;
	}

	/**
	 *
	 */
	class DesignComparator implements Comparator<ULDListVO> {
		/**
		 *
		 * @param vA
		 * @param vB
		 * @return
		 */
		public int compare(ULDListVO vA,
				ULDListVO vB) {
			int endindex1=vA.getUldNumber().length()-vA.getOwnerAirlineCode().length();
			int endindex2=vB.getUldNumber().length()-vB.getOwnerAirlineCode().length();
			String serialNo1= vA.getUldNumber().substring(3, endindex1);
			String serialNo2= vB.getUldNumber().substring(3, endindex2);
			
			int val=0;
			try{
			if(Integer.parseInt(serialNo1)<Integer.parseInt(serialNo2)){
				val=-1;
			}else if(Integer.parseInt(serialNo1)>Integer.parseInt(serialNo2)){
				val=1;
			}
			}
			catch(NumberFormatException e){
				val=1;
//printStackTrrace()();
			}
			
			return val;
		}
	}
	
	
}

