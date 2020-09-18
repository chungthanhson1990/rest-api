pipeline {
    agent any

    environment {
    	SVC_ACCOUNT_KEY = credentials('terraform-auth')
    }

    stages {
	stage('Build Application') { 
            steps {
                echo '=== Building Application ==='
                sh 'mvn -B -DskipTests clean package' 
            }
        }
	
	stage("Test Application") {
	    steps {
                echo '=== Running Tests ==='
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage("Build image") {
            when {
                branch 'master'
            }
            steps {
                echo '=== Building Docker Image ==='
                script {
                    myapp = docker.build("dchung1990/rest-api:${env.BUILD_ID}")
                }
            }
        }

        stage("Push image") {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'DockerHubCredentials') {
                            myapp.push("latest")
                            myapp.push("${env.BUILD_ID}")
                    }
                }
            }
        }  
	//just the concept
	stage('Provision EKS Cluster') {
            steps{       
		  sh "terraform apply"
            }
        }
        //just the concept 
        stage('Deploy to EKS') {
            steps{
                sh "kubectl apply -f K8S_Deployment.yaml"
            }
        }
    }    
}
