/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file contains all constants relating to colors and sounds
 * so they can be accessed throughout the program
 */

package main;

import processing.sound.SoundFile;
import java.awt.*;

public final class Constants {

    //Lost piece coordinate
    public static int lostLightX = 1015;
    public static int lostLightY = 160;
    public static int lostDarkX = 1015;
    public static int lostDarkY = 670;
    public static int numLostDark = 0;
    public static int numLostLight = 0;
    public static int darkLostRows = 0;
    public static int lightLostRows = 0;

    // Colors
    public static Color WHITE = new Color(255, 255 ,255);
    public static Color BLACK = new Color(0, 0, 0);
    public static Color LIGHT = new Color(255, 255, 221);
    public static Color DARK = new Color(104, 136, 72);
    public static Color HIGHLIGHT = new Color(200, 100, 100);
    public static Color BACKGROUND = new Color(200, 200, 200);
    public static Color BUTTONEDGE = new Color(0, 0, 0);
    public static Color GREY = new Color (150, 150, 150);

    // Sounds
    public static SoundFile MOVESOUND;
    public static SoundFile TAKESOUND;
    public static SoundFile CASTLESOUND;
    public static SoundFile VICTORYSOUND;
    public static SoundFile INVALIDSOUND;
    public static SoundFile CHECKSOUND;
}
