pipeline {
    agent any

    tools {
        maven 'Maven'   // Jenkins मध्ये configure केलेले tool name
        jdk 'JDK17'     // किंवा तुझ्या Jenkins मधील JDK चे नाव
    }
    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/Santosh2019/appointment-booking-system.git',
                    branch: 'master'
            }
        }
        stage('Build Project') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('List Artifacts') {
            steps {
                sh 'ls -l */target/*.jar'
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying services..."
                // Patient Service
                sh '''
                    echo "Starting Patient Service..."
                    nohup java -jar patient-service/target/patient-service-0.0.1-SNAPSHOT.jar > patient.log 2>&1 &
                '''
                // Doctor Service
                sh '''
                    echo "Starting Doctor Service..."
                    nohup java -jar doctor-service/target/doctor-service-0.0.1-SNAPSHOT.jar > doctor.log 2>&1 &
                '''
            }
        }
    }
}
