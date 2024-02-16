import React, { PureComponent, Fragment } from 'react';
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content }
  from 'icoreact/lib/ico/framework/component/common/grid';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import BillingPeriodPanel from '../panels/custompanel/BillingPeriodPanel.jsx';
import AddParameterPopup from '../panels/popup/AddParameterPopup.jsx';
import { DatePicker } from 'icoreact/lib/ico/framework/component/common/date';
import PropTypes from 'prop-types';
import { ITextField } from 'icoreact/lib/ico/framework/html/elements';
class BillingDetailsTable extends PureComponent {
  constructor(props) {
    super(props);
    this.selectedBillingData = [];
    this.selectedBillingDataIndex = [];
    this.state = {
      rowClkCount: 0,
      showPopup: false,
      paramterpopUpIndex: ' '
    }
  }
  onSelect = (value) => {
    this.setState({ paramterpopUpIndex: value });
    this.props.onAddParameter(value);
  }
  getParameterValue = (index) => {
    let parameterMap = this.props.parameterMap;
    let parameterValues = parameterMap.get(index);

    if (this.props.billingScheduleData[index].parameterList) {
      if (this.props.billingScheduleData[index].parameterList[0].paramterCode && this.props.billingScheduleData[index].parameterList[0].paramterCode == "CNTCOD" && this.props.billingScheduleData[index].parameterList[1] && this.props.billingScheduleData[index].parameterList[1].paramterCode == "GPACOD") {

        return (
          <div>
            <div>{`country (${this.props.billingScheduleData[index].parameterList[0].excludeFlag})${this.props.billingScheduleData[index].parameterList[0].parameterValue}`}</div>
            <div>{`GPA Code (${this.props.billingScheduleData[index].parameterList[1].excludeFlag})${this.props.billingScheduleData[index].parameterList[1].parameterValue}`}</div>
          </div>
        )
      }
      else if (this.props.billingScheduleData[index].parameterList[0].paramterCode && this.props.billingScheduleData[index].parameterList[0].paramterCode == "CNTCOD") {
        return (
          <div>{`country (${this.props.billingScheduleData[index].parameterList[0].excludeFlag})${this.props.billingScheduleData[index].parameterList[0].parameterValue}`}</div>
        )
      }
      else if (this.props.billingScheduleData[index].parameterList[0].paramterCode && this.props.billingScheduleData[index].parameterList[0].paramterCode == "GPACOD") {
        return (
          <div>{`GPA Code (${this.props.billingScheduleData[index].parameterList[0].excludeFlag})${this.props.billingScheduleData[index].parameterList[0].parameterValue}`}</div>
        )
      }





    } else if (parameterValues) {
      return (
        <div>{parameterValues[0] && parameterValues[0].parameterCode === "CNTCOD" ? <div>{`Country (${parameterValues[0].includeExcludeFlag ? parameterValues[0].includeExcludeFlag : ' '}) ${parameterValues[0].parameterValue ? parameterValues[0].parameterValue : ' '}`}</div> : ''}
          {parameterValues[0] && parameterValues[0].parameterCode === "GPACOD" ? <div>{`GPA Code (${parameterValues[0].includeExcludeFlag ? parameterValues[0].includeExcludeFlag : ' '}) ${parameterValues[0].parameterValue ? parameterValues[0].parameterValue : ' '}`}</div> : ''}
          {parameterValues[1] ? <div>{`GPA Code (${parameterValues[1].includeExcludeFlag ? parameterValues[1].includeExcludeFlag : ' '}) ${parameterValues[1].parameterValue ? parameterValues[1].parameterValue : ''}`}</div> : ''}
        </div>
      )
    }
    else {
      return (<a href="#" onClick={() => this.onSelect(index)}>Add</a>)
    }
  }
  onParamterOK = () => {
    this.props.onParamterOK(this.state.paramterpopUpIndex);
  }
  deleteRow = () => {
    this.props.deleteRow(this.props.selectedBillingDataIndex)
  }
  onBlurFromDate=(index) => {
    this.props.generatePeriodNumber(index);
  }
  selectBillingDetails = (billingData) => {
    this.selectedBillingData.push(billingData);
    this.props.selectBillingDetails(this.selectedBillingData);
  }
  unSelectBillingDetails = (billingData) => {
    let index = -1;
    for (let i = 0; i < this.selectedBillingData.length; i++) {
      var element = this.selectedBillingData[i];
      if (element.id === billingData.id) {
        index = i;
        break;
      }
    }
    if (index > -1) {
      this.selectedBillingData.splice(index, 1);
    }
    this.props.selectBillingDetails(this.selectedBillingData);
  }
  add = () => {
    this.props.onAddParameter
  };
  selectAllBillingData = (data) => {
    this.selectedBillingData = data
    this.props.selectBillingDetails(this.selectedBillingData);
  }

  unSelectAllBillingDetails = () => {
    this.selectedBillingData = []
    this.props.selectBillingDetails(this.selectedBillingData);
  }
  onSelectmultiple = (data) => {
    let count = this.state.rowClkCount;
    if (data.index > -1) {
      if (data.isRowSelected === true) {
        count = ++count
        this.selectBillingDetails(data.rowData);
        this.props.saveSelectedBillingIndex(data.index, 'SELECT');
      } else {
        count = --count
        this.unSelectBillingDetails(data.rowData);
        this.props.saveSelectedBillingIndex(data.index, 'UNSELECT');
      }
    } else {
      if (data.selectedIndexes) {
        //this.props.getTotalCount();
        this.selectedBillingDataIndex = this.props.selectedBillingDataIndex;
        this.selectAllBillingData(this.props.filterValues);
        this.props.saveSelectedMultipleBillingIndex(data.selectedIndexes,'SELECT');

      } else {
        count = 0
        this.unSelectAllBillingDetails();
        this.props.saveSelectedBillingIndex(data.index, 'UNSELECT');
      }
    }
    this.setState({
      rowClkCount: count
    })

  }

