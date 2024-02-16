import React,{Component}  from "react";
import {
  IPopoverHeader,
    IPopover,
    IPopoverBody
  } from "icoreact/lib/ico/framework/component/common/popover";

export default class AdditionalNamesPopOverPanel extends Component{
  constructor(props){
    super(props);
    this.state ={
        showAdditionalName: false
    };
  }

  toogleAdditionalName=()=>{
    this.setState({
        showAdditionalName: !this.state.showAdditionalName
    });
  }
  render(){
    let nameRemaining = this.props.additionalNames.length -1
    return(
      <>
        <span class="ml-2"
          id={`additionalName-${this.props.rowIndex}`}
          onClick={this.toogleAdditionalName}
        >
        <a href="#">+{nameRemaining}</a>
        </span>
        <IPopover
            isOpen={this.state.showAdditionalName}
            target={`additionalName-${this.props.rowIndex}`}
            toggle={this.toogleAdditionalName}
            className="stn-popover"
            placement="right"
        >
           <IPopoverHeader>
                    <h4>Additional Names</h4>
                </IPopoverHeader>
            <IPopoverBody>
                  <div className="pad-y-md">
                 
                  <div className="overflow-auto origin_height pad-x-md">
              <div className="d-flex flex-column px-3 py-3">
                            {(this.props.additionalNames).map((additionalName, index)  => {
                                    return (<div className="d-flex mb-2" key={index}> <span>{additionalName}</span></div>)
                                  
                                })
                            }
                        </div>
                  </div>
                  </div>
            </IPopoverBody>
        </IPopover>
      </>
    );
     }
}