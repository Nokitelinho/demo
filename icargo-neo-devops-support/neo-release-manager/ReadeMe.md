# Release Manager Application

## Concepts
### Artifact Master
This is the master of all neo deployable artifacts with artifactId ( spring application name or node service name ) 
as the pk.
### Release Catalogue
The entity which captures the build, release version ( git commit id ) which is fed by the Jenkins pipeline as part 
of a successful build.
### Application Master
Flavour of neo for a tenant. Specifies id, description. 
### Tenant Catalogue
A catalogue of artefacts for an Application
### Build Catalogue
An immutable point-in-time snapshot of ReleaseCatalogue entries. Identified by a build number. This has to be triggered 
by the scheduled Jenkins QA Test pipeline
### Release Package
A planning tool. Created for an Application with a Build Number for a planned date. Deployment BOMs can be generated
from a release package



## UI
The in-built UI can be accessed at http://{host}:5000/release-manager-service

## REST APIs
APIs can be accessed at url 
```
http://{host}:5000/release-manager-service/rest/api/{resource}
```
Where {resource} can be:
### Release Catalogue
#### Create an release catalogue entry
```
POST {url}/release/{artifactId}/{artifactVersion}
{
    artifactId:'',
    artifactVersion": '',
    branch: '',
    description: '',
    committerEmail:''
}
```
### Build
#### Create a build
```
POST {url}/builds
```
#### Change status of  build
```
POST {url}/builds/{buildnum}/[alpha|beta|rc]
```
Returns the build number
#### BOM for a build
```
GET {url}/builds/{buildnum}/bom
```
### Release Package
#### BOM for a release package
```
GET {url}/packages/{tenantId}/{packageId}/bom
```






