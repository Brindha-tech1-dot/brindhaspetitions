pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Brindha-tech1-dot/brindhaspetitions.git'
            }
        }
        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }
        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }
        stage('Package WAR') {
            steps {
                sh './mvnw package'
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
        stage('Deploy') {
            steps {
                input message: 'Deploy to Tomcat?', ok: 'Deploy'
                // Example deploy command if Tomcat is on same EC2:
                sh 'cp target/brindhaspetitions.war /opt/tomcat/webapps/'
            }
        }
    }
}