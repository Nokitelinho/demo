import React, { Component } from "react";

import icpopup, {
    PopupFooter, PopupBody
} from 'icoreact/lib/ico/framework/component/common/modal';
import { IButton,ITextField,IMessage} from 'icoreact/lib/ico/framework/html/elements'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid';

import CustomConatiner from "../CustomPanel.jsx";
class AdditionalNamesPopUpPanel extends Component {
    constructor(props) {
        super(props);
        
    }
    onRowSelect = (data) => {
        let updatedAddNamIndex=[];
        if (data.index > -1) {
          if (data.isRowSelected) {
            updatedAddNamIndex=[...this.props.selectedAddNamIndex,data.rowData];
          }else {
            updatedAddNamIndex = this.props.selectedAddNamIndex;
            let index = -1;
            for (let i = 0; i < updatedAddNamIndex.length; i++) {
                var element = updatedAddNamIndex[i].index;
                if (element === data.index) {
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                updatedAddNamIndex.splice(index, 1);
            }
          }
        }else {
          if ((data.event) && (data.event.target.checked)) {
            for (let i = 0; i < this.props.awbDetails.length; i++) {
                updatedAddNamIndex.push(this.props.awbDetails[i]);
            }
          } else {
            updatedAddNamIndex = [];
          }
        }
        this.props.saveSelectedSinglePoaIndex(updatedAddNamIndex);
      }
      /**
       * function to be called when deleting a poa
       * updates the operation flag to I_D-->insert_deleted(if a new poa is inserted and deleted)
       * D-delete(delelted already exsisting poa)
       * 
       */
      onDelete = () => {
        this.props.selectedAddNamIndex.map((record) => {
          if (record.operationalFlag == "I") {
            record.operationalFlag = "I_D";
          } else if (record.operationalFlag == "N") {
            record.operationalFlag = "D";
          }
        });
        this.props.onDelete();
      };
    
    render() {
       
        return (
          <>
            <div className="d-flex flex-column">
              <PopupBody>
                <div className="pad-md">
                  <div className="row">
                    <div className="col-12">
                      <div className="form-group">
                        <label className="form-control-label">
                          <IMessage
                            msgkey="customermanagement.defaults.brokermapping.adlName"
                            defaultMessage="Additional Name"
                          />
                        </label>
                        <ITextField
                          className="form-control-label text-transform"
                          componentId=" "
                          // uppercase={true}
                          name="additionalName"
                          type="text"
                        />
                      </div>
                    </div>
                    <div className="col">
                      <div className="mar-t-md">
                        <IButton
                          category="primary"
                          bType=""
                          componentId=""
                          onClick={() => this.props.onAdd()}
                        >
                          <IMessage
                            msgkey="customermanagement.defaults.brokermapping.add"
                            defaultMessage="Add"
                          />
                        </IButton>
                      </div>
                    </div>
                  </div>
                </div>
                <div
                  className="inner-panel border-top"
                  style={{ height: "43vh", display: "flex" }}
                >
                  <ITable
                    rowCount={
                      this.props.additionalNames
                        ? this.props.additionalNames.length
                        : 0
                    }
                    headerHeight={40}
                    className="table-list"
                    gridClassName="table_grid"
                    rowHeight={35}
                    rowClassName="table-row"
                    sortEnabled={false}
                    onRowSelection={this.onRowSelect}
                    data={this.props.additionalNames}
                    customHeader={{
                      customPanel: (
                        <CustomConatiner
                          header="Additional Name List"
                          flag="A"
                        />
                      ),
                    }}
                  >
                    <Columns>
                      <IColumn dataKey="" className="fix-col" width={40}>
                        <Cell>
                          <HeadCell disableSort>
                            {(cellProps) => <Content></Content>}
                          </HeadCell>

                          <RowCell selectOption></RowCell>
                        </Cell>
                      </IColumn>
                      <IColumn
                        dataKey=""
                        id=""
                        label="Name List"
                        width={70}
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
                              <Content>
                                {cellProps.rowData.adlNam
                                  ? cellProps.rowData.adlNam
                                  : " "}
                              </Content>
                            )}
                          </RowCell>
                        </Cell>
                      </IColumn>
                    </Columns>
                  </ITable>
                </div>
              </PopupBody>
            </div>
            <PopupFooter>
              <IButton
                category="primary"
                bType=""
                componentId=""
                onClick={() => this.props.onOk()}
              >
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.ok"
                  defaultMessage="Ok"
                />
              </IButton>
              <IButton
                category="default"
                bType=""
                componentId=""
                onClick={this.onDelete}
              >
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.delete"
                  defaultMessage="Delete"
                />
              </IButton>
              <IButton
                category="default"
                bType=""
                componentId=""
                onClick={() => this.props.onClose()}
              >
                <IMessage
                  msgkey="customermanagement.defaults.brokermapping.close"
                  defaultMessage="Close"
                />
              </IButton>
            </PopupFooter>
          </>
        );
    }

}

const additionalNamesPopUpPanel = wrapForm('addnamesform')(AdditionalNamesPopUpPanel);
export default icpopup((additionalNamesPopUpPanel), { title: 'Additional Names', className: "modal_50w", });

