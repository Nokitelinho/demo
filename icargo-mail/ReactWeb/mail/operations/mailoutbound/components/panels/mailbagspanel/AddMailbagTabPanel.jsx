import React, { Component, Fragment } from 'react';
import { Col } from 'reactstrap';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import AddMailbagHeader from './AddMailbagHeader.jsx';
import PropTypes from 'prop-types';
export default class AddMailbagTabPanel extends Component {
    constructor(props) {
        super(props);
 
    }
    changeTab = (currentTab) => {
        this.props.changeAddMailbagTab(currentTab)
    }
    addRow = () => {
        this.props.addRow(this.props.newMailbags,this.props.currentDate,this.props.currentTime,this.props.defaultWeightUnit,this.props.previousRowWeight)
    }
    onDeleteRow = () => {
       this.props.onDeleteRow();
    }
    
    onImportMailbags=()=>{
        this.props.onClickImportPopup();
    }
   

    render() {
        let active = 0;
        if (this.props.activeMailbagAddTab === 'EXCEL_VIEW') {
            active = 0;
        } else {
            active = 1;
        }
        return (
            <Fragment>
                <div className="card-header card-header-action">
                    <Col><h4>Mailbag Details</h4></Col>
                    {(this.props.activeMailbagAddTab === 'NORMAL_VIEW') &&
                        <Fragment>
                            <Col xs="auto">
                                <ICustomHeaderHolder tableId='addmailbagstable' />
                            </Col>
                            {(this.props.addModifyFlag === 'ADD_MAILBAG') &&
                             <Col xs="auto">
                                 {/* add/delete button will be disabled if mail bag popup is reassigned. added as part of IASCB-39444 */}
                                <IButton category="primary" onClick={this.addRow} disabled={ this.props.existingMailbagPresent===true }>Add</IButton>
                                <IButton category="primary" onClick={this.onDeleteRow} disabled={ this.props.existingMailbagPresent===true }>Delete</IButton>
                                <IButton category="default" onClick={this.onImportMailbags}>Import</IButton>
                            </Col>}
                            {(!this.props.addModifyFlag) &&
                            <Col xs="auto">
                                <IButton category="primary" onClick={this.addRow} disabled={ this.props.existingMailbagPresent===true }>Add</IButton>
                                <IButton category="primary" onClick={this.onDeleteRow} disabled={ this.props.existingMailbagPresent===true }>Delete</IButton>
                                <IButton category="default" onClick={this.onImportMailbags}>Import</IButton>
                            </Col>}
                        </Fragment>
                    }
                    <Col xs="auto">
                        <div className="tab tab-icon">
                        {(!this.props.existingMailbagPresent) &&
                            <IButtonbar active={active}>
                                <IButtonbarItem
                                 disabled={this.props.disableForModify?true:false}>
                                    <div className="btnbar" data-tab="EXCEL_VIEW" onClick={() => this.changeTab('EXCEL_VIEW')} ><i className="icon ico-list-blue align-middle"/></div>
                                </IButtonbarItem>
                                <IButtonbarItem>
                                    <div className="btnbar" data-tab="NORMAL_VIEW" onClick={() => this.changeTab('NORMAL_VIEW')}><i className="icon ico-list-item align-middle" /></div>
                                </IButtonbarItem>
                            </IButtonbar>
                        }
                        </div>
                    </Col>
                </div>
              <AddMailbagHeader 
              show={this.props.showImportPopup} 
              onCloseImport={this.props.onCloseImport} 
              onImportMailbags={this.props.onImportMailbags} 
              initialValues={{containerJnyID: this.props.containerJnyID}}/>
              </Fragment>

        )
    }
}
AddMailbagTabPanel.propTypes = {
    showImportPopup:PropTypes.bool,
    containerJnyID:PropTypes.string,
    onCloseImport:PropTypes.func,
    onImportMailbags:PropTypes.func,
    addModifyFlag:PropTypes.string,
    activeMailbagAddTab:PropTypes.string,
    onClickImportPopup:PropTypes.func,
    onDeleteRow:PropTypes.func,
    addRow:PropTypes.func,
    newMailbags:PropTypes.array,
    currentDate:PropTypes.string,
    currentTime:PropTypes.string,
    defaultWeightUnit:PropTypes.object,
    previousRowWeight:PropTypes.object,
    changeAddMailbagTab:PropTypes.func
}