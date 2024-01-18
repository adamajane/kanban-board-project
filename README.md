# About the project
This project is an exercise in coordination challenges in distributed applications as part of the course Introduction to
Coordination in Distributed Applications (02148).
We have created a text-based Kanban board that we call KanPlan.

# Authors
Artur Barcij - s216217@student.dtu.dk\
Niclas Schæffer - s224744@student.dtu.dk\
Alexander Vaaben - s214958@student.dtu.dk\
Mark Bidstrup - s215782@student.dtu.dk\
Adam Ajane - s211048@student.dtu.dk

# How to run
It is recommended to run the program with IntelliJ IDEA.

## Cloning repository to IntelliJ from GitHub
1. On the repo start page, click "Code" in the top right-hand corner
2. Copy the HTTPS or SSH URL
3. Open IntelliJ on your computer
4. Click "Get from VCS" in the top right-hand corner
5. Paste the URL and clone the repo

## Importing the project to IntelliJ from a ZIP file
1. Download the ZIP file from the repo start page, under "Code"
2. Unzip the file
3. Open IntelliJ on your computer
4. Click "Open" and select the ZIP file

## Running the program
To run the program, please ensure you have set up the correct IP address in the `Config` class.
Any other collaborators must add the same IP address to their `Config` class.
Then, the host first runs `serverMain` and the clients run `clientMain`.

If you are running the program on the same computer as the server, you can run the program as localhost using
`127.0.0.1`.
Similarly, first run `serverMain` and then run `clientMain`.
