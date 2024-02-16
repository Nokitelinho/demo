import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import MailbagDetailsTable from './MailbagDetailsTable.jsx';
import AddConsignment from './AddConsignment.jsx';
import AddMailbagTabPanel from './AddMailbagTabPanel.jsx'
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import ExcelMailBagsDetailsTable from './ExcelMailBagsDetailsTable.jsx';
import PrintMailTag from './PrintMailTag.jsx';
import NoDataPanel from './NoDataPanel.jsx';
import { IAccordion, IAccordionItem, IAccordionItemTitle, IAccordionItemBody } from 'icoreact/lib/ico/framework/component/common/accordion';
import 'icoreact/lib/ico/framework/assets/styles/scss/main.scss'

 class DetailsPanel extends Component {
    constructor(props) {
        super(props);
        this.toggleFilter = this.toggleFilter.bind(this);
    }
    toggleFilter() {
        this.props.onToggleFilter((this.props.showAddConsignment === 'show') ? 'dontShow' : 'show');
    }

    onCloseFunction() {
        this.props.onCloseFunction;
    }
    render() {


        return (
            <Fragment>
                <IAccordion>
                    {
                        this.props.fromScreen ==='mail.operations.ux.mailoutbound' ?
                        // default will be collapsed, added as part of BUG IASCB-51477
                        <IAccordionItem>
                            {(this.props.showAddConsignment == 'show') ? 
                            (<IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i> Add Consignment</div></IAccordionItemTitle>):
                            ''}
                            <IAccordionItemBody>
                                {(this.props.showAddConsignment === 'show') ? (
                                    <AddConsignment initialValues={this.props.initialValuesinConsignment} oneTimeType={this.props.oneTimeType} diableMailbagLevel={this.props.diableMailbagLevel}
                                        oneTimeFlightType={this.props.oneTimeFlightType} oneTimeSubType={this.props.oneTimeSubType} typeFlag={this.props.typeFlag}
                                        changeSubTypeFlag={this.props.changeSubTypeFlag} style={this.props.style} changeStyle={this.props.changeStyle} populateFlightNumber={this.props.populateFlightNumber} oneTimeTransportStage ={this.props.oneTimeTransportStage} transportStageQualifierDefaulting={this.props.transportStageQualifierDefaulting} isDomestic={this.props.isDomestic}/>) : ""}
                            </IAccordionItemBody>
                        </IAccordionItem> :
                        <IAccordionItem expanded>
                            {(this.props.showAddConsignment == 'show') ? 
                            (<IAccordionItemTitle><div className="accordion-head"><i className="icon ico-orange-top v-middle"></i> Add Consignment</div></IAccordionItemTitle>):
                            ''}
                            <IAccordionItemBody>
                                {(this.props.showAddConsignment === 'show') ? (
                                    <AddConsignment initialValues={this.props.initialValuesinConsignment} oneTimeType={this.props.oneTimeType} diableMailbagLevel={this.props.diableMailbagLevel}
                                        oneTimeFlightType={this.props.oneTimeFlightType} oneTimeSubType={this.props.oneTimeSubType} typeFlag={this.props.typeFlag}
                                        changeSubTypeFlag={this.props.changeSubTypeFlag} style={this.props.style} changeStyle={this.props.changeStyle} populateFlightNumber={this.props.populateFlightNumber} oneTimeTransportStage ={this.props.oneTimeTransportStage} transportStageQualifierDefaulting={this.props.transportStageQualifierDefaulting} isDomestic={this.props.isDomestic}/>) : ""}
                            </IAccordionItemBody>
                        </IAccordionItem>
                    }
                    
                </IAccordion>

                {this.props.screenMode === 'initial' ?
                    <div className="card mar-t-md">
                        <NoDataPanel activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} />
                    </div> :
                    <div className="card mar-t-md consignment-enquiry-list">
                        <AddMailbagTabPanel activeMailbagAddTab={this.props.activeMailbagAddTab} changeAddMailbagTab={this.props.changeAddMailbagTab} />
                        {
                            this.props.activeMailbagAddTab === 'initial' &&
                            <ExcelMailBagsDetailsTable excelmailBagsList={this.props.excelMailbags} />
                        }
                        {
                            this.props.activeMailbagAddTab === 'EXCEL_VIEW' &&
                            <ExcelMailBagsDetailsTable excelmailBagsList={this.props.excelMailbags} />
                        }
                        {
                            this.props.activeMailbagAddTab === 'NORMAL_VIEW' &&
                            <MailbagDetailsTable mailbagDetails={this.props.dataList} addRow={this.props.addRow} deleteRow={this.props.deleteRow} resetRow={this.props.resetRow} getNewPage={this.props.onlistMaintainConsignment} oneTimeCat={this.props.oneTimeCat}
                                oneTimeMailClass={this.props.oneTimeMailClass} mailBagsList={this.props.mailBagsPage} exportToExcel={this.props.exportToExcel} oneTimeRSN={this.props.oneTimeRSN} oneTimeHNI={this.props.oneTimeHNI} oneTimeMailServiceLevel={this.props.oneTimeMailServiceLevel}
                                selectMailBags={this.props.selectMailBags} newRowData={this.props.newRowData} lastRowData={this.props.lastRowData}
                                getLastRowData={this.props.getLastRowData} showAddMultiplePanel={this.props.showAddMultiplePanel} showAddMultipleFlag={this.props.showAddMultipleFlag}
                                receptacles={this.props.receptacles} calculateReseptacles={this.props.calculateReseptacles} newRSN={this.props.newRSN} onClose={this.props.onClose}
                                getAddMultipleData={this.props.getAddMultipleData} addMultipleData={this.props.addMultipleData} rsnData={this.props.rsnData} addMultiple={this.props.addMultiple}
                                populateMailbagId={this.props.populateMailbagId} storeFormToReducer={this.props.storeFormToReducer} isDomestic={this.props.isDomestic} addRowDomestic={this.props.addRowDomestic}
                                getFormData={this.props.getFormData} saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex} selectedMailbagIndex={this.props.selectedMailbagIndex}
                                mailBags={this.props.mailBags} getTotalCount={this.props.getTotalCount} count={this.props.count} newRowDataDomestic={this.props.newRowDataDomestic} populateMailbagIdTextField={this.props.populateMailbagIdTextField}
                                saveSelectedMultipleMailbagIndex={this.props.saveSelectedMultipleMailbagIndex}/>
                        } 
                        <PrintMailTag show={this.props.showPrintMailTagFlag} onClose={this.props.onClose} mailBagsSelected={this.props.mailBagsSelected} printMailTag={this.props.printMailTag}
                        initialValues={this.props.initialValuesinConsignment} />
                    </div>
                }
            </Fragment>
        );
    }
}
export default wrapForm('mailBagsTable')(DetailsPanel);
