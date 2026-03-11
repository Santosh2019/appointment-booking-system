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

    stage('Verify JAR Files') {
        steps {
            bat 'dir appointment-service\\target'
            bat 'dir patient-service\\target'
            bat 'dir doctor-service\\target'
            bat 'dir api-gateway-service\\target'
            bat 'dir eureka-service\\target'
            bat 'dir appointment-ui-service\\target'
        }
    }

    stage('Build Docker Images') {
        steps {
            bat '''
            docker build -t %DOCKERHUB%/appointment-service:%TAG% ./appointment-service
            docker build -t %DOCKERHUB%/patient-service:%TAG% ./patient-service
            docker build -t %DOCKERHUB%/doctor-service:%TAG% ./doctor-service
            docker build -t %DOCKERHUB%/api-gateway-service:%TAG% ./api-gateway-service
            docker build -t %DOCKERHUB%/eureka-service:%TAG% ./eureka-service
            docker build -t %DOCKERHUB%/appointment-ui-service:%TAG% ./appointment-ui-service
            '''
        }
    }

    stage('Push Docker Images') {
        steps {
            withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                bat '''
                docker login -u %DOCKER_USER% -p %DOCKER_PASS%

                docker push %DOCKERHUB%/appointment-service:%TAG%
                docker push %DOCKERHUB%/patient-service:%TAG%
                docker push %DOCKERHUB%/doctor-service:%TAG%
                docker push %DOCKERHUB%/api-gateway-service:%TAG%
                docker push %DOCKERHUB%/eureka-service:%TAG%
                docker push %DOCKERHUB%/appointment-ui-service:%TAG%
                '''
            }
        }
    }

    stage('Deploy Application') {
        when {
            branch 'master'
        }
        steps {
            bat '''
            docker-compose down
            docker-compose pull
            docker-compose up -d
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
