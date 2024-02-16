import React, { PureComponent } from 'react';
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
export default class MailbagTableCustomRowPanel extends PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        console.log("subrow");
        const { rowData } = this.props
        return (
            <div className="pad-y-2xs pad-x-md">
                <div className="row">
                    <DataDisplay label="RI" id="ri" className="col-auto">
                        <div className="text-grey mar-b-2xs">RI</div>
                        <div className="text-common strong">
                            {
                                rowData.registeredOrInsuredIndicator != null && rowData.registeredOrInsuredIndicator != undefined && rowData.registeredOrInsuredIndicator.trim().length > 0 ?
                                    rowData.registeredOrInsuredIndicator : "--"
                            }
                        </div>
                    </DataDisplay>
                    <DataDisplay label="Weight" id="weight" className="col-auto">
                        <div className="text-grey mar-b-2xs">Weight</div>
                        <div className="text-common strong">
                            {
                                rowData.weight != null && rowData.weight != undefined ? rowData.weight.roundedDisplayValue : "--"
                                 // rowData.weight.displayValue : "--"                                                                        
                            }
                        </div>
                    </DataDisplay>
                    <DataDisplay label="Consignment Number" id="consignmentNumber" className="col-auto">
                        <div className="text-grey mar-b-2xs">Consignment No</div>
                        <div className="text-common strong">
                            {
                                rowData.consignmentNumber != null && rowData.consignmentNumber != undefined && rowData.consignmentNumber.trim().length > 0 ?
                                    rowData.consignmentNumber : "--"
                            }
                        </div>
                    </DataDisplay>
                    <DataDisplay label="Consignement Date" id="consignmentDate" className="col-auto">
                        <div className="text-grey mar-b-2xs">Consignment Date</div>
                        <div className="text-common strong">
                            {
                                rowData.consignmentDate != null && rowData.consignmentDate != undefined && rowData.consignmentDate.trim().length > 0 ?
                                    rowData.consignmentDate : "--"
                            }
                        </div>
                    </DataDisplay>
                    <DataDisplay label="AWB No." id="awbNo" className="col-auto">
                        <div className="text-grey mar-b-2xs">AWB No.</div>
                        <div className="text-common strong">
                            {
                                rowData.shipmentPrefix != null && rowData.shipmentPrefix != undefined && rowData.shipmentPrefix.trim().length > 0 ?
                                    rowData.shipmentPrefix + "-" + rowData.masterDocumentNumber : "--"
                            }
                        </div>
                    </DataDisplay>
                    <DataDisplay label="RDT" id="rdt" className="col-auto">
                        <div className="text-grey mar-b-2xs">RDT</div>
                        <div className="text-common strong">
                            {
                                rowData.reqDeliveryDate != null && rowData.reqDeliveryDate != undefined && rowData.reqDeliveryDate.trim().length > 0 ?
                                    rowData.reqDeliveryDate + " " + rowData.reqDeliveryTime : "--"
                            }
                        </div>
                    </DataDisplay>

                    <DataDisplay label="ULD Number" id="uldNumber" className="col">
                        <div className="text-grey mar-b-2xs">ULD Number</div>
                        <div className="text-common strong">
                            {
                                rowData.uldNumber != null && rowData.uldNumber != undefined && rowData.uldNumber.trim().length > 0 ?
                                    rowData.uldNumber : "--"
                            }
                        </div>
                    </DataDisplay>


                </div>
            </div>

        );
    }
}