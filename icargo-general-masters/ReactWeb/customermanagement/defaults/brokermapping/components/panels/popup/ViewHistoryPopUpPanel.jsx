import React, { Component } from "react";
import icpoup, {
  PopupFooter,
  PopupBody,
} from "icoreact/lib/ico/framework/component/common/modal";
import { Col,Row} from "reactstrap";
import {
  IButton,
  ITextField,
} from "icoreact/lib/ico/framework/html/elements";
import {
  IColumn,
  ITable,
  Columns,
  Cell,
  HeadCell,
  RowCell,
  Content
} from "icoreact/lib/ico/framework/component/common/grid";
import { wrapForm } from "icoreact/lib/ico/framework/component/common/form";
import { IMessage } from "icoreact/lib/ico/framework/html/elements";
import { isEmpty } from "icoreact/lib/ico/framework/component/util/util";
/**
 * This component is used for the View history Pop up
 */
class ViewHistoryPopup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      filteredData: [],
      searchValue: "",
      viewHistoryDetails:[]
    };
  }

  handleChange = (event) => {
    this.setState({ searchValue: event.target.value }, () => {
      this.globalSearch();
    });
  };

  globalSearch = () => {
    let searchValue = this.state.searchValue;
    let data = this.state.viewHistoryDetails;
    let filteredData = data.filter((value) => {
      return (
        value.transactionCode.includes(searchValue) ||
        value.userId.includes(searchValue) ||
        value.deletionDate.includes(searchValue) ||
        value.adlInfo.includes(searchValue) ||
        value.station.includes(searchValue) ||
        value.triggerPoint.includes(searchValue)
      );
    });
    this.setState({ filteredData });
  };
  componentDidMount(){
    this.setState({viewHistoryDetails:this.props.ViewHistoryDetails})
  }

  render() {
    const viewHistoryDetails = this.props.ViewHistoryDetails;

    const transactionCode=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.transaction" defaultMessage="Transaction"/>;
    const user=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.user" defaultMessage="User"/>;
    const dateTime=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.dateTime" defaultMessage="Date & Time"/>;
    const adlInfo=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.additionalInfo" defaultMessage="Additional Info"/>;
    const station=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.station" defaultMessage="Station"/>;
    const trigger=<IMessage msgkey="customermanagement.defaults.brokermapping.viewhistory.trigger" defaultMessage="Trigger"/>;
    return (
      <div>
        <PopupBody style={{ width: "90vw" }}>
          <Row className="no-mar highlight pad-md">
            <Col> </Col>
            <Col
              xs="6"
              md="6"
              lg="6"
              xl="6"
              className="audit-histoty-multiselect"
            >
              <ITextField
                name="searchText"
                placeholder="Enter the keywords to search"
                onChange={this.handleChange}
                value={this.state.searchValue}
              />
            </Col>
          </Row>
          <Row>
            <Col xs="24" md="24" lg="24" xl="24" className="m-t-15">
              <div class="card audithistory-table" style={{ height: "60vh" }}>
                <ITable
                  rowCount={
                    this.state.filteredData.length
                      ? this.state.filteredData.length
                      : viewHistoryDetails.length
                  }
                  headerHeight={35}
                  className="table-list"
                  gridClassName="table_grid"
                  headerClassName="table-head"
                  rowClassName="table-row"
                  data={
                    this.state.filteredData &&
                    (this.state.filteredData.length ||
                      !isEmpty(this.state.searchValue))
                      ? this.state.filteredData
                      : viewHistoryDetails
                  }
                  tableId="viewHistoryDetails"
                >
                  <Columns>
                    <IColumn
                      label={transactionCode}
                      dataKey="transactionCode"
                      width={50}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <div className="wrap-view">
                                {cellProps.cellData}
                              </div>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                    <IColumn
                      label={user}
                      dataKey="userId"
                      width={15}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <div className="wrap-view">
                                {cellProps.cellData}
                              </div>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                    <IColumn
                      label={dateTime}
                      dataKey="deletionDate"
                      width={50}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <div className="wrap-view">
                                {cellProps.cellData}
                              </div>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                    <IColumn
                      label={adlInfo}
                      dataKey="adlInfo"
                      width={320}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <div className="wrap-view">
                                {cellProps.cellData}
                              </div>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                    <IColumn
                      label={station}
                      dataKey="station"
                      width={10}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <em>{cellProps.cellData}</em>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                    <IColumn
                      label={trigger}
                      dataKey="triggerPoint"
                      width={15}
                      flexGrow={1}
                    >
                      <Cell>
                        <HeadCell>
                          {(cellProps) => <Content>{cellProps.label}</Content>}
                        </HeadCell>
                        <RowCell>
                          {(cellProps) => (
                            <Content>
                              <div className="wrap-view">
                                {cellProps.cellData}
                              </div>
                            </Content>
                          )}
                        </RowCell>
                      </Cell>
                    </IColumn>
                  </Columns>
                </ITable>
              </div>
            </Col>
          </Row>
        </PopupBody>
        <PopupFooter>
          <IButton category="secondary" onClick={()=>this.props.onClose()}>
            <IMessage msgkey="" defaultMessage="Close" />
          </IButton>
        </PopupFooter>
      </div>
    );
  }
}
const viewHistoryPopup = wrapForm("viewHistoryForm")(ViewHistoryPopup);
export default icpoup(viewHistoryPopup, {
  title: "View History",
  className: "modal_70w",
});
