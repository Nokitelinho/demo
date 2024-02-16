import React, { Component } from 'react';
import { Row, Col, Container } from "reactstrap";
import InvoicDetailsTable from './InvoicDetailsTable.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import ExtentedFilterNoActionPanel from './ExtentedFilterNoActionPanel.jsx';
import ExtentedFilterPanel from './ExtentedFilterPanel.jsx';
import {ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
export default class DetailsPanel extends Component {


    constructor(props) {
        super(props);
         this.onReset = this.onReset.bind(this);
    }
    onReset() {
        //const extendedfilterform = this.props.extendedfilterform;
        this.props.onReset();
    }

    render() {


        return (
            (this.props.screenMode === 'initial' || this.props.noData == true ) ?
                //   <div class="container-pane m-t-20 animated fadeInUp">
                <section className="mar-y-md animated fadeInDown section-panel">
                    <Row className="flex-grow-1">
                        <Col sm="5" xs="5" lg="5" className="inner-pannel">
                    <div className="card">
                                <div className="card-header justify-content-end border-bottom-0" >
                                    <h4 className="col-12">Filter</h4>
                                    <div className="col-12 d-inline-block text-right  align-items-center">
                                    <a href="#" onClick={this.onReset}>
                                    <i className="icon ico-close-fade v-top" ></i> Reset</a>
                                    </div>
                                </div>
                                <div className="card-body border-top">
                                <ExtentedFilterPanel displayDestination={this.props.displayDestination}
                                originInputChange={this.props.originInputChange}
                                displayOrigin={this.props.displayOrigin}
                                displayError={this.props.displayError}
                                invoicFilterForm={this.props.invoicFilterForm}
                                origin={this.props.origin}
                                ondoneGroupRemarks={this.props.ondoneGroupRemarks}
                                onbucketSort={this.props.onbucketSort}
                                oneTimeValues={this.props.oneTimeValues}
                                mailSubClassCodes={this.props.mailSubClassCodes}
                                mailbags={this.props.mailbags}
                                currencyCode={this.props.currencyCode}/>
                                </div>
                            </div>
                        </Col>
                        <Col sm="19" xs="19" lg="19" className="inner-row-pannel">
                            <div className="card">
                                 <div className="card-header d-flex justify-content-end">
                            <h4 className="col">Mailbags</h4>
                        </div>
                                 <div className="card-body p-0 billing-table table-responsive">

                            <NoDataPanel/>

                    </div>

                            </div>
                        </Col>
                    </Row>

                </section>
                :

                <section className="mar-y-md section-panel">
                    <Row className="flex-grow-1">
                        <Col sm="5" xs="5" lg="5" className="inner-pannel">

                            <div className="card" >
                                <div className="card-header justify-content-end border-bottom-0" >
                                    <h4 className="col-12">Filter</h4>
                                    <div className="col-12 d-inline-block text-right  align-items-center">
                                    <a href="#" onClick={this.onReset}>
                                    <i className="icon ico-close-fade v-top"></i> Reset</a>
                                    </div>
                                </div>
                                <div className="card-body border-top">

                                    <ExtentedFilterPanel displayDestination={this.props.displayDestination}
                                        originInputChange={this.props.originInputChange}
                                        displayOrigin={this.props.displayOrigin}
                                        clmlessfiv={this.props.clmlessfiv}
                                        clmfivtoten={this.props.clmfivtoten}
                                        clmtentotwentyfiv={this.props.clmtentotwentyfiv}
                                        clmtwentyfivtofifty={this.props.clmtwentyfivtofifty}
                                        clmfiftytohundred={this.props.clmfiftytohundred}
                                        clmhundredtofivhundred={this.props.clmhundredtofivhundred}
                                        clmgrtfivhundred={this.props.clmgrtfivhundred}
                                        displayError={this.props.displayError}
                                        invoicFilterForm={this.props.invoicFilterForm}
                                        origin={this.props.origin}
                                        ondoneGroupRemarks={this.props.ondoneGroupRemarks}
                                        onbucketSort={this.props.onbucketSort}
                                        oneTimeValues={this.props.oneTimeValues}
                                        mailSubClassCodes={this.props.mailSubClassCodes}
                                        mailbags={this.props.mailbags}
                                        currencyCode={this.props.currencyCode}
                                        cntawtinc={this.props.cntawtinc}
                                        cntovrnotmra={this.props.cntovrnotmra}
                                        clmzropay={this.props.clmzropay}
                                        clmnoinc={this.props.clmnoinc}
                                        clmratdif={this.props.clmratdif}
                                        clmwgtdif={this.props.clmwgtdif}
                                        clmmisscn={this.props.clmmisscn}
                                        clmlatdlv={this.props.clmlatdlv}
                                        clmsrvrsp={this.props.clmsrvrsp}
                                        latdlv={this.props.latdlv}
                                        dlvscnwrg={this.props.dlvscnwrg}
                                        misorgscn={this.props.misorgscn}
                                        misdstscn={this.props.misdstscn}
                                        fulpaid={this.props.fulpaid}
                                        ovrratdif={this.props.ovrratdif}
                                        ovrwgtdif={this.props.ovrwgtdif}
                                        ovrclsdif={this.props.ovrclsdif}
                                        ovrsrvrsp={this.props.ovrsrvrsp}
                                        ovroth={this.props.ovroth}
                                        clmoth={this.props.clmoth}
                                        clmnotinv={this.props.clmnotinv}
                                        ovrpayacp={this.props.ovrpayacp}
                                        ovrpayrej={this.props.ovrpayrej}
                                        clmfrcmjr={this.props.clmfrcmjr}
                                        dummyorg={this.props.dummyorg}
                                        dummydst={this.props.dummydst}
                                        shrpayacp={this.props.shrpayacp}
                                        clmstagen={this.props.clmstagen}
                                        clmstasub={this.props.clmstasub}
                                        amotobeact={this.props.amotobeact}
                                        amotact={this.props.amotact}
                                    />
                                </div>
                            </div>
                        </Col>
                        <Col sm="19" xs="19" lg="19" className="inner-row-pannel">
                    <div className="card">
                            <ICustomHeaderHolder tableId='mailbagtable'/>
                        <div className="d-flex flex-column flex-grow-2">
                                <InvoicDetailsTable moveToActionIndividual={this.props.moveToActionIndividual}
                                moveToAction={this.props.moveToAction}
                                mailbags={this.props.mailbags}
                                invoicFilterform={this.props.invoicFilterForm}
                                 getNewPage={this.props.onlistInvoicDetails}
                                 tableFilter={this.props.tableFilter}
                                 initialValues={this.props.initialValues}
                                 invoicMultipleSelectionAction={this.props.invoicMultipleSelectionAction}
                                 saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex}
                                 selectedMailbagIndex={this.props.selectedMailbagIndex}
                                 onOKRemarks={this.props.onOKRemarks}
                                 claimHistory={this.props.claimHistory}
                                 filterValues={this.props.filterValues}
                                 disableAcceptButton={this.props.disableAcceptButton}
                                 enableAcceptButton={this.props.enableAcceptButton}
                                 />
                        </div>

                    </div>
                        </Col>
                    </Row>

                </section>




        )
    }
}