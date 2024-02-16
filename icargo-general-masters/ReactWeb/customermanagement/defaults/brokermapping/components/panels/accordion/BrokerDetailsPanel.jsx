import React, { Component } from "react";
import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
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
import {getGenericDetails,getLength,getDest} from "../../../utils/utils"
import NewBrokerDetailsPopUpContainer from "../../containers/popup/newbrokerdetailspopupcontainer";
import SccPopOverPanel from "../popover/SccPopOverPanel.jsx";
import OrginPopOverPanel from "../popover/OrginPopOverPanel.jsx";
import DestinationPopOverPanel from "../popover/DestinationPopOverPanel.jsx";
import BrokerDetailsFilterContainer from "../../containers/brokerdetailsfiltercontainer";
import DeletionRemarksPopUpContainer from "../../containers/popup/deletionremarkspopupcontainer";
import FetchAdditionalDetailsContainer from "../../containers/fetchadditionaldetailscontainer";
export default class BrokerDetailsPanel extends Component {
    constructor(props) {
      super(props);
      
    }

    sortList = (sortBy, sortOrder) => {
      this.props.sortedList(sortBy, sortOrder);
    };
    /**
     * function to select and unselect a row
     * @param {*} data 
     */
    onRowSelect = (data) => {
      let updatedSelectedIndex=[];
      if (data.index > -1) {
        if (data.isRowSelected) {
            updatedSelectedIndex=[...this.props.selectedIndex,data.rowData];
        }else {
          updatedSelectedIndex = this.props.selectedIndex;
          let index = -1;
          for (let i = 0; i < updatedSelectedIndex.length; i++) {
              var element = updatedSelectedIndex[i].index;
              if (element === data.index) {
                  index = i;
                  break;
              }
          }
          if (index > -1) {
              updatedSelectedIndex.splice(index, 1);
          }
        }
      }else {
        if ((data.event) && (data.event.target.checked)) {
          for (let i = 0; i < this.props.brokerDetails.length; i++) {
              updatedSelectedIndex.push(this.props.brokerDetails[i]);
          }
        } else {
          updatedSelectedIndex = [];
        }
      }
      this.props.saveSelectedIndex(updatedSelectedIndex);
    }
    
    onApplyFilter = () => {
      this.props.applyBrokerDetailsFilter();
    };
  
    render() {

      const poaType=<IMessage msgkey="customermanagement.defaults.brokermapping.poaType" defaultMessage="POA Type"/>;
      const brokerCode=<IMessage msgkey="customermanagement.defaults.brokermapping.brokercode" defaultMessage="Broker Code"/>;
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
              <NewBrokerDetailsPopUpContainer show={this.props.showBroker} />
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
                show={this.props.showDeleteRemarks}
                id="B"
              />
            </IButton>
          </div>
        );
        }
        
      return (
        <div className="d-flex" style={{height:"300px"}}>
            <InfiniteLoaderTable 
                rowCount={
                  this.props.brokerDetails?this.props.brokerDetails.length:0
                }
                className="table-list"
                gridClassName="table_grid"
                rowClassName="table-row"
                headerHeight={40}
                rowHeight={40}
                onRowSelection={this.onRowSelect}
                data={this.props.brokerDetails}
                fetchSize={5}
                fetchFn={() => {}}
                sortEnabled={false}
                tableId="brokerMappingTable"
                customHeader={{
                  customPanel: <CustomPanel/>,
                  filterConfig: {
                    panel: <BrokerDetailsFilterContainer/>,
                    title: "Filter",
                    onApplyFilter: this.onApplyFilter,
                    onClearFilter: this.props.onClearFilter
                  },
                  sortBy: {
                    onSort: this.sortList,
                    enableDefaultSortSelection:false
                  }
                }}
            >
              <Columns>
                //Check Box
                  <IColumn
                    dataKey=""
                    className="fix-col"
                    width={40}
                    // flexGrow={1}
                  >
                    <Cell>
                      <HeadCell disableSort selectOption>
                        {cellProps => <Content></Content>}
                      </HeadCell>
                      <RowCell selectOption>
                      
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //POA Type
                  <IColumn
                    dataKey=""
                      id=""
                    label={poaType}
                    width={88}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                              {
                                cellProps.rowData.poaType?cellProps.rowData.poaType:" "
                              }
                            </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //Broker Code
                  <IColumn
                    dataKey=""
                    id=""
                    label={brokerCode}
                    width={93}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                              {
                                cellProps.rowData.agentCode?cellProps.rowData.agentCode:" "
                              }
                            </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //Broker Name
                  <IColumn
                    dataKey=""
                    id="agentName"
                    label="Broker Name"
                    width={88}
                    flexGrow={1}
                    sortByItem={true}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                              <div className='wrap-view d-flex align-items-center'>
                                <div>{cellProps.rowData.agentName?cellProps.rowData.agentName:" "}</div>
                                {<FetchAdditionalDetailsContainer 
                                  rowIndex={cellProps.rowIndex} 
                                  customerCode={cellProps.rowData.agentCode}
                                  operationFlag={cellProps.rowData.operatonalFlag}
                                  />}
                              </div>
                            </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //SSC Code
                  <IColumn
                    dataKey=""
                    id=""
                    label={sccCode}
                    width={148}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                                {
                                  getGenericDetails(cellProps.rowData.sccCodeExclude,cellProps.rowData.sccCodeInclude,true)
                                }
                                {
                                  (getLength(cellProps.rowData.sccCodeExclude,cellProps.rowData.sccCodeInclude,true)>3)?
                                  <SccPopOverPanel
                                  scc ={getGenericDetails(cellProps.rowData.sccCodeExclude,cellProps.rowData.sccCodeInclude,false)}
                                  rowIndex= {cellProps.rowIndex}/>:""
                                }
                             </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  
                  //Orgin
                  <IColumn
                    dataKey=""
                    id=""
                    label={origin}
                    width={132}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                              <div className="d-flex align-items-center">
                                
                                  {
                                    getGenericDetails(cellProps.rowData.orginExclude,cellProps.rowData.orginInclude,true)
                                  }
                                  {
                                  (getLength(cellProps.rowData.orginExclude,cellProps.rowData.orginInclude)>3)?
                                  <OrginPopOverPanel
                                  orgin ={getGenericDetails(cellProps.rowData.orginExclude,cellProps.rowData.orginInclude,false)}
                                  rowIndex= {cellProps.rowIndex}/>:""
                                }
                              </div>
                            </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //Destination
                  <IColumn
                    dataKey=""
                    id=""
                    label={destination}
                    width={107}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                                {
                                  cellProps.rowData.destination.length!==0?getDest(cellProps.rowData.destination):""
                                }
                                {
                                  cellProps.rowData.destination.length>3?
                                  <DestinationPopOverPanel 
                                    destination={cellProps.rowData.destination}
                                    rowIndex={cellProps.rowIndex}
                                  />:""
                                }
                             </Content>
                          )}
                      </RowCell>
                    </Cell>
                  </IColumn>
                  //Station
                  <IColumn
                    dataKey=""
                    id=""
                    label={station}
                    width={65}
                    flexGrow={1}
                  >
                    <Cell>
                      <HeadCell  disableSort>
                        {cellProps => <Content>{cellProps.label}</Content>}
                      </HeadCell>
                      <RowCell>
                        {cellProps => (
                            <Content>
                                {
                                  cellProps.rowData.station?cellProps.rowData.station:" "
                                }
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