/*
 * Wes Beard - wesley.beard@mymail.champlain.edu
 * Michael Leonard - michael.leonard@mymail.chamamplain.edu
 * CSI-340 Final Project
 * 12/7/2020
 *
 * Written by Wes Beard
 *
 * This file represents an interactive button, storing its
 * dimensions, text, appearance, etc as well as to check
 * if the button had been clicked by the user
 */

package interactivity;

import java.awt.*;

public class iButton {

    public int x;
    public int y;
    public int height;
    public int width;
    public String text;
    public String id;
    public Color color;
    public boolean hidden;

    public iButton(int x, int y, int height, int width, String text, String id, Color color, boolean hidden) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.text = text;
        this.id = id;
        this.color = color;
        this.hidden = hidden;
    }

    public boolean clicked(int mouseX, int mouseY) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        if (mouseX >= x - halfWidth && mouseX <= x + halfWidth && mouseY >= y - halfHeight && mouseY <= y + halfHeight) {
            return true;
        }
        return false;
    }
}
