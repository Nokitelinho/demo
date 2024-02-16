import 'icoreact/lib/ico/framework/lang/polyfill';
import 'icoreact/lib/ico/framework/assets/styles/bootstrap.min.css'
import 'icoreact/lib/ico/framework/assets/styles/scss/main.scss'
import './styles/scss/customer_console.scss'
import MainPage from './main/Main.jsx'
import Gateway from 'icoreact/lib/ico/framework/component/common/app/bridge/gateway';

export default Gateway(MainPage);








