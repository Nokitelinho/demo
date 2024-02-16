import React, { Fragment } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { IHandsontable,ToUppercaseEditor } from 'icoreact/lib/ico/framework/component/common/handsontable'
import PropTypes from 'prop-types';
import {isSubGroupEnabled} from 'icoreact/lib/ico/framework/component/common/orchestration';
import { Col } from 'reactstrap';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
import ModifyOriginAndDestinationTable from './ModifyOriginAndDestinationTable.jsx';
class ModifyOriginAndDestination extends React.PureComponent {
    constructor(props) {
        super(props);
        this.rowindex = '';
    }
    onSaveOrgAndDest = () => {
        this.props.onSaveOrgAndDest(this.props.newMailbagsTable);
    } 
    onCloseModifyPopup=()=>{
        this.props.onCloseModifyPopup();
    }
   
    render() {
        return (
            <Fragment>
                <PopupBody>
                        <div className="card border-0 test">
                            <Fragment>
                                <div className="card-header card-header-action">
                                    <Col><h4>Mailbag Details</h4></Col>
                                        <Fragment>
                                            <Col xs="auto">
                                                <ICustomHeaderHolder tableId='addmailbagstable' />
                                            </Col>
                                        </Fragment>
                                            <Col xs="auto">
                                                <div className="tab tab-icon">
                                                    <IButtonbar>
                                                        <IButtonbarItem
                                                          disabled={true}>
                                                            <div className="btnbar" data-tab="EXCEL_VIEW"  ><i className="icon ico-list-blue align-middle"/></div>
                                                        </IButtonbarItem>
                                                        <IButtonbarItem>
                                                            <div className="btnbar" data-tab="NORMAL_VIEW" ><i className="icon ico-list-item align-middle" /></div>
                                                        </IButtonbarItem>
                                                    </IButtonbar>
                                                </div>
                                            </Col>
                                </div>
                            </Fragment>            
                                <ModifyOriginAndDestinationTable 
                                selectedMailbags={this.props.selectedMailbags} 
                                checkValidOrgAndDest={this.props.checkValidOrgAndDest} 
                                newMailbagsTable={this.props.newMailbagsTable}
                                dummyAirportForDomMail={this.props.dummyAirportForDomMail} />   
                         </div>
                </PopupBody>
                <PopupFooter>            
                    <IButton category="primary" onClick={this.onSaveOrgAndDest}>Save</IButton>
                    <IButton category="default" onClick={this.onCloseModifyPopup}>Close</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

ModifyOriginAndDestination.propTypes = {
    onSaveOrgAndDest:PropTypes.func,
}

export default icpopup(wrapForm('newMailbagsTable')(ModifyOriginAndDestination), { title: 'Add Mail bag', className: 'modal_990px' })
