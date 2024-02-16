import React,{Component} from 'react';
import { IMessage,IButton} from 'icoreact/lib/ico/framework/html/elements';
export class FilterSummaryPanel extends Component{
    constructor(props)
    {
        super(props);
    }
    toggleScreenMode =()=>{
        this.props.toggleScreenMode();
    };
    render(){
        return (
          <div
            className="header-summary-panel flippane position-relative"
            id="headerData"
          >
            <div className="pad-md">
              <div className="row">
                <div className="col-4">
                  <label className="form-control-label">
                    <IMessage
                      msgkey="customermanagement.defaults.brokermapping.custcode"
                      defaultMessage="Customer Code"
                    />
                  </label>
                  <div className="form-control-data">
                    {this.props.filterDetails.customerCode.toUpperCase()}
                  </div>
                </div>
                {/*
                *List and Clear button is added and hidden for accessing keyboard shortcut(alt+C=Clear and alt+L=List) 
                *when screemode changes to "list"
                */}
                <IButton
                    accesskey="L"
                    className="hidden"
                    category="primary"
                    bType="LIST"
                    componentId="CMP_CUSTOMERMANAGEMENT_DEFAULTS_BROKER_MAPPING_LIST"
                    onClick={() => this.props.onList()}
                >
                    <IMessage msgkey="customermanagement.defaults.brokermapping.list" defaultMessage="New / List" />
                </IButton>
                <IButton
                    accesskey="C"
                    className="hidden"
                    category="default"
                    bType="CLEAR"
                    componentId=""
                    onClick={() => this.props.onClear()}
                >
                    <IMessage msgkey="customermanagement.defaults.brokermapping.clear" defaultMessage="Clear" />
                </IButton>
              </div>
            </div>
            <i
              className="icon ico-edit-sm-white flipper flipper-ico"
              flipper="headerForm"
              onClick={this.toggleScreenMode}
            />
          </div>
        );

    }
}