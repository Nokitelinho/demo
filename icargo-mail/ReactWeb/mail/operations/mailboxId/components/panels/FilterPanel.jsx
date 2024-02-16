import React, { Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { IButton } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';

class FilterPanel extends React.Component {
    constructor(props) {
        super(props);
        this.onlistMailboxDetails = this.onlistMailboxDetails.bind(this);
        this.toggleFilter = this.toggleFilter.bind(this);
        this.onclearMailboxIdDetails = this.onclearMailboxIdDetails.bind(this);
    }

    onlistMailboxDetails() {
        this.props.onlistMailboxDetails();
    }
    toggleFilter() {
        this.props.onToggleFilter((this.props.screenMode === 'edit') ? 'display' : 'edit');
    }
    onclearMailboxIdDetails(){
        this.props.onclearMailboxIdDetails();
    }
    render() {



        return (<Fragment>
            {(this.props.screenMode === 'edit' || this.props.screenMode === 'initial') ? (
            
				<div className="header-filter-panel flippane position-relative" id="headerForm" >
					<div className="pad-md pad-b-3xs">
						<Row>
							<Col xs="4">
								<div className="form-group">
                                    <label className="form-control-label">Mailbox ID</label>
									<Lov name="mailboxId" id = "mailboxId" lovTitle="MailboxID" dialogWidth="600" dialogHeight="530" actionUrl="mailtracking.defaults.ux.mailidlov.list.do?formCount=1"  uppercase={true} maxlength="50" disabled={this.props.listfilter} />
                                    <IToolTip value={'Mailbox Id'} target={'mailboxId'} placement='bottom'/>
                                </div>
                            </Col>
							<Col>
								<div className="mar-t-md">
                                <IButton id = "list" category="btn btn-primary flipper" bType="LIST" accesskey="L" flipper="headerData"  onClick={this.onlistMailboxDetails} disabled={this.props.listfilter}>List</IButton>
                                <IToolTip value={'List'} target={'list'} placement='bottom'/>
                                <IButton id ="Clear" category="btn btn-default" bType="CLEAR" accesskey="C"onClick={this.onclearMailboxIdDetails}>Clear</IButton>
                                <IToolTip value={'Clear'} target={'Clear'} placement='bottom'/>
                                </div>
							</Col>
                        </Row>
                    </div>
                    {(this.props.screenMode === 'edit') && <i className="icon ico-close-fade flipper flipper-ico" onClick={this.toggleFilter}></i>}
                </div>
            ):(
                <div className="header-summary-panel flippane position-relative" id="headerData">
					<div className="pad-md">
						<Row>
							
                            {this.props.filterValues && this.props.filterValues.length > 0 ?
                                    <Col xs="4">
                                        <label className="form-control-label">
                                        Mailbox ID
                                        </label>
                                        <div className="form-control-data">{this.props.filterValues}</div>
                                    </Col> : ""
                                }
							
						</Row>
					</div>
                    <i className="icon ico-pencil-rounded-orange flipper flipper-ico" flipper="headerForm" onClick={this.toggleFilter}> </i>
				</div>
            )}


        </Fragment>)
    }
}


export default wrapForm('mailboxidFilter')(FilterPanel);