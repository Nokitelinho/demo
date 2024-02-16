import React, { Component, Fragment } from 'react';
import { Row, Col } from "reactstrap";
import MailbagDetailsTable from './MailbagDetailsTable.jsx';
import ImgPanel from './ImgPanel.jsx';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import {IAccordion, IAccordionItem,IAccordionItemTitle, IAccordionItemBody } from 'icoreact/lib/ico/framework/component/common/accordion';
export default class DetailsPanel extends Component {



    onReset() {
        this.props.onReset();
    }

    onClickHandle = () => {
    }
    render() {


        return (
            (this.props.screenMode === 'initial' || this.props.noData == true) ?
           
                <section>
                   {/*  <Row>
                        <Col> 
                              <NoImgPanel />


                         </Col> 
                    </Row>
                     <Row> 
                        <Col>
                              <NoDataPanel />
                        </Col>
                     </Row>     */}

                </section>
                :
                <Fragment>
                   
            <div className="w-100">
                <IAccordion>
                        <IAccordionItem className="click-fix" expanded>
                            <IAccordionItemTitle>
                                    <div className="accordion-head d-flex align-items-center" onClick={this.onClickHandle}>
                                     <i className="icon ico-orange-top"></i>
                                     <span>Graph Panel</span>
                                     {/* Use below code for the information popover */}
                                     {/* <a className="ml-auto">
                                        <span className="icon ico-warn-sm"></span>
                                     </a> */}
                                     </div>
                            </IAccordionItemTitle>
                            <IAccordionItemBody>
                            <ImgPanel
                                mailMonitorSummary={this.props.mailMonitorSummary}
                                activeGraph={this.props.activeGraph}
                                changeGraph={this.props.changeGraph}
                                activeTab={this.props.activeTab}
                                changeTab={this.props.changeTab}
                            />
                            </IAccordionItemBody>
                        </IAccordionItem>
                </IAccordion>
                </div>
                    <Row className="inner-panel">
                        <Col className="inner-panel">
                            <div className="card mar-t-2sm custom-tab-pane sub-panel">
                                <div className="tabs ui-tabs inner-panel">

                                     <ICustomHeaderHolder tableId='mailbagtable'/> 

                                                <div className="card-body p-0 d-flex" >
                                    
                                    
                                    
                                    
     

                                        <MailbagDetailsTable
                                            mailbagsInTable={this.props.mailbagsdetails}
                                            tableFilter={this.props.currentTableFilter}
                                            initialValues={this.props.currentTableFilter}
                                            saveSelectedMailbagsIndex={this.props.saveSelectedMailbagsIndex}
                                            selectedMailbagIndex={this.props.selectedMailbagIndex}
                                            onApplyTableFilter={this.props.onApplyTableFilter}
                                            onClearTableFilter={this.props.onClearTableFilter}
                                            oneTimeValues={this.props.oneTimeValues}
                                            updateSortVariables={this.props.updateSortVariables}
                                            onChangeTab={this.props.onChangeTab}
                                            exportToExcel={this.props.exportToExcel}
                                            openHistoryPopup={this.props.openHistoryPopup}

                                            activeTab={this.props.activeTab}
                                            changeTab={this.props.changeTab}
                                            currentTableFilter={this.props.currentTableFilter}
                                            activeGraph={this.props.activeGraph}
                                            changeGraph={this.props.changeGraph}
                                            
                                            sort={this.props.sort}
                                            paginatedMailbags = {this.props.paginatedMailbags}
                                            getNewPage={this.props.onlistMailbagDetails} 
                                       
                                        />
                                    </div>
                                  
                                </div>
                            </div>
                        </Col>
                    </Row>

            </Fragment>

        )
    }
}