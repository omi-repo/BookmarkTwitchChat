# BookmarkTwitchChat

This project is an Android app for bookmarking Twitch chat the user likes. Other function maybe will
be added in the future. And to practice using WebSocket and Twitch chat WSS API.

## Getting Started

Read the notes to add your Github Personal Authenticated Token.

## Images

|<img src="https://raw.githubusercontent.com/omi-repo/BookmarkTwitchChat/master/images/Screenshot_1631524323.png" alt="1" width="200"/>
|<img src="https://raw.githubusercontent.com/omi-repo/BookmarkTwitchChat/master/images/Screenshot_1631524361.png" alt="1" width="200"/>
|

## Libraries Used

- [nv-websocket-client](https://github.com/TakahikoKawasaki/nv-websocket-client) - is a High-quality
  WebSocket client implementation in Java which.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - is Android’s modern toolkit for
  building native UI. It simplifies and accelerates UI development on Android. Quickly bring your
  app to life with less code, powerful tools, and intuitive Kotlin APIs.

## Notes

If you want to compile and run this project, don't forget to create a file (called keys.properties)
in the root of project containing [Github's API keys](https://github.com/settings/applications/new):

    GithubClientId="YOUR_ID"
    GithubClientSecret="YOUR_SECRET"
    Token="YOUR_PRIVATE_AUTHENTICATED_TOKEN"

License
-------

    Copyright 2021 Romi

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.