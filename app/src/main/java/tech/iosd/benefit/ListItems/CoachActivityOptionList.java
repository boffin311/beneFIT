package tech.iosd.benefit.ListItems;

import java.util.ArrayList;

/**
 * Created by kushalgupta on 31/12/17.
 */

public class CoachActivityOptionList {
    String topic;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public static ArrayList<String> getList(){

        ArrayList<String> topicList = new ArrayList<>();

        topicList.add("Nutrition Plan");
        topicList.add("Fitness Plan");
        topicList.add("Fitness and Nutrition Plan");
        topicList.add("Body Composition Analysis Plan");

        return topicList;
    }

}
