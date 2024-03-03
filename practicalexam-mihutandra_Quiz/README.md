# APM - Practical Examination Repository
Quiz
The goal is to implement a simplified quiz application allowing the user to answer questions. Each Question has an id (unique), a text, a correct answer, a score and the user's answer. In the beginning none of the questions have a user's answer. Input at least the following 5 items in your database table:
1, What nut is used to make marzipan?, almond, 21,
2, What element does O represent in the periodic table?, oxygen, 30, "
3, What is the highest mountain on Earth?, Everest, 42, '
4, What year did World War Il end?, 1945, 35, "'
5, What is the capital of France?, Paris, 10, '
Write an application with a graphical user interface (use JavaFX) which allows to:
1. Visualize all unanswered questions in a list. The list will display the id, the text and the score for each question (2p), sorted by score (1p). When the application starts, the list is populated automatically.
2. Allow the user to search questions. The user inputs the text and a minimum score and the list is refreshed, containing only the questions whose text match the searched text (the matching should not be exact) and whose score is greater than the given score. Use Java streams for this filtering. (2p) If you do not use Java streams, the maximum score is 1p.
3. The user can answer the selected question, by writing the answer in an editable control.
   Once a question is answered, the user's answer is also updated in the database (1.5p).
4. The user's total score is shown in the window. The total score is updated as the user answers questions. For each correct answer the total score is increased by the answered question's score (1p). The user can ask for a hint, by pressing a "Hint" button. The shown hint will be the first 2 letters from the correct answer (1p). In case a hint was asked, the score for is halved (0.5p).
   Observations:
   • The application must use a layered architecture (domain, repository, service, ui).
   • The application must read the data from a relational database.
   • If any of the above observations is not followed, the maximum score for each requirement is 50% of the indicated score.
   1p-of
   Allotted time: 70 minutes.