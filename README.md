Muvi :construction_worker::hammer:
=====
[![Build Status](https://travis-ci.com/andriiginting/Muvi.svg?branch=master)](https://travis-ci.com/andriiginting/Muvi)
[![codecov](https://codecov.io/gh/andriiginting/Muvi/branch/master/graph/badge.svg?token=JGB5AOHJRF)](https://codecov.io/gh/andriiginting/Muvi)
 
 <p align="center">
 <img src="/screenshot/Muvi-banner.png"/>
 </p>
 
Very simple project to show collection of Movie from [MovieDb](https://developers.themoviedb.org) with minimalist design.

Main Feature
-------------
* Showing List of Movie
* Showing detail movie and similar movie
* Showing your favorite movie
* Shuffle Banner
* Filter based on Category
* Search your favorite Movie

Tech Stack
----------
* AndroidX
* Kotlin
* RXJava
* Retrofit for network request
* Room
* Glide
* Mockito for unit testing
* Dagger for DI

Architecture
-----------
<p align="center" width="40%">
 <img src="/screenshot/design-arch.png"/>
</p>
 
In this project I'm using MVVM - Clean Architecture. By implementing clean architecture, it will give us :  
- clean separation of concern; making your code easier to navigate and maintain
- easier to test the code

How to use it?
------
- Clone this project using `git clone [url]`
- build the project by using `./gradlew clean :app:assembleDebug`
- You can run the Unit test by using `./gradlew clean test`

*Clean Architecture will not be appropriate for every project, so it is down to you to decide whether or not it fits your needs*
