import React,{Component}  from "react";
import {
    IPopover,
    IPopoverBody
  } from "icoreact/lib/ico/framework/component/common/popover";
export default class DestinationPopOverPanel extends Component{
  constructor(props){
    super(props);
    this.state ={
        showDestinationPanel: false
    };
  }
  
  toogleDestinationPanel=()=>{
    this.setState({
        showDestinationPanel: !this.state.showDestinationPanel
    });
  }
  render(){
    let destRemaining = this.props.destination.length -3 
    let scc
    return(
      <>
        <span class="ml-2"
          id={`destination-${this.props.rowIndex}`}
          onClick={this.toogleDestinationPanel}
        >
        <a href="#">+{destRemaining}</a>
        </span>
        <IPopover
            isOpen={this.state.showDestinationPanel}
            target={`destination-${this.props.rowIndex}`}
            toggle={this.toogleDestinationPanel}
            className="stn-popover"
            placement="bottom"
        >
            <IPopoverBody>
                  <div className="pad-md">
                    <div className="d-flex flex-column px-3 py-3">
                       { (this.props.destination).map((dest, index)  => {
                            return  <div className="d-flex mb-2" key={index}> <span>{dest}</span></div>
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
