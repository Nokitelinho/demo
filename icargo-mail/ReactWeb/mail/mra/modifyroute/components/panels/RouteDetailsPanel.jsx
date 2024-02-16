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
import { IFlightNumber } from 'icoreact/lib/ico/framework/component/business/flightnumber';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date'
import { Constants,ComponentId,Key } from '../../constants/constants.js'

class RouteDetailsPanel extends React.Component {
  
populateFlightNumber=(event)=>{

    event.preventDefault();

    this.props.populateFlightNumber(event.target.getAttribute("data-rowIndex"));
}

    addRow = () => {
                this.props.addRow(this.props.newRowData);
    }
    onDeleteRow = () => {
        this.props.onDeleteRow();
    }

     onSelectmultiple = (data) => {
      
           if (data.index > -1) {
            if (data.isRowSelected === true) {
                this.props.selectedFlightDetailIndex(data.index, Constants.SELECT);
            } else {
                this.props.selectedFlightDetailIndex(data.index, Constants.UNSELECT);
            }
        } else {
            if ((data.event) && (data.event.target.checked)) {
                this.props.getTotalCount();
                this.selectedMaileventIndex = this.props.selectedMailEventIndex;
            } else {
                this.props.selectedFlightDetailIndex(data.index, Constants.UNSELECT);

            }
        }
    }

