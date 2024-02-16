import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';
import store from '../store/store.js';
import { Urls} from '../constants/constants.js' 

class MainPage extends React.Component {


    render() {
        return (
                <App></App>
        );
    }
}
export default initapp({store,screenId:Urls.SCREEN_ID,screenAction:Urls.SCREEN_ACTION,appConfig:config})(MainPage)