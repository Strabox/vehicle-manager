![Master branch CI/CD workflow](https://github.com/Strabox/vehicle-manager/workflows/Master%20branch%20CI/CD%20job/badge.svg?branch=master)
![Development branch CI workflow](https://github.com/Strabox/vehicle-manager/workflows/Feature%20development%20branches%20CI%20workflow/badge.svg?branch=feature%2Fdevelopment)
![Last version](https://badgen.net/github/release/strabox/vehicle-manager)
![Last commit](https://badgen.net/github/last-commit/strabox/vehicle-manager)
![License](https://badgen.net/github/license/strabox/vehicle-manager)

# Vehicle Maintenance Manager

Vehicle Maintenance Manager for personal use.

## Development

## Deployment

### Kubernetes Deployment

Kubernetes Documentation useful links used to write the vehicle manager deployment.

1. [Pull an image from private registry (GitHub packages in this case)](https://kubernetes.io/docs/tasks/configure-pod-container/pull-image-private-registry/)
   1. Create secret to access private registry when pulling image.
```bash
kubectl create secret docker-registry github-reg-cred --docker-server=<your-registry-server> --docker-username=<your-name> --docker-password=<your-pword> --docker-email=<your-email>
```

## Badge Generation Service

This service (https://badgen.net) is used to generate badge markdown dynamically by consulting GitHub public API data from repository.

## License

Apache Lincese 2.0.

Check LICENSE file.