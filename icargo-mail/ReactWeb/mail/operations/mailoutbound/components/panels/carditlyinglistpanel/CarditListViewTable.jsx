import React, { Component, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import CarditFilterPanel from './CarditFilterPanel.jsx'
import { IDropdown, IDropdownToggle, IDropdownItem, IDropdownMenu } from 'icoreact/lib/ico/framework/component/common/dropdown';
import PropTypes from 'prop-types';
import {IToolTip} from 'icoreact/lib/ico/framework/component/common/tooltip';

export default class CarditListViewTable extends Component {
    constructor(props) {
        super(props);
        this.selectedMailbags = [];
        this.state = {

            rowClkCount: 0,

        }
    }

    onRowSelection = (data) => {
        if(data.selectedIndexes) {
          this.props.selectCarditMailbags(data.selectedIndexes);
        }
  
    }

    gridUtitity = (event) => {
        event.stopPropagation();
        event.nativeEvent.stopImmediatePropagation();
    }

    carditAction = (event) => {
        let actionName = event.target.dataset.mode
        let index = event.target.dataset.index
        if (actionName === 'VIEW_CONDOC') {
          this.props.openViewConsignmentPopup({index: index });
        }
    }
    onApplyCarditFilter=()=> {
        this.selectedMailbags = [];//Added for ICRD-342293
        this.props.onApplyCarditFilter(this.props.carditFilter.values);
    }
    render() {
        const rowCount = (this.props.carditMailbags) ? this.props.carditMailbags.results.length : 0
        return (
            <Fragment>
                <ITable
                    rowCount={rowCount}
                    headerHeight={35}
                    className="table-list"
                    gridClassName=""
                    headerClassName=""
                    rowHeight={70}
                    rowClassName="table-row"
                    tableId="carditlisttable"
                    customHeader={{
                        placement:'dynamic',
                        "pagination": { "page": this.props.carditMailbags, getPage: this.props.getNewPage, "mode": 'subminimal' },
                        filterConfig: {
                            panel: <CarditFilterPanel carditFilterFormValues={this.props.carditFilter}oneTimeValues={this.props.oneTimeValues} tablefilterValues={this.props.tablefilterValues} initialValues={this.props.initialValues} />,
                            title: 'Filter',
                            onApplyFilter: this.onApplyCarditFilter,
                            onClearFilter: this.props.onClearCarditFilter
                        },
                       // customPanel: <CarditSummary carditSummary={this.props.carditSummary} />
                    }}
                    data={this.props.carditMailbags ? this.props.carditMailbags.results:[]}
                    onRowSelection={this.onRowSelection}>
                    <Columns>
                        <IColumn
                           // width={32}
                            flexGrow={0}
                            dataKey="">
                            <Cell>
                                <HeadCell disableSort selectOption>
                                </HeadCell>
                                <RowCell selectOption>
                                </RowCell>
                            </Cell>
                        </IColumn>
                        <IColumn
                            dataKey=""
                            label=""
                            flexGrow={1}
                            id="ooe"
                            width={100}
                            selectColumn={false}>
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (<Content>
                                        {<div className="pad-b-3xs">{cellProps.rowData.mailbagId}</div>}
                                        {<div className="row">
                                            <div className="col">
                                                <div className="text-light-grey pad-b-3xs">{cellProps.rowData.consignmentDate}</div>
                                                <div>{cellProps.rowData.mailorigin ? cellProps.rowData.mailorigin:''} - {cellProps.rowData.mailDestination ? cellProps.rowData.mailDestination:''}</div>
                                            </div>
                                            <div className="col">
                                                <div className="pad-b-3xs">{cellProps.rowData.weight ? cellProps.rowData.weight.roundedDisplayValue:''} {''} {cellProps.rowData.weight ? cellProps.rowData.weight.unitCodeForDisplay:''}</div>
                                                <div>{cellProps.rowData.consignmentNumber}</div>
                                            </div>
                                            <div className="col-auto p-0">
                                                {((cellProps.rowData.latestStatus!='NEW'&&cellProps.rowData.accepted!='E') 
                                                ||cellProps.rowData.accepted === 'Y') ? <i className="icon ico-ok-green" id= {'accepted'+cellProps.rowIndex}></i>  : (cellProps.rowData.accepted === 'E' ? <i className="icon ico-error-sm"></i> : <i className="icon ico-ok-dark" id= {'notAccepted'+cellProps.rowIndex}></i>)}
                                                <IToolTip value={'Accepted'} target={'accepted'+cellProps.rowIndex} placement='bottom'/>
                                                <IToolTip value={'Not Accepted'} target={'notAccepted'+cellProps.rowIndex} placement='bottom'/>   
                                            </div>
                                            <div className="col-auto">
                                                <span onClick={this.gridUtitity}>
                                                    <IDropdown>
                                                        <IDropdownToggle className="dropdown-toggle more-toggle">
                                                            <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                        </IDropdownToggle>
                                                        <IDropdownMenu right={true}>
                                                            <IDropdownItem data-mode="VIEW_CONDOC" privilegeCheck={true} componentId="CMP_MAIL_OPERATIONS_OUTBOUND_VIEW_CONDOC" data-index={cellProps.rowIndex} onClick={this.carditAction}> View Consignment Document </IDropdownItem>
                                                        </IDropdownMenu>
                                                    </IDropdown>
                                                </span>
                                            </div>
                                        </div>}
                                    </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn>
                        {/* <IColumn
                            dataKey="accepted"
                            label=""
                            width={20}
                            flexGrow={0}
                            id="accepted">
                            <Cell>
                                <HeadCell disableSort>
                                    {(cellProps) => (
                                        <Content>{cellProps.label}</Content>)
                                    }
                                </HeadCell>
                                <RowCell>
                                    {(cellProps) => (
                                        <Content>
                                            {cellProps.rowData.accepted === 'Y' ? <i className="icon ico-ok-green"></i> : <i className="icon ico-ok-dark"></i>}
                                        </Content>)
                                    }
                                </RowCell>
                            </Cell>
                        </IColumn> */}
                        {/* <IColumn
                                width={40}
                                flexGrow={0}
                                hideOnExport
                                selectColumn={false}>
                                <Cell>
                                    <HeadCell disableSort>
                                        {() => (
                                            <Content></Content>)
                                        }
                                    </HeadCell>
                                    <RowCell>
                                        {(cellProps) => (
                                            <Content>
                                               
                                                    <span onClick={this.gridUtitity}>

                                                        <IDropdown className="drop-icon-setwidth">
                                                            <IDropdownToggle className="dropdown-toggle more-toggle">
                                                        <a href="#"><i className="icon ico-v-ellipsis"></i></a>
                                                    </IDropdownToggle>
                                                    <IDropdownMenu right={true}>
                                                        <IDropdownItem data-mode="VIEW_CONDOC" data-index={cellProps.rowIndex} onClick={this.carditAction}> View Consignment Document </IDropdownItem>
                                                    </IDropdownMenu>
                                                        </IDropdown>
                                                    </span>
                                            </Content>)
                                        }
                                    </RowCell>
                                </Cell>
                            </IColumn> */}
                    </Columns>
                </ITable>
            </Fragment>
        );
    }
}
CarditListViewTable.propTypes={
    selectCarditMailbags: PropTypes.func,
    openViewConsignmentPopup:PropTypes.func,
    onApplyCarditFilter:PropTypes.func,
    carditMailbags: PropTypes.object,
    carditFilter:PropTypes.object,
    onClearCarditFilter: PropTypes.func,
    oneTimeValues:PropTypes.object,
    initialValues:PropTypes.object,
    tablefilterValues:PropTypes.object,
    getNewPage:PropTypes.func,
}