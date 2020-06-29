var request = require('request'),
    _ = require('lodash'),
    Q = require('q'),
    path = require("path");

var cacheContainer = {};
var base = {
    setCache: function (key, value) {
        cacheContainer[key] = value;
    },

    getCache: function (key) {
        return cacheContainer[key];
    },
    serialize: function (data) {
        if (!_.isObject(data)) {
            return ( ( data == null ) ? "" : data.toString() );
        }
        var buffer = [];
        for (var name in data) {
            if (!data.hasOwnProperty(name)) {
                continue;
            }
            var value = data[name];
            buffer.push(
                encodeURIComponent(name) + "=" + encodeURIComponent(( value == null ) ? "" : value)
            );
        }
        var source = buffer.join("&").replace(/%20/g, " ");
        return ( source );
    },
    setDefaultContentType: function (headers) {
        _.extend(headers, {'Content-Type': 'application/json'});
        return headers;
    }
    ,
    getOptions: function (context, headers, data) {
        if (context.cookie)
            headers.Cookie = context.cookie;

        if (context.Authorization) {
            headers.Authorization = context.Authorization;
        }

        var options = {
            headers: headers
        };
        if (data)
            options.body = JSON.stringify(data);
        return options;
    }
    ,
    createParams: function (ids) {
        var data;
        if (_.isArray(ids)) {
            data = ids.map(function (item) {
                return item = {uid: item};
            });
        } else if (_.isString(ids)) {
            data = {uid: ids};
        } else if (ids.uid) {
            data = ids;
        }
        return base.serialize(data);
    }
    ,
    parseJSON: function (body) {
        if (body.length === 0) {
            return {}
        }
        var firstcharRegExp = /^[\x20\x09\x0a\x0d]*(.)/;

        function firstchar(str) {
            var match = firstcharRegExp.exec(str);
            return match ? match[1] : ''
        }

        var first = firstchar(body);
        if (first !== '{' && first !== '[') {
            return false;
        }
        return JSON.parse(body)
    }
    ,
    resource: function (gateway) {
        var context = {};

        return {
            'setAuthorization': function (authorization) {
                return context['Authorization'] = authorization;
            },
            'getAuthorization': function () {
                return context['Authorization'];
            },
            'setCookie': function (cookie) {
                return context['cookie'] = cookie;
            },
            'getCookie': function () {
                return context['cookie'];
            },
            'get': function (path, callback) {
                var uri = gateway + path;
                var headers = base.setDefaultContentType({});
                request.get(uri, base.getOptions(context, headers), callback);
            },
            'post': function (path, data, callback) {
                var uri = gateway + path;
                var headers = base.setDefaultContentType({});
                var options = base.getOptions(context, headers, data);
                request.post(uri, options, callback);
            },
            'put': function (path, data, callback) {
                var uri = gateway + path;
                var headers = base.setDefaultContentType({});
                request.put(uri, base.getOptions(context, headers, data), callback);
            },
            'delete': function (path, callback) {
                var uri = gateway + path;
                var headers = base.setDefaultContentType({});
                request.del(uri, base.getOptions(context, headers), callback);
            },
            'patch': function (path, data, callback) {
                var uri = gateway + path;
                var headers = base.setDefaultContentType({});
                request.patch(uri, base.getOptions(context, headers, data), callback);
            }
        };
    }
};


module.exports = base;