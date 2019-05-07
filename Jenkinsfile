pipeline {   environment {     registry = "yoksar/authentication-service"     registryCredential = 
'dockerhub'     dockerImage = ''   }   agent any     stages {         stage('Cloning Git') {             
steps {                 git([url: 'https://github.com/proftaak-s6/authentication-service.git', branch: 
'master', credentialsId: 'Github'])             }         }         stage('Building image') {             
steps{                 script {                 dockerImage = docker.build registry                 }             
}         }         stage('Deploy Image') {             steps{                     script {                         
docker.withRegistry( '', registryCredential ) {                         dockerImage.push()                     
}                 }             }         }         stage('Remove Unused docker image') {             steps{                 
sh "docker rmi $registry"             }         }           stage("Production") {             steps{                 
node("docker-prod"){                     git([url: 
'https://github.com/proftaak-s6/authentication-service.git', branch: 'master', credentialsId: 'Github'])                     
// sh "docker service rm uptime-portal_overheidsportaal"                     sh "docker stack deploy 
--with-registry-auth -c docker-compose.production.yml authentication-service"                 }             }         
}     } }
