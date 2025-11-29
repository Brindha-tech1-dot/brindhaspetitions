pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = '13.60.188.85'
        EC2_KEY  = '/home/jenkins/.ssh/Jenkins-1.pem'
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
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Package WAR') {
            steps {
                sh 'ls -l target'
                sh 'cp target/*.war target/brindhaspetitions.war'
                archiveArtifacts artifacts: 'target/brindhaspetitions.war', fingerprint: true
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                input message: 'Deploy new build to EC2 Tomcat?', ok: 'Deploy'

                sh """
                    scp -o StrictHostKeyChecking=no -i ${EC2_KEY} target/brindhaspetitions.war ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/
                    ssh -o StrictHostKeyChecking=no -i ${EC2_KEY} ${EC2_USER}@${EC2_HOST} "
                        sudo mv /home/${EC2_USER}/brindhaspetitions.war ${TOMCAT_WEBAPPS}/ROOT.war &&
                        sudo systemctl restart tomcat10
                    "
                """
            }
        }
    }
}