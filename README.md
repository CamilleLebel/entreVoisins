# EntreVoisins
> This is an Android app that allows neihgbours to provide themselves with small service like animals care, private lessons, small DIY, many options are available to users !

## Table of contents
* [General info](#general-info)
* [Screenshots](#screenshots)
* [Setup](#setup)
* [Tests](#tests)
* [Status](#status)

## General info
This project is led by the company Entrevoisins

## Screenshots
<img src="/img/screenshot_app_1.png"
     style="float: left; margin-right: 10px;"
     width="200"/> <img src="/img/screenshot_app_2.png"
     style="float: left; margin-right: 10px;"
     width="200"/> <img src="/img/screenshot_app_3.png"
     style="float: left; margin-right: 10px;"
     width="200"/> <img src="/img/screenshot_app_4.png"
     style="float: left; margin-right: 10px;"
     width="200"/> 	

## Setup
- You have to use Android Studio, download it and intall it if you don't have it yet.
- Download the Project.zip and extract zip
- Open Android Studio then open Entrevoisins project
- To compile the entire project, click the "Build project" button
![build screen](/img/screenshot_ide_1_LI.jpg "build screenshot")
- To execute the project on a device just plug in your phone and click the "Run app" button
  (same thing for emulator but you have to download and install one first)
![build screen](/img/screenshot_ide_2_LI.jpg "run screenshot")
- To execute Tests, make sure you have a plugged device for instrumented tests, then right-click on the "app" floder then "Run all Tests"
![build screen](/img/screenshot_ide_3_LI.jpg "run tests screenshot")
  

## Tests
4 Unit tests :
- get the list of niehgbours with success
- delete neighbour with success
- add neighbour to favorite with success
- get favorites into favorite list of neighbours with success

4 Instrumented tests :
- recycler view not empty
- delete a niehgbour delete his item aswell
- clicking on an item should display his details
- when you add a neighbour to favorite, it should be diplayed in the favorites fragment

## Status
Project is:  _finished_
