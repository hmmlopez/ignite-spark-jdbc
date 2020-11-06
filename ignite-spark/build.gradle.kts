dependencies {
    implementation("org.apache.ignite:ignite-slf4j")
    implementation("org.apache.ignite:ignite-spark-2.4") {
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
    implementation("org.codehaus.janino:commons-compiler")
    implementation("org.codehaus.janino:janino")
}