import React, { Component } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import MailbagTableCustomRowPanel from './MailbagTableCustomRowPanel.jsx';
import TableHeaderPanel from './TableHeaderPanel.jsx'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import { ITextField} from 'icoreact/lib/ico/framework/html/elements';
import { IPopover, IPopoverHeader, IPopoverBody } from 'icoreact/lib/ico/framework/component/common/popover';
import { IButton, IMessage } from 'icoreact/lib/ico/framework/html/elements';



//const getTableResults = (state) => state.filterReducer.mailbagsdetails && state.filterReducer.mailbagsdetails.results ? state.filterReducer.mailbagsdetails.results : [];
let Aux = (props)=>  props.children
export default class InvoicDetailsTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = []
        this.state = {
            openPopover: false,
            remarksTarget: '',
            remarksToDisplay:''
        }

    }

   onRowSelection = (data) => {

        if (data.index > -1) {
            if (data.isRowSelected) {

                this.selectMailbag(data.index);
            } else {
                  this.unSelectMailbag(data.index);
            }
        }
        else {
             if ((data.event) && (data.event.target.checked)) {
                this.selectAllMailbags(-1);
             } else {
                 this.unSelectAllMailbags();
             }
         }
    }

    selectMailbag = (mailbag) => {
        let claimSubExists = 'N';
        this.selectedMailbags.push(mailbag);
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);

        for (let i = 0; i < this.selectedMailbags.length; i++) {
            if (this.props.mailbags.results[this.selectedMailbags[i]].claimStatus === 'SUB'){
                claimSubExists = 'Y';
                 break;
            }
        }
        if (claimSubExists === 'Y'){
            this.props.disableAcceptButton();
        }
    }

    unSelectMailbag = (mailbag) => {
        let index = -1;
        let claimSubExists = 'N';
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            var element = this.selectedMailbags[i];
            if (element === mailbag) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            this.selectedMailbags.splice(index, 1);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            if (this.props.mailbags.results[this.selectedMailbags[i]].claimStatus === 'SUB'){
                claimSubExists = 'Y';
            }
        }
        if (claimSubExists === 'N'){
            this.props.enableAcceptButton();
        }
    }

    selectAllMailbags = () => {
        const results = this.props.mailbags ? this.props.mailbags.results : '';
        const length = results?results.length:'';
        let claimSubExists = 'N';

        for (let i = 0; i < length; i++) {
            this.selectedMailbags.push(i);
        }
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            if (this.props.mailbags.results[this.selectedMailbags[i]].claimStatus === 'SUB'){
                claimSubExists = 'Y';
                 break;
            }
        }
        if (claimSubExists === 'Y'){
            this.props.disableAcceptButton();
        }
    }

    unSelectAllMailbags = () => {
        let claimSubExists = 'N';
        this.selectedMailbags = []
        this.props.saveSelectedMailbagsIndex(this.selectedMailbags);
        for (let i = 0; i < this.selectedMailbags.length; i++) {
            if (this.props.mailbags.results[this.selectedMailbags[i]].claimStatus === 'SUB'){
                claimSubExists = 'Y';
            }
        }
        if (claimSubExists === 'N'){
            this.props.enableAcceptButton();
        }
    }
    onOpenRemarks = (id,remarks) => {
        this.setState(() => {
            return {
                openPopover: true,
                remarksTarget: id,
                remarksToDisplay:remarks
            }
        })

    }

    onCloseRemarks = () => {
        this.setState(() => {
            return {
                openPopover: false
            }
        })
    }

    onOKRemarks = () => {
        let remarks = document.getElementById('remarks').value;
        let index = this.state.remarksTarget;
        let data = { remarks: remarks, index: index }
        this.props.onOKRemarks(data);
        this.setState(() => {
            return {
                openPopover: false,
            }
        })
    }
    moveToActionIndividual = (id, status, toStatus) => {
        let index = id;
        let fromProcessStatus = status;
        let toProcessStatus = toStatus;
        let data = { index: index, toProcessStatus: toProcessStatus, fromProcessStatus: fromProcessStatus };
        this.props.moveToActionIndividual(data);
    }

    claimHistory = (id) => {
        let index = id;
        let data ={index:index}
        this.props.claimHistory(data);
    }

        render() {

            const tableFilterobj=this.props.tableFilter;
            const results = this.props.mailbags?this.props.mailbags.results:'';
        const rowCount = results?results.length:'';
           const currencyCode=this.props.mailbags?this.props.mailbags.results[0].currencyCode:'';
           // const mailbags=this.props.mailbags;


        return (
            <Aux>
            <ITable
                rowCount={rowCount}
                headerHeight={45}
                className="table-list"
                rowHeight={50}
                rowClassName="table-row"
                tableId="mailbagtable"
                sortEnabled={false}
                form={true}
                resetSelectionOnDataChange={true}
                name='newMailbagsTable'
                onRowSelection={this.onRowSelection}
                customHeader={{
                            headerClass: '',
                            placement: 'dynamic',

                        customPanel: <TableHeaderPanel filterValues={this.props.filterValues} moveToAction={this.props.moveToAction} />,
                            "pagination":{"page":this.props.mailbags,getPage:this.props.getNewPage,defaultPageSize:this.props.defaultPageSize,
                            options : [ { value: '10', label: '10' } , { value: '20', label: '20' },{ value: '30', label: '30' },{ value: '40', label: '40' },{ value: '100', label: '100' } ] },


                  }}
                data={results}
               >
                    <Columns customRow={MailbagTableCustomRowPanel} customRowDataKey='mailIdr' >
                   <IColumn
                        // width={15}
                        dataKey=""
                        //flexGrow={0.2}
                        hideOnExport>
                        <Cell>
                              <HeadCell disableSort selectOption>
                              </HeadCell>
                              <RowCell selectOption>
                              </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        // width={5}
                         //flexGrow={.5}
                         hideOnExport>
                        <Cell>
                             <HeadCell disableSort rowToggler>
                             </HeadCell>
                             <RowCell rowToggler>
                             </RowCell>
                        </Cell>
                    </IColumn>

                    <IColumn
                        dataKey="mailIdr"
                        label="Mailbag ID"
                        flexGrow={3}
                        id="mailbagId"
                        width={175}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>{cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="weight"
                        label="Wt"
                        flexGrow={1}
                        id="weight"
                        width={30}
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>{cellProps.label}</Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>{cellProps.cellData} {cellProps.rowData.weightUnit}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                     <IColumn
                        dataKey="rate"
                        label="Rate"
                        width={40}
                        flexGrow={1}
                        id="rate/kg"
                        headerClass="text-right"
                        className="text-right"
                        >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                        <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content> {cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                     <IColumn
                        dataKey="charge"
                        label="Charge"
                        width={45}
                        flexGrow={1}
                        id="charge/kg"
                        className="text-right">
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                        <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content> {cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                     <IColumn
                        dataKey="disincentive"
                        label="Disincentive"
                            width={55}
                        flexGrow={1}
                            id="discentive"
                            className="text-right">

                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                       <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>  {cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="incentive"
                        label="Incentive"
                        width={55}
                        flexGrow={1}
                        id="incentive"
                        className="text-right"
                       >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                     <div className="text-right">
                                        {cellProps.label+"("}{currencyCode+") "}
                                    </div>
                                    </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>  {cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                     <IColumn
                        dataKey="netamount"
                        label="Net Amount"
                        width={80}
                        flexGrow={1}
                        id="netamount"
                        className="text-right"
                       >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                        <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content> {cellProps.cellData}</Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>
                     <IColumn
                        //dataKey="invoicamount"
                        label="INVOIC Amount"
                        width={80}
                        flexGrow={1}
                        id="invoicamount"
                        className="text-right"
                       >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                        <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>

                            <RowCell>
                                {(cellProps) => {
                                        if (cellProps.rowData.invoicPayStatus === 'TBR' || cellProps.rowData.invoicPayStatus === 'PART' || cellProps.rowData.invoicPayStatus === 'REJ' ||cellProps.rowData.invoicPayStatus === 'SP'||cellProps.rowData.invoicPayStatus === 'ROP') {
                                            return <Content>
                                               <span>{ cellProps.rowData.totalSettlementAmount}</span><span className="badge mar-l-xs light badge-error pad-y-3xs pull-right">{cellProps.rowData.invoicPayStatus}</span>
                                        </Content>
                                    }

                                        if (cellProps.rowData.invoicPayStatus === 'PAID' || cellProps.rowData.invoicPayStatus === 'OP') {
                                            return <Content> <span>{cellProps.rowData.totalSettlementAmount}</span><span class="badge mar-l-xs light badge-active pad-y-3xs pull-right">{cellProps.rowData.invoicPayStatus}</span>
                                        </Content>
                                    }
                                    if (cellProps.rowData.invoicPayStatus === 'ASP' || cellProps.rowData.invoicPayStatus === 'AOP') {
                                            return <Content> <span> {cellProps.rowData.totalSettlementAmount}</span><span class="badge mar-l-xs light badge-info pad-y-3xs pull-right">{cellProps.rowData.invoicPayStatus}</span>
                                        </Content>
                                    }
                                    else
                                            return <Content><span>{cellProps.rowData.totalSettlementAmount}</span><span class="badge mar-l-xs light badge-info pad-y-3xs pull-right"></span>
                                        </Content>

                                }}
                            </RowCell>
                        </Cell>
                    </IColumn>
                    <IColumn
                        dataKey="claimamount"
                        label="Claim Amount"
                        width={60}
                        flexGrow={1}
                        id="claimamount"
                        className="text-right"
                       >
                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content>
                                         <div className="text-right">
                                            {cellProps.label +"("}{currencyCode+") "}
                                        </div>
                                    </Content>)
                                }
                            </HeadCell>


                            <RowCell>
                                {(cellProps) => (
                                    <Content>
                                        <div className="d-inline">
                                                <ITextField className="form-control fix-width-money-input d-inline text-right" name={`${cellProps.rowIndex}.claimamount`} value={cellProps.cellData} disabled={cellProps.rowData.claimStatus === 'SUB' || (this.props.filterValues ? this.props.filterValues.fromScreen==='MRA078':false) ? true :false}/>

                                            {
                                                cellProps.rowData.claimStatus === 'REJ' &&
                                                <span className="badge light badge-error pad-y-3xs mar-l-xs">{cellProps.rowData.claimStatus}</span>
                                            }
                                            {
                                                cellProps.rowData.claimStatus === 'APPD' &&
                                                <span class="badge light badge-active pad-y-3xs mar-l-xs">{cellProps.rowData.claimStatus}</span>
                                            }
                                            {
                                                (cellProps.rowData.claimStatus === 'GEN' || cellProps.rowData.claimStatus === 'SUB') &&
                                                <span class="badge light badge-info pad-y-3xs mar-l-xs">{cellProps.rowData.claimStatus}</span>
                                            }
                                        </div>
                                    </Content>
                                )
                                }
                            </RowCell>




                        </Cell>
                    </IColumn>

                     <IColumn
                        //dataKey="disincentive"
                        //label="Disincentive"
                            width={45}
                       flexGrow={1}
                       id="info"
                        >

                        <Cell>
                            <HeadCell disableSort>
                                {(cellProps) => (
                                    <Content></Content>)
                                }
                            </HeadCell>
                            <RowCell>
                                {(cellProps) => (
                                    <Content>
                                            <div class="d-flex fix-outline">
                                            <a href="#" className="info-hex-icon mar-r-2xs align-self-center">
                                               <i className="icon ico-exclamatory" onClick={() => this.claimHistory(cellProps.rowIndex)}></i>

                                        </a>
                                            <a href="#" className="trigger_remarks align-self-center">
                                                <i className="icon ico-review v-middle"   id={"remarksbutton_"+cellProps.rowIndex} onClick={()=>this.onOpenRemarks(cellProps.rowIndex,cellProps.rowData.remarks)}></i>
                                        </a>

                                            <IDropdown disabled={false} dropup={false}  className="d-inline-block">
                                                <IDropdownToggle className="dropdown-toggle btn-link no-pad more-toggle btn btn-secondary">
                                                    <a href="#" className="mar-l-2xs trigger_moveto align-self-center">
                                                <i className="icon ico-folder"></i>
                                        </a>
                                                </IDropdownToggle>
                                                <IDropdownMenu>
                                                    <IDropdownItem onClick={() => this.moveToActionIndividual(cellProps.rowIndex, cellProps.rowData.mailbagInvoicProcessingStatus, 'CLMZROPAY')}>Zero Pay (MSX) </IDropdownItem>
                                                    <IDropdownItem onClick={() => this.moveToActionIndividual(cellProps.rowIndex, cellProps.rowData.mailbagInvoicProcessingStatus, 'CLMNOTINV')}>Not in  INVOIC(NPR)</IDropdownItem>
                                                    <IDropdownItem onClick={() => this.moveToActionIndividual(cellProps.rowIndex, cellProps.rowData.mailbagInvoicProcessingStatus, 'CLMNOINC')}>Incentive Not Paid</IDropdownItem>
                                                    <IDropdownItem onClick={() => this.moveToActionIndividual(cellProps.rowIndex, cellProps.rowData.mailbagInvoicProcessingStatus, 'CLMRATDIF')}>Rate Difference(RVX)</IDropdownItem>
                                                    <IDropdownItem onClick={() => this.moveToActionIndividual(cellProps.rowIndex, cellProps.rowData.mailbagInvoicProcessingStatus, 'CLMWGTDIF')}>Weight Difference(WXX)</IDropdownItem>


                                                </IDropdownMenu>
                                            </IDropdown>
                                          </div>

                                    </Content>)
                                }
                            </RowCell>
                        </Cell>
                    </IColumn>





                </Columns>
            </ITable>
                {
                    this.state.openPopover &&
                    <IPopover isOpen={this.state.openPopover}  target={'remarksbutton_' + this.state.remarksTarget} toggle={this.onCloseRemarks} className="icpopover"> >
                <IPopoverHeader>

                            Remarks
                        </IPopoverHeader>
                        <IPopoverBody>
                            <div className="queue-item pad-sm">
                                <textarea className="textarea" defaultValue={this.state.remarksToDisplay ? this.state.remarksToDisplay : ''} id="remarks" style={{"width": "220px" }}>

                                </textarea>
                                <div >
                                </div>
                            </div>
                            <div className="pad-sm w-100 text-right common-border-color divider-top">
                                        <IButton category="primary" onClick={this.onOKRemarks} > <IMessage msgkey="mail.mra.invoicenquiry.button.done" /></IButton>
                                        <IButton category="default" onClick={this.onCloseRemarks}><IMessage msgkey="mail.mra.invoicenquiry.button.close" /></IButton>
                            </div>
                        </IPopoverBody>
                    </IPopover>
                }

            </Aux>

        );

    }
}
