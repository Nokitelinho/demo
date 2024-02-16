import React, { Component } from 'react';
import { Row, Col } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';
import { IList, IListItem } from 'icoreact/lib/ico/framework/component/common/list'
import PropTypes from 'prop-types';
import { IMoney } from 'icoreact/lib/ico/framework/component/business/money';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';

class BillingPanel extends Component {
    constructor(props) {
        super(props);


    }

    getIconClass=(data)=> {
        switch (data) {
            case "0-15": return "green m-0"
            case "16-30": return "green m-0"
            case "31-45": return "orange m-0"
            case "46-60": return "orange m-0"
            case "61-90": return "red m-0"
            case "91-120": return "red m-0"
            case ">120": return "red m-0"
        }
    }
    getRowClass=(data)=> {
        switch (data.rowData.name) {
            case "0-15": return "row-1 border-bottom"
            case "16-30": return "row-2 border-bottom"
            case "31-45": return "row-3 border-bottom"
            case "46-60": return "row-4 border-bottom"
            case "61-90": return "row-5 border-bottom"
            case "91-120": return "row-6 border-bottom"
            case ">120": return "row-7"
        }
    }

    getstatusViewTab = (statusViewData) => {
        this.statusViewTab = statusViewData.map((statusViewValues) => {
            return (

                <IListItem >


                    {(statusViewValues.awbType === 'TOTAL' ?
                        <div>
                            <span className="w-40 inline-block"><strong className="fs14">{statusViewValues.awbType}</strong></span>
                            <span className="w-35 inline-block"><strong className="fs14">{statusViewValues.awbCount}</strong></span>
                            <span className="w-25 inline-block"><strong className="fs14 money-reset"><IMoney value={statusViewValues.awbAmount} mode="display" placement="left" /></strong></span> </div> :
                        <div><span className="w-40 inline-block"><strong>{statusViewValues.awbType}</strong></span>
                            <span className="w-35 inline-block">{statusViewValues.awbCount}</span>
                            <span className=" w-25 inline-block money-reset"><IMoney value={statusViewValues.awbAmount} mode="display" placement="left" /></span>
                        </div>)}


                </IListItem>

            );
        });
    }

    getAgeingReceivablesTab = (ageingReceivablesData) => {
        this.ageingReceivablesTab = ageingReceivablesData.map((ageingReceivablesValues) => {
            return (

                <IListItem className="table-row">
                    <div>
                        <span className="w-40 inline-block">
                            <div className="dot-indicators mar-r-2xs">
                                <span className="pl-0"><i className={this.getIconClass(ageingReceivablesValues.name)}></i></span>
                            </div>
                            <strong>{ageingReceivablesValues.name}</strong>
                        </span>
                        <span className="w-35 inline-block">
                            {ageingReceivablesValues.awbCount}
                        </span>
                        <span className=" w-25 inline-block money-reset"><IMoney value={ageingReceivablesValues.value} mode="display" placement="left" />
                        </span>

                    </div>


                </IListItem>

            );
        });
    }


