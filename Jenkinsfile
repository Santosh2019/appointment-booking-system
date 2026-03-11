pipeline {
    agent any

    environment {
        DOCKERHUB = 'santoshlimbale76'
        TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                bat "mvn -B -DskipTests clean package"
            }
        }

        stage('Build Docker Images') {
            steps {
                bat """
                docker build -t %DOCKERHUB%/appointment-service:%TAG% -t %DOCKERHUB%/appointment-service:latest appointment-service
                docker build -t %DOCKERHUB%/patient-service:%TAG% -t %DOCKERHUB%/patient-service:latest patient-service
                docker build -t %DOCKERHUB%/doctor-service:%TAG% -t %DOCKERHUB%/doctor-service:latest doctor-service
                docker build -t %DOCKERHUB%/api-gateway-service:%TAG% -t %DOCKERHUB%/api-gateway-service:latest api-gateway-service
                docker build -t %DOCKERHUB%/eureka-service:%TAG% -t %DOCKERHUB%/eureka-service:latest eureka-service
                docker build -t %DOCKERHUB%/appointment-ui-service:%TAG% -t %DOCKERHUB%/appointment-ui-service:latest appointment-ui-service
                """
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                bat """
                docker push %DOCKERHUB%/appointment-service:%TAG%
                docker push %DOCKERHUB%/appointment-service:latest

                docker push %DOCKERHUB%/patient-service:%TAG%
                docker push %DOCKERHUB%/patient-service:latest

                docker push %DOCKERHUB%/doctor-service:%TAG%
                docker push %DOCKERHUB%/doctor-service:latest

                docker push %DOCKERHUB%/api-gateway-service:%TAG%
                docker push %DOCKERHUB%/api-gateway-service:latest

                docker push %DOCKERHUB%/eureka-service:%TAG%
                docker push %DOCKERHUB%/eureka-service:latest

                docker push %DOCKERHUB%/appointment-ui-service:%TAG%
                docker push %DOCKERHUB%/appointment-ui-service:latest
                """
            }
        }

        stage('Deploy Application') {
            when {
                anyOf {
                    branch 'master'
                }
            }
            steps {
                bat """
                docker compose down
                docker compose pull
                docker compose up -d
                """
            }
        }
    }

    post {
        success {
            echo "✅ Build & Deployment Successful - Version ${TAG}"
        }

        failure {
            echo "❌ Pipeline Failed - Check Logs"
        }

        always {
            cleanWs()
        }
    }
}