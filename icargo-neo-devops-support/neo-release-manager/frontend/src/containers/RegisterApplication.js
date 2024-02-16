import React,{useState,useEffect} from 'react';
import { Container } from '@material-ui/core';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import { Typography} from '@material-ui/core';
import { Button } from '@material-ui/core';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableContainer from '@material-ui/core/TableContainer';

import EditIcon from '@material-ui/icons/Edit';
import DoneIcon from '@material-ui/icons/Done';
import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';
import { useRecoilState } from 'recoil';
import tenantState from '../store/TenantAtom';
import { postToBEnd } from '../components/Common'; 
import { getFromBEnd,NoListRow } from '../components/Common'; 



import commonStyles,{ HeadingCell} from '../components/styles';
import clsx from 'clsx';





const SelectableArtefactList = (props) =>{
      const {classes,artefacts,onRowClick} = props;
      return (
     <TableContainer >
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <HeadingCell align="left">Artefact Id</HeadingCell>
            <HeadingCell align="left">Domain</HeadingCell>        
            <HeadingCell align="left">Type</HeadingCell>              
            <HeadingCell align="left">Technology</HeadingCell>            
            <HeadingCell align="left">Description</HeadingCell>
           
          </TableRow>
        </TableHead>
          <TableBody>
            {artefacts && artefacts.length>0?artefacts.map((row,idx) => (
              <TableRow
                key={idx}
                onClick={()=>onRowClick(row.artifactId)}
                selected={row.selected}
              >
                <TableCell align="left">{row.artifactId}</TableCell>
                <TableCell align="left">{row.domain}</TableCell>
                <TableCell align="left">{row.serviceType}</TableCell>
                <TableCell align="left">{row.serviceTechnology}</TableCell>
                <TableCell align="left">{row.description}</TableCell>
              </TableRow>
            )):<NoListRow message='No artefacts to list?'/>}
          </TableBody>
        </Table>
      </TableContainer>
    );
}

const  mergeArtifcatList =(appArtList,fullArtList)=>{
  
  let result = fullArtList
    if (appArtList){
        result = fullArtList.map(item=>{
          let matched = false
          appArtList.forEach(a=>{
            if(item.artifactId === a.artifactId){
              matched = true
            }
          })
          item.selected = matched
          return item
        })
    }
    return result
}

const getFullArtList = async () =>{
    return getFromBEnd("/artifacts");
}



const getAppDefinition = async (tenant,appId) =>{
    let appDef = undefined;
    if(appId){
      appDef = getFromBEnd(`/tenant/${tenant}/applications/${appId}`);
    }
    return  appDef;
}

const getAppArtList = async (tenant,appId) =>{
    let appArtList = undefined;
    if(appId){
      appArtList = getFromBEnd(`/tenant/${tenant}/applications/${appId}/artefacts`);
    }
    return  appArtList;
}


