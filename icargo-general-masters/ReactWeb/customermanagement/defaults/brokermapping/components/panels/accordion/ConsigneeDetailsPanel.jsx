import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import React, { Component } from "react";
import { IButton,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import {
    IColumn,
    InfiniteLoaderTable,
    Columns,
    Cell,
    HeadCell,
    RowCell,
    Content
  } from "icoreact/lib/ico/framework/component/common/grid";
import {getGenericDetails,getLength,getDest} from "../../../utils/utils";
import ConsigneeDetailsFilterContainer from "../../containers/consigneedetailsfiltercontainer";
import NewConsigneeDetailsPopUpContainer from "../../containers/popup/newconsigneedetailspopupcontainer";
import DestinationPopOverPanel from "../popover/DestinationPopOverPanel.jsx";
import ConsigneeSccPopOverPanel from "../popover/ConsigneeSccPopOverPanel.jsx";
import ConsigneeOrginPopOverPanel from "../popover/ConsigneeOrginPopOverPanel.jsx";
import DeletionRemarksPopUpContainer from "../../containers/popup/deletionremarkspopupcontainer";
class ConsigneeDetailsPanel extends Component {
    constructor(props) {
      super(props);
      
    }

    onRowSelect = (data) => {
      let updatedSelectedConsigneeIndex=[];
      if (data.index > -1) {
        if (data.isRowSelected) {
          updatedSelectedConsigneeIndex=[...this.props.selectedConsigneeIndex,data.rowData];
        }else {
          updatedSelectedConsigneeIndex = this.props.selectedConsigneeIndex;
          let index = -1;
          for (let i = 0; i < updatedSelectedConsigneeIndex.length; i++) {
              var element = updatedSelectedConsigneeIndex[i].index;
              if (element === data.index) {
                  index = i;
                  break;
              }
          }
          if (index > -1) {
            updatedSelectedConsigneeIndex.splice(index, 1);
          }
        }
      }else {
        if ((data.event) && (data.event.target.checked)) {
          for (let i = 0; i < this.props.consigneeDetails.length; i++) {
            updatedSelectedConsigneeIndex.push(this.props.consigneeDetails[i]);
          }
        } else {
          updatedSelectedConsigneeIndex = [];
        }
      }
      this.props.saveSelectedConsigneeIndex(updatedSelectedConsigneeIndex);
    }

    sortList = (sortBy, sortOrder) => {
      this.props.sortedList(sortBy, sortOrder);
    };
    onApplyFilter = () => {
      this.props.applyConsigneeDetailsFilter();
    };
    render() {
      
      const poaType=<IMessage msgkey="customermanagement.defaults.brokermapping.poaType" defaultMessage="POA Type"/>;
      const consigneeCode=<IMessage msgkey="customermanagement.defaults.brokermapping.consigneecode" defaultMessage="Consignee Code"/>;
      // const consigneeName=<IMessage msgkey="customermanagement.defaults.brokermapping.consigneename" defaultMessage="Consignee Name"/>;
      const sccCode=<IMessage msgkey="customermanagement.defaults.brokermapping.sccCode" defaultMessage="SCC Code"/>;
      const origin=<IMessage msgkey="customermanagement.defaults.brokermapping.origin" defaultMessage="Origin"/>;
      const destination=<IMessage msgkey="customermanagement.defaults.brokermapping.destination" defaultMessage="Destination"/>;
      const station=<IMessage msgkey="customermanagement.defaults.brokermapping.station" defaultMessage="Station"/>;

      const CustomPanel = () => {
      return (
        <div className="d-flex justify-content-end card-header-action">
          <IButton
            category="primary"
            bType="LIST"
            componentId=""
            onClick={() => this.props.onAdd()}
          >
            <IMessage msgkey="customermanagement.defaults.brokermapping.add" defaultMessage="Add" />
            <NewConsigneeDetailsPopUpContainer
              show={this.props.showConsigneePopUp}
            />
          </IButton>
          <IButton
            className="btn btn-default"
            bType="LIST"
            componentId=""
            onClick={() => this.props.openRemarkPopup()}
            disabled={this.props.disableDeleteButton}
          >
            <IMessage msgkey="customermanagement.defaults.brokermapping.delete" defaultMessage="Delete" />
            <DeletionRemarksPopUpContainer
              show={this.props.showRemarksForConsignee}
              id="C"
            />
          </IButton>
        </div>
      );
      }
      return (
        <div className="d-flex" style={{ height: "300px" }}>
          <InfiniteLoaderTable
            rowCount={
              this.props.consigneeDetails
                ? this.props.consigneeDetails.lenght
                : 0
            }
            className="table-list"
            gridClassName="table_grid"
            rowClassName="table-row"
            headerHeight={40}
            rowHeight={65}
            data={this.props.consigneeDetails}
            onRowSelection={this.onRowSelect}
            fetchSize={10}
            fetchFn={() => {}}
            sortEnabled={false}
            tableId="consigneeMappingTable"
            customHeader={{
              customPanel: <CustomPanel />,
              filterConfig: {
                panel: <ConsigneeDetailsFilterContainer />,
                title: "Filter",
                onApplyFilter: this.onApplyFilter,
                onClearFilter: this.props.onClearFilter,
              },
              sortBy: {
                onSort: this.sortList,
                enableDefaultSortSelection: false,
              },
            }}
          >
            <Columns>
              //Check Box
              <IColumn dataKey="" className="fix-col" width={40}>
                <Cell>
                  <HeadCell disableSort selectOption>
                    {(cellProps) => <Content></Content>}
                  </HeadCell>
                  <RowCell selectOption></RowCell>
                </Cell>
              </IColumn>
              //POA Type
              <IColumn
                dataKey=""
                id="poaType"
                label={poaType}
                width={88}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {cellProps.rowData.poaType
                          ? cellProps.rowData.poaType
                          : " "}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //Broker Code
              <IColumn
                dataKey=""
                id=""
                label={consigneeCode}
                width={93}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {cellProps.rowData.agentCode
                          ? cellProps.rowData.agentCode
                          : " "}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //Broker Name
              <IColumn
                dataKey=""
                id="agentName"
                label="Consignee Name"
                sortByItem={true}
                width={88}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        <div className="wrap-view">
                          {cellProps.rowData.agentName
                            ? cellProps.rowData.agentName
                            : " "}
                        </div>
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //SSC Code
              <IColumn
                dataKey=""
                id="sscCode"
                label={sccCode}
                width={148}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {getGenericDetails(
                          cellProps.rowData.sccCodeExclude,
                          cellProps.rowData.sccCodeInclude,
                          true
                        )}
                        {getLength(
                          cellProps.rowData.sccCodeExclude,
                          cellProps.rowData.sccCodeInclude,
                          true
                        ) > 3 ? (
                          <ConsigneeSccPopOverPanel
                            scc={getGenericDetails(
                              cellProps.rowData.sccCodeExclude,
                              cellProps.rowData.sccCodeInclude,
                              false
                            )}
                            rowIndex={cellProps.rowIndex}
                          />
                        ) : (
                          ""
                        )}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //Orgin
              <IColumn
                dataKey=""
                id="orgin"
                label={origin}
                width={132}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {getGenericDetails(
                          cellProps.rowData.orginExclude,
                          cellProps.rowData.orginInclude,
                          true
                        )}
                        {getLength(
                          cellProps.rowData.orginExclude,
                          cellProps.rowData.orginInclude
                        ) > 3 ? (
                          <ConsigneeOrginPopOverPanel
                            orgin={getGenericDetails(
                              cellProps.rowData.orginExclude,
                              cellProps.rowData.orginInclude,
                              false
                            )}
                            rowIndex={cellProps.rowIndex}
                          />
                        ) : (
                          ""
                        )}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //Destination
              <IColumn
                dataKey=""
                id="destination"
                label={destination}
                width={107}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {cellProps.rowData.destination.length !== 0
                          ? getDest(cellProps.rowData.destination)
                          : ""}
                        {cellProps.rowData.destination.length > 3 ? (
                          <DestinationPopOverPanel
                            destination={cellProps.rowData.destination}
                            rowIndex={cellProps.rowIndex}
                          />
                        ) : (
                          ""
                        )}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
              //Station
              <IColumn
                dataKey=""
                id="station"
                label={station}
                width={65}
                flexGrow={1}
                hideOnExport={true}
              >
                <Cell>
                  <HeadCell disableSort>
                    {(cellProps) => <Content>{cellProps.label}</Content>}
                  </HeadCell>
                  <RowCell>
                    {(cellProps) => (
                      <Content>
                        {cellProps.rowData.station
                          ? cellProps.rowData.station
                          : " "}
                      </Content>
                    )}
                  </RowCell>
                </Cell>
              </IColumn>
            </Columns>
          </InfiniteLoaderTable>
        </div>
      );
    }
  }
  export default wrapForm("consigneeDetailsForm")(ConsigneeDetailsPanel)