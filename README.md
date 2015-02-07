# java-LinkChain
Java program that will try to find chain of links between two URLs. This is an eclipse project, so I would suggest importing it. 

### Background
Written for my artificial intelligence class, this was meant to be an exploration of finding solutions by search. We were to write a program 
that takes two urls and finds a path of links from one to other. 

### Usage
The program will take two urls and depth as input. It will then perform a depth first search of all the links found starting at 
the first url, up to the specified depth. The search will terminate when the second url is found or there are no more urls to check within the search space. 
If the second url is found within the search space, the chain of links from the first url to the second will be printed. 
