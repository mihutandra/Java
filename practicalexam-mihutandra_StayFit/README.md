# APM - Practical Examination Repository
Stay fit
Implement an application that allows monitoring a person's fitness. For each obv, the application stores the total number of steps, hours of sleep, and a list of phsical octhties with their associated move minutes. Input at least the following 5 items in vour database table:
2023.01.28, 3256, 7.5, morning_walk 12; afternoon_walk
2023.02.04,
3660, 6, afternoon_walk 35
2023.02.13, 364, 6,
2023.02.10, 259,
6.35 , swimming 30
2023.01.18, 2580, 8.5, morning_walk 25; gomnastics 60
Write an application with a graphical user interface (use JavaFX) which allows to:
1. Visualise all activities in a list, with all their information (2p), sorted ascending by date (1p). When the application starts, the list is populated automatically.
2. Allow the user to filter activities, by showing only those for which the total number of move minutes is greater than a given value. Use Java streams for this filtering (2p). If you do not use Java streams, the maximum score is 1p.
3. Add a new activity, by giving the date, number of steps, hours of sleep, physical activity and move minutes (1p). These changes will be saved to the database (1.5p).
4. Show the total number of steps for a given month. The total number of steps is updated when a new activity for the given month is added (1.5p).
   Observations:
   • The application must use a layered architecture (domain, repository, service, ui).
   The application must read the data from a relational database.
   • If any of the above observations is not followed, the maximum score for each requirement is 50% of the indicated score.
   1p - of
   Allotted time: 70 minutes.