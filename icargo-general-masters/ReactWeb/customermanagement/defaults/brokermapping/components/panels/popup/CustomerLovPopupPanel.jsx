import React, { Component} from "react";

import { FormGroup, Label } from "reactstrap";
import { IButton, ITextField, IMessage, ISelect } from 'icoreact/lib/ico/framework/html/elements';
import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import icpopup, {
    PopupFooter, PopupBody
} from 'icoreact/lib/ico/framework/component/common/modal';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content }
    from 'icoreact/lib/ico/framework/component/common/grid';
import { Lov } from "icoreact/lib/ico/framework/component/common/lov";
import { isEmpty } from "icoreact/lib/ico/framework/component/util";

import { getFirstNameofAdditionalName, getLengthOfListData, convertStringToArray } from "../../../utils/utils";
import AdditionalNamesPopOverPanel from "../popover/AdditionalNamesPopOverPanel.jsx"


class CustomerLovPopupPanel extends Component {
    constructor(props) {
        super(props);
    }

    closeCustomerPopup = () => {
        this.props.closeCustomerPopup();
    }
    handleClear = () => {
        this.props.reset();
        this.props.handleClear();
    }
    onRowSelect = (data) => {
        let updatedCustomerSelectedIndex = [];
        if (data.index > -1) {
            if (data.isRowSelected) {
                updatedCustomerSelectedIndex = [data.rowData];
            }
        }
        // this.detailsTableRef.selectedIndexes=[data.index];
        // this.detailsTableRef.selectedIndexesHandler(this.detailsTableRef.selectedIndexes);
        this.props.displaySelectedIndex(updatedCustomerSelectedIndex);
    }
    displaySelectedCustomer = () => {
         //Customer Details for Add Consignee POA Section
        if(this.props.brokerCode?this.props.brokerCode==="Y":false)
        {
            let customerDetails = this.props.selectedCustomerDetails;
            let brokerDetails={brokerCode:customerDetails?customerDetails[0].customerCode:"",
                                brokerName:customerDetails?customerDetails[0].customerName:"",consigneeCode:'',consigneeName:''}
            this.props.selectPoaCustomer(brokerDetails);
        }
        //Customer Details for Add Broker POA Section
        else if(this.props.consigneeCode?this.props.consigneeCode==="Y":false)
        {
            let customerDetails = this.props.selectedCustomerDetails;
            let consigneeDetails={
                brokerCode:'', 
                brokerName:'',
                consigneeCode:customerDetails?customerDetails[0].customerCode:"",
                consigneeName:customerDetails?customerDetails[0].customerName:""}
            this.props.selectPoaCustomer(consigneeDetails);
        }
        else{
        //if not of the above cases then the customer belongs to Main Customer Field
        let customerDetails = this.props.selectedCustomerDetails;
        this.props.selectCustomer(customerDetails.length>0 ? customerDetails[0].customerCode : "");
        }
    }
    // setDetailsTableRef = (ref) => {
    //     this.detailsTableRef = ref;
    // }
    render() {
        const { oneTimeValues } = this.props;
        let customerType = [];
        if (!isEmpty(oneTimeValues)) {
            customerType = oneTimeValues['shared.customer.customertype'].
                map(custType => ({ value: custType.fieldValue, label: custType.fieldDescription }));
        }
        return (

            <div className="d-flex flex-column" >
                <PopupBody >
                    <>
                        <div className="lov-filter pad-md ">
                            <div className="row">
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.custTyp" defaultMessage="Customer Type" />
                                        </Label>

                                        <ISelect name="customerType" options={customerType} multi={true} defaultOption={false} />

                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.custcode" defaultMessage="Customer Code" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="customerCode" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.custName" defaultMessage="Customer Name" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="customerName" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.address" defaultMessage="Address" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="address" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.city" defaultMessage="City" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="cityCode" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.country" defaultMessage="Country" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="countryCode" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">
                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.zip" defaultMessage="Zip Code" />
                                        </Label>
                                        <ITextField componentId=" " //uppercase={true}
                                             className="text-transform"
                                            name="zipCode" type="text" />
                                    </FormGroup >
                                </div>
                                <div className="col-6">

                                    <FormGroup className="form-group">
                                        <Label className="form-control-label">
                                            <IMessage msgkey="customermanagement.defaults.brokermapping.station" defaultMessage="Station" />
                                        </Label>
                                        <div class="input-group">

                                            <Lov
                                                isMultiselect="N"
                                                componentId=""
                                                uppercase={true}
                                                name="stationCode"
                                                lovTitle="Station"
                                                maxlength="3"
                                                dialogWidth="600"
                                                dialogHeight="540"
                                                lovContainerHeight="540"
                                                actionUrl="ux.showAirport.do"
                                            />
                                        </div>
                                        <div className="input-group">

                                        </div>
                                    </FormGroup >
                                </div>
                            </div>
                        </div>
                        <div className='row'>
                            <div className='col'>
                                <div className="btn-row">
                                    <IButton accesskey="" category="primary" bType="LIST" componentId="" onClick={() => this.props.onList()} >
                                        <IMessage msgkey="customermanagement.defaults.brokermapping.customerlov.list" defaultMessage="List" />
                                    </IButton>
                                    <IButton accesskey="" category="default" bType="CLEAR" componentId="" onClick={() => this.handleClear()}>
                                        <IMessage msgkey="customermanagement.defaults.brokermapping.clear" defaultMessage="Clear" />
                                    </IButton>
                                </div>
                            </div>
                        </div>
                
                    <div className="inner-panel border-top" style={{ height: '43vh', display: "flex" }} >
                        <ITable rowCount={this.props.customerListDetails ? this.props.customerListDetails.length : 0}
                            headerHeight={40}
                            className="table-list"
                            gridClassName="table_grid"
                            rowHeight={35}
                            rowClassName="table-row"
                            sortEnabled={false}
                            onRowSelection={this.onRowSelect}
                            singleSelectEnabled={true}
                            // innerRef={this.setDetailsTableRef}
                            data={this.props.customerListDetails ? this.props.customerListDetails : []}>
                            <Columns>
                                <IColumn
                                    dataKey=""
                                    className="fix-col"
                                     width={40}
                                >
                                    <Cell>
                                        <HeadCell disableSort selectOption>
                                            {cellProps => <Content></Content>}

                                        </HeadCell>

                                        <RowCell selectOption>
                                        
                                        </RowCell>

                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Customer Type"
                                    width={55}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>
                                                    <div className='wrap-view'>
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "AG" ? "Agent" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "CU" ? "Customer" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "GSA" ? "GSA" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "CC" ? "CC Collector" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "GPA" ? "GPA" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "HC" ? "Holding Company" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "IC" ? "Interline Carrier" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "TC" ? "Trucking Company" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "TSP" ? "Trucking Service Provider" : "" : ""}
                                                    {cellProps.rowData.customerType ? cellProps.rowData.customerType === "TMP" ? "Temporary Customer" : "" : ""}
                                                    </div>
                                                </Content>
                                            )}
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Customer Code"
                                    width={85}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>
                                            )}
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.customerCode ? cellProps.rowData.customerCode : ""}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Customer Name"
                                    width={90}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content><div className='wrap-view'>{cellProps.rowData.customerName ? cellProps.rowData.customerName : " "}</div></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Additional Name"
                                    width={90}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>
                                                    <div className='wrap-view'>
                                                    {getFirstNameofAdditionalName(cellProps.rowData.additionalNames)}
                                                    {(getLengthOfListData(cellProps.rowData.additionalNames) > 1) ?
                                                        <AdditionalNamesPopOverPanel
                                                            additionalNames={cellProps.rowData.additionalNames ? convertStringToArray(cellProps.rowData.additionalNames) : ""}
                                                            rowIndex={cellProps.rowIndex}
                                                        /> : ""}
                                                    </div>
                                                </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Address"
                                    width={80}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content><div className='wrap-view'>{cellProps.rowData.address}</div></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="City"
                                    width={50}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content><div className='wrap-view'>{cellProps.rowData.cityCode}</div></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Country"
                                    width={20}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>{cellProps.rowData.countryCode}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Zipcode"
                                    width={30}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>{cellProps.rowData.zipCode}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    id=""
                                    selectColumn={true}
                                    label="Station"
                                    width={20}
                                    flexGrow={1}
                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (

                                                <Content>{cellProps.rowData.stationCode}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                            </Columns>
                        </ITable>

                    </div>
                    </>
                </PopupBody>
                <PopupFooter>
                    <IButton
                        category="default"
                        componentId=""
                        onClick={this.displaySelectedCustomer}
                    >
                        <IMessage msgkey="customermanagement.defaults.brokermapping.ok" defaultMessage="OK" />
                    </IButton>
                    <IButton
                        category="default"
                        componentId=""
                        onClick={this.closeCustomerPopup}
                    >
                        <IMessage msgkey="customermanagement.defaults.brokermapping.close" defaultMessage="Close" />
                    </IButton>
                </PopupFooter>
                {/* </Container>  */}
            </div>
        );

    }

}

let customerLovPopupPanelForm = wrapForm("customerListform")(CustomerLovPopupPanel);
export default icpopup(customerLovPopupPanelForm,
    {
        title: 'Search Customer',
        className: "modal_70w"
    })