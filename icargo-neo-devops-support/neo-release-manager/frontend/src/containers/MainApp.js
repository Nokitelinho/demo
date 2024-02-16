import React,{ useState,useEffect } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { BrowserRouter as Router,Route,Switch} from 'react-router-dom'
import RegisterApplication from './RegisterApplication';
import CompositeMenu from '../components/CompositeMenu';
import menu from './MenuDef';
import ReleasePackage from './ReleasePackage';
import BuildList from './BuildList';
import ReleasePackageList from './ReleasePackageList';
import ApplicationList from './ApplicationList';
import ArtefactList from './ArtefactList';
import RegisterArtefact from './RegisterArtefact';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import { useRecoilState } from 'recoil';
import tenantState from '../store/TenantAtom';
import { getFromBEnd,asWebPath } from '../components/Common'; 
import IconButton from '@material-ui/core/IconButton';
import HomeIcon from '@material-ui/icons/Home';
import { useHistory } from "react-router-dom";
import ReleaseCatalogueList from './ReleaseCatalogueList';
import CloneABuild from './CloneABuild';
import MaintainTenants from './MaintainTenants';


const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    marginLeft: theme.spacing(2),
    flexGrow: 1
  },
}));


const MenuBar = (props)=>{
  const classes = useStyles();
  const [tenantList, setTenantList] = useState([]);
  const [tenant, setTenant] = useRecoilState(tenantState);
 

  useEffect(() =>{
    getFromBEnd("/tenants")
      .then(list=>setTenantList(list))      
  },[]);

  const onTenantChange = (event) => {
    const tenant = event.target.value;
    setTenant({value:tenant});
  }

  const history = useHistory();
  return(
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
     
        <IconButton size="medium"  onClick={()=>history.push('/')} >
           <HomeIcon/>
        </IconButton>

        {menu.map((item, index) => (
          <CompositeMenu
            key={item.name}
            menuName={item.name}
            menuItems={item.items}
            edge="start" className={classes.menuButton} color="inherit" aria-label="menu"/>
        ))}


          <Typography variant="h6" className={classes.title}>
            Release Manager v0.1
          </Typography>
          <TextField id="tenant" select label="Pick Tenant" value={tenant.value}
                onChange={onTenantChange}
                style={{width:'15ch'}} >
                {tenantList?tenantList.map((t,idx) => (
                    <MenuItem key={idx} value={t.tenantId}>
                        {t.tenantId}
                    </MenuItem>
                  )):''}
          </TextField>         

        </Toolbar>
      </AppBar>
      <Switch>
          <Route exact path={`${asWebPath('/maintain-app/:applicationId?')}`} component={RegisterApplication}/>
          <Route exact path={`${asWebPath('/list-app')}`} component={ApplicationList}/>
           <Route exact path={`${asWebPath("/maintain-relp/:buildNum/:applicationId/:packageId?")}`} component={ReleasePackage}/>
           <Route exact path={`${asWebPath("/list-build")}`} component={BuildList}/>
           <Route exact path={`${asWebPath("/list-relp")}`} component={ReleasePackageList}/>
           <Route exact path={`${asWebPath("/list-artfs")}`} component={ArtefactList}/>
           <Route exact path={`${asWebPath("/list-artf-releases")}`} component={ReleaseCatalogueList}/>

           <Route exact path={`${asWebPath("/maintain-artefact/:artefactId?")}`} component={RegisterArtefact}/>
           <Route exact path={`${asWebPath("/clone-build/:buildNum/:buildStartTime/:tenant/:applicationId")}`} component={CloneABuild}/>
          <Route exact path={`${asWebPath('/maintain/tenants')}`} component={MaintainTenants}/>

    
      </Switch>
    </div>
  )


}


 function MainApp() {
  return (
    
  <Router>
    <MenuBar/>
  </Router>
  
  );
}
 export default MainApp;