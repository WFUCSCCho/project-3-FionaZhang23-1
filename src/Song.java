/***********************************************************
 * @file: Song.java
 * @Description: This file contains the implementation of a constructor class of my dataset that initialized the variables inside the dataset.
 * @Author: Fiona Zhang
 * @Date: October 23, 2024
 ***********************************************************/
public class Song implements Comparable<Song>{
    private String trackName;
    private String artistName;
    private int releaseYear;
    private long streams;
    private int bpm;
    private int danceability;

    // Constructor
    public Song(String trackName, String artistName, int releaseYear, long streams, int bpm, int danceability) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.releaseYear = releaseYear;
        this.streams = streams;
        this.bpm = bpm;
        this.danceability = danceability;
    }

    // Copy Constructor
    public Song(Song other) {
        this(other.trackName, other.artistName, other.releaseYear, other.streams, other.bpm, other.danceability);
    }

    // toString method
    @Override
    public String toString() {
        return "Song{" +
                "trackName='" + trackName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", releaseYear=" + releaseYear +
                ", streams=" + streams +
                ", bpm=" + bpm +
                ", danceability=" + danceability +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        // Compare only trackName and artistName
        return trackName.equals(song.trackName);
    }

    // compareTo method (based on streams)
    @Override
    public int compareTo(Song other) {
        return Long.compare(this.streams, other.streams);
    }

    // Getters (if needed)
    public String getTrackName() { return trackName; }
    public String getArtistName() { return artistName; }
    public int getReleaseYear() { return releaseYear; }
    public long getStreams() { return streams; }
    public int getBpm() { return bpm; }
    public int getDanceability() { return danceability; }
}
