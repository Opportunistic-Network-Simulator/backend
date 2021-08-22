# Usage

Currently, the simulator is only available for use through the executable jar. It can run in whatever OS.

## Executable Jar
It is need java version 8 in your computer.

Download the IONS-Simulator.zip [here](https://github.com/PFC-IME-Opportunistic-Network-Simulator/backend/releases/download/1.0/IONS-Simulator.zip):

Unzip the file, open a terminal in CLI (Command Line Interface) folder and run:

```
java -jar CLI_IONS_Simulator.jar -i <config file> -o <output file name>
```
The config file must to bem located at input folder and it must be passed just the file name wiht its extension (without any path). This is a [toml file](https://github.com/toml-lang/toml) that has all information needed to configure the simulation scenario.

The information needed is: 

### Number of Rounds
A interger number to determinate the number of times the simulation will run with the specified scenarion, in order to get all results and make the average.

### Message Generation

It determines the message generation that will be used. There are 3 parameters:

- #### type: There are 3 avaiable types:
  - FIXED_NODES: One node specified by the user will have a message whose destination is other node specified by the user. That is, there is just one message in all simulation.
  
  - RANDOM_NODES: One random node will have a message whose destination is other random node. That is, there is just one message in all simulation.
  
  - ALL_PAIRS: Each node will have n-1 messages, each one to a other node destination. That is, there are n*(n-1) messages in the simulation, since node i will have a messagem to node j and j will have a message to node i.

- #### Generation Instant:
A double number to determinate in which instant the all messages of simulation will be generated in the respectives source nodes.

- #### Amount Of Nodes:
A integer number to determinate the total number of node in the simulation.


### Protocol

It determines the fowarding protocol tha will be used in the simulation. It may be have 4 parameters:

- #### type: There are 6 avaiable protocol types:
  - EPIDEMIC
  - SINGLE_COPY_EPIDEMIC
  - EPIDEMIC_P_Q
  - SPRAY_AND_WAIT
  - BINARY_SPRAY_AND_WAIT
  - DIRECT_DELIVERY

- L: It means the L-parameter (number of messages copies) of SPRAY_AND_WAIT and BINARY_SPRAY_AND_WAIT protocols. So, it must be informed only for use of SPRAY_AND_WAIT and BINARY_SPRAY_AND_WAIT protocols.

- p: It means the p-parameters (probability to occour a message trasnfer between two relay nodes) of EPIDEMIC_P_Q protocol. So, it must be informed only for use of EPIDEMIC_P_Q protocol.

- q: It means the p-parameters (probability to occour a message trasnfer between the source and any relay node) of EPIDEMIC_P_Q protocol. So, it must be informed only for use of EPIDEMIC_P_Q protocol.

### Meeting Trace

It determines how the meet between pairs will be generated. There are 3 parameters:

- #### type: 
Only EXPONENTIAL type is avaiable. That is, the interval between meet for each pair will be modelated by a exponential probability distribution and each interval until next meet will be calculated using the Inverse Transform Sampling method using the meet rate of each pair how the λ-parameter.

- #### pairDefinitionFile:
Name of json file that contains the meet rate of each pair. This file must be located at pairDefinitionFile folder, inside input folder.

- #### totalSimulationTime: 
A double number to determine the total simulation time

### Test
In the dowloaded zip file, there are the configExample.toml (inside input folder) and the pairsExample.json (inside pairDefinitionFile folder) files to test.

Open a terminal in executableJar folder, inside IONS-Simulator folder and run:

```
java -jar IONS-simulator.jar -i configExample.toml -o example
```

So, it will be generated a outupt folder with the example.txt, containing the simulation result (Delivery ratio and Average delay).
