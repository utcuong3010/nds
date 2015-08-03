1. Connect vpn:

sudo vpnc ./AITSVPN.conf

2.From app folder:

java -cp .:../lib/*:../lib/barcode/*:../lib/browser/*:../lib/endorsed/*:../lib/ext/*:../lib/license/*:../lib/schema/*:../lib/security/* com.sabre.qik.boot.Main -config desktop


3. Turn logging on:

	Modify qik.properties:
		log4j.externalConfigurationFile=log4j.properties
		qik.log.encryptionMode=clear text
	
	Create /app/log4j.properties:
		log4j.rootLogger=DEBUG, stdout, FILE
		#log4j.logger.com.sabre.qik=OFF, stdout
		#log4j.logger.com.sabre.hostaccess.commlink.jsapi=DEBUG,stdout,FILE
		
		# Direct log messages to stdout
		log4j.appender.stdout=org.apache.log4j.ConsoleAppender
		log4j.appender.stdout.Target=System.out
		log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
		log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c - %m%n
		
		log4j.appender.FILE=org.apache.log4j.RollingFileAppender
		log4j.appender.FILE.File=log.out
		log4j.appender.FILE.ImmediateFlush=true
		log4j.appender.FILE.Threshold=debug 
		log4j.appender.FILE.Append=false  
		log4j.appender.FILE.layout=org.apache.log4j.PatternLayout    
		log4j.appender.FILE.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c - %m%n
