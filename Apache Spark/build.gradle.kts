plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.spark:spark-core_2.13:3.5.3")
    implementation("org.apache.spark:spark-sql_2.13:3.5.3")
    implementation ("org.jfree:jfreechart:1.5.3")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.json:json:20210307")

}

tasks.test {
    useJUnitPlatform()
}