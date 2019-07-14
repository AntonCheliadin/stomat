const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    mode: 'development',
    devtool: 'source-map',
    entry: {
        managerBooking: path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js', 'pages', 'booking', 'manager', 'index.js'),
        patientBooking: path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js', 'pages', 'booking', 'patient', 'index.js'),
    },
    devServer: {
        contentBase: './dist',
        compress: true,
        port: 8000,
        allowedHosts: [
            'localhost:8080'
        ]
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.(css|scss)$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            }
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js'),
            path.join(__dirname, 'node_modules'),
        ],
    }
}