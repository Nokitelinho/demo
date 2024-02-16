import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {FlightCarrierFilterPanel} from '../panels/FlightCarrierFilterPanel.jsx'

const mapStateToProps = () => {
  return {
  
  }
}
const mapDispatchToProps = () => {
  return {
   
 
   

  }
}
const FlightCarrierFilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FlightCarrierFilterPanel)


export default FlightCarrierFilterContainer;