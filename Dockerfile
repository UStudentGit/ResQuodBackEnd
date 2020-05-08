FROM openjdk:14
ADD target/resquod-*.jar resquod.jar
CMD ["java", "-jar", "resquod.jar"]