export default function RegisterApplication(props){
    const classes = commonStyles();
    const [tenant, setTenant] = useRecoilState(tenantState);
    const appId = props.match.params.applicationId;
    const initialValues={
      applicationId:appId?appId:'',
      applicationDesc: '',
      releaseNumber:'',
      artefactList:[],
      errors:{}
    };
    const [formData,setFormData] = useState(initialValues);
    const [disableSaveBtn,setDisableSaveBtn] = useState(false);
    const [releaseState, setReleaseState] = useState("");
    
    useEffect(() => {
        //let fullArt = getFullArtList();//from remote
        getFullArtList().then((fullArtList)=>
            getAppArtList(tenant.value,appId)
              .then((appArtList)=>mergeArtifcatList(appArtList,fullArtList)))
                .then((mergedArtList)=>{
                    getAppDefinition(tenant.value,appId)
                    .then(appDef=>{
                      setFormData({
                        ...formData,
                        applicationDesc: appDef?appDef.applicationDesc:'',
                        releaseNumber: appDef?appDef.releaseNumber:'',
                        artefactList: mergedArtList,
                    });
                    if(appDef && appDef.releaseNumber && appDef.releaseNumber!==""){
                      setReleaseState("Disabled")
                    }
                  })
              });
    },[])



  
     const onRowSelect = (id) => {
        const newFormData = {...formData};
        const {artefactList} = formData;
        const newRows = [...artefactList];
        const index = artefactList.findIndex(row => row.artifactId === id);
        const row = artefactList[index];

        newRows[index] = { ...row, selected: !row.selected };
        newFormData.artefactList = newRows;
        setFormData(newFormData);
    }

    const validateAndSet = (event) =>{
      
      let newFormData = {...formData,
        [event.target.name]: event.target.value
      };
      
      const errors = {};
      if(!newFormData.applicationId){
        errors.applicationId = 'Required';
      }
      const badAppIDreg=/[^A-Za-z0-9_-]/i
      if (badAppIDreg.test(newFormData.applicationId)) {
          errors.applicationId  = 'Invalid Application .Should only contain (A-Za-z0-9-+_)';
      }

      if(!newFormData.applicationDesc){
        errors.applicationDesc = 'Required';
      }
      const badRelIDreg=/[^A-Za-z0-9_.-]/i
      if (badRelIDreg.test(newFormData.releaseNumber)) {
        errors.releaseNumber  = 'Invalid Release Number .Should only contain (A-Za-z0-9-+._)';
      }
      newFormData.errors = errors;
      const inError = errors.applicationId || errors.applicationDesc || errors.releaseNumber
//Disable save button if errors present
      setDisableSaveBtn(inError?true:false)
      setFormData(newFormData);
   }

    const saveHandler = () =>{
      setDisableSaveBtn(true);
      const artefacts= formData.artefactList.filter(item=> item.selected);
      let appToSave={
          applicationId : formData.applicationId,
          applicationDesc:formData.applicationDesc,
          releaseNumber : formData.releaseNumber,
          tenantId:tenant.value
        };

        if(artefacts){
            appToSave.artifactIds = artefacts.filter((a)=>a.selected).map((a) => a.artifactId);
        }
        postToBEnd(`/tenant/${tenant.value}/applications/${appToSave.applicationId}`,appToSave)
        setDisableSaveBtn(false);
    }

    const saveReleaseNumber = () => {
       postToBEnd(`/tenant/${tenant.value}/applications/${formData.applicationId}/release/${formData.releaseNumber}`)
       setReleaseState("Disabled")
    }

    return(
    <div className={classes.margin}>
    <Container disableGutters={true} maxWidth="xl">  
        <Paper className={classes.paper} >
            <Grid container direction="column" spacing={3} >
            <Grid item lg={12}>
                <Grid container   direction="row"  justify="flex-start"> 
                    <Typography variant="h6">Maintain Application</Typography>
                </Grid>
              </Grid>
            <Grid item lg={12} className={clsx(classes.border,classes.margin)}>  
              <Grid container  direction="row"  justify="flex-start">   
                <Grid item md={2}>
                  <TextField margin="normal" error={formData.errors.applicationId!==undefined} 
                    label="Application Id"
                    InputLabelProps={{ shrink: true, }}
                    disabled={appId!==undefined}
                    name="applicationId"
                    onChange={validateAndSet}
                    value={formData.applicationId}
                    />
                </Grid>    
                <Grid item md={2}>
                  <TextField label="Release Number"  margin="normal"
                          InputLabelProps={{ shrink: true, }} 
                          name="releaseNumber"
                          value={formData.releaseNumber}
                          error={formData.errors.releaseNumber!=null}
                          onChange={validateAndSet} disabled={releaseState=="Disabled"}
                   />
                   
                </Grid>
                <Grid container spacing={0}  md={1} >
                {releaseState==="Disabled" ?
                <Tooltip title="Edit Release Number">
                      <IconButton  size="small" onClick={() => {setReleaseState("Edit")}} >
                        <EditIcon />
                      </IconButton>
                    </Tooltip> : ""}
                    {releaseState==="Edit" ?
                <Tooltip title="Save Release Number">
                      <IconButton  size="small" onClick={saveReleaseNumber} >
                        <DoneIcon />
                      </IconButton>
                    </Tooltip> : ""}
                </Grid>
                <Grid item md={4}>
                  <TextField label="Description" fullWidth margin="normal"
                          InputLabelProps={{ shrink: true, }} multiline maxrows={2}
                          name="applicationDesc"
                          value={formData.applicationDesc}
                          error={formData.errors.applicationDesc!=null}
                          onChange={validateAndSet}
                   />
                </Grid>
                <Grid item md={2}>
                  <Button onClick={saveHandler} className={classes.margin} variant="contained" color="primary" disabled={disableSaveBtn}>
                  Save
                  </Button>
              </Grid>                   
              </Grid>
                          
            </Grid>   
            <Grid container  direction="column" style={{margin:'1ch'}} alignItems="center">  
                <Grid container   direction="row"  justify="flex-start"> 
                    <Typography>Select Artefacts below that make-up the Application Definition </Typography>
                </Grid>  
        
            </Grid>               
            
              <Grid item lg={12} className={clsx(classes.border,classes.margin)}>  
                    <SelectableArtefactList clasess={classes} artefacts={formData.artefactList} onRowClick={onRowSelect}/>
              </Grid>   
            </Grid>
        </Paper>
    </Container>
    </div>
    )
   
}