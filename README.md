# A* Search Algorithm
This project aims to implement the A* searching algorithm for a fictional problem where a spaceship needs to find the shortest path between two starts in a galaxy. The spaceship can only go a maximum distance D before needing to refuel and can refuel at any star.

## Usage
To compile run:
```
javac *.java
```
To perform a search:
```
java Stars [galaxy_csv_filename] [start_index] [end_index] [D]
```
Where start index is the row in the csv file which corresponds the starting point in the x,y format, end index is the row which corresponds to the end point in the x,y format and D is the maximum distance between two stars in the path.

The input dataset must be in the positive x,y plane from 0 to 100 in both dimensions and can include decimal values. 

## Output
There is both an output to standard out as well as a window which will graph the stars and the path between start and end.

### Standard output
This includes the best path found represented as a block of csv data (or a message stating there is no solution), the f value of that path and the total time taken to perform the search. Additionally, every one second the frontier size will be reported for monitoring.
```
Frontier size: 0
Best path found:
5.62,40.66
2.01,41.46
f value:3.697580289865253
Search took 4ms to run.
```

### Windowed Output
Below is the output for the path between point 3 and 61 with a D of 9 using the spiral galaxy dataset. As you can see from the image it includes a window containing all the stars represented as yellow circles labelled in accordance with their row within the dataset and the best path between start and end represented by a red line.
![Example output using the spiral galaxy dataset.](./img/Spiral%20Example%20Output.png "Example output using the spiral galaxy dataset.")