    render() {

        let blockSpace = [];
        let agreementType = [];

        if (!isEmpty(this.props.oneTimeValues)) {
            blockSpace = this.props.oneTimeValues['cra.proration.blockspacetype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
        
        if (!isEmpty(this.props.oneTimeValues)) {
            agreementType = this.props.oneTimeValues['mailtracking.defaults.agreementtype'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        }
    
        return (<Fragment>
            
            <div className="card p-0 border-bottom-0">

                    <div class="pad-md pad-b-3xs border-top">
                        <Row>
                            <Col xs="3" >
                                <div className="form-group">
                                <label className="form-control-label">Transfer PA</label>
                                <Lov name="transferPA" id = "transferPA" lovTitle="Select Postal Administration"  dialogWidth="600" dialogHeight="425" actionUrl="mailtracking.defaults.ux.palov.list.do?formCount=1" uppercase={true} />
                                 <IToolTip value='Transfer PA' target={'transferPA'} placement='bottom'/>
                                </div>
                            </Col>
                            <Col xs="3" >
                                <div className="form-group">
                                <label className="form-control-label">Transfer Airline</label>
                                <Lov name="transferAirline" id = "transferAirline" lovTitle="CarrierCode" dialogWidth="600" dialogHeight="425" actionUrl="ux.showAirline.do?formCount=1"  uppercase={true}/>
                                <IToolTip value='Transfer Airline' target={'transferAirline'} placement='bottom'/>
                                </div>
                            </Col>
                        </Row>
                    </div>
            </div>
            
            <div className="card p-0 table-card">

                    <div className="card-header card-header-action border-bottom-0">
    
                        <div className="card-header-icons contentDisplay Filter">
                        
                          

                                <React.Fragment>
                                    <IButton id ="add" category="btn btn-primary" privilegeCheck={false} componentId={ComponentId.ADD_BTN}  bType="ADD" accessKey="A" onClick={this.addRow}>
                                    <IMessage msgkey={Key.ADD_LBL} defaultMessage="Add"/> 
                                    </IButton>
                                    <IToolTip value={Key.ADD_TLTP} target={'add'} placement='bottom'/>
                                    <IButton id = "delete" privilegeCheck={false} componentId={ComponentId.DELETE_BTN} category="btn btn-default" bType="DELETE" accessKey="D" onClick={this.onDeleteRow}>
                                    <IMessage msgkey={Key.DELETE_LBL} defaultMessage="Delete"/> 
                                    </IButton>
                                    <IToolTip value={Key.DELETE_TLTP} target={'delete'} placement='bottom'/>
                                </React.Fragment>
                         
                        </div>

                    </div>
         
             <div className="card-body p-0 d-flex border-top">
                            <ITable
                                form={true}
                                destroyFormOnUnmount={true}
                                name={Constants.ROUTEDETAIL_TABLE}
                                headerHeight={35}
                                resetFormOnDataChange={false}
                                className="table-list"
                                gridClassName=""
                                headerClassName=""
                                rowHeight={45}
                                rowClassName="table-row"
                                sortEnabled={false}
                                resetSelectionOnDataChange={true}
                                tableId={Constants.ROUTEDETAIL_TABLE}
                                onRowSelection={this.onSelectmultiple}
                                data={this.props.routeDetails}
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
                                        dataKey="carrierCode"
                                        label="Flight No "
                                        width={70}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                             <RowCell >
                                                {(cellProps) => (
                                                        <Content>                                                            
                                                            <ITextField name={`${cellProps.rowIndex}.carrierCode`} id='carrierCode'  uppercase={true} maxLength='3'  />
                                                           
                                                            </Content>       
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                                
                                    <IColumn
                                        dataKey="flightNumber"
                                        label="Flight No"
                                        width={120}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content></Content>)
                                                }
                                            </HeadCell>
                                             <RowCell >
                                                {(cellProps) => (
                                                        <Content>                                                                                                                                                                                                                                     
                                                            <ITextField maxLength="5"   data-rowIndex={cellProps.rowIndex}  name={`${cellProps.rowIndex}.flightNumber`} id ='flightNumber'  onBlur={(event) => this.populateFlightNumber(event)} />                                                        
                                                           
                                                            </Content>                                                   
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                    <IColumn
                                        dataKey="flightDate"
                                        label="Dep Date"
                                        width={140}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                             <RowCell >
                                                {(cellProps) => (
                                                        <Content>                                                          
                                                            <DatePicker name={`${cellProps.rowIndex}.flightDate`} id='flightDate' />    
                                                                                                                 
                                                            </Content>                                                           
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                     <IColumn
                                        dataKey="truck"
                                        label="Truck"
                                        width={70}
                                        id="truck"
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                            <RowCell >
                                                {(cellProps) => (
                                                      
                                                        <Content><ICheckbox disabled={true} id='truck' name={`${cellProps.rowIndex}.truck`} className="form-check-input" />
                                                         
                                                        </Content>
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                    <IColumn
                                        dataKey="pol"
                                        label="POL"
                                        width={130}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                             <RowCell >
                                                {(cellProps) => (
                                                        <Content>
                                                           <Lov disabled={false} maxLength="3" id='pol'   uppercase={true} name={`${cellProps.rowIndex}.pol`} lovTitle= "Select Airport"  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />                                                        
                                                        
                                                        </Content>
                                                            
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>
                                     <IColumn
                                        dataKey="pou"
                                        label="POU"
                                        width={130}
                                        flexGrow={0}>
                                        <Cell>
                                            <HeadCell disableSort>
                                                {(cellProps) => (
                                                    <Content>{cellProps.label}</Content>)
                                                }
                                            </HeadCell>
                                             <RowCell >
                                                {(cellProps) => (
                                                        <Content>                                                            
                                                            <Lov disabled={false} maxLength="3" id='pou'   uppercase={true} name={`${cellProps.rowIndex}.pou`} lovTitle= "Select Airport"  dialogWidth="600" dialogHeight="520" actionUrl="ux.showAirport.do?formCount=1" />                                                                                                               
                                                            
                                                            </Content>
                                                            
                                                )
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                 
                                    <IColumn
                                        dataKey="blockSpace"
                                        label="Block Space"
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
                                                        <Content> <ISelect clearable={true} name={`${cellProps.rowIndex}.blockSpace`} id = "blockSpace" options={blockSpace}/>
                                                          
                                                    
                                                        </Content>
                                                    )

                                                }
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                     <IColumn
                                        dataKey="agreementType"
                                        label="Agreement Type"
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
                                                        <Content> <ISelect clearable={true} name={`${cellProps.rowIndex}.agreementType`}  id = "agreementType" options={agreementType}/>
                                                         
                                                         
                                                        </Content>
                                                    )

                                                }
                                                }
                                            </RowCell>
                                        </Cell>
                                    </IColumn>

                                </Columns>
                            </ITable>

                    </div>
        
            </div>

        </Fragment>)
    }
}


export default wrapForm('routeDetailsPanel')(RouteDetailsPanel);