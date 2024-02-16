# Python script to update the commit tags of images into a values-commit-map.yaml file
import yaml;
import docker;

# Struct representing the deployment infomation
class DeploymentInfo:
  images = {}
  docker_registry = ''
  image_labels = {}

  def set_docker_registry(self, registry):
    if len(self.docker_registry) == 0:
      self.docker_registry = registry
    elif self.docker_registry != registry:
      raise ValueError("Multiple docker registry values " + self.docker_registry + " and " + registry)

  def __str__(self) -> str:
    return "DockerRegistry :" + self.docker_registry + "\nImages :" + self.images.__str__() + "\nImageLabels :" + self.image_labels.__str__() + "\n"

# Read the value files and aggregate the list of deployments and their images
def read_values_yaml(*value_files):
  dep_infos = DeploymentInfo()
  for value_yaml_file in value_files:
    print("Reading values yaml :", value_yaml_file)
    yaml_file = open(value_yaml_file, 'r')
    yaml_dict = yaml.load(yaml_file, Loader = yaml.FullLoader)
    if 'dockerRegistry' in yaml_dict:
      dep_infos.set_docker_registry(yaml_dict['dockerRegistry'])
    if 'deployments' in yaml_dict:
      for key, value in yaml_dict['deployments'].items():
        if 'image' in value:
          dep_infos.images[key] = value['image']
    yaml_file.close()
  return dep_infos

# Populate the docker image label info
def populate_docker_labels(dep_infos):
  docker_client = docker.from_env() # docker.DockerClient(base_url='unix://var/run/docker.sock')
  for dep_name, dep_image in dep_infos.images.items():
    try:
      image_name = dep_infos.docker_registry + "/" + dep_image
      print("Pulling image :", image_name)
      image = docker_client.images.get(image_name)
      depInfos.image_labels[dep_name] = image.labels
      depInfos.image_labels[dep_name]['dockerId'] = image.short_id;
    except Exception as err:
      print("Error occured while pulling image :", dep_image, " error :", err)

# Write the yaml file
def write_values_yaml(dep_infos):
  commit_value_yaml = open('values-commit.yaml', 'w')
  image_labels = {"imageLabels": dep_infos.image_labels}
  yaml.dump(image_labels, default_flow_style = False, stream = commit_value_yaml)
  commit_value_yaml.close()
  print("Wrote file :", commit_value_yaml.name)


#
# Main block
#
depInfos = read_values_yaml("values.yaml")
print("DeploymentInfo :", depInfos)
populate_docker_labels(depInfos)
write_values_yaml(depInfos)
