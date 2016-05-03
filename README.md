# Hardware
A toast module that makes accessing robot hardware easier

This module provides wrappers around wpilib hardware classes.
The wrappers are intended to make it easier to get a robot working without spending lots of time messing with configuration.

The module is designed with the injection philosophy that an object should be initialized with all of the information that it
needs to work.
A programmer using the library wont need to keep track of what state hardware components are in manually. 
