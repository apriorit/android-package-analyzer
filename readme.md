# Android App to Analyze the Programs Installed on device

## About

The app to collect and display various information about apps installed on Android device.

Functionality:

- Get the installed apps list;
- Get the lists of Activities, Services, Broadcast Receivers, and Permissions;
- Notify about potentially dangerous permissions used by an app;
- Continuously track the apps being installed and check the permissions they request (in the service).

## Implementation

The project was initially created for Android 4.1.

The information about installed apps is acquired from PackageManager. 

Details of implementation and customization can be found in the [related article](https://www.apriorit.com/dev-blog/304-android-package-analyzer).

## License

Licensed under the MIT license. © Apriorit.
