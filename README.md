# MonicOra GW 
![MonicOra](https://github.com/jbenadik/MonicOra/blob/master/Pictures/MonicOra.png)
<!-- Short description of the project. -->

![Optinvent](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Optinvent.png)

MonicOra GW is an application GW for Smart Glasses made by Optinvent.

<!-- A teaser figure may be added here. It is best to keep the figure small (<500KB) and in the same repo -->



# Quickstart for first use
<!-- Instruction to make the project up and running. -->

## Setting up the server
a) Run RSAKeyGenerator once. It will generate two RSA key.
b) Put opt_privKey.key with MonicOraServer.jar in the same folder.
c) Run MonicOraServer (you can also use java –jar MonicOraServer.jar in command line so it generate debug
messages).
d) Close the server. You know have generated config_MonicOra.txt and MonicaServerRes.
e) Edit config_MonicOra.txt (keep the formatting in place): change PORT and IP according to your network.

## Setting up the App
a) Install the app according to the How_to_install_the_app.pdf (ADB or Android Studio)
b) Check that Wi-Fi is connected
c) Check that Location is activated
d) Run MonicOraAppConfigurator.jar
e) Change the IP and PORT value, if using the ACGAM R1 on the glasses, leave default value for the others field.
f) Connect the glasses over USB, and on the glasses select in the top-left corner « Activate USB ».
g) Press Make Config and save it inside the Document folder of the glasses.
h) Put the opt_pubKey.key generated in 1)a) inside the Document folder of the glasses.

## Run the Server and the App
a) Check on the splashscreen if the defined name and the IP/PORT is correct.
b) Check possible error message and report to the next slide.

The project documentation is available on the [Wiki](https://github.com/MONICA-Project/template/wiki).



# Error handling
## - FAQ Server

### How can I know my IP / PORT ?
Running on a local network, your IP can be accessed in various way. It usually start with 192.168. or 172.16. On windows, Press Windows Key + R, type cmd,
execute. Inside the console, type ipconfig. On linux, type ifconfig on a terminal. The port represents somehow where the data will be delivered on this IP, some firewall prevent some port to be used, or some application can already use this port. You can choose default (9999) or select another one.

### I can’t get the MonicOraServer.jar starting :
This can be caused because you don’t have permission to write/read files or because there is no opt_privKey.key file within the folder. Start the
java application through command line to see the logs (java –jar MonicOraServer.jar).

### The error « Can’t init the server » is shown:
This error happens when the app can’t bind the server on the given parameter : Check that the IP/PORT values are OK.


# Error handling
## – FAQ App
### Getting the NO ENCRYPTION KEY :
This could be caused because the permission to access storage directory is not set or because the opt_pubkey.key file is not found.

### Getting the Not Connected :
The app can’t connect with the server. Check the IP in the config file, the IP/PORT of the Server and network infrastructure configuration (ping for visibility).

### The server zoom into the sea on the map :
Check that GPS is turned on on the glasses. Set the option to “High”.

### Getting the BAD ENCRYPTION KEY :
Either the app has closed during initialization, either the public key is not related with the server private key.




# How to install APP on the ORA-2
<!-- Deployment/Installation instructions. If this is software library, change this section to "Usage" and give usage examples -->

## Recommendation

As using the touchpad and wearing the glasses for configuration is not convenient, we recommend to use the Vysor application that let you mirror the screen and interact through the mouse and keyboard. No purchase is required : https://www.vysor.io/

The settings and storage menu are accessible by swipping at top and left corner :

![Storage_menu](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Storage_menu.png)

Settings -> Developer option:
USB Debugging = « On »
Verify App over USB = « Off » (should be unnecessary
but still try with more permission).
Settings -> Location :
Mode set to « High Accuracy ».

![Settings](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Settings.png)

### Check the storage of the device

In order to install the app, a minimum storage capacity is needed.
We recommend to uninstall app that you don’t use, so that during the installation, 100 MB free space are available.

![Internal_storage](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Internal_storage.png)


## Method 1 : Android Studio - Recommended
If you have Android Studio installed, you can easly open the MonicOra .apk and install it :

![Android_studio](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Android_studio.png)

Find and select the MonicOra_App.apk

![Select_APK](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Select_APK.png)

If asked, select Create a new folder for a clean installation.

![Create_new_folder](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Create_new_folder.png)

Connect the ORA-2 on USB, the device must be turned on. Press the play button and select Chipsip Ora2.

![Connect_ORA-2](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Connect_ORA-2.png)

If no error is shown, the application is now installed on the device and should be launched.
If any errors shows up, you can try to correct what went wrong or send us the error message, a bunch of possible issues are described later.

## Method 2 – Using ADB
Connect the device on USB
Check the PATH of your machine so that it can access the adb command.
Check on the glass the permission
Use a terminal to run the command : adb install
PATH_TO_THE_APP/Monicora_App.apk
On Windows, using cmd or powershell :

![PowerShell](https://github.com/jbenadik/MonicOra/blob/master/Pictures/PowerShell.png)


# Possible errors
We found that because lack of free spaces even if the app is not big enough, installation may fail. Free some space may solve the problem.
Check permissions, so that the device allows debug mode.
Check your Android Studio Version.
Check your ADB version.


## Reseting to factory for cleanest installation

![Resetting](https://github.com/jbenadik/MonicOra/blob/master/Pictures/Resetting.png)

On the settings menu, find and select Factory Reset. Check Reset with SD CARD.
Wait 30s.
Restart the device to complete the reset.
Retry the installation of the App.
If errors persist, please attach the error message.

## Contributing
Contributions are welcome. 

Please fork, make your changes, and submit a pull request. For major changes, please open an issue first and discuss it with the other authors.

## Affiliation
![MONICA](https://github.com/MONICA-Project/template/raw/master/monica.png)  
This work is supported by the European Commission through the [MONICA H2020 PROJECT](https://www.monica-project.eu) under grant agreement No 732350.

> # Notes
>
> * The above templace is adapted from [[1](https://github.com/cpswarm/template), [2](https://www.makeareadme.com), [3](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2), [4](https://github.com/dbader/readme-template)].
> * Versioning: Use [SemVer](http://semver.org/) and tag the repository with full version string. E.g. `v1.0.0`
> * License: Provide a LICENSE file at the top level of the source tree. You can use Github to [add a license](https://help.github.com/en/articles/adding-a-license-to-a-repository). This template repository has an [Apache 2.0](LICENSE) file.
>
> *Remove this section from the actual readme.*
