package tech.iosd.benefit.Model;

import java.util.ArrayList;

/**
 * Created by SAM33R on 28-06-2018.
 */

public class Workout {
    private String _id;
    private String name;
    private String search_name;
    private String description;
    private ArrayList<Exercise> exercises;

    public Workout(String _id, String name, String search_name, String description, ArrayList<Exercise> exercises) {
        this._id = _id;
        this.name = name;
        this.search_name = search_name;
        this.description = description;
        this.exercises = exercises;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
