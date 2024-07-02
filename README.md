[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

# Weather Application

![Demo](./demo/five-day.gif)


<!-- Project Overview -->

## Project Overview
This is an Android application designed to provide real-time weather updates and forecasts for cities around the world. It leverages the [OpenWeatherMap API](https://openweathermap.org/api) to fetch weather data, offering features such as current weather conditions, a five-day forecast, and management of user-saved cities.

<!-- Table of Contents -->

## Table of Contents
* [Features](#features)
* [Built With](#built-with)
* [Architecture and User Interface](#architecture-and-user-interface)
* [Setup and Installation](#setup-and-installation)
* [License](#license)

<!-- Features -->

## Features
* **Current Weather Display**: Shows temperature, humidity, wind speed, and more for the user's current location or selected cities.
* **Five-Day Weather Forecast**: Provides a detailed forecast for the next five days.
* **Save Favorite Cities**: Users can save their favorite cities for quick weather checks.
* **Settings**: Allows users to customize app settings, including temperature units.

<!-- Built With -->

## Built With
- [![Android][Android]][Android-url]
- [![Kotlin][Kotlin]][Kotlin-url]
- **Libraries**
  - Retrofit
  - Room
  - LiveData and ViewModel
  - Material Design

<!-- Architecture and User Interface -->

## Architecture and User Interface
The application adopts the Model-View-ViewModel (MVVM) architectural pattern, facilitating a robust separation of concerns and enhancing the maintainability and scalability of the codebase.

### Activities
- **MainActivity**: Integrates the navigation component and serves as the host for various fragments, providing a seamless user navigation experience.

### ViewModels
- Each ViewModel interacts with repositories to fetch and manage data, offering an abstraction layer that prepares data for UI consumption, responding to user interactions from the Fragments.

### Fragments
- **CurrentWeatherFragment** and **FiveDayForecastFragment** deliver weather data to users, utilizing LiveData to reactively update the UI based on data changes or user actions.
- **AddCityDialogFragment** offers a user-friendly interface to augment the list of monitored locations.
- **SettingsFragment** allows users to customize application settings, enhancing user satisfaction and personalization.

### Adapters
- **ForecastAdapter** dynamically adjusts to changes in the dataset, enabling the RecyclerView in **FiveDayForecastFragment** to display an updated list efficiently.

### Implementation Highlights
- LiveData and Data Binding are extensively used to update the UI reactively and minimize boilerplate code.
- The application integrates with the OpenWeatherMap API, handling data persistence with Room for offline data access and smoother user experiences.

<!-- Utilities and Helpers -->

## Utilities and Helpers

The application includes several utility classes that abstract and encapsulate common functionalities, reducing code duplication and simplifying maintenance.

### OpenWeatherUtils
- **Purpose**: `OpenWeatherUtils.kt` enhances the application by providing a set of static methods used for formatting and manipulating weather data obtained from the OpenWeatherMap API.
- **Key Functions**:
 - **Format Temperature**: Converts raw temperature data into a user-friendly format, optionally switching between Celsius and Fahrenheit.
  - **Date Formatter**: Parses and formats date strings from the API to display them in a readable format to the users.
  - **Build API URL**: Dynamically constructs API request URLs based on user inputs or saved preferences.

### Integration
These utilities are integrated throughout the application, particularly within repository and ViewModel classes, to process and prepare data for user interface rendering or further logic implementation.

<!-- Setup and Installation -->

## Setup and Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/yourusername/weather-app.git
   ```
2. Open the project in Android Studio
3. Create an account at [OpenWeatherMap API](https://openweathermap.org/api) to get an API key. Once you have the API key, copy and paste it in `gradle.properties` file.
4. Build the project
    - Navigate to `Build -> Rebuild Project`
5. Run the application:
    - Use an Android emulator or real device to run the application

<!-- License -->

## License
This project is licensed under the MIT license. See LICENSE.txt for more information.


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/kaung-myat-htay-win-258ab9251/

[HTML5]: https://img.shields.io/badge/html5-%23E44D26?style=for-the-badge&logo=html5&logoColor=white
[HTML5-url]: https://developer.mozilla.org/en-US/docs/Web/HTML

[CSS3]: https://img.shields.io/badge/css3-%23254DE4?style=for-the-badge&logo=css3&logoColor=white
[CSS3-url]: https://developer.mozilla.org/en-US/docs/Web/CSS

[JavaScript]: https://img.shields.io/badge/javascript-%23F0D91C?style=for-the-badge&logo=javascript&logoColor=white&logoSize=auto
[JavaScript-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript

[Bootstrap5]: https://img.shields.io/badge/bootstrap5-%238414F8?style=for-the-badge&logo=bootstrap&logoColor=white&logoSize=auto
[Bootstrap5-url]: https://getbootstrap.com/docs/5.0/getting-started/introduction/

[Node.js]: https://img.shields.io/badge/node.js-green?style=for-the-badge&logo=nodedotjs&logoColor=white&logoSize=auto
[Node-url]: https://nodejs.org/en

[Express.js]: https://img.shields.io/badge/express.js-000000?style=for-the-badge&logo=express&logoColor=white&logoSize=auto
[Express-url]: https://expressjs.com/

[MongoDB]: https://img.shields.io/badge/mongodb-4FAA41?style=for-the-badge&logo=mongodb&logoColor=white&logoSize=auto
[MongoDB-url]: https://www.mongodb.com/

[Redis]: https://img.shields.io/badge/redis-e30007?style=for-the-badge&logo=redis&logoColor=white&logoSize=auto
[Redis-url]: https://redis.io/

[RabbitMQ]: https://img.shields.io/badge/rabbitmq-ff6600?style=for-the-badge&logo=rabbitmq&logoColor=white&logoSize=auto
[RabbitMQ-url]: https://www.rabbitmq.com/

[JWT]:https://img.shields.io/badge/jwt-000000?style=for-the-badge&logoColor=white&logoSize=auto
[JWT-url]: https://jwt.io/

[Docker]: https://img.shields.io/badge/docker-blue?style=for-the-badge&logo=docker&logoColor=white&logoSize=auto
[Docker-url]: https://www.docker.com/

[AWS ECS]: https://img.shields.io/badge/aws_ecs-000000?style=for-the-badge&logo=awsfargate&logoColor=white&logoSize=auto
[AWS-url]: https://aws.amazon.com/ecs/

[Kotlin]: https://img.shields.io/badge/kotlin-%23C21EF0?style=for-the-badge&logo=kotlin&logoColor=white&logoSize=auto
[Kotlin-url]: https://kotlinlang.org/

[Android]: https://img.shields.io/badge/android-%233BD682?style=for-the-badge&logo=android&logoColor=white&logoSize=auto
[Android-url]: https://www.android.com/