pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE = "zavarcedrick/calculatrice"
        DOCKER_TAG = "1.0"
    }
    
    stages {
        stage('Clone') {
            steps {
                git branch: 'main', credentialsId: 'github-token-new', url: 'https://github.com/zavar-cedrick/calculatrice.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .'
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        docker logout
                    '''
                }
            }
        }
    }
    
    post {
        success {
            mail to: 'votre_email@gmail.com',
                 subject: "Build SUCCESS: Calculatrice #${env.BUILD_NUMBER}",
                 body: "La calculatrice a été compilée, testée et déployée avec succès!"
        }
        failure {
            mail to: 'votre_email@gmail.com',
                 subject: "Build FAILED: Calculatrice #${env.BUILD_NUMBER}",
                 body: "Le build a échoué. Consultez les logs: ${env.BUILD_URL}"
        }
    }
}
