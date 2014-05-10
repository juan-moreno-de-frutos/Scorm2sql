/**********************************************************/
/* SCORM2SQL 											  */
/******************										  */
/* Analizador sintáctico que obtiene de un XML en formato */
/* SCORM 1.2 sus campos, cargándolos en un BD preparada   */
/* para ello.											  */
/* 														  */
/* IDE: NetBeans.									  */
/*										  				  */
/* Autor: Juan Moreno de Frutos	    	  	    		  */
/**********************************************************/

/*****************************************/
/* Fichero de configuración: 			 */
/* (En caso de cambiar esta ubicación, 	 */
/*	indicarlo en el fichero Config.java) */
/* * * * * * * * * * * * * * * * * * * * */
.../Scorm2sql/config.properties


/*****************************************/
/* Librerías empleadas:			 */
/* * * * * * * * * * * * * * * * * * * * */
- jdom.jar
- log4-1.2.16.jar
- mysql-connector-java-5.0.4-bin.jar

/*****************************************/
/* Esquema de la BD:		 			 */
/* Nombre BD: scorm						 */
/* * * * * * * * * * * * * * * * * * * * */
.../Scorm2sql/BD/scorm.sql


/*****************************************/
/* Estructura del proyecto scorm2sql:	 */
/* * * * * * * * * * * * * * * * * * * * */

/* Ejecutable
Scorm2sql

/* Manejador de los ficheros
JManejadorFichero

/* Analizador sintáctico
TratarManifest  
(Esta clase se encarga de obtener los campos del XML, recorriéndolo con métodos JDOM)

/* Clases Beans
Ej: BeanOrganizations

/* Clases DAO (Database access object)
Ej: DaoDependency

/* Utilidades
ConexionBD    		(La conexion se configura a través del fichero config.properties)
Config 				(En esta clase se indica la ubicación del fichero config.properties)
FileExtensionFilter	
InicializaLogger
JUnzip				(Utlizado en la primera versión en la que se obtenian los XML de los ZIP)
MensajeError
ValidadorEntradas
VentanaPrincipal
copyFile

/* Excepciones
Ej: ExceptionManifest


/*****************************************/
/* Modo de ejecución:					 */
/* * * * * * * * * * * * * * * * * * * * */

1) Crear la BD scorm:

2) Configurar: 
	- El fichero config.properties
	  [En caso de no estar en su ubicación por defecto: Configurar la clase Config.java]

3) Correr el programa:
	- Desde NetBeans
	- [O desde consola: ]
		* El "jar" se encuentra en la carpeta ".../Scorm2sql/dist".
		* Comando:  java -jar Scorm2sql.jar
		






