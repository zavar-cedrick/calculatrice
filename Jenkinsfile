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
        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=calculatrice'
                }
            }
        }
        
        stage('Quality Gate') {
            steps {
                waitForQualityGate abortPipeline: false
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
        
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl apply -f k8s-deployment.yaml
                    kubectl rollout status deployment/calculatrice-deployment
                '''
            }
        }
    }
    
    post {
        success {
            mail to: 'votre_email@gmail.com',
                 subject: "Build & Deploy SUCCESS: Calculatrice #${env.BUILD_NUMBER}",
                 body: "La calculatrice a été compilée, testée, analysée, containerisée et déployée sur Kubernetes avec succès!"
        }
        failure {
            mail to: 'votre_email@gmail.com',
                 subject: "Build & Deploy FAILED: Calculatrice #${env.BUILD_NUMBER}",
                 body: "Le build/deploy a échoué. Consultez les logs: ${env.BUILD_URL}"
        }
    }
}
