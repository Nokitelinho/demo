import React, { PureComponent } from 'react';
import { Row, Col } from "reactstrap";
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
export default class MailbagTableCustomRowPanel extends PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        console.log("subrow");
        const { rowData } = this.props
       
      
        // if( rowData.isUSPSPerformed != null && rowData.isUSPSPerformed != undefined && rowData.isUSPSPerformed.trim().length > 0){
        //    if(rowData.isUSPSPerformed == 'Y')
        //         rowData.isUSPSPerformed ='Yes';
        //     else if (rowData.isUSPSPerformed == 'N')
        //         rowData.isUSPSPerformed ='No';  
        //     else
        //         rowData.isUSPSPerformed ='NA';    
        // }
        
        return (

            <div className="hidden-table thead-bg  pad-sm">
                <Row>
                    <div className=" col-5 border-right">

                        <Row id="rateType">
                            <div className="col-12 text-grey">UPU/Contract:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.rateType != null && rowData.rateType != undefined && rowData.rateType.trim().length > 0 ?
                                        rowData.rateType : "--"
                                }
                            </div>
                        </Row>
                        <Row id="orgOfficeOfExchange">
                            <div className="col-12 text-grey">Org OE:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.orgOfficeOfExchange != null && rowData.orgOfficeOfExchange != undefined && rowData.orgOfficeOfExchange.trim().length > 0 ?
                                        rowData.orgOfficeOfExchange : "--"
                                }
                            </div>
                        </Row>
                        <Row id="destOfficeOfExchange">
                            <div className="col-12 text-grey">Dest OE:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.destOfficeOfExchange != null && rowData.destOfficeOfExchange != undefined && rowData.destOfficeOfExchange.trim().length > 0 ?
                                        rowData.destOfficeOfExchange : "--"
                                }
                            </div>
                        </Row>
                        <Row id="category">
                            <div className="col-12 text-grey">Cat:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.category != null && rowData.category != undefined && rowData.category.trim().length > 0 ?
                                        rowData.category : "--"
                                }
                            </div>
                        </Row>
                        <Row id="subClass">
                            <div className="col-12 text-grey">Class:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.subClass != null && rowData.subClass != undefined && rowData.subClass.trim().length > 0 ?
                                        rowData.subClass : "--"
                                }
                            </div>
                        </Row>                        
                    </div>

                    <div className=" col-5 border-right">
                        <Row id="csgDocumentNumber">
                            <div className="col-11 text-grey">Cons Doc. No:</div>
                            <div className="col-12 text-black ">
                                {
                                    rowData.csgDocumentNumber != null && rowData.csgDocumentNumber != undefined && rowData.csgDocumentNumber.trim().length > 0 ?
                                        rowData.csgDocumentNumber : "--"
                                }
                            </div>
                       </Row>                        
                        <Row id="year">
                            <div className="col-11 text-grey">Year:</div>
                            <div className="col-12 text-black ">
                                {
                                    rowData.year != null && rowData.year != undefined && rowData.year.trim().length > 0 ?
                                        rowData.year : "--"
                                }
                            </div>
                        </Row>                        
                        <Row id="dsn">
                            <div className="col-11 text-grey">DSN:</div>
                            <div className="col-12 text-black ">
                                {
                                    rowData.dsn != null && rowData.dsn != undefined && rowData.dsn.trim().length > 0 ?
                                        rowData.dsn : "--"
                                }
                            </div>
                        </Row>
                        <Row id="rsn">
                            <div className="col-11 text-grey">RSN:</div>
                            <div className="col-12 text-black ">
                                {
                                    rowData.rsn != null && rowData.rsn != undefined && rowData.rsn.trim().length > 0 ?
                                        rowData.rsn : "--"
                                }
                            </div>
                        </Row>
                    </div>

                    <div className=" col-4 border-right">
                        <Row id="hni">
                            <div className="col-10 text-grey">HNI:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.hni != null && rowData.hni != undefined && rowData.hni.trim().length > 0 ?
                                        rowData.hni : "--"
                                }
                            </div>
                        </Row>                        
                        <Row id="regInd">
                            <div className="col-10 text-grey">RI:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.regInd != null && rowData.regInd != undefined && rowData.regInd.trim().length > 0 ?
                                        rowData.regInd : "--"
                                }
                            </div>
                       </Row>                        
                        <Row id="grossAmount">
                            <div className="col-10 text-grey">Gross Amt:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.grossAmount != null && rowData.grossAmount != undefined && rowData.grossAmount != 0.0 ?
                                        rowData.grossAmount : "--"
                                }
                            </div>
                        </Row>                        
                                               
                        <Row id="valCharges">
                            <div className="col-10 text-grey">Val Chgs:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.valCharges != null && rowData.valCharges != undefined && rowData.valCharges != 0.0 ?
                                        rowData.valCharges : "--"
                                }
                            </div>
                        </Row>
                        <Row id="pabuilt">
                            <div className="col-11 text-grey">Actual wt:</div>
                            <div className="col-13 text-black  ">
                                { (   rowData.paBuilt != null &&  rowData.paBuilt != null && rowData.paBuilt != undefined && rowData.paBuilt.trim().length > 0  && "Yes"=== rowData.paBuilt ) &&
                                    rowData.actualWeight != null && rowData.actualWeight != undefined && rowData.actualWeight >=0.0 ?
                                        rowData.actualWeight : "--"
                                }
                                {
                                     (   rowData.paBuilt != null &&  rowData.paBuilt != null && rowData.paBuilt != undefined && rowData.paBuilt.trim().length > 0  && "Yes"=== rowData.paBuilt ) &&
                                    rowData.actualWeightUnit != null && rowData.actualWeightUnit != undefined && rowData.actualWeightUnit.trim().length > 0 ?
                                        rowData.actualWeightUnit : ""
                                }
                            </div>
                        </Row>  
                    </div>

                    <div className=" col-5 border-right">
                        <Row id="declaredValue">
                            <div className="col-12 text-grey">Declared Value:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.declaredValue != null && rowData.declaredValue != undefined && rowData.declaredValue != 0.0 ?
                                        rowData.declaredValue : "--"
                                }
                            </div>
                        </Row>                        
                        <Row id="surChg">
                            <div className="col-12 text-grey">Surcharge:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.surChg != null && rowData.surChg != undefined && rowData.surChg != 0.0 ?
                                        rowData.surChg : "--"
                                }
                            </div>
                        </Row>                       
                        <Row id="serviceTax">
                            <div className="col-12 text-grey">S. Tax:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.serviceTax != null && rowData.serviceTax != undefined && rowData.serviceTax != 0.0 ?
                                        rowData.serviceTax : "--"
                                }
                            </div>
                        </Row>
                        <Row id="netAmount">
                            <div className="col-12 text-grey">Net Amount:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.netAmount != null && rowData.netAmount != undefined && rowData.netAmount != 0.0 ?
                                        rowData.netAmount : "--"
                                }
                            </div>
                        </Row>

                        <Row id="paBuilt">
                            <div className="col-11 text-grey">PA Built:</div>
                            <div className="col-13 text-black  ">
                                {
                                    rowData.paBuilt != null && rowData.paBuilt != undefined &&  rowData.paBuilt.trim().length > 0 ?
                                        rowData.paBuilt : "No"
                                }
                            </div>
                        </Row>  
                          
                       
                          
                        <Row id="containerID">
                            <div className="col-11 text-grey">Container ID:</div>
                            <div className="col-13 text-black  ">
                                {
                                   (   rowData.paBuilt != null &&  rowData.paBuilt != null && rowData.paBuilt != undefined && rowData.paBuilt.trim().length > 0  && "Yes"=== rowData.paBuilt ) &&
                                    rowData.containerID != null && rowData.containerID != undefined && rowData.containerID.trim().length > 0 ?
                                        rowData.containerID : "--"
                                }
                            </div>
                        </Row>  
                    </div>

                    <div className=" col-5 ">
                        <Row id="invoiceNumber">
                            <div className="col-11 text-grey">Inv No:</div>
                            <div className="col-13 text-black ">
                                {
                                    rowData.invoiceNumber != null && rowData.invoiceNumber != undefined && rowData.invoiceNumber.trim().length > 0 ?
                                        rowData.invoiceNumber : "--"
                                }
                            </div>
                        </Row>                        
                        <Row id="ccaRefNumber">
                            <div className="col-11 text-grey">MCA No:</div>
                            <div className="col-13 text-black ">
                                {
                                    rowData.ccaRefNumber != null && rowData.ccaRefNumber != undefined && rowData.ccaRefNumber.trim().length > 0 ?
                                        rowData.ccaRefNumber : "--"
                                }
                            </div>
                        </Row>                        
                        <Row id="rateIdentifier">
                            <div className="col-11 text-grey">Rate Card:</div>
                            <div className="col-13 text-black ">
                                {
                                    rowData.rateIdentifier != null && rowData.rateIdentifier != undefined && rowData.rateIdentifier.trim().length > 0 ?
                                        rowData.rateIdentifier : "--"
                                }
                            </div>
                        </Row>
                        <Row id="rateLineIdentifier">
                            <div className="col-11 text-grey">Rate Line:</div>
                            <div className="col-13 text-black ">
                                {
                                    rowData.rateLineIdentifier != null && rowData.rateLineIdentifier != undefined && rowData.rateLineIdentifier.trim().length > 0 ?
                                        rowData.rateLineIdentifier : "--"
                                }
                            </div>
                        </Row> 
                        <Row id="isUSPSPerformed">
                            <div className="col-11 text-grey">USPS Performance Met:</div>
                            <div className="col-13 text-black  ">
                                {
                                    rowData.isUSPSPerformed != null && rowData.isUSPSPerformed != undefined && rowData.isUSPSPerformed.trim().length > 0 ?
                                        rowData.isUSPSPerformed : "--"
                                }
                            </div>
                        </Row> 
                        


                    </div>                    
                </Row>
            </div>

        );
    }
}