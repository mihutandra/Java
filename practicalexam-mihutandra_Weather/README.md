# APM - Practical Examination Repository
Weather
Write an application allowing you to see the weather for the current day. Each Time Interval is represented by its hours (start and end), temperature, precipitation probability and a description (e.g. "sunny", "rainy", "cloudy"). Input at least the following 5 items in your database table:
12; 14; 18; 23; sunny, wind 5km/h
6; 9; 10; 13; foggy
9; 12; 13; 7; cloudy, wind 2km/h
18; 20; 20; 78; rainy, heavy rain
14; 18; 20; 62; rainy, wind 10km/h, real feel 18C
Write an application with a graphical user interface (use JavaFX) which allows to:
1. Visualize all time intervals in a list. The list will display the time interval, the temperature, the precipitation probability and the description (2p), sorted by the start hour (1p). When the application starts, the list is populated automatically.
2. A combo box will show all possible descriptions found in the table. Consider only the first word of these descriptions (e.g. "sunny", "cloudy", "foggy") (1.5p). When selecting a description from the combo box, the list will be filtered accordingly and sorted by the start hour (1.5p). Use Java streams for this operation (filter and sort). If you do not use Java streams, the maximum score is 0.75p.
3. For a chosen interval (clicked in the list), the user can update its precipitation probability and description and the modifications will be saved in the database (1.5p).
4. Compute the total number of hours, for a given input word (e.g. how many hours of
   "wind" will there be today?). Input the word and show the total number of hours for all intervals whose description contain that word (1.5p). For the items given in the example,
   for the word "wind" the total number of hours is (12-9) + (14-12) + (18-14) = 9.
   Observations:
   • The application must use a layered architecture (domain, repository, service, ui).
   • The application must read the data from a relational database.
   • If any of the above observations is not followed, the maximum score for each requirement is 50% of the indicated score.
   1p - of
   Allotted time: 70 minutes.
   1p - of