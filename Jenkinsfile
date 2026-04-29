pipeline {
    agent any

    stages {
        stage('Limpieza y Preparación') {
            steps {
                // Esto da permisos de ejecución al wrapper de Maven
                sh 'chmod +x mvnw'
            }
        }
        stage('Compilación') {
            steps {
                // Compila el proyecto saltándose los tests para ir rápido
                sh './mvnw clean compile -DskipTests'
            }
        }
        stage('Pruebas Unitarias') {
            steps {
                sh './mvnw test'
            }
        }
    }
    post {
        always {
            echo 'Finalizó el proceso de integración.'
        }
    }
}