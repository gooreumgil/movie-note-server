#!/bin/bash

echo "> spring.profiles.active = $MOVIE_NOTE_API_ENV"

if [ -z "$MOVIE_NOTE_API_ENV" ]; then
        echo "운영 정보가 존재하지 않습니다. (env: $MOVIE_NOTE_API_ENV)"
        exit 1
fi

if [ "$MOVIE_NOTE_API_ENV" == "dev" ]; then
  BASE_URL=https://dev-movie-note-api.old-team.net
elif [ "$MOVIE_NOTE_API_ENV" == "prod" ]; then
  BASE_URL=https://movie-note-api.old-team.net
fi

CURRENT_PORT_PROFILE=$(curl -k "$BASE_URL"/profile)

echo "> 현재 구동중인 Port 프로필"
echo $CURRENT_PORT_PROFILE

if [ $CURRENT_PORT_PROFILE == "port-1" ]; then
        CURRENT_RUNNING_PORT=8080
        WILL_RUNNING_PORT=8081
        WILL_RUNNING_PORT_PROFILE="port-2"
elif [ $CURRENT_PORT_PROFILE == "port-2" ]; then
        CURRENT_RUNNING_PORT=8081
        WILL_RUNNING_PORT=8080
        WILL_RUNNING_PORT_PROFILE="port-1"
else
    CURRENT_RUNNING_PORT=8081
    WILL_RUNNING_PORT=8080
    WILL_RUNNING_PORT_PROFILE="port-1"
fi

echo "> 현재 구동중인 Port $CURRENT_RUNNING_PORT"
echo "> 구동 시 Port $WILL_RUNNING_PORT"

JAR="/projects/movie-note-server/movie-note-api/build/libs/movie-note-api-0.0.1-SNAPSHOT.jar"

fuser -k $WILL_RUNNING_PORT/tcp
echo "> 구동 할 port profile $WILL_RUNNING_PORT_PROFILE"
nohup java -jar --spring.profiles.active=$MOVIE_NOTE_API_ENV,$WILL_RUNNING_PORT_PROFILE $JAR &

echo "> Health check 시작합니다."
echo "> curl -s http://localhost:$WILL_RUNNING_PORT/hello"
sleep 3

for retry_count in {1..500}
do
  response=$(curl -s http://localhost:$WILL_RUNNING_PORT/hello)
  up_count=$(echo $response | grep 'hello' | wc -l)

  if [ $up_count -ge 1 ]
  then
      echo "> Health check 성공"
      break
  else
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 100 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 1
done

echo "> 전환할 Port: $WILL_RUNNING_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:$WILL_RUNNING_PORT;" |sudo tee /etc/nginx/conf.d/movie-note-api-service-url.inc

sudo service nginx reload

sleep 3
echo "> 구동중이었던 프로세스 종료"
fuser -k $CURRENT_RUNNING_PORT/tcp
