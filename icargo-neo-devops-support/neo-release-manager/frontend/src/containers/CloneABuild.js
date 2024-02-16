import React,{useState,useEffect} from 'react';
import { Container } from '@material-ui/core';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';

import { Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';


import tenantState from '../store/TenantAtom';
import { postToBEnd } from '../components/Common'; 
import { getFromBEnd } from '../components/Common'; 
import moment from 'moment';

import commonStyles from '../components/styles';
import clsx from 'clsx';
import EditableBuildCatalogue from '../components/EditableBuildCatalogue';


export default function CloneABuild(props) {
    const classes = commonStyles();
    const buildNum = props.match.params.buildNum;
    const buildStartTime = props.match.params.buildStartTime;
    const applicationId = props.match.params.applicationId;
    const tenant = props.match.params.tenant;
    const [selectedArts, setSelectedArts] = React.useState([]);
    const [disableSaveBtn,setDisableSaveBtn] = useState(true); 
    const [bom, setBom] = React.useState([]);
    
    useEffect(() =>{
        getFromBEnd(`/builds/${buildNum}/bom`)
        .then(setBom);
    },[buildNum]);

    const artReleasePickedCallBack = (artRelease)=>{
        let found = false
        let mappedArts = selectedArts.map(art=>{
            let result = art
            //Existing one
            if(art.artifactId===artRelease.artifactId){
                found = true
                result = artRelease
            }
            return result
        })
        //New one
        if(!found){
            mappedArts.push(artRelease)
        }
        setSelectedArts(mappedArts) 
        setDisableSaveBtn(false);
    }

    const saveHandler = () =>{
      setDisableSaveBtn(true);
      selectedArts.length > 0 && postToBEnd(`/builds/${buildNum}/clone`,selectedArts)
      setDisableSaveBtn(false);
    }    

    return(
        <div className={clsx(classes.margin)}>
            <Container disableGutters={true} maxWidth="xl">
                <Paper className={classes.paper}>
                    <Grid container direction="column"  >
                        <Grid container   direction="row"  justify="flex-start"> 
                            <Typography variant="h6">Clone Build</Typography>
                        </Grid>  
                        <Grid container  direction="row" className={clsx(classes.border,classes.padding)} justify="space-around">   
                            <Grid item md={1}>
                                <TextField margin="normal" disabled={true} id="build-num" label="Build# To Clone"
                                    InputLabelProps={{ shrink: true, }}
                                    value={buildNum}
                                    variant="outlined"
                                />
                            </Grid>    
                               <Grid item md={3}>
                                <TextField fullWidth margin="normal" disabled={true} id="build-date" label="Build Time (UTC)"
                                    InputLabelProps={{ shrink: true, }}
                                    value={moment(buildStartTime,moment.ISO_8601).format("LLLL")}
                                    variant="outlined"
                                />
                               </Grid> 
                                <Grid item md={2}>
                                <TextField fullWidth margin="normal" disabled={true} id="applicationId" label="Application"
                                    InputLabelProps={{ shrink: true, }} variant="outlined"
                                    value={applicationId}
                                />
                               </Grid> 
                             <Grid item md={2}>
                                <TextField fullWidth margin="normal" disabled={true} id="tenant" label="For Tenant"
                                    InputLabelProps={{ shrink: true, }} variant="outlined"
                                    value={tenant}
                                />
                               </Grid>                                                                
                        </Grid>
                       <Grid container  direction="column" style={{marginTop:'2ch'}} alignItems="center">  

                            <Grid container   direction="row"  justify="flex-start"> 
                                <Typography>1. Select Artefact Release</Typography>
                            </Grid>  
                            <Grid container   direction="row"  justify="flex-start"> 
                                <Typography>2. Choose new versions as required</Typography>
                            </Grid>  
                        </Grid>    
                        <Grid container  direction="column" style={{marginTop:'2ch'}} alignItems="center">  
                            <Grid container  direction="row" className={clsx(classes.border,classes.padding)} justify="flex-start">   
                                <Grid item md={12}>
                                    <EditableBuildCatalogue artifacts={bom} description='Build Catalogue'
                                        artReleasePickedCallBack={artReleasePickedCallBack}
                                    />
                            </Grid>
                            <Grid container  direction="row" className={clsx(classes.padding)} justify="flex-end">   
                                <Grid item md={1} justify="flex-end">
                                    <Button onClick={saveHandler} variant="contained" color="primary" disabled={disableSaveBtn}>
                                    Clone
                                    </Button>
                                </Grid>  
                            </Grid>                            
                        </Grid>
                      

                        </Grid>

                    </Grid>
                </Paper>
             </Container>
        </div>        

    )
    
}