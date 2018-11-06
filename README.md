[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=plastic)](https://android-arsenal.com/api?level=21)
# TumblrViewer 
*Android prototype Thumblr browser*

## What is TumblrViewer 
TumblrViewer is an android app with core features:
* show choosen blog
* search blog by name
* store favourite blog
* remember last successful search
* load random favourite blog

## What was used to build app
To code and build this app was used:

* Kotlin
* Android Studio 3.2

**Pattern**
* MVI ( Model-View-Intent) with <a href="https://github.com/sockeqwe/mosby" title="Mosby">Mosby</a> + Clean

**Libraries**
* <a href="https://github.com/sockeqwe/mosby" title="Mosby">Mosby</a>
* <a href="https://github.com/ReactiveX/RxKotlin" title="RxKotlin">RxKotlin</a>
* <a href="https://github.com/JakeWharton/RxBinding" title="RxBinding">RxBinding</a>
* <a href="https://github.com/google/dagger" title="Dagger">Dagger</a>
* <a href="https://github.com/square/okhttp" title="okHttp">okHttp</a>
* <a href="https://github.com/square/retrofit" title="Retrofit">Retrofit</a>
* <a href="https://github.com/bumptech/glide" title="Glide">Glide</a>
* <a href="https://developer.android.com/topic/libraries/architecture/room" title="Room">Room</a>

**Tests**
* JUnit, Mockito; Local tests - MVI business model layers ( Interactors and storage); Android tests - Room database

**Tested on**
* Nexus 4
* API version 22

## How does it look like
App is splitted into three different sections.

Navigation between activities and fragments is done through BottomNavigationView | Home view section. Consists of randomly loaded blog on start. Can be refreshed by Swipe Layout drag with random load from favourites blog.
:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/13487803/48045019-df22b700-e18e-11e8-9db9-d46ad3194771.png" title="nav drawer" height="250" />  |  <img src="https://user-images.githubusercontent.com/13487803/48045018-de8a2080-e18e-11e8-9934-749d8f239210.png" title="main view" height="250" />

Search section. Loads last successful search on init, user searches for blog in here. | Favourites section. Represents favourites blogs stored in Room. These blogs are loaded on init or refresh home screen.
:-------------------------:|:-------------------------:
<img src="https://user-images.githubusercontent.com/13487803/48045017-de8a2080-e18e-11e8-8e92-34debf12cc6d.png" title="recording session" height="250" />  |  <img src="https://user-images.githubusercontent.com/13487803/48045016-de8a2080-e18e-11e8-8ca7-7eb39953d5db.png" title="details section" height="250" />

## REST communication - addition
Only one endpoint from https://www.tumblr.com/docs/en/api/v1 was used to establish communication between app and servers.

Reason for that is Tumblr docs are extremly outdated when relating to api v1. From misleading description of returning JSON format ( acually JSON wrapped by javascript object), through non-standard place of blog name in url ( retrofit url conflict), outdated optional params of path/query ( most don't work), and ending with very chaotic json structure ( no single rule when coming to type of objects returned when calling blog or post).

Most of these problems were resolved through additional network interceptors, multiple types of post viewholders, parsers and storages.
