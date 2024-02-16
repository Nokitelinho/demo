import React, { Fragment, Component } from 'react';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { Row, FormGroup, Label } from "reactstrap";
import { IMessage, IButton ,ITextField} from 'icoreact/lib/ico/framework/html/elements';

import CustomerLovPopupContainer from "../containers/customerlovpopupcontainer.js"
import { FilterSummaryPanel } from './FilterSummaryPanel.jsx';
/**
 * ITextField UpperCase props is commented as a part of IASCB-177106
 */
class FilterPanel extends Component {
    constructor(props) {
        super(props);
    }
    openCustomerLov = () => {
        this.props.showCustomerPopup();
    }
    toggleScreenMode=()=>{
        this.props.onToggleScreenMode();
    }
    handleClear=()=>{
        this.props.reset();
        this.props.handleClear();
        //Added to keep the cursor on ItextField on clearing.
        this.props.focusCustomerCodeField(); 
    };
    onList=()=>{
      this.props.onList();
    };
    //Added to keep the cursor on ItextField during render
    componentDidMount(){
      this.props.focusCustomerCodeField(); 
    }
    render() {
        const {filterDetails} = this.props;
        return (
          <Fragment>
            {this.props.screenMode === "list" ? (
              <FilterSummaryPanel
                toggleScreenMode={this.toggleScreenMode}
                filterDetails={filterDetails}
                onClear={this.handleClear}
                onList={this.onList}
              />
            ) : (
              <div
                className="header-filter-panel flippane position-relative"
                id="headerForm"
              >
                {this.props.screenMode === "initial" ? (
                  ""
                ) : this.props.screenMode === "edit" ? (
                  <div edit-close-ico justify-content-center d-flex>
                    <i
                      className="icon ico-close-sm-white flipper flipper-ico"
                      flipper="headerData"
                      onClick={this.toggleScreenMode}
                    ></i>
                  </div>
                ) : (
                  ""
                )}
                <div className="pad-md pad-b-3xs">
                  <div className="row">
                    <div className="col-4">
                      <div className="form-group">
                        <label className="form-control-label">
                          <IMessage
                            msgkey="customermanagement.defaults.brokermapping.custcode"
                            defaultMessage="Customer Code"
                          />
                        </label>
                        <div className="input-group">
                          <ITextField
                            componentId=""
                            // uppercase={true}
                            className="text-transform"
                            name="customerCode"
                            type="text"
                          />
                          <div className="input-group-append">
                            <IButton
                              className="btn btn-icon "
                              category="default"
                              bType=""
                              componentId=""
                              onClick={this.openCustomerLov}
                            >
                              <i className="icon ico-expand"></i>
                            </IButton>
                            <CustomerLovPopupContainer
                              show={this.props.showCustomerLovPopupFlag}
                            />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div className="col">
                      <div className="mar-t-md">
                        <IButton
                          accesskey="L"
                          category="primary"
                          bType="LIST"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_LIST"
                          onClick={() => this.onList()}
                        >
                          <IMessage msgkey="customermanagement.defaults.brokermapping.list" defaultMessage="New / List" />
                        </IButton>
                        <IButton
                          accesskey="C"
                          category="default"
                          bType="CLEAR"
                          componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_CLEAR"
                          onClick={() => this.handleClear()}
                        >
                          <IMessage msgkey="customermanagement.defaults.brokermapping.clear" defaultMessage="Clear" />
                        </IButton>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </Fragment>
        );
    }
}
export default wrapForm('brokermappingform')(FilterPanel);
