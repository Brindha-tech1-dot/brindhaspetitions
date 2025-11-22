pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = '16.170.232.34'
        EC2_KEY = '/home/jenkins/.ssh/Jenkins-1.pem'
        TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps/ROOT.war'
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

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Package WAR') {
            steps {
                sh 'cp target/*.war target/brindhaspetitions.war'
                archiveArtifacts artifacts: 'target/brindhaspetitions.war', fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                input message: 'Deploy to EC2?', ok: 'Deploy'
                sh """
                scp -o StrictHostKeyChecking=no -i ${EC2_KEY} target/brindhaspetitions.war ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/
                ssh -o StrictHostKeyChecking=no -i ${EC2_KEY} ${EC2_USER}@${EC2_HOST} '
                    sudo mv /home/${EC2_USER}/brindhaspetitions.war /var/lib/tomcat10/webapps/ROOT.war &&
                    sudo chown tomcat:tomcat /var/lib/tomcat10/webapps/ROOT.war &&
                    sudo systemctl restart tomcat10
                '
                """
            }
        }
    }
}
