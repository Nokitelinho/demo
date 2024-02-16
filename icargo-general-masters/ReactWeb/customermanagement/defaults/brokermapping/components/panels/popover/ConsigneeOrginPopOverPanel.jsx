import React,{Component}  from "react";
import {
    IPopover,
    IPopoverBody
  } from "icoreact/lib/ico/framework/component/common/popover";
export default class ConsigneeOrginPopOverPanel extends Component{
  constructor(props){
    super(props);
    this.state ={
        showOrginPanel: false
    };
  }
  
  toogleOrginPanel=()=>{
    this.setState({
        showOrginPanel: !this.state.showOrginPanel
    });
  }
  render(){
    let OrginRemaining = this.props.orgin.length -3 
    return(
      <>
        <span className="ml-2"
          id={`ConsigneeOrginPanel-${this.props.rowIndex}`}
          onClick={this.toogleOrginPanel}
        >
        <a href="#">+{OrginRemaining}</a>
        </span>
        <IPopover
            isOpen={this.state.showOrginPanel}
            target={`ConsigneeOrginPanel-${this.props.rowIndex}`}
            toggle={this.toogleOrginPanel}
            className="stn-popover"
            placement="right"
        >
            <IPopoverBody>
                  <div className="overflow-auto origin_height">
                    <div className="d-flex flex-column px-3 py-3">
                       { (this.props.orgin).map((orgin, index)  => {
                            return  <div className="d-flex mb-2" key={index}> <span>{orgin}</span></div>
                        })
                       }
                    </div>
                  </div>
            </IPopoverBody>
        </IPopover>
      </>
    );
  };
};
