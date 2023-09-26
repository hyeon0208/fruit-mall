sudo docker ps -a -q --filter "name=fruit-mall" | grep -q . && docker stop awsstudy && docker rm awsstudy | true

sudo docker rmi hyeon0208/fruit-mall

sudo docker pull hyeon0208/fruit-mall

docker run -d -p 8080:8080 --name fruit-mall hyeon0208/fruit-mall

docker rmi -f $(docker images -f "dangling=true" -q) || true