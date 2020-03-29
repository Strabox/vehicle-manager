![Master branch CI/CD workflow](https://github.com/Strabox/vehicle-manager/workflows/Master%20branch%20CI/CD%20job/badge.svg?branch=master)
![Development branch CI workflow](https://github.com/Strabox/vehicle-manager/workflows/Feature%20development%20branches%20CI%20workflow/badge.svg?branch=feature%2Fdevelopment)
![Last version](https://badgen.net/github/release/strabox/vehicle-manager)
![Last commit](https://badgen.net/github/last-commit/strabox/vehicle-manager)
![License](https://badgen.net/github/license/strabox/vehicle-manager)

# Vehicle Maintenance Manager

Vehicle Maintenance Manager used to maintain maintenance data about different kinds of vehicled in order to track its history.

It also support email based notifications to alert about periodic maintenance procedures for some vehicles.

## Contributing/Development

- Maven: Building, Testing and Deploying the application.
- Github: Code Host, Issues Management, Documentation, CI/CD Pipelines (GitHub actions)
- Docker: Used to build the container image using 'docker/Dockerfile'.
- Kubernetes: Deployment of all components.

## Deployment

### Kubernetes Deployment

Kubernetes deployment documentation for vehicle manager:
1. [Pull an image from private registry (GitHub packages in this case)](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/)
   1. Create secret to access private registry when pulling image. Necessary before applying the Kuberentes deploy yaml.
```bash
kubectl create secret docker-registry reg-cred --docker-server=<your-registry-server e.g. docker.io> --docker-username=<your-name> --docker-password=<your-pword> --docker-email=<your-email>
```
2. Deploy the MariaDB in kubernetes using the respective yaml not present here since this MariaDB will be shared among different apps.
3. Deploy in the Kuberentes the file 'kubernetes/vehicle-mngr-deploy.yaml' to launch the Web App in Kubernetes.

**Note: Not using docker.github.packages because Containerd can't fetch from it due to a bug/my inability :). Possible workaround https://docs.docker.com/registry/recipes/mirror/ registry service proxying to GitHub packages.**

## Badge Generation Service

This service (https://badgen.net) is used to generate badge markdown dynamically by consulting GitHub public API data from repository.

## License

Apache Lincese 2.0.

Check LICENSE file present in the repository.