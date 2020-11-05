const rndi = process.env.GOOGLE_FREE ? {platforms: {android: null}} : {};

module.exports = {
    dependencies: {
        'react-native-config': {
            platforms: {
                ios: null,
            },
        },
        'react-native-dialogs': {
            platforms: {
                android: null,
                ios: null,
            },
        },
        'react-native-image-resizer': {
            platforms: {
                ios: null,
            },
        },
        'react-native-status-keycard': {
            platforms: {
                android: null,
                ios: null,
            },
        },
        'react-native-device-info': {
            platforms: {
                android: null,
            }
        },
        'react-native-device-info': rndi,
    },
};
