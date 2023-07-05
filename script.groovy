def check() {
    echo "building the application..."
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t rahulkumarpaswan/my-python-project:1.3 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push rahulkumarpaswan/my-python-project:1.3'
    }
} 

def deployApp() {
    echo 'deploying the application...'
    def dockerCmd = 'docker run -d --name rahul-project -p 3000:3000 rahulkumarpaswan/my-python-project:1.3'
    sshagent(['ec2-user-Rahul']) {
    sh "ssh -o StrictHostKeyChecking=no ec2-user@13.232.90.226 ${dockerCmd}"
    }
} 

return this

//

