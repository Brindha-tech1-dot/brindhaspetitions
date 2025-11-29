pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = '13.60.188.85'
        EC2_KEY = '/home/jenkins/.ssh/Jenkins-1.pem'
        TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Brindha-tech1-dot/brindhaspetitions.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Archive WAR') {
            steps {
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }

        stage('Deploy to EC2') {
            steps {
                input message: "Deploy new WAR to EC2?", ok: "Deploy"

                sh """
                    scp -i ${EC2_KEY} target/*.war ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/brindha.war
                    ssh -i ${EC2_KEY} ${EC2_USER}@${EC2_HOST} "
                        sudo rm -rf ${TOMCAT_WEBAPPS}/ROOT ${TOMCAT_WEBAPPS}/ROOT.war
                        sudo mv /home/${EC2_USER}/brindha.war ${TOMCAT_WEBAPPS}/ROOT.war
                        sudo systemctl restart tomcat10
                    "
                """
            }
        }
    }
}