  render() {
    const results = this.props.billingScheduleData ? this.props.billingScheduleData : [];
    const rowCount = results.length;
    return (
      <Fragment>
          <ITable
            customHeader={{
              headerClass: 'highlight no-pad no-mar',
              customPanel: <BillingPeriodPanel addRow={this.props.addRow} deleteRow={this.deleteRow} newRowData={this.props.newRowData} lastRowData={this.props.lastRowData}
                getLastRowData={this.props.getLastRowData} selectedBillingDataIndex={this.props.selectedBillingDataIndex} />,
                "pagination": {
              "page": this.props.billingScheduleData && this.props.billingScheduleData.length > 0 && this.props.billingSchedulePage && this.props.billingSchedulePage.totalRecordCount ? this.props.billingSchedulePage : null, getPage: this.props.onlistdetails,

                options: [{ value: '10', label: '10' }, { value: '20', label: '20' }, { value: '30', label: '30' }, { value: '40', label: '40' }, { value: '100', label: '100' }]
              } ,
            pageable: true,
            }}
            rowCount={rowCount}
            headerHeight={35}
            className="table-list"
            gridClassName="table_grid"
            headerClassName="table-head"
            rowHeight={35}
            rowClassName='table-row'
            data={results}
            tableId="BillingDetailsTable"
            name="BillingDetailsTable"
            destroyFormOnUnmount={true}
            form={true}
            resetSelectionOnDataChange={true}
            enableFixedRowScroll
            hideTopRightGridScrollbar
            hideBottomLeftGridScrollbar
            onRowSelection={this.onSelectmultiple}
          >
            <Columns >
              <IColumn
                width={35}
                dataKey=""
                flexGrow={0}
                className="align-items-center first-column" hideOnExport>
                <Cell>
                  <HeadCell disableSort selectOption>
                  </HeadCell>
                  <RowCell selectOption>
                  </RowCell>
                </Cell>
              </IColumn>

              <IColumn
                label='periodNumber'
                dataKey='periodNumber'
                width={35} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Period No:</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => {
                      return (
                        <Content><ITextField componentId="TXT_MAIL_MRA_UX_BILLING_PERIOD_NO" name={`${cellProps.rowIndex}.periodNumber`} id={`periodNumber${cellProps.rowIndex}`} type="text" value="abc" data-rowIndex={cellProps.rowIndex} disabled='yes' /></Content>)
                    }
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='billingPeriodFromDate'
                dataKey='billingPeriodFromDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Billing Period From</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.billingPeriodFromDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true} />
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='billingPeriodToDate'
                dataKey='billingPeriodToDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Billing Period To</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.billingPeriodToDate`} value={cellProps.cellData} onFlightDateBlur={()=>this.onBlurFromDate(cellProps.rowIndex)} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='gpaInvoiceGenarateDate'
                dataKey='gpaInvoiceGenarateDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>GPA Invoice Generation</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.gpaInvoiceGenarateDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='passFileGenerateDate'
                dataKey='passFileGenerateDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Pass File Generation </Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.passFileGenerateDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='masterCutDataDate'
                dataKey='masterCutDataDate'
                width={55} flexGrow={1}
                className="d-flex justify-content-center">
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>
                        <div className="d-flex justify-content-center w-100">
                          Parameters
                        </div>
                      </Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <div className="d-flex align-items-center justify-content-center">
                          {
                            this.getParameterValue(cellProps.rowIndex)
                          }
                        </div>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='masterCutDataDate'
                dataKey='masterCutDataDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Master Data Cut-off for Processing</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.masterCutDataDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='airLineUploadCutDate'
                dataKey='airLineUploadCutDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Airline Input Upload Cut-off</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.airLineUploadCutDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='invoiceAvailableDate'
                dataKey='invoiceAvailableDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Invoice Available</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.invoiceAvailableDate`} value={cellProps.cellData} disabled={cellProps.rowData.__opFlag==="I" ? false : true}/>
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
              <IColumn
                label='postalOperatorUploadDate'
                dataKey='postalOperatorUploadDate'
                width={55} flexGrow={1} >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => (
                      <Content>Postal Operator Reconciliation Upload Cut-off</Content>)
                    }
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <DatePicker popperPlacement='auto' popperContainer={document.getElementById('calendar-portal')} name={`${cellProps.rowIndex}.postalOperatorUploadDate`} value={cellProps.cellData} popperPlacement='left' disabled={cellProps.rowData.__opFlag==="I" ? false : true} />
                      </Content>)
                    }
                  </RowCell>
                </Cell>
              </IColumn>
            </Columns >
          </ITable>
          <AddParameterPopup 
          show={this.props.showAddPopup}
          toggleFn={this.props.onCloseParameter}
          saveParameter={this.props.saveParameter}
          parameterExists={this.props.parameterExists}
          oneTimeMap={this.props.oneTimeMap} initialValues={this.props.initialValues} />
      </Fragment>
     

    )


  }
}
export default wrapForm('BillingDetailsTable')(BillingDetailsTable);
BillingDetailsTable.propTypes = {
  onChangeScreenMode: PropTypes.func,
  submitFilter: PropTypes.func,
  clearFilter: PropTypes.func,
  screenMode: PropTypes.string,

}