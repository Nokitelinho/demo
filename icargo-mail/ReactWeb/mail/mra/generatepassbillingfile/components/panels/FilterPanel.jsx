import React, { PureComponent, Fragment } from 'react';
import { Field } from 'redux-form';
import { Row, Col, FormGroup } from "reactstrap";
import { ITextField, IMessage, IButton,ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { SCREEN_MODE } from '../../constants';
import PropTypes from 'prop-types';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { commonCompValidations } from 'icoreact/lib/ico/framework/component/common/event/compvalidations';

class FilterPanel extends PureComponent {

    constructor(props) {
        super(props);
    }
    changeScreenMode = (event) => {
        this.props.onChangeScreenMode((this.props.screenMode === SCREEN_MODE.EDIT) ?
            SCREEN_MODE.DISPLAY : SCREEN_MODE.EDIT);
    }

    submitFilter = () => {
        this.props.submitFilter();
    }
    clearFilter = () => {
        this.props.reset();
        this.props.onClearFilter();
    }

    populatePeriodNumberAndDate=(event,populateSource)=>{
        event.preventDefault();
        this.props.populatePeriodNumberAndDate(populateSource);
    }


    render() {
         return (<Fragment>
                    
                        <div className="header-filter-panel flippane">
                            <div className="pad-md pad-b-3xs">
                                <Row>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label">Country </label>
                                            <Lov name="country" id = "country" lovTitle="Country"  dialogWidth="600" dialogHeight="530" actionUrl="ux.showCountry.do?formCount=1"  uppercase={true}/>                                       
                                            <IToolTip value={'Country'} target={'country'} placement='bottom'/>
                                             </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group">
                                            <label className="form-control-label">GPA Code</label>
									        <Lov name="gpacode" id = "gpacode" lovTitle="GPA Code" dialogWidth="600" dialogHeight="530" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1"  uppercase={true} />                                        
                                            <IToolTip value={'GPA Code'} target={'gpacode'} placement='bottom'/>
                                            </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Period Number</label>
                                            <ITextField  name="periodnumber"  id="periodnumber" type="text" uppercase={true} onBlur ={(event)=>this.populatePeriodNumberAndDate(event,'PRDNUM')} onKeyPress={commonCompValidations.restrictInt}></ITextField>                                        
                                            <IToolTip value={'Period Number'} target={'periodnumber'} placement='bottom'/>
                                            </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Billing Period From</label>
                                            <DatePicker   name="fromDate" id="fromDate" onFlightDateBlur ={(event)=>this.populatePeriodNumberAndDate(event,'BLGPRD')} />          
                                            <IToolTip value={'From Date'} target={'fromDate'} placement='bottom'/>                             
                                             </div>
                                    </Col>
                                    <Col xs="4">
                                        <div className="form-group mandatory">
                                            <label className="form-control-label mandatory_label">Billing Period To</label>
                                            <DatePicker  name="toDate" id="toDate" onFlightDateBlur ={(event)=>this.populatePeriodNumberAndDate(event,'BLGPRD')} />
                                            <IToolTip value={'To Date'} target={'toDate'} placement='bottom'/>
                                        </div>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col xs="8">
                                    <span className="d-flex mb-2 font-bold">Re-Generate</span>
                                        <div className="border d-flex flex-column mb-3 p-3 rounded">
                                        <Row>
                                        <Col xs="14">
                                        <div className="form-group">
                                            <label className="form-control-label">File Name</label>
									        <Lov name="filename" id = "filename" lovTitle="File Name" dialogWidth="600" dialogHeight="425" actionUrl="mail.mra.gpabilling.ux.filenamelov.list.do?formCount=1"  />                                        
                                            <IToolTip value={'File Name'} target={'filename'} placement='bottom'/>
                                        </div>
                                        </Col>
                                        <Col>
                                        <div class="form-check mar-t-md">
                                            <ICheckbox name="includenewinvoice" className="form-check-input" id ="includenewinvoice" />
                                            <label className="form-check-label">Include New Invoice </label>
                                            <IToolTip value={'Include New Invoice'} target={'includenewinvoice'} placement='bottom'/>
                                        </div>
                                        </Col>
                                        </Row>
                                        </div>
                                    </Col> 
                                </Row>
                        </div>
                    </div>
                </Fragment>)
    }

}

export default wrapForm('filterPanelForm')(FilterPanel);

FilterPanel.propTypes = {
    onChangeScreenMode: PropTypes.func,
    submitFilter: PropTypes.func,
    clearFilter: PropTypes.func,
    screenMode: PropTypes.string,

}
