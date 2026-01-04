pipeline {
    agent any

    environment {
        DOCKERHUB = 'santoshlimbale76'
        TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Clean & Checkout') {
            steps {
                cleanWs()
                checkout scm
            }
        }

        stage('Maven Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Build & Push Docker Images') {
            steps {
                bat '''
                REM ==============================
                REM HARD-CODED DOCKER LOGIN (as requested)
                REM ==============================
                docker login -u santoshlimbale76 -p Santosh@123

                REM ========== Build Docker Images ==========
                docker build -t %DOCKERHUB%/appointment-service:%TAG% ./appointment-service
                docker build -t %DOCKERHUB%/patient-service:%TAG% ./patient-service
                docker build -t %DOCKERHUB%/doctor-service:%TAG% ./doctor-service
                docker build -t %DOCKERHUB%/au-service:%TAG% ./au-service
                docker build -t %DOCKERHUB%/api-gateway-service:%TAG% ./api-gateway-service
                docker build -t %DOCKERHUB%/eureka-service:%TAG% ./eureka-service
                docker build -t %DOCKERHUB%/appointment-ui-service:%TAG% ./appointment-ui-service

                REM ========== Push Docker Images ==========
                docker push %DOCKERHUB%/appointment-service:%TAG%
                docker push %DOCKERHUB%/patient-service:%TAG%
                docker push %DOCKERHUB%/doctor-service:%TAG%
                docker push %DOCKERHUB%/au-service:%TAG%
                docker push %DOCKERHUB%/api-gateway-service:%TAG%
                docker push %DOCKERHUB%/eureka-service:%TAG%
                docker push %DOCKERHUB%/appointment-ui-service:%TAG%
                '''
            }
        }

        stage('Tag as Latest (master branch only)') {
            when {
                branch 'master'   // Fixed: Your repo uses 'master', not 'main'
            }
            steps {
                bat '''
                REM Hard-coded login again for latest tagging
                docker login -u santoshlimbale76 -p Santosh@123

                REM ========== Tag as Latest ==========
                docker tag %DOCKERHUB%/appointment-service:%TAG% %DOCKERHUB%/appointment-service:latest
                docker tag %DOCKERHUB%/patient-service:%TAG% %DOCKERHUB%/patient-service:latest
                docker tag %DOCKERHUB%/doctor-service:%TAG% %DOCKERHUB%/doctor-service:latest
                docker tag %DOCKERHUB%/au-service:%TAG% %DOCKERHUB%/au-service:latest
                docker tag %DOCKERHUB%/api-gateway-service:%TAG% %DOCKERHUB%/api-gateway-service:latest
                docker tag %DOCKERHUB%/eureka-service:%TAG% %DOCKERHUB%/eureka-service:latest
                docker tag %DOCKERHUB%/appointment-ui-service:%TAG% %DOCKERHUB%/appointment-ui-service:latest

                REM ========== Push Latest Tags ==========
                docker push %DOCKERHUB%/appointment-service:latest
                docker push %DOCKERHUB%/patient-service:latest
                docker push %DOCKERHUB%/doctor-service:latest
                docker push %DOCKERHUB%/au-service:latest
                docker push %DOCKERHUB%/api-gateway-service:latest
                docker push %DOCKERHUB%/eureka-service:latest
                docker push %DOCKERHUB%/appointment-ui-service:latest
                '''
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
