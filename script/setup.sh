echo "===== 1. 패키지 업데이트 ====="
sudo apt update && sudo apt upgrade -y

echo "===== 2. Docker 설치 ====="
sudo apt install -y ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt update
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

echo "===== 3. Docker 권한 설정 ====="
sudo usermod -aG docker ubuntu

#echo "===== 4. 디렉토리 생성 ====="
# mkdir -p ~/app/build/libs
# mkdir -p ~/app/certs
# mkdir -p ~/app/fastapi
#
#echo "===== 5. 자체 서명 SSL 인증서 생성 ====="
#openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
#  -keyout ~/app/certs/key.pem \
#  -out ~/app/certs/cert.pem \
#  -subj "/CN=localhost"

echo "===== 완료. 재접속 후 docker 명령어 사용 가능 ====="