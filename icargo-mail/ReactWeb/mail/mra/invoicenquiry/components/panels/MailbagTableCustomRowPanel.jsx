import React, { PureComponent } from 'react';
import { Row, Col } from "reactstrap";
import { DataDisplay } from 'icoreact/lib/ico/framework/component/common/grid'
export default class MailbagTableCustomRowPanel extends PureComponent {
    constructor(props) {
        super(props);
    }
    render() {
        console.log("subrow");
        const { rowData } = this.props
        return (

            <div className="hidden-table thead-bg border-top">
                <Row>
                    <div className=" col-6 border-right">
                        <div className="pad-sm">
                            <Row id="consignment" className="mar-b-2xs">
                            <div className="col-14 text-grey">Consignment:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.consignment != null && rowData.consignment != undefined && rowData.consignment.trim().length > 0 ?
                                        rowData.consignment : "--"
                                }
                            </div>
                        </Row>
                            <Row id="codeshareInterlinePartner" className="mar-b-2xs">
                            <div className="col-14 text-grey">Codeshare/Interline Partner:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.codeshareInterlinePartner != null && rowData.codeshareInterlinePartner != undefined && rowData.codeshareInterlinePartner.trim().length > 0 ?
                                        rowData.codeshareInterlinePartner : "--"
                                }
                            </div>
                        </Row>
                            <Row id="codeShareAmount" className="mar-b-2xs">
                            <div className="col-14 text-grey">Codeshare Amount:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.codeShareAmount != null && rowData.codeShareAmount != undefined && rowData.codeShareAmount != 0 ?
                                        rowData.codeShareAmount : "--"
                                }
                            </div>
                        </Row>
                        </div>
                    </div>

                    <div className=" col-6 border-right">
                        <div className="pad-sm">
                            <Row id="origin" className="mar-b-2xs">
                            <div className="col-14 text-grey">Origin:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.origin != null && rowData.origin != undefined && rowData.origin.trim().length > 0 ?
                                        rowData.origin : "--"
                                }
                            </div>
                        </Row>
                            <Row id="scheduledDelivery" className="mar-b-2xs">
                            <div className="col-14 text-grey">Scheduled delivery:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.scheduledDelivery != null && rowData.scheduledDelivery != undefined && rowData.scheduledDelivery.trim().length > 0 ?
                                        rowData.scheduledDelivery : "--"
                                }
                            </div>
                        </Row>
                            <Row id="destination" className="mar-b-2xs">

                            <div className="col-14 text-grey"> Force Majeure Approved:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.forceMajeureStatus !== null && rowData.forceMajeureStatus !== undefined && rowData.forceMajeureStatus.length !== 0 ?
                                        rowData.forceMajeureStatus : "--"
                                }
                            </div>
                        </Row>
                        </div>
                    </div>
                    <div className=" col-6 border-right">
                    <Row id="destination" className="mar-b-2xs">

                            <div className="col-14 text-grey">Destination:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.destination != null && rowData.destination != undefined && rowData.destination != 0 ?
                                        rowData.destination : "--"
                                }
                            </div>
                        </Row>
                        <Row id="regionCode" className="mar-b-2xs">

                            <div className="col-14 text-grey">Region code:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.regionCode != null && rowData.regionCode != undefined && rowData.regionCode != 0 ?
                                        rowData.regionCode : "--"
                                }
                            </div>
                        </Row>
                        </div>
                    <div className=" col-6">
                        <div className="pad-sm">
                            <Row id="mailClass" className="mar-b-2xs">
                            <div className="col-14 text-grey">Mail Class:</div>
                            <div className="col-10 text-black ">
                                {
                                    rowData.mailClass != null && rowData.mailClass != undefined && rowData.mailClass.trim().length > 0 ?
                                        rowData.mailClass : "--"
                                }
                            </div>

                        </Row>
                        </div>
                    </div>
                </Row>
            </div>

        );
    }
}