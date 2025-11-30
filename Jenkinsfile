pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = '16.171.196.231'
        EC2_KEY  = '/home/jenkins/.ssh/Jenkins-1.pem'
        TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps'
        WAR_NAME = 'brindhaspetitions.war'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Brindha-tech1-dot/brindhaspetitions.git'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package -DskipTests'
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
                    echo "Copying WAR to EC2..."
                    scp -o StrictHostKeyChecking=no -i ${EC2_KEY} target/${WAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/

                    echo "Deploying WAR to Tomcat..."
                    ssh -o StrictHostKeyChecking=no -i ${EC2_KEY} ${EC2_USER}@${EC2_HOST} '
                        sudo rm -rf ${TOMCAT_WEBAPPS}/brindhaspetitions ${TOMCAT_WEBAPPS}/brindhaspetitions.war
                        sudo cp /home/${EC2_USER}/${WAR_NAME} ${TOMCAT_WEBAPPS}/
                        sudo systemctl restart tomcat10
                    '
                """
            }
        }
    }
}