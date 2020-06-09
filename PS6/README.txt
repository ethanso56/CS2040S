- Open the ps6 folder (the one that this README is in) as an IntelliJ Project (this should have been configured)
  - If not, File -> Close Project
  - Import Project and simply keep pressing "next" to configure the ps6 folder as an IntelliJ project

- If IntelliJ complains "cannot resolve symbol JUnit" for the test classes:
  - Import the required test libraries
  - Go to File -> Project Structure
  - Under libraries, add a two new libraries: hamcrest-core-1.3 and junit-4.12
  - These libraries can be found in your IntelliJ installation directory

- The other .txt files such as Alice.txt, hamlet.txt, etc and PS6Test.in are used for you to try the TextGenerator class