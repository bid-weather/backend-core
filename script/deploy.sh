cd "$(dirname "$0")/.."

EC2_IP="15.164.120.126"
KEY_PATH="$HOME/.ssh/bid-key.pem"
REMOTE="ubuntu@$EC2_IP"
REMOTE_DIR="~/bid-app"

echo "===== 1. Spring 빌드 ====="
./gradlew bootJar

echo "===== 2. 파일 전송 ====="
scp -i $KEY_PATH compose.yaml $REMOTE:$REMOTE_DIR/
scp -i $KEY_PATH nginx.conf $REMOTE:$REMOTE_DIR/
scp -i $KEY_PATH .env $REMOTE:$REMOTE_DIR/
scp -i $KEY_PATH Dockerfile $REMOTE:$REMOTE_DIR/
scp -i $KEY_PATH build/libs/*.jar $REMOTE:$REMOTE_DIR/build/libs/

# FastAPI 전송
#scp -i $KEY_PATH -r fastapi/ $REMOTE:$REMOTE_DIR/

echo "===== 3. Docker Compose 실행 ====="
ssh -i $KEY_PATH $REMOTE "cd $REMOTE_DIR && docker compose up -d --build"

echo "===== 배포 완료 ====="