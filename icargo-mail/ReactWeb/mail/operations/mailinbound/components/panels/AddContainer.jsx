import React from 'react';
import { Row, Col, Label } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, ISelect, IButton, ICheckbox  } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import PropTypes from 'prop-types'
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { IUldNumber }
    from 'icoreact/lib/ico/framework/component/business/uldnumber';
class AddContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
        
            uldFormatValidation: true
        };
    }
    onSaveContainer=(values)=>{
        this.props.onSaveContainer(values);
    }
    displayError=()=>{
        const containerNo=this.props.addContainerForm.containerNo;
        const barrowCheck=this.props.addContainerForm.barrow;
        if(!barrowCheck){
            if(containerNo.length>10){
                this.props.displayError('Please enter a valid container number','');
            }
        }
        
    }

    disableFields =(data)=>{ 
       /* this.setState (() => {  
            return{
                isDisabled:data.target.checked  
            }
        }  
      )*/
      this.props.populateULD(data.target.checked);   
    }   

toggle=(event)=> {
        this.setState({
            uldFormatValidation: !event.target.checked
        });
    }
    render() {
        
        return (
            <div>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="auto">
                                <div className="form-check mar-t-md">
                                    <ICheckbox className="form-check-input" name="barrow" label="Barrow" onClick={()=>this.disableFields(event)} />
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                                <IUldNumber name="containerNo" uppercase={true}  onBlur={() => this.displayError()} validateFormat={this.state.uldFormatValidation} disabled={this.props.isDisabled}/>
                                </div>
                            </Col>
                            <Col xs="6">
                                <div className="form-group">
                                <Label className="form-control-label">
                                 <IMessage msgkey="mailtracking.defaults.arrivemails.tooltip.pol"/></Label>
                                        <ISelect name='pol' options={this.props.pol} />
                                </div>
                                    </Col>
                            <Col xs="6">
                                <div className="form-group">
                                <Label className="form-control-label">
                                 <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.destination"/></Label>
                                    <ISelect name='desination' disabled={this.props.isDisabled} options={this.props.dest} />
                                </div>
                                    </Col>
                                </Row>
                                <Row>
                            <Col xs="auto">
                                <div className="form-check mar-b-xs">
                                    <ICheckbox className="form-check-input" name="paBuilt" label="PA Built" disabled={this.props.isDisabled}/>
                                </div>
                            </Col>
                            <Col xs="auto">
                                <div className="form-check">
                                    <ICheckbox className="form-check-input" name="rcvd" label="Rcvd" />
                                </div>
                            </Col>
                            <Col xs="auto">
                                <div className="form-check">
                                    <ICheckbox className="form-check-input" name="dlvd" label="Dlvd" />
                                </div>
                            </Col>
                            <Col xs="auto">
                                <div className="form-check">
                                    <ICheckbox className="form-check-input" name="intact" label="Intact" />
                                </div>
                            </Col>
                                </Row>
                                <Row>
                            <Col>
                            <Label className="form-control-label">
                             <IMessage msgkey="mailtracking.defaults.mailarrival.lbl.remarks"/></Label>
                                        <ITextField  name="remarks" uppercase={true} />
                                    </Col>
                                </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.onSaveContainer}>Ok</IButton>{' '}
                    <IButton category="default" bType="CLEAR" accesskey="C" onClick={this.props.onClearContainer}>Clear</IButton>
                    <IButton category="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>  
            </div>
        )
    }
}

AddContainer.propTypes={
    toggleFn: PropTypes.func,
    onSaveContainer: PropTypes.func,
    addContainerForm:PropTypes.object,
    displayError:PropTypes.func,
    pol:PropTypes.array,
    dest:PropTypes.array,
    onClearContainer:PropTypes.func,
}
export default icpopup(wrapForm('addcontainerForm')(AddContainer), { title: 'Add Container' })
