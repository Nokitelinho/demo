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



class MailTransitTableFilter extends React.Component { 
   

	constructor(props) {
		super(props);
	}
	// render(){
	render() {
	






		return (
			<Fragment>
				<Row>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Carrier code</label>
							<ITextField mode="edit" name="carrierCode" uppercase={true} type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Mailbag Destination</label>
							{/* <ITextField mode="edit" name="mailbagDestination" type="text"></ITextField> */}
							<Lov name="mailBagDestination"  uppercase={true} lovTitle="MailBagDestination" dialogWidth="400" dialogHeight="325"  actionUrl="mailtracking.defaults.ux.oelov.list.do?formCount=1"/>
							
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Total Number of Import Mailbags</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Total Weight of Import Mailbags</label>
							<ITextField mode="edit" name="totalWeightImportBags" type="text"></ITextField>	
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Count of Export-NotAssigned Mailbags</label>
							<ITextField mode="edit" name="countOfExportNonAssigned" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Total weight of Export-NotAssigned Mailbags</label>
							<ITextField mode="edit" name="totalWeightOfExportNotAssigned" type="text"></ITextField>
						</div>
					</Col>
					 {/* <Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Used capacity of mail</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>

						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Allotted Mail ULD position(LDC)</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Allotted Mail ULD position(LDP)</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>

						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Allotted Mail ULD position(MDP)</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Available Free Sale capacity</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Free sale ULD position(LDC)</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Free sale ULD position(LDP)</label>
							<ITextField mode="edit" name="totalNoImportBags" type="text"></ITextField>
						</div>
					</Col>
					<Col xs="6">
						<div className="form-group">
							<label className="form-control-label ">Free sale ULD position(MDP)</label>
							<ITextField mode="edit" name="freeSaleULDPostnMDP" type="text"></ITextField>
						</div>
					</Col> */}
					 
				</Row>
			</Fragment>
		)
	}
}


export default wrapForm('mailTransitTableFilterForm')(MailTransitTableFilter)