    render() {
        { this.getstatusViewTab(this.props.statusView) };
        { this.getAgeingReceivablesTab(this.props.ageingReceivables) };
        return (
            <Row>
                <Col xs="8">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.statusview"/> </h4>
                            </Col>
                        </div>
                        <div className="card-body p-0">
                            <Row className="animated fadeInDown" >
                                <Col className="d-flex">
                                    {this.props.statusView ?
                                    <IList className="w-100">
                                        <IListItem className="header-blue">
                                            <span className="w-40 inline-block"></span>
                                            <span className="text-grey strong inline-block w-35"><IMessage msgkey="customermanagement.defaults.customerconsole.count"/></span>
                                            <span className="text-grey strong inline-block w-25"><IMessage msgkey="customermanagement.defaults.customerconsole.value"/></span>
                                        </IListItem>
                                        {this.statusViewTab}
                                    </IList> 
                                    :<div><IMessage msgkey="customermanagement.defaults.customerconsole.noresult"/></div>
                                    }
                                </Col>
                            </Row>

                        </div>
                    </div>

                </Col>
                <Col xs="8">
                    <div className="card">
                        <div className="card-header card-header-action border-bottom-0">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.receivables"/></h4>
                            </Col>
                        </div>
                        <div className="card-body p-0">
                            <Row className="animated fadeInDown" >
                                <Col className="d-flex">
                                    <IList className="w-100">
                                        <IListItem className="header-blue">
                                            <span className="w-40 inline-block"></span>
                                            <span className="text-grey strong inline-block w-35"><IMessage msgkey="customermanagement.defaults.customerconsole.count"/></span>
                                            <span className="text-grey strong inline-block w-25"><IMessage msgkey="customermanagement.defaults.customerconsole.value"/></span>
                                        </IListItem>
                                        <IListItem>
                                            <div>
                                                <span className="w-40 inline-block"><strong><IMessage msgkey="customermanagement.defaults.customerconsole.osreceiv"/></strong></span>
                                                <span className="w-35 inline-block">{this.props.receivablesCreditInfo.outstandingReceivableCount}</span>
                                                <span className="w-25 inline-block">
                                            {
                                                this.props.receivablesCreditInfo.outstandingReceivableValue ?
                                                <IMoney value={this.props.receivablesCreditInfo.outstandingReceivableValue} />:0.00
                                            }
                                                </span>
                                            </div>
                                        </IListItem>
                                        <IListItem>
                                            <div>
                                                <span className="w-40 inline-block"><strong><IMessage msgkey="customermanagement.defaults.customerconsole.unbilledreceiv"/> </strong></span>
                                                <span className="w-35 inline-block">{this.props.receivablesCreditInfo.unbilledReceivableCount}</span>
                                                <span className="w-25 inline-block">
                                            {
                                                this.props.receivablesCreditInfo.unbilledReceivableValue ?
                                                <IMoney value={this.props.receivablesCreditInfo.unbilledReceivableValue} />:0.00
                                            }
                                                </span>
                                            </div>
                                        </IListItem>
                                    </IList>
                                </Col>
                            </Row>
                            <div className="d-block">
                                <div className="border-bottom  border-top col">
                                    <h6 className="pad-2xs"><IMessage msgkey="customermanagement.defaults.customerconsole.credinfo"/></h6>
                                </div>
                                <Col className="pad-md">
                                    <Col className="border border-secondary rounded no-gutters pad-2xs bg-light text-dark">
                                        <Row>
                                            <Col className="col-12">
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.customertyp"/></span>
                                                    <label>{this.props.receivablesCreditInfo.customerType}</label>
                                                </div>
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.vatreg"/></span>
                                                    <label>{this.props.receivablesCreditInfo.vatRegNumber}</label>
                                                </div>
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.credlimit"/></span>
                                                    <label>
                                                    {
                                                        this.props.receivablesCreditInfo.creditLimit ?
                                                        <IMoney value={this.props.receivablesCreditInfo.creditLimit} />:0.00
                                                    }
                                                   </label>
                                                </div>
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.avlcredlimit"/></span>
                                                     <label>
                                                    {
                                                       this.props.receivablesCreditInfo.availableCreditLimit ?
                                                        <IMoney value={this.props.receivablesCreditInfo.availableCreditLimit} />:0.00
                                                    }
                                                   </label>
                                                </div>

                                            </Col>
                                            <Col className="col-12">
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.daytoclose"/></span>
                                                    <label>{this.props.receivablesCreditInfo.daysToClose}</label>
                                                </div>
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.restrictfop"/></span>
                                                    <label>{this.props.receivablesCreditInfo.restrictedFOP}</label>
                                                </div>
                                                <div className="d-flex justify-content-between pad-y-3xs">
                                                    <span className="text-grey"><IMessage msgkey="customermanagement.defaults.customerconsole.collectagency"/></span>
                                                    <label>{this.props.receivablesCreditInfo.collectionAgency}</label>
                                                </div>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Col>
                            </div>
                        </div>
                    </div>

                </Col>
                <Col xs="8">
                    <div className="card">
                        <div className="card-header card-header-action">
                            <Col>
                                <h4><IMessage msgkey="customermanagement.defaults.customerconsole.receivablesaging"/></h4>
                            </Col>
                        </div>
                        <div className="card-body p-0">
                            <Row className="animated fadeInDown" >
                                <Col className="d-flex" /*style={{ 'height': '326px' }}*/>

                                    {this.props.ageingReceivables ?
                                        <IList className="w-100">
                                            <IListItem className="header-blue">
                                                <span className="w-40 inline-block"></span>
                                                <span className="text-grey strong inline-block w-35"><IMessage msgkey="customermanagement.defaults.customerconsole.count"/></span>
                                                <span className="text-grey strong inline-block w-25"><IMessage msgkey="customermanagement.defaults.customerconsole.value"/></span>
                                            </IListItem>
                                            <div className="gradient-table">
                                                {this.ageingReceivablesTab}
                                                                </div>
                                        </IList>
                                        : <div><IMessage msgkey="customermanagement.defaults.customerconsole.noresult"/></div>
                                    }
                                </Col>
                            </Row>
                        </div>
                    </div>

                </Col>
            </Row>

        )
    }
}

BillingPanel.propTypes = {
    ageingReceivables: PropTypes.array,
    statusView: PropTypes.array,
    receivablesCreditInfo: PropTypes.object
};
export default BillingPanel;