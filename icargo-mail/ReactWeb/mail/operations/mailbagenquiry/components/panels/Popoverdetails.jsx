import React from 'react'
import { Manager, Target, Popper } from 'react-popper';
import PropTypes from 'prop-types';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import OnwardRouting from './OnwardRouting.jsx';
import { ITextField, ICheckbox,ISelect } from 'icoreact/lib/ico/framework/html/elements';
import {  Col, Row } from 'reactstrap'
import { IPopover, IPopoverBody, IPopoverHeader } from 'icoreact/lib/ico/framework/component/common/popover';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
export default class PopoverDetails extends React.Component {

    constructor(props) {
        super(props);
        // this.togglePanel = this.togglePanel.bind(this);
        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false,
            uldFormatValidation: true
        }
    }
    togglePanel = () => {  
		this.props.showTransfer ? this.props.clearAddContainerPopover('TRANSFER'): this.props.showReassign ?this.props.clearAddContainerPopover('REASSIGN'):this.props.clearAddContainerPopover('CHANGE_FLIGHT');
		 this.setState({ isOpen: !this.state.isOpen })
    }

    saveContainer = () => {
		this.props.showTransfer ? this.props.saveNewContainer('TRANSFER'): this.props.showReassign ?this.props.saveNewContainer('REASSIGN'):this.props.saveNewContainer('CHANGE_FLIGHT');
        this.setState({ isOpen: false })
    }

    closePopover = () => {
        this.setState({ isOpen: false })
    }

    toggle(event) {
        this.setState({
            uldFormatValidation: !event.target.checked
        });
        this.props.pabuiltUpdate(event.target.checked);
    }

    render() {
        const { height, width } = this.props;
        return (
            <Manager>
                <Target >
                    <IButton className="btn btn-default" onClick={this.togglePanel} disabled= {this.props.isValidFlight} id="addContainer">Add</IButton>
                </Target>
                {(this.state.isOpen) ?
                <IPopover placement="top-center" isOpen={this.state.isOpen} target={'addContainer'} toggle={this.closePopover} className="icpopover_40w"> 
                                    <IPopoverHeader>
                                        <h4> Add Container</h4>
                                    </IPopoverHeader>
                                    <IPopoverBody>
                                                <div className="pad-md pad-b-3xs border-bottom">
                                                    <Row>
                                                        <Col xs="auto">
                                                            <div className="form-group mar-t-md">
                                                                <ICheckbox name="barrowFlag" label="Barrow" onChange={this.toggle}/>
                                                            </div>
                                                        </Col>
                                                        <Col xs="6">
                                                            <div className="form-group">
                                                                <IUldNumber mode="edit" name="uldNumber" uppercase={true} type="text" validateFormat={this.state.uldFormatValidation}></IUldNumber>
                                                            </div>
                                                        </Col>
                                                        {this.props.disablePou||this.props.filterType==='C'?
                                                        <Col xs="5">
                                                        <div className="form-group">
                                                            <label className="form-control-label ">POU</label>
                                                           
                                                            <ISelect name="pou"  disabled={true} uppercase={true}  />
                                                            
                                                            
                                                        </div>
                                                        </Col>
                                                    :
                                                        <Col xs="5">
                                                            <div className="form-group">
                                                                <label className="form-control-label ">POU</label>
                                                               
                                                                <ISelect name="pou"  options={this.props.pous} uppercase={true}  />
                                                                
                                                                
                                                            </div>
                                                        </Col>
                                                        }
                                                        <Col xs="5">
                                                            <div className="form-group">
                                                                <label className="form-control-label ">Destination</label>
                                                                {/*<ITextField mode="edit" name="uldDestination" uppercase={true} type="text"></ITextField>*/}
                                                                <Lov name="uldDestination" disabled={this.props.disablePou} uppercase={true}  lovTitle="Destination" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />

                                                            </div>
                                                        </Col>
                                                        <Col xs="auto">
                                                            <div className="form-group mar-t-md">
                                                                <ICheckbox name="paBuilt" label="PA Build" disabled={!this.state.uldFormatValidation} />
                                                            </div>
                                                        </Col>
                                                        <Col xs="24">
                                                            <div className="form-group">
                                                                <label className="form-control-label ">Remarks</label>
                                                                <ITextField mode="edit" name="uldRemarks" uppercase={true} type="text"></ITextField>
                                                            </div>
                                                        </Col>
                                                    </Row>
                                                </div>
                                                {this.props.filterType==='F'? 
                                                <div className="border-bottom pad-sm">
                                                    <h4>Onward Routing</h4>
                                                </div>:null}
                                                {this.props.filterType==='F'? 
                                                <div className="pad-md pad-b-3xs scroll-y" style={{ maxHeight: "100px" }}>
                                                    <OnwardRouting />
                                                </div>:null}
                                                <div className="btn-row">
                                                    <IButton category="primary" onClick={this.saveContainer}>Add</IButton>
                                                    <IButton category="secondary" bType="CLOSE" accesskey="O" onClick={this.closePopover}>Close</IButton>
                                                </div>
                                        </IPopoverBody>
                                </IPopover>: ''}
            </Manager>
        )
    }
}
PopoverDetails.propTypes = {
    height: PropTypes.string,
    width: PropTypes.string
}