package com.tm470.mb24853.projectlluca;

/**
 * Created by Admin on 06/04/2015.
 */
public class deckpartClass {

    private int deckpart_id;
    private String deckpart_name;
    private int deckpart_box_id;
    private String deckpart_cycle;
    private String deckpart_box;
    private int deckpart_parent;

    public deckpartClass()
    {
        //empty constructor
    }

    public deckpartClass(int id, String name, int box_id, String cycle, int parent)
    {
        this.deckpart_id = id;
        this.deckpart_name = name;
        this.deckpart_box_id = box_id;
        this.deckpart_cycle = cycle;
        this.deckpart_parent = parent;
    }

    public void setDeckpart_id(int id)
    {
        this.deckpart_id = id;
    }

    public void setDeckpart_box(String deckpart_box) {
        this.deckpart_box = deckpart_box;
    }

    public void setDeckpart_cycle(String deckpart_cycle) {
        this.deckpart_cycle = deckpart_cycle;
    }

    public void setDeckpart_box_id(int deckpart_box_id) {
        this.deckpart_box_id = deckpart_box_id;
    }

    public void setDeckpart_name(String deckpart_name) {
        this.deckpart_name = deckpart_name;
    }

    public int getDeckpart_id()
    {
        return deckpart_id;
    }

    public String getDeckpart_name()
    {
        return deckpart_name;
    }

    public int getDeckpart_box_id()
    {
        return deckpart_box_id;
    }

    public String getDeckpart_cycle() {
        return deckpart_cycle;
    }

    public String getDeckpart_box() {
        return deckpart_box;
    }

    public int getDeckpart_parent() {
        return deckpart_parent;
    }

    public void setDeckpart_parent(int deckpart_parent) {
        this.deckpart_parent = deckpart_parent;
    }
}
