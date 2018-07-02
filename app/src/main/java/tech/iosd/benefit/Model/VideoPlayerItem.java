package tech.iosd.benefit.Model;

public class VideoPlayerItem {

    public static final int TYPE_FOLLOW=1000;
    public static final int TYPE_REPETITIVE =1001;

    //type = repetitive workflow
    // Tutorial ----> singleVideo (repeated for total reps) ----> Rest --\
    //                         ^----(looped for number of sets)----------/

    //type = follow workflow
    // Tutorial ----> Rest ------------\
    //    ^----Looped for total sets---/


    //common..............
    private int type,sets;
    private String videoName;
    //rest info
    private int restTimeSec;

    private int totalReps;

    private String introVideo,singleRepVideo;
    private String tutorialVideo;


    public String getIntroVideo() {
        return introVideo;
    }

    public void setIntroVideo(String introVideo) {
        this.introVideo = introVideo;
    }

    public String getSingleRepVideo() {
        return singleRepVideo;
    }

    public void setSingleRepVideo(String singleRepVideo) {
        this.singleRepVideo = singleRepVideo;
    }

    public String getTutorialVideo() {
        return tutorialVideo;
    }

    public void setTutorialVideo(String tutorialVideo) {
        this.tutorialVideo = tutorialVideo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getRestTimeSec() {
        return restTimeSec;
    }

    public void setRestTimeSec(int restTimeSec) {
        this.restTimeSec = restTimeSec;
    }

    public int getTotalReps() {
        return totalReps;
    }

    public void setTotalReps(int totalReps) {
        this.totalReps = totalReps;
    }
}
