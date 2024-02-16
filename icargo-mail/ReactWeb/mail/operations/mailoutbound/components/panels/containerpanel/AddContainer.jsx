import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Col, Row, Input ,Label} from 'reactstrap'
import { ITextField, ISelect, IButton, ICheckbox } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { IUldNumber }
    from 'icoreact/lib/ico/framework/component/business/uldnumber';
    import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
class AddContainer extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {

            uldFormatValidation: true,
            destinationChanged:false
        };

    }
    onclickPABuilt=(event)=> {
        this.props.onclickPABuilt(event);
    }
    onSaveContainer = () => {
       if(this.props.containerActionMode === 'MODIFY'
          &&this.props.initialValues
           &&this.props.initialValues.type&&
            this.props.initialValues.type==='B'
            &&!this.state.warningForULDConversion
            &&this.state.destinationChanged){
            this.setState({
                warningForULDConversion: true
            });
        } 
        this.props.onSaveContainer(this.state.warningForULDConversion);
    }
    onListContainer = () => {
        this.props.onListToModifyContainer();
    }

    toggle=(event)=> {
        this.setState({
            uldFormatValidation: !event.target.checked
        });
        if(this.props.uldToBarrowAllow==='Y'&&this.props.containerActionMode === 'MODIFY'){
            this.setState({
                warningForULDConversion: true
            });
        }
        else{
            this.setState({
                warningForULDConversion: false
            });
        }
        if(this.props.containerActionMode === 'MODIFY'){
            this.props.populateDestForBULKContainer(this.props.initialValues.pou,event.target.checked);
        }

        this.props.barrowCheck(event.target.checked);
    }
    populateDestForBarrow = (event) => {
       this.props.populateDestForBarrow(event);
    }
     onDestinationChange=(event)=>{
        this.setState({
            destinationChanged:true
        });
    } 
    
    render() {
        let pou = []
        pou = this.props.mailAcceptance.pouList ? this.props.mailAcceptance.pouList : null;
        if (pou != null) {
            pou = pou.map((value) => ({ value: value, label: value }));
        }

        // var disabled = 'disabled';
        // if (this.props.PABuiltChecked) {
            // disabled = '';
        // }
        let wareHouses = [];
        wareHouses = this.props.wareHouses ? this.props.wareHouses : null;
        if (wareHouses != null) {
            wareHouses = this.props.wareHouses.map((value) => ({ value: value.warehouseCode, label: value.warehouseCode }));

        }
        // if (!isEmpty(this.props.oneTimeValues)) {

        // category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        //  status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        //  }
        let isUldFormatBulk=false;
        if(this.props.initialValues.type==='B'&&/[A-Z]{1,3}[0-9]{1,5}[A-Z]{2}/.test(this.props.initialValues.containerNumber)){
           isUldFormatBulk=true;

        }
        else if(this.props.initialValues.type==='U'){
            isUldFormatBulk=true;
        }
        if(this.props.initialValues.paBuiltFlag==='Y'){
            isUldFormatBulk=false;
        }
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="auto">
                                <div className="mar-t-md">
                                    <ICheckbox name="barrowFlag" disabled={this.props.containerActionMode === 'MODIFY' &&(!isUldFormatBulk||this.props.uldToBarrowAllow==='N')} label="Barrow" componentId='CMP_Operations_FltHandling_ExportManifest_Barrow' onChange={this.toggle} />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <IUldNumber name="containerNumber" disabled={this.props.containerActionMode === 'MODIFY'} validateFormat={this.state.uldFormatValidation} />
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.pou" />
                                    </Label>
                                    <ISelect name='pou' options={pou} onOptionChange= {this.populateDestForBarrow} disabled={(this.props.mailAcceptance.flightpk == null||
                                        this.props.containerActionMode === 'MODIFY')? true: false}/>
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.dest" />
                                    </Label>
                                    <Lov name="destination"  actionUrl="ux.showAirport.do?formCount=1" uppercase={true} onChange={this.onDestinationChange}/>
                                </div>
                            </Col>
                            <Col xs="auto">
                                <div className="form-group mar-t-md">
                                    <ICheckbox type="checkbox" className="form-check-input" label="PA Built" name="paBuiltFlagValue" onClick={this.onclickPABuilt} disabled={this.props.isBarrow}/>
                                    
                                </div>
                            </Col>
                            <Col xs="4">
                                <div className="form-group">
                                    <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.pacode" />
                                    </Label>
                                    <Lov name="paCode" disabled={!this.props.PABuiltChecked} lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" uppercase={true} componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" />
                                </div>
                            </Col>
                            <Col>
                                <div className="mar-t-md">
                                    <IButton category="primary" bType="LIST" accesskey="L" disabled={this.props.containerActionMode === 'MODIFY'|| this.props.enableContainerSave} onClick={this.onListContainer}>List</IButton>{' '}
                                </div>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs="4">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.warehouse" />
                                </Label>
                                <ISelect name="wareHouse" options={wareHouses} />
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.location" />
                                </Label>
                                <ITextField className="form-control" name="location" uppercase={true}/>
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.transfercarrier" />
                                </Label>
                                <Lov name="transferFromCarrier" lovTitle="TransferCarrier" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.onwardflights" />
                                </Label>
                                <ITextField className="form-control" name="onwardFlights" uppercase={true}/>
                            </Col>
                            <Col xs="8">
                                <Label className="form-control-label">
                                    <IMessage msgkey="mail.operations.mailoutbound.remarks" />
                                </Label>
                                <ITextField className="form-control" name="remarks" />
                            </Col>
                        </Row>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" bType="SAVE" accesskey="S" onClick={this.onSaveContainer} disabled={!this.props.enableContainerSave}>Save</IButton>{' '}
                    <IButton color="default" bType="CANCEL" accesskey="N" onClick={this.props.toggleFn}>Cancel</IButton>
                </PopupFooter>
            </Fragment>
        )
    }
}

AddContainer.propTypes = {
    onSaveContainer: PropTypes.func,
    onclickPABuilt: PropTypes.func,
    onListToModifyContainer: PropTypes.func,
    populateDestForBarrow: PropTypes.func,
    mailAcceptance: PropTypes.object,
    PABuiltChecked: PropTypes.bool,
    wareHouses: PropTypes.array,
    containerActionMode: PropTypes.string,
    enableContainerSave: PropTypes.bool,
    toggleFn: PropTypes.func,
}
// export default icpopup(wrapForm('addcontainerForm')(AddContainer), { title: 'Add Container', maxWidth: '1200px !important', width: '1200px !important' })
export default icpopup(wrapForm('addcontainerForm')(AddContainer), { title: 'Add Container', className: 'modal_800px' })
