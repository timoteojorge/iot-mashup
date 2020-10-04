### Pré-requisitos para rodar o projeto

* Java 11
* gradle

### Para rodar o projeto

Você vai necessitar de uma Twitter consumer key and secret. Abra os arquivos `src/main/resources/application.properties` e `src/test/resources/application-test.properties` e preencha as seguintes propriedades:<br>

`twitter.api.key=${SUA_TWITTER_API_KEY}`
`twitter.api.secret=${SUA_TWITTER_API_SECRET}`

<br>

Abra o terminal na pasta raiz do projeto e digite:<br>
`./gradlew clean build -x test`<br><br>

Logo em seguida digite:<br>
`java -jar build/libs/iotmashup-0.0.1-SNAPSHOT.jar` <br><br>

Os resultados em json devem ser exibidos no console do terminal.

### Para rodar os testes unitários

Abra o terminal na pasta raiz do projeto e digite:<br>
`./gradlew test`<br>
