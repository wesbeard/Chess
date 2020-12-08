#    Chess

-----------------------------------

###  by Wes Beard & Michael Leonard
#### CSI-340
#### 12/8/2020

-----------------------------------
## Setup Instructions

 - We wrote this used IntelliJ, so we recommend using that as your IDE
 - There are multiple libraries required for Processing to function, allowing us to draw to the screen, play sounds, etc  
 - The .jar files for these libraries can be found in the "Required Libraries" folder
 - To install these in IntelliJ simply right click them and select "Install as library" from the bottom of the list
 - You can group install them all by ctrl+clicking to select all of them at once
 - You may need to right click inside of main.Chess and select "run" to load it as a configuration for a first time run
 
    We know it's  a complicated project so let us know if there are any problems!
 
 ## Extras

 - You can change the color theme of the board by simply change the variables `DARK` and `LIGHT` inside the colors section of Constants.java
 - You can also change the piece appearance by changing the variable `pieceSet` inside Piece.java to any of the other pieceset folder names   
   
    Note: Not all piece sets work due to SVG parsing errors, so some may not appear correctly or throw an exception when run