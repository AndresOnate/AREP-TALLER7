FROM openjdk:17

WORKDIR /usrapp/bin

ENV PORT 46000
ENV KEY_PASSWORD 123456
ENV KEY_TRUST_PASSWORD 123456

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency
COPY /certificados /usrapp/bin/certificados

CMD ["java","-cp","./classes:./dependency/*","org.edu.eci.arep.LoginServer"]