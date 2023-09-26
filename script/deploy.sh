sudo docker ps -a -q --filter "name=fruit-mall" | grep -q . && docker stop fruit-mall && docker rm fruit-mall | true

sudo docker rmi guswns0208/fruit-mall

sudo docker pull guswns0208/fruit-mall

docker run -d -p 8080:8080 --name fruit-mall guswns0208/fruit-mall

docker rmi -f $(docker images -f "dangling=true" -q) || true