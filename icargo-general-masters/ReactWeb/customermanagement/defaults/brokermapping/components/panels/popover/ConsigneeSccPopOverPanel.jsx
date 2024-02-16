import React,{Component}  from "react";
import {
    IPopover,
    IPopoverBody
  } from "icoreact/lib/ico/framework/component/common/popover";
export default class ConsigneeSccPopOverPanel extends Component{
  constructor(props){
    super(props);
    this.state ={
        showSccPanel: false
    };
  }
  
  toogleSccPanel=()=>{
    this.setState({
        showSccPanel: !this.state.showSccPanel
    });
  }
  render(){
    let SccRemaining = this.props.scc.length -3 
    let scc
    return(
      <>
        <span className="ml-2"
          id={`ConsigneeSccPanel-${this.props.rowIndex}`}
          onClick={this.toogleSccPanel}
        >
        <a href="#">+{SccRemaining}</a>
        </span>
        <IPopover
            isOpen={this.state.showSccPanel}
            target={`ConsigneeSccPanel-${this.props.rowIndex}`}
            toggle={this.toogleSccPanel}
            className="stn-popover"
            placement="right"
        >
            <IPopoverBody>
                <div className="overflow-auto origin_height">
                    <div className="d-flex flex-column px-3 py-3">
                       { (scc = this.props.scc).map((scc, index)  => {
                            return  <div className="d-flex mb-2" key={index}> <span>{scc}</span></div>
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
