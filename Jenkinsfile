pipeline {
    agent any

    tools {
        maven '3.9.6'
        jdk   'JDK 17'
    }

    environment {
        DOCKERHUB = 'santoshlimbale76'
        TAG       = "${BUILD_NUMBER}"
    }

    stages {
        stage('Cleanup') { steps { cleanWs() } }

        stage('Checkout') { steps { checkout scm } }

        stage('Maven Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Build Docker Images (Spring Boot)') {
            steps {
                script {
                    def services = [
                        'appointment-service', 'patient-service', 'doctor-service',
                        'au-service', 'api-gateway-service', 'eureka-service'
                    ]

                    services.each { service ->
                        bat """
                            call "C:\\docker-machine\\docker-machine.exe" env default
                            mvn spring-boot:build-image ^
                              -pl ${service} ^
                              -DskipTests ^
                              -Dspring-boot.build-image.imageName=${DOCKERHUB}/${service}:${TAG}
                        """
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                                                 usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    bat """
                        call "C:\\docker-machine\\docker-machine.exe" env default

                        echo %PASS% | docker login -u %USER% --password-stdin

                        docker push ${DOCKERHUB}/appointment-service:${TAG}
                        docker push ${DOCKERHUB}/patient-service:${TAG}
                        docker push ${DOCKERHUB}/doctor-service:${TAG}
                        docker push ${DOCKERHUB}/au-service:${TAG}
                        docker push ${DOCKERHUB}/api-gateway-service:${TAG}
                        docker push ${DOCKERHUB}/eureka-service:${TAG}
                    """
                }
            }
        }

        stage('Tag as Latest (main branch only)') {
            when { branch 'main' }
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials',
                                                 usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    bat """
                        call "C:\\docker-machine\\docker-machine.exe" env default

                        echo %PASS% | docker login -u %USER% --password-stdin

                        docker tag ${DOCKERHUB}/appointment-service:${TAG}     ${DOCKERHUB}/appointment-service:latest
                        docker tag ${DOCKERHUB}/patient-service:${TAG}         ${DOCKERHUB}/patient-service:latest
                        docker tag ${DOCKERHUB}/doctor-service:${TAG}          ${DOCKERHUB}/doctor-service:latest
                        docker tag ${DOCKERHUB}/au-service:${TAG}              ${DOCKERHUB}/au-service:latest
                        docker tag ${DOCKERHUB}/api-gateway-service:${TAG}     ${DOCKERHUB}/api-gateway-service:latest
                        docker tag ${DOCKERHUB}/eureka-service:${TAG}          ${DOCKERHUB}/eureka-service:latest

                        docker push ${DOCKERHUB}/appointment-service:latest
                        docker push ${DOCKERHUB}/patient-service:latest
                        docker push ${DOCKERHUB}/doctor-service:latest
                        docker push ${DOCKERHUB}/au-service:latest
                        docker push ${DOCKERHUB}/api-gateway-service:latest
                        docker push ${DOCKERHUB}/eureka-service:latest
                    """
                }
            }
        }
    }

    post {
        always { cleanWs() }
    }
}
