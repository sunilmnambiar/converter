node {
	stage('Build Docker Image') {
		echo "Building docker image"
      	sh "docker build -t sunilmnambiar/converter ."
    }
   
    stage('Deploy Docker Image'){
      echo "Deploying docker image"
	  
	  sh "docker stop sunilmnambiar/converter"
	  
	  sh "docker rm sunilmnambiar/converter"
	  
	  sh "docker run -p 8080:8080 sunilmnambiar/converter"
      
    }
}