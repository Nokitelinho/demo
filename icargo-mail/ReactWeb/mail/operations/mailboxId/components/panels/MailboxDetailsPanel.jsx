import React, { Fragment } from 'react';
import { Row, Col } from "reactstrap";
import { IButton, ICheckbox, IRadio } from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Lov } from 'icoreact/lib/ico/framework/component/common/lov';
import { ITextField, ISelect, IMessage } from 'icoreact/lib/ico/framework/html/elements'
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';
import { commonCompValidations } from 'icoreact/lib/ico/framework/component/common/event/compvalidations';
class MailboxDetailsPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            messagingEnabled: "",
            
        }
    }
    onFilterChange = (event) => {
        this.props.onMessagingOptionChange(event.target.value);
    }

    addRow = () => {
           
                // this.props.getLastRowData();
                // this.props.getFormData;
                this.props.addRow(this.props.newRowData);
          
        // this.props.addRow(this.props.newRowData)
    }
    onDeleteRow = () => {
        this.props.onDeleteRow();
    }
    onSelectmultiple = (data) => {
        if (data.index > -1) {
            if (data.isRowSelected === true) {
                this.props.saveSelectedMailEventIndex(data.index, 'SELECT');
            } else {
                this.props.saveSelectedMailEventIndex(data.index, 'UNSELECT');
            }
        } else {
            if ((data.event) && (data.event.target.checked)) {
                this.props.getTotalCount();
                this.selectedMaileventIndex = this.props.selectedMailEventIndex;
                //this.selectAllMailbagsIndex();

            } else {
                this.props.saveSelectedMailEventIndex(data.index, 'UNSELECT');

            }
        }
      

    }

    

    mailCategory = (rowIndex) => {
        this.props.mailCategory(rowIndex);
    }

    mailClass = (rowIndex) => {
        this.props.mailClass(rowIndex);
    }
    render() {

        let mailCategory = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            mailCategory = this.props.oneTimeValues['mailtracking.defaults.mailcategory'].map((value) => ({ value: value.fieldValue, label: value.fieldValue }));
        }
        let resditversion = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            resditversion = this.props.oneTimeValues['mailtracking.defaults.postaladministration.resditversion'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        let mailboxOwner = [];
        if (!isEmpty(this.props.oneTimeValues)) {
            mailboxOwner = this.props.oneTimeValues['mail.operations.partytype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        let array = [{ "label": "Yes", "value": "Y" }, { "label": "No", "value": "N" }, { "label": "Partial", "value": "P" }]

        const results = this.props.mailEvents ? this.props.mailEvents : [];
        return (<Fragment>
            
                <div className="card p-0 border-bottom-0">
                    <div className="card-header card-header-action">
                        <Col>
                            <h4>Mailbox Details</h4>
                        </Col>
                    </div>
                    <div className="card-body pb-0">
                        <Row>

                            <Col xs="3">


                                <div className="form-group mandatory"><label className="form-control-label mandatory_label">Mailbox Name</label>
                                    <div className="input-group"><ITextField name="mailboxName" id="mailboxName" maxlength="25" uppercase={true} /></div>
                                    <IToolTip value={'Mailbox Name'} target={'mailboxName'} placement='bottom'/>
                                </div>


                            </Col>


                            <Col xs="3">


                                <div className="form-group mandatory">
                                    <label className="form-control-label mandatory_label">Mailbox Owner</label>
                                    <ISelect name="mailboxOwner" id = "mailboxOwner" options={mailboxOwner}/>
                                    <IToolTip value={'MailboxOwner'} target={'mailboxOwner'} placement='bottom'/>
                                </div>


                            </Col>

                            <Col xs="3">

                                <div className="form-group mandatory"><label className="form-control-label mandatory_label">Owner Code</label>
                                {this.props.mailboxowner=== 'PA' &&
                                            <div className="input-group"><Lov name="ownerCode" id = "paCode" lovTitle="Select Postal Administration" componentId="TXT_MAIL_OPERATIONS_CONSIGNMENT_PA" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" uppercase={true} />
                                             <IToolTip value={'Owner Code'} target={'paCode'} placement='bottom'/>
                                    </div>
                                }
                                {this.props.mailboxowner=== 'AR' &&
                                            <div className="input-group"><Lov name="ownerCode" id = "carrierCode" lovTitle="CarrierCode" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1" componentId="CMP_MAIL_OPERATIONS_CARDITENQUIRY_AIRPORT" uppercase={true}/>
                                            <IToolTip value={'Owner Code'} target={'carrierCode'} placement='bottom'/>
                                            </div>
                                        }
                                {this.props.mailboxowner=== '' &&
                                            <div className="input-group"><Lov disabled = {true}/>
                                            </div>
                                }        
                                </div>

                            </Col>
                            <Col xs="3">


                                <div >
                                    <label className="form-control-label mandatory_label">Status</label>
                                    <ITextField name="mailboxStatus" id="mailboxStatus" maxlength="7" uppercase={true} disabled={true}/>
                                </div>


                            </Col>
                            <Col xs="3">


                                <div >
                                    <label className="form-control-label mandatory_label">Remarks</label>
                                    <ITextField name="remarks" id="remarks" maxlength="400" />
                                </div>


                            </Col>
                        </Row>
                    </div>
                    <div class="pad-md pad-b-3xs border-top">
                        <Row>
                            <Col xs="4" >
                                <div className="form-group">

                                    <label className="form-control-label mandatory_label">Resdit Triggering Period (mins) </label>
                                    <ITextField name="resditTriggerPeriod" id ="resditTriggerPeriod" onKeyPress={commonCompValidations.restrictInt} maxlength="6"/>
                                    <IToolTip value={'Resdit Trigger Period'} target={'resditTriggerPeriod'} placement='bottom'/>
                                </div>
                            </Col>
                            <Col xs="4">

                                <div class="form-group">

                                    <label className="form-control-label mandatory_label">Resdit Version</label>
                                    <ISelect name="resditversion" id ="resditversion" options={resditversion}/>
                                    <IToolTip value={'Resdit Version'} target={'resditversion'} placement='bottom'/>
                                </div>
                            </Col>

                            <Col xs="4">

                                <div class="form-check mar-t-md">
                                    <ICheckbox name="partialResdit" className="form-check-input" />
                                    <label className="form-check-label">List all mailbags in RESDITs </label>
                                </div>
                            </Col>



                            <Col xs="4" >
                                <div class="form-check mar-t-md">

                                    <ICheckbox name="msgEventLocationNeeded" className="form-check-input" />
                                    <label className="form-check-label">Specify location in all RESDITs </label>

                                </div>
                            </Col>



                        </Row>
                    </div>
                </div>
                <div className="card p-0 table-card">

                    <div className="card-header card-header-action border-bottom-0">
                        <Col className="col align-items-center d-flex">


                            <h4>Messaging</h4>
                            <div className="d-flex">
                                <IRadio name="messagingEnabled" options={array} onChange={this.onFilterChange} />
                            </div>

                        </Col>
                        <div className="card-header-icons contentDisplay Filter">
                            {'P' === (this.props.messagingEnabledFlag) ?

                                <React.Fragment>
                                    <IButton id ="add" category="btn btn-primary" bType="ADD" accesskey="A" onClick={this.addRow}>Add</IButton>
                                    <IToolTip value={'Add'} target={'add'} placement='bottom'/>
                                    <IButton id = "delete" category="btn btn-default" bType="DELETE" accesskey="D" onClick={this.onDeleteRow}>Delete</IButton>
                                    <IToolTip value={'Delete'} target={'delete'} placement='bottom'/>
                                </React.Fragment>
                                : null}
                        </div>




                    </div>
                    <div class="card-body p-0 d-flex border-top">
                        {(this.props.messagingEnabledFlag === 'P') ?


                            <ITable
                                form={true}
                                name="mailBoxIdTable"
                                headerHeight={35}
                                resetFormOnDataChange={false}
                                className="table-list"
                                gridClassName=""
                                headerClassName=""
                                rowHeight={45}
                                rowClassName="table-row"
                                sortEnabled={false}
                                resetSelectionOnDataChange={true}
                                tableId="mailBoxIdTable"
                                
                                onRowSelection={this.onSelectmultiple}
                                data={this.props.mailEvents}
                            >
                                <Columns>
                                    <IColumn
                                        width={40}
                                        dataKey=""
                                        flexGrow={0}
                                        className="">
                                        <Cell>
                                            <HeadCell selectOption>
                                            </HeadCell>
                                            <RowCell selectOption>
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="mailCategory"
                                        label="Category"
                                        width={110}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => {

                                                    return (
                                                        <Content> <ISelect name={`${cellProps.rowIndex}.mailCategory`} id = "mailCategory" options={mailCategory} portal = {true}/>
                                                          <IToolTip value={'Mail Category'} target={'mailCategory'} placement='bottom'/> 
                                                        </Content>
                                                    )

                                                }
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="mailClass"
                                        label="Subclass"
                                        width={130}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => {

                                                    return (
                                                        //<Content>{cellProps.cellData}</Content>
                                                        <Content><Lov name={`${cellProps.rowIndex}.mailClass`} id = "Subclass" uppercase={true} data-rowIndex={cellProps.rowIndex} lovTitle="Subclass" dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.subclaslov.list.do?formCount=1" maxlength="2" />
                                                          <IToolTip value={'SubClass'} target={'Subclass'} placement='bottom'/> 
                                                        </Content>
                                                        //<Content><Lov name="mailboxCategory" uppercase={true} type="text" value={cellProps.cellData}/></Content>)
                                                    )

                                                }
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                    <IColumn
                                        dataKey="received"
                                        label="Received"
                                        width={90}
                                        id="received"
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (

                                                        //<Content><input type="checkbox" name='received' value='received' id='received' checked={cellProps.rowData.received === 'Y' ? true : false} /></Content>
                                                        <Content><ICheckbox name={`${cellProps.rowIndex}.received`} className="form-check-input" /></Content>
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="handedOver"
                                        label="Handover Received"
                                        id="handedOver"
                                        width={130}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" checked={cellProps.rowData.handedOver === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.handedOver`} className="form-check-input" /></Content>  
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="uplifted"
                                        label="Uplifted"
                                        id="uplifted"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" name='uplifted' checked={cellProps.rowData.uplifted === 'Y' ? true : false} /></Content>
                                              <Content><ICheckbox name={`${cellProps.rowIndex}.uplifted`} className="form-check-input" /></Content>            
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="loadedResditFlag"
                                        label="Loaded"
                                        id="loadedResditFlag"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (

                                             //<Content><input type="checkbox" checked={cellProps.rowData.loadedResditFlag === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.loadedResditFlag`} className="form-check-input"/></Content>           
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="assigned"
                                        label="Assigned"
                                        id="assigned"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" checked={cellProps.rowData.assigned === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.assigned`} className="form-check-input" /></Content>         
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="returned"
                                        label="Returned"
                                        id="returned"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                             //<Content><input type="checkbox" checked={cellProps.rowData.returned === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.returned`} className="form-check-input" /></Content>           
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="onlineHandedOverResditFlag"
                                        label="Online Handover"
                                        id="onlineHandedOverResditFlag"
                                        width={120}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (

                                             //<Content><input type="checkbox" checked={cellProps.rowData.onlineHandedOverResditFlag === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.onlineHandedOverResditFlag`} className="form-check-input" /></Content>       
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="pending"
                                        label="Pending"
                                        id="pending"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" checked={cellProps.rowData.pending === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.pending`} className="form-check-input" /></Content>            
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="readyForDelivery"
                                        label="Ready for Delivery"
                                        id="readyForDelivery"
                                        width={122}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" checked={cellProps.rowData.readyForDelivery === 'Y' ? true : false} /></Content>
                                               <Content><ICheckbox name={`${cellProps.rowIndex}.readyForDelivery`} className="form-check-input" /></Content> 
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="transportationCompleted"
                                        label="Transport Completed"
                                        id="transportationCompleted"
                                        width={140}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            //<Content><input type="checkbox" checked={cellProps.rowData.transportationCompleted === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.transportationCompleted`} className="form-check-input" /></Content>      
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="arrived"
                                        label="Mail Arrived"
                                        id="arrived"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                           //<Content><input type="checkbox" checked={cellProps.rowData.arrived === 'Y' ? true : false} /></Content>
                                           <Content><ICheckbox name={`${cellProps.rowIndex}.arrived`} className="form-check-input" /></Content>               
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                    <IColumn
                                        dataKey="delivered"
                                        label="Delivered"
                                        id="delivered"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            // <Content><input type="checkbox" checked={cellProps.rowData.delivered === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.delivered`} className="form-check-input" /></Content>  
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
									  
                                       <IColumn
                                        dataKey="foundFlag"
                                        label="Found"
                                        id="foundFlag"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            // <Content><input type="checkbox" checked={cellProps.rowData.delivered === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.foundFlag`} className="form-check-input" /></Content>  
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                 
                                       <IColumn
                                        dataKey="lostFlag"
                                        label="Lost"
                                        id="lostFlag"
                                        width={90}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell>
                                                {(cellProps) => (

                                            // <Content><input type="checkbox" checked={cellProps.rowData.delivered === 'Y' ? true : false} /></Content>
                                            <Content><ICheckbox name={`${cellProps.rowIndex}.lostFlag`} className="form-check-input" /></Content>  
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                </Columns>
                            </ITable>



                            : null}

                    </div>

                </div>
            
        </Fragment>)
    }
}


export default wrapForm('mailboxidPanel')(MailboxDetailsPanel);