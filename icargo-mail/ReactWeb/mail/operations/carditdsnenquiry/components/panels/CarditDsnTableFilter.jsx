import React from 'react';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { Row, Col, Container } from "reactstrap";
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IUldNumber } from 'icoreact/lib/ico/framework/component/business/uldnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber'
import { Forms, Key, Constants, Urls, ComponentId } from '../../constants/constants.js'
import { IAwbNumber } from 'icoreact/lib/ico/framework/component/business/awb';


class CarditDsnTableFilter extends React.PureComponent {
    constructor(props) {
        super(props); 
        this.state = {
            category: [],
            status: [],
            flightType: [],
        }
    }

    componentDidMount() {
        this.initializeOneTime();
    }

    initializeOneTime = () => {

        let category = [];
        let status = [];
        let flightType = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            if (!isEmpty(this.props.oneTimeValues[Key.MAIL_STATUS]))
                status = this.props.oneTimeValues[Key.MAIL_STATUS].filter((value) => { if (value.fieldValue === Constants.ACP || value.fieldValue === Constants.CAP) return value });
            if (!isEmpty(this.props.oneTimeValues[Key.MAIL_CATEGORY]))
                category = this.props.oneTimeValues[Key.MAIL_CATEGORY].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            if (!isEmpty(this.props.oneTimeValues[Key.FLIGHT_TYPE]))
                flightType = this.props.oneTimeValues[Key.FLIGHT_TYPE].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            if (!isEmpty(status))
                status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
            this.setState({ status: status, category: category, flightType: flightType })
        }
    }

    render() {

        return (
            <div>
                <Row>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.DSN_LBL} /></label>
                            <ITextField mode="edit" name="dsn" type="text" componentId={ComponentId.DSN_TEXT}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.OOE_LBL} /></label>
                            <Lov name="originExchangeOffice" lovTitle="Origin OE" dialogWidth="600" dialogHeight="425"
                                actionUrl={Urls.OOELOV}
                                componentId={ComponentId.OOE_TEXT} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.DOE_LBL} /></label>
                            <Lov name="destinationExchangeOffice" lovTitle="Destination OE" dialogWidth="600" dialogHeight="425"
                                actionUrl={Urls.OOELOV} componentId={ComponentId.DOE_TEXT} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.CATEGORY_LBL} /></label>
                            <ISelect name="mailCategoryCode" options={this.state.category}
                                componentId={ComponentId.CATEGORY_SELECT} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.SUBCLASS_LBL} /></label>
                            <Lov name="mailSubclass" lovTitle="Subclass" dialogWidth="600" dialogHeight="425"
                                actionUrl={Urls.SUBCLASSLOV} componentId={ComponentId.SUBCLASS_TEXT} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.YEAR_LBL} /></label>
                            <ITextField mode="edit" name="year" type="text" componentId={ComponentId.YEAR_TEXT}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.CONSIGNMENT_NUMBER_LBL} /></label>
                            <ITextField mode="edit" name="csgDocNum" type="text" componentId={ComponentId.CONSIGNMENT_NO_TEXT}></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.PACODE_LBL} /></label>
                            <Lov name="paCode" lovTitle="PA Code" dialogWidth="600" dialogHeight="425" actionUrl={Urls.PACODELOV}
                                componentId={ComponentId.PACODE_TEXT} />
                        </div>
                    </Col>
                    <Col xs="12">
                        <IFlightNumber mode="edit" ></IFlightNumber>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label" >{Constants.WEIGHT}</label>
                            <ITextField mode="edit" name="weight" type="text" ></ITextField>
                        </div>
                    </Col>
                    <Col xs="6">
                        <IUldNumber name="containerNumber" defaultLabel="Container" mode="edit" />
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label class="form-control-label "><IMessage msgkey={Key.MAILSTATUS_LBL} /></label>
                            <ISelect defaultOption={true} name="acceptanceStatus" options={this.state.status} componentId={ComponentId.MAILSTATUS_TEXT} />
                        </div>
                    </Col>
                    <Col xs="10">
                        <div className="form-group">
                            <label className="form-control-label" >{Constants.AWB}</label>
                            <IAwbNumber reducerName="awbReducer" hideLabel={true} form="true" />
                        </div>
                    </Col>
                    <Col xs="6">
                        <div className="form-group">
                            <label className="form-control-label "><IMessage msgkey={Key.RDT_LBL} /></label>
                            <DatePicker name="readyForDeliveryDate" componentId={ComponentId.RDT_TEXT} />
                        </div>
                    </Col>
                    <Col xs="6">
                        <label className="form-control-label "><IMessage msgkey={Key.CONSIGNMENT_DATE_LBL} /></label>
                        <DatePicker name="consignmentDate" componentId={ComponentId.CONSIGNMENTDATE_TEXT} />
                    </Col>
                </Row>
            </div>
        )
    }
}
export default wrapForm(Forms.CARDIT_DSN_TABLE_FILTER)(CarditDsnTableFilter)
