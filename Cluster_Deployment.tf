resource "aws_default_vpc" "default" {

}

data "aws_subnet_ids" "subnets" {
    vpc_id = aws_default_vpc.default.id
}


provider "kubernetes" {
    host                   = data.aws_eks_cluster.cluster.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate)
    token                  = data.aws_eks_cluster_auth.cluster.token
    load_config_file       = false
    version                = "1.9"
}

module "rest-api" {
    source          ="terraform-aws-module/eks/aws"
    cluster_name    = "rest-api-cluster"
    cluster_version = "1.0"
    subnets         = data.aws_subnet_ids.subnets.ids
    vpc_id          = aws_default_vpc.default.id
}

node_groups = [
  {
    instance_type.    = "t2.micro"
    max_capacity      = 5
    desired_capacity  = 2
    min_capacity      = 2
  }
]

data "aws_eks_cluster" "cluster" {
    name = module.rest-api-cluster.cluster_id
}

data "aws_eks_cluster_auth" "cluster" {
    name = module.rest-api-cluster.cluster_id
}

resource "kubernetes_cluster_role_binding" "service" {
    metadata {
       name= "fabric8-rbac"
    }
    role_ref {
       api_group   = "brace.authorization.k8s.io"
       kind        = "ClusterRole"
       name        = "cluster-admin"
    }
    subject {
       kind      = "ServiceAccount"
       name      = "default"
       namespace = "default"
    }

}