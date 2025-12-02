pipeline {
    agent any
    tools {
        maven '3.9.6'
        jdk   'JDK 17'
    }

    environment {
        DOCKERHUB         = 'santoshlimbale76'
        TAG              = "${BUILD_NUMBER}"
        DOCKER_HOST       = 'tcp://192.168.99.100:2376'
        DOCKER_TLS_VERIFY = '1'
        DOCKER_CERT_PATH  = 'C:\\Users\\santo\\.docker\\machine\\machines\\default'
    }

    stages {
        {
        stage('Clean & Checkout') { steps { cleanWs(); checkout scm } }

        stage('Maven Compile') {
            steps { bat 'mvn -B -DskipTests clean package' }
        }

        stage('Build + Push All Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    bat '''
                        echo %PASS% | docker login -u %USER% --password-stdin

                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/appointment-service:%TAG%     -pl appointment-service
                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/patient-service:%TAG%        -pl patient-service
                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/doctor-service:%TAG%         -pl doctor-service
                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/au-service:%TAG%             -pl au-service
                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/api-gateway-service:%TAG%    -pl api-gateway-service
                        mvn spring-boot:build-image -DskipTests -Dspring-boot.build-image.imageName=%DOCKERHUB%/eureka-service:%TAG%        -pl eureka-service

                        docker push %DOCKERHUB%/appointment-service:%TAG%
                        docker push %DOCKERHUB%/patient-service:%TAG%
                        docker push %DOCKERHUB%/doctor-service:%TAG%
                        docker push %DOCKERHUB%/au-service:%TAG%
                        docker push %DOCKERHUB%/api-gateway-service:%TAG%
                        docker push %DOCKERHUB%/eureka-service:%TAG%
                    '''
                }
            }
        }

        stage('Tag as latest — main branch only') {
            when { branch 'main' }
            steps {
                bat '''
                    docker tag %DOCKERHUB%/appointment-service:%TAG%   %DOCKERHUB%/appointment-service:latest
                    docker tag %DOCKERHUB%/patient-service:%TAG%       %DOCKERHUB%/patient-service:latest
                    docker tag %DOCKERHUB%/doctor-service:%TAG%        %DOCKERHUB%/doctor-service:latest
                    docker tag %DOCKERHUB%/au-service:%TAG%            %DOCKERHUB%/au-service:latest
                    docker tag %DOCKERHUB%/api-gateway-service:%TAG%   %DOCKERHUB%/api-gateway-service:latest
                    docker tag %DOCKERHUB%/eureka-service:%TAG%        %DOCKERHUB%/eureka-service:latest

                    docker push %DOCKERHUB%/appointment-service:latest
                    docker push %DOCKERHUB%/patient-service:latest
                    docker push %DOCKERHUB%/doctor-service:latest
                    docker push %DOCKERHUB%/au-service:latest
                    docker push %DOCKERHUB%/api-gateway-service:latest
                    docker push %DOCKERHUB%/eureka-service:latest
                '''
            }
        }
    }

    post {
        always { cleanWs() }
    }
}
