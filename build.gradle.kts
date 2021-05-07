plugins {
	application
	id("com.github.johnrengelman.shadow") version "7.0.0"
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.eclipse.jetty", "jetty-server", "11.0.2")
	implementation("org.eclipse.jetty.websocket", "websocket-jetty-server", "11.0.2")
	implementation("javax.vecmath", "vecmath", "1.5.2")
}

application {
	mainClass.set("com.weisbrja.App")
}
