# 50.001_1D_Proj


## Motivation

Singapore has a large number of recreational spaces in every neighbourhood that are free for everyone to use. These spaces include basketball courts, badminton courts, street soccer courts, and even playgrounds. However, the want to use these public spaces can be hindered by the lack of knowledge as to whether the space is occupied at any given time. This problem has yet to been solved even till today. Our project aims at solving this problem in an effective and convenient way.


## Our Solution

Our solution includes adding a small camera that overlooks each space. This camera, with the help of computer vision, will help identify the total number people using the facility at every given period of time. This information is then sent to a central database. The data is pulled by a phone application which can then be accessed by everyone with that application installed. Only the numerical capacity will be displayed on the app and not the pictures taken as to provide as much information as possible while still maintaning privacy.


## Implementation

As a proof of concept, we will be applying our solution on a smaller scale within our school campus. 

We will be using a small web camera to monitor two pool tables. A LattePanda will be used to simulate a back-end to carry out the people detection scripts and to push the final data to a common database. The camera will take a photo of the tables every 30 seconds. Once the detection is done on these photos, the detected number of people as well as the date and time is pushed to firebase.

The app consists of two main components:
- HomePage
- Public Space Page

---
### HomePage

This page is a tabbed activity, which consists of three tabs:
- Categories
- Available
- Favourites

#### Categories

This tab sorts all listed public spaces based on the intended type of usage. Examples include basketball courts or badminton courts.


#### Available

This tab lists all public spaces that are not at maximum capacity to serve as an easy method of filtering out spaces that are full.

#### Favourites

This tab lists the public spaces that have been favourited by the user before.

---

### Public Space Page

There is a different public space page for every public space, and provides information about the specific space such as the current capacity, the opening hours, the address, and the booking link if relevant.
