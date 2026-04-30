pipeline {
    agent any

    /* Aquí definimos las herramientas. 
       Asegúrate de que en 'Manage Jenkins' -> 'Tools', 
       el nombre de Maven coincida con el que pongas aquí (ej. 'M3')
    */
    tools {
        maven 'M3' 
    }

    stages {
        stage('Carga de Código') {
            steps {
                // Jenkins descarga automáticamente el código de GitHub
                echo 'Descargando código de GitHub...'
            }
        }

        stage('Compilación y Empaquetado') {
            steps {
                echo 'Compilando el proyecto Spring Boot...'
                // Usamos 'bat' porque Jenkins corre sobre Windows
                // -DskipTests es opcional para que la primera vez sea más rápido
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Pruebas') {
            steps {
                echo 'Ejecutando pruebas unitarias...'
                bat 'mvn test'
            }
        }

        stage('Resumen de Artefatos') {
            steps {
                echo 'El archivo .jar se ha generado con éxito en la carpeta target.'
                // Esto guarda el archivo generado dentro de Jenkins para que lo puedas descargar
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '¡Felicidades! El pipeline terminó correctamente.'
        }
        failure {
            echo 'Hubo un error en la construcción. Revisa el Console Output.'
        }
    }
}