플러스 주차 개인과제 기반 코드입니다.

# Minio 사용법
- 로컬에서 S3 를 사용하기 위한 방법
```shell
docker-compose up -d
```
- localhost:9000 접속
- id : minio
- pw : minio123
- 으로되어있지만 [docker-compose.yml](docker-compose.yml) 에서 수정가능
- 내부 버킷 생성후 [application.properties](src/main/resources/application.properties) 에서 아래의 값 수정
```properties
amazon.s3.region=us-east-1
amazon.s3.url=http://localhost:9000
amazon.s3.access-key=minio
amazon.s3.secret-key=minio123
amazon.s3.bucket=test
amazon.s3.force-path=true
```
- s3 의 경우 force-path 를 사용하지 않기에 false 지만 minio 는 true
- 관련 설명은 [S3Config.java](src/main/java/com/example/demo/config/S3Config.java) 클래스 참고
