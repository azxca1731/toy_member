const express = require('express');
const https = require('https');
const fs = require('fs');

const app = express();
const port = 443;

// HTTPS 옵션 설정 (자체 서명된 인증서를 사용)
const options = {
    key: fs.readFileSync('./key.pem'),
    cert: fs.readFileSync('./cert.pem')
};

// HTTPS 서버 생성
https.createServer(options, app).listen(port, () => {
    console.log(`HTTPS Mock Server listening at https://localhost:${port}`);
});