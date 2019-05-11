# Sign Language
The Sign Language app is an Android application which can translate static ASL and BSL signs, such as the fingerspelling alphabet. These translated signs can be displayed to the user whilst allowing for sentences to be constructed. This app is currently a proof of concept to illustrate low-cost, freely available and offline Sign Language recognition using purely visual data.

* https://github.com/Mquinn960/offline-trainer
* https://github.com/Mquinn960/dataset-creator

The current beta version of this app can be tested here:
* https://play.google.com/store/apps/details?id=mquinn.sign_language

**(Click the image below to watch the video demo)**
[![Alt text](/Preview.png?raw=true "Preview")](https://youtu.be/8Ta_CMhd454)

## For a comprehensive step by step guide on using these applications and some additional info on how they work, please see [my new help repo](https://github.com/Mquinn960/sign-language-help)

## Getting Started

* Clone the repo onto your local machine by using:

    HTTPS: ```git clone https://github.com/Mquinn960/sign-language.git```
    
    SSH: ```git clone git@github.com:Mquinn960/sign-language.git```
   
* Ensure the prerequisites below are installed/satisfied
* If you're using Android Studio, load the project and hit run

### Prerequisites

* Load in a trained SVM XML file created using the Offline Trainer
  * https://github.com/Mquinn960/offline-trainer
  * You must place the file (named ```trained.xml```) in the  ```sign-language\app\src\main\res\raw\``` directory. This gets loaded when running the app the first time.
  * Note: If you're just looking for a quick start, then  you can download a sample ```trained.xml``` **[here](https://drive.google.com/open?id=12qacJUjiKk5l_TmHY-olxb72GG3Je7CI)**
* Ensure you have an Android smartphone connected to your computer, and that [debugging](https://developer.android.com/studio/debug/dev-options) is enabled
* It is recommended that you use the Android Studio IDE noted below

### Development Environment

* Created using [Android Studio](https://developer.android.com/studio)

## User Guide

### For a comprehensive step by step guide on using these applications and some additional info on how they work, please see [my new help repo](https://github.com/Mquinn960/sign-language-help)

* Using this app requires the use of a ```trained.xml``` file, which contains the Machine Learning information required to make predictions about your Sign Language gestures
* Follow the instructions found in the Offline Trainer repo to create this file from input image training data you create using the Dataset Creator app, or find online - for more info see the Offline Trainer repo
    * https://github.com/Mquinn960/offline-trainer
    * https://github.com/Mquinn960/dataset-creator
* Once the ```trained.xml``` file has been added to the app ```raw``` resources folder as per the Prerequisites
* Start the app, and point the smartphone camera at a person performing Sign Language alphabet gestures
* Use the onscreen buttons to capture the translated gestures

### Exporting the imaging kernel

If you want to alter the Sign Language app and then use the Sign Language app's imaging kernel to train a new model with the Offline Trainer, you must first run the Grade ```make-jar``` task.

* Edit the app's Gradle build file ```sign-language\app\build.gradle```
* Comment out the entire ```com.android.application``` build step
* Uncomment the ```com.android.library``` task
* Perform a Gradle sync
* Run the ```make-jar``` Gradle task
* Find the exported imaging kernel ```sign-language\app\build\outputs\jar\app-release-null.jar``` and import this into the Offline Trainer's "new" folder, as described in the repo README
  * https://github.com/Mquinn960/offline-trainer
* (Optional) Run any training you want to do in the Offline Trainer, then take the trained SVM XML file and import it into the Sign Language app as descibed in the User Guide above
* Undo steps 2 and 3 and run the main ```app``` task again.

## Built With

* [Java 8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - Java Programming Language
* [Android](https://www.android.com/) - Android OS
* [Gradle](https://gradle.org/) - Gradle build system
* [OpenCV](https://opencv.org/) - The Open Source Computer Vision Library 

## Contributing

* Feel free to submit issues to this repository but please include usable information if you are looking to have something fixed
* Use [Feature Branching](https://www.atlassian.com/git/tutorials/comparing-workflows/feature-branch-workflow) where possible
* Submit Pull Requests to @Mquinn960 for review

## Authors

* **[Matthew Quinn](http://mquinn.co.uk)**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
