import React, { Fragment } from 'react';
import PropTypes from 'prop-types'
import { ITextField, ISelect, ICheckbox, IRadio } from 'icoreact/lib/ico/framework/html/elements'
import { FormGroup, Label, Row, Col, Collapse, Container } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';

class MailbagTableFilter extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {


		let category = [];
		let status = [];
		let operationType=[];
		let carditStat = [];
		if (!isEmpty(this.props.oneTimeValues)) {
			status = this.props.oneTimeValues['mailtracking.defaults.mailstatus'].filter((value) => {
				if (value.fieldValue !== 'ASG' && value.fieldValue !== 'DMG'
					&& value.fieldValue !== 'CDT' && value.fieldValue !== 'MFT'
					&& value.fieldValue !== 'DEP' && value.fieldValue !== 'ARV'
					&& (!value.fieldDescription.includes("Resdit")))
					return value
			});

			category = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
			status = status.map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
		    operationType= this.props.oneTimeValues['mailtracking.defaults.operationtype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
		}

		carditStat = [{ "label": "Available", "value": "Y" },
		{ "label": "Not Available", "value": "N" }]



		return (
			<Fragment>
				<Row>
					<Col xs="12">
						<div className="form-group">
							<label className="form-control-label ">Maibag Id</label>
							<ITextField mode="edit" componentId="CMP_CAPACITY_MONITORING_LISTFLIGHTS_FLIGHTCARRIERCODE" name="mailbagId" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Latest Operation</label>
							<ISelect name="latestStatus" options={status} />
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Date of Operation</label>
							<DatePicker name="scannedDate"/>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Origin Airport</label>
							<Lov name="mailorigin" lovTitle="Origin OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_OOE"/>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Dest. Airport</label>
							<Lov name= "mailDestination" lovTitle="Destination OE" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_DOE" />
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Airport</label>
							<Lov name="scannedPort" componentId='CMP_Operations_FltHandling_ExportManifest_ThruRouting_Destination' lovTitle="Airport" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirport.do?formCount=1" />
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">RDT</label>
							<DatePicker name="reqDeliveryDate"/>

						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">IN/OUT</label>
							<ISelect name="operationalStatus" options={operationType} />
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Flight/Dest</label>
							<ITextField mode="edit" name="carrierCode" type="text"></ITextField>

						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Dep/Arr Date</label>
							<DatePicker name="flightDate"/>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Container No</label>
							<ITextField mode="edit" name="uldNumber" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Cons. No</label>
							<ITextField mode="edit" name="consignmentNumber" type="text"></ITextField>
						</div>
					</Col>
					
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">User ID</label>
							<ITextField mode="edit" name="scannedUser" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Wt</label>
							<ITextField mode="edit" name="weight" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Actual Weight</label>
							<ITextField mode="edit" name="actualWeight" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Vol.</label>
							<ITextField mode="edit" name="volume" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="12">
						<div className="form-group">
							<label className="form-control-label ">AWB No</label>
							<ITextField mode="edit" name="awb" type="text"></ITextField>
						</div>
					</Col>
				</Row>
			</Fragment>
		)
	}
}


export default wrapForm('mailbagTableFilter')(MailbagTableFilter)