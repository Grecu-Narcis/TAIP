kubectl get services

scale to 0 nodes with:
get the node group name: eksctl get nodegroup --cluster=taip-cluster --region=eu-central-1 --profile=taip
then run: eksctl scale nodegroup \
--cluster=taip-cluster \
--name=standard-workers \
--nodes=0 \
--nodes-min=0 \
--nodes-max=0 \
--region=eu-central-1 \
--profile=taip