FROM hseeberger/scala-sbt

COPY . /usr/app
WORKDIR /usr/app

RUN sbt stage

ENTRYPOINT [ "./target/universal/stage/bin/calgeneratorchoco", "-Dapplication.secret=GrosSecret", "-Dhttp.port=80" ]
EXPOSE 80