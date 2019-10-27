const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
    mode: 'development',
    devtool: 'source-map',
    entry: {
        patientBooking: path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js', 'apps', 'patient', 'index.js'),
        adminVue: path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js', 'apps', 'admin', 'index.js'),
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
                test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {
                            name: '[name].[ext]',
                            outputPath: 'fonts/',
                            publicPath: '../'
                        }
                    }
                ]
            },
            {
                test: /\.(css|scss)$/,
                use: [
                    'vue-style-loader',
                    'css-loader',
                    'sass-loader'
                ]
            },
        ]
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    resolve: {
        modules: [
            path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat'),
            path.join(__dirname, 'node_modules'),
        ],
        extensions: ['.js', '.vue', '.scss'],
        alias: {
            '@': path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'js'),
            '@styles': path.join(__dirname, 'src', 'main', 'resources', 'static', 'stomat', 'styles'),
        }
    }
};