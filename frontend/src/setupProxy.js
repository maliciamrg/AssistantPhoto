const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api', // Assuming your backend API is prefixed with /api
        createProxyMiddleware({
            target: 'http://localhost:8080', // Spring Boot backend URL
            changeOrigin: true,
        })
    );
};
