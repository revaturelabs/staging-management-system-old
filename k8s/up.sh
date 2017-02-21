# variables
APP=scheduler
#ENVIRONMENT=production
ENVIRONMENT=staging


# services
kubectl apply -f services/$APP.yaml --namespace $ENVIRONMENT

# secrets
kubectl apply -f secrets/db-info-$ENVIRONMENT.yaml

# deployments
kubectl apply -f deployments/$APP.yaml --namespace $ENVIRONMENT

# ingress
kubectl apply -f ingress/$APP-$ENVIRONMENT.yaml

