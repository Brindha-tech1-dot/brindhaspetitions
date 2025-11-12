pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = '13.60.13.106'
        EC2_KEY = '/Users/Brindha/Downloads/Jenkins-1.pem' // path to your .pem file
        TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps/'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Brindha-tech1-dot/brindhaspetitions.git'
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
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
        stage('Deploy') {
            steps {
                input message: 'Deploy to EC2 Tomcat?', ok: 'Deploy'
                // Copy WAR to EC2 and restart Tomcat
                sh """
                scp -i ${EC2_KEY} target/brindhaspetitions.war ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/
                ssh -i ${EC2_KEY} ${EC2_USER}@${EC2_HOST} 'sudo mv /home/${EC2_USER}/brindhaspetitions.war ${TOMCAT_WEBAPPS} && sudo systemctl restart tomcat10'
                """
            }
        }
